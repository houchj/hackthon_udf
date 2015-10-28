package com.sap.hackthon.services.meta;

import java.util.List;

import com.sap.hackthon.framework.beans.PropertyMeta;
import com.sap.hackthon.framework.enumeration.UDFType;

public interface PropertyMetaService {

	public PropertyMeta get(Long id);

	public boolean create(PropertyMeta propertyMeta);

	public boolean delete(Long id);

	public boolean update(PropertyMeta propertyMeta);

	public List<PropertyMeta> getByObjectType(String objectType);

	public int getMaxParamIndexByObjectTypeAndType(String objectType, UDFType type);
	
	public boolean getByObjectTypeAndDisplayName(String objectType, String displayName);

}
