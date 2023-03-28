package com.example.spring_boot_demo;

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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Random;
import java.util.Set;

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
    public void setPool() {

    }
}
