/*    */ package com.spacecode.smartserver.database.dao;
/*    */ 
/*    */ import com.j256.ormlite.support.ConnectionSource;
/*    */ import com.spacecode.sdk.network.alert.SmtpServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.entity.DeviceEntity;
/*    */ import com.spacecode.smartserver.database.entity.SmtpServerEntity;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ public class DaoSmtpServer
/*    */   extends DaoEntity<SmtpServerEntity, Integer>
/*    */ {
/*    */   public DaoSmtpServer(ConnectionSource connectionSource)
/*    */     throws SQLException
/*    */   {
/* 17 */     super(connectionSource, SmtpServerEntity.class);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean persist(SmtpServer smtpServer)
/*    */   {
/* 29 */     SmtpServerEntity currentSse = getSmtpServerConfig();
/*    */     
/* 31 */     if (currentSse == null)
/*    */     {
/* 33 */       return insert(new SmtpServerEntity(smtpServer.getAddress(), smtpServer.getPort(), smtpServer.getUsername(), smtpServer.getPassword(), smtpServer.isSslEnabled()));
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 39 */     currentSse.updateFrom(smtpServer);
/* 40 */     return updateEntity(currentSse);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public SmtpServerEntity getSmtpServerConfig()
/*    */   {
/* 47 */     return (SmtpServerEntity)getEntityBy("device_id", Integer.valueOf(DbManager.getDevEntity().getId()));
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoSmtpServer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */