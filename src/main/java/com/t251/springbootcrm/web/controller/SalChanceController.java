package com.t251.springbootcrm.web.controller;

import com.t251.springbootcrm.entity.BasDict;
import com.t251.springbootcrm.entity.SalChance;
import com.t251.springbootcrm.entity.User;
import com.t251.springbootcrm.service.IUserService;
import com.t251.springbootcrm.service.SalChanceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 销售机会
 * @author world
 */
@Controller
@RequestMapping("/salChance")
public class SalChanceController {
    @Resource
    private SalChanceService salChanceService;
    @Resource
    private IUserService userService;
    @RequestMapping("/salChancelist")
    public String salList(Model model, @RequestParam(required = false) String custName, @RequestParam(required = false)String title,
                          @RequestParam(required = false,defaultValue = "1")Integer pageIndex){
        //springboot 2.2.1  以上已经不能用new Sort了
        //Sort sort=new Sort(Sort.Direction.ASC,"usrId");
        //控制分页的辅助类，可以设置页码(从0开始！！！)、每页的数据条数、排序等
        Sort.Order order=new Sort.Order(Sort.Direction.DESC, "id");
        Pageable pageable= PageRequest.of(pageIndex-1,5, Sort.by(order));
        Page<SalChance> salPager=salChanceService.getSalChanceByPage(custName,title,pageable);
        model.addAttribute("salPager",salPager);
        model.addAttribute("custName",custName);
        model.addAttribute("title",title);
        // List<Role>roles=roleService.findAllRoles();
        //model.addAttribute("roles",roles);
        return "salChance/salChancelist";
    }

    /**
     * 新增销售机会
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String toadd(Model model){
        List<BasDict>regionList=salChanceService.getBasDictByType("地区");
        List<BasDict>levelList=salChanceService.getBasDictByType("客户等级");
        model.addAttribute("regionList",regionList);
        model.addAttribute("levelList",levelList);
        model.addAttribute("userList",userService.findAllUsers());
        return "salChance/add";
    }

    /**
     * 保存新增的销售机会
     * @param salChance
     * @param session
     * @param dueto
     * @return
     */
    @RequestMapping("/save")
    public String addSave(SalChance salChance, HttpSession session,String dueto){
        User user=(User)session.getAttribute("loginUser");
        salChance.setCreateId(user.getUsrId());
        salChance.setCreateBy(user.getUsrName());
        salChance.setDueTo(dueto);
        salChanceService.addSal(salChance);
        return "redirect:/salChance/salChancelist";
    }

    /**
     * 编辑销售机会
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String toedit(Long id, Model model){
        SalChance salChance=salChanceService.getSalChanceById(id);
        List<BasDict>levelList=salChanceService.getBasDictByType("客户等级");
        List<BasDict>regionList=salChanceService.getBasDictByType("地区");
        model.addAttribute("salChance",salChance);
        model.addAttribute("regionList",regionList);
        model.addAttribute("levelList",levelList);
        model.addAttribute("userList",userService.findAllUsers());
        return "salChance/edit";
    }

    /*@RequestMapping("/edit/json")
    @ResponseBody
    public SalChance editjson(Long id){
        SalChance salChance=salChanceService.getSalChanceById(id);
        return salChance;
    }*/

     @RequestMapping("/editSave")
        public String editSave(SalChance salChance){
            salChanceService.editSal(salChance);
         System.err.println(salChance.getRegion());
            return "redirect:/salChance/salChancelist";
        }

    /**
     * 指派销售机会页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/assign")
    public String assign(Long id,Model model){
        SalChance salChance=salChanceService.getSalChanceById(id);
        model.addAttribute("salChance",salChance);
        model.addAttribute("userList",userService.findAllUsers());
    return "salChance/assign";
    }

    /**
     * 指派销售机会
     * @param dueId
     * @param dueName
     * @param dueDate
     * @param status
     * @param id
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping("/assignSave")
    public String assignSave(Integer dueId,String dueName, String dueDate,Integer status,Long id,Model model) throws ParseException {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        salChanceService.assignChance( dueId, dueName, dateFormat.parse(dueDate),status, id);
        return "redirect:/salChance/salChancelist";
    }



}
