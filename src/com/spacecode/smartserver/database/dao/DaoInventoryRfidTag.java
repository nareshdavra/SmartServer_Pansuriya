/*    */ package com.spacecode.smartserver.database.dao;
/*    */ 
/*    */ import com.j256.ormlite.support.ConnectionSource;
/*    */ import com.spacecode.smartserver.database.entity.InventoryRfidTag;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DaoInventoryRfidTag
/*    */   extends DaoEntity<InventoryRfidTag, Integer>
/*    */ {
/*    */   public DaoInventoryRfidTag(ConnectionSource connectionSource)
/*    */     throws SQLException
/*    */   {
/* 15 */     super(connectionSource, InventoryRfidTag.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoInventoryRfidTag.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */