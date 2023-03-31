package com.example.spring_boot_demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring_boot_demo.photo.Dao.TableMapper;
import com.example.spring_boot_demo.photo.DoMain.PhotoTable;
import com.example.spring_boot_demo.photo.services.Table_Services;
import com.example.spring_boot_demo.sex.Dao.GirlsMapper;
import com.example.spring_boot_demo.sex.Dao.NineOneMapper;
import com.example.spring_boot_demo.sex.Dao.OneMapper;
import com.example.spring_boot_demo.sex.DoMain.OneUrl;
import com.example.spring_boot_demo.sex.weather.WeatherUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.utils.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.net.URL;
import java.util.*;

@SpringBootTest
public class SpringBootDemoApplicationTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private GirlsMapper girlsMapper;
    @Autowired
    private TableMapper tableMapper;
    @Autowired
    private Table_Services tableservices;

    @Test
    public void SelectAll_test() {
        for (int i = 0; i < 100; i++) {
            redisTemplate.opsForValue().set(String.valueOf(i), "999");
        }
        Set<String> keys = redisTemplate.keys("*");
        System.out.println(keys);

        Long delete = redisTemplate.delete(keys);
        System.out.println(delete);

    }


    @Test
    public void select() {
        IPage page = new Page(2, 24);
        QueryWrapper objectQueryWrapper = new QueryWrapper();
        objectQueryWrapper.eq("table_name", "photo");
        objectQueryWrapper.select("id,name,thumbnail,table_name");
        tableMapper.selectPage(page, objectQueryWrapper);
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
        System.out.println(page.getRecords());
        System.out.println(page.getCurrent());
    }

    @Autowired
    private NineOneMapper nineOneMapper;
    @Autowired
    private OneMapper oneMapper;

    @Test
    public void paging_select() {

//        IPage photo = tableservices.paging(1, "photo", "王");
        String s = "edmasic1226.mp4";
        List<OneUrl> oneUrl = oneMapper.select_for_name(s);
        for (int i = 0; i < oneUrl.size() - 1; i++) {
            oneMapper.delete(oneUrl.get(i).getId());
        }

//        redisTemplate.opsForValue().set("test","test",5*1000);


    }

    @Test
    public void rad() {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int ix = random.nextInt(98, 99);
            System.out.println(ix);
        }

    }

    @Test
    public void sellectforid() {
        PhotoTable photoTable = new PhotoTable();

        photoTable.setJson("test");
        photoTable.setName("test");
        photoTable.setTable_name("test");
        photoTable.setThumbnail("test");
        int insert = tableMapper.insert(photoTable);
        System.out.println(insert);
    }

    @Test
    public void setPool() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = objectMapper.readValue("{\"code\":\"200\",\"location\":[{\"name\":\"宝山\",\"id\":\"101020300\",\"lat\":\"31.39890\",\"lon\":\"121.48994\",\"adm2\":\"上海\",\"adm1\":\"上海市\",\"country\":\"中国\",\"tz\":\"Asia/Shanghai\",\"utcOffset\":\"+08:00\",\"isDst\":\"0\",\"type\":\"city\",\"rank\":\"23\",\"fxLink\":\"https://www.qweather.com/weather/baoshan-101020300.html\"}],\"refer\":{\"sources\":[\"QWeather\"],\"license\":[\"QWeather Developers License\"]}}", Map.class);
        ArrayList<Map<String,String>> code = (ArrayList<Map<String, String>>) map.get("location");
        for (Map<String, String> stringStringMap : code) {
            Set<Map.Entry<String, String>> entries = stringStringMap.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                String key = entry.getKey();
                String value = entry.getValue();
                System.out.println(key + "+" +value);
            }
        }

        System.out.println(code);


    }
    @Test
    public void jsouptext() {
//
//        LambdaQueryWrapper<PhotoTable> qw = new LambdaQueryWrapper<>();
//        qw.eq(PhotoTable::getName,"");
//        qw.eq(PhotoTable::getTable_name,"photo");
//        Long count = tableMapper.selectCount(qw);
//        System.out.println(count);

//        Document document = Jsoup.connect("https://yaoyao.dynv6.net/onedrive/%E6%9D%82%E4%B8%83%E6%9D%82%E5%85%AB/%E5%86%99%E7%9C%9F/A").ignoreContentType(true).post();
//        System.out.println(document);

    }
//    public void text() throws IOException {
//
//        BufferedReader fileReader = new BufferedReader(new FileReader("/Users/super/IdeaProjects/Spring_boot_demo/src/main/resources/q.txt"));
//
//        String name = "";
//        while ((name = fileReader.readLine()) != null){
//            LambdaQueryWrapper<PhotoTable> lam = new LambdaQueryWrapper<>();
//            lam.eq(PhotoTable::getName,name);
//            Long aLong = tableMapper.selectCount(lam);
//            if (aLong > 1){
//                List<PhotoTable> photoTables = tableMapper.selectList(lam);
//                for (int i = 0; i < photoTables.size(); i++) {
//                    if (i == 0){
//                        continue;
//                    }
//                    tableMapper.deleteById(photoTables.get(i));
//                }
//            }
//
//
//
//        }
//
//
//
//
//    }
}
