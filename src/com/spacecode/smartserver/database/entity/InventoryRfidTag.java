/*    */ package com.spacecode.smartserver.database.entity;
/*    */ 
/*    */ import com.j256.ormlite.field.DatabaseField;
/*    */ import com.j256.ormlite.table.DatabaseTable;
/*    */ import com.spacecode.smartserver.database.dao.DaoInventoryRfidTag;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @DatabaseTable(tableName="sc_inventory_rfid_tag", daoClass=DaoInventoryRfidTag.class)
/*    */ public final class InventoryRfidTag
/*    */   extends Entity
/*    */ {
/*    */   public static final String TABLE_NAME = "sc_inventory_rfid_tag";
/*    */   public static final String INVENTORY_ID = "inventory_id";
/*    */   public static final String RFID_TAG_ID = "rfid_tag_id";
/*    */   public static final String MOVEMENT = "movement";
/*    */   public static final String SHELVE_NUMBER = "shelve_number";
/*    */   @DatabaseField(foreign=true, columnName="inventory_id", canBeNull=false)
/*    */   private InventoryEntity _inventory;
/*    */   @DatabaseField(foreign=true, columnName="rfid_tag_id", canBeNull=false, foreignAutoRefresh=true)
/*    */   private RfidTagEntity _rfidTag;
/*    */   @DatabaseField(columnName="movement", canBeNull=false)
/*    */   private int _movement;
/*    */   @DatabaseField(columnName="shelve_number", canBeNull=false)
/*    */   private int _shelveNumber;
/*    */   
/*    */   InventoryRfidTag() {}
/*    */   
/*    */   public InventoryRfidTag(InventoryEntity inventory, RfidTagEntity rfidTag, int movementType)
/*    */   {
/* 49 */     this._inventory = inventory;
/* 50 */     this._rfidTag = rfidTag;
/* 51 */     this._movement = movementType;
/* 52 */     this._shelveNumber = 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public InventoryRfidTag(InventoryEntity inventory, RfidTagEntity rfidTag, int movementType, int shelveNumber)
/*    */   {
/* 65 */     this(inventory, rfidTag, movementType);
/* 66 */     this._shelveNumber = shelveNumber;
/*    */   }
/*    */   
/*    */ 
/*    */   public InventoryEntity getInventory()
/*    */   {
/* 72 */     return this._inventory;
/*    */   }
/*    */   
/*    */ 
/*    */   public RfidTagEntity getRfidTag()
/*    */   {
/* 78 */     return this._rfidTag;
/*    */   }
/*    */   
/*    */ 
/*    */   public int getMovement()
/*    */   {
/* 84 */     return this._movement;
/*    */   }
/*    */   
/*    */ 
/*    */   public int getShelveNumber()
/*    */   {
/* 90 */     return this._shelveNumber;
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\InventoryRfidTag.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */