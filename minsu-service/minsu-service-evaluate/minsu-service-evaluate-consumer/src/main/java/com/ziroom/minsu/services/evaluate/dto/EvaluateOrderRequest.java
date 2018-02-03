/**
 * @FileName: EvaluateOrderRequest.java
 * @Package com.ziroom.minsu.services.evaluate.dto
 * 
 * @author yd
 * @created 2016年4月7日 下午10:44:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.dto;

import java.io.Serializable;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>评价请求参数</p>
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
public class EvaluateOrderRequest extends BaseEntity implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -535336015802376595L;
	
	 /**
     * 业务主键
     */
    private String fid;

    /**
     * 评价状态(1=待审核 2=系统下线 3=人工下线 4=已发布 5=已举报)
     */
    private Integer evaStatu;
    
    /**
     * 修改备注
     */
    private String remark;
    
    /**
     * 修改人uid
     */
    private String createUid;

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getEvaStatu() {
		return evaStatu;
	}

	public void setEvaStatu(Integer evaStatu) {
		this.evaStatu = evaStatu;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUid() {
		return createUid;
	}

	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
    
    

}
