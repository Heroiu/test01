package com.example.mymanager.service;

import com.example.mymanager.bean.Capacity;
import com.example.mymanager.bean.Page;

import java.util.List;

public interface CapacityService {
    List<Capacity> selectCapacityAll();
    Integer insertCapacity(Capacity capacity);
    Integer deleteCapacity(Integer id);
    Capacity selectCapacityByName(String name);
    List<Capacity> selectCapacityByTier1All();
    List<Capacity> selectPageCapacity(Page page);
    Integer selectCountCapacity(Page page);
}
