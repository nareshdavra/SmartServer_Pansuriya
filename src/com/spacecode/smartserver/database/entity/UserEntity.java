/*     */ package com.spacecode.smartserver.database.entity;
/*     */ 
/*     */ import com.j256.ormlite.dao.ForeignCollection;
/*     */ import com.j256.ormlite.field.DatabaseField;
/*     */ import com.j256.ormlite.field.ForeignCollectionField;
/*     */ import com.j256.ormlite.table.DatabaseTable;
/*     */ import com.spacecode.sdk.user.User;
/*     */ import com.spacecode.smartserver.database.dao.DaoUser;
/*     */ import java.util.Date;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @DatabaseTable(tableName="sc_user", daoClass=DaoUser.class)
/*     */ public final class UserEntity
/*     */   extends Entity
/*     */ {
/*     */   public static final String TABLE_NAME = "sc_user";
/*     */   public static final String USERNAME = "username";
/*     */   public static final String BADGE_NUMBER = "badge_number";
/*     */   public static final String THIEF_FINGER_INDEX = "thief_finger_index";
/*     */   public static final String UPDATED_AT = "updated_at";
/*     */   @DatabaseField(columnName="username", canBeNull=false, unique=true)
/*     */   private String _username;
/*     */   @DatabaseField(columnName="badge_number")
/*     */   private String _badgeNumber;
/*     */   @DatabaseField(columnName="thief_finger_index")
/*     */   private Integer _thiefFingerIndex;
/*     */   @DatabaseField(columnName="updated_at", version=true)
/*     */   private Date _updatedAt;
/*     */   @ForeignCollectionField(eager=true)
/*     */   private ForeignCollection<FingerprintEntity> _fingerprints;
/*     */   @ForeignCollectionField(eager=false)
/*     */   private ForeignCollection<GrantedAccessEntity> _grantedAccesses;
/*     */   
/*     */   UserEntity() {}
/*     */   
/*     */   public UserEntity(String username, String badgeNumber)
/*     */   {
/*  58 */     this._username = username;
/*  59 */     this._badgeNumber = badgeNumber;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UserEntity(User newUser)
/*     */   {
/*  71 */     this(newUser.getUsername(), newUser.getBadgeNumber());
/*     */   }
/*     */   
/*     */ 
/*     */   public String getUsername()
/*     */   {
/*  77 */     return this._username;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getBadgeNumber()
/*     */   {
/*  83 */     return this._badgeNumber;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBadgeNumber(String badgeNumber)
/*     */   {
/*  93 */     this._badgeNumber = badgeNumber;
/*     */   }
/*     */   
/*     */ 
/*     */   public Date getUpdatedAt()
/*     */   {
/*  99 */     return new Date(this._updatedAt.getTime());
/*     */   }
/*     */   
/*     */ 
/*     */   public Integer getThiefFingerIndex()
/*     */   {
/* 105 */     return this._thiefFingerIndex;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setThiefFingerIndex(Integer thiefFingerIndex)
/*     */   {
/* 115 */     this._thiefFingerIndex = thiefFingerIndex;
/*     */   }
/*     */   
/*     */ 
/*     */   public ForeignCollection<FingerprintEntity> getFingerprints()
/*     */   {
/* 121 */     return this._fingerprints;
/*     */   }
/*     */   
/*     */ 
/*     */   public ForeignCollection<GrantedAccessEntity> getGrantedAccesses()
/*     */   {
/* 127 */     return this._grantedAccesses;
/*     */   }
/*     */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\entity\UserEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */