package com.example.topic.MetaDbUtils;

import com.example.topic.service.InvitationService;
import com.example.topic.utils.NumberTool;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2019\5\11 0011.
 */
@Service
public class MetaDbClazzChangeSql extends MetaDbTemplateJdbc {
    private IMetaDbQuery metaDbQuery;
    public  String getInsertSql(Object bean,IMetaDbQuery metaDbQuery)  {
        this.metaDbQuery = metaDbQuery;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Class clazz = bean.getClass();
            Map<String, Object> valueMap = new HashMap<String, Object>();
            // 取出bean里的所有方法
            Method[] methods = clazz.getDeclaredMethods();
            Field[] fields = clazz.getDeclaredFields();
            String tablename = "";
            int objid = 0;
            for (Field field : fields) {
                try {
                    String fieldType = field.getType().getSimpleName();
                    String fieldGetName = parGetName(field.getName());
                    if (!checkGetMet(methods, fieldGetName)) {
                        continue;
                    }
                    Method fieldGetMet = clazz.getMethod(fieldGetName, new Class[]{});
                    Object fieldVal = fieldGetMet.invoke(bean, new Object[]{});
                    String value = null;
                    if ("TABLENAME".equals(field.getName())) {
                        tablename = String.valueOf(fieldVal);

                        continue;
                    } else {
                        if (null != fieldVal && !"0".equals(fieldVal.toString())) {
                            if("String".equals(fieldType)){
                                valueMap.put(field.getName(), "'"+String.valueOf(fieldVal)+"'");
                            }
                            else {
                                valueMap.put(field.getName(), String.valueOf(fieldVal));
                            }
                        }else if("Date".equals(fieldType)) {
                            valueMap.put(field.getName(), "'" + sdf.format(new Date()) + "'");
                        }
                    }

                } catch (Exception e) {
                    continue;
                }
            }
            SQL sql = new SQL();
            if(NumberTool.transitionInteger(valueMap,"objid") > 0 ){
                sql.UPDATE(tablename);
                StringBuffer v = new StringBuffer();
                for (String key : valueMap.keySet()) {
                    if ("objid".equals(key) ) {
                        continue;
                    }
                    if("update_at".equals(key) ||"create_at".equals(key)){
                        sql.SET(" " + key + "='" + sdf.format(new Date(valueMap.get(key).toString())) + "'\n ");
                    }else{
                        sql.SET(" " + key + "=" + valueMap.get(key) + "\n ");
                    }

//                    sql.VALUES(key,valueMap.get(key));
                }
                sql.WHERE(" objid = " + NumberTool.transitionInteger(valueMap, "objid"));
            }else{
                objid = getMaxObjid(tablename);
                sql.INSERT_INTO(tablename);
                for (String key : valueMap.keySet()) {
                    sql.VALUES(key,valueMap.get(key).toString());
                }
                setFieldValue(bean, valueMap, objid);
            }

            return sql.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public  int getMaxObjid(String tablename){
        String sql= "select max(objid)id from "+tablename;
        List<Map> list = metaDbQuery.queryOrderByParam(sql);
        if(list!=null && list.size()>0){
            int id = NumberTool.transitionInteger(list.get(0),"id",0);
            return id+1;
        }
        return 1;
    }
        /**
         * 拼接某属性的 get方法
         * @param fieldName
         * @return String
         */
        private static String parGetName(String fieldName) {
            if (null == fieldName || "".equals(fieldName)) {
                return null;
            }
            return "get" + fieldName.substring(0, 1).toUpperCase()
                    + fieldName.substring(1);
        }
    /**
     * 判断是否存在某属性的 get方法
     * @param methods
     * @param fieldGetMet
     * @return boolean
     */
    private static boolean checkGetMet(Method[] methods, String fieldGetMet) {
        for (Method met : methods) {
            if (fieldGetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * set属性的值到Bean
     * @param bean
     * @param valMap
     */
    public  void setFieldValue(Object bean, Map<String, Object> valMap,int objid) {
        Class<?> cls = bean.getClass();
        // 取出bean里的所有方法
//        Method[] methods = cls.getDeclaredMethods();
//        Field[] fields = cls.getDeclaredFields();

//        for (Field field : fields) {
            try {
//                String fieldSetName = parSetName(field.getName());
//                if (!checkSetMet(methods, fieldSetName)) {
//                    continue;
//                }
                String fieldSetName="setObjid";
                Method fieldSetMet = cls.getMethod(fieldSetName, int.class);
//                String value = valMap.get(field.getName());
                int value = objid;
                fieldSetMet.invoke(bean, value);
            } catch (Exception e) {

            }

    }
    /**
     * 拼接在某属性的 set方法
     * @param fieldName
     * @return String
     */
    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "set" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }
    /**
     * 判断是否存在某属性的 set方法
     * @param methods
     * @param fieldSetMet
     * @return boolean
     */
    private static boolean checkSetMet(Method[] methods, String fieldSetMet) {
        for (Method met : methods) {
            if (fieldSetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }
}
