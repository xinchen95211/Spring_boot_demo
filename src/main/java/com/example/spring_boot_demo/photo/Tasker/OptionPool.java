package com.example.spring_boot_demo.photo.Tasker;

import com.example.spring_boot_demo.photo.Dao.TableMapper;
import com.example.spring_boot_demo.photo.config.EXecutorPool;
import com.example.spring_boot_demo.photo.services.Table_Services;
import com.example.spring_boot_demo.photo.utils.Option;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

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
    @Scheduled(fixedRate = 8640000L)
//    @Scheduled(cron = "0 0 3 * * ?")
    public void setPool() {
        ExecutorService pool = EXecutorPool.getExecutorService();
//       pool.submit(new Option("https://yaoyao.dynv6.net/onedriveyaoyao/jpmn/Artgravia/"));
        pool.submit(new Option(tableMapper, "https://yaoyao.dynv6.net/onedrive/%E6%9D%82%E4%B8%83%E6%9D%82%E5%85%AB/%E5%86%99%E7%9C%9F/"));
        String top_url = "https://yaoyao.dynv6.net/onedriveyaoyao/jpmn/";
        String top_url_two = "https://yaoyao.dynv6.net/onedriveyaoyao/jpmn2/";
        for (String xs : sx) {
            if ("photo".equals(xs)) {
                continue;
            }
            pool.submit(new Option(tableMapper, top_url + xs + "/"));
//            pool.submit(new Option(tableMapper, top_url_two + xs + "/"));
        }
    }

    /**
     * 每天更新
     */
    @Scheduled(cron = "0 50 4 * * ?")
//    @Scheduled(fixedRate = 8640000L)
    public void recovery_redis() {
        for (String s : sx) {
            System.out.println(s);
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
