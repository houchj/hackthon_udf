package com.sap.hackthon.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.repository.PropertyMetaRepository;
import com.sap.hackthon.utils.UDFTypeEnum;

@Service
@Transactional
public class PropertyMetaServiceImpl implements PropertyMetaService {

	@Autowired
	PropertyMetaRepository propertyMetaRepository;

	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean create(PropertyMeta propertyMeta) {
		
		propertyMetaRepository.saveAndFlush(propertyMeta);
		
		StringBuffer alterTableAddColumn = new StringBuffer();
		
		alterTableAddColumn
		.append("alter table ")
		.append(propertyMeta.getObjectName())
		.append(" add(")
		.append(propertyMeta.getInternalName())
		.append(" ")
		.append(propertyMeta.getType().equals(UDFTypeEnum.NVARCHAR) ? propertyMeta.getType() + "(200)" : propertyMeta.getType())
		.append(")");
		
		jdbcTemplate.execute(alterTableAddColumn.toString());
		
		return true;
	}

	@Override
	public boolean delete(Long id) {
		
		PropertyMeta propertyMeta = propertyMetaRepository.findOne(id);
		
		StringBuffer alterTableDropColumn = new StringBuffer();
		
		alterTableDropColumn
		.append("alter table ")
		.append(propertyMeta.getObjectName())
		.append(" drop(")
		.append(propertyMeta.getInternalName())
		.append(" ")
		.append(")");
		
		jdbcTemplate.execute(alterTableDropColumn.toString());
		
		propertyMetaRepository.delete(propertyMeta);
		
		return true;
	}

	@Override
	public boolean update(PropertyMeta propertyMeta) {
		
		propertyMetaRepository.saveAndFlush(propertyMeta);
		
		return true;
	}

	@Override
	public List<PropertyMeta> getByTenantIdAndObjectName(String tenantId,
			String objectName) {
		return propertyMetaRepository.findByTenantIdAndObjectName(tenantId, objectName);
	}

}
