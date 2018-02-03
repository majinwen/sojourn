/**
 * @FileName: EvaStatusEnum.java
 * @Package com.ziroom.minsu.valenum.evaluate
 * 
 * @author yd
 * @created 2016年4月12日 下午6:01:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.evaluate;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.valenum.common.RequestTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.common.YesOrNoOrFrozenEnum;

/**
 * <p>订单评价状态的枚举</p>
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
public enum EvaStatusEnum {

	NO_NEED(200,"不需要评价"),
	TWO_NOT_EVA(100,"都未评价"){
		@Override
		public Integer getCjShowStatus(RequestTypeEnum requestTypeEnum) {
			return YesOrNoEnum.NO.getCode();
		}

		@Override
		public Integer getNextStatus(EvaluateUserEnum evaluateUserEnum) {
			if (Check.NuNObj(evaluateUserEnum)){
				return null;
			}
			if (evaluateUserEnum.getCode() == EvaluateUserEnum.LAN.getCode()){
				return LANFLORD_HAVE_EVA.getCode();
			}else {
				return TENANT_HAVE_EVA.getCode();
			}
		}
	},
	LANFLORD_HAVE_EVA(110,"房东已评价"){


		@Override
		public Integer getNextStatus(EvaluateUserEnum evaluateUserEnum) {
			if (Check.NuNObj(evaluateUserEnum)){
				return null;
			}
			if (evaluateUserEnum.getCode() == EvaluateUserEnum.LAN.getCode()){
				return LANFLORD_HAVE_EVA.getCode();
			}else {
				return TWO_HAVA_EVA.getCode();
			}
		}


		@Override
		public Integer getCjShowStatus(RequestTypeEnum requestTypeEnum) {
			if (Check.NuNObj(requestTypeEnum)){
				return null;
			}
			if (requestTypeEnum.getRequestType() == RequestTypeEnum.TENANT.getRequestType()){
				//房客
				return YesOrNoEnum.NO.getCode();
			}else {
				//房东
				return YesOrNoEnum.YES.getCode();
			}
		}
	},
	TENANT_HAVE_EVA(101,"房客已评价"){

		@Override
		public Integer getNextStatus(EvaluateUserEnum evaluateUserEnum) {
			if (Check.NuNObj(evaluateUserEnum)){
				return null;
			}
			if (evaluateUserEnum.getCode() == EvaluateUserEnum.LAN.getCode()){
				return TWO_HAVA_EVA.getCode();
			}else {
				return TENANT_HAVE_EVA.getCode();
			}
		}


		@Override
		public Integer getCjShowStatus(RequestTypeEnum requestTypeEnum) {
			if (Check.NuNObj(requestTypeEnum)){
				return null;
			}
			if (requestTypeEnum.getRequestType() == RequestTypeEnum.TENANT.getRequestType()){
				//房客
				return YesOrNoEnum.YES.getCode();
			}else {
				//房东
				return YesOrNoEnum.NO.getCode();
			}
		}
	},
	TWO_HAVA_EVA(111,"都已评价"){
		@Override
		public Integer getNextStatus(EvaluateUserEnum evaluateUserEnum) {
			if (Check.NuNObj(evaluateUserEnum)){
				return null;
			}
			return TWO_HAVA_EVA.getCode();
		}
	},
	CLOSE(300,"评价入口关闭");

	EvaStatusEnum(int code,String value){
		this.code = code;
		this.value = value;
	};
	
	private int code;
	
	private String value;


	/**
	 * 获取当前初见的显示状态
	 * @param requestTypeEnum
	 * @return
	 */
	public Integer getCjShowStatus(RequestTypeEnum requestTypeEnum){
		return YesOrNoOrFrozenEnum.FROZEN.getCode();
	}


	/**
	 * 获取当前评价的下一状态
	 * @author afi
	 * @param evaluateUserEnum
	 * @return
	 */
	public Integer getNextStatus(EvaluateUserEnum evaluateUserEnum){
		return null;
	}


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
     * @param code
     * @return
     */
    public static EvaStatusEnum getEvaStatusByCode(Integer code) {
		if (Check.NuNObj(code)){
			return null;
		}
        for (final EvaStatusEnum evaStatusEnum : EvaStatusEnum.values()) {
            if (evaStatusEnum.getCode()== code) {
                return evaStatusEnum;
            }
        }
        return null;
    }


}
