package com.sap.hackthon.services;

import java.util.List;
import java.util.Map;

import com.sap.hackthon.entity.BasicEntity;

/**
 * 
 */

/**
 * @author I310717
 *
 */
public interface EntityService {

	public <T extends BasicEntity> T create(T entity);
	
	public <T extends BasicEntity> T update(T entity);

	public <T extends BasicEntity> void delete(Long id, String objectType);

    public <T extends BasicEntity> T get(Long id, String objectType);

    public <T extends BasicEntity> List<T> list(String objectType);

    public <T extends BasicEntity> List<T> find(String query, Map<String, Object> params, String objectType);
}
