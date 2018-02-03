package com.ziroom.minsu.services.house.photog.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>摄影师请求dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class PhotogRequestDto extends PageRequest{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -9182779525041579880L;

	/**
     * 真实姓名
     */
    private String realName;

    /**
     * 摄影师联系手机号
     */
    private String mobile;

    /**
     * 所在城市code
     */
    private String cityCode;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}
