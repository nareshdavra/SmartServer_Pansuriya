/*    */ package com.spacecode.smartserver.database.dao;
/*    */ 
/*    */ import com.j256.ormlite.stmt.QueryBuilder;
/*    */ import com.j256.ormlite.stmt.Where;
/*    */ import com.j256.ormlite.support.ConnectionSource;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.entity.DeviceEntity;
/*    */ import com.spacecode.smartserver.database.entity.TemperatureMeasurementEntity;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ public class DaoTemperatureMeasurement
/*    */   extends DaoEntity<TemperatureMeasurementEntity, Integer>
/*    */ {
/*    */   public DaoTemperatureMeasurement(ConnectionSource connectionSource)
/*    */     throws SQLException
/*    */   {
/* 22 */     super(connectionSource, TemperatureMeasurementEntity.class);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public List<TemperatureMeasurementEntity> getTemperatureMeasures(Date from, Date to)
/*    */   {
/*    */     try
/*    */     {
/* 37 */       QueryBuilder<TemperatureMeasurementEntity, Integer> qb = queryBuilder();
/*    */       
/* 39 */       qb.orderBy("created_at", true);
/*    */       
/* 41 */       return query(qb.where().eq("device_id", Integer.valueOf(DbManager.getDevEntity().getId())).and().between("created_at", from, to).prepare());
/*    */ 
/*    */ 
/*    */ 
/*    */     }
/*    */     catch (SQLException sqle)
/*    */     {
/*    */ 
/*    */ 
/* 50 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting temperature measures.", sqle); }
/* 51 */     return new ArrayList();
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoTemperatureMeasurement.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */