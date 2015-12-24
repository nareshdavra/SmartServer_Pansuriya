/*    */ package com.spacecode.smartserver.database.entity;
/*    */ 
/*    */ import com.j256.ormlite.field.DatabaseField;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Entity
/*    */ {
/*    */   public static final String ID = "id";
/*    */   @DatabaseField(columnName="id", generatedId=true)
/*    */   protected int _id;
/*    */   
/*    */   public int getId()
/*    */   {
/* 18 */     return this._id;
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\Entity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */