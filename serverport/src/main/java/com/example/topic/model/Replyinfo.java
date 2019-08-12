package com.example.topic.model;

import java.util.Date;

/**
 * 回复帖子
 * Created by Administrator on 2019\5\12 0012.
 */
public class Replyinfo {

    private static String  TABLENAME ="REPLYINFO";

    private int objid;
    private int orgid;
    private String content;
    private Date create_at;
    private Date update_at;
    private int articleid;

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOrgid() {
        return orgid;
    }

    public void setOrgid(int orgid) {
        this.orgid = orgid;
    }

    public int getObjid() {
        return objid;
    }

    public void setObjid(int objid) {
        this.objid = objid;
    }

    public String getTABLENAME() {
        return TABLENAME;
    }
}
