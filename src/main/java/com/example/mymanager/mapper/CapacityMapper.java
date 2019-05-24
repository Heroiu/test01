package com.example.mymanager.mapper;

import com.example.mymanager.bean.Capacity;
import com.example.mymanager.bean.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface CapacityMapper {
    @Insert("insert into CAPACITY values(capacity_sequence.nextval,#{name},#{detail},#{state},#{parentCapacityId},#{tier},#{code})")
    Integer insertCapacity(Capacity capacity);

    @Update("update CAPACITY set name=#{name} ,detail=#{detail},parent_capacityid=#{parentCapacityId},tier=#{tier} where id=#{id}")
    Integer updateCapacity(Capacity capacity);

    @Delete("delete from CAPACITY where id=#{id}")
    Integer deleteCapacity(Integer id);

    @Select("select * from CAPACITY")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "PARENT_CAPACITYID",property = "parentCapacityId"),
            @Result(column = "id",property = "childCapacityList",
                    many =@Many(select = "com.example.mymanager.mapper.CapacityMapper.selectCapacityByParentId",fetchType= FetchType.LAZY)),
            @Result(column = "parent_CapacityId",property = "parentCapacity",javaType = Capacity.class,
                    one = @One(select = "com.example.mymanager.mapper.CapacityMapper.selectCapacityById"))

    })
    List<Capacity> selectCapacityAll();

    @Select("SELECT * FROM  (  SELECT A.*, ROWNUM RN  FROM (SELECT * FROM capacity ) A  WHERE ROWNUM <=#{start}+#{rows} )  WHERE RN >= #{start}+1")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "PARENT_CAPACITYID",property = "parentCapacityId"),
            @Result(column = "id",property = "childCapacityList",
                    many =@Many(select = "com.example.mymanager.mapper.CapacityMapper.selectCapacityByParentId",fetchType= FetchType.LAZY)),
            @Result(column = "parent_CapacityId",property = "parentCapacity",javaType = Capacity.class,
                    one = @One(select = "com.example.mymanager.mapper.CapacityMapper.selectCapacityById"))

    })
    List<Capacity> selectPageCapacity(Page page);

    @Select("select count(1) from capacity")
    Integer selectCountCapacity(Page page);
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "PARENT_CAPACITYID",property = "parentCapacityId"),
            @Result(column = "id",property = "childCapacityList",
                    many =@Many(select = "com.example.mymanager.mapper.CapacityMapper.selectCapacityByParentId",fetchType= FetchType.LAZY)),
    })
    @Select("select * from capacity where id=#{id}")
    Capacity selectCapacityById(Integer id);

    @Select("select * from capacity where name=#{name}")
    Capacity selectCapacityByName(String name);

    @Select("select * from capacity where parent_capacityid=#{id}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "PARENT_CAPACITYID",property = "parentCapacityId"),
            @Result(column = "id",property = "childCapacityList",
                    many =@Many(select = "com.example.mymanager.mapper.CapacityMapper.selectCapacityByParentId",fetchType= FetchType.LAZY)),
    })
    List<Capacity> selectCapacityByParentId(Integer id);

    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "PARENT_CAPACITYID",property = "parentCapacityId"),
            @Result(column = "id",property = "childCapacityList",
                    many =@Many(select = "com.example.mymanager.mapper.CapacityMapper.selectCapacityByParentId",fetchType= FetchType.LAZY)),
    })
    @Select("select * from capacity where tier = 1")
    List<Capacity> selectCapacityByTier1All();
}
