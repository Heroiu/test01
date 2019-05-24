package com.example.mymanager.service.impl;

import com.example.mymanager.bean.RoleCapacity;
import com.example.mymanager.mapper.RoleCapacityMapper;
import com.example.mymanager.service.RoleCapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleCapacityServiceImpl implements RoleCapacityService {
    @Autowired
    private RoleCapacityMapper roleCapacityMapper;

    public Integer insertRoleCapacity(RoleCapacity roleCapacity){
        return  roleCapacityMapper.insertRoleCapacity(roleCapacity);
    }
}
