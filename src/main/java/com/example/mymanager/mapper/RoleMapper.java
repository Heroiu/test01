package com.example.mymanager.mapper;

import com.example.mymanager.bean.Page;
import com.example.mymanager.bean.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into ROLE values(ROLE_SEQUENCE.nextval,#{name},#{detail},#{code})")
    public Integer insertRole(Role role);

    @Update("update ROLE set name=#{name} ,detail=#{detail} where id=#{id}")
    public Integer updateRole(Role role);

    @Delete("delete from ROLE where id=#{id}")
    public Integer deleteRole(Integer id);

    @Select("select * from ROLE")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "id",property = "roleCapacityList",
                    many =@Many(select = "com.example.mymanager.mapper.RoleCapacityMapper.selectRoleCapacityByRoleId",fetchType= FetchType.LAZY))
    })
    public List<Role> selectRoleAll();

    @Select("SELECT * FROM  (  SELECT A.*, ROWNUM RN  FROM (SELECT * FROM role ) A  WHERE ROWNUM <=#{start}+#{rows} )  WHERE RN >= #{start}+1")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "id",property = "roleCapacityList",
                    many =@Many(select = "com.example.mymanager.mapper.RoleCapacityMapper.selectRoleCapacityByRoleId",fetchType= FetchType.LAZY))
    })
    List<Role> selectPageRole(Page page);

    @Select("select count(1) from role")
    Integer selectCountRole(Page page);
    @Select("select * from role where id=#{id}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "id",property = "roleCapacityList",
                    many =@Many(select = "com.example.mymanager.mapper.RoleCapacityMapper.selectRoleCapacityByRoleId",fetchType= FetchType.LAZY))
    })
    public Role selectRoleById(Integer id);
}
