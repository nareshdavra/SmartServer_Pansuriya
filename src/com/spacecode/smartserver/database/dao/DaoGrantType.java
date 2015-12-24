/*    */ package com.spacecode.smartserver.database.dao;
/*    */ 
/*    */ import com.j256.ormlite.stmt.QueryBuilder;
/*    */ import com.j256.ormlite.stmt.Where;
/*    */ import com.j256.ormlite.support.ConnectionSource;
/*    */ import com.spacecode.sdk.user.data.GrantType;
/*    */ import com.spacecode.smartserver.database.entity.GrantTypeEntity;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import java.sql.SQLException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ 
/*    */ public class DaoGrantType
/*    */   extends DaoEntity<GrantTypeEntity, Integer>
/*    */ {
/* 18 */   private static final Map<String, GrantTypeEntity> TYPE_TO_ENTITY = new HashMap();
/*    */   
/*    */   public DaoGrantType(ConnectionSource connectionSource) throws SQLException
/*    */   {
/* 22 */     super(connectionSource, GrantTypeEntity.class);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static GrantType asGrantType(GrantTypeEntity gte)
/*    */   {
/* 32 */     if (gte == null)
/*    */     {
/* 34 */       return null;
/*    */     }
/*    */     
/*    */     try
/*    */     {
/* 39 */       return GrantType.valueOf(gte.getType());
/*    */     }
/*    */     catch (IllegalArgumentException iae) {
/* 42 */       SmartLogger.getLogger().log(Level.WARNING, "Unknown grant type", iae); }
/* 43 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public GrantTypeEntity fromGrantType(GrantType grantType)
/*    */   {
/* 53 */     if (grantType == null)
/*    */     {
/* 55 */       return null;
/*    */     }
/*    */     
/* 58 */     if (!TYPE_TO_ENTITY.containsKey(grantType.name()))
/*    */     {
/*    */       try
/*    */       {
/* 62 */         GrantTypeEntity gte = (GrantTypeEntity)queryForFirst(queryBuilder().where().eq("type", grantType.name()).prepare());
/*    */         
/*    */ 
/*    */ 
/* 66 */         TYPE_TO_ENTITY.put(grantType.name(), gte);
/*    */       }
/*    */       catch (SQLException sqle) {
/* 69 */         SmartLogger.getLogger().log(Level.SEVERE, "Unable to get GrantType.", sqle);
/* 70 */         return null;
/*    */       }
/*    */     }
/*    */     
/* 74 */     return (GrantTypeEntity)TYPE_TO_ENTITY.get(grantType.name());
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoGrantType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */