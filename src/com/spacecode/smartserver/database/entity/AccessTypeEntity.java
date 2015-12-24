/*    */ package com.spacecode.smartserver.database.entity;
/*    */ 
/*    */ import com.j256.ormlite.field.DatabaseField;
/*    */ import com.j256.ormlite.table.DatabaseTable;
/*    */ import com.spacecode.smartserver.database.dao.DaoAccessType;
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
/*    */ @DatabaseTable(tableName="sc_access_type", daoClass=DaoAccessType.class)
/*    */ public final class AccessTypeEntity
/*    */   extends Entity
/*    */ {
/*    */   public static final String TABLE_NAME = "sc_access_type";
/*    */   public static final String TYPE = "type";
/*    */   @DatabaseField(columnName="type", canBeNull=false)
/*    */   private String _type;
/*    */   
/*    */   AccessTypeEntity() {}
/*    */   
/*    */   public AccessTypeEntity(String accessType)
/*    */   {
/* 33 */     this._type = accessType;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getType()
/*    */   {
/* 42 */     return this._type;
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\AccessTypeEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */