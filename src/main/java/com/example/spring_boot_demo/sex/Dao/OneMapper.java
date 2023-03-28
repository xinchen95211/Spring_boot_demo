package com.example.spring_boot_demo.sex.Dao;

import com.example.spring_boot_demo.sex.DoMain.OneUrl;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface OneMapper {
    @Select("select * from 91_url WHERE url = #{url}")
    List<OneUrl> select_for_name(@Param("url") String url);

    @Insert("insert into 91_url (url) values (#{url})")
    boolean add(String url);

    @Delete("delete from 91_url where id = #{id}")
    void delete(@Param("id") int id);
}
