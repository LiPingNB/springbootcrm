package com.t251.springbootcrm.web.controller;

import com.t251.springbootcrm.entity.SalChance;
import com.t251.springbootcrm.entity.SalPlan;
import com.t251.springbootcrm.service.SalChanceService;
import com.t251.springbootcrm.service.SalPlanService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开发计划
 * @author world
 */
@Controller
@RequestMapping("/salPlan")
public class SalPlanController {
    @Resource
    private SalChanceService salChanceService;
    @Resource
    private SalPlanService salPlanService;

    @RequestMapping("/devplanlist")
    public String devplanlist(Model model, @RequestParam(required = false) String custName, @RequestParam(required = false)String linkman
            , @RequestParam(required = false)String title , @RequestParam(required = false,defaultValue = "1")Integer pageIndex){
        Sort.Order order=new Sort.Order(Sort.Direction.DESC,"id");
        Pageable pageable= PageRequest.of(pageIndex-1,5,Sort.by(order));
        Page<SalChance> devplanPager=salChanceService.getDevPlanByPage(custName,linkman,title,pageable);
        model.addAttribute("devplanPager",devplanPager);
        model.addAttribute("custName",custName);
        model.addAttribute("linkman",linkman);
        model.addAttribute("title",title);
        return "salChance/devlist";
    }

    /**
     * 制定开发计划页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/formulatePlan")
    public String formulate(Long id,Model model) {
        SalChance salChance=salChanceService.getSalChanceById(id);
        List<SalPlan>salPlans= salPlanService.getSalPlansById(id);
        model.addAttribute("salChance",salChance);
        model.addAttribute("salPlans",salPlans);

        return "salChance/formulatelist";
    }

    /**
     * 新增开发计划
     * @param plaChcId
     * @param plaTodo
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map addPlan(Long plaChcId,String plaTodo){
        SalPlan salPlan=new SalPlan();
        salPlan.setPlaChcId(plaChcId);
        salPlan.setPlaTodo(plaTodo);
        salPlanService.add(salPlan);
        Map map=new HashMap(1);
        List<SalPlan>salPlans= salPlanService.getSalPlansById(plaChcId);
        map.put("flag","true");
        map.put("plaId",salPlans.get(salPlans.size()-1).getPlaId());
        return map;
    }

    /**
     * 删除开发计划
     * @param id
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public Map delPlan(Long id){
        salPlanService.delPlan(id);
        Map map=new HashMap(1);
        map.put("flag","true");
        return map;
    }

    /**
     * 执行开发计划页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toexecute")
    public String toexecute(Long id,Model model) {
        SalChance salChance=salChanceService.getSalChanceById(id);
        List<SalPlan>salPlans= salPlanService.getSalPlansById(id);
        model.addAttribute("salChance",salChance);
        model.addAttribute("salPlans",salPlans);
        return "salChance/executelist";
    }

    /**
     * 保存执行效果
     * @param plaId
     * @param plaResult
     * @return
     */
    @RequestMapping("/saveResult")
    @ResponseBody
    public Map saveResult(Long plaId,String plaResult){
        Map map=new HashMap(2);
        try {
            salPlanService.saveResult(plaId,plaResult);
            map.put("flag","true");
        }catch (Exception e){
            e.printStackTrace();
        }

        return map;
    }


    /**
     * 开发状态
     * @param status  3：终止开发  2：开发成功
     * @param id
     * @return
     */
    @RequestMapping("/devStatus")
    @ResponseBody
    public Map devStatus(Long status,Long id){
        Map<Object,String> map=new HashMap<>(2);
        if (status==2){
            salPlanService.updateDevStatus(status,id);
            map.put("flag","succeed");
        }else if(status==3) {
            salPlanService.updateDevStatus(status,id);
            map.put("flag","failure");
        }
        return map;
    }

    @RequestMapping("/look")
    public String look(){
        return "salChance/Developmentplanpage";
    }
}
