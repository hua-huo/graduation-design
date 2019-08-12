package com.example.topic.utils;

import java.util.Map;

/**
 * Created by Administrator on 2019\4\29 0029.
 */
public class NumberTool {
    public static int transitionInteger(Map<Object,Object> map,String key,int defaultValue){
        if (map == null || map.size() == 0) {
            return defaultValue;
        }
        return map.get(key) == null || "".equals(map.get(key)) ? defaultValue : Integer.valueOf(map.get(key).toString());
    }
    public static int transitionInteger(Map<String,Object> map,String key ){
        if (map == null || map.size() == 0) {
            return 0;
        }
        return map.get(key) == null || "".equals(map.get(key)) ? 0 : Integer.valueOf(map.get(key).toString());
    }
}
