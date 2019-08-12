package com.example.topic.service;

import com.example.topic.MetaDbUtils.MetaDbTemplate;
import com.example.topic.MetaDbUtils.MetaDbTemplateJdbc;
import com.example.topic.config.CurrentUser;
import com.example.topic.model.Invitation;
import com.example.topic.model.Replyinfo;
import com.example.topic.utils.NumberTool;
import com.example.topic.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019\5\7 0007.
 */
@Service
public  class InvitationService extends MetaDbTemplateJdbc {

    /**
     * 查询帖子列表
     * @param param
     * @return
     */
    public MetaDbTemplate qureyInvitation(Map param){
        StringBuffer sql = new StringBuffer("SELECT a.*,b.name FROM invitation a left join spr_user b on a.orgid=b.orgid where 1=1");
        int edition = NumberTool.transitionInteger(param,"edition",1);
        sql.append(" and a.edition="+ edition);
        MetaDbTemplate query = this.getMetaDbTemplate(sql.toString());
        query.setOrderby("create_at desc");
        return query;
    }
    /**
     * 查询我发表的帖子列表
     * @return
     */
    public MetaDbTemplate publishFindByOrgid(){
        StringBuffer sql = new StringBuffer("SELECT a.*,b.name FROM invitation a left join spr_user b on a.orgid=b.orgid where 1=1");
        int orgid = CurrentUser.getUser().getOrgid();
        sql.append(" and a.orgid="+ orgid);
        MetaDbTemplate query = this.getMetaDbTemplate(sql.toString());
        query.setOrderby("create_at desc");
        return query;
}
    /**
     * 查询我回复的帖子列表
     * @return
     */
    public MetaDbTemplate replyFindByOrgid(){
        StringBuffer sql = new StringBuffer(" select * from invitation where objid in (select articleid from REPLYINFO where 1=1 ");
        int orgid = CurrentUser.getUser().getOrgid();
        sql.append(" and orgid="+ orgid +" ) ");
        MetaDbTemplate query = this.getMetaDbTemplate(sql.toString());
        query.setOrderby("create_at desc");
        return query;
}
    public void deleteReplyinfo(int id){
        String sql ="delete from Replyinfo where objid="+id;
        this.getMetaDbTemplate(sql).updateExecute();
    }
    /**
     * 根据帖子ID查询主题帖内容
     * @param objid
     * @return
     */
    public MetaDbTemplate qureyContent(int objid){
        StringBuffer sql =new StringBuffer("SELECT a.*,b.name,b.gender FROM invitation a left join spr_user b on a.orgid=b.orgid ");
//        int objid = NumberTool.transitionInteger(map,"objid",0);
        sql.append(" where a.objid=").append(objid);
        MetaDbTemplate query = this.getMetaDbTemplate(sql.toString());
        query.setOrderby("create_at asc");
        return query;
    }
    public void setReadNum(int objid){
        String maxsql ="select max(readnum)+1 readnum from INVITATION where objid="+objid;
        List<Map> list = this.getMetaDbTemplate(maxsql).getResult();
        int maxread = 0;
        if(list!=null && list.size()>0){
            maxread = NumberTool.transitionInteger(list.get(0),"readnum",1);
        }else{
            maxread = 1;
        }
        String sql = "UPDATE invitation SET readnum="+maxread+" where objid="+objid;
        this.getMetaDbTemplate(sql).updateExecute();
    }
    public void setReplyNum(int articleid){
        String maxsql ="select count(1) countnum  from REPLYINFO where articleid="+articleid;
        List<Map> list = this.getMetaDbTemplate(maxsql).getResult();
        int maxread = 0;
        if(list!=null && list.size()>0){
            maxread = NumberTool.transitionInteger(list.get(0),"countnum",1);
        }else{
            maxread = 1;
        }
        String sql = "UPDATE invitation SET replynum="+maxread+" where objid="+articleid;
        this.getMetaDbTemplate(sql).updateExecute();
    }

    /**
     * 根据帖子ID查询下面所有的回复内容
     * @param articleid
     * @return
     */
    public List<Map> qureyReplyinfo(int articleid){
        StringBuffer sql =new StringBuffer("SELECT a.*,b.name,b.gender FROM REPLYINFO a left join spr_user b on a.orgid=b.orgid ");
//        int objid = NumberTool.transitionInteger(map,"objid",0);
        sql.append(" where a.articleid=").append(articleid);
        MetaDbTemplate query = this.getMetaDbTemplate(sql.toString());
        query.setOrderby("create_at asc");
        return query.getResult();
    }

    /**
     * 查询帖子类型枚举
     * @param module
     * @param type
     * @return
     */
    public List<Map> getEnum(String module,String type) {
        String sql = "SELECT name , value from spr_enum where module='" + module + "' and type='" + type + "'";
        return this.getMetaDbTemplate(sql).getResult();
    }

    /**
     * 查询帖子类型枚举
     * @param module
     * @param type
     * @return
     */
    public List<Map> getEnumList(String module,String type) {
        String sql = "SELECT illustrate as label , value  from spr_enum where module='" + module + "' and type='" + type + "'";
        return this.getMetaDbTemplate(sql).getResult();
    }

    public Map getEndPelyd(int articleid){
        String sql = "select b.name,a.create_at from REPLYINFO a join spr_user b on a.orgid=b.orgid where a.articleid="+articleid;
        MetaDbTemplate metaDbTemplate = this.getMetaDbTemplate(sql);
        metaDbTemplate.setOrderby(" create_at desc");
        List<Map> list= metaDbTemplate.getResult();
        return list!=null &&list.size()>0?list.get(0):null;
    }
    public void delete(int id){
        String sql = "delete from invitation where objid="+id;
        this.getMetaDbTemplate(sql).updateExecute();
    }
    public void save(Invitation invitation){
        this.getMetaDbTemplateBatis().save(invitation);
    }
    public void save(Replyinfo replyinfo){
        this.getMetaDbTemplateBatis().save(replyinfo);
    }
}
