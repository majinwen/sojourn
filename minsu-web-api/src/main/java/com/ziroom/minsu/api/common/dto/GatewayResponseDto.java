package com.ziroom.minsu.api.common.dto;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR> 修改记录
 * <BR>-----------------------------------------------
 * <BR> 修改日期 修改人 修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @since 1.0
 */
public class GatewayResponseDto {

    public final static String SUCCESS = "200";
    /**
     * 业务消息
     */
    private String message;

    /**
     * 业务数据
     */
    private Object data;

    /**
     * 业务状态码
     */
    private String code;

    /**
     * 网关回传的状态,标识网关层是否成功调用
     */
    private String status;

    /**
     * 网关生成的requestId（后台不需要关注这个值）
     */
    private String requestId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public static GatewayResponseDto response2GatewayDto(ResponseDto responseDto) {
        GatewayResponseDto grd = new GatewayResponseDto();
        grd.setMessage(responseDto.getMessage());
        grd.setData(responseDto.getData());
        if (responseDto.getStatus().equals(ResponseDto.SUCCESS)) {
            grd.setCode(SUCCESS);
        } else {
            grd.setCode(responseDto.getStatus());
        }
        return grd;
    }
}
