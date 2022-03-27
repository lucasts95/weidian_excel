package com.lucas.excel.lucasexcel.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExportModel {
    @ExcelProperty("订单编号")
    private String orderId;

    @ExcelProperty("订单描述")
    private String orderDescription;

    @ExcelProperty("商品名称")
    private String productName ;

    @ExcelProperty("商品型号")
    private String marque;

    @ExcelProperty("商品编码")
    private String productCode;

    @ExcelProperty("商品ID")
    private String productID;

    @ExcelProperty("型号编码")
    private String modelCode;

    @ExcelProperty("下单账号")
    private String account;

    @ExcelProperty("商品金额合计")
    private String totalValue;

    @ExcelProperty("收货人/提货人姓名")
    private String consignee;

    @ExcelProperty("收货人/提货人手机号")
    private String telephone;

    @ExcelProperty("退款状态")
    private String refund;

    @ExcelProperty("订单状态")
    private String status;

    @ExcelProperty("省")
    private String province;

    @ExcelProperty("市")
    private String city;

    @ExcelProperty("区")
    private String region;

    @ExcelProperty("收货/提货详细地址")
    private String address;

    @ExcelProperty("下单模板信息")
    private String template;
}
