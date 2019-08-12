package com.example.topic.MetaDbUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by Administrator on 2019\5\9 0009.
 */

public class MetaDbTemplateJdbc {
    @Autowired
    private IMetaDbQuery metaDbInquire;
    public MetaDbTemplate getMetaDbTemplate(String sql) {
        MetaDbTemplate metaDbTemplate = new MetaDbTemplate();
        metaDbTemplate.init(metaDbInquire,sql);
        return metaDbTemplate;
    }

    public MetaDbTemplateBatis getMetaDbTemplateBatis() {
        MetaDbTemplateBatis metaDbTemplate = new MetaDbTemplateBatis();
        metaDbTemplate.init(metaDbInquire);
        return metaDbTemplate;
    }
    public void save(Object clazz){
        MetaDbTemplateBatis metaDbTemplate = new MetaDbTemplateBatis();
        metaDbTemplate.init(metaDbInquire);
        metaDbTemplate.save(clazz);
    }
}
