/*    */ package com.spacecode.smartserver.database.entity;
/*    */ 
/*    */ import com.j256.ormlite.field.DatabaseField;
/*    */ import com.j256.ormlite.table.DatabaseTable;
/*    */ import com.spacecode.smartserver.database.dao.DaoDevice;
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
/*    */ @DatabaseTable(tableName="sc_device", daoClass=DaoDevice.class)
/*    */ public final class DeviceEntity
/*    */   extends Entity
/*    */ {
/*    */   public static final String TABLE_NAME = "sc_device";
/*    */   public static final String SERIAL_NUMBER = "serial_number";
/*    */   @DatabaseField(unique=true, columnName="serial_number", canBeNull=false)
/*    */   private String _serialNumber;
/*    */   
/*    */   DeviceEntity() {}
/*    */   
/*    */   public DeviceEntity(String serialNumber)
/*    */   {
/* 29 */     this._serialNumber = serialNumber;
/*    */   }
/*    */   
/*    */ 
/*    */   public String getSerialNumber()
/*    */   {
/* 35 */     return this._serialNumber;
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\DeviceEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */