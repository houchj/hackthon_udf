package com.sap.hackthon.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.hackthon.dto.SessionInfo;
import com.sap.hackthon.dto.User;
import com.sap.hackthon.framework.beans.UserSettings;
import com.sap.hackthon.framework.utils.GlobalConstants;

@Controller
public class UserController {

	@Autowired
	private UserSettings settings;
	
    @RequestMapping(method = RequestMethod.POST, value = "login")
    public @ResponseBody boolean login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> validUsers = new HashMap<String, String>();
        validUsers.put("A", "Tenant012");
        validUsers.put("B", "Tenant004");
        if (!validUsers.containsKey(user.getUsername())) {
            return false;
        }
        HttpSession session = request.getSession();
        settings.setVariable(GlobalConstants.TENANT, validUsers.get(user.getUsername()));
        session.setAttribute(GlobalConstants.USERNAME, user.getUsername());

        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "sessionInfo")
    public @ResponseBody SessionInfo sessionInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String tenantName = (String) session.getAttribute(GlobalConstants.TENANT);
        String username = (String) settings.getVariable(GlobalConstants.TENANT);
        SessionInfo si = new SessionInfo();
        si.setTenantName(tenantName);
        si.setUsername(username);
        return si;
    }

}
