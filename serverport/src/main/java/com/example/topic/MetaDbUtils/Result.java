package com.example.topic.MetaDbUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019\5\8 0008.
 */
public class Result {
    private String message;
    private int page;//当前页数
    private int pageSize; //每页大小
    private int pageNum;//总页数
    private int count;//总条数
    private List<Map> data;//返回数据
    private Map parmap; //查询条件
    public Result(){}
    public Result(int page, int pageSize, int pageNum, List<Map> data) {
        this.page = page;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.data = data;
    }
    public Result getResult(MetaDbTemplate metaDbTemplate,ITableBatch tableBatch){
        try {


        metaDbTemplate.setPage(page);
        metaDbTemplate.setPagesize(pageSize);
        List<Map> list = metaDbTemplate.getResult();
        if(tableBatch != null){
            this.data = tableBatch.tableBatch(list,parmap);
        }else{
            this.data = list;
        }
        pageNum =metaDbTemplate.getPagenum();
        count=metaDbTemplate.getCount();
        }catch (Exception e){
            e.printStackTrace();
            message ="error";
            return this;
        }
        return this;
    }
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Map> getData() {
        return data;
    }

    public void setData(List<Map> data) {
        this.data = data;
    }

    public Map getParmap() {
        return parmap;
    }

    public void setParmap(Map parmap) {
        this.parmap = parmap;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
