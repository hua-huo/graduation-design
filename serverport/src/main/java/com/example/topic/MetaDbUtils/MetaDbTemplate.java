package com.example.topic.MetaDbUtils;

import com.example.topic.utils.NumberTool;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019\5\8 0008.
 */
@Service
public class MetaDbTemplate {
    private Integer pagesize;
    private Integer page;
    private Integer pagenum;
    private Integer count;
    private String sql;
    private String orderby;
    private IMetaDbQuery metaDbInquire;

    public MetaDbTemplate() {
    }
    public MetaDbTemplate init(IMetaDbQuery metaDbInquire ,String sql){
        this.metaDbInquire = metaDbInquire;
        this.sql = sql;
        return this;
    }
    public void updateExecute(){
        metaDbInquire.queryOrderByParam(sql);
    }
    public List<Map> getResult() {
        StringBuffer resultSql = new StringBuffer("select * from (")
                .append(sql)
                .append(")tablename  ");
        if (this.orderby != null) {
            resultSql.append(" order by ").append(this.orderby);
        }
        if (page != null && pagesize != null) {
            resultSql.append(" limit ").append((page - 1) * pagesize).append(",").append(pagesize);
        }
        return metaDbInquire.queryOrderByParam(resultSql.toString());
    }

    public List<Map> qureyTransmitSql(String sql) {
        StringBuffer resultSql = new StringBuffer("select * from (")
                .append(sql).append(") ");
        if (page != null && pagesize != null) {
            resultSql.append(" limit ").append((page - 1) * pagesize).append(",").append(pagesize);
        }
        return metaDbInquire.queryOrderByParam(resultSql.toString());
    }

    public int getCount() {
        if (this.count != null) {
            return this.count;
        }
        StringBuffer countSql = new StringBuffer("select count(1) count from (").append(sql).append(")");
        List<Map> list = metaDbInquire.queryOrderByParam(countSql.toString());
        int count = 0;
        if (list != null && list.size() > 0) {
            Map map = list.get(0);
            count = NumberTool.transitionInteger(map, "count", 0);
        }
        this.count = count;
        this.pagenum = count / pagesize;
        return this.count;
    }

    public int getPagenum() {
        if (this.pagenum != null) {
            return this.pagenum;
        }
        StringBuffer countSql = new StringBuffer("select count(1) count from (").append(sql).append(")tablename");
        List<Map> list = metaDbInquire.queryOrderByParam(countSql.toString());
        int count = 0;
        if (list != null && list.size() > 0) {
            Map map = list.get(0);
            count = NumberTool.transitionInteger(map, "count", 0);
        }
        this.count = count;
        this.pagenum = count / pagesize + 1;
        return this.pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

}
