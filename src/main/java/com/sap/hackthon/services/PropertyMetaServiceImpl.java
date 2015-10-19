package com.sap.hackthon.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.metamodel.EntityType;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.jpa.JpaHelper;
import org.eclipse.persistence.mappings.AttributeAccessor;
import org.eclipse.persistence.mappings.DirectToFieldMapping;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hackthon.entity.BasicEntity;
import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.enumeration.UDFTypeEnum;
import com.sap.hackthon.repository.PropertyMetaRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class PropertyMetaServiceImpl extends DataService implements PropertyMetaService {

	@Autowired
	private PropertyMetaRepository propertyMetaRepository;

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean create(PropertyMeta propertyMeta) {
		propertyMetaRepository.saveAndFlush(propertyMeta);
		jdbcTemplate.execute(this.addColumn(propertyMeta.getObjectType(), propertyMeta.getInternalName(), propertyMeta.getType()));
		try{
			jdbcTemplate.execute(this.dropView(propertyMeta.getTenantId(), propertyMeta.getObjectType()));
		}
		catch(Exception e) {
		}
		jdbcTemplate.execute(this.createView(propertyMeta.getTenantId(), propertyMeta.getObjectType()));
		return true;
	}
	
	// drop view T_ORDER_TENANT1005_VIEW
	private String dropView(String tenantId, String objectName) {
		StringBuffer dropView = new StringBuffer();
		dropView
		.append("drop view ")
		.append(objectName)
		.append("_")
		.append(tenantId)
		.append("_")
		.append("VIEW");
		return dropView.toString();
	}

	// create view T_ORDER_TENANT1005_VIEW as select order_id from T_ORDER
	private String createView(String tenantId, String objectType) {
		List<PropertyMeta> propertiesMeta = propertyMetaRepository.findByObjectType(objectType);
		StringBuffer createView = new StringBuffer();
		createView.append("create view ").append(objectType).append("_").append(tenantId).append("_").append("VIEW as select ");
		for(PropertyMeta propertyMeta : propertiesMeta) {
			createView.append(" ").append(propertyMeta.getInternalName()).append(" as ").append(propertyMeta.getDisplayName()).append(", ");
		}
		createView.deleteCharAt(createView.lastIndexOf(","));
		createView.append(" from ").append(objectType);
		return createView.toString();
	}
	
	private String addColumn(String objectName, String internalName, UDFTypeEnum type) {
		StringBuffer alterTableAddColumn = new StringBuffer();
		alterTableAddColumn
		.append("alter table ")
		.append(objectName)
		.append(" add(")
		.append(internalName)
		.append(" ")
		.append(type.equals(UDFTypeEnum.NVARCHAR) ? type + "(200)" : type)
		.append(")");
		return alterTableAddColumn.toString();
	}

	@Override
	public boolean delete(Long id) {
		PropertyMeta propertyMeta = propertyMetaRepository.findOne(id);
		jdbcTemplate.execute(this.dropColumn(propertyMeta));
		propertyMetaRepository.delete(propertyMeta);
		try{
			jdbcTemplate.execute(this.dropView(propertyMeta.getTenantId(), propertyMeta.getObjectType()));
		}
		catch(Exception e) {
		}
		jdbcTemplate.execute(this.createView(propertyMeta.getTenantId(), propertyMeta.getObjectType()));
		return true;
	}
	
	private String dropColumn(PropertyMeta propertyMeta) {
		StringBuffer alterTableDropColumn = new StringBuffer();
		alterTableDropColumn
		.append("alter table ")
		.append(propertyMeta.getObjectType())
		.append(" drop(")
		.append(propertyMeta.getInternalName())
		.append(" ")
		.append(")");
		return alterTableDropColumn.toString();
	}

	@Override
	public boolean update(PropertyMeta propertyMeta) {
		propertyMetaRepository.saveAndFlush(propertyMeta);
		try{
			jdbcTemplate.execute(this.dropView(propertyMeta.getTenantId(), propertyMeta.getObjectType()));
		}
		catch(Exception e) {
		}
		jdbcTemplate.execute(this.createView(propertyMeta.getTenantId(), propertyMeta.getObjectType()));
		return true;
	}

	@Override
	public List<PropertyMeta> getByObjectType(String objectType) {
		return propertyMetaRepository.findByObjectType(objectType);
	}

	@Override
	public PropertyMeta get(Long id) {
		return propertyMetaRepository.findOne(id);
	}

	@Override
	public int getMaxParamIndexByObjectTypeAndType(String objectType, UDFTypeEnum type) {
		return propertyMetaRepository.findMaxParamIndexByObjectTypeAndType(objectType, type);
	}

	@Override
	public boolean getByObjectTypeAndDisplayName(String objectType, String displayName) {
		List<PropertyMeta> propertiesMeta = propertyMetaRepository.findByObjectTypeAndDisplayName(objectType, displayName);
		if(propertiesMeta != null && propertiesMeta.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
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
			descriptors.put(eType.getJavaType(), descriptor);
			;
		});
		descriptors.values().stream().forEach(descriptor -> descriptor.getObjectBuilder().initialize((AbstractSession)dbSession));
	}
}
