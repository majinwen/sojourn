package com.ziroom.minsu.valenum.order;

/**
 * <p>付款单来源</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年4月20日
 * @since 1.0
 * @version 1.0
 */
public enum PaySourceTypeEnum {
	TASK(1, "定时任务"), 
	USER_SETTLEMENT(2, "用户结算"),
	USER_WITHDRAWAL(3, "用户提现"),
	OLD_NEW_TRANS(4, "新旧订单折算"),
	FORCE_SETTLEMENT(5, "强制取消结算"),
    OVERTIME_CANCEL(6, "超时取消"),
    OVER_DRAFT(7, "透支打款"),
    CLEAR_COUPON(8, "清空优惠券金额"),
	PAY_FAILED_RECREATE(9, "打款失败重新生成"),
	CLEAN(10, "清洁费"),
	CASHBACK(11, "返现"),
	CLEAR_ACT(12, "清空活动金额");

	PaySourceTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/** code */
	private int code;

	/** 名称 */
	private String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * 根据code获取name
	 * @author lishaochuan
	 * @create 2016年8月15日下午7:18:13
	 * @param code
	 * @return
	 */
	public static String getPaySourceTypeName(Integer code) {
    	for (final PaySourceTypeEnum type : PaySourceTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getName();
            }
        }
        return "";
    }
}
