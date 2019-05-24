package com.example.mymanager.service;

import com.example.mymanager.bean.Page;
import com.example.mymanager.bean.Role;

import java.util.List;

public interface RoleService {
    public List<Role> selectRoleAll();

    public Integer insertRole(Role role);

    public Integer deleteRole(Integer id);
    List<Role> selectPageRole(Page page);
    Integer selectCountRole(Page page);
}
