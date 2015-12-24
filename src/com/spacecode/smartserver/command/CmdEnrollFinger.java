/*     */ package com.spacecode.smartserver.command;
/*     */ 
/*     */ import com.spacecode.sdk.device.Device;
/*     */ import com.spacecode.sdk.user.User;
/*     */ import com.spacecode.sdk.user.UsersService;
/*     */ import com.spacecode.sdk.user.data.FingerIndex;
/*     */ import com.spacecode.smartserver.SmartServer;
/*     */ import com.spacecode.smartserver.database.DbManager;
/*     */ import com.spacecode.smartserver.database.dao.DaoFingerprint;
/*     */ import com.spacecode.smartserver.database.entity.FingerprintEntity;
/*     */ import com.spacecode.smartserver.helper.DeviceHandler;
/*     */ import com.spacecode.smartserver.helper.SmartLogger;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CommandContract(paramCount=3, deviceRequired=true)
/*     */ public class CmdEnrollFinger
/*     */   extends ClientCommand
/*     */ {
/*     */   public synchronized void execute(final ChannelHandlerContext ctx, String[] parameters)
/*     */   {
/*  32 */     final String username = parameters[0];
/*     */     
/*  34 */     final boolean masterReader = Boolean.parseBoolean(parameters[2]);
/*  35 */     String template = parameters.length > 3 ? parameters[3] : null;
/*     */     
/*  37 */     final User gu = DeviceHandler.getDevice().getUsersService().getUserByName(username);
/*     */     
/*  39 */     if (gu == null)
/*     */     {
/*     */ 
/*  42 */       SmartServer.sendMessage(ctx, new String[] { "enrollfinger", "false" }); return;
/*     */     }
/*     */     
/*     */     final FingerIndex fingerIndex;
/*     */     try
/*     */     {
/*  48 */       int fingerIndexVal = Integer.parseInt(parameters[1]);
/*  49 */       fingerIndex = FingerIndex.getValueByIndex(fingerIndexVal);
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/*  53 */       SmartServer.sendMessage(ctx, new String[] { "enrollfinger", "false" });
/*  54 */       return;
/*     */     }
/*     */     
/*     */ 
/*  58 */     if (fingerIndex == null)
/*     */     {
/*  60 */       SmartServer.sendMessage(ctx, new String[] { "enrollfinger", "false" });
/*  61 */       return;
/*     */     }
/*     */     
/*  64 */     final String oldTemplate = gu.getFingerprintTemplate(fingerIndex);
/*     */     
/*     */ 
/*  67 */     if ((template == null) || (template.trim().isEmpty()))
/*     */     {
/*     */ 
/*  70 */       parallelize(new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*     */           boolean result;
/*     */           
/*     */ 
/*     */           try
/*     */           {
/*  79 */             result = CmdEnrollFinger.this.enrollAndPersist(gu, fingerIndex, masterReader, oldTemplate);
/*     */           }
/*     */           catch (TimeoutException te) {
/*  82 */             SmartLogger.getLogger().log(Level.WARNING, "Enrollment process timed out for User " + username, te);
/*     */             
/*  84 */             result = false;
/*     */           }
/*     */           
/*  87 */           SmartServer.sendMessage(ctx, new String[] { "enrollfinger", result ? "true" : "false" });
/*     */         }
/*     */         
/*     */ 
/*     */       });
/*     */     }
/*     */     else
/*     */     {
/*  95 */       if (!DeviceHandler.getDevice().getUsersService().enrollFinger(username, fingerIndex, template))
/*     */       {
/*  97 */         SmartServer.sendMessage(ctx, new String[] { "enrollfinger", "false" });
/*  98 */         return;
/*     */       }
/*     */       
/* 101 */       SmartServer.sendMessage(ctx, new String[] { "enrollfinger", persistTemplate(gu, fingerIndex, oldTemplate) ? "true" : "false" });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean enrollAndPersist(User user, FingerIndex fingerIndex, boolean masterReader, String oldTemplate)
/*     */     throws TimeoutException
/*     */   {
/* 121 */     return (DeviceHandler.getDevice().getUsersService().enrollFinger(user.getUsername(), fingerIndex, masterReader)) && (persistTemplate(user, fingerIndex, oldTemplate));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean persistTemplate(User user, FingerIndex fingerIndex, String oldTemplate)
/*     */   {
/* 137 */     String fpTpl = user.getFingerprintTemplate(fingerIndex);
/* 138 */     DaoFingerprint daoFp = (DaoFingerprint)DbManager.getDao(FingerprintEntity.class);
/*     */     
/* 140 */     if (!daoFp.persist(user.getUsername(), fingerIndex.getIndex(), fpTpl))
/*     */     {
/* 142 */       DeviceHandler.getDevice().getUsersService().enrollFinger(user.getUsername(), fingerIndex, oldTemplate);
/* 143 */       return false;
/*     */     }
/*     */     
/* 146 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdEnrollFinger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */