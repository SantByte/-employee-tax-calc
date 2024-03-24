package com.santosh.imagin.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmpDetails {
	public EmpDetails(int employeeId) {
		this.employeeId = employeeId;
	}

	public EmpDetails() {
	}

	@NotNull(message = "Employee Id is mandatory")
	private int employeeId;
}
