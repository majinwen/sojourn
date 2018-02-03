package com.ziroom.minsu.valenum.common;


/**
 * <p>返回结果类型</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public enum RstTypeEnum {

	SUCCESS(0,"成功"),
	FAIL(1,"失败"),
	REPEAT(2,"重复调用");

	RstTypeEnum(int code, String name){
		
		this.code = code;
		this.name = name;
	}
	
	/**
	 * code值
	 */
	private int code;
	
	/**
	 * 类型名称
	 */
	private String name;

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
