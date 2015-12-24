/*    */ package com.spacecode.smartserver.database.dao;
/*    */ 
/*    */ import com.j256.ormlite.stmt.QueryBuilder;
/*    */ import com.j256.ormlite.stmt.Where;
/*    */ import com.j256.ormlite.support.ConnectionSource;
/*    */ import com.spacecode.sdk.network.alert.AlertType;
/*    */ import com.spacecode.smartserver.database.entity.AlertTypeEntity;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import java.sql.SQLException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ 
/*    */ public class DaoAlertType
/*    */   extends DaoEntity<AlertTypeEntity, Integer>
/*    */ {
/* 18 */   private static final Map<String, AlertTypeEntity> TYPE_TO_ENTITY = new HashMap();
/*    */   
/*    */   public DaoAlertType(ConnectionSource connectionSource) throws SQLException
/*    */   {
/* 22 */     super(connectionSource, AlertTypeEntity.class);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public AlertTypeEntity fromAlertType(AlertType alertType)
/*    */   {
/* 31 */     if (alertType == null)
/*    */     {
/* 33 */       return null;
/*    */     }
/*    */     
/* 36 */     if (!TYPE_TO_ENTITY.containsKey(alertType.name()))
/*    */     {
/*    */       try
/*    */       {
/* 40 */         AlertTypeEntity ate = (AlertTypeEntity)queryForFirst(queryBuilder().where().eq("type", alertType.name()).prepare());
/*    */         
/*    */ 
/*    */ 
/* 44 */         TYPE_TO_ENTITY.put(alertType.name(), ate);
/*    */       }
/*    */       catch (SQLException sqle) {
/* 47 */         SmartLogger.getLogger().log(Level.SEVERE, "Unable to get AlertType " + alertType.name() + " from database.", sqle);
/* 48 */         return null;
/*    */       }
/*    */     }
/*    */     
/* 52 */     return (AlertTypeEntity)TYPE_TO_ENTITY.get(alertType.name());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static AlertType asAlertType(AlertTypeEntity ate)
/*    */   {
/* 64 */     if (ate == null)
/*    */     {
/* 66 */       return null;
/*    */     }
/*    */     
/*    */     try
/*    */     {
/* 71 */       return AlertType.valueOf(ate.getType());
/*    */     }
/*    */     catch (IllegalArgumentException iae) {
/* 74 */       SmartLogger.getLogger().log(Level.WARNING, "Unknown alert type", iae); }
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoAlertType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */