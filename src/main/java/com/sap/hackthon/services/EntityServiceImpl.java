package com.sap.hackthon.services;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.hackthon.entity.DynamicEntity;
import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.repository.PropertyMetaRepository;
import com.sap.hackthon.utils.UDFTypeEnum;

@Service
public class EntityServiceImpl implements EntityService {
	
	@Autowired
	PropertyMetaRepository propertyMetaRepository;
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public DynamicEntity create(DynamicEntity entity, Long tanentId) {
		PropertyMeta meta=new PropertyMeta();
		meta.setDisplayName("Display");
		meta.setInternalName("Internal_001");
		meta.setObjectName("order");
		meta.setParamIndex(1);
		meta.setSystemField(false);
		meta.setTenantId("TN001");
		meta.setType(UDFTypeEnum.NVARCHAR);
		
		propertyMetaRepository.saveAndFlush(meta);
//		entityManager.persist(meta);
//		entityManager.flush();
		
		return null;
	}

	@Override
	public DynamicEntity update(DynamicEntity entity, Long tanentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id, String objectType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DynamicEntity get(Long id, String objectType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DynamicEntity> list(String objectType, Long tanentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
