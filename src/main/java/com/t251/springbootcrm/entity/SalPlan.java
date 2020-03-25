package com.t251.springbootcrm.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author world
 */
@Table(name = "sal_plan")
@Entity
public class SalPlan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pla_id")
    private Long plaId;
    @Column(name = "pla_chc_id")
    private Long plaChcId;
    @Column(name = "pla_date")
    private Date plaDate;
    @Column(name = "pla_todo")
    private String plaTodo;
    @Column(name = "pla_result")
    private String plaResult;

    public Long getPlaId() {
        return plaId;
    }

    public void setPlaId(Long plaId) {
        this.plaId = plaId;
    }

    public Long getPlaChcId() {
        return plaChcId;
    }

    public void setPlaChcId(Long plaChcId) {
        this.plaChcId = plaChcId;
    }

    public Date getPlaDate() {
        return plaDate;
    }

    public void setPlaDate(Date plaDate) {
        this.plaDate = plaDate;
    }

    public String getPlaTodo() {
        return plaTodo;
    }

    public void setPlaTodo(String plaTodo) {
        this.plaTodo = plaTodo;
    }

    public String getPlaResult() {
        return plaResult;
    }

    public void setPlaResult(String plaResult) {
        this.plaResult = plaResult;
    }
}
