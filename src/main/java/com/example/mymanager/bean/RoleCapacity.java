package com.example.mymanager.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "handler" })
public class RoleCapacity {
    private Integer id;
    private  Integer RoleId;
    private  Integer CapacityId;

    private Role role;
    private Capacity capacity;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public RoleCapacity() {
    }

    public RoleCapacity(Integer id, Integer roleId, Integer capacityId) {
        this.id = id;
        RoleId = roleId;
        CapacityId = capacityId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return RoleId;
    }

    public void setRoleId(Integer roleId) {
        RoleId = roleId;
    }

    public Integer getCapacityId() {
        return CapacityId;
    }

    public void setCapacityId(Integer capacityId) {
        CapacityId = capacityId;
    }
}
