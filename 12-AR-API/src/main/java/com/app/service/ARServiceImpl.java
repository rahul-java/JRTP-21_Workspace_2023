package com.app.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.app.binding.CitizenApp;
import com.app.entity.CitizenAppEntity;
import com.app.repository.CitizenAppRepository;

@Service
public class ARServiceImpl implements ARService {

	@Autowired
	private CitizenAppRepository appRepo;
	
	@Override
	public Integer createApplication(CitizenApp citizenApp) {
		
		//make rest call to ssa-web api with ssn as input
		
		String endpointURL="https://ssa-web-api.herokuapp.com/ssn/{ssn}";
		//currently this url is not working
		
		//accessing url
		/*
		RestTemplate rt=new RestTemplate();
		ResponseEntity<String> responseEntity = rt.getForEntity(endpointURL, String.class, citizenApp.getSsn());
		String stateName = responseEntity.getBody();
		*/
		
		//accessing url another method  WebClient by using webflux dependencies
		/*
		WebClient webClient = WebClient.create();
		String stateName = webClient.get()  //it represent GET request
		         .uri(endpointURL, citizenApp.getSsn())  //it represents url to send req
		         .retrieve()  // to retrieve response
		         .bodyToMono(String.class)  // to specify response type
		         .block();  // to make synchronous call
		*/
		
		String stateName="";
		if(citizenApp.getSsn()>60000)
			 stateName="New Jersey";
		else
		     stateName="Other State";
		
		System.err.println(stateName);
		
		if(stateName.equals("New Jersey"))
		{
			//create application
			CitizenAppEntity entity = new CitizenAppEntity();
			BeanUtils.copyProperties(citizenApp, entity);
			entity.setStateName(stateName);
			CitizenAppEntity save = appRepo.save(entity);
			
			return save.getAppId();
		}
		
		return 0;
	}

}
