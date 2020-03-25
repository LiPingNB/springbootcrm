package com.t251.springbootcrm.web.controller;

import com.t251.springbootcrm.entity.User;
import com.t251.springbootcrm.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author 亚力克
 */
@Controller
public class ExampleController {
    @Resource
    private IUserService userService;
    @GetMapping(value = "/hello/{id}")
    public String getUser(@PathVariable("id")Long usrId, Model model){
        User user=userService.getUser(usrId);
        model.addAttribute("user",user);
    return "demo/hello";
    }
    @GetMapping(value = "/string")
    public String getString(Model model, HttpServletRequest request){//演示赋值、字符串拼接
    model.addAttribute("userName","ktSB");
        request.setAttribute("test","request");
        request.getSession().setAttribute("test","session");
        model.addAttribute("date",new Date());
    return  "demo/string";
    }
    @GetMapping("/inline")
    public String inline(Model model, HttpServletRequest request){  //演示内联
        model.addAttribute("userName","kysbSb");
        return "demo/inline";
    }
    @GetMapping("/object")
    public String objecthhh(Model model){
        User user=userService.getUser(2L);
        model.addAttribute("user",user);
        return "demo/objectnbsp";
    }
    @GetMapping(value = "/if")
    public String ifunless(Model model){
        model.addAttribute("flag","111");
    return  "demo/if";
    }
    @GetMapping(value = "/sex")
    public String sex(Model model){
        model.addAttribute("sex","woman");
        return  "demo/sex";
    }
    @GetMapping(value = "/list")
    public String list(Model model){
        List<User>list=userService.findAllUsers();
        model.addAttribute("users",list);
        return  "demo/list";
    }
}
