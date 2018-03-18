package com.ziroom.minsu.report.basedata.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>城市 请求参数</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */
public class CityRequest extends PageRequest {
    private static final long serialVersionUID = -7610942746679148374L;
	private String cityCode;
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
   
}
