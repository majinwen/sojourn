/**
 * @FileName: SysMsgParamDto.java
 * @Package com.ziroom.minsu.api.common.dto
 * 
 * @author jixd
 * @created 2016年4月21日 上午11:53:12
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.message.dto;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>系统消息参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class SysMsgParamDto extends BaseParamDto {
	/**
	 * 删除消息fid
	 */
	private String sysMsgFid;

	public String getSysMsgFid() {
		return sysMsgFid;
	}

	public void setSysMsgFid(String sysMsgFid) {
		this.sysMsgFid = sysMsgFid;
	}
	
}
