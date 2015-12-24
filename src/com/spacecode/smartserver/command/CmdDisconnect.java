/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import io.netty.channel.Channel;
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
/*    */ public class CmdDisconnect
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */     throws ClientCommandException
/*    */   {
/* 22 */     SmartServer.removeAdministrator(ctx.channel().remoteAddress());
/* 23 */     ctx.channel().close();
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdDisconnect.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */