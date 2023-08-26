package com.app.service;

import java.util.List;
import java.util.Map;

import com.app.entity.Plan;

public interface PlanService {

	public Map<Integer, String> getPlanCategories();
	
	public boolean savePlan(Plan plan);
	
	public List<Plan> getALlPlans();
	
	public Plan getPlanById(Integer planId);
	
	public boolean updatePlan(Plan plan);
	
	public boolean deletePlanById(Integer planId);
	
	public boolean changePlanStatus(Integer id,String status);
	
	
}
