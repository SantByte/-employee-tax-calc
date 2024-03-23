package com.santosh.imagin.model;

import java.util.List;

import lombok.Data;

@Data
public class Employee {
	private String employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> phoneNumbers;
	private String dateOfJoining;
	private double salary;
	private int lossOfPayDays;

	
}
