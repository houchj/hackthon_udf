package com.sap.hackthon.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hackthon.entity.UserDefineEntity;
import com.sap.hackthon.repository.PropertyMetaRepository;

@Service
@Transactional
public class EntityServiceImpl implements EntityService {

    @Autowired
    PropertyMetaRepository propertyMetaRepository;

    @PersistenceContext
    EntityManager entityManager;

	@Override
	public UserDefineEntity create(UserDefineEntity entity, String tanentId) {
		return null;
	}

	@Override
	public UserDefineEntity update(UserDefineEntity entity, String tanentId) {
		return null;
	}

	@Override
	public boolean delete(Long id, String objectType) {
		return false;
	}

	@Override
	public UserDefineEntity get(Long id, String tanentId, String objectType) {
		return null;
	}

	@Override
	public List<UserDefineEntity> list(String objectType, String tanentId) {
		return null;
	}

	@Override
	public List<UserDefineEntity> find(String query) {
		// TODO Auto-generated method stub
		return null;
	}

    
}
