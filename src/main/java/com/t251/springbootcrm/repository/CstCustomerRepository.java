package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.CstCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public interface CstCustomerRepository extends JpaRepository<CstCustomer,Long>, JpaSpecificationExecutor<CstCustomer> {
public CstCustomer findCstCustomerByCustId(Long custId);
@Transactional
public void deleteByCustNo(String custNo);
/*@Query("select custName from CstCustomer")
public ArrayList<String> findCustNoAndCustName();*/
}
