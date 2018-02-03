
package com.ziroom.minsu.valenum.order;

/**
 * <p>新旧订单关联 中 审核状态的枚举</p>
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
public enum CheckedStatusEnum {

	INIT_CHECKED(0,"未关联"),
	WAITTING_CHECKED(1,"待审批"),
	PERSONER_CHECKED_OK(2,"人工审核通过"),
	SYS_CHECKED_OK(3,"系统审核通过"),
	HAD_REFUSED(4,"已拒绝");

	CheckedStatusEnum(int code, String name) {
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
	 * 
	 * get  CheckedStatusEnum by code
	 *
	 * @author yd
	 * @created 2016年4月25日 下午3:43:46
	 *
	 * @param code
	 * @return
	 */
	public static CheckedStatusEnum getCheckedStatusEnumByCode(int code){

		for (CheckedStatusEnum checkedStatusEnum : CheckedStatusEnum.values()) {
			if(code == checkedStatusEnum.getCode()){
				return checkedStatusEnum;
			}
		}
		return null;
	}
}
