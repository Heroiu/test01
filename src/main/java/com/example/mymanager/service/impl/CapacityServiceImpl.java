package com.example.mymanager.service.impl;

import com.example.mymanager.bean.Capacity;
import com.example.mymanager.bean.Page;
import com.example.mymanager.mapper.CapacityMapper;
import com.example.mymanager.mapper.RoleCapacityMapper;
import com.example.mymanager.service.CapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapacityServiceImpl implements CapacityService {
    @Autowired
    private CapacityMapper capacityMapper;

    @Autowired
    private RoleCapacityMapper roleCapacityMapper;

    public List<Capacity> selectCapacityAll(){
        return capacityMapper.selectCapacityAll();
    }


    public Integer insertCapacity(Capacity capacity){
        return  capacityMapper.insertCapacity(capacity);
    }

    public Integer deleteCapacity(Integer id){
        List<Capacity> capacityList=capacityMapper.selectCapacityByParentId(id);
        for (Capacity capacity : capacityList) {
            roleCapacityMapper.deleteRoleCapacityByCapacityId(capacity.getId());
            deleteCapacity(capacity.getId());
        }
        roleCapacityMapper.deleteRoleCapacityByCapacityId(id);
        return capacityMapper.deleteCapacity(id);
    }
    public Capacity selectCapacityByName(String name){
        return capacityMapper.selectCapacityByName(name);
    }

    public List<Capacity> selectCapacityByTier1All(){
        return capacityMapper.selectCapacityByTier1All();
    }


    public  List<Capacity> selectPageCapacity(Page page){
        return capacityMapper.selectPageCapacity(page);
    }
    public Integer selectCountCapacity(Page page){
        return capacityMapper.selectCountCapacity(page);
    }
}
