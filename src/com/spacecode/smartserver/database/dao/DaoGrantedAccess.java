/*    */ package com.spacecode.smartserver.database.dao;
/*    */ 
/*    */ import com.j256.ormlite.support.ConnectionSource;
/*    */ import com.spacecode.sdk.user.data.GrantType;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.entity.DeviceEntity;
/*    */ import com.spacecode.smartserver.database.entity.GrantTypeEntity;
/*    */ import com.spacecode.smartserver.database.entity.GrantedAccessEntity;
/*    */ import com.spacecode.smartserver.database.entity.UserEntity;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DaoGrantedAccess
/*    */   extends DaoEntity<GrantedAccessEntity, Integer>
/*    */ {
/*    */   public DaoGrantedAccess(ConnectionSource connectionSource)
/*    */     throws SQLException
/*    */   {
/* 23 */     super(connectionSource, GrantedAccessEntity.class);
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
/*    */   public boolean persist(String username, GrantType grantType)
/*    */   {
/* 36 */     DaoUser daoUser = (DaoUser)DbManager.getDao(UserEntity.class);
/* 37 */     return persist((UserEntity)daoUser.getEntityBy("username", username), grantType);
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
/*    */   public boolean persist(UserEntity gue, GrantType grantType)
/*    */   {
/* 50 */     DaoGrantType daoGrantType = (DaoGrantType)DbManager.getDao(GrantTypeEntity.class);
/* 51 */     DeviceEntity devEntity = DbManager.getDevEntity();
/*    */     
/* 53 */     if ((gue == null) || (devEntity == null))
/*    */     {
/* 55 */       return false;
/*    */     }
/*    */     
/* 58 */     GrantTypeEntity gte = daoGrantType.fromGrantType(grantType);
/*    */     
/* 60 */     if (gte == null)
/*    */     {
/* 62 */       SmartLogger.getLogger().severe("Persisting permission: unknown grant type " + grantType + ".");
/* 63 */       return false;
/*    */     }
/*    */     
/* 66 */     GrantedAccessEntity gae = new GrantedAccessEntity(gue, gte);
/* 67 */     Collection<GrantedAccessEntity> gaesList = gue.getGrantedAccesses();
/*    */     
/* 69 */     if (gaesList == null)
/*    */     {
/* 71 */       SmartLogger.getLogger().severe("UserEntity with null collection of GrantedAccess. Not hydrated?");
/* 72 */       return false;
/*    */     }
/*    */     
/* 75 */     Iterator<GrantedAccessEntity> it = gaesList.iterator();
/*    */     
/*    */ 
/* 78 */     while (it.hasNext())
/*    */     {
/* 80 */       if (((GrantedAccessEntity)it.next()).getDevice().getId() == devEntity.getId())
/*    */       {
/* 82 */         it.remove();
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 87 */     DbManager.forceUpdate(gue);
/*    */     
/*    */ 
/* 90 */     return gaesList.add(gae);
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoGrantedAccess.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */