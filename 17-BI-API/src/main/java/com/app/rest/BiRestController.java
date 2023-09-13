package com.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.BiService;

@RestController
public class BiRestController {

	@Autowired
	private BiService biService;
	
	@GetMapping("/create-csv")
	public void  processPendingTriggers()
	{
		 biService.createCSVFile();
	}

}
