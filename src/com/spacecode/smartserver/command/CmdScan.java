/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.device.Device;
/*    */ import com.spacecode.sdk.device.data.DeviceStatus;
/*    */ import com.spacecode.sdk.device.data.ScanOption;
/*    */ import com.spacecode.smartserver.helper.DeviceHandler;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ @CommandContract(deviceRequired=true, responseIfInvalid="event_scan_failed", respondToAllIfInvalid=true)
/*    */ public class CmdScan
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
            DeviceStatus sts = DeviceHandler.getDevice().getStatus();
/* 29 */    if (sts == DeviceStatus.SCANNING)
/*    */    {
                SmartLogger.getLogger().warning("Trying to start a scan whereas the Device is already 'SCANNING'!");
                return;
/*    */    }
            if(sts == DeviceStatus.LED_ON)
            {
                SmartLogger.getLogger().warning("Trying to start a scan whereas the Device 'LEDon'!");
                return;
            }
/*    */
/* 35 */     List<ScanOption> scanOptions = new ArrayList();
/*    */     
/* 37 */     if (parameters.length > 0)
/*    */     {
/* 39 */       for (String option : parameters)
/*    */       {
/*    */         try
/*    */         {
/* 43 */           scanOptions.add(ScanOption.valueOf(option));
/*    */         }
/*    */         catch (IllegalArgumentException iae) {
/* 46 */           SmartLogger.getLogger().log(Level.WARNING, "Invalid ScanOption provided: " + option, iae);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 52 */     DeviceHandler.setRecordInventory(!scanOptions.contains(ScanOption.NO_RECORD));
/*    */     
/*    */ 
/* 55 */     DeviceHandler.getDevice().requestScan((ScanOption[])scanOptions.toArray(new ScanOption[scanOptions.size()]));
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdScan.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */