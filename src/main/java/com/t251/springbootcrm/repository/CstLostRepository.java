package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.CstLost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CstLostRepository extends JpaRepository<CstLost,Long>, JpaSpecificationExecutor<CstLost> {
public CstLost findCstLostByLstId(Long lstId);
@Transactional
@Modifying
@Query("update CstLost set lstDelay=:delay,lstReason=:reason,lstStatus=:lststatus where lstId=:lstId")
public void updateLstDelayBylstId(Long lstId,String delay,String lststatus);
    @Transactional
    @Modifying
    @Query("update CstLost set lstReason=:reason,lstStatus=:lststatus where lstId=:lstId")
    public void updateLstReasonBylstId(Long lstId,String reason,String lststatus);
}
