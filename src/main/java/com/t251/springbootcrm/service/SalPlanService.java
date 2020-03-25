package com.t251.springbootcrm.service;

import com.t251.springbootcrm.entity.SalPlan;

import java.util.List;

public interface SalPlanService {
    public void add(SalPlan salPlan);
    public List<SalPlan>getSalPlansById(Long chcId);
    public void delPlan(Long id);
    public void saveResult(Long plaId,String plaResult);
    public void updateDevStatus(Long status,Long id);
}
