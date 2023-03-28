package com.example.spring_boot_demo.sex.Dao;


import com.example.spring_boot_demo.sex.DoMain.Logser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 日志操作接口
 */
@Mapper
@Transactional
public interface LogerMapper {
    @Insert("insert into loger (log,date) values (#{log},now())")
    boolean add(@Param("log") String log);

    @Select("select * from loger")
    List<Logser> select();


}
