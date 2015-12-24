//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spacecode.smartserver.database.dao;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.spacecode.sdk.user.User;
import com.spacecode.sdk.user.data.FingerIndex;
import com.spacecode.sdk.user.data.GrantType;
import com.spacecode.smartserver.database.DbManager;
import com.spacecode.smartserver.database.dao.DaoEntity;
import com.spacecode.smartserver.database.dao.DaoFingerprint;
import com.spacecode.smartserver.database.dao.DaoGrantType;
import com.spacecode.smartserver.database.dao.DaoGrantedAccess;
import com.spacecode.smartserver.database.entity.DeviceEntity;
import com.spacecode.smartserver.database.entity.FingerprintEntity;
import com.spacecode.smartserver.database.entity.GrantedAccessEntity;
import com.spacecode.smartserver.database.entity.UserEntity;
import com.spacecode.smartserver.helper.SmartLogger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;

public class DaoUser extends DaoEntity<UserEntity, Integer> {
    public DaoUser(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, UserEntity.class);
    }

    public UserEntity getByUsername(String username) {
        return username != null && !username.trim().isEmpty()?(UserEntity)this.getEntityBy("username", username):null;
    }

    public boolean updateBadgeNumber(String username, String badgeNumber) {
        UserEntity gue = this.getByUsername(username);
        if(gue == null) {
            return false;
        } else {
            gue.setBadgeNumber(badgeNumber);
            return this.updateEntity(gue);
        }
    }

    public boolean updateThiefFingerIndex(String username, Integer fingerIndex) {
        UserEntity gue = this.getByUsername(username);
        if(gue == null) {
            return false;
        } else {
            gue.setThiefFingerIndex(fingerIndex);
            return this.updateEntity(gue);
        }
    }

    public boolean persist(User newUser) {
        try {
            TransactionManager.callInTransaction(DbManager.getConnectionSource(), new DaoUser.PersistUserCallable(newUser));
            return true;
        } catch (SQLException var3) {
            SmartLogger.getLogger().log(Level.SEVERE, "Error while persisting new user.", var3);
            return false;
        }
    }

    public boolean removePermission(String username) {
        UserEntity gue = this.getByUsername(username);
        DeviceEntity devEntity = DbManager.getDevEntity();
        DaoGrantedAccess gaRepo = (DaoGrantedAccess)DbManager.getDao(GrantedAccessEntity.class);
        if(gue != null && devEntity != null && gaRepo != null) {
            ForeignCollection gaesList = gue.getGrantedAccesses();
            ArrayList gaesOndevice = new ArrayList();
            Iterator i$ = gaesList.iterator();

            while(i$.hasNext()) {
                GrantedAccessEntity gae = (GrantedAccessEntity)i$.next();
                if(gae.getDevice().getId() == devEntity.getId()) {
                    gaesOndevice.add(gae);
                }
            }

            DbManager.forceUpdate(gue);
            return gaRepo.deleteEntity(gaesOndevice);
        } else {
            return false;
        }
    }

    public boolean sortUsersFromDb(List<User> authorizedUsers, List<User> unregisteredUsers) {
        if(authorizedUsers != null && unregisteredUsers != null) {
            authorizedUsers.clear();
            unregisteredUsers.clear();
            if(DbManager.getDevEntity() == null) {
                return false;
            } else {
                DaoGrantedAccess daoAccess = (DaoGrantedAccess)DbManager.getDao(GrantedAccessEntity.class);
                if(daoAccess == null) {
                    return false;
                } else {
                    ArrayList authorizedIds = new ArrayList();
                    List grantedAccesses = daoAccess.getEntitiesBy("device_id", Integer.valueOf(DbManager.getDevEntity().getId()));
                    Iterator unregEntities = grantedAccesses.iterator();

                    while(unregEntities.hasNext()) {
                        GrantedAccessEntity i$ = (GrantedAccessEntity)unregEntities.next();
                        EnumMap unregEntity = new EnumMap(FingerIndex.class);
                        UserEntity fingers = i$.getUser();
                        GrantType i$1 = DaoGrantType.asGrantType(i$.getGrantType());
                        if(fingers != null && i$1 != null) {
                            authorizedIds.add(Integer.valueOf(fingers.getId()));
                            Iterator fpe = fingers.getFingerprints().iterator();

                            while(fpe.hasNext()) {
                                FingerprintEntity fIndex = (FingerprintEntity)fpe.next();
                                FingerIndex fIndex1 = FingerIndex.getValueByIndex(fIndex.getFingerIndex());
                                if(fIndex1 == null) {
                                    SmartLogger.getLogger().warning("Null value on Finger Index while loading Authorized Users...");
                                } else {
                                    unregEntity.put(fIndex1, fIndex.getTemplate());
                                }
                            }

                            authorizedUsers.add(new User(fingers.getUsername(), i$1, fingers.getBadgeNumber(), unregEntity));
                        } else {
                            SmartLogger.getLogger().warning("Null value on User or GrantType while loading Authorized Users...");
                        }
                    }

                    List unregEntities1 = this.getEntitiesWhereNotIn("id", authorizedIds);
                    Iterator i$2 = unregEntities1.iterator();

                    while(i$2.hasNext()) {
                        UserEntity unregEntity1 = (UserEntity)i$2.next();
                        EnumMap fingers1 = new EnumMap(FingerIndex.class);
                        Iterator i$3 = unregEntity1.getFingerprints().iterator();

                        while(i$3.hasNext()) {
                            FingerprintEntity fpe1 = (FingerprintEntity)i$3.next();
                            FingerIndex fIndex2 = FingerIndex.getValueByIndex(fpe1.getFingerIndex());
                            if(fIndex2 == null) {
                                SmartLogger.getLogger().warning("Null value on Finger Index while loading Unregistered Users...");
                            } else {
                                fingers1.put(fIndex2, fpe1.getTemplate());
                            }
                        }

                        unregisteredUsers.add(new User(unregEntity1.getUsername(), GrantType.UNDEFINED, unregEntity1.getBadgeNumber(), fingers1));
                    }

                    return true;
                }
            }
        } else {
            return false;
        }
    }

    private class PersistUserCallable implements Callable<Void> {
        private final User _newUser;

        private PersistUserCallable(User newUser) {
            this._newUser = newUser;
        }

        public Void call() throws Exception {
            UserEntity gue = DaoUser.this.getByUsername(this._newUser.getUsername());
            if(gue == null) {
                gue = new UserEntity(this._newUser);
                if(!DaoUser.this.insert(gue)) {
                    throw new SQLException("Failed when inserting new user.");
                }

                DaoUser.this.refresh(gue);
                DaoFingerprint daoGrantedAccess = (DaoFingerprint)DbManager.getDao(FingerprintEntity.class);
                Iterator i$ = this._newUser.getEnrolledFingersIndexes().iterator();

                while(i$.hasNext()) {
                    FingerIndex index = (FingerIndex)i$.next();
                    if(!daoGrantedAccess.updateEntity(new FingerprintEntity(gue, index.getIndex(), this._newUser.getFingerprintTemplate(index)))) {
                        throw new SQLException("Failed when inserting new user\'s fingerprints.");
                    }
                }
            }

            DaoGrantedAccess daoGrantedAccess1 = (DaoGrantedAccess)DbManager.getDao(GrantedAccessEntity.class);
            if(!daoGrantedAccess1.persist(gue, this._newUser.getPermission())) {
                throw new SQLException("Failed when inserting new permission.");
            } else {
                return null;
            }
        }
    }
}
