package com.ziroom.minsu.api.search.common.log.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.api.search.common.header.Header;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;

/**
 * <p>用户的行为的记录</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi
 * @version 1.0
 * @since 1.0
 */
public class TraceInfoVo extends BaseEntity{

    private static final long serialVersionUID = 18310298308703L;

    /** 头信息  */
    private Header header;


    /** 请求参数  */
    private HouseInfoRequest request;

    /** 用户uid  */
    private String uid;


    /** 返回code  */
    private  Integer code;

    /** 返回信息  */
    private  String msg;

    /** 返回总条数  */
    private  Integer total = 0;


    /** 时间  */
    private  Long cost;


    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public HouseInfoRequest getRequest() {
        return request;
    }

    public void setRequest(HouseInfoRequest request) {
        this.request = request;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }
}
