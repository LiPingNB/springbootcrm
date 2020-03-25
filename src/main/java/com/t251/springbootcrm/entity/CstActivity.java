package com.t251.springbootcrm.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "cst_activity")
@Entity
public class CstActivity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atv_id")
    private Long atvId;
    @Column(name = "atv_cust_no")
    private String atvCustNo;
    @Column(name = "atv_cust_name")
    private String atvCustName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "atv_date")
    private Date atvDate;
    @Column(name = "atv_place")
    private String atvPlace;
    @Column(name = "atv_title")
    private String atvTitle;
    @Column(name = "atv_desc")
    private String atvDesc;
    @Column(name = "atv_remark")
    private String atvRemark;

    public Long getAtvId() {
        return atvId;
    }

    public void setAtvId(Long atvId) {
        this.atvId = atvId;
    }

    public String getAtvCustNo() {
        return atvCustNo;
    }

    public void setAtvCustNo(String atvCustNo) {
        this.atvCustNo = atvCustNo;
    }

    public String getAtvCustName() {
        return atvCustName;
    }

    public void setAtvCustName(String atvCustName) {
        this.atvCustName = atvCustName;
    }

    public Date getAtvDate() {
        return atvDate;
    }

    public void setAtvDate(Date atvDate) {
        this.atvDate = atvDate;
    }

    public String getAtvPlace() {
        return atvPlace;
    }

    public void setAtvPlace(String atvPlace) {
        this.atvPlace = atvPlace;
    }

    public String getAtvTitle() {
        return atvTitle;
    }

    public void setAtvTitle(String atvTitle) {
        this.atvTitle = atvTitle;
    }

    public String getAtvDesc() {
        return atvDesc;
    }

    public void setAtvDesc(String atvDesc) {
        this.atvDesc = atvDesc;
    }

    public String getAtvRemark() {
        return atvRemark;
    }

    public void setAtvRemark(String atvRemark) {
        this.atvRemark = atvRemark;
    }
}
