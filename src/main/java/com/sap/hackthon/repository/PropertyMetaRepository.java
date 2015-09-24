package com.sap.hackthon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sap.hackthon.entity.PropertyMeta;


public interface PropertyMetaRepository extends JpaRepository<PropertyMeta, Long> {
	List<PropertyMeta> findByTenantIdAndObjectName(String tenantId, String objectName);
}
