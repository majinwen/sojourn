package com.ziroom.minsu.services.search.proxy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.services.basedata.constant.BaseDataConstant;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SearchConstant;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.lianjia.service.SyncHousesInfoServiceImpl;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.dto.HouseListByBrandSnListRequest;
import com.ziroom.minsu.services.search.dto.HouseListRequset;
import com.ziroom.minsu.services.search.dto.HouseSearchOneRequest;
import com.ziroom.minsu.services.search.dto.HouseSearchRequest;
import com.ziroom.minsu.services.search.dto.LandHouseRequest;
import com.ziroom.minsu.services.search.dto.LocationConditionDistrictsResult;
import com.ziroom.minsu.services.search.dto.LocationConditionResult;
import com.ziroom.minsu.services.search.dto.LocationConditionSubwaysResult;
import com.ziroom.minsu.services.search.dto.Top50HouseListRequest;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.search.service.AutoServiceImpl;
import com.ziroom.minsu.services.search.service.FreshIndexServiceImpl;
import com.ziroom.minsu.services.search.service.SearchConditionServiceImpl;
import com.ziroom.minsu.services.search.service.SearchIndexServiceImpl;
import com.ziroom.minsu.services.search.service.SearchServiceImpl;
import com.ziroom.minsu.services.search.service.SpellIndexServiceImpl;
import com.ziroom.minsu.services.search.service.SuggestIndexServiceImpl;
import com.ziroom.minsu.services.search.vo.HotRegionSimpleVo;
import com.ziroom.minsu.services.search.vo.HotRegionVo;
import com.ziroom.minsu.services.search.vo.LocationNestingStructureVo;
import com.ziroom.minsu.services.search.vo.StaticResourceVo;
import com.ziroom.minsu.services.search.vo.SubwayStationVo;
import com.ziroom.minsu.services.search.vo.SubwayVo;
import com.ziroom.minsu.services.solr.constant.SolrConstant;
import com.ziroom.minsu.valenum.base.RegionTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.search.IconPicTypeEnum;
import com.ziroom.minsu.valenum.search.SortTypeEnum;

/**
 * <p>搜素的代理层</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/16
 * @version 1.0
 * @since 1.0
 */
@Component("search.searchProxy")
public class SearchProxy implements SearchService {


    private static final Logger LOGGER = LoggerFactory.getLogger(SearchProxy.class);

    @Resource(name = "search.searchConditionServiceImpl")
    SearchConditionServiceImpl searchConditionServiceImpl;

    @Resource(name = "search.searchServiceImpl")
    SearchServiceImpl searchService;

    @Resource(name = "search.freshIndexServiceImpl")
    FreshIndexServiceImpl freshIndexService;

    @Resource(name = "search.searchIndexServiceImpl")
    SearchIndexServiceImpl searchIndexService;

    @Resource(name = "search.syncHousesInfoServiceImpl")
    SyncHousesInfoServiceImpl syncHousesInfoService;

    @Resource(name = "search.suggestIndexServiceImpl")
    private SuggestIndexServiceImpl suggestIndexService;


    @Resource(name="search.spellIndexServiceImpl")
    private SpellIndexServiceImpl spellIndexService;

    @Resource(name = "search.autoServiceImpl")
    AutoServiceImpl autoService;

    @Autowired
    private RedisOperations redisOperations;

    @Resource(name = "search.messageSource")
    private MessageSource messageSource;

    @Value("#{'${default_pic_size}'.trim()}")
    private String defaultPicSize;


    /**
     * 获取分词之后的数据
     * @param txt
     * @return
     */
    public String getChangzuIkList(String txt){
        DataTransferObject dto = new DataTransferObject();
        //非空校验
        if(Check.NuNObj(txt)){
            return dto.toJsonString();
        }
        try{
            List<String> ikList = searchService.getChangzuIkList(txt);
            dto.putValue("ikList", ikList);
            if (Check.NuNCollection(ikList)){
                dto.putValue("isSensitive", YesOrNoEnum.NO.getCode());
            }else {
                dto.putValue("isSensitive", YesOrNoEnum.YES.getCode());
            }
        }catch (Exception e){
            LogUtil.error(LOGGER, "获取长租分词之后的数据异常[getChangzuIkList],param ={},e:{}",txt,e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
        }
        return dto.toJsonString();

    }


    /**
     * 获取分词之后的数据
     * @param txt
     * @return
     */
    public String getIkList(String txt){
        DataTransferObject dto = new DataTransferObject();
        //非空校验
        if(Check.NuNObj(txt)){
            return dto.toJsonString();
        }
        try{
            List<String> ikList = searchService.getIkList(txt);
            dto.putValue("ikList", ikList);
            if (Check.NuNCollection(ikList)){
                dto.putValue("isSensitive", YesOrNoEnum.NO.getCode());
            }else {
                dto.putValue("isSensitive", YesOrNoEnum.YES.getCode());
            }
        }catch (Exception e){
            LogUtil.error(LOGGER, "获取分词之后的数据异常[getIkList],param={},e:{}",txt, e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
        }
        return dto.toJsonString();

    }


    /**
     * 获取补全信息
     * @param suggest
     * @return
     */
    @Override
    public String getOneCommunityInfo(String suggest,String cityCode){
        DataTransferObject dto = new DataTransferObject();
        //非空校验
        if(Check.NuNObj(suggest)){
            dto.putValue("info", new HashMap<>());
            return dto.toJsonString();
        }
        try{
            QueryResult queryResult = autoService.getCommunityName(suggest, cityCode);
            if(queryResult.getTotal() > 0){
                List<Object> rst = (List)queryResult.getValue();
                dto.putValue("info", rst.get(0));
            }else {
                dto.putValue("info", new HashMap<>());
            }
        }catch (Exception e){
            LogUtil.error(LOGGER, "获取一条补全信息异常 [getOneCommunityInfo],param={},cityCode={}, e:{}",suggest,cityCode, e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }


    /**
     * 获取补全信息
     * @param suggest
     * @return
     */
    @Override
    public String getCommunityInfo(String suggest,String cityCode){
        DataTransferObject dto = new DataTransferObject();
        //非空校验
        if(Check.NuNObj(suggest)){
            dto.putValue("list", 0);
            dto.putValue("total", new ArrayList<>());
            return dto.toJsonString();
        }
        try{
            QueryResult queryResult = autoService.getCommunityName(suggest, cityCode);
            dto.putValue("list", queryResult.getValue());
            dto.putValue("total", queryResult.getTotal());
        }catch (Exception e){
            LogUtil.error(LOGGER, "获取补全信息异常 [getCommunityInfo],param={},cityCode={}, e:{}",suggest, cityCode,e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }

    /**
     * 获取房客端的补全信息
     * @param suggest
     * @return
     */
    @Override
    public String getSuggestInfo(String suggest,String cityCode){
        DataTransferObject dto = new DataTransferObject();
        //非空校验
        if(Check.NuNObj(suggest)){
            dto.putValue("list", new ArrayList<>());
            dto.putValue("total", 0);
            return dto.toJsonString();
        }
        try{
            QueryResult queryResult = autoService.getSuggestInfo(suggest, cityCode);
            dto.putValue("list", queryResult.getValue());
            dto.putValue("total", queryResult.getTotal());
        }catch (Exception e){
            LogUtil.error(LOGGER, "获取房客端的补全信息异常 [getSuggestInfo],param={},cityCode={},e:{}",suggest,cityCode, e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }

    /**
     * 获取房源名称的补全信息
     * @param pre
     * @return
     */
    @Override
    public String getComplateTermsCommunityName(String pre) {
        DataTransferObject dto = new DataTransferObject();
        //非空校验
        if(Check.NuNObj(pre)){
            dto.putValue("comSet", new HashSet<>());
           return dto.toJsonString();
        }
        try{
            dto.putValue("comSet", searchService.getComplateTermsCommunityName(pre).toString());
        }catch (Exception e){
            LogUtil.error(LOGGER, "获取房源名称的补全信息异常 [getComplateTermsCommunityName],param={},e:{}",pre, e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }
    
    
    /**
     * 获取单个房源信息
     * @author afi
     * @return
     */
    public String getOneHouseInfo(String searchOneRequestJson){
        DataTransferObject dto = new DataTransferObject();
        try {
           
        	HouseSearchOneRequest requset = JsonEntityTransform.json2Object(searchOneRequestJson,HouseSearchOneRequest.class);
        	if(Check.NuNObjs(requset,requset.getFid(),requset.getRentWay())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数错误");
            }
        	
        	HouseInfoRequest houseInfoRequest = new HouseInfoRequest();        	
        	BeanUtils.copyProperties(requset, houseInfoRequest);
			dto = vilidateHouseListBaseParam(houseInfoRequest);
			if (dto.getCode() == DataTransferObject.ERROR) {
				return dto.toJsonString();
			}    
        	
        	// 条件查询后台用户
            QueryResult houseListInfo = searchService.getOneHouseInfo(requset);

            if (!Check.NuNObj(houseListInfo) && !Check.NuNObj(houseListInfo.getValue())) {
            	List<HouseInfoEntity> list = (List<HouseInfoEntity>) houseListInfo.getValue();
            	if (!Check.NuNCollection(list) && !Check.NuNObj(list.get(0))) {
            		 dto.putValue("houseInfo",list.get(0));
				}
			}

        } catch (Exception e) {
            LogUtil.error(LOGGER, "获取单个房源信息异常 [getOneHouseInfo] error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }
    
    

    /**
     * 获取最新房源
     * @author afi
     * @param picSize
     * @return
     */
    public String getNewHouseLst(String picSize){
        DataTransferObject dto = new DataTransferObject();
        try {
            // 条件查询后台用户
            QueryResult houseListInfo = searchService.getNewHouseLst(picSize,null);

            dto.putValue("list",houseListInfo.getValue());

        } catch (Exception e) {
            LogUtil.error(LOGGER, "获取最新房源异常 [getNewHouseLst] error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }




    /**
     * 获取房东的房源列表
     * @author afi
     * @param landJson
     * @return
     */
    public String getLandHouseList(String picSize,String landJson){

        DataTransferObject dto = new DataTransferObject();
        try {
            LandHouseRequest request = JsonEntityTransform.json2Object(landJson, LandHouseRequest.class);
            if(Check.NuNObj(request)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto.toJsonString();
            }
            if(Check.NuNObjs(request.getLandlordUid())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto.toJsonString();
            }
            //参数校验
            if(!Check.NuNStr(request.getStartTime()) &&  !Check.NuNStr(request.getEndTime())){
                try {
                    Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(request.getStartTime()), "yyyy-MM-dd");
                    Date endTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(request.getEndTime()), "yyyy-MM-dd");
                    if(startTime.after(endTime)){
                        dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                        dto.setMsg(" startTime is after endTime");
                        return dto.toJsonString();
                    }
                }catch (Exception e){
                    LogUtil.error(LOGGER, "getLandHouseList:{},e:{}", JsonEntityTransform.Object2Json(request),e);
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                    return dto.toJsonString();
                }
            }
            
            // 条件查询后台用户
            QueryResult getHouseListInfo = searchService.getLandHouseList(picSize,null, request);
            dto.putValue("list", getHouseListInfo.getValue());
            dto.putValue("total", getHouseListInfo.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "获取房东的房源列表异常 [getLandHouseList],param={},error:{}",landJson ,e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }
    
    
    /**
     * 根据 房东uid，起止时间  获取房东的分享房源列表
     * 
     * @author zl
     * @created 2017年3月28日 下午5:12:43
     *
     * @param picSize
     * @param landJson
     * @return
     */
    public String getLandShareHouseList(String picSize,String landJson){

        DataTransferObject dto = new DataTransferObject();
        try {
            LandHouseRequest request = JsonEntityTransform.json2Object(landJson, LandHouseRequest.class);
            if(Check.NuNObj(request)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto.toJsonString();
            }
            if(Check.NuNObjs(request.getLandlordUid())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto.toJsonString();
            }
            //参数校验
            List<String> daysList  = new ArrayList<>();            
            if(!Check.NuNStr(request.getStartTime()) &&  !Check.NuNStr(request.getEndTime())){
                try {
                    Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(request.getStartTime()), "yyyy-MM-dd");
                    Date endTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(request.getEndTime()), "yyyy-MM-dd");
                    if(startTime.after(endTime)){
                        dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                        dto.setMsg(" startTime is after endTime");
                        return dto.toJsonString();
                    }
                    
                    List<Date> days = DateSplitUtil.dateSplit(startTime, endTime);
                    for (Date date : days) {
                    	daysList.add(DateUtil.dateFormat(date, "yyMMdd"));
					}
                    
                }catch (Exception e){
                    LogUtil.error(LOGGER, "getLandShareHouseList:{},e:{}", JsonEntityTransform.Object2Json(request),e);
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                    return dto.toJsonString();
                }
            }
           
            LandHouseRequest newRequest = new LandHouseRequest();
            newRequest.setLimit(request.getLimit());
            newRequest.setPage(request.getPage());
            newRequest.setLandlordUid(request.getLandlordUid());
            newRequest.setVersionCode(request.getVersionCode());
            
            // 条件查询后台用户
            QueryResult getHouseListInfo = searchService.getLandHouseList(picSize,null, newRequest);
            
            List<HouseInfoEntity> resultList = new ArrayList<>();
            if(getHouseListInfo.getTotal()>0){
            	
            	resultList = (List<HouseInfoEntity>) getHouseListInfo.getValue();
            	
            	//判断是否可租
                if(!Check.NuNCollection(daysList) && !Check.NuNCollection(resultList)){
                	for (HouseInfoEntity houseInfoEntity : resultList) {
                		
                		houseInfoEntity.setIsAvailable(YesOrNoEnum.YES.getCode());
                		
                		List<String> occupyDays = houseInfoEntity.getOccupyDays();
                		if (!Check.NuNCollection(occupyDays)) {
                			boolean available = true;
                			for (String dayStr : daysList) {
                				if(occupyDays.contains(dayStr)){
                					available = false;
                					break;
                				}
                			}
                			if (!available) {
                				houseInfoEntity.setIsAvailable(YesOrNoEnum.NO.getCode());
                			}
                		} 
                	} 
                } 
            } 
            
            dto.putValue("list", resultList);
            dto.putValue("total", getHouseListInfo.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "获取房东的分享房源列表异常 [getLandShareHouseList],param={},error:{}",landJson ,e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }
    


    /**
     * 获取房源列表
     * @param requset
     * @return
     */
    private String getHouseListBase(HouseListRequset requset){
        DataTransferObject dto = new DataTransferObject();
        try {

            if(Check.NuNObj(requset)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto.toJsonString();
            }
            if(Check.NuNCollection(requset.getHouseList())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto.toJsonString();
            }
            
            //参数校验
            if(!Check.NuNObj(requset.getStartTime()) &&  !Check.NuNObj(requset.getEndTime())){
                try {
                    if(requset.getStartTime().after(requset.getEndTime())){
                        dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                        dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                        return dto.toJsonString();
                    }
                    
                    //出租日历最长月数
                    int calendarMounthLimit = getCalendarMounthLimit();                    
                    
                    Calendar calendar = Calendar.getInstance(); 
                    calendar.add(Calendar.MONTH, calendarMounthLimit);
                    if(requset.getEndTime().after(calendar.getTime())){ //防止日期过长
                    	LogUtil.info(LOGGER, "查询时间段过长，缩短处理，原始时间段：{}--{}", DateUtil.dateFormat(requset.getStartTime(), "yyyy-MM-dd"),DateUtil.dateFormat(requset.getEndTime(), "yyyy-MM-dd"));
                    	requset.setEndTime(calendar.getTime());
                    }             
                    
                }catch (Exception e){
                    LogUtil.error(LOGGER, "getHouseListBase 时间校验异常 ,param={},e:{}", JsonEntityTransform.Object2Json(requset),e);
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                    return dto.toJsonString();                
                }
            }
            
            // 条件查询后台用户
            QueryResult getHouseListInfo = searchService.getHouseListByList(requset);
            dto.putValue("list", getHouseListInfo.getValue());
            dto.putValue("total", getHouseListInfo.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "getHouseListBase exception,param={},error:{}",JsonEntityTransform.Object2Json(requset), e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }


    /**
     * 获取房源列表
     * @param houseJson
     * @return
     */
    public String getHouseList(String picSize,Integer iconType,String houseJson){
        DataTransferObject dto = new DataTransferObject();
        try {
            List<String> queryList = new ArrayList<>();
            //获取房源的列表
            List<HouseSearchRequest> houseList = JsonEntityTransform.json2ObjectList(houseJson, HouseSearchRequest.class);
            if(Check.NuNCollection(houseList)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto.toJsonString();
            }
            for(HouseSearchRequest searchRequest: houseList){
                if(!Check.NuNObjs(searchRequest.getFid(),searchRequest.getRentWay())){
                    queryList.add(searchRequest.getFid()+"_"+searchRequest.getRentWay());
                }
            }
            //校验过滤之后的数据
            if(Check.NuNCollection(queryList)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("ine the list someone fid or rentWay is null ");
                return dto.toJsonString();
            }
            HouseListRequset houseListRequset = new HouseListRequset();
            houseListRequset.setPicSize(picSize);
            houseListRequset.setIconPicType(iconType);
            houseListRequset.setHouseList(houseList);
            
            return getHouseListBase( houseListRequset);  
            
        } catch (Exception e) {
            LogUtil.error(LOGGER, "getHouseList exception,param={},error:{}",houseJson, e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }

    /**
     * 获取房源列表
     * @param houseJson
     * @return
     */
    public String getHouseListByList(String picSize,String houseJson){
        return getHouseList(picSize, IconPicTypeEnum.middle.getCode(),houseJson);
    }


    /**
     * 获取房源列表
     * @author afi
     * @param houseListRequsetJson
     * @return
     */
    public String getHouseListByListInfo(String houseListRequsetJson){
        HouseListRequset requset = JsonEntityTransform.json2Object(houseListRequsetJson,HouseListRequset.class);
        return getHouseListBase(requset);
    }
    
    /**
     * 
     * 根据品牌编码列表查询房源
     *
     * @author zl
     * @created 2017年4月1日 下午3:45:06
     *
     * @param picSize
     * @param brandSnListJson
     * @return
     */
    public String getHouseListByBrandSnList(String picSize,String brandSnListJson){

        DataTransferObject dto = new DataTransferObject();
        try {
        	
        	HouseListByBrandSnListRequest brandRequest = JsonEntityTransform.json2Object(brandSnListJson, HouseListByBrandSnListRequest.class);
            if(Check.NuNObj(brandRequest)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto.toJsonString();
            }
            if(Check.NuNCollection(brandRequest.getBrandSnList())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto.toJsonString();
            } 
            
            // 条件查询后台用户
            QueryResult getHouseListInfo = searchService.searchByBrandSnList(brandRequest);
            
            dto.putValue("list", getHouseListInfo.getValue());
            dto.putValue("total", getHouseListInfo.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "根据品牌编码列表查询房源异常 [getHouseListByBrandSnList],param={},error:{}",brandSnListJson ,e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }
    

	/**
	 * 获取房源信息当没有数据时添加到建议
	 * 
	 * @author afi
	 * @param picSize
	 * @param jsonDto
	 * @return
	 */
	public String getHouseListInfoAndSuggest(String picSize, String jsonDto, String uid) {
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseInfoRequest houseInfoRequest = JsonEntityTransform.json2Object(jsonDto, HouseInfoRequest.class);

			dto = vilidateHouseListBaseParam(houseInfoRequest);

			if (dto.getCode() == DataTransferObject.ERROR) {
				return dto.toJsonString();
			}

			// 条件查询后台用户
			QueryResult getHouseListInfo = getHouseListBase(picSize, houseInfoRequest, uid);

			if (getHouseListInfo.getTotal() == 0) {
				HouseInfoRequest suggestRequest = new HouseInfoRequest();
                suggestRequest.setStartTime(houseInfoRequest.getStartTime());
                suggestRequest.setEndTime(houseInfoRequest.getEndTime());
                suggestRequest.setPersonCount(houseInfoRequest.getPersonCount());
				suggestRequest.setCityCode(houseInfoRequest.getCityCode());
				suggestRequest.setLimit(SolrConstant.suggest_page_limit);
				QueryResult suggest = getHouseListBase(picSize, suggestRequest, uid);
				dto.putValue("suggest", suggest.getValue());
                dto.putValue("suggestMsg",getMsg(SearchConstant.StaticResourceCode.NO_MORE_TIPS_MINSU ));
			} else {
				dto.putValue("suggest", new ArrayList<>());
			}
			dto.putValue("list", getHouseListInfo.getValue());
			dto.putValue("total", getHouseListInfo.getTotal());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "getHouseListInfoAndSuggest ,param={},uid={},e:{}", jsonDto, uid, e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

    /**
     * 查询静态资源
     * @author zl
     * @created 2017/8/9 17:54
     * @param
     * @return
     */
    private String getMsg(String resCode ){

        try {
            StaticResourceVo resource=  searchConditionServiceImpl.getStaticResourceByResCode(resCode);
            if (!Check.NuNObj(resource)) return resource.getResContent();

        } catch (Exception e) {
            LogUtil.error(LOGGER, "查询静态资源异常 ,param={},e:{}", resCode, e);
        }

        return  "";
    }



    /**
     * 获取房源信息
     * @author afi
     * @param picSize
     * @param jsonDto
     * @return
     */
    public String getHouseListInfo(String picSize,String jsonDto){
        DataTransferObject dto = new DataTransferObject();
        try {
            HouseInfoRequest houseInfoRequest = JsonEntityTransform.json2Object(jsonDto, HouseInfoRequest.class);
            
            dto = vilidateHouseListBaseParam( houseInfoRequest);
            
            if (dto.getCode() ==DataTransferObject.ERROR) { 
                return dto.toJsonString();
			} 
            
            // 条件查询后台用户
            QueryResult getHouseListInfo = getHouseListBase(picSize, houseInfoRequest,null);
            dto.putValue("list", getHouseListInfo.getValue());
            dto.putValue("total", getHouseListInfo.getTotal());
        } catch (Exception e) {
        	LogUtil.error(LOGGER, "getHouseListInfo ,param={},e:{}", jsonDto, e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }
    
    
    /**
     * 
     * 校验搜索参数
     *
     * @author zl 
     *
     * @param houseInfoRequest
     * @return
     */
    public DataTransferObject vilidateHouseListBaseParam(HouseInfoRequest houseInfoRequest){
    	
    	DataTransferObject dto = new DataTransferObject();
    	
    	if(Check.NuNObj(houseInfoRequest)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto;
        }
        //参数校验
        if(!Check.NuNStr(houseInfoRequest.getStartTime()) &&  !Check.NuNStr(houseInfoRequest.getEndTime())){
            try {
                Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfoRequest.getStartTime()), "yyyy-MM-dd");
                Date endTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfoRequest.getEndTime()), "yyyy-MM-dd");
                if(startTime.after(endTime)){
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                    return dto;
                }
                
                //出租日历最长月数
                int calendarMounthLimit = getCalendarMounthLimit();                    
                
                Calendar calendar = Calendar.getInstance(); 
                calendar.add(Calendar.MONTH, calendarMounthLimit);
                if(endTime.after(calendar.getTime())){ //防止日期过长
                	LogUtil.info(LOGGER, "搜索时间段过长，缩短处理，原始时间段：{}--{}", houseInfoRequest.getStartTime(),houseInfoRequest.getEndTime());
                	endTime = calendar.getTime();
                	houseInfoRequest.setEndTime(DateUtil.dateFormat(endTime, "yyyy-MM-dd"));
                }             
                
            }catch (Exception e){
                LogUtil.error(LOGGER, "getHouseListInfoAndSuggest 时间校验异常 ,param={},e:{}", JsonEntityTransform.Object2Json(houseInfoRequest),e);
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto;                
            }
        }
        
        if (!Check.NuNStr(houseInfoRequest.getHouseType())) {
        	Pattern p = Pattern.compile("^((\\d*)[,]?)*(\\d*)$",Pattern.CASE_INSENSITIVE);
    		Matcher matcher = p.matcher(houseInfoRequest.getHouseType());
    		if (!matcher.find()) {
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto;
    		}
		}
        
        if (!Check.NuNStr(houseInfoRequest.getLongTermDiscount())) {
        	Pattern p = Pattern.compile("^([A-Za-z0-9]*[,]?)*[A-Za-z0-9]$",Pattern.CASE_INSENSITIVE);
    		Matcher matcher = p.matcher(houseInfoRequest.getLongTermDiscount());
    		if (!matcher.find()) {
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto;
    		}
		}
        
        if (!Check.NuNStr(houseInfoRequest.getJiaxinDiscount())) {
        	Pattern p = Pattern.compile("^([A-Za-z0-9]*[,]?)*[A-Za-z0-9]$",Pattern.CASE_INSENSITIVE);
    		Matcher matcher = p.matcher(houseInfoRequest.getJiaxinDiscount());
    		if (!matcher.find()) {
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto;
    		}
		}
    	
        return dto;
    }
    
    /**
     * 按照搜索条件查询房源列表 
     *
     * @author zl 
     *
     * @param picSize
     * @param houseInfoRequest
     * @param uid 
     * @return
     */
    public QueryResult getHouseListBase(String picSize,HouseInfoRequest houseInfoRequest,String uid) throws Exception {
    	
    	DataTransferObject dto = this.vilidateHouseListBaseParam(houseInfoRequest);
    	if(Check.NuNObj(dto) || dto.getCode() == DataTransferObject.ERROR){
    		return new QueryResult();
    	}    	
        
        if(Check.NuNStr(picSize)){
            picSize = defaultPicSize;
        }
    	
        return searchService.getHouseListInfo(picSize, houseInfoRequest,uid);
         
    }
    
    
    
	/**
	 * 
	 * 出租日历最长月数
	 *
	 * @author zl
	 *
	 * @return
	 */
	private Integer getCalendarMounthLimit() {

		Integer calendarMounthLimit = null;

		try {

			String key = RedisKeyConst.getSearchKey(ProductRulesEnum.ProductRulesEnum0018.getValue());
			String listJson = null;
			try {
				listJson = redisOperations.get(key);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}", e);
			}

			if (!Check.NuNStr(listJson)) {
				return Integer.valueOf(listJson);
			}

			List<DicItemEntity> calendarLimitList = searchConditionServiceImpl.getDicItemListByCodeAndTemplate(
					ProductRulesEnum.ProductRulesEnum0018.getValue(), BaseDataConstant.HOUSE_RENTWAY_BY_HOUSE);
			DicItemEntity calendarLimitDic = null;
			if (!Check.NuNCollection(calendarLimitList)) {
				calendarLimitDic = calendarLimitList.get(0);
			}

			if (!Check.NuNObj(calendarLimitDic) && !Check.NuNStr(calendarLimitDic.getItemValue())) {
				calendarMounthLimit = Integer.valueOf(calendarLimitDic.getItemValue());
			}

			try {
				redisOperations.setex(key, RedisKeyConst.CONF_CACHE_TIME,
						String.valueOf(calendarMounthLimit));
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}", e);
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取出租日历最长月数异常，e={}", e);
		}

		return calendarMounthLimit;

	}
    



    /**
     * 同步楼盘信息
     * @param cityCodes
     * @return
     */
    public String syncHousesInfoByCode(final String cityCodes){
        final DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(cityCodes)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }
        LogUtil.info(LOGGER, "开始同步楼盘信息，cityCodeStr：{}",cityCodes);
        try {
            Thread thread = new Thread(){
                @Override
                public void run() {
                    syncHousesInfoService.syncHousesInfoByCode(cityCodes);
                }
            };
            SendThreadPool.execute(thread);
            if(dto.getCode() == DataTransferObject.SUCCESS){
                dto.setMsg("ok");
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "同步楼盘信息异常，cityCodes={},e={}",cityCodes, e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();

    }
    /**
     * 刷新某个区域的房源信息
     * @param areaCode
     * @return
     */
    public String creatAllIndexByArea(final String areaCode){
        final DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER, "开始刷新{}索引",areaCode);
        if(Check.NuNStr(areaCode)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }
        try {
            Thread thread = new Thread(){
                @Override
                public void run() {
                    searchIndexService.creatAllIndexByArea(areaCode, dto);
                }
            };
            SendThreadPool.execute(thread);
            if(dto.getCode() == DataTransferObject.SUCCESS){
                dto.setMsg("ok");
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "刷新区域{}的索引异常，e={}",areaCode, e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }
    /**
     * 刷新房源的索引信息
     * @param houseFid
     * @return
     */
    public String freshIndexByHouseFid(String houseFid){
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(houseFid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }
        LogUtil.info(LOGGER, "开始刷新单个房源信息houseFid：{}",houseFid);
        try {
            //直接刷新房源信息
            freshIndexService.freshIndexByHouseFid(houseFid, dto);
            if(dto.getCode() == DataTransferObject.SUCCESS){
                dto.setMsg("ok");
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "刷新单个房源信息异常，houseFid={},e={}",houseFid, e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }



    /**
     * 全量刷新数据库的楼盘信息
     * @author afi
     * @return
     */
    public String creatAllCommunityInfoDb(){
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER, "开始刷新楼盘信息...........");
        try {
            Thread thread = new Thread(){
                @Override
                public void run() {
                    spellIndexService.initCommunityIndex();
                }
            };
            SendThreadPool.execute(thread);
            if(dto.getCode() == DataTransferObject.SUCCESS){
                dto.setMsg("ok");
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "刷新楼盘信息异常，e={}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(e.getLocalizedMessage());
        }
        return dto.toJsonString();
    }

    /**
     * 全量刷新用户的联想词信息
     * @author afi
     * @return
     */
    public  String creatAllSuggest(){
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER, "开始刷新suggest...........");
        try {
            Thread thread = new Thread(){
                @Override
                public void run() {
                    suggestIndexService.initSuggestIndex();
                }
            };
            SendThreadPool.execute(thread);
            if(dto.getCode() == DataTransferObject.SUCCESS){
                dto.setMsg("ok");
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "全量刷新用户的联想词信息异常，e={}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(e.getLocalizedMessage());
        }
        return dto.toJsonString();
    }


    /**
     * 全量刷新当前索引信息
     * @author afi
     * @return
     */
    public  String creatAllIndex(){
        DataTransferObject dto = new DataTransferObject();
        try {
            LogUtil.info(LOGGER, "开始全量刷新索引...........");
            Thread thread = new Thread(){
                @Override
                public void run() {
                    searchIndexService.creatAllIndex();
                }
            };
            SendThreadPool.execute(thread);
            if(dto.getCode() == DataTransferObject.SUCCESS){
                dto.setMsg("ok");
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "全量刷新索引线程启动异常,e={}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(e.getLocalizedMessage());
        }
        return dto.toJsonString();
    }



    /**
     * 获取搜索位置条件
     * @author lishaochuan
     * @create 2016年8月23日下午2:21:21
     * @param cityCode
     * @return
     * 
     */
	@Override
	public String getLocationCondition(String cityCode) {
		DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(cityCode)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("城市为空");
            return dto.toJsonString();
        }
        try {
            LocationConditionResult  location =  this.getLocation(cityCode);
			dto = new DataTransferObject(DataTransferObject.SUCCESS, "", location.toMap());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取搜索位置条件异常，param={},e={}",cityCode,e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
    
    /**
     * 获取搜索位置条件 包含排序规则
     * @author afi
     * @create 2016年9月19日下午2:21:21
     * @param cityCode
     * @return
     */
    @Override
    public String getLocationConditionSort(String cityCode) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(cityCode)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("城市为空");
            return dto.toJsonString();
        }
        try {
            LocationConditionResult location =  this.getLocation(cityCode);
            dto = new DataTransferObject(DataTransferObject.SUCCESS, "", location.transToLocationPar().toMap());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "获取搜索位置条件异常，param={},e={}",cityCode, e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }
    
    /**
     * 
     * 获取搜索位置条件（统一嵌套的数据结构）
     *
     * @author zhangyl
     * @created 2017年8月10日 下午6:17:58
     *
     * @param cityCode
     * @return
     */
    @Override
	public String getLocationNestingStructure(String cityCode) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(cityCode)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("城市为空");
			return dto.toJsonString();
		}
		try {
            // 位置条件统一数据结构
            // 热搜
            LocationNestingStructureVo hotSearchRegionStructureVo = null;
            // 商圈
            LocationNestingStructureVo commercialStructureVo = null;
            // 景点
            LocationNestingStructureVo sceneryStructureVo = null;
            // 地铁
            LocationNestingStructureVo subwayStructureVo = null;
            // 行政区
            LocationNestingStructureVo areaStructureVo = null;

            // 位置条件元数据
            List<HotRegionVo> hotSearchRegions = searchConditionServiceImpl.getHotSearchRegionList(cityCode);
            List<HotRegionSimpleVo> areas = searchConditionServiceImpl.getAreaByCity(cityCode);
            List<HotRegionVo> hotRegions = searchConditionServiceImpl.getHotRegionListByCityEnumStatus(cityCode);
            List<SubwayStationVo> subwayStations = searchConditionServiceImpl.getSubwayStation(cityCode);

            // 转换统一数据结构
            // 热搜
            List<LocationNestingStructureVo> hotSearchRegionList = new ArrayList<>();
            for (HotRegionVo HotRegionVo : hotSearchRegions) {
                LocationNestingStructureVo lnso = new LocationNestingStructureVo();
                lnso.setName(HotRegionVo.getRegionName());
                lnso.setValue(HotRegionVo.getRegionName());
                hotSearchRegionList.add(lnso);
            }
            if(!Check.NuNCollection(hotSearchRegionList)){
                hotSearchRegionStructureVo = new LocationNestingStructureVo();
                hotSearchRegionStructureVo.setName("热搜");
                hotSearchRegionStructureVo.setKey("hotSearchRegion");
                hotSearchRegionStructureVo.setData(hotSearchRegionList);
            }

            // 商圈
            List<LocationNestingStructureVo> commercialList = new ArrayList<>();
            // 景点
            List<LocationNestingStructureVo> sceneryList = new ArrayList<>();
            for (HotRegionVo hotRegionVo : hotRegions) {
                LocationNestingStructureVo lnso = new LocationNestingStructureVo();
                lnso.setName(hotRegionVo.getRegionName());
                lnso.setValue(hotRegionVo.getRegionName());
                if(hotRegionVo.getRegionType() == RegionTypeEnum.BUSINESS.getCode()){
                    commercialList.add(lnso);
                } else if(hotRegionVo.getRegionType() == RegionTypeEnum.SCENIC.getCode()){
                    sceneryList.add(lnso);
                }
            }
            if(!Check.NuNCollection(commercialList)){
                commercialStructureVo = new LocationNestingStructureVo();
                commercialStructureVo.setName("商圈");
                commercialStructureVo.setKey("hotReginBusiness");
                commercialStructureVo.setData(commercialList);
            }
            if(!Check.NuNCollection(sceneryList)){
                sceneryStructureVo = new LocationNestingStructureVo();
                sceneryStructureVo.setName("景点");
                sceneryStructureVo.setKey("hotReginScenic");
                sceneryStructureVo.setData(sceneryList);
            }

            // 地铁
            List<LocationNestingStructureVo> lineList = new ArrayList<>();
            Map<String, LocationNestingStructureVo> linesMap = new HashMap<>();
            for (SubwayStationVo subwayStationVo : subwayStations) {
                String lineFid = subwayStationVo.getLineFid();
                // 不存在线路
                if(!linesMap.containsKey(lineFid)){
                    List<LocationNestingStructureVo> subwayList = new ArrayList<>();
                    // 增加线路
                    LocationNestingStructureVo lineStructureVo = new LocationNestingStructureVo();
                    lineStructureVo.setName(subwayStationVo.getLineName());
                    lineStructureVo.setValue(lineFid);
                    lineStructureVo.setKey("subway");
                    lineStructureVo.setData(subwayList);
                    lineList.add(lineStructureVo);
                    linesMap.put(lineFid, lineStructureVo);
                    // 增加"不限"
                    LocationNestingStructureVo unlimitedSubway = new LocationNestingStructureVo();
                    unlimitedSubway.setName("不限");
                    unlimitedSubway.setUnlimited(1);
                    subwayList.add(unlimitedSubway);
                }
                // 增加站点
                LocationNestingStructureVo lineStructureVo = linesMap.get(lineFid);
                List<LocationNestingStructureVo> subwayList = lineStructureVo.getData();
                LocationNestingStructureVo subway = new LocationNestingStructureVo();
                subway.setName(subwayStationVo.getStationName());
                subway.setValue(subwayStationVo.getStationName());
                subwayList.add(subway);
            }
            if(!Check.NuNCollection(lineList)){
                subwayStructureVo = new LocationNestingStructureVo();
                subwayStructureVo.setName("地铁");
                subwayStructureVo.setKey("lineFid");
                subwayStructureVo.setData(lineList);
            }

            // 行政区
            List<LocationNestingStructureVo> areaList = new ArrayList<>();
            for (HotRegionSimpleVo area : areas) {
                LocationNestingStructureVo lnso = new LocationNestingStructureVo();
                lnso.setName(area.getAreaName());
                lnso.setValue(area.getCityCode());
                areaList.add(lnso);
            }
            if(!Check.NuNCollection(areaList)){
                areaStructureVo = new LocationNestingStructureVo();
                areaStructureVo.setName("行政区");
                areaStructureVo.setKey("areaCode");
                areaStructureVo.setData(areaList);
            }

            // 按非空并顺序添加地理位置数据
            List<LocationNestingStructureVo> location = new ArrayList<>();
            if(!Check.NuNObj(hotSearchRegionStructureVo)){
                location.add(hotSearchRegionStructureVo);
            }
            if(!Check.NuNObj(commercialStructureVo)){
                location.add(commercialStructureVo);
            }
            if(!Check.NuNObj(sceneryStructureVo)){
                location.add(sceneryStructureVo);
            }
            if(!Check.NuNObj(subwayStructureVo)){
                location.add(subwayStructureVo);
            }
            if(!Check.NuNObj(areaStructureVo)){
                location.add(areaStructureVo);
            }

            dto.putValue("list", location);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取搜索位置条件异常，param={},e={}", cityCode, e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

    /**
     * 获取当前的有序的地理信息
     * @param cityCode
     * @return
     */
    private LocationConditionResult getLocation(String cityCode){

        List<HotRegionSimpleVo> areas = searchConditionServiceImpl.getAreaByCity(cityCode);
        List<HotRegionVo> hotRegions = searchConditionServiceImpl.getHotRegionListByCityEnumStatus(cityCode);
        List<SubwayStationVo> subwayStations = searchConditionServiceImpl.getSubwayStation(cityCode);

        LocationConditionResult location = new LocationConditionResult();
        //行政区
        List<LocationConditionDistrictsResult> districts = location.getDistricts();
        for (HotRegionSimpleVo hotRegionSimpleVo : areas) {
            LocationConditionDistrictsResult district = new LocationConditionDistrictsResult();
            district.setCode(hotRegionSimpleVo.getCityCode());
            district.setName(hotRegionSimpleVo.getAreaName());
            districts.add(district);
        }
        //景点、商圈
        List<String> commercialRegions = location.getCommercialRegions();
        List<String> sceneryRegions = location.getSceneryRegions();
        for (HotRegionVo hotRegionVo : hotRegions) {
            if(hotRegionVo.getRegionType() == RegionTypeEnum.BUSINESS.getCode()){
                commercialRegions.add(hotRegionVo.getRegionName());
            }
            if(hotRegionVo.getRegionType() == RegionTypeEnum.SCENIC.getCode()){
                sceneryRegions.add(hotRegionVo.getRegionName());
            }
        }
        //地铁
        List<LocationConditionSubwaysResult> subways = location.getSubways();
        Map<String, List<SubwayVo>> lines = new TreeMap<>();
        Map<String, String> names = new TreeMap<>();
        for (SubwayStationVo subwayStationVo : subwayStations) {
            String key = subwayStationVo.getLineFid();
            if(lines.containsKey(key)){
                List<SubwayVo> value = lines.get(key);
                SubwayVo subwayVo = new SubwayVo();
                subwayVo.setName(subwayStationVo.getStationName());
                value.add(subwayVo);
            }else{
                List<SubwayVo> value = new ArrayList<>();

                SubwayVo all = new SubwayVo();
                all.setName("不限");
                all.setCode(0);
                value.add(all);
                SubwayVo subwayVo = new SubwayVo();
                subwayVo.setName(subwayStationVo.getStationName());
                value.add(subwayVo);
                lines.put(key, value);
                names.put(key,subwayStationVo.getLineName());
            }
        }
        for (Map.Entry<String, List<SubwayVo>> entry : lines.entrySet()) {
            LocationConditionSubwaysResult subway = new LocationConditionSubwaysResult();
            String eleKey = entry.getKey();
            subway.setStations(entry.getValue());
            subway.setLineFid(eleKey);
            subway.setName(names.get(eleKey));
            subways.add(subway);
        }
        return location;
    }


	@Override
	public String getDicItemListByCodeAndTemplate(String code,
			String templateFid) {
		DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(code)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("枚举key不能为空");
            return dto.toJsonString();
        }
        try {
        	List<DicItemEntity> dicList=searchConditionServiceImpl.getDicItemListByCodeAndTemplate(code, templateFid);
        	dto.putValue("dicList", dicList);
		} catch (Exception e) {
			 LogUtil.error(LOGGER, "获取枚举值错误:{}", e);
			 dto.setErrCode(DataTransferObject.ERROR);
	         dto.setMsg("服务错误");
	         return dto.toJsonString();
		}
		
        return dto.toJsonString();
	}


	@Override
	public String selectEffectiveDefaultEnumList(String dicCode,
			String templateFid) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(dicCode)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("枚举key不能为空");
            return dto.toJsonString();
        }
        try {
        	List<DicItemEntity> dicList=searchConditionServiceImpl.selectEffectiveDefaultEnumList(dicCode, templateFid);
        	dto.putValue("dicList", dicList);
		} catch (Exception e) {
			 LogUtil.error(LOGGER, "获取有效枚举值错误:{}", e);
			 dto.setErrCode(DataTransferObject.ERROR);
	         dto.setMsg("服务错误");
	         return dto.toJsonString();
		}
		
        return dto.toJsonString();
        
	}
	

	@Override
	public String getHotRegionListByEffectiveEnum(String dicCode,
			String templateFid, String cityCode, String regionType) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(dicCode)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("枚举key不能为空");
            return dto.toJsonString();
        }
//		if (Check.NuNStr(cityCode)){
//            dto.setErrCode(DataTransferObject.ERROR);
//            dto.setMsg("城市不能为空");
//            return dto.toJsonString();
//        }
        try {
        	List<HotRegionVo> hotRegionList=searchConditionServiceImpl.getHotRegionListByEffectiveEnum(dicCode, templateFid, cityCode, regionType);
        	dto.putValue("hotRegionList", hotRegionList);
		} catch (Exception e) {
			 LogUtil.error(LOGGER, "获取有效枚举值获取景点商圈错误:{}", e);
			 dto.setErrCode(DataTransferObject.ERROR);
	         dto.setMsg("服务错误");
	         return dto.toJsonString();
		}
		
        return dto.toJsonString();
	}
	
	@Override
	public String getHotRegionListByEffectiveEnumStatus(String dicCode,
			String templateFid, String cityCode, String regionType) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(dicCode)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("枚举key不能为空");
            return dto.toJsonString();
        }
//		if (Check.NuNStr(cityCode)){
//            dto.setErrCode(DataTransferObject.ERROR);
//            dto.setMsg("城市不能为空");
//            return dto.toJsonString();
//        }
        try {
        	List<HotRegionVo> hotRegionList=searchConditionServiceImpl.getHotRegionListByEffectiveEnumStatus(dicCode, templateFid, cityCode, regionType);
        	dto.putValue("hotRegionList", hotRegionList);
		} catch (Exception e) {
			 LogUtil.error(LOGGER, "获取有效枚举值获取景点商圈错误:{}", e);
			 dto.setErrCode(DataTransferObject.ERROR);
	         dto.setMsg("服务错误");
	         return dto.toJsonString();
		}
		
        return dto.toJsonString();
	}


	@Override
	public String getHouseTypeByCityCode(String cityCode) {
		
		DataTransferObject dto = new DataTransferObject(); 
		if (Check.NuNStr(cityCode)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("城市不能为空");
            return dto.toJsonString();
        }
        try {
        	List<Integer> list = searchConditionServiceImpl.getHouseTypeByCityCode(cityCode);
        	dto.putValue("houseTypeList", list);
		} catch (Exception e) {
			 LogUtil.error(LOGGER, "查询城市下已经上线的房源类型异常:{}", e);
			 dto.setErrCode(DataTransferObject.ERROR);
	         dto.setMsg("服务错误");
	         return dto.toJsonString();
		}
		
        return dto.toJsonString();		
		
	}
	
	
	

    @Override
    public String getTOP50List(String top50HouseListRequestJson,String picSize,String uid){
        
    	DataTransferObject dto = new DataTransferObject(); 
    	
    	HouseInfoRequest houseInfoRequest = new HouseInfoRequest();
    	try {
    		Top50HouseListRequest requset = JsonEntityTransform.json2Object(top50HouseListRequestJson,Top50HouseListRequest.class);
			if(Check.NuNObj(requset)){
	            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
	            return dto.toJsonString();
		    }

    		dto = vilidateHouseListBaseParam(houseInfoRequest);
    		if (dto.getCode() == DataTransferObject.ERROR) {
    			return dto.toJsonString();
    		}
    		 
    		houseInfoRequest.setPage(requset.getPage());
    		houseInfoRequest.setLimit(requset.getLimit());
    		houseInfoRequest.setIsTop50Online(YesOrNoEnum.YES.getCode());
    		houseInfoRequest.setIsTop50ListShow(YesOrNoEnum.YES.getCode());
    		houseInfoRequest.setSortType(SortTypeEnum.DEFAULT.getCode());

    		// 条件查询后台用户
    		QueryResult getHouseListInfo = getHouseListBase(picSize, houseInfoRequest, uid);
    		dto.putValue("list", getHouseListInfo.getValue());
    		dto.putValue("total", getHouseListInfo.getTotal());
    		
		} catch (Exception e) {
			 LogUtil.error(LOGGER, "查找top50房源列表异常:param={},e={}",top50HouseListRequestJson, e);
			 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			 dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
	         return dto.toJsonString();
		}
    	
		return dto.toJsonString();
    }



	@Override
	public String getStaticResourceByResCode(String resCode) {
    	DataTransferObject dto = new DataTransferObject(); 
    	 
    	try {
			if(Check.NuNStr(resCode)){
	            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
	            return dto.toJsonString();
		    }
			
			StaticResourceVo resource = searchConditionServiceImpl.getStaticResourceByResCode(resCode);

			dto.putValue("result", resource);
    		
		} catch (Exception e) {
			 LogUtil.error(LOGGER, "查询静态资源异常:resCode={},e={}",resCode, e);
			 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			 dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
	         return dto.toJsonString();
		}
    	
		return dto.toJsonString();
	}


	@Override
	public String getSimilarHouse(String picSize, String landJson) {
		LogUtil.info(LOGGER, "查询类似房源参数：{}", landJson);
		DataTransferObject dto = new DataTransferObject(); 
		try {
			if(Check.NuNStr(landJson)){
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("参数不能为空！");
		         return dto.toJsonString();
			}
			LandHouseRequest request = JsonEntityTransform.json2Object(landJson, LandHouseRequest.class);
			if(Check.NuNStr(request.getFid())){
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("房源和房间fid不能为空！");
		         return dto.toJsonString();
			}
	        // 条件查询后台用户
			request.setLimit(5);
	        QueryResult getHouseListInfo = searchService.getSimilarHouse(picSize, request);
	        dto.putValue("list", getHouseListInfo.getValue());
	        dto.putValue("total", getHouseListInfo.getTotal());
		} catch (Exception e) {
			 LogUtil.error(LOGGER, "查询类似房源参数：{}，错误：{}", landJson, e);
			 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			 dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
	         return dto.toJsonString();
		}
		return dto.toJsonString();
	}
}
