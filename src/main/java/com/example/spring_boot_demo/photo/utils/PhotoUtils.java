package com.example.spring_boot_demo.photo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class PhotoUtils {
    public static Object string_to_map(String str) {
        try {
            return new ObjectMapper().readValue(str, Map.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 把符合条件的map转为string
     */
    public static String map_str(Object map) {
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return "";
        }
    }


}
