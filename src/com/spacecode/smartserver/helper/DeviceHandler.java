//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spacecode.smartserver.helper;

import com.spacecode.sdk.device.Device;
import com.spacecode.sdk.device.DeviceCreationException;
import com.spacecode.sdk.device.data.DeviceStatus;
import com.spacecode.sdk.device.data.Inventory;
import com.spacecode.sdk.device.data.PluggedDevice;
import com.spacecode.sdk.device.event.AccessControlEventHandler;
import com.spacecode.sdk.device.event.AccessModuleEventHandler;
import com.spacecode.sdk.device.event.BasicEventHandler;
import com.spacecode.sdk.device.event.DoorEventHandler;
import com.spacecode.sdk.device.event.LedEventHandler;
import com.spacecode.sdk.device.event.MaintenanceEventHandler;
import com.spacecode.sdk.device.event.ScanEventHandler;
import com.spacecode.sdk.device.event.TemperatureEventHandler;
import com.spacecode.sdk.device.module.authentication.FingerprintReader;
import com.spacecode.sdk.device.module.authentication.FingerprintReaderException;
import com.spacecode.sdk.user.User;
import com.spacecode.sdk.user.data.AccessType;
import com.spacecode.smartserver.SmartServer;
import com.spacecode.smartserver.database.DbManager;
import com.spacecode.smartserver.database.dao.DaoAuthentication;
import com.spacecode.smartserver.database.dao.DaoInventory;
import com.spacecode.smartserver.database.dao.DaoUser;
import com.spacecode.smartserver.database.entity.AuthenticationEntity;
import com.spacecode.smartserver.database.entity.InventoryEntity;
import com.spacecode.smartserver.database.entity.UserEntity;
import com.spacecode.smartserver.helper.AlertCenter;
import com.spacecode.smartserver.helper.ConfManager;
import com.spacecode.smartserver.helper.SmartLogger;
import com.spacecode.smartserver.helper.TemperatureCenter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public final class DeviceHandler {
    private static volatile Device DEVICE;
    private static volatile boolean RECORD_INVENTORY = true;
    private static boolean SERIAL_PORT_FORWARDING = false;

    private DeviceHandler() {
    }

    public static synchronized boolean connectDevice() {
        if(DEVICE != null) {
            return true;
        } else {
            Map pluggedDevices = Device.getPluggedDevices();
            if(!pluggedDevices.isEmpty() && pluggedDevices.size() <= 1) {
                PluggedDevice deviceInfo = (PluggedDevice)((Entry)pluggedDevices.entrySet().iterator().next()).getValue();

                try {
                    DEVICE = new Device((String)null, deviceInfo.getSerialPort());
                    DEVICE.addListener(new DeviceHandler.SmartEventHandler());
                    return true;
                } catch (DeviceCreationException var3) {
                    SmartLogger.getLogger().log(Level.INFO, "Unable to instantiate a device.", var3);
                    return false;
                }
            } else {
                SmartLogger.getLogger().warning("0 or more than 1 device detected.");
                return false;
            }
        }
    }

    public static synchronized void disconnectDevice() {
        if(DEVICE != null) {
            DEVICE.release();
            DEVICE = null;
        }

    }

    public static boolean reconnectDevice() {
        boolean deviceConnected = false;
        long initialTimestamp = System.currentTimeMillis();

        while(!SERIAL_PORT_FORWARDING && System.currentTimeMillis() - initialTimestamp < 3600000L) {
            SmartLogger.getLogger().info("Reconnecting Device...");
            deviceConnected = connectDevice();
            if(deviceConnected) {
                SmartServer.sendAllClients(new String[]{"event_status_changed", DeviceStatus.READY.name()});
                onConnected();
                break;
            }

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException var4) {
                SmartLogger.getLogger().log(Level.WARNING, "Interrupted while trying to reconnect Device.", var4);
                break;
            }
        }

        return deviceConnected;
    }

    public static Device getDevice() {
        return DEVICE;
    }

    public static void connectModules() {
        if(DEVICE == null) {
            SmartLogger.getLogger().warning("Unable to connect modules, the device is not initialized.");
        } else {
            String fprMaster = ConfManager.getDevFprMaster();
            String fprSlave = ConfManager.getDevFprSlave();

            try {
                if(fprMaster != null && !fprMaster.trim().isEmpty()) {
                    if(fprSlave != null && !fprSlave.trim().isEmpty()) {
                        if(FingerprintReader.connectFingerprintReaders(2) != 2) {
                            SmartLogger.getLogger().warning("Couldn\'t initialize the two fingerprint readers.");
                        } else if(!DEVICE.addFingerprintReader(fprMaster, true) || !DEVICE.addFingerprintReader(fprSlave, false)) {
                            SmartLogger.getLogger().warning("Couldn\'t connect the two fingerprint readers.");
                        }
                    } else if(FingerprintReader.connectFingerprintReader() != 1) {
                        SmartLogger.getLogger().warning("Couldn\'t initialize the fingerprint reader.");
                    } else if(!DEVICE.addFingerprintReader(fprMaster, true)) {
                        SmartLogger.getLogger().warning("Couldn\'t connect the fingerprint reader.");
                    }
                }
            } catch (FingerprintReaderException var4) {
                SmartLogger.getLogger().log(Level.INFO, "An unexpected error occurred during fingerprint readers initialization.", var4);
            }

            String brMaster = ConfManager.getDevBrMaster();
            String brSlave = ConfManager.getDevBrSlave();
            if(brMaster != null && !brMaster.trim().isEmpty()) {
                if(!DEVICE.addBadgeReader(brMaster, true)) {
                    SmartLogger.getLogger().warning("Unable to add Master Badge Reader on " + brMaster);
                }

                if(brSlave != null && !brSlave.trim().isEmpty() && !DEVICE.addBadgeReader(brSlave, false)) {
                    SmartLogger.getLogger().warning("Unable to add Slave Badge Reader on " + brSlave);
                }
            }
            //DEVICE.
            connectProbeIfEnabled();
        }
    }

    private static void connectProbeIfEnabled() {
        if(ConfManager.isDevTemperature()) {
            int measurementDelay = ConfManager.getDevTemperatureDelay();
            double measurementDelta = ConfManager.getDevTemperatureDelta();
            measurementDelay = measurementDelay == -1?60:measurementDelay;
            measurementDelta = measurementDelta == -1.0D?0.3D:measurementDelta;
            if(!DEVICE.addTemperatureProbe("tempProbe1", measurementDelay, measurementDelta)) {
                SmartLogger.getLogger().warning("Unable to add the Temperature probe.");
            }
        }

    }

    public static void reloadTemperatureProbe() {
        DEVICE.disconnectTemperatureProbe();
        connectProbeIfEnabled();
    }

    public static void setForwardingSerialPort(boolean state) {
        SERIAL_PORT_FORWARDING = state;
    }

    public static boolean isAvailable() {
        return !SERIAL_PORT_FORWARDING && DEVICE != null;
    }

    public static boolean loadUsers() {
        if(!isAvailable()) {
            return false;
        } else {
            DaoUser userRepo = (DaoUser)DbManager.getDao(UserEntity.class);
            ArrayList authorizedUsers = new ArrayList();
            ArrayList unregisteredUsers = new ArrayList();
            if(!userRepo.sortUsersFromDb(authorizedUsers, unregisteredUsers)) {
                SmartLogger.getLogger().severe("An error occurred when getting Authorized/Unregistered users from DB.");
                return false;
            } else {
                List notAddedUsers = DEVICE.getUsersService().addUsers(authorizedUsers);
                if(!notAddedUsers.isEmpty()) {
                    SmartLogger.getLogger().warning(notAddedUsers.size() + " Authorized users could not be added.");
                    notAddedUsers.clear();
                }

                notAddedUsers = DEVICE.getUsersService().addUsers(unregisteredUsers);
                if(!notAddedUsers.isEmpty()) {
                    SmartLogger.getLogger().warning(notAddedUsers.size() + " Unregistered users could not be added.");
                    notAddedUsers.clear();
                }

                Iterator i$ = unregisteredUsers.iterator();

                while(i$.hasNext()) {
                    User unregUser = (User)i$.next();
                    DEVICE.getUsersService().removeUser(unregUser.getUsername());
                }

                return true;
            }
        }
    }

    public static boolean loadLastInventory() {
        DaoInventory daoInventory = (DaoInventory)DbManager.getDao(InventoryEntity.class);
        Inventory lastInventoryRecorded = daoInventory.getLastInventory();
        if(lastInventoryRecorded == null) {
            return false;
        } else {
            DEVICE.setLastInventory(lastInventoryRecorded);
            return true;
        }
    }

    public static boolean onConnected() {
        boolean result = true;
        connectModules();
        if(!loadUsers()) {
            SmartLogger.getLogger().severe("FATAL ERROR: Users could not be loaded from Database.");
            result = false;
        }

        if(!loadLastInventory()) {
            SmartLogger.getLogger().info("No \"last\" Inventory loaded: none found.");
        }

        AlertCenter.initialize();
        TemperatureCenter.initialize();
        return result;
    }

    public static void setRecordInventory(boolean state) {
        RECORD_INVENTORY = state;
    }

    public static boolean getRecordInventory() {
        return RECORD_INVENTORY;
    }

    static class SmartEventHandler implements BasicEventHandler, ScanEventHandler, DoorEventHandler, AccessControlEventHandler, AccessModuleEventHandler, TemperatureEventHandler, LedEventHandler, MaintenanceEventHandler {
        SmartEventHandler() {
        }

        public void deviceDisconnected() {
            SmartLogger.getLogger().info("Device Disconnected...");
            SmartServer.sendAllClients(new String[]{"event_device_disconnected"});
            DeviceHandler.DEVICE = null;
            DeviceHandler.reconnectDevice();
        }

        public void doorOpened() {
            SmartServer.sendAllClients(new String[]{"event_door_opened"});
        }

        public void doorClosed() {
            SmartServer.sendAllClients(new String[]{"event_door_closed"});
        }

        public void doorOpenDelay() {
            SmartServer.sendAllClients(new String[]{"event_door_open_delay"});
        }

        public void scanStarted() {
            SmartServer.sendAllClients(new String[]{"event_scan_started"});
        }

        public void scanCancelledByHost() {
            SmartServer.sendAllClients(new String[]{"event_scan_cancelled_by_host"});
        }

        public void scanCompleted() {
            Inventory newInventory = DeviceHandler.DEVICE.getLastInventory();
            if(DeviceHandler.RECORD_INVENTORY && (newInventory.getNumberAdded() != 0 || newInventory.getNumberRemoved() != 0 || newInventory.getNumberPresent() != 0 || newInventory.getAccessType() != AccessType.UNDEFINED)) {
                DaoInventory daoInventory = (DaoInventory)DbManager.getDao(InventoryEntity.class);
                daoInventory.persist(newInventory);
                //DaoAuthentication daoAuthentication = (DaoAuthentication)DbManager.getDao(AuthenticationEntity.class);
                //daoAuthentication.persist(lastUser, lastAccessType,lastDoorinfo);
            }
            SmartServer.sendAllClients(new String[]{"event_scan_completed"});
        }

        public void scanFailed() {
            SmartServer.sendAllClients(new String[]{"event_scan_failed"});
        }

        public void tagAdded(String tagUID) {
            SmartServer.sendAllClients(new String[]{"event_tag_added", tagUID});
        }

        public void authenticationSuccess(User grantedUser, AccessType accessType, boolean isMaster) {
            SmartServer.sendAllClients(new String[]{"event_authentication_success", grantedUser.serialize(), accessType.name(), String.valueOf(isMaster)});
            //lastDoorinfo  = (isMaster) ? DoorInfo.DI_MASTER_DOOR: DoorInfo.DI_SLAVE_DOOR;
            lastUser = grantedUser;
            lastAccessType = accessType;
        }

        User lastUser;
        AccessType lastAccessType;
        //DoorInfo lastDoorinfo;
        public void authenticationFailure(User grantedUser, AccessType accessType, boolean isMaster) {
            SmartServer.sendAllClients(new String[]{"event_authentication_failure", grantedUser.serialize(), accessType.name(), String.valueOf(isMaster)});
        }

        public void fingerTouched(boolean isMaster) {
            SmartServer.sendAllClients(new String[]{"event_finger_touched", Boolean.valueOf(isMaster).toString()});
        }

        public void fingerprintEnrollmentSample(byte sampleNumber) {
            SmartServer.sendAllClients(new String[]{"event_enrollment_sample", String.valueOf(sampleNumber)});
        }

        public void badgeReaderConnected(boolean isMaster) {
            SmartLogger.getLogger().info("Badge reader (" + (isMaster?"Master":"Slave") + ") connected.");
        }

        public void badgeReaderDisconnected(boolean isMaster) {
            SmartLogger.getLogger().info("Badge reader (" + (isMaster?"Master":"Slave") + ") disconnected.");
        }

        public void badgeScanned(String badgeNumber) {
            SmartServer.sendAllClients(new String[]{"event_badge_scanned", badgeNumber});
        }

        public void scanCancelledByDoor() {
            SmartLogger.getLogger().info("Scan has been cancelled because someone opened the door.");
            SmartServer.sendAllClients(new String[]{"event_scan_cancelled_by_door"});
        }

        public void temperatureMeasure(double value) {
            SmartServer.sendAllClients(new String[]{"event_temperature_measure", String.valueOf(value)});
        }


        @Override
        public void lightingStarted(List list) {
            ArrayList responsePackets = new ArrayList();
            responsePackets.add("event_lighting_started");
            responsePackets.addAll(list);
            SmartServer.sendAllClients((String[])responsePackets.toArray(new String[responsePackets.size()]));

        }

        public void lightingStopped() {
            SmartServer.sendAllClients(new String[]{"event_lighting_stopped"});
        }

        public void deviceStatusChanged(DeviceStatus status) {
            SmartServer.sendAllClients(new String[]{"event_status_changed", status.name()});
        }

        public void flashingProgress(int rowNumber, int rowCount) {
            SmartServer.sendAllClients(new String[]{"event_flashing_progress", String.valueOf(rowNumber), String.valueOf(rowCount)});
        }

        public void correlationSample(int correlation, int phaseShift) {
        }

        public void correlationSampleSeries(short[] presentSamples, short[] missingSamples) {
            if(presentSamples != null && missingSamples != null) {
                ArrayList responsePackets = new ArrayList();
                responsePackets.add("event_correlation_series");
                responsePackets.add("present");
                short[] arr$ = presentSamples;
                int len$ = presentSamples.length;

                int i$;
                short missingSample;
                for(i$ = 0; i$ < len$; ++i$) {
                    missingSample = arr$[i$];
                    responsePackets.add(String.valueOf(missingSample));
                }

                responsePackets.add("missing");
                arr$ = missingSamples;
                len$ = missingSamples.length;

                for(i$ = 0; i$ < len$; ++i$) {
                    missingSample = arr$[i$];
                    responsePackets.add(String.valueOf(missingSample));
                }

                SmartServer.sendAllClients((String[])responsePackets.toArray(new String[responsePackets.size()]));
            }
        }
    }
}
