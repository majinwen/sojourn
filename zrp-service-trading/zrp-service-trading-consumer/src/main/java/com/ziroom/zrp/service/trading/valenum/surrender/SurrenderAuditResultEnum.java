package com.ziroom.zrp.service.trading.valenum.surrender;
/**
 * <p>退租审核是否通过枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年11月21日
 * @since 1.0
 */
public enum SurrenderAuditResultEnum {
	WTG("no","审核未通过"),
	TG("yes","审核通过");
	
	private String status;
	private String name;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private SurrenderAuditResultEnum(String status, String name) {
		this.status = status;
		this.name = name;
	}
	
	
	
	

}
