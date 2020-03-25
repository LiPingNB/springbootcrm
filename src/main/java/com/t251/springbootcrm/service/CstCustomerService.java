package com.t251.springbootcrm.service;

import com.t251.springbootcrm.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CstCustomerService {
    //客户
    public Page<CstCustomer>getCstCustomerByPage(String custName, String custNo, String custRegion, String custManagerName, String custLevelLabel, Pageable pageable);
    public List<CstCustomer>getAllCustomer();
    public CstCustomer getCustomerById(Long custId);
    public void editCustomer(CstCustomer cstCustomer);
    public Long getusrIdByusrName(String usrName);
    public void delCustomer(String custNo);
    //联系人
    public List<CstLinkman>getCstLinkmanByCustNo(String custNo);
    public CstLinkman getCstLinkmanById(Long id);
    public void editLinkman(CstLinkman cstLinkman);
    public void delLinkman(Long lkmId);
    public void addLinkman(CstLinkman cstLinkman);
    //交往记录
    public List<CstActivity> getActivityByCustNo(String custNo);
    public CstActivity getActivityByAtvId(Long atvId);
    public void editActivity(CstActivity cstActivity);
    public void addActivity(CstActivity cstActivity);
    public void delActivity(Long atvId);
    //历史订单
    public List<Orders> getOrdersByCustNo(String custNo);
    public OrdersLine getOrdersLineByOrderId(Long orderId);
    public Orders getOrdersByOdrId(Long odrId);
    public String getProdNameByProdId(Long prodId);
    //客户流失
    public Page<CstLost>getCstLostByPage(String custName,String lstCustManagerName,String lstStatus,Pageable pageable);
    public CstLost getCstLostByLstId(Long lstId);
    public void editLstDelayBylstId(Long lstId,String delay,String status);
    public void editLstReasonBylstId(Long lstId,String reason,String status);
}
