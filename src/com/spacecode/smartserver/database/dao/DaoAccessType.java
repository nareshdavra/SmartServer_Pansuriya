/*    */ package com.spacecode.smartserver.database.dao;
/*    */ 
/*    */ import com.j256.ormlite.stmt.QueryBuilder;
/*    */ import com.j256.ormlite.stmt.Where;
/*    */ import com.j256.ormlite.support.ConnectionSource;
/*    */ import com.spacecode.sdk.user.data.AccessType;
/*    */ import com.spacecode.smartserver.database.entity.AccessTypeEntity;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import java.sql.SQLException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ 
/*    */ public class DaoAccessType
/*    */   extends DaoEntity<AccessTypeEntity, Integer>
/*    */ {
/* 18 */   private static final Map<String, AccessTypeEntity> TYPE_TO_ENTITY = new HashMap();
/*    */   
/*    */   public DaoAccessType(ConnectionSource connectionSource) throws SQLException
/*    */   {
/* 22 */     super(connectionSource, AccessTypeEntity.class);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static AccessType asAccessType(AccessTypeEntity ate)
/*    */   {
/* 32 */     if (ate == null)
/*    */     {
/* 34 */       return null;
/*    */     }
/*    */     
/*    */     try
/*    */     {
/* 39 */       return AccessType.valueOf(ate.getType());
/*    */     }
/*    */     catch (IllegalArgumentException iae) {
/* 42 */       SmartLogger.getLogger().log(Level.WARNING, "Unknown access type", iae); }
/* 43 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public AccessTypeEntity fromAccessType(AccessType accessType)
/*    */   {
/* 53 */     if (accessType == null)
/*    */     {
/* 55 */       return null;
/*    */     }
/*    */     
/* 58 */     if (!TYPE_TO_ENTITY.containsKey(accessType.name()))
/*    */     {
/*    */       try
/*    */       {
/* 62 */         AccessTypeEntity ate = (AccessTypeEntity)queryForFirst(queryBuilder().where().eq("type", accessType.name()).prepare());
/*    */         
/*    */ 
/*    */ 
/* 66 */         TYPE_TO_ENTITY.put(accessType.name(), ate);
/*    */       }
/*    */       catch (SQLException sqle) {
/* 69 */         SmartLogger.getLogger().log(Level.SEVERE, "Unable to get AccessType from database.", sqle);
/* 70 */         return null;
/*    */       }
/*    */     }
/*    */     
/* 74 */     return (AccessTypeEntity)TYPE_TO_ENTITY.get(accessType.name());
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoAccessType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */