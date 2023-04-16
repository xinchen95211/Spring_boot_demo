package com.example.spring_boot_demo.sex.DoMain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Telegram")
public class TelegramPOJO {
    private Long id;
    private String name;
}
