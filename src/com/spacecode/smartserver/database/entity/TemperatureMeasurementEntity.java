/*    */ package com.spacecode.smartserver.database.entity;
/*    */ 
/*    */ import com.j256.ormlite.field.DatabaseField;
/*    */ import com.j256.ormlite.table.DatabaseTable;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoTemperatureMeasurement;
/*    */ import java.util.Date;
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
/*    */ @DatabaseTable(tableName="sc_temperature", daoClass=DaoTemperatureMeasurement.class)
/*    */ public final class TemperatureMeasurementEntity
/*    */   extends Entity
/*    */ {
/*    */   public static final String TABLE_NAME = "sc_temperature";
/*    */   public static final String DEVICE_ID = "device_id";
/*    */   public static final String VALUE = "value";
/*    */   public static final String CREATED_AT = "created_at";
/*    */   @DatabaseField(foreign=true, columnName="device_id", canBeNull=false)
/*    */   private DeviceEntity _device;
/*    */   @DatabaseField(columnName="value", canBeNull=false)
/*    */   private double _value;
/*    */   @DatabaseField(columnName="created_at", index=true)
/*    */   private Date _createdAt;
/*    */   
/*    */   TemperatureMeasurementEntity() {}
/*    */   
/*    */   public TemperatureMeasurementEntity(double value)
/*    */   {
/* 44 */     this._device = DbManager.getDevEntity();
/* 45 */     this._value = value;
/* 46 */     this._createdAt = new Date();
/*    */   }
/*    */   
/*    */ 
/*    */   public DeviceEntity getDevice()
/*    */   {
/* 52 */     return this._device;
/*    */   }
/*    */   
/*    */ 
/*    */   public double getValue()
/*    */   {
/* 58 */     return this._value;
/*    */   }
/*    */   
/*    */ 
/*    */   public Date getCreatedAt()
/*    */   {
/* 64 */     return new Date(this._createdAt.getTime());
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\TemperatureMeasurementEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */