package com.sap.hackthon.services;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.sap.hackthon.entity.PropertyMeta;

public interface PropertyMetaService {
	public PropertyMeta get(Long id);
	public boolean create(PropertyMeta propertyMeta);
	public boolean delete(Long id);
	public boolean update(PropertyMeta propertyMeta);
	public List<PropertyMeta> getByTenantIdAndObjectName(String tenantId, String objectName);

}
