package com.santosh.imagin.model;

import lombok.Data;

@Data
public class TaxDetails {

	private String employeeId;
	private String firstName;
	private String lastName;
	private double totalSalary;
	private double taxAmount;
	private double cessAmount;

	public TaxDetails(String employeeId, String firstName, String lastName, double totalSalary, double taxAmount,
			double cessAmount) {
	}

}
