package com.example.topic.model;

import java.util.Date;

/**
 * Created by Administrator on 2019\5\13 0013.
 */
public class User {
    private static String TABLENAME="SPR_USER";

    private int objid;
    private Date update_at;
    private Date  create_at;
    private String password;
    private String username;
    private int  orgid;
    private String name;
    private String gender;
    private int authority;
    public static String getTABLENAME() {
        return TABLENAME;
    }

    public int getObjid() {
        return this.objid;
    }

    public void setObjid(int objid) {
        this.objid = objid;
    }

    public Date getUpdate_at() {
        return this.update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }

    public Date getCreate_at() {
        return this.create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getOrgid() {
        return this.orgid;
    }

    public void setOrgid(int orgid) {
        this.orgid = orgid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAuthority() {
        return this.authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }
}
