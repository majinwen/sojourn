package com.ziroom.zrp.service.trading.valenum;
/**
 * <p>公司编码枚举类</p>
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
public enum CompanyCodeEnum {
	BJ_CODE(801500,"北京"),
	SH_CODE(310000,"上海");
	
	
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
	private CompanyCodeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

}
