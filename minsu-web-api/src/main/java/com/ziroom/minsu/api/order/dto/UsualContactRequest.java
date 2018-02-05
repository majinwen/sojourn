package com.ziroom.minsu.api.order.dto;

import java.util.List;

import com.ziroom.minsu.api.common.dto.BaseParamDto;
import com.ziroom.minsu.api.order.entity.UsualContactVo;

/**
 * <p>入住人的请求信息</p>
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
public class UsualContactRequest extends BaseParamDto{

	/**
	 * 常用联系人列表
	 */
	List<UsualContactVo> listContactVos;

	public List<UsualContactVo> getListContactVos() {
		return listContactVos;
	}

	public void setListContactVos(List<UsualContactVo> listContactVos) {
		this.listContactVos = listContactVos;
	}
	
}
