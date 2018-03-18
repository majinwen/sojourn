package com.ziroom.zrp.service.trading.entity;

/*
 * <P></P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 18日 14:33
 * @Version 1.0
 * @Since 1.0
 */

import java.io.Serializable;

public class EspResponseVo implements Serializable{

    private static final long serialVersionUID = 7978797003748414511L;

    //是否成功，true:成功，false:失败
    private Boolean isOK;

    //返回码，0：成功，1：失败
    private Integer code;

    //返回消息
    private String message;

    //用户uid
    private String userId;

    //印章表示id
    private String sealId;

    private Boolean isResult;

    public Boolean getOK() {
        return isOK;
    }

    public void setOK(Boolean OK) {
        isOK = OK;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSealId() {
        return sealId;
    }

    public void setSealId(String sealId) {
        this.sealId = sealId;
    }

    public Boolean getResult() {
        return isResult;
    }

    public void setResult(Boolean result) {
        isResult = result;
    }
}
