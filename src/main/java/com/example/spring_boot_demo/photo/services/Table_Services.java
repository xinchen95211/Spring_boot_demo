package com.example.spring_boot_demo.photo.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface Table_Services {
    /**
     * 查询所有表名_cn
     */
    String selectAllTable();


    /**
     * 分页查询页面数据
     */
    IPage paging(int Current, String tables, String condition);

    /**
     * 查询对应的json数据
     */
    String id_for_json(long id);

    /**
     * 分页查询  - 缓存redis
     *
     * @param Current
     * @param tables
     * @param condition
     * @return
     */
    String paging_redis(int Current, String tables, String condition);

    /**
     * 删除所有缓存
     */
    void delete_redis();

    /**
     * 查询对应的页码
     */
    Long select_pages(String name);

    /**
     * 缓存判定是否一致方法
     */
    void add_redis(int Current, String tables);

    /**
     * 添加id的缓存
     */
    void add_id_redis();

}
