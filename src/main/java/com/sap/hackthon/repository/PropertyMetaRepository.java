package com.sap.hackthon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.enumeration.UDFTypeEnum;


public interface PropertyMetaRepository extends JpaRepository<PropertyMeta, Long> {
	
	List<PropertyMeta> findByTenantIdAndObjectName(String tenantId, String objectName);
	
	@Query("select max(pm.paramIndex) from PropertyMeta pm where pm.tenantId = ?1 and pm.objectName = ?2 and pm.type = ?3")
	Long findMaxParamIndexByTenantIdAndObjectNameAndType(String tenantId, String objectName, UDFTypeEnum type);
}
