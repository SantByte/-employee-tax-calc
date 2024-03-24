package com.santosh.imagin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.santosh.imagin.exception.DuplicateEmployeeIdException;
import com.santosh.imagin.exception.EmployeeNotFoundException;
import com.santosh.imagin.model.Employee;
import com.santosh.imagin.model.TaxDetails;
import com.santosh.imagin.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Test
	void testStoreEmployeeDetails() throws DuplicateEmployeeIdException {
		Employee employee = new Employee();
		employee.setEmployeeId(123);
		employee.setFirstName("John");
		employee.setLastName("Doe");
		employee.setEmail("john.doe@example.com");
		List<String> phoneNumbers = new ArrayList<>();
		phoneNumbers.add("1234567890");
		employee.setDateOfJoining(new Date());
		employee.setSalary(60000);

		when(employeeRepository.existsById(employee.getEmployeeId())).thenReturn(false);

		String result = employeeService.validateAndSave(employee);

		verify(employeeRepository, times(1)).existsById(employee.getEmployeeId());

		verify(employeeRepository, times(1)).save(employee);

		assertEquals("Employee details stored successfully", result);
	}

	@Test
	void testCalculateTaxDeduction() {
		int employeeId = 123;

		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setFirstName("John");
		employee.setLastName("Doe");
		employee.setDateOfJoining(new Date());
		employee.setSalary(750000);

		when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

		TaxDetails taxDetails = employeeService.calculateTaxDeduction(employeeId);

		verify(employeeRepository, times(1)).findById(employeeId);

		assertEquals(employeeId, taxDetails.getEmployeeId());
		assertEquals("John", taxDetails.getFirstName());
		assertEquals("Doe", taxDetails.getLastName());
		assertEquals(37500, taxDetails.getTaxAmount());
		assertEquals(0, taxDetails.getCessAmount());
	}

	@Test
	void testCalculateTaxDeduction_EmployeeNotFound() {
		int employeeId = 1001;

		when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

		assertThrows(EmployeeNotFoundException.class, () -> {
			employeeService.calculateTaxDeduction(employeeId);
		});
	}

	@Test
	void testStoreEmployeeDetails_DuplicateEmployeeId() {
		Employee employee = new Employee();
		employee.setEmployeeId(124);

		when(employeeRepository.existsById(employee.getEmployeeId())).thenReturn(true);

		assertThrows(DuplicateEmployeeIdException.class, () -> {
			employeeService.validateAndSave(employee);
		});
	}

	@Test
	void testCalculateTaxDeduction_CalculateCessAmount() {
		Employee employee = new Employee();
		employee.setEmployeeId(123);
		employee.setFirstName("John");
		employee.setLastName("Doe");
		employee.setDateOfJoining(new Date());
		employee.setSalary(2800000);

		when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));

		TaxDetails taxDetails = employeeService.calculateTaxDeduction(employee.getEmployeeId());

		assertEquals(6000, taxDetails.getCessAmount());
	}

}
