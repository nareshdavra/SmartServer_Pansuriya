/*     */ package com.spacecode.smartserver.database.entity;
/*     */ 
/*     */ import com.j256.ormlite.field.DatabaseField;
/*     */ import com.j256.ormlite.table.DatabaseTable;
/*     */ import com.spacecode.sdk.network.alert.SmtpServer;
/*     */ import com.spacecode.smartserver.database.DbManager;
/*     */ import com.spacecode.smartserver.database.dao.DaoSmtpServer;
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
/*     */ @DatabaseTable(tableName="sc_smtp_server", daoClass=DaoSmtpServer.class)
/*     */ public final class SmtpServerEntity
/*     */   extends Entity
/*     */ {
/*     */   public static final String TABLE_NAME = "sc_smtp_server";
/*     */   public static final String DEVICE_ID = "device_id";
/*     */   public static final String ADDRESS = "address";
/*     */   public static final String PORT = "port";
/*     */   public static final String USERNAME = "username";
/*     */   public static final String PASSWORD = "password";
/*     */   public static final String SSL_ENABLED = "ssl_enabled";
/*     */   @DatabaseField(foreign=true, columnName="device_id", canBeNull=false)
/*     */   private DeviceEntity _device;
/*     */   @DatabaseField(columnName="address", canBeNull=false)
/*     */   private String _address;
/*     */   @DatabaseField(columnName="port", canBeNull=false)
/*     */   private int _port;
/*     */   @DatabaseField(columnName="username", canBeNull=false)
/*     */   private String _username;
/*     */   @DatabaseField(columnName="password", canBeNull=false)
/*     */   private String _password;
/*     */   @DatabaseField(columnName="ssl_enabled", canBeNull=false)
/*     */   private boolean _sslEnabled;
/*     */   
/*     */   SmtpServerEntity() {}
/*     */   
/*     */   public SmtpServerEntity(String address, int port, String username, String password, boolean sslEnabled)
/*     */   {
/*  60 */     this._device = DbManager.getDevEntity();
/*  61 */     this._address = address;
/*  62 */     this._port = port;
/*  63 */     this._username = username;
/*  64 */     this._password = password;
/*  65 */     this._sslEnabled = sslEnabled;
/*     */   }
/*     */   
/*     */ 
/*     */   public DeviceEntity getDevice()
/*     */   {
/*  71 */     return this._device;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getAddress()
/*     */   {
/*  77 */     return this._address;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getPort()
/*     */   {
/*  83 */     return this._port;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getUsername()
/*     */   {
/*  89 */     return this._username;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getPassword()
/*     */   {
/*  95 */     return this._password;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isSslEnabled()
/*     */   {
/* 101 */     return this._sslEnabled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateFrom(SmtpServer smtpServer)
/*     */   {
/* 110 */     this._address = smtpServer.getAddress();
/* 111 */     this._port = smtpServer.getPort();
/* 112 */     this._username = smtpServer.getUsername();
/* 113 */     this._password = smtpServer.getPassword();
/* 114 */     this._sslEnabled = smtpServer.isSslEnabled();
/*     */   }
/*     */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\SmtpServerEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */