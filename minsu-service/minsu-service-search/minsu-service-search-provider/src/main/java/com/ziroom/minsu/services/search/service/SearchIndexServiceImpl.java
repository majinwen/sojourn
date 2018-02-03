package com.ziroom.minsu.services.search.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import com.ziroom.minsu.valenum.house.*;
import com.ziroom.minsu.valenum.search.SoreWeightEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.constant.BaseDataConstant;
import com.ziroom.minsu.services.basedata.dao.BasedateDbInfoDao;
import com.ziroom.minsu.services.basedata.dao.HotRegionDbInfoDao;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.BaseConstant;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.IpUtil;
import com.ziroom.minsu.services.common.utils.MapDistanceUtil;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.dao.CustomerDbDao;
import com.ziroom.minsu.services.evaluate.dao.EvaluateDbDao;
import com.ziroom.minsu.services.house.dao.HouseDbInfoDao;
import com.ziroom.minsu.services.message.dao.MessageDbInfoDao;
import com.ziroom.minsu.services.order.dao.OrderDbInfoDao;
import com.ziroom.minsu.services.search.dto.CreatIndexRequest;
import com.ziroom.minsu.services.search.dto.HouseCurrentStatsDto;
import com.ziroom.minsu.services.search.dto.LeaseCalendarDto;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.services.search.entity.QueryRequest;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.search.vo.AdHouseInfo;
import com.ziroom.minsu.services.search.vo.BedNumVo;
import com.ziroom.minsu.services.search.vo.BrandLandlordVo;
import com.ziroom.minsu.services.search.vo.CustomerDbInfoVo;
import com.ziroom.minsu.services.search.vo.EvaluateDbInfoVo;
import com.ziroom.minsu.services.search.vo.HotRegionVo;
import com.ziroom.minsu.services.search.vo.HouseDbInfoVo;
import com.ziroom.minsu.services.search.vo.HouseInfo;
import com.ziroom.minsu.services.search.vo.HousePicVo;
import com.ziroom.minsu.services.search.vo.HousePriceVo;
import com.ziroom.minsu.services.search.vo.WeightEvalVo;
import com.ziroom.minsu.services.search.vo.WeightMessageVo;
import com.ziroom.minsu.services.search.vo.WeightOrderNumVo;
import com.ziroom.minsu.services.solr.common.IndexService;
import com.ziroom.minsu.services.solr.common.QueryService;
import com.ziroom.minsu.services.solr.constant.SolrConstant;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.valenum.base.RegionTypeEnum;
import com.ziroom.minsu.valenum.common.WeekEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.search.IconPicTypeEnum;
import com.ziroom.minsu.valenum.search.SearchDataSourceTypeEnum;

/**
 * <p>创建索引</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/6.
、 * @version 1.0
 * @since 1.0
 */
@Service(value = "search.searchIndexServiceImpl")
public class SearchIndexServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchIndexServiceImpl.class);

    @Resource(name = "search.houseDbInfoDao")
    private HouseDbInfoDao houseDbInfoDao;

    @Resource(name="search.evaluateDbDao")
    private EvaluateDbDao evaluateDbDao;

    @Resource(name = "search.orderDbInfoDao")
    private OrderDbInfoDao orderDbInfoDao;

    @Resource(name = "search.hotRegionDbInfoDao")
    private HotRegionDbInfoDao hotRegionDbInfoDao;


    @Resource(name="search.basedateDbInfoDao")
    private BasedateDbInfoDao basedateDbInfoDao;

    @Resource(name="search.messageDbInfoDao")
    private MessageDbInfoDao messageDbInfoDao;

    @Resource(name = "search.customerDbDao")
    private CustomerDbDao customerDbDao;

    @Resource(name = "search.queryService")
    QueryService queryService;

    @Resource(name="search.indexService")
    private IndexService indexService;

    @Resource(name = "search.messageSource")
    private MessageSource messageSource;


    @Value("#{'${pic_base_addr_minsu}'.trim()}")
    private String picBaseAddrMona;


    @Value("#{'${default_head_size}'.trim()}")
    private String defaultHeadSize;

    @Value("#{'${default_head_url}'.trim()}")
    private String defaultHeadUrl;
    
    @Value("#{'${static_base_url}'.trim()}")
    private String staticBaseUrl;

    @Value("#{'${default_pic_size}'.trim()}")
    private String defaultPicSize;

    private Map<String, Integer> cityIndexMap = new HashMap<String, Integer>();

	@Resource(name="basedata.zkSysService")
	private ZkSysService zkSysService;
    
    //当前商圈的热度
    private Map<String,Integer>  reginCurrentHotMap = new HashMap<>();    
    //当前商圈的热度更新时间
    private Date  reginCurrentHotMapUpdateTime = null;
     
    //更新缓存数据的时间（小时）
    private int updateCache=4;
    
    //字典緩存
    private Map<String, Object> dicCacheMap = new HashMap<>();
    //字典緩存更新時間
    private Map<String, Date> dicCacheFreshDateMap = new HashMap<>();
    
    //分组字典
    private Map<String,Object> groupByMap = new HashMap<>(); 
    
    
    
	/**
	 * 更新当前商圈的热度
	 */
	private void updateCurrentReginHotMap() {

		Thread td = new Thread(new Runnable() {

			@Override
			public void run() {

				Map<String, Integer> reginCurrentHotMapTmp = new HashMap<>();

				List<ConfCityEntity> cityList = basedateDbInfoDao.getAllCityList();
				if (!Check.NuNCollection(cityList)) {
					for (ConfCityEntity city : cityList) {
						List<HotRegionVo> regionList = hotRegionDbInfoDao
								.getHotRegionListByCityEnumStatus(city.getCode());
						if (!Check.NuNCollection(regionList)) {

							QueryRequest request = new QueryRequest();
							request.setPageSize(50);

							for (HotRegionVo region : regionList) {

								Map<String, Object> filterQueries = new HashMap<String, Object>();
								filterQueries.put("cityCode", region.getCityCode());
								filterQueries.put("hotRegin", region.getRegionName());
								request.setFilterQueries(filterQueries);

								Map<String, Object> par = new HashMap<>();
								par.put("picSize", defaultPicSize);
								par.put("iconType", IconPicTypeEnum.middle.getCode());
								par.put("iconBaseUrl", staticBaseUrl);

								int hot = 0;
								int page = 1;
								boolean end = true;
								do {
									request.setPageIndex(page);
									QueryResult result = queryService.query(SolrCore.m_house_info, par, request);
									if (!Check.NuNObj(result) && !Check.NuNObj(result.getValue())) {
										List<HouseInfoEntity> houseList = (List<HouseInfoEntity>) result.getValue();
										if (!Check.NuNCollection(houseList)) {
											for (HouseInfoEntity houseInfoEntity : houseList) {
												HouseCurrentStatsDto statsDto = new HouseCurrentStatsDto();
												statsDto.setHouseFid(houseInfoEntity.getFid());
												statsDto.setRentWay(houseInfoEntity.getRentWay());
												hot += houseDbInfoDao.getHouseCurrentHot(statsDto);
											}
											end = false;
											page++;
										} else {
											end = true;
										}
									} else {
										end = true;
									}

								} while (!end);

								reginCurrentHotMapTmp.put(region.getRegionName(), hot);

							}

						}
					}
				}

				reginCurrentHotMap = reginCurrentHotMapTmp;
				reginCurrentHotMapUpdateTime = new Date();
			}

		});

		if (Check.NuNObj(reginCurrentHotMapUpdateTime)) {
			td.run();
		} else if (!Check.NuNObj(reginCurrentHotMapUpdateTime)
				&& new Date().getTime() - reginCurrentHotMapUpdateTime.getTime() >= updateCache * 60 * 60 * 1000) {
			td.start();
		}
	}

    /**
     * 获取当前的城市的有效半径
     * @return
     */
    public Integer getKm(){
        Integer km = hotRegionDbInfoDao.getKm();
        if(Check.NuNObj(km)){
            km = 3;
        }
       return km;
    }
    
    
	/**
	 * 
	 * 获取城市map
	 *
	 * @author zl
	 * @created 2017年3月2日 上午10:49:56
	 *
	 * @return
	 */
	private Map<String, String> getAllCityMap() {
		
		String key = "AllCityMap";
    	Date date = dicCacheFreshDateMap.get(key);
    	
    	Map<String, String> cityMap = null;
    	if(!Check.NuNObj(date) && new Date().getTime() - date.getTime() < updateCache * 60 * 60 * 1000 ){
    		cityMap = (Map<String, String>) dicCacheMap.get(key); 
    	} 
    	
    	if (Check.NuNMap(cityMap)) {
			cityMap = basedateDbInfoDao.getAllCityMap();
			if (!Check.NuNMap(cityMap)) {
				dicCacheMap.put(key, cityMap);
    			dicCacheFreshDateMap.put(key, new Date());
			}
			
		} 
    	if (Check.NuNObj(cityMap)) {
    		cityMap = new HashMap<>();
    	}
		return cityMap;
	}
    
    
	/**
	 * 给房客开通的城市code
	 *
	 * @author zl
	 * @created 2017年3月2日 上午11:09:46
	 *
	 * @return
	 */
	private Set<String> getOpenCityCode2TenantSet() {
		
		String key = "OpenCityCode2TenantSet";
    	Date date = dicCacheFreshDateMap.get(key);
    	
    	Set<String> openCityTenantCode = null;
    	if(!Check.NuNObj(date) && new Date().getTime() - date.getTime() < updateCache * 60 * 60 * 1000 ){
    		openCityTenantCode = (Set<String>) dicCacheMap.get(key);  
    	}  
		if (Check.NuNCollection(openCityTenantCode)) {
			List<ConfCityEntity> openCityTenant = basedateDbInfoDao.getOpenCityTenant();
			openCityTenantCode = new HashSet<>();
			if (!Check.NuNCollection(openCityTenant)) {
				for (ConfCityEntity confCityEntity : openCityTenant) {
					openCityTenantCode.add(confCityEntity.getCode());
				}
			}
			if (!Check.NuNCollection(openCityTenantCode)) {
				dicCacheMap.put(key, openCityTenantCode);
    			dicCacheFreshDateMap.put(key, new Date());
			}
			
		}
		if(Check.NuNObj(openCityTenantCode)){
			openCityTenantCode = new HashSet<>();
		}
		return openCityTenantCode;
	}
    
    
	/**
	 * 房源类型map
	 *
	 * @author zl
	 * @created 2017年3月2日 上午11:17:13
	 *
	 * @return
	 */
	private Map<Integer, String> getHouseTypeMap() {
		
		String key = "HouseTypeMap";
    	Date date = dicCacheFreshDateMap.get(key);
    	
    	Map<Integer, String> houseTypeMap = null;
    	if(!Check.NuNObj(date) && new Date().getTime() - date.getTime() < updateCache * 60 * 60 * 1000 ){
    		houseTypeMap = (Map<Integer, String>) dicCacheMap.get(key);  
    	} 
		if (Check.NuNMap(houseTypeMap)) {
			List<DicItemEntity> dicItemEntityList = basedateDbInfoDao.getDicItemListByCodeAndTemplate(
					ProductRulesEnum.ProductRulesEnum001.getValue(), BaseDataConstant.HOUSE_RENTWAY_BY_HOUSE);
			houseTypeMap = new HashMap<Integer, String>();
			if (!Check.NuNCollection(dicItemEntityList)) {
				for (DicItemEntity dicItemEntity : dicItemEntityList) {
					houseTypeMap.put(Integer.valueOf(dicItemEntity.getItemValue()), dicItemEntity.getShowName());
				}
			}
			
			if (!Check.NuNMap(houseTypeMap)) {
    			dicCacheMap.put(key, houseTypeMap);
    			dicCacheFreshDateMap.put(key, new Date());
    		}
			
		}
		if (Check.NuNObj(houseTypeMap)) {
			houseTypeMap = new HashMap<>();
		}
		
		return houseTypeMap;
	}

    
	/**
	 * 获取出租日历最长月数
	 *
	 * @author zl
	 * @created 2017年3月2日 上午11:29:44
	 *
	 * @return
	 */
	private int getCalendarMounthLimitInt() {
		String key = "CalendarMounthLimitInt";
		Date date = dicCacheFreshDateMap.get(key);

		Integer calendarMounthLimit = null;
		if (!Check.NuNObj(date) && new Date().getTime() - date.getTime() < updateCache * 60 * 60 * 1000) {
			calendarMounthLimit = (Integer) dicCacheMap.get(key);
		}
		if (Check.NuNObj(calendarMounthLimit)) {
			// 出租日历最长月数
			List<DicItemEntity> calendarLimit = basedateDbInfoDao.getDicItemListByCodeAndTemplate(
					ProductRulesEnum.ProductRulesEnum0018.getValue(), BaseDataConstant.HOUSE_RENTWAY_BY_HOUSE);
			DicItemEntity calendarLimitDic = null;
			if (!Check.NuNCollection(calendarLimit)) {
				calendarLimitDic = calendarLimit.get(0);
			}
			if (!Check.NuNObj(calendarLimitDic) && !Check.NuNStr(calendarLimitDic.getItemValue())) {
				try {
					calendarMounthLimit = Integer.valueOf(calendarLimitDic.getItemValue());
				} catch (Exception e) {
					LogUtil.error(LOGGER, "出租日历最大月数转化异常，e={}", JsonEntityTransform.Object2Json(calendarLimitDic));
				}
				if (!Check.NuNObj(calendarMounthLimit)) {
					dicCacheMap.put(key, calendarMounthLimit);
					dicCacheFreshDateMap.put(key, new Date());
				}
			}

		}
		if (Check.NuNObj(calendarMounthLimit)) {
			calendarMounthLimit = 6;
		}
		return calendarMounthLimit;
	} 
    
    /**
     * 获取夹心日期最长天数 
     *
     * @author zl
     * @created 2017年3月2日 上午11:39:16
     *
     * @return
     */
    private int getPriorityDateLimit(){
    	String key = "PriorityDateLimit";
    	Date date = dicCacheFreshDateMap.get(key);
    	Integer priorityDateLimit = null;
    	if(!Check.NuNObj(date) && new Date().getTime() - date.getTime() < updateCache * 60 * 60 * 1000 ){
    		priorityDateLimit = (Integer) dicCacheMap.get(key);  
    	}
    	if (Check.NuNObj(priorityDateLimit)) {
    		List<DicItemEntity> jianxiDays =  basedateDbInfoDao.getDicItemListByCodeAndTemplate(ProductRulesEnum0019.ProductRulesEnum0019003.getValue(),null);
            if (!Check.NuNCollection(jianxiDays) && !Check.NuNObj(jianxiDays.get(0))) {
            	priorityDateLimit = ValueUtil.getintValue(jianxiDays.get(0).getItemValue());
            	dicCacheMap.put(key, priorityDateLimit);
            	dicCacheFreshDateMap.put(key, new Date());
    		}             
		} 
    	if (Check.NuNObj(priorityDateLimit)) {
        	priorityDateLimit =ProductRulesEnum0019.ProductRulesEnum0019003.getDayNum();
        }
    	return priorityDateLimit;
    }
    
    /**
     * 获取特色房源类型 
     *
     * @author zl
     * @created 2017年3月2日 上午11:50:07
     *
     * @return
     */
    private Map<String, List<Integer>> getFeatureHouseMap(){
    	
    	String key = "FeatureHouseMap";
    	Date date = dicCacheFreshDateMap.get(key);
    	Map<String, List<Integer>> cityFeatureHouseMap = null;
    	if(!Check.NuNObj(date) && new Date().getTime() - date.getTime() < updateCache * 60 * 60 * 1000 ){
    		cityFeatureHouseMap = (Map<String, List<Integer>>) dicCacheMap.get(key);  
    	} 
    	
    	if (Check.NuNMap(cityFeatureHouseMap)) {
    		cityFeatureHouseMap = basedateDbInfoDao.getAllCityFeatureHouseTypes();
    		if (!Check.NuNMap(cityFeatureHouseMap)) {
    			dicCacheMap.put(key, cityFeatureHouseMap);
    			dicCacheFreshDateMap.put(key, new Date());
    		}
    	}     
    	if (Check.NuNObj(cityFeatureHouseMap)) {
    		cityFeatureHouseMap = new HashMap<>();
    	}
    	return cityFeatureHouseMap;    	
    }
    
    
    /**
     * 获取当前城市的热度
     *
     * @author zl
     * @created 2017年3月2日 上午11:56:50
     *
     * @return
     */
    private Map<String,Integer> getAllCityCurrentHotMap(){
    	String key = "AllCityCurrentHotMap";
    	Date date = dicCacheFreshDateMap.get(key);
    	Map<String,Integer> cityCurrentHotMap = null;
    	if(!Check.NuNObj(date) && new Date().getTime() - date.getTime() < updateCache * 60 * 60 * 1000 ){
    		cityCurrentHotMap = (Map<String, Integer>) dicCacheMap.get(key); 
    	} 	 
    	if (Check.NuNMap(cityCurrentHotMap)) {
    		cityCurrentHotMap = houseDbInfoDao.getAllCityCurrentHot(new HouseCurrentStatsDto());
    		if (!Check.NuNMap(cityCurrentHotMap)) {
    			dicCacheMap.put(key, cityCurrentHotMap);
    			dicCacheFreshDateMap.put(key, new Date());
    		}
    	} 
    	if (Check.NuNObj(cityCurrentHotMap)) {
    		cityCurrentHotMap = new HashMap<>();
    	}
    	
    	return cityCurrentHotMap;  
    }
    
    /**
     * 
     * 查询房东所属的品牌列表
     *
     * @author zl
     * @created 2017年4月1日 下午5:01:00
     *
     * @return
     */
	private Map<String, String> getLandLordBrandMap() {
		
		String key = "LandLordBrandMap";
    	Date date = dicCacheFreshDateMap.get(key);
    	
    	Map<String, String> landLordBrandMap = null;
    	if(!Check.NuNObj(date) && new Date().getTime() - date.getTime() < updateCache * 60 * 60 * 1000 ){
    		landLordBrandMap = (Map<String, String>) dicCacheMap.get(key);  
    	} 
		if (Check.NuNMap(landLordBrandMap)) {
			
			List<BrandLandlordVo> landLordList = customerDbDao.getBrandLandLordList(null);
			 
			landLordBrandMap = new HashMap<String, String>();
			if (!Check.NuNCollection(landLordList)) {
				 for (BrandLandlordVo brandLandlordVo : landLordList) {
					 landLordBrandMap.put(brandLandlordVo.getLandlordUid(), brandLandlordVo.getBrandsn());
					 
				}
			}
			
			if (!Check.NuNMap(landLordBrandMap)) {
    			dicCacheMap.put(key, landLordBrandMap);
    			dicCacheFreshDateMap.put(key, new Date());
    		}
			
		}
		if (Check.NuNObj(landLordBrandMap)) {
			landLordBrandMap = new HashMap<>();
		}
		
		return landLordBrandMap;
	}

    
   
    

    /**
     * 全量的刷新索引，每次查询的条数是可以控制的
     * @author afi
     */
    public void creatAllIndex(){

        LogUtil.info(LOGGER,"开始全量创建索引。。。。。。");
        Date t0 = new Date();
       
        LogUtil.info(LOGGER,"同步商圈景点当前的热度开始。。。");
        updateCurrentReginHotMap();
        Date t1 = new Date();
        LogUtil.info(LOGGER,"同步商圈景点当前的热度结束，共耗时{}ms",(t1.getTime()-t0.getTime()));        
        //重新计算
        cityIndexMap = new HashMap<String, Integer>();
        //获取商圈景点半径
        Integer km = getKm();
        Map<String,List<HotRegionVo>>  reginMap = new HashMap<>();
        Map<String,Object>  baseMap = new HashMap<>();

        baseMap.put(AdHouseInfo.ad,AdHouseInfo.ad);
        //虚拟整租的集合
        Set<String> virtualSet = new HashSet<>();
        
        groupByMap = new HashMap<>();       		
        
        CreatIndexRequest pageRequest = new CreatIndexRequest();
        pageRequest.setLimit(SolrConstant.create_index_page_limit);
        PagingResult<HouseDbInfoVo> pagingResult = houseDbInfoDao.getHouseDbInfoForPage(pageRequest);
        //获取第一页的信息
        List<HouseDbInfoVo> houseDbInfoVoList = pagingResult.getRows();
        if(Check.NuNObj(houseDbInfoVoList)){
            return;
        }
        
        //城市map
        Map<String, String> cityMap= getAllCityMap();
        
        //处理第一页的信息
        dealSearchIndex(houseDbInfoVoList, reginMap,baseMap, virtualSet,km,cityMap);
        Integer length = ValueUtil.getintValue(pagingResult.getTotal());

        LogUtil.info(LOGGER, "需要创建索引的条数为：{}",pagingResult.getTotal());
        LogUtil.info(LOGGER, "分页数为：{}",length);

        int page = ValueUtil.getPage(length, SolrConstant.create_index_page_limit);
        for(int i=1;i<=page;i++){
            LogUtil.info(LOGGER, "开始创建索引第{}页",i);
            //从第二页开始，循环处理之后的数据信息
            pageRequest.setPage(i);
            dealSearchIndex(houseDbInfoDao.getHouseDbInfoForPage(pageRequest).getRows(),reginMap,baseMap,virtualSet,km,cityMap);
        }
        //更新索引完成，删掉昨天的索引信息，依照今天的为准
        //String delIndex = "createTime:{* TO "+ DateUtil.dateFormat(new Date()) +"}";
        LogUtil.info(LOGGER, "全量创建索引完毕。。。。。。");

        LogUtil.info(LOGGER, "开始删除索引库的无效索引。。。。。。");
        try {
            this.dealAdHouseIndex(baseMap);            
        }catch (Exception e){
            LogUtil.error(LOGGER, "处理广告位置失败");
        }

        //删除索引差
        this.delIndexNotExit(SolrCore.m_house_info, virtualSet);
        LogUtil.info(LOGGER,"全量创建索引结束，共耗时{}ms",(new Date().getTime()-t0.getTime()));  
    }



    /**
     * 处理每个城市前10的信息
     * @param baseMap
     */
    private void dealAdHouseIndex(Map<String,Object> baseMap){
        LogUtil.info(LOGGER, "开始处理广告位置。。。。。。");
        //当前的刷新需要处理广告信息
        if (!baseMap.containsKey(AdHouseInfo.ad)){
            return;
        }
        AdHouseInfo adHouseInfo = (AdHouseInfo)baseMap.get(AdHouseInfo.adHouseInfo);
        if (Check.NuNObj(adHouseInfo)){
            return;
        }
        Map<String,Map<String,Object>> cityMap = adHouseInfo.getCityMap();
        if (Check.NuNMap(cityMap)){
            return;
        }
        for (Map.Entry entry:cityMap.entrySet()){
            List<Object> cityHouse = new ArrayList<>();
            
            String cityCode = (String) entry.getKey();
            LogUtil.info(LOGGER, "【广告:{}】。。。。。。",cityCode);
            Map<String,Object> city = (Map<String,Object>)entry.getValue();
            //获取房源信息
            Map<String,HouseInfo> houseMap = (Map<String,HouseInfo>)city.get(AdHouseInfo.house_map);
            if (Check.NuNMap(houseMap)){
                continue;
            }
            
            List<Map.Entry<String, HouseInfo>> houseMapList = new ArrayList<Map.Entry<String, HouseInfo>>(houseMap.entrySet());  
            
            Collections.sort(houseMapList, new Comparator<Map.Entry<String, HouseInfo>>() {  
            	  
                @Override  
                public int compare(Entry<String, HouseInfo> arg0,  
                        Entry<String, HouseInfo> arg1) {  
                    return arg1.getValue().getWeights().compareTo(arg0.getValue().getWeights());  
                }  
            });  
            
            Map<String, HouseInfo> newHouseMap = new LinkedHashMap<String, HouseInfo>();  
            Iterator<Map.Entry<String, HouseInfo>> iter = houseMapList.iterator();
    		Map.Entry<String, HouseInfo> tmpEntry = null;
    		while (iter.hasNext()) {
    			tmpEntry = iter.next();
    			newHouseMap.put(tmpEntry.getKey(), tmpEntry.getValue());  
    		} 
            
            int size = 20;
            for (Map.Entry ele:newHouseMap.entrySet()){
                HouseInfo houseInfo = (HouseInfo)ele.getValue();
                
                if (!Check.NuNStr((houseInfo.getHouseQualityGrade())) && QualityGradeEnum.GRADE_C.getCode().equalsIgnoreCase(houseInfo.getHouseQualityGrade()))  {
	            	houseInfo.setAdIndex(-5);
	            }else {					
	            	if (size>0) {					
	            		houseInfo.setAdIndex(size); 
	            		size --;
	            	}else {
	            		houseInfo.setAdIndex(0); 
	            	}
				}
                 
                cityHouse.add(houseInfo);
                
            }
            int newCount = ValueUtil.getintValue(city.get(AdHouseInfo.city_new_count));
            if (newCount <= 2){
                HouseInfo frist = (HouseInfo)city.get(AdHouseInfo.frist_key);
                HouseInfo second = (HouseInfo)city.get(AdHouseInfo.second_key);
                if (!Check.NuNObj(frist)){
                    frist.setAdIndex(new Random().nextInt(houseMap.size()));
                    cityHouse.add(frist);
                }
                if (!Check.NuNObj(second)){
                    second.setAdIndex(new Random().nextInt(houseMap.size()));
                    cityHouse.add(second);
                }
            }
            
            //最后依次处理场景推荐房源
            dealRecCommHouseIndex(cityHouse,cityCode, adHouseInfo);            
            
            indexService.batchCreateIndex(SolrCore.m_house_info, cityHouse);
        }
        
        //处理剩余城市的场景推荐房源
        dealRecRemainHouseIndex(adHouseInfo);
    }
    
    
    
    /**
     * 
     * 获取个性化标签
     *
     * @author zl
     * @created 2017年3月21日 下午4:39:45
     *
     * @param houseBaseFid
     * @param roomFid
     * @return
     */
    private List<String> getTagList(String houseBaseFid,String roomFid){
    	
    	List<String> tags =new ArrayList<>();
    	List<String> fids =  houseDbInfoDao.getHouseTagFids(houseBaseFid, roomFid);
    	if (!Check.NuNCollection(fids)) {
    		tags = basedateDbInfoDao.findByConfTagFidsList(fids);
		}
    	LogUtil.debug(LOGGER, "######--houseBaseFid={},tagFids={},tags={}", houseBaseFid,JsonEntityTransform.Object2Json(fids),JsonEntityTransform.Object2Json(tags));
    	return tags;
    }
    
    
    /**
     * 处理推荐房源 
     *
     * @author zl
     * @created 2016年12月7日 下午3:12:40
     *
     */
    private void dealRecCommHouseIndex(List<Object> indexHouse,String cityCode,AdHouseInfo adHouseInfo){
         
        if (Check.NuNStr(cityCode) ||Check.NuNObj(adHouseInfo) || Check.NuNCollection(indexHouse)){
            return;
        }
        
        //热门商圈景点推荐
        Map<String,List<HouseInfo>> recReginHotCityMap = adHouseInfo.getRecReginHotCityMap();
        if(!Check.NuNMap(recReginHotCityMap) && !Check.NuNCollection(recReginHotCityMap.get(cityCode))){
        	List<HouseInfo> reginHotHouse = recReginHotCityMap.get(cityCode);
        	List<Object> houses = new ArrayList<>();
        	houses.addAll(indexHouse);
        	
        	for(int i=0;i<indexHouse.size();i++){
        		HouseInfo houseInfo1 = (HouseInfo) indexHouse.get(i);
        		
        		Iterator<HouseInfo> iterator2 = reginHotHouse.iterator();
            	while(iterator2.hasNext()){
            		HouseInfo houseInfo2 = iterator2.next();
            		if (houseInfo1.getHouseId().equals(houseInfo2.getHouseId())) {
    					if (!Check.NuNStr(houseInfo1.getRoomId()) && !Check.NuNStr(houseInfo2.getRoomId()) && houseInfo1.getRoomId().equals(houseInfo2.getRoomId())) {
    						houseInfo1.setCurrentReginMaxHotIndex(houseInfo2.getCurrentReginMaxHotIndex());
    						indexHouse.set(i, houseInfo1);
    						iterator2.remove();
    					}else if (Check.NuNStr(houseInfo1.getRoomId()) && Check.NuNStr(houseInfo2.getRoomId())) {
    						houseInfo1.setCurrentReginMaxHotIndex(houseInfo2.getCurrentReginMaxHotIndex());
    						indexHouse.set(i, houseInfo1);
    						iterator2.remove();
    					}
    				}
            	}
        	}
        	
        	if (!Check.NuNCollection(reginHotHouse)) {
        		indexHouse.addAll(reginHotHouse);
			}
        	adHouseInfo.updateRecReginHotCityMap(cityCode, null);
        	
        }
        
        //特色房源推荐
        Map<String,List<HouseInfo>> recFeatureHouseCityMap = adHouseInfo.getRecFeatureHouseCityMap();
        if(!Check.NuNMap(recFeatureHouseCityMap) && !Check.NuNCollection(recFeatureHouseCityMap.get(cityCode))){
        	List<Object> houses = new ArrayList<>();
        	houses.addAll(indexHouse);
        	
        	List<HouseInfo> featureHouse = recFeatureHouseCityMap.get(cityCode);
        	
        	for(int i=0;i<indexHouse.size();i++){
        		HouseInfo houseInfo1 = (HouseInfo) indexHouse.get(i);
        		
        		Iterator<HouseInfo> iterator2 = featureHouse.iterator();
            	while(iterator2.hasNext()){
            		HouseInfo houseInfo2 = iterator2.next();
            		if (houseInfo1.getHouseId().equals(houseInfo2.getHouseId())) {
    					if (!Check.NuNStr(houseInfo1.getRoomId()) && !Check.NuNStr(houseInfo2.getRoomId()) && houseInfo1.getRoomId().equals(houseInfo2.getRoomId())) {
    						houseInfo1.setFeatureHouseIndex(houseInfo2.getFeatureHouseIndex());
    						indexHouse.set(i, houseInfo1);
    						iterator2.remove();
    					}else if (Check.NuNStr(houseInfo1.getRoomId()) && Check.NuNStr(houseInfo2.getRoomId())) {
    						houseInfo1.setFeatureHouseIndex(houseInfo2.getFeatureHouseIndex());
    						indexHouse.set(i, houseInfo1);
    						iterator2.remove();
    					}
    				}
            	}
        	}
        	if (!Check.NuNCollection(featureHouse)) {
        		indexHouse.addAll(featureHouse);
        	}
        	adHouseInfo.updateRecFeatureHouseCityMap(cityCode, null);
        }
        
   }
    
   
   /**
    * 处理遗留的推荐房源 
    *
    * @author zl
    * @created 2016年12月7日 下午3:35:39
    *
    * @param adHouseInfo
    */
   private void dealRecRemainHouseIndex(AdHouseInfo adHouseInfo){
	   if (Check.NuNObj(adHouseInfo)){
           return;
       }
	   List<Object> list = new ArrayList<>();
	   
	   //热门商圈景点推荐
       Map<String,List<HouseInfo>> recReginHotCityMap = adHouseInfo.getRecReginHotCityMap();
       if(!Check.NuNMap(recReginHotCityMap)){
    	   Iterator<Map.Entry<String,List<HouseInfo>>> iterator = recReginHotCityMap.entrySet().iterator();
    	   while(iterator.hasNext()){
    		   Entry<String,List<HouseInfo>> entry = iterator.next();
    		   if (!Check.NuNCollection(entry.getValue())) {
    			   list.addAll(entry.getValue());
    		   }
    	   }
       }
       
       //特色房源推荐
       Map<String,List<HouseInfo>> recFeatureHouseCityMap = adHouseInfo.getRecFeatureHouseCityMap();
       if(!Check.NuNMap(recFeatureHouseCityMap)){
    	   Iterator<Map.Entry<String,List<HouseInfo>>> iterator = recFeatureHouseCityMap.entrySet().iterator();
    	   while(iterator.hasNext()){
    		   Entry<String,List<HouseInfo>> entry = iterator.next();
    		   if (!Check.NuNCollection(entry.getValue())) {
    			   list.addAll(entry.getValue());
    		   }
    	   }
       }
       
       indexService.batchCreateIndex(SolrCore.m_house_info, list);
       
   }
    

    /**
     * 删除索引除此之外的数据
     * @author afi
     * @param hasSet
     */
    private void delIndexNotExit(SolrCore solrCore,Set<String> hasSet){
        Set<String> indexIds = this.getAllDocIds();
        LogUtil.info(LOGGER, "当前索引的条数：{}", indexIds.size());
        indexIds.removeAll(hasSet);
        LogUtil.info(LOGGER, "更新的索引条数：{}", hasSet.size());
        List<String> list = new ArrayList<>();
        LogUtil.info(LOGGER,"开始删除无效索引，无效索引条数：{}",indexIds.size());
        list.addAll(indexIds);
        
        try {
			
        	Iterator<String> iterator = list.iterator();
        	while(iterator.hasNext()){
        		String id = iterator.next();
        		String houseId = id.split("_")[0];
        		Integer rentWay = ValueUtil.getintValue(id.split("_")[1]);
        		Integer status = null;
        		if (rentWay == RentWayEnum.HOUSE.getCode()) {
        			status = houseDbInfoDao.getHouseOrRoomStatus(houseId, null, rentWay);
        		}else if (rentWay == RentWayEnum.ROOM.getCode()) {
        			status = houseDbInfoDao.getHouseOrRoomStatus(null, houseId, rentWay);
        		}
        		
        		if (!Check.NuNObj(status) && status == HouseStatusEnum.SJ.getCode()) {
        			iterator.remove();
        		}
        		
        	}
		} catch (Exception e) {
			LogUtil.info(LOGGER,"删除无效索引，校验房源状态异常,e={}",e);
		}
        
        //删除历史索引信息
        indexService.deleteByIds(solrCore, list);
    }

    /**
     * 
     * 获取所有民宿房源id集合
     *
     * @author zhangyl
     * @created 2017年8月8日 下午4:43:39
     *
     * @return
     */
	private Set<String> getAllDocIds(){
        
        Map<String, Object> filterQueries = new HashMap<String, Object>();
        
        //区分solr文档里 民宿、自如驿
        filterQueries.put("dataSource", SearchDataSourceTypeEnum.minsu.getCode());
        
        Set<String> all = queryService.getAllIds(SolrCore.m_house_info, filterQueries);
        
        return all;
    }

    /**
     * 区域内的刷新房源信息
     * @param areaCode
     * @param dto
     */
    public void creatAllIndexByArea(String areaCode,DataTransferObject dto){

        LogUtil.info(LOGGER,"开始刷新当前区域的索引信息，当前的区域code：{}。。。。。。",areaCode);

        Map<String,List<HotRegionVo>>  reginMap = new HashMap<>();
        
        Map<String,Object>  baseMap = new HashMap<>();
        //获取商圈景点半径
        Integer km = getKm();
        //虚拟整租的集合
        Set<String> virtualSet = new HashSet<>();
        CreatIndexRequest pageRequest = new CreatIndexRequest();
        pageRequest.setLimit(SolrConstant.create_index_page_limit);
        pageRequest.setAreaCode(areaCode);
        PagingResult<HouseDbInfoVo> pagingResult = houseDbInfoDao.getHouseDbInfoForPage(pageRequest);

        if(pagingResult.getTotal() == 0){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.NOT_FOUND));
            return;
        }

        //获取第一页的信息
        List<HouseDbInfoVo> houseDbInfoVoList = pagingResult.getRows();
        if(Check.NuNObj(houseDbInfoVoList)){
            return;
        }
        
        //城市map
        Map<String, String> cityMap = getAllCityMap();
        //处理第一页的信息
        dealSearchIndex(houseDbInfoVoList, reginMap, baseMap,virtualSet, km,cityMap);
        Integer length = ValueUtil.getintValue(pagingResult.getTotal());
        int page = ValueUtil.getPage(length, SolrConstant.create_index_page_limit);
        for(int i=1;i<page;i++){
            //从第二页开始，循环处理之后的数据信息
            pageRequest.setPage(i);
            dealSearchIndex(houseDbInfoDao.getHouseDbInfoForPage(pageRequest).getRows(), reginMap,baseMap, virtualSet, km,cityMap);
        }


        LogUtil.info(LOGGER, "刷新区域的索引结束。。。。。。");
    }


    /**
     * 创建索引数据
     * @param houseDbInfoVoList
     * @param reginMap
     * @param virtualSet
     * @param km
     */
    public void dealSearchIndex(List<HouseDbInfoVo> houseDbInfoVoList,Map<String,List<HotRegionVo>>  reginMap,Map<String,Object>  baseMap,Set<String> virtualSet,Integer km,Map<String, String> cityMap){
        if(Check.NuNObj(houseDbInfoVoList)){
            return;
        }
        
        String houseFid_rentway_roomFid="";
        for (HouseDbInfoVo houseDbInfoVo : houseDbInfoVoList) {
        	houseFid_rentway_roomFid+=houseDbInfoVo.getHouseFid()+"_"+houseDbInfoVo.getRentWay()+"_"+houseDbInfoVo.getRoomFid()+",";
		}
        
        LogUtil.debug(LOGGER, "XXX要创建的房源：houseFid_rentway_roomFid={}", houseFid_rentway_roomFid);
        
        try{
            List<Object> houseInfoList = new ArrayList<>();
            
            
            //给房客开通的城市
//            Set<String> openCityTenantCode = getOpenCityCode2TenantSet();             
            
            //房源类型列表
            Map<Integer, String> houseTypeMap = getHouseTypeMap();
            
            //出租日历最长月数            
            int calendarMounthLimit = getCalendarMounthLimitInt();
            
            //特色房源
            Map<String, List<Integer>> cityFeatureHouseMap = getFeatureHouseMap();
            
            //当前城市的热度
            Map<String,Integer> cityCurrentHotMap = getAllCityCurrentHotMap();
            
            //房东品牌
            Map<String, String> landLordBrandMap = getLandLordBrandMap();
            
            LeaseCalendarDto leaseCalendarDto = new LeaseCalendarDto();
            //夹心日期最长天数
            Integer priorityDateLimit = getPriorityDateLimit();
            if (!Check.NuNObj(priorityDateLimit) && priorityDateLimit>0) {
        		leaseCalendarDto.setPriorityDateLimit(priorityDateLimit);
			}             
            
            Calendar calendar = Calendar.getInstance(); 
            calendar.add(Calendar.MONTH, calendarMounthLimit);
            
            HouseCurrentStatsDto statsDto = new HouseCurrentStatsDto();
            
            for(HouseDbInfoVo houseDbInfoVo:houseDbInfoVoList){
            	
//            	if(!openCityTenantCode.contains(houseDbInfoVo.getCityCode())){//未给房客开通
//            		LogUtil.info(LOGGER, "房源所在城市未给房客开通，houseFid：{},rentWay：{},roomFid：{}",houseDbInfoVo.getHouseFid(),houseDbInfoVo.getRentWay(),houseDbInfoVo.getRoomFid());
//            		continue;
//            	}
            	
            	dealGroupIndex(houseDbInfoVo);
            	
            	//特色房源
            	if (!Check.NuNMap(cityFeatureHouseMap) && !Check.NuNCollection(cityFeatureHouseMap.get(houseDbInfoVo.getCityCode()))) {
					if (cityFeatureHouseMap.get(houseDbInfoVo.getCityCode()).contains(houseDbInfoVo.getHouseType())) {
						houseDbInfoVo.setIsFeatureHouse(YesOrNoEnum.YES.getCode());
					}
				}
            	
                RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(houseDbInfoVo.getRentWay());
                if(rentWayEnum == null){
                    //记录log并继续
                    LogUtil.error(LOGGER, "rentWay is null the houseFid is:{}. and roomFid: {}",houseDbInfoVo.getHouseFid(),houseDbInfoVo.getRoomFid());
                    continue;
                }
                
                if (calendar.getTime().before(houseDbInfoVo.getTillDate())) {//设置日历截止时间
                	leaseCalendarDto.setEndDate(calendar.getTime());
    			}else{
    				leaseCalendarDto.setEndDate(houseDbInfoVo.getTillDate());
    			}  
                
                dealTop50ListHouse(houseDbInfoVo);                
                houseDbInfoVo.setTop50Title(houseDbInfoDao.getTop50ArticleTitle(houseDbInfoVo.getHouseFid(), null, rentWayEnum.getCode()));
                
                //靈活定價配置
                dealFlexiblePrice(houseDbInfoVo.getHouseFid(),houseDbInfoVo.getRoomFid(),houseDbInfoVo.getRentWay(),houseDbInfoVo);
                                               
                if(rentWayEnum.getCode() == RentWayEnum.HOUSE.getCode()){
                    //如果是整租
                    //1. 设置整租的服务信息
                    houseDbInfoVo.setServices(houseDbInfoDao.getHouseServices(houseDbInfoVo.getHouseFid()));
                    houseDbInfoVo.setOccupyDays(orderDbInfoDao.getLocksByHouseFid(houseDbInfoVo.getHouseFid()));
                    
                    leaseCalendarDto.setHouseBaseFid(houseDbInfoVo.getHouseFid());
                	leaseCalendarDto.setRentWay(RentWayEnum.HOUSE.getCode());
                	houseDbInfoVo.setPriorityDate(orderDbInfoDao.getPriorityDate(leaseCalendarDto));
                	
                	statsDto.setHouseFid(houseDbInfoVo.getHouseFid());
                	statsDto.setRentWay(RentWayEnum.HOUSE.getCode());
                	houseDbInfoVo.setCurrentHot(houseDbInfoDao.getHouseCurrentHot(statsDto));
                	                  
                    //長租折扣配置
                	houseDbInfoVo.setLongTermLeaseDiscount(houseDbInfoDao.getLongTermLeaseDiscountConf(houseDbInfoVo.getHouseFid(), null));
                   
                	houseDbInfoVo.setAcceptOrder60DaysCount(orderDbInfoDao.getAcceptOrder60DaysCount(houseDbInfoVo.getHouseFid(), null, rentWayEnum.getCode()));
                	houseDbInfoVo.setOrder60DaysCount(orderDbInfoDao.getOrder60DaysCount(houseDbInfoVo.getHouseFid(), null, rentWayEnum.getCode()));
                   
                }else if(rentWayEnum.getCode() == RentWayEnum.ROOM.getCode()){
                    //如果是合租 获取合租的兄弟房子
                    houseDbInfoVo.setBrotherRooms(houseDbInfoDao.getRoomsByHouseFid(houseDbInfoVo.getHouseFid()));
                    houseDbInfoVo.setServices(houseDbInfoDao.getHouseServices(houseDbInfoVo.getHouseFid()));
                    houseDbInfoVo.setOccupyDays(orderDbInfoDao.getLocksByRoomFid(houseDbInfoVo.getRoomFid()));
                    
                    leaseCalendarDto.setHouseRoomFid(houseDbInfoVo.getRoomFid());
                	leaseCalendarDto.setRentWay(RentWayEnum.ROOM.getCode());
                	houseDbInfoVo.setPriorityDate(orderDbInfoDao.getPriorityDate(leaseCalendarDto));
                	
                	statsDto.setHouseFid(houseDbInfoVo.getRoomFid());
                	statsDto.setRentWay(RentWayEnum.ROOM.getCode());
                	houseDbInfoVo.setCurrentHot(houseDbInfoDao.getHouseCurrentHot(statsDto));
                	                   
                    //長租折扣配置
                    houseDbInfoVo.setLongTermLeaseDiscount(houseDbInfoDao.getLongTermLeaseDiscountConf(houseDbInfoVo.getHouseFid(), houseDbInfoVo.getRoomFid()));
                    
                    houseDbInfoVo.setAcceptOrder60DaysCount(orderDbInfoDao.getAcceptOrder60DaysCount(houseDbInfoVo.getHouseFid(), houseDbInfoVo.getRoomFid(), rentWayEnum.getCode()));
                	houseDbInfoVo.setOrder60DaysCount(orderDbInfoDao.getOrder60DaysCount(houseDbInfoVo.getHouseFid(), houseDbInfoVo.getRoomFid(), rentWayEnum.getCode()));

					if(!Check.NuNObj(houseDbInfoVo.getRoomMinDay())){
						houseDbInfoVo.setMinDay(houseDbInfoVo.getRoomMinDay());
					}

					//查询新版本开关
					Integer isOpenNew=0;
					String isOpenNewS =zkSysService.getZkSysValue(EnumMinsuConfig.minsu_isOpenNewVersion.getType(), EnumMinsuConfig.minsu_isOpenNewVersion.getCode());
					if(!Check.NuNStr(isOpenNewS)){
						isOpenNew=Integer.valueOf(isOpenNewS);
					}
					if(isOpenNew==1){
						if(!Check.NuNObj(houseDbInfoVo.getRoomMinDay())){
							houseDbInfoVo.setMinDay(houseDbInfoVo.getRoomMinDay());
						}
						if(!Check.NuNObj(houseDbInfoVo.getRoomOrderType())){
							houseDbInfoVo.setOrderType(houseDbInfoVo.getRoomOrderType());
						}
					}

                }else{
                    continue;
                }
                
                //个性化标签
                houseDbInfoVo.setIndivLabelTipsList(getTagList(houseDbInfoVo.getHouseFid(),null));
                
                //房源类型名称
                houseDbInfoVo.setHouseTypeName(houseTypeMap.get(houseDbInfoVo.getHouseType()));
                
                //设置城市名称
                houseDbInfoVo.setCityName(cityMap.get(houseDbInfoVo.getCityCode()));
                
                //所在城市當前的熱度
                houseDbInfoVo.setCurrentCityHot(ValueUtil.getintValue(cityCurrentHotMap.get(houseDbInfoVo.getCityCode())));
                
                houseDbInfoVo.setBrandSn(landLordBrandMap.get(houseDbInfoVo.getLandlordUid()));              
                
                
                if (virtualSet.contains(this.getFid(houseDbInfoVo))){
                    //当前房源已经刷新完毕
                    continue;
                }
                HouseInfo houseInfo = this.transHouseInfoFromHouseDbInfoVo(houseDbInfoVo,reginMap,baseMap,km);
                //如果是虚拟整租 需要复制一份信息并生成一条虚拟整租的信息[这个暂时去掉]
                //this.dealVirtualFilter(houseInfoList,houseInfo,virtualSet);
                if(Check.NuNObj(houseInfo)){
                    continue;
                }
                if(checkHouseStatus(houseDbInfoVo)){
                    virtualSet.add(houseInfo.getId());
                    
                    //C类房源放在最后
                    if (!Check.NuNStr((houseInfo.getHouseQualityGrade())) && QualityGradeEnum.GRADE_C.getCode().equalsIgnoreCase(houseInfo.getHouseQualityGrade()))  {
    	            	houseInfo.setAdIndex(-5);
    	            	houseInfo.setCurrentReginMaxHotIndex(-5);
    	            	houseInfo.setFeatureHouseIndex(-5);
    	            	
    	            	houseInfoList.add(houseInfo);
    	            	continue;
    	            } 
                    
                    houseInfoList.add(houseInfo);
                }else{
                	continue;
                }               
                
                //当前的刷新需要处理广告信息
                if (!baseMap.containsKey(AdHouseInfo.ad)){
                    //只有设置了广告标记 才进行广告标记处理
                    continue;
                }
                AdHouseInfo adHouseInfo = (AdHouseInfo)baseMap.get("adHouseInfo");
                if (Check.NuNObj(adHouseInfo)){
                    adHouseInfo = new AdHouseInfo();
                    baseMap.put("adHouseInfo",adHouseInfo);
                }
                if ((Check.NuNObj(houseDbInfoVo.getHouseChannel()) || houseDbInfoVo.getHouseChannel() != HouseChannelEnum.CH_ZHIYING.getCode() ) 
                		&& (!Check.NuNStr((houseInfo.getHouseQualityGrade())) && !QualityGradeEnum.GRADE_C.getCode().equalsIgnoreCase(houseInfo.getHouseQualityGrade())) ){
                    //只有非直营的才放在前10
                    adHouseInfo.pushHouse(houseInfo);
                    
                    //场景推荐的房源也要满足此规则
                    adHouseInfo.pushRecHouse(houseInfo);
                }
            }
            indexService.batchCreateIndex(SolrCore.m_house_info, houseInfoList);
            
            String houseFid_rentway_roomFid_solr="";
            for(int i=0;i<houseInfoList.size();i++){
            	HouseInfo houseInfo =(HouseInfo) houseInfoList.get(i);
            	houseFid_rentway_roomFid_solr+=houseInfo.getHouseId()+"_"+houseInfo.getRentWay()+"_"+houseInfo.getRoomId()+",";
            }
            
            LogUtil.debug(LOGGER, "XXX要创建的索引：houseFid_rentway_roomFid={}", houseFid_rentway_roomFid_solr);
            
        }catch (Exception e){
            //其中创建索引异常，继续。。。。
            LogUtil.error(LOGGER,"批量 创建索引失败：par:{} e:{}",JsonEntityTransform.Object2Json(houseDbInfoVoList),e);
        }
    }
    
    /**
     * 
     * 处理灵活定价
     *
     * @author zl
     * @created 2017年5月10日 下午8:49:30
     *
     * @param houseFid
     * @param roomFid
     * @param rentWay
     */
    private void dealFlexiblePrice(String houseFid,String roomFid,int rentWay,HouseDbInfoVo houseDbInfoVo){
    	List<String> list = new ArrayList<>();
    	List<TonightDiscountEntity>  today = new ArrayList<>();
        
		if(rentWay == RentWayEnum.HOUSE.getCode()){
			list = houseDbInfoDao.getFlexiblePriceConf(houseFid, null);
			today = houseDbInfoDao.getTonightDiscountList(houseFid, null, true);
		}else if(rentWay == RentWayEnum.ROOM.getCode()){
			list = houseDbInfoDao.getFlexiblePriceConf(houseFid, roomFid);
			today = houseDbInfoDao.getTonightDiscountList(houseFid, roomFid, true);
		}
		if (!Check.NuNCollection(today) && !Check.NuNObj(today.get(0))) {
			TonightDiscountEntity tonightDiscount = today.get(0);
			list.add(ProductRulesEnum020.ProductRulesEnum020001.getValue()+","+String.valueOf(BigDecimalUtil.round(tonightDiscount.getDiscount(), 2)));
			
			houseDbInfoVo.setTonightDiscount(tonightDiscount);
			
		}
		if (!Check.NuNCollection(list)) {
			//靈活定價配置
			houseDbInfoVo.setFlexiblePrice(list); 
		}    	
    	
    }
    
    /**
     * 
     * top50列表
     *
     * @author zl
     * @created 2017年4月10日 下午7:49:09
     *
     * @param houseDbInfoVo
     */
	private void dealTop50ListHouse(HouseDbInfoVo houseDbInfoVo) {

		houseDbInfoVo.setIsTop50ListShow(YesOrNoEnum.NO.getCode());
		if (houseDbInfoVo.getIsTop50Online() == YesOrNoEnum.YES.getCode()) {

			if (houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()) {

				houseDbInfoVo.setIsTop50ListShow(YesOrNoEnum.YES.getCode());

			} else if (houseDbInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()) {

				if (Check.NuNStr(houseDbInfoVo.getTop50ListRoomFid()) || Check.NuNStr(houseDbInfoVo.getRoomFid())) {
					LogUtil.error(LOGGER, "top50 列表分组房源数据不正确，houseFid:{},roomFid:{},top50ListRoomFid:{}",
							houseDbInfoVo.getHouseFid(), houseDbInfoVo.getRoomFid(),
							houseDbInfoVo.getTop50ListRoomFid());
					return;
				}

				if (houseDbInfoVo.getRoomFid().equals(houseDbInfoVo.getTop50ListRoomFid())) {
					houseDbInfoVo.setIsTop50ListShow(YesOrNoEnum.YES.getCode());
				}

			}

		}
	}
    
    
    
    /**
     * 
     * 处理分组的序号
     *
     * @author zl
     * @created 2017年4月10日 下午3:45:25
     *
     * @param houseDbInfoVo
     */
    private void dealGroupIndex(HouseDbInfoVo houseDbInfoVo){
    	
    	String  houseIndexByLandKey ="houseIndexByLand_"+houseDbInfoVo.getHouseFid();
    	if (Check.NuNObj(groupByMap.get(houseIndexByLandKey))) {
    		Map<String, Integer> houseIndexMap = houseDbInfoDao.getHouseIndexbyLand(houseDbInfoVo.getLandlordUid());
    		if (!Check.NuNMap(houseIndexMap)) {
				 Iterator<Map.Entry<String, Integer>> iterator  = houseIndexMap.entrySet().iterator();
				 while(iterator.hasNext()){
					 Map.Entry<String, Integer> entry = iterator.next();
					 groupByMap.put("houseIndexByLand_"+entry.getKey(), entry.getValue());
				 }
			}
		}    	
    	if (!Check.NuNObj(groupByMap.get(houseIndexByLandKey))) {
    		Integer houseIndexByLand = ValueUtil.getintValue(groupByMap.get(houseIndexByLandKey));
    		if (!Check.NuNObj(houseIndexByLand)) {
    			houseDbInfoVo.setHouseIndexByLand(houseIndexByLand);
			}
    	}
    	
    	if(houseDbInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
    		String  roomIndexByHouseKey ="roomIndexByHouse_"+houseDbInfoVo.getRoomFid();
    		if (Check.NuNObj(groupByMap.get(roomIndexByHouseKey))) {
        		Map<String, Integer> roomIndexMap = houseDbInfoDao.getRoomIndexbyHouse(houseDbInfoVo.getRoomFid());
        		if (!Check.NuNMap(roomIndexMap)) {
    				 Iterator<Map.Entry<String, Integer>> iterator  = roomIndexMap.entrySet().iterator();
    				 while(iterator.hasNext()){
    					 Map.Entry<String, Integer> entry = iterator.next();
    					 groupByMap.put("roomIndexByHouse_"+entry.getKey(), entry.getValue());
    				 }
    			}
    		}
        	
        	if (!Check.NuNObj(groupByMap.get(roomIndexByHouseKey))) {
        		Integer roomIndexByHouse = ValueUtil.getintValue(groupByMap.get(roomIndexByHouseKey));
        		if (!Check.NuNObj(roomIndexByHouse)) {
        			houseDbInfoVo.setRoomIndexByHouse(roomIndexByHouse);
    			}
        	}
    		
    	}
    	
    }
 

    /**
     * 校验当前的房源是否上架
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private boolean checkHouseStatus(HouseDbInfoVo houseDbInfoVo){
        if(Check.NuNObj(houseDbInfoVo)){
            return false;
        }
        boolean flag = false;
        // 初始化当前的房源状态
        Integer status = 0;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            status = houseDbInfoVo.getHouseStatus();
        }else {
            status = houseDbInfoVo.getRoomStatus();
        }
        //校验当前的房源是否上架
        if(!Check.NuNObj(status) && status == HouseStatusEnum.SJ.getCode()){
            flag =true;
        }
        return flag;
    }

    /**
     * 拦截生成逻辑整租
     * @param houseInfoList
     * @param houseInfo
     * @param virtualSet
     */
    private void dealVirtualFilter(List<Object> houseInfoList,HouseInfo houseInfo,Set<String> virtualSet){
        if(houseInfo.getRentWay() == RentWayEnum.ROOM.getCode()){
            if(virtualSet.contains(houseInfo.getHouseId())){
                return;
            }
            HouseInfo houseInfoNew = new HouseInfo(SearchDataSourceTypeEnum.minsu.getCode());
            BeanUtils.copyProperties(houseInfo,houseInfoNew);
            houseInfoNew.setRentWay(RentWayEnum.VIRTUAL.getCode());
            houseInfoNew.setRentWayName(RentWayEnum.VIRTUAL.getName());
            houseInfoNew.setRoomId(null);
            houseInfoNew.setId(getFid(houseInfoNew));
            houseInfoNew.setOccupyDays(ValueUtil.transList2Set(orderDbInfoDao.getLocksByHouseFid(houseInfoNew.getHouseId())));
            houseInfoNew.setBrotherRooms(null);
            houseInfoNew.setRooms(ValueUtil.transList2Set(houseDbInfoDao.getRoomsByHouseFid(houseInfoNew.getHouseId())));
            virtualSet.add(houseInfo.getHouseId());
            houseInfoList.add(houseInfoNew);
        }
    }

    /**
     * 手动的刷新某一房源的信息
     * @author afi
     * @param houseFid
     */
    public void freshHouseInfo(String houseFid){

        LogUtil.info(LOGGER,"刷新房源信息，当前的房源houseFid：{}。。。。。。",houseFid);

        //商圈或者景点的本地map 创建索引之后改map失效
        Map<String,List<HotRegionVo>>  reginMap = new HashMap<>();
        Map<String,Object>  baseMap = new HashMap<>();
        //虚拟整租的集合
        Set<String> virtualSet = new HashSet<>();
        //获取商圈景点半径
        Integer km = getKm();
        //校验当前的房源fid
        if(Check.NuNStr(houseFid)){
            throw new BusinessException("houseFid is null on freshHouseInfo");
        }
        if(houseFid.contains("*") || houseFid.contains(":")){
            throw new BusinessException("houseFid is Illegal on freshHouseInfo,the houseFid is :"+houseFid);
        }
        List<HouseDbInfoVo> houseDbInfoVoList = houseDbInfoDao.getHouseDbInfoByHouseFid(houseFid);
        //先将原来的索引信息删掉
		indexService.deleteByQuery(SolrCore.m_house_info, "houseId:" + houseFid);

		if(Check.NuNObj(houseDbInfoVoList)){
            //当前房源不存在
            return;
        }
        //城市map
        Map<String, String> cityMap= getAllCityMap();
        dealSearchIndex(houseDbInfoVoList,reginMap,baseMap,virtualSet,km,cityMap);
        LogUtil.info(LOGGER, "刷新单条房源信息完成");
    }


    /**
     * 获取当前房源的价格
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    public static Integer getHousePrice(HouseDbInfoVo houseDbInfoVo){
        Integer price = 0;
        if(!Check.NuNObj(houseDbInfoVo)){
            RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(houseDbInfoVo.getRentWay());
            if(Check.NuNObj(rentWayEnum)){
                throw new BusinessException("houseInfo.getRentWay() is null");
            }
            if(rentWayEnum.getCode() == RentWayEnum.HOUSE.getCode()){
                price = houseDbInfoVo.getLeasePrice();
            }else if(rentWayEnum.getCode() == RentWayEnum.ROOM.getCode()){
                price = houseDbInfoVo.getRoomPrice();
            }else {
                throw new BusinessException("rentWay is wrong:"+houseDbInfoVo.getRentWay());
            }
        }
        return price;

    }


    /**
     * 获取当前房源的价格
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    public static String getHouseName(HouseDbInfoVo houseDbInfoVo){
        String houseName = null;
        if(!Check.NuNObj(houseDbInfoVo)){
            RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(houseDbInfoVo.getRentWay());
            if(Check.NuNObj(rentWayEnum)){
                LogUtil.error(LOGGER,"houseInfo.getRentWay() is null houseFid:{}",houseDbInfoVo.getHouseFid());
                throw new BusinessException("houseInfo.getRentWay() is null");
            }
            if(rentWayEnum.getCode() == RentWayEnum.HOUSE.getCode()){
                houseName = houseDbInfoVo.getHouseName();
            }else if(rentWayEnum.getCode() == RentWayEnum.ROOM.getCode()){
                houseName = houseDbInfoVo.getRoomName();
            }else {
                throw new BusinessException("rentWay is wrong:"+houseDbInfoVo.getRentWay());
            }
        }
        return houseName;

    }
    
    /**
     * 获取房源或者房间编号
     *
     * @author zl
     * @created 2016年12月15日 下午1:56:19
     *
     * @param houseDbInfoVo
     * @return
     */
    public static String getHouseSn(HouseDbInfoVo houseDbInfoVo){
        String houseSN = null;
        if(!Check.NuNObj(houseDbInfoVo)){
            RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(houseDbInfoVo.getRentWay());
            if(Check.NuNObj(rentWayEnum)){
                LogUtil.error(LOGGER,"houseInfo.getRentWay() is null houseFid:{}",houseDbInfoVo.getHouseFid());
                throw new BusinessException("houseInfo.getRentWay() is null");
            }
            if(rentWayEnum.getCode() == RentWayEnum.HOUSE.getCode()){
            	houseSN = houseDbInfoVo.getHouseSn();
            }else if(rentWayEnum.getCode() == RentWayEnum.ROOM.getCode()){
            	houseSN = houseDbInfoVo.getRoomSn();
            }else {
                throw new BusinessException("rentWay is wrong:"+houseDbInfoVo.getRentWay());
            }
        }
        return houseSN;

    }

    /**
     * 将数据库转化成索引中的数据
     * @author afi
     * @param houseDbInfoVo
     * @param reginMap
     * @param baseMap
     * @param km
     * @return
     */
    public HouseInfo transHouseInfoFromHouseDbInfoVo(HouseDbInfoVo houseDbInfoVo, Map<String,List<HotRegionVo>>  reginMap,Map<String,Object>  baseMap,Integer km){
        if(Check.NuNObj(houseDbInfoVo)){
            return null;
        }
        if(Check.NuNObjs(houseDbInfoVo.getRentWay(),houseDbInfoVo.getHouseFid())){
            return null;
        }
        LogUtil.info(LOGGER,"刷新房源信息，当前的房源houseFid：{},rentWay：{}，roomFid：{}",houseDbInfoVo.getHouseFid(),houseDbInfoVo.getRentWay(),houseDbInfoVo.getRoomFid());
        HouseInfo houseInfo = new HouseInfo(SearchDataSourceTypeEnum.minsu.getCode());
        houseInfo.setId(this.getFid(houseDbInfoVo));
        houseInfo.setHouseId(houseDbInfoVo.getHouseFid());
        houseInfo.setRoomId(houseDbInfoVo.getRoomFid());
        dealHousePrices(houseDbInfoVo,houseInfo);
        houseInfo.setCityCode(houseDbInfoVo.getCityCode());
        houseInfo.setCityName(houseDbInfoVo.getCityName());
        houseInfo.setAreaCode(houseDbInfoVo.getAreaCode());
        //出租方式
        RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(houseDbInfoVo.getRentWay());
        houseInfo.setRentWay(rentWayEnum.getCode());

        //房间类型
        RoomTypeEnum roomTypeEnum = RoomTypeEnum.getEnumByCode(houseDbInfoVo.getRoomType());

        //没房间类型则默认是房间 容错 2017-12-17
        houseInfo.setRoomType(Check.NuNObj(roomTypeEnum) ? RoomTypeEnum.ROOM_TYPE.getCode() : roomTypeEnum.getCode());

        // 是独立房间，并且是共享客厅
        if (RentWayEnum.ROOM.equals(rentWayEnum) && RoomTypeEnum.HALL_TYPE.equals(roomTypeEnum)) {
            houseInfo.setRentWayName(RentWayEnum.HALL.getName());
        } else {
            houseInfo.setRentWayName(rentWayEnum.getName());
        }

        houseInfo.setHouseSn(getHouseSn(houseDbInfoVo));
        houseInfo.setHouseType(houseDbInfoVo.getHouseType());
        houseInfo.setHouseTypeName(houseDbInfoVo.getHouseTypeName());
        
        OrderTypeEnum orderTypeEnum = OrderTypeEnum.getOrderTypeByCode(houseDbInfoVo.getOrderType());
        if(orderTypeEnum ==null){
            orderTypeEnum = OrderTypeEnum.ORDINARY;
        }
        houseInfo.setOrderType(houseDbInfoVo.getOrderType());
        houseInfo.setOrderTypeName(orderTypeEnum.getName());
        houseInfo.setPersonCount(this.getPersonCount(houseDbInfoVo));
        houseInfo.setRoomCount(houseDbInfoVo.getRoomNum());
        houseInfo.setToiletCount(houseDbInfoVo.getToiletNum());
        houseInfo.setBalconyCount(houseDbInfoVo.getBalconyNum());
        houseInfo.setOccupyDays(ValueUtil.transList2SetAndFill(houseDbInfoVo.getOccupyDays()));
        houseInfo.setMinDay(ValueUtil.getintValue(houseDbInfoVo.getMinDay()));
        //填充热门区域信息
        this.dealRegin(houseDbInfoVo, houseInfo, reginMap, km);
        //获取当前的房源或者房间名称
        String houseName = getHouseName(houseDbInfoVo);
        houseInfo.setHouseName(houseName);
        houseInfo.setHouseNameAuto(houseName);
        houseInfo.setHouseAddr(houseDbInfoVo.getHouseAddr());
        houseInfo.setHouseAddrAuto(houseDbInfoVo.getHouseAddr());
        houseInfo.setPicUrl(this.getFullPic(houseDbInfoVo));
        
        //填充房东信息
        this.fillLandlordInfo(houseDbInfoVo, houseInfo);
        //填充房间信息
        this.fillRoomInfo(houseDbInfoVo, houseInfo);

        //填充床信息
        this.fillBedInfo(houseDbInfoVo, houseInfo,baseMap);

        houseInfo.setRefreshDate(houseDbInfoVo.getRefreshDate().getTime());
        houseInfo.setHouseEndTime(this.getHouseEndTime(houseDbInfoVo));
        houseInfo.setLoc(this.getLocStr(houseDbInfoVo.getLatitude(), houseDbInfoVo.getLongitude()));
        
        //处理房源品质等级
        dealHouseQualityGrade( houseDbInfoVo, houseInfo);
               
        houseInfo.setEvaluateCount(houseDbInfoVo.getEvaluateCount());
        
        houseInfo.setHouseQualityGrade(houseDbInfoVo.getHouseQualityGrade()); 
        
        //是否特色房源
        houseInfo.setIsFeatureHouse(houseDbInfoVo.getIsFeatureHouse());
        
        //夹心日期
        houseInfo.setPriorityDate(ValueUtil.transList2Set(houseDbInfoVo.getPriorityDate()));
        
        //长租折扣
        houseInfo.setLongTermLeaseDiscount(ValueUtil.transList2Set(houseDbInfoVo.getLongTermLeaseDiscount()));
        
        //灵活定价
        houseInfo.setFlexiblePrice(ValueUtil.transList2Set(houseDbInfoVo.getFlexiblePrice()));

        //今夜特价
		if (!Check.NuNObj(houseDbInfoVo.getTonightDiscount())) {
			TonightDiscountEntity tonightDiscountEntity = houseDbInfoVo.getTonightDiscount();
			if (!Check.NuNObj(tonightDiscountEntity.getStartTime())) {
				houseInfo.setTonightDisOpenDate(tonightDiscountEntity.getStartTime().getTime());
			}
			if (!Check.NuNObj(tonightDiscountEntity.getEndTime())) {
				houseInfo.setTonightDisDeadlineDate(tonightDiscountEntity.getEndTime().getTime());
			}
			if (!Check.NuNObj(tonightDiscountEntity.getDiscount())) {
				houseInfo.setTonightDiscount(tonightDiscountEntity.getDiscount());
			}
		}

        //所在商圈景点最大的热度
        houseInfo.setCurrentReginMaxHot(ValueUtil.getintValue(houseDbInfoVo.getCurrentReginMaxHot()));
        
        //当前城市的热度
        houseInfo.setCurrentCityHot(ValueUtil.getintValue(houseDbInfoVo.getCurrentCityHot()));
        
        //是否top50上线房源
        houseInfo.setIsTop50Online(houseDbInfoVo.getIsTop50Online());
        
        //个性化标签
        houseInfo.setIndivLabelTipsList(ValueUtil.transList2Set(houseDbInfoVo.getIndivLabelTipsList()));
        
        //品牌编码
        houseInfo.setBrandSn(houseDbInfoVo.getBrandSn());
        
        //top50文章标题
        houseInfo.setTop50Title(houseDbInfoVo.getTop50Title());
        
        houseInfo.setHouseIndexByLand(houseDbInfoVo.getHouseIndexByLand());
        houseInfo.setRoomIndexByHouse(houseDbInfoVo.getRoomIndexByHouse());
        
        houseInfo.setIsTop50ListShow(houseDbInfoVo.getIsTop50ListShow());
        
        houseInfo.setIsLandTogether(houseDbInfoVo.getIsHezhu());
        
        houseInfo.setAcceptOrder60DaysCount(houseDbInfoVo.getAcceptOrder60DaysCount());
        houseInfo.setOrder60DaysCount(houseDbInfoVo.getOrder60DaysCount());
        
        //处理房源的权重信息 并填充评价数 需要在设置热门区域之后
        dealWeight(houseInfo,houseDbInfoVo,baseMap);
        
        return houseInfo;
    }
    
    /**
     * 处理房源品质等级
     * @param houseDbInfoVo
     * @param houseInfo
     */
    private void dealHouseQualityGrade(HouseDbInfoVo houseDbInfoVo,HouseInfo houseInfo){
    	if(Check.NuNObjs(houseDbInfoVo, houseInfo)){
            return;
        }
    	
    	//C等品质的房源处理
    	if (!Check.NuNStr((houseDbInfoVo.getHouseQualityGrade())) && QualityGradeEnum.GRADE_C.getCode().equalsIgnoreCase(houseDbInfoVo.getHouseQualityGrade())) {
    		houseInfo.setAdIndex(-5);
		}
    	
    }
    

    /**
     * 填充房间信息
     * @author afi
     * @param houseDbInfoVo
     * @param houseInfo
     */
    private void fillRoomInfo(HouseDbInfoVo houseDbInfoVo,HouseInfo houseInfo){
        if(Check.NuNObjs(houseDbInfoVo, houseInfo)){
            return;
        }
        //填充房间信息
        if (houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            //整租
            houseInfo.setIsBalcony(YesOrNoEnum.NO.getCode());
            houseInfo.setIsToilet(YesOrNoEnum.NO.getCode());
            houseInfo.setBalconyCount(houseDbInfoVo.getBalconyNum());
            houseInfo.setToiletCount(houseDbInfoVo.getToiletNum());
        }else {
            //合租
            houseInfo.setIsBalcony(houseDbInfoVo.getIsBalcony());
            houseInfo.setIsToilet(houseDbInfoVo.getIsToilet());
        }
    }

    /**
     * 填充床信息
     * @author afi
     * @param houseDbInfoVo
     * @param houseInfo
     */
    private void fillBedInfo(HouseDbInfoVo houseDbInfoVo,HouseInfo houseInfo,Map<String,Object> baseMap){
        if(Check.NuNObjs(houseDbInfoVo, houseInfo)){
            return;
        }
        if (Check.NuNObj(baseMap)){
            baseMap = new HashMap<>();
        }
        if (!baseMap.containsKey(ProductRulesEnum.ProductRulesEnum005.getValue())){
            List<DicItemEntity> dicItemEntityList = basedateDbInfoDao.getDicItemListByCodeAndTemplate(ProductRulesEnum.ProductRulesEnum005.getValue(),null);
            if (Check.NuNCollection(dicItemEntityList)){
                LogUtil.error(LOGGER,"异常的的配置文件信息code:{}",ProductRulesEnum.ProductRulesEnum005.getValue());
                return;
            }
            Map<String,MinsuEleEntity> bedMap= new HashMap<>();
            for (DicItemEntity itemEntity : dicItemEntityList) {
                MinsuEleEntity ele = new MinsuEleEntity();
                ele.setEleKey(itemEntity.getItemValue());
                ele.setEleValue(itemEntity.getShowName());
                bedMap.put(itemEntity.getItemValue(),ele);
            }
            baseMap.put(ProductRulesEnum.ProductRulesEnum005.getValue(),bedMap);
        }
        Map<String,MinsuEleEntity> bedMap = (Map<String,MinsuEleEntity>)baseMap.get(ProductRulesEnum.ProductRulesEnum005.getValue());

        List<BedNumVo>  bedNumVoList = null;
        if (houseDbInfoVo.getRentWay().equals(RentWayEnum.HOUSE.getCode())){
            //整租床信息
            bedNumVoList = houseDbInfoDao.getBedNumByHouseFid(houseDbInfoVo.getHouseFid());
        }else {
            //合租床信息
            bedNumVoList = houseDbInfoDao.getBedNumByRoomFid(houseDbInfoVo.getRoomFid());
        }
        if (Check.NuNCollection(bedNumVoList)){
            //LogUtil.error(LOGGER,"当前房源的床信息不存在rentWay:{},:houseFid:{},roomFid:{}",houseDbInfoVo.getRentWay(),houseDbInfoVo.getHouseFid(),houseDbInfoVo.getRoomFid());
            return;
        }
        Set<String> bedSet = new HashSet<>();
        for (BedNumVo bedNumVo : bedNumVoList) {
            Integer bedType = bedNumVo.getBedType();
            if (Check.NuNObj(bedType)){
                continue;
            }

            String bedTypeStr = bedNumVo.getBedType() +"";
            if (!bedMap.containsKey(bedTypeStr)){
                continue;
            }else {
                String bedInfo = bedTypeStr + BaseConstant.split + bedNumVo.getBedNum() + BaseConstant.split + bedMap.get(bedTypeStr).getEleValue();
                bedSet.add(bedInfo);
            }
        }
        houseInfo.setBedList(bedSet);
    }


    /**
     * 兼容地理位置为空的情况
     * @param latitude
     * @param longitude
     * @return
     */
    private String getLocStr(String latitude,String longitude){
        if(Check.NuNObj(latitude)){
            latitude = "00.000000";
        }
        if(Check.NuNObj(longitude)){
            longitude = "000.000000";
        }
        return latitude + "," + longitude;
    }

    /**
     * 填充房东信息
     * @param houseDbInfoVo
     * @param houseInfo
     */
    private void fillLandlordInfo(HouseDbInfoVo houseDbInfoVo,HouseInfo houseInfo){
        if(Check.NuNObjs(houseDbInfoVo, houseInfo)){
            return;
        }
        CustomerDbInfoVo customer = customerDbDao.getCustomerInfo(houseDbInfoVo.getLandlordUid());
        if(!Check.NuNObj(customer)){
            houseInfo.setLandlordUid(ValueUtil.getStrValue(houseDbInfoVo.getLandlordUid()));
            String headUrl = getHeadPic(customer.getHeadUrl(), customer.getPicSuffix());
            if(Check.NuNStr(headUrl)){
                headUrl = defaultHeadUrl;
            }
            houseInfo.setLandlordUrl(headUrl);
            houseInfo.setLandlordName(ValueUtil.getStrValue(customer.getRealName()));
            houseInfo.setNickName(ValueUtil.getStrValue(customer.getNickName()));
        }
    }


    /**
     * 获取图片信息
     * @author afi
     * @param pic
     * @param picSuffix
     * @return
     */
    private String getHeadPic(String pic, String picSuffix){
        if(Check.NuNStr(pic)) {
            return "";
        }else if(pic.startsWith("http:")){
            return pic;
        }else {
            if(Check.NuNObj(picBaseAddrMona) || !picBaseAddrMona.startsWith("http")){
                return "小子 你的id是："+ IpUtil.getIp() +"终于找到你了";
            }
//            return  picBaseAddrMona + pic + picSuffix + defaultHeadSize + picSuffix.toLowerCase();
            return PicUtil.getFullPic(picBaseAddrMona, pic, picSuffix, defaultHeadSize);
        }
    }

    /**
     * 获取图片信息
     * @param houseDbInfoVo
     * @return
     */
    private String getFullPic(HouseDbInfoVo houseDbInfoVo){

        if(Check.NuNObj(houseDbInfoVo)){
            return "";
        }
        HousePicVo housePicVo = null;
        //获取房源的名称
        int rentWay = houseDbInfoVo.getRentWay();
        if (rentWay == RentWayEnum.HOUSE.getCode()){
            housePicVo = getHousePic(houseDbInfoVo.getHouseDefaultPicFid(),houseDbInfoVo.getHouseOldDefaultPicFid(),houseDbInfoVo.getHouseFid(),null);
        }else {
            housePicVo = getHousePic(houseDbInfoVo.getRoomDefaultPicFid(), houseDbInfoVo.getRoomOldDefaultPicFid(), null, houseDbInfoVo.getRoomFid());
        }
        //非空校验
        if(Check.NuNObj(housePicVo)){
            return "";
        }
        String pic = housePicVo.getPicUrl();
        String picSuffix = housePicVo.getPicSuffix();
        if(Check.NuNStr(pic)) {
            return "";
        }else if(pic.startsWith("http:")){
            return pic;
        }else {
            if(Check.NuNObj(picBaseAddrMona) || !picBaseAddrMona.startsWith("http")){
                return "小子 你的id是："+ IpUtil.getIp() +"终于找到你了";
            }
            //获取图片的全路径
            return PicUtil.getFullPic(picBaseAddrMona, pic, picSuffix, SolrConstant.picSize);
        }
    }


    /**
     * 获取当前房源的图片信息
     * @param picFid
     * @param oldPicFid
     * @param houseFid
     * @param roomFid
     * @return
     */
    private HousePicVo getHousePic(String picFid,String oldPicFid,String houseFid,String roomFid){

        //LogUtil.info(LOGGER,"开始刷新图片picFid,{} oldPicFid,,{}  houseFid,,{}  roomFid ,{} ",picFid,oldPicFid,houseFid,roomFid);
        HousePicVo housePicVo = null;
        //先获取默认图片
        if (!Check.NuNStr(picFid)){
            housePicVo = houseDbInfoDao.getPicByFid(picFid);
        }
        //先获取删掉的默认图片
        if (Check.NuNObj(housePicVo)){
            housePicVo = houseDbInfoDao.getPicByFidForce(oldPicFid);
        }
        //都没有直接取原来的逻辑
        if (Check.NuNObj(housePicVo)){
            if (!Check.NuNStr(houseFid)){
                housePicVo = houseDbInfoDao.getHousePicInfo(houseFid);
            }else if (!Check.NuNStr(roomFid)){
                housePicVo = houseDbInfoDao.getRoomPicInfo(roomFid);
            }
        }
        return housePicVo;
    }

    /**
     * 处理房源信息的权重
     * @author afi
     * @param houseInfo
     * @param houseDbInfoVo
     */
    private void dealWeight(HouseInfo houseInfo,HouseDbInfoVo houseDbInfoVo,Map<String,Object>  baseMap){
        if(Check.NuNObjs(houseInfo, houseDbInfoVo)){
            return;
        }
        Long weight = 0L;
        // 权重明细
        StringBuilder weightsComposition = new StringBuilder();
        if (houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            LogUtil.info(LOGGER,"【权重】整租houseFid:{}",houseDbInfoVo.getHouseFid());
        }else if (houseDbInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
            LogUtil.info(LOGGER,"【权重】分组roomFid:{}",houseDbInfoVo.getRoomFid());
        }

        //1.1 计算当前的刷新时间的权重 更新日历的时间也在统计范围之内
        int refreshDate = this.getScoreRefreshDate(houseDbInfoVo);
        weight += refreshDate;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.base_refresh_date.name(), refreshDate));

        //1.2 面积权重
//        int area = this.getScoreArea(houseDbInfoVo);
//        weight += area;

        //1.3 价格权重
        int price = this.getScorePrice(houseDbInfoVo.getRentWay(), getHousePrice(houseDbInfoVo));
        weight += price;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.base_price.name(), price));

        //1.4 房源品质等级
        int quality = this.getQualityGradeScore(houseDbInfoVo);
        weight += quality;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.base_quality.name(), quality));

        //1.5 立即预定
        int immediatelyBook = getImmediatelyBookScore( houseDbInfoVo);
        weight += immediatelyBook;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.immediately_book.name(), immediatelyBook));
        
        //2.1 商圈景点热度
//        int hot = this.getScoreHot(houseDbInfoVo);
//        weight += hot;
//        //2.2 商圈景点距离
//        int dis = this.getScoreDis(houseDbInfoVo);
//        weight += dis;

		//与房东同住
        int isLandTogether = this.getIsLandTogetherScore(houseDbInfoVo);
        weight += isLandTogether;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.weight_is_land_together.name(), isLandTogether));
        
        //2.3 地铁距离
//        int subWayDis = this.getSubWayScoreDis(houseDbInfoVo);
//        weight += subWayDis;


        //3.1 有效订单数
        int orderNum = this.getScoreOrderNum(houseInfo, houseDbInfoVo, baseMap);
        weight += orderNum;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.trade_order_num.name(), orderNum));

        //3.2 有效订单占比
//        int orderRate = this.getScoreOrder(houseDbInfoVo);
//        weight += orderRate;

        //3.3 评价数量占比
        int orderEval = this.getScoreOrderEval(houseDbInfoVo);
        weight += orderEval;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.trade_eval_rate.name(), orderEval));

        //3.4 评价数量
        int evalNum= getScoreOrderEvalNum(houseInfo, houseDbInfoVo,baseMap);
        weight += evalNum;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.trade_eval.name(), evalNum));
       
        //3.5 设置pv
        int pv = this.getScorePv(houseInfo, houseDbInfoVo, baseMap);
        weight += pv;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.trade_pv.name(), pv));

        //获取当前的房源的订单数量统计信息
        WeightOrderNumVo weightOrderNumVo = this.getOrderRateInfo(houseDbInfoVo);

        //4.1 申请预订回复率
//        int orderReplyWeight = this.getOrderReply(weightOrderNumVo);
//        weight += orderReplyWeight;

        //4.2 申请预订拒绝率
        int orderRefuseWeight = this.getOrderRefuse(weightOrderNumVo);
        weight += orderRefuseWeight;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.reply_refused.name(), orderRefuseWeight));

        //4.3 申请预订回复时间
        int orderAvgAcceptTime = this.getAvgAcceptTime(houseDbInfoVo);
        weight += orderAvgAcceptTime;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.reply_time.name(), orderAvgAcceptTime));

        //4.4 IM回复率
//        int imReplyWeight = this.getScoreImReply(houseDbInfoVo);
//        weight += imReplyWeight;

        //4.5 IM回复时间
        int imTimeWeight = this.getScoreImTime(houseDbInfoVo);
        weight += imTimeWeight;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.reply_im_time.name(), imTimeWeight));

        //4.6 取消订单率
        int cancleOrderWeight = this.getCancleOrderWeight(weightOrderNumVo);
        weight += cancleOrderWeight;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.trade_order_cancle.name(), cancleOrderWeight));

        //5.1 房源本身的权重
        int houseWeight = this.getHouseWeight(houseDbInfoVo);
        weight += houseWeight;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.weight_manage.name(), houseWeight));

		//5.2 房源发布时间
		int timeWeight = this.dealTimeWeight(houseInfo,houseDbInfoVo);
        weight += timeWeight;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.weight_time.name(), timeWeight));

        //今夜特价
        int tonightDisWeight = dealTonightDisWeight(houseDbInfoVo);
        weight += tonightDisWeight;
		weightsComposition.append(String.format("&%s=%s", SoreWeightEnum.weight_tonight_discount.name(), tonightDisWeight));
        
        LogUtil.info(LOGGER,"【权重】===总得分：{}", weight);
		weightsComposition.deleteCharAt(0);
		LogUtil.info(LOGGER,"【权重】===组成：{}", weightsComposition);

        //处理当前的评价信息
        getScoreEvaluate(houseInfo,houseDbInfoVo);

        houseInfo.setWeights(weight);
		houseInfo.setWeightsComposition(weightsComposition.toString());
    }
    

    /**
     * 
     * 今夜特价权重
     *
     * @author zl
     * @created 2017年5月18日 下午8:11:07
     *
     * @param houseDbInfoVo
     * @return
     */
    private int dealTonightDisWeight(HouseDbInfoVo houseDbInfoVo){
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.weight_tonight_discount;
        int score = Score.ZERO;
        if(Check.NuNObj(houseDbInfoVo) || Check.NuNObj(houseDbInfoVo.getTonightDiscount())){
            return score;
        }
        
        Double discount = houseDbInfoVo.getTonightDiscount().getDiscount();
        if (Check.NuNObj(discount)){
            return score;
        } 
        
        if(discount <=0 ||discount >=1){
        	score = Score.ZERO;
        }else if(discount >=0.8){
        	score = Score.ONE;
        }else if(discount >= 0.7){
            score = Score.TWO;
        }else if(discount >= 0.6){
            score = Score.THREE;
        }else if(discount >= 0.5){
            score = Score.FOUR;
        }else{
            score = Score.FIVE;
        }
        Double avDou =BigDecimalUtil.mul(score, soreWeight.getCode());
        LogUtil.info(LOGGER,"【权重】今夜特价权重:{}%,值：{}，得分：{}",soreWeight.getCode(),discount,avDou.intValue());
        return avDou.intValue();
    }
    



    /**
     * 获取当前订单的申请预订拒绝率
     * @author afi
     * @param weightOrderNumVo
     * @return
     */
    private int getOrderRefuse(WeightOrderNumVo weightOrderNumVo){
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.reply_refused;
        int score = Score.ZERO;
        if(Check.NuNObj(weightOrderNumVo)){
            LogUtil.info(LOGGER,"【权重】申请预订拒绝率权重:{}%，得分：{}",soreWeight.getCode(),score);
            return score;
        }
        //获取当前订单的拒绝率
        Double rate =0.0;
        if (!Check.NuNObj(weightOrderNumVo.getTimeRefuse()) && !Check.NuNObj(weightOrderNumVo.getOrderCount()) && weightOrderNumVo.getOrderCount()>0) {
        	rate = BigDecimalUtil.div(ValueUtil.getintValue(weightOrderNumVo.getTimeRefuse()),ValueUtil.getintValue(weightOrderNumVo.getOrderCount()));
		}
        if(rate > 0.3){
            score = Score.FIVE;
        }else if(rate > 0.2){
            score = Score.FOUR;
        }else if(rate > 0.1){
            score = Score.THREE;
        }else if(rate > 0.05){
            score = Score.TWO;
        }else if(rate > 0){
            score = Score.ONE;
        }
        Double orderDou =-BigDecimalUtil.mul(score, soreWeight.getCode());
        LogUtil.info(LOGGER,"【权重】申请预订拒绝率权重:{}%,值：{}， 得分：{}",soreWeight.getCode(),rate,orderDou.intValue());
        return orderDou.intValue();
    }
    
    /**
     * 
     * 取消订单率权重
     *
     * @author zl
     * @created 2017年5月10日 上午11:05:11
     *
     * @param weightOrderNumVo
     * @return
     */
    private int getCancleOrderWeight(WeightOrderNumVo weightOrderNumVo){
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.trade_order_cancle;
        int score = Score.ZERO;
        if(Check.NuNObj(weightOrderNumVo)){
            LogUtil.info(LOGGER,"【权重】取消订单率权重:{}%，得分：{}",soreWeight.getCode(),score);
            return score;
        }
        //取消订单率
        Double rate = 0.0;
        if (!Check.NuNObj(weightOrderNumVo.getCancleOrderCount()) && !Check.NuNObj(weightOrderNumVo.getPaidOrderCount()) && weightOrderNumVo.getPaidOrderCount()>0) {
        	rate = BigDecimalUtil.div(ValueUtil.getintValue(weightOrderNumVo.getCancleOrderCount()),ValueUtil.getintValue(weightOrderNumVo.getPaidOrderCount()));
		}
        
        if(rate > 0.3){
            score = Score.FIVE;
        }else if(rate > 0.2){
            score = Score.FOUR;
        }else if(rate > 0.1){
            score = Score.THREE;
        }else if(rate > 0.05){
            score = Score.TWO;
        }else if(rate > 0){
            score = Score.ONE;
        }
        Double orderDou =-BigDecimalUtil.mul(score, soreWeight.getCode());
        LogUtil.info(LOGGER,"【权重】取消订单率权重:{}%,值：{}， 得分：{}",soreWeight.getCode(),rate,orderDou.intValue());
        return orderDou.intValue();
    }
    


    /**
     * 获取当前订单的申请预订回复率
     * @author afi
     * @param weightOrderNumVo
     * @return
     */
    private int getOrderReply(WeightOrderNumVo weightOrderNumVo){
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.reply_deal;
        int score = Score.ZERO;
        if(Check.NuNObj(weightOrderNumVo)){
            LogUtil.info(LOGGER,"【权重】申请预订回复率权重:{}%，得分：{}",soreWeight.getCode(),score);
            return score;
        }
        //获取当前的回复率
        int replyNum = ValueUtil.getintValue(weightOrderNumVo.getOrderCount())
                - ValueUtil.getintValue(weightOrderNumVo.getWaitCount())
                - ValueUtil.getintValue(weightOrderNumVo.getTimeRefuse());
        Double rate =0.0;
        if (!Check.NuNObj(replyNum) && !Check.NuNObj(weightOrderNumVo.getOrderCount()) && weightOrderNumVo.getOrderCount()>0) {
        	rate =BigDecimalUtil.div(replyNum,ValueUtil.getintValue(weightOrderNumVo.getOrderCount()));
        }
        
        if(rate >= 0.9){
            score = Score.FIVE;
        }else if(rate >= 0.8){
            score = Score.FOUR;
        }else if(rate >= 0.7){
            score = Score.THREE;
        }else if(rate >= 0.5){
            score = Score.TWO;
        }else{
            score = Score.ONE;
        }
        Double orderDou =BigDecimalUtil.mul(score, soreWeight.getCode());
        LogUtil.info(LOGGER,"【权重】申请预订回复率:{}%,值：{}， 得分：{}",soreWeight.getCode(),rate,orderDou.intValue());
        return orderDou.intValue();
    }



    /**
     * 设置房源的新加上的权重
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int dealTimeWeight(HouseInfo houseInfo,HouseDbInfoVo houseDbInfoVo){
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.weight_time;
        int score = Score.ZERO;
        if(Check.NuNObj(houseDbInfoVo)){
            return score;
        }
        Date passDate = this.getAndDealPassDate(houseInfo,houseDbInfoVo);
        if (Check.NuNObj(passDate)){
            return 0;
        }
        if (new Date().before(passDate)){
            return 0;
        }
        Integer daysOrg = DateSplitUtil.countDateSplit(passDate, new Date());
        if (Check.NuNObj(daysOrg)){
            return 0;
        }
        if (daysOrg <= 6){
            houseInfo.setIsNew(YesOrNoEnum.YES.getCode());
        }
        //获取当前最大分数的随机数
        score = new Random().nextInt(5);
        Double avDou =BigDecimalUtil.mul(score, soreWeight.getCode());
        LogUtil.info(LOGGER,"【权重】上架时间权重:{}%,值：{}，得分：{}",soreWeight.getCode(),score,avDou.intValue());
        return avDou.intValue();
    }




    /**
     * 设置房源本身的权重
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int getHouseWeight(HouseDbInfoVo houseDbInfoVo){
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.weight_manage;
        int score = Score.ZERO;
        if(Check.NuNObj(houseDbInfoVo)){
            return score;
        }
        Integer weight = null;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            weight = houseDbInfoVo.getHouseWeight();
        }else if(houseDbInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
            weight = houseDbInfoVo.getRoomWeight();
        }else {
            //什么也不做
        }
        score = ValueUtil.getintValue(weight);
        Double avDou =BigDecimalUtil.mul(score, soreWeight.getCode());

        LogUtil.info(LOGGER,"【权重】后台设置权重:{}%,值：{}，得分：{}",soreWeight.getCode(),score,avDou.intValue());
        return avDou.intValue();
    }

    /**
     * 获取当前订单的百分比
     * @param houseDbInfoVo
     * @return
     */
    private int getScoreOrder(HouseDbInfoVo houseDbInfoVo){
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.trade_order_rate;
        int score = Score.ZERO;
        if(Check.NuNObj(houseDbInfoVo)){
            return score;
        }
        Double rate = this.getOrderRate(houseDbInfoVo);
        if(rate > 90.00){
            score = Score.FIVE;
        }else if(rate > 80.00 ){
            score = Score.FOUR;
        }else if(rate > 70.00 ){
            score = Score.THREE;
        }else if(rate > 60.00 ){
            score = Score.TWO;
        }else{
            score = Score.ONE;
        }
        Double orderDou =BigDecimalUtil.mul(score, soreWeight.getCode());
        LogUtil.info(LOGGER,"【权重】有效订单占比权重:{}%,值：{}， 得分：{}",soreWeight.getCode(),rate/100,orderDou.intValue());
        return orderDou.intValue();
    }




    /**
     * 获取当前的申请预订回复时间
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int getAvgAcceptTime(HouseDbInfoVo houseDbInfoVo){
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.reply_time;
        int score = Score.ZERO;
        if(Check.NuNObj(houseDbInfoVo)){
            return score;
        }

        Double avgTime = null;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            avgTime = orderDbInfoDao.getHouseAvgAcceptTime(houseDbInfoVo.getHouseFid());
        }else if(houseDbInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
            avgTime = orderDbInfoDao.getRoomAvgAcceptTime(houseDbInfoVo.getRoomFid());
        }else {
            //什么也不做
        }
        if (Check.NuNObj(avgTime)){
            avgTime = 0.0;
        }
        if(avgTime <= 5){
            score = Score.FIVE;
        }else if(avgTime <= 20){
            score = Score.FOUR;
        }else if(avgTime <= 35){
            score = Score.THREE;
        }else if(avgTime <= 60){
            score = Score.TWO;
        }else{
            score = Score.ONE;
        }
        Double orderDou =BigDecimalUtil.mul(score, soreWeight.getCode());
        LogUtil.info(LOGGER,"【权重】申请预订回复时间权重:{}%,值：{}， 得分：{}",soreWeight.getCode(),avgTime,orderDou.intValue());
        return orderDou.intValue();
    }

    /**
     * 获取当前的房源的审核通过的时间
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private Date getAndDealPassDate(HouseInfo houseInfo,HouseDbInfoVo houseDbInfoVo){

        Date passDate = null;
        if(Check.NuNObjs(houseInfo,houseDbInfoVo)){
            return passDate;
        }
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            passDate = houseDbInfoDao.getHousePassDate(houseDbInfoVo.getHouseFid());
        }else if(houseDbInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
            passDate = houseDbInfoDao.getRoomPassDate(houseDbInfoVo.getRoomFid());
        }
        if (Check.NuNObj(passDate)){
            //审核通过时间为空
            houseInfo.setPassDate(0L);
        }else {
            houseInfo.setPassDate(passDate.getTime());
        }
        return passDate;
    }


    /**
     * 获取当前订单的百分比
     * @param houseDbInfoVo
     * @return
     */
    private Double getOrderRate(HouseDbInfoVo houseDbInfoVo){
        Double intDou = 0.00;
        if(Check.NuNObj(houseDbInfoVo)){
            return intDou;
        }
        Long all =null;
        Long ok = null;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            all = orderDbInfoDao.countHouseOrder(houseDbInfoVo.getHouseFid());
            if(all > 0){
                ok = orderDbInfoDao.countHouseOrderEffective(houseDbInfoVo.getHouseFid());
            }
        }else if(houseDbInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
            all = orderDbInfoDao.countRoomOrder(houseDbInfoVo.getRoomFid());
            if(all > 0){
                ok = orderDbInfoDao.countRoomOrderEffective(houseDbInfoVo.getRoomFid());
            }
        }else {
            //什么也不做
        }
        if(!Check.NuNObjs(all,ok) && all>0){
            return BigDecimalUtil.mul(BigDecimalUtil.div(ok, all),100);
        }else {
            return intDou;
        }
    }
    
    /**
     * 评价数量 
     *
     * @author zl
     * @created 2016年12月5日 下午6:32:14
     *
     * @param houseDbInfoVo
     * @return
     */
    private int getScoreOrderEvalNum(HouseInfo houseInfo,HouseDbInfoVo houseDbInfoVo,Map<String,Object>  baseMap){
    	
    	String orderEvalNumKey = "orderEvalNum";
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.trade_eval;
        int score = Score.ZERO;
        if(Check.NuNObj(houseDbInfoVo)){
            return score;
        }
        if (!baseMap.containsKey(orderEvalNumKey)){
            baseMap.put(orderEvalNumKey,orderDbInfoDao.countOrderEvalAll());
        }

        Long evalAll = ValueUtil.getlongValue(baseMap.get(orderEvalNumKey));
        Long evalNumRank = null;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
        	evalNumRank = orderDbInfoDao.countHouseOrderEvalAllRank(houseDbInfoVo.getHouseFid());
        }else {
        	evalNumRank = orderDbInfoDao.countRoomOrderEvalAllRank(houseDbInfoVo.getRoomFid());
        }
        
        double percent =0;
        evalNumRank=Check.NuNObj(evalNumRank)?0:evalNumRank;
        
        percent=Check.NuNObj(evalAll)?0:BigDecimalUtil.div(evalNumRank,evalAll);

        if (percent <= 0.1){
        	score = Score.FIVE;
        }else if (percent <= 0.25){
        	score = Score.FOUR;
        }else if (percent <= 0.35){
        	score = Score.THREE;
        }else if (percent <= 0.45){
        	score = Score.TWO;
        }else {
        	score = Score.ONE;
        }
        
        Double avDou =BigDecimalUtil.mul(score, soreWeight.getCode());
        LogUtil.info(LOGGER,"【权重】评价数量权重:{}%,评价数量排名：{}，得分：{}",soreWeight.getCode(),evalNumRank,avDou.intValue());
        return avDou.intValue();
        
    }


    /**
     * 获取当前的订单的评价比例
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int getScoreOrderEval(HouseDbInfoVo houseDbInfoVo){
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.trade_eval_rate;

        WeightEvalVo weightEval = null;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            weightEval = orderDbInfoDao.getHouseEvalRate(houseDbInfoVo.getHouseFid());
        }else if(houseDbInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
            weightEval = orderDbInfoDao.getRoomEvalRate(houseDbInfoVo.getRoomFid());
        }else {
            //什么也不做
        }
        if (Check.NuNObj(weightEval)){
            weightEval = new WeightEvalVo();
            weightEval.setEvlNum(0);
            weightEval.setOrderNum(1);
        }
        
        double evalPercent =0;
        if(!Check.NuNObjs(weightEval,weightEval.getOrderNum()) && weightEval.getOrderNum()>0){
        	evalPercent = BigDecimalUtil.div(weightEval.getEvlNum(),weightEval.getOrderNum());
        }
        
        Integer evalScore = Score.ZERO;
        if (evalPercent > 0.9){
            evalScore = Score.FIVE;
        }else if (evalPercent > 0.8){
            evalScore = Score.FOUR;
        }else if (evalPercent > 0.7){
            evalScore = Score.THREE;
        }else if (evalPercent > 0.6){
            evalScore = Score.TWO;
        }else {
            evalScore = Score.ONE;
        }
        Double avDou =BigDecimalUtil.mul(evalScore, soreWeight.getCode());
        LogUtil.info(LOGGER,"【权重】评价数量比权重:{}%,比例：{}，得分：{}",soreWeight.getCode(),evalPercent,avDou.intValue());
        return avDou.intValue();
    }



    /**
     * 获取当前的有效订单数，并获取当前订单数量的占比
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int getScoreOrderNum(HouseInfo houseInfo,HouseDbInfoVo houseDbInfoVo,Map<String,Object>  baseMap){
        String key = "orderNum";
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.trade_order_num;
        int score = Score.ZERO;
        if(Check.NuNObj(houseDbInfoVo)){
            return score;
        }
        if (!baseMap.containsKey(key)){
            baseMap.put(key,orderDbInfoDao.countHouseEffective());
        }



        Long effectAll = ValueUtil.getlongValue(baseMap.get(key));

        Long effectNum = null;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            effectNum = orderDbInfoDao.countHouseOrderEffective(houseDbInfoVo.getHouseFid());
        }else if(houseDbInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
            effectNum = orderDbInfoDao.countRoomOrderEffective(houseDbInfoVo.getRoomFid());
        }else {
            //什么也不做
        }
        Long effectIndex = null;
        if (Check.NuNObj(effectNum) || effectNum == 0){
            effectIndex = 0L;
        }else {
            effectIndex = orderDbInfoDao.countHouseEffectiveByEffect(effectNum);
        }
        
        double numPercent =0;
        
        if (effectAll>0) {
        	numPercent = BigDecimalUtil.div(effectIndex,effectAll);
		} 
        
        Integer unmScore = Score.ZERO;
        if (Check.NuNObj(effectNum) || effectNum == 0){
            unmScore = Score.ZERO;
        }else if (numPercent < 0.1){
            unmScore = Score.FIVE;
        }else if (numPercent < 0.25){
            unmScore = Score.FOUR;
        }else if (numPercent < 0.35){
            unmScore = Score.THREE;
        }else if (numPercent < 0.45){
            unmScore = Score.TWO;
        }else {
            unmScore = Score.ONE;
        }
        Double avDou =BigDecimalUtil.mul(unmScore, soreWeight.getCode());
        LogUtil.info(LOGGER,"【权重】有效订单数权重:{}%,有效订单数量：{}，排名：{}，得分：{}",soreWeight.getCode(),effectNum,effectIndex,avDou.intValue());
        return avDou.intValue();
    }


    /**
     * 获取当前的订单统计信息
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private WeightOrderNumVo getOrderRateInfo(HouseDbInfoVo houseDbInfoVo){
        if (Check.NuNObj(houseDbInfoVo)){
            return null;
        }
        WeightOrderNumVo weightOrderNumVo = null;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            weightOrderNumVo = orderDbInfoDao.getHouseOrderRate(houseDbInfoVo.getHouseFid());
        }else {
            weightOrderNumVo = orderDbInfoDao.getRoomOrderRate(houseDbInfoVo.getRoomFid());
        }
        return weightOrderNumVo;
    }

    /**
     * 获取当前浏览量的数量
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int getScorePv(HouseInfo houseInfo,HouseDbInfoVo houseDbInfoVo,Map<String,Object>  baseMap){
        String pvKey = "pv";
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.trade_pv;
        int score = Score.ZERO;
        if(Check.NuNObj(houseDbInfoVo)){
            return score;
        }
        if (!baseMap.containsKey(pvKey)){
            baseMap.put(pvKey,houseDbInfoDao.countPvAll());
        }

        Long pvAll = ValueUtil.getlongValue(baseMap.get(pvKey));
        //目前只考虑评级分数暂不考虑评价数量：按照实际评价分数计算。
        Integer pv = null;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            pv = houseDbInfoDao.getHousePv(houseDbInfoVo.getHouseFid());
        }else {
            pv = houseDbInfoDao.getRoomPv(houseDbInfoVo.getRoomFid());
        }
        Long pvIndex = null;
        if (Check.NuNObj(pv)){
            pvIndex = 0L;
            pv = 0;
        }else {
            pvIndex = houseDbInfoDao.countPvByPv(pv);
        }
        double pvPercent = 0;
        if (!Check.NuNObjs(pvIndex,pvAll) && pvAll>0) {			
        	pvPercent = BigDecimalUtil.div(pvIndex,pvAll);
		}

        Integer pvScore = Score.ZERO;
        if (pvPercent == 0){
            //不包含浏览量
        }else if (pvPercent < 0.1){
            pvScore = Score.FIVE;
        }else if (pvPercent < 0.25){
            pvScore = Score.FOUR;
        }else if (pvPercent < 0.35){
            pvScore = Score.THREE;
        }else if (pvPercent < 0.45){
            pvScore = Score.TWO;
        }else {
            pvScore = Score.ONE;
        }
        Double avDou =BigDecimalUtil.mul(pvScore, soreWeight.getCode());
        LogUtil.info(LOGGER,"【权重】浏览量权重:{}%,浏览量：{}，占比：{}，得分：{}",soreWeight.getCode(),pv,pvPercent,avDou.intValue());
        return avDou.intValue();
    }


    /**
     * 获取当前的评级的权重
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int getScoreEvaluate(HouseInfo houseInfo,HouseDbInfoVo houseDbInfoVo){
        //权重
        int WEIGHT = 18;
        int score = Score.ZERO;
        if(Check.NuNObj(houseDbInfoVo)){
            return score;
        }
        //目前只考虑评级分数暂不考虑评价数量：按照实际评价分数计算。
        EvaluateDbInfoVo evaluateDbInfoVo = null;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            evaluateDbInfoVo = evaluateDbDao.getEvaluateByHouse(houseDbInfoVo.getHouseFid());
        }else {
            evaluateDbInfoVo = evaluateDbDao.getEvaluateByRoom(houseDbInfoVo.getRoomFid());
        }
        
       
        
        Double av = 0.00;
        if(Check.NuNObj(evaluateDbInfoVo)){
            //当前不存在评价，评价不得分
            //设置平均分
            houseInfo.setEvaluateScore(ValueUtil.getEvaluteSoreDefault(null));
            //真实评分
            houseInfo.setRealEvaluateScore(ValueUtil.getRealEvaluteSore(null));
            return 0;
        }
        //获取当前的评论数量
        houseDbInfoVo.setEvaluateCount(evaluateDbInfoVo.getEvaTotal());
        houseInfo.setEvaluateCount(evaluateDbInfoVo.getEvaTotal());

        if(!Check.NuNObj(evaluateDbInfoVo.getCostPerforAva())){
            av += evaluateDbInfoVo.getCostPerforAva();
        }
        if(!Check.NuNObj(evaluateDbInfoVo.getDesMatchAva())){
            av += evaluateDbInfoVo.getDesMatchAva();
        }
        if(!Check.NuNObj(evaluateDbInfoVo.getHouseCleanAva())){
            av += evaluateDbInfoVo.getHouseCleanAva();
        }
        if(!Check.NuNObj(evaluateDbInfoVo.getSafeDegreeAva())){
            av += evaluateDbInfoVo.getSafeDegreeAva();
        }
        if(!Check.NuNObj(evaluateDbInfoVo.getTrafPosAva())){
            av += evaluateDbInfoVo.getTrafPosAva();
        }
        double avReal = BigDecimalUtil.div(av, 5,5);
        //设置平均分
        houseInfo.setEvaluateScore(ValueUtil.getEvaluteSoreDefault(ValueUtil.getStrValue(avReal)));
        
        //真实评分
        houseInfo.setRealEvaluateScore(ValueUtil.getRealEvaluteSore(ValueUtil.getStrValue(avReal)));
        
        Double avDou =BigDecimalUtil.mul(avReal, WEIGHT);

        return avDou.intValue();
    }




    /**
     * 获取当前的IM回复时间
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int getScoreImTime(HouseDbInfoVo houseDbInfoVo){

        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.reply_im_time;
        int score = Score.ZERO;
        if(Check.NuNObj(houseDbInfoVo)){
            return score;
        }
        //
        Double imTime = null;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            imTime = messageDbInfoDao.getHouseMessageTime(houseDbInfoVo.getHouseFid());
        }else {
            imTime = messageDbInfoDao.getRoomMessageTime(houseDbInfoVo.getRoomFid());
        }
        if (Check.NuNObj(imTime)){

        }else if(imTime <= 5){
            score = Score.FIVE;
        }else if(imTime <= 10){
            score = Score.FOUR;
        }else if(imTime <= 15){
            score = Score.THREE;
        }else if(imTime <= 30){
            score = Score.TWO;
        }else{
            score = Score.ONE;
        }
        Double orderDou =BigDecimalUtil.mul(score, soreWeight.getCode());
        LogUtil.info(LOGGER,"【权重】IM回复时间权重:{}%,值：{}， 得分：{}",soreWeight.getCode(),imTime,orderDou.intValue());
        return orderDou.intValue();
    }


    /**
     * 获取当前的IM回复率
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int getScoreImReply(HouseDbInfoVo houseDbInfoVo){

        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.reply_im_rate;
        int score = Score.ZERO;
        if(Check.NuNObj(houseDbInfoVo)){
            return score;
        }
        //
        WeightMessageVo weightMessageVo = null;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            weightMessageVo = messageDbInfoDao.getHouseMessageRate(houseDbInfoVo.getHouseFid());
        }else {
            weightMessageVo = messageDbInfoDao.getRoomMessageRate(houseDbInfoVo.getRoomFid());
        }
        if (Check.NuNObj(weightMessageVo)){
            weightMessageVo = new WeightMessageVo();
            weightMessageVo.setReplyNum(0);
            weightMessageVo.setMessageNum(1);

        }
        //当前的回复率
        Double rate =0.0;
        if (!Check.NuNObjs(weightMessageVo.getReplyNum(),weightMessageVo.getMessageNum()) && weightMessageVo.getMessageNum()>0) {
        	rate =BigDecimalUtil.div(ValueUtil.getintValue(weightMessageVo.getReplyNum()),ValueUtil.getintValue(weightMessageVo.getMessageNum()));
		}
        if(rate >= 0.9){
            score = Score.FIVE;
        }else if(rate >= 0.8){
            score = Score.FOUR;
        }else if(rate >= 0.7){
            score = Score.THREE;
        }else if(rate >= 0.5){
            score = Score.TWO;
        }else{
            score = Score.ONE;
        }
        Double orderDou =BigDecimalUtil.mul(score, soreWeight.getCode());
        LogUtil.info(LOGGER,"【权重】IM回复率权重:{}%,值：{}， 得分：{}",soreWeight.getCode(),rate,orderDou.intValue());
        return orderDou.intValue();
    }




    /**
     * 获取当前房源的价格的权重
     * @author afi
     * @param rentWay
     * @param priceOrg
     * @return
     */
    private int getScorePrice(int rentWay,Integer priceOrg){
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.base_price;
        int score = Score.ZERO;
        if(Check.NuNObj(priceOrg)){
            return score;
        }
        //转化成元
        double price = BigDecimalUtil.div(priceOrg,100);
        if(rentWay == RentWayEnum.HOUSE.getCode() &&  price > 800 ){
            score = Score.ONE;
        }else if(rentWay == RentWayEnum.HOUSE.getCode() && (price > 700 && price <= 800) ){
            score = Score.TWO;
        }else if(rentWay == RentWayEnum.HOUSE.getCode() && ((price > 600 && price <= 700) || (price <= 200))){
            score = Score.THREE;
        }else if(rentWay == RentWayEnum.HOUSE.getCode() && ((price > 500 && price <= 600) || (price > 200 && price <= 300))){
            score = Score.FOUR;
        }else if(rentWay == RentWayEnum.HOUSE.getCode() && (price > 300 && price <= 500)){
            score = Score.FIVE;
        }else if(rentWay == RentWayEnum.ROOM.getCode() &&  price > 600 ){
            score = Score.ONE;
        }else if(rentWay == RentWayEnum.ROOM.getCode() && (price > 500 && price <= 600) ){
            score = Score.TWO;
        }else if(rentWay == RentWayEnum.ROOM.getCode() && ((price > 400 && price <= 500) || (price <= 150))){
            score = Score.THREE;
        }else if(rentWay == RentWayEnum.ROOM.getCode() && ((price > 300 && price <= 400) || (price > 150 && price <= 200))){
            score = Score.FOUR;
        }else if(rentWay == RentWayEnum.ROOM.getCode() && (price > 200 && price <= 300)){
            score = Score.FIVE;
        }
        int rst = score * soreWeight.getCode();
        LogUtil.info(LOGGER,"【权重】价格权重:{}%, 值：{}元，得分：{}",soreWeight.getCode(),price,rst);
        return rst;
    }


    /**
     * 获取当前的信息完整率 暂时还没用到
     * @author afi
     * @param intactRate
     * @return
     */
    @Deprecated
    private int getScoreFullRate(Double intactRate){
        //权重
        int WEIGHT = 0;
        int score = Score.ZERO;
        if(Check.NuNObj(intactRate)){
            return score;
        }
        //信息完整：90%<x<=100%：5分；80%<x<=90%：4分；70%<x<=80%：3分；60%<x<=70%：2分；x<=60%：1分；
        if(intactRate > 90.00){
            score = Score.FIVE;
        }else if(intactRate > 80.00 && intactRate <= 90.00){
            score = Score.FOUR;
        }else if(intactRate > 70.00 && intactRate <= 80.00){
            score = Score.THREE;
        }else if(intactRate > 60.00 && intactRate <= 70.00){
            score = Score.TWO;
        }else{
            score = Score.ONE;
        }
        return score * WEIGHT;
    }

    /**
     * 地铁距离
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int getSubWayScoreDis(HouseDbInfoVo houseDbInfoVo){
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.region_subway_dis;
        int score = this.getDisSore(houseDbInfoVo.getSubWayDisMin());
        int rst = score * soreWeight.getCode();
        LogUtil.info(LOGGER,"【权重】地铁距离权重:{}%,值：{}，得分：{}",soreWeight.getCode(),houseDbInfoVo.getSubWayDisMin(),rst);
        return rst;
    }

    /**
     * 商圈距离权重
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int getScoreDis(HouseDbInfoVo houseDbInfoVo){
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.region_dis;
        int score = this.getDisSore(houseDbInfoVo.getRegionDisMin());
        int rst = score * soreWeight.getCode();
        LogUtil.info(LOGGER,"【权重】景点商圈距离权重:{}%,值：{}，得分：{}",soreWeight.getCode(),houseDbInfoVo.getRegionDisMin(),rst);
        return rst;
    }

    /**
     * 获取当前距离的分值
     * @author afi
     * @param disOrg
     * @return
     */
    private int getDisSore(Double disOrg){
        int score = Score.ZERO;
        if(Check.NuNObj(disOrg)){
            return score;
        }
        double dis = ValueUtil.getdoubleValue(disOrg);
        if(dis < 0.5){
            score = Score.FIVE;
        }else if(dis <= 1.0){
            score = Score.FOUR;
        }else if(dis <= 1.5){
            score = Score.THREE;
        }else if(dis < 2.0){
            score = Score.TWO;
        }else{
            score = Score.ONE;
        }
        return score;
    }


    /**
     * 当前面积的权重
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int getScoreArea(HouseDbInfoVo houseDbInfoVo){

        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.base_area;

        int score = Score.ZERO;
        double area = 0;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            area = ValueUtil.getdoubleValue(houseDbInfoVo.getHouseArea());
            if(area <= 60){
                score = Score.ONE;
            }else if(area <= 80){
                score = Score.TWO;
            }else if(area <= 100){
                score = Score.THREE;
            }else if(area <= 140){
                score = Score.FOUR;
            }else {
                score = Score.FIVE;
            }

        }else if(houseDbInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
            area = ValueUtil.getdoubleValue(houseDbInfoVo.getRoomArea());
            if(area <= 10){
                score = Score.ONE;
            }else if(area <= 15){
                score = Score.TWO;
            }else if(area <= 20){
                score = Score.THREE;
            }else if(area <= 25){
                score = Score.FOUR;
            }else{
                score = Score.FIVE;
            }
        }
        int rst =  score * soreWeight.getCode();
        LogUtil.info(LOGGER,"【权重】面积权重:{}%,值：{}得分：{}",soreWeight.getCode(),area,rst);
        return rst;
    }
    
    
    /**
     * 房源品质登记的权重
     * @author zl
     * @param houseDbInfoVo
     * @return
     */
    private int getQualityGradeScore(HouseDbInfoVo houseDbInfoVo){

        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.base_quality;

        int score = Score.ZERO;
        String houseQualityGrade = ValueUtil.getTrimStrValue(houseDbInfoVo.getHouseQualityGrade());
        if(QualityGradeEnum.GRADE_A.getCode().equalsIgnoreCase(houseQualityGrade) || QualityGradeEnum.GRADE_A_PLUS.getCode().equalsIgnoreCase(houseQualityGrade)){
        	score = Score.FIVE;
        }else if(QualityGradeEnum.GRADE_B_PLUS.getCode().equalsIgnoreCase(houseQualityGrade)){
        	score = Score.FOUR;
        }else if(QualityGradeEnum.GRADE_B.getCode().equalsIgnoreCase(houseQualityGrade)){
        	score = Score.THREE;
        }else if(QualityGradeEnum.GRADE_B_SUBTRACT.getCode().equalsIgnoreCase(houseQualityGrade)){
        	score = Score.TWO;
        }else if(QualityGradeEnum.GRADE_C.getCode().equalsIgnoreCase(houseQualityGrade)){
        	score = Score.ONE;
        }
        
        int rst =  score * soreWeight.getCode();
        LogUtil.info(LOGGER,"【权重】房源品质:{}%,值：{}得分：{}",soreWeight.getCode(),houseQualityGrade,rst);
        return rst;
    }
    
    private int getImmediatelyBookScore(HouseDbInfoVo houseDbInfoVo){
    	
    	//权重
        SoreWeightEnum soreWeight = SoreWeightEnum.immediately_book;
        
    	int ib=ValueUtil.getintValue(houseDbInfoVo.getOrderType());
    	int score = Score.ZERO;
    	if (ib==1) {
    		score = Score.FIVE;
		}
    	 int rst =  score * soreWeight.getCode();
         LogUtil.info(LOGGER,"【权重】立即预定:{}%,值：{}得分：{}",soreWeight.getCode(),ib,rst);
         return rst;
    	
    }
    
    private int getIsLandTogetherScore(HouseDbInfoVo houseDbInfoVo){
    	
    	//权重
        SoreWeightEnum soreWeight = SoreWeightEnum.weight_is_land_together;
        
    	int ib=ValueUtil.getintValue(houseDbInfoVo.getIsHezhu());
    	int score = Score.ZERO;
    	if (ib==1) {
    		score = Score.FIVE;
		}
    	 int rst =  score * soreWeight.getCode();
         LogUtil.info(LOGGER,"【权重】是否与房东同住:{}%,值：{}得分：{}",soreWeight.getCode(),ib,rst);
         return rst;
    	
    }


    /**
     * 获取当前的商圈热度
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int getScoreHot(HouseDbInfoVo houseDbInfoVo){
        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.region_hot;
        int score = Score.ZERO;
        if(Check.NuNObj(houseDbInfoVo.getHotMax())){
            return score;
        }
        int hot = ValueUtil.getintValue(houseDbInfoVo.getHotMax());
        if(hot < 80){
            score = Score.ONE;
        }else if(hot <= 85){
            score = Score.TWO;
        }else if(hot <= 90){
            score = Score.THREE;
        }else if(hot < 95){
            score = Score.FOUR;
        }else{
            score = Score.FIVE;
        }
        int rst = score * soreWeight.getCode();
        LogUtil.info(LOGGER,"【权重】商圈景点热度权重:{}%,值：{}，得分：{}",soreWeight.getCode(),hot,rst);
        return rst;
    }


    /**
     * 获取当前的刷新值 或者当前房源日历的最新时间
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private int getScoreRefreshDate(HouseDbInfoVo houseDbInfoVo){
        if (Check.NuNObj(houseDbInfoVo)){
            return 0;
        }

        //权重
        SoreWeightEnum soreWeight = SoreWeightEnum.base_refresh_date;
        Date refreshDate = null;
        if (houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            refreshDate = orderDbInfoDao.getHouseCalendarFreshTime(houseDbInfoVo.getHouseFid());
        }else if (houseDbInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
            refreshDate = orderDbInfoDao.getRoomCalendarFreshTime(houseDbInfoVo.getRoomFid());
        }
        if (!Check.NuNObj(refreshDate)){
            if (!Check.NuNObj(houseDbInfoVo.getRefreshDate()) && houseDbInfoVo.getRefreshDate().after(refreshDate)){
                refreshDate = houseDbInfoVo.getRefreshDate();
            }
        }else {
            refreshDate = houseDbInfoVo.getRefreshDate();
        }
        int WEIGHT = soreWeight.getCode();
        int score = Score.ZERO;
        if(Check.NuNObj(refreshDate)){
            return score;
        }
        Date now = new Date();
        if(now.before(refreshDate)){
            score = Score.FIVE;
            return score;
        }
        int dayCount = DateUtil.getDatebetweenOfDayNum(refreshDate, now);
        if(dayCount > 7){
            score = Score.ONE;
        }else if(dayCount > 3 && dayCount <= 7){
            score = Score.TWO;
        }else if(dayCount > 2 && dayCount <= 3){
            score = Score.THREE;
        }else if(dayCount > 1 && dayCount <= 2){
            score = Score.FOUR;
        }else{
            score = Score.FIVE;
        }
        int rst = score * WEIGHT;
        LogUtil.info(LOGGER,"【权重】刷新时间权重:{}%,值：{},得分：{}",soreWeight.getCode(),DateUtil.timestampFormat(refreshDate),rst);
        return rst;
    }


    /**
     * 填充房源的商圈景点信息
     * @author afi
     * @param houseDbInfoVo
     * @param houseInfo
     * @param reginMap
     * @param km
     */
    private void dealRegin(HouseDbInfoVo houseDbInfoVo,HouseInfo houseInfo,Map<String,List<HotRegionVo>>  reginMap,Integer km){
        //先从本地缓存获取城市的热门区域
        try {
            if (Check.NuNStr(houseDbInfoVo.getLongitude())
                    ||  Check.NuNStr(houseDbInfoVo.getLatitude())){
                return;
            }

            Set<String> businessSet = new HashSet<>();
            Set<String> scenicSet = new HashSet<>();
            Set<String> subWaySet = new HashSet<>();
            //当前的地铁线
            Set<String> lineFidSet = new HashSet<>();
            //距离地铁的最小距离
            double disMin = km;
            //所在商圈的最大热度
            int  hotMax = 0;
            List<HotRegionVo> hotRegionVoList = null;
            String cityCode = houseDbInfoVo.getCityCode();
            if(!reginMap.containsKey(cityCode)){
                hotRegionVoList = hotRegionDbInfoDao.getHotRegionList(cityCode);
                //给map赋值
                reginMap.put(cityCode,hotRegionVoList==null?new ArrayList<HotRegionVo>():hotRegionVoList);
            }
            //获取区域
            hotRegionVoList = reginMap.get(cityCode);
            for(HotRegionVo hot:hotRegionVoList){
                String dis = MapDistanceUtil.getDistance(houseDbInfoVo.getLongitude(), houseDbInfoVo.getLatitude(), hot.getLongitude(), hot.getLatitude());
                
                if(hot.getRegionType() == RegionTypeEnum.BUSINESS.getCode()){
                    //商圈
                	if (!Check.NuNObj(hot.getRadii()) && hot.getRadii()>0) {
                		if (ValueUtil.getdoubleValue(dis) < hot.getRadii()) {
                			scenicSet.add(hot.getRegionName());
						}
                	}else if(ValueUtil.getdoubleValue(dis) < km){
                        businessSet.add(hot.getRegionName());
                    }
                }else if(hot.getRegionType() == RegionTypeEnum.SCENIC.getCode()){
                    //景点
                	if (!Check.NuNObj(hot.getRadii()) && hot.getRadii()>0 ) {
                		if (ValueUtil.getdoubleValue(dis) < hot.getRadii()) {
                			scenicSet.add(hot.getRegionName());
						}
                	}else if(ValueUtil.getdoubleValue(dis) < km){
                        scenicSet.add(hot.getRegionName());
                    }
                }
                //设置最热度
                int hotValue = ValueUtil.getintValue(hot.getHot());
                if (hotMax < hotValue){
                    hotMax = hotValue;
                }
                //设置最小距离
                if (disMin > ValueUtil.getdoubleValue(dis)){
                    disMin = ValueUtil.getdoubleValue(dis);
                }
            }
            //设置景点商圈的最小距离
            houseDbInfoVo.setRegionDisMin(disMin);
            //设置商圈的最热度
            houseDbInfoVo.setHotMax(hotMax);

           //當前商圈景點的熱度
           Set<String> reginSet = new HashSet<>();
           reginSet.addAll(businessSet);
           reginSet.addAll(scenicSet);
           
           //景点商圈当前热度
           dealCurrentReginHotByName( houseDbInfoVo, reginSet);
           
            List<HotRegionVo> subList = null;
            String subKey = houseDbInfoVo.getCityCode()+"_s";
            if(!reginMap.containsKey(subKey)){
                subList = hotRegionDbInfoDao.getSubWayList(houseDbInfoVo.getCityCode());
                //给map赋值
                reginMap.put(subKey,subList==null?new ArrayList<HotRegionVo>():subList);
            }
            //获取区域
            subList = reginMap.get(subKey);
            //距离地铁的最小距离
            double subWayDisMin = km;
            for(HotRegionVo sub:subList){
                String dis = MapDistanceUtil.getDistance(houseDbInfoVo.getLongitude(), houseDbInfoVo.getLatitude(), sub.getLongitude(), sub.getLatitude());
                if(ValueUtil.getdoubleValue(dis) < km){
                    //当前的地铁距离满足当前的条件
                    subWaySet.add(sub.getRegionName());
                    lineFidSet.add(sub.getLineFid());
                }
                //设置最小距离
                if (subWayDisMin > ValueUtil.getdoubleValue(dis)){
                    subWayDisMin = ValueUtil.getdoubleValue(dis);
                }
            }
            //设置地铁的最小距离
            houseDbInfoVo.setSubWayDisMin(subWayDisMin);
            //设置商圈景点地铁
            houseInfo.setHotReginBusiness(businessSet);
            houseInfo.setHotReginScenic(scenicSet);
            houseInfo.setSubway(subWaySet);
            houseInfo.setLineFidSet(lineFidSet);
        }catch (Exception e){
            LogUtil.error(LOGGER, "houseDbInfoVo:{}, e:{}", houseDbInfoVo, e);
        }

    }
    
    
    
  /**
   * 查詢當前最熱的景點商圈下的房源，統計當前熱度 
   *
   * @author zl
   * @created 2016年12月5日 下午2:34:37
   *
   * @param houseDbInfoVo
   * @return
   */
    private void dealCurrentReginHotByName(HouseDbInfoVo houseDbInfoVo, Set<String> reginSet) {
    	if (Check.NuNCollection(reginSet)) {
    		return;
    	}
        
    	try {
    		int maxHot =0;
	     	QueryRequest request = new QueryRequest();
	     	request.setPageSize(50); 
	     	
	     	for (String reginName : reginSet) {
				Integer hot =reginCurrentHotMap.get(reginName);
				if(Check.NuNObj(hot)){
					hot=0;
				}
				
	     		if (maxHot < hot){
	     			maxHot = hot;
                }
	     		
			}
	     	
	     	houseDbInfoVo.setCurrentReginMaxHot(maxHot);	
     		
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查詢景點商圈當前的熱度異常e={}", e);
		}
				
	}


    /**
     * 获取房源的截止时间
     * @author afi
     * @param houseDbInfoVo
     * @return
     */
    private Long getHouseEndTime(HouseDbInfoVo houseDbInfoVo){
        Date tillDate = houseDbInfoVo.getTillDate();
        if(Check.NuNObj(tillDate)){
            tillDate = DateSplitUtil.getNextYear(new Date());
        }
        return tillDate.getTime();
    }

    /**
     * 处理当前房屋的价格
     * 包括特殊价格
     * @author afi
     * @param houseDbInfoVo
     * @param houseInfo
     */
    private void dealHousePrices(HouseDbInfoVo houseDbInfoVo,HouseInfo houseInfo){
        if(Check.NuNObj(houseInfo)){
            return;
        }
        //处理当前的房源的价格信息
        houseInfo.setPrice(getHousePrice(houseDbInfoVo));
        //获取当前的特殊价格

        if(Check.NuNObj(houseDbInfoVo.getRentWay())){
            return;
        }
        List<HousePriceVo> prices = null;

        List<HousePriceVo> weekPrices = null;

        int rentType = houseDbInfoVo.getRentWay().intValue();

        if(rentType == RentWayEnum.HOUSE.getCode()){
            prices = houseDbInfoDao.getHousePrices(houseDbInfoVo.getHouseFid());
            weekPrices = houseDbInfoDao.getHouseWeekPrices(houseDbInfoVo.getHouseFid());
        }else if (rentType == RentWayEnum.ROOM.getCode()){
            prices = houseDbInfoDao.getRoomPrices(houseDbInfoVo.getRoomFid());
            weekPrices = houseDbInfoDao.getRoomWeekPrices(houseDbInfoVo.getRoomFid());
        }else if (rentType == RentWayEnum.VIRTUAL.getCode()){
            prices = houseDbInfoDao.getHousePrices(houseDbInfoVo.getHouseFid());
        }
        Set<String> priceSet = new HashSet<>();
        Set<String> weekPriceSet = new HashSet<>();
        String nowKey = DateUtil.dateFormat(new Date());
        //是否特殊价格
        boolean spPrice = false;
        //校验当前存在特殊价格
        if(!Check.NuNCollection(prices)){
            for(HousePriceVo price:prices){
                if (price.getSetTime().equals(nowKey)){
                    spPrice = true;
                    houseInfo.setPrice(ValueUtil.getintValue(price.getPrice()));
                }
                String priceStr = price.getSetTime() + "," + price.getPrice();
                priceSet.add(priceStr);
            }
        }
        //当天周几
        WeekEnum cw = WeekEnum.getWeek(new Date());
        //校验当前存在特殊价格
        if(!Check.NuNCollection(weekPrices)){
            for(HousePriceVo price:weekPrices){
                if (!spPrice && price.getSetTime().equals(ValueUtil.getStrValue(cw.getNumber()))){
                    houseInfo.setPrice(ValueUtil.getintValue(price.getPrice()));
                }
                String weekPriceStr = price.getSetTime() + "," + price.getPrice();
                weekPriceSet.add(weekPriceStr);
            }
        }
        houseInfo.setWeekPrices(weekPriceSet);
        houseInfo.setPrices(priceSet);
    }

    /**
     * 获取当前的入住人数的限制
     * @param houseDbInfoVo
     * @return
     */
    private int getPersonCount(HouseDbInfoVo houseDbInfoVo){
        Integer personCount = null;
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            personCount = houseDbInfoVo.getHouseCheckInLimit();
        }else if (houseDbInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
            personCount = houseDbInfoVo.getRoomCheckInLimit();
        }else if (houseDbInfoVo.getRentWay() == RentWayEnum.VIRTUAL.getCode()){
            personCount = houseDbInfoVo.getHouseCheckInLimit();
        }
        if (Check.NuNObj(personCount)){
            personCount = SolrConstant.default_person_count;
        }else if (personCount == SolrConstant.default_person_count_show){
            personCount = SolrConstant.default_person_count;
        }
        return personCount;
    }


    /**
     * 获取搜索的逻辑id
     * @param houseDbInfoVo
     * @return
     */
    private String getFid(HouseDbInfoVo houseDbInfoVo){
        String fid = "";
        int rentType = houseDbInfoVo.getRentWay().intValue();
        if(rentType == RentWayEnum.HOUSE.getCode()){
            fid = houseDbInfoVo.getHouseFid() + "_" + RentWayEnum.HOUSE.getCode();
        }else if (rentType == RentWayEnum.ROOM.getCode()){
            fid = houseDbInfoVo.getRoomFid() + "_" + RentWayEnum.ROOM.getCode();
        }else if (rentType == RentWayEnum.VIRTUAL.getCode()){
            fid = houseDbInfoVo.getRoomFid() + "_" + RentWayEnum.VIRTUAL.getCode();
        }
        return fid;
    }

    /**
     * 获取搜索的逻辑id
     * @param houseDbInfoVo
     * @return
     */
    private String getFid(HouseInfo houseDbInfoVo){
        String fid = "";
        if(houseDbInfoVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
            fid = houseDbInfoVo.getHouseId() + "_" + RentWayEnum.HOUSE.getCode();
        }else if (houseDbInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
            fid = houseDbInfoVo.getHouseId() + "_" + RentWayEnum.ROOM.getCode();
        }else if (houseDbInfoVo.getRentWay() == RentWayEnum.VIRTUAL.getCode()){
            fid = houseDbInfoVo.getHouseId() + "_" + RentWayEnum.VIRTUAL.getCode();
        }
        return fid;
    }


    public static class Score{

        private  static Integer ZERO = 0;

        private  static Integer ONE = 1;

        private  static Integer TWO = 2;

        private  static Integer THREE = 3;

        private  static Integer FOUR = 4;

        private  static Integer FIVE = 5;

        private  static Integer SIX = 6;

        private  static Integer SEVEN = 7;

        private  static Integer EIGHT = 8;

        private  static Integer NINE = 9;

        private  static Integer TEN = 10;


    }
}
