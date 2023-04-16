package com.example.spring_boot_demo.sex.Tasker;

import com.example.spring_boot_demo.sex.DoMain.flag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class TelegramTaskerRUN extends TelegramTasker implements Runnable{
    private flag flag;

    public TelegramTaskerRUN(flag flag) {
        this.flag = flag;
    }
    @Override
    public void task() {
        System.out.println("重写的方法准备运行");
        try {
            super.red("https://yaoyao.dynv6.net/Telegram/Telegram/");
        } catch (Exception e) {
            flag.setF(false);
        }
        System.out.println("运行结束");
        flag.setF(false);
    }
    @Override
    public void run() {
        task();
    }
}
