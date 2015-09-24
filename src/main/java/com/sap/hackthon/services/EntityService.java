package com.sap.hackthon.services;

import java.util.List;

import com.sap.hackthon.entity.DynamicEntity;
import com.sap.hackthon.entity.PropertyMeta;

/**
 * 
 */

/**
 * @author I310717
 *
 */
public interface EntityService {

    public DynamicEntity create(DynamicEntity entity, Long tanentId);

    public DynamicEntity update(DynamicEntity entity, Long tanentId);

    public boolean delete(Long id, String objectType);

    public DynamicEntity get(Long id, String objectType);

    public List<DynamicEntity> list(String objectType, Long tanentId);

    public PropertyMeta getEntityMeta(String objectType, Long tanentId);

}
