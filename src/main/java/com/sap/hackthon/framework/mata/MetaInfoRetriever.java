package com.sap.hackthon.framework.mata;

import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.sap.hackthon.framework.beans.BasicEntity;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MetaInfoRetriever {
	
	@Autowired
	private EntityManager entityManager;
	
	private Map<String, EntityType<?>> eCache;
	
	@SuppressWarnings("unchecked")
	public <T extends BasicEntity> EntityType<T> retrieveEntityType(String objectType){
		if(eCache == null){
			eCache = entityManager.getEntityManagerFactory().getMetamodel().getEntities().stream()
					.filter(s -> BasicEntity.class.isAssignableFrom(s.getJavaType()))
					.collect(Collectors.toConcurrentMap(t -> t.getName(), u -> u));
		}
		return (EntityType<T>) eCache.get(objectType);
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
