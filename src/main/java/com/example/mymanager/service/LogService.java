package com.example.mymanager.service;

import com.example.mymanager.bean.Log;
import com.example.mymanager.bean.Page;

import java.util.List;

public interface LogService {
    Integer insertLog(Log log);
    List<Log> selectLogAll();
    List<Log> selectPageLogWhere(Page page, Integer capacityId, String content);
    Integer selectCountLog(Page page,Integer capacityId,String content);
}
