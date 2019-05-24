package com.example.mymanager.service;

import com.example.mymanager.bean.Page;
import com.example.mymanager.bean.Users;

import java.util.List;
import java.util.Map;

public interface UsersService {
    public List<Users> getUsersAll();
    public Map<String,Object> selectUsers(String username, String password);
    public Integer deleteUsers(Integer id);
    public Integer insertUsers(Users users);
    public Integer updateUsers(Users users);
    public List<Users> selectPageUsers(Page page);
    public Integer selectCountUsers(Page page);
    public Users selectUsersById(Integer id);
}
