package com.example.spring_boot_demo.sex.Tasker;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.spring_boot_demo.sex.Dao.OneMapper;
import com.example.spring_boot_demo.sex.DoMain.OneUrl;
import com.example.spring_boot_demo.sex.DoMain.TelegramPOJO;
import com.example.spring_boot_demo.sex.Services.TelegramServices;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Configuration
public class TelegramTasker {
    @Autowired
    private TelegramServices telegramServices;
//        @Scheduled(fixedRate = 8640000L)
    @Scheduled(cron = "* 30 20 * * ?")
    public void task() {
        System.out.println("线程准备运行");
        red("https://yaoyao.dynv6.net/onedriveyaoyao/Telegram/");
        red("https://yaoyao.dynv6.net/onedriveyaoyao/right/");
        red("https://yaoyao.dynv6.net/Telegram/Telegram/");
        red("https://yaoyao.dynv6.net/onedrive/91video/");
        red("https://yaoyao.dynv6.net/onedriveyaoyao/91_urlvideo/");
        red("https://yaoyao.dynv6.net/onedriveyaoyao/91_video/");
    }


    public static HashMap<String, String> show(String str) {
        //post遍历数据0
        String pattern = ".*preview.*";
        boolean b = true;
        int page = 1;
        HashMap<String, String> Map = new HashMap<>();
        while (b) {
            Document parse = null;
            try {
                parse = Jsoup.connect(str).postDataCharset("utf-8").data("pagenum", String.valueOf(page)).post();
            } catch (Exception e) {
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException ex) {
                    continue;
                }
                continue;
            }
            Element tr1 = parse.getElementById("tr1");
            if (tr1 == null) {
                break;
            } else {
                Element elementById = parse.getElementById("list-table");
                Elements a = elementById.getElementsByTag("a");
                for (Element element : a) {
                    String href = element.attr("href");
                    if (href.equals("") || href.equals("/") || href.equals("/杂七杂八/") || Pattern.matches(pattern, href) || href.equals("/onedrive/") || href.equals(" ")) {
                        continue;
                    }
                    String[] split = href.split("/");
                    Map.put(str + href, split[0]);
                }
                if (parse.getElementById("nextpageform") == null) {
                    break;
                }
                page++;
            }

        }
        return Map;
    }

    public void add(String url) {
        LambdaQueryWrapper<TelegramPOJO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TelegramPOJO::getName, url);
        TelegramPOJO one = telegramServices.getOne(lambdaQueryWrapper);
        if (one == null){
            TelegramPOJO telegramPOJO = new TelegramPOJO();
            telegramPOJO.setName(url);
            telegramServices.save(telegramPOJO);
        }
    }

    public void red(String st) {
        HashMap<String, String> show = show(st);
        Set<Map.Entry<String, String>> entries = show.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String value = entry.getValue();
           add(value);
        }
    }
    public void reds(String st) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(st);
        }


    }
}
