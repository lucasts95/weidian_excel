package com.lucas.excel.lucasexcel.controller;

import com.lucas.excel.lucasexcel.tools.ExcelTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    ExcelTool excelTool;

    @GetMapping(value = "/excel")
    @ResponseBody
    public String excelLogic() {
        excelTool.simpleRead();
        return "hello!";
    }

    @GetMapping(value = "/hello")
    @ResponseBody
    public String hello() {
        return "hello!";
    }

    @GetMapping(value = "/getstr")
    @ResponseBody
    public List<Map<String, Object>> getStr() {
        String sql = "select * from weidian_info";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
}
