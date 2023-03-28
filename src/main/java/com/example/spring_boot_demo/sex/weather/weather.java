package com.example.spring_boot_demo.sex.weather;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;

public class weather {
    static final String nowapi = "https://devapi.qweather.com/v7/weather/now?location=101020300&key=135be044fcf841fd8b43b9064e712bff";
    static final String onedapi = "https://devapi.qweather.com/v7/weather/24h?location=101020300&key=135be044fcf841fd8b43b9064e712bff";
    static final String minutely = "https://devapi.qweather.com/v7/minutely/5m?location=116.38,39.91&key=135be044fcf841fd8b43b9064e712bff";
    static final String threeapi = "https://devapi.qweather.com/v7/weather/3d?location=116.38,39.91&key=135be044fcf841fd8b43b9064e712bff";

    public static String nowweather() {
        Stringutils string = getString(nowapi);
        //现在天气状况
        Object text = string.get("now.text");
        //现在天气温度
        Object temp = string.get("now.temp");
        //现在体感温度
        Object feelsLike = string.get("now.feelsLike");
        //现在风力等级
        Object windscale = string.get("now.windScale");
        //风向
        Object windDir = string.get("now.windDir");
        return "现在天气:" + text + "\n" + windDir + windscale + "级\n室外温度:" + temp + "°C\n体感温度:" + feelsLike + "°C**" + "\n";
    }

    public static String oneweather() {
        Stringutils string = getString(onedapi);
        ArrayList<String> temp = (ArrayList<String>) string.get("hourly.fxTime.text.temp.windDir.windScale");
        StringBuilder temps = new StringBuilder();
        for (int i = 0; i < temp.size(); i++) {
            if (i % 5 == 0) {
                temps.append(">" + temp.get(i).replace("2023-", "").replace("+08:00", "") + ":<font color=\\\"comment\\\">\n");
            } else if (i % 5 == 1) {
                temps.append("天气:" + temp.get(i) + "   ");
            } else if (i % 5 == 2) {
                temps.append(temp.get(i) + "°C\n");
            } else if (i % 5 == 3) {
                temps.append(temp.get(i));
            } else if (i % 5 == 4) {
                temps.append(temp.get(i) + "级\n</font>");
            }
        }
        temp.clear();
        System.out.println(temps.toString());
        return temps.toString();
    }

    public static String minutelyweather() {
        Stringutils string = getString(minutely);
        Object summary = string.get("summary");
        return summary + "\n";
    }

    public static String threeweather() {
        Stringutils string = getString(threeapi);
        ArrayList<String> temp = (ArrayList<String>) string.get("daily.textDay.tempMin.tempMax.windDirDay.windScaleDay.textNight.windDirNight.windScaleNight");
        StringBuilder temps = new StringBuilder(">今天:<font color=\\\"comment\\\">");
        for (int i = 0; i < temp.size(); i++) {
            if (i == 8) {
                temps.append("</font>\n>**明天:<font color=\\\"comment\\\">");
            } else if (i == 16) {
                temps.append("</font>\n>**后天:<font color=\\\"comment\\\">");
            }
            if (i % 8 == 0) {
                temps.append("白天:" + temp.get(i) + "\t");
            } else if (i % 8 == 1) {
                temps.append(temp.get(i) + "-");
            } else if (i % 8 == 2) {
                temps.append(temp.get(i) + "°C\n");
            } else if (i % 8 == 3) {
                temps.append(temp.get(i));
            } else if (i % 8 == 4) {
                temps.append(temp.get(i) + "级\n");
            } else if (i % 8 == 5) {
                temps.append("晚上:" + temp.get(i) + "\t");
            } else if (i % 8 == 6) {
                temps.append(temp.get(i));
            } else if (i % 8 == 7) {
                temps.append(temp.get(i) + "级");
            }
            if (i == temp.size() - 1) {
                temps.append("</font>");
            }
        }
        temp.clear();
        System.out.println(temps.toString());
        return temps.toString();
    }

    private static Stringutils getString(String api) {
        try {
            String text = Jsoup.connect(api).ignoreContentType(true).get().body().text();
            Stringutils stringutils = new Stringutils(text);
            if (stringutils.get("code").equals("200")) {
                return stringutils;
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }
}
