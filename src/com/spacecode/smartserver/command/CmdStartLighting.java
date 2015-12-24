/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.device.Device;
/*    */ import com.spacecode.sdk.device.data.DeviceStatus;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.helper.DeviceHandler;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
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
/*    */ @CommandContract(paramCount=1, deviceRequired=true)
/*    */ public class CmdStartLighting
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 28 */     if (DeviceHandler.getDevice().getStatus() != DeviceStatus.READY)
/*    */     {
/* 30 */       SmartServer.sendMessage(ctx, new String[] { "startlighting", "false" });
/* 31 */       return;
/*    */     }
/*    */     
/*    */ 
/* 35 */     boolean result = DeviceHandler.getDevice().startLightingTagsLed(new ArrayList(Arrays.asList(parameters)));
/* 36 */     SmartServer.sendMessage(ctx, new String[] { "startlighting", result ? "true" : "false" });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdStartLighting.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */