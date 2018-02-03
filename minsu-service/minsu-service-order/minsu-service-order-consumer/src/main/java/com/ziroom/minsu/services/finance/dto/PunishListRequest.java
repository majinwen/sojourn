package com.ziroom.minsu.services.finance.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

public class PunishListRequest extends PageRequest {

	/** 序列化ID */
	private static final long serialVersionUID = 7958484608670729232L;

	/** 扣款人uid */
	String punishUid;

	public String getPunishUid() {
		return punishUid;
	}

	public void setPunishUid(String punishUid) {
		this.punishUid = punishUid;
	}

}
