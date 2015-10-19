package com.sap.hackthon.services;

import java.util.List;

import com.sap.hackthon.entity.UserDefineEntity;
import com.sap.hackthon.entity.PropertyMeta;

/**
 * 
 */

/**
 * @author I310717
 *
 */
public interface EntityService {

    public UserDefineEntity create(UserDefineEntity entity, String tanentId);

    public UserDefineEntity update(UserDefineEntity entity, String tanentId);

    public boolean delete(Long id, String objectType);

    public UserDefineEntity get(Long id, String tanentId, String objectType);

    public List<UserDefineEntity> list(String objectType, String tanentId);

    public List<UserDefineEntity> find(String query);
}
