package com.example.mymanager.mapper;

import com.example.mymanager.bean.Capacity;
import com.example.mymanager.bean.Role;
import com.example.mymanager.bean.RoleCapacity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleCapacityMapper {
    @Insert("insert into Role_Capacity values(Role_Capacity_sequence.nextval,#{RoleId},#{CapacityId})")
    public Integer insertRoleCapacity(RoleCapacity roleCapacity);

    @Update("update Role_Capacity set ROLE_ID=#{RoleId} ,CAPACITY_ID=#{CapacityId} where id=#{id}")
    public Integer updateRoleCapacity(RoleCapacity roleCapacity);

    @Delete("delete from Role_Capacity where id=#{id}")
    public Integer deleteRoleCapacity(Integer id);

    @Delete("delete from Role_Capacity where role_id=#{id}")
    public Integer deleteRoleCapacityByRoleId(Integer id);

    @Delete("delete from Role_Capacity where capacity_id=#{id}")
    public Integer deleteRoleCapacityByCapacityId(Integer id);

    @Select("select * from Role_Capacity where Role_Id=#{id}")
    @Results({
            @Result(column = "id",property = "id"),
//            @Result(column = "role_id",property = "role",javaType = Role.class,
//                    one = @One(select = "com.example.mymanager.mapper.RoleMapper.selectRoleById")),
            @Result(column = "capacity_id",property = "capacity",javaType = Capacity.class,
                    one = @One(select = "com.example.mymanager.mapper.CapacityMapper.selectCapacityById"))
    })
    public List<RoleCapacity> selectRoleCapacityByRoleId(Integer id);

    @Select("select * from Role_Capacity where Capacity_Id=#{id}")
    @Results({
            @Result(column = "id",property = "id"),
//            @Result(column = "role_id",property = "role",javaType = Role.class,
//                    one = @One(select = "com.example.mymanager.mapper.RoleMapper.selectRoleById")),
            @Result(column = "capacity_id",property = "capacity",javaType = Capacity.class,
                    one = @One(select = "com.example.mymanager.mapper.CapacityMapper.selectCapacityById"))
    })
    public List<RoleCapacity> selectRoleCapacityByCapacityId(Integer id);
}
