package com.sap.hackthon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sap.hackthon.framework.beans.PropertyCounting;

public interface PropertyCountingRepository extends JpaRepository<PropertyCounting, Long> {
	List<PropertyCounting> findByObjectTypeAndFieldName(String objectType, String fieldName);
}
