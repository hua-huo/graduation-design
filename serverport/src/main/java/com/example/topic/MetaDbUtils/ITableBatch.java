package com.example.topic.MetaDbUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019\5\9 0009.
 */
public interface ITableBatch {
    public List<Map> tableBatch( List<Map> list,Map map) throws ParseException;
}
