package com.ziroom.zrp.service.houses.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>查询签约邀请列表参数dto</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author tianxf9
 * @Date Created in 2017年09月26日 17:01
 * @version 1.0
 * @since 1.0
 */
public class QueryInvitereCordParamDto extends BaseEntity {
	
	private String roomId;
	
	private int page;
	
	private int limit;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
