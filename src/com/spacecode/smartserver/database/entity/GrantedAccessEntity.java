/*    */ package com.spacecode.smartserver.database.entity;
/*    */ 
/*    */ import com.j256.ormlite.field.DatabaseField;
/*    */ import com.j256.ormlite.table.DatabaseTable;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoGrantedAccess;
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
/*    */ @DatabaseTable(tableName="sc_granted_access", daoClass=DaoGrantedAccess.class)
/*    */ public final class GrantedAccessEntity
/*    */   extends Entity
/*    */ {
/*    */   public static final String TABLE_NAME = "sc_granted_access";
/*    */   public static final String DEVICE_ID = "device_id";
/*    */   public static final String USER_ID = "user_id";
/*    */   public static final String GRANT_TYPE_ID = "grant_type_id";
/*    */   @DatabaseField(foreign=true, columnName="device_id", canBeNull=false)
/*    */   private DeviceEntity _device;
/*    */   @DatabaseField(foreign=true, columnName="user_id", canBeNull=false, foreignAutoRefresh=true)
/*    */   private UserEntity _user;
/*    */   @DatabaseField(foreign=true, columnName="grant_type_id", canBeNull=false, foreignAutoRefresh=true)
/*    */   private GrantTypeEntity _grantType;
/*    */   
/*    */   GrantedAccessEntity() {}
/*    */   
/*    */   public GrantedAccessEntity(UserEntity user, GrantTypeEntity grantType)
/*    */   {
/* 44 */     this._user = user;
/* 45 */     this._device = DbManager.getDevEntity();
/* 46 */     this._grantType = grantType;
/*    */   }
/*    */   
/*    */ 
/*    */   public UserEntity getUser()
/*    */   {
/* 52 */     return this._user;
/*    */   }
/*    */   
/*    */ 
/*    */   public DeviceEntity getDevice()
/*    */   {
/* 58 */     return this._device;
/*    */   }
/*    */   
/*    */ 
/*    */   public GrantTypeEntity getGrantType()
/*    */   {
/* 64 */     return this._grantType;
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\GrantedAccessEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */