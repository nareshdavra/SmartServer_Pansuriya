/*     */ package com.spacecode.smartserver.database.dao;
/*     */ 
/*     */ import com.j256.ormlite.dao.BaseDaoImpl;
/*     */ import com.j256.ormlite.stmt.QueryBuilder;
/*     */ import com.j256.ormlite.stmt.Where;
/*     */ import com.j256.ormlite.support.ConnectionSource;
/*     */ import com.spacecode.smartserver.helper.SmartLogger;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ public class DaoEntity<T, ID>
/*     */   extends BaseDaoImpl<T, ID>
/*     */ {
/*     */   protected DaoEntity(ConnectionSource connectionSource, Class<T> dataClass)
/*     */     throws SQLException
/*     */   {
/*  20 */     super(connectionSource, dataClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final T getEntityById(ID value)
/*     */   {
/*     */     try
/*     */     {
/*  34 */       return (T)queryForId(value);
/*     */     }
/*     */     catch (SQLException sqle) {
/*  37 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting entity with Id.", sqle); }
/*  38 */     return null;
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
/*     */   public final T getEntityBy(String field, Object value)
/*     */   {
/*     */     try
/*     */     {
/*  54 */       return (T)queryForFirst(queryBuilder().where().eq(field, value).prepare());
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*     */ 
/*  60 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting entity with criteria.", sqle); }
/*  61 */     return null;
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
/*     */   public final List<T> getEntitiesBy(String field, Object value)
/*     */   {
/*     */     try
/*     */     {
/*  77 */       return query(queryBuilder().where().eq(field, value).prepare());
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*     */ 
/*  83 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting entities with criteria.", sqle); }
/*  84 */     return new ArrayList();
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
/*     */   public final T getEntityWhereNotEqual(String field, Object value)
/*     */   {
/*     */     try
/*     */     {
/* 100 */       return (T)queryForFirst(queryBuilder().where().ne(field, value).prepare());
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*     */ 
/* 106 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting entity where field not equal.", sqle); }
/* 107 */     return null;
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
/*     */   public final List<T> getEntitiesWhereNotEqual(String field, Object value)
/*     */   {
/*     */     try
/*     */     {
/* 123 */       return query(queryBuilder().where().ne(field, value).prepare());
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*     */ 
/* 129 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting entities with criteria.", sqle); }
/* 130 */     return new ArrayList();
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
/*     */   public boolean insert(T newEntity)
/*     */   {
/*     */     try
/*     */     {
/* 145 */       return create(newEntity) == 1;
/*     */     }
/*     */     catch (SQLException sqle) {
/* 148 */       SmartLogger.getLogger().log(Level.SEVERE, "Error occurred while inserting new entity.", sqle); }
/* 149 */     return false;
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
/*     */   public boolean insert(Collection<T> newEntities)
/*     */   {
/* 162 */     for (T entity : newEntities)
/*     */     {
/* 164 */       if (!insert(entity))
/*     */       {
/* 166 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 170 */     return true;
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
/*     */   public boolean updateEntity(T entity)
/*     */   {
/*     */     try
/*     */     {
/* 185 */       return update(entity) > 0;
/*     */     }
/*     */     catch (SQLException sqle) {
/* 188 */       SmartLogger.getLogger().log(Level.SEVERE, "Error occurred while updating entity.", sqle); }
/* 189 */     return false;
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
/*     */   public boolean deleteEntity(T entity)
/*     */   {
/*     */     try
/*     */     {
/* 205 */       return delete(entity) > 0;
/*     */     }
/*     */     catch (SQLException sqle) {
/* 208 */       SmartLogger.getLogger().log(Level.SEVERE, "Error occurred while deleting an entity.", sqle); }
/* 209 */     return false;
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
/*     */   public boolean deleteEntity(Collection<T> entities)
/*     */   {
/* 223 */     if (entities == null)
/*     */     {
/* 225 */       return false;
/*     */     }
/*     */     
/* 228 */     if (entities.isEmpty())
/*     */     {
/* 230 */       return true;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 235 */       return delete(entities) == entities.size();
/*     */     }
/*     */     catch (SQLException sqle) {
/* 238 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while deleting entities.", sqle); }
/* 239 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final List<T> getAll()
/*     */   {
/*     */     try
/*     */     {
/* 248 */       return queryForAll();
/*     */     }
/*     */     catch (SQLException sqle) {
/* 251 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting all entities.", sqle); }
/* 252 */     return new ArrayList();
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
/*     */   public final List<T> getEntitiesWhereIn(String field, Iterable<?> object)
/*     */   {
/* 266 */     List<T> emptyResult = new ArrayList();
/*     */     
/* 268 */     if ((object instanceof Collection))
/*     */     {
/* 270 */       if (((Collection)object).isEmpty())
/*     */       {
/* 272 */         return emptyResult;
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 278 */       return query(queryBuilder().where().in(field, object).prepare());
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*     */ 
/* 285 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting 'IN'.", sqle); }
/* 286 */     return emptyResult;
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
/*     */   public final List<T> getEntitiesWhereNotIn(String field, Iterable<?> object)
/*     */   {
/* 300 */     List<T> emptyResult = new ArrayList();
/*     */     
/* 302 */     if ((object instanceof Collection))
/*     */     {
/* 304 */       if (((Collection)object).isEmpty())
/*     */       {
/* 306 */         return emptyResult;
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 312 */       return query(queryBuilder().where().notIn(field, object).prepare());
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (SQLException sqle)
/*     */     {
/*     */ 
/* 319 */       SmartLogger.getLogger().log(Level.SEVERE, "Exception occurred while getting 'NOT IN'.", sqle); }
/* 320 */     return emptyResult;
/*     */   }
/*     */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\database\dao\DaoEntity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */