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
/*    */ public class CmdProbeSettings
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 22 */     SmartServer.sendMessage(ctx, new String[] { "probesettings", String.valueOf(ConfManager.getDevTemperatureDelay()), String.valueOf(ConfManager.getDevTemperatureDelta()), String.valueOf(ConfManager.isDevTemperature()) });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdProbeSettings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */