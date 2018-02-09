/**
 * @FileName: DtoToResponseDto.java
 * @Package com.ziroom.minsu.web.im.chat.controller.utils
 * 
 * @author yd
 * @created 2016年9月22日 下午3:43:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.web.im.common.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.web.im.common.dto.ResponseDto;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class DtoToResponseDto {

	  
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
    public static ResponseDto responseEncrypt(DataTransferObject dto){
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
        return responseDto;
    }
    
	/**
	 * 
	 * 重新登录公用返回
	 *
	 * @author yd
	 * @created 2016年5月20日 上午2:06:32
	 *
	 * @param response
	 * @param msg
	 * @param responseDto
	 * @throws BusinessException
	 * @throws IOException
	 */
	public  static void  getResponseMsg(HttpServletResponse response,ResponseDto responseDto ) throws BusinessException, IOException{

		if(Check.NuNObj(responseDto)) responseDto = new ResponseDto();
		response.setContentType("application/json; charset=utf-8");
		response.getOutputStream().write(JsonEntityTransform.Object2Json(responseDto).getBytes());
		response.flushBuffer();
	}
}
