package com.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.binding.CitizenApp;
import com.app.service.ARService;

@RestController
public class ArRestController {

	@Autowired
	private ARService arService;
	
	@PostMapping("/app")
	public ResponseEntity<String> createCitizenApp(@RequestBody CitizenApp citizenApp)
	{
		Integer appId = arService.createApplication(citizenApp);
		if(appId>0)
		{
			return new ResponseEntity<>("App Created with App ID : "+appId, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("INVALID SSN : "+citizenApp.getSsn(), HttpStatus.BAD_REQUEST);

		}
	}
}
