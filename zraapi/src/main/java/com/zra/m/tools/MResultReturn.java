package com.zra.m.tools;

import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.ResultException;
import io.swagger.annotations.ApiModelProperty;

import javax.ws.rs.core.Response;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/9.
 */
public final class MResultReturn {
    @ApiModelProperty(value = "response code(成功:200)")
    private int resCode;
    @ApiModelProperty(value = "response message(成功:OK)")
    private String resMsg;
//    @ApiModelProperty(value = "请求状态:值为\"success\"或\"fail\"")
    private String status;
    @ApiModelProperty(value = "结果数据")
    private Object data;

    private MResultReturn(int resCode, String resMsg, String status, Object data) {
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.status = status;
        this.data = data;
    }

    public static MResultReturn toSuccess(Object data) {
        return new MResultReturn(Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase(), "success", data);
    }

    public static MResultReturn toSuccess() {
        return new MResultReturn(Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase(), "success", null);
    }

    public static MResultReturn toFail(ErrorEnum errorEnum, Object data) {
        if (errorEnum == null) {
            throw new RuntimeException("返回的错误信息不能为空！！！");
        }
        return new MResultReturn(errorEnum.getCode(), errorEnum.getMessage(), "fail", data);
    }

    public static MResultReturn toFail(Exception e) {
        if (e instanceof ResultException) {
            ResultException re = (ResultException) e;
            return new MResultReturn(re.getCode(), re.getMessage(), "fail", null);
        }
        return new MResultReturn(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()/*Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase()*/, "fail", null);
    }

    public static MResultReturn toFail() {
        return new MResultReturn(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), "fail", null);
    }

    public int getResCode() {
        return resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public String getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }
}
