/*    */ package com.spacecode.smartserver.command;
/*    */ import com.spacecode.sdk.device.data.Inventory;
/*    */ import com.spacecode.smartserver.SmartServer;
/*    */ import com.spacecode.smartserver.database.DbManager;
/*    */ import com.spacecode.smartserver.database.dao.DaoEntity;
/*    */ import com.spacecode.smartserver.database.entity.AuthenticationEntity;

import com.spacecode.smartserver.database.entity.InventoryEntity;
/*    */ import com.spacecode.smartserver.database.entity.InventoryWithDoor;
import io.netty.channel.ChannelHandlerContext;

/*    */ @CommandContract(paramCount=1, strictCount=true, deviceRequired=true, responseIfInvalid="")
/*    */ public class CmdInventoryById
/*    */   extends ClientCommand
/*    */ {
/*    */   public void execute(ChannelHandlerContext ctx, String[] parameters)
/*    */   {
/* 25 */     String inventoryId = parameters[0];
/*    */     
/*    */     try
/*    */     {
/* 29 */       int id = Integer.parseInt(inventoryId);
/* 30 */       InventoryEntity invEntity = (InventoryEntity)DbManager.getDao(InventoryEntity.class).getEntityById(Integer.valueOf(id));
/*    */
               if (invEntity == null)
/*    */       {
/* 34 */         SmartServer.sendMessage(ctx, new String[] { "inventorybyid", "" });
/* 35 */         return;
/*    */       }
/*    */       
/* 38 */       SmartServer.sendMessage(ctx, new String[] { "inventorybyid", invEntity.asInventory().serialize() });
/*    */     }
/*    */     catch (NumberFormatException nfe) {
/* 41 */       SmartServer.sendMessage(ctx, new String[] { "inventorybyid", "" });
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CmdInventoryById.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */