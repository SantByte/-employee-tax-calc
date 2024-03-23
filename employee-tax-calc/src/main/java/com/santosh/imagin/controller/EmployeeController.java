package com.santosh.imagin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.santosh.imagin.exception.DuplicateEmployeeIdException;
import com.santosh.imagin.exception.EmployeeNotFoundException;
import com.santosh.imagin.model.EmpDetails;
import com.santosh.imagin.model.Employee;
import com.santosh.imagin.model.TaxDetails;
import com.santosh.imagin.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Validated
	@PostMapping("/employees")
	public ResponseEntity<String> storeEmpDetails(@Valid @RequestBody Employee emp) {
		try {
			String result = employeeService.validateAndSave(emp);
			return ResponseEntity.ok(result);
		} catch (DuplicateEmployeeIdException employeeIdException) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate Employee ID");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}

	@Validated
	@PostMapping("/tax")
	public ResponseEntity<?> calculateTaxDeduction(@Valid @RequestBody EmpDetails emp) {
		int employeeId = emp.getEmployeeId();
		try {
			TaxDetails taxDetails = employeeService.calculateTaxDeduction(employeeId);
			return ResponseEntity.ok(taxDetails);
		} catch (EmployeeNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}
}
