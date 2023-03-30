package com.example.spring_boot_demo;


import com.example.spring_boot_demo.photo.config.EXecutorPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.ZoneId;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@Slf4j
public class SpringBootDemoApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("CTT")));
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }
    //定时更新任务 6小时一次

    @Scheduled(fixedRate = 60000L)
    public void th(){
        ExecutorService pool = EXecutorPool.getExecutorService();
        log.warn(pool.toString());
    }

}
