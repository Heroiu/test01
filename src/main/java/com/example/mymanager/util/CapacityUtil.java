package com.example.mymanager.util;

import com.example.mymanager.service.CapacityService;

import javax.servlet.http.HttpSession;
import java.util.Set;

public class CapacityUtil {

    public static void init(CapacityService capacityService,HttpSession session){
        session.setAttribute("capacityManager",capacityService.selectCapacityByName("权限管理").getId());
        session.setAttribute("usersManager",capacityService.selectCapacityByName("用户管理").getId());
        session.setAttribute("roleManager",capacityService.selectCapacityByName("角色管理").getId());
        session.setAttribute("funcManager",capacityService.selectCapacityByName("功能管理").getId());
        session.setAttribute("logManager",capacityService.selectCapacityByName("日志管理").getId());
        session.setAttribute("logInfo",capacityService.selectCapacityByName("日志信息").getId());

        session.setAttribute("insertUsers",capacityService.selectCapacityByName("添加用户").getId());
        session.setAttribute("deleteUsers",capacityService.selectCapacityByName("删除用户").getId());
        session.setAttribute("updateUsers",capacityService.selectCapacityByName("修改用户").getId());
        session.setAttribute("insertRole",capacityService.selectCapacityByName("添加角色").getId());
        session.setAttribute("deleteRole",capacityService.selectCapacityByName("删除角色").getId());
        session.setAttribute("updateRole",capacityService.selectCapacityByName("修改角色").getId());
        session.setAttribute("insertCapacity",capacityService.selectCapacityByName("添加权限").getId());
        session.setAttribute("deleteCapacity",capacityService.selectCapacityByName("删除权限").getId());
        session.setAttribute("updateCapacity",capacityService.selectCapacityByName("修改权限").getId());

    }
    public static boolean checkCapacity(CapacityService capacityService,HttpSession session,String capacityName){
        Integer capacityId=capacityService.selectCapacityByName(capacityName).getId();
        Set<Integer> myCapacity = ( Set<Integer>)session.getAttribute("myCapacity");
        if (myCapacity.contains(capacityId)){
            return true;
        }else{
            return false;
        }
    }
}
