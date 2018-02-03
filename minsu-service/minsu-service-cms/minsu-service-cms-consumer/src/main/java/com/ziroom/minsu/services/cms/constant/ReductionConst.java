package com.ziroom.minsu.services.cms.constant;

/**
 * <p>首单立减返回状态</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lusp on 2017年6月3日
 * @since 1.0
 * @version 1.0
 */
public enum ReductionConst {

	REDUCTION_WAS_OPEN (111000,"首单立减活动已开启"),
	REDUCTION_NO_OPEN (111001,"首单立减活动未开启"),
	REDUCTION_UID_NULL(111002,"用户不存在"),
	REDUCTION_NO_SMOKE(111003,"用户不符合首单立减条件"),
	REDUCTION_PAR_ERROR(111004,"异常参数"),
	REDUCTION_SYS_ERROR(111005,"系统异常");


	private int code;
	private String name;

	ReductionConst(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
