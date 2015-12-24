/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.network.alert.Alert;
/*    */ import com.spacecode.sdk.network.alert.AlertTemperature;
/*    */ import com.spacecode.sdk.network.alert.AlertType;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoAlert;
/*    */ import com.spacecode.smartserver.database.entity.AlertEntity;
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
/*    */ @CommandContract(paramCount=1, strictCount=true)
/*    */ public class CmdUpdateAlert
/*    */   extends ClientCommand
/*    */ {
/*    */   public synchronized void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 28 */     Alert alert = Alert.deserialize(parameters[0]);
/*    */     
/* 30 */     if (alert == null)
/*    */     {
/* 32 */       SmartServer.sendMessage(ctx, new String[] { "updatealert", "false" });
/* 33 */       return;
/*    */     }
/*    */     
/* 36 */     if ((alert.getType() == AlertType.TEMPERATURE) && (!(alert instanceof AlertTemperature)))
/*    */     {
/* 38 */       SmartServer.sendMessage(ctx, new String[] { "updatealert", "false" });
/* 39 */       return;
/*    */     }
/*    */     
/* 42 */     DaoAlert daoAlert = (DaoAlert)DbManager.getDao(AlertEntity.class);
/* 43 */     if (!daoAlert.persist(alert))
/*    */     {
/* 45 */       SmartServer.sendMessage(ctx, new String[] { "updatealert", "false" });
/* 46 */       return;
/*    */     }
/*    */     
/* 49 */     SmartServer.sendMessage(ctx, new String[] { "updatealert", "true" });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdUpdateAlert.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */