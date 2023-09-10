package com.app.service;

import com.app.response.EligResponse;

public interface EligService {

	public EligResponse determineEligbility(Long caseNum);
	
}
