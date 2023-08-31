package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WelcomeRestController {

	@Autowired
	private GreetClient greetClient;
	
	@GetMapping("/welcome")
	public WelcomeResponse welcomeMsg()
	{
		String welcomeMsg="Welcome To Microservices Tutorial";
		
		//Internal Service Communication
		String greetMsg = greetClient.invokeGreetApi();
		
		//External Service Communication
		RestTemplate rt=new RestTemplate();
		String endpointurl="https://qdpw52aw9i.execute-api.eu-north-1.amazonaws.com/dev/pets/1";
		ResponseEntity<Pet> entity = rt.getForEntity(endpointurl, Pet.class);
		Pet pet = entity.getBody();
		
		WelcomeResponse finalResponse = new WelcomeResponse();
		finalResponse.setGreetMsg(greetMsg);
		finalResponse.setWelcomeMsg(welcomeMsg);
		finalResponse.setPet(pet);
	
		return finalResponse;
		//return greetMsg +" , "+ welcomeMsg;
	}
}
