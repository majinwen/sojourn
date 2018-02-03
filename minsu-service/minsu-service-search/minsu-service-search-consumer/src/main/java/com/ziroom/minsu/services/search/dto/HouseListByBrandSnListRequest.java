package com.ziroom.minsu.services.search.dto;

import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;

/** 
 * 
 * <p>根据brandsn列表查询</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class HouseListByBrandSnListRequest extends PageRequest{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4393905558070393694L;

	private List<String> brandSnList; 

    /**
     * 图片尺寸
     */
    private String picSize;
    
    
    /**
     * 图标的尺寸大小
     * @see com.ziroom.minsu.valenum.search.IconPicTypeEnum
     */
    private Integer iconPicType;
    
    private String uid;
    
    /**
     * 版本号，移动端每次加1，需要转化为数字
     */
    private Integer versionCode;
    
	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getIconPicType() {
		return iconPicType;
	}

	public void setIconPicType(Integer iconPicType) {
		this.iconPicType = iconPicType;
	}

	public List<String> getBrandSnList() {
		return brandSnList;
	}

	public String getPicSize() {
		return picSize;
	}

	public void setBrandSnList(List<String> brandSnList) {
		this.brandSnList = brandSnList;
	}

	public void setPicSize(String picSize) {
		this.picSize = picSize;
	}

 
}
