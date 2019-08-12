package com.example.topic.dao;

import com.example.topic.model.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2019\4\24 0024.
 */
@Mapper
public interface DepartmentDao {
    @Results({
            @Result(property = "department_id", column = "department_id"), //2
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "create_at", column = "create_at"),
            @Result(property = "update_at", column = "update_at")
    })
    @Select("SELECT * FROM department order by create_at desc")
    List<Department> get();
    @Select("SELECT * FROM department where department_id = #{department_id}")
    List<Department> getFindById(int department_id);

    @Select("SELECT * FROM department where name = #{name}")
    List<Department> getDepartmentByName(String name);

    @Insert("INSERT INTO department( name,description,create_at,update_at) VALUES (#{name},#{description},now(),now())") //3
    void insert(Department department);

    @Select("delete FROM department WHERE department_id = #{department_id}") //3
    void delete(int department_id);

    @Select("update  department set name=#{name},description#{description},update_at=#{now()} WHERE department_id = #{department_id}") //3
    void update(Department department);
}
