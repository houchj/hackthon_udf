package com.sap.hackthon.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.sap.hackthon.entity.PropertyCounting;
import com.sap.hackthon.repository.PropertyCountingRepository;

public class PropertyCountingServiceImpl implements PropertyCountingService {

	@Autowired
	PropertyCountingRepository propertyCountingRepository;
	
	@Override
	public void addReferenceCounting(String objectName, String fieldName,
			Long counting) {
		if(counting == 0) {
			PropertyCounting propertyCounting = new PropertyCounting();
			propertyCounting.setObjectName(objectName);
			propertyCounting.setFieldName(fieldName);
			propertyCounting.setCounting(counting);
			
			propertyCountingRepository.saveAndFlush(propertyCounting);
		} else {
			List<PropertyCounting> propertyCountings = propertyCountingRepository.findByObjectNameAndFieldName(objectName, fieldName);
			if(!CollectionUtils.isEmpty(propertyCountings)) {
				PropertyCounting propertyCounting = propertyCountings.get(0);
				propertyCounting.setCounting(counting + 1);
				propertyCountingRepository.saveAndFlush(propertyCounting);
			}
		}
	}

	@Override
	public void minusReferenceCounting(String objectName, String fieldName,
			Long counting) {
		List<PropertyCounting> propertyCountings = propertyCountingRepository.findByObjectNameAndFieldName(objectName, fieldName);
		if(!CollectionUtils.isEmpty(propertyCountings)) {
			PropertyCounting propertyCounting = propertyCountings.get(0);
			propertyCounting.setCounting(counting - 1);
			propertyCountingRepository.saveAndFlush(propertyCounting);
		}		
	}
}
