package com.t251.springbootcrm.service.impl;

import com.t251.springbootcrm.entity.*;
import com.t251.springbootcrm.repository.*;
import com.t251.springbootcrm.service.CstCustomerService;
import com.t251.springbootcrm.util.Rediscache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class CstCustomerServiceImpl implements CstCustomerService {
    @Resource
    private CstCustomerRepository cstCustomerRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private CstLinkmanRepository cstLinkmanRepository;
    @Resource
    private CstActivityRepository cstActivityRepository;
    @Resource
    private OrdersRepository ordersRepository;
    @Resource
    private OrdersLineRepository ordersLineRepository;
    @Resource
    private ProductRepository productRepository;
    @Resource
    private CstLostRepository cstLostRepository;

    /**
     * 带条件分页查询所有客户
     * @param custName
     * @param custNo
     * @param custRegion
     * @param custManagerName
     * @param custLevelLabel
     * @param pageable
     * @return
     */
    @Override
    public Page<CstCustomer> getCstCustomerByPage(String custName, String custNo, String custRegion, String custManagerName, String custLevelLabel, Pageable pageable) {
        Specification<CstCustomer> specification= (Specification<CstCustomer>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates=new ArrayList<>();
            if (custName!=null&&!custName.equals("")){
                predicates.add(criteriaBuilder.like(root.get("custName"),"%"+custName+"%"));
            }
            if (custNo!=null&&!custNo.equals("")){
                predicates.add(criteriaBuilder.like(root.get("custNo"),"%"+custNo+"%"));
            }
            if (custRegion!=null&&!custRegion.equals("")){
                predicates.add(criteriaBuilder.equal(root.get("custRegion"),""+custRegion+""));
            }
            if (custManagerName!=null&&!custManagerName.equals("")){
                predicates.add(criteriaBuilder.like(root.get("custManagerName"),"%"+custManagerName+"%"));
            }
            if (custLevelLabel!=null&&!custLevelLabel.equals("")){
                predicates.add(criteriaBuilder.equal(root.get("custLevelLabel"),""+custLevelLabel+""));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return cstCustomerRepository.findAll(specification,pageable);

    }

    @Override
    public List<CstCustomer> getAllCustomer() {
        return cstCustomerRepository.findAll();
    }

    @Override
    public CstCustomer getCustomerById(Long custId) {
        return cstCustomerRepository.findCstCustomerByCustId(custId);
    }

    @Override
    public void editCustomer(CstCustomer cstCustomer) {
        cstCustomerRepository.save(cstCustomer);
    }

    @Override
    public Long getusrIdByusrName(String usrName) {
        return userRepository.findUsrIdByUsrName(usrName);
    }

    /**
     * 删除客户信息 先删除从表数据再删主表
     * @param custNo
     */
    @Override
    public void delCustomer(String custNo) {
        try {
        cstLinkmanRepository.deleteBylkmCustNo(custNo);
        cstActivityRepository.deleteByAtvCustNo(custNo);
        Long odrId=ordersRepository.findOdrIdByOdrCustomerNo(custNo);
        ordersLineRepository.deleteByOddOrderId(odrId);
        ordersRepository.deleteByOdrCustomerNo(custNo);
        cstCustomerRepository.deleteByCustNo(custNo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    通过redis存储的custNo获取指定联系人信息 如果custNo为null则保存当前custNo到redis
     */
    @Override
    public List<CstLinkman> getCstLinkmanByCustNo(String custNo) {
        /*if (custNo!=null){
            Rediscache.saveCode("custNo",custNo);
        }
        return cstLinkmanRepository.findCstLinkmanByLkmCustNo(Rediscache.get("custNo"));*/
        return cstLinkmanRepository.findCstLinkmanByLkmCustNo(custNo);
    }

    @Override
    public CstLinkman getCstLinkmanById(Long id) {
        return cstLinkmanRepository.findCstLinkmanBylkmId(id);
    }

    @Override
    public void editLinkman(CstLinkman cstLinkman) {
        cstLinkmanRepository.updateCstLinkmanBylkmId(cstLinkman);
        //cstLinkmanRepository.save(cstLinkman);
    }

    @Override
    public void delLinkman(Long lkmId) {
        cstLinkmanRepository.deleteById(lkmId);
    }

    @Override
    public void addLinkman(CstLinkman cstLinkman) {
        cstLinkmanRepository.save(cstLinkman);
    }

    @Override
    public List<CstActivity> getActivityByCustNo(String custNo) {
        return cstActivityRepository.findCstActivityByAtvCustNo(custNo);
    }

    @Override
    public CstActivity getActivityByAtvId(Long atvId) {
        return cstActivityRepository.findCstActivityByAtvId(atvId);
    }

    @Override
    public void editActivity(CstActivity cstActivity) {
        cstActivityRepository.updateCstActivityByAtvId(cstActivity);
    }

    @Override
    public void addActivity(CstActivity cstActivity) {
        cstActivityRepository.save(cstActivity);
    }

    @Override
    public void delActivity(Long atvId) {
        cstActivityRepository.deleteById(atvId);
    }

    @Override
    public List<Orders> getOrdersByCustNo(String custNo) {
        return ordersRepository.findOrdersByOdrCustomerNo(custNo);
    }

    @Override
    public OrdersLine getOrdersLineByOrderId(Long orderId) {
        return ordersLineRepository.findOrdersLineByOddOrderId(orderId);
    }

    @Override
    public Orders getOrdersByOdrId(Long odrId) {
        return ordersRepository.findOrdersByOdrId(odrId);
    }

    @Override
    public String getProdNameByProdId(Long prodId) {
        return productRepository.findProdNameByProdId(prodId);
    }

    @Override
    public Page<CstLost> getCstLostByPage(String custName, String lstCustManagerName, String lstStatus, Pageable pageable) {
        Specification<CstLost> specification= (Specification<CstLost>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates=new ArrayList<>();
            if (custName!=null&&!custName.equals("")){
                predicates.add(criteriaBuilder.like(root.get("lstCustName"),"%"+custName+"%"));
            }

            if (lstCustManagerName!=null&&!lstCustManagerName.equals("")){
                predicates.add(criteriaBuilder.equal(root.get("lstCustManagerName"),""+lstCustManagerName+""));
            }

            if (lstStatus!=null&&!lstStatus.equals("")){
                predicates.add(criteriaBuilder.equal(root.get("lstStatus"),""+lstStatus+""));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return cstLostRepository.findAll(specification,pageable);
    }

    @Override
    public CstLost getCstLostByLstId(Long lstId) {
        return cstLostRepository.findCstLostByLstId(lstId);
    }

    @Override
    public void editLstDelayBylstId(Long lstId, String delay,String status) {
        cstLostRepository.updateLstDelayBylstId(lstId,delay,status);
    }

    @Override
    public void editLstReasonBylstId(Long lstId, String reason, String status) {
        cstLostRepository.updateLstReasonBylstId(lstId,reason,status);
    }


}
