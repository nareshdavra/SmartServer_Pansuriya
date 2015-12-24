/*    */ package com.spacecode.smartserver.command;
/*    */ 
/*    */ import com.spacecode.sdk.device.Device;
/*    */ import com.spacecode.sdk.device.data.DeviceStatus;
/*    */ import com.spacecode.sdk.device.data.Inventory;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoInventory;
/*    */ import com.spacecode.smartserver.database.entity.InventoryEntity;
/*    */ import com.spacecode.smartserver.helper.DeviceHandler;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandContract(deviceRequired=true, responseIfInvalid="")
/*    */ public class CmdLastInventory
/*    */   extends ClientCommand
/*    */ {
/* 21 */   private Inventory _lastInventory = null;
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
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 34 */     if (!DeviceHandler.getRecordInventory())
/*    */     {
/*    */ 
/*    */ 
/* 38 */       if (DeviceHandler.getDevice().getStatus() != DeviceStatus.SCANNING)
/*    */       {
                 getAndSendLastInventory(ctx);
/* 40 */         //sendInventory(ctx, DeviceHandler.getDevice().);
/* 41 */         return;
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 46 */     //if (this._lastInventory == null)
/*    */     //{
/* 48 */       getAndSendLastInventory(ctx);
/*    */ 
/*    */     //}
/*    */     //else
/*    */     //
///* 53 */       Inventory deviceInventory = DeviceHandler.getDevice().getLastInventory();
///*    */
/*    */ 
///* 56 */       if (this._lastInventory.getCreationDate().getTime() == deviceInventory.getCreationDate().getTime())
///*    */       {
///* 58 */         SmartServer.sendMessage(ctx, new String[] { "lastinventory", this._lastInventory.serialize() });
/*    */ 
///*    */       }
///*    */       else
///*    */       {
/*    */ 
///* 64 */         getAndSendLastInventory(ctx);
///*    */       }
///*    */     }
/*    */   }
/*    */   
/*    */   private void getAndSendLastInventory(ChannelHandlerContext ctx)
/*    */   {
             InventoryEntity invEntity = ((DaoInventory)DbManager.getDao(InventoryEntity.class)).getLastInventoryEntity();
/* 71 */     this._lastInventory = ((DaoInventory)DbManager.getDao(InventoryEntity.class)).getLastInventory();
/* 72 */     sendInventory(ctx, invEntity);
/*    */   }
/*    */   
/*    */ 
/*    */   private void sendInventory(ChannelHandlerContext ctx, InventoryEntity inventory)
/*    */   {
/* 78 */     SmartServer.sendMessage(ctx, new String[] { "lastinventory", inventory == null ? "" : inventory.asInventory().serialize() });
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdLastInventory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */