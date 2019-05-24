package com.example.mymanager.service.impl;

import com.example.mymanager.bean.Page;
import com.example.mymanager.bean.Role;
import com.example.mymanager.mapper.RoleCapacityMapper;
import com.example.mymanager.mapper.RoleMapper;
import com.example.mymanager.mapper.UsersRoleMapper;
import com.example.mymanager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleCapacityMapper roleCapacityMapper;

    @Autowired
    private UsersRoleMapper usersRoleMapper;
    public List<Role> selectRoleAll(){
        return roleMapper.selectRoleAll();
    }

    public Integer insertRole(Role role){
        return roleMapper.insertRole(role);
    }

    public Integer deleteRole(Integer id){
        roleCapacityMapper.deleteRoleCapacityByRoleId(id);
        usersRoleMapper.deleteUsersRoleByRoleId(id);
        return roleMapper.deleteRole(id);
    }

    public List<Role> selectPageRole(Page page){
        return  roleMapper.selectPageRole(page);
    }
    public Integer selectCountRole(Page page){
        return  roleMapper.selectCountRole(page);
    }
}
