package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.OrdersLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface OrdersLineRepository extends JpaRepository<OrdersLine,Long> {
    public OrdersLine findOrdersLineByOddOrderId(Long oddOrderId);
    @Transactional
    public void deleteByOddOrderId(Long odrId);
    @Query(value ="SELECT sum(orders_line.odd_price) FROM orders_line ,orders WHERE orders_line.odd_order_id=orders.odr_id and orders.odr_customer_name=:custName",nativeQuery = true)
    public String getSumPriceByCustName(String custName);
}
