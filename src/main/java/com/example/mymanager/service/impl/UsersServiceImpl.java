package com.example.mymanager.service.impl;

import com.example.mymanager.bean.Page;
import com.example.mymanager.bean.Users;
import com.example.mymanager.mapper.UsersMapper;
import com.example.mymanager.mapper.UsersRoleMapper;
import com.example.mymanager.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UsersRoleMapper usersRoleMapper;
    public List<Users> getUsersAll(){
        return  usersMapper.getUsersAll();
    }

    public Map<String,Object> selectUsers(String username, String password){
        Map<String,Object> returnData=new HashMap<>();
        Users users=usersMapper.getUsersByUserName(username);
        if(users==null){
            returnData.put("code",-1);
            returnData.put("message","用户不存在");
            returnData.put("data",null);
        }else if(!users.getPassword().equals(password)){
            returnData.put("code",-2);
            returnData.put("message","密码错误");
            returnData.put("data",null);
        }else if(users.getState()==-1){
            returnData.put("code",-3);
            returnData.put("message","账户已锁定");
            returnData.put("data",null);
        }
        else{
            returnData.put("code",1);
            returnData.put("message","");
            returnData.put("data",users);
        }
        return  returnData;
    }
    public Integer deleteUsers(Integer id){
        usersRoleMapper.deleteUsersRoleByUsersId(id);
        return usersMapper.deleteUsers(id);
    }

    public Integer insertUsers(Users users){
        if (usersMapper.getUsersByUserName(users.getUsername())==null){
            return usersMapper.insertUsers(users);
        }else{
            return -1;
        }
    }

    public List<Users> selectPageUsers(Page page){
        return  usersMapper.selectPageUsers(page);
    }
    public Integer selectCountUsers(Page page){
        return  usersMapper.selectCountUsers(page);
    }
    public Users selectUsersById(Integer id){
        return usersMapper.selectUsersById(id);
    }

    public Integer updateUsers(Users users){
        return usersMapper.updateUsers(users);
    }
}
