package com.ziroom.minsu.api.house.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.header.Header;
import com.ziroom.minsu.entity.conf.GuardAreaEntity;
import com.ziroom.minsu.entity.customer.CustomerLocationEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.entity.house.HousePriceWeekConfEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.GuardAreaService;
import com.ziroom.minsu.services.basedata.dto.GuardAreaRequest;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.common.entity.FieldSelectListVo;
import com.ziroom.minsu.services.common.entity.FieldSelectVo;
import com.ziroom.minsu.services.common.entity.FieldTextVo;
import com.ziroom.minsu.services.common.thread.pool.SynLocationThreadPool;
import com.ziroom.minsu.services.common.utils.*;
import com.ziroom.minsu.services.customer.api.inner.CustomerLocationService;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.valenum.common.WeekendPriceEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.common.YesOrNoOrFrozenEnum;
import com.ziroom.minsu.valenum.customer.LocationTypeEnum;
import com.ziroom.minsu.valenum.customer.SynStatusEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum021Enum;
import com.ziroom.minsu.valenum.traderules.*;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.*;

/**
 * <p>房源</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 16/10/9.
 * @version 1.0
 * @since 1.0
 */
@Service("api.houseService")
public class HouseService extends AbstractController{

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(HouseService.class);


	@Resource(name="customer.customerLocationService")
	private CustomerLocationService customerLocationService;

	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Autowired
	private RedisOperations redisOperations;


	@Value("#{'${BAIDU_API_IP_URL}'.trim()}")
	private String BAIDU_API_IP_URL;

	@Resource(name = "house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Resource(name = "house.houseManageService")
	private HouseManageService houseManageService;
	
	@Resource(name = "basedata.guardAreaService")
	private GuardAreaService guardAreaService;
	
	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;
 

	/**
	 * 保存当前用户的head信息
	 * @param uid
	 * @param header
	 */
	public void saveLocation(final String uid, final Header header, final String serverIp, final LocationTypeEnum locationTypeEnum,String serialNumber,Integer rentWay){
		saveLocation(uid,header,serverIp,locationTypeEnum,serialNumber,rentWay,false);
	}



	/**
	 * 保存当前用户的head信息
	 * @param uid
	 * @param header
	 */
	public void saveLocation(final String uid, final Header header, final String serverIp, final LocationTypeEnum locationTypeEnum,final String serialNumber,final Integer rentWay, final boolean fillAuto){

		if (Check.NuNObj(header)){
			return;
		}
		try{
			Thread task = new Thread(){
				@Override
				public void run() {
					CustomerLocationEntity customerLocationEntity = new CustomerLocationEntity();
					customerLocationEntity.setFid(UUIDGenerator.hexUUID());
					customerLocationEntity.setUid(uid);
					customerLocationEntity.setAppName(header.getAppName());
					customerLocationEntity.setChannelName(header.getChannelName());
					customerLocationEntity.setOsVersion(header.getOsVersion());
					customerLocationEntity.setImei(header.getImei());
					customerLocationEntity.setImsi(header.getImsi());
					customerLocationEntity.setSerialNumber(serialNumber);
					customerLocationEntity.setRentWay(rentWay);
					Long ip = null;
					String deviceIP = header.getDeviceIP();
					if (!Check.NuNStr(serverIp)){
						//当服务获取ip直接获取当前的服务ip
						deviceIP = serverIp;
					}

					if (!IpUtil.checkIp(deviceIP)){
						LogUtil.info(LOGGER,"当前的ip异常,location info uid:{},header:{},deviceIP:{}" , uid, JsonEntityTransform.Object2Json(header),deviceIP);
						return;
					}


					ip = com.ziroom.minsu.services.common.utils.IpUtil.Ip2Long(deviceIP);
					customerLocationEntity.setDeviceIp(ip);
					customerLocationEntity.setDeviceNo(header.getDeviceId());
					String locationCoordinate = header.getLocationCoordinate();
					Double longitude = null;
					Double latitude = null;
					if (Check.NuNStr(locationCoordinate)
							|| ",".equals(locationCoordinate)
							|| "0,0".equals(locationCoordinate)
							|| locationCoordinate.indexOf(",") < 0
							){
						//当前位置
					}else {
						String[] coordinate = locationCoordinate.split(",");
						DecimalFormat df=new DecimalFormat("#.000000");
						df.format(ValueUtil.getdoubleValue(coordinate[0]));
						longitude = ValueUtil.getdoubleValue(df.format(ValueUtil.getdoubleValue(coordinate[0])));
						latitude = ValueUtil.getdoubleValue(df.format(ValueUtil.getdoubleValue(coordinate[1])));
						if (longitude < 0 || longitude > 180){
							longitude = null;
						}
						if (latitude < 0 || latitude > 90){
							latitude = null;
						}
					}
					customerLocationEntity.setLatitude(latitude);
					customerLocationEntity.setLongitude(longitude);
					customerLocationEntity.setPhoneModel(header.getPhoneModel());
					customerLocationEntity.setVersionCode(ValueUtil.getStrValue(header.getVersionCode()));
					customerLocationEntity.setCityCode(header.getLocationCityCode());
					customerLocationEntity.setCityName(header.getLocationCityName());
					customerLocationEntity.setLocationType(locationTypeEnum.getCode());
					
					//搜索，IM一个小时保存一次
					if(Check.NuNObj(locationTypeEnum.getCode()) || (!Check.NuNObj(locationTypeEnum.getCode()) && (locationTypeEnum.getCode() == LocationTypeEnum.IM.getCode() || locationTypeEnum.getCode() == LocationTypeEnum.SEARCH.getCode()) ) ){
						if (checkLocatByRedis(customerLocationEntity)){
							LogUtil.info(LOGGER,"location info uid:{},header:{}" , uid, JsonEntityTransform.Object2Json(header));
							return;
						}
					}
					//LogUtil.info(LOGGER, "保存当前用户的head信息，fillAuto={},uid={},deviceNo={}", fillAuto,uid, customerLocationEntity.getDeviceNo());
					if (fillAuto){
						DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerLocationService.getCustomerLocation(JsonEntityTransform.Object2Json(customerLocationEntity))) ; 

						if(dto.getCode() == DataTransferObject.ERROR){
							LogUtil.info(LOGGER,"查询用户位置信息错误uid={},deviceNo={}，msg={}" , uid, customerLocationEntity.getDeviceNo(),dto.getMsg());
							return;
						}
						CustomerLocationEntity has = dto.parseData("result", new TypeReference<CustomerLocationEntity>() {
						});

						if (!Check.NuNObj(has)){
							//已经保存成功 将对象放入缓存中
							fill2cache(customerLocationEntity);
							return ;
						}
						//填充 城市经纬度 以及 城市code和名称
                        String getUrl = BAIDU_API_IP_URL + IpUtil.intToIP(customerLocationEntity.getDeviceIp());
                        //填充 城市经纬度 以及 城市code和名称
                        String Json = CloseableHttpUtil.sendGet(getUrl, null);
                        if (Check.NuNStr(Json) || Json.indexOf("html") != -1) {
                            LogUtil.info(LOGGER, "请求城市信息url={}", getUrl);
                            return;
                        }
                        Map<String,Object>  map = transLocationMap(Json);
						if (Check.NuNObj(map) || ValueUtil.getdoubleValue(map.get("longitude")) >180 || ValueUtil.getdoubleValue(map.get("latitude")) >90){
							//异常的经纬度
							LogUtil.info(LOGGER, "获取百度经纬度失败:ip:{}",IpUtil.intToIP(customerLocationEntity.getDeviceIp()));
						}else{
							customerLocationEntity.setCityName(ValueUtil.getStrValue(map.get("cityName")));
							customerLocationEntity.setCityCode(ValueUtil.getStrValue(map.get("cityCode")));
							customerLocationEntity.setLongitude(ValueUtil.getdoubleValue(map.get("longitude")));
							customerLocationEntity.setLatitude(ValueUtil.getdoubleValue(map.get("latitude")));
							customerLocationEntity.setSynStatus(SynStatusEnum.SUCCESS.getCode());
						}

						customerLocationService.saveUserLocationAndFillV1(JsonEntityTransform.Object2Json(customerLocationEntity));
					}else {
						customerLocationService.saveUserLocation(JsonEntityTransform.Object2Json(customerLocationEntity));
					}

				}
			};
			SynLocationThreadPool.execute(task);
		}catch(Exception e){
			LogUtil.error(LOGGER,"the Exception on save location uid:{},header:{},e:{}" , uid, JsonEntityTransform.Object2Json(header),e);
		}
	}

	/**
	 * 将当前的对象放入缓存
	 * @param customerLocationEntity
	 */
	private void fill2cache(CustomerLocationEntity customerLocationEntity){
		try {
			String key = getLocalKey(customerLocationEntity);
			redisOperations.setex(RedisKeyConst.getLocalKey(key).toString(), RedisKeyConst.LOCATION_TIME, JsonEntityTransform.Object2Json(customerLocationEntity));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}", e);
		}
	}



	/**
	 * 解析当前的定位
	 * @author afi
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Map<String,Object> transLocationMap(String json){
		if (Check.NuNStr(json)){
			return null;
		}
		Map<String,Object> map =(Map<String,Object>)JsonEntityTransform.json2Map(json);
		if (!ValueUtil.getStrValue(map.get("status")).equals("0")){
			return null;
		}
		Map<String,Object> content = (Map<String,Object> )map.get("content");
		if (Check.NuNObj(content)){
			return null;
		}
		Map<String,Object> address_detail = (Map<String,Object> )content.get("address_detail");
		if (Check.NuNObj(address_detail)){
			return null;
		}
		Map<String,Object> rst = new HashMap<>();
		rst.put("cityName",address_detail.get("city"));
		rst.put("cityCode",address_detail.get("city_code"));

		Map<String,Object> point = (Map<String,Object> )content.get("point");
		if (!Check.NuNObj(point)){
			rst.put("longitude",point.get("x"));
			rst.put("latitude",point.get("y"));
		}
		return rst;
	}


	/**
	 * 获取当前的key
	 * @param customerLocationEntity
	 * @return
	 */
	private String getLocalKey(CustomerLocationEntity customerLocationEntity){
		String  keyPre = "";
		//判断是否存在uid
		if(!Check.NuNStr(customerLocationEntity.getUid())){
			keyPre = customerLocationEntity.getUid();
		}else if(Check.NuNStr(customerLocationEntity.getDeviceNo())){
			keyPre = customerLocationEntity.getDeviceNo();
		}
		return keyPre;
	}
	/**
	 * 获取当前缓存中的信息
	 * @param customerLocationEntity
	 * @return
	 */
	private boolean checkLocatByRedis(CustomerLocationEntity customerLocationEntity){
		boolean has = false;
		String keyPre = getLocalKey(customerLocationEntity);
		if (Check.NuNStr(keyPre)){
			return has;
		}
		String key = RedisKeyConst.getLocalKey(keyPre);
		String rst = null;
		try {
			//获取key
			rst = redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}", e);
		}
		if (!Check.NuNStr(rst)){
			has = true;
		}
		return has;
	}







	/**
	 *
	 * 跳转退订政策页面
	 *
	 * @author liujun
	 * @created 2016年5月28日
	 *
	 * @param checkOutRulesCode
	 * @return
	 */
	public TradeRulesVo getTradeRulesCommon(String checkOutRulesCode) {
		try {
			//退订政策
			String checkOutRulesJson = cityTemplateService.getSelectSubDic(null, TradeRulesEnum.TradeRulesEnum005.getValue());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(checkOutRulesJson);
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "cityTemplateService.getSelectSubDic调用失败,code={}", TradeRulesEnum.TradeRulesEnum005.getValue());
			}else{
				List<EnumVo> checkOutRulesList = dto.parseData("subDic", new TypeReference<List<EnumVo>>() {});

				//退订政策显示
				for (EnumVo checkOutRules : checkOutRulesList) {
					if(!checkOutRules.getKey().equals(checkOutRulesCode)){
						continue;
					}

					Map<String, Object> checkOutRulesMap = new HashMap<String, Object>();

					TradeRulesVo tradeRulesVo  = new TradeRulesVo();

					String dicCode = null;
					if(TradeRulesEnum005001Enum.TradeRulesEnum005001001.getValue().startsWith(checkOutRules.getKey())){
						dicCode = TradeRulesEnum005001Enum.TradeRulesEnum005001001.getValue();
					}else if(TradeRulesEnum005003Enum.TradeRulesEnum005003001.getValue().startsWith(checkOutRules.getKey())){
						dicCode = TradeRulesEnum005003Enum.TradeRulesEnum005003001.getValue();
					}else if(TradeRulesEnum005002Enum.TradeRulesEnum005002001.getValue().startsWith(checkOutRules.getKey())){
						dicCode = TradeRulesEnum005002Enum.TradeRulesEnum005002001.getValue();
					}else if(TradeRulesEnum005004Enum.TradeRulesEnum005004001.getValue().startsWith(checkOutRules.getKey())){
						dicCode = TradeRulesEnum005004Enum.TradeRulesEnum005004001.getValue();
					}else{
						LogUtil.error(LOGGER, "error:{}", JsonEntityTransform.Object2Json(checkOutRules));
						return null;
					}

					String checkOutRulesValJson = cityTemplateService.getSelectSubDic(null, dicCode);
					DataTransferObject dto1 = JsonEntityTransform.json2DataTransferObject(checkOutRulesValJson);
					if (dto1.getCode() == DataTransferObject.ERROR) {
						LogUtil.error(LOGGER, "cityTemplateService.getSelectSubDic调用失败,code={}", dicCode);
					}else{
						List<EnumVo> checkOutRulesValList = dto1.parseData("subDic", new TypeReference<List<EnumVo>>() {});
						int i = 0;
						for (EnumVo checkOutRulesVal : checkOutRulesValList) {
							if(!Check.NuNCollection(checkOutRulesVal.getSubEnumVals())){
								i++;
								checkOutRulesMap.put("checkOutRulesVal" + i, checkOutRulesVal.getSubEnumVals().get(0).getKey());
							}
						}
					}

					checkOutRulesMap.put("checkOutRulesName", checkOutRules.getText());
					checkOutRulesMap.put("checkOutRulesCode", checkOutRules.getKey());
					if(!Check.NuNStrStrict(String.valueOf(checkOutRulesMap.get("checkOutRulesVal1")))){
						tradeRulesVo.setUnregText1(Integer.valueOf(String.valueOf(checkOutRulesMap.get("checkOutRulesVal1"))));
					}
					tradeRulesVo.setUnregText2(String.valueOf(checkOutRulesMap.get("checkOutRulesVal2")));
					tradeRulesVo.setUnregText3(String.valueOf(checkOutRulesMap.get("checkOutRulesVal3")));

					//严格
					if(dicCode.equals(TradeRulesEnum005001Enum.TradeRulesEnum005001001.getValue())){

						TradeRulesEnum005001001Enum.TradeRulesEnum005001001001.trans1ShowNameOld(tradeRulesVo);
						TradeRulesEnum005001001Enum.TradeRulesEnum005001001003.trans1ShowNameOld(tradeRulesVo);
						TradeRulesEnum005001001Enum.TradeRulesEnum005001001002.trans1ShowNameOld(tradeRulesVo);

					}
					//适中
					if(dicCode.equals(TradeRulesEnum005002Enum.TradeRulesEnum005002001.getValue())){
						TradeRulesEnum005002001Enum.TradeRulesEnum005002001001.trans1ShowNameOld(tradeRulesVo);
						TradeRulesEnum005002001Enum.TradeRulesEnum005002001003.trans1ShowNameOld(tradeRulesVo);
						TradeRulesEnum005002001Enum.TradeRulesEnum005002001002.trans1ShowNameOld(tradeRulesVo);

					}
					//灵活
					if(dicCode.equals(TradeRulesEnum005003Enum.TradeRulesEnum005003001.getValue())){
						TradeRulesEnum005003001Enum.TradeRulesEnum005003001001.trans1ShowNameOld(tradeRulesVo);
						TradeRulesEnum005003001Enum.TradeRulesEnum005003001003.trans1ShowNameOld(tradeRulesVo);
						TradeRulesEnum005003001Enum.TradeRulesEnum005003001002.trans1ShowNameOld(tradeRulesVo);

					}
					
					//新版本 修改退订政策
					if(!Check.NuNObj(checkOutRulesMap.get("checkOutRulesVal4"))
							&&!Check.NuNObj(checkOutRulesMap.get("checkOutRulesVal5"))
							&&!Check.NuNObj(checkOutRulesMap.get("checkOutRulesVal6"))){
						
						tradeRulesVo.setUnregText4(String.valueOf(checkOutRulesMap.get("checkOutRulesVal4")));
						tradeRulesVo.setUnregText5(String.valueOf(checkOutRulesMap.get("checkOutRulesVal5")));
						tradeRulesVo.setUnregText6(String.valueOf(checkOutRulesMap.get("checkOutRulesVal6")));
						tradeRulesVo.setUnregText7(ValueUtil.getStrValue(checkOutRulesMap.get("checkOutRulesVal7")));

						//严格
						if(dicCode.equals(TradeRulesEnum005001Enum.TradeRulesEnum005001001.getValue())){

							TradeRulesEnum005001001Enum.TradeRulesEnum005001001001.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005001001Enum.TradeRulesEnum005001001003.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005001001Enum.TradeRulesEnum005001001005.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005001001Enum.TradeRulesEnum005001001001.commonShowName(tradeRulesVo);

						}
						//适中
						if(dicCode.equals(TradeRulesEnum005002Enum.TradeRulesEnum005002001.getValue())){
							TradeRulesEnum005002001Enum.TradeRulesEnum005002001001.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005002001Enum.TradeRulesEnum005002001003.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005002001Enum.TradeRulesEnum005002001005.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005002001Enum.TradeRulesEnum005002001001.commonShowName(tradeRulesVo);
						}
						//灵活
						if(dicCode.equals(TradeRulesEnum005003Enum.TradeRulesEnum005003001.getValue())){
							TradeRulesEnum005003001Enum.TradeRulesEnum005003001001.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005003001Enum.TradeRulesEnum005003001003.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005003001Enum.TradeRulesEnum005003001005.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005003001Enum.TradeRulesEnum005003001001.commonShowName(tradeRulesVo);
						}
						
						
						Integer longTermLimit =null;
						try {
		        			//长租天数 设置
		        			String longTermLimitStr = cityTemplateService.getTextValue(null,TradeRulesEnum0020.TradeRulesEnum0020001.getValue());
		        			longTermLimit = SOAResParseUtil.getValueFromDataByKey(longTermLimitStr, "textValue", Integer.class);
		        		} catch (Exception e) {
		        			LogUtil.error(LOGGER, "长租入住最小天数查询失败e={}", e);
		        		}
						
						// 长租
						if(dicCode.equals(TradeRulesEnum005004Enum.TradeRulesEnum005004001.getValue())){
							
							if(!Check.NuNObj(longTermLimit)){
								tradeRulesVo.setLongTermLimit(longTermLimit);
							}
							
							TradeRulesEnum005004001Enum.showContext(tradeRulesVo);
						}
						
						
						 //添加说明
		                tradeRulesVo.setExplain(TradeRulesEnum005005001Enum.getDefautExplain(longTermLimit));
						
					}

					/*tenantHouseDetailVo.setUnregText1(tradeRulesVo.getUnregText1());
                    tenantHouseDetailVo.setUnregText2(tradeRulesVo.getCheckInOnNameM());
                    tenantHouseDetailVo.setUnregText3(tradeRulesVo.getCheckOutEarlyNameM());
                    tenantHouseDetailVo.setTradeRulesShowName(tradeRulesVo.getCheckInPreNameM()+"\r\n"+tradeRulesVo.getCheckInOnNameM()+"\r\n"+tradeRulesVo.getCheckOutEarlyNameM()
                            +"\r\n"+ tradeRulesVo.getCommonShowName());*/

					return tradeRulesVo;
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取退订政策异常e={}", e);
		}
		return null;
	}

	/**
	 *
	 * 价格初始化
	 *
	 * @author zl
	 * @created 2017年6月19日 下午8:46:38
	 *
	 * 从controller 抽离到service wangwt2017年6月29日
	 * @param resultMap
	 * @param leasePrice
	 * @param cleaningFees
	 * @param houseBaseFid
	 * @param roomFid
	 * @param rentWay
	 */
	public void initSetPrice(Map<String, Object> resultMap,Integer leasePrice,Integer cleaningFees,String houseBaseFid,String roomFid,Integer rentWay,Map<String, Boolean> notEditFiled) {

		if (Check.NuNObjs(houseBaseFid, rentWay,resultMap)) {
			return;
		}

		try {
			//基础价格
			FieldTextVo<Integer> leasePricefield = new FieldTextVo<Integer>(true,0);
			if(!Check.NuNObj(leasePrice)){
				leasePricefield.setValue(DecimalCalculate.div(String.valueOf(leasePrice), "100", 0).intValue());
			}
			if(!Check.NuNMap(notEditFiled)){
				Boolean leasePriceEdit = !Check.NuNObj(notEditFiled.get("all"))?notEditFiled.get("all"):notEditFiled.get("leasePrice");
				if(!Check.NuNObj(leasePriceEdit)){
					leasePricefield.setIsEdit(leasePriceEdit);
				}		
			}
			resultMap.put("leasePrice", leasePricefield);
			// 房源价格限制
			String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(priceLowJson);
			if(dto.getCode() == DataTransferObject.SUCCESS){
				Integer priceLow = Integer.valueOf(SOAResParseUtil.getStrFromDataByKey(priceLowJson, "textValue"));
				if(!Check.NuNObj(priceLow)){
					resultMap.put("minPrice", priceLow);
				}
			}

			String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(priceHighJson);
			if(dto.getCode() == DataTransferObject.SUCCESS){
				Integer priceHigh = Integer.valueOf(SOAResParseUtil.getStrFromDataByKey(priceHighJson, "textValue"));
				if(!Check.NuNObj(priceHigh)){
					resultMap.put("maxPrice", priceHigh);
				}
			}

			//清洁费
			FieldTextVo<Integer> cleaningFeesField = new FieldTextVo<Integer>(true,0);
			if(!Check.NuNObj(cleaningFees)){
				cleaningFeesField.setValue(DecimalCalculate.div(String.valueOf(cleaningFees), "100", 0).intValue());
			}
			if(!Check.NuNMap(notEditFiled)){
				Boolean edit = !Check.NuNObj(notEditFiled.get("all"))?notEditFiled.get("all"):notEditFiled.get("cleaningFees");
				if(!Check.NuNObj(edit)){
					cleaningFeesField.setIsEdit(edit);
				}		
			}
			resultMap.put("cleaningFees", cleaningFeesField);

			//清洁费比例
			String cleaningFeesPerJson = cityTemplateService.getTextValue(null, TradeRulesEnum.TradeRulesEnum0019.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(cleaningFeesPerJson);
			if(dto.getCode() == DataTransferObject.SUCCESS){
				Double cleaningFeesPer = Double.valueOf(SOAResParseUtil.getStrFromDataByKey(cleaningFeesPerJson, "textValue"));
				if(!Check.NuNObj(cleaningFeesPer)){
					resultMap.put("cleaningFeesPer",new Double(Math.ceil(cleaningFeesPer)).intValue());
				}
			}

			//周末价格
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("houseFid", houseBaseFid);
			paramMap.put("roomFid", roomFid);
			List<HousePriceWeekConfEntity> weekendPriceList = new ArrayList<HousePriceWeekConfEntity>();
			if(rentWay==RentWayEnum.HOUSE.getCode() || (rentWay==RentWayEnum.ROOM.getCode() && !Check.NuNStr(roomFid))){
				String weekendPriceJson = houseManageService.findWeekPriceByFid(JsonEntityTransform.Object2Json(paramMap));
				weekendPriceList = SOAResParseUtil.getListValueFromDataByKey(weekendPriceJson, "list",HousePriceWeekConfEntity.class);
			}

			Integer weekendPriceSwitch = YesOrNoEnum.NO.getCode();
			Integer weekendPrice =null;
			Set<Integer> weekdays =new HashSet<>();
			if (!Check.NuNCollection(weekendPriceList)) {
				for (HousePriceWeekConfEntity housePriceWeekConf : weekendPriceList) {
					if (housePriceWeekConf.getIsDel().intValue() == YesOrNoEnum.NO.getCode()) {
						weekendPrice =housePriceWeekConf.getPriceVal();
						weekdays.add(housePriceWeekConf.getSetWeek());

						if (housePriceWeekConf.getIsValid().intValue() == YesOrNoEnum.YES.getCode()) {
							weekendPriceSwitch = YesOrNoEnum.YES.getCode();
						}

					}
				}
			}

			WeekendPriceEnum weekEnum = WeekendPriceEnum.getEnumByColl(weekdays);

			resultMap.put("weekendPriceSwitch", weekendPriceSwitch);
			FieldTextVo<Integer> weekendPriceField = new FieldTextVo<Integer>(true,0);
			if (!Check.NuNObj(weekendPrice)) {
				weekendPriceField.setValue(DecimalCalculate.div(String.valueOf(weekendPrice), "100", 0).intValue());
			}
			if(!Check.NuNMap(notEditFiled)){
				Boolean edit = !Check.NuNObj(notEditFiled.get("all"))?notEditFiled.get("all"):notEditFiled.get("weekendPrice");
				if(!Check.NuNObj(edit)){
					weekendPriceField.setIsEdit(edit);
				}		
			}
			resultMap.put("weekendPrice", weekendPriceField);

			FieldSelectListVo weekFieldList = new FieldSelectListVo();
			FieldSelectVo<String> weekFieldListSelect1 = new FieldSelectVo<String>(WeekendPriceEnum.FRI_SAT.getValue(),WeekendPriceEnum.FRI_SAT.getText(),false);
			FieldSelectVo<String> weekFieldListSelect2 = new FieldSelectVo<String>(WeekendPriceEnum.SAT_SUN.getValue(),WeekendPriceEnum.SAT_SUN.getText(),false);
			FieldSelectVo<String> weekFieldListSelect3 = new FieldSelectVo<String>(WeekendPriceEnum.FRI_SAT_SUN.getValue(),WeekendPriceEnum.FRI_SAT_SUN.getText(),false);
			if (!Check.NuNObj(weekEnum) && weekEnum.getValue().equals(WeekendPriceEnum.FRI_SAT.getValue())) {
				weekFieldListSelect1.setIsSelect(true);
			}else if (!Check.NuNObj(weekEnum) && weekEnum.getValue().equals(WeekendPriceEnum.SAT_SUN.getValue())) {
				weekFieldListSelect2.setIsSelect(true);
			}else if (!Check.NuNObj(weekEnum) && weekEnum.getValue().equals(WeekendPriceEnum.FRI_SAT_SUN.getValue())) {
				weekFieldListSelect3.setIsSelect(true);
			}
			weekFieldList.getList().add(weekFieldListSelect1);
			weekFieldList.getList().add(weekFieldListSelect2);
			weekFieldList.getList().add(weekFieldListSelect3);
			if(!Check.NuNMap(notEditFiled)){
				Boolean edit = !Check.NuNObj(notEditFiled.get("all"))?notEditFiled.get("all"):notEditFiled.get("weekendList");
				if(!Check.NuNObj(edit)){
					weekFieldList.setIsEdit(edit);
				}		
			}
			resultMap.put("weekendList", weekFieldList);

			//长租折扣
			HouseConfMsgEntity confMsgEntity = new HouseConfMsgEntity();
			confMsgEntity.setHouseBaseFid(houseBaseFid);
			confMsgEntity.setRoomFid(roomFid);
			List<HouseConfMsgEntity> sevenList = new ArrayList<>();
			List<HouseConfMsgEntity> thirtyList = new ArrayList<>();
			if(rentWay==RentWayEnum.HOUSE.getCode() || (rentWay==RentWayEnum.ROOM.getCode() && !Check.NuNStr(roomFid))){
				confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
				String sevenJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
				sevenList = SOAResParseUtil.getListValueFromDataByKey(sevenJson, "list", HouseConfMsgEntity.class);
				
				confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
				String thirtyJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
				thirtyList = SOAResParseUtil.getListValueFromDataByKey(thirtyJson, "list", HouseConfMsgEntity.class);
				
			}

			Integer fullDayRateSwitch = YesOrNoEnum.NO.getCode();
			FieldTextVo<Double> sevenDiscountRateField = new FieldTextVo<Double>(true, DecimalCalculate.div("0", "10", 2).doubleValue());
			if(!Check.NuNCollection(sevenList) &&!Check.NuNObj(sevenList.get(0)) &&!Check.NuNObj(sevenList.get(0).getDicVal())
					&& ValueUtil.getintValue(sevenList.get(0).getDicVal())>0 && ValueUtil.getintValue(sevenList.get(0).getDicVal())<100
					&& sevenList.get(0).getIsDel()!= YesOrNoOrFrozenEnum.YES.getCode()){
				sevenDiscountRateField.setValue(DecimalCalculate.div(sevenList.get(0).getDicVal(), "10", 2).doubleValue());
				if(sevenList.get(0).getIsDel()==YesOrNoOrFrozenEnum.NO.getCode()){
					fullDayRateSwitch = YesOrNoEnum.YES.getCode();
				}
			}
			if(!Check.NuNMap(notEditFiled)){
				Boolean edit = !Check.NuNObj(notEditFiled.get("all"))?notEditFiled.get("all"):notEditFiled.get("sevenDiscountRate");
				if(!Check.NuNObj(edit)){
					sevenDiscountRateField.setIsEdit(edit);
				}		
			}
			resultMap.put("sevenDiscountRate", sevenDiscountRateField);
			
			FieldTextVo<Double> thirtyDiscountRateField = new FieldTextVo<Double>(true, DecimalCalculate.div("0", "10", 2).doubleValue());
			if(!Check.NuNCollection(thirtyList) &&!Check.NuNObj(thirtyList.get(0))&&!Check.NuNObj(thirtyList.get(0).getDicVal())
					&& ValueUtil.getintValue(thirtyList.get(0).getDicVal())>0 && ValueUtil.getintValue(thirtyList.get(0).getDicVal())<100
					&& thirtyList.get(0).getIsDel()!=YesOrNoOrFrozenEnum.YES.getCode()){
				thirtyDiscountRateField.setValue(DecimalCalculate.div(thirtyList.get(0).getDicVal(), "10", 2).doubleValue());
				if(thirtyList.get(0).getIsDel()==YesOrNoOrFrozenEnum.NO.getCode()){
					fullDayRateSwitch = YesOrNoEnum.YES.getCode();
				}
			}
			if(!Check.NuNMap(notEditFiled)){
				Boolean edit = !Check.NuNObj(notEditFiled.get("all"))?notEditFiled.get("all"):notEditFiled.get("thirtyDiscountRate");
				if(!Check.NuNObj(edit)){
					thirtyDiscountRateField.setIsEdit(edit);
				}		
			}
			resultMap.put("thirtyDiscountRate", thirtyDiscountRateField);

			resultMap.put("fullDayRateSwitch", fullDayRateSwitch);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "初始化价格异常，error = {}", e);
		}

	}
	
	/**
	 * 
	 * 查询管家运营专员
	 *
	 * @author bushujie
	 * @created 2017年7月13日 下午2:40:39
	 *
	 * @param housePhyPcDto
	 * @param customerBase
	 * @return
	 * @throws SOAParseException
	 */
	public HouseGuardRelEntity setHouseGuardRel(String regionCode,String uid) throws SOAParseException{
		HouseGuardRelEntity houseGuardRelEntity  = null;
		if(Check.NuNObj(regionCode)||Check.NuNObj(uid)) {
			return houseGuardRelEntity;
		}
    	// 区域专员关系表中随机分配运营专员
    	if (Check.NuNObj(houseGuardRelEntity)) {
    		GuardAreaRequest guardAreaRequest = new GuardAreaRequest();
    		guardAreaRequest.setAreaCode(regionCode.split(",")[3]);
    		guardAreaRequest.setCityCode(regionCode.split(",")[2]);
    		guardAreaRequest.setNationCode(regionCode.split(",")[0]);
    		guardAreaRequest.setProvinceCode(regionCode.split(",")[1]);
    		guardAreaRequest.setIsDel(IsDelEnum.NOT_DEL.getCode());
    		String guardJson = guardAreaService.findGuardAreaByCode(JsonEntityTransform.Object2Json(guardAreaRequest));
    		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(guardJson);
    		
    		if(dto.getCode() ==DataTransferObject.SUCCESS){
    			GuardAreaEntity guardArea = SOAResParseUtil.getValueFromDataByKey(guardJson, "guardArea", GuardAreaEntity.class);
    			if(!Check.NuNObj(guardArea)){
    				houseGuardRelEntity = new HouseGuardRelEntity();
    				houseGuardRelEntity.setFid(UUIDGenerator.hexUUID());
    				houseGuardRelEntity.setEmpGuardCode(guardArea.getEmpCode());
    				houseGuardRelEntity.setEmpGuardName(guardArea.getEmpName());
    				houseGuardRelEntity.setCreateFid(uid);
    			}
    		}
		}
    	
		return houseGuardRelEntity;
	}
	
	/**
	 * 
	 * 获取来源
	 *
	 * @author yd
	 * @created 2017年7月6日 下午8:21:50
	 *
	 * @param request
	 * @return
	 */
	public int getUserSourceType(HttpServletRequest request){

		int houseSource = HouseSourceEnum.MSIT.getCode();
		Header header=getHeader(request);
		//判断来源
		if(header.getOsType().equals("1")){
			houseSource =HouseSourceEnum.ANDROID.getCode();
		} else if(header.getOsType().equals("2")) {
			houseSource = HouseSourceEnum.IOS.getCode();
		}

		return houseSource;
	}
	
	/**
	 * 
	 * 获取房东uid
	 *
	 * @author bushujie
	 * @created 2017年8月1日 上午10:49:00
	 *
	 * @param request
	 * @return
	 */
	public String getLandlordUid(HttpServletRequest request){
		return getUserId(request);
	}
	
	/**
	 * 
	 * 待审核字段值map
	 *
	 * @author bushujie
	 * @created 2017年8月2日 上午9:59:15
	 *
	 * @param list
	 * @return
	 * @throws SOAParseException 
	 */
	public Map<String , HouseFieldAuditLogVo> houseFieldAuditLogVoConvertMap(String houseFid,String roomFid,Integer rentWay,Integer fieldAuditStatu) throws SOAParseException{
		Map<String , HouseFieldAuditLogVo> resultMap=new HashMap<String,HouseFieldAuditLogVo>();
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog=new HouseUpdateFieldAuditNewlogEntity();
		houseUpdateFieldAuditNewlog.setHouseFid(houseFid);
		houseUpdateFieldAuditNewlog.setRentWay(rentWay);
		houseUpdateFieldAuditNewlog.setRoomFid(roomFid);
		houseUpdateFieldAuditNewlog.setFieldAuditStatu(fieldAuditStatu);
		String resultJson=troyHouseMgtService.getHouseUpdateFieldAuditNewlogByCondition(JsonEntityTransform.Object2Json(houseUpdateFieldAuditNewlog));
		List<HouseFieldAuditLogVo> list=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseFieldAuditLogVo.class);
		for(HouseFieldAuditLogVo vo:list){
			resultMap.put(vo.getFieldPath(), vo);
		}
		return resultMap;
	}

	public static void main(String[] args) {
		String url = "http://api.map.baidu.com/location/ip?ak=sakCBjtP5nIXwobWibcIGma6xVGjgn4m&ip=120.134.33.9";
		String rst = CloseableHttpUtil.sendGet(url,null);
		if (Check.NuNStr(rst)){
			return;
		}
		Map<String,Object>  map = transLocationMap(rst);
		System.out.println(JsonEntityTransform.Object2Json(map));

	}
}
