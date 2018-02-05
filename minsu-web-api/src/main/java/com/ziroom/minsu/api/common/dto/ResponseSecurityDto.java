/**
 * @FileName: ResponseSecurityDto.java
 * @Package: com.ziroom.sms.api.cleaning.common.dto
 * @author sence
 * @created 9/21/2015 5:13 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.minsu.api.common.dto;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;

/**
 * 
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class ResponseSecurityDto {

    /**
     * 是否加密
     */
    private boolean isSercurity;

    /**
     * 返回数据
     */
    private ResponseDto responseDto;


    public boolean isSercurity() {
        return isSercurity;
    }

    public void setIsSercurity(boolean isSercurity) {
        this.isSercurity = isSercurity;
    }

    public ResponseDto getResponseDto() {
        return responseDto;
    }

    public void setResponseDto(ResponseDto responseDto) {
        this.responseDto = responseDto;
    }



    /**
     * @param obj
     * @return
     */
    public static ResponseSecurityDto responseEncryptOK(Object obj) {
        ResponseSecurityDto responseSecurityDto = new ResponseSecurityDto();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(ResponseDto.SUCCESS);
        responseDto.setData(obj);
        responseSecurityDto.setIsSercurity(true);
        responseSecurityDto.setResponseDto(responseDto);
        return responseSecurityDto;
    }

    /**
     * @param obj
     * @return
     */
    public static ResponseSecurityDto responseUnEncryptOK(Object obj) {
        ResponseSecurityDto responseSecurityDto = new ResponseSecurityDto();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(ResponseDto.SUCCESS);
        responseDto.setData(obj);
        responseSecurityDto.setIsSercurity(false);
        responseSecurityDto.setResponseDto(responseDto);
        return responseSecurityDto;
    }

    /**
     * @param str
     * @return
     */
    public static ResponseSecurityDto responseUnEncryptFail(String str) {
        ResponseSecurityDto responseSecurityDto = new ResponseSecurityDto();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(ResponseDto.FAILED);
        responseDto.setMessage(str);
        responseSecurityDto.setIsSercurity(false);
        responseSecurityDto.setResponseDto(responseDto);
        return responseSecurityDto;
    }

    /**
     * @param str
     * @return
     */
    public static ResponseSecurityDto responseEncryptFail(String str,String code) {
        ResponseSecurityDto responseSecurityDto = new ResponseSecurityDto();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(code);
        responseDto.setMessage(str);
        responseSecurityDto.setIsSercurity(true);
        responseSecurityDto.setResponseDto(responseDto);
        return responseSecurityDto;
    }

    /**
     * @param str
     * @return
     */
    public static ResponseSecurityDto responseEncryptFail(String str) {
        ResponseSecurityDto responseSecurityDto = new ResponseSecurityDto();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(ResponseDto.FAILED);
        responseDto.setMessage(str);
        responseSecurityDto.setIsSercurity(true);
        responseSecurityDto.setResponseDto(responseDto);
        return responseSecurityDto;
    }
    
    /**
     * 
     * 直接返回dto的数据
     *
     * @author yd
     * @created 2016年4月30日 下午8:36:05
     *
     * @param dto
     * @return
     */
    public static ResponseSecurityDto responseEncrypt(DataTransferObject dto){
    	ResponseSecurityDto responseSecurityDto = new ResponseSecurityDto();
        ResponseDto responseDto = new ResponseDto();

        responseDto.setStatus(ResponseDto.FAILED);
    	if(Check.NuNObj(dto)) {
    		  responseDto.setMessage("返回对象不存在");
    	}else{
    		if(dto.getCode() == 0){
            	responseDto.setStatus(ResponseDto.SUCCESS);
            	responseDto.setData(dto.getData());
            	responseDto.setMessage(dto.getMsg());
            }
            if(dto.getCode() == 1){
            	  responseDto.setMessage(dto.getMsg());
            }
    	}
        responseSecurityDto.setIsSercurity(true);
        responseSecurityDto.setResponseDto(responseDto);
        return responseSecurityDto;
    }

    
    /**
     * 
     * 直接返回dto的数据
     *
     * @author yd
     * @created 2016年4月30日 下午8:36:05
     *
     * @param dto
     * @return
     */
    public static ResponseSecurityDto responseUnEncrypt(DataTransferObject dto){
    	ResponseSecurityDto responseSecurityDto = new ResponseSecurityDto();
        ResponseDto responseDto = new ResponseDto();

        responseDto.setStatus(ResponseDto.FAILED);
    	if(Check.NuNObj(dto)) {
    		  responseDto.setMessage("返回对象不存在");
    	}else{
    		if(dto.getCode() == 0){
            	responseDto.setStatus(ResponseDto.SUCCESS);
            	responseDto.setData(dto.getData());
            }
            if(dto.getCode() == 1){
            	  responseDto.setMessage(dto.getMsg());
            }
    	}
        responseSecurityDto.setIsSercurity(false);
        responseSecurityDto.setResponseDto(responseDto);
        return responseSecurityDto;
    }
}
