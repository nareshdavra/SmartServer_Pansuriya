/*    */ package com.spacecode.smartserver.database.dao;
/*    */ 
/*    */ import com.j256.ormlite.stmt.QueryBuilder;
/*    */ import com.j256.ormlite.stmt.Where;
/*    */ import com.j256.ormlite.support.ConnectionSource;
/*    */ import com.spacecode.sdk.device.data.DoorInfo;
import com.spacecode.sdk.device.data.Inventory;
import com.spacecode.sdk.user.User;
/*    */ import com.spacecode.sdk.user.data.AccessType;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.entity.*;
/*    */
/*    */
/*    */
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ public class DaoAuthentication
/*    */   extends DaoEntity<AuthenticationEntity, Integer>
/*    */ {
/*    */   public DaoAuthentication(ConnectionSource connectionSource)
/*    */     throws SQLException
/*    */   {
/* 26 */     super(connectionSource, AuthenticationEntity.class);
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
/*    */   public List<AuthenticationEntity> getAuthentications(Date from, Date to)
/*    */   {
/*    */     try
/*    */     {
/* 41 */       QueryBuilder<AuthenticationEntity, Integer> qb = queryBuilder();
/*    */       
/* 43 */       return query(qb.orderBy("created_at", true).where().eq("device_id", Integer.valueOf(DbManager.getDevEntity().getId())).and().between("created_at", from, to).prepare());
/*    */ 
/*    */ 
/*    */ 
/*    */     }
/*    */     catch (SQLException sqle)
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/* 53 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting authentications.", sqle); }
/* 54 */     return new ArrayList();
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

    /*    */
            public boolean persist(User user, AccessType accessType,DoorInfo isMaster)
/*    */   {
/* 68 */     DaoUser daoUser = (DaoUser)DbManager.getDao(UserEntity.class);
/* 69 */     DaoAccessType daoAccessType = (DaoAccessType)DbManager.getDao(AccessTypeEntity.class);
/*    */
/* 71 */     UserEntity gue = (UserEntity)daoUser.getEntityBy("username", user.getUsername());
/*    */
/* 73 */     if (gue == null)
/*    */     {
/*    */
/* 76 */       return false;
/*    */     }
/*    */
/* 79 */     AccessTypeEntity ate = daoAccessType.fromAccessType(accessType);
/*    */
/* 81 */     if (ate == null)
/*    */     {
/* 83 */       SmartLogger.getLogger().severe("Persisting authentication: unknown access type " + accessType + ".");
/* 84 */       return false;
/*    */     }
             DaoInventory daoInventory = (DaoInventory)DbManager.getDao(InventoryEntity.class);
             InventoryEntity inventoryEntity = daoInventory.getLastInventoryEntity();
/*    */
/* 87 */     return insert(new AuthenticationEntity(DbManager.getDevEntity(), gue, ate,inventoryEntity,isMaster));
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoAuthentication.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */