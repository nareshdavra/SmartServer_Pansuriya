/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.device.data.Inventory;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoInventory;
/*    */ import com.spacecode.smartserver.database.entity.InventoryEntity;
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
/*    */ public class CmdInventoriesList
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/*    */     long timestampStart;
/*    */     long timestampEnd;
/*    */     try
/*    */     {
/* 37 */       timestampStart = Long.parseLong(parameters[0]);
/* 38 */       timestampEnd = Long.parseLong(parameters[1]);
/*    */     }
/*    */     catch (NumberFormatException nfe) {
/* 41 */       SmartLogger.getLogger().log(Level.WARNING, "Invalid timestamp sent by client for Inventories.", nfe);
/*    */       
/* 43 */       SmartServer.sendMessage(ctx, new String[] { "inventorieslist" });
/* 44 */       return;
/*    */     }
/*    */     
/* 47 */     if (timestampEnd <= timestampStart)
/*    */     {
/* 49 */       SmartServer.sendMessage(ctx, new String[] { "inventorieslist" });
/* 50 */       return;
/*    */     }
/*    */     
/* 53 */     DaoInventory daoInvent = (DaoInventory)DbManager.getDao(InventoryEntity.class);
/* 54 */     List<Inventory> inventories = daoInvent.getInventories(new Date(timestampStart), new Date(timestampEnd));
/*    */     
/* 56 */     List<String> responsePackets = new ArrayList();
/* 57 */     responsePackets.add("inventorieslist");
/*    */     
/* 59 */     for (Inventory inventory : inventories)
/*    */     {
/* 61 */       responsePackets.add(inventory.serialize());
/*    */     }
/*    */     
/* 64 */     SmartServer.sendMessage(ctx, (String[])responsePackets.toArray(new String[responsePackets.size()]));
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdInventoriesList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */