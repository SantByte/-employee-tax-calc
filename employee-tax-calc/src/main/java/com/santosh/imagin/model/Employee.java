package com.santosh.imagin.model;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Employee {
	@NotBlank(message = "Employee Id is mandatory")
	private String employeeId;

	@NotBlank(message = "First name is mandatory")
	private String firstName;

	@NotBlank(message = "Last name is mandatory")
	private String lastName;

	@NotBlank(message = "Email id should not be blanc")
	@Email(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", message = "Email is not valid")
	private String email;

	@NotEmpty(message = "Phone numbers list cannot be empty")
	@Size(min = 1, max = 2, message = "Only two phone numbers are allowed")
	private List<String> phoneNumbers;

	@NotBlank(message = "DateOfJoining is mandatory")
	private String dateOfJoining;

	@Min(value = 0, message = "Salary must be greater than or equal to 0")
	private double salary;

	private int lossOfPayDays;

}
