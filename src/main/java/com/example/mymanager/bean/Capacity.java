package com.example.mymanager.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(value = { "handler" })
public class Capacity {
    private Integer id;
    private String name;
    private String detail;
    private Integer state;
    private Integer parentCapacityId;
    private Integer tier;
    private String code;
    private Capacity parentCapacity;
    private List<Capacity> childCapacityList;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public Capacity getParentCapacity() {
        return parentCapacity;
    }

    public void setParentCapacity(Capacity parentCapacity) {
        this.parentCapacity = parentCapacity;
    }

    public List<Capacity> getChildCapacityList() {
        return childCapacityList;
    }

    public void setChildCapacityList(List<Capacity> childCapacityList) {
        this.childCapacityList = childCapacityList;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getParentCapacityId() {
        return parentCapacityId;
    }

    public void setParentCapacityId(Integer parentCapacityId) {
        this.parentCapacityId = parentCapacityId;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
