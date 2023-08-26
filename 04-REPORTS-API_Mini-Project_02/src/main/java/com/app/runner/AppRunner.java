package com.app.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.app.entity.EligibilityDetails;
import com.app.repo.EligibilityDetailsRepo;

@Component
public class AppRunner implements ApplicationRunner{

	@Autowired
	private EligibilityDetailsRepo eligibilityDetailsRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		EligibilityDetails entity1 = new EligibilityDetails();
		entity1.setEligId(1);
		entity1.setName("John");
		entity1.setMobile(22323l);
		entity1.setGender('M');
		entity1.setSsn(86868l);
		entity1.setPlanName("SNAP");
		entity1.setPlanStatus("Approved");
		eligibilityDetailsRepo.save(entity1);
		
		EligibilityDetails entity2 = new EligibilityDetails();
		entity2.setEligId(2);
		entity2.setName("Nick");
		entity2.setMobile(22323l);
		entity2.setGender('M');
		entity2.setSsn(87878l);
		entity2.setPlanName("CCAP");
		entity2.setPlanStatus("Denied");
		eligibilityDetailsRepo.save(entity2);
		
		EligibilityDetails entity3 = new EligibilityDetails();
		entity3.setEligId(3);
		entity3.setName("Robert");
		entity3.setMobile(32323l);
		entity3.setGender('M');
		entity3.setSsn(989868l);
		entity3.setPlanName("MEDICAP");
		entity3.setPlanStatus("Closed");
		eligibilityDetailsRepo.save(entity3);
		
	}

}
