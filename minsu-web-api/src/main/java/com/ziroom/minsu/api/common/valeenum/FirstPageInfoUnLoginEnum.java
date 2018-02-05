/**
 * @FileName: FirstPageInfoEnum.java
 * @Package com.ziroom.minsu.api.common.valeenum
 * 
 * @author yd
 * @created 2017年5月25日 下午3:26:57
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.valeenum;

/**
 * <p>首页 未登陆接口整合</p>
 *  说明:
 *    此处key值不能重复
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
public enum FirstPageInfoUnLoginEnum {
	
	
	TOP50("TOP50",10001,"TOP50列表数据"),
	TODAYDISCOUNT("TODAYDISCOUNT",10002,"今日特惠"),
	LASTHOUSE("LASTHOUSE",10003,"新上房源");
	
	
	FirstPageInfoUnLoginEnum(String key,int code,String desc){
		
		this.key = key;
		this.code = code;
		this.desc = desc;
	}
	
	/**
	 * 接口返回key
	 */
	private String key;
	
	/**
	 * code值
	 */
	private int code;
	
	/**
	 * 接口返回信息描述
	 */
	private String desc;
	

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
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	
	
	
	
}
