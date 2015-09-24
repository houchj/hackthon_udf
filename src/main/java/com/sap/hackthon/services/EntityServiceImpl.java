package com.sap.hackthon.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.hackthon.entity.DynamicEntity;
import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.enumeration.UDFTypeEnum;
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
    	
        return null;
    }

    @Override
    public List<PropertyMeta> getEntityMeta(String objectType, String tanentId) {
        return null;
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
