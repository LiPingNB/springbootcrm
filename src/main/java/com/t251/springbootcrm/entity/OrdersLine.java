package com.t251.springbootcrm.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "orders_line")
@Entity
public class OrdersLine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odd_id")
    private Long oddId;
    @Column(name = "odd_order_id")
    private Long oddOrderId;
    @Column(name = "odd_prod_id")
    private Long oddProdId;
    @Column(name = "odd_count")
    private Integer oddCount;
    @Column(name = "odd_unit")
    private String oddUnit;
    @Column(name = "odd_price")
    private Double oddPrice;

    public Long getOddId() {
        return oddId;
    }

    public void setOddId(Long oddId) {
        this.oddId = oddId;
    }

    public Long getOddOrderId() {
        return oddOrderId;
    }

    public void setOddOrderId(Long oddOrderId) {
        this.oddOrderId = oddOrderId;
    }

    public Long getOddProdId() {
        return oddProdId;
    }

    public void setOddProdId(Long oddProdId) {
        this.oddProdId = oddProdId;
    }

    public Integer getOddCount() {
        return oddCount;
    }

    public void setOddCount(Integer oddCount) {
        this.oddCount = oddCount;
    }

    public String getOddUnit() {
        return oddUnit;
    }

    public void setOddUnit(String oddUnit) {
        this.oddUnit = oddUnit;
    }

    public Double getOddPrice() {
        return oddPrice;
    }

    public void setOddPrice(Double oddPrice) {
        this.oddPrice = oddPrice;
    }
}
