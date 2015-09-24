package com.sap.hackthon.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sap.hackthon.utils.GlobalConstants;

@Controller
public class UserController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestParam String user, @RequestParam String password, HttpServletRequest request) {
        List<String> validUsers = new ArrayList<String>();
        validUsers.add("A");
        validUsers.add("B");
        if (!validUsers.contains(user)) {
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute(GlobalConstants.TENANT, user + "_Tenant");
    }
}
