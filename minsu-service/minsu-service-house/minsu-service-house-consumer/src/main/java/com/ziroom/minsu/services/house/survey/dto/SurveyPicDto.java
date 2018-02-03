package com.ziroom.minsu.services.house.survey.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>实勘图片dto</p>
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
public class SurveyPicDto extends BaseEntity{
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 7568079617076310703L;

	/**
     * 实勘表逻辑fid
     */
    private String surveyFid;
    
    /**
     * 图片类型
     */
	private Integer picType;

	public String getSurveyFid() {
		return surveyFid;
	}

	public void setSurveyFid(String surveyFid) {
		this.surveyFid = surveyFid;
	}

	public Integer getPicType() {
		return picType;
	}

	public void setPicType(Integer picType) {
		this.picType = picType;
	}
}
