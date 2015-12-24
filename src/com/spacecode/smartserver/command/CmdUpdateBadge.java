/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.device.Device;
/*    */ import com.spacecode.sdk.user.User;
/*    */ import com.spacecode.sdk.user.UsersService;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoUser;
/*    */ import com.spacecode.smartserver.database.entity.UserEntity;
/*    */ import com.spacecode.smartserver.helper.DeviceHandler;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
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
/*    */ @CommandContract(paramCount=1, deviceRequired=true)
/*    */ public class CmdUpdateBadge
/*    */   extends ClientCommand
/*    */ {
/*    */   public synchronized void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 28 */     String username = parameters[0];
/* 29 */     String badgeNumber = parameters.length > 1 ? parameters[1] : "";
/*    */     
/* 31 */     User user = DeviceHandler.getDevice().getUsersService().getUserByName(username);
/* 32 */     String badgeSave = "";
/*    */     
/* 34 */     if (user != null)
/*    */     {
/* 36 */       badgeSave = user.getBadgeNumber();
/*    */     }
/*    */     
/* 39 */     if (!DeviceHandler.getDevice().getUsersService().updateBadgeNumber(username, badgeNumber))
/*    */     {
/* 41 */       SmartServer.sendMessage(ctx, new String[] { "updatebadge", "false" });
/* 42 */       return;
/*    */     }
/*    */     
/* 45 */     DaoUser daoUser = (DaoUser)DbManager.getDao(UserEntity.class);
/* 46 */     if (!daoUser.updateBadgeNumber(username, badgeNumber))
/*    */     {
/*    */ 
/* 49 */       DeviceHandler.getDevice().getUsersService().updateBadgeNumber(username, badgeSave);
/*    */       
/* 51 */       SmartLogger.getLogger().warning(String.format("Unable to persist badge  number for %s", new Object[] { username }));
/* 52 */       SmartServer.sendMessage(ctx, new String[] { "updatebadge", "false" });
/* 53 */       return;
/*    */     }
/*    */     
/* 56 */     SmartServer.sendMessage(ctx, new String[] { "updatebadge", "true" });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdUpdateBadge.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */