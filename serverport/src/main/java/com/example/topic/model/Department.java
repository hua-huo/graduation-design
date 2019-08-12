package com.example.topic.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by Administrator on 2019\4\24 0024.
 */
@Entity
public class Department {
    /**
     * 部门ID
     */
    private Integer department_id;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date create_at;
    /**
     * 更新时间
     */
    private Date update_at;

    private boolean checkbox;

    public Department(){}
    public Department(Integer department_id,String name,String description,Date create_at,Date update_at){
        this.department_id=department_id;
        this.name=name;
        this.description=description;
        this.create_at=create_at;
        this.update_at=update_at;
    }
    /**
     * 获取部门ID
     */
    public Integer getDepartment_id() {
        return department_id;
    }
    /**
     * 设置部门ID
     */
    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }
    /**
     * 获取创建时间
     */
    public Date getCreate_at() {
        return create_at;
    }
    /**
     * 设置创建时间
     */
    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }
    /**
     * 获取更新时间
     */
    public Date getUpdate_at() {
        return update_at;
    }
    /**
     * 设置更新时间
     */
    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }
    /**
     * 获取部门说明
     */
    public String getDescription() {
        return description;
    }
    /**
     * 设置部门说明
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * 获取部门名称
     */
    public String getName() {
        return name;
    }
    /**
     * 设置部门名称
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setCheckbox(boolean checkbox){
        this.checkbox=checkbox;
    }
    public boolean getCheckbox(){
        return this.checkbox;
    }
}
