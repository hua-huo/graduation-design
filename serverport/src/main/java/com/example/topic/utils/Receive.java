package com.example.topic.utils;

import com.example.topic.model.User;

/**
 * Created by Administrator on 2019\5\14 0014.
 */
public class Receive {
    private User user;
    private Object data;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
