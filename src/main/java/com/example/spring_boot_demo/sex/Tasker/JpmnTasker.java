package com.example.spring_boot_demo.sex.Tasker;

import com.example.spring_boot_demo.photo.config.EXecutorPool;
import com.example.spring_boot_demo.sex.Dao.GirlsMapper;
import com.example.spring_boot_demo.sex.Dao.LogerMapper;
import com.example.spring_boot_demo.sex.utils.GrilsUtils;
import com.example.spring_boot_demo.sex.utils.Reptile;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;
import java.util.concurrent.ExecutorService;


@Configuration
/**
 * xgmn01.com 爬虫多线程开启类
 */
public class JpmnTasker {
    @Autowired
    private GirlsMapper girlsMapper;
    @Autowired
    private LogerMapper logerMapper;
    private ExecutorService executorService = EXecutorPool.getExecutorService();

    //    @Scheduled(fixedRate = 8640000L)
    @Scheduled(cron = "0 20 22 * * ?")
    public void r() {
//        logerMapper.add("程序开始运行了");
        //获取最新域名  -> https://Www.xgmn01.com
        Document top = GrilsUtils.Accessor("https://www.quanjixiu.top/baidu.html");
        String top_url = top.select("a").get(0).attr("href");
        System.out.println(top_url);
        logerMapper.add("获取到最新域名为" + top_url);
        //访问获取到的域名,得到相应目录   /aiyouwu/
        Document dis = GrilsUtils.Accessor(top_url);
        Elements elementsByTag = dis.select("li[class='menu-item'] a[target != ]");
        for (Element element : elementsByTag) {
            String href = element.attr("href");
            //先获取页码和所有的内容
            Document accessor = GrilsUtils.Accessor(top_url + href);
            //获取所有数量
            //查询数据库数量
            String table_name = GrilsUtils.trim(href);
            Integer integer = girlsMapper.select_count(table_name);
            int Total = Integer.parseInt(accessor.select(".pagination strong").html());
            if (Total == integer) {
                System.out.println(table_name + "跳过了");
                continue;
            }
//            计算页数量
            int page = Total % 20 == 0 ? Total / 20 : Total / 20 + 1;
            if (page > 5) {
                Random random = new Random();
                //随机下载前几页数据，最少是3
                page = random.nextInt(3, page + 1);
                System.out.println(table_name + page);
            }


            for (int i = 1; i <= page; i++) {
                String url = top_url + href;
                if (i != 1) {
                    url = top_url + href + "page_" + i + ".html";
                }
                executorService.submit(new Reptile(top_url, url, table_name, executorService, girlsMapper, logerMapper));
            }
        }
    }
}
