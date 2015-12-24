/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.device.Device;
/*    */ import com.spacecode.sdk.user.User;
/*    */ import com.spacecode.sdk.user.UsersService;
/*    */ import com.spacecode.smartserver.SmartServer;
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
/*    */ @CommandContract(paramCount=1, strictCount=true, deviceRequired=true, responseIfInvalid="")
/*    */ public class CmdUserByName
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 24 */     String username = parameters[0];
/*    */     
/* 26 */     User user = DeviceHandler.getDevice().getUsersService().getUserByName(username);
/*    */     
/* 28 */     if (user == null)
/*    */     {
/* 30 */       SmartServer.sendMessage(ctx, new String[] { "userbyname", "" });
/* 31 */       return;
/*    */     }
/*    */     
/* 34 */     SmartServer.sendMessage(ctx, new String[] { "userbyname", user.serialize() });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdUserByName.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */