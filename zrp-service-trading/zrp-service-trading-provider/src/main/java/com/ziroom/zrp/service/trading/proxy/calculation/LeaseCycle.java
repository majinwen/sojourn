package com.ziroom.zrp.service.trading.proxy.calculation;

import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.pojo.PaymentBaseDataPojo;

/**
 * <p>租赁周期接口</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年09月12日 17:28
 * @since 1.0
 */
public interface LeaseCycle {
	//计算月租和年租
	PaymentTermsDto calculate(PaymentBaseDataPojo paymentBaseDataPojo)throws Exception;

	// 计算实际房租
	String calculateActualRoomPrice(Integer conRentYear, Double roomSalesPrice);
}
