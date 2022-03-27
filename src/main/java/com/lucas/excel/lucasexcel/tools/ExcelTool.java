package com.lucas.excel.lucasexcel.tools;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.lucas.excel.lucasexcel.model.DemoData;
import com.lucas.excel.lucasexcel.model.WeiDianModel;
import com.lucas.excel.lucasexcel.service.DemoService;
import com.lucas.excel.lucasexcel.util.TestFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Slf4j
@Component
public class ExcelTool {
    @Resource
    DemoService service;

    /**
     * 最简单的读
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>
     * 3. 直接读即可
     */
    public void simpleRead() {

        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // since: 3.0.0-beta1
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "export.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取3000条数据 然后返回过来 直接调用使用数据就行
//        EasyExcel.read(fileName, DemoData.class, new PageReadListener<DemoData>(dataList -> {
//            for (DemoData demoData : dataList) {
//                log.info("读取到一条数据{}", JSON.toJSONString(demoData));
//            }
//        })).sheet().doRead();

//        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
//        // 写法3：
//        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
//        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
//        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
//
//        // 写法4：
//        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";

        // 写法2：
        // 匿名内部类 不用额外写一个DemoDataListener
//        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, WeiDianModel.class, new ReadListener<WeiDianModel>() {
            /**
             * 单次缓存的数据量
             */
            public static final int BATCH_COUNT = 100;
            /**
             *临时存储
             */
            private List<WeiDianModel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            @Override
            public void invoke(WeiDianModel data, AnalysisContext context) {
                cachedDataList.add(data);
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData(cachedDataList);
                    // 存储完成清理 list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData(cachedDataList);
            }

            /**
             * 加上存储数据库
             */
            private void saveData(List<WeiDianModel> cachedDataList) {
                log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                service.save(cachedDataList);
                log.info("存储数据库成功！");
            }
        }).sheet().doRead();

        // 一个文件一个reader
//        ExcelReader excelReader = null;
//        try {
//            excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
//            // 构建一个sheet 这里可以指定名字或者no
//            ReadSheet readSheet = EasyExcel.readSheet(0).build();
//            // 读取一个sheet
//            excelReader.read(readSheet);
//        } finally {
//            if (excelReader != null) {
//                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
//                excelReader.finish();
//            }
//        }
    }
}
