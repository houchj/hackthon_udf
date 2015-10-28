package com.sap.hackthon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sap.hackthon.framework.beans.PropertyMeta;
import com.sap.hackthon.framework.enumeration.UDFType;


public interface PropertyMetaRepository extends JpaRepository<PropertyMeta, Long> {
	
	List<PropertyMeta> findByObjectType(String objectType);
	
	List<PropertyMeta> findByObjectTypeAndDisplayName(String objectType, String displayName);
	
	@Query("select max(pm.paramIndex) from PropertyMeta pm where pm.objectType = ?1 and pm.type = ?2")
	int findMaxParamIndexByObjectTypeAndType(String objectType, UDFType type);
}
