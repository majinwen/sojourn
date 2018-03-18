package com.ziroom.zrp.service.trading.valenum;

/**
 * <p>APP账单状态</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月28日 15:23
 * @version 1.0
 * @since 1.0
 */
public enum BillStatusForAppEnum {
	YYQ(1,"已逾期"),
	DZF(2,"待支付"),
	YZF(0,"支付成功");

	BillStatusForAppEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	private int code;
	private String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static String getNameByCode(Integer code){
		for(final BillStatusForAppEnum billStatusForAppEnum : BillStatusForAppEnum.values()){
            if(billStatusForAppEnum.getCode() == code){
                return billStatusForAppEnum.getName();
            }
        }
		return null;
	}
}
