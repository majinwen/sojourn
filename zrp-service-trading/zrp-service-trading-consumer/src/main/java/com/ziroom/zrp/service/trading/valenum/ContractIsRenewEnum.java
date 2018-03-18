package com.ziroom.zrp.service.trading.valenum;
/**
 * <p>是否续约枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年11月13日
 * @since 1.0
 */
public enum ContractIsRenewEnum {
	DQR(0, "待确认"),
	YXY(1, "已续约"),
	WXQ(2, "未续约");
	
	
	private int code;
	
	private String status;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private ContractIsRenewEnum(int code, String status) {
		this.code = code;
		this.status = status;
	}

}
