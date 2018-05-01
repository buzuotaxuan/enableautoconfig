package com.configration.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.METHOD,ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MDM {
	public boolean route() default true;
	
	public String routeKey() default "";
}
