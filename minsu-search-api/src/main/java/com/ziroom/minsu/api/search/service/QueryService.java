package com.ziroom.minsu.api.search.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.search.common.header.Header;
import com.ziroom.minsu.api.search.common.interceptor.ParamCollector;
import com.ziroom.minsu.api.search.constant.Constant;
import com.ziroom.minsu.api.search.controller.abs.AbstractController;
import com.ziroom.minsu.entity.customer.CustomerLocationEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SearchConstant;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.IpUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.BaiDuIPUtil.BaiduIPResult;
import com.ziroom.minsu.services.common.utils.BaiDuMapUtil.BaiduGeocodingResult;
import com.ziroom.minsu.services.customer.api.inner.CustomerLocationService;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.dto.Top50HouseListRequest;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.services.search.vo.StaticResourceVo;
import com.ziroom.minsu.services.search.vo.Top50HouseListVo;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.house.QualityGradeEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.search.SearchSourceTypeEnum;
import com.ziroom.minsu.valenum.search.SortTypeEnum;

/**
 * 
 * <p>查询服务层  给 controler 层提供</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Service("query.queryService")
public class QueryService extends AbstractController{



	private static final Logger LOGGER = LoggerFactory.getLogger(QueryService.class);
	/**
	 * 搜索的api
	 */
	@Resource(name = "search.searchServiceApi")
	private SearchService searchService;


	@Resource(name="searchApi.messageSource")
	private MessageSource messageSource;


	@Value("#{'${default_pic_size}'.trim()}")
	private String defaultPicSize;
	
    @Autowired
    private RedisOperations redisOperations;

    @Resource(name="customer.customerLocationService")
    private CustomerLocationService customerLocationService;
    
    @Resource(name="basedata.confCityService")
    private ConfCityService confCityService;
    
	@Value("#{'${default_icon_size}'.trim()}")
	private String defaultIconSize;


	/**
	 * 
	 * 获取top50 房源列表
	 *
	 * @author yd
	 * @created 2017年6月2日 上午11:01:11
	 *
	 * @param dto
	 */
	public DataTransferObject top50HouseListArticles(HttpServletRequest request, HttpServletResponse response){

		DataTransferObject dto = new DataTransferObject();
		try {
			Top50HouseListRequest top50ListRequest = new Top50HouseListRequest();
			top50ListRequest.setPage(1);
			top50ListRequest.setLimit(11);	
			request.setAttribute(ParamCollector.PARAMS, JsonEntityTransform.Object2Json(top50ListRequest));

			dto = top50HouseList(request,  response);

			if (dto.getCode()==DataTransferObject.ERROR) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
				return dto;
			}

			//首页top50专栏标题
			String shareTitleStr = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TOP50_LIST_SHARE_TITLE_ARTICLES);
			StaticResourceVo shareTitle =  SOAResParseUtil.getValueFromDataByKey(shareTitleStr, "result", StaticResourceVo.class);
			if (!Check.NuNObj(shareTitle)) {            	
				dto.putValue("top50ListShareTitle", shareTitle.getResContent()); 
			}

			//首页top50专栏中文标题
			String cnTtitleStr = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TOP50_LIST_SHARE_ARTICLES_TITLE_CN);
			StaticResourceVo cnTtitle =  SOAResParseUtil.getValueFromDataByKey(cnTtitleStr, "result", StaticResourceVo.class);
			String cnTtitleRes = null;
			if (!Check.NuNObj(cnTtitle)) {  
				cnTtitleRes = cnTtitle.getResContent();
			}

			//首页top50专栏英文标题
			String enTtitleStr = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TOP50_LIST_SHARE_ARTICLES_TITLE_EN);
			StaticResourceVo enTtitle =  SOAResParseUtil.getValueFromDataByKey(enTtitleStr, "result", StaticResourceVo.class);
			String enTtitleRes = null;
			if (!Check.NuNObj(enTtitle)) {  
				enTtitleRes = enTtitle.getResContent();
			}

			if(Check.NuNStr(cnTtitleRes) && Check.NuNStr(enTtitleRes) && !Check.NuNObj(shareTitle) && !Check.NuNStr(shareTitle.getResContent())){
				String[] strings = shareTitle.getResContent().split("&");
				if (strings!=null && strings.length==2) {
					cnTtitleRes = strings[0];
					enTtitleRes = strings[1];
				}
			}

			dto.putValue("zhTitle", cnTtitleRes); 
			dto.putValue("enTitle", enTtitleRes); 

		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询top50专栏数据异常,e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto;
	}


	/**
	 * 
	 * top50列表
	 *
	 * @author yd
	 * @created 2017年6月2日 上午11:05:13
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public DataTransferObject top50HouseList(HttpServletRequest request, HttpServletResponse response){

		DataTransferObject dto = new DataTransferObject();

		try {
			response.addHeader( "Access-Control-Allow-Origin", "*" ); // open your api to any client
			response.addHeader( "Access-Control-Max-Age", "3000" ); // time from request to response before timeout

			Header header = getHeader(request);

			List<Top50HouseListVo> resultList = new ArrayList<>(); 
			if(!Check.NuNObj(header) && !Check.NuNStr(header.getOsType()) 
					&& String.valueOf(HouseSourceEnum.IOS.getCode()).equals(header.getOsType().trim())
					&&Constant.APP_IOS_VERSION_NAME.equals(header.getVersionName())){
				LogUtil.info(LOGGER, "【top50首页专栏查询】osType={},versionName={}", header.getOsType(),header.getVersionName());
				return dto;
			}
			// 获取当前的用户uid
			String uid = getUserId(request);
			Top50HouseListRequest top50ListRequest = getEntity(request, Top50HouseListRequest.class);
			if (Check.NuNObj(top50ListRequest)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto;
			}

			try {			
				if (!Check.NuNObj(header) && !Check.NuNStr(header.getVersionCode())) {
					top50ListRequest.setVersionCode(ValueUtil.getintValue(header.getVersionCode()));
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "版本号转化错误，versionCode={},e={}", header.getVersionCode(),e);
			}

			LogUtil.info(LOGGER, "top50ListRequest:{}", JsonEntityTransform.Object2Json(top50ListRequest));


			String jsonRst = searchService.getTOP50List(JsonEntityTransform.Object2Json(top50ListRequest), defaultPicSize,
					uid);
			DataTransferObject resDto = JsonEntityTransform.json2DataTransferObject(jsonRst);

			if (resDto!=null && DataTransferObject.SUCCESS==resDto.getCode()) {
				List<HouseInfoEntity> list = SOAResParseUtil.getListValueFromDataByKey(jsonRst, "list", HouseInfoEntity.class);
				if(!Check.NuNCollection(list)){
					for (HouseInfoEntity houseInfoEntity : list) {
						Top50HouseListVo vo = new Top50HouseListVo();
						BeanUtils.copyProperties(vo, houseInfoEntity);
						if (!Check.NuNStr(houseInfoEntity.getTop50Title())) {
							vo.setHouseName(houseInfoEntity.getTop50Title());
						}
						resultList.add(vo);
					}
				}

			}
			dto.putValue("list", resultList);
			dto.putValue("total", SOAResParseUtil.getIntFromDataByKey(jsonRst, "total"));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询top50列表异常,e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto;
		}

		return dto;
	}
	
	

	/**
	 * 今日特惠专栏
	 *
	 * @author yd
	 * @created 2017年6月2日 上午11:35:32
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public DataTransferObject todayDiscountArticles(HttpServletRequest request, HttpServletResponse response) {

		response.addHeader( "Access-Control-Allow-Origin", "*" ); // open your api to any client
		// response.addHeader( "Access-Control-Allow-Methods", "POST" ); // a allow post
		response.addHeader( "Access-Control-Max-Age", "3000" ); // time from request to response before timeout
		
		DataTransferObject dto = new DataTransferObject();

		try {

			Header header = getHeader(request);
			// 获取当前的用户uid
			String uid = getUserId(request);		
			int page =1;
			HouseInfoRequest houseInfo = new HouseInfoRequest();
			houseInfo.setPage(page);
			houseInfo.setLimit(50);	
			houseInfo.setJiaxinDiscount(ProductRulesEnum020.ProductRulesEnum020001.getValue()); 
			houseInfo.setQ("*:*");
			houseInfo.setIsRecommend(YesOrNoEnum.YES.getCode());
			houseInfo.setStartTime(DateUtil.dateFormat(new Date()));
			houseInfo.setEndTime(DateUtil.dateFormat(DateSplitUtil.getTomorrow(new Date())));
			try {			
	        	if (!Check.NuNObj(header) && !Check.NuNStr(header.getVersionCode())) {
	        		houseInfo.setVersionCode(ValueUtil.getintValue(header.getVersionCode()));
	        	}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "版本号转化错误，versionCode={},e={}", header.getVersionCode(),e);
			}
			
			try {
        		Boolean inTargetCity =  inTargetCity(houseInfo,uid,header,getIpAddress(request));
        		if (!Check.NuNObj(inTargetCity)) {
    				if (inTargetCity) {
    					houseInfo.setIsTargetCityLocal(1);
					}else{
						houseInfo.setIsTargetCityLocal(0);
					}
    			}
        		
        		houseInfo.setInCityName(header.getLocationCityName());
				
			} catch (Exception e) {
				 LogUtil.info(LOGGER, "定位城市失败，e={}", e);
			}
			
			
			houseInfo.setSearchSourceTypeEnum(SearchSourceTypeEnum.today_article);
			houseInfo.setSortType(SortTypeEnum.TONIGHT_ARTICLE.getCode());
			
			int size = 11;
			
			try {
				
				List<HouseInfoEntity> list = null;
				
				List<HouseInfoEntity> resultList = new ArrayList<>();
	            List<HouseInfoEntity> notInCityList = new ArrayList<>();
	            
	            String jsonRst =null;
				
				do {
					String par = JsonEntityTransform.Object2Json(houseInfo); 
					jsonRst =searchService.getHouseListInfoAndSuggest(defaultPicSize, par,uid);
		            dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
		            
		            if (dto!=null && DataTransferObject.SUCCESS==dto.getCode()) {
						list = SOAResParseUtil.getListValueFromDataByKey(jsonRst, "list", HouseInfoEntity.class);

						if (!Check.NuNStr(houseInfo.getInCityName())) {
							Iterator<HouseInfoEntity> iterator =  list.iterator();
							while(iterator.hasNext()){
								HouseInfoEntity houseInfoEntity = iterator.next(); 
								if (!Check.NuNStr(houseInfoEntity.getCityName()) && houseInfoEntity.getCityName().contains(houseInfo.getInCityName())
										&& !QualityGradeEnum.GRADE_C.getCode().equals(houseInfoEntity.getHouseQualityGrade())) {
									resultList.add(houseInfoEntity);
									iterator.remove();
								}
							}
							if (!Check.NuNCollection(list)) {
								for (HouseInfoEntity houseInfoEntity : list) {
									if (!QualityGradeEnum.GRADE_C.getCode().equals(houseInfoEntity.getHouseQualityGrade())) {
										notInCityList.add(houseInfoEntity);
									}
								}
							}
						}else{							
							if (!Check.NuNCollection(list)) {
								for (HouseInfoEntity houseInfoEntity : list) {
									if (!QualityGradeEnum.GRADE_C.getCode().equals(houseInfoEntity.getHouseQualityGrade())) {
										resultList.add(houseInfoEntity);
									}
								}
							} 
						}
						
					}
		            page++;
		            houseInfo.setPage(page);
				} while (resultList.size() < size && !Check.NuNCollection(list));
	            
				sortImOrderList(resultList);
				
				if (!Check.NuNCollection(notInCityList)) {
					sortImOrderList(notInCityList);
					resultList.addAll(notInCityList);				
				}
	            if (resultList.size()>size) {
	            	resultList = resultList.subList(0, size);
				} 
	            
				dto.putValue("list", resultList);
				dto.putValue("total", resultList.size());
				
			} catch (Exception e) {
				LogUtil.error(LOGGER, "查询今日特惠专栏数据失败，param={},e={}", JsonEntityTransform.Object2Json(houseInfo),e);
			} 
			
			//今日特惠专栏中文标题
            String cnTitleStr = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_ARTICLES_TITLE_CN);
            StaticResourceVo cnTitle =  SOAResParseUtil.getValueFromDataByKey(cnTitleStr, "result", StaticResourceVo.class);
            if (!Check.NuNObj(cnTitle)) {            	
            	dto.putValue("zhTitle", cnTitle.getResContent()); 
            }
            
			//今日特惠专栏英文标题
            String enTitleStr = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_ARTICLES_TITLE_EN);
            StaticResourceVo enTitle =  SOAResParseUtil.getValueFromDataByKey(enTitleStr, "result", StaticResourceVo.class);
            if (!Check.NuNObj(enTitle)) {            	
            	dto.putValue("enTitle", enTitle.getResContent()); 
            } 
            
            String subTitleStr = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_ARTICLES_SUB_TITLE);
            StaticResourceVo subtitle =  SOAResParseUtil.getValueFromDataByKey(subTitleStr, "result", StaticResourceVo.class);
            if (!Check.NuNObj(subtitle)) {            	
            	dto.putValue("subTitle", subtitle.getResContent()); 
            }
            
            dto.putValue("todayDiscountCode", ProductRulesEnum020.ProductRulesEnum020001.getValue()); 
           
            //不要推荐
            dto.putValue("suggest", new ArrayList<>());
            
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询今日特惠专栏数据异常,e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto;
		}

		return dto;
	}
	
	/**
     * 判断是否在目标城市
     * @param uid
     * @param header
     * @param houseInfo
     * @return
     */
    private Boolean inTargetCity(final HouseInfoRequest houseInfo,final String uid,final Header header,final String serverIp){
    	
    	
    	long begin = new Date().getTime(); 
    	if (Check.NuNObj(header) || Check.NuNObj(houseInfo)) {
			return null;
		}
    	String targetCityCode = houseInfo.getCityCode();
    	if (Check.NuNStr(targetCityCode)) {
    		return null;
		}
    	String targetCityName = getCityNameByCode(targetCityCode);   	
    	if (Check.NuNStr(targetCityCode)) {
    		return null;
		}
    	
    	CustomerLocationEntity customerLocationEntity = transParams2Entity(uid,header,serverIp);        
        if (Check.NuNObj(customerLocationEntity)){
            return null;
        }
    	String keyPre = getLocalKey(customerLocationEntity);
        keyPre = Check.NuNStr(keyPre)?"":keyPre;
        String key =null;
        if (!Check.NuNStr(keyPre)) {
        	key = RedisKeyConst.getLocalKey(keyPre);
		}
        
        String rst = null;
        
        CustomerLocationEntity customerLocationEntityStore=null;
        if (!Check.NuNStr(key)) {
        	try {
        		rst = redisOperations.get(key);
        		
        		if (!Check.NuNStr(rst)) {
        			customerLocationEntityStore = JsonEntityTransform.json2Object(rst, CustomerLocationEntity.class);
        		}
        		
        	} catch (Exception e) {
        		LogUtil.error(LOGGER, "redis错误,e:{}", e);
        	}
		}
        
        if (Check.NuNObj(customerLocationEntityStore)) {
        	try {
        		rst = customerLocationService.getCustomerLocation(JsonEntityTransform.Object2Json(customerLocationEntity));
        		if (!Check.NuNStr(rst)) {
        			DataTransferObject locationDto = JsonEntityTransform.json2DataTransferObject(rst);
        			if (!Check.NuNObj(locationDto)) {
        				customerLocationEntityStore = locationDto.parseData("result", new TypeReference<CustomerLocationEntity>() {});
					}
        		}
        		
			} catch (Exception e) {
				 LogUtil.error(LOGGER, "查询用户位置信息错误,e:{}", e);
			}
        }
        
        if (!Check.NuNObj(customerLocationEntityStore)) {
    		if (Check.NuNStr(customerLocationEntity.getCityName()) && !Check.NuNStr(customerLocationEntityStore.getCityName())) {
    			customerLocationEntity.setCityName(customerLocationEntityStore.getCityName());
			}
    		if (Check.NuNStr(customerLocationEntity.getCityCode()) && !Check.NuNStr(customerLocationEntityStore.getCityCode())) {
    			customerLocationEntity.setCityCode(customerLocationEntityStore.getCityCode());
			}
    		if (Check.NuNObj(customerLocationEntity.getDeviceIp()) && !Check.NuNObj(customerLocationEntityStore.getDeviceIp())) {
    			customerLocationEntity.setDeviceIp(customerLocationEntityStore.getDeviceIp());
			}
    		if (Check.NuNObj(customerLocationEntity.getDeviceIp()) && !Check.NuNObj(customerLocationEntityStore.getDeviceIp())) {
    			customerLocationEntity.setDeviceIp(customerLocationEntityStore.getDeviceIp());
			}
    		if (Check.NuNObj(customerLocationEntity.getLongitude()) && !Check.NuNObj(customerLocationEntityStore.getLongitude())) {
    			customerLocationEntity.setLongitude(customerLocationEntityStore.getLongitude());
			}
    		if (Check.NuNObj(customerLocationEntity.getLatitude()) && !Check.NuNObj(customerLocationEntityStore.getLatitude())) {
    			customerLocationEntity.setLatitude(customerLocationEntityStore.getLatitude());
			}
		}
        
        
        String inCityName = customerLocationEntity.getCityName();
        Long indeviceIp = customerLocationEntity.getDeviceIp();         
        Double inlongitude =customerLocationEntity.getLongitude();
        Double inlatitude = customerLocationEntity.getLatitude();
        
        if (!Check.NuNStr(inCityName)) {
			if (targetCityName.indexOf(inCityName)>=0 || inCityName.indexOf(targetCityName)>=0) {
				return true;
			}else{
				return false;
			}
		}else if(!Check.NuNObj(inlongitude) && !Check.NuNObj(inlatitude)){
			 
			try {
				BaiduGeocodingResult baiduInfo = null;
//				baiduInfo =BaiDuMapUtil.getBaiduAreaCodeByLocation(inlatitude, inlongitude);
				if (!Check.NuNObj(baiduInfo) && baiduInfo.getStatus()==0) {
					inCityName =  baiduInfo.getCity();
					customerLocationEntity.setCityName(inCityName);
					 if (!Check.NuNStr(key)) {
						 try {
							 redisOperations.setex(RedisKeyConst.getLocalKey(key).toString(), RedisKeyConst.LOCATION_TIME, JsonEntityTransform.Object2Json(customerLocationEntity));
						 } catch (Exception e) {
							 LogUtil.error(LOGGER, "redis错误,e:{}", e);
						 }
					 }
					if (targetCityName.indexOf(inCityName)>=0 || inCityName.indexOf(targetCityName)>=0) {
						return true;
					}else{
						return false;
					}
				}
				
			} catch (Exception e) {
				LogUtil.error(LOGGER, "获取百度经纬度定位失败,e:{}", e);
			}		
			
		}else if(!Check.NuNObj(indeviceIp)){
			try {
				BaiduIPResult ipInfo =null; 
//				ipInfo =BaiDuIPUtil.getBaiduIPInfo(IpUtil.intToIP(indeviceIp));
				if (!Check.NuNObj(ipInfo) && ipInfo.getStatus()==0) {
					inCityName =  ipInfo.getCity();
					customerLocationEntity.setCityName(inCityName);
					 if (!Check.NuNStr(key)) {
						 try {
							 redisOperations.setex(RedisKeyConst.getLocalKey(key).toString(), RedisKeyConst.LOCATION_TIME, JsonEntityTransform.Object2Json(customerLocationEntity));
						 } catch (Exception e) {
							 LogUtil.error(LOGGER, "redis错误,e:{}", e);
						 }
					 }
					if (targetCityName.indexOf(inCityName)>=0 || inCityName.indexOf(targetCityName)>=0) {
						return true;
					}else{
						return false;
					}
				}
				
			} catch (Exception e) {
				LogUtil.error(LOGGER, "获取百度IP定位失败,e:{}", e);
			}	
			
		}
        LogUtil.info(LOGGER, "城市定位结束，共耗时{}ms", (new Date().getTime()-begin));
    	return null;    	
    	
    }
    
	/**
	 * 
	 * 立即预定靠前
	 *
	 * @author zl
	 * @created 2017年4月13日 下午8:38:12
	 *
	 * @param list
	 */
	private void sortImOrderList(List<HouseInfoEntity> list){
		if(Check.NuNCollection(list)){
			return;
		}
		
		Collections.sort(list, new Comparator<HouseInfoEntity>() {

			@Override
			public int compare(HouseInfoEntity o1, HouseInfoEntity o2) {
				if (!Check.NuNObj(o1.getOrderType()) && !Check.NuNObj(o1.getOrderType())) {
					return o1.getOrderType().compareTo(o2.getOrderType());
				}
				return 0;
			}
    		
		});
		
	}
	
	   /**
     * 获取城市名称
     * @param cityCode
     * @return
     */
	private String getCityNameByCode(String cityCode) {
		if (Check.NuNStr(cityCode)) {
			return null;
		}

		Map<String, String> cityMap = null;
		try {

			String key = RedisKeyConst.getLocalKey("cityMapKey");
			String rst = null;
			try {
				rst = redisOperations.get(key);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}", e);
			}
			if (!Check.NuNStr(rst)) {
				cityMap = JsonEntityTransform.json2Object(rst,Map.class);
			} else {
				cityMap = new HashMap<>();
				String resultJson = confCityService.getConfCitySelect();
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				List<TreeNodeVo> cityList = dto.parseData("list", new TypeReference<List<TreeNodeVo>>() {
				});
				if (!Check.NuNCollection(cityList)) {
					for (TreeNodeVo treeNodeVo : cityList) {// 国家
						List<TreeNodeVo> provices = treeNodeVo.getNodes();
						if (!Check.NuNCollection(provices)) {
							for (TreeNodeVo proviceNode : provices) {// 省份
								List<TreeNodeVo> citys = proviceNode.getNodes();
								if (!Check.NuNCollection(citys)) {
									for (TreeNodeVo cityNode : citys) {// 城市
										cityMap.put(cityNode.getCode(), cityNode.getText());
									}
								}

							}
						}
					}
				}

				if (!Check.NuNMap(cityMap)) {
					try {
						redisOperations.setex(RedisKeyConst.getLocalKey(key).toString(), RedisKeyConst.LOCATION_TIME,
								JsonEntityTransform.Object2Json(cityMap));
					} catch (Exception e) {
						LogUtil.error(LOGGER, "redis错误,e:{}", e);
					}
				}

			}
		} catch (Exception e) {
			LogUtil.info(LOGGER, "城市字典缓存错误,e:{}", e);
		}

		if (Check.NuNMap(cityMap)) {
			return null;
		} else {
			return cityMap.get(cityCode);
		}
	}
	
	  /**
     * 参数转化
     * @param uid
     * @param header
     * @param serverIp
     * @return
     */
    private CustomerLocationEntity transParams2Entity(final String uid,final Header header,final String serverIp){
    	if (Check.NuNObj(header)){
            return null;
        }
    	
    	CustomerLocationEntity customerLocationEntity = new CustomerLocationEntity();
        customerLocationEntity.setFid(UUIDGenerator.hexUUID());
        customerLocationEntity.setUid(uid);
        customerLocationEntity.setAppName(header.getAppName());
		customerLocationEntity.setChannelName(header.getChannelName());
		customerLocationEntity.setOsVersion(header.getOsVersion());
		customerLocationEntity.setImei(header.getImei());
        customerLocationEntity.setImsi(header.getImsi());
        Long ip = null;
        String deviceIP = header.getDeviceIP();
        if (!Check.NuNStr(serverIp)){
            //当服务获取ip直接获取当前的服务ip
            deviceIP = serverIp;
        }
        if (!IpUtil.checkIp(deviceIP)){
            LogUtil.info(LOGGER,"当前的ip异常,location info uid:{},header:{},deviceIP:{}" , uid, JsonEntityTransform.Object2Json(header),deviceIP);
            return null;
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
        customerLocationEntity.setVersionCode(header.getVersionCode());
        customerLocationEntity.setCityCode(header.getLocationCityCode());
        customerLocationEntity.setCityName(header.getLocationCityName());
    	
    	return customerLocationEntity;
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
     * 
     * 最新房源
     *
     * @author yd
     * @created 2017年6月2日 上午11:46:27
     *
     * @param request
     * @param response
     * @return
     */
    public DataTransferObject lasthouse(HttpServletRequest request,HttpServletResponse response) {
        DataTransferObject dto = null;
        String jsonRst = null;
        try {
            //获取搜索结果
            jsonRst =searchService.getNewHouseLst(defaultIconSize);
            dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
        } catch (Exception e) {
            dto = new DataTransferObject();
            LogUtil.error(LOGGER, "jsonpLasthouse e:{}", e);
        }
        return dto;
    }
}
