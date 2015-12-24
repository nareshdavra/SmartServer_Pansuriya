/*    */ package com.spacecode.smartserver.database.entity;
/*    */ 
/*    */ import com.j256.ormlite.field.DatabaseField;
/*    */ import com.j256.ormlite.table.DatabaseTable;
/*    */ import com.spacecode.smartserver.database.dao.DaoAlertType;
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
/*    */ @DatabaseTable(tableName="sc_alert_type", daoClass=DaoAlertType.class)
/*    */ public final class AlertTypeEntity
/*    */   extends Entity
/*    */ {
/*    */   public static final String TABLE_NAME = "sc_alert_type";
/*    */   public static final String TYPE = "type";
/*    */   @DatabaseField(columnName="type", canBeNull=false)
/*    */   private String _type;
/*    */   
/*    */   AlertTypeEntity() {}
/*    */   
/*    */   public AlertTypeEntity(String alertType)
/*    */   {
/* 33 */     this._type = alertType;
/*    */   }
/*    */   
/*    */ 
/*    */   public String getType()
/*    */   {
/* 39 */     return this._type;
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\AlertTypeEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */