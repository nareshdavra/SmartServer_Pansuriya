package com.spacecode.smartserver.command;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
@Inherited
@interface CommandContract
{
  int paramCount() default 0;
  
  boolean strictCount() default false;
  
  boolean deviceRequired() default false;
  
  boolean adminRequired() default false;
  
  String responseIfInvalid() default "false";
  
  boolean noResponseWhenInvalid() default false;
  
  boolean respondToAllIfInvalid() default false;
}


/* Location:              C:\Users\MY\Desktop\Pansuriya SAS\SmartServer\SmartServer.jar!\com\spacecode\smartserver\command\CommandContract.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */