package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/add/employee", method = RequestMethod.GET)
	public Employee addEmployee(@RequestParam("name") String name, @RequestParam("empId") String empId) {
		/*
		 * localhost:8080/add/employee?empId=1&name=test
		 */
		System.out.println("[addEmployee] name["+name+"], empId["+empId+"]");
		return employeeService.createEmployee(name, empId);
	}

	@RequestMapping(value = "/remove/employee", method = RequestMethod.GET)
	public String removeEmployee( @RequestParam("empId") String empId) {
		/*
		 * localhost:8080/remove/employee?empId=2
		 */
		System.out.println("[removeEmployee] empId["+empId+"]");
		employeeService.deleteEmployee(empId);

		return "Employee removed";
	}

	@RequestMapping(value = "/list/employee", method = RequestMethod.GET)
	public String listEmployee() {
		/*
		 * localhost:8080/list/employee
		 */
		System.out.println("[listEmployee] ");
		return "listEmployee";
	}

}
