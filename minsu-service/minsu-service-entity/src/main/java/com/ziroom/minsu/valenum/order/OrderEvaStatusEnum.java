package com.ziroom.minsu.valenum.order;


/**
 * <p>订单上的评价状态：评价状态200：不可评价 100 待评价 101 用户已评价 110 房东已评价 111 都已经评价</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public enum OrderEvaStatusEnum {

	NO_EVA(200,"不可评价"),
	WAITINT_EVA(100,"待评价"),
	UESR_HVA_EVA(101,"用户已评价 "),
	LANLORD_EVA(110,"房东已评价"),
	ALL_EVA(111,"都已经评价");
	
	OrderEvaStatusEnum(int code,String value){
		this.code = code;
		this.value = value;
	};
	
	private int code;
	
	private String value;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	 /**
     * 获取
     * @param EvaStatusEnum
     * @return
     */
    public static OrderEvaStatusEnum getOrderEvaStatusEnumByCode(int code) {
        for (final OrderEvaStatusEnum evaStatusEnum : OrderEvaStatusEnum.values()) {
            if (evaStatusEnum.getCode()== code) {
                return evaStatusEnum;
            }
        }
        return null;
    }
	
}
