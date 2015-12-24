/*    */ package com.spacecode.smartserver.database.entity;
/*    */ 
/*    */ import com.j256.ormlite.field.DatabaseField;
/*    */ import com.j256.ormlite.table.DatabaseTable;
/*    */ import com.spacecode.smartserver.database.dao.DaoAlertHistory;
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
/*    */ @DatabaseTable(tableName="sc_alert_history", daoClass=DaoAlertHistory.class)
/*    */ public final class AlertHistoryEntity
/*    */   extends Entity
/*    */ {
/*    */   public static final String TABLE_NAME = "sc_alert_history";
/*    */   public static final String ALERT_ID = "alert_id";
/*    */   public static final String EXTRA_DATA = "extra_data";
/*    */   public static final String CREATED_AT = "created_at";
/*    */   @DatabaseField(foreign=true, columnName="alert_id", canBeNull=false, foreignAutoRefresh=true)
/*    */   private AlertEntity _alert;
/*    */   @DatabaseField(columnName="extra_data")
/*    */   private String _extraData;
/*    */   @DatabaseField(columnName="created_at", canBeNull=false, index=true)
/*    */   private Date _createdAt;
/*    */   
/*    */   AlertHistoryEntity() {}
/*    */   
/*    */   public AlertHistoryEntity(AlertEntity alert, String extraData)
/*    */   {
/* 42 */     this._alert = alert;
/* 43 */     this._extraData = extraData;
/* 44 */     this._createdAt = new Date();
/*    */   }
/*    */   
/*    */ 
/*    */   public AlertEntity getAlert()
/*    */   {
/* 50 */     return this._alert;
/*    */   }
/*    */   
/*    */ 
/*    */   public Date getCreatedAt()
/*    */   {
/* 56 */     return new Date(this._createdAt.getTime());
/*    */   }
/*    */   
/*    */ 
/*    */   public String getExtraData()
/*    */   {
/* 62 */     return this._extraData != null ? this._extraData : "";
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\AlertHistoryEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */