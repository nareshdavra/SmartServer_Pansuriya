/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoTemperatureMeasurement;
/*    */ import com.spacecode.smartserver.database.entity.TemperatureMeasurementEntity;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
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
/*    */ 
/*    */ 
/*    */ @CommandContract(paramCount=2, strictCount=true, deviceRequired=true, responseIfInvalid="")
/*    */ public class CmdTemperatureList
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/*    */     long timestampStart;
/*    */     long timestampEnd;
/*    */     try
/*    */     {
/* 36 */       timestampStart = Long.parseLong(parameters[0]);
/* 37 */       timestampEnd = Long.parseLong(parameters[1]);
/*    */     }
/*    */     catch (NumberFormatException nfe) {
/* 40 */       SmartLogger.getLogger().log(Level.WARNING, "Invalid timestamp sent by client for TemperatureList.", nfe);
/*    */       
/* 42 */       SmartServer.sendMessage(ctx, new String[] { "temperaturelist" });
/* 43 */       return;
/*    */     }
/*    */     
/* 46 */     if (timestampEnd <= timestampStart)
/*    */     {
/* 48 */       SmartServer.sendMessage(ctx, new String[] { "temperaturelist" });
/* 49 */       return;
/*    */     }
/*    */     
/* 52 */     DaoTemperatureMeasurement repo = (DaoTemperatureMeasurement)DbManager.getDao(TemperatureMeasurementEntity.class);
/*    */     
/*    */ 
/* 55 */     List<TemperatureMeasurementEntity> entities = repo.getTemperatureMeasures(new Date(timestampStart), new Date(timestampEnd));
/*    */     
/*    */ 
/* 58 */     List<String> responsePackets = new ArrayList();
/* 59 */     responsePackets.add("temperaturelist");
/*    */     
/* 61 */     for (TemperatureMeasurementEntity entity : entities)
/*    */     {
/*    */ 
/* 64 */       responsePackets.add(String.valueOf(entity.getCreatedAt().getTime() / 1000L));
/* 65 */       responsePackets.add(String.valueOf(entity.getValue()));
/*    */     }
/*    */     
/* 68 */     SmartServer.sendMessage(ctx, (String[])responsePackets.toArray(new String[responsePackets.size()]));
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdTemperatureList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */