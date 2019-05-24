package com.example.mymanager.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(value = { "handler" })
public class Users {
    private Integer id;

    @ExcelColumn(value = "username", col = 1)
    private String username;

    @ExcelColumn(value = "password", col = 2)
    private String password ;

    @ExcelColumn(value = "name", col = 3)
    private String name;

    private Integer state;

    private String code;

    private String iconName;
    private byte[] iconValue;
    private List<UsersRole> usersRoleList;


    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public byte[] getIconValue() {
        return iconValue;
    }

    public void setIconValue(byte[] iconValue) {
        this.iconValue = iconValue;
    }



    public List<UsersRole> getUsersRoleList() {
        return usersRoleList;
    }

    public void setUsersRoleList(List<UsersRole> usersRoleList) {
        this.usersRoleList = usersRoleList;
    }

    public Users() {
    }

    public Users(Integer id, String username, String password, String name, Integer state) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.state = state;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
