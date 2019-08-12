package com.example.topic.service;

import com.example.topic.MetaDbUtils.MetaDbTemplate;
import com.example.topic.MetaDbUtils.MetaDbTemplateBatis;
import com.example.topic.MetaDbUtils.MetaDbTemplateJdbc;
import com.example.topic.dao.UserDao;
import com.example.topic.model.User;
import com.example.topic.utils.NumberTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019\5\13 0013.
 */
@Service
public class UserService extends MetaDbTemplateJdbc {
    @Autowired
    private UserDao userDao;

    public User getUser(String username){
        List<User> list = userDao.get(username);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
    public int getMaxObjid(){
        String sql="select max(objid)+1 objid from spr_user";
        MetaDbTemplate metaDbTemplate=this.getMetaDbTemplate(sql);
        List<Map> list =metaDbTemplate.getResult();
        if(list !=null && list.size()>0){
            return NumberTool.transitionInteger(list.get(0),"objid",1);
        }
        return 1;
    }
    public void saveUser(User user){
        this.save(user);
    }
}
