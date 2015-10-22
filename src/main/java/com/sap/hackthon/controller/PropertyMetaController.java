package com.sap.hackthon.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.framework.beans.GlobalSettings;
import com.sap.hackthon.framework.utils.GlobalConstants;
import com.sap.hackthon.services.meta.PropertyMetaService;

@Controller
@RequestMapping("/propertiesMeta")
public class PropertyMetaController {

	@Autowired
	private PropertyMetaService service;

	@Autowired
	private GlobalSettings settings;

	@RequestMapping(value = "/getByTenantIdAndObjectName", method = RequestMethod.POST)
	public @ResponseBody List<PropertyMeta> getByTenantIdAndObjectName(
			@RequestParam String objectName, HttpServletRequest request) {
		return service.getByObjectType(objectName);
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody boolean create(@RequestBody PropertyMeta propertyMeta,
			HttpServletRequest request) {
		if(service.getByObjectTypeAndDisplayName(propertyMeta.getObjectType(), propertyMeta.getDisplayName())) {
			return false;
		}
		String tenantId = settings.getVariable(GlobalConstants.TENANT).toString();
		propertyMeta.setTenantId(tenantId);
		int nextParamIndex = service.getMaxParamIndexByObjectTypeAndType(propertyMeta.getObjectType(), propertyMeta.getType()) + 1;
		String internalName = GlobalConstants.UDF + "_" + propertyMeta.getTenantId() + "_" + propertyMeta.getType() + "_" + nextParamIndex;
		propertyMeta.setParamIndex(nextParamIndex);
		propertyMeta.setInternalName(internalName);
		return service.create(propertyMeta);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody boolean update(@RequestBody PropertyMeta propertyMeta,
			HttpServletRequest request) {
		String tenantId = settings.getVariable(GlobalConstants.TENANT).toString();
		if (tenantId == null) {
			return false;
		}
		if(service.getByObjectTypeAndDisplayName(propertyMeta.getObjectType(), propertyMeta.getDisplayName())) {
			return false;
		}
		return service.update(propertyMeta);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody PropertyMeta get(@PathVariable("id") Long id) {
		if (id == null) {
			return null;
		}
		return service.get(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable("id") Long id,
			HttpServletRequest request) {
		String tenantId = settings.getVariable(GlobalConstants.TENANT).toString();
		if (tenantId == null) {
			return false;
		}
		if (id == null) {
			return false;
		}
		return service.delete(id);
	}

}
