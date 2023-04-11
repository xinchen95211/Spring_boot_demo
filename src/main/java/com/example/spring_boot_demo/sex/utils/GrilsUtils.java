package com.example.spring_boot_demo.sex.utils;

import com.example.spring_boot_demo.sex.Dao.LogerMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


/**
 * 工具类
 */
@Slf4j
public class GrilsUtils {


    public static LogerMapper logerMapper;


    /**
     * 传入一个连接，尝试访问200次后未访问成功则抛出错误
     */
    public static Document Accessor(String url) {
        Document parse = null;
        try {
            parse = Jsoup.parse(new URL(url), 8000);
        } catch (Exception e) {
            return Accessor(url, 1);
        }
        return parse;

    }

    public static Document Accessor(String url, int count) {
        try {
            log.error("访问发生错误，线程将休眠15s");
            Thread.sleep(15000);
        } catch (InterruptedException e) {
        }
        Document parse = null;
        try {
            parse = Jsoup.parse(new URL(url), 10000);
        } catch (Exception e) {
            if (count >= 200) {
                logerMapper.add(url + "访问两百次出错了,错误原因为" + e);
                return null;
            }
            return Accessor(url, ++count);
        }
        return parse;
    }

    /**
     * 去除字符串第一个和最后一个字符
     *
     * @param string
     * @return
     */
    public static String trim(String string) {
        return string.substring(1, string.length() - 1);
    }

    /**
     * 把一个Elements集合转换为ArrayList集合
     */
    public static ArrayList elements_attr_string(Elements elementsByTag, String attr) {
        ArrayList<String> list = new ArrayList<>();
        for (Element element : elementsByTag) {
            list.add(element.attr(attr));
        }
        return list;
    }


    /**
     * 把符合条件的string转为map
     */
    public static Map<String, ArrayList<String>> string_map(String string) {
        try {
            return new ObjectMapper().readValue(string, Map.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 把符合条件的map转为string
     */
    public static String map_str(Map<String, ArrayList<String>> map) {
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return "";
        }
    }


//    弃用
//    //给一个目录范围，读取其中的所有文件名称()
//    public static void old_to_new(File file,GirlsMapper girlsMapper){
//        String name1 = file.getAbsolutePath();
//        System.out.println(name1);
//        //获取其中所有的文件名称
//        File[] files = file.listFiles();
//        for (File file1 : files) {
//            if (file1.getName().equals(".DS_Store")){continue;}
//            String name = file1.getName();
//            //获取更深入的文件名称
//            old_to_new2(file1,girlsMapper);
//        }
//    }
//    public static void old_to_new2(File file,GirlsMapper girlsMapper){
//        String name1 = file.getName();
//        girlsMapper.create_table(name1);
//        System.out.println(name1);
//        //获取其中所有的文件名称
//        File[] files = file.listFiles();
//        for (File file1 : files) {
//            String name = file1.getName();
//            if (name.equals(".DS_Store")){continue;}
//            System.out.println(name);
//            //读取所有的file_state
//            File_State file_state = read_class(name1, name);
//            ArrayList<String> list = file_state.getList();
//            GirlsPOJO girlsPOJO = new GirlsPOJO();
//            System.out.println(list);
//            Map<String, ArrayList<String>> map = new HashMap<>();
//            map.put("success", list);
//            map.put("error", new ArrayList<String>());
//            girlsPOJO.setName(name);
//            girlsPOJO.setTotal(-1);
//            girlsPOJO.setErrorTotal(-1);
//            girlsPOJO.setSuccessTotal(-1);
//            ObjectMapper mapper = new ObjectMapper();
//            try {
//                String s = mapper.writeValueAsString(map);
//                girlsPOJO.setCollection(s);
//
//                boolean add = girlsMapper.add(name1, girlsPOJO);
//                System.out.println(add);
//            } catch (JsonProcessingException e) {
//                System.out.println("mapper:"+e);
//            }
//
//
//
//
//
//
//        }
//    }

    /**
     * 字符串还原Arraylist集合
     *
     * @param str 传入符合规范的字符串
     * @return 返回Arraylist集合
     */
    public static ArrayList<String> StringTOArraylist(String str) {
        ArrayList<String> list = new ArrayList<>();
        if (str == null || str.length() <= 0 || "".equals(str)) {
            return list;
        }
        //分割字符串
        list.addAll(Arrays.asList(StrTrim(str.substring(1, str.length() - 1).split(","))));
        return list;

    }

    /**
     * 去除字符串数组中每个字符串末尾的空格
     *
     * @param strings 字符串数组
     * @return 处理好的字符串数组
     */
    public static String[] StrTrim(String[] strings) {
        //如果数组为空则直接返回
        if (strings == null || strings.length == 0) {
            return strings;
        }
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].trim();
        }
        return strings;
    }
    /**
     * 读取序列化的对象 弃用
     *弃用
     * @param path 要查询的目录
     * @param name 目录下序列化文件的名字->写真的文件夹名字
     * @return File_State对象
     */
//    public static File_State read_class(String path, String name) {
//        //创建一个空的类来接收读取到的序列化对象
//        File_State fileState = new File_State();
//        //文件对象
//        File f = new File("map/" + path + "/" + name);
//        System.out.println(f.getAbsolutePath());
//        //先判断文件相应的文件存不存在，如果存在再读取，如果不存在则直接返回创建好的空对象
//        if (f.exists()) {
//            try {
//                FileInputStream fi = new FileInputStream(f);
//                ObjectInputStream obi = new ObjectInputStream(fi);
//                fileState = (File_State) obi.readObject();
//                obi.close();
//                fi.close();
//            } catch (IOException | ClassNotFoundException e) {
//                System.out.println("发生错误错误为" + e);
//            }
//        }
//        return fileState;
//    }
}
