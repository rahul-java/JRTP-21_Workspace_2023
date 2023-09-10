package com.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.app.response.EligResponse;
import com.app.service.EligService;

@RestController
public class EdRestController {

	@Autowired
	private EligService eligService;
	
	@GetMapping("/eligibility/{caseNum}")
	public EligResponse determineEligibility(@PathVariable Long caseNum)
	{
		
		EligResponse eligResponse = eligService.determineEligbility(caseNum);
		
		return eligResponse;
	}
	
}
