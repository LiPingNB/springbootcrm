package com.t251.springbootcrm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping(value = "/login")
    public String login(){
    return "login";
    }
    @RequestMapping(value = "/main")
    public String main(){
        return "main";
    }
    @RequestMapping(value = "/403")
    public String unauthorizedRole(){
        System.out.println("---没有权限---");
        return "403";
    }
}
