/**
 * @FileName: MemberStatuEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author yd
 * @created 2017年8月7日 下午2:26:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>群成员 状态</p>
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
public enum MemberStatuEnum {
	//0=正常 1=禁言 2=黑名单
	NORMAL(0,"正常 ","#{nickName}加入了群聊","#{nickName}被移除了群聊","#{nickName}退出了群聊"),
	GAG(1,"禁言","#{nickName}被禁言","#{nickName}被解除了禁言",""),
	BLACKLIST(2,"黑名单","#{nickName}加入黑名单","#{nickName}被移除黑名单",""),
	MANAGER(3,"管理员","#{nickName}升级为管理员","#{nickName}被取消管理员","");

	/**
	 * 角色code
	 */
	private int code;

	/**
	 * 角色描述
	 */
	private String  val;
	
	/**
	 * 向群里推送消息  XX已入群
	 */
	private String inImMsg;
	
	/**
	 * 出群消息
	 */
	private String outImMsg;
	
	/**
	 * 出群消息 扩展
	 */
	private String outImMsgExt;

	MemberStatuEnum(int code,String val,String inImMsg,String outImMsg,String  outImMsgExt){
		this.code = code;
		this.val = val;
		this.inImMsg = inImMsg;
		this.outImMsg = outImMsg;
		this.outImMsgExt = outImMsgExt;
	}

	

	/**
	 * @return the outImMsgExt
	 */
	public String getOutImMsgExt() {
		return outImMsgExt;
	}



	/**
	 * @param outImMsgExt the outImMsgExt to set
	 */
	public void setOutImMsgExt(String outImMsgExt) {
		this.outImMsgExt = outImMsgExt;
	}



	/**
	 * @return the inImMsg
	 */
	public String getInImMsg() {
		return inImMsg;
	}


	/**
	 * @param inImMsg the inImMsg to set
	 */
	public void setInImMsg(String inImMsg) {
		this.inImMsg = inImMsg;
	}


	/**
	 * @return the outImMsg
	 */
	public String getOutImMsg() {
		return outImMsg;
	}


	/**
	 * @param outImMsg the outImMsg to set
	 */
	public void setOutImMsg(String outImMsg) {
		this.outImMsg = outImMsg;
	}




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
	 * @return the val
	 */
	public String getVal() {
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(String val) {
		this.val = val;
	}


	public static MemberStatuEnum   getMemberStatuEnumByCode(int code){

		for (MemberStatuEnum memberStatu : MemberStatuEnum.values()) {

			if(code == memberStatu.getCode()){
				return memberStatu;
			}
		}
		return null;
	}

}
