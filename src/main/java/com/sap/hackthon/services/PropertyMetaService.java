package com.sap.hackthon.services;

import java.util.List;

import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.enumeration.UDFTypeEnum;

public interface PropertyMetaService {

	public PropertyMeta get(Long id);

	public boolean create(PropertyMeta propertyMeta);

	public boolean delete(Long id);

	public boolean update(PropertyMeta propertyMeta);

	public List<PropertyMeta> getByObjectType(String objectType);

	public int getMaxParamIndexByObjectTypeAndType(String objectType, UDFTypeEnum type);
	
	public boolean getByObjectTypeAndDisplayName(String objectType, String displayName);

	public void scanAndInstallProperties();
}
