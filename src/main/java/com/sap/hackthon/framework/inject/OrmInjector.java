package com.sap.hackthon.framework.inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.jpa.JpaHelper;
import org.eclipse.persistence.mappings.AttributeAccessor;
import org.eclipse.persistence.mappings.DirectToFieldMapping;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.hackthon.entity.BasicEntity;
import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.repository.PropertyMetaRepository;

@Component
public class OrmInjector {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PropertyMetaRepository propertyMetaRepository;
	
	public void scanAndInstallProperties() {
		
		JpaEntityManager jpaEntityManager = JpaHelper.getEntityManager(entityManager);
		DatabaseSession dbSession = jpaEntityManager.getDatabaseSession();
		List<PropertyMeta> propertyMetas = propertyMetaRepository.findAll();
		Map<Class<? extends BasicEntity>, ClassDescriptor> descriptors = new HashMap<Class<? extends BasicEntity>, ClassDescriptor>();
		propertyMetas.forEach(meta -> {
			EntityType<? extends BasicEntity> eType = retrieveEntityType(meta.getObjectType());
			ClassDescriptor descriptor = dbSession.getDescriptor(eType.getJavaType());
			AttributeAccessor accessor = new UDFAttributeAccessor();
			accessor.setAttributeName(meta.getDisplayName());
			DirectToFieldMapping udfMapping = new DirectToFieldMapping();
			udfMapping.setFieldName(meta.getInternalName());
			udfMapping.setAttributeAccessor(accessor);
			descriptor.addMapping(udfMapping);
			udfMapping.initialize((AbstractSession)dbSession);
			descriptor.getFields().addAll(udfMapping.getFields());
			descriptor.getAllFields().addAll(udfMapping.getFields());
			descriptors.put(eType.getJavaType(), descriptor);
		});
		descriptors.values().stream().forEach(descriptor -> {
			descriptor.getObjectBuilder().initialize((AbstractSession)dbSession);
			descriptor.initialize(descriptor.getQueryManager(), (AbstractSession)dbSession);
		});
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BasicEntity> EntityType<T> retrieveEntityType(String objectType){
		Optional<EntityType<?>> res = entityManager.getMetamodel().getEntities().stream().
				filter(e -> BasicEntity.class.isAssignableFrom(e.getJavaType()) && objectType.equals(e.getName())).findFirst();
		return (EntityType<T>) res.get();
	}
	
	public String retrieveTableName(String objectType){
		EntityType<? extends BasicEntity> entityType = retrieveEntityType(objectType);
		Class<? extends BasicEntity> eCls = entityType.getJavaType();
		Table table = eCls.getAnnotation(Table.class);
		if(table == null || table.name().isEmpty()){
			return eCls.getSimpleName();
		}
		return table.name();
	}
}
