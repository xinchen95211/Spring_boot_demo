package com.example.spring_boot_demo.sex.DoMain;


import lombok.Data;

@Data
public class GirlsPOJO {
    /**
     * id -> 唯一id
     * name -> 名字
     * Total -> 总数
     * SuccessTotal -> 成功数量
     * ErrorTota  -> 失败数量
     * collection -> 存储数据的集合
     * TableName  -> 所属的系列
     */
    private int id;
    private String Name;
    private int Total;
    private int SuccessTotal;
    private int ErrorTotal;
    private String Collection;
    private String TableName;


}
