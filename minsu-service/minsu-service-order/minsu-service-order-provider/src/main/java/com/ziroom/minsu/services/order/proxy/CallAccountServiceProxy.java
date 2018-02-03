package com.ziroom.minsu.services.order.proxy;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.jboss.resteasy.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.account.dto.AccountDetailRequest;
import com.ziroom.minsu.services.account.dto.BalanceMoneyRequest;
import com.ziroom.minsu.services.account.dto.BalanceThawRequest;
import com.ziroom.minsu.services.account.dto.BalanceToFreezeRequest;
import com.ziroom.minsu.services.account.dto.FillMoneyRequest;
import com.ziroom.minsu.services.account.dto.FreezeConsumeRequest;
import com.ziroom.minsu.services.account.dto.TransfersAccountRequest;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.CryptAES;

/**
 * <p>调账户系统接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年5月1日
 * @since 1.0
 * @version 1.0
 */
@Service("order.callAccountServiceProxy")
public class CallAccountServiceProxy {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CallAccountServiceProxy.class);
	
	
	@Value(value = "${ACCOUNT.ACCOUNT_KEY}")
    private String ACCOUNT_KEY;
	
	@Value(value = "${ACCOUNT.ACCOUNT_URL}")
    private String ACCOUNT_URL;
	
	@Value(value = "${ACCOUNT.ACCOUNT_DETAILS}")
    private String ACCOUNT_DETAILS;
	
	@Value(value = "${ACCOUNT.TRANSFERS_ACCOUNT}")
    private String TRANSFERS_ACCOUNT;
	
	@Value(value = "${ACCOUNT.ACCOUNT_BALANCE_THAW}")
    private String ACCOUNT_BALANCE_THAW;
	
	@Value(value = "${ACCOUNT.CONSUME_R_FROZEN_ACCOUNT}")
    private String CONSUME_R_FROZEN_ACCOUNT;
	
	@Value(value = "${ACCOUNT.BALANCE_TO_FREEZE_ACCOUNT}")
    private String BALANCE_TO_FREEZE_ACCOUNT;
	
	@Value(value = "${ACCOUNT.FILL_FREEZE_AMOUNT}")
    private String FILL_FREEZE_AMOUNT;
	
	@Value(value = "${ACCOUNT.CONSUME_BALANCE_MONEY}")
    private String CONSUME_BALANCE_MONEY;
	
	@Value(value = "${ACCOUNT.FILL_BALANCE_MONEY}")
    private String FILL_BALANCE_MONEY;
	
	@Value(value = "${PAY.ENCODING}")
    private String ENCODING;
	
	/**
   	 * 获取账户余额
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param adr
   	 * @return
   	 */
	public Map<String,String> accountDetails(AccountDetailRequest adr) throws Exception{
		LogUtil.info(LOGGER,"accountDetails params:{}",JsonEntityTransform.Object2Json(adr));
		JSONObject jsonParam = makeAccountDetailsDataJson(adr);
		String resultContent = "";
		
		String url = ACCOUNT_URL+ACCOUNT_DETAILS+"?encryption="+URLEncoder.encode(CryptAES.AES_Encrypt(ACCOUNT_KEY, jsonParam.toString()),ENCODING);
		resultContent = excuteAndGetResult(url);
		LogUtil.info(LOGGER,"accountDetails result:{}",resultContent);
		return parseAcDetailsRep(resultContent);
		
	}
	
	
	/**
   	 * 处理 账户余额 接口返回值
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param resultContent
   	 * @return
   	 */
	private Map<String,String> parseAcDetailsRep(String resultContent) throws Exception {
		Map<String,String> resMap = new HashMap<String,String>(7);
		if (resultContent != null) {
			JSONObject resJson = JSONObject.parseObject(resultContent);
			JSONObject metaJson = resJson.getJSONObject("meta");
			if(!Check.NuNObj(metaJson)){
				resMap.put("api", metaJson.getString("api"));
				resMap.put("params", metaJson.getString("params"));
				resMap.put("status", metaJson.getString("status"));
			}
			
			JSONObject resultJson = resJson.getJSONObject("result");
			if(!Check.NuNObj(resultJson)){
				resMap.put("balance", resultJson.getString("balance"));
				resMap.put("frozen_balance", resultJson.getString("frozen_balance"));
			}
			
		} else {
			resMap.put("status", "FAILED");
			LogUtil.error(LOGGER, "parseAcDetailsRep接口没有返回信息{}", resultContent);
		}
		return resMap;
	}
	
	
	
	/**
   	 * 封装 获取账户余额 参数
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param adr
   	 * @return
   	 */
	private JSONObject makeAccountDetailsDataJson(AccountDetailRequest adr){
		JSONObject paramJson = new JSONObject();
		paramJson.put("uid", adr.getUid()); 
		paramJson.put("city_code", SysConst.Common.company_ziroom_minsu);
		paramJson.put("sys_source", SysConst.sys_source); 
		paramJson.put("uid_type", adr.getUidType()); 
		return paramJson;

	}
	
	
	
	
	/**
   	 * 账户间 转账
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param tar
   	 * @return
   	 */
	public Map<String,String> transfersAccount(TransfersAccountRequest tar) throws Exception{
		LogUtil.info(LOGGER,"transfersAccount params:{}",JsonEntityTransform.Object2Json(tar));
		JSONObject jsonParam = makeTransfersAccountDataJson(tar);
		String resultContent = "";
		
		String url = ACCOUNT_URL + TRANSFERS_ACCOUNT+"?encryption="+URLEncoder.encode(CryptAES.AES_Encrypt(ACCOUNT_KEY, jsonParam.toString()),ENCODING);
		resultContent = excuteAndGetResult(url);
		LogUtil.info(LOGGER,"transfersAccount result:{}",resultContent);
		return parseReponse(resultContent);
		
	}
	
	
	/**
   	 * 封装 账户间转账 参数
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param tar
   	 * @return
   	 */
	private JSONObject makeTransfersAccountDataJson(TransfersAccountRequest tar){
		JSONObject paramJson = new JSONObject();
		
		paramJson.put("orig_uid", tar.getOrigUid()); 
		paramJson.put("orig_city_code", SysConst.Common.company_ziroom_minsu);
		paramJson.put("orig_business_type", tar.getOrigBusinessType()); 
		paramJson.put("uid_type", tar.getUidType()); 
		paramJson.put("reduce_type", tar.getReduceType()); 
		paramJson.put("target_uid", tar.getTargetUid()); 
		paramJson.put("pay_type", tar.getPayType()); 
		paramJson.put("target_city_code",SysConst.Common.company_ziroom_minsu);
		paramJson.put("target_uid_type", tar.getTargetUidType()); 
		paramJson.put("add_type", tar.getAddType()); 
		paramJson.put("trade_no", tar.getTradeNo()); 
		paramJson.put("total_fee", tar.getTotalFee()); 
		paramJson.put("biz_common", SysConst.sys_source+ SysConst.sys_under_line + SysConst.account_transfers_money); 
		paramJson.put("description", tar.getDescription()); 
		paramJson.put("sys_source", SysConst.sys_source); 
		paramJson.put("order_id", tar.getOrderSn());
		return paramJson;
    }
	
	
	
	/**
   	 * 账户余额解冻
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param btr
   	 * @return
   	 */
	public Map<String,String> accountBalanceThaw(BalanceThawRequest btr) throws Exception{
		LogUtil.info(LOGGER,"accountBalanceThaw params:{}",JsonEntityTransform.Object2Json(btr));
		JSONObject jsonParam = makeBalanceThawDataJson(btr);
		String resultContent = "";
		
		String url = ACCOUNT_URL+ACCOUNT_BALANCE_THAW+"?encryption="+URLEncoder.encode(CryptAES.AES_Encrypt(ACCOUNT_KEY, jsonParam.toString()),ENCODING);
		resultContent = excuteAndGetResult(url);
		LogUtil.info(LOGGER,"accountBalanceThaw result:{}",resultContent);
		return parseReponse(resultContent);
		
	}
	
	
	/**
   	 * 封装 余额解冻 参数
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param btr
   	 * @return
   	 */
	private JSONObject makeBalanceThawDataJson(BalanceThawRequest btr){
		JSONObject paramJson = new JSONObject();
		paramJson.put("unique_num", btr.getUnique_num());
		paramJson.put("uid", btr.getUid()); 
		paramJson.put("trade_no", btr.getTrade_no()); 
		paramJson.put("total_fee", btr.getTotalFee()); 
		paramJson.put("biz_common", SysConst.sys_source+ SysConst.sys_under_line + SysConst.account_balance_thaw);
		paramJson.put("city_code", SysConst.Common.company_ziroom_minsu);
	    paramJson.put("business_type", SysConst.account_dy006); 
		paramJson.put("sys_source", SysConst.sys_source); 
		paramJson.put("uid_type", btr.getUid_type()); 
		paramJson.put("order_id", btr.getOrderSn());
		return paramJson;

	}
	
	
	
    
    /**
   	 * 消费  冻结金
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param fsr
   	 * @return
   	 */
	public Map<String,String> consumeRfrozenAccount(FreezeConsumeRequest fsr) throws Exception{
		LogUtil.info(LOGGER,"consumeRfrozenAccount params:{}",JsonEntityTransform.Object2Json(fsr));
		JSONObject jsonParam = makeRfrozenAccountDataJson(fsr);
		String resultContent = "";
		
		String url = ACCOUNT_URL + CONSUME_R_FROZEN_ACCOUNT+"?encryption="+URLEncoder.encode(CryptAES.AES_Encrypt(ACCOUNT_KEY, jsonParam.toString()),ENCODING);
		resultContent = excuteAndGetResult(url);
		LogUtil.info(LOGGER,"consumeRfrozenAccount result:{}",resultContent);
		return parseReponse(resultContent);
		
	}

	
	/**
   	 * 消费  冻结金
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param fsr
   	 * @return
   	 */
	private JSONObject makeRfrozenAccountDataJson(FreezeConsumeRequest fsr){
		JSONObject paramJson = new JSONObject();
		paramJson.put("unique_num", fsr.getUnique_num());
		paramJson.put("uid", fsr.getUid()); 
		paramJson.put("total_fee", fsr.getTotalFee()); 
		paramJson.put("biz_common", SysConst.sys_source+ SysConst.sys_under_line + fsr.getBiz_common_type()); 
		paramJson.put("description",fsr.getDescription() ); 
		paramJson.put("city_code", SysConst.Common.company_ziroom_minsu);
		paramJson.put("business_type", fsr.getBussiness_type()); //短租自动提现传：3       短租业主按天结算（或者解约的时候），消费40     作为服务费传：4
		paramJson.put("sys_source", SysConst.sys_source); 
		paramJson.put("uid_type", fsr.getUidType()); 
		paramJson.put("order_id", fsr.getOrderSn());

		return paramJson;

	}
	
	 /**
   	 * 余额 转 冻结
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param btfr
   	 * @return
   	 */
	public Map<String,String> balanceToFreezeAccount(BalanceToFreezeRequest btfr) throws Exception{
		LogUtil.info(LOGGER,"balanceToFreezeAccount params:{}",JsonEntityTransform.Object2Json(btfr));
		JSONObject jsonParam = makeRfrozenAccountDataJson(btfr);
		String resultContent = "";
		
		String url = ACCOUNT_URL + BALANCE_TO_FREEZE_ACCOUNT+"?encryption="+URLEncoder.encode(CryptAES.AES_Encrypt(ACCOUNT_KEY, jsonParam.toString()),ENCODING);
		resultContent = excuteAndGetResult(url);
		LogUtil.info(LOGGER,"balanceToFreezeAccount result:{}",resultContent);
		return parseReponse(resultContent);
		
	}

	
	/**
   	 * 拼 余额 转 冻结 参数
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param btfr
   	 * @return
   	 */
	private JSONObject makeRfrozenAccountDataJson(BalanceToFreezeRequest btfr){
		JSONObject paramJson = new JSONObject();
		paramJson.put("unique_num", btfr.getUnique_num()); 
		paramJson.put("uid", btfr.getUid()); 
		paramJson.put("total_fee", btfr.getTotalFee()); 
		paramJson.put("city_code", SysConst.Common.company_ziroom_minsu);
		paramJson.put("biz_common", SysConst.sys_source+ SysConst.sys_under_line + SysConst.account_balance_to_freeze);
	    paramJson.put("business_type", "");//TODO:暂时不传
		paramJson.put("sys_source", btfr.getSysSource()); 
		paramJson.put("uid_type", btfr.getUidType());
		return paramJson;

	}
	
	 /**
   	 * 账户余额 账户余额增加
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月25日 
   	 *
   	 * @param btfr
   	 * @return
   	 */
	public Map<String,String> fillBalanceAmount(FillMoneyRequest btfr) throws Exception{
		LogUtil.info(LOGGER,"fillBalanceAmount params:{}",JsonEntityTransform.Object2Json(btfr));
		JSONObject jsonParam = makeFillMoneyDataJson(btfr);
		String resultContent = "";
		
		String url = ACCOUNT_URL + FILL_BALANCE_MONEY +"?encryption="+URLEncoder.encode(CryptAES.AES_Encrypt(ACCOUNT_KEY, jsonParam.toString()),ENCODING);
		resultContent = excuteAndGetResult(url);
		LogUtil.info(LOGGER,"fillBalanceAmount result:{}",resultContent);
		return parseReponse(resultContent);
		
	}
	
	
	/**
   	 * 账户充值冻结
   	 * 给账户充值冻结金，该账户的冻结金增加，余额不变
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月25日 
   	 *
   	 * @param btfr
   	 * @return
   	 */
	public Map<String,String> fillFreezeAmount(FillMoneyRequest btfr) throws Exception{
		LogUtil.info(LOGGER,"fillFreezeAmount params:{}",JsonEntityTransform.Object2Json(btfr));
		JSONObject jsonParam = makeFillFreezeMoneyDataJson(btfr);
		String resultContent = "";
		
		String url = ACCOUNT_URL + FILL_FREEZE_AMOUNT +"?encryption="+URLEncoder.encode(CryptAES.AES_Encrypt(ACCOUNT_KEY, jsonParam.toString()),ENCODING);
		resultContent = excuteAndGetResult(url);
		LogUtil.info(LOGGER,"fillFreezeAmount result:{}",resultContent);
		return parseReponse(resultContent);
		
	}
	
	
	/**
   	 * 封装 充值余额 参数
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月25日 
   	 * @param ffr
   	 * @return
   	 */
	private JSONObject makeFillMoneyDataJson(FillMoneyRequest ffr){
		JSONObject paramJson = new JSONObject();
		paramJson.put("uid", ffr.getUid()); 
		paramJson.put("trade_no", ffr.getTradeNo());
		paramJson.put("total_fee", ffr.getTotalFee());
		paramJson.put("pay_type", ffr.getPayType());
		paramJson.put("pay_time", DateUtil.formatDate(ffr.getPayTime(), "yyyy-MM-dd HH:mm:ss"));
		paramJson.put("city_code", SysConst.Common.company_ziroom_minsu);
		paramJson.put("biz_common", SysConst.sys_source+ SysConst.sys_under_line + SysConst.account_fill_money);
		paramJson.put("description", ffr.getBizCommon());
		paramJson.put("business_type", String.valueOf(ffr.getBusiness_type()));
		paramJson.put("sys_source", SysConst.sys_source);
		paramJson.put("uid_type", ffr.getUidType());
		paramJson.put("order_id", ffr.getOrderSn());
		return paramJson;

	}
	
	
	/**
   	 * 封装充值冻结金额参数
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月25日 
   	 * @param ffr
   	 * @return
   	 */
	private JSONObject makeFillFreezeMoneyDataJson(FillMoneyRequest ffr){
		JSONObject paramJson = new JSONObject();
		paramJson.put("uid", ffr.getUid()); 
		paramJson.put("trade_no", ffr.getTradeNo());
		paramJson.put("total_fee", ffr.getTotalFee());
		paramJson.put("pay_type", ffr.getPayType());
		paramJson.put("pay_time", DateUtil.formatDate(ffr.getPayTime(), "yyyy-MM-dd HH:mm:ss"));
		paramJson.put("city_code", SysConst.Common.company_ziroom_minsu);
		paramJson.put("biz_common", SysConst.sys_source+ SysConst.sys_under_line + ffr.getBiz_common_type());
		paramJson.put("description", ffr.getBizCommon());
		paramJson.put("business_type", String.valueOf(ffr.getBusiness_type()));
		paramJson.put("sys_source", SysConst.sys_source);
		paramJson.put("uid_type", ffr.getUidType());
		paramJson.put("order_id", ffr.getOrderSn());
		return paramJson;

	}
	
	/**
   	 * 消费账户余额，余额减少
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月28日 
   	 *
   	 * @param bmr
   	 * @return
   	 */
	public Map<String,String> consumeBalanceMoney(BalanceMoneyRequest bmr) throws Exception{
		LogUtil.info(LOGGER,"consumeBalanceMoney params:{}",JsonEntityTransform.Object2Json(bmr));
		JSONObject jsonParam = makeConsumeBalanceDataJson(bmr);
		String resultContent = "";
		String url = ACCOUNT_URL + CONSUME_BALANCE_MONEY +"?encryption="+URLEncoder.encode(CryptAES.AES_Encrypt(ACCOUNT_KEY, jsonParam.toString()),ENCODING);
		resultContent = excuteAndGetResult(url);
		LogUtil.info(LOGGER,"consumeBalanceMoney result:{}",resultContent);
		return parseReponse(resultContent);
		
	}
	
	
	/**
   	 * 封装消费账户余额，余额减少接口参数
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月25日 
   	 *
   	 * @param bmr
   	 * @return
   	 */
	private JSONObject makeConsumeBalanceDataJson(BalanceMoneyRequest bmr){
		JSONObject paramJson = new JSONObject();
		paramJson.put("unique_num", bmr.getUnique_num()); 
		paramJson.put("uid", bmr.getUid()); 
		paramJson.put("description", bmr.getDescription());
		paramJson.put("total_fee", bmr.getTotalFee());
		paramJson.put("city_code", SysConst.Common.company_ziroom_minsu);
		paramJson.put("biz_common", SysConst.sys_source+ SysConst.sys_under_line + SysConst.account_consume_balance);
		paramJson.put("business_type", SysConst.account_xf004);
		paramJson.put("sys_source", SysConst.sys_source);
		paramJson.put("uid_type", bmr.getUidType());
		
		return paramJson;

	}
	
	
	
	
	
	/**
   	 * 处理 接口返回值
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月20日 
   	 *
   	 * @param resultContent
   	 * @return
   	 */
	private Map<String,String> parseReponse(String resultContent) throws Exception {
		Map<String,String> resMap = new HashMap<String,String>(7);
		if (resultContent != null) {
			JSONObject resJson = JSONObject.parseObject(resultContent);
			JSONObject metaJson = resJson.getJSONObject("meta");
			resMap.put("result", resJson.getString("result"));
			
			if(!Check.NuNObj(metaJson)){
				resMap.put("api", metaJson.getString("api"));
				resMap.put("params", metaJson.getString("params"));
				resMap.put("status", metaJson.getString("status"));
				resMap.put("error_string", metaJson.getString("error_string"));
			}
				
		} else {
			resMap.put("status", "FAILED");
			LogUtil.error(LOGGER, "parseReponse接口没有返回信息{}", resultContent);
		}
		return resMap;
	}
	

	
	
	/**
   	 * 发送请求
   	 *
   	 *
   	 * @author liyingjie
   	 * @created 2016年4月8日 
   	 *
   	 * @param url http请求路径 及参数
   	 * @return
   	 */
	private String excuteAndGetResult(String url){
		String resJson = "";
		try {
			resJson = CloseableHttpUtil.sendFormPost(url, null);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "excuteAndGetResults接口没有返回信息{}", resJson);
		}
		return resJson;
	}
	
	
	
}
