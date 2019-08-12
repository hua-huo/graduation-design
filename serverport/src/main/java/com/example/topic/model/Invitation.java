package com.example.topic.model;

import java.util.Date;

/**
 * 必须拥有TABLENAME属性  并且所有属性必须实现get方法
 * Created by Administrator on 2019\5\7 0007.
 */
public class Invitation {

    private static String TABLENAME="INVITATION";

    private int objid;
    private String title;
    private String type;
    private int edition;
    private String content;
    private int orgid;
    private Date create_at;
    private Date update_at;
    private String createDate;
    private String updateDate;
    private int readnum;
    private int replynum;
    public Invitation(){}
    public Invitation(int objid, String title, String type, int edition, String content, int orgid, Date create_at, Date update_at) {
        this.objid = objid;
        this.title = title;
        this.type = type;
        this.edition = edition;
        this.content = content;
        this.orgid = orgid;
        this.create_at = create_at;
        this.update_at = update_at;
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

    public int getOrgid() {
        return orgid;
    }

    public void setOrgid(int orgid) {
        this.orgid = orgid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getObjid() {
        return objid;
    }

    public void setObjid(int objid) {
        this.objid = objid;
    }
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getTABLENAME() {
        return TABLENAME;
    }

    public int getReadnum() {
        return readnum;
    }

    public void setReadnum(int readnum) {
        this.readnum = readnum;
    }

    public int getReplynum() {
        return replynum;
    }

    public void setReplynum(int replynum) {
        this.replynum = replynum;
    }
}
