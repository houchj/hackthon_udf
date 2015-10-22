package com.sap.hackthon.services.biz;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hackthon.framework.beans.BasicEntity;
import com.sap.hackthon.framework.inject.OrmInjector;
import com.sap.hackthon.framework.mata.MetaInfoRetriever;

@Service
@Transactional
public class EntityServiceImpl implements EntityService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private MetaInfoRetriever metaInfo;
	
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
		Class<T> eCls = metaInfo.<T>retrieveEntityType(objectType).getJavaType();
		return entityManager.find(eCls, id);
	}

	@Override
	public <T extends BasicEntity> List<T> list(String objectType) {
		Class<T> eCls = metaInfo.<T>retrieveEntityType(objectType).getJavaType();
		return entityManager.createQuery("SELECT o FROM " + eCls.getName() + " o", eCls).getResultList();
	}

	@Override
	public <T extends BasicEntity> List<T> find(String query, Map<String, Object> params, String objectType) {
		Class<T> eCls = metaInfo.<T>retrieveEntityType(objectType).getJavaType();
		TypedQuery<T> typedQuery =  entityManager.createQuery(query, eCls);
		params.entrySet().stream().forEach(e -> typedQuery.setParameter(e.getKey(), e.getValue()));
		return typedQuery.getResultList();
	}
	

}
