package com.example.spring_boot_demo.sex.weather;

import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class Stringutils {
    public Stringutils(String data) {
        this.data = data;
    }

    String data;

    public Object get(String rule) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        if (rule.contains(".")) {
            ArrayList<String> list = new ArrayList<>();
            String[] split = rule.split("\\.");
            List<LinkedHashMap<String, String>> maps;
            try {
                maps = (List<LinkedHashMap<String, String>>) jsonObject.get(split[0]);
            } catch (Exception e) {
                JSONObject json = JSONObject.parseObject(jsonObject.get(split[0]).toString());

                return json.get(split[1]);
            }
            if (maps == null) {
                return jsonObject.get(split[0]);
            } else {
                for (Map<String, String> map : maps) {
                    if (split.length == 2) {
                        return map.get(split[1]);
                    }
                    for (int i = 1; i < split.length; i++) {
                        String s = map.get(split[i]);
                        list.add(s);
                    }
                }
            }
            return list;
        } else {
            return jsonObject.get(rule);

        }
    }
}
