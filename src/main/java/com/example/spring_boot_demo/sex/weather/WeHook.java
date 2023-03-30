package com.example.spring_boot_demo.sex.weather;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Component
public class WeHook {
    //导入微信配置
    @Value("${wx.we_hook}")
    private String wx_hook;
    private RestTemplate client = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    /**
     * 发送本地图片
     * @param file 文件路径
     */
    public void Toest_img(File file){
        headers.set("Content-Type","application/json; charset=utf-8");
        HttpMethod post = HttpMethod.POST;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] bytes = fileInputStream.readAllBytes();
            String md5 = DigestUtils.md5DigestAsHex(bytes);
            String base64 = Base64.getEncoder().encodeToString(bytes);
            HashMap<String,Object> map = new HashMap<>();
            HashMap<String,Object> map2 = new HashMap<>();
            map2.put("md5",md5);
            map2.put("base64",base64);
            map.put("msgtype", "image");
            map.put("image", map2);
            HttpEntity<HashMap<String,Object>> requestEntity = new HttpEntity<>(map);
            client.exchange(wx_hook,
                    post,
                    requestEntity,
                    String.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 发送文字
     * @param str 发送的文字信息
     */
    public void Toest_text(String str){
        headers.set("Content-Type","application/json; charset=utf-8");
        HttpMethod post = HttpMethod.POST;
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("content",str);
        map.put("msgtype", "text");
        map.put("text", map2);
        HttpEntity<HashMap> requestEntity = new HttpEntity<>(map);
       client.exchange(wx_hook,
                post,
                requestEntity,
                String.class);
    }
    /**
     * 发送网络图片
     * @param inputStream 输入流
     */
    public void Toest_net_img(InputStream inputStream){
        headers.set("Content-Type","application/json; charset=utf-8");
        HttpMethod post = HttpMethod.POST;
        try {
            byte[] bytes = inputStream.readAllBytes();
            String md5 = DigestUtils.md5DigestAsHex(bytes);
            String base64 = Base64.getEncoder().encodeToString(bytes);
            HashMap<String,Object> map = new HashMap<>();
            HashMap<String,Object> map2 = new HashMap<>();
            map2.put("md5",md5);
            map2.put("base64",base64);
            map.put("msgtype", "image");
            map.put("image", map2);
            HttpEntity<HashMap<String,Object>> requestEntity = new HttpEntity<>(map);
            client.exchange(wx_hook,
                    post,
                    requestEntity,
                    String.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
