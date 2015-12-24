/*    */ package com.spacecode.smartserver.database.dao;
/*    */ 
/*    */ import com.j256.ormlite.support.ConnectionSource;
/*    */ import com.spacecode.smartserver.database.entity.DeviceEntity;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DaoDevice
/*    */   extends DaoEntity<DeviceEntity, Integer>
/*    */ {
/*    */   public DaoDevice(ConnectionSource connectionSource)
/*    */     throws SQLException
/*    */   {
/* 15 */     super(connectionSource, DeviceEntity.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoDevice.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */