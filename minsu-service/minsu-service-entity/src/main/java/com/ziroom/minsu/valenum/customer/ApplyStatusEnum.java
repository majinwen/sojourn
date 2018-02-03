
package com.ziroom.minsu.valenum.customer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>活动申请状态</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月30日
 * @since 1.0
 * @version 1.0
 */
public enum ApplyStatusEnum {

	APPLY(1,"提交申请");
	
	ApplyStatusEnum(int code,String value){
		this.code = code;
		this.value = value;
	}
	
	/**
	 * code值
	 */
	private  int code;
	
	/**
	 * 中文含义
	 */
	private String value;
	
	
    private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();

    static {
        for (ApplyStatusEnum em : ApplyStatusEnum.values()) {
            enumMap.put(em.getCode(), em.getValue());
        }
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
	
	public static String getNameByCode(int code){
		return enumMap.get(code);
	}
	
	
}
