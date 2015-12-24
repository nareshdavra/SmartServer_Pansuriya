/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.device.Device;
/*    */ import com.spacecode.sdk.device.data.DeviceStatus;
/*    */ import com.spacecode.sdk.device.data.DeviceType;
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
/*    */ @CommandContract(deviceRequired=true, noResponseWhenInvalid=true)
/*    */ public class CmdInitialization
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 24 */     SmartServer.sendMessage(ctx, new String[] { "initialization", DeviceHandler.getDevice().getSerialNumber(), DeviceHandler.getDevice().getDeviceType().name(), DeviceHandler.getDevice().getHardwareVersion(), DeviceHandler.getDevice().getSoftwareVersion(), DeviceHandler.getDevice().getStatus().name() });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdInitialization.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */