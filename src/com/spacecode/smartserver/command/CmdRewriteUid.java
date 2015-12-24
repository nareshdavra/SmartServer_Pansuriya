/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.device.Device;
/*    */ import com.spacecode.sdk.device.data.RewriteUidResult;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.helper.DeviceHandler;
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
/*    */ @CommandContract(paramCount=2, strictCount=true, deviceRequired=true, responseIfInvalid="ERROR")
/*    */ public class CmdRewriteUid
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 25 */     RewriteUidResult result = DeviceHandler.getDevice().rewriteUid(parameters[0], parameters[1]);
/* 26 */     SmartServer.sendMessage(ctx, new String[] { "rewriteuid", result.name() });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdRewriteUid.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */