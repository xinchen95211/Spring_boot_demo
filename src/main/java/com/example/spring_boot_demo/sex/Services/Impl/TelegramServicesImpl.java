package com.example.spring_boot_demo.sex.Services.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spring_boot_demo.sex.Dao.TelegramMapper;
import com.example.spring_boot_demo.sex.DoMain.TelegramPOJO;
import com.example.spring_boot_demo.sex.Services.TelegramServices;
import org.springframework.stereotype.Service;

@Service
public class TelegramServicesImpl extends ServiceImpl<TelegramMapper, TelegramPOJO> implements TelegramServices {
}
