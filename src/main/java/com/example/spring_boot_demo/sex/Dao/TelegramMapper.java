package com.example.spring_boot_demo.sex.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spring_boot_demo.sex.DoMain.TelegramPOJO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TelegramMapper extends BaseMapper<TelegramPOJO> {
}
