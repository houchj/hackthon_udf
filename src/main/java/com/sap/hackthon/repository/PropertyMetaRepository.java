package com.sap.hackthon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.sap.hackthon.entity.PropertyMeta;


public interface PropertyMetaRepository extends JpaRepository<PropertyMeta, Long> {
	List<PropertyMeta> findByTenantIdAndObjectName(@Param("tenantId") String tenantId, @Param("objectName") String objectName);
}
