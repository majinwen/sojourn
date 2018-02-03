package com.ziroom.minsu.services.house.photog.dto;

import com.ziroom.minsu.entity.photographer.PhotographerBookOrderEntity;

/**
 * <p>关于预约单操作接收参数的DTO</p>
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
public class PhotoOrderDto extends PhotographerBookOrderEntity {

	private static final long serialVersionUID = 6115696225699401843L;

	//实际上门时间str格式
	private String doorHomeTimeStr;

	//收图时间str格式
	private String receivePhotoTimeStr;

	//摄影预约单修改类型
	private String updateType;

	//作废状态
	private Integer cancelStatus;

	public Integer getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(Integer cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public String getDoorHomeTimeStr() {
		return doorHomeTimeStr;
	}

	public void setDoorHomeTimeStr(String doorHomeTimeStr) {
		this.doorHomeTimeStr = doorHomeTimeStr;
	}

	public String getReceivePhotoTimeStr() {
		return receivePhotoTimeStr;
	}

	public void setReceivePhotoTimeStr(String receivePhotoTimeStr) {
		this.receivePhotoTimeStr = receivePhotoTimeStr;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}
}
