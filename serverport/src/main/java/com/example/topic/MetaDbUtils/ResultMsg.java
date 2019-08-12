package com.example.topic.MetaDbUtils;

import java.util.Map;

/**
 * Created by Administrator on 2019\5\12 0012.
 */
public class ResultMsg {
    private String message;
    private Object data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String MESSAGE) {
        this.message = MESSAGE;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
