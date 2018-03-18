package com.ziroom.zrp.service.trading.valenum.finance;
/**
 * <p>财务付款单 审核状态 Integer类型1:待提交，2：待审核，3：审核驳回，4：审核通过</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2018年1月10日
 * @since 1.0
 */
public enum PayVoucherAuditFlagEnum {
	DTJ(1,"待提交"),
	DSH(2,"待审核"),
	SHBH(3,"审核驳回"),
	SHTG(4,"审核通过");
	
	private int code;
	private String name;
	private PayVoucherAuditFlagEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
