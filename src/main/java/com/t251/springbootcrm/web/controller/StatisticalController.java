package com.t251.springbootcrm.web.controller;

import com.t251.springbootcrm.entity.CstCustomer;
import com.t251.springbootcrm.entity.Statistical;
import com.t251.springbootcrm.repository.*;
import com.t251.springbootcrm.service.CstCustomerService;
import com.t251.springbootcrm.service.IUserService;
import com.t251.springbootcrm.service.SalChanceService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/statistical")
public class StatisticalController {

    @Resource
    private OrdersLineRepository ordersLineRepository;
    @Resource
    private CstCustomerRepository cstCustomerRepository;
    @Resource
    private CstCustomerService cstCustomerService;

    @RequestMapping("/CustomerContributionsList")
    public String CustomerContributions(Model model,@RequestParam(required = false)String custName,@RequestParam(required = false,defaultValue = "1")Integer pageIndex) {
        /*List<CstCustomer>customerslist=cstCustomerRepository.findAll();
        List<Statistical>statisticalList=new ArrayList<>();
        Statistical[] statistical=new Statistical[customerslist.size()];
        for (CstCustomer cstCustomer:customerslist){
            Integer i=0;i++;
            String bbb=ordersLineRepository.getSumPriceByCustName(cstCustomer.getCustName());
            statistical[i]=new Statistical();
            statistical[i].setName(cstCustomer.getCustName());
            statistical[i].setValue(bbb);
            statisticalList.add(statistical[i]);
        }*/
        Pageable pageable= PageRequest.of(pageIndex-1,5);
        Page<CstCustomer> cstCustomersPage=cstCustomerService.getCstCustomerByPage(custName,null,null,null,null,pageable);
        JSONObject[] jsonObject= new JSONObject[cstCustomersPage.getContent().size()+1];
        JSONArray jsonArray=new JSONArray();
        for (CstCustomer cstCustomer:cstCustomersPage.getContent()){
            Integer i=0;i++;
            System.out.println(cstCustomersPage.getContent().size());
            String bbb=ordersLineRepository.getSumPriceByCustName(cstCustomer.getCustName());
            jsonObject[i]=new JSONObject();
            jsonObject[i].put("name",cstCustomer.getCustName());
            jsonObject[i].put("value",bbb);
            jsonArray.add(jsonObject[i]);
        }
        model.addAttribute("cstCustomersPage",cstCustomersPage);
       model.addAttribute("jsonArray",jsonArray);
        return "statistical/CustomerContributions";
    }


   /* @PostConstruct
    public void initData(){

    }*/


    @RequestMapping("/getStatisticalData")
    @ResponseBody
    public JSONArray getStatisticalData(){
        List<CstCustomer>customerslist=cstCustomerRepository.findAll();
        JSONObject[] jsonObject= new JSONObject[customerslist.size()+1];
        JSONArray jsonArray=new JSONArray();
        for (CstCustomer cstCustomer:customerslist){
            Integer i=0;i++;
            String bbb=ordersLineRepository.getSumPriceByCustName(cstCustomer.getCustName());
            jsonObject[i]=new JSONObject();
            jsonObject[i].put("name",cstCustomer.getCustName());
            jsonObject[i].put("value",bbb);
            jsonArray.add(jsonObject[i]);

        }
        return jsonArray;
       /* List<CstCustomer>customerslist=cstCustomerRepository.findAll();
        List<Statistical>statisticalList=new ArrayList<>();
        Statistical[] statistical=new Statistical[customerslist.size()];
        for (CstCustomer cstCustomer:customerslist){
            Integer i=0;i++;
            String bbb=ordersLineRepository.getSumPriceByCustName(cstCustomer.getCustName());
            statistical[i]=new Statistical();
            statistical[i].setName(cstCustomer.getCustName());
            statistical[i].setValue(bbb);
            statisticalList.add(statistical[i]);
        }
        return statisticalList;*/
    }
}
