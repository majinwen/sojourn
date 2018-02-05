package com.ziroom.minsu.api.customer.dto;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>验证手机号请求参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月16日
 * @since 1.0
 * @version 1.0
 */
public class MobileDto extends BaseParamDto {

	// 手机号
	private String mobile;

	// 验证码
	private String authCode;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

}
