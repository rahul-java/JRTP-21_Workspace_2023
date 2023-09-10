package com.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.binding.CoResponse;
import com.app.service.CoService;

@RestController
public class CoRestController {

	@Autowired
	private CoService coService;
	
	@GetMapping("/process")
	public CoResponse processPendingTriggers()
	{
		return coService.processPendingTriggers();
	}

}
