package com.sap.hackthon.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sap.hackthon.entity.PropertyMeta;

@Controller
@RequestMapping("/propertiesMeta")
public class PropertyMetaController {

	public boolean addPropertyMeta() {
		return false;
	}
	
	public boolean deletePropertyMeta() {
		return false;
	}
	
	public boolean updatePropertyMeta() {
		return false;
	}
	
	public List<PropertyMeta> getPropertyMetas() {
		return null;
	}
}
