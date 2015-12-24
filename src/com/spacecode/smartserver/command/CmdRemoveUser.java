/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.device.Device;
/*    */ import com.spacecode.sdk.user.User;
/*    */ import com.spacecode.sdk.user.UsersService;
/*    */ import com.spacecode.sdk.user.data.GrantType;
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
/*    */ @CommandContract(paramCount=1, strictCount=true, deviceRequired=true)
/*    */ public class CmdRemoveUser
/*    */   extends ClientCommand
/*    */ {
/*    */   public synchronized void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 29 */     String username = parameters[0];
/*    */     
/* 31 */     User user = DeviceHandler.getDevice().getUsersService().getUserByName(username);
/*    */     
/* 33 */     if (user == null)
/*    */     {
/* 35 */       SmartServer.sendMessage(ctx, new String[] { "removeuser", "false" });
/* 36 */       return;
/*    */     }
/*    */     
/* 39 */     GrantType grantSave = user.getPermission();
/*    */     
/* 41 */     if (!DeviceHandler.getDevice().getUsersService().removeUser(username))
/*    */     {
/* 43 */       SmartServer.sendMessage(ctx, new String[] { "removeuser", "false" });
/* 44 */       return;
/*    */     }
/*    */     
/* 47 */     DaoUser daoUser = (DaoUser)DbManager.getDao(UserEntity.class);
/* 48 */     if (!daoUser.removePermission(username))
/*    */     {
/*    */ 
/* 51 */       DeviceHandler.getDevice().getUsersService().updatePermission(username, grantSave);
/*    */       
/*    */ 
/* 54 */       SmartLogger.getLogger().warning(username + " removed from authorized users, but permission was not removed from DB.");
/*    */       
/* 56 */       SmartServer.sendMessage(ctx, new String[] { "removeuser", "false" });
/* 57 */       return;
/*    */     }
/*    */     
/* 60 */     SmartServer.sendMessage(ctx, new String[] { "removeuser", "true" });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdRemoveUser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */