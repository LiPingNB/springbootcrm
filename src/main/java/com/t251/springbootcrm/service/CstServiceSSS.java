package com.t251.springbootcrm.service;

import com.t251.springbootcrm.entity.CstService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface CstServiceSSS {
    public void addService(CstService cstService);
    public Page<CstService>getCstServiceByPage(String svrCustName, String svrTitle, String svrType, Pageable pageable,String status);
    public void editStatusBysvrId(Integer svrDueId,String svrDueTo,String svrStatus,Long svrId);
    public void delService(Long svrId);
    public CstService getCstServiceBySvrId(Long svrId);
    public void editSvrDealBySvrId(String svrDeal, Long svrDealId, String svrDealBy, String svrStatus, Date svrDealDate, Long svrId);
    public void editSvrResultBySvrId(Long svrDealId, String svrDealBy, String svrStatus, Date svrDealDate,String svrResult,Integer svrSatisfy,Long svrId);
}
