package com.example.mymanager.mapper;

import com.example.mymanager.bean.Page;
import com.example.mymanager.bean.Users;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface UsersMapper {
    @Select("select * from users")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "icon_name" ,property = "iconName"),
            @Result(column = "icon_value",property = "iconValue"),
            @Result(column = "id",property = "usersRoleList",
                    many =@Many(select = "com.example.mymanager.mapper.UsersRoleMapper.selectUsersRoleByUsersId",fetchType= FetchType.LAZY))
    })
    public List<Users> getUsersAll();

    @Select("SELECT * FROM  (  SELECT A.*, ROWNUM RN  FROM (SELECT * FROM users ) A  WHERE ROWNUM <=#{start}+#{rows} )  WHERE RN >= #{start}+1")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "icon_name" ,property = "iconName"),
            @Result(column = "icon_value",property = "iconValue"),
            @Result(column = "id",property = "usersRoleList",
                    many =@Many(select = "com.example.mymanager.mapper.UsersRoleMapper.selectUsersRoleByUsersId",fetchType= FetchType.LAZY))
    })
    public List<Users> selectPageUsers(Page page);

    @Select("select count(1) from users")
    public Integer selectCountUsers(Page page);

    @Select("select * from users where username=#{username}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "icon_name" ,property = "iconName"),
            @Result(column = "icon_value",property = "iconValue"),
            @Result(column = "id",property = "usersRoleList",
                    many =@Many(select = "com.example.mymanager.mapper.UsersRoleMapper.selectUsersRoleByUsersId",fetchType= FetchType.LAZY))
    })
    public Users getUsersByUserName(String username);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into users values(USERS_SEQUENCE.nextval,#{username},#{password},#{name},#{state},#{code},#{iconName},#{iconValue})")
    public Integer insertUsers(Users users);

   // @Update("update users set username=#{username},password=#{password},name=#{name},state=#{state},icon_name=#{iconName},icon_value=#{iconValue} where id=#{id}")
    @Update({"<script>",
            "update users ",
            "<set>",
            "<if test='username!=null'>username=#{username},</if>",
            "<if test='password!=null'>password=#{password},</if>",
            "<if test='name!=null'>name=#{name},</if>",
            "<if test='state!=null'>state=#{state},</if>",
            "<if test='iconName!=null'>icon_name=#{iconName},</if>",
            "<if test='iconValue!=null'>icon_value=#{iconValue},</if>",
            "</set>",
            "where id=#{id}",
            "</script>"})
    public Integer updateUsers(Users users);

    @Delete("delete from users where id=#{id}")
    public Integer deleteUsers(Integer id);

    @Select("select * from users where id=#{id}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "icon_name" ,property = "iconName"),
            @Result(column = "icon_value",property = "iconValue"),
    })
    public Users selectUsersById(Integer id);
}
