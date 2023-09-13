package com.app.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.EligDtlsEntity;

public interface EligDtlsRepository extends JpaRepository<EligDtlsEntity, Serializable>{

	public List<EligDtlsEntity> findByPlanStatus(String planStatus);
}
