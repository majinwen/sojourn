package com.ziroom.minsu.troy.order.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;

/**
 * <p>调用财务系统</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月29日
 * @since 1.0
 * @version 1.0
 */
@Service("api.callFinanceService")
public class CallFinanceService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CallFinanceService.class);

	@Value("#{'${pay_vourcher_flag_url}'.trim()}")
	private String PAY_VOURCHER_FLAG_URL;

	
	/**
	 * 根据付款单订单号和付款单号查询付款状态
	 * @author lishaochuan
	 * @create 2016年8月29日下午3:53:39
	 * @param orderCode
	 * @param pvSn
	 * @return
	 */
	public Map<String, Object> callPayVoucherFlag(String orderCode, String pvSn) {
		Map<String, String> param = new HashMap<>();
		param.put("orderCode", orderCode);
		param.put("busId", pvSn);
		String resJson = CloseableHttpUtil.sendFormPost(PAY_VOURCHER_FLAG_URL, param);

		Map<String, Object> resMap = new HashMap<String, Object>(2);
		if (!Check.NuNStr(resJson)) {
			JSONObject jsonObj = JSONObject.parseObject(resJson);
			resMap.put("result", jsonObj.getInteger("result"));
			resMap.put("payFlag", jsonObj.getString("content"));
		} else {
			resMap.put("result", 0);
		}
		return resMap;
	}

}
