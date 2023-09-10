package com.app.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.app.binding.CreateCaseResponse;
import com.app.service.DcService;

@RestController
public class CreateCaseRestController {

	@Autowired
	private DcService dcService;

	@GetMapping("/case/{appId}")
	public ResponseEntity<CreateCaseResponse> createCase(@PathVariable Integer appId)
	{
		Long caseNum = dcService.loadCaseNum(appId);
		Map<Integer,String> planNames = dcService.getPlanNames();
		
		CreateCaseResponse createCaseResponse = new CreateCaseResponse();
		createCaseResponse.setCaseNum(caseNum);
		createCaseResponse.setPlanName(planNames);
		
		return new ResponseEntity<>(createCaseResponse,HttpStatus.OK) ;
	}
}
