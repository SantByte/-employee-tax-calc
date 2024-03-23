package com.santosh.imagin.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmpDetails {
	@NotNull(message = "Employee Id is mandatory")
	private int employeeId;
}
