package com.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.binding.Education;
import com.app.binding.Income;
import com.app.service.DcService;

@RestController
public class EducationRestController {

	@Autowired
	private DcService dcService;
	
	@PostMapping("/education")
	public ResponseEntity<Long> saveEducation(@RequestBody Education education)
	{
		Long caseNum = dcService.saveEducationData(education);
		return new ResponseEntity<>(caseNum,HttpStatus.CREATED);
	}
}
