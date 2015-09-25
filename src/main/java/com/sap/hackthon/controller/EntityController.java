package com.sap.hackthon.controller;

import java.util.List;

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
import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.services.EntityService;
import com.sap.hackthon.utils.EntityConvertor;
import com.sap.hackthon.utils.GlobalConstants;

@Controller
public class EntityController {

    @Autowired
    private EntityService service;

    @RequestMapping(value = "/entity", method = RequestMethod.POST)
    public @ResponseBody DynamicEntity create(@RequestBody DynamicEntity entity, HttpServletRequest request) {
        if (entity == null || entity.getObjectType() == null) {
            return null;
        }
        HttpSession session = request.getSession();
        String tenantId = (String) session.getAttribute(GlobalConstants.TENANT);
        if (tenantId == null) {
            return null;
        }
        EntityConvertor convertor = EntityConvertor.getInstance();
        List<PropertyMeta> entityMeta = service.getEntityMeta(entity.getObjectType(), tenantId);
        DynamicEntity dynamicEntity = convertor.convertEntity(entity, entityMeta);
        return service.create(dynamicEntity, tenantId);
    }

    @RequestMapping(value = "/entity", method = RequestMethod.PUT)
    public @ResponseBody DynamicEntity update(@RequestBody DynamicEntity entity, HttpServletRequest request) {
        if (entity == null || entity.getObjectType() == null) {
            return null;
        }
        if (entity.getProperty("id") == null) {
            return null;
        }

        HttpSession session = request.getSession();
        String tenantId = (String) session.getAttribute(GlobalConstants.TENANT);
        if (tenantId == null) {
            return null;
        }
        EntityConvertor convertor = EntityConvertor.getInstance();
        List<PropertyMeta> entityMeta = service.getEntityMeta(entity.getObjectType(), tenantId);
        DynamicEntity dynamicEntity = convertor.convertEntity(entity, entityMeta);
        return service.update(dynamicEntity, tenantId);
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.DELETE)
    public @ResponseBody boolean delete(@RequestBody String objectType, @PathVariable("id") Long entityId) {
        if (objectType == null || entityId == null) {
            return false;
        }
        return service.delete(entityId, objectType);
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.GET)
    public @ResponseBody DynamicEntity get(@RequestBody String objectType, @PathVariable("id") Long entityId) {
        if (objectType == null || entityId == null) {
            return null;
        }
        return service.get(entityId, objectType);
    }

    @RequestMapping(value = "/entities", method = RequestMethod.PATCH)
    public @ResponseBody List<DynamicEntity> list(@RequestParam String objectType, HttpServletRequest request) {
        if (objectType == null) {
            return null;
        }
        HttpSession session = request.getSession();
        String tenantId = (String) session.getAttribute(GlobalConstants.TENANT);
        if (tenantId == null) {
            return null;
        }
        return service.list(objectType, tenantId);
    }

    @RequestMapping(value = "/test/cr", method = RequestMethod.GET)
    public String cr() {
        service.create(null, null);
        return "home";
    }

    @RequestMapping(value = "/test/gt", method = RequestMethod.GET)
    public String gt() {
//        DynamicEntity entity = new DynamicEntity("T_ORDER");
//        entity.setProperty("ORDER_ID", "orderid1009291");
//        entity.setProperty("PRICE_UDF", "aa");
//        service.update(entity, "TN001");
    	service.list("T_ORDER", "Tenant004");
        return "home";
    }
}
