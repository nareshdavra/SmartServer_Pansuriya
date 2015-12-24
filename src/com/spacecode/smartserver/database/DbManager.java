//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spacecode.smartserver.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.spacecode.sdk.network.alert.AlertType;
import com.spacecode.sdk.user.data.AccessType;
import com.spacecode.sdk.user.data.GrantType;
import com.spacecode.smartserver.database.dao.DaoDevice;
import com.spacecode.smartserver.database.dao.DaoEntity;
import com.spacecode.smartserver.database.entity.AccessTypeEntity;
import com.spacecode.smartserver.database.entity.AlertEntity;
import com.spacecode.smartserver.database.entity.AlertHistoryEntity;
import com.spacecode.smartserver.database.entity.AlertTemperatureEntity;
import com.spacecode.smartserver.database.entity.AlertTypeEntity;
import com.spacecode.smartserver.database.entity.AuthenticationEntity;
import com.spacecode.smartserver.database.entity.DeviceEntity;
import com.spacecode.smartserver.database.entity.Entity;
import com.spacecode.smartserver.database.entity.FingerprintEntity;
import com.spacecode.smartserver.database.entity.GrantTypeEntity;
import com.spacecode.smartserver.database.entity.GrantedAccessEntity;
import com.spacecode.smartserver.database.entity.InventoryEntity;
import com.spacecode.smartserver.database.entity.InventoryRfidTag;
import com.spacecode.smartserver.database.entity.RfidTagEntity;
import com.spacecode.smartserver.database.entity.SmtpServerEntity;
import com.spacecode.smartserver.database.entity.TemperatureMeasurementEntity;
import com.spacecode.smartserver.database.entity.UserEntity;
import com.spacecode.smartserver.helper.ConfManager;
import com.spacecode.smartserver.helper.DeviceHandler;
import com.spacecode.smartserver.helper.SmartLogger;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

public class DbManager {
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "smartserver";
    private static final String DB_USER = "spacecode";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/smartserver?user=spacecode&password=Spacecode4sql";
    private static JdbcPooledConnectionSource _pooledConnectionSrc;
    private static DeviceEntity _deviceEntity;

    private DbManager() {
    }

    private static String getConnectionString() {
        String confDbHost = ConfManager.getDbHost();
        String confDbPort = ConfManager.getDbPort();
        String confDbDbms = ConfManager.getDbDbms();
        String confDbName = ConfManager.getDbName();
        String confDbUser = ConfManager.getDbUser();
        if(confDbHost != null && confDbDbms != null && confDbName != null && confDbUser != null) {
            confDbPort = confDbPort == null?"":confDbPort;
            byte var6 = -1;
            switch(confDbDbms.hashCode()) {
                case -2105481388:
                    if(confDbDbms.equals("postgresql")) {
                        var6 = 1;
                    }
                    break;
                case -1874470255:
                    if(confDbDbms.equals("sqlserver")) {
                        var6 = 0;
                    }
                    break;
                case 104382626:
                    if(confDbDbms.equals("mysql")) {
                        var6 = 2;
                    }
            }

            switch(var6) {
                case 0:
                    confDbPort = "".equals(confDbPort)?"1433":confDbPort;
                    return String.format("jdbc:%s://%s:%s;databaseName=%s;", new Object[]{confDbDbms, confDbHost, confDbPort, confDbName});
                case 1:
                    confDbPort = "".equals(confDbPort)?"5432":confDbPort;
                    return String.format("jdbc:%s://%s:%s/%s", new Object[]{confDbDbms, confDbHost, confDbPort, confDbName});
                case 2:
                    confDbPort = "".equals(confDbPort)?"3306":confDbPort;
                    return String.format("jdbc:%s://%s:%s/%s", new Object[]{confDbDbms, confDbHost, confDbPort, confDbName});
                default:
                    return "jdbc:mysql://localhost:3306/smartserver?user=spacecode&password=Spacecode4sql";
            }
        } else {
            return "jdbc:mysql://localhost:3306/smartserver?user=spacecode&password=Spacecode4sql";
        }
    }

    public static boolean initializeDatabase() {
        try {
            String sqle = getConnectionString();
            if("jdbc:mysql://localhost:3306/smartserver?user=spacecode&password=Spacecode4sql".equals(sqle)) {
                _pooledConnectionSrc = new JdbcPooledConnectionSource("jdbc:mysql://localhost:3306/smartserver?user=spacecode&password=Spacecode4sql");
                SmartLogger.getLogger().warning("Using embedded MySQL database.");
            } else {
                String dbUser = ConfManager.getDbUser() == null?"":ConfManager.getDbUser();
                String dbPassword = ConfManager.getDbPassword() == null?"":ConfManager.getDbPassword();
                _pooledConnectionSrc = new JdbcPooledConnectionSource(sqle, dbUser, dbPassword);
                SmartLogger.getLogger().info("Connecting to database: " + sqle);
            }

            _pooledConnectionSrc.setMaxConnectionAgeMillis(600000L);
            createModelIfNotExists();
            return true;
        } catch (SQLException var3) {
            SmartLogger.getLogger().log(Level.SEVERE, "Unable to connect to the database, or initialize ORM.", var3);
            return false;
        }
    }

    public static JdbcPooledConnectionSource getConnectionSource() {
        return _pooledConnectionSrc;
    }

    public static void close() {
        if(_pooledConnectionSrc != null && _pooledConnectionSrc.isOpen()) {
            try {
                _pooledConnectionSrc.close();
                _pooledConnectionSrc = null;
            } catch (SQLException var1) {
                SmartLogger.getLogger().log(Level.WARNING, "Unable to close connection pool.", var1);
            }

        }
    }

    private static void createModelIfNotExists() throws SQLException {
        List entityClasses = Arrays.asList(new Class[]{AccessTypeEntity.class, AlertEntity.class, AlertHistoryEntity.class, AlertTemperatureEntity.class, AlertTypeEntity.class, AuthenticationEntity.class, DeviceEntity.class, FingerprintEntity.class, GrantedAccessEntity.class, GrantTypeEntity.class, InventoryEntity.class, InventoryRfidTag.class, RfidTagEntity.class, SmtpServerEntity.class, TemperatureMeasurementEntity.class, UserEntity.class});
        Iterator i$ = entityClasses.iterator();

        while(i$.hasNext()) {
            Class entityClass = (Class)i$.next();
            Dao dao = DaoManager.createDao(_pooledConnectionSrc, entityClass);
            if(!dao.isTableExists()) {
                TableUtils.createTableIfNotExists(_pooledConnectionSrc, entityClass);
                onTableCreated(entityClass, dao);
            }
        }

    }

    private static void onTableCreated(Class entityClass, Dao<Entity, Integer> dao) throws SQLException {
        if(entityClass.equals(AccessTypeEntity.class)) {
            dao.create(new AccessTypeEntity(AccessType.UNDEFINED.name()));
            dao.create(new AccessTypeEntity(AccessType.BADGE.name()));
            dao.create(new AccessTypeEntity(AccessType.FINGERPRINT.name()));
        } else if(entityClass.equals(AlertTypeEntity.class)) {
            dao.create(new AlertTypeEntity(AlertType.DEVICE_DISCONNECTED.name()));
            dao.create(new AlertTypeEntity(AlertType.DOOR_OPEN_DELAY.name()));
            dao.create(new AlertTypeEntity(AlertType.TEMPERATURE.name()));
            dao.create(new AlertTypeEntity(AlertType.THIEF_FINGER.name()));
        } else if(entityClass.equals(GrantTypeEntity.class)) {
            dao.create(new GrantTypeEntity(GrantType.UNDEFINED.name()));
            dao.create(new GrantTypeEntity(GrantType.SLAVE.name()));
            dao.create(new GrantTypeEntity(GrantType.MASTER.name()));
            dao.create(new GrantTypeEntity(GrantType.ALL.name()));
        }

    }

    public static <E extends Entity> DaoEntity<E, Integer> getDao(Class<E> entityClass) {
        try {
            return (DaoEntity)DaoManager.createDao(_pooledConnectionSrc, entityClass);
        } catch (SQLException var2) {
            SmartLogger.getLogger().log(Level.WARNING, "Unable to get requested DAO instance.", var2);
            throw new RuntimeException("Unable to create a DAO: Does the entity class has a daoClass?");
        }
    }

    public static DeviceEntity getDevEntity() {
        if(_deviceEntity != null) {
            return _deviceEntity;
        } else {
            DaoDevice daoDevice = (DaoDevice)getDao(DeviceEntity.class);
            DeviceEntity result = (DeviceEntity)daoDevice.getEntityBy("serial_number", DeviceHandler.getDevice().getSerialNumber());
            _deviceEntity = result == null?null:result;
            return _deviceEntity;
        }
    }

    public static boolean createDeviceIfNotExists(String devSerialNumber) {
        DaoDevice daoDevice = (DaoDevice)getDao(DeviceEntity.class);
        return getDevEntity() != null || daoDevice.insert(new DeviceEntity(devSerialNumber)) && getDevEntity() != null;
    }

    public static <E extends Entity> boolean forceUpdate(E entity) {
        DaoEntity daoEntity = getDao(entity.getClass());
        return daoEntity.updateEntity(entity);
    }
}
