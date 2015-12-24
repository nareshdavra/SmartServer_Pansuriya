/*    */ package com.spacecode.smartserver.database.entity;
/*    */ 
/*    */ import com.j256.ormlite.field.DataType;
/*    */ import com.j256.ormlite.field.DatabaseField;
/*    */ import com.j256.ormlite.table.DatabaseTable;
/*    */ import com.spacecode.smartserver.database.dao.DaoFingerprint;
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
/*    */ @DatabaseTable(tableName="sc_fingerprint", daoClass=DaoFingerprint.class)
/*    */ public final class FingerprintEntity
/*    */   extends Entity
/*    */ {
/*    */   public static final String TABLE_NAME = "sc_fingerprint";
/*    */   public static final String USER_ID = "user_id";
/*    */   public static final String FINGER_INDEX = "finger_index";
/*    */   public static final String TEMPLATE = "template";
/*    */   @DatabaseField(foreign=true, columnName="user_id", canBeNull=false)
/*    */   private UserEntity _user;
/*    */   @DatabaseField(columnName="finger_index", canBeNull=false)
/*    */   private int _fingerIndex;
/*    */   @DatabaseField(columnName="template", dataType=DataType.LONG_STRING, canBeNull=false)
/*    */   private String _template;
/*    */   
/*    */   FingerprintEntity() {}
/*    */   
/*    */   public FingerprintEntity(UserEntity user, int index, String template)
/*    */   {
/* 44 */     this._user = user;
/* 45 */     this._fingerIndex = index;
/* 46 */     this._template = template;
/*    */   }
/*    */   
/*    */ 
/*    */   public UserEntity getUser()
/*    */   {
/* 52 */     return this._user;
/*    */   }
/*    */   
/*    */ 
/*    */   public int getFingerIndex()
/*    */   {
/* 58 */     return this._fingerIndex;
/*    */   }
/*    */   
/*    */ 
/*    */   public String getTemplate()
/*    */   {
/* 64 */     return this._template;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setTemplate(String template)
/*    */   {
/* 74 */     this._template = template;
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\FingerprintEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */