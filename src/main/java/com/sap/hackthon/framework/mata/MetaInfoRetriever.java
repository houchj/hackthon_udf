package com.sap.hackthon.framework.mata;

import java.lang.reflect.Member;
import java.util.Iterator;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.hackthon.entity.BasicEntity;

@Component
public class MetaInfoRetriever {
	
	@Autowired
	private EntityManager entityManager;
	
	
	@SuppressWarnings("unchecked")
	public <T extends BasicEntity> EntityType<T> retrieveEntityType(String objectType){
		Optional<EntityType<?>> res = entityManager.getMetamodel().getEntities().stream().
				filter(e -> BasicEntity.class.isAssignableFrom(e.getJavaType()) && objectType.equals(e.getName())).findFirst();
		return (EntityType<T>) res.get();
	}
	
	public <T extends BasicEntity> String retrieveTableName(String objectType){
		EntityType<T> entityType = retrieveEntityType(objectType);
		Class<T> eCls = entityType.getJavaType();
		Table table = eCls.getAnnotation(Table.class);
		if(table == null || table.name().isEmpty()){
			return eCls.getSimpleName();
		}
		return table.name();
	}
	
}
