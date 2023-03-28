package com.example.spring_boot_demo.photo.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spring_boot_demo.photo.DoMain.PhotoTable;
import com.example.spring_boot_demo.photo.DoMain.PhotoTables;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface TableMapper extends BaseMapper<PhotoTable> {

    @Select("select * from TablesName_cn")
    List<PhotoTables> selectAllTable();
}
