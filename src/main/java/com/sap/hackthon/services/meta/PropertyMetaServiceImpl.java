package com.sap.hackthon.services.meta;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hackthon.entity.BasicEntity;
import com.sap.hackthon.entity.GlobalSettings;
import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.enumeration.UDFTypeEnum;
import com.sap.hackthon.framework.inject.OrmInjector;
import com.sap.hackthon.framework.inject.UDFAttributeAccessor;
import com.sap.hackthon.repository.PropertyMetaRepository;
import com.sap.hackthon.utils.GlobalConstants;

@Service
@Transactional(rollbackFor = Exception.class)
public class PropertyMetaServiceImpl implements PropertyMetaService {

	@Autowired
	private PropertyMetaRepository propertyMetaRepository;
	
	@Autowired
	private GlobalSettings settings;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private OrmInjector injector;
	
	
	
	@Override
	public boolean create(PropertyMeta propertyMeta) {
		propertyMetaRepository.saveAndFlush(propertyMeta);
		jdbcTemplate.execute(this.addColumn(propertyMeta.getObjectType(), propertyMeta.getInternalName(), propertyMeta.getType()));
		try{
			jdbcTemplate.execute(this.dropView(propertyMeta.getObjectType()));
		}
		catch(Exception e) {
		}
		jdbcTemplate.execute(this.createView(propertyMeta.getObjectType()));
		return true;
	}
	
	// drop view T_ORDER_TENANT1005_VIEW
	private String dropView(String objectType) {
		StringBuffer dropView = new StringBuffer();
		String tenantId = settings.getVariable(GlobalConstants.TENANT).toString();
		String table = injector.retrieveTableName(objectType);
		dropView
		.append("drop view ")
		.append(table)
		.append("_")
		.append(tenantId)
		.append("_")
		.append("VIEW");
		return dropView.toString();
	}

	// create view T_ORDER_TENANT1005_VIEW as select order_id from T_ORDER
	private String createView(String objectType) {
		List<PropertyMeta> propertiesMeta = propertyMetaRepository.findByObjectType(objectType);
		String tenantId = settings.getVariable(GlobalConstants.TENANT).toString();
		String table = injector.retrieveTableName(objectType);
		StringBuffer createView = new StringBuffer();
		createView.append("create view ").append(table).append("_").append(tenantId).append("_").append("VIEW as select ");
		for(PropertyMeta propertyMeta : propertiesMeta) {
			createView.append(" ").append(propertyMeta.getInternalName()).append(" as ").append(propertyMeta.getDisplayName()).append(", ");
		}
		createView.deleteCharAt(createView.lastIndexOf(","));
		createView.append(" from ").append(table);
		return createView.toString();
	}
	
	private String addColumn(String objectType, String internalName, UDFTypeEnum type) {
		StringBuffer alterTableAddColumn = new StringBuffer();
		String table = injector.retrieveTableName(objectType);
		alterTableAddColumn
		.append("alter table ")
		.append(table)
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
			jdbcTemplate.execute(this.dropView(propertyMeta.getObjectType()));
		}
		catch(Exception e) {
		}
		jdbcTemplate.execute(this.createView( propertyMeta.getObjectType()));
		return true;
	}
	
	private String dropColumn(PropertyMeta propertyMeta) {
		String table = injector.retrieveTableName(propertyMeta.getObjectType());
		StringBuffer alterTableDropColumn = new StringBuffer();
		alterTableDropColumn
		.append("alter table ")
		.append(table)
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
			jdbcTemplate.execute(this.dropView(propertyMeta.getObjectType()));
		}
		catch(Exception e) {
		}
		jdbcTemplate.execute(this.createView(propertyMeta.getObjectType()));
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

}
