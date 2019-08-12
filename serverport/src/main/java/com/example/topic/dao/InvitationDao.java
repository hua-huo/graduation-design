package com.example.topic.dao;

import com.example.topic.model.Invitation;
import com.example.topic.service.InvitationService;
import com.example.topic.utils.OrderProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019\5\7 0007.
 */
@Mapper
public interface InvitationDao {
    @Results({
            @Result(property = "objid", column = "objid"), //2
            @Result(property = "title", column = "title"),
            @Result(property = "type", column = "type"),
            @Result(property = "edition", column = "edition"),
            @Result(property = "content", column = "content"),
            @Result(property = "orgid", column = "orgid"),
            @Result(property = "create_at", column = "create_at"),
            @Result(property = "update_at", column = "update_at")
    })
    @Select("SELECT * FROM invitation order by create_at desc")
    List<Invitation> get();
    @SelectProvider(type = OrderProvider.class, method = "queryOrderByParam")
    List<Map> queryOrderByParam(String sql);
}
