package com.ziroom.minsu.services.order.utils;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.common.constant.CodeConst;
import com.ziroom.minsu.valenum.common.ErrorCodeEnum;

public class PayUtil {

	
	/**
	 * 获取支付类型
	 * @author lishaochuan
	 * @create 2016年5月1日
	 * @param payType
	 * @param sourceType
	 * @return
	 */
	public static String getClientPayType(String payType, String sourceType){
		if(!Check.NuNStr(sourceType)){
			payType += "_"+sourceType;
		}
		payType += "_pay";
		return payType;
	}


    /**
     * 封装失败参数
     *
     *
     * @author liyingjie
     * @created 2016年4月26日
     *
     * @param
     * @return
     */
    public static void transFailDto(DataTransferObject dto){
        dto.putValue("error_code", ErrorCodeEnum.fail.getCode());
        dto.putValue("error_message", ErrorCodeEnum.fail.getName());
        dto.putValue("status", CodeConst.fail_status);
    }

    /**
     * 封装失败参数
     *
     *
     * @author liyingjie
     * @created 2016年4月26日
     *
     * @param
     * @return
     */
    public static void transSuccessDto(DataTransferObject dto){
        dto.putValue("error_code", ErrorCodeEnum.success.getCode());
        dto.putValue("error_message", ErrorCodeEnum.success.getName());
        dto.putValue("status", CodeConst.success_status);
    }
    
    /**
     * 封装成功参数
     *
     * @author liyingjie
     * @created 2016年7月12日
     *
     * @param
     * @return
     */
    public static void parseSuccessDto(DataTransferObject dto){
    	dto.setErrCode(ErrorCodeEnum.success.getCode());
    	dto.setMsg(ErrorCodeEnum.success.getName());
    }
    
    /**
     * 封装失败参数
     *
     * @author liyingjie
     * @created 2016年5月11日
     * @param
     * @return
     */
    public static void parseFailDto(DataTransferObject dto,int errorCode){
    	dto.setErrCode(ErrorCodeEnum.fail.getCode());
    	dto.setMsg(String.valueOf(errorCode));
    }
    
}
