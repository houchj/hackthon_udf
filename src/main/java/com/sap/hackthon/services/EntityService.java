package com.sap.hackthon.services;

import java.util.List;

import com.sap.hackthon.entity.DynamicEntity;

/**
 * 
 */

/**
 * @author I310717
 *
 */
public interface EntityService {

    public void save(DynamicEntity entity, Long tanentId);

    public void update(DynamicEntity entity);

    public void delete(Long id, String objectType);

    public DynamicEntity get(Long id, String objectType);

    public List<DynamicEntity> list(String objectType);

}
