package com.t251.springbootcrm.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "orders")
@Entity
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odr_id")
    private Long odrId;
    @Column(name = "odr_customer_no")
    private String odrCustomerNo;
    @Column(name = "odr_customer_name")
    private String odrCustomerName;
    @Column(name = "odr_date")
    private Date odrDate;
    @Column(name = "odr_addr")
    private String odrAddr;
    @Column(name = "odr_status")
    private String odrStatus;



    /*@Column(name = "odr_customer")
   private String odrCustomer;*/
    public Long getOdrId() {
        return odrId;
    }

    public void setOdrId(Long odrId) {
        this.odrId = odrId;
    }

    /*public String getOdrCustomer() {
        return odrCustomer;
    }

    public void setOdrCustomer(String odrCustomer) {
        this.odrCustomer = odrCustomer;
    }*/

    public String getOdrCustomerNo() {
        return odrCustomerNo;
    }

    public void setOdrCustomerNo(String odrCustomerNo) {
        this.odrCustomerNo = odrCustomerNo;
    }

    public String getOdrCustomerName() {
        return odrCustomerName;
    }

    public void setOdrCustomerName(String odrCustomerName) {
        this.odrCustomerName = odrCustomerName;
    }

    public Date getOdrDate() {
        return odrDate;
    }

    public void setOdrDate(Date odrDate) {
        this.odrDate = odrDate;
    }

    public String getOdrAddr() {
        return odrAddr;
    }

    public void setOdrAddr(String odrAddr) {
        this.odrAddr = odrAddr;
    }

    public String getOdrStatus() {
        return odrStatus;
    }

    public void setOdrStatus(String odrStatus) {
        this.odrStatus = odrStatus;
    }
}
