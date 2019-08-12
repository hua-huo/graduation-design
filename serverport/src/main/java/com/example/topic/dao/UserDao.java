package com.example.topic.dao;

import com.example.topic.model.Invitation;
import com.example.topic.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2019\5\13 0013.
 */
@Mapper
public interface UserDao {
    @Results({
            @Result(property = "objid", column = "objid"), //2
            @Result(property = "update_at", column = "update_at"),
            @Result(property = "create_at", column = "create_at"),
            @Result(property = "password", column = "password"),
            @Result(property = "username", column = "username"),
            @Result(property = "orgid", column = "orgid"),
            @Result(property = "name", column = "name"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "authority", column = "authority")
    })
    @Select("SELECT * FROM spr_user where username=#{username}")
    List<User> get(String username);
}
