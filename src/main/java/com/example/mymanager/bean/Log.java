package com.example.mymanager.bean;

import java.sql.Timestamp;

public class Log {
    private Integer id;
    private Integer capacityId;
    private String content;
    private Integer usersId;
    private Timestamp createTime;

    private Capacity capacity;
    private Users users;

    public Log() {
    }

    public Log(Integer capacityId, String content, Integer usersId, Timestamp createTime) {
        this.capacityId = capacityId;
        this.content = content;
        this.usersId = usersId;
        this.createTime = createTime;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCapacityId() {
        return capacityId;
    }

    public void setCapacityId(Integer capacityId) {
        this.capacityId = capacityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUsersId() {
        return usersId;
    }

    public void setUsersId(Integer usersId) {
        this.usersId = usersId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
