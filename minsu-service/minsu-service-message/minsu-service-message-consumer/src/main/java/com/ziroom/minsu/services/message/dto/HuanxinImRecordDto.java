/**
 * @FileName: HuanxinImRecordDto.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author loushuai
 * @created 2017年9月5日 上午11:42:13
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class HuanxinImRecordDto extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -825958383309056974L;
	
	private String fromOnOrOffLine;

	private HuanxinImRecordEntity huanxinImRecord;
	public String getFromOnOrOffLine() {
		return fromOnOrOffLine;
	}

	public void setFromOnOrOffLine(String fromOnOrOffLine) {
		this.fromOnOrOffLine = fromOnOrOffLine;
	}

	public HuanxinImRecordEntity getHuanxinImRecord() {
		return huanxinImRecord;
	}

	public void setHuanxinImRecord(HuanxinImRecordEntity huanxinImRecord) {
		this.huanxinImRecord = huanxinImRecord;
	}
	
}
