package com.example.spring_boot_demo.sex.config;

import com.example.spring_boot_demo.sex.DoMain.flag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class fiage {
    @Bean
    public flag create(){
        flag flag = new flag();
        flag.setF(false);
        return flag;
    }
}
