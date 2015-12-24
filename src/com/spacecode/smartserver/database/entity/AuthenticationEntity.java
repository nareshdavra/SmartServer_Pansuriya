/*    */ package com.spacecode.smartserver.database.entity;
/*    */ 
/*    */ import com.j256.ormlite.field.DatabaseField;
/*    */ import com.j256.ormlite.table.DatabaseTable;
/*    */ import com.spacecode.sdk.device.data.DoorInfo;
import com.spacecode.smartserver.database.dao.DaoAuthentication;
/*    */ import java.util.Date;
/*    */ 

/*    */ @DatabaseTable(tableName="sc_authentication", daoClass=DaoAuthentication.class)
/*    */ public final class AuthenticationEntity
/*    */   extends Entity
/*    */ {
/*    */   public static final String TABLE_NAME = "sc_authentication";
/*    */   public static final String DEVICE_ID = "device_id";
/*    */   public static final String USER_ID = "user_id";
/*    */   public static final String ACCESS_TYPE_ID = "access_type_id";
/*    */   public static final String CREATED_AT = "created_at";
           public static final String DOOR_USED = "door_used";
           public static final String INVENTORY_ID = "inventory_id";
/*    */   @DatabaseField(foreign=true, columnName="device_id", canBeNull=false)
/*    */   private DeviceEntity _device;
/*    */   @DatabaseField(foreign=true, columnName="user_id", canBeNull=false, foreignAutoRefresh=true)
/*    */   private UserEntity _user;
/*    */   @DatabaseField(foreign=true, columnName="access_type_id", canBeNull=false, foreignAutoRefresh=true)
/*    */   private AccessTypeEntity _accessType;
/*    */   @DatabaseField(columnName="created_at", index=true)
/*    */   private Date _createdAt;
           @DatabaseField(foreign = true,columnName="inventory_id",canBeNull = false, foreignAutoRefresh=true)
           private InventoryEntity _inventory;
           @DatabaseField(columnName = "door_used",canBeNull = false)
           private DoorInfo _door_used = DoorInfo.DI_NO_DOOR;
/*    */   
/*    */   AuthenticationEntity() {}
/*    */

/*    */   public AuthenticationEntity(DeviceEntity device, UserEntity gte, AccessTypeEntity ate,InventoryEntity inv,DoorInfo di)
/*    */   {
/* 49 */     this._device = device;
/* 50 */     this._user = gte;
/* 51 */     this._accessType = ate;
/* 52 */     this._createdAt = new Date();
             this._inventory = inv;
             this._door_used = di;
/*    */   }

/*    */   public DeviceEntity getDevice()
/*    */   {
/* 58 */     return this._device;
/*    */   }
/*    */   
/*    */ 
/*    */   public UserEntity getUser()
/*    */   {
/* 64 */     return this._user;
/*    */   }
/*    */   
/*    */ 
/*    */   public AccessTypeEntity getAccessType()
/*    */   {
/* 70 */     return this._accessType;
/*    */   }
/*    */   
/*    */ 
/*    */   public Date getCreatedAt()
/*    */   {
/* 76 */     return new Date(this._createdAt.getTime());
/*    */   }

           public InventoryEntity get_inventory()
           {
               return this._inventory;
           }
            public DoorInfo getDoorUsed()
            {
                return this._door_used;
            }
/*    */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\AuthenticationEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */