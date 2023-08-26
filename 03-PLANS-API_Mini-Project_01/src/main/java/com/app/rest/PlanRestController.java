package com.app.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.constants.AppConstants;
import com.app.entity.Plan;
import com.app.properties.AppProperties;
import com.app.service.PlanService;

@RestController
public class PlanRestController {

	//@Autowired
	//private PlanService planService;
	
	//@Autowired
	//private AppProperties appProps;
	
	//private Map<String, String> messages = appProps.getMessages();
	
	private PlanService planService;
	private Map<String, String> messages;
	
	public PlanRestController(PlanService planService,AppProperties appProps)
	{
		this.planService=planService;
		this.messages = appProps.getMessages();
		System.out.println(this.messages);
	}
	
	@GetMapping("/categories")
	public ResponseEntity<Map<Integer, String>> planCategories(){
		Map<Integer, String> planCategories = planService.getPlanCategories();
		return new ResponseEntity<>(planCategories,HttpStatus.OK);
	}
	
	@PostMapping("/plan")
	public ResponseEntity<String> savePlan(@RequestBody Plan plan)
	{
		boolean isSaved = planService.savePlan(plan);
		String responseMsg=AppConstants.EMPTY_STRING;
		
		//Map<String, String> messages = appProps.getMessages();
		
		if(isSaved)
		{
			//responseMsg="Plan Saved...";  //Hard Code
			
			//String planSaveSucc = messages.get("planSaveSucc");
			//responseMsg=planSaveSucc;
			
			responseMsg=messages.get(AppConstants.PLAN_SAVE_SUCC);
		}
		else
		{
			//responseMsg="Plan Not Saved..."; //Hard Code
			
			//String planSaveFail = messages.get("planSaveFail");
			//responseMsg=planSaveFail;
			
			responseMsg=messages.get(AppConstants.PLAN_SAVE_FAIL);
		}
		return new ResponseEntity<>(responseMsg,HttpStatus.CREATED);
	}
	@GetMapping("/plans")
	public ResponseEntity<List<Plan>> plans()
	{
		List<Plan> aLlPlans = planService.getALlPlans();
		return new ResponseEntity<>(aLlPlans, HttpStatus.OK);
	}
	
	@GetMapping("/plan/{planId}")
	public ResponseEntity<Plan> editPlan(Integer planId)
	{
		Plan plan = planService.getPlanById(planId);
		
		return new ResponseEntity<>(plan, HttpStatus.OK);
		
	}
	
	@PutMapping("/plan")
	public ResponseEntity<String> updatePlan(@RequestBody Plan plan)
	{
		boolean isUpdated = planService.updatePlan(plan);
		String responseMsg=AppConstants.EMPTY_STRING;
		
		//Map<String, String> messages = appProps.getMessages();
		
		if(isUpdated)
		{
			//responseMsg="Plan Updated...";
			
			responseMsg=messages.get(AppConstants.PLAN_UPDATE_SUCC);
		}	
		else
		{
			//responseMsg="Plan Not Updated...";
			
			responseMsg=messages.get(AppConstants.PLAN_UPDATE_FAIL);
		}
			
		return new ResponseEntity<>(responseMsg,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/plan/{planId}")
	public ResponseEntity<String> deletePlan(@PathVariable Integer planId)
	{
		boolean isDeleted = planService.deletePlanById(planId);
		String msg=AppConstants.EMPTY_STRING;
		
		//Map<String, String> messages = appProps.getMessages();
		
		if(isDeleted)
			msg=messages.get(AppConstants.PLAN_DEL_SUCC);
		else
			msg=messages.get(AppConstants.PLAN_DEL_FAIL);
		
		return new ResponseEntity<>(msg, HttpStatus.OK);
		
	}
	
	@PutMapping("/status-change/{planId}/{status}")
	public ResponseEntity<String> changeStatus(@PathVariable Integer planId,@PathVariable String status)
	{
		boolean isPlanStatusChanged = planService.changePlanStatus(planId, status);
		String msg=AppConstants.EMPTY_STRING;
		
		//Map<String, String> messages = appProps.getMessages();
		
		if(isPlanStatusChanged)
			msg=messages.get(AppConstants.PLAN_STATUS_CHANGE_SUCC);
		else
			msg=messages.get(AppConstants.PLAN_STATUS_CHANGE_FAIL);
		
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
}
