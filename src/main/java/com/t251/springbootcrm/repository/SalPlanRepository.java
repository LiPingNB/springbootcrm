package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.SalPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SalPlanRepository extends JpaRepository<SalPlan,Long> {
    public List<SalPlan>findSalPlansByPlaChcId(Long chcId);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update sal_plan set pla_result=:plaResult where pla_id=:plaId")
    public void updatePlaResultByplaId(Long plaId,String plaResult);


    /**
     * 修改开发状态
     * @param status
     * @param id
     */
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update sal_chance set chc_status=:status where chc_id=:id")
    public void updateStatusById(Long status,Long id);
}
