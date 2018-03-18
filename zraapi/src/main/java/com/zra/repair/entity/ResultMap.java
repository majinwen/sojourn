package com.zra.repair.entity;

import com.zra.common.error.ResultException;
import io.swagger.annotations.ApiModelProperty;

import javax.ws.rs.core.Response;

/**
 * <p>返回结果集</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author fengbo.yue
 * @Date Created in 2017年10月09日 11:36
 * @since 1.0
 * @version 1.0
 */
public class ResultMap {
    
    @ApiModelProperty(value = "response code(成功:200)")
    private int code;

    @ApiModelProperty(value = "response message(成功:OK)")
    private String message;

    @ApiModelProperty(value = "请求状态:值为\"success\"或\"fail\"")
    private String status;

    @ApiModelProperty(value = "结果数据")
    private Object data;

    public ResultMap() {
    }

    private ResultMap(int code, String message, String status, Object data) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public static ResultMap build() {
        return new ResultMap();
    }

    public static ResultMap success(Object data) {
        return new ResultMap(Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase(), "success", data);
    }

    public static ResultMap success() {
        return new ResultMap(Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase(), "success", null);
    }

    public static ResultMap fail(Exception e) {
        if (e instanceof ResultException) {
            ResultException re = (ResultException) e;
            return new ResultMap(re.getCode(), re.getMessage(), "fail", null);
        }
        return new ResultMap(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()/*Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase()*/, "fail", null);
    }

    public static ResultMap fail() {
        return new ResultMap(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), "fail", null);
    }

    public int getCode() {
        return code;
    }

    public ResultMap setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultMap setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ResultMap setStatus(String status) {
        this.status = status;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResultMap setData(Object data) {
        this.data = data;
        return this;
    }
}
