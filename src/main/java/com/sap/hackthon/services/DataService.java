package com.sap.hackthon.services;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;

import com.sap.hackthon.entity.BasicEntity;

public abstract class DataService {

	@PersistenceContext
	protected EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	protected <T extends BasicEntity> EntityType<T> retrieveEntityType(String objectType){
		Optional<EntityType<?>> res = entityManager.getMetamodel().getEntities().stream().
				filter(e -> e.getJavaType().isAssignableFrom(BasicEntity.class) && objectType.equals(e.getName())).findFirst();
		return (EntityType<T>) res.get();
	}
	
}
