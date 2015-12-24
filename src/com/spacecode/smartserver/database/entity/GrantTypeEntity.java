/*    */ package com.spacecode.smartserver.database.entity;
/*    */ 
/*    */ import com.j256.ormlite.field.DatabaseField;
/*    */ import com.j256.ormlite.table.DatabaseTable;
/*    */ import com.spacecode.smartserver.database.dao.DaoGrantType;
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
/*    */ @DatabaseTable(tableName="sc_grant_type", daoClass=DaoGrantType.class)
/*    */ public final class GrantTypeEntity
/*    */   extends Entity
/*    */ {
/*    */   public static final String TABLE_NAME = "sc_grant_type";
/*    */   public static final String TYPE = "type";
/*    */   @DatabaseField(columnName="type", canBeNull=false)
/*    */   private String _type;
/*    */   
/*    */   GrantTypeEntity() {}
/*    */   
/*    */   public GrantTypeEntity(String userGrant)
/*    */   {
/* 33 */     this._type = userGrant;
/*    */   }
/*    */   
/*    */ 
/*    */   public String getType()
/*    */   {
/* 39 */     return this._type;
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\GrantTypeEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */