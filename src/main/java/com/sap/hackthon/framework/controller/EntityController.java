package com.sap.hackthon.framework.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.hackthon.framework.beans.BasicEntity;
import com.sap.hackthon.services.biz.EntityService;

@RestController
public class EntityController {

	@Autowired
	private EntityService entityService;
	
    @RequestMapping(value = "/entity", method = RequestMethod.POST)
    public BasicEntity create(BasicEntity entity, HttpServletRequest request) {
        return entityService.create(entity);
    }

    @RequestMapping(value = "/entity", method = RequestMethod.PUT)
    public BasicEntity update(BasicEntity entity, HttpServletRequest request) {
    	return entityService.update(entity);
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.DELETE)
    public boolean delete(String objectType, @PathVariable("id") Long entityId) {
        entityService.delete(entityId, objectType);
        return true;
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.GET)
    public BasicEntity get(String objectType, @PathVariable("id") Long entityId,
            HttpServletRequest request) {
        return entityService.get(entityId, objectType);
    }

    @RequestMapping(value = "/entities", method = RequestMethod.POST)
    public List<BasicEntity> list(String objectType, HttpServletRequest request) {
        return entityService.list(objectType);
    }

}
