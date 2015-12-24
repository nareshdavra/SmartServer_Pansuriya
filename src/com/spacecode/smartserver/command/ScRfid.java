/*     */ package com.spacecode.smartserver.command;
/*     */ 
/*     */ import com.spacecode.sdk.device.Device;
/*     */ import com.spacecode.smartserver.SmartServer;
/*     */ import com.spacecode.smartserver.helper.DeviceHandler;
/*     */ import com.spacecode.smartserver.helper.SmartLogger;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ScRfid
/*     */ {
/*     */   @CommandContract(deviceRequired=true, adminRequired=true, responseIfInvalid="-1")
/*     */   static class CmdRfidAxisCount
/*     */     extends ClientCommand
/*     */   {
/*     */     public void execute(ChannelHandlerContext ctx, String[] parameters)
/*     */     {
/*  30 */       Object queryAxisCount = DeviceHandler.getDevice().adminQuery("axis_count", new Object[0]);
/*     */       
/*  32 */       if (!(queryAxisCount instanceof Byte))
/*     */       {
/*  34 */         SmartServer.sendMessage(ctx, new String[] { "rfidaxiscount", "-1" });
/*  35 */         return;
/*     */       }
/*     */       
/*  38 */       byte axisCount = ((Byte)queryAxisCount).byteValue();
/*     */       
/*  40 */       SmartServer.sendMessage(ctx, new String[] { "rfidaxiscount", String.valueOf(axisCount == 0 ? 1 : axisCount) });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CommandContract(deviceRequired=true, adminRequired=true, responseIfInvalid="")
/*     */   static class CmdRfidCalibrate
/*     */     extends ClientCommand
/*     */   {
/*     */     public void execute(ChannelHandlerContext ctx, String[] parameters)
/*     */     {
/*  60 */       Object queryCalibrationValues = DeviceHandler.getDevice().adminQuery("calibration", new Object[0]);
/*     */       
/*  62 */       if (!(queryCalibrationValues instanceof byte[]))
/*     */       {
/*  64 */         SmartServer.sendMessage(ctx, new String[] { "rfidcalibrate" });
/*  65 */         return;
/*     */       }
/*     */       
/*  68 */       List<String> packets = new ArrayList();
/*  69 */       packets.add("rfidcalibrate");
/*     */       
/*  71 */       for (byte value : (byte[])queryCalibrationValues)
/*     */       {
/*  73 */         packets.add(String.valueOf(value));
/*     */       }
/*     */       
/*  76 */       SmartServer.sendMessage(ctx, (String[])packets.toArray(new String[packets.size()]));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CommandContract(deviceRequired=true, adminRequired=true)
/*     */   static class CmdRfidDecFrequency
/*     */     extends ClientCommand
/*     */   {
/*     */     public void execute(ChannelHandlerContext ctx, String[] parameters)
/*     */     {
/*  95 */       Object queryFrequency = DeviceHandler.getDevice().adminQuery("decrease_frequency", new Object[0]);
/*     */       
/*  97 */       if (!(queryFrequency instanceof Boolean))
/*     */       {
/*  99 */         SmartServer.sendMessage(ctx, new String[] { "rfiddecfrequency", "false" });
/* 100 */         return;
/*     */       }
/*     */       
/* 103 */       SmartServer.sendMessage(ctx, new String[] { "rfiddecfrequency", ((Boolean)queryFrequency).booleanValue() ? "true" : "false" });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CommandContract(deviceRequired=true, adminRequired=true, responseIfInvalid="")
/*     */   static class CmdRfidDutyCycle
/*     */     extends ClientCommand
/*     */   {
/*     */     public void execute(ChannelHandlerContext ctx, String[] parameters)
/*     */     {
/* 123 */       Object queryDcu = DeviceHandler.getDevice().adminQuery("duty_cycle", new Object[0]);
/*     */       
/* 125 */       if (!(queryDcu instanceof short[]))
/*     */       {
/* 127 */         SmartServer.sendMessage(ctx, new String[] { "rfiddutycycle" });
/* 128 */         return;
/*     */       }
/*     */       
/* 131 */       short[] dcuInfo = (short[])queryDcu;
/*     */       
/* 133 */       if (dcuInfo.length < 3)
/*     */       {
/* 135 */         SmartServer.sendMessage(ctx, new String[] { "rfiddutycycle" });
/* 136 */         return;
/*     */       }
/*     */       
/* 139 */       SmartServer.sendMessage(ctx, new String[] { "rfiddutycycle", String.valueOf(dcuInfo[0]), String.valueOf(dcuInfo[1]), String.valueOf(dcuInfo[2]) });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CommandContract(deviceRequired=true, adminRequired=true, responseIfInvalid="")
/*     */   static class CmdRfidFrequency
/*     */     extends ClientCommand
/*     */   {
/*     */     public void execute(ChannelHandlerContext ctx, String[] parameters)
/*     */     {
/* 160 */       Object queryCarrier = DeviceHandler.getDevice().adminQuery("carrier_period", new Object[0]);
/*     */       
/* 162 */       if (!(queryCarrier instanceof int[]))
/*     */       {
/* 164 */         SmartServer.sendMessage(ctx, new String[] { "rfidfrequency" });
/* 165 */         return;
/*     */       }
/*     */       
/* 168 */       int[] carrierInfo = (int[])queryCarrier;
/*     */       
/* 170 */       if (carrierInfo.length < 2)
/*     */       {
/* 172 */         SmartServer.sendMessage(ctx, new String[] { "rfidfrequency" });
/* 173 */         return;
/*     */       }
/*     */       
/* 176 */       SmartServer.sendMessage(ctx, new String[] { "rfidfrequency", String.valueOf(carrierInfo[0]), String.valueOf(carrierInfo[1]) });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CommandContract(deviceRequired=true, adminRequired=true)
/*     */   static class CmdRfidIncFrequency
/*     */     extends ClientCommand
/*     */   {
/*     */     public void execute(ChannelHandlerContext ctx, String[] parameters)
/*     */     {
/* 197 */       Object queryFrequency = DeviceHandler.getDevice().adminQuery("increase_frequency", new Object[0]);
/*     */       
/* 199 */       if (!(queryFrequency instanceof Boolean))
/*     */       {
/* 201 */         SmartServer.sendMessage(ctx, new String[] { "rfidincfrequency", "false" });
/* 202 */         return;
/*     */       }
/*     */       
/* 205 */       SmartServer.sendMessage(ctx, new String[] { "rfidincfrequency", ((Boolean)queryFrequency).booleanValue() ? "true" : "false" });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CommandContract(deviceRequired=true, adminRequired=true)
/*     */   static class CmdRfidSaveDutyCycle
/*     */     extends ClientCommand
/*     */   {
/*     */     public void execute(ChannelHandlerContext ctx, String[] parameters)
/*     */       throws ClientCommandException
/*     */     {
/* 225 */       Object querySaveDcu = DeviceHandler.getDevice().adminQuery("save_duty_cycle", new Object[0]);
/*     */       
/* 227 */       if (!(querySaveDcu instanceof Boolean))
/*     */       {
/* 229 */         SmartServer.sendMessage(ctx, new String[] { "rfidsavedutycycle", "false" });
/* 230 */         return;
/*     */       }
/*     */       
/* 233 */       SmartServer.sendMessage(ctx, new String[] { "rfidsavedutycycle", ((Boolean)querySaveDcu).booleanValue() ? "true" : "false" });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CommandContract(paramCount=1, strictCount=true, deviceRequired=true, adminRequired=true)
/*     */   static class CmdRfidSelectAxis
/*     */     extends ClientCommand
/*     */   {
/*     */     public void execute(ChannelHandlerContext ctx, String[] parameters)
/*     */     {
/*     */       byte axis;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 255 */         axis = Byte.parseByte(parameters[0]);
/*     */         
/* 257 */         if ((axis < 1) || (axis > 126))
/*     */         {
/* 259 */           throw new NumberFormatException("Axis value out of allowed range [0;126]");
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException nfe) {
/* 263 */         SmartLogger.getLogger().log(Level.WARNING, "Invalid value provided for the axis", nfe);
/* 264 */         SmartServer.sendMessage(ctx, new String[] { "rfidselectaxis", "false" });
/* 265 */         return;
/*     */       }
/*     */       
/* 268 */       Object querySelectAxis = DeviceHandler.getDevice().adminQuery("select_axis", new Object[] { Byte.valueOf(axis) });
/*     */       
/* 270 */       if (!(querySelectAxis instanceof Boolean))
/*     */       {
/* 272 */         SmartServer.sendMessage(ctx, new String[] { "rfidselectaxis", "false" });
/* 273 */         return;
/*     */       }
/*     */       
/* 276 */       SmartServer.sendMessage(ctx, new String[] { "rfidselectaxis", ((Boolean)querySelectAxis).booleanValue() ? "true" : "false" });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CommandContract(paramCount=2, strictCount=true, deviceRequired=true, adminRequired=true)
/*     */   static class CmdRfidSetDoorState
/*     */     extends ClientCommand
/*     */   {
/*     */     public void execute(ChannelHandlerContext ctx, String[] parameters)
/*     */     {
/* 296 */       boolean isMaster = Boolean.parseBoolean(parameters[0]);
/* 297 */       boolean state = Boolean.parseBoolean(parameters[1]);
/*     */       
/* 299 */       Object queryDoor = DeviceHandler.getDevice().adminQuery("set_door_state", new Object[] { Boolean.valueOf(isMaster), Boolean.valueOf(state) });
/*     */       
/* 301 */       if (!(queryDoor instanceof Boolean))
/*     */       {
/* 303 */         SmartServer.sendMessage(ctx, new String[] { "rfidsetdoorstate", "false" });
/* 304 */         return;
/*     */       }
/*     */       
/* 307 */       SmartServer.sendMessage(ctx, new String[] { "rfidsetdoorstate", ((Boolean)queryDoor).booleanValue() ? "true" : "false" });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @CommandContract(paramCount=3, strictCount=true, deviceRequired=true, adminRequired=true)
/*     */   static class CmdRfidSetDutyCycle
/*     */     extends ClientCommand
/*     */   {
/*     */     public void execute(ChannelHandlerContext ctx, String[] parameters)
/*     */     {
/*     */       short bridgeType;
/*     */       
/*     */ 
/*     */       short dcuFull;
/*     */       
/*     */ 
/*     */       short dcuHalf;
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 331 */         bridgeType = Short.parseShort(parameters[0]);
/* 332 */         dcuFull = Short.parseShort(parameters[1]);
/* 333 */         dcuHalf = Short.parseShort(parameters[2]);
/*     */         
/* 335 */         if ((bridgeType < 0) || (bridgeType > 1))
/*     */         {
/* 337 */           throw new NumberFormatException("Invalid bridge type");
/*     */         }
/*     */         
/* 340 */         if ((dcuFull < 0) || (dcuFull > 167))
/*     */         {
/* 342 */           throw new NumberFormatException("Duty Cycle for Full Bridge out of range [0;167]");
/*     */         }
/* 344 */         if ((dcuHalf < 0) || (dcuHalf > 167))
/*     */         {
/* 346 */           throw new NumberFormatException("Duty Cycle for Full Bridge out of range [0;167]");
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException nfe) {
/* 350 */         SmartLogger.getLogger().log(Level.WARNING, "Invalid value provided for the duty cycle...", nfe);
/* 351 */         SmartServer.sendMessage(ctx, new String[] { "rfidsetdutycycle", "false" });
/* 352 */         return;
/*     */       }
/*     */       
/* 355 */       Object querySetDcu = DeviceHandler.getDevice().adminQuery("set_duty_cycle", new Object[] { Short.valueOf(bridgeType), Short.valueOf(dcuFull), Short.valueOf(dcuHalf) });
/*     */       
/* 357 */       if (!(querySetDcu instanceof Boolean))
/*     */       {
/* 359 */         SmartServer.sendMessage(ctx, new String[] { "rfidsetdutycycle", "false" });
/* 360 */         return;
/*     */       }
/*     */       
/* 363 */       SmartServer.sendMessage(ctx, new String[] { "rfidsetdutycycle", ((Boolean)querySetDcu).booleanValue() ? "true" : "false" });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CommandContract(paramCount=1, strictCount=true, deviceRequired=true, adminRequired=true)
/*     */   static class CmdRfidSetThreshold
/*     */     extends ClientCommand
/*     */   {
/*     */     public void execute(ChannelHandlerContext ctx, String[] parameters)
/*     */     {
/*     */       short threshold;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 387 */         threshold = Short.parseShort(parameters[0]);
/*     */         
/* 389 */         if ((threshold < 3) || (threshold > 250))
/*     */         {
/* 391 */           throw new NumberFormatException("Threshold value out of allowed range [3;250]");
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException nfe) {
/* 395 */         SmartLogger.getLogger().log(Level.WARNING, "Invalid value provided for the threshold", nfe);
/* 396 */         SmartServer.sendMessage(ctx, new String[] { "rfidsetthreshold", "false" });
/* 397 */         return;
/*     */       }
/*     */       
/* 400 */       Object queryThreshold = DeviceHandler.getDevice().adminQuery("set_threshold", new Object[] { Short.valueOf(threshold) });
/*     */       
/* 402 */       if (!(queryThreshold instanceof Boolean))
/*     */       {
/* 404 */         SmartServer.sendMessage(ctx, new String[] { "rfidsetthreshold", "false" });
/* 405 */         return;
/*     */       }
/*     */       
/* 408 */       SmartServer.sendMessage(ctx, new String[] { "rfidsetthreshold", ((Boolean)queryThreshold).booleanValue() ? "true" : "false" });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CommandContract(deviceRequired=true, adminRequired=true)
/*     */   static class CmdRfidThreshold
/*     */     extends ClientCommand
/*     */   {
/*     */     public void execute(ChannelHandlerContext ctx, String[] parameters)
/*     */     {
/* 429 */       Object queryThreshold = DeviceHandler.getDevice().adminQuery("threshold", new Object[0]);
/*     */       
/* 431 */       if ((!(queryThreshold instanceof Integer)) && (!(queryThreshold instanceof Short)))
/*     */       {
/* 433 */         SmartServer.sendMessage(ctx, new String[] { "rfidthreshold", "-1" });
/* 434 */         return;
/*     */       }
/*     */       
/* 437 */       SmartServer.sendMessage(ctx, new String[] { "rfidthreshold", String.valueOf(((Short)queryThreshold).shortValue()) });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @CommandContract(paramCount=2, strictCount=true, deviceRequired=true, adminRequired=true)
/*     */   static class CmdRfidThresholdSampling
/*     */     extends ClientCommand
/*     */   {
/* 449 */     private static short[] _presentSamples = new short['Ä'];
/* 450 */     private static short[] _missingSamples = new short['Ä'];
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void execute(ChannelHandlerContext ctx, String[] parameters)
/*     */     {
/* 462 */       boolean cycling = "true".equals(parameters[1]);
/*     */       int samplesCount;
/*     */       try
/*     */       {
/* 466 */         samplesCount = Integer.parseInt(parameters[0]);
/*     */         
/* 468 */         if ((samplesCount < 3) || (samplesCount > 120))
/*     */         {
/* 470 */           throw new NumberFormatException("Samples count out of allowed range [3;120]");
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException nfe) {
/* 474 */         SmartLogger.getLogger().log(Level.WARNING, "Invalid value provided for the sample count", nfe);
/* 475 */         SmartServer.sendMessage(ctx, new String[] { "rfidthresholdsampling", "false" });
/* 476 */         return;
/*     */       }
/*     */       
/* 479 */       if (cycling)
/*     */       {
/*     */ 
/* 482 */         _presentSamples =new short['Ä'];
/* 483 */         _missingSamples = new short['Ä'];
/*     */       }
/*     */       
/* 486 */       Object queryThreshold = DeviceHandler.getDevice().adminQuery("threshold_sampling", new Object[] { Integer.valueOf(samplesCount), _missingSamples, _presentSamples });
/*     */       
/*     */ 
/* 489 */       if (!(queryThreshold instanceof Boolean))
/*     */       {
/* 491 */         SmartServer.sendMessage(ctx, new String[] { "rfidthresholdsampling", "false" });
/* 492 */         return;
/*     */       }
/*     */       
/* 495 */       SmartServer.sendMessage(ctx, new String[] { "rfidthresholdsampling", ((Boolean)queryThreshold).booleanValue() ? "true" : "false" });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\ScRfid.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */