package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.PlanCategory;

public interface PlanCategoryRepo extends JpaRepository<PlanCategory, Integer> {

}
