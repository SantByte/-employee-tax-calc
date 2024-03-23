package com.santosh.imagin.service;

import org.springframework.stereotype.Service;

import com.santosh.imagin.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Override
	public boolean validateAndSave(Employee employee) {
		if (isValid(employee)) {
			return true;
		}
		return false;
	}

	private boolean isValid(Employee employee) {
		return true;
	}


}
