package com.ziroom.zrp.service.trading.proxy.calculation;

import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.pojo.PaymentBaseDataPojo;

/**
 * <p>付款方式接口</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月16日 14:59
 * @version 1.0
 * @since 1.0
 */
public interface PaymentMethod {

	PaymentTermsDto calculate(PaymentBaseDataPojo paymentBaseDataPojo) throws Exception;

}
