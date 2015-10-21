package com.sap.hackthon.framework.inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import com.sap.hackthon.framework.mata.MetaInfoRetriever;
import com.sap.hackthon.repository.PropertyMetaRepository;

@Component
public class OrmInjector {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private MetaInfoRetriever metaInfo;
	
	@Autowired
	private PropertyMetaRepository propertyMetaRepository;
	
	public void injectUDFMappings() {
		
		JpaEntityManager jpaEntityManager = JpaHelper.getEntityManager(entityManager);
		DatabaseSession dbSession = jpaEntityManager.getDatabaseSession();
		List<PropertyMeta> propertyMetas = propertyMetaRepository.findAll();
		Map<Class<? extends BasicEntity>, ClassDescriptor> descriptors = new HashMap<Class<? extends BasicEntity>, ClassDescriptor>();
		
		propertyMetas.forEach(meta -> {
			EntityType<? extends BasicEntity> eType = metaInfo.retrieveEntityType(meta.getObjectType());
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
	
	
}