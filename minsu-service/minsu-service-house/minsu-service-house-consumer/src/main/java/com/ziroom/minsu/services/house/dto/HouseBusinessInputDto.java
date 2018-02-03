/**
 * @FileName: HouseBusinessInputDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2016年7月7日 下午9:54:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.entity.house.HouseBusinessMsgEntity;
import com.ziroom.minsu.entity.house.HouseBusinessMsgExtEntity;
import com.ziroom.minsu.entity.house.HouseBusinessSourceEntity;

/**
 * <p>商机录入dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class HouseBusinessInputDto {
	
	/**
	 * 房源商机主表
	 */
	private HouseBusinessMsgEntity businessMsg=new HouseBusinessMsgEntity();
	
	/**
	 * 房源商机扩展表
	 */
	private HouseBusinessMsgExtEntity businessExt=new HouseBusinessMsgExtEntity();
	
	/**
	 * 房源商机来源表
	 */
	private HouseBusinessSourceEntity businessSource =new HouseBusinessSourceEntity();
	/**
	 *  发布时间
	 */
	private String releaseDate;

	/**
	 * 上架时间
	 */
	private String putawayDate;
	/**
	 * 预约线下验房时间
	 */
	private String makeCheckDate;
	/**
	 *实际线下验房时间
	 */
	private String realCheckDate;
	/**
	 * 预约拍照时间
	 */
	private String makePhotoDate;
	/**
	 * 实际拍照时间
	 */
	private String realPhotoDate;
	/**
	 * 登记时间
	 */
	private String registerDate;
	
	/**
	 * 旧地推管家
	 */
	private String oldDtGuardCode;

	/**
	 * @return the oldDtGuardCode
	 */
	public String getOldDtGuardCode() {
		return oldDtGuardCode;
	}

	/**
	 * @param oldDtGuardCode the oldDtGuardCode to set
	 */
	public void setOldDtGuardCode(String oldDtGuardCode) {
		this.oldDtGuardCode = oldDtGuardCode;
	}

	/**
	 * @return the businessMsg
	 */
	public HouseBusinessMsgEntity getBusinessMsg() {
		return businessMsg;
	}

	/**
	 * @param businessMsg the businessMsg to set
	 */
	public void setBusinessMsg(HouseBusinessMsgEntity businessMsg) {
		this.businessMsg = businessMsg;
	}

	/**
	 * @return the businessExt
	 */
	public HouseBusinessMsgExtEntity getBusinessExt() {
		return businessExt;
	}

	/**
	 * @param businessExt the businessExt to set
	 */
	public void setBusinessExt(HouseBusinessMsgExtEntity businessExt) {
		this.businessExt = businessExt;
	}

	/**
	 * @return the businessSource
	 */
	public HouseBusinessSourceEntity getBusinessSource() {
		return businessSource;
	}

	/**
	 * @param businessSource the businessSource to set
	 */
	public void setBusinessSource(HouseBusinessSourceEntity businessSource) {
		this.businessSource = businessSource;
	}
	
	/**
	 * @return the releaseDate
	 */
	public String getReleaseDate() {
		return releaseDate;
	}

	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * @return the putawayDate
	 */
	public String getPutawayDate() {
		return putawayDate;
	}

	/**
	 * @param putawayDate the putawayDate to set
	 */
	public void setPutawayDate(String putawayDate) {
		this.putawayDate = putawayDate;
	}

	/**
	 * @return the makeCheckDate
	 */
	public String getMakeCheckDate() {
		return makeCheckDate;
	}

	/**
	 * @param makeCheckDate the makeCheckDate to set
	 */
	public void setMakeCheckDate(String makeCheckDate) {
		this.makeCheckDate = makeCheckDate;
	}

	/**
	 * @return the realCheckDate
	 */
	public String getRealCheckDate() {
		return realCheckDate;
	}

	/**
	 * @param realCheckDate the realCheckDate to set
	 */
	public void setRealCheckDate(String realCheckDate) {
		this.realCheckDate = realCheckDate;
	}

	/**
	 * @return the makePhotoDate
	 */
	public String getMakePhotoDate() {
		return makePhotoDate;
	}

	/**
	 * @param makePhotoDate the makePhotoDate to set
	 */
	public void setMakePhotoDate(String makePhotoDate) {
		this.makePhotoDate = makePhotoDate;
	}

	/**
	 * @return the realPhotoDate
	 */
	public String getRealPhotoDate() {
		return realPhotoDate;
	}

	/**
	 * @param realPhotoDate the realPhotoDate to set
	 */
	public void setRealPhotoDate(String realPhotoDate) {
		this.realPhotoDate = realPhotoDate;
	}

	/**
	 * @return the registerDate
	 */
	public String getRegisterDate() {
		return registerDate;
	}

	/**
	 * @param registerDate the registerDate to set
	 */
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
}
