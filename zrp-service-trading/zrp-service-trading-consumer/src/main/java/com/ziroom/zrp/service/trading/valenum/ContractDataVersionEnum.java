package com.ziroom.zrp.service.trading.valenum;
/**
 * <p>合同表data_version枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年12月5日
 * @since 1.0
 */
public enum ContractDataVersionEnum {
	OLD(0,"旧"),//改版前的数据
	NEW(1,"新");
	
	private int code;
	private String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	private ContractDataVersionEnum() {
	}

	private ContractDataVersionEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
}
