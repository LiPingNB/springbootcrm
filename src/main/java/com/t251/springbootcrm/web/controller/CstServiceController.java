package com.t251.springbootcrm.web.controller;

import com.t251.springbootcrm.entity.BasDict;
import com.t251.springbootcrm.entity.CstCustomer;
import com.t251.springbootcrm.entity.CstService;
import com.t251.springbootcrm.repository.CstCustomerRepository;
import com.t251.springbootcrm.service.CstCustomerService;
import com.t251.springbootcrm.service.CstServiceSSS;
import com.t251.springbootcrm.service.IUserService;
import com.t251.springbootcrm.service.SalChanceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/serviceManage")
public class CstServiceController {
    @Resource
    private CstServiceSSS cstServiceSSS;
    @Resource
    private CstCustomerService cstCustomerService;
    @Resource
    private SalChanceService salChanceService;
    @Resource
    private IUserService userService;

    private List<BasDict>svrTypeAllList;
    @RequestMapping("/toserviceCreation")
    public String toServiceCretion(HttpSession session){
        List<CstCustomer> custlist=cstCustomerService.getAllCustomer();
        session.setAttribute("custList", custlist);
        return "serviceManage/servicecreation";
    }
    @RequestMapping("/serviceCreationSave")
    public String serviceCreationSave(CstService cstService){
        cstServiceSSS.addService(cstService);
        return "redirect:/serviceManage/creation";
    }
    //@PostConstruct当bean加载完之后，就会执行init方法，并且将svrTypeAllList实例化；
    @PostConstruct
    public void initvrTypeList(){
        svrTypeAllList = salChanceService.getBasDictByType("服务类型");
    }
    @RequestMapping("/creation")
    public String tocreation(){
        return "serviceManage/servicecreation";
    }
    @RequestMapping("/serviceAllocationList")
    public String allocationList(@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                 @RequestParam(required = false) String svrCustName,
                                 @RequestParam(required = false)String svrTitle,
                                 @RequestParam(required = false) String svrType, Model model){
        Pageable pageable= PageRequest.of(pageIndex-1,5);
        Page<CstService>cstServicePage=cstServiceSSS.getCstServiceByPage(svrCustName,svrTitle,svrType,pageable,"新创建");
        List<BasDict>svrTypeList=svrTypeAllList;
        model.addAttribute("cstServicePage",cstServicePage);
        model.addAttribute("svrCustName",svrCustName);
        model.addAttribute("svrTitle",svrTitle);
        model.addAttribute("svrType",svrType);
        model.addAttribute("svrTypeList",svrTypeList);
        model.addAttribute("userList",userService.findAllUsers());
        return "serviceManage/serviceAllocationList";
    }
    @RequestMapping("/editStatus")
    @ResponseBody
    public Map editStatus(Integer svrDueId, String svrDueTo, String svrStatus, Long svrId){
        Map map=new HashMap(1);
        cstServiceSSS.editStatusBysvrId(svrDueId,svrDueTo,svrStatus,svrId);
        map.put("flag","true");
        return map;
    }
    @RequestMapping("/delService")
    @ResponseBody
    public Map delService(Long svrId){
        Map map=new HashMap(1);
        cstServiceSSS.delService(svrId);
        map.put("flag","true");
        return map;
    }

    //服务处理
    @RequestMapping("/serviceProcessingList")
    public String serviceProcessingList(@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                        @RequestParam(required = false) String svrCustName,
                                        @RequestParam(required = false)String svrTitle,
                                        @RequestParam(required = false) String svrType, Model model){
        Pageable pageable= PageRequest.of(pageIndex-1,5);
        Page<CstService>cstServicePage=cstServiceSSS.getCstServiceByPage(svrCustName,svrTitle,svrType,pageable,"已分配");
        List<BasDict>svrTypeList=svrTypeAllList;
        model.addAttribute("cstServicePage",cstServicePage);
        model.addAttribute("svrCustName",svrCustName);
        model.addAttribute("svrTitle",svrTitle);
        model.addAttribute("svrType",svrType);
        model.addAttribute("svrTypeList",svrTypeList);
        return "serviceManage/serviceProcessingList";
    }

    @RequestMapping("/serviceDispose")
    public String serviceDispose(Long svrId,Model model){
        CstService cstService=cstServiceSSS.getCstServiceBySvrId(svrId);
        model.addAttribute("cstService",cstService);
        return "serviceManage/serviceDispose";
    }
    @RequestMapping("/serviceDisposesave")
    @ResponseBody
    public Map serviceDisposesave(String svrDeal, String svrDealBy, String svrStatus, String svrDealDate, Long svrId) throws ParseException {
        //System.err.println(svrDeal);
        Map map=new HashMap(1);
        Long svrDealId=userService.getUsrIdByUsrName(svrDealBy);
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /*DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);*/
       try {
           cstServiceSSS.editSvrDealBySvrId(svrDeal,svrDealId,svrDealBy,svrStatus,dateFormat.parse(svrDealDate), svrId);
       }catch (Exception e){
           e.printStackTrace();
       }
        map.put("flag","true");
        return map;
    }



    //服务反馈
    @RequestMapping("/serviceFeedbackList")
    public String serviceFeedbackList(@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                        @RequestParam(required = false) String svrCustName,
                                        @RequestParam(required = false)String svrTitle,
                                        @RequestParam(required = false) String svrType, Model model){
        Pageable pageable= PageRequest.of(pageIndex-1,5);
        Page<CstService>cstServicePage=cstServiceSSS.getCstServiceByPage(svrCustName,svrTitle,svrType,pageable,"已处理");
        List<BasDict>svrTypeList=svrTypeAllList;
        model.addAttribute("cstServicePage",cstServicePage);
        model.addAttribute("svrCustName",svrCustName);
        model.addAttribute("svrTitle",svrTitle);
        model.addAttribute("svrType",svrType);
        model.addAttribute("svrTypeList",svrTypeList);
        return "serviceManage/serviceFeedbackList";
    }
    @RequestMapping("/toServiceFeedbacksave")
    public String toServiceFeedbacksave(Long svrId,Model model){
        CstService cstService=cstServiceSSS.getCstServiceBySvrId(svrId);
        model.addAttribute("cstService",cstService);
        return "serviceManage/serviceFeedback";
    }
    @RequestMapping("/serviceFeedbacksave")
    @ResponseBody
    public Map serviceFeedbacksave( String svrDealBy, String svrDealDate,String svrResult,String svrSatisfy ,Long svrId) throws ParseException {
        Map map=new HashMap(1);
        String svrStatus="";
        if (Integer.parseInt(svrSatisfy)>=3){
            svrStatus="已归档";
        }else if(Integer.parseInt(svrSatisfy)<3){
            svrStatus="已分配";
        }
        Long svrDealId=userService.getUsrIdByUsrName(svrDealBy);
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cstServiceSSS.editSvrResultBySvrId(svrDealId, svrDealBy, svrStatus, dateFormat.parse(svrDealDate), svrResult, Integer.parseInt(svrSatisfy), svrId);
        map.put("flag","true");
        return map;
    }

    //服务归档

    @RequestMapping("/serviceArchiveList")
    public String serviceArchiveList(@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                      @RequestParam(required = false) String svrCustName,
                                      @RequestParam(required = false)String svrTitle,
                                      @RequestParam(required = false) String svrType, Model model){
        Pageable pageable= PageRequest.of(pageIndex-1,5);
        Page<CstService>cstServicePage=cstServiceSSS.getCstServiceByPage(svrCustName,svrTitle,svrType,pageable,"已归档");
        List<BasDict>svrTypeList=svrTypeAllList;

        model.addAttribute("cstServicePage",cstServicePage);
        model.addAttribute("svrCustName",svrCustName);
        model.addAttribute("svrTitle",svrTitle);
        model.addAttribute("svrType",svrType);
        model.addAttribute("svrTypeList",svrTypeList);
        return "serviceManage/serviceArchiveList";
    }

    @RequestMapping("/toServiceArchive")
    public String toServiceArchivesave(Long svrId,Model model){
        CstService cstService=cstServiceSSS.getCstServiceBySvrId(svrId);
        model.addAttribute("cstService",cstService);
        return "serviceManage/serviceArchive";
    }
}









