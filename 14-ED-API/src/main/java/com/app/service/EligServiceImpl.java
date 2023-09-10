package com.app.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.function.LongFunction;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.CitizenAppEntity;
import com.app.entity.CoTriggerEntity;
import com.app.entity.DcCaseEntity;
import com.app.entity.DcChildrenEntity;
import com.app.entity.DcEducationEntity;
import com.app.entity.DcIncomeEntity;
import com.app.entity.EligDtlsEntity;
import com.app.entity.PlanEntity;
import com.app.repository.CitizenAppRepository;
import com.app.repository.CoTriggerRepository;
import com.app.repository.DcCaseRepository;
import com.app.repository.DcChildrenRepository;
import com.app.repository.DcEducationRepository;
import com.app.repository.DcIncomeRepository;
import com.app.repository.EligDtlsRepository;
import com.app.repository.PlanRepository;
import com.app.response.EligResponse;

@Service
public class EligServiceImpl implements EligService {

	@Autowired
	private DcCaseRepository dcCaseRepository;
	@Autowired
	private PlanRepository planRepository;
	@Autowired
	private DcIncomeRepository dcIncomeRepository;
	@Autowired
	private DcChildrenRepository dcChildrenRepository;
	@Autowired
	private CitizenAppRepository citizenAppRepository;
	@Autowired
	private DcEducationRepository dcEducationRepository;
	@Autowired
	private EligDtlsRepository eligDtlsRepository;
	@Autowired
	private CoTriggerRepository coTriggerRepository;

	@Override
	public EligResponse determineEligbility(Long caseNum) {

		Integer planId = null;
		String planName = null;
		Integer appId = null;

		Optional<DcCaseEntity> caseEntityOptional = dcCaseRepository.findById(caseNum);
		if (caseEntityOptional.isPresent()) {
			DcCaseEntity dcCaseEntity = caseEntityOptional.get();
			planId = dcCaseEntity.getPlanId();
			appId = dcCaseEntity.getAppId();
		}

		Optional<PlanEntity> planEntityOptional = planRepository.findById(planId);
		if (planEntityOptional.isPresent()) {
			PlanEntity planEntity = planEntityOptional.get();
			planName = planEntity.getPlanName();
		}
		
		Optional<CitizenAppEntity> citizenEntityOptional = citizenAppRepository.findById(appId);
		Integer age=0;
		CitizenAppEntity citizen=null;
		if(citizenEntityOptional.isPresent())
		{
			citizen = citizenEntityOptional.get();
			LocalDate dob = citizen.getDob();
			LocalDate now = LocalDate.now();
			
			Period period = Period.between(dob, now);
			age = period.getYears();
		}

		 EligResponse eligResponse = executePlanCondition(caseNum, planName, age);
		 
		 //logic to store data in db
		 
		 EligDtlsEntity eligDtlsEntity = new EligDtlsEntity();
		 
		 BeanUtils.copyProperties(eligResponse, eligDtlsEntity);
		 eligDtlsEntity.setCaseNum(caseNum);
		 eligDtlsEntity.setHolderName(citizen.getFullname());
		 eligDtlsEntity.setHolderSsn(citizen.getSsn());
		 
		 eligDtlsRepository.save(eligDtlsEntity);
		 
		 CoTriggerEntity coTriggerEntity = new CoTriggerEntity();
		 coTriggerEntity.setCaseNum(caseNum);
		 coTriggerEntity.setTrgStatus("PENDING");
		 
		 coTriggerRepository.save(coTriggerEntity);
		 
		 return eligResponse;
	}

	private EligResponse executePlanCondition(Long caseNum, String planName,Integer age) {
		EligResponse response = new EligResponse();
		response.setPlanName(planName);

		// logic to execute condition

		DcIncomeEntity income = dcIncomeRepository.findByCaseNum(caseNum);
		Double empIncome = income.getEmpIncome();
		
		if (planName.equals("SNAP")) {
			
			if (empIncome <= 300) {
				response.setPlanStatus("AP");
			} else {
				response.setPlanStatus("DN");
				response.setDenialReason("High Income");
			}

		} else if (planName.equals("CCAP")) {
			
			boolean ageCondition=true;
			boolean kidsCountCondition=false;
			
			List<DcChildrenEntity> childs = dcChildrenRepository.findByCaseNum(caseNum);
			if(!childs.isEmpty())
			{
				kidsCountCondition=true;
				
				for(DcChildrenEntity ch:childs)
				{
					Integer childAge = ch.getChildAge();
					if(childAge>16)
					{
						ageCondition=false;
						break;
					}
				}
			}
			
			if(empIncome<=300 && kidsCountCondition && ageCondition)
			{
				response.setPlanStatus("AP");
			}
			else {
				response.setPlanStatus("DN");
				response.setDenialReason("NOT SATISFIED BUSINESS RULES");
			}

		} else if (planName.equals("Medicare")) {

				if(age>=65)
				{
					response.setPlanStatus("AP");
				}
				else {
					response.setPlanStatus("DN");
					response.setDenialReason("AGE NOT MATCHED");
				}
			
		} else if (planName.equals("Medicaid")) {

			Double propertyIncome = income.getPropertyIncome();
			if(empIncome<=300 && propertyIncome==0)
			{
				response.setPlanStatus("AP");
			}
			else {
				response.setPlanStatus("DN");
				response.setDenialReason("HIGH INCOME");
			}
			
		} else if (planName.equals("NJW")) {

			DcEducationEntity educationEntity = dcEducationRepository.findByCaseNum(caseNum);
			Integer graduationYear = educationEntity.getGraduationYear();
			int currYear = LocalDate.now().getYear();
			if(empIncome <= 0 && graduationYear < currYear)
			{
				response.setPlanStatus("AP");
			}
			else {
				response.setPlanStatus("DN");
				response.setDenialReason("RULES NOT SATISFIED");
			}
		}

		if(response.getPlanStatus().equals("AP"))
		{
			response.setPlanStartDate(LocalDate.now());
			response.setPlanEndDate(LocalDate.now().plusYears(1));
			response.setBenefitAmt(350.00);
		}
		
		return response;
	}

}
