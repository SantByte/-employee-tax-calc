package com.santosh.imagin.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santosh.imagin.exception.DuplicateEmployeeIdException;
import com.santosh.imagin.exception.EmployeeNotFoundException;
import com.santosh.imagin.model.Employee;
import com.santosh.imagin.model.TaxDetails;
import com.santosh.imagin.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public String validateAndSave(Employee employee) throws DuplicateEmployeeIdException {
		if (employeeRepository.existsById(employee.getEmployeeId())) {
			throw new DuplicateEmployeeIdException();
		}
		employeeRepository.save(employee);
		return "Employee details stored successfully";
	}

	@Override
	public TaxDetails calculateTaxDeduction(int employeeId) throws EmployeeNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + employeeId + " not found"));

		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH);

		calendar.setTime(employee.getDateOfJoining());
		int dojYear = calendar.get(Calendar.YEAR);
		int dojMonth = calendar.get(Calendar.MONTH);

		int monthsWorked = (currentYear - dojYear) * 12 + (currentMonth - dojMonth) + 1;
		double yearlySalary = employee.getSalary() * monthsWorked;

		double taxAmount = calculateTaxAmount(yearlySalary);
		double cessAmount = calculateCessAmount(yearlySalary);

		TaxDetails taxDetails = new TaxDetails();
		taxDetails.setEmployeeId(employee.getEmployeeId());
		taxDetails.setFirstName(employee.getFirstName());
		taxDetails.setLastName(employee.getLastName());
		taxDetails.setTotalSalary(yearlySalary);
		taxDetails.setTaxAmount(taxAmount);
		taxDetails.setCessAmount(cessAmount);

		return taxDetails;
	}

	private double calculateTaxAmount(double yearlySalary) {
		double taxAmount = 0;

		if (yearlySalary > 1000000) {
			taxAmount += (yearlySalary - 1000000) * 0.2;
			yearlySalary = 1000000;
		}
		if (yearlySalary > 500000) {
			taxAmount += (yearlySalary - 500000) * 0.1;
			yearlySalary = 500000;
		}
		if (yearlySalary > 250000) {
			taxAmount += (yearlySalary - 250000) * 0.05;
		}

		return taxAmount;
	}

	private double calculateCessAmount(double yearlySalary) {
		if (yearlySalary > 2500000) {
			return 0.02 * (yearlySalary - 2500000);
		}
		return 0;
	}

}
