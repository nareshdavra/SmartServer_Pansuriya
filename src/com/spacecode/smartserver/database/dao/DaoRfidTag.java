/*    */ package com.spacecode.smartserver.database.dao;
/*    */ 
/*    */ import com.j256.ormlite.stmt.QueryBuilder;
/*    */ import com.j256.ormlite.stmt.Where;
/*    */ import com.j256.ormlite.support.ConnectionSource;
/*    */ import com.spacecode.smartserver.database.entity.RfidTagEntity;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import java.sql.SQLException;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ public class DaoRfidTag
/*    */   extends DaoEntity<RfidTagEntity, Integer>
/*    */ {
/*    */   public DaoRfidTag(ConnectionSource connectionSource)
/*    */     throws SQLException
/*    */   {
/* 17 */     super(connectionSource, RfidTagEntity.class);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public RfidTagEntity createIfNotExists(String uid)
/*    */   {
/* 29 */     if ((uid == null) || ("".equals(uid.trim())))
/*    */     {
/* 31 */       return null;
/*    */     }
/*    */     
/* 34 */     RfidTagEntity rte = getByUid(uid);
/*    */     
/* 36 */     if (rte != null)
/*    */     {
/* 38 */       return rte;
/*    */     }
/*    */     
/*    */     try
/*    */     {
/* 43 */       RfidTagEntity newRte = new RfidTagEntity(uid);
/* 44 */       create(newRte);
/*    */       
/* 46 */       return newRte;
/*    */     }
/*    */     catch (SQLException sqle) {
/* 49 */       SmartLogger.getLogger().log(Level.SEVERE, "Unable to insert RfidTag in DB.", sqle); }
/* 50 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public RfidTagEntity getByUid(String uid)
/*    */   {
/* 61 */     if ((uid == null) || (uid.trim().isEmpty()))
/*    */     {
/* 63 */       return null;
/*    */     }
/*    */     
/*    */     try
/*    */     {
/* 68 */       return (RfidTagEntity)queryForFirst(queryBuilder().where().eq("uid", uid).prepare());
/*    */ 
/*    */ 
/*    */     }
/*    */     catch (SQLException sqle)
/*    */     {
/*    */ 
/*    */ 
/* 76 */       SmartLogger.getLogger().log(Level.SEVERE, "Unable to get Rfid Tag entity from DB.", sqle); }
/* 77 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoRfidTag.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */