package com.example.mymanager.service.impl;

import com.example.mymanager.bean.Log;
import com.example.mymanager.bean.Page;
import com.example.mymanager.mapper.LogMapper;
import com.example.mymanager.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    public Integer insertLog(Log log){
        return logMapper.insertLog(log);
    }

    public List<Log> selectLogAll(){
        return logMapper.selectLogAll();
    }

    public  List<Log> selectPageLogWhere(Page page, Integer capacityId, String content){
        return  logMapper.selectPageLogWhere(page,capacityId,content);
    }

    public Integer selectCountLog(Page page,Integer capacityId,String content){
        return  logMapper.selectCountLog(page,capacityId,content);
    }
}
