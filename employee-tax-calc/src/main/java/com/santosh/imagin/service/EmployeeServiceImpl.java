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
		return employee != null && isValidString(employee.getEmployeeId()) && isValidString(employee.getFirstName())
				&& isValidString(employee.getLastName()) && isValidEmail(employee.getEmail())
				&& employee.getPhoneNumbers() != null && employee.getPhoneNumbers().isEmpty()
				&& isValidString(employee.getDateOfJoining()) && employee.getSalary() > 0;
	}

	private boolean isValidEmail(String email) {
		return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
	}


	private boolean isValidString(String str) {
		return str != null && !str.isEmpty();
	}

}
