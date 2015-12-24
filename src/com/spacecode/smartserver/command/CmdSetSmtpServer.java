/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.network.alert.SmtpServer;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoSmtpServer;
/*    */ import com.spacecode.smartserver.database.entity.SmtpServerEntity;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.logging.Level;
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
/*    */ 
/*    */ 
/*    */ @CommandContract(paramCount=5, strictCount=true)
/*    */ public class CmdSetSmtpServer
/*    */   extends ClientCommand
/*    */ {
/*    */   public synchronized void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 30 */     String address = parameters[0];
/*    */     
/* 32 */     String username = parameters[2];String password = parameters[3];
/* 33 */     boolean sslEnabled = Boolean.parseBoolean(parameters[4]);
/*    */     int port;
/*    */     try
/*    */     {
/* 37 */       port = Integer.parseInt(parameters[1]);
/*    */     }
/*    */     catch (NumberFormatException nfe) {
/* 40 */       SmartLogger.getLogger().log(Level.SEVERE, "TCP Port number invalid.", nfe);
/* 41 */       SmartServer.sendMessage(ctx, new String[] { "setsmtpserver", "false" });
/* 42 */       return;
/*    */     }
/*    */     
/*    */     try
/*    */     {
/* 47 */       SmtpServer smtpServer = new SmtpServer(address, port, username, password, sslEnabled);
/*    */       
/* 49 */       if (!((DaoSmtpServer)DbManager.getDao(SmtpServerEntity.class)).persist(smtpServer))
/*    */       {
/*    */ 
/* 52 */         SmartServer.sendMessage(ctx, new String[] { "setsmtpserver", "false" });
/* 53 */         return;
/*    */       }
/*    */     }
/*    */     catch (IllegalArgumentException iae) {
/* 57 */       SmartLogger.getLogger().log(Level.SEVERE, "Invalid SmtpServer provided.", iae);
/* 58 */       SmartServer.sendMessage(ctx, new String[] { "setsmtpserver", "false" });
/* 59 */       return;
/*    */     }
/*    */     
/* 62 */     SmartServer.sendMessage(ctx, new String[] { "setsmtpserver", "true" });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdSetSmtpServer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */