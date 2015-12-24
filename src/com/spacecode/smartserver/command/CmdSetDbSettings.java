/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.network.DbConfiguration;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.helper.ConfManager;
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
/*    */ @CommandContract(paramCount=6, strictCount=true)
/*    */ public class CmdSetDbSettings
/*    */   extends ClientCommand
/*    */ {
/*    */   public synchronized void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/*    */     try
/*    */     {
/* 31 */       String host = parameters[0];
/* 32 */       int port = Integer.parseInt(parameters[1]);
/* 33 */       String dbName = parameters[2];String user = parameters[3];String password = parameters[4];String dbms = parameters[5];
/*    */       
/* 35 */       DbConfiguration newConfig = new DbConfiguration(host, port, dbName, user, password, dbms);
/*    */       
/* 37 */       if (!ConfManager.setDbConfiguration(newConfig))
/*    */       {
/* 39 */         SmartServer.sendMessage(ctx, new String[] { "setdbsettings", "false" });
/* 40 */         return;
/*    */       }
/*    */       
/* 43 */       SmartServer.sendMessage(ctx, new String[] { "setdbsettings", "true" });
/*    */       
/* 45 */       SmartLogger.getLogger().info("DB Settings have changed... Connecting to Database...");
/*    */       
/* 47 */       if (!DbManager.initializeDatabase())
/*    */       {
/* 49 */         SmartLogger.getLogger().severe("Unable to re-initialize Database. New database configuration may be invalid.");
/*    */       }
/*    */       
/* 52 */       return;
/*    */     }
/*    */     catch (NumberFormatException nfe) {
/* 55 */       SmartLogger.getLogger().log(Level.SEVERE, "TCP Port number invalid.", nfe);
/*    */     }
/*    */     catch (IllegalArgumentException iae) {
/* 58 */       SmartLogger.getLogger().log(Level.SEVERE, "Invalid DbConfiguration provided.", iae);
/*    */     }
/*    */     
/* 61 */     SmartServer.sendMessage(ctx, new String[] { "setdbsettings", "false" });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdSetDbSettings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */