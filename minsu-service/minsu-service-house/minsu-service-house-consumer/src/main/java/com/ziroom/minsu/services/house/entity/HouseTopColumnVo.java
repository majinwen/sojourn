/**
 * @FileName: HouseTopColumnVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author yd
 * @created 2017年3月15日 下午7:26:08
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import java.io.Serializable;

/**
 * <p>房源top条目 vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HouseTopColumnVo  implements Serializable{

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -5249499935879670738L;

	/**
	 * 条目类型 101：亮点标题,102：描述标题,103：文章标题,104：标题1,105：标题2，201：亮点文本，202：文章文本，203：图片注释，204：文章出处，301：图片，401：视频，501：音频
	 */
	private Integer columnType;
	
	/**
	 * 条目样式 101：左对齐，102：右对齐，103：中对齐
	 */
	private Integer columnStyle;
	
	/**
	 * 条目顺序
	 */
	private Integer columnSort;
	
	/**
	 * 条目内容(根据类型存不同内容）
	 */
	private String columnContent;
	
	/**
	 * 图片地址
	 */
	private String picUrl;
	
	/**
	 * 音频地址
	 */
	private String audioUrl;
	
	/**
	 * 视频地址
	 */
	private String videoUrl;
	
	/**
	 * 视频播放地址
	 */
	private String playVideoUrl;
	
	
    /**
     * 图片宽度
     */
    private Integer width;
    
    /**
     * 图片高度
     */
    private Integer hight;
    
    /**
     * 图片宽高比
     */
    private Double imageAspectratio;
    
    
    public Double getImageAspectratio() {
		return imageAspectratio;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public void setHight(Integer hight) {
		this.hight = hight;
	}

	public void setImageAspectratio(Double imageAspectratio) {
		this.imageAspectratio = imageAspectratio;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHight() {
		return hight;
	}
	
	
	/**
	 * @return the playVideoUrl
	 */
	public String getPlayVideoUrl() {
		return playVideoUrl;
	}

	/**
	 * @param playVideoUrl the playVideoUrl to set
	 */
	public void setPlayVideoUrl(String playVideoUrl) {
		this.playVideoUrl = playVideoUrl;
	}

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * @param picUrl the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	/**
	 * @return the audioUrl
	 */
	public String getAudioUrl() {
		return audioUrl;
	}

	/**
	 * @param audioUrl the audioUrl to set
	 */
	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	/**
	 * @return the videoUrl
	 */
	public String getVideoUrl() {
		return videoUrl;
	}

	/**
	 * @param videoUrl the videoUrl to set
	 */
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	/**
	 * @return the columnType
	 */
	public Integer getColumnType() {
		return columnType;
	}

	/**
	 * @param columnType the columnType to set
	 */
	public void setColumnType(Integer columnType) {
		this.columnType = columnType;
	}

	/**
	 * @return the columnStyle
	 */
	public Integer getColumnStyle() {
		return columnStyle;
	}

	/**
	 * @param columnStyle the columnStyle to set
	 */
	public void setColumnStyle(Integer columnStyle) {
		this.columnStyle = columnStyle;
	}

	/**
	 * @return the columnSort
	 */
	public Integer getColumnSort() {
		return columnSort;
	}

	/**
	 * @param columnSort the columnSort to set
	 */
	public void setColumnSort(Integer columnSort) {
		this.columnSort = columnSort;
	}

	/**
	 * @return the columnContent
	 */
	public String getColumnContent() {
		return columnContent;
	}

	/**
	 * @param columnContent the columnContent to set
	 */
	public void setColumnContent(String columnContent) {
		this.columnContent = columnContent;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
