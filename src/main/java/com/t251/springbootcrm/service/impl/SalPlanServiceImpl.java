package com.t251.springbootcrm.service.impl;

import com.t251.springbootcrm.entity.SalPlan;
import com.t251.springbootcrm.repository.SalPlanRepository;
import com.t251.springbootcrm.service.SalPlanService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class SalPlanServiceImpl implements SalPlanService {
    @Resource
    private SalPlanRepository salPlanRepository;

    @Override
    public void add(SalPlan salPlan) {
        salPlanRepository.save(salPlan);
    }

    @Override
    public List<SalPlan> getSalPlansById(Long chcId) {
        return salPlanRepository.findSalPlansByPlaChcId(chcId);
    }

    @Override
    public void delPlan(Long id) {
        salPlanRepository.deleteById(id);
    }

    @Override
    public void saveResult(Long plaId,String plaResult) {
        salPlanRepository.updatePlaResultByplaId(plaId,plaResult);
    }

    @Override
    public void updateDevStatus(Long status, Long id) {
        salPlanRepository.updateStatusById(status,id);
    }
}
