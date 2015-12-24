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
/*    */ public class CmdRemoveAlert
/*    */   extends ClientCommand
/*    */ {
/*    */   public synchronized void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 28 */     Alert alert = Alert.deserialize(parameters[0]);
/*    */     
/* 30 */     if (alert == null)
/*    */     {
/* 32 */       SmartServer.sendMessage(ctx, new String[] { "removealert", "false" });
/* 33 */       return;
/*    */     }
/*    */     
/* 36 */     if ((alert.getType() == AlertType.TEMPERATURE) && (!(alert instanceof AlertTemperature)))
/*    */     {
/* 38 */       SmartServer.sendMessage(ctx, new String[] { "removealert", "false" });
/* 39 */       return;
/*    */     }
/*    */     
/* 42 */     if (!((DaoAlert)DbManager.getDao(AlertEntity.class)).deleteFromAlert(alert))
/*    */     {
/* 44 */       SmartServer.sendMessage(ctx, new String[] { "removealert", "false" });
/* 45 */       return;
/*    */     }
/*    */     
/* 48 */     SmartServer.sendMessage(ctx, new String[] { "removealert", "true" });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdRemoveAlert.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */