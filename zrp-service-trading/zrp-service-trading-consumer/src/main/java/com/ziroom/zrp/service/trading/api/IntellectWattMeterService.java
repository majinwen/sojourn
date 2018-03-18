/**
 * @FileName: IntellectWaterMeterService.java
 * @Package com.ziroom.zrp.service.trading.api
 * 
 * @author bushujie
 * @created 2018年1月22日 下午2:42:01
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.trading.api;


/**
 * <p>智能电表相关接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public interface IntellectWattMeterService {

	/**
	 * 回调保存数据
	 * @param param
	 * @return
	 */
	String rechargeIntellectWattMeterCallBack(String param);

	/**
	 * 重试失败记录 定时任务
	 */
	void retryFailWattMeterJob();

	/**
	 * 低电量回调
	 * @param param
	 * @return
	 */
	String lowlevelCallBack(String param);

	/**
	  * @description: 校验当前房间有没有有效合同并修改智能电表的付费方式
	  * 				1.解约、退租、合同作废、关闭合同后,判断当前房间下有没有有效合同,若有，直接跳过，若没有，设置电表为后付费
	  * 				2.新签时（物业交割前），判断当前房间下是否有有效合同，若有，直接跳过，若没有，设置电表为预付费
	  * @author: lusp
	  * @date: 2018/3/14 下午 15:56
	  * @params: paramJson
	  * @return: String
	  */
	String checkValidContractAndModifyWattPayType(String paramJson);

}
