package com.example.mymanager.controller;

import com.example.mymanager.bean.Log;
import com.example.mymanager.bean.Page;
import com.example.mymanager.bean.ResultMap;
import com.example.mymanager.bean.Users;
import com.example.mymanager.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping("/list")
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.addObject("logList",logService.selectLogAll());
        modelAndView.setViewName("logIndex");
        return modelAndView;
    }

    @RequestMapping("/listajax")
    @ResponseBody
    public Object listajax(Page page, @RequestParam("limit") int limit,Integer capacityId,String content){
        System.out.println("backContent========================"+limit);
        page.setRows(limit);
        System.out.println("page:"+page.toString());
        List<Log>contentList=logService.selectPageLogWhere(page,capacityId,content);
        int totals=logService.selectCountLog(page,capacityId,content);
        page.setTotalRecord(totals);
        return new ResultMap<List<Log>>("",contentList,0,totals);
    }
}
