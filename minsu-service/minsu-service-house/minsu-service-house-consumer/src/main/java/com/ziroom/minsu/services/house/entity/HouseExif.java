package com.ziroom.minsu.services.house.entity;

import java.io.Serializable;

/**
 * 
 * <p>
 * 房源图片Exif信息
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseExif implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4055172073471576043L;
	
    // 宽度像素：属性值为正整数，单位是pixel
    private Integer widthPixel;

    // 高度像素：属性值为正整数，单位是pixel
    private Integer heightPixel;

    // 水平分辨率：属性值为正整数，单位是dpi
    private Integer widthDpi;

    // 垂直分辨率：属性值为正整数，单位是dpi
    private Integer heightDpi;

    // 图片大小：属性值为正整数，单位是kb
    private Double picSize;
	
    // 图片名称
    private String picName;
    
	public Integer getWidthPixel() {
		return widthPixel;
	}

	public void setWidthPixel(Integer widthPixel) {
		this.widthPixel = widthPixel;
	}

	public Integer getHeightPixel() {
		return heightPixel;
	}

	public void setHeightPixel(Integer heightPixel) {
		this.heightPixel = heightPixel;
	}

	public Integer getWidthDpi() {
		return widthDpi;
	}

	public void setWidthDpi(Integer widthDpi) {
		this.widthDpi = widthDpi;
	}

	public Integer getHeightDpi() {
		return heightDpi;
	}

	public void setHeightDpi(Integer heightDpi) {
		this.heightDpi = heightDpi;
	}

	public Double getPicSize() {
		return picSize;
	}

	public void setPicSize(Double picSize) {
		this.picSize = picSize;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

}