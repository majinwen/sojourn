
package com.ziroom.minsu.valenum.customer;

/**
 * <p>照片类型枚举</p>
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
public enum PicTypeEnum {

	POSTIVE_PHOTO(0,"证件正面照"),
	NEGATIVE_PHOTO(1,"证件反面照"),
	HOLD_PHOTO(2,"手持证件照"),
	USER_PHOTO(3,"用户头像");
	
	PicTypeEnum(int code,String value){
		this.code  = code;
		this.value = value;
	}
	
	/**
	 * code值
	 */
	private  int code;
	
	/**
	 * z中文含义
	 */
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
	
	
	
}
