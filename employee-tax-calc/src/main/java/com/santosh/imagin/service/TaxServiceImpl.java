package com.santosh.imagin.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.santosh.imagin.model.Employee;
import com.santosh.imagin.model.TaxDetails;

@Service
public class TaxServiceImpl implements TaxService {

	private List<Employee> employees = new ArrayList<>();
	private List<TaxDetails> taxDetailsList = new ArrayList<>();

	@Override
	public List<TaxDetails> calculateTaxDeduction() {
		int currentFinancialYear = getCurrentFinancialYear();
		for (Employee employee : employees) {
			double yearlySalary = calculateYearlySalary(employee, currentFinancialYear);
			double taxAmount = calculateTaxAmount(yearlySalary);
			double cessAmount = calculateCessAmount(yearlySalary);
			taxDetailsList.add(new TaxDetails(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
					yearlySalary, taxAmount, cessAmount));
		}
		return taxDetailsList;
	}

	private int getCurrentFinancialYear() {
		LocalDate currentDate = LocalDate.now();
		int year = currentDate.getYear();
		int month = currentDate.getMonthValue();
		if (month < 4) {
			year--; // If the current month is before April, consider the previous year
		}
		return year;
	}

	private double calculateYearlySalary(Employee employee, int currentFinancialYear) {
		LocalDate doj = LocalDate.parse(employee.getDateOfJoining());
		int joiningYear = doj.getYear();
		int monthsWorked = 12 - doj.getMonthValue() + 1;
		if (joiningYear == currentFinancialYear) {
			monthsWorked = monthsWorked - (12 - LocalDate.now().getMonthValue() + 1);
		}
		double totalSalary = employee.getSalary() * monthsWorked;
		totalSalary -= (employee.getSalary() / 30) * employee.getLossOfPayDays();
		return totalSalary;
	}

	private double calculateTaxAmount(double yearlySalary) {
		double taxAmount = 0;
		if (yearlySalary > 250000 && yearlySalary <= 500000) {
			taxAmount = (yearlySalary - 250000) * 0.05;
		} else if (yearlySalary > 500000 && yearlySalary <= 1000000) {
			taxAmount = 12500 + (yearlySalary - 500000) * 0.1;
		} else if (yearlySalary > 1000000) {
			taxAmount = 62500 + (yearlySalary - 1000000) * 0.2;
		}
		return taxAmount;
	}

	private double calculateCessAmount(double yearlySalary) {
		if (yearlySalary > 2500000) {
			return (yearlySalary - 2500000) * 0.02;
		}
		return 0;
	}

}
