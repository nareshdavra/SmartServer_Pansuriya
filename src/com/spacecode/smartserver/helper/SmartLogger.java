/*     */ package com.spacecode.smartserver.helper;
/*     */ 
/*     */ import com.spacecode.smartserver.SmartServer;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.logging.ConsoleHandler;
/*     */ import java.util.logging.FileHandler;
/*     */ import java.util.logging.Formatter;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogManager;
/*     */ import java.util.logging.LogRecord;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public final class SmartLogger extends Logger
/*     */ {
/*     */   private static final String LOG_FILENAME = "smartserver.log";
/*  18 */   private static final String LOG_FILE = SmartServer.getWorkingDirectory() + "smartserver.log";
/*     */   
/*     */ 
/*  21 */   private static final SmartLogger LOGGER = new SmartLogger();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SmartLogger()
/*     */     throws MissingResourceException
/*     */   {
/*  34 */     super("SmartLogger", null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void initialize()
/*     */   {
/*     */     try
/*     */     {
/*  44 */       LogManager.getLogManager().reset();
/*     */       
/*  46 */       ConsoleHandler consoleHandler = new SmartConsoleHandler();
/*  47 */       FileHandler fileHandler = new FileHandler(LOG_FILE, true);
/*     */       
/*  49 */       ShortFormatter formatter = new ShortFormatter();
/*     */       
/*  51 */       fileHandler.setLevel(Level.WARNING);
/*  52 */       fileHandler.setFormatter(formatter);
/*     */       
/*  54 */       consoleHandler.setLevel(Level.INFO);
/*  55 */       consoleHandler.setFormatter(formatter);
/*     */       
/*  57 */       LOGGER.addHandler(fileHandler);
/*  58 */       LOGGER.addHandler(consoleHandler);
/*     */     }
/*     */     catch (java.io.IOException|SecurityException e) {
/*  61 */       LOGGER.log(Level.SEVERE, "Unable to initialize SmartLogger.", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static SmartLogger getLogger()
/*     */   {
/*  68 */     return LOGGER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static class ShortFormatter
/*     */     extends Formatter
/*     */   {
/*  76 */     private final DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String format(LogRecord record)
/*     */     {
/*  86 */       StringBuilder builder = new StringBuilder();
/*  87 */       builder.append(this.df.format(new Date(record.getMillis()))).append(" ");
/*  88 */       builder.append("[").append(record.getLevel()).append("] ");
/*  89 */       builder.append(formatMessage(record));
/*  90 */       builder.append("\n");
/*     */       
/*  92 */       if (record.getThrown() != null)
/*     */       {
/*  94 */         builder.append(record.getThrown().getMessage());
/*  95 */         builder.append("\n");
/*     */       }
/*     */       
/*  98 */       return builder.toString();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static class SmartConsoleHandler
/*     */     extends ConsoleHandler
/*     */   {
/*     */     public SmartConsoleHandler()
/*     */     {
/* 109 */       super.setOutputStream(System.out);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\helper\SmartLogger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */