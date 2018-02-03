package com.ziroom.minsu.valenum.msg;

/**
 * <p>扩展消息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public enum MsgExtTypeEnum {

	NORMAL(0,"普通消息"),
	FIRST_ADVICE(1,"用户咨询消息"),
	SHARE_CARD(2,"分享卡片"),
	AUTO_REPLY(3,"自动回复消息");

	MsgExtTypeEnum(int code , String name){
		
		this.code = code;
		this.name = name;
	}
	
	/**
	 * 枚举编码
	 */
	private int code;
	
	/**
	 * 名称
	 */
	private String name;

	public int getCode() {
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

	/**
	 * 根据code获取对象targetTypeEnum
	 *
	 * @author lunan
	 */
	public static MsgExtTypeEnum getTargetTypeEnum(int code){
		for (final MsgExtTypeEnum targetTypeEnum : MsgExtTypeEnum.values()) {
			if(targetTypeEnum.code == code){
				return targetTypeEnum;
			}
		} 
		return null;
	}
	
	
}
