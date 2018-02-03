/**
 * @FileName: MsgUserRelSCreateTypeEnum.java
 * @Package com.ziroom.minsu.valenum.msg
 * 
 * @author loushuai
 * @created 2017年9月4日 下午3:43:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.msg;

/**
 * <p>聊天客户关系表创建者类型枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public enum MsgUserRelSCreateTypeEnum {
	    ZIROOMKE(0,"自如客"),
		MANAGER(1,"管理员");
		
		private int code;
	    
	    private String name;

	    
		private MsgUserRelSCreateTypeEnum(int code, String name) {
			this.code = code;
			this.name = name;
		}

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
		 * 根据code获取对象
		 *
		 * @author loushuai
		 */
		public static MsgUserRelSCreateTypeEnum getByCode(int code){
			for (final MsgUserRelSCreateTypeEnum temp : MsgUserRelSCreateTypeEnum.values()) {
				if(temp.code == code){
					return temp;
				}
			} 
			return null;
		}
}
