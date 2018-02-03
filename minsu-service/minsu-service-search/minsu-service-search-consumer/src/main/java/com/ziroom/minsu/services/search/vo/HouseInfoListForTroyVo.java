/**
 * @FileName: HouseInfoListVo.java
 * @Package com.ziroom.minsu.services.search.vo
 * 
 * @author zl
 * @created 2017年5月27日 上午11:16:39
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.search.vo;

import com.ziroom.minsu.services.search.entity.HouseInfoEntity;

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
public class HouseInfoListForTroyVo extends HouseInfoEntity {

	    
    /**
	 * 
	 */
	private static final long serialVersionUID = 3216518288565600720L;

	/** 房东姓名 */
    private String landlordName;

    /** 房东手机 */
    private String landlordMobile;
    
    /** 分享链接 */
    private String shareUrl;
    
	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public String getLandlordMobile() {
		return landlordMobile;
	}
 
	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
	}
	
}
