package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 *  
 * <p>房东品牌</p>
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
public class BrandLandlordVo extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1927245755078787375L;

	private String landlordUid;

    private String brandsn;

	public String getLandlordUid() {
		return landlordUid;
	}

	public String getBrandsn() {
		return brandsn;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public void setBrandsn(String brandsn) {
		this.brandsn = brandsn;
	} 
 
}
