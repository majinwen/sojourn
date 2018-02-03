/**
 * @FileName: AuthIdentifyEnum.java
 * @Package com.ziroom.minsu.valenum.base
 * 
 * @author loushuai
 * @created 2017年9月1日 下午7:45:03
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.base;

/**
 * <p>自如网标识</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 动画点击状态枚举
 * @author yanb
 * @since 1.0
 * @version 1.0
 */
public enum MgClickEnum {

	MG_CLICK_OFF(0,"关闭"),
	MG_CLICK_POPUP(1,"弹窗"),
	MG_CLICK_H5(2,"H5页面");

	private Integer code;

	private String name;



	private MgClickEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}


	public Integer getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static MgClickEnum getByCode(Integer code){
		for(MgClickEnum temp : MgClickEnum.values()){
			if(code==temp.getCode()){
				return temp;
			}
		}
		return null;
	}

	
}
