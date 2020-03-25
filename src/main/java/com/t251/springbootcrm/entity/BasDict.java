package com.t251.springbootcrm.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 数据字典表
 */
@Entity
@Table(name = "bas_dict")
public class BasDict implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dict_id")
    private Long id;
    @Column(name = "dict_type")
    private String type;
    @Column(name = "dict_item")
    private String item;
    @Column(name = "dict_value")
    private String value;
    @Column(name = "dict_is_editable")
    private Integer isEditable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Integer isEditable) {
        this.isEditable = isEditable;
    }
}

