package com.santosh.imagin.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

	@InjectMocks
	private GlobalExceptionHandler globalExceptionHandler;

	@Test
	void testHandleValidationExceptions() {

		MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(exception.getBindingResult()).thenReturn(bindingResult);

		FieldError fieldError1 = new FieldError("objectName", "fieldName1", "error message 1");
		FieldError fieldError2 = new FieldError("objectName", "fieldName2", "error message 2");
		when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

		ResponseEntity<Map<String, String>> responseEntity = globalExceptionHandler
				.handleValidationExceptions(exception);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		Map<String, String> expectedErrors = new HashMap<>();
		expectedErrors.put("fieldName1", "error message 1");
		expectedErrors.put("fieldName2", "error message 2");
		assertEquals(expectedErrors, responseEntity.getBody());
	}
}
