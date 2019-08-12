package com.example.topic.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by Administrator on 2019\4\29 0029.
 */
public class StringUtils {
    public static String transitionString(Map<Object, Object> map, String key, String defaultValue) {
        if (map == null || map.size() == 0) {
            return defaultValue;
        }
        return map.get(key) == null ? defaultValue : map.get(key).toString();
    }
    public static String toDate(Map<Object, Object> map, String key, String expr) {
        SimpleDateFormat sdf = new SimpleDateFormat(expr);
        String datestr = transitionString(map,key,"");
        if (!"".equals(datestr)) {
            try {
                return sdf.format(sdf.parse(datestr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
