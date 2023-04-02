package com.example.spring_boot_demo.photo.Tasker;

import com.example.spring_boot_demo.photo.Dao.TableMapper;
import com.example.spring_boot_demo.photo.config.EXecutorPool;
import com.example.spring_boot_demo.photo.services.Table_Services;
import com.example.spring_boot_demo.photo.utils.Option;
import com.example.spring_boot_demo.sex.utils.GrilsUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URL;
import java.util.concurrent.ExecutorService;


@Configuration
public class OptionPool {
    private final TableMapper tableMapper;
    private final Table_Services table_services;
    String[] sx = new String[]{"Aiyouwu", "Artgravia", "BoLoli", "Candy", "Cosplay", "DJAWA", "DKGirl", "FeiLin", "Gangtaimeinv", "Girlt", "Guochanmeinv", "Huayan", "HuaYang", "IMiss", "LEGBABY", "LeYuan", "MFStar", "Micat", "MiiTao", "MintYe", "MissLeg", "MiStar", "Mtcos", "Mtmeng", "MyGirl", "Neiyiyouwu", "Oumeimeinv", "Pdl", "Rihanmeinv", "Siwameitui", "Slady", "Taste", "Tgod", "TouTiao", "Tuigirl", "Tukmo", "Ugirls", "Uxing", "WingS", "Xgyw", "XiaoYu", "XingYan", "Xiuren", "YaoJingShe", "Yituyu", "YouMei", "YouMi", "YouWu", "Ysweb", "photo"};

    public OptionPool(TableMapper tableMapper, Table_Services table_services) {
        this.tableMapper = tableMapper;
        this.table_services = table_services;
    }

    /**
     * 每日更新
     */
//    @Scheduled(fixedRate = 8640000L)
    @Scheduled(cron = "0 0 3 * * ?")
    public void setPool() {
        ExecutorService pool = EXecutorPool.getExecutorService();
        pool.submit(new Option(tableMapper, "https://yaoyao.dynv6.net/onedrive/%E6%9D%82%E4%B8%83%E6%9D%82%E5%85%AB/%E5%86%99%E7%9C%9F/"));
        String top_url = "https://yaoyao.dynv6.net/onedriveyaoyao/jpmn/";
        String top_url_two = "https://yaoyao.dynv6.net/onedriveyaoyao/jpmn2/";

        Document accessor2 = GrilsUtils.Accessor(top_url_two);
        if (accessor2 != null){
        Elements tbody_a2 = accessor2.select("tbody td a");
        for (Element element : tbody_a2) {
            pool.submit(new Option(tableMapper, top_url_two + element.text() + "/"));
        }
        }

        Document accessor = GrilsUtils.Accessor(top_url);
        if (accessor != null) {
            Elements tbody_a = accessor.select("tbody td a");
            for (Element element : tbody_a) {
                pool.submit(new Option(tableMapper, top_url + element.text() + "/"));
            }
        }

    }

    /**
     * 每天更新
     */
    @Scheduled(cron = "0 50 4 * * ?")
//    @Scheduled(fixedRate = 8640000L)
    public void recovery_redis() {
        for (String s : sx) {
            int aLong = Math.toIntExact(table_services.select_pages(s));
            for (int i = 1; i <= aLong; i++) {
                table_services.add_redis(i, s);
            }
        }
    }

    /**
     * 每月更新一次
     */
    @Scheduled(cron = "0 0 1 27 * ?")
//    @Scheduled(fixedRate = 8640000L)
    public void recovery_id_redis() {
        table_services.add_id_redis();
    }



}
