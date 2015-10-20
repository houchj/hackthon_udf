package com.sap.hackthon.services;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;

import com.sap.hackthon.entity.BasicEntity;

public abstract class DataService {

	@PersistenceContext
	protected EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	protected <T extends BasicEntity> EntityType<T> retrieveEntityType(String objectType){
		Optional<EntityType<?>> res = entityManager.getMetamodel().getEntities().stream().
				filter(e -> BasicEntity.class.isAssignableFrom(e.getJavaType()) && objectType.equals(e.getName())).findFirst();
		return (EntityType<T>) res.get();
	}
	
	protected String retrieveTableName(String objectType){
		EntityType<? extends BasicEntity> entityType = retrieveEntityType(objectType);
		Class<? extends BasicEntity> eCls = entityType.getJavaType();
		Table table = eCls.getAnnotation(Table.class);
		if(table == null || table.name().isEmpty()){
			return eCls.getSimpleName();
		}
		return table.name();
	}
	
}
