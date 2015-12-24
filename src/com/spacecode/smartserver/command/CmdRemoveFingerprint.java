/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.device.Device;
/*    */ import com.spacecode.sdk.user.User;
/*    */ import com.spacecode.sdk.user.UsersService;
/*    */ import com.spacecode.sdk.user.data.FingerIndex;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoFingerprint;
/*    */ import com.spacecode.smartserver.database.entity.FingerprintEntity;
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
/*    */ @CommandContract(paramCount=2, strictCount=true, deviceRequired=true)
/*    */ public class CmdRemoveFingerprint
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 28 */     String username = parameters[0];
/*    */     
/*    */     FingerIndex fingerIndex;
/*    */     try
/*    */     {
/* 33 */       int fingerIndexVal = Integer.parseInt(parameters[1]);
/* 34 */       fingerIndex = FingerIndex.getValueByIndex(fingerIndexVal);
/*    */     }
/*    */     catch (NumberFormatException nfe)
/*    */     {
/* 38 */       SmartServer.sendMessage(ctx, new String[] { "removefingerprint", "false" });
/* 39 */       return;
/*    */     }
/*    */     
/*    */ 
/* 43 */     if (fingerIndex == null)
/*    */     {
/* 45 */       SmartServer.sendMessage(ctx, new String[] { "removefingerprint", "false" });
/* 46 */       return;
/*    */     }
/*    */     
/* 49 */     User user = DeviceHandler.getDevice().getUsersService().getUserByName(username);
/*    */     
/* 51 */     if (user == null)
/*    */     {
/*    */ 
/* 54 */       SmartServer.sendMessage(ctx, new String[] { "removefingerprint", "false" });
/* 55 */       return;
/*    */     }
/*    */     
/*    */ 
/* 59 */     DaoFingerprint daoFp = (DaoFingerprint)DbManager.getDao(FingerprintEntity.class);
/* 60 */     if (!daoFp.delete(username, fingerIndex.getIndex()))
/*    */     {
/* 62 */       SmartServer.sendMessage(ctx, new String[] { "removefingerprint", "false" });
/* 63 */       return;
/*    */     }
/*    */     
/* 66 */     DeviceHandler.getDevice().getUsersService().removeFingerprint(username, fingerIndex);
/* 67 */     SmartServer.sendMessage(ctx, new String[] { "removefingerprint", "true" });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdRemoveFingerprint.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */