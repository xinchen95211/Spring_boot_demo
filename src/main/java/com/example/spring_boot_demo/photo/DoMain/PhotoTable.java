package com.example.spring_boot_demo.photo.DoMain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@TableName("PhotoTable")
public class PhotoTable {
    /**
     * id -> id
     * name -> 名字
     * prefix -> 前缀
     * suffix -> 后缀
     * thumbnail -> 缩略图
     * collection -> 数据
     */
    @TableId
    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
//    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    private String name;
    private String thumbnail;
    private String table_name;
    private String json;

}
