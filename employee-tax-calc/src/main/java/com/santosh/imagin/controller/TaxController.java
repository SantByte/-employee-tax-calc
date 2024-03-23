//package com.santosh.imagin.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.santosh.imagin.model.TaxDetails;
//import com.santosh.imagin.service.TaxService;
//
//@RestController
//@RequestMapping("/tax")
//public class TaxController {
//	@Autowired
//	private TaxService service;
//
//	@GetMapping("/deduction")
//	public List<TaxDetails> getTaxDeduction() {
//		return service.calculateTaxDeduction();
//	}
//}
