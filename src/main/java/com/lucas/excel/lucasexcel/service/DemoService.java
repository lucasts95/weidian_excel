package com.lucas.excel.lucasexcel.service;

import com.lucas.excel.lucasexcel.model.export.ExportModel;

import java.util.List;

/**
 * 假设这个是你的DAO存储。当然还要这个类让spring管理，当然你不用需要存储，也不需要这个类。
 **/

public interface DemoService {
    void save(List<ExportModel> list);
}