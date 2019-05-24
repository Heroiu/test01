package com.example.mymanager.service.impl;

import com.example.mymanager.bean.UsersRole;
import com.example.mymanager.mapper.UsersRoleMapper;
import com.example.mymanager.service.UsersRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersRoleServiceImpl implements UsersRoleService {
    @Autowired
    private UsersRoleMapper usersRoleMapper;

    public Integer insertUsersRole(UsersRole usersRole){
        return usersRoleMapper.insertUsersRole(usersRole);
    }
}
