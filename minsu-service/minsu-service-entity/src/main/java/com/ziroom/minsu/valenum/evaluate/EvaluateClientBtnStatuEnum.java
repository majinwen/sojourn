/**
 * @FileName: EvaluateClientBtnStatuEnum.java
 * @Package com.ziroom.minsu.services.evaluate.utils
 * 
 * @author  zl
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.evaluate;


/**
 * <p>返回给客户端的评价状态</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public enum EvaluateClientBtnStatuEnum {

	N_EVAL(0,"不可评价"),
	C_EVAL(1,"写评价"),
	C_REPLAY(2,"写公开回复"),
	S_EVAL(3,"查看评价"),
	INVITE_EVAL(11,"邀请Ta评价");
	
	EvaluateClientBtnStatuEnum(int evaStatuCode,String evaStatuName){
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
    public static EvaluateClientBtnStatuEnum getEvaluateStatuByCode(int evaStatuCode) {
        for (final EvaluateClientBtnStatuEnum statu : EvaluateClientBtnStatuEnum.values()) {
            if (statu.getEvaStatuCode() == evaStatuCode) {
                return statu;
            }
        }
        return null;
    }
	
}
