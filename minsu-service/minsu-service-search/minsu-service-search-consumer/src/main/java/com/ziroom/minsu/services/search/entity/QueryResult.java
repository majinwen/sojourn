package com.ziroom.minsu.services.search.entity;

import com.ziroom.minsu.services.search.namevalue.LongsNameValue;

import java.io.Serializable;

/**
 * <p>搜索返回的结果</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/16.
 * @version 1.0
 * @since 1.0
 */
public class QueryResult implements Serializable{


    private static final long serialVersionUID = 3584564565456456L;

    public final static int OK = 0;

    private int code;

    private String msg;

    private long elapse;

    private long total;

    private Object value;

    private LongsNameValue[] facets;

    public QueryResult() {
        this(OK, "OK");
    }

    public QueryResult(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getElapse() {
        return elapse;
    }

    public void setElapse(long elapse) {
        this.elapse = elapse;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public LongsNameValue[] getFacets() {
        return facets;
    }

    public void setFacets(LongsNameValue[] facets) {
        this.facets = facets;
    }

    @Override
    public String toString() {
        return "code:" + code + " elapse:" + elapse + " total:" + total + "\n" + "value:" + value;
    }

}
