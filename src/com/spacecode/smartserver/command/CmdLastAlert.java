/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.network.alert.Alert;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoAlertHistory;
/*    */ import com.spacecode.smartserver.database.dao.DaoAlertTemperature;
/*    */ import com.spacecode.smartserver.database.entity.AlertEntity;
/*    */ import com.spacecode.smartserver.database.entity.AlertHistoryEntity;
/*    */ import com.spacecode.smartserver.database.entity.AlertTemperatureEntity;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.Date;
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
/*    */ @CommandContract(deviceRequired=true, responseIfInvalid="")
/*    */ public class CmdLastAlert
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 29 */     DaoAlertHistory daoAlertHistory = (DaoAlertHistory)DbManager.getDao(AlertHistoryEntity.class);
/*    */     
/* 31 */     DaoAlertTemperature daoAlertTemp = (DaoAlertTemperature)DbManager.getDao(AlertTemperatureEntity.class);
/*    */     
/*    */ 
/* 34 */     AlertHistoryEntity alertHisto = daoAlertHistory.getLastAlertHistory();
/*    */     
/* 36 */     if (alertHisto != null)
/*    */     {
/* 38 */       AlertEntity lastAlert = alertHisto.getAlert();
/*    */       
/* 40 */       if (lastAlert == null)
/*    */       {
/* 42 */         SmartServer.sendMessage(ctx, new String[] { "lastalert", "" });
/* 43 */         return;
/*    */       }
/*    */       
/*    */ 
/* 47 */       AlertTemperatureEntity ate = (AlertTemperatureEntity)daoAlertTemp.getEntityBy("alert_id", Integer.valueOf(lastAlert.getId()));
/*    */       
/*    */ 
/* 50 */       if (ate != null)
/*    */       {
/*    */ 
/* 53 */         SmartServer.sendMessage(ctx, new String[] { "lastalert", AlertEntity.toAlert(ate).serialize(), String.valueOf(alertHisto.getCreatedAt().getTime() / 1000L), alertHisto.getExtraData() });
/*    */         
/*    */ 
/*    */ 
/*    */ 
/* 58 */         return;
/*    */       }
/*    */       
/*    */ 
/* 62 */       SmartServer.sendMessage(ctx, new String[] { "lastalert", AlertEntity.toAlert(lastAlert).serialize(), String.valueOf(alertHisto.getCreatedAt().getTime() / 1000L), alertHisto.getExtraData() });
/*    */       
/*    */ 
/*    */ 
/*    */ 
/* 67 */       return;
/*    */     }
/*    */     
/* 70 */     SmartServer.sendMessage(ctx, new String[] { "lastalert", "" });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdLastAlert.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */