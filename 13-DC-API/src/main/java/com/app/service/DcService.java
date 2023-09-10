package com.app.service;

import java.util.List;
import java.util.Map;

import com.app.binding.Child;
import com.app.binding.ChildRequest;
import com.app.binding.DcSummary;
import com.app.binding.Education;
import com.app.binding.Income;
import com.app.binding.PlanSelection;

public interface DcService {

	public Long loadCaseNum(Integer appId);
	
	public Map<Integer,String> getPlanNames();
	
	public Long savePlanSelection(PlanSelection planSelection);
	
	public Long saveIncomeData(Income income);
	
	public Long saveEducationData(Education education);
	
	public Long saveChildrensData(ChildRequest childRequest);
	
	public DcSummary getSummary(Long caseNum);
	
}
