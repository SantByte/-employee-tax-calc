package com.santosh.imagin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.santosh.imagin.exception.DuplicateEmployeeIdException;
import com.santosh.imagin.exception.EmployeeNotFoundException;
import com.santosh.imagin.model.EmpDetails;
import com.santosh.imagin.model.Employee;
import com.santosh.imagin.model.TaxDetails;
import com.santosh.imagin.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

	@Mock
	private EmployeeService employeeService;

	@InjectMocks
	private EmployeeController employeeController;

	@Test
	void testStoreEmpDetails() throws Exception {
		Employee employee = new Employee();
		employee.setEmployeeId(123);
		employee.setFirstName("John");
		employee.setLastName("Doe");
		employee.setEmail("john.doe@example.com");
		List<String> phoneNumbers = new ArrayList<>();
		phoneNumbers.add("1234567890");
		employee.setDateOfJoining(new Date());
		employee.setSalary(60000);
		
		when(employeeService.validateAndSave(employee)).thenReturn("Employee details stored successfully");
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		ResponseEntity<String> responseEntity = employeeController.storeEmpDetails(employee);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Employee details stored successfully", responseEntity.getBody());

	}

	@Test
	void testCalculateTaxDeduction() throws Exception {
		int employeeId = 123;
		TaxDetails taxDetails = new TaxDetails();
		taxDetails.setEmployeeId(employeeId);
		taxDetails.setFirstName("Biscuit");
		taxDetails.setLastName("Pumpkin");
		taxDetails.setTotalSalary(720000);
		taxDetails.setTaxAmount(60000);
		taxDetails.setCessAmount(1440);

		when(employeeService.calculateTaxDeduction(anyInt())).thenReturn(taxDetails);

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		EmpDetails empDetails = new EmpDetails();
		empDetails.setEmployeeId(employeeId);
		ResponseEntity<?> responseEntity = employeeController.calculateTaxDeduction(empDetails);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(taxDetails, responseEntity.getBody());
	}
	
	@Test
    void testStoreEmployeeDetails_DuplicateEmployeeId() throws DuplicateEmployeeIdException {
        // Mock employee object
        Employee employee = new Employee();
        employee.setEmployeeId(123);
        when(employeeService.validateAndSave(employee)).thenThrow(DuplicateEmployeeIdException.class);

        ResponseEntity<String> responseEntity = employeeController.storeEmpDetails(employee);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Duplicate Employee ID", responseEntity.getBody());
    }

    @Test
    void testStoreEmployeeDetails_InternalServerError() throws DuplicateEmployeeIdException {
        Employee employee = new Employee();
        employee.setEmployeeId(123);
        when(employeeService.validateAndSave(employee)).thenThrow(RuntimeException.class);

        ResponseEntity<String> responseEntity = employeeController.storeEmpDetails(employee);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
    
    @Test
    void testCalculateTaxDeduction_InternalServerError() {
        int employeeId = 123;

        when(employeeService.calculateTaxDeduction(employeeId)).thenThrow(RuntimeException.class);

        ResponseEntity<?> responseEntity = employeeController.calculateTaxDeduction(new EmpDetails());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
    
    @Test
    void testCalculateTaxDeduction_EmployeeNotFound() {
        int employeeId = 1001;

        when(employeeService.calculateTaxDeduction(employeeId)).thenThrow(EmployeeNotFoundException.class);

        ResponseEntity<?> responseEntity = employeeController.calculateTaxDeduction(new EmpDetails(employeeId));

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

}
