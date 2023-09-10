package com.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.binding.PlanSelection;
import com.app.service.DcService;

@RestController
public class PlanSelectionRestController {

	@Autowired
	private DcService dcService;
	
	@PostMapping("/plansel")
	public ResponseEntity<Long> planSelection(@RequestBody PlanSelection planSelection)
	{
		Long caseNum = dcService.savePlanSelection(planSelection);
		return new ResponseEntity<>(caseNum,HttpStatus.CREATED);
	}
}
