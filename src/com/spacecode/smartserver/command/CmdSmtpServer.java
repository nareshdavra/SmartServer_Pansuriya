/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoSmtpServer;
/*    */ import com.spacecode.smartserver.database.entity.SmtpServerEntity;
/*    */ import io.netty.channel.ChannelHandlerContext;
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
/*    */ public class CmdSmtpServer
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */     throws ClientCommandException
/*    */   {
/* 26 */     SmtpServerEntity sse = ((DaoSmtpServer)DbManager.getDao(SmtpServerEntity.class)).getSmtpServerConfig();
/*    */     
/*    */ 
/* 29 */     if (sse == null)
/*    */     {
/* 31 */       SmartServer.sendMessage(ctx, new String[] { "smtpserver" });
/*    */ 
/*    */     }
/*    */     else
/*    */     {
/* 36 */       SmartServer.sendMessage(ctx, new String[] { "smtpserver", sse.getAddress(), String.valueOf(sse.getPort()), sse.getUsername(), sse.getPassword(), String.valueOf(sse.isSslEnabled()) });
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdSmtpServer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */