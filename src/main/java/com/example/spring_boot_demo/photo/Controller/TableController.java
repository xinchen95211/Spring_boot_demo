package com.example.spring_boot_demo.photo.Controller;

import com.example.spring_boot_demo.photo.services.Table_Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/photo")
public class TableController {
    @Autowired
    private Table_Services table_services;

    @GetMapping
    public String select_tables_cn() {

        return table_services.selectAllTable();
    }

    @PostMapping
    @ResponseBody
    private String page_select(@RequestBody Map<String, Object> map) {
        String tables = map.get("tables").toString();
        String search = map.get("search").toString();
        int row = (int) map.get("row");
        String paging = table_services.paging_redis(row, tables, search);
        return paging;
    }

    @GetMapping("/{id}")
    public String select_fo_id(@PathVariable long id) {
        return table_services.id_for_json(id);
    }
}
