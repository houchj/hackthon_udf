package com.sap.hackthon.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.hackthon.entity.DynamicEntity;
import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.repository.PropertyMetaRepository;

@Service
public class EntityServiceImpl implements EntityService {

	@Autowired
	PropertyMetaRepository propertyMetaRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public DynamicEntity update(DynamicEntity entity, String tanentId) {
		List<PropertyMeta> metas=this.getMetas(entity, tanentId);
		if(metas.isEmpty()){
			throw new RuntimeException("No meta found for"+entity.getObjectType());
		}
		String updateStr=this.buildUpdateClause(entity, tanentId, metas);
		entityManager.createNativeQuery(updateStr).executeUpdate();
		
		return null;
	}

    @Override
    public DynamicEntity create(DynamicEntity entity, String tanentId) {
		List<PropertyMeta> metas=this.getMetas(entity, tanentId);
		if(metas.isEmpty()){
			throw new RuntimeException("No meta found for"+entity.getObjectType());
		}
		String insertStr=this.buildInsertClause(entity, tanentId, metas);
		entityManager.createNativeQuery(insertStr).executeUpdate();

        return null;
    }

    @Override
    public boolean delete(Long id, String objectType) {
		String deleteStr=this.buildDeleteClause(id,objectType);
		entityManager.createNativeQuery(deleteStr).executeUpdate();

		return false;
    }

    @Override
    public DynamicEntity get(Long id, String objectType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DynamicEntity> list(String objectType, String tanentId) {
        return this.listSingle(objectType, tanentId);
    }

    @Override
    public List<PropertyMeta> getEntityMeta(String objectType, String tanentId) {
        return null;
    }
    
    public List<DynamicEntity> listSingle(String objectType, String tanentId, String orderId) {
    	List<PropertyMeta> metas=this.getMetas(objectType, tanentId);
    	Map<String,String> internalNameDisplayNameMap=this.getInternalNameDisplayNameMap(metas);
    	Map<String,String> conditionMap=new HashMap<String, String>();
    	conditionMap.put("TENANT_ID", tanentId);
    	if(orderId!=null){
        	conditionMap.put("ORDER_ID", orderId);
    	}
    	
    	List<DynamicEntity> entities=new ArrayList<DynamicEntity>();
    	
    	String selectStr=this.buildSelectClause(objectType, internalNameDisplayNameMap, conditionMap);
    	System.out.println(selectStr);
    	List reslt=entityManager.createNativeQuery(selectStr).getResultList();
    	String[] keys = internalNameDisplayNameMap.keySet().toArray(new String[]{});
    	for (Object rslt : reslt) {
    		Object[] objs=(Object[])rslt; 
    		DynamicEntity entity=new DynamicEntity(objectType);
    		entities.add(entity);
    		Map<String, Object> propertities=new HashMap<String, Object>();
    		for (int i = 0; i < objs.length; i++) {
    			propertities.put(keys[i], objs[i]);
			}
    		entity.setPropertities(propertities);
		}
    	
        return entities;
    }
    public List<DynamicEntity> listSingle(String objectType, String tanentId) {
    	return listSingle(objectType, tanentId, null);
    }

    private Map<String, String> getInternalNameDisplayNameMap(List<PropertyMeta> metas){
    	Map<String,String> internalNameDisplayNameMap=new HashMap<String, String>();
    	for (PropertyMeta propertyMeta : metas) {
			internalNameDisplayNameMap.put(propertyMeta.getInternalName(), propertyMeta.getDisplayName());
		}
    	
    	return internalNameDisplayNameMap;
    }
    
    private Map<String, Object> getInternalNameValueMap(Map<String, Object> propertities, List<PropertyMeta> metas){
    	Map<String,Object> internalNameValueMap=new HashMap<String, Object>();
    	for (PropertyMeta propertyMeta : metas) {
			internalNameValueMap.put(propertyMeta.getInternalName(), propertities.get(propertyMeta.getDisplayName()));
		}
    	
    	return internalNameValueMap;
    }

    private List<PropertyMeta> getMetas(
			DynamicEntity dynamicEntity, String tanentId) {
		//TODO display name could not duplicated
		Map<String, String> map = new HashMap<String, String>();
		String jpql = "select pm from "
				+ PropertyMeta.class.getName()
				+ " pm where pm.tenantId=:tenantId and pm.objectName=:objectName and displayName in :displayName";
		List<PropertyMeta> metas = entityManager
				.createQuery(jpql, PropertyMeta.class)
				.setParameter("tenantId", tanentId)
				.setParameter("objectName", dynamicEntity.getObjectType())
				.setParameter("displayName",
						dynamicEntity.getPropertities().keySet())
				.getResultList();
		
		return metas;
	}
	
	
    private List<PropertyMeta> getMetas(
			String objectType, String tanentId) {
		//TODO display name could not duplicated
		Map<String, String> map = new HashMap<String, String>();
		String jpql = "select pm from "
				+ PropertyMeta.class.getName()
				+ " pm where pm.tenantId=:tenantId and pm.objectName=:objectName";
		List<PropertyMeta> metas = entityManager
				.createQuery(jpql, PropertyMeta.class)
				.setParameter("tenantId", tanentId)
				.setParameter("objectName", objectType)
				.getResultList();
		
		return metas;
	}

    private String buildInsertClause(DynamicEntity dynamicEntity, String tanentId, List<PropertyMeta> metas){
		String tbName=dynamicEntity.getObjectType();
		String orderIdColumnName="ORDER_ID";

		StringBuilder colNameBuilder=new StringBuilder();
		StringBuilder colValueBuilder=new StringBuilder();
		colNameBuilder.append("TENANT_ID");
		colValueBuilder.append("'");
		colValueBuilder.append(tanentId);
		colValueBuilder.append("'");

		for (PropertyMeta propertyMeta : metas) {
			if(colNameBuilder.length()!=0){
				colNameBuilder.append(",");
				colValueBuilder.append(",");
			}
			String colName=propertyMeta.getDisplayName();
			colNameBuilder.append(colName);
			colValueBuilder.append("'");
			colValueBuilder.append(dynamicEntity.getProperty(colName));
			colValueBuilder.append("'");
		}
		
		StringBuilder builder=new StringBuilder();
		builder.append("INSERT INTO ");
		builder.append(tbName);
		builder.append("(");
		builder.append(colNameBuilder.toString());
		builder.append(")");
		builder.append(" ");
		builder.append("VALUES(");
		builder.append(colValueBuilder.toString());
		builder.append(")");
		builder.append(" ");
		
		return builder.toString();
	}

	private String buildSelectClause(String tbName, Map<String,String> internalNameDisplayNameMap, Map<String,String> conditionMap){
		StringBuilder whereBuilder=new StringBuilder();
		for (Entry<String, String> entry : conditionMap.entrySet()) {
			if(whereBuilder.length()!=0){
				whereBuilder.append(" AND ");
			}
			whereBuilder.append(entry.getKey());
			whereBuilder.append("=");
			whereBuilder.append("'");
			whereBuilder.append(entry.getValue());
			whereBuilder.append("'");
		}

		StringBuilder selectBuilder=new StringBuilder();
		for (Entry<String, String> entry : internalNameDisplayNameMap.entrySet()) {
			if(selectBuilder.length()!=0){
				selectBuilder.append(", ");
			}
			selectBuilder.append(entry.getKey());
			selectBuilder.append(" ");
			selectBuilder.append(entry.getValue());
		}
		
		StringBuilder builder=new StringBuilder();
		builder.append("SELECT ");
		builder.append(selectBuilder.toString());
		builder.append(" FROM ");
		builder.append(tbName);
		builder.append(" ");
		builder.append("WHERE ");
		builder.append(whereBuilder.toString());
		builder.append(" ");
		
		return builder.toString();
	}
	
	private String buildSelectClause(DynamicEntity dynamicEntity, String tanentId, List<PropertyMeta> metas){
		String tbName=dynamicEntity.getObjectType();
		String orderIdColumnName="ORDER_ID";
		
		StringBuilder whereBuilder=new StringBuilder();
		whereBuilder.append("TENANT_ID");
		whereBuilder.append("=");
		whereBuilder.append("'");
		whereBuilder.append(tanentId);
		whereBuilder.append("'");
		
		whereBuilder.append(" AND ");

		Object oi=dynamicEntity.getProperty(orderIdColumnName);
		if(oi==null){
			throw new RuntimeException("No order id!");
		}
		whereBuilder.append(orderIdColumnName);
		whereBuilder.append("=");
		whereBuilder.append("'");
		whereBuilder.append(oi.toString());
		whereBuilder.append("'");


		StringBuilder selectBuilder=new StringBuilder();
		for (PropertyMeta propertyMeta : metas) {
			String colName=propertyMeta.getDisplayName();
			if(selectBuilder.length()!=0){
				selectBuilder.append(",");
			}
			selectBuilder.append(colName);
		}
		
		StringBuilder builder=new StringBuilder();
		builder.append("SELECT ");
		builder.append(selectBuilder.toString());
		builder.append(" FROM ");
		builder.append(tbName);
		builder.append(" ");
		builder.append("WHERE ");
		builder.append(whereBuilder.toString());
		builder.append(" ");
		
		return builder.toString();
	}
	
	private String buildUpdateClause(DynamicEntity dynamicEntity, String tanentId, List<PropertyMeta> metas){
		String tbName=dynamicEntity.getObjectType();
		String orderIdColumnName="ORDER_ID";
		
		StringBuilder whereBuilder=new StringBuilder();
		whereBuilder.append("TENANT_ID");
		whereBuilder.append("=");
		whereBuilder.append("'");
		whereBuilder.append(tanentId);
		whereBuilder.append("'");
		
		whereBuilder.append(" AND ");

		Object oi=dynamicEntity.getProperty(orderIdColumnName);
		if(oi==null){
			throw new RuntimeException("No order id!");
		}
		whereBuilder.append(orderIdColumnName);
		whereBuilder.append("=");
		whereBuilder.append("'");
		whereBuilder.append(oi.toString());
		whereBuilder.append("'");


		StringBuilder setBuilder=new StringBuilder();
		for (PropertyMeta propertyMeta : metas) {
			String colName=propertyMeta.getDisplayName();
			if(colName.trim().equalsIgnoreCase(orderIdColumnName)){
				continue;
			}
			if(setBuilder.length()!=0){
				setBuilder.append(",");
			}
			setBuilder.append(colName);
			setBuilder.append("=");
			setBuilder.append("'");
			setBuilder.append(dynamicEntity.getProperty(colName));
			setBuilder.append("'");
		}
		
		StringBuilder builder=new StringBuilder();
		builder.append("UPDATE ");
		builder.append(tbName);
		builder.append(" ");
		builder.append("SET ");
		builder.append(setBuilder.toString());
		builder.append(" ");
		builder.append("WHERE ");
		builder.append(whereBuilder.toString());
		builder.append(" ");
		
		return builder.toString();
	}

	private String buildDeleteClause(Long id, String tbName){
		StringBuilder whereBuilder=new StringBuilder();
		whereBuilder.append("ID");
		whereBuilder.append("=");
		whereBuilder.append("'");
		whereBuilder.append(id.toString());
		whereBuilder.append("'");
		
		StringBuilder builder=new StringBuilder();
		builder.append("DELETE FROM ");
		builder.append(tbName);
		builder.append(" ");
		builder.append("WHERE ");
		builder.append(whereBuilder.toString());
		builder.append(" ");
		
		return builder.toString();
	}

	private String buildDeleteClause(DynamicEntity dynamicEntity, String tanentId, List<PropertyMeta> metas){
		String tbName=dynamicEntity.getObjectType();
		String orderIdColumnName="ORDER_ID";

		StringBuilder whereBuilder=new StringBuilder();
		whereBuilder.append("TENANT_ID");
		whereBuilder.append("=");
		whereBuilder.append("'");
		whereBuilder.append(tanentId);
		whereBuilder.append("'");
		
		whereBuilder.append(" AND ");

		Object oi=dynamicEntity.getProperty(orderIdColumnName);
		if(oi==null){
			throw new RuntimeException("No order id!");
		}
		whereBuilder.append(orderIdColumnName);
		whereBuilder.append("=");
		whereBuilder.append("'");
		whereBuilder.append(oi.toString());
		whereBuilder.append("'");


		StringBuilder builder=new StringBuilder();
		builder.append("DELETE FROM ");
		builder.append(tbName);
		builder.append(" ");
		builder.append("WHERE ");
		builder.append(whereBuilder.toString());
		builder.append(" ");
		
		return builder.toString();
	}
}
