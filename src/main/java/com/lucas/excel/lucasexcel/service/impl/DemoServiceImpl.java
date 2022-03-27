package com.lucas.excel.lucasexcel.service.impl;

import com.lucas.excel.lucasexcel.mapper.DemoMapper;
import com.lucas.excel.lucasexcel.model.export.ExportModel;
import com.lucas.excel.lucasexcel.model.ordinary.SongYuanHuiModel;
import com.lucas.excel.lucasexcel.service.DemoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DemoServiceImpl implements DemoService {
    @Resource
    DemoMapper demoMapper;

    @Override
    public void save(List<ExportModel> list) {
        String toyCode = "";
        String necklaceCode = "";
        String boneCode = "";

        // 只有尾款无其他: 只有娃娃, 无项圈,无骨架

        // 根据account分组
        Map<String, List<ExportModel>> collect = list.stream().collect(Collectors.groupingBy(ExportModel::getAccount));

        /**
         * 1. value是list, 退款状态("退款完成(全额退款)")
         * 2. 商品id有多个
         */
        ArrayList<SongYuanHuiModel> result = new ArrayList<>();
        for (Map.Entry<String, List<ExportModel>> entry : collect.entrySet()) {
            List<ExportModel> valueList = entry.getValue();
            SongYuanHuiModel songYuanHuiModel = new SongYuanHuiModel();


            if (valueList.size() == 1) {
                ExportModel exportModel = valueList.get(0);

                String template = exportModel.getTemplate();
                List<String> templateMsg = Arrays.asList(template.split(";"));
                Optional<String> weiboOpt = templateMsg.stream().filter(msg -> msg.contains("微博")).findAny();
                Optional<String> qqOpt = templateMsg.stream().filter(msg -> msg.contains("qq号")).findAny();

                songYuanHuiModel.setWeiBo(weiboOpt.orElse(""));
                songYuanHuiModel.setQq(qqOpt.orElse(""));
                songYuanHuiModel.setWeiDian(exportModel.getConsignee());

                // 一个account只有一个订单
                if (StringUtils.isNotEmpty(exportModel.getRefund())) {
                    // 如果退款,跳过
                    continue;
                }

                if (exportModel.getStatus().equals("已关闭")) {
                    // 如果订单关闭,跳过
                    continue;
                }
                // 查看商品id是否有多个
                List<String> productIds = Arrays.asList(exportModel.getProductID().split(";"));
                HashMap<String, Integer> productMap = new HashMap<>();
                productIds.forEach(id->{
                    if (!productMap.containsKey(id)) {
                        productMap.put(id, 1);
                    } else {
                        productMap.put(id, productMap.get(id) + 1);
                    }
                });

                int size = productMap.size();
                // 再根据型号编码来判断是什么
                if (size > 1) {
                    // 根据map获取count
                    for (Map.Entry<String, Integer> mapEntry: productMap.entrySet()) {
                        if (mapEntry.getKey().equals(necklaceCode)) {
                            songYuanHuiModel.setNecklaceCount(mapEntry.getValue().toString());
                        }
                        if (mapEntry.getKey().equals(boneCode)) {
                            songYuanHuiModel.setIfBone("是");
                        }
                    }
                    List<String> modelCodes = Arrays.asList(exportModel.getModelCode().split(";"));
                    // 商品ID和型号编码 一一对应
                    StringBuilder color = new StringBuilder();

                    // 配件数量 根据商品id区分
                    for (int i = 0; i < productIds.size(); i++) {
                        String productId = productIds.get(i);
                        if (!productId.equals(toyCode)) {
                            color.append(modelCodes.get(i));
                        }
                    }
                    songYuanHuiModel.setColor(color.toString());
                }
            } else {
                for (ExportModel exportModel : valueList) {
                    // 多个订单
                    if (StringUtils.isNotEmpty(exportModel.getRefund())) {
                        // 如果退款,跳过
                        continue;
                    }

                    if (exportModel.getStatus().equals("已关闭")) {
                        // 如果订单关闭,跳过
                        continue;
                    }
                    String template = exportModel.getTemplate();
                    List<String> templateMsg = Arrays.asList(template.split(";"));
                    Optional<String> weiboOpt = templateMsg.stream().filter(msg -> msg.contains("微博")).findAny();
                    Optional<String> qqOpt = templateMsg.stream().filter(msg -> msg.contains("qq号")).findAny();

                    songYuanHuiModel.setWeiBo(weiboOpt.orElse(""));
                    songYuanHuiModel.setQq(qqOpt.orElse(""));
                    songYuanHuiModel.setWeiDian(exportModel.getConsignee());

                    // todo 商品不同类型用不同订单 保证商品编码 商品id是独立的, 型号编码用来 区分颜色等

                }


            }
        }
        // 逻辑处理
        demoMapper.saveSYH(list);
    }
}
