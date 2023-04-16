package com.example.spring_boot_demo.sex.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.spring_boot_demo.photo.config.PhotoRequestExecutorService;
import com.example.spring_boot_demo.sex.DoMain.TelegramPOJO;
import com.example.spring_boot_demo.sex.DoMain.flag;
import com.example.spring_boot_demo.sex.Services.TelegramServices;
import com.example.spring_boot_demo.sex.Tasker.TelegramTasker;
import com.example.spring_boot_demo.sex.Tasker.TelegramTaskerRUN;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.output.ScanOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@Slf4j
@RestController
@RequestMapping("/sex/telegram")
public class TelegramController {
    @Autowired
    private TelegramServices telegramServices;
    @Autowired
    private flag flag;
    ExecutorService pool = PhotoRequestExecutorService.getExecutorService();
//    @PostMapping
//    public String add(@RequestBody TelegramPOJO telegramPOJO){
//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(telegramPOJO);
//        boolean save = telegramServices.save(telegramPOJO);
//        if (save){
//            return "添加成功";
//        }
//        return "添加失败";
//    }
    @PostMapping
    public Map<String,String> Select(@RequestBody Map<String,String> map){
        HashMap<String, String> maps = new HashMap<>(map);
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            LambdaQueryWrapper<TelegramPOJO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(TelegramPOJO::getName, key);
            TelegramPOJO one = telegramServices.getOne(lambdaQueryWrapper);
            if (one == null){
                maps.remove(key);
            }
        }
        return maps;
    }
    @GetMapping("/{id}")
    public String Select(@PathVariable int id){
        if (id == 21398){
            if (flag.isF()){
                return "2";
            }
            pool.submit(new TelegramTaskerRUN(flag));
            flag.setF(true);
            return 1 + "";
        }
        return 0 + "";

    }
}
