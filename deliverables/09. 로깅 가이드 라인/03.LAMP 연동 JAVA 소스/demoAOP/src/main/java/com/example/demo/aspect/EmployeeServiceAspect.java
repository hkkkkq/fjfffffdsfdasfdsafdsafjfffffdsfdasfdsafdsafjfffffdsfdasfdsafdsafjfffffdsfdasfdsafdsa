package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmployeeServiceAspect {

	@Before(value = "execution(* com.example.demo.service.EmployeeService.*(..)) and args(name,empId)")
	public void beforeAdvice(JoinPoint joinPoint, String name, String empId) {
		System.out.println("[beforeAdvice] method:" + joinPoint.getSignature());
		System.out.println("[beforeAdvice] Creating Employee with name - " + name + " and id - " + empId);
	}

	@After(value = "execution(* com.example.demo.service.EmployeeService.*(..)) and args(name,empId)")
	public void afterAdvice(JoinPoint joinPoint, String name, String empId) {
		System.out.println("[afterAdvice] method:" + joinPoint.getSignature());
		System.out.println("[afterAdvice] Successfully created Employee with name - " + name + " and id - " + empId);
	}
}
