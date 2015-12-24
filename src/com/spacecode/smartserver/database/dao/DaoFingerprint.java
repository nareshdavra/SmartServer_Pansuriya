/*     */ package com.spacecode.smartserver.database.dao;
/*     */ 
/*     */ import com.j256.ormlite.stmt.QueryBuilder;
/*     */ import com.j256.ormlite.stmt.Where;
/*     */ import com.j256.ormlite.support.ConnectionSource;
/*     */ import com.spacecode.smartserver.database.DbManager;
/*     */ import com.spacecode.smartserver.database.entity.FingerprintEntity;
/*     */ import com.spacecode.smartserver.database.entity.UserEntity;
/*     */ import com.spacecode.smartserver.helper.SmartLogger;
/*     */ import java.sql.SQLException;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ public class DaoFingerprint
/*     */   extends DaoEntity<FingerprintEntity, Integer>
/*     */ {
/*     */   public DaoFingerprint(ConnectionSource connectionSource)
/*     */     throws SQLException
/*     */   {
/*  19 */     super(connectionSource, FingerprintEntity.class);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FingerprintEntity getFingerprint(UserEntity gue, int index)
/*     */   {
/*  32 */     if (gue == null)
/*     */     {
/*  34 */       return null;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/*  39 */       return (FingerprintEntity)queryForFirst(queryBuilder().where().eq("user_id", Integer.valueOf(gue.getId())).and().eq("finger_index", Integer.valueOf(index)).prepare());
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*     */ 
/*     */ 
/*  47 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting Fingerprint.", sqle); }
/*  48 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean updateEntity(FingerprintEntity fpEntity)
/*     */   {
/*  62 */     UserEntity ue = fpEntity.getUser();
/*  63 */     FingerprintEntity fpEnt = getFingerprint(ue, fpEntity.getFingerIndex());
/*     */     
/*  65 */     DbManager.forceUpdate(ue);
/*     */     
/*  67 */     if (fpEnt == null)
/*     */     {
/*  69 */       return insert(fpEntity);
/*     */     }
/*     */     
/*  72 */     fpEnt.setTemplate(fpEntity.getTemplate());
/*     */     
/*  74 */     return super.updateEntity(fpEnt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean delete(String username, int index)
/*     */   {
/*  87 */     DaoUser daoUser = (DaoUser)DbManager.getDao(UserEntity.class);
/*  88 */     UserEntity gue = (UserEntity)daoUser.getEntityBy("username", username);
/*     */     
/*  90 */     if (gue == null)
/*     */     {
/*  92 */       return false;
/*     */     }
/*     */     
/*  95 */     DbManager.forceUpdate(gue);
/*     */     
/*  97 */     FingerprintEntity fpe = getFingerprint(gue, index);
/*  98 */     return (fpe != null) && (deleteEntity(fpe));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean persist(String username, int fingerIndex, String fpTpl)
/*     */   {
/* 112 */     DaoUser daoUser = (DaoUser)DbManager.getDao(UserEntity.class);
/* 113 */     UserEntity gue = (UserEntity)daoUser.getEntityBy("username", username);
/*     */     
/* 115 */     return (gue != null) && (updateEntity(new FingerprintEntity(gue, fingerIndex, fpTpl)));
/*     */   }
/*     */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoFingerprint.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */