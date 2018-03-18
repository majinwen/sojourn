package com.zra.house.entity.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 户型标签dto
 * @author tianxf9
 *
 */
public class HouseTypeLabDto implements Serializable{
	
	@ApiModelProperty(value="显示内容")
	private String content;
	
	@ApiModelProperty(value="icon地址")
	private String imgUrl;
	
	@ApiModelProperty(value="活动h5链接")
	private String hUrl;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String gethUrl() {
		return hUrl;
	}

	public void sethUrl(String hUrl) {
		this.hUrl = hUrl;
	}
	
	
	
}
