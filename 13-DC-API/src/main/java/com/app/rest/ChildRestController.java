package com.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.binding.ChildRequest;
import com.app.binding.DcSummary;
import com.app.service.DcService;

@RestController
public class ChildRestController {

	@Autowired
	private DcService dcService;
	
	@PostMapping("/childrens")
	public ResponseEntity<DcSummary> saveChilds(@RequestBody ChildRequest childRequest)
	{
		Long caseNum = dcService.saveChildrensData(childRequest);
		DcSummary summary = dcService.getSummary(caseNum);
		return new ResponseEntity<>(summary,HttpStatus.OK);
	}
}
