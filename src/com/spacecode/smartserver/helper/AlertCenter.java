//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spacecode.smartserver.helper;

import com.spacecode.sdk.device.data.DeviceStatus;
import com.spacecode.sdk.device.event.AccessControlEventHandler;
import com.spacecode.sdk.device.event.BasicEventHandler;
import com.spacecode.sdk.device.event.DoorEventHandler;
import com.spacecode.sdk.device.event.TemperatureEventHandler;
import com.spacecode.sdk.network.alert.AlertType;
import com.spacecode.sdk.user.User;
import com.spacecode.sdk.user.data.AccessType;
import com.spacecode.sdk.user.data.FingerIndex;
import com.spacecode.smartserver.SmartServer;
import com.spacecode.smartserver.database.DbManager;
import com.spacecode.smartserver.database.dao.DaoAlert;
import com.spacecode.smartserver.database.dao.DaoAlertHistory;
import com.spacecode.smartserver.database.dao.DaoAlertTemperature;
import com.spacecode.smartserver.database.dao.DaoAlertType;
import com.spacecode.smartserver.database.dao.DaoSmtpServer;
import com.spacecode.smartserver.database.dao.DaoUser;
import com.spacecode.smartserver.database.entity.AlertEntity;
import com.spacecode.smartserver.database.entity.AlertHistoryEntity;
import com.spacecode.smartserver.database.entity.AlertTemperatureEntity;
import com.spacecode.smartserver.database.entity.AlertTypeEntity;
import com.spacecode.smartserver.database.entity.Entity;
import com.spacecode.smartserver.database.entity.SmtpServerEntity;
import com.spacecode.smartserver.database.entity.UserEntity;
import com.spacecode.smartserver.helper.DeviceHandler;
import com.spacecode.smartserver.helper.SmartLogger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public final class AlertCenter {
    private static Session _mailSession;
    private static SmtpServerEntity _smtpServerConfiguration;
    private static boolean _isSmtpServerSet;
    private static String _lastAuthenticatedUsername;
    private static DaoAlert _daoAlert;
    private static DaoAlertHistory _daoAlertHistory;
    private static DaoAlertType _daoAlertType;
    private static DaoAlertTemperature _daoAlertTemperature;

    private AlertCenter() {
    }

    public static void initialize() {
        _isSmtpServerSet = initializeSmtpServer();
        if(!_isSmtpServerSet) {
            SmartLogger.getLogger().warning("No SMTP server is set. AlertCenter won\'t send any email.");
        }

        _daoAlert = (DaoAlert)DbManager.getDao(AlertEntity.class);
        _daoAlertHistory = (DaoAlertHistory)DbManager.getDao(AlertHistoryEntity.class);
        _daoAlertType = (DaoAlertType)DbManager.getDao(AlertTypeEntity.class);
        _daoAlertTemperature = (DaoAlertTemperature)DbManager.getDao(AlertTemperatureEntity.class);
        DeviceHandler.getDevice().addListener(new AlertCenter.AlertEventHandler(null));
    }

    private static boolean initializeSmtpServer() {
        DaoSmtpServer daoSmtpServer = (DaoSmtpServer)DbManager.getDao(SmtpServerEntity.class);
        final SmtpServerEntity sse = daoSmtpServer.getSmtpServerConfig();
        if(sse == null) {
            return false;
        } else {
            _smtpServerConfiguration = sse;
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", sse.getAddress());
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "false");
            props.put("mail.smtp.port", Integer.valueOf(sse.getPort()));
            props.put("mail.smtp.socketFactory.port", Integer.valueOf(sse.getPort()));
            props.put("mail.smtp.starttls.enable", Boolean.valueOf(sse.isSslEnabled()));
            _mailSession = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sse.getUsername(), sse.getPassword());
                }
            });
            return true;
        }
    }

    private static void sendEmail(AlertEntity alertEntity) {
        if(_isSmtpServerSet) {
            try {
                String me = alertEntity.getToList();
                String recipientsCc = alertEntity.getCcList();
                String recipientsBcc = alertEntity.getBccList();
                InternetAddress[] toList = InternetAddress.parse(me == null?"":me);
                InternetAddress[] ccList = InternetAddress.parse(recipientsCc == null?"":recipientsCc);
                InternetAddress[] bccList = InternetAddress.parse(recipientsBcc == null?"":recipientsBcc);
                MimeMessage message = new MimeMessage(_mailSession);
                message.setSubject(alertEntity.getEmailSubject());
                message.setFrom(new InternetAddress(_smtpServerConfiguration.getUsername()));
                message.setRecipients(RecipientType.TO, toList);
                message.addRecipients(RecipientType.CC, ccList);
                message.addRecipients(RecipientType.BCC, bccList);
                message.setContent(alertEntity.getEmailContent(), "text/html");
                Transport.send(message);
            } catch (MessagingException var8) {
                SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while sending an alert email. Id: " + alertEntity.getId(), var8);
            }

        }
    }

    private static void recordAndSend(Collection<AlertEntity> matchingAlerts, String extraData) {
        if(matchingAlerts != null && !matchingAlerts.isEmpty()) {
            ArrayList alertHistoryEntities = new ArrayList();
            Iterator i$ = matchingAlerts.iterator();

            while(i$.hasNext()) {
                AlertEntity ae = (AlertEntity)i$.next();
                alertHistoryEntities.add(new AlertHistoryEntity(ae, extraData));
                sendEmail(ae);
                SmartLogger.getLogger().info("Raising an Alert (id: " + ae.getId() + ")!");
            }

            if(!_daoAlertHistory.insert(alertHistoryEntities)) {
                SmartLogger.getLogger().severe("Unable to insert AlertHistory entities.");
            }

        }
    }

    private static class AlertEventHandler implements BasicEventHandler, DoorEventHandler, AccessControlEventHandler, TemperatureEventHandler {
        private AlertEventHandler(Object o) {
        }

        public void deviceDisconnected() {
            AlertTypeEntity alertTypeDisconnected = AlertCenter._daoAlertType.fromAlertType(AlertType.DEVICE_DISCONNECTED);
            if(alertTypeDisconnected != null) {
                List matchingAlerts = AlertCenter._daoAlert.getEnabledAlerts(alertTypeDisconnected);
                ArrayList notifiableAlerts = new ArrayList();
                notifiableAlerts.addAll(matchingAlerts);
                this.notifyAlertEvent(notifiableAlerts, "");
                AlertCenter.recordAndSend(matchingAlerts, "");
            }
        }

        public void doorOpenDelay() {
            AlertTypeEntity alertTypeDoorDelay = AlertCenter._daoAlertType.fromAlertType(AlertType.DOOR_OPEN_DELAY);
            if(alertTypeDoorDelay != null) {
                List matchingAlerts = AlertCenter._daoAlert.getEnabledAlerts(alertTypeDoorDelay);
                ArrayList notifiableAlerts = new ArrayList();
                notifiableAlerts.addAll(matchingAlerts);
                this.notifyAlertEvent(notifiableAlerts, AlertCenter._lastAuthenticatedUsername);
                AlertCenter.recordAndSend(matchingAlerts, AlertCenter._lastAuthenticatedUsername);
            }
        }

        public void authenticationSuccess(User grantedUser, AccessType accessType, boolean isMaster) {
            DaoUser daoUser = (DaoUser)DbManager.getDao(UserEntity.class);
            AlertCenter._lastAuthenticatedUsername = grantedUser.getUsername();
            if(accessType == AccessType.FINGERPRINT) {
                UserEntity gue = (UserEntity)daoUser.getEntityBy("username", grantedUser.getUsername());
                if(gue != null && gue.getThiefFingerIndex() != null) {
                    FingerIndex index = DeviceHandler.getDevice().getUsersService().getLastVerifiedFingerIndex(isMaster);
                    AlertTypeEntity alertTypeThiefFinger = AlertCenter._daoAlertType.fromAlertType(AlertType.THIEF_FINGER);
                    if(index != null && alertTypeThiefFinger != null && index.getIndex() == gue.getThiefFingerIndex().intValue()) {
                        List matchingAlerts = AlertCenter._daoAlert.getEnabledAlerts(alertTypeThiefFinger);
                        ArrayList notifiableAlerts = new ArrayList();
                        notifiableAlerts.addAll(matchingAlerts);
                        this.notifyAlertEvent(notifiableAlerts, AlertCenter._lastAuthenticatedUsername);
                        AlertCenter.recordAndSend(matchingAlerts, AlertCenter._lastAuthenticatedUsername);
                    }
                }
            }
        }

        public void temperatureMeasure(double value) {
            if(value != 777.0D) {
                AlertTypeEntity alertTypeTemperature = AlertCenter._daoAlertType.fromAlertType(AlertType.TEMPERATURE);
                if(alertTypeTemperature != null) {
                    List alerts = AlertCenter._daoAlert.getEnabledAlerts(alertTypeTemperature);
                    if(!alerts.isEmpty()) {
                        ArrayList alertIds = new ArrayList();
                        Iterator atList = alerts.iterator();

                        while(atList.hasNext()) {
                            AlertEntity matchingAlerts = (AlertEntity)atList.next();
                            alertIds.add(Integer.valueOf(matchingAlerts.getId()));
                        }

                        List atList1 = AlertCenter._daoAlertTemperature.getEntitiesWhereIn("alert_id", alertIds);
                        HashMap matchingAlerts1 = new HashMap();
                        Iterator extraData = atList1.iterator();

                        while(extraData.hasNext()) {
                            AlertTemperatureEntity at = (AlertTemperatureEntity)extraData.next();
                            if(value > at.getTemperatureMax() || value < at.getTemperatureMin()) {
                                matchingAlerts1.put(at, at.getAlert());
                            }
                        }

                        if(!matchingAlerts1.isEmpty()) {
                            String extraData1 = String.valueOf(value);
                            this.notifyAlertEvent(matchingAlerts1.keySet(), extraData1);
                            AlertCenter.recordAndSend(matchingAlerts1.values(), extraData1);
                        }
                    }
                }
            }
        }

        private void notifyAlertEvent(Collection<Entity> alertEntities, String additionalData) {
            Iterator i$ = alertEntities.iterator();

            while(i$.hasNext()) {
                Entity alertEntity = (Entity)i$.next();
                if(alertEntity instanceof AlertEntity) {
                    SmartServer.sendAllClients(new String[]{"event_alert", AlertEntity.toAlert((AlertEntity)alertEntity).serialize(), additionalData});
                } else if(alertEntity instanceof AlertTemperatureEntity) {
                    SmartServer.sendAllClients(new String[]{"event_alert", AlertEntity.toAlert((AlertTemperatureEntity)alertEntity).serialize(), additionalData});
                }
            }

        }

        public void scanCancelledByDoor() {
        }

        public void doorOpened() {
        }

        public void doorClosed() {
        }

        public void authenticationFailure(User grantedUser, AccessType accessType, boolean isMaster) {
        }

        public void deviceStatusChanged(DeviceStatus status) {
        }
    }
}
