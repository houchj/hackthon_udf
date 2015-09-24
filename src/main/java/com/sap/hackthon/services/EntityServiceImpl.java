package com.sap.hackthon.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sap.hackthon.entity.DynamicEntity;

@Service
public class EntityServiceImpl implements EntityService {

	@Override
	public DynamicEntity create(DynamicEntity entity, Long tanentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DynamicEntity update(DynamicEntity entity, Long tanentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id, String objectType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DynamicEntity get(Long id, String objectType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DynamicEntity> list(String objectType, Long tanentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
