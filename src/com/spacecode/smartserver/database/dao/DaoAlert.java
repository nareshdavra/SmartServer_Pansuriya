/*     */ package com.spacecode.smartserver.database.dao;
/*     */ 
/*     */ import com.j256.ormlite.stmt.QueryBuilder;
/*     */ import com.j256.ormlite.stmt.Where;
/*     */ import com.j256.ormlite.support.ConnectionSource;
/*     */ import com.spacecode.sdk.network.alert.Alert;
/*     */ import com.spacecode.sdk.network.alert.AlertTemperature;
/*     */ import com.spacecode.sdk.network.alert.AlertType;
/*     */ import com.spacecode.smartserver.database.DbManager;
/*     */ import com.spacecode.smartserver.database.entity.AlertEntity;
/*     */ import com.spacecode.smartserver.database.entity.AlertTemperatureEntity;
/*     */ import com.spacecode.smartserver.database.entity.AlertTypeEntity;
/*     */ import com.spacecode.smartserver.database.entity.DeviceEntity;
/*     */ import com.spacecode.smartserver.helper.SmartLogger;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ public class DaoAlert
/*     */   extends DaoEntity<AlertEntity, Integer>
/*     */ {
/*     */   public DaoAlert(ConnectionSource connectionSource) throws SQLException
/*     */   {
/*  25 */     super(connectionSource, AlertEntity.class);
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
/*     */   public boolean deleteEntity(AlertEntity entity)
/*     */   {
/*  38 */     if (entity == null)
/*     */     {
/*  40 */       return false;
/*     */     }
/*     */     
/*     */ 
/*  44 */     if (DaoAlertType.asAlertType(entity.getAlertType()) == AlertType.TEMPERATURE)
/*     */     {
/*  46 */       DaoAlertTemperature atRepo = (DaoAlertTemperature)DbManager.getDao(AlertTemperatureEntity.class);
/*  47 */       AlertTemperatureEntity ate = (AlertTemperatureEntity)atRepo.getEntityBy("alert_id", Integer.valueOf(entity.getId()));
/*     */       
/*  49 */       atRepo.deleteEntity(ate);
/*     */     }
/*     */     
/*     */ 
/*  53 */     return super.deleteEntity(entity);
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
/*     */   public List<AlertEntity> getEnabledAlerts(AlertTypeEntity ate)
/*     */   {
/*  66 */     if (ate == null)
/*     */     {
/*  68 */       return new ArrayList();
/*     */     }
/*     */     
/*     */     try
/*     */     {
/*  73 */       return query(queryBuilder().where().eq("alert_type_id", Integer.valueOf(ate.getId())).and().eq("device_id", Integer.valueOf(DbManager.getDevEntity().getId())).and().eq("enabled", Boolean.valueOf(true)).prepare());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  85 */       SmartLogger.getLogger().log(Level.SEVERE, "Unable to get enabled alerts id.", sqle); }
/*  86 */     return new ArrayList();
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
/*     */   public boolean persist(Alert alert)
/*     */   {
/*  99 */     DaoAlertType daoAlertType = (DaoAlertType)DbManager.getDao(AlertTypeEntity.class);
/*     */     
/* 101 */     AlertTypeEntity ate = daoAlertType.fromAlertType(alert.getType());
/*     */     
/* 103 */     if (ate == null)
/*     */     {
/* 105 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 109 */     AlertEntity newAlertEntity = new AlertEntity(ate, alert);
/*     */     
/* 111 */     if (alert.getId() == 0)
/*     */     {
/* 113 */       if (!insert(newAlertEntity))
/*     */       {
/*     */ 
/* 116 */         return false;
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */     }
/* 122 */     else if (!updateEntity(newAlertEntity))
/*     */     {
/*     */ 
/* 125 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 129 */     if (alert.getType() != AlertType.TEMPERATURE)
/*     */     {
/*     */ 
/* 132 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 136 */     if (!(alert instanceof AlertTemperature))
/*     */     {
/* 138 */       SmartLogger.getLogger().severe("Trying to persist an Alert as an AlertTemperature whereas it is not.");
/* 139 */       return false;
/*     */     }
/*     */     
/* 142 */     AlertTemperature alertTemperature = (AlertTemperature)alert;
/*     */     
/* 144 */     DaoAlertTemperature daoAlertTemp = (DaoAlertTemperature)DbManager.getDao(AlertTemperatureEntity.class);
/*     */     
/*     */ 
/* 147 */     if (alert.getId() != 0)
/*     */     {
/* 149 */       AlertTemperatureEntity atEntity = (AlertTemperatureEntity)daoAlertTemp.getEntityBy("alert_id", Integer.valueOf(alert.getId()));
/*     */       
/* 151 */       if (atEntity == null)
/*     */       {
/* 153 */         return false;
/*     */       }
/*     */       
/* 156 */       atEntity.setTemperatureMin(alertTemperature.getTemperatureMin());
/* 157 */       atEntity.setTemperatureMax(alertTemperature.getTemperatureMax());
/* 158 */       return daoAlertTemp.updateEntity(atEntity);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 164 */     return daoAlertTemp.insert(new AlertTemperatureEntity(newAlertEntity, alertTemperature));
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
/*     */   public boolean deleteFromAlert(Alert alert)
/*     */   {
/* 177 */     if ((alert == null) || (alert.getId() == 0))
/*     */     {
/* 179 */       return false;
/*     */     }
/*     */     
/* 182 */     AlertEntity gue = (AlertEntity)getEntityById(Integer.valueOf(alert.getId()));
/*     */     
/* 184 */     return (gue != null) && (deleteEntity(gue));
/*     */   }
/*     */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoAlert.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */