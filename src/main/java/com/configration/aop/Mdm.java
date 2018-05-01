package com.configration.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.configration.annotation.MDM;

@Aspect
public class Mdm {
	
	private static final Logger log = LoggerFactory.getLogger(Mdm.class);
	@Pointcut("execution(* org.springframework.web.client.RestOperations+.*(..))") //织入
	public void mdm(){}
	
	@Before("mdm()")
	public void before(JoinPoint point) {
		Method md = ((MethodSignature) point.getSignature()).getMethod();
		String classNameAndMethod = md.getDeclaringClass().getSimpleName() + "." + md.getName();
		
		log.info(classNameAndMethod);
		
		Object[] args = point.getArgs();
		
		
		for (Object object : args) {
		//	AnnotatedElementUtils.isAnnotated(object, MDM.class);
		//	.findAnnotation(object, MDM.class);
			if(object instanceof Set){
				 Set<Class<?>> classes = (Set<Class<?>>) object;
				 
				 for (Class clazz : classes) {
                     for (Annotation annotation : clazz.getDeclaredAnnotations()) {
                         if (annotation.annotationType().isAssignableFrom(MDM.class)) {
                        	 MDM mdm=clazz.getClass().getAnnotation(MDM.class);
         					if(null!=mdm){
         						System.out.println(mdm.routeKey());	
         					}
                         }
                     }
                 }
				 
				 
			}else if(object instanceof List){
				List<Class<?>> classes = (List<Class<?>>) object;
				for (Class clazz : classes) {
                    for (Annotation annotation : clazz.getDeclaredAnnotations()) {
                        if (annotation.annotationType().isAssignableFrom(MDM.class)) {
                       	 MDM mdm=clazz.getClass().getAnnotation(MDM.class);
        					if(null!=mdm){
        						System.out.println(mdm.routeKey());	
        					}
                        }
                    }
                }
			}else if(object instanceof Object[]){
				Object[] chlids=(Object[])object;
				for (Object childObject : chlids) {
					//Annotation[] annotations=childObject.getClass().getFields().getClass().getAnnotations();
					Field[] fileds=childObject.getClass().getFields();
					for (Field field : fileds) {
						field.setAccessible(true);
						MDM mdm=field.getAnnotation(MDM.class);
						if(null!=mdm){
							try {
								System.out.println(mdm.routeKey());
								System.out.println(field.getName());
								System.out.println(field.get(childObject));
								field.set(childObject, "008");
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
			else{
				MDM mdm=object.getClass().getAnnotation(MDM.class);
				if(null!=mdm){
					System.out.println(mdm.routeKey());	
				}
			}
			
		}
	}
	
}
