package com.ziroom.minsu.report.house.vo;

import java.util.Date;

import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>HouseAuditVo</p>
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

public class HouseAuditVo extends HouseCommonVo {
   /**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 1449723418011236882L;
	
	@FieldMeta(name="驳回时间",order=31)
	private Date auditDate;
	
	@FieldMeta(skip = true)
	private Integer cause;
	
	@FieldMeta(name="驳回原因",order=31)
	private String causeName;

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getCause() {
		return cause;
	}

	public void setCause(Integer cause) {
		this.cause = cause;
	}

	public String getCauseName() {
		return causeName;
	}

	public void setCauseName(String causeName) {
		this.causeName = causeName;
	}

	
	
}
