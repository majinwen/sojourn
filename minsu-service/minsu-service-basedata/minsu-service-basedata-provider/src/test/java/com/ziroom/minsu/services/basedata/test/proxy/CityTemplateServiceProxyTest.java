package com.ziroom.minsu.services.basedata.test.proxy;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.services.basedata.dto.ConfTagRequest;
import com.ziroom.minsu.services.basedata.dto.ZrpPayRequest;
import com.ziroom.minsu.services.basedata.entity.entityenum.ServiceLineEnum;
import com.ziroom.minsu.services.basedata.proxy.ConfTagServiceProxy;

import com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum;
import com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum002;
import com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum004;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.ziroom.minsu.services.basedata.proxy.CityTemplateProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.valenum.houseaudit.HouseAuditEnum005;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
//import com.ziroom.minsu.valenum.productrules.ProductRulesEnum007Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum0020;

/**
 * 
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class CityTemplateServiceProxyTest extends BaseTest {
	
	@Resource(name="basedata.cityTemplateProxy")
	private CityTemplateProxy cityTemplateProxy;

	@Resource(name="basedata.confTagServiceProxy")
	private ConfTagServiceProxy confTagServiceProxy;

	@Autowired
	private RedisOperations redisOperations;

	@Test
	public void confTagTest(){
		ConfTagRequest params = new ConfTagRequest();
		params.setTagType(1);
		String resultJson = confTagServiceProxy.findByConfTagRequestList(JsonEntityTransform.Object2Json(params));
		System.err.println(resultJson);
	}

	@Test
	public void getSelectSubDicTest(){
		String resultJson = cityTemplateProxy.getSelectSubDic(null, ProductRulesEnum.ProductRulesEnum007.getValue());
		System.err.print(resultJson);
	}
	
	@Test
	public void getSelectEnumTest() {
		String resultJson = cityTemplateProxy.getSelectEnum(null,
				ProductRulesEnum.ProductRulesEnum005.getValue());
		System.err.print(resultJson);
	}
	
	@Test
	public void getTextValueTest() throws SOAParseException{

		String timeStrJson = cityTemplateProxy.getTextValue(null,ProductRulesEnum.ProductRulesEnum005.getValue());
		System.out.println(timeStrJson);

		Integer longTermLimit = SOAResParseUtil.getValueFromDataByKey(timeStrJson, "textValue", Integer.class);
		
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);

	    System.out.println(longTermLimit);
	}
//
//	@Test
//	public void getEffectiveSelectEnum() {
//		String minPixelJson = cityTemplateProxy.getEffectiveSelectEnum(null,
//				ProductRulesEnum007Enum.ProductRulesEnum007001.getValue());
//		System.out.println(minPixelJson);
//	}
	
	@Test
	public void removeRedisKey() {
//		String keyString="minsu:redis:conf:effective_ProductRulesEnum0017001";
		String key = RedisKeyConst.getConfigKey(null, "CityRulesEnum002");
        redisOperations.del("common:redis:conf2ContractTradingEnum002001");
        System.out.println("result="+redisOperations.get(key));
		
	}
	
	
	
	@Test
	public void getPicValidParamsTest(){
		
		DataTransferObject dto  = JsonEntityTransform.json2DataTransferObject(this.cityTemplateProxy.getPicValidParams());
		
		Map<String, Integer> validMap = (Map<String, Integer>) dto.getData().get("validMap");
		
		System.out.println(validMap);
	}
	

	@Test
	public void updateDicItemListTest() {
		String resultJson = cityTemplateProxy.updateDicItemList("[{\"dicCode\":\"ProductRulesEnum001\",\"fid\":\"0000000053dafc260153db36de340011\",\"itemIndex\":\"1\"},{\"dicCode\":\"ProductRulesEnum001\",\"fid\":\"0000000053dafc260153db377b400012\",\"itemIndex\":\"2\"},{\"dicCode\":\"ProductRulesEnum001\",\"fid\":\"0000000053dafc260153db382cde0013\",\"itemIndex\":\"3\"},{\"dicCode\":\"ProductRulesEnum001\",\"fid\":\"00000000569bc9a501569bc9a6370000\",\"itemIndex\":\"5\"},{\"dicCode\":\"ProductRulesEnum001\",\"fid\":\"0000000056daf9440156daf9449f0000\",\"itemIndex\":\"4\"}]");
		System.err.print(resultJson);
	}

	@Test
	public void testlistZrpPayStyle() {
		ZrpPayRequest zrpPayRequest = new ZrpPayRequest();
		zrpPayRequest.setRentType(2);
		zrpPayRequest.setRentTime(6);
		String s = cityTemplateProxy.listZrpPayStyle(JsonEntityTransform.Object2Json(zrpPayRequest));
		System.err.println(s);
	}

	@Test
	public void testgetTextValueForCommon() {
        String textValueForCommon = cityTemplateProxy.getTextValueForCommon(ServiceLineEnum.ZRP.getCode(), ContractTradingEnum002.ContractTradingEnum002001.getValue());
        System.err.println(textValueForCommon);
	}


	@Test
	public void testlistTextValueForCommon() {
        String textValueForCommon = cityTemplateProxy.listTextValueForCommon(ServiceLineEnum.ZRP.getCode(), ContractTradingEnum002.ContractTradingEnum002001.getValue());
        System.err.println(textValueForCommon);
	}

	
}
