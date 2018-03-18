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
 * <p>智能水表相关接口</p>
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
public interface IntellectWaterMeterService {

    /**
     *
     * 查询最后一个定时抄水表记录
     *
     * @author bushujie
     * @created 2018年1月22日 下午2:44:17
     *
     * @param paramJson
     * @return
     */
    String getLastIntellectWatermeterReadByRoomId(String paramJson);

	/**
	 * 
	 * 查询应收账单生成明细记录根据应收账单fid
	 *
	 * @author bushujie
	 * @created 2018年1月31日 下午2:47:08
	 *
	 * @param billFid
	 * @return
	 */
	String getIntellectWaterMeterBillLogByBillFid(String billFid);

	/**
	 *
	 * 分页查询定时任务的水表抄表记录
	 *
	 * @author zhangyl2
	 * @created 2018年02月24日 18:40
	 * @param
	 * @return
	 */
    String getIntellectWatermeterReadByPage(String paramJson);

}
