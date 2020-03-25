package com.t251.springbootcrm;

import com.t251.springbootcrm.entity.BasDict;
import com.t251.springbootcrm.entity.CstCustomer;
import com.t251.springbootcrm.entity.Statistical;
import com.t251.springbootcrm.entity.User;
import com.t251.springbootcrm.repository.*;
import com.t251.springbootcrm.service.IUserService;
import com.t251.springbootcrm.service.SalChanceService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
class SpringbootcrmApplicationTests {
    @Resource
    private IUserService userService;
    @Resource
    private BasDictRepository basDictRepository;
    @Resource
    private SalChanceService salChanceService;
    @Resource
    private OrdersLineRepository ordersLineRepository;
    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private CstLinkmanRepository cstLinkmanRepository;
    @Resource
    private CstCustomerRepository cstCustomerRepository;
    @Test
    void contextLoads() {
        /*SalChance salChance=new SalChance();
        salChance.setId(15L);
        salChance.setCustName("888");
        SalChance salChance1= salChanceService.getSalChanceById(15L);
        UpdateTool.copyNullProperties(salChance1,salChance);
        salChanceService.editSal(salChance);*/
        List<BasDict> basDict=basDictRepository.findBasDictByType("地区");
        System.err.println(basDict);

    }
    @Test
    public void testGetUser(){
        User user=userService.getUser(1L);
        System.out.println("对象输出>>"+user);
        //没有使用缓存，第二次任然还是需要从数据库中查询
        User user1=userService.getUser(1L);
        System.out.println("对象输出>>"+user1);
    }
    @Test
    public void testdel(){
        Long odrId=ordersRepository.findOdrIdByOdrCustomerNo("A000666");
        System.out.println(odrId);
    }
    @Test
    public void testlist(){
        List<CstCustomer>customerslist=cstCustomerRepository.findAll();
        List<Statistical>listJSON=new ArrayList<>();
        JSONObject[] jsonObject=null;
        for (CstCustomer cstCustomer:customerslist){
            Integer i=0;i++;
            String bbb=ordersLineRepository.getSumPriceByCustName(cstCustomer.getCustName());
            jsonObject= new JSONObject[customerslist.size()];
            jsonObject[i]=new JSONObject();
            jsonObject[i].put("name",cstCustomer.getCustName());
            jsonObject[i].put("value",bbb);
            Iterator iter = jsonObject[i].entrySet().iterator();
            Object[] object=new Object[customerslist.size()];
            Statistical[] statistical=new Statistical[customerslist.size()];
            statistical[i]=new Statistical();
            statistical[i].setName(cstCustomer.getCustName());
            statistical[i].setValue(bbb);
            listJSON.add(statistical[i]);
            System.err.println(bbb);
            //System.err.println(statistical);
            /*while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                System.out.println(entry.getKey().toString());System.out.println(entry.getValue().toString());
            }*/
        }
        System.out.println(listJSON);
        //System.err.println(listJSON);
       // System.err.println(customerslist);
    }
    @Test
    public void testJSON(){
        List<CstCustomer>customerslist=cstCustomerRepository.findAll();
        JSONObject[] jsonObject= new JSONObject[customerslist.size()];
        JSONArray jsonArray=new JSONArray();
        for (CstCustomer cstCustomer:customerslist){
            Integer i=0;i++;
            String bbb=ordersLineRepository.getSumPriceByCustName(cstCustomer.getCustName());
            jsonObject[i]=new JSONObject();
            jsonObject[i].put("name",cstCustomer.getCustName());
            jsonObject[i].put("value",bbb);
            jsonArray.add(jsonObject[i]);

        }
        System.err.println(jsonArray);
        //System.err.println(jsonObject[1]);
       // System.err.println(jsonObject[2]);
    }
}
