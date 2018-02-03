/**
 * @FileName: CancleReasonEnum.java
 * @Package com.ziroom.minsu.valenum.order
 * 
 * @author loushuai
 * @created 2017年5月10日 下午5:11:49
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.order;

/**
 * <p>房东申请取消订单原因</p>
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
public enum CancelReasonEnum {
    	OTHERREASON(0,"其他"),
    	REPAIRUPGRADE(1,"房屋维修升级"),
    	CALENDARCONFLICT(2,"日历冲突"),
    	UNFIGHTREASON(3,"无法抗拒原因");
    	
    	private Integer code;
    	
    	private String reasonName;

		private CancelReasonEnum(Integer code, String reasonName) {
			this.code = code;
			this.reasonName = reasonName;
		}

		public Integer getCode() {
			return code;
		}

		public String getReasonName() {
			return reasonName;
		}
		
		/**
		 * 
		 * 根据code获取取消原因
		 *
		 * @author loushuai
		 * @created 2017年5月10日 下午5:23:24
		 *
		 * @param code
		 * @return
		 */
		public String getNameByCode(Integer code){
			if(null != code){
				for(CancelReasonEnum temp : CancelReasonEnum.values()){
					if(temp.getCode().equals(code)){
						return temp.getReasonName();
					}
				}
			}
			return null;
		}
    	
}
