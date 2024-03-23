package com.santosh.imagin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.santosh.imagin.model.Employee;
import com.santosh.imagin.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/employees")
	public ResponseEntity<String> storeEmpDetails(@RequestBody @Valid Employee emp) {
		if (employeeService.validateAndSave(emp)) {
			return ResponseEntity.ok("Employee Details are saved successfully");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid employee data");
		}
	}
}
