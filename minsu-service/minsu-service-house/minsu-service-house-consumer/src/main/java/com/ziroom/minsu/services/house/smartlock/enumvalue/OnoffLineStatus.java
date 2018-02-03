/**
 * @FileName: OnoffLineStatus.java
 * @Package com.ziroom.minsu.services.house.smartlock.enumvalue
 * 
 * @author yd
 * @created 2016年6月24日 下午3:26:21
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.enumvalue;

/**
 * <p>门锁状态</p>
 * 门锁在线状态：
 *1：在线
 *2：离线
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
public enum OnoffLineStatus {

	   ON_LINE(1,"在线"),
	   OFF_LINE(2,"离线"),
	   ERROR(3,"门锁异常");
	   
	   OnoffLineStatus(int code,String value){
		   
		   this.code = code;
		   this.value =  value;
	   }
	   
	   /**
		 * 枚举值
		 */
		private int code;
		
		/**
		 * 中文含义
		 * 
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
