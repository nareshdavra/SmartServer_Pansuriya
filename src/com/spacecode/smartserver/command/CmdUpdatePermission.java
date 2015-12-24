/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.device.Device;
/*    */ import com.spacecode.sdk.user.UsersService;
/*    */ import com.spacecode.sdk.user.data.GrantType;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoGrantedAccess;
/*    */ import com.spacecode.smartserver.database.entity.GrantedAccessEntity;
/*    */ import com.spacecode.smartserver.helper.DeviceHandler;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import io.netty.channel.ChannelHandlerContext;
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
/*    */ @CommandContract(paramCount=2, strictCount=true, deviceRequired=true)
/*    */ public class CmdUpdatePermission
/*    */   extends ClientCommand
/*    */ {
/*    */   public synchronized void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 30 */     String username = parameters[0];
/* 31 */     String newPermission = parameters[1];
/*    */     
/*    */ 
/* 34 */     if (username.trim().isEmpty())
/*    */     {
/* 36 */       SmartServer.sendMessage(ctx, new String[] { "updatepermission", "false" }); return;
/*    */     }
/*    */     
/*    */     GrantType grantType;
/*    */     try
/*    */     {
/* 42 */       grantType = GrantType.valueOf(newPermission);
/*    */     }
/*    */     catch (IllegalArgumentException iae) {
/* 45 */       SmartLogger.getLogger().log(Level.SEVERE, "Invalid GrantType for permission update", iae);
/* 46 */       SmartServer.sendMessage(ctx, new String[] { "updatepermission", "false" });
/* 47 */       return;
/*    */     }
/*    */     
/* 50 */     boolean result = DeviceHandler.getDevice().getUsersService().updatePermission(username, grantType);
/*    */     
/* 52 */     if (!result)
/*    */     {
/* 54 */       SmartServer.sendMessage(ctx, new String[] { "updatepermission", "false" });
/* 55 */       return;
/*    */     }
/*    */     
/* 58 */     DaoGrantedAccess daoGa = (DaoGrantedAccess)DbManager.getDao(GrantedAccessEntity.class);
/* 59 */     if (!daoGa.persist(username, grantType))
/*    */     {
/* 61 */       SmartLogger.getLogger().severe(String.format("Permission set to %s for User %s, but not persisted!", new Object[] { newPermission, username }));
/*    */       
/* 63 */       SmartServer.sendMessage(ctx, new String[] { "updatepermission", "false" });
/* 64 */       return;
/*    */     }
/*    */     
/* 67 */     SmartServer.sendMessage(ctx, new String[] { "updatepermission", "true" });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdUpdatePermission.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */