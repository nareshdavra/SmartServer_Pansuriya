/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.network.alert.Alert;
/*    */ import com.spacecode.sdk.network.alert.AlertType;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoAlert;
/*    */ import com.spacecode.smartserver.database.dao.DaoAlertTemperature;
/*    */ import com.spacecode.smartserver.database.dao.DaoAlertType;
/*    */ import com.spacecode.smartserver.database.entity.AlertEntity;
/*    */ import com.spacecode.smartserver.database.entity.AlertTemperatureEntity;
/*    */ import com.spacecode.smartserver.database.entity.AlertTypeEntity;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class CmdAlertsList
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */     throws ClientCommandException
/*    */   {
/* 34 */     List<String> responsePackets = new ArrayList();
/*    */     
/* 36 */     DaoAlertType daoAlertType = (DaoAlertType)DbManager.getDao(AlertTypeEntity.class);
/* 37 */     DaoAlert daoAlert = (DaoAlert)DbManager.getDao(AlertEntity.class);
/* 38 */     DaoAlertTemperature daoAlertTemperature = (DaoAlertTemperature)DbManager.getDao(AlertTemperatureEntity.class);
/*    */     
/* 40 */     AlertTypeEntity alertTypeTemperature = daoAlertType.fromAlertType(AlertType.TEMPERATURE);
/*    */     
/* 42 */     if (alertTypeTemperature == null)
/*    */     {
/* 44 */       SmartServer.sendMessage(ctx, (String[])responsePackets.toArray(new String[responsePackets.size()]));
/* 45 */       SmartLogger.getLogger().severe("Unable to get AlertTypeEntity Temperature from DB.");
/* 46 */       return;
/*    */     }
/*    */     
/*    */ 
/* 50 */     List<AlertEntity> simpleAlertsFromDb = daoAlert.getEntitiesWhereNotEqual("alert_type_id", Integer.valueOf(alertTypeTemperature.getId()));
/*    */     
/*    */ 
/* 53 */     List<AlertTemperatureEntity> alertsTemperatureFromDb = daoAlertTemperature.getAll();
/*    */     
/*    */ 
/* 56 */     List<Alert> serializableAlerts = new ArrayList();
/*    */     
/* 58 */     for (AlertEntity ae : simpleAlertsFromDb)
/*    */     {
/* 60 */       serializableAlerts.add(AlertEntity.toAlert(ae));
/*    */     }
/*    */     
/* 63 */     for (AlertTemperatureEntity alertTempE : alertsTemperatureFromDb)
/*    */     {
/* 65 */       serializableAlerts.add(AlertEntity.toAlert(alertTempE));
/*    */     }
/*    */     
/*    */ 
/* 69 */     responsePackets.add("alertslist");
/*    */     
/*    */ 
/* 72 */     for (Alert alert : serializableAlerts)
/*    */     {
/* 74 */       responsePackets.add(alert.serialize());
/*    */     }
/*    */     
/*    */ 
/* 78 */     SmartServer.sendMessage(ctx, (String[])responsePackets.toArray(new String[responsePackets.size()]));
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdAlertsList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */