package com.santosh.imagin.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Employee {
	@Id
	@NotNull(message = "Employee Id is mandatory")
	private int employeeId;

	@NotBlank(message = "First name is mandatory")
	private String firstName;

	@NotBlank(message = "Last name is mandatory")
	private String lastName;

	@NotBlank(message = "Email id should not be blanc")
	@Email(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", message = "Email is not valid")
	private String email;

	@Size(min = 1, max = 2, message = "Only two phone numbers are allowed")
	private List<String> phoneNumbers;

	private Date dateOfJoining;

	@NotNull
	@DecimalMin(value = "0.0", message = "Salary must be greater than or equal to 0")
	private double salary;

}
