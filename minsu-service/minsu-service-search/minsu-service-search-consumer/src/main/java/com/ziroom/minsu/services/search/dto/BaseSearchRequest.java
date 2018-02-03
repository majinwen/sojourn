/**
 * @FileName: BaseSearchRequest.java
 * @Package com.ziroom.minsu.services.search.dto
 * 
 * @author zl
 * @created 2017年5月10日 下午9:22:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.search.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.valenum.search.SearchSourceTypeEnum;

/**
 * <p>TODO</p>
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
public class BaseSearchRequest extends PageRequest {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6785872163879809668L;

	/**
	 * 图标的类型
	 * 
	 * @see com.ziroom.minsu.valenum.search.IconPicTypeEnum
	 */
	private Integer iconType;
	
    /**
     * 图片尺寸
     */
    private String picSize;

	/**
	 * 是否在目标城市
	 */
	private Integer isTargetCityLocal;

	/**
	 * 当前所在城市名称
	 */
	private String inCityName;

	/**
	 * 版本号，移动端每次加1，需要转化为数字
	 */
	private Integer versionCode;

	/**
	 * 是否推荐
	 */
	private Integer isRecommend;
	
	/**
	 * 搜索入口
	 */
	private SearchSourceTypeEnum searchSourceTypeEnum;
	
	/**
	 * 当前用户uid
	 */
    private String uid;

	public Integer getIconType() {
		return iconType;
	}

	public String getPicSize() {
		return picSize;
	}

	public Integer getIsTargetCityLocal() {
		return isTargetCityLocal;
	}

	public String getInCityName() {
		return inCityName;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public SearchSourceTypeEnum getSearchSourceTypeEnum() {
		return searchSourceTypeEnum;
	}

	public String getUid() {
		return uid;
	}

	public void setIconType(Integer iconType) {
		this.iconType = iconType;
	}

	public void setPicSize(String picSize) {
		this.picSize = picSize;
	}

	public void setIsTargetCityLocal(Integer isTargetCityLocal) {
		this.isTargetCityLocal = isTargetCityLocal;
	}

	public void setInCityName(String inCityName) {
		this.inCityName = inCityName;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public void setSearchSourceTypeEnum(SearchSourceTypeEnum searchSourceTypeEnum) {
		this.searchSourceTypeEnum = searchSourceTypeEnum;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	
}
