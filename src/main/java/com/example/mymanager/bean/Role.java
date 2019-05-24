package com.example.mymanager.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(value = { "handler" })
public class Role {
    private Integer id;
    private String name;
    private String detail;
    private String code;
    private List<RoleCapacity> roleCapacityList;

    public List<RoleCapacity> getRoleCapacityList() {
        return roleCapacityList;
    }

    public void setRoleCapacityList(List<RoleCapacity> roleCapacityList) {
        this.roleCapacityList = roleCapacityList;
    }

    public Role() {
    }

    public Role(Integer id, String name, String detail) {
        this.id = id;
        this.name = name;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
