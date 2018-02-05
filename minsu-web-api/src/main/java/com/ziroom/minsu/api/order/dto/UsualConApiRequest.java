package com.ziroom.minsu.api.order.dto;

import java.util.List;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>常用联系人查询接口 请求参数</p>
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
public class UsualConApiRequest extends BaseParamDto{

	/**
	 * fid 集合
	 */
	private List<String> listFid;
	
	/**
	 * 预订人uid
	 */
	private String userUid;

	public List<String> getListFid() {
		return listFid;
	}

	public void setListFid(List<String> listFid) {
		this.listFid = listFid;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}
	
	
}
