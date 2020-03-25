package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.Orders;
import com.t251.springbootcrm.entity.OrdersLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
    public List<Orders>findOrdersByOdrCustomerNo(String custNo);
    public Orders findOrdersByOdrId(Long odrId);
    @Query("select odrId from Orders where odrCustomerNo=:custNo")
    public Long findOdrIdByOdrCustomerNo(String custNo);
    @Transactional
    public void deleteByOdrCustomerNo(String custNo);

}
