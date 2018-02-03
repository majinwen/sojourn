package com.ziroom.minsu.services.house.photog.dto;

import com.ziroom.minsu.entity.photographer.PhotographerBookLogEntity;



/**
 * <p>预约摄影日志扩展</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
public class PhotogLogDto extends PhotographerBookLogEntity {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 7329443031951695410L;

	/** 创建日期*/
	private String createDateStr;

	/** 创建人*/
	private String createName;

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
}
