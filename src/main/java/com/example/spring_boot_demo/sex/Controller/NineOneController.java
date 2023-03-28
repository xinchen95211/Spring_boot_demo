package com.example.spring_boot_demo.sex.Controller;

import com.example.spring_boot_demo.sex.Dao.NineOneMapper;
import com.example.spring_boot_demo.sex.Dao.OneMapper;
import com.example.spring_boot_demo.sex.DoMain.NineOneUrl;
import com.example.spring_boot_demo.sex.DoMain.OneUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//添加@Rest风格注解
@RestController
//定义访问模块
@RequestMapping("/sex")
@CrossOrigin(origins = "*")
public class NineOneController {
    //自动装配
    @Autowired
    private NineOneMapper nineOneMapper;
    @Autowired
    private OneMapper oneMapper;

    @GetMapping("/nineone/{url}")
    public String select_for_name(@PathVariable String url) {
        if (url == null || url.equals("")) {
            //参数为空
            return "a";
        }
        List<NineOneUrl> nineOneUrls = nineOneMapper.select_for_name(url);
        if (nineOneUrls != null && nineOneUrls.size() > 0) {
            //数据存在
            return "b";
        }
        //数据不存在
        return "c";
    }

    @GetMapping("/one/{url}")
    public String select(@PathVariable String url) {
        if (url == null || url.equals("")) {
            //参数为空
            return "a";
        }
        List<OneUrl> nineOneUrls = oneMapper.select_for_name(url);
        if (nineOneUrls != null && nineOneUrls.size() > 0) {
            //数据存在
            return "b";
        }
        //数据不存在
        return "c";
    }

    /**
     * 网络检测
     *
     * @return
     */
    @GetMapping("/checknet")
    public String checknet() {
        return "ok";
    }
}
