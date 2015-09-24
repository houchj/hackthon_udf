package com.sap.hackthon.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.hackthon.entity.DynamicEntity;
import com.sap.hackthon.services.EntityService;
import com.sap.hackthon.utils.GlobalConstants;

@Controller
public class EntityController {

    @Autowired
    private EntityService service;

    @RequestMapping(value = "/tenant", method = RequestMethod.POST)
    public void tenant(@RequestParam Long tenantId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(GlobalConstants.TENANT, tenantId);
    }

    @RequestMapping(value = "/entity", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> create(@RequestBody String objectType,
            @RequestBody Map<String, Object> entity, HttpServletRequest request) {
        if (objectType == null || entity == null) {
            return null;
        }
        DynamicEntity dynamicEntity = new DynamicEntity(objectType, entity);
        HttpSession session = request.getSession();
        Long tenantId = (Long) session.getAttribute(GlobalConstants.TENANT);
        if (tenantId == null) {
            return null;
        }
        dynamicEntity = service.create(dynamicEntity, tenantId);
        if (dynamicEntity == null) {
            return null;
        }
        return dynamicEntity.getPropertities();
    }

    @RequestMapping(value = "/entity", method = RequestMethod.PUT)
    public @ResponseBody Map<String, Object> update(@RequestBody String objectType,
            @RequestBody Map<String, Object> entity, HttpServletRequest request) {
        if (objectType == null || entity == null) {
            return null;
        }
        if (entity.get("id") == null) {
            return null;
        }

        DynamicEntity dynamicEntity = new DynamicEntity(objectType, entity);
        HttpSession session = request.getSession();
        Long tenantId = (Long) session.getAttribute(GlobalConstants.TENANT);
        if (tenantId == null) {
            return null;
        }
        dynamicEntity = service.update(dynamicEntity, tenantId);
        if (dynamicEntity == null) {
            return null;
        }
        return dynamicEntity.getPropertities();
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.DELETE)
    public @ResponseBody boolean delete(@RequestBody String objectType, @PathVariable("id") Long entityId) {
        if (objectType == null || entityId == null) {
            return false;
        }
        return service.delete(entityId, objectType);
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> get(@RequestBody String objectType, @PathVariable("id") Long entityId) {
        if (objectType == null || entityId == null) {
            return null;
        }
        DynamicEntity entity = service.get(entityId, objectType);
        return entity.getPropertities();
    }
}
