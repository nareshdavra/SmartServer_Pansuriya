//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spacecode.smartserver.database.dao;

import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.spacecode.sdk.device.data.Inventory;
import com.spacecode.smartserver.database.DbManager;
import com.spacecode.smartserver.database.dao.DaoAccessType;
import com.spacecode.smartserver.database.dao.DaoEntity;
import com.spacecode.smartserver.database.dao.DaoInventoryRfidTag;
import com.spacecode.smartserver.database.dao.DaoRfidTag;
import com.spacecode.smartserver.database.dao.DaoUser;
import com.spacecode.smartserver.database.entity.AccessTypeEntity;
import com.spacecode.smartserver.database.entity.InventoryEntity;
import com.spacecode.smartserver.database.entity.InventoryRfidTag;
import com.spacecode.smartserver.database.entity.RfidTagEntity;
import com.spacecode.smartserver.database.entity.UserEntity;
import com.spacecode.smartserver.helper.DeviceHandler;
import com.spacecode.smartserver.helper.SmartLogger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;

public class DaoInventory extends DaoEntity<InventoryEntity, Integer> {
    public DaoInventory(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, InventoryEntity.class);
    }

    public Inventory getLastInventory() {
        if(DbManager.getDevEntity() == null) {
            return null;
        } else {
            try {
                InventoryEntity sqle = (InventoryEntity)this.queryForFirst(this.queryBuilder().orderBy("id", false).limit(Long.valueOf(1L)).where().eq("device_id", Integer.valueOf(DbManager.getDevEntity().getId())).prepare());
                return sqle != null?sqle.asInventory():null;
            } catch (SQLException var2) {
                SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting entity where field not equal.", var2);
                return null;
                
            }
        }
    }
    public InventoryEntity getLastInventoryEntity() {
        if(DbManager.getDevEntity() == null) {
            return null;
        } else {
            try {
                InventoryEntity sqle = (InventoryEntity)this.queryForFirst(this.queryBuilder().orderBy("id", false).limit(Long.valueOf(1L)).where().eq("device_id", Integer.valueOf(DbManager.getDevEntity().getId())).prepare());
                return sqle != null?sqle:null;
            } catch (SQLException var2) {
                SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting entity where field not equal.", var2);
                return null;
            }
        }
    }

    public List<Inventory> getInventories(Date from, Date to) {
        ArrayList result = new ArrayList();

        List queryResult;
        try {
            queryResult = this.query(this.queryBuilder().orderBy("created_at", true).where().eq("device_id", Integer.valueOf(DbManager.getDevEntity().getId())).and().between("created_at", from, to).prepare());
        } catch (SQLException var7) {
            SmartLogger.getLogger().log(Level.SEVERE, "SQL Exception occurred while getting inventories.", var7);
            return new ArrayList();
        }

        Iterator i$ = queryResult.iterator();

        while(i$.hasNext()) {
            InventoryEntity invEntity = (InventoryEntity)i$.next();
            result.add(invEntity.asInventory());
        }

        return result;
    }

    public boolean persist(Inventory lastInventory) {
        try {
            TransactionManager.callInTransaction(DbManager.getConnectionSource(), new DaoInventory.PersistInventoryCallable(lastInventory));
            return true;
        } catch (SQLException var3) {
            SmartLogger.getLogger().log(Level.SEVERE, "Error while persisting new inventory.", var3);
            return false;
        }
    }

    private class PersistInventoryCallable implements Callable<Void> {
        private final Inventory _inventory;

        private PersistInventoryCallable(Inventory inventory) {
            this._inventory = inventory;
        }

        public Void call() throws Exception {
            DaoUser daoUser = (DaoUser)DbManager.getDao(UserEntity.class);
            DaoAccessType daoAccessType = (DaoAccessType)DbManager.getDao(AccessTypeEntity.class);
            DaoRfidTag daoTag = (DaoRfidTag)DbManager.getDao(RfidTagEntity.class);
            DaoInventoryRfidTag daoInventoryTag = (DaoInventoryRfidTag)DbManager.getDao(InventoryRfidTag.class);
            UserEntity gue = null;
            String username = this._inventory.getUsername();
            if(username != null && !username.trim().isEmpty()) {
                gue = (UserEntity)daoUser.getEntityBy("username", username);
            }

            AccessTypeEntity ate = daoAccessType.fromAccessType(this._inventory.getAccessType());
            if(ate == null) {
                throw new SQLException("Invalid access type. Unable to insert Inventory in database");
            } else {
                InventoryEntity ie = new InventoryEntity(this._inventory, gue, ate,this._inventory.getDoorIsMaster());
                if(!DaoInventory.this.insert(ie)) {
                    throw new SQLException("Failed when inserting new Inventory");
                } else {
                    HashMap uidToEntity = new HashMap();
                    ArrayList allUids = new ArrayList(this._inventory.getTagsAll());
                    allUids.addAll(this._inventory.getTagsRemoved());
                    Iterator inventoryRfidTags = allUids.iterator();

                    while(inventoryRfidTags.hasNext()) {
                        String tagToAxis = (String)inventoryRfidTags.next();
                        RfidTagEntity shelveNbr = daoTag.createIfNotExists(tagToAxis);
                        if(shelveNbr == null) {
                            throw new SQLException("Unable to createIfNotExists a tag in database");
                        }

                        uidToEntity.put(tagToAxis, shelveNbr);
                    }

                    ArrayList inventoryRfidTags1 = new ArrayList();
                    Map tagToAxis1 = DeviceHandler.getDevice().getTagToAxis();
                    Iterator i$ = this._inventory.getTagsAdded().iterator();

                    String tagUid;
                    byte shelveNbr1;
                    while(i$.hasNext()) {
                        tagUid = (String)i$.next();
                        shelveNbr1 = tagToAxis1.get(tagUid) == null?0:((Byte)tagToAxis1.get(tagUid)).byteValue();
                        inventoryRfidTags1.add(new InventoryRfidTag(ie, (RfidTagEntity)uidToEntity.get(tagUid), 1, shelveNbr1));
                    }

                    i$ = this._inventory.getTagsPresent().iterator();

                    while(i$.hasNext()) {
                        tagUid = (String)i$.next();
                        shelveNbr1 = tagToAxis1.get(tagUid) == null?0:((Byte)tagToAxis1.get(tagUid)).byteValue();
                        inventoryRfidTags1.add(new InventoryRfidTag(ie, (RfidTagEntity)uidToEntity.get(tagUid), 0, shelveNbr1));
                    }

                    i$ = this._inventory.getTagsRemoved().iterator();

                    while(i$.hasNext()) {
                        tagUid = (String)i$.next();
                        shelveNbr1 = tagToAxis1.get(tagUid) == null?0:((Byte)tagToAxis1.get(tagUid)).byteValue();
                        inventoryRfidTags1.add(new InventoryRfidTag(ie, (RfidTagEntity)uidToEntity.get(tagUid), -1, shelveNbr1));
                    }

                    if(!daoInventoryTag.insert(inventoryRfidTags1)) {
                        throw new SQLException("Unable to insert all tags and movements of the new Inventory");
                    } else {
                        return null;
                    }
                }
            }
        }
    }
}
