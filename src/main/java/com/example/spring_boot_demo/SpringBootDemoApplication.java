package com.example.spring_boot_demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class SpringBootDemoApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("CTT")));
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }
    //定时更新任务 6小时一次


}
