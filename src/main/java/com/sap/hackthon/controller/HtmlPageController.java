package com.sap.hackthon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author I075885
 *
 */
@Controller
public class HtmlPageController {

    @RequestMapping(value = "/page/home.html", method = RequestMethod.GET)
    public String home(Model model){
        model.addAttribute("title","Mainstay - Web");
        return "home";
    }
    
    @RequestMapping(value = "/page/udfList.html", method = RequestMethod.GET)
    public String udfList(Model model){
        model.addAttribute("title","Mainstay - Web");
        return "udfList";
    }
    
    @RequestMapping(value = "/page/udfCreate.html", method = RequestMethod.GET)
    public String udfCreate(Model model){
        model.addAttribute("title","Mainstay - Web");
        return "udfCreate";
    }
    
    @RequestMapping(value = "/page/udfView.html", method = RequestMethod.GET)
    public String udfView(Model model){
        model.addAttribute("title","Mainstay - Web");
        return "udfView";
    }
    
    @RequestMapping(value = "/page/orderList.html", method = RequestMethod.GET)
    public String orderList(Model model){
        model.addAttribute("title","Mainstay - Web");
        return "orderList";
    }
    
    @RequestMapping(value = "/page/orderCreate.html", method = RequestMethod.GET)
    public String orderCreate(Model model){
        model.addAttribute("title","Mainstay - Web");
        return "orderCreate";
    }
    
    @RequestMapping(value = "/page/orderView.html", method = RequestMethod.GET)
    public String orderView(Model model){
        model.addAttribute("title","Mainstay - Web");
        return "orderView";
    }
}
