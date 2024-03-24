package com.santosh.imagin.exception;

public class DuplicateEmployeeIdException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateEmployeeIdException() {
		super("Duplicate Employee ID");
	}
}
