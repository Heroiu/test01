package com.example.mymanager.mapper;

import com.example.mymanager.bean.Role;
import com.example.mymanager.bean.RoleCapacity;
import com.example.mymanager.bean.Users;
import com.example.mymanager.bean.UsersRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsersRoleMapper {
    @Insert("insert into USERS_ROLE values(USERS_ROLE_SEQUENCE.nextval,#{usersId},#{roleId})")
    public Integer insertUsersRole(UsersRole usersRole);

    @Update("update USERS_ROLE set users_Id=#{usersRole.usersId} ,role_Id=#{roleId} where id=#{id}")
    public Integer updateUsersRole(UsersRole usersRole);

    @Delete("delete from USERS_ROLE where id=#{id}")
    public Integer deleteUsersRole(Integer id);

    @Delete("delete from USERS_ROLE where users_id=#{id}")
    public Integer deleteUsersRoleByUsersId(Integer id);

    @Delete("delete from USERS_ROLE where role_id=#{id}")
    public Integer deleteUsersRoleByRoleId(Integer id);

    @Select("select * from USERS_ROLE where users_id=#{id}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "users_id",property = "users",javaType = Users.class,
                    one = @One(select = "com.example.mymanager.mapper.UsersMapper.selectUsersById")),
            @Result(column = "role_id",property = "role",javaType = Role.class,
                    one = @One(select = "com.example.mymanager.mapper.RoleMapper.selectRoleById"))
    })
    public List<UsersRole> selectUsersRoleByUsersId(Integer id);

    @Select("select * from USERS_ROLE where role_id=#{id}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "users_id",property = "users",javaType = Users.class,
                    one = @One(select = "com.example.mymanager.mapper.UsersMapper.selectUsersById")),
            @Result(column = "role_id",property = "role",javaType = Role.class,
                    one = @One(select = "com.example.mymanager.mapper.RoleMapper.selectRoleById"))
    })
    public List<UsersRole> selectUsersRoleByRoleId(Integer id);
}
