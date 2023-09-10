package com.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.binding.Child;
import com.app.binding.ChildRequest;
import com.app.binding.DcSummary;
import com.app.binding.Education;
import com.app.binding.Income;
import com.app.binding.PlanSelection;
import com.app.entity.CitizenAppEntity;
import com.app.entity.DcCaseEntity;
import com.app.entity.DcChildrenEntity;
import com.app.entity.DcEducationEntity;
import com.app.entity.DcIncomeEntity;
import com.app.entity.PlanEntity;
import com.app.repository.CitizenAppRepository;
import com.app.repository.DcCaseRepository;
import com.app.repository.DcChildrenRepository;
import com.app.repository.DcEducationRepository;
import com.app.repository.DcIncomeRepository;
import com.app.repository.PlanRepository;

@Service
public class DCServiceImpl implements DcService {

	@Autowired
	private DcCaseRepository dcCaseRepository;
	@Autowired
	private DcChildrenRepository dcChildrenRepository;
	@Autowired
	private DcEducationRepository dcEducationRepository;
	@Autowired
	private DcIncomeRepository dcIncomeRepository;
	@Autowired
	private PlanRepository planRepository;
	@Autowired
	private CitizenAppRepository citizenAppRepository;
	
	@Override
	public Long loadCaseNum(Integer appId) {

		Optional<CitizenAppEntity> findById = citizenAppRepository.findById(appId);
		if(findById.isPresent())
		{
			DcCaseEntity entity = new DcCaseEntity();
			entity.setAppId(appId);
			entity = dcCaseRepository.save(entity);
					
			return entity.getCaseNum();
		}
		
		return 0l;
	}

	@Override
	public Map<Integer,String> getPlanNames() {

		List<PlanEntity> findAll = planRepository.findAll();
		Map<Integer,String> plansMap=new HashMap<>();
		for(PlanEntity entity:findAll)
		{
			plansMap.put(entity.getPlanId(), entity.getPlanName());
		}
		return plansMap;
	}

	@Override
	public Long savePlanSelection(PlanSelection planSelection) {

		Optional<DcCaseEntity> findByIdOptional = dcCaseRepository.findById(planSelection.getCaseNum());
		if(findByIdOptional.isPresent())
		{
			DcCaseEntity dcCaseEntity = findByIdOptional.get();
			dcCaseEntity.setPlanId(planSelection.getPlanId());
			
			dcCaseRepository.save(dcCaseEntity);
			
			return planSelection.getCaseNum();
		}
		
		return null;
	}

	@Override
	public Long saveIncomeData(Income income) {

		DcIncomeEntity entity = new DcIncomeEntity();
		BeanUtils.copyProperties(income, entity);
		DcIncomeEntity save = dcIncomeRepository.save(entity);
		if(save.getIncomeId()!=null)
			return save.getCaseNum();
		
		return null;
	}

	@Override
	public Long saveEducationData(Education education) {

		DcEducationEntity entity = new DcEducationEntity();
		BeanUtils.copyProperties(education, entity);
		DcEducationEntity save = dcEducationRepository.save(entity);
		
		if(save.getEduId()!=null)
			return save.getCaseNum();
		
		return null;
	}

	@Override
	public Long saveChildrensData(ChildRequest childRequest) {

		List<Child> childs = childRequest.getChilds();
		for(Child c:childs)
		{
			DcChildrenEntity entity = new DcChildrenEntity();
			BeanUtils.copyProperties(c, entity);
			entity.setCaseNum(childRequest.getCaseNum());
			dcChildrenRepository.save(entity);
		}
		
		//dcChildrenRepository.saveAll(entities)
		
		return childRequest.getCaseNum();
	}

	@Override
	public DcSummary getSummary(Long caseNum) {

		String planName ="";
		
		DcIncomeEntity incomeEntity = dcIncomeRepository.findByCaseNum(caseNum);
		DcEducationEntity educationEntity = dcEducationRepository.findByCaseNum(caseNum);
		List<DcChildrenEntity> childList = dcChildrenRepository.findByCaseNum(caseNum);
		
		Optional<DcCaseEntity> dcCaseOptional = dcCaseRepository.findById(caseNum);
		if(dcCaseOptional.isPresent())
		{
			Integer planId = dcCaseOptional.get().getPlanId();
			Optional<PlanEntity> planOptional = planRepository.findById(planId);
			if(planOptional.isPresent())
			{
			    planName = planOptional.get().getPlanName();
			}
		}
		
		DcSummary summary = new DcSummary();
		
		Education education = new Education();
		BeanUtils.copyProperties(educationEntity, education);
		summary.setEducation(education);
		
		Income income = new Income();
		BeanUtils.copyProperties(incomeEntity, income);
		summary.setIncome(income);
		
		List<Child> childs = new ArrayList<>();
		for(DcChildrenEntity ch:childList)
		{
			Child child = new Child();
			BeanUtils.copyProperties(ch, child);
			childs.add(child);
		}
		summary.setChilds(childs);
		
		summary.setPlanName(planName);
		
		return summary;
	}

}
