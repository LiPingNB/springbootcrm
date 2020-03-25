package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.SalChance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface SalChanceRepository extends JpaRepository<SalChance,Long>, JpaSpecificationExecutor<SalChance> {
    public SalChance findSalChanceById(Long id);
    /*@Query("select s from SalChance s where status=1")
    public List<SalChance>findSalChanceByStatus();*/

    /**
     * 指派销售机会
     * @param status
     * @param id
     */
    @Transactional
    @Modifying
    @Query("update SalChance set dueId=:dueId ,dueTo=:dueName,dueDate=:dueDate,status=:status where id=:id")
    public void updateSalChanceById(Integer dueId,String dueName, Date dueDate,Integer status,Long id);


}
