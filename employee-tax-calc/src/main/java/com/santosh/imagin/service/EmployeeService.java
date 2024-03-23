package com.santosh.imagin.service;

import com.santosh.imagin.exception.DuplicateEmployeeIdException;
import com.santosh.imagin.exception.EmployeeNotFoundException;
import com.santosh.imagin.model.Employee;
import com.santosh.imagin.model.TaxDetails;

public interface EmployeeService {
	String validateAndSave(Employee employee) throws DuplicateEmployeeIdException;
	
	TaxDetails calculateTaxDeduction(int employeeId) throws EmployeeNotFoundException;

}
