/**
 * @FileName: AccountSignEnum.java
 * @Package com.ziroom.minsu.services.common.commenum
 * 
 * @author yd
 * @created 2017年6月15日 下午2:10:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.commenum;


/**
 * <p>账户系统 返回code</p>
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
public enum AccountSignEnum {

	ACCOUNT_CODE_0(0,"成功","成功"),
	ACCOUNT_CODE_10001(10001,"查无此uid","查无此uid"),
	ACCOUNT_CODE_10002(10002,"必传参数不全","必传参数不全"),
	ACCOUNT_CODE_10003(10003,"签名不匹配","签名不匹配"),
	ACCOUNT_CODE_10004(10004,"appid无效","appid无效"),
	ACCOUNT_CODE_10005(10005,"保存信息失败","证件信息已绑定其他自如账号，请修改");
	
	
	AccountSignEnum(int code,String message,String showTip ){
		this.code = code;
		this.message = message;
		this.showTip = showTip;
	}
	
	private int code;
	
	private String message;
	
	private String showTip;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the showTip
	 */
	public String getShowTip() {
		return showTip;
	}

	/**
	 * @param showTip the showTip to set
	 */
	public void setShowTip(String showTip) {
		this.showTip = showTip;
	}
	
	  /**
     * 获取当前的默认值
     * @param type
     * @param code
     * @return
     */
    public static AccountSignEnum getAccountSignEnumByCode(int code){
      
        for (AccountSignEnum sign : AccountSignEnum.values()) {
            if(code == sign.getCode()){
                return sign;
            }
        }
        return null;
    }
	
	
}
