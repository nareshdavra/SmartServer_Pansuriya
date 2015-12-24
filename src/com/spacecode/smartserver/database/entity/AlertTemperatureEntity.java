/*    */ package com.spacecode.smartserver.database.entity;
/*    */ 
/*    */ import com.j256.ormlite.field.DatabaseField;
/*    */ import com.j256.ormlite.table.DatabaseTable;
/*    */ import com.spacecode.sdk.network.alert.AlertTemperature;
/*    */ import com.spacecode.smartserver.database.dao.DaoAlertTemperature;
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
/*    */ @DatabaseTable(tableName="sc_alert_temperature", daoClass=DaoAlertTemperature.class)
/*    */ public final class AlertTemperatureEntity
/*    */   extends Entity
/*    */ {
/*    */   public static final String TABLE_NAME = "sc_alert_temperature";
/*    */   public static final String ALERT_ID = "alert_id";
/*    */   public static final String TEMPERATURE_MIN = "temperature_min";
/*    */   public static final String TEMPERATURE_MAX = "temperature_max";
/*    */   @DatabaseField(foreign=true, columnName="alert_id", canBeNull=false, foreignAutoRefresh=true)
/*    */   private AlertEntity _alert;
/*    */   @DatabaseField(columnName="temperature_min", canBeNull=false)
/*    */   private double _temperatureMin;
/*    */   @DatabaseField(columnName="temperature_max", canBeNull=false)
/*    */   private double _temperatureMax;
/*    */   
/*    */   AlertTemperatureEntity() {}
/*    */   
/*    */   public AlertTemperatureEntity(AlertEntity newAlertEntity, AlertTemperature at)
/*    */   {
/* 45 */     this._temperatureMin = at.getTemperatureMin();
/* 46 */     this._temperatureMax = at.getTemperatureMax();
/* 47 */     this._alert = newAlertEntity;
/*    */   }
/*    */   
/*    */ 
/*    */   public AlertEntity getAlert()
/*    */   {
/* 53 */     return this._alert;
/*    */   }
/*    */   
/*    */ 
/*    */   public double getTemperatureMin()
/*    */   {
/* 59 */     return this._temperatureMin;
/*    */   }
/*    */   
/*    */ 
/*    */   public double getTemperatureMax()
/*    */   {
/* 65 */     return this._temperatureMax;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setTemperatureMin(double temperatureMin)
/*    */   {
/* 74 */     this._temperatureMin = temperatureMin;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setTemperatureMax(double temperatureMax)
/*    */   {
/* 83 */     this._temperatureMax = temperatureMax;
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\AlertTemperatureEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */