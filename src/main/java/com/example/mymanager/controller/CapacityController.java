package com.example.mymanager.controller;

import com.example.mymanager.bean.*;
import com.example.mymanager.service.CapacityService;
import com.example.mymanager.service.LogService;
import com.example.mymanager.util.CapacityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/capacity")
public class CapacityController {
    @Autowired
    private CapacityService capacityService;
    @Autowired
    private LogService logService;

    @GetMapping("/list")
    public ModelAndView list(ModelAndView modelAndView,HttpSession session){
        List<Capacity> capacityList=capacityService.selectCapacityAll();
        modelAndView.addObject("capacityList",capacityList);
        modelAndView.setViewName("capacityIndex");
        return  modelAndView;
    }

    @ResponseBody
    @PostMapping("/add")
    public Object add(Capacity capacity, HttpSession session){
        if(!CapacityUtil.checkCapacity(capacityService,session,"添加权限")){
            logService.insertLog(new Log(capacityService.selectCapacityByName("添加权限").getId(),"添加权限"+capacity.getName()+"时没有权限",
                    ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
            return 0;
        }
        capacity.setCode("CAPA"+new Random().nextInt(10000));
        if(capacity.getTier()==3){
            return 0;
        }
        if(capacity.getState()==-1){
            return 0;
        }
        logService.insertLog(new Log(capacityService.selectCapacityByName("添加权限").getId(),"添加权限"+capacity.getName(),
                ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
        capacity.setState(1);
        return capacityService.insertCapacity(capacity);
    }

    @ResponseBody
    @PostMapping("/delete")
    public  Object delete(Integer id,HttpSession session){
        if(!CapacityUtil.checkCapacity(capacityService,session,"删除权限")){
            logService.insertLog(new Log(capacityService.selectCapacityByName("删除权限").getId(),"删除权限"+id+"时没有权限",
                    ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
            return 0;
        }
        logService.insertLog(new Log(capacityService.selectCapacityByName("删除权限").getId(),"删除权限"+id,
                ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
        return  capacityService.deleteCapacity(id);
    }

    @RequestMapping("listajax")
    @ResponseBody
    public Object listajax(Page page, @RequestParam("limit") int limit){
        System.out.println("backContent========================"+limit);
        page.setRows(limit);
        System.out.println("page:"+page.toString());
        List<Capacity> contentList=capacityService.selectPageCapacity(page);
        int totals=capacityService.selectCountCapacity(page);
        page.setTotalRecord(totals);
        return new ResultMap<List<Capacity>>("",contentList,0,totals);
    }
}
