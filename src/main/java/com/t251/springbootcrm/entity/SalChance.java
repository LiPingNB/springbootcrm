package com.t251.springbootcrm.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 销售机会
 */
@Entity
@Table(name = "sal_chance")
public class SalChance implements  Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chc_id")
    private Long id;
    @Column(name = "chc_source")
    private String source;
    @Column(name = "chc_cust_name")
    private String custName;
    @Column(name = "chc_title")
    private String title;
    @Column(name = "chc_rate")
    private Integer rate;
    @Column(name = "chc_linkman")
    private String linkman;
    @Column(name = "chc_tel")
    private String tel;
    @Column(name = "chc_desc")
    private String desc;
    @Column(name = "chc_create_id")
    private Long createId;
    @Column(name = "chc_create_by")
    private String createBy;
    @Column(name = "chc_create_date")
    private Date createDate;
    @Column(name = "chc_due_id")
    private Integer dueId;
    @Column(name = "chc_due_to")
    private String dueTo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "chc_due_date")
    private Date dueDate;
    @Column(name = "chc_status")
    private Integer status;
    @Column(name = "chc_region")
    private String region;
    @Column(name = "chc_level_label")
    private String level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDueId() {
        return dueId;
    }

    public void setDueId(Integer dueId) {
        this.dueId = dueId;
    }

    public String getDueTo() {
        return dueTo;
    }

    public void setDueTo(String dueTo) {
        this.dueTo = dueTo;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}