package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.CstActivity;
import com.t251.springbootcrm.entity.CstCustomer;
import com.t251.springbootcrm.entity.CstLinkman;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CstActivityRepository extends JpaRepository<CstActivity,Long> {
    public List<CstActivity> findCstActivityByAtvCustNo(String atvCustNo);
    public CstActivity findCstActivityByAtvId(Long atvId);

    /**
     * 自定义修改必备注解
     * @Transactional
     * @Modifying
     * @Query
     *
     * #{#cstActivity.atvDate}实体类赋值方式
     */
    @Transactional
    @Modifying
    @Query("update CstActivity set atvDate=:#{#cstActivity.atvDate},atvPlace=:#{#cstActivity.atvPlace},atvTitle=:#{#cstActivity.atvTitle},atvDesc=:#{#cstActivity.atvDesc},atvRemark=:#{#cstActivity.atvRemark} where atvId=:#{#cstActivity.atvId}")
    public void updateCstActivityByAtvId(@Param("cstActivity") CstActivity cstActivity);
    @Transactional
    public void deleteByAtvCustNo(String custNo);
}
