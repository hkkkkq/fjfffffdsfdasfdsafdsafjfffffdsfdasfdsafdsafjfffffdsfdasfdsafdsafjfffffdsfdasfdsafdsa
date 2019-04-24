package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmployeeControllerAspect {
	
	@Before(value = "execution(* com.example.demo.controller.EmployeeController.listEmployee*(..))")
	public void beforeAdvice(JoinPoint joinPoint) {
		System.out.println("[EmployeeControllerAspect.beforeAdvice] method:" + joinPoint.getSignature());
	}
	
	@After(value = "execution(* com.example.demo.controller.EmployeeController.listEmployee*(..))")
	public void afterAdvice2(JoinPoint joinPoint) {
		System.out.println("[EmployeeControllerAspect.afterAdvice] method:" + joinPoint.getSignature());
	}

}
