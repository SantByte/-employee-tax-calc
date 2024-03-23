package com.santosh.imagin.service;

import java.util.List;

import com.santosh.imagin.model.TaxDetails;

public interface TaxService {
	List<TaxDetails> calculateTaxDeduction();
}
