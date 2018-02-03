package com.ziroom.minsu.valenum.customer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年5月20日
 * @since 1.0
 * @version 1.0
 */
public enum BankNameEnum {

	BJYH(1003, "北京银行"),
	HXYH(1038, "华夏银行股份有限公司总行"),
	JTYH(1046, "交通银行"),
	PAYH(1068, "平安银行（原深圳发展银行）"),
	XYYH(1092, "兴业银行总行"),
	ZSYH(1100, "招商银行股份有限公司"),
	GSYH(1105, "中国工商银行"),
	GDYH(1106, "中国光大银行"),
	ZGYH(1107, "中国银行总行"),
	MSYH(1108, "中国民生银行"),
	NYYH(1109, "中国农业银行股份有限公司"),
	YZCXYH(1110, "中国邮政储蓄银行有限责任公司"),
	JSYH(1111, "中国建设银行股份有限公司总行"),
	ZXYH(1112, "中信银行股份有限公司"),
	BJNCSYYH(1118, "北京农村商业银行股份有限公司"),
	GFYH(1120, "广发银行股份有限公司"),
	SHPDYH(1126, "上海浦东发展银行");

	BankNameEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/** 编码 */
	private int code;

	/** 名称 */
	private String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static String getBankNameByCode(int code) {
		for (BankNameEnum bankName : BankNameEnum.values()) {
			if (bankName.getCode() == code) {
				return bankName.getName();
			}
		}
		return "";
	}
	
	
	public static void main(String[] args) {
		System.out.println(getBankNameByCode(1108));
	}
}
