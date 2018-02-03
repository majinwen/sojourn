package com.ziroom.minsu.services.message.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * 系统消息基本查询参数
 * @author jixd on 2016年4月16日
 * @version 1.0
 * @since 1.0
 */
public class SysMsgRequest extends PageRequest {
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 7246454403890788952L;
	/*通知对象uid*/
	private String msgTargetUid;
	/*是否已读 0：未读 1：已读*/
	private Integer isRead;
	/*是否删除 0：未删除 1：已删除*/
	private Integer isDel;

	public String getMsgTargetUid() {
		return msgTargetUid;
	}

	public void setMsgTargetUid(String msgTargetUid) {
		this.msgTargetUid = msgTargetUid;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

}
