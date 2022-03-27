package com.lucas.excel.lucasexcel.mapper;

import com.lucas.excel.lucasexcel.model.WeiDianModel;
import com.lucas.excel.lucasexcel.model.ordinary.SongYuanHuiModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DemoMapper {
    void save(List<WeiDianModel> list);

    void saveSYH(List<SongYuanHuiModel> list);
}
