package com.ziroom.minsu.services.house.dto;

import com.asura.framework.base.entity.BaseEntity;


/**
 * 
 * <p>房源品质审核dto</p>
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
public class HouseQualityAuditDto extends BaseEntity{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6486045910419754335L;

	/**
	 * 房源或房间fid
	 */
	private String houseFid;
	
	/**
	 * 操作人
	 */
	private String operaterFid;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 审核说明
	 * @Deprecated
	 */
	private Integer cause;
	
	/**
	 * 审核说明
	 */
	private String auditCause;

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperaterFid() {
		return operaterFid;
	}

	public void setOperaterFid(String operaterFid) {
		this.operaterFid = operaterFid;
	}

	@Deprecated
	public Integer getCause() {
		return cause;
	}

	@Deprecated
	public void setCause(Integer cause) {
		this.cause = cause;
	}

	public String getAuditCause() {
		return auditCause;
	}

	public void setAuditCause(String auditCause) {
		this.auditCause = auditCause;
	}
	
}
