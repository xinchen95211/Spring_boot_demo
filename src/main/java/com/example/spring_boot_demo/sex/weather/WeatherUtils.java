package com.example.spring_boot_demo.sex.weather;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

@Slf4j
public class WeatherUtils {
    public static Document api(String url){
        try {
            return Jsoup.parse(new URL(url), 5000);
        } catch (IOException e) {
            return api(url,1);
        }

    }
    public static Document api(String url, int num){
        if (num > 100){return null;}
        try {
            return Jsoup.parse(new URL(url), 5000);
        } catch (IOException e) {
            return api(url,++num);
        }
    }
    public static Document get_api(String url){
        try {
            Document document = Jsoup.connect(url).ignoreContentType(true).get();
            return document;
        } catch (IOException e) {
            return get_api(url,1);
        }

    }
    public static Document get_api(String url,int num){
        if (num > 100){return null;}
        try {
            Document document = Jsoup.connect(url).ignoreContentType(true).get();
            return document;
        } catch (IOException e) {
            return get_api(url,++num);
        }
    }
}
