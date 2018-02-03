package com.ziroom.minsu.services.message.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>
 * 房东反馈信息查询请求参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author wangwentao on 2017年4月24日
 * @since 1.0
 * @version 1.0
 */
public class LandlordComplainRequest extends PageRequest {
	private static final long serialVersionUID = -4416001436655033060L;
	private String complainUid;
	private String complainUsername;
	private String complainMphone;
	private String content;
	private String createTimeStart;
	private String createTimeEnd;

	public String getComplainUid() {
		return complainUid;
	}

	public void setComplainUid(String complainUid) {
		this.complainUid = complainUid;
	}

	public String getComplainUsername() {
		return complainUsername;
	}

	public void setComplainUsername(String complainUsername) {
		this.complainUsername = complainUsername;
	}

	public String getComplainMphone() {
		return complainMphone;
	}

	public void setComplainMphone(String complainMphone) {
		this.complainMphone = complainMphone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
}
