package com.example.spring_boot_demo.sex.Tasker;

import com.example.spring_boot_demo.sex.weather.weather;
import com.example.spring_boot_demo.sex.weather.wehook_msg;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Configuration
/**
 * 天气通知开启类
 */
public class WeatherTasker {

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
    @Scheduled(cron = "0 0 21 * * ?")
//    @Scheduled(fixedRate = 864000L)
    public void weather() {
        String minutelyweather = weather.minutelyweather();
        String s = "## 上海宝山\n**" + minutelyweather;
        String threeapi = weather.threeweather();
        wehook_msg wehook_msg = new wehook_msg(s + threeapi);
        try {
            wehook_msg.newmain();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //    @Scheduled(fixedRate = 864000L)
    @Scheduled(cron = "59 59 * * * ?")
    public void now() {
        String minutelyweather = weather.minutelyweather();
        String s = "## 上海宝山\n**" + minutelyweather;
        String nowweather = weather.nowweather();
        wehook_msg wehooks_msg = new wehook_msg(s + nowweather);
        try {
            wehooks_msg.newmain();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //    @Scheduled(fixedRate = 864000L)
    @Scheduled(cron = "0 30 6 * * ?")
    public void ondday() {
        String minutelyweather = weather.minutelyweather();
        String s = "## 上海宝山\n**" + minutelyweather;
        String oneweather = weather.oneweather();
        wehook_msg hooks = new wehook_msg(s + oneweather);
        try {
            hooks.newmain();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
