package com.sap.hackthon.services;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hackthon.entity.BasicEntity;
import com.sap.hackthon.repository.PropertyMetaRepository;

@Service
@Transactional
public class EntityServiceImpl extends DataService implements EntityService {

    @Autowired
    protected PropertyMetaRepository propertyMetaRepository;

	@Override
	public <T extends BasicEntity> T create(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public <T extends BasicEntity> T update(T entity) {
		entityManager.merge(entity);
		return entity;
	}

	@Override
	public <T extends BasicEntity> void delete(Long id, String objectType) {
		T entity = get(id, objectType);
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
	

}
