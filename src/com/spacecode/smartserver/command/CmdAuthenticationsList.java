/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.user.data.AccessType;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoAccessType;
/*    */ import com.spacecode.smartserver.database.dao.DaoAuthentication;
/*    */ import com.spacecode.smartserver.database.entity.AuthenticationEntity;
/*    */ import com.spacecode.smartserver.database.entity.UserEntity;
/*    */ import com.spacecode.smartserver.helper.SmartLogger;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandContract(paramCount=2, strictCount=true, responseIfInvalid="")
/*    */ public class CmdAuthenticationsList
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/*    */     long timestampStart;
/*    */     long timestampEnd;
/*    */     try
/*    */     {
/* 39 */       timestampStart = Long.parseLong(parameters[0]);
/* 40 */       timestampEnd = Long.parseLong(parameters[1]);
/*    */     }
/*    */     catch (NumberFormatException nfe) {
/* 43 */       SmartLogger.getLogger().log(Level.WARNING, "Invalid timestamp sent by client for Authentications.", nfe);
/*    */       
/* 45 */       SmartServer.sendMessage(ctx, new String[] { "authenticationslist" });
/* 46 */       return;
/*    */     }
/*    */     
/* 49 */     if (timestampEnd <= timestampStart)
/*    */     {
/* 51 */       SmartServer.sendMessage(ctx, new String[] { "authenticationslist" });
/* 52 */       return;
/*    */     }
/*    */     
/* 55 */     DaoAuthentication daoAuthentication = (DaoAuthentication)DbManager.getDao(AuthenticationEntity.class);
/*    */     
/* 57 */     List<AuthenticationEntity> authentications = daoAuthentication.getAuthentications(new Date(timestampStart), new Date(timestampEnd));
/*    */     
/*    */ 
/* 60 */     List<String> responsePackets = new ArrayList();
/* 61 */     responsePackets.add("authenticationslist");
/*    */     
/* 63 */     for (AuthenticationEntity authentication : authentications)
/*    */     {
/* 65 */       AccessType accessType = DaoAccessType.asAccessType(authentication.getAccessType());
/* 66 */       String accessTypePacket = accessType == AccessType.FINGERPRINT ? "F" : accessType == AccessType.BADGE ? "B" : "U";
/*    */       
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 73 */       UserEntity authenticatedUser = authentication.getUser();
/* 74 */       responsePackets.add(authenticatedUser != null ? authenticatedUser.getUsername() : "Unknown User");
/* 75 */       responsePackets.add(String.valueOf(authentication.getCreatedAt().getTime() / 1000L));
/* 76 */       responsePackets.add(accessTypePacket);
/*    */     }
/*    */     
/* 79 */     SmartServer.sendMessage(ctx, (String[])responsePackets.toArray(new String[responsePackets.size()]));
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdAuthenticationsList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */