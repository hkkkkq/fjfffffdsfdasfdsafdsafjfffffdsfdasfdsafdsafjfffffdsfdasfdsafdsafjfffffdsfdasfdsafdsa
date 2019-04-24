package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;

@Service
public class EmployeeService {

	public Employee createEmployee(String name, String empId) {

		Employee emp = new Employee();
		emp.setName(name);
		emp.setEmpId(empId);
		
		System.out.println("[EmployeeService.createEmployee] Employee created");
		return emp;
	}

	public void deleteEmployee(String empId) {
		System.out.println("[EmployeeService.deleteEmployee] Employee deleted");
	}
}
