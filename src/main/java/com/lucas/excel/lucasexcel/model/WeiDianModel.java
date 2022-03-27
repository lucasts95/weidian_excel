package com.lucas.excel.lucasexcel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class WeiDianModel extends CommonModel{
    @ExcelProperty("是否加购双马尾")
    private String ifTail;

    @ExcelProperty("是否加购骨架")
    private String ifBone;
}
