package com.sap.hackthon.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.hackthon.dto.EntityListParam;
import com.sap.hackthon.entity.UserDefineEntity;

@Controller
public class EntityController {


    @RequestMapping(value = "/entity", method = RequestMethod.POST)
    public @ResponseBody UserDefineEntity create(@RequestBody UserDefineEntity entity, HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "/entity", method = RequestMethod.PUT)
    public @ResponseBody UserDefineEntity update(@RequestBody UserDefineEntity entity, HttpServletRequest request) {
    	return null;
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.DELETE)
    public @ResponseBody boolean delete(@RequestBody String objectType, @PathVariable("id") Long entityId) {
        if (objectType == null || entityId == null) {
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.GET)
    public @ResponseBody UserDefineEntity get(@RequestBody String objectType, @PathVariable("id") Long entityId,
            HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "/entities", method = RequestMethod.POST)
    public @ResponseBody List<UserDefineEntity> list(@RequestBody EntityListParam param, HttpServletRequest request) {
        return null;
    }


}
