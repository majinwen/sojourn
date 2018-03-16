package com.ziroom.minsu.api.search.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.search.common.header.Header;
import com.ziroom.minsu.api.search.common.jsonp.JsonpVo;
import com.ziroom.minsu.api.search.controller.abs.AbstractController;
import com.ziroom.minsu.entity.base.StaticResourcePicEntity;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SearchConstant;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.dto.HouseListRequset;
import com.ziroom.minsu.services.search.vo.HotRegionVo;
import com.ziroom.minsu.services.search.vo.StaticResourceVo;
import com.ziroom.minsu.valenum.city.CityRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.search.SortTypeEnum;

/**
 * <p>搜索的api层</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/14. 
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/searchInit")
public class InitController extends AbstractController{

    private static final Logger LOGGER = LoggerFactory.getLogger(InitController.class);


    @Resource(name="searchApi.messageSource")
    private MessageSource messageSource;


    @Value("#{'${default_pic_size}'.trim()}")
    private String defaultPicSize;
    
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
    @Autowired
    private RedisOperations redisOperations;
    
    @Value("#{'${detail_big_1200_1200_pic}'.trim()}")
    private String detail_big_1200_1200_pic;


    /**
     * 搜索的api
     */
    @Resource(name = "search.searchServiceApi")
    private SearchService searchService;


    /**
     * 房源的搜索
     * @param request
     * @param cityCode
     * @param
     * @return
     */
    @RequestMapping(value ="/sort")
    public @ResponseBody
    DataTransferObject sort(HttpServletRequest request,String cityCode){
        DataTransferObject dto = new DataTransferObject();
        Header header = getHeader(request);
        //获取当前的用户uid
        String uid = getUserId(request);
        dto.putValue("sortList",SortTypeEnum.getSortList());
        return dto;
    }

    /**
     * 房源的搜索
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value ="/jsonp/list")
    public @ResponseBody
    JsonpVo listp(HttpServletRequest request, HttpServletResponse response, String par) {
        JsonpVo jsonpVo = new JsonpVo();
        String jsonpcallback=request.getParameter("callback");
        jsonpVo.setCallBack(jsonpcallback);
        DataTransferObject dto = null;
        Header header = getHeader(request);
        if (Check.NuNStr(par)){
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            jsonpVo.setDto(dto);
            return jsonpVo;
        }
        dto =  this.getHouseList(par);
        jsonpVo.setDto(dto);
        return jsonpVo;
    }

    private DataTransferObject getHouseList(String par){
        DataTransferObject dto = null;
        HouseListRequset houseListRequset = null;
        try {
            houseListRequset = JsonEntityTransform.json2Entity(par,HouseListRequset.class);
        }catch (Exception e){
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto;
        }
        if(Check.NuNObj(houseListRequset)){
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto;
        }
        if (Check.NuNStr(houseListRequset.getPicSize())){
            houseListRequset.setPicSize(defaultPicSize);
        }
        String jsonRst = null;
        try {
            //获取查询结果
            jsonRst =searchService.getHouseList(houseListRequset.getPicSize(),houseListRequset.getIconPicType(),JsonEntityTransform.Object2Json(houseListRequset.getHouseList()));
            dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
        } catch (Exception e) {
            dto = new DataTransferObject();
            LogUtil.error(LOGGER, "list par :{} e:{}", JsonEntityTransform.Object2Json(houseListRequset), e);
            return dto;
        }
        return dto;
    }
    /**
     * 房源的搜索
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value ="/list")
    public @ResponseBody
    DataTransferObject list(HttpServletRequest request,HttpServletResponse response,String par) {
        DataTransferObject dto = null;

        Header header = getHeader(request);
        if (Check.NuNStr(par)){
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto;
        }
        return this.getHouseList(par);
    }


    /**
     * 获取搜索位置条件
     * @author lishaochuan
     * @create 2016年8月23日下午4:35:47
     * @param request
     * @param response
     * @param cityCode
     * @return
     */
    @RequestMapping(value ="/location")
    @ResponseBody
    public DataTransferObject location(HttpServletRequest request,HttpServletResponse response, String cityCode) {
        Header header = getHeader(request);
        if (Check.NuNObj(header)) {
            LogUtil.info(LOGGER, "location head为空:{}", header);
        }
        DataTransferObject dto = null;
        try {
            String jsonRst = searchService.getLocationCondition(cityCode);
            dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
        } catch (Exception e) {
            dto = new DataTransferObject();
            LogUtil.error(LOGGER, "location cityCode :{} e:{}", cityCode, e);
            return dto;
        }
        return dto;
    }


    /**
     * 获取搜索位置条件
     * @author afi
     * @create 2016年9月19日下午4:35:47
     * @param request
     * @param response
     * @param cityCode
     * @return
     */
    @RequestMapping(value ="/locationSort")
    @ResponseBody
    public DataTransferObject locationSort(HttpServletRequest request,HttpServletResponse response, String cityCode) {
       
    	response.addHeader( "Access-Control-Allow-Origin", "*" ); // open your api to any client
		response.addHeader( "Access-Control-Allow-Methods", "POST" ); // a allow post
		response.addHeader( "Access-Control-Max-Age", "3000" ); // time from request to response before timeout
    	
		Header header = getHeader(request);
		if (Check.NuNObj(header)) {
			LogUtil.info(LOGGER, "locationSort head为空:{}", header);
		}
		DataTransferObject dto = null;
		try {
			String jsonRst = searchService.getLocationConditionSort(cityCode);

			dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
		} catch (Exception e) {
			dto = new DataTransferObject();
			LogUtil.error(LOGGER, "locationSort cityCode :{} e:{}", cityCode, e);
			return dto;
		}
		return dto;
    }
    
    /**
     * 
     * 获取搜索位置条件（统一嵌套的数据结构）
     *
     * @author zhangyl
     * @created 2017年8月11日 下午2:32:17
     *
     * @param request
     * @param response
     * @param cityCode
     * @return
     */
    @RequestMapping(value ="/locationNestingStructure")
    @ResponseBody
    public DataTransferObject getLocationNestingStructure(HttpServletRequest request,HttpServletResponse response, String cityCode) {
       
    	response.addHeader( "Access-Control-Allow-Origin", "*" ); // open your api to any client
		response.addHeader( "Access-Control-Allow-Methods", "POST" ); // a allow post
		response.addHeader( "Access-Control-Max-Age", "3000" ); // time from request to response before timeout
    	
		Header header = getHeader(request);
		if (Check.NuNObj(header)) {
			LogUtil.info(LOGGER, "getLocationNestingStructure head为空:{}", header);
		}
		DataTransferObject dto = null;
		try {
			String jsonRst = searchService.getLocationNestingStructure(cityCode);

			dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
		} catch (Exception e) {
			dto = new DataTransferObject();
			LogUtil.error(LOGGER, "getLocationNestingStructure cityCode :{} e:{}", cityCode, e);
			return dto;
		}
		return dto;
    }
    
    
    /**
     * 搜索页面基础数据
     * 
     * @author zl
     * @param request
     * @return
     */
    @RequestMapping("/initSearchBaseData")
    @ResponseBody
    public DataTransferObject initSearchBaseData(HttpServletRequest request){
    	DataTransferObject dto = new DataTransferObject();
    	try{
    		 Header header = getHeader(request);    		
    		
    		DataTransferObject datadto =null;
    		String cityCode = (String) request.getParameter("cityCode");
    		
    		List<Map<String, String>> houseTypeListResult = new ArrayList<Map<String, String>>();
    		String houseTypeJson = searchService.selectEffectiveDefaultEnumList( ProductRulesEnum.ProductRulesEnum001.getValue(),null);
    		datadto = JsonEntityTransform.json2DataTransferObject(houseTypeJson);
    		//判断调用状态
    		if (datadto.getCode() == DataTransferObject.SUCCESS) {
    			List<DicItemEntity> houseTypeList = SOAResParseUtil.getListValueFromDataByKey(houseTypeJson, "dicList", DicItemEntity.class);
    			if (!Check.NuNCollection(houseTypeList)) {
    				for (DicItemEntity dicItemEntity:houseTypeList) {
    					Map<String, String> houseTypeMap = new HashMap<String, String>();
    					houseTypeMap.put("key", dicItemEntity.getItemValue());
    					houseTypeMap.put("text", dicItemEntity.getShowName());
    					houseTypeListResult.add(houseTypeMap);
    				}
    			}
    		}
    		if (!Check.NuNStr(cityCode)) {//各个城市下有线上房源的返回
    			houseTypeJson = searchService.getHouseTypeByCityCode(cityCode);
        		datadto = JsonEntityTransform.json2DataTransferObject(houseTypeJson);
        		if (datadto.getCode() == DataTransferObject.SUCCESS) {
        			List<Integer> hTypeList = SOAResParseUtil.getListValueFromDataByKey(houseTypeJson, "houseTypeList", Integer.class);
        			if (!Check.NuNCollection(hTypeList)) {
        				Iterator<Map<String, String>> iterator = houseTypeListResult.iterator();
        				while (iterator.hasNext()) {
        					Map<String, String> hTypeMap =iterator.next();
        					if (!hTypeList.contains(Integer.valueOf(hTypeMap.get("key")))) {
        						iterator.remove();
							}
							
						}
        			}
        		}
				
				
			}
    		
    		dto.putValue("houseTypeList", houseTypeListResult);
            
    		if (!Check.NuNStr(cityCode)) {//只有有城市的时候才返回景点商圈				
    			String effectiveEnumJson= searchService.selectEffectiveDefaultEnumList(CityRulesEnum.CityRulesEnum002.getValue(),null);
    			datadto = JsonEntityTransform.json2DataTransferObject(effectiveEnumJson);
    			if (datadto.getCode() == DataTransferObject.SUCCESS) {
    				List<DicItemEntity> dicList=SOAResParseUtil.getListValueFromDataByKey(effectiveEnumJson, "dicList", DicItemEntity.class);
    				if (!Check.NuNCollection(dicList)) {
    					
    					List<HotRegionVo> hotRegionListResult= new ArrayList<HotRegionVo>();		
    					
    					for (DicItemEntity dicItemEntity:dicList) {
    						String hotRegionJson = searchService.getHotRegionListByEffectiveEnumStatus(CityRulesEnum.CityRulesEnum002.getValue(), null, cityCode, dicItemEntity.getItemValue());
    						datadto = JsonEntityTransform.json2DataTransferObject(houseTypeJson);
    						if (datadto.getCode() == DataTransferObject.SUCCESS) {
    							List<HotRegionVo> hotRegionList = SOAResParseUtil.getListValueFromDataByKey(hotRegionJson, "hotRegionList", HotRegionVo.class);
    							if (hotRegionList!=null && hotRegionList.size()>4) {
    								hotRegionList = hotRegionList.subList(0, 4);
    							} 
    							if (hotRegionList!=null && hotRegionList.size()>0) {
    								hotRegionListResult.addAll(hotRegionList);
    							}
    						}
    					}
    					
    					dto.putValue("hotRegionList", hotRegionListResult);
    				}
    				
    			}
			}else{
				dto.putValue("hotRegionList", new ArrayList<HotRegionVo>());
			}
    		
    		Map<String, Object> longTermMap = new HashMap<>();
    		longTermMap.put("title", "入住特惠");
    		
    		List<Map<String, Object>> longTermsList = new ArrayList<>();   
    		
    		try {			
    			if (!Check.NuNObj(header) && !Check.NuNStr(header.getVersionCode())) {
    				int versionCode =ValueUtil.getintValue(header.getVersionCode());
    				if(versionCode>=100016){
    					Map<String, Object> todayDisMap = new HashMap<>();
    					todayDisMap.put("name", "今夜特价");
    					todayDisMap.put("tips", "今日预订入住，享受超值优惠");
    					todayDisMap.put("value", ProductRulesEnum020.ProductRulesEnum020001.getValue());
    					todayDisMap.put("index", 1);
    					longTermsList.add(todayDisMap);    					
    				}
    			}
    		} catch (Exception e) {
    			LogUtil.error(LOGGER, "版本号转化错误，versionCode={},e={}", header.getVersionCode(),e);
    		}
    		
    		Map<String, Object> weekMap = new HashMap<>();
    		weekMap.put("name", "周租特惠");
    		weekMap.put("tips", String.format("入住满%d天，享受折扣", ProductRulesEnum0019.ProductRulesEnum0019001.getDayNum()));
    		weekMap.put("value", ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
    		weekMap.put("index", 2);
    		longTermsList.add(weekMap);    		
    		Map<String, Object> monthMap = new HashMap<>();
    		monthMap.put("name", "月租特惠");
    		monthMap.put("tips", String.format("入住满%d天，享受折扣且服务费低至3%%", ProductRulesEnum0019.ProductRulesEnum0019002.getDayNum()));
    		monthMap.put("value", ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
    		monthMap.put("index", 3);
    		longTermsList.add(monthMap);
    		
    		longTermMap.put("values", longTermsList);    		
    		dto.putValue("longTermLeaseDiscount", longTermMap);
            
            LogUtil.info(LOGGER, "结果：" + JsonEntityTransform.Object2Json(dto));
            
            return dto;
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
			return dto;
        }
    }
    
    
    
    /**
     * 
     * top50列表初始化数据
     *
     * @author zl
     * @created 2017年3月15日 下午3:56:55
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value ="/top50HouseListInit")
    @ResponseBody
    public DataTransferObject top50HouseListInit(HttpServletRequest request,HttpServletResponse response) {
    	
    	 DataTransferObject dto = new DataTransferObject();
    	 
    	 try {
			
    		 Header header = getHeader(request);
    		 //获取当前的用户uid
    		 String uid = getUserId(request);
    		 
    		String key = RedisKeyConst.getCollectKey("search_top50HouseListInit");
 	        String json= null;
 	        try {
// 	            json=redisOperations.get(key);
 	        } catch (Exception e) {
 	            LogUtil.error(LOGGER, "redis错误,e:{}",e);
 	        }
 	        
 	        if (!Check.NuNStr(json)) { 	        	
 	        	dto = JsonEntityTransform.json2DataTransferObject(json);
 	        	return dto; 	        	
 			}
    		 
    		//top50榜单列表头图
             String titleBackStr = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TOP50_LIST_TITLE_BACKGROUND);             
             StaticResourceVo titleBackImg =  SOAResParseUtil.getValueFromDataByKey(titleBackStr, "result", StaticResourceVo.class);
             if (Check.NuNObj(titleBackImg) || Check.NuNCollection(titleBackImg.getStaticResourcePicList()) || Check.NuNObj(titleBackImg.getStaticResourcePicList().get(0)) ) {
            	LogUtil.error(LOGGER, "top50榜单列表头图为空");
     			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
     			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
     			return dto;
			 }             
             StaticResourcePicEntity  titleBackImgEntity = titleBackImg.getStaticResourcePicList().get(0);
             dto.putValue("top50ListTitleBackground",  PicUtil.getFullPic(picBaseAddrMona, titleBackImgEntity.getPicBaseUrl(), titleBackImgEntity.getPicSuffix(), detail_big_1200_1200_pic));
    		 
             //top50榜单列表分享URL
             String shareUrlStr = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TOP50_LIST_SHARE_URL);
             StaticResourceVo shareURL =  SOAResParseUtil.getValueFromDataByKey(shareUrlStr, "result", StaticResourceVo.class);
             if (Check.NuNObj(shareURL)) {
             	LogUtil.error(LOGGER, "top50榜单列表分享URL为空");
      			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
      			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
      			return dto;
 			 }
             dto.putValue("top50ListShareUrl", shareURL.getResContent());
             
    		 //top50榜单列表分享标题
             String shareTitleStr = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TOP50_LIST_SHARE_TITLE);
             StaticResourceVo shareTitle =  SOAResParseUtil.getValueFromDataByKey(shareTitleStr, "result", StaticResourceVo.class);
             if (Check.NuNObj(shareTitle)) {
             	LogUtil.error(LOGGER, "top50榜单列表分享标题为空");
      			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
      			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
      			return dto;
 			 }
             dto.putValue("top50ListShareTitle", shareTitle.getResContent());             
    		 
             //top50榜单列表分享内容
             String shareContentStr = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TOP50_LIST_SHARE_CONTENT);
             StaticResourceVo shareContent =  SOAResParseUtil.getValueFromDataByKey(shareContentStr, "result", StaticResourceVo.class);
             if (Check.NuNObj(shareContent)) {
             	LogUtil.error(LOGGER, "top50榜单列表分享内容为空");
      			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
      			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
      			return dto;
 			 }
             dto.putValue("top50ListShareContent", shareContent.getResContent());
    		 
             //top50榜单列表分享图
             String shareImgStr = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TOP50_LIST_SHARE_IMG_SRC);             
             StaticResourceVo shareImg =  SOAResParseUtil.getValueFromDataByKey(shareImgStr, "result", StaticResourceVo.class);
             if (Check.NuNObj(shareImg) || Check.NuNCollection(shareImg.getStaticResourcePicList()) || Check.NuNObj(shareImg.getStaticResourcePicList().get(0)) ) {
            	LogUtil.error(LOGGER, "top50榜单列表分享图");
     			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
     			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
     			return dto;
			 }             
             StaticResourcePicEntity  shareImgEntity = shareImg.getStaticResourcePicList().get(0);
             dto.putValue("top50ListShareImgSrc",  PicUtil.getFullPic(picBaseAddrMona, shareImgEntity.getPicBaseUrl(), shareImgEntity.getPicSuffix(), defaultPicSize));
    		 
             try {
//            	 redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, JsonEntityTransform.Object2Json(dto));
             } catch (Exception e) {
            	 LogUtil.error(LOGGER, "redis错误,e:{}",e);
             }              
    		 
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询top50列表初始化数据异常,e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto;
		}
 	
        return dto;
    	
    }
    

}
