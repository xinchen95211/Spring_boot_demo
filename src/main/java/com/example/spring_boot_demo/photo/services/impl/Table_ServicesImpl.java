package com.example.spring_boot_demo.photo.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring_boot_demo.photo.Dao.TableMapper;
import com.example.spring_boot_demo.photo.DoMain.PhotoTable;
import com.example.spring_boot_demo.photo.services.Table_Services;
import com.example.spring_boot_demo.photo.utils.PhotoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class Table_ServicesImpl implements Table_Services {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TableMapper tableMapper;

    public Table_ServicesImpl() {
    }

    @Override
    public String selectAllTable() {
        String s = redisTemplate.opsForValue().get("selectAllTable");
        if (s == null) {
            s = PhotoUtils.map_str(tableMapper.selectAllTable());
            redisTemplate.opsForValue().set("selectAllTable", s, 30, TimeUnit.DAYS);
        }
        return s;
    }

    @Override
    public IPage paging(int Current, String tables, String condition) {
        IPage page = new Page(Current, 24);
        QueryWrapper objectQueryWrapper = new QueryWrapper();
        objectQueryWrapper.eq(condition == null || condition.equals(""), "table_name", tables);
        objectQueryWrapper.like(condition != null && !condition.equals(""), "name", condition);
        objectQueryWrapper.select("id,name,thumbnail,table_name");
        tableMapper.selectPage(page, objectQueryWrapper);
        return page;
    }

    @Override
    public String id_for_json(long id) {
        String s = redisTemplate.opsForValue().get("id_for_json_" + id);
        if (s == null) {
            s = tableMapper.selectById(id).getJson();
            redisTemplate.opsForValue().set("id_for_json_" + id, s, 10, TimeUnit.DAYS);
        }
        return s;
    }

    @Override
    public String paging_redis(int Current, String tables, String condition) {
        if (condition != null && !condition.equals("")) {
            String s = redisTemplate.opsForValue().get(Current + "_" + condition);
            if (s == null) {
                String str = PhotoUtils.map_str(this.paging(Current, tables, condition));
                redisTemplate.opsForValue().set(Current + "_" + condition, str, 2, TimeUnit.DAYS);
                return str;
            }
            return s;
        }
        String s = redisTemplate.opsForValue().get(tables + "_" + Current);
        if (s == null) {
            String str = PhotoUtils.map_str(this.paging(Current, tables, condition));
            redisTemplate.opsForValue().set(tables + "_" + Current, str, 1, TimeUnit.DAYS);
            return str;
        }
        return s;


    }

    @Override
    public void delete_redis() {
        Set keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }

    @Override
    public Long select_pages(String name) {
        QueryWrapper qw = new QueryWrapper<>();
        qw.eq("table_name", name);
        Long aLong = tableMapper.selectCount(qw);
        return aLong % 24 == 0 ? aLong / 24 : aLong / 24 + 1;
    }

    @Override
    public void add_redis(int Current, String tables) {
        String str = PhotoUtils.map_str(this.paging(Current, tables, null));
        redisTemplate.opsForValue().set(tables + "_" + Current, str, 1, TimeUnit.DAYS);
    }

    @Override
    public void add_id_redis() {
        QueryWrapper qw = new QueryWrapper<>();
        qw.select("id,json");
        List<PhotoTable> photoTables = tableMapper.selectList(qw);
        for (PhotoTable photoTable : photoTables) {
            Long ids = photoTable.getId();
            String s = photoTable.getJson();
            redisTemplate.opsForValue().set("id_for_json_" + ids, s, 30, TimeUnit.DAYS);
        }
    }


}
