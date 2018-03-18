package com.ziroom.zrp.service.trading.valenum.finance;
/**
 * <p>财务付款单付款类型枚举</p>
 * <p>wiki地址:http://wiki.ziroom.com/pages/viewpage.action?pageId=191791167</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2018年1月11日
 * @since 1.0
 */
public enum PayVoucherType {
	ZHCZ("zhcz","充值账户"),
	YHFK("yhfk","银行付款"),
	ZZ("zz","中转"),
	YLFH("ylfh","原路返回"),
	GDYJF("gdyjf","光大云缴费"),
	ZHDJZYE("zhdjzye","账户冻结转余额"),
	YJZK("yjzk","押金转款"),
	DJZK("djzk","定金转款");
	
	
	private String value;
	private String name;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private PayVoucherType(String value, String name) {
		this.value = value;
		this.name = name;
	}
}
