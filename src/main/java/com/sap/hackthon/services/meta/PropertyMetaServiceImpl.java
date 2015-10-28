package com.sap.hackthon.services.meta;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hackthon.framework.beans.PropertyMeta;
import com.sap.hackthon.framework.beans.UserSettings;
import com.sap.hackthon.framework.beans.VersionObserver;
import com.sap.hackthon.framework.enumeration.UDFType;
import com.sap.hackthon.framework.mata.MetaInfoRetriever;
import com.sap.hackthon.framework.utils.CommonUtils;
import com.sap.hackthon.framework.utils.GlobalConstants;
import com.sap.hackthon.repository.PropertyMetaRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class PropertyMetaServiceImpl implements PropertyMetaService {

	@Autowired
	private PropertyMetaRepository propertyMetaRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UserSettings settings;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private MetaInfoRetriever metaInfo;
	
	
	
	@Override
	public boolean create(PropertyMeta propertyMeta) {
		propertyMetaRepository.saveAndFlush(propertyMeta);
		jdbcTemplate.execute(this.addColumn(propertyMeta.getObjectType(), propertyMeta.getInternalName(), propertyMeta.getType()));
		try{
			jdbcTemplate.execute(this.dropView(propertyMeta.getObjectType()));
			updateMetaVersion();
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
		String table = metaInfo.retrieveTableName(objectType);
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
		String table = metaInfo.retrieveTableName(objectType);
		StringBuffer createView = new StringBuffer();
		createView.append("create view ").append(table).append("_").append(tenantId).append("_").append("VIEW as select ");
		for(PropertyMeta propertyMeta : propertiesMeta) {
			createView.append(" ").append(propertyMeta.getInternalName()).append(" as ").append(propertyMeta.getDisplayName()).append(", ");
		}
		createView.deleteCharAt(createView.lastIndexOf(","));
		createView.append(" from ").append(table);
		return createView.toString();
	}
	
	private String addColumn(String objectType, String internalName, UDFType type) {
		StringBuffer alterTableAddColumn = new StringBuffer();
		String table = metaInfo.retrieveTableName(objectType);
		alterTableAddColumn
		.append("alter table ")
		.append(table)
		.append(" add(")
		.append(internalName)
		.append(" ")
		.append(type.equals(UDFType.NVARCHAR) ? type + "(200)" : type)
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
			updateMetaVersion();
		}
		catch(Exception e) {
		}
		jdbcTemplate.execute(this.createView( propertyMeta.getObjectType()));
		return true;
	}
	
	private String dropColumn(PropertyMeta propertyMeta) {
		String table = metaInfo.retrieveTableName(propertyMeta.getObjectType());
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
			updateMetaVersion();
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
	public int getMaxParamIndexByObjectTypeAndType(String objectType, UDFType type) {
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
	
	private void updateMetaVersion(){
		VersionObserver vo = metaInfo.versionOfPropertyMeta();
		String version = CommonUtils.uniqueString(25, false);
		if(vo == null){
			vo = new VersionObserver();
			vo.setObserverType(GlobalConstants.VO_TYPE_PROPERTY_META);
			vo.setVersion(version);
			entityManager.persist(vo);
		} else {
			vo.setVersion(version);
			entityManager.merge(vo);
		}
	}
}
