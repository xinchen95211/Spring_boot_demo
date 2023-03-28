package com.example.spring_boot_demo.photo.utils;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.spring_boot_demo.photo.Dao.TableMapper;
import com.example.spring_boot_demo.photo.DoMain.PhotoTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Option implements Runnable {


    //前缀
    private TableMapper tableMapper;
    private String prefixs;

    public Option(TableMapper tableMapper, String prefixs) {
        this.tableMapper = tableMapper;
        this.prefixs = prefixs;
    }

    public Option(String prefixs) {
        this.prefixs = prefixs;
    }

    public Option() {
    }


    @Override
    public void run() {
        //前缀
        String prefix = this.prefixs;
        String[] split = prefix.split("/");

        String namesd = split[split.length - 1];
        if (namesd == null || namesd.equals("写真") || namesd.equals("%E5%86%99%E7%9C%9F")) {
            namesd = "photo";
        }

        int page = 1;
        int id = 0;
        Document post_a = null;
        do {
            post_a = post(prefix, page);
            Element tr1 = post_a.getElementById("tr1");
            if (tr1 == null) {
                break;
            } else {
                Elements select = post_a.select("a[name='folderlist']");
                for (Element element : select) {
                    //name
                    String name = element.html();
                    QueryWrapper qw = new QueryWrapper<>().eq("name", name);
                    PhotoTable photoTable_str = tableMapper.selectOne(qw);
                    if (photoTable_str == null) {
                        String namepath = element.attr("href").replace("/", "");
                        String suffix = URLEncoder.encode(namepath);
                        //后缀suffix
                        suffix = suffix.replace("+", "%20").replace("%25", "%");
                        String strs = prefix + suffix + "/";
                        //集合
                        List<String> list = new ArrayList<>();
                        int pages = 1;
                        Document post_b = null;
                        do {
                            post_b = post(strs, pages);

                            Element tr1s = post_b.getElementById("tr1");
                            if (tr1s == null) {
                                break;
                            } else {
                                Elements select1s = post_b.select("a[class='download']");
                                for (Element select1 : select1s) {
                                    String href = select1.attr("href");
//                                System.out.println("href"+href);
                                    list.add(href);
                                }
                                pages++;
                            }
                        } while (post_b.getElementById("nextpageform") != null);
                        //判断是否存在

                        if (list.size() == 0) {
                            continue;
                        }

                        PhotoTable photoTable = new PhotoTable();
                        photoTable.setName(name);
                        photoTable.setThumbnail(strs + list.get(0));
                        photoTable.setTable_name(namesd);
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name);
                        map.put("prefix", prefix);
                        map.put("suffix", suffix);
                        map.put("collection", list);
                        ObjectMapper obj = new ObjectMapper();
                        try {
                            String s = obj.writeValueAsString(map);
                            photoTable.setJson(s);
                            tableMapper.insert(photoTable);
                            System.out.println("添加成功");
                        } catch (JsonProcessingException e) {
                            System.out.println(e);
                        }
                    } else {
                        System.out.println(name + "跳过了");
                    }
                }
                page++;
            }
        } while (post_a.getElementById("nextpageform") != null);

    }

    public static Document post(String url, int page) {
        Document parse = null;
        try {
            parse = Jsoup.connect(url).postDataCharset("utf-8").data("pagenum", String.valueOf(page)).post();
        } catch (IOException e) {
            return post(url, page);
        }
        return parse;
    }

}
