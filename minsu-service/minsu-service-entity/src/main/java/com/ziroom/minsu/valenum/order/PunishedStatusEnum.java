/**
 * @FileName: PunishedStatusEnum.java
 * @Package com.ziroom.minsu.valenum.order
 * 
 * @author 
 * @created 2017年5月10日 下午5:25:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.order;

/**
 * <p>房东取消订单——处罚结果的总状态枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public enum PunishedStatusEnum {

	DEALUNDONE(10,"	处理未完成"),
	DEALHAVEDONE(11,"处理已完成");
	
	private Integer code;
	
	private String statusName;

	private PunishedStatusEnum(Integer code, String statusName) {
		this.code = code;
		this.statusName = statusName;
	}

	/**
	 * 
	 * 根据code获取取消原因
	 *
	 * @author loushuai
	 * @created 2017年5月10日 下午5:23:24
	 *
	 * @param code
	 * @return
	 */
	public String getNameByCode(Integer code){
		if(null != code){
			for(PunishedStatusEnum temp : PunishedStatusEnum.values()){
				if(temp.getCode().equals(code)){
					return temp.getStatusName();
				}
			}
		}
		return null;
	}
	
	public Integer getCode() {
		return code;
	}

	public String getStatusName() {
		return statusName;
	}

}
