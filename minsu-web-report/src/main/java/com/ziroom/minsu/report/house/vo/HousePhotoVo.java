package com.ziroom.minsu.report.house.vo;

import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>HousePhotoVo</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */

public class HousePhotoVo extends HouseCommonVo {
   
	/**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 1449723418011236882L;
	
	@FieldMeta(name="拍摄需求",order=1)
	private String isNeedPhoto;

	public String getIsNeedPhoto() {
		return isNeedPhoto;
	}

	public void setIsNeedPhoto(String isNeedPhoto) {
		this.isNeedPhoto = isNeedPhoto;
	}
    
}
