package com.example.topic.MetaDbUtils;

import com.example.topic.utils.OrderProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019\5\8 0008.
 */
@Mapper
public interface  IMetaDbQuery {
    @SelectProvider(type = OrderProvider.class, method = "queryOrderByParam")
    List<Map> queryOrderByParam(String sql);
}
