package com.t251.springbootcrm.entity;

import java.io.Serializable;
import javax.persistence.*;
/**
 * @author world
 */

@Table(name = "product")
@Entity
public class Product implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long prodId;
    @Column(name = "prod_name")
    private String prodName;
    @Column(name = "prod_type")
    private String prodType;
    @Column(name = "prod_batch")
    private String prodBatch;
    @Column(name = "prod_unit")
    private String prodUnit;
    @Column(name = "prod_price")
    private Double prodPrice;
    @Column(name = "prod_memo")
    private String prodMemo;

    public Product(){}

    public Product(String prodName, String prodType, String prodBatch, String prodUnit, Double prodPrice, String prodMemo) {
        this.prodName = prodName;
        this.prodType = prodType;
        this.prodBatch = prodBatch;
        this.prodUnit = prodUnit;
        this.prodPrice = prodPrice;
        this.prodMemo = prodMemo;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getProdBatch() {
        return prodBatch;
    }

    public void setProdBatch(String prodBatch) {
        this.prodBatch = prodBatch;
    }

    public String getProdUnit() {
        return prodUnit;
    }

    public void setProdUnit(String prodUnit) {
        this.prodUnit = prodUnit;
    }

    public Double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(Double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdMemo() {
        return prodMemo;
    }

    public void setProdMemo(String prodMemo) {
        this.prodMemo = prodMemo;
    }
}

