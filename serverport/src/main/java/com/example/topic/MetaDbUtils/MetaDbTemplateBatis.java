package com.example.topic.MetaDbUtils;

import com.example.topic.model.Invitation;

/**
 * Created by Administrator on 2019\5\11 0011.
 */
public class MetaDbTemplateBatis extends MetaDbClazzChangeSql {
    private IMetaDbQuery metaDbInquire;
    public MetaDbTemplateBatis init(IMetaDbQuery metaDbInquire) {
        this.metaDbInquire = metaDbInquire;
        return this;
    }
    public void save(Object clazz){
        String sql = getInsertSql(clazz,metaDbInquire);
        if(!"".equals(sql)){
            metaDbInquire.queryOrderByParam(sql);
        }
    }
}
