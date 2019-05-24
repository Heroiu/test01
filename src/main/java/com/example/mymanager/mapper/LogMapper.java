package com.example.mymanager.mapper;

import com.example.mymanager.bean.Capacity;
import com.example.mymanager.bean.Log;
import com.example.mymanager.bean.Page;
import com.example.mymanager.bean.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogMapper {
    @Select("select * from log order by createtime desc")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "users_id",property = "usersId"),
            @Result(column = "capacity_id",property = "capacityId"),
            @Result(column = "users_id",property = "users",javaType = Users.class,
                    one = @One(select = "com.example.mymanager.mapper.UsersMapper.selectUsersById")),
            @Result(column = "capacity_id",property = "capacity",javaType = Capacity.class,
                    one = @One(select = "com.example.mymanager.mapper.CapacityMapper.selectCapacityById"))
    })
    public List<Log> selectLogAll();

    @Select({"<script>",
            "SELECT * FROM  (  SELECT A.*, ROWNUM RN  FROM (SELECT * FROM log ",
            "WHERE 1=1",
            "<when test='capacityId!=null and capacityId!=0'>",
            "AND capacity_id = #{capacityId}",
            "</when>",
            "<when test='content!=null'>",
            "AND content like concat(concat('%',#{content}),'%')",
            "</when>",
            ") A  WHERE ROWNUM &lt;=#{page.start}+#{page.rows} )  WHERE RN &gt;= #{page.start}+1",
            "</script>"})
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "users_id",property = "usersId"),
            @Result(column = "capacity_id",property = "capacityId"),
            @Result(column = "users_id",property = "users",javaType = Users.class,
                    one = @One(select = "com.example.mymanager.mapper.UsersMapper.selectUsersById")),
            @Result(column = "capacity_id",property = "capacity",javaType = Capacity.class,
                    one = @One(select = "com.example.mymanager.mapper.CapacityMapper.selectCapacityById"))
    })
    List<Log> selectPageLogWhere(@Param("page")Page page,@Param("capacityId") Integer capacityId,@Param("content")String content);

    @Select({"<script>",
            "SELECT count(1) FROM log ",
            "WHERE 1=1",
            "<when test='capacityId!=null and capacityId!=0'>",
            "AND capacity_id = #{capacityId}",
            "</when>",
            "<when test='content!=null'>",
            "AND content like concat(concat('%',#{content}),'%')",
            "</when>",
            "</script>"})
    Integer selectCountLog(Page page,@Param("capacityId") Integer capacityId,@Param("content")String content);

    @Insert("insert into log values(LOG_SEQUENCE.nextval,#{capacityId},#{content},#{usersId},#{createTime})")
    public Integer insertLog(Log log);

    @Update("update log set CAPACITY_ID=#{capacityId},CONTENT=#{content},USERS_ID=#{usersId},CREATETIME=#{createTime} where id=#{id}")
    public Integer updateLog(Log log);

    @Delete("delete from log where id=#{id}")
    public Integer deleteLog(Integer id);
}
