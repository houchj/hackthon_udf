package com.sap.hackthon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.services.PropertyMetaService;

@Controller
@RequestMapping("/propertiesMeta")
public class PropertyMetaController {

	@Autowired
	private PropertyMetaService service;

	@RequestMapping(value = "/getByTenantIdAndObjectName", method = RequestMethod.POST)
	public @ResponseBody List<PropertyMeta> getByTenantIdAndObjectName(
			@RequestParam String tenantId, @RequestParam String objectName) {
		return service.getByTenantIdAndObjectName(tenantId, objectName);
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody boolean create(@RequestBody PropertyMeta propertyMeta) {
		if (propertyMeta != null) {
			return service.create(propertyMeta);
		}
		return false;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody boolean update(@RequestBody PropertyMeta propertyMeta) {
		if (propertyMeta != null) {
			return service.update(propertyMeta);
		}
		return false;
	}

	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable("id") Long id) {
		if (id == null) {
			return false;
		}
		return service.delete(id);
	}

}
