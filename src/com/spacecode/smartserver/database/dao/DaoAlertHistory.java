/*    */ package com.spacecode.smartserver.database.dao;
/*    */ 
/*    */ import com.j256.ormlite.stmt.QueryBuilder;
/*    */ import com.j256.ormlite.stmt.Where;
/*    */ import com.j256.ormlite.support.ConnectionSource;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.entity.AlertEntity;
/*    */ import com.spacecode.smartserver.database.entity.AlertHistoryEntity;
/*    */ import com.spacecode.smartserver.database.entity.DeviceEntity;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ public class DaoAlertHistory
/*    */   extends DaoEntity<AlertHistoryEntity, Integer>
/*    */ {
/*    */   public DaoAlertHistory(ConnectionSource connectionSource)
/*    */     throws SQLException
/*    */   {
/* 23 */     super(connectionSource, AlertHistoryEntity.class);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public AlertHistoryEntity getLastAlertHistory()
/*    */   {
/*    */     try
/*    */     {
/* 36 */       QueryBuilder<AlertEntity, Integer> alertQb = DbManager.getDao(AlertEntity.class).queryBuilder();
/* 37 */       alertQb.where().eq("device_id", Integer.valueOf(DbManager.getDevEntity().getId()));
/*    */       
/* 39 */       return (AlertHistoryEntity)queryForFirst(queryBuilder().join(alertQb).orderBy("created_at", false).limit(Long.valueOf(1L)).prepare());
/*    */ 
/*    */ 
/*    */ 
/*    */     }
/*    */     catch (SQLException sqle)
/*    */     {
/*    */ 
/*    */ 
/* 48 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting last AlertHistory.", sqle); }
/* 49 */     return null;
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
/*    */ 
/*    */   public List<AlertHistoryEntity> getAlertsHistory(Date startDate, Date endDate)
/*    */   {
/*    */     try
/*    */     {
/* 65 */       QueryBuilder<AlertEntity, Integer> alertQb = DbManager.getDao(AlertEntity.class).queryBuilder();
/* 66 */       alertQb.where().eq("device_id", Integer.valueOf(DbManager.getDevEntity().getId()));
/*    */       
/* 68 */       return query(queryBuilder().orderBy("created_at", true).join(alertQb).where().between("created_at", startDate, endDate).prepare());
/*    */ 
/*    */ 
/*    */ 
/*    */     }
/*    */     catch (SQLException sqle)
/*    */     {
/*    */ 
/*    */ 
/* 77 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting Alerts history.", sqle); }
/* 78 */     return new ArrayList();
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoAlertHistory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */