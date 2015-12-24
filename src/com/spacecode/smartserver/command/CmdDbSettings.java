/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.helper.ConfManager;
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
/*    */ public class CmdDbSettings
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */     throws ClientCommandException
/*    */   {
/* 24 */     SmartServer.sendMessage(ctx, new String[] { "dbsettings", ConfManager.getDbHost(), ConfManager.getDbPort(), ConfManager.getDbName(), ConfManager.getDbUser(), ConfManager.getDbDbms() });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdDbSettings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */