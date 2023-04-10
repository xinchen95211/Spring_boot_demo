package com.example.spring_boot_demo.sex.Tasker;

import com.example.spring_boot_demo.sex.Dao.OneMapper;
import com.example.spring_boot_demo.sex.DoMain.OneUrl;
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
public class OneTasker {
    @Autowired
    private OneMapper oneMapper;

    //    @Scheduled(fixedRate = 8640000L)
    @Scheduled(cron = "* 30 5 * * ?")
    public void task() {
        red("https://yaoyao.dynv6.net/onedriveyaoyao/Aria2/");
        red("https://yaoyao.dynv6.net/huifaguang/Aria2/");
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

    public boolean add(String url) {
        List<OneUrl> oneUrl = oneMapper.select_for_name(url);
        if (oneUrl == null || oneUrl.size() == 0) {
            oneMapper.add(url);
            return true;
        }
        return false;
    }

    public void red(String st) {
        HashMap<String, String> show = show(st);
        Set<Map.Entry<String, String>> entries = show.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String value = entry.getValue();
           add(value);
        }
    }
}
