package com.lucas.excel.lucasexcel.model.ordinary;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lucas.excel.lucasexcel.model.CommonModel;
import lombok.Data;

/**
 * 松原会普入
 */
@Data
public class SongYuanHuiModel extends CommonModel {
    @ExcelProperty("项圈颜色")
    private String color;

    @ExcelProperty("项圈个数")
    private String necklaceCount;

    @ExcelProperty("是否加购骨架")
    private String ifBone;
}
