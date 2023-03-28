package com.example.spring_boot_demo.sex.Dao;

import com.example.spring_boot_demo.sex.DoMain.GirlsPOJO;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Mapper
@Transactional
public interface GirlsMapper {
    @Select(" select count(*) from GirlsTable where TableName = #{table_name}")
    Integer select_count(@Param("table_name") String table_name);

    @Select("select * from GirlsTable")
    List<GirlsPOJO> selectall(@Param("table_name") String table_name);

    @Insert(" insert into GirlsTable (Name,Total,SuccessTotal,ErrorTotal,Collection,TableName) " +
            "values (#{Name},#{Total},#{SuccessTotal},#{ErrorTotal},#{Collection},#{TableName})")
    boolean add(GirlsPOJO girlsPOJO);

    @Delete("delete from GirlsTable where id = #{id}")
    boolean delete_for_id(@Param("id") int id);

    @Delete("delete from GirlsTable where name = #{name}")
    boolean delete_for_name(@Param("name") String name);

    @Update(" update GirlsTable set " +
            "Name = #{Name}," +
            "Total = #{Total}," +
            "SuccessTotal = #{SuccessTotal}," +
            "ErrorTotal = #{ErrorTotal}," +
            "Collection = #{Collection}, " +
            "TableName = #{TableName} " +
            "where id =  #{id}")
    boolean update(GirlsPOJO girlsPOJO);

    @Select("select * from GirlsTable where id = #{id}")
    GirlsPOJO select_for_id(@Param("id") int id);

    @Select("select * from GirlsTable where name = #{name}")
    GirlsPOJO select_for_name(@Param("name") String name);


    @Select("select * from GirlsTable where ErrorTotal > 0;")
    List<GirlsPOJO> select_error();

}
