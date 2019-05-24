package com.example.mymanager.controller;

import com.example.mymanager.bean.*;
import com.example.mymanager.service.CapacityService;
import com.example.mymanager.service.LogService;
import com.example.mymanager.service.RoleCapacityService;
import com.example.mymanager.service.RoleService;
import com.example.mymanager.util.CapacityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private CapacityService capacityService;

    @Autowired
    private RoleCapacityService roleCapacityService;

    @Autowired
    private LogService logService;
    @GetMapping("/list")
    public ModelAndView list(ModelAndView modelAndView){
        List<Role> roleList=roleService.selectRoleAll();
        List<Capacity> capacityList=capacityService.selectCapacityAll();
        List<Capacity> capacityTier1List=capacityService.selectCapacityByTier1All();
        modelAndView.addObject("capacityTier1List",capacityTier1List);
        modelAndView.addObject("roleList",roleList);
        modelAndView.addObject("capacityList",capacityList);
        modelAndView.setViewName("roleIndex");
        return modelAndView;
    }

    @RequestMapping("/listajax")
    @ResponseBody
    public Object listajax(Page page,@RequestParam("limit") int limit){
        System.out.println("backContent========================"+limit);
        page.setRows(limit);
        System.out.println("page:"+page.toString());
        List<Role>contentList=roleService.selectPageRole(page);
        int totals=roleService.selectCountRole(page);
        page.setTotalRecord(totals);
        return new ResultMap<List<Role>>("",contentList,0,totals);
    }

    @ResponseBody
    @PostMapping("/add")
    public Object add(Role role, @RequestParam(value = "capacityArray[]")Integer [] capacityArray, HttpSession session){
        Map<String,Object> returnData=new HashMap<>();
        if(!CapacityUtil.checkCapacity(capacityService,session,"添加角色")){
            returnData.put("code",-8);
            returnData.put("message","没有权限");
            logService.insertLog(new Log(capacityService.selectCapacityByName("添加角色").getId(),"添加角色时"+role.getName()+"没有权限",
                    ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
            return  returnData;
        }
        role.setCode("ROLE"+new Random().nextInt(10000));
        if(roleService.insertRole(role)==1){
            if(capacityArray!=null&&capacityArray.length>0){
                int i=0;
                RoleCapacity roleCapacity=new RoleCapacity();
                roleCapacity.setRoleId(role.getId());
                for (Integer capacityId:capacityArray) {
                    roleCapacity.setCapacityId(capacityId);
                    if(roleCapacityService.insertRoleCapacity(roleCapacity)!=1){
                        i++;
                    }
                }
                if(i==0){
                    returnData.put("code",1);
                    returnData.put("message","添加成功");
                    logService.insertLog(new Log(capacityService.selectCapacityByName("添加角色").getId(),"添加角色"+role.getName()+"时添加成功",
                            ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
                }else{
                    returnData.put("code",-2);
                    returnData.put("message","发生意外错误");
                    logService.insertLog(new Log(capacityService.selectCapacityByName("添加角色").getId(),"添加角色"+role.getName()+"时发生意外错误",
                            ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
                }
            }
        }else{
            returnData.put("code",-1);
            returnData.put("message","添加失败");
            logService.insertLog(new Log(capacityService.selectCapacityByName("添加角色").getId(),"添加角色"+role.getName()+"时添加失败",
                    ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
        }
        return returnData;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Object delete(Integer id,HttpSession session){
        if(!CapacityUtil.checkCapacity(capacityService,session,"删除角色")){
            logService.insertLog(new Log(capacityService.selectCapacityByName("删除角色").getId(),"删除角色"+id+"时没有权限",
                    ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
            return 0;
        }
        logService.insertLog(new Log(capacityService.selectCapacityByName("删除角色").getId(),"删除角色"+id,
                ((Users)session.getAttribute("myusers")).getId(),new Timestamp(System.currentTimeMillis())));
        return roleService.deleteRole(id);
    }

}
