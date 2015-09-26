package com.sap.hackthon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sap.hackthon.entity.PropertyCounting;

public interface PropertyCountingRepository extends JpaRepository<PropertyCounting, Long> {
	List<PropertyCounting> findByObjectNameAndFieldName(String objectName, String fieldName);
}
