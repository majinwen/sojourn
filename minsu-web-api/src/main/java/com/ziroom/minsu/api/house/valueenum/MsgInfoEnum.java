/**
 * @FileName: MsgInfoEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author yd
 * @created 2016年11月22日 上午10:11:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.house.valueenum;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005Enum;

/**
 * <p>预定订单页面 押金消息提示  兼容老板和新版</p>
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
public enum MsgInfoEnum {

	MSG_INFO("x","入住前x取消订单将产生违约金"){
		
		@Override
		public String setMsgInfo(TenantHouseDetailVo tenantHouseDetailVo){
			
			if(!Check.NuNObj(tenantHouseDetailVo)){
				
				String msg = MSG_INFO.getMsgInfoNew();
				msg = msg.replace("x", ValueUtil.getTimeInfoByDay(tenantHouseDetailVo.getUnregText1()));
				tenantHouseDetailVo.setMsgInfo(msg);
				
				//严格退订   老板展示0 新版展示： 入住前订单将产生违约金
				if(TradeRulesEnum005Enum.TradeRulesEnum005001.getValue().equals(tenantHouseDetailVo.getCheckOutRulesCode())){
					
					if(!Check.NuNStrStrict(tenantHouseDetailVo.getUnregText4())
							&&!Check.NuNStrStrict(tenantHouseDetailVo.getUnregText5())
							&&!Check.NuNStrStrict(tenantHouseDetailVo.getUnregText6())){
						tenantHouseDetailVo.setUnregText1(0);
						tenantHouseDetailVo.setMsgInfo("入住前订单将产生违约金");
					}
				}
				
			}
			return "";
		}
	};
	
	
	/**
	 * 
	 * 设置 提示信息
	 *
	 * @author yd
	 * @created 2016年11月22日 上午10:26:01
	 *
	 * @param tenantHouseDetailVo
	 * @return
	 */
	public String setMsgInfo(TenantHouseDetailVo tenantHouseDetailVo){
		return "";
	}
	
	MsgInfoEnum(String msgInfoOld,String msgInfoNew){
		
		this.msgInfoOld = msgInfoOld;
		this.msgInfoNew = msgInfoNew;
	}
	
	/**
	 * 老版本押金提示
	 */
	private String msgInfoOld;
	
	/**
	 * 新版本押金提示
	 */
	private String msgInfoNew;

	/**
	 * @return the msgInfoOld
	 */
	public String getMsgInfoOld() {
		return msgInfoOld;
	}

	/**
	 * @param msgInfoOld the msgInfoOld to set
	 */
	public void setMsgInfoOld(String msgInfoOld) {
		this.msgInfoOld = msgInfoOld;
	}

	/**
	 * @return the msgInfoNew
	 */
	public String getMsgInfoNew() {
		return msgInfoNew;
	}

	/**
	 * @param msgInfoNew the msgInfoNew to set
	 */
	public void setMsgInfoNew(String msgInfoNew) {
		this.msgInfoNew = msgInfoNew;
	}
	
	
	
}
