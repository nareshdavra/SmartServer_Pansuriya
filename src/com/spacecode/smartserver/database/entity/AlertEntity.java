/*     */ package com.spacecode.smartserver.database.entity;
/*     */ 
/*     */ import com.j256.ormlite.field.DatabaseField;
/*     */ import com.j256.ormlite.table.DatabaseTable;
/*     */ import com.spacecode.sdk.network.alert.Alert;
/*     */ import com.spacecode.sdk.network.alert.AlertTemperature;
/*     */ import com.spacecode.smartserver.database.DbManager;
/*     */ import com.spacecode.smartserver.database.dao.DaoAlert;
/*     */ import com.spacecode.smartserver.database.dao.DaoAlertType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @DatabaseTable(tableName="sc_alert", daoClass=DaoAlert.class)
/*     */ public final class AlertEntity
/*     */   extends Entity
/*     */ {
/*     */   public static final String TABLE_NAME = "sc_alert";
/*     */   public static final String ALERT_TYPE_ID = "alert_type_id";
/*     */   public static final String DEVICE_ID = "device_id";
/*     */   public static final String ENABLED = "enabled";
/*     */   public static final String TO_LIST = "to_list";
/*     */   public static final String CC_LIST = "cc_list";
/*     */   public static final String BCC_LIST = "bcc_list";
/*     */   public static final String EMAIL_SUBJECT = "email_subject";
/*     */   public static final String EMAIL_CONTENT = "email_content";
/*     */   @DatabaseField(foreign=true, columnName="alert_type_id", canBeNull=false, foreignAutoRefresh=true)
/*     */   private AlertTypeEntity _alertType;
/*     */   @DatabaseField(foreign=true, columnName="device_id", canBeNull=false)
/*     */   private DeviceEntity _device;
/*     */   @DatabaseField(columnName="to_list", canBeNull=false)
/*     */   private String _toList;
/*     */   @DatabaseField(columnName="cc_list", canBeNull=false)
/*     */   private String _ccList;
/*     */   @DatabaseField(columnName="bcc_list", canBeNull=false)
/*     */   private String _bccList;
/*     */   @DatabaseField(columnName="email_subject", canBeNull=false)
/*     */   private String _emailSubject;
/*     */   @DatabaseField(columnName="email_content", canBeNull=false)
/*     */   private String _emailContent;
/*     */   @DatabaseField(columnName="enabled", canBeNull=false)
/*     */   private boolean _enabled;
/*     */   
/*     */   AlertEntity() {}
/*     */   
/*     */   public AlertEntity(AlertTypeEntity ate, String to, String emailSubject, String emailContent, boolean enabled)
/*     */   {
/*  71 */     this(ate, to, "", "", emailSubject, emailContent, enabled);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AlertEntity(AlertTypeEntity ate, String to, String cc, String bcc, String emailSubject, String emailContent, boolean enabled)
/*     */   {
/*  88 */     this._alertType = ate;
/*  89 */     this._device = DbManager.getDevEntity();
/*  90 */     this._toList = (to == null ? "" : to);
/*  91 */     this._ccList = (cc == null ? "" : cc);
/*  92 */     this._bccList = (bcc == null ? "" : bcc);
/*  93 */     this._emailSubject = (emailSubject == null ? "" : emailSubject);
/*  94 */     this._emailContent = (emailContent == null ? "" : emailContent);
/*  95 */     this._enabled = enabled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AlertEntity(AlertTypeEntity ate, Alert alert)
/*     */   {
/* 107 */     this(ate, alert.getToList(), alert.getCcList(), alert.getBccList(), alert.getEmailSubject(), alert.getEmailContent(), alert.isEnabled());
/*     */     
/*     */ 
/* 110 */     this._id = alert.getId();
/*     */   }
/*     */   
/*     */ 
/*     */   public AlertTypeEntity getAlertType()
/*     */   {
/* 116 */     return this._alertType;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getEmailSubject()
/*     */   {
/* 122 */     return this._emailSubject;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getEmailContent()
/*     */   {
/* 128 */     return this._emailContent;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getToList()
/*     */   {
/* 134 */     return this._toList;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getCcList()
/*     */   {
/* 140 */     return this._ccList;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getBccList()
/*     */   {
/* 146 */     return this._bccList;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isEnabled()
/*     */   {
/* 152 */     return this._enabled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Alert toAlert(AlertEntity ae)
/*     */   {
/* 162 */     return new Alert(ae.getId(), DaoAlertType.asAlertType(ae.getAlertType()), ae._toList, ae._ccList, ae._bccList, ae._emailSubject, ae._emailContent, ae._enabled);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Alert toAlert(AlertTemperatureEntity ate)
/*     */   {
/* 177 */     AlertEntity ae = ate.getAlert();
/*     */     
/* 179 */     return new AlertTemperature(ae.getId(), ae._toList, ae._ccList, ae._bccList, ae._emailSubject, ae._emailContent, ae._enabled, ate.getTemperatureMin(), ate.getTemperatureMax());
/*     */   }
/*     */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\AlertEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */