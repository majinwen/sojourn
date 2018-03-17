/**
 * @FileName: ActivityController.java
 * @Package com.ziroom.minsu.activity.controller
 * 
 * @author bushujie
 * @created 2016年5月13日 下午3:04:55
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.activity.controller;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.dto.EvaluateShareDto;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.dianping.cat.Cat;
import com.ziroom.minsu.activity.common.utils.PinyinTool;
import com.ziroom.minsu.activity.common.utils.PropertiesUtil;
import com.ziroom.minsu.activity.common.utils.WxShareVo;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.entity.cms.ActivityGiftEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.cms.api.inner.ActivityRecordService;
import com.ziroom.minsu.services.cms.api.inner.ActivityService;
import com.ziroom.minsu.services.cms.dto.BindActivityRequest;
import com.ziroom.minsu.services.cms.dto.BindCouponRequest;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.randomUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.dto.CarouselTypeRequest;
import com.ziroom.minsu.services.search.dto.HouseListRequset;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.valenum.cms.GiftTypeEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

/**
 * <p>活动相关控制层</p>
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
@RequestMapping("/topics")
@Controller
public class TopicsController {
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(TopicsController.class);

	private static final RestTemplate restTemplate = new RestTemplate();

	private static final Map<String, Object> TOKEN_MAP = new ConcurrentHashMap<String, Object>();

	private static final Map<String, Object> TICKET_MAP = new ConcurrentHashMap<String, Object>();

	private static final Map<String, Object> HOLDER_MAP = new ConcurrentHashMap<String, Object>();

	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;
	
	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name = "cms.activityRecordService")
	private ActivityRecordService activityRecordService;
	
	@Resource(name = "house.houseManageService")
	private HouseManageService houseManageService;
	
	@Resource(name="cms.actCouponService")
	private ActCouponService actCouponService;
	
	@Resource(name = "cms.activityService")
	private ActivityService activityService;
	
	@Resource(name = "basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

	@Resource(name = "evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;

	@Resource(name = "order.orderCommonService")
	private OrderCommonService orderCommonService;
	
	@Autowired
	private RedisOperations redisOperations;
	
	@Resource(name="search.searchServiceApi")
	private SearchService searchService;

	@Resource(name="propertyConfigurer")
	private PropertiesUtil propertiesUtil;

	@Value("#{'${HOUSE_SHARE_URL}'.trim()}")
	private String shareUrl;

	@Value("#{'${APPID}'.trim()}")
	private String appId;
	
	@Value("#{'${ACT_LANDLORD}'.trim()}")
	private String landLordGroup;
	
	@Value("#{'${ACT_TEANT}'.trim()}")
	private String tenantGroup;

	@Value("#{'${APPSECRET}'.trim()}")
	private String appSecret;

	@Value("#{'${NONCESTR}'.trim()}")
	private String nonceStr;

	@Value("#{'${GET_ACCESS_TOKEN_URL}'.trim()}")
	private String accessTokenUrl;

	@Value("#{'${GET_API_TICKET_URL}'.trim()}")
	private String apiTicketUrl;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${pic_size}'.trim()}")
	private String picSize;

	/**
	 * 
	 * 轮播图片数据接口 
	 * 
	 * 使用场景：
	 * 1、app-发现精彩
	 *
	 * 访问实例：${项目名称}/minsu-web-activity/topics/carouselPicData?paramJson=${页面标示}
	 *
	 * http://10.30.26.26:8080/minsu-web-activity/topics/carouselPicData?paramJson=page_siheyuan_param
	 * 
	 * @author bushujie
	 * @created 2016年5月13日 下午5:18:47
	 *
	 * @param request
	 * @param paramJson
	 * @throws SOAParseException 
	 */
	@RequestMapping("carouselPicData")
	public String carouselPicData(HttpServletRequest request,String paramJson) throws SOAParseException{
		if ("page_siheyuan_param".equals(paramJson)) {
			Cat.logMetricForCount("发现精彩-北京四合院");
		} else if ("page_fazujie_param".equals(paramJson)) {
			Cat.logMetricForCount("发现精彩-上海法租界");
		}
		paramJson=propertiesUtil.get(paramJson);
		CarouselTypeRequest carouselTypeDto=JsonEntityTransform.json2Object(paramJson, CarouselTypeRequest.class);
		if(!"page_landlordStory_param".equals(paramJson)){
			String resultJson=searchService.getHouseListByList(request.getAttribute("picSize").toString(),JsonEntityTransform.Object2Json(carouselTypeDto.getCarouselDtoList()));
			List<HouseInfoEntity> houseList=SOAResParseUtil.getListValueFromDataByKey(resultJson,"list",HouseInfoEntity.class);
			request.setAttribute("list", houseList);
			request.setAttribute("shareFlag", request.getParameter("shareFlag"));
			LogUtil.info(LOGGER, "焕心之旅专题:huanxinzhilv");
		}
		request.setAttribute("shareUrl", shareUrl);
		//微信二次分享
		try {
			this.setAttributes(request);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "微信二次分享error:{}", e);
		}
		return carouselTypeDto.getCarouseType();
	}

	/**
	 * 
	 * 中秋阿波罗活动
	 * 访问实例http://10.30.26.26:8080/minsu-web-activity/topics/appolloMoon?paramJson=page_appollo_param
	 *
	 * @author lunan
	 * @created 2016年8月30日 上午9:22:22
	 *
	 * @param request
	 * @param paramJson
	 * @return
	 * @throws SOAParseException
	 */
	@RequestMapping("appolloMoon")
	public String appolloMoon(HttpServletRequest request,String paramJson) throws SOAParseException{

		//阿波罗活动
		//		String[] appollo = {"beijing","shanghai","guangz","shenzhen","suzhou","chengdu","qingdao","xian"};
		paramJson=propertiesUtil.get(paramJson);
		CarouselTypeRequest carouselTypeDto=JsonEntityTransform.json2Object(paramJson, CarouselTypeRequest.class);
		HouseListRequset hlr = new HouseListRequset();
		hlr.setHouseList(carouselTypeDto.getCarouselDtoList());
		try {
			hlr.setStartTime(DateUtil.parseDate("2016-09-14", "yyyy-MM-dd"));
			hlr.setEndTime(DateUtil.parseDate("2016-09-17", "yyyy-MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		hlr.setPicSize(request.getAttribute("picSize").toString());
		String resultJson=searchService.getHouseListByListInfo(JsonEntityTransform.Object2Json(hlr));

		List<HouseInfoEntity> houseList=SOAResParseUtil.getListValueFromDataByKey(resultJson,"list",HouseInfoEntity.class);

		if(Check.NuNCollection(houseList)) houseList = new ArrayList<HouseInfoEntity>();
		LogUtil.info(LOGGER, "中秋活动返回房源数量num={}", houseList.size());
		request.setAttribute("list", houseList);
		request.setAttribute("shareUrl", shareUrl);
		request.setAttribute("shareFlag", request.getParameter("shareFlag"));
		LogUtil.info(LOGGER, "中秋活动:打点标识zhongqiu");
		//微信二次分享
		try {
			this.setAttributes(request);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "微信二次分享error:{}", e);
		}
		return carouselTypeDto.getCarouseType();
	}
	
	/**
	 * 
	 * 国庆活动分享
	 * 访问实例http://10.30.26.26:8080/minsu-web-activity/topics/nationalDay_dscj
	 * 访问实例http://10.30.26.26:8080/minsu-web-activity/topics/shudefang?paramJson=page_shudefang_param
	 *
	 * @author lunan
	 * @created 2016年9月8日 下午6:46:43
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException
	 */
	@RequestMapping("nationalDay_dscj")
	public String nationalDayDscj(HttpServletRequest request) throws SOAParseException{
		//微信二次分享
		try {
			this.setAttributes(request);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "微信二次分享error:{}", e);
		}
		return "topics/wonderful/dscj";
	}
	
	@RequestMapping("shudefang")
	public String shudefang(HttpServletRequest request,String paramJson) throws SOAParseException{
		paramJson=propertiesUtil.get(paramJson);
		CarouselTypeRequest carouselTypeDto=JsonEntityTransform.json2Object(paramJson, CarouselTypeRequest.class);
		String resultJson=searchService.getHouseListByList(request.getAttribute("picSize").toString(),JsonEntityTransform.Object2Json(carouselTypeDto.getCarouselDtoList()));
		List<HouseInfoEntity> houseList=SOAResParseUtil.getListValueFromDataByKey(resultJson,"list",HouseInfoEntity.class);
		request.setAttribute("list", houseList);
		request.setAttribute("shareUrl", shareUrl);
		request.setAttribute("shareFlag", request.getParameter("shareFlag"));
		LogUtil.info(LOGGER, "树德坊:shudefang");
		//微信二次分享
		try {
			this.setAttributes(request);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "微信二次分享error:{}", e);
		}
		return carouselTypeDto.getCarouseType();
	}

	/**
	 * 
	 * 设置微信二次分享参数
	 *
	 * @author liujun
	 * @created 2016年9月1日
	 *
	 * @param request
	 */
	private void setAttributes(HttpServletRequest request) {
		this.getAccessToken();
		this.getTicket();
		this.sha1(request);
	}



	/**
	 * sha1加密
	 *
	 * @author liujun
	 * @param request 
	 * @created 2016年9月1日
	 *
	 */
	private void sha1(HttpServletRequest request) {
		if(!HOLDER_MAP.isEmpty() && HOLDER_MAP.size() == 3){
			StringBuilder sb = new StringBuilder(200);
			String jsApiTicket = (String) HOLDER_MAP.get("jsapi_ticket");
			sb.append("jsapi_ticket=").append(jsApiTicket).append("&");
			String noncestr = (String) HOLDER_MAP.get("noncestr");
			sb.append("noncestr=").append(noncestr).append("&");
			long timestamp = (long) HOLDER_MAP.get("timestamp");
			sb.append("timestamp=").append(timestamp).append("&");

			StringBuilder requestUrl = new StringBuilder(200);
			String urlHttps = request.getRequestURL().toString();
			if(!urlHttps.contains("https")){
				urlHttps = urlHttps.replace("http", "https");
			}
			requestUrl.append(urlHttps);
			LogUtil.info(LOGGER, "url={}",urlHttps);
			requestUrl.append("?");
			requestUrl.append(request.getQueryString());
			String url = requestUrl.toString();
			if(url.indexOf("#") > -1){
				url = url.substring(0, url.indexOf("#"));
			}

			sb.append("url=").append(url);
			String signature = DigestUtils.sha1Hex(sb.toString());
			request.setAttribute("signature", signature);
			request.setAttribute("appId", appId);
			request.setAttribute("nonceStr", nonceStr);
			request.setAttribute("timestamp", HOLDER_MAP.get("timestamp"));
		}

	}

	/**
	 * 获取微信 ticket
	 *
	 * @author liujun
	 * @return 
	 * @created 2016年9月1日
	 *
	 */
	private void getTicket() {
		if(!isTicketValid()){
			TICKET_MAP.clear();
			String accessToken = (String) TOKEN_MAP.get("accessToken");
			if(Check.NuNStr(accessToken)){
				LogUtil.info(LOGGER, "accessToken is null or blank", JsonEntityTransform.Object2Json(TOKEN_MAP));
				return;
			}

			long currentMillis = System.currentTimeMillis();
			LogUtil.info(LOGGER, "获取微信ticket，标识weixin={}", currentMillis);
			//请求微信获得签名参数，超时重试
			String ticketJson = "";
			for(int i=0;i<3;i++){
				try{
					ticketJson = restTemplate.getForObject(apiTicketUrl, String.class, accessToken);
					if(!Check.NuNObj(ticketJson)){
						break;
					}
				}catch (RestClientException e){
					LogUtil.info(LOGGER,"第"+i+"次请求失败，重试......");
					ticketJson = restTemplate.getForObject(apiTicketUrl, String.class, accessToken);
					if(!Check.NuNObj(ticketJson)){
						break;
					}
				}
			}
			JSONObject ticketObject = SOAResParseUtil.getJsonObj(ticketJson);
			if (Check.NuNObj(ticketObject) || !ticketObject.containsKey("errcode")) {
				LogUtil.info(LOGGER, "获取微信ticket失败");
				return;
			}

			int errcode = ticketObject.getIntValue("errcode");
			if (Check.NuNObj(errcode) || errcode != 0) {
				LogUtil.info(LOGGER, "获取微信ticket失败");
				return;
			}

			String ticket = ticketObject.getString("ticket");
			int expiresIn = ticketObject.getIntValue("expires_in");
			if (!Check.NuNStr(ticket) && !Check.NuNObj(expiresIn)) {
				long expireTime = currentMillis +(expiresIn*1000 >> 1) + (expiresIn*1000 >> 2);;
				LogUtil.info(LOGGER, "ticketExpireTime失效时间:{}", expireTime);
				TICKET_MAP.put("ticketExpireTime", expireTime);
				TICKET_MAP.put("ticket", ticket);

				HOLDER_MAP.put("jsapi_ticket", ticket);
				HOLDER_MAP.put("noncestr", nonceStr);
				HOLDER_MAP.put("timestamp", currentMillis);
			}
		}
	}

	/**
	 * 判断ticket是否有效
	 *
	 * @author liujun
	 * @created 2016年9月1日
	 *
	 * @return
	 */
	private boolean isTicketValid() {
		if(TICKET_MAP.isEmpty() || !TICKET_MAP.containsKey("ticket") || !TICKET_MAP.containsKey("ticketExpireTime")){
			return false;
		}

		Object ticket = TICKET_MAP.get("ticket");
		Object ticketExpireTime = TICKET_MAP.get("ticketExpireTime");
		if(Check.NuNObj(ticket) || Check.NuNObj(ticketExpireTime)){
			return false;
		}

		if(!(ticket instanceof String) || !(ticketExpireTime instanceof Long)){
			return false;
		}

		if(System.currentTimeMillis() > (long)ticketExpireTime){
			return false;
		}

		return true;
	}

	/**
	 * 获取微信access_token
	 * {"expires_in":7200,"access_token":"j2_sn6U2lAV8uqYw4SUhN7w8t8S6i-54bfIbmWZhiajdtZWsjLut0KR6X3VA2biYI9uXTjIeqvzZmjMkzW7mNcQnbMh2SemP3KDNqODTzikTAAaAAAKRR"}
	 * @author liujun
	 * @created 2016年9月1日
	 *
	 */
	private void getAccessToken() {
		if(!isTokenValid()){
			TOKEN_MAP.clear();
			long currentMillis = System.currentTimeMillis();
			LogUtil.info(LOGGER, "获取微信token，标识weixin={}", currentMillis);
			String accessTokenJson = restTemplate.getForObject(accessTokenUrl, String.class, appId, appSecret);
			JSONObject accessTokenObject = SOAResParseUtil.getJsonObj(accessTokenJson);
			if (Check.NuNObj(accessTokenObject) || !accessTokenObject.containsKey("access_token") || !accessTokenObject.containsKey("expires_in")) {
				LogUtil.info(LOGGER, "获取微信access_token失败");
				return;
			}

			String accessToken = accessTokenObject.getString("access_token");
			int expiresIn = accessTokenObject.getIntValue("expires_in");
			if (Check.NuNStr(accessToken) || Check.NuNObj(expiresIn)) {
				LogUtil.info(LOGGER, "获取微信access_token为空");
				return;
			}

			long expireTime = currentMillis + (expiresIn*1000 >> 1) + (expiresIn*1000 >> 2);
			LogUtil.info(LOGGER, "tokenExpireTime失效时间:{}", expireTime);
			TOKEN_MAP.put("tokenExpireTime", expireTime);
			TOKEN_MAP.put("accessToken", accessToken);
		}
	}

	/**
	 * 判断access_token是否有效
	 *
	 * @author liujun
	 * @return 
	 * @created 2016年9月1日
	 *
	 */
	private boolean isTokenValid() {
		if(TOKEN_MAP.isEmpty() || !TOKEN_MAP.containsKey("accessToken") || !TOKEN_MAP.containsKey("tokenExpireTime")){
			return false;
		}

		Object accessToken = TOKEN_MAP.get("accessToken");
		Object tokenExpireTime = TOKEN_MAP.get("tokenExpireTime");
		if(Check.NuNObj(accessToken) || Check.NuNObj(tokenExpireTime)){
			return false;
		}

		if(!(accessToken instanceof String) || !(tokenExpireTime instanceof Long)){
			return false;
		}

		if(System.currentTimeMillis() > (long)tokenExpireTime){
			return false;
		}

		return true;
	}

	@RequestMapping("wxShare")
	@ResponseBody
	public void wxShare(HttpServletRequest request,HttpServletResponse response){
		String currentUrl = request.getParameter("currentUrl");
		DataTransferObject dto = null;
		try {
			synchronized (request) {
				this.getAccessToken();
				this.getTicket();
			}
		    dto = new DataTransferObject();
			if(!HOLDER_MAP.isEmpty() && HOLDER_MAP.size() == 3){
				StringBuilder sb = new StringBuilder(200);
				String jsApiTicket = (String) HOLDER_MAP.get("jsapi_ticket");
				sb.append("jsapi_ticket=").append(jsApiTicket).append("&");
				String noncestr = (String) HOLDER_MAP.get("noncestr");
				sb.append("noncestr=").append(noncestr).append("&");
				long timestamp = (long) HOLDER_MAP.get("timestamp");
				sb.append("timestamp=").append(timestamp).append("&");
				
				StringBuilder requestUrl = new StringBuilder(200);
				requestUrl.append(currentUrl);
				String url = requestUrl.toString();
				if(url.indexOf("#") > -1){
					url = url.substring(0, url.indexOf("#"));
				}
				
				sb.append("url=").append(url);
				String signature = DigestUtils.sha1Hex(sb.toString());
				WxShareVo wShareVo = new WxShareVo();
				wShareVo.setAppId(appId);
				wShareVo.setNonceStr(nonceStr);
				wShareVo.setTimestamp(HOLDER_MAP.get("timestamp"));
				wShareVo.setSignature(signature);
				dto.putValue("wShareVo", wShareVo);
			}
			response.setContentType("text/plain");
			String callBackName = request.getParameter("callback");
			
			response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
		} catch (IOException e) {
			LogUtil.error(LOGGER, "json返回error:{}", e);
		}catch (Exception e1) {
			LogUtil.error(LOGGER, "微信二次分享error:{}", e1);
		}
	}
	
	/**
	 * 
	 * 获得开通城市
	 * @author lunan
	 * @created 2016年9月30日 下午2:36:58
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws SOAParseException 
	 * @throws IOException 
	 */
	@RequestMapping("openCity")
	@ResponseBody
	public void getOpenCity(HttpServletRequest request,HttpServletResponse response,String type) throws SOAParseException, IOException{
		String openCity = confCityService.getOpenCity();
		List<Map> list = SOAResParseUtil.getListValueFromDataByKey(openCity, "list", Map.class);
		PinyinTool tool = new PinyinTool();
		for (Map map : list) {
			try {
				Integer length = String.valueOf(map.get("name")).length()-1;
				map.put("pinyin",tool.toPinYin(String.valueOf(map.get("name")).substring(0,length), "", PinyinTool.Type.FIRSTUPPER));
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				LogUtil.error(LOGGER," pinyin转化error:{},e:{}",e);
			}
		}
		DataTransferObject dto = new DataTransferObject();
		response.setContentType("text/plain");
		String callBackName = request.getParameter("callback");
		dto.putValue("city", list);
		response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
	}
	
	/**
	 * 
	 * 发送验证码
	 *
	 * @author lunan
	 * @created 2016年8月26日 下午4:16:43
	 *
	 * @return
	 * @throws SOAParseException 
	 * @throws IOException 
	 */
	@RequestMapping("getMobileCodeByToken")
	public @ResponseBody void getMobileCodeByToken(HttpServletResponse response,String mobile,String groupSn,HttpServletRequest request,Integer type) throws SOAParseException, IOException{
		DataTransferObject dto = new DataTransferObject();
		response.setContentType("text/plain");
		String callBackName = request.getParameter("callback");
		if(Check.NuNStr(mobile)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("10");
			response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
			return;
		}
		if(type == 0){
			//判断房东是否参加过活动
			BindActivityRequest activityRecord = new BindActivityRequest();
			activityRecord.setGroupSn(groupSn);
			activityRecord.setMobile(mobile);
			String mcrJson = JsonEntityTransform.Object2Json(activityRecord);
			String resultJson = activityRecordService.checkActivity4Record(mcrJson);
			LogUtil.info(LOGGER, "【房东校验是否参加过活动的返回值】:{}", resultJson);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() == 1){
				//参加过
				response.getWriter().write(callBackName + "("+resultJson+")");
				return;
			}
		}else if(type == 1){
			//判断房客是否参加过活动
			MobileCouponRequest mcr = new MobileCouponRequest();
			mcr.setGroupSn(groupSn);
			mcr.setMobile(mobile);
			String result = actCouponService.checkActivityByMobile(JsonEntityTransform.Object2Json(mcr));
			LogUtil.info(LOGGER, "【房客校验是否参加过活动的返回值】:{}", result);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
			if(resultDto.getCode() == 1){
				//参加过
				response.getWriter().write(callBackName + "("+result+")");
				return;
			}
		}
		//获取验证码
        String vcode = randomUtil.getNumrOrChar(6, "num");
        try {
			if(!Check.NuNStr(vcode)){
				String key = RedisKeyConst.getMobileCodeKey(mobile,groupSn);
				SmsRequest smsRequest = new SmsRequest();
				smsRequest.setMobile(mobile);
				smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.MOBILE_CODE.getCode()));
				Map<String, String> paMap = new HashMap<String, String>();
				paMap.put("{1}", vcode);
				paMap.put("{2}", String.valueOf(RedisKeyConst.MOBILE_CODE_CACHE_TIME/60));
				smsRequest.setParamsMap(paMap);
				dto = JsonEntityTransform.json2DataTransferObject( this.smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest)));
				
				if(dto.getCode()== DataTransferObject.SUCCESS){
					
					try {
						LogUtil.info(LOGGER, "【当前手机验证码为:vcode={}】", vcode);
						LogUtil.info(LOGGER, "【当前手机号为:vcode={}】", mobile);
						this.redisOperations.setex(key, RedisKeyConst.MOBILE_CODE_CACHE_TIME, vcode);
					} catch (Exception e) {
						LogUtil.error(LOGGER, "redis错误e={}",e);
					}
					response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				}
			}
		} catch (Exception e) {
            LogUtil.error(LOGGER," phone:{}，vcode:{},e:{}",mobile,vcode,e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("获取验证码异常");
			response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
			return;
		}
	}
	
	/**
	 * 
	 * 校验验证码
	 *
	 * @author lunan
	 * @created 2016年8月26日 下午6:25:44
	 *
	 * @param mobile
	 * @param vcode
	 * @param actSn
	 * @return
	 * @throws SOAParseException 
	 * @throws IOException 
	 */
	@RequestMapping("verifiVcodeByToken")
	public @ResponseBody void verifiVcodeByToken(HttpServletRequest request,String mobile,String vcode,String groupSn,Integer type,HttpServletResponse response) throws SOAParseException, IOException{
		response.setContentType("text/plain");
		String callBackName = request.getParameter("callback");
		DataTransferObject dto = new DataTransferObject();
		//发短信
		SmsRequest smsRequest = new SmsRequest();
		smsRequest.setMobile(mobile);
		Map<String, String> paMap = new HashMap<String, String>();
		
		if(Check.NuNStr(mobile)||Check.NuNStr(vcode)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("10");
			response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
			return;
		}
		String currVcode = null;
		try {
			 currVcode = this.redisOperations.get(RedisKeyConst.getMobileCodeKey(mobile,groupSn));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误e={}",e);
		}
		if(Check.NuNStr(currVcode)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("9");
			response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
			return;
		}
		
		if(!currVcode.equals(vcode)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("8");
			response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
			return;
		}
		//再次校验是否领取过，防止刷票
		if(type == 0){
			//判断房东是否参加过活动
			BindActivityRequest activityRecord = new BindActivityRequest();
			activityRecord.setGroupSn(groupSn);
			activityRecord.setMobile(mobile);
			String mcrJson = JsonEntityTransform.Object2Json(activityRecord);
			String resultJson = activityRecordService.checkActivity4Record(mcrJson);
			LogUtil.info(LOGGER, "【房东校验是否参加过活动的返回值】:{}", resultJson);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() == 1){
				//参加过
				response.getWriter().write(callBackName + "("+resultJson+")");
				return;
			}
		}else if(type == 1){
			//判断房客是否参加过活动
			MobileCouponRequest mcr = new MobileCouponRequest();
			mcr.setGroupSn(groupSn);
			mcr.setMobile(mobile);
			String result = actCouponService.checkActivityByMobile(JsonEntityTransform.Object2Json(mcr));
			LogUtil.info(LOGGER, "【房客校验是否参加过活动的返回值】:{}", result);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
			if(resultDto.getCode() == 1){
				//参加过
				response.getWriter().write(callBackName + "("+result+")");
				return;
			}
		}
		
		//此处验证码正确，调用领取优惠券接口
		if(type == 0){
			//判断如果是房东的话，查询出手机号对应的uid，request给页面
			String resultJson = customerInfoService.getCustomerListByMobile(mobile);
			List<CustomerBaseMsgEntity> customerList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "customerList", CustomerBaseMsgEntity.class);
			if(Check.NuNCollection(customerList)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("1");
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
			HouseBaseMsgEntity houseBaseMsg = null;
			String uid = "";
			for (CustomerBaseMsgEntity customer : customerList) {
				houseBaseMsg = SOAResParseUtil.getValueFromDataByKey( houseManageService.searchHouseBaseMsgByLandlorduid(customer.getUid()), "obj", HouseBaseMsgEntity.class);
				if(!Check.NuNObj(houseBaseMsg)){
					uid = customer.getUid();
					break;
				}
			}
			if(Check.NuNObj(houseBaseMsg)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("11");
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}else{
				//房东，uid领取免佣金或者礼品
				String roleInfoByUid = customerInfoService.getCustomerRoleInfoByUid(uid);
				List<CustomerRoleEntity> roles = SOAResParseUtil.getListValueFromDataByKey(roleInfoByUid, "roles", CustomerRoleEntity.class);
				BindActivityRequest bcRequest = new BindActivityRequest();
				bcRequest.setGroupSn(groupSn);
				bcRequest.setUid(uid);
				bcRequest.setMobile(mobile);
				bcRequest.setRoles(roles);
				String paramJson = JsonEntityTransform.Object2Json(bcRequest);
				LogUtil.info(LOGGER, "【房东领取参数】:{}", paramJson);
				//领取活动
				String record = activityRecordService.exchangeActivity4Record(paramJson);
				LogUtil.info(LOGGER, "【房东身份领取的结果】:{}", record);
				DataTransferObject dtoJson = JsonEntityTransform.json2DataTransferObject(record);
				ActivityGiftEntity afiEntity = SOAResParseUtil.getValueFromDataByKey(record, "gift", ActivityGiftEntity.class);
				if(dtoJson.getCode() == 1){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("2");
					response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
					return;
				}else if(dtoJson.getCode() == 0){
					//领取成功，返回statTime endTime gift fid
					if(!Check.NuNObj(afiEntity) && afiEntity.getGiftType() == GiftTypeEnum.NO_LOAD.getCode()){
						smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.ACTIVITY_GIFT.getCode()));
						paMap.put("{3}", String.valueOf(dtoJson.getData().get("statTime")));
						paMap.put("{4}", String.valueOf(dtoJson.getData().get("endTime")));
						smsRequest.setParamsMap(paMap);
						dto = JsonEntityTransform.json2DataTransferObject( this.smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest)));
					}else if(!Check.NuNObj(afiEntity) && afiEntity.getGiftType() == GiftTypeEnum.GIFT_THING.getCode()){
						smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.ACTIVITY_YONGJ.getCode()));
						dto = JsonEntityTransform.json2DataTransferObject( this.smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest)));
					}
					response.getWriter().write(callBackName + "("+record+")");
					return;
				}
			}
		}else if(type == 1){
			//房客，手机号领取优惠券
			MobileCouponRequest mcRequest = new MobileCouponRequest();
			mcRequest.setGroupSn(groupSn);
			mcRequest.setMobile(mobile);
			mcRequest.setSourceType(1);
			String resultJson = JsonEntityTransform.Object2Json(mcRequest);
			LogUtil.info(LOGGER, "【房客身份领取优惠券参数】:{}", resultJson);
			String dtoJson = actCouponService.pullActivityByMobile(resultJson); 
			LogUtil.info(LOGGER, "【房客身份领取优惠券返回值】:{}", dtoJson);
			DataTransferObject record = JsonEntityTransform.json2DataTransferObject(dtoJson);
//			SOAResParseUtil.getValueFromDataByKey(dtoJson, arg1, arg2)
			if(record.getCode() == 1){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("2");
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}else{
				//领取成功
				response.getWriter().write(callBackName + "("+dtoJson+")");
				//发送通知短信
				smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.ACTIVITY_YHQ.getCode()));
				paMap.put("{5}", String.valueOf(record.getData().get("cut")));
				smsRequest.setParamsMap(paMap);
				dto = JsonEntityTransform.json2DataTransferObject( this.smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest)));
				//response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
		}else{
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误，请传递领取对象类别");
			response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
			return;
		}
	}
	
	/**
	 * 初始化页面，
	 *
	 * @author lunan
	 * @created 2016年10月8日 下午8:28:22
	 *
	 * @param request
	 * @param type 我是房东  或者  我是房客
	 * @throws SOAParseException 
	 * @throws IOException 
	 */
	@RequestMapping("initPage")
	public @ResponseBody void initPage(HttpServletRequest request,Integer type,String startTime,String endTime,HttpServletResponse response) throws SOAParseException, IOException{ 
		LogUtil.info(LOGGER, "【请求类型，开始时间，结束时间】:{}", type+"####"+startTime+"####"+endTime);
		response.setContentType("text/plain");
		String callBackName = request.getParameter("callback");
		DataTransferObject dto = new DataTransferObject();
		if(type == 2){
			//设置日期格式
			String currTime = DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
			long curr = DateUtil.formatDateToLong(currTime);
			long start = DateUtil.formatDateToLong(startTime);
			long end = DateUtil.formatDateToLong(endTime);
			if(curr < start){
				//如果当前活动在开始日期之前
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("30");
			}else if(curr > end){
				//如果当前活动在结束日期之后
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("31");
			}
			response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
			return;
		}else{
			String currTime = DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
			long curr = DateUtil.formatDateToLong(currTime);
			startTime = DateUtil.dateFormat(new Date(), "yyyy-MM-dd")+" "+startTime;
			long start = DateUtil.formatDateToLong(startTime);
			endTime = DateUtil.dateFormat(new Date(), "yyyy-MM-dd")+" "+endTime;
			long end = DateUtil.formatDateToLong(endTime);
			if(curr < start){
				//如果用户在非活动时间段中点进来
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("3");
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}else if(curr > end){
				//如果用户在非活动时间段中点进来
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("3");
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
			
			if(type == 0){
				BindActivityRequest activityRecord = new BindActivityRequest();
				activityRecord.setGroupSn(landLordGroup);
				LogUtil.info(LOGGER, "【房东初始化参数】:{}", landLordGroup);
				String dtoJson = activityRecordService.checkActivity4Record(JsonEntityTransform.Object2Json(activityRecord));
				DataTransferObject landJson = JsonEntityTransform.json2DataTransferObject(dtoJson);
				landJson.putValue("groupSn", landLordGroup);
				//{"code":0,"msg":"","data":{"last":0}
				LogUtil.info(LOGGER, "【房东初始化返回值】:{}", dtoJson);
				response.getWriter().write(callBackName + "("+landJson.toJsonString()+")");
				return;
			}else if(type == 1){
				//房客校验
				MobileCouponRequest mcr = new MobileCouponRequest();
				mcr.setGroupSn(tenantGroup);
				LogUtil.info(LOGGER, "【房客初始化参数】:{}", tenantGroup);
				String result = actCouponService.checkActivityByMobile(JsonEntityTransform.Object2Json(mcr));
				DataTransferObject tenantJson = JsonEntityTransform.json2DataTransferObject(result);
				tenantJson.putValue("groupSn", tenantGroup);
				LogUtil.info(LOGGER, "【房客初始化返回值】:{}", tenantJson);
				response.getWriter().write(callBackName + "("+tenantJson.toJsonString()+")");
				return;
			}
		}
		
	}
	
	@RequestMapping("updateGift")
	public @ResponseBody void updateGift(HttpServletRequest request,String recordFid,HttpServletResponse response,String mobile,String name,String address) throws IOException{
		//领取礼品
		LogUtil.info(LOGGER, "【领取礼品参数】:{}", recordFid+"####"+mobile+"####"+name+"####"+address);
		String resultJson = activityRecordService.updateAddress(recordFid,name,address+mobile);
		LogUtil.info(LOGGER, "【领取礼品返回值】:{}", resultJson);
		response.setContentType("text/plain");
		String callBackName = request.getParameter("callback");
		response.getWriter().write(callBackName + "("+resultJson+")");
		
	}

	/**
	 * 评价分享页
	 *
	 * @author lunan
	 * @created 2017年2月10日
	 *
	 */
	@RequestMapping("evaluateShare")
	@ResponseBody
	public void evaluateShare(HttpServletRequest request,String orderSn,HttpServletResponse response){
		String callBackName = request.getParameter("callback");
		DataTransferObject dto = new DataTransferObject();
		try{
			dto = JsonEntityTransform.json2DataTransferObject(orderCommonService.getOrderInfoByOrderSn(orderSn));
			if(dto.getCode() == DataTransferObject.ERROR){
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
			OrderInfoVo oe = SOAResParseUtil.getValueFromDataByKey(dto.toJsonString(), "orderInfoVo", OrderInfoVo.class);
			if(Check.NuNObj(oe)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("非法订单号");
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
			//返回参数对象
			EvaluateShareDto shareDto = new EvaluateShareDto();
			//根据cityCode获取cityName
			String cityJson = confCityService.getCityNameByCode(oe.getCityCode());
			if(!Check.NuNObj(dto)){
				String cityName = SOAResParseUtil.getStrFromDataByKey(cityJson, "cityName");
				shareDto.setCityName(cityName);
			}
			//根据rentWay去判断使用哪个名字
			shareDto.setRentWay(oe.getRentWay());
			shareDto.setHouseName(oe.getHouseName());
			shareDto.setRoomName(oe.getRoomName());
			shareDto.setPicUrl(PicUtil.getFullPic(picBaseAddrMona, oe.getPicUrl(), picSize)); 
			
			//查询房客评价实体
			EvaluateRequest evaluateRequest  = new EvaluateRequest();
			evaluateRequest.setOrderSn(orderSn);
			evaluateRequest.setEvaUserType(UserTypeEnum.TENANT.getUserType());
			dto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaluateRequest)));
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "查询房客的已评价列表时，封装房客的评价信息出现异常dto={},信息为", dto.toJsonString(), dto.getMsg());
				dto.setErrCode(DataTransferObject.ERROR);
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
			Map<String, Object> map = dto.parseData("evaluateMap", new TypeReference<Map<String, Object>>() {});
			if(Check.NuNObj(map.get("tenantEvaluate"))){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("不存在的房客评价");
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
			//将评价相关放入shareDto
			shareDto.setTenantEvaluate(JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(map.get("tenantEvaluate")), TenantEvaluateEntity.class));
			dto.putValue("tenantEvaShare",shareDto);
			response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
		}catch (Exception e){
			LogUtil.error(LOGGER,"e:{}",e);
			dto.setErrCode(DataTransferObject.ERROR);
		}
	}

	/**
	 * 
	 * 推荐页访问
	 * 
	 * 使用场景：
	 * 1、app-民宿推荐
	 * 
	 *访问实例：${项目名称}/topics/recommendStaticPage?pageName=${页面路径}&${房源参数 }
	 *
	 * http://10.30.26.26:8080/minsu-web-activity/topics/recommendStaticPage?pageName=topics/recommend/recommend1&fid=8a90a2d4549ac7990154a577327802b0&rentWay=0
	 * 
	 * @author bushujie
	 * @created 2016年5月14日 下午3:34:30
	 *
	 * @param pageName
	 * @return
	 */
	@RequestMapping("recommendStaticPage")
	public String recommendStaticPage(String pageName,HttpServletRequest request){
		request.setAttribute("fid", request.getParameter("fid"));
		request.setAttribute("rentWay", request.getParameter("rentWay"));
		request.setAttribute("shareUrl", shareUrl);
		request.setAttribute("shareFlag", request.getParameter("shareFlag"));
		return pageName;
	}
}
