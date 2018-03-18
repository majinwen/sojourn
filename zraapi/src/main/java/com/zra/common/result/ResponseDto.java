package com.zra.common.result;


import com.asura.framework.base.entity.DataTransferObject;
import com.zra.common.enums.ErrorEnum;

/**
 * <p>网关返回记录</p>
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
public class ResponseDto {

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


    public ResponseDto(String message, Object data, String code) {
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public ResponseDto() {
    }

    public static ResponseDto responseOK() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setCode(String.valueOf(ErrorEnum.MSG_SUCCESS.getCode()));
        return responseDto;
    }

    public static ResponseDto responseOK(Object obj) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setCode(String.valueOf(ErrorEnum.MSG_SUCCESS.getCode()));
        responseDto.setData(obj);
        return responseDto;
    }

    public static ResponseDto responseDtoFail(String msg){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setCode(String.valueOf(ErrorEnum.MSG_FAIL.getCode()));
        responseDto.setMessage(msg);
        return responseDto;
    }

    public static ResponseDto responseDtoErrorEnum(ErrorEnum errorEnum){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setCode(String.valueOf(errorEnum.getCode()));
        responseDto.setMessage(errorEnum.getMessage());
        return responseDto;
    }

    public static ResponseDto responseDtoErrorEnum(ErrorEnum errorEnum,Object data){
        ResponseDto responseDto = responseDtoErrorEnum(errorEnum);
        responseDto.setData(data);
        return responseDto;
    }

    public static ResponseDto responseDtoForData(DataTransferObject data){
        ResponseDto responseDto = new ResponseDto();
        if (data.getCode() == DataTransferObject.ERROR){
            responseDto.setCode(String.valueOf(ErrorEnum.MSG_FAIL.getCode()));
        }else{
            responseDto.setCode(String.valueOf(ErrorEnum.MSG_SUCCESS.getCode()));
        }
        responseDto.setMessage(data.getMsg());
        responseDto.setData(data.getData());
        return responseDto;
    }


}
