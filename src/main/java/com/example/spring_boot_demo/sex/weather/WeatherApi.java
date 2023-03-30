package com.example.spring_boot_demo.sex.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.events.Event;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class WeatherApi {
    @Value("${weather.key}")
    private String key;
    @Value("${weather.location}")
    private String location;
    private String weather_api = "https://www.qweather.com/weather/shanghai-101020100.html";
    //现在天气

    //分钟级降水预报

    //https://devapi.qweather.com/v7/weather/3d
    //https://devapi.qweather.com/v7/weather/7d
    //https://devapi.qweather.com/v7/weather/10d
    //https://devapi.qweather.com/v7/weather/15d
    //https://devapi.qweather.com/v7/weather/30d
    //https://devapi.qweather.com/v7/weather/24h
    //https://devapi.qweather.com/v7/weather/72h
    //https://devapi.qweather.com/v7/weather/168h
    //https://devapi.qweather.com/v7/grid-weather/now?
    //https://devapi.qweather.com/v7/grid-weather/3d?
    //https://devapi.qweather.com/v7/grid-weather/7d
    //https://devapi.qweather.com/v7/grid-weather/24h?
    //https://devapi.qweather.com/v7/grid-weather/72h?
    //https://devapi.qweather.com/v7/warning/now
    //https://devapi.qweather.com/v7/indices/1d
    //https://devapi.qweather.com/v7/indices/3d
    //https://devapi.qweather.com/v7/air/now
    //https://devapi.qweather.com/v7/air/5d
    /**
     * 获取城市列表
     */

    public void get_city_location(String adm,String location){
//        String url = "https://geoapi.qweather.com/v2/city/lookup?location=%E5%AE%9D%E5%B1%B1&adm=%E4%B8%8A%E6%B5%B7&key=135be044fcf841fd8b43b9064e712bff";
//
//        try {
//            System.out.println(url);
//            Document document = Jsoup.connect(url).ignoreContentType(true).get();
//            System.out.println(document);
//        } catch (IOException e) {
//            System.out.println(e);
//            this.get_city_location(adm, location);
//        }
    }
    /**
     * 获取网页的文字展示
     */
    public String web_weather(){
            Document weather_apis = WeatherUtils.api(weather_api);
            return weather_apis.select(".current-abstract").text();
    }

    //分钟级降水
    public String minutely_weather(){
        String minutely = "https://devapi.qweather.com/v7/minutely/5m?location="+location+"&key="+key;
        Document api = WeatherUtils.get_api(minutely);
        if (api == null){return "";}
        try {
            Map<String, Object> map = new ObjectMapper().readValue(api.body().text(), Map.class);
            String summary = map.get("summary").toString();
            return summary;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 现在天气
     * @return
     */
    public String now_weather(){
        String weather_now = "https://devapi.qweather.com/v7/weather/now?location="+location+"&key="+key;
        Document api = WeatherUtils.get_api(weather_now);
        if (api == null){return "";}
        try {
            Map<String, Object> map = new ObjectMapper().readValue(api.body().text(), Map.class);
            Object code = map.get("code");
            log.info(code.toString());
            if (code.equals("200")){
                Map<String,String> now = (Map<String, String>) map.get("now");
                //温度
                String temp = now.get("temp");
                //体感温度
                String feelsLike = now.get("feelsLike");
                //文字描述
                String text = now.get("text");
                //风向
                String windDir = now.get("windDir");
                //风力等级
                String windScale = now.get("windScale");
                StringBuilder sb = new StringBuilder();
                String s = minutely_weather();
                sb.append("上海宝山").append("\n");
                sb.append(s).append("\n");
                sb.append("现在天气:").append(text).append("\n");
                sb.append(windDir).append(windScale).append("级").append("\n");
                sb.append("室外温度:").append(temp).append("\n");
                sb.append("体感温度:").append(feelsLike).append("");
                return sb.toString();
            }
            return "";
        } catch (JsonProcessingException e) {
            return null;
        }

    }
    public String today_weather(){
        String weather_today = "https://devapi.qweather.com/v7/weather/3d?location="+location+"&key="+key;
        Document api = WeatherUtils.get_api(weather_today);
        if (api == null){return "";}
        try {
            Map<String, Object> map = new ObjectMapper().readValue(api.body().text(), Map.class);
            Object code = map.get("code");
            log.info(code.toString());
            if (code.equals("200")){
                List<Map<String, Object>> daily = (List<Map<String, Object>>) map.get("daily");
                StringBuilder sb = new StringBuilder();


                String s1 = web_weather();
                sb.append(s1).append("\n\n");
                sb.append("上海宝山").append("\n");
                String s = minutely_weather();
                sb.append(s).append("\n\n\n");
                int count = 0;
                String day_text = "今天";
                for (Map<String, Object> Maps : daily) {
                    if (count == 1){
                        day_text = "明天";
                    }
                    if (count == 2){
                        day_text = "后天";
                    }
                    sb.append(day_text).append("白天:").append(Maps.get("textDay")).append("\n");
                    sb.append(day_text).append("夜间:").append(Maps.get("textNight")).append("\n");
                    sb.append("最低温度:").append(Maps.get("tempMin")).append("\n");
                    sb.append("最高温度:").append(Maps.get("tempMax")).append("\n");
                    sb.append("\n\n");
                    count++;
                }
                return sb.toString();
            }
            return "";
        } catch (JsonProcessingException e) {
            return null;
        }
    }




}
