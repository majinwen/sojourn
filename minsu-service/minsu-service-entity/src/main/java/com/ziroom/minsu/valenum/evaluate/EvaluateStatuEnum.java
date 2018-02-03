/**
 * @FileName: EvaluateStatuEnum.java
 * @Package com.ziroom.minsu.services.evaluate.utils
 * 
 * @author yd
 * @created 2016年4月7日 下午9:12:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.evaluate;


/**
 * <p>评价状态枚举类型</p>
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
public enum EvaluateStatuEnum {

	AUDIT(1,"待审核"),
	SYSTEM_OFFLINE(2,"系统下线"),
	PERSON_OFFLINE(3,"人工下线"),
	ONLINE(4,"已发布"),
	REPORTED(5,"已举报");
	
	
	EvaluateStatuEnum(int evaStatuCode,String evaStatuName){
		this.evaStatuCode = evaStatuCode;
		this.evaStatuName = evaStatuName;
	}
	/**
	 * 状态的code
	 */
	private int evaStatuCode;
	
	/**
	 * 状态的名称
	 */
	private String evaStatuName;

	public int getEvaStatuCode() {
		return evaStatuCode;
	}

	public void setEvaStatuCode(int evaStatuCode) {
		this.evaStatuCode = evaStatuCode;
	}

	public String getEvaStatuName() {
		return evaStatuName;
	}

	public void setEvaStatuName(String evaStatuName) {
		this.evaStatuName = evaStatuName;
	}
	

    /**
     * 获取
     * @param EvaluateStatuEnum
     * @return
     */
    public static EvaluateStatuEnum getEvaluateStatuByCode(int evaStatuCode) {
        for (final EvaluateStatuEnum statu : EvaluateStatuEnum.values()) {
            if (statu.getEvaStatuCode() == evaStatuCode) {
                return statu;
            }
        }
        return null;
    }
	
}
