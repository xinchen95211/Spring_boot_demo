package com.example.spring_boot_demo.sex.Dao;

import com.example.spring_boot_demo.sex.DoMain.NineOneUrl;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface NineOneMapper {
    @Select("select * from 91video_url WHERE url = #{url}")
    List<NineOneUrl> select_for_name(@Param("url") String url);

    @Insert("insert into 91video_url (url) values (#{url})")
    boolean add(@Param("url") String url);

    @Delete("delete from 91video_url where id = #{id}")
    void delete(@Param("id") int id);
}
