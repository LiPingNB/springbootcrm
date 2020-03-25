package com.t251.springbootcrm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
    @GetMapping("/index")
    public String index(){
        return  "demo/userList";
    }
    @GetMapping("/fragment")
    public String fragment(){
        return  "demo/fragment";
    }
    @GetMapping("/test")
    public String test(){
        return  "demo/test";
    }
}
