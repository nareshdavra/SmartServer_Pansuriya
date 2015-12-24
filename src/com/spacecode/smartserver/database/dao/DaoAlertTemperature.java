/*    */ package com.spacecode.smartserver.database.dao;
/*    */ 
/*    */ import com.j256.ormlite.support.ConnectionSource;
/*    */ import com.spacecode.smartserver.database.entity.AlertTemperatureEntity;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DaoAlertTemperature
/*    */   extends DaoEntity<AlertTemperatureEntity, Integer>
/*    */ {
/*    */   public DaoAlertTemperature(ConnectionSource connectionSource)
/*    */     throws SQLException
/*    */   {
/* 15 */     super(connectionSource, AlertTemperatureEntity.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoAlertTemperature.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */