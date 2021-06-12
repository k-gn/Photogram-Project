package com.cos.photogramstart.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAdvice {

	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
	public Object apiAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		
		System.out.println("web api controller ===============================");
		
		return joinPoint.proceed();
	}
	
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {
		
		System.out.println("web controller ===============================");
		
		return joinPoint.proceed();
	}
}
