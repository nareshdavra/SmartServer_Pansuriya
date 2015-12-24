/*     */ package com.spacecode.smartserver.database.entity;
/*     */ 
/*     */ import com.j256.ormlite.dao.ForeignCollection;
/*     */ import com.j256.ormlite.field.DatabaseField;
/*     */ import com.j256.ormlite.field.ForeignCollectionField;
/*     */ import com.j256.ormlite.table.DatabaseTable;
/*     */ import com.spacecode.sdk.device.data.DoorInfo;
import com.spacecode.sdk.device.data.Inventory;
/*     */ import com.spacecode.smartserver.database.DbManager;
/*     */ import com.spacecode.smartserver.database.dao.DaoAccessType;
/*     */ import com.spacecode.smartserver.database.dao.DaoInventory;
import com.spacecode.smartserver.helper.SmartLogger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;

/*     */ @DatabaseTable(tableName="sc_inventory", daoClass=DaoInventory.class)
/*     */ public final class InventoryEntity
/*     */   extends Entity
/*     */ {
/*     */   public static final String TABLE_NAME = "sc_inventory";
/*     */   public static final String DEVICE_ID = "device_id";
/*     */   public static final String USER_ID = "user_id";
/*     */   public static final String ACCESS_TYPE_ID = "access_type_id";
/*     */   public static final String TOTAL_ADDED = "total_added";
/*     */   public static final String TOTAL_PRESENT = "total_present";
/*     */   public static final String TOTAL_REMOVED = "total_removed";
/*     */   public static final String CREATED_AT = "created_at";
/*     */   @DatabaseField(foreign=true, columnName="device_id", canBeNull=false)
/*     */   private DeviceEntity _device;
/*     */   @DatabaseField(foreign=true, columnName="user_id", foreignAutoRefresh=true)
/*     */   private UserEntity _user;
/*     */   @DatabaseField(foreign=true, columnName="access_type_id", canBeNull=false, foreignAutoRefresh=true)
/*     */   private AccessTypeEntity _accessType;
/*     */   @DatabaseField(columnName="total_added", canBeNull=false)
/*     */   private int _totalAdded;
/*     */   @DatabaseField(columnName="total_present", canBeNull=false)
/*     */   private int _totalPresent;
/*     */   @DatabaseField(columnName="total_removed", canBeNull=false)
/*     */   private int _totalRemoved;
/*     */   @DatabaseField(columnName="created_at", index=true)
/*     */   private Date _createdAt;
/*     */   @DatabaseField(columnName="door_number", canBeNull = false, defaultValue="-1")
/*     */   private byte _doorUsed;
            @ForeignCollectionField(eager=true)

/*     */   private ForeignCollection<InventoryRfidTag> _rfidTags;
/*     */   
/*     */   InventoryEntity() {}
/*     */   
/*     */   public InventoryEntity(Inventory inventory, UserEntity gue, AccessTypeEntity ate,byte doorUsed)
/*     */   {
/*  72 */     this._device = DbManager.getDevEntity();
/*  73 */     this._user = gue;
/*  74 */     this._accessType = ate;
/*     */     this._doorUsed = doorUsed;
/*  76 */     this._totalAdded = inventory.getNumberAdded();
/*  77 */     this._totalPresent = inventory.getNumberPresent();
/*  78 */     this._totalRemoved = inventory.getNumberRemoved();
/*     */     
/*  80 */     this._createdAt = inventory.getCreationDate();
/*     */   }
/*     */   
/*     */ 
/*     */   public DeviceEntity getDevice()
/*     */   {
/*  86 */     return this._device;
/*     */   }
/*     */   
/*     */ 
/*     */   public UserEntity getUser()
/*     */   {
/*  92 */     return this._user;
/*     */   }
/*     */   
/*     */ 
/*     */   public AccessTypeEntity getAccessType()
/*     */   {
/*  98 */     return this._accessType;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTotalAdded()
/*     */   {
/* 104 */     return this._totalAdded;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTotalPresent()
/*     */   {
/* 110 */     return this._totalPresent;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTotalRemoved()
/*     */   {
/* 116 */     return this._totalRemoved;
/*     */   }
/*     */   
/*     */ 
/*     */   public Date getCreatedAt()
/*     */   {
/* 122 */     return new Date(this._createdAt.getTime());
/*     */   }
/*     */   
/*     */ 
/*     */   public ForeignCollection<InventoryRfidTag> getRfidTags()
/*     */   {
/* 128 */     return this._rfidTags;
/*     */   }
/*     */   

/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Inventory asInventory()
/*     */   {
/* 138 */     List<String> tagsAdded = new ArrayList();
/* 139 */     List<String> tagsPresent = new ArrayList();
/* 140 */     List<String> tagsRemoved = new ArrayList();
/* 142 */     for (InventoryRfidTag irtEntity : this._rfidTags)
/*     */     {
/* 144 */       switch (irtEntity.getMovement())
/*     */       {
/*     */       case 1: 
/* 147 */         tagsAdded.add(irtEntity.getRfidTag().getUid());
/* 148 */         break;
/*     */
/*     */       case 0: 
/* 151 */         tagsPresent.add(irtEntity.getRfidTag().getUid());
/* 152 */         break;
/*     */       
/*     */       case -1: 
/* 155 */         tagsRemoved.add(irtEntity.getRfidTag().getUid());
/*     */       }
/*     */     }
/* 163 */     return new Inventory(this._id, tagsAdded, tagsPresent, tagsRemoved, this._user != null ? this._user.getUsername() : "", DaoAccessType.asAccessType(this._accessType), this._createdAt,_doorUsed);
/*     */   }

    public DoorInfo getDoorUsed() {
        return  (_doorUsed==0)? DoorInfo.DI_MASTER_DOOR:DoorInfo.DI_SLAVE_DOOR;
    }

    public void setDoorUsed(byte _doorUsed) {
        this._doorUsed = _doorUsed;
    }

/*     */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\InventoryEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */