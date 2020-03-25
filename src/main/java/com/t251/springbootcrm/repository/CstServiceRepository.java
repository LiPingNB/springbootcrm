package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.CstService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;

public interface CstServiceRepository extends JpaRepository<CstService,Long>, JpaSpecificationExecutor<CstService> {
    @Transactional
    @Modifying
    @Query("update CstService set svrDueId=:svrDueId,svrDueTo=:svrDueTo,svrStatus=:svrStatus where svrId=:svrId")
    public void updateSvrAllocationBySvrId(Integer svrDueId,String svrDueTo,String svrStatus,Long svrId);
    public CstService findCstServiceBySvrId(Long svrId);

    @Transactional
    @Modifying
    @Query("update CstService set svrDeal=:svrDeal, svrDealId=:svrDealId,svrDealBy=:svrDealBy,svrStatus=:svrStatus,svrDealDate=:svrDealDate where svrId=:svrId")
    public void updateSvrDealBySvrId(String svrDeal, Long svrDealId, String svrDealBy, String svrStatus, Date svrDealDate, Long svrId);


    @Transactional
    @Modifying
    @Query("update CstService set  svrDealId=:svrDealId,svrDealBy=:svrDealBy,svrStatus=:svrStatus,svrDealDate=:svrDealDate,svrResult=:svrResult,svrSatisfy=:svrSatisfy where svrId=:svrId")
    public void updateSvrResultBySvrId(Long svrDealId, String svrDealBy, String svrStatus, Date svrDealDate,String svrResult,Integer svrSatisfy,Long svrId);

}
