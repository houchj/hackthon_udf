package com.sap.hackthon.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.hackthon.dto.User;
import com.sap.hackthon.utils.GlobalConstants;

@Controller
public class UserController {

    @RequestMapping(method = RequestMethod.POST, value = "login")
    public @ResponseBody boolean login(@RequestBody User user, HttpServletRequest request,
    		HttpServletResponse response) throws ServletException, IOException {
        List<String> validUsers = new ArrayList<String>();
        validUsers.add("A");
        validUsers.add("B");
        if (!validUsers.contains(user.getUsername())) {
            return false;
        }
        HttpSession session = request.getSession();
        session.setAttribute(GlobalConstants.TENANT, user.getUsername() + "_Tenant");
        return true;
    }
}
