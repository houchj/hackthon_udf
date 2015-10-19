package com.sap.hackthon.services;

import java.util.List;
import java.util.Map;

import com.sap.hackthon.entity.BasicEntity;
import com.sap.hackthon.entity.UserDefineEntity;

/**
 * 
 */

/**
 * @author I310717
 *
 */
public interface EntityService {

    public BasicEntity create(BasicEntity entity);

    public BasicEntity update(BasicEntity entity);

    public void delete(Long id, String objectType);

    public <T extends BasicEntity> T get(Long id, String objectType);

    public <T extends BasicEntity> List<T> list(String objectType);

    public <T extends BasicEntity> List<T> find(String query, Map<String, Object> params, String objectType);
}
