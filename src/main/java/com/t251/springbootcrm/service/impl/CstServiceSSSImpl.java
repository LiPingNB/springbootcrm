package com.t251.springbootcrm.service.impl;

import com.t251.springbootcrm.entity.CstService;
import com.t251.springbootcrm.entity.SalChance;
import com.t251.springbootcrm.repository.CstServiceRepository;
import com.t251.springbootcrm.service.CstServiceSSS;
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
import java.util.Date;
import java.util.List;

@Service
public class CstServiceSSSImpl implements CstServiceSSS {
    @Resource
    private CstServiceRepository cstServiceRepository;
    @Override
    public void addService(CstService cstService) {
        cstServiceRepository.save(cstService);
    }

    @Override
    public Page<CstService> getCstServiceByPage(String svrCustName, String svrTitle, String svrType, Pageable pageable,String status) {
        Specification<CstService> specification= (Specification<CstService>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates=new ArrayList<>();
            if (svrCustName!=null&&!svrCustName.equals("")){
                predicates.add(criteriaBuilder.like(root.get("svrCustName"),"%"+svrCustName+"%"));
            }
            if (svrTitle!=null&&!svrTitle.equals("")){
                predicates.add(criteriaBuilder.like(root.get("svrTitle"),"%"+svrTitle+"%"));
            }
            if (svrType!=null&&!svrType.equals("")){
                predicates.add(criteriaBuilder.like(root.get("svrType"),"%"+svrType+"%"));
            }
            /*if (whetherAllocation!=null&&!whetherAllocation.equals("")){
                predicates.add(criteriaBuilder.isNull(root.get("svrDueId")));
                predicates.add(criteriaBuilder.isNull(root.get("svrDueTo")));
            }*/
            predicates.add(criteriaBuilder.equal(root.get("svrStatus"),""+status+""));

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return cstServiceRepository.findAll(specification,pageable);
    }

    @Override
    public void editStatusBysvrId(Integer svrDueId, String svrDueTo, String svrStatus, Long svrId) {
        cstServiceRepository.updateSvrAllocationBySvrId( svrDueId,  svrDueTo,  svrStatus,  svrId);
    }

    @Override
    public void delService(Long svrId) {
        cstServiceRepository.deleteById(svrId);
    }

    @Override
    public CstService getCstServiceBySvrId(Long svrId) {
        return cstServiceRepository.findCstServiceBySvrId(svrId);
    }

    @Override
    public void editSvrDealBySvrId(String svrDeal, Long svrDealId, String svrDealBy, String svrStatus, Date svrDealDate, Long svrId) {
       try {
           cstServiceRepository.updateSvrDealBySvrId(svrDeal,svrDealId,  svrDealBy,  svrStatus,svrDealDate,  svrId);
       }catch (Exception e) {
       e.printStackTrace();
       }
    }

    @Override
    public void editSvrResultBySvrId(Long svrDealId, String svrDealBy, String svrStatus, Date svrDealDate, String svrResult, Integer svrSatisfy, Long svrId) {
        cstServiceRepository.updateSvrResultBySvrId( svrDealId, svrDealBy, svrStatus, svrDealDate, svrResult, svrSatisfy, svrId);
    }
}
