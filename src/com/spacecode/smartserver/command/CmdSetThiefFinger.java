/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoUser;
/*    */ import com.spacecode.smartserver.database.entity.UserEntity;
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
/*    */ @CommandContract(paramCount=1)
/*    */ public class CmdSetThiefFinger
/*    */   extends ClientCommand
/*    */ {
/*    */   public synchronized void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 25 */     String username = parameters[0];
/* 26 */     Integer fingerIndex = null;
/*    */     
/*    */ 
/* 29 */     if (parameters.length > 1)
/*    */     {
/*    */       try
/*    */       {
/* 33 */         fingerIndex = Integer.valueOf(Integer.parseInt(parameters[1]));
/*    */       }
/*    */       catch (NumberFormatException nfe) {
/* 36 */         SmartServer.sendMessage(ctx, new String[] { "setthieffinger", "false" });
/* 37 */         return;
/*    */       }
/*    */     }
/*    */     
/* 41 */     DaoUser daoUser = (DaoUser)DbManager.getDao(UserEntity.class);
/* 42 */     if (!daoUser.updateThiefFingerIndex(username, fingerIndex))
/*    */     {
/* 44 */       SmartServer.sendMessage(ctx, new String[] { "setthieffinger", "false" });
/* 45 */       return;
/*    */     }
/*    */     
/* 48 */     SmartServer.sendMessage(ctx, new String[] { "setthieffinger", "true" });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdSetThiefFinger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */