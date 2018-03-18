package com.ziroom.zrp.service.trading.valenum.surrender;
/**
 * <p>解约申请状态:0 解约申请 1解约</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年10月30日
 * @since 1.0
 */
public enum SurrenderApplyStatusEnum {
	APPLYSTAGE(0,"解约申请"),
	SURRSTAGE(1,"解约");
	
	private int code;
	private String name;
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
	private SurrenderApplyStatusEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
}
