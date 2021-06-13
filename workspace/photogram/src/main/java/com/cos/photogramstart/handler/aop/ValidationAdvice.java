package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

@Aspect
@Component
public class ValidationAdvice {

	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
	public Object apiAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		
		// ProceedingJoinPoint : 해당 메서드가 들어온다.
		System.out.println("web api controller ===============================");
		Object[] args = joinPoint.getArgs();
		for(Object arg : args) {
			if(arg instanceof BindingResult) {
				System.out.println("유효성 검사를 하는 함수입니다.");
				BindingResult bindingResult = (BindingResult) arg;
				if(bindingResult.hasErrors()) {
					Map<String, String> errMap = new HashMap<>();
					for(FieldError error : bindingResult.getFieldErrors()) {
						errMap.put(error.getField(), error.getDefaultMessage());
						System.out.println(error.getDefaultMessage());
					}
					throw new CustomValidationApiException("유효성 검사 실패", errMap);
				}
			}
		}
		
		return joinPoint.proceed();
	}
	
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {
		
		System.out.println("web controller ===============================");
		Object[] args = joinPoint.getArgs();
		for(Object arg : args) {
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				if(bindingResult.hasErrors()) {
					Map<String, String> errMap = new HashMap<>();
					for(FieldError error : bindingResult.getFieldErrors()) {
						errMap.put(error.getField(), error.getDefaultMessage());
						System.out.println(error.getDefaultMessage());
					}
					throw new CustomValidationException("유효성 검사 실패", errMap);
				}
			}
		}
		
		return joinPoint.proceed();
	}
}
