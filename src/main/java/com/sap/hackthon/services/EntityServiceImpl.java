package com.sap.hackthon.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hackthon.entity.BasicEntity;
import com.sap.hackthon.repository.PropertyMetaRepository;

@Service
@Transactional
public class EntityServiceImpl implements EntityService {

    @Autowired
    PropertyMetaRepository propertyMetaRepository;

    @PersistenceContext
    EntityManager entityManager;

	@Override
	public BasicEntity create(BasicEntity entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public BasicEntity update(BasicEntity entity) {
		entityManager.merge(entity);
		return entity;
	}

	@Override
	public void delete(Long id, String objectType) {
		BasicEntity entity = get(id, objectType);
		if(entity == null){
			return;
		}
		entityManager.remove(entity);
	}

	@Override
	public <T extends BasicEntity> T get(Long id, String objectType) {
		Class<T> eCls = this.<T>retrieveEntityType(objectType).getJavaType();
		return entityManager.find(eCls, id);
	}

	@Override
	public <T extends BasicEntity> List<T> list(String objectType) {
		Class<T> eCls = this.<T>retrieveEntityType(objectType).getJavaType();
		return entityManager.createQuery("FROM " + objectType, eCls).getResultList();
	}

	@Override
	public <T extends BasicEntity> List<T> find(String query, Map<String, Object> params, String objectType) {
		Class<T> eCls = this.<T>retrieveEntityType(objectType).getJavaType();
		TypedQuery<T> typedQuery =  entityManager.createQuery(query, eCls);
		params.entrySet().stream().forEach(e -> typedQuery.setParameter(e.getKey(), e.getValue()));
		return typedQuery.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	private <T extends BasicEntity> EntityType<T> retrieveEntityType(String objectType){
		Optional<EntityType<?>> res = entityManager.getMetamodel().getEntities().stream().
				filter(e -> e.getJavaType().isAssignableFrom(BasicEntity.class) && objectType.equals(e.getName())).findFirst();
		return (EntityType<T>) res.get();
	}
	

}
