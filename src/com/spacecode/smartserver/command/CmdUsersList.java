/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.device.Device;
/*    */ import com.spacecode.sdk.user.User;
/*    */ import com.spacecode.sdk.user.UsersService;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.helper.DeviceHandler;
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
/*    */ @CommandContract(deviceRequired=true, responseIfInvalid="")
/*    */ public class CmdUsersList
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 27 */     List<String> responsePackets = new ArrayList();
/*    */     
/*    */ 
/* 30 */     responsePackets.add("userslist");
/*    */     
/*    */ 
/* 33 */     for (User user : DeviceHandler.getDevice().getUsersService().getUsers())
/*    */     {
/* 35 */       responsePackets.add(user.serialize());
/*    */     }
/*    */     
/* 38 */     SmartServer.sendMessage(ctx, (String[])responsePackets.toArray(new String[responsePackets.size()]));
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdUsersList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */