/*    */ package com.spacecode.smartserver.database.entity;
/*    */ 
/*    */ import com.j256.ormlite.field.DatabaseField;
/*    */ import com.j256.ormlite.table.DatabaseTable;
/*    */ import com.spacecode.smartserver.database.dao.DaoRfidTag;
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
/*    */ @DatabaseTable(tableName="sc_rfid_tag", daoClass=DaoRfidTag.class)
/*    */ public final class RfidTagEntity
/*    */   extends Entity
/*    */ {
/*    */   public static final String TABLE_NAME = "sc_rfid_tag";
/*    */   public static final String UID = "uid";
/*    */   @DatabaseField(columnName="uid", canBeNull=false, unique=true)
/*    */   private String _uid;
/*    */   
/*    */   RfidTagEntity() {}
/*    */   
/*    */   public RfidTagEntity(String uid)
/*    */   {
/* 33 */     this._uid = uid;
/*    */   }
/*    */   
/*    */ 
/*    */   public String getUid()
/*    */   {
/* 39 */     return this._uid;
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\RfidTagEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */