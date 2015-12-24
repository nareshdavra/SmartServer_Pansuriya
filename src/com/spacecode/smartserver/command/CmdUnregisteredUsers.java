/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.device.Device;
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
/*    */ public class CmdUnregisteredUsers
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 26 */     List<String> responsePackets = new ArrayList();
/*    */     
/*    */ 
/* 29 */     responsePackets.add("usersunregistered");
/*    */     
/*    */ 
/* 32 */     for (String username : DeviceHandler.getDevice().getUsersService().getUnregisteredUsers())
/*    */     {
/* 34 */       responsePackets.add(username);
/*    */     }
/*    */     
/* 37 */     SmartServer.sendMessage(ctx, (String[])responsePackets.toArray(new String[responsePackets.size()]));
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdUnregisteredUsers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */