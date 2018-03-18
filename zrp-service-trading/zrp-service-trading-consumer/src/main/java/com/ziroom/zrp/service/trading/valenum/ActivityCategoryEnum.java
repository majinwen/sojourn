package com.ziroom.zrp.service.trading.valenum;

/**
 * <p>活动种类枚举</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月18日 20:56
 * @since 1.0
 */
public enum ActivityCategoryEnum {
	ACTIVITY(1,"优惠活动"),
	PAY_METHOD(2,"付款方式折扣"),
	ADVANCE(3,"提前续约折扣"),
	OLD_CUSTOMER(4,"老客户续约折扣");

	ActivityCategoryEnum(int code, String name) {
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
}
