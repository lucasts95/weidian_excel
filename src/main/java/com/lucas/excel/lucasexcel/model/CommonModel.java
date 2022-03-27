package com.lucas.excel.lucasexcel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CommonModel {
    private String uuid;

    @ExcelProperty("微博号")
    private String weiBo;

    @ExcelProperty("qq号")
    private String qq;

    @ExcelProperty("微店昵称")
    private String weiDian;
}
