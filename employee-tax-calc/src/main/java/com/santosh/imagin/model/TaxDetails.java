package com.santosh.imagin.model;

import lombok.Data;

@Data
public class TaxDetails {

	private int employeeId;
	private String firstName;
	private String lastName;
	private double totalSalary;
	private double taxAmount;
	private double cessAmount;

}
