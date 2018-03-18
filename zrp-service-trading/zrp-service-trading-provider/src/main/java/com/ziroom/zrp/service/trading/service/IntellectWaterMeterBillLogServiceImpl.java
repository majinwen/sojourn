/**
 * @FileName: IntellectWaterMeterBillLogServiceImpl.java
 * @Package com.ziroom.zrp.service.trading.service
 * 
 * @author bushujie
 * @created 2018年1月31日 下午2:50:19
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.trading.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.zrp.service.trading.dao.IntellectWaterMeterBillLogDao;
import com.ziroom.zrp.trading.entity.IntellectWaterMeterBillLogEntity;

/**
 * <p>TODO</p>
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
@Service("trading.intellectWaterMeterBillLogServiceImpl")
public class IntellectWaterMeterBillLogServiceImpl {
	
	@Resource(name="trading.IntellectWaterMeterBillLogDao")
	private IntellectWaterMeterBillLogDao intellectWaterMeterBillLogDao;
	
	/**
	 * 
	 * 	查询应收账单生成明细记录根据应收账单fid
	 *
	 * @author bushujie
	 * @created 2018年1月31日 下午2:53:46
	 *
	 * @param billFid
	 * @return
	 */
	public IntellectWaterMeterBillLogEntity getIntellectWaterMeterBillLogBybillFid(String billFid){
		return intellectWaterMeterBillLogDao.getIntellectWaterMeterBillLog(billFid);
	}
}
