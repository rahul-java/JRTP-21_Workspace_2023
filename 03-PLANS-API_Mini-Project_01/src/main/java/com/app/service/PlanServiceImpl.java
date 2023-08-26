package com.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Plan;
import com.app.entity.PlanCategory;
import com.app.repo.PlanCategoryRepo;
import com.app.repo.PlanRepo;

@Service
public class PlanServiceImpl implements PlanService{

	@Autowired
	private PlanRepo planRepo;
	
	@Autowired
	private PlanCategoryRepo planCategoryRepo;
	
	@Override
	public Map<Integer, String> getPlanCategories() {
		List<PlanCategory> planCategories = planCategoryRepo.findAll();
		Map<Integer, String> categoryMap=new HashMap<>();
		
		planCategories.forEach(category->{
			categoryMap.put(category.getCategoryId(), category.getCategoryName());
		});
		
		return categoryMap;
	}

	@Override
	public boolean savePlan(Plan plan) {
		Plan savedPlan = planRepo.save(plan);
		
		/*
		if(savedPlan.getPlanId()!=null)
			return true;
		else
		    return false;
	    */
		
		//return savedPlan.getPlanId()!=null?true:false;
		
		return savedPlan.getPlanId()!=null;
	}

	@Override
	public List<Plan> getALlPlans() {
		
		return planRepo.findAll();
	}

	@Override
	public Plan getPlanById(Integer planId) {
		Optional<Plan> findById = planRepo.findById(planId);
		if(findById.isPresent())
			return findById.get();
		else
		    return null;
	}

	@Override
	public boolean updatePlan(Plan plan) {
		Plan savedPlan = planRepo.save(plan);
		return savedPlan.getPlanId()!=null;
	}

	@Override
	public boolean deletePlanById(Integer planId) {
		boolean status=false;
		try {
			planRepo.deleteById(planId);
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean changePlanStatus(Integer id, String status) {
		Optional<Plan> findById = planRepo.findById(id);
		if(findById.isPresent())
		{
			Plan plan = findById.get();
			plan.setActiveSwitch(status);
			planRepo.save(plan);
			return true;
		}
		return false;
	}

}
