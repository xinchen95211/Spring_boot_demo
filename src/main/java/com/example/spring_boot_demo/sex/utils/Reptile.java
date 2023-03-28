package com.example.spring_boot_demo.sex.utils;

import com.example.spring_boot_demo.sex.Dao.GirlsMapper;
import com.example.spring_boot_demo.sex.Dao.LogerMapper;
import com.example.spring_boot_demo.sex.DoMain.GirlsPOJO;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;


/**
 * xgmn02.com 爬虫多线程工具类
 */
public class Reptile implements Runnable {
    private String top_url;
    private String url;
    private String tablename;
    private String reg = "(?<=com).*?(?=(/upload))";

    private LogerMapper logerMapper;
    private GirlsMapper girlsMapper;
    private ExecutorService executorService;
    private String path_url = "jpmn5/output/";

    public Reptile(String top_url, String url, String tablename, ExecutorService executorService, GirlsMapper girlsMapper, LogerMapper logerMapper) {
        this.top_url = top_url;
        this.url = url;
        this.tablename = tablename;
        this.executorService = executorService;
        this.girlsMapper = girlsMapper;
        this.logerMapper = logerMapper;
    }

    @Override
    public void run() {
        //输出目录
        //访问线程给定的url
        Document accessor = GrilsUtils.Accessor(url);

        //获取相应写真的名称
        Elements select = accessor.select(".widget-title a");
        //遍历获取
        for (Element element : select) {
            //名字
            String photo_name = element.text();
            //通过名字获取封装的pojo对象
//            System.out.println(photo_name);
            GirlsPOJO girlsPOJO = girlsMapper.select_for_name(photo_name);
            boolean flag = false;
            if (girlsPOJO == null) {
                flag = true;
                girlsPOJO = new GirlsPOJO();
                Map<String, ArrayList<String>> maps = new HashMap<>();
                ArrayList list = new ArrayList<>();
                maps.put("success", list);
                maps.put("error", list);
                girlsPOJO.setCollection(GrilsUtils.map_str(maps));
                girlsPOJO.setTotal(-1);
            }
            Map<String, ArrayList<String>> map = GrilsUtils.string_map(girlsPOJO.getCollection());
            ArrayList<String> success_list = map.get("success");
            ArrayList<String> error_list = map.get("error");
            girlsPOJO.setName(photo_name);
            if (girlsPOJO.getTotal() != -1 && girlsPOJO.getSuccessTotal() == girlsPOJO.getTotal()) {
                continue;
            }

            String href = element.attr("href");
            ArrayList<String> Total = total_photo_url(top_url + href);
//            System.out.println(photo_name + ":"+Total);
            if (Total == null || Total.size() == 0) {
                continue;
            }


            girlsPOJO.setTotal(Total.size());

            //遍历集合
            //判断相应文件是否存在
            new File(path_url + tablename).mkdir();

            for (String photo_url : Total) {
                String[] split = photo_url.split("/");
                String img_file_name = split[split.length - 1].replace("?jpxgyw.com", "");
                if (success_list.contains(img_file_name) || error_list.contains(photo_url)) {
                    continue;
                }
                try {
                    URL path_URL = new URL((top_url + photo_url).replaceAll(reg, ""));
                    File photo_down = new File(path_url + tablename + "/" + photo_name + "/" + img_file_name);

                    //TODO 添加文件是否存在判断
                    if (photo_down.exists()) {
                        continue;
                    }

                    FileUtils.copyURLToFile(path_URL, photo_down);
                    success_list.add(img_file_name);
                } catch (FileNotFoundException e) {
                    error_list.add(photo_url);
                } catch (Exception e) {
                    Total.clear();
                    success_list.clear();
                    error_list.clear();
                    map.clear();
                    executorService.submit(new Reptile(top_url, url, tablename, executorService, girlsMapper, logerMapper));

                }

            }


            girlsPOJO.setErrorTotal(error_list.size());
            girlsPOJO.setSuccessTotal(success_list.size());
            girlsPOJO.setCollection(GrilsUtils.map_str(map));
            girlsPOJO.setTableName(tablename);
            if (flag) {
                girlsMapper.add(girlsPOJO);
            } else {
                girlsMapper.update(girlsPOJO);
            }
//                System.out.println(girlsPOJO);
            logerMapper.add(tablename + "->" + photo_name + "->抓取完毕");
            Total.clear();
            success_list.clear();
            error_list.clear();
            map.clear();
        }
    }

    public void reqtiles() {


    }

    /**
     * 链接获取所有照片集合
     *
     * @param str
     * @return
     */
    public static ArrayList<String> total_photo_url(String str) {
        ArrayList list = new ArrayList<>();

        Document accessor = GrilsUtils.Accessor(str);

        if (accessor.select("font b").html().equals("预")) {
            return null;
        }
        String url = str.replace(".html", "");

        int count = 0;
        while (true) {
            if (count != 0) {
                accessor = GrilsUtils.Accessor(url + "_" + count + ".html");
            }
            if ("访问页面出错了".equals(accessor.title())) {
                break;
            }
            Elements selects = accessor.select("article img");
            for (Element element : selects) {
                list.add(element.attr("src"));

            }
            count++;
        }
        return list;
    }


}
