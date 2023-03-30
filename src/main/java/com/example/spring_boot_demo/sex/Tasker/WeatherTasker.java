package com.example.spring_boot_demo.sex.Tasker;

import com.example.spring_boot_demo.sex.weather.WeHook;
import com.example.spring_boot_demo.sex.weather.WeatherApi;
import com.example.spring_boot_demo.sex.weather.weather;
import com.example.spring_boot_demo.sex.weather.wehook_msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Configuration
/**
 * 天气通知开启类
 */
public class WeatherTasker {
    @Autowired
    private WeHook weHook;
    @Autowired
    private WeatherApi weatherApi;

    /**
     * 数据库更新操作
     * 弃用
     */

//    @Scheduled(cron = "0 0 5 * * ?")
//    public void fw(){
//        try {
//            Document parse = Jsoup.parse(new URL("http://192.168.0.108:8080/demo05_war/updata"), 100000);
//            Document parse2 = Jsoup.parse(new URL("http://192.168.0.108:8080/demo05_war/updata2"), 100000);
//            System.out.println(parse);
//            System.out.println(parse2);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//
//
//    }
//    @Scheduled(cron = "0 0 21 * * ?")
////    @Scheduled(fixedRate = 864000L)
//    public void weather() {
//
//    }

    //    @Scheduled(fixedRate = 864000L)
    @Scheduled(cron = "59 59 * * * ?")
    public void now() {
        weHook.Toest_text(weatherApi.now_weather());
    }

    //    @Scheduled(fixedRate = 864000L)
    @Scheduled(cron = "0 30 6 * * ?")
    public void ondday() {
        weHook.Toest_text(weatherApi.today_weather());
    }
}
