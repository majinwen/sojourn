package com.ziroom.zrp.service.houses.proxy;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dto.finance.PayVoucherResponse;
import com.ziroom.zrp.service.trading.proxy.commonlogic.FinanceBaseCall;
import com.zra.common.exception.FinServiceException;

/**
 * <p>测试付款单接口</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2018年1月13日
 * @since 1.0
 */
public class FinanceBaseCallTest extends BaseTest{
	
	@Resource(name="trading.financeBaseCall")
	private FinanceBaseCall financeBaseCall;
	
	@Test
	public void testgetPayVouchers() throws FinServiceException{
		String contractCode = "SHZYS081733115";
		List<PayVoucherResponse> payVouchers = financeBaseCall.getPayVouchers(contractCode);
		System.err.println(JsonEntityTransform.Object2Json(payVouchers));
	}

}
