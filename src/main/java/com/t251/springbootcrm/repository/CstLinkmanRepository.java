package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.CstLinkman;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CstLinkmanRepository extends JpaRepository<CstLinkman,Long> {
    public List<CstLinkman>findCstLinkmanByLkmCustNo(String lkmCustNo);
    public CstLinkman findCstLinkmanBylkmId(Long lkmId);

    /**
     * 自定义修改必备注解
     * @Transactional
     * @Modifying
     * @Query
     *
     * #{#cstLinkman.lkmName}实体类赋值方式
     */
    @Transactional
    @Modifying
    @Query("update CstLinkman set lkmName=:#{#cstLinkman.lkmName},lkmSex=:#{#cstLinkman.lkmSex},lkmPostion=:#{#cstLinkman.lkmPostion},lkmTel=:#{#cstLinkman.lkmTel},lkmMobile=:#{#cstLinkman.lkmMobile},lkmMemo=:#{#cstLinkman.lkmMemo} where lkmId=:#{#cstLinkman.lkmId}")
    public void updateCstLinkmanBylkmId(@Param("cstLinkman")CstLinkman cstLinkman);
    @Transactional
    //@Modifying
    public void deleteBylkmCustNo(String custNo);

}
