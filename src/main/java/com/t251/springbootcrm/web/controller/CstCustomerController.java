package com.t251.springbootcrm.web.controller;

import com.t251.springbootcrm.entity.*;
import com.t251.springbootcrm.service.CstCustomerService;
import com.t251.springbootcrm.service.IUserService;
import com.t251.springbootcrm.service.SalChanceService;
import com.t251.springbootcrm.util.Rediscache;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author world
 */
@Controller
@RequestMapping("/customer")
public class CstCustomerController {
    @Resource
    private CstCustomerService cstCustomerService;
    @Resource
    private SalChanceService salChanceService;
    /**
    客户信息分页展示
     */
    @RequestMapping("/customerList")
    public String customerList(Model model, @RequestParam(required = false)String custName,
                               @RequestParam(required = false)String custNo,
                               @RequestParam(required = false) String custRegion,
                               @RequestParam(required = false) String custManagerName,
                               @RequestParam(required = false) String custLevelLabel,
                               @RequestParam(required = false,defaultValue = "1")Integer pageIndex,@RequestParam(required = false)String custLevellable){
        Sort.Order order=new Sort.Order(Sort.Direction.DESC,"custNo");
        Pageable pageable= PageRequest.of(pageIndex-1,5,Sort.by(order));
        Page<CstCustomer>cstCustomersPage=cstCustomerService.getCstCustomerByPage(custName,custNo,custRegion,custManagerName,custLevelLabel,pageable);
        List<BasDict>regionList=salChanceService.getBasDictByType("地区");
        List<BasDict>levelList=salChanceService.getBasDictByType("客户等级");
        model.addAttribute("cstCustomersPage",cstCustomersPage);
        model.addAttribute("custName",custName);
        model.addAttribute("custNo",custNo);  //null
        model.addAttribute("custRegion",custRegion);
        model.addAttribute("custManagerName",custManagerName);
        //model.addAttribute("custLevel",custLevel);
        model.addAttribute("custLevelLabel",custLevelLabel);
        model.addAttribute("regionList",regionList);
        model.addAttribute("levelList",levelList);
        return "customer/customerList";
    }

    /**
     * 编辑客户信息页面
     * @param custId
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/toedit")
    public String toEditCustomer(Long custId,Model model,HttpSession session){
        CstCustomer cstCustomer=cstCustomerService.getCustomerById(custId);
        List<BasDict>regionList=salChanceService.getBasDictByType("地区");
        List<BasDict>levelList=salChanceService.getBasDictByType("客户等级");
        model.addAttribute("cstCustomer",cstCustomer);
        model.addAttribute("regionList",regionList);
        model.addAttribute("levelList",levelList);
         return "customer/edit";
    }

    /**
     * 通过custId获取CstCustomer信息达到数据回显
     * @param custId
     * @return
     */
    @RequestMapping("/edit/json")
    @ResponseBody
    public CstCustomer editJson(Long custId){
        CstCustomer cstCustomer=cstCustomerService.getCustomerById(custId);
        return cstCustomer;
    }

    /**
     * 通过管理员姓名查询管理员id
     * @param custManagerName
     * @return
     */
    @RequestMapping("/getManagerId/json")
    @ResponseBody
    public Long getManagerId(String custManagerName){
        Long managerId=cstCustomerService.getusrIdByusrName(custManagerName);
        System.err.println(managerId);
        if (managerId==null){
            return null;
        }
        return managerId;
    }

    /**
     * 保存编辑客户信息
     * @param cstCustomer
     * @param session
     * @return
     */
    @RequestMapping("/editSave")
    public String editSaveCustomer(CstCustomer cstCustomer, HttpSession session){
            try {
                cstCustomerService.editCustomer(cstCustomer);
                session.setAttribute("editStatus","true");
            }catch (Exception e){
                e.printStackTrace();
            }
        return "redirect:/customer/customerList";
    }

    //联系人

    @RequestMapping("/linkmanList")
    public String linkmanList(@RequestParam(required = false) String custNo,Model model,HttpSession session){
        List<CstLinkman>cstLinkmen=cstCustomerService.getCstLinkmanByCustNo(custNo);
        if(custNo==null){
            cstLinkmen=cstCustomerService.getCstLinkmanByCustNo((String)session.getAttribute("custNo"));
        }
        session.setAttribute("custNo",cstLinkmen.get(0).getLkmCustNo());
        session.setAttribute("custName",cstLinkmen.get(0).getLkmCustName());
        model.addAttribute("cstLinkmenList",cstLinkmen);
        return "customer/linkmanList";
    }

    /**
     * 修改联系人页面
     * @param lkmId
     * @param model
     * @return
     */
    @RequestMapping("/toEditLinkman")
    public String toEditLinkman(Long lkmId,Model model){
        CstLinkman cstLinkman=cstCustomerService.getCstLinkmanById(lkmId);
        model.addAttribute("cstLinkman",cstLinkman);
        return "customer/editLinkman";
    }

    /**
     * 保存修改的联系人
     * @param cstLinkman
     * @return
     */
    @RequestMapping("/editLinkmanSave")
    public String editLinkmanSave(CstLinkman cstLinkman){
        cstCustomerService.editLinkman(cstLinkman);
        return "redirect:/customer/linkmanList";
    }

    /**
     * 新增联系人页面
     * @return
     */
    @RequestMapping("/toAddLinkman")
    public String toAddLinkman(){
        return "customer/addLinkman";
    }
    /**
     * 保存联系人
     * @return
     */
    @RequestMapping("/addLinkmanSave")
    public String addLinkmanSave(CstLinkman cstLinkman){
        cstCustomerService.addLinkman(cstLinkman);
        return "redirect:/customer/linkmanList";
    }

    //交往记录

    /**
     * 交往记录页面
     * @param model
     * @return
     */
    @RequestMapping("/activityRecordList")
    public String activityRecordList(@RequestParam(required = false) String custNo, Model model,HttpSession session){
        List<CstActivity> cstActivityList=cstCustomerService.getActivityByCustNo(custNo);
        if (custNo==null){
            cstActivityList=cstCustomerService.getActivityByCustNo((String)session.getAttribute("custNo"));
        }
        session.setAttribute("custNo",cstActivityList.get(0).getAtvCustNo());
        session.setAttribute("custName",cstActivityList.get(0).getAtvCustName());
        model.addAttribute("cstActivityList",cstActivityList);
        return "customer/activityRecordList";
    }

    /**
     * 编辑交往记录页面
     * @param atvId
     * @param model
     * @return
     */
    @RequestMapping("/toEditActivityRecord")
    public String toEditActivityRecord(Long atvId,Model model){
        CstActivity cstActivity=cstCustomerService.getActivityByAtvId(atvId);
        model.addAttribute("cstActivity",cstActivity);
        return "customer/editActivityRecord";
    }

    /**
     * 保存编辑交往记录
     * @param cstActivity
     * @return
     */
    @RequestMapping("/editActivityRecordSave")
    public String editActivityRecordSave(CstActivity cstActivity){
        cstCustomerService.editActivity(cstActivity);
        return "redirect:/customer/activityRecordList";
    }

    /**
     * 新增交往记录页面
     * @return
     */
    @RequestMapping("/toAddActivityRecord")
    public String toAddActivityRecord(){
        return "customer/addActivityRecord";
    }
    /**
     * 保存新增交往记录
     * @param cstActivity
     * @return
     */
    @RequestMapping("/addActivityRecordSave")
    public String addActivityRecordSave(CstActivity cstActivity){
        cstCustomerService.addActivity(cstActivity);
        return "redirect:/customer/activityRecordList";
    }


    /*
     * 历史订单
     */

    @RequestMapping("/orderList")
    public String orderList(@RequestParam(required = false) String custNo,Model model,HttpSession session){
        List<Orders> ordersList=cstCustomerService.getOrdersByCustNo(custNo);
        session.setAttribute("custNo",ordersList.get(0).getOdrCustomerNo());
        session.setAttribute("custName",ordersList.get(0).getOdrCustomerName());
        model.addAttribute("ordersList",ordersList);
        return "customer/orderList";
    }

    /**
     * 订单详情
     * @param odrId
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/orderDetails")
    public String orderDetails(Long odrId,Model model,HttpSession session){
        Orders order=cstCustomerService.getOrdersByOdrId(odrId);
        OrdersLine ordersLine=cstCustomerService.getOrdersLineByOrderId(odrId);
        String productName=cstCustomerService.getProdNameByProdId(ordersLine.getOddProdId());
        double sum=ordersLine.getOddCount()*ordersLine.getOddPrice();
        model.addAttribute("order",order);
        model.addAttribute("ordersLine",ordersLine);
        model.addAttribute("sum",sum);
        model.addAttribute("productName",productName);
        return "customer/orderDetails";
    }

    /**
     * 删除相关信息
     * @param custNo
     * @param atvId
     * @param lkmId
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public Map del(@RequestParam(required = false) String custNo,
                    @RequestParam(required = false)Long atvId,
                    @RequestParam(required = false)Long lkmId){
        Map map=new HashMap(3);
        if (custNo!=null){ cstCustomerService.delCustomer(custNo); map.put("flag","true");}
        if (atvId!=null){ cstCustomerService.delActivity(atvId); map.put("flag","true");}
        if (lkmId != null) { cstCustomerService.delLinkman(lkmId); map.put("flag","true");}
        return map;
    }

    /**
     * 客户流失分页展示
    * @param model
     * @param pageIndex
     * @param lstCustName
     * @param lstCustManagerName
     * @param lstStatus
     * @return
     */
    @RequestMapping("/customerLostList")
    public String customerLostList(Model model,@RequestParam(required = false,defaultValue = "1")Integer pageIndex,
                                   @RequestParam(required = false)String lstCustName,
                                   @RequestParam(required = false) String lstCustManagerName,
                                   @RequestParam(required = false) String lstStatus){
        Sort.Order order=new Sort.Order(Sort.Direction.DESC,"lstLostDate");
        Pageable pageable= PageRequest.of(pageIndex-1,5,Sort.by(order));
        Page<CstLost>cstLostPage=cstCustomerService.getCstLostByPage(lstCustName,lstCustManagerName,lstStatus,pageable);
        model.addAttribute("cstLostPage",cstLostPage);
        model.addAttribute("lstCustName",lstCustName);
        model.addAttribute("lstCustManagerName",lstCustManagerName);
        model.addAttribute("lstStatus",lstStatus);
        return "customer/customerLostList";
    }
        @RequestMapping("/toConfirmLost")
        public String toConfirmLost(Long lstId,Model model){
            CstLost cstLost=cstCustomerService.getCstLostByLstId(lstId);
            model.addAttribute("cstLost",cstLost);
        return "customer/confirmLost";
        }
    @RequestMapping("/confirmLost")
    @ResponseBody
    public Map confirmLost(Long lstId,String reason,String status){
        Map map =new HashMap(1);
        cstCustomerService.editLstReasonBylstId(lstId,reason,status);
        map.put("flag","true");
        return map;
    }

        @RequestMapping("/toPostponeLost")
        public String toPostponeLost(Long lstId,Model model){
        CstLost cstLost=cstCustomerService.getCstLostByLstId(lstId);
        model.addAttribute("cstLost",cstLost);
        return "customer/postponeLost";
        }
    @RequestMapping("/appendDelay")
    @ResponseBody
    public Map appendDelay(Long lstId,String delay,String status){
        Map map =new HashMap(1);
        cstCustomerService.editLstDelayBylstId(lstId,delay,status);
        map.put("flag","true");
        return map;
    }

}
