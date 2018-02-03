package com.ziroom.minsu.valenum.order;

/**
 * <p>费用项目</p>
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
public enum FeeItemCodeEnum {
	RENT(1, "房租","txfy"),
	DEPOSIT(2, "押金","txfy"),
	COMPENSATION(3, "赔付款","gswyj"),//这个项供以后房东给客户赔付款时使用。
	CONTRARY(4, "违约金","txfy"),
    CHECK(5, "用户结算","txfy"),
    WITHDRAWAL(6, "提现","txfy"),
    USER_ORTHER_MONEY(7, "额外消费","txfy"),
    CLEAN(8, "清洁费","txfy"),
	CASHBACK(9, "返现","txfy");

	FeeItemCodeEnum(int code, String name,String financeName) {
		this.code = code;
		this.name = name;
		this.financeName = financeName;
	}
	
	
	 public static FeeItemCodeEnum getByCode(int code){
	        for(final FeeItemCodeEnum ose : FeeItemCodeEnum.values()){
	            if(ose.getCode() == code){
	                return ose;
	            }
	        }
	        return null;
	 }
	
	

	/** 编码 */
	private int code;

	/** 名称 */
	private String name;
	
	/** 财务 名称*/
	private String financeName;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public String getFinanceName() {
		return financeName;
	}
}
