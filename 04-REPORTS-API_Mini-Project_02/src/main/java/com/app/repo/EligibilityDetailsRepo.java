package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entity.EligibilityDetails;

public interface EligibilityDetailsRepo extends JpaRepository<EligibilityDetails, Integer>{

	@Query("select distinct(planName) from EligibilityDetails")
	public List<String> findPlanNames();
	
	@Query("select distinct(planStatus) from EligibilityDetails")
	public List<String> findPlanStatuses();
}
