package com.ziroom.minsu.api.search.controller;

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

import com.ziroom.minsu.entity.base.StaticResourcePicEntity;
import com.ziroom.minsu.services.search.vo.BannerVo;
import com.ziroom.minsu.valenum.search.BannerTypeEnum;
import com.ziroom.minsu.valenum.version.VersionCodeEnum;
import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.type.TypeReference;
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
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.search.common.header.Header;
import com.ziroom.minsu.api.search.common.interceptor.ParamCollector;
import com.ziroom.minsu.api.search.common.jsonp.JsonpVo;
import com.ziroom.minsu.api.search.common.log.user.QueryLog;
import com.ziroom.minsu.api.search.common.log.vo.TraceInfoVo;
import com.ziroom.minsu.api.search.constant.Constant;
import com.ziroom.minsu.api.search.controller.abs.AbstractController;
import com.ziroom.minsu.api.search.service.ElasticSearchService;
import com.ziroom.minsu.entity.customer.CustomerLocationEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.cms.api.inner.CityFileService;
import com.ziroom.minsu.services.cms.entity.ColumnRegionPicVo;
import com.ziroom.minsu.services.cms.entity.FileRegionsVo;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SearchConstant;
import com.ziroom.minsu.services.common.thread.pool.SynLocationThreadPool;
import com.ziroom.minsu.services.common.utils.BaiDuIPUtil.BaiduIPResult;
import com.ziroom.minsu.services.common.utils.BaiDuMapUtil.BaiduGeocodingResult;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.IpUtil;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerLocationService;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.dto.HouseListByBrandSnListRequest;
import com.ziroom.minsu.services.search.dto.LandHouseRequest;
import com.ziroom.minsu.services.search.dto.Top50HouseListRequest;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.services.search.vo.StaticResourceVo;
import com.ziroom.minsu.services.search.vo.Top50HouseListVo;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.LocationTypeEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.house.QualityGradeEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.search.GuideCardEnum;
import com.ziroom.minsu.valenum.search.SearchSourceTypeEnum;
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
@RequestMapping("/search")
public class QueryController extends AbstractController{

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryController.class);


    /**
     * 用于记录搜索的记录
     */
    private static final Logger record = LoggerFactory.getLogger(QueryLog.class);

    /**
     * 搜索的api
     */
    @Resource(name = "search.searchServiceApi")
    private SearchService searchService;


    @Resource(name="searchApi.messageSource")
    private MessageSource messageSource;
    
    @Resource(name="basedata.zkSysService")
    private ZkSysService zksysService;
    
    @Resource(name="cms.cityFileService")
    private CityFileService cityFileService;

    @Resource(name="customer.customerLocationService")
    private CustomerLocationService customerLocationService;
    
    @Resource(name="basedata.confCityService")
    private ConfCityService confCityService;
    
    @Resource(name = "query.elasticSearchService")
	private ElasticSearchService elasticSearchService;

    @Autowired
    private RedisOperations redisOperations;

	@Value("#{'${default_pic_size}'.trim()}")
	private String defaultPicSize;
	
	@Value("#{'${default_icon_size}'.trim()}")
	private String defaultIconSize;
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;
	
	
	//城市档案插入位置
	private static Integer cityCardIndex =3;

    /**
     * 获取房东的当前搜索词对应的楼盘信息，只取对应的第一条
     * @param request
     * @param response
     * @param cn 联想词
     * @param cc 城市code
     * @return
     */
    @RequestMapping(value ="/fix", produces = "application/json; charset=utf-8")
    public @ResponseBody
    String fix(HttpServletRequest request,HttpServletResponse response, String cn,String cc) {

        Header header = getHeader(request);
        if(LOGGER.isDebugEnabled() && Check.NuNObj(header)){
            LogUtil.debug(LOGGER,"fix head:{}", JsonEntityTransform.Object2Json(header));
        }

        if(LOGGER.isDebugEnabled()){
            LogUtil.debug(LOGGER,"fix par cn:{} .cc:{}",cn,cc);
        }
        DataTransferObject dto = new DataTransferObject();
        String suggestName = StringUtils.removeInvalidChar(ValueUtil.getStrValue(cn));
        String cityCode = StringUtils.removeInvalidChar(ValueUtil.getStrValue(cc));
        if(Check.NuNObj(suggestName) || Check.NuNObj(cityCode)){
            dto.putValue("info", new HashMap<>());
            return dto.toJsonString();
        }
        String jsonRst = null;
        try {
            //获取用户的联想词
            jsonRst = searchService.getOneCommunityInfo(suggestName, cityCode);
        }catch (Exception e){
            LogUtil.error(LOGGER, "fix par cn:{} .cc:{} and e:{}", cn, cc, e);
            dto.putValue("info", new HashMap<>());
            return dto.toJsonString();
        }
        if(LOGGER.isDebugEnabled()){
            LogUtil.debug(LOGGER,"fix result {}.",jsonRst);
        }
        return jsonRst;
    }

    /**
     * 房源的搜索
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value ="/query")
    public @ResponseBody
    DataTransferObject query(HttpServletRequest request,HttpServletResponse response) {


        DataTransferObject dto = null;
        Header header = getHeader(request);
        //获取当前的用户uid
        String uid = getUserId(request);

        HouseInfoRequest houseInfo = getEntity(request,HouseInfoRequest.class);
        if(Check.NuNObj(houseInfo)){
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto;
        }

        try {			
        	if (!Check.NuNObj(header) && !Check.NuNStr(header.getVersionCode())) {
        		houseInfo.setVersionCode(ValueUtil.getintValue(header.getVersionCode()));
        	}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "版本号转化错误，versionCode={},e={}", header.getVersionCode(),e);
		}
        
        LogUtil.info(LOGGER, "query par:{}", JsonEntityTransform.Object2Json(houseInfo));
        //ElasticSearch索引搜索数据
        elasticSearchService.generateIndex(houseInfo);
        
        //参数校验
        if(!Check.NuNStr(houseInfo.getStartTime())){
            try {
                Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfo.getStartTime()), "yyyy-MM-dd");
                Date now =DateUtil.parseDate(DateUtil.dateFormat(new Date()), "yyyy-MM-dd");
                if(startTime.before(now)){
                    dto = new DataTransferObject();
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg("只能选择今天及以后的日期");
                    return dto;
                }
            }catch (Exception e){
                LogUtil.error(LOGGER, "query par:{},e:{}", JsonEntityTransform.Object2Json(houseInfo),e);
                dto = new DataTransferObject();
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto;
            }
        }

        //参数校验
        if(!Check.NuNStr(houseInfo.getStartTime()) &&  !Check.NuNStr(houseInfo.getEndTime())){
            try {
                Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfo.getStartTime()), "yyyy-MM-dd");
                Date endTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfo.getEndTime()), "yyyy-MM-dd");
                if(startTime.after(endTime)){
                    dto = new DataTransferObject();
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg(" startTime is after endTime");
                    return dto;
                }
            }catch (Exception e){
                LogUtil.error(LOGGER, "query par:{},e:{}", JsonEntityTransform.Object2Json(houseInfo),e);
                dto = new DataTransferObject();
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto;
            }
        }
//        if(Check.NuNStr(houseInfo.getCityCode())){
//            dto = new DataTransferObject();
//            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
//            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
//            return dto;
//        }
        if(Check.NuNStr(houseInfo.getQ())){
            houseInfo.setQ("*:*");
        }
        if (Check.NuNObj(houseInfo.getIsRecommend())) {
    		houseInfo.setIsRecommend(YesOrNoEnum.YES.getCode());
		}
        
        houseInfo.setSearchSourceTypeEnum(SearchSourceTypeEnum.search_list);
        
        //今日特惠
        if(Check.NuNStr(houseInfo.getStartTime()) && Check.NuNStr(houseInfo.getEndTime()) 
        		&& !Check.NuNStr(houseInfo.getJiaxinDiscount()) && houseInfo.getJiaxinDiscount().equalsIgnoreCase(ProductRulesEnum020.ProductRulesEnum020001.getValue())){
        	houseInfo.setStartTime(DateUtil.dateFormat(new Date()));
    		houseInfo.setEndTime(DateUtil.dateFormat(DateSplitUtil.getTomorrow(new Date())));
        }
		
        String jsonRst = null;


        TraceInfoVo traceInfo = new TraceInfoVo();
        traceInfo.setHeader(header);
        traceInfo.setRequest(houseInfo);
        Long start = System.currentTimeMillis();
        try {
        	
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
        	
            String par = JsonEntityTransform.Object2Json(houseInfo);
            //获取搜索结果
            long searchBegin = new Date().getTime();
            jsonRst =searchService.getHouseListInfoAndSuggest(defaultPicSize, par,uid);
//            jsonRst =searchService.getHouseListInfo(defaultPicSize, JsonEntityTransform.Object2Json(houseInfo));
            LogUtil.info(LOGGER, "搜索结束，共耗时{}ms", (new Date().getTime()-searchBegin));
            dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
            if (dto!=null && DataTransferObject.SUCCESS==dto.getCode()) {
            	int total =0;
            	try {
            		total = SOAResParseUtil.getIntFromDataByKey(jsonRst, "total");
				} catch (Exception e) {
					LogUtil.info(LOGGER, "获取总数异常", e);
				}
            	 
            	Map<String, Object> cityCardMap =  new HashMap<String, Object>();
            	
            	if (houseInfo.getPage()==1 && !Check.NuNStr(houseInfo.getCityCode())) {//只有在第一页且选择了城市才会返回城市档案
            		long cityCardIndexBegin = new Date().getTime();
            		try {
            			Integer index = Integer.valueOf(zksysService.getZkSysValue("statics", "cityCardIndex"));
            			
            			if (!Check.NuNObj(index) && index<8 && index>0) {
            				cityCardIndex = index;
            			}            		
            		} catch (Exception e) {
            			LogUtil.info(LOGGER, "获取zk配置cityCardIndex异常e={}", e);
            		}            		
            		LogUtil.info(LOGGER, "获取zk配置cityCardIndex结束，共耗时{}ms", (new Date().getTime()-cityCardIndexBegin));
            		
            		int suggestTotal =0;
            		
            		if (total==0) {
            			try {
            				List<Object> suggest = SOAResParseUtil.getListValueFromDataByKey(jsonRst, "suggest", Object.class);
            				if (!Check.NuNCollection(suggest)) {
								suggestTotal = suggest.size();
							}
            			} catch (Exception e) {
            				LogUtil.debug(LOGGER, "转化推荐列表异常，e={}", e);
            			}
					}
            		
            		
            		long cityCardBegin = new Date().getTime();
            		try {
            			if(total!=0){
            				cityCardMap =  getCityCardByCondition(houseInfo,total);
            			}else{
            				cityCardMap =  getCityCardByCondition(houseInfo,suggestTotal);
            			}
					} catch (Exception e) {
						LogUtil.error(LOGGER, "获取城市档案数据异常,cityCode={},e={}", houseInfo.getCityCode(),e);
					}
            		LogUtil.info(LOGGER, "获取城市档案数据结束，共耗时{}ms", (new Date().getTime()-cityCardBegin));
            		
				}
            	
            	dto.putValue("cityCard",cityCardMap);
            	
            	if (total>10 && houseInfo.getPage() <= ValueUtil.getPage(total, houseInfo.getLimit())) {//只有记录总条数大于10才返回
            		
            		if (!Check.NuNObj(cityCardMap) && cityCardMap.size()>0 ) {
            			GuideCardEnum.index = (Integer) cityCardMap.get("index")+2;
					}else if (total<cityCardIndex) {
						GuideCardEnum.index =total;
					} else{	
						GuideCardEnum.index =cityCardIndex-1;
					}            		
            		
            		long guideCardBegin = new Date().getTime();
            		try {
            			dto.putValue("guideCard", getCardByCondition(houseInfo));
					} catch (Exception e) {
						LogUtil.error(LOGGER, "获取搜索卡片异常,e={}",e);
					}
            		LogUtil.info(LOGGER, "获取搜索卡片结束，共耗时{}ms", (new Date().getTime()-guideCardBegin));
            	}else {
            		dto.putValue("guideCard", new ArrayList<Map<String, Object>>());
            	}
            	
			}
        } catch (Exception e) {
            traceInfo.setCode(-1);
            traceInfo.setMsg("调用服务异常");
            Long end = System.currentTimeMillis();
            traceInfo.setCost(end-start);
            dto = new DataTransferObject();
            LogUtil.error(record,"traceinfo:{}",JsonEntityTransform.Object2Json(traceInfo));
            LogUtil.error(LOGGER, "query par :{} e:{}", JsonEntityTransform.Object2Json(houseInfo), e);
            return dto;
        }
        Long end = System.currentTimeMillis();
        traceInfo.setCost(end-start);
        traceInfo.setCode(dto.getCode());
        traceInfo.setMsg(dto.getMsg());
        if (dto.getCode() == DataTransferObject.SUCCESS){
            traceInfo.setTotal(ValueUtil.getintValue(dto.getData().get("total")));
        }
        LogUtil.info(record,"traceinfo:{}",JsonEntityTransform.Object2Json(traceInfo));

        try {
            //保存当前的位置信息
            this.saveLocation(uid,header,getIpAddress(request),LocationTypeEnum.SEARCH);
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}" , e);
        }
        LogUtil.debug(LOGGER,"query result {}.",jsonRst);

        // 处理列表的banner
        if(!Check.NuNStr(header.getVersionCode()) && VersionCodeEnum.checkCompatibleVersion(Integer.valueOf(header.getVersionCode()), VersionCodeEnum.V_20170924.getCode())){
            dealHouseListBanner(houseInfo, dto);
        }
        return dto;
    }

    /**
     * 处理banner
     *
     * @param
     * @return
     * @author zhangyl
     * @created 2017/9/11 14:31
     */
    private void dealHouseListBanner(HouseInfoRequest houseInfo, DataTransferObject dto) {
        List<BannerVo> bannerList = dto.parseData("bannerList", new TypeReference<List<BannerVo>>() {});
        if(Check.NuNCollection(bannerList)){
            bannerList = new ArrayList<>();
        }

        // 今夜特价banner 2017-09-08
        BannerVo tonightDiscountBanner = getTonightDiscountBanner(houseInfo, dto);
        if (!Check.NuNObj(tonightDiscountBanner)) {
            bannerList.add(tonightDiscountBanner);
        }

        // 对banner排序返回
        Collections.sort(bannerList, new Comparator<BannerVo>() {
            @Override
            public int compare(BannerVo o1, BannerVo o2) {
                return o1.getBannerIndex().compareTo(o2.getBannerIndex());
            }
        });

        dto.putValue("bannerList", bannerList);
    }

    /**
     * 民宿列表页-今夜特价引导BANNER
     *
     * @param
     * @return
     * @author zhangyl
     * @created 2017/9/8 14:38
     */
    private BannerVo getTonightDiscountBanner(HouseInfoRequest houseInfo, DataTransferObject dto) {
        BannerVo bannerVo = null;
        String logPreStr = "民宿列表页-今夜特价引导BANNER-";
        try {
            // 第一页并且未筛选今夜特价
            if (houseInfo.getPage() == 1
                    && !ProductRulesEnum020.ProductRulesEnum020001.getValue().equalsIgnoreCase(houseInfo.getJiaxinDiscount())) {

                String startTime = houseInfo.getStartTime();
                Date now = new Date();
                String nowStr = DateUtil.dateFormat(new Date());

                List<HouseInfoEntity> houseList = dto.parseData("list", new TypeReference<List<HouseInfoEntity>>() {
                });

                // 未选开始时间或从今天开始&&房源卡片数量大于足够banner位置数
                if ((Check.NuNStr(startTime) || nowStr.equals(startTime))
                        && !Check.NuNCollection(houseList)
                        && houseList.size() > BannerTypeEnum.TonightDiscountGuideBanner001.getIndex()) {

                    // 查今夜特价最低折扣
                    HouseInfoRequest tonightDiscountRequest = new HouseInfoRequest();
                    tonightDiscountRequest.setPage(1);
                    tonightDiscountRequest.setLimit(1);
                    tonightDiscountRequest.setCityCode(houseInfo.getCityCode());
                    tonightDiscountRequest.setStartTime(nowStr);
                    tonightDiscountRequest.setEndTime(DateUtil.dateFormat(DateSplitUtil.getTomorrow(now)));
                    tonightDiscountRequest.setJiaxinDiscount(ProductRulesEnum020.ProductRulesEnum020001.getValue());
                    tonightDiscountRequest.setSortType(SortTypeEnum.TONIGHT_ARTICLE.getCode());
                    String par = JsonEntityTransform.Object2Json(tonightDiscountRequest);
                    String jsonRst = searchService.getHouseListInfo(defaultPicSize, par);
                    LogUtil.info(LOGGER, logPreStr + "最低折扣房源 jsonRst={}", jsonRst);

                    if (SOAResParseUtil.checkSOAReturnExpect(jsonRst, DataTransferObject.SUCCESS)) {
                        List<HouseInfoEntity> houseInfoEntityList = SOAResParseUtil.getListValueFromDataByKey(jsonRst, "list", HouseInfoEntity.class);
                        if (!Check.NuNCollection(houseInfoEntityList) && !Check.NuNObj(houseInfoEntityList.get(0))) {
                            // 折扣值
                            Double discount = houseInfoEntityList.get(0).getTonightDiscountInfoVo().getTonightDiscount();

                            if (Check.NuNObj(discount) || discount >= 1 || discount <= 0) {
                                LogUtil.error(LOGGER, logPreStr + "折扣值异常");
                                return null;
                            }

                            // 民宿列表页 今夜特价引导BANNER文案title
                            String bannerTitleJson = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_GUIDE_BANNER_TITLE);
                            StaticResourceVo bannerTitleResourceVo = SOAResParseUtil.getValueFromDataByKey(bannerTitleJson, "result", StaticResourceVo.class);
                            if (Check.NuNObj(bannerTitleResourceVo) || Check.NuNObj(bannerTitleResourceVo.getResContent())) {
                                LogUtil.error(LOGGER, logPreStr + "文案title为空，bannerTitleJson={}", bannerTitleJson);
                                return null;
                            }
                            String bannerTitle = bannerTitleResourceVo.getResContent();

                            // 民宿列表页 今夜特价引导BANNER文案subtitle
                            String bannerSubtitleJson = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_GUIDE_BANNER_SUBTITLE);
                            StaticResourceVo bannerSubtitleResourceVo = SOAResParseUtil.getValueFromDataByKey(bannerSubtitleJson, "result", StaticResourceVo.class);
                            if (Check.NuNObj(bannerSubtitleResourceVo) || Check.NuNObj(bannerSubtitleResourceVo.getResContent())) {
                                LogUtil.error(LOGGER, logPreStr + "文案subtitle为空，bannerSubtitleJson={}", bannerSubtitleJson);
                                return null;
                            }

                            DecimalFormat format = new DecimalFormat("0.#");
                            String bannerSubtitle = bannerSubtitleResourceVo.getResContent().replace("{1}", format.format(discount * 10));

                            // 民宿列表页 今夜特价引导BANNER图片
                            String bannerImgJson = searchService.getStaticResourceByResCode(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_GUIDE_BANNER_IMG_SRC);
                            StaticResourceVo bannerImgResourceVo = SOAResParseUtil.getValueFromDataByKey(bannerImgJson, "result", StaticResourceVo.class);
                            if (Check.NuNObj(bannerImgResourceVo) || Check.NuNCollection(bannerImgResourceVo.getStaticResourcePicList()) || Check.NuNObj(bannerImgResourceVo.getStaticResourcePicList().get(0))) {
                                LogUtil.error(LOGGER, logPreStr + "图片为空，bannerImgJson={}", bannerImgJson);
                                return null;
                            }
                            StaticResourcePicEntity bannerImgEntity = bannerImgResourceVo.getStaticResourcePicList().get(0);
                            String bannerImg = PicUtil.getFullPic(picBaseAddrMona, bannerImgEntity.getPicBaseUrl(), bannerImgEntity.getPicSuffix(), defaultPicSize);

                            // banner
                            bannerVo = new BannerVo();
                            bannerVo.setBannerType(BannerTypeEnum.TonightDiscountGuideBanner001.getType());
                            bannerVo.setBannerTitle(bannerTitle);
                            bannerVo.setBannerSubtitle(bannerSubtitle);
                            bannerVo.setBannerImg(bannerImg);
                            bannerVo.setBannerIndex(BannerTypeEnum.TonightDiscountGuideBanner001.getIndex());

                        } else {
                            LogUtil.info(LOGGER, "民宿列表页 今夜特价引导BANNER 获取最低折扣房源为空 jsonRst={}", jsonRst);
                        }
                    } else {
                        LogUtil.error(LOGGER, "民宿列表页 今夜特价引导BANNER 获取最低折扣房源异常 jsonRst={}", jsonRst);
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "民宿列表页 今夜特价引导BANNER 异常，houseInfo={},e={}", houseInfo, e);
        }

        return bannerVo;
    }

    /**
     * 查询城市档案
     * @author zl
     * @param houseInfo
     * @return
     */
    private Map<String, Object> getCityCardByCondition( HouseInfoRequest houseInfo,int total) {
    	
    	
    	Map<String, Object> resultMap = new HashMap<>();
    	
    	try {
    		String resJson = cityFileService.getCityRegionsByCityCode(houseInfo.getCityCode());
    		LogUtil.info(LOGGER, "获取城市档案结果:{}", resJson);
    		if (!Check.NuNStr(resJson)) {
    			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resJson);
    			if (dto.getCode()!=DataTransferObject.SUCCESS) {
					return resultMap;
				}
    			
    			FileRegionsVo fileRegionsVo = dto.parseData("fileRegions", new TypeReference<FileRegionsVo>() {});
    			if (!Check.NuNObj(fileRegionsVo)) {
    				resultMap.put("cityCode", fileRegionsVo.getCityCode());
    		    	resultMap.put("colTitle", fileRegionsVo.getColTitle());
    		    	resultMap.put("colDeputyTitle", fileRegionsVo.getColDeputyTitle());
    		    	resultMap.put("colShareTitle", fileRegionsVo.getColShareTitle());
    		    	resultMap.put("colBackColor", fileRegionsVo.getColBackColor());
    		    	if (total<cityCardIndex) {
    		    		resultMap.put("index", total);
					} else{						
						resultMap.put("index", cityCardIndex-1);
					}
    		    	
    		    	List<ColumnRegionPicVo> regions = fileRegionsVo.getRegionList();
    		    	if (!Check.NuNCollection(regions)) {
    		    		List<Map<String, Object>> regionList = new ArrayList<>();
    		    		
    		    		for (ColumnRegionPicVo regionPicVo : regions) {
    		    			Map<String, Object> regionMap = new HashMap<>();
    	    		    	regionMap.put("regionName", regionPicVo.getRegionName());
    	    		    	regionMap.put("regionBrief", regionPicVo.getRegionBrief());
    	    		    	String picSrc = PicUtil.getFullPic(picBaseAddrMona, regionPicVo.getPicBaseUrl(), regionPicVo.getPicSuffix(), detail_big_pic);
    	    		    	regionMap.put("picSrc", picSrc);
    	    		    	regionMap.put("targetUrl", regionPicVo.getJumpUrl());
    	    		    	
    	    		    	regionList.add(regionMap);
						}
    		    		
    		    		resultMap.put("regionList", regionList);
					}
    		    	
				}
    			
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取城市档案数据异常,cityCode={},e={}", houseInfo.getCityCode(),e);
		}
    	
    	return resultMap;
    }
    
    
    /**
     * 按照搜索条件返回卡片信息
     * 
     * @author zl
     * @param houseInfo
     * @return
     */
	private static List<Map<String, Object>> getCardByCondition( HouseInfoRequest houseInfo) {
		List<Map<String, Object>> list = new ArrayList<>();
		
		if (houseInfo==null) {
			return null;
		}
		
		int size=GuideCardEnum.size;
		
		if (Check.NuNStr(houseInfo.getCityCode()) && list.size()<size) {
			list.add(GuideCardEnum.getCardEnumMap(GuideCardEnum.city_card));
		}
		if ((Check.NuNStr(houseInfo.getStartTime())&&Check.NuNStr(houseInfo.getEndTime())) && list.size()<size) {
			list.add(GuideCardEnum.getCardEnumMap(GuideCardEnum.date_card));
		}
		if (Check.NuNObj(houseInfo.getPersonCount()) && list.size()<size) {
			list.add(GuideCardEnum.getCardEnumMap(GuideCardEnum.guests_num_card));
		}
		if (Check.NuNObj(houseInfo.getRentWay()) && list.size()<size) {
			list.add(GuideCardEnum.getCardEnumMap(GuideCardEnum.rent_way_card));
		}
		if ((Check.NuNObj(houseInfo.getPriceStart())&&Check.NuNObj(houseInfo.getPriceEnd())) && list.size()<size) {
			list.add(GuideCardEnum.getCardEnumMap(GuideCardEnum.price_card));
		}
		
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			
			@Override
			public int compare(Map<String, Object> o1,
					Map<String, Object> o2) {
				return String.valueOf(o1.get("code")).compareTo(String.valueOf(o2.get("code")));
			}
		});
		
		//目前业务和分页无关
//		if (houseInfo.getPage()*size<=list.size()) {
//			list=list.subList((houseInfo.getPage()-1)*size, houseInfo.getPage()*size);
//		}else if ((houseInfo.getPage()-1)*size<=list.size()) {
//			list=list.subList((houseInfo.getPage()-1)*size, list.size());
//		}else {
//			list = new ArrayList<Map<String, Object>>();
//		}  
			
		return list;		
	}

    /**
     * 房源的搜索
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value ="/landList")
    public @ResponseBody
    DataTransferObject landList(HttpServletRequest request,HttpServletResponse response) {
    	
    	response.addHeader( "Access-Control-Allow-Origin", "*" ); // open your api to any client
		// response.addHeader( "Access-Control-Allow-Methods", "POST" ); // a allow post
		response.addHeader( "Access-Control-Max-Age", "3000" ); // time from request to response before timeout
    	
        DataTransferObject dto = null;

        Header header = getHeader(request);
        if( Check.NuNObj(header)){
            LogUtil.debug(LOGGER, "landList head:{}", JsonEntityTransform.Object2Json(header));
        }

        LandHouseRequest landRequest = getEntity(request,LandHouseRequest.class);
        if(Check.NuNObj(landRequest)){
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto;
        }
        
        try {			
        	if (!Check.NuNObj(header) && !Check.NuNStr(header.getVersionCode())) {
        		landRequest.setVersionCode(ValueUtil.getintValue(header.getVersionCode()));
        	}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "版本号转化错误，versionCode={},e={}", header.getVersionCode(),e);
		}
        
        if(LOGGER.isDebugEnabled()){
            LogUtil.debug(LOGGER, "landList par:{}", JsonEntityTransform.Object2Json(landRequest));
        }
        //参数校验
        if(Check.NuNStr(landRequest.getLandlordUid())){
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto;
        }
        String jsonRst = null;
        try {
            //获取搜索结果
            jsonRst =searchService.getLandHouseList(defaultIconSize,JsonEntityTransform.Object2Json(landRequest));
            dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
        } catch (Exception e) {
            dto = new DataTransferObject();
            LogUtil.error(LOGGER, "landList par :{} e:{}", JsonEntityTransform.Object2Json(landRequest), e);
            return dto;
        }
        LogUtil.debug(LOGGER,"landList result {}.",jsonRst);
        return dto;
    }
    
    
    
    /**
     *  
     * 房东的分享房源列表
     *
     * @author zl
     * @created 2017年3月28日 下午5:08:19
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value ="/landShareHouseList")
    public @ResponseBody
    DataTransferObject landShareHouseList(HttpServletRequest request,HttpServletResponse response) {
    	
        DataTransferObject dto = new DataTransferObject();

        Header header = getHeader(request);
        if( Check.NuNObj(header)){
            LogUtil.debug(LOGGER, "landShareHouseList head:{}", JsonEntityTransform.Object2Json(header));
        }

        LandHouseRequest landRequest = getEntity(request,LandHouseRequest.class);
        if(Check.NuNObj(landRequest)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto;
        } 
        
        try {			
        	if (!Check.NuNObj(header) && !Check.NuNStr(header.getVersionCode())) {
        		landRequest.setVersionCode(ValueUtil.getintValue(header.getVersionCode()));
        	}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "版本号转化错误，versionCode={},e={}", header.getVersionCode(),e);
		}
        
        LogUtil.info(LOGGER, "landShareHouseList par:{}", JsonEntityTransform.Object2Json(landRequest));
        //参数校验
        if(Check.NuNStr(landRequest.getLandlordUid())){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto;
        }
        //参数校验
        if(!Check.NuNStr(landRequest.getStartTime()) &&  !Check.NuNStr(landRequest.getEndTime())){
            try {
                Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(landRequest.getStartTime()), "yyyy-MM-dd");
                Date endTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(landRequest.getEndTime()), "yyyy-MM-dd");
                if(startTime.after(endTime)){
                    dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                    dto.setMsg(" startTime is after endTime");
                    return dto;
                }
            }catch (Exception e){
                LogUtil.error(LOGGER, "landShareHouseList:{},e:{}", JsonEntityTransform.Object2Json(request),e);
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto;
            }
        }
        
        String jsonRst = null;
        try {
            //获取搜索结果
            jsonRst =searchService.getLandShareHouseList(defaultIconSize,JsonEntityTransform.Object2Json(landRequest));
            dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
        } catch (Exception e) {
            dto = new DataTransferObject();
            LogUtil.error(LOGGER, "landShareHouseList par :{} e:{}", JsonEntityTransform.Object2Json(landRequest), e);
            return dto;
        }
        LogUtil.debug(LOGGER,"landShareHouseList result {}.",jsonRst);
        return dto;
    }
    

    /**
     * 老接口兼容，直接转发新接口
     * @author zl
     * @param request
     */
    @RequestMapping(value ="/sort")
    @ResponseBody
    public DataTransferObject sort(HttpServletRequest request,HttpServletResponse response ){
    	DataTransferObject dto = new DataTransferObject();
    	long t1 = System.currentTimeMillis();
    	String url="/searchInit/sort";
    	 try {
			request.getRequestDispatcher(url).forward( request, response );
		} catch (Exception e) {
			LogUtil.error(LOGGER, "老接口转发异常,接口地址:{},总共耗时:{}ms,e:{}",url,(System.currentTimeMillis()-t1),e);
		} 
		 dto.setErrCode(DataTransferObject.ERROR);
		 dto.setMsg("请更新版本！");
		 return dto;
    }
    
    /**
     * 老接口兼容，直接转发新接口
     * @author zl
     * @param request
     */
    @RequestMapping(value ="/jsonp/list")
    @ResponseBody
    public JsonpVo listp(HttpServletRequest request,HttpServletResponse response ){
    	long t1 = System.currentTimeMillis();
    	 JsonpVo jsonpVo = new JsonpVo();
         String jsonpcallback=request.getParameter("callback");
         jsonpVo.setCallBack(jsonpcallback);
    	DataTransferObject dto = new DataTransferObject();
    	String url = "/searchInit/jsonp/list";
    	 try {
			request.getRequestDispatcher(url).forward( request, response );
		} catch (Exception e) {
			LogUtil.error(LOGGER, "老接口转发异常,接口地址:{},总共耗时:{}ms,e:{}",url,(System.currentTimeMillis()-t1),e); 
		} 
		 dto.setErrCode(DataTransferObject.ERROR);
		 dto.setMsg("请更新版本！");
		 jsonpVo.setDto(dto);
	     return jsonpVo;
    }
    
    /**
     * 老接口兼容，直接转发新接口
     * @author zl
     * @param request
     */
    @RequestMapping(value ="/list")
    @ResponseBody
    public DataTransferObject list(HttpServletRequest request,HttpServletResponse response ){
    	DataTransferObject dto = new DataTransferObject();
    	long t1 = System.currentTimeMillis();
    	String url ="/searchInit/list";
    	 try {
			request.getRequestDispatcher(url).forward( request, response );
		} catch (Exception e) {
			LogUtil.error(LOGGER, "老接口转发异常,接口地址:{},总共耗时:{}ms,e:{}",url,(System.currentTimeMillis()-t1),e); 
		} 
		 dto.setErrCode(DataTransferObject.ERROR);
		 dto.setMsg("请更新版本！");
		 return dto;
    }
    /**
     * 老接口兼容，直接转发新接口
     * @author zl
     * @param request
     */
    @RequestMapping(value ="/location")
    @ResponseBody
    public DataTransferObject location(HttpServletRequest request,HttpServletResponse response ){
    	DataTransferObject dto = new DataTransferObject();
    	long t1 = System.currentTimeMillis();
    	String url="/searchInit/location";
    	 try {
			request.getRequestDispatcher(url).forward( request, response );
		} catch (Exception e) {
			LogUtil.error(LOGGER, "老接口转发异常,接口地址:{},总共耗时:{}ms,e:{}",url,(System.currentTimeMillis()-t1),e); 
		} 
		 dto.setErrCode(DataTransferObject.ERROR);
		 dto.setMsg("请更新版本！");
		 return dto;
    }
    /**
     * 老接口兼容，直接转发新接口
     * @author zl
     * @param request
     */
    @RequestMapping(value ="/locationSort")
    @ResponseBody
    public DataTransferObject locationSort(HttpServletRequest request,HttpServletResponse response ){
    	DataTransferObject dto = new DataTransferObject();
    	long t1 = System.currentTimeMillis();
    	String url="/searchInit/locationSort";
    	 try {
			request.getRequestDispatcher(url).forward( request, response );
		} catch (Exception e) {
			LogUtil.error(LOGGER, "老接口转发异常,接口地址:{},总共耗时:{}ms,e:{}",url,(System.currentTimeMillis()-t1),e);
		} 
		 dto.setErrCode(DataTransferObject.ERROR);
		 dto.setMsg("请更新版本！");
		 return dto;
    }
    
    /**
     * 老接口兼容，直接转发新接口
     * @author zl
     * @param request
     */
    @RequestMapping(value ="/initSearchBaseData")
    @ResponseBody
    public DataTransferObject initSearchBaseData(HttpServletRequest request,HttpServletResponse response ){
    	DataTransferObject dto = new DataTransferObject();
    	long t1 = System.currentTimeMillis();
    	String url="/searchInit/initSearchBaseData";
    	 try {
			request.getRequestDispatcher(url).forward( request, response );
		} catch (Exception e) {
			LogUtil.error(LOGGER, "老接口转发异常,接口地址:{},总共耗时:{}ms,e:{}",url,(System.currentTimeMillis()-t1),e);
		} 
		 dto.setErrCode(DataTransferObject.ERROR);
		 dto.setMsg("请更新版本！");
		 return dto;
    }
    
    /**
     * 老接口兼容，直接转发新接口
     * @author zl
     * @param request
     */
    @RequestMapping(value ="/suggest")
    @ResponseBody
    public DataTransferObject suggest(HttpServletRequest request,HttpServletResponse response ){
    	DataTransferObject dto = new DataTransferObject();
    	long t1 = System.currentTimeMillis();
    	String url="/searchSuggest/suggest";
    	 try {
			request.getRequestDispatcher(url).forward( request, response );
		} catch (Exception e) {
			LogUtil.error(LOGGER, "老接口转发异常,接口地址:{},总共耗时:{}ms,e:{}",url,(System.currentTimeMillis()-t1),e); 
		} 
		 dto.setErrCode(DataTransferObject.ERROR);
		 dto.setMsg("请更新版本！");
		 return dto;
    }
    
    /**
     * 老接口兼容，直接转发新接口
     * @author zl
     * @param request
     */
    @RequestMapping(value ="/complate")
    @ResponseBody
    public String complate(HttpServletRequest request,HttpServletResponse response ){
    	DataTransferObject dto = new DataTransferObject();
    	long t1 = System.currentTimeMillis();
    	String url="/searchSuggest/complate";
    	 try {
			request.getRequestDispatcher(url).forward( request, response );
		} catch (Exception e) {
			LogUtil.error(LOGGER, "老接口转发异常,接口地址:{},总共耗时:{}ms,e:{}",url,(System.currentTimeMillis()-t1),e); 
		} 
		 dto.setErrCode(DataTransferObject.ERROR);
		 dto.setMsg("请更新版本！");
		 return dto.toJsonString();
    }

    /**
     * 最新房源-jsonp格式
     * @author afi
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value ="/jsonp/lasthouse")
    public @ResponseBody
    JsonpVo jsonpLasthouse(HttpServletRequest request,HttpServletResponse response) {
        JsonpVo jsonpVo = new JsonpVo();
        String jsonpcallback=request.getParameter("callback");
        jsonpVo.setCallBack(jsonpcallback);

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
        jsonpVo.setDto(dto);
        return jsonpVo;
    }




    /**
     * 最新房源
     * @author afi
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value ="/lasthouse")
    public @ResponseBody
    DataTransferObject lasthouse(HttpServletRequest request,HttpServletResponse response) {
        DataTransferObject dto = null;

        Header header = getHeader(request);
        if( Check.NuNObj(header)){
            LogUtil.debug(LOGGER, "lasthouse head:{}", JsonEntityTransform.Object2Json(header));
        }
        String jsonRst = null;
        try {
            //获取搜索结果
            jsonRst =searchService.getNewHouseLst(defaultIconSize);
            dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
        } catch (Exception e) {
            dto = new DataTransferObject();
            LogUtil.error(LOGGER, "lasthouse e:{}", e);
            return dto;
        }
        return dto;
    }
    
    
    /**
     * 保存当前用户的head信息
     * @param uid
     * @param header
     */
    private void saveLocation(final String uid, final Header header, final String serverIp, final LocationTypeEnum locationTypeEnum){

        if (Check.NuNObj(header)){
            return;
        }

        try{
            Thread task = new Thread(){
                @Override
                public void run() {
                	
                	CustomerLocationEntity customerLocationEntity = transParams2Entity(uid,header,serverIp);
                    customerLocationEntity.setLocationType(locationTypeEnum.getCode());
                	if (!Check.NuNObj(customerLocationEntity)) {						
                		if (checkLocatByRedis(customerLocationEntity)){
                			LogUtil.info(LOGGER,"location info uid:{},header:{}" , uid, JsonEntityTransform.Object2Json(header));
                			return;
                		}
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
	 * 
	 * top50列表
	 *
	 * @author zl
	 * @created 2017年3月15日 下午3:56:55
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/top50HouseList")
	@ResponseBody
	public DataTransferObject top50HouseList(HttpServletRequest request, HttpServletResponse response) {

		DataTransferObject dto = new DataTransferObject();
		
		try {
			
			response.addHeader( "Access-Control-Allow-Origin", "*" ); // open your api to any client
			// response.addHeader( "Access-Control-Allow-Methods", "POST" ); // a allow post
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
	 * 
	 * 首页top50专栏
	 *
	 * @author zl
	 * @created 2017年3月21日 下午2:08:17
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/top50HouseListArticles")
	@ResponseBody
	public DataTransferObject top50HouseListArticles(HttpServletRequest request, HttpServletResponse response) {

		DataTransferObject dto = new DataTransferObject();

		try {

			Header header = getHeader(request);
			// 获取当前的用户uid
			String uid = getUserId(request);
			
			Top50HouseListRequest top50ListRequest = new Top50HouseListRequest();
			top50ListRequest.setPage(1);
			top50ListRequest.setLimit(11);	
			request.setAttribute(ParamCollector.PARAMS, JsonEntityTransform.Object2Json(top50ListRequest));
 
			dto = top50HouseList( request,  response);
			
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
			return dto;
		}

		return dto;
	}
	
	
	/**
	 * 
	 * 今日特惠专栏
	 *
	 * @author zl
	 * @created 2017年4月1日 上午10:41:29
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/todayDiscountArticles")
	@ResponseBody
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
						int total = SOAResParseUtil.getIntFromDataByKey(jsonRst, "total");
						
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
	 * 
	 * 根据品牌编码列表查询房源
	 *
	 * @author zl
	 * @created 2017年4月1日 上午10:41:29
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/searchByBrandSnList")
	@ResponseBody
	public DataTransferObject searchByBrandSnList(HttpServletRequest request, HttpServletResponse response, String par) {
		
		response.addHeader( "Access-Control-Allow-Origin", "*" ); // open your api to any client
		// response.addHeader( "Access-Control-Allow-Methods", "POST" ); // a allow post
		response.addHeader( "Access-Control-Max-Age", "3000" ); // time from request to response before timeout

		DataTransferObject dto = new DataTransferObject();

		try {

			Header header = getHeader(request);
			// 获取当前的用户uid
			String uid = getUserId(request);
			
			HouseListByBrandSnListRequest brandRequest = new HouseListByBrandSnListRequest();
			
			try {
				brandRequest = JsonEntityTransform.json2Entity(par,HouseListByBrandSnListRequest.class);
	        }catch (Exception e){
	            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
	            return dto;
	        }
			
			if(Check.NuNCollection(brandRequest.getBrandSnList())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto;
            } 
			
			try {			
	        	if (!Check.NuNObj(header) && !Check.NuNStr(header.getVersionCode())) {
	        		brandRequest.setVersionCode(ValueUtil.getintValue(header.getVersionCode()));
	        	}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "版本号转化错误，versionCode={},e={}", header.getVersionCode(),e);
			}
			
            String resultStr = searchService.getHouseListByBrandSnList(defaultPicSize,JsonEntityTransform.Object2Json(brandRequest));
            dto = JsonEntityTransform.json2DataTransferObject(resultStr);
            
		} catch (Exception e) {
			LogUtil.error(LOGGER, "根据品牌编码列表查询数据异常,e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto;
		}

		return dto;
	}
	
	
    @RequestMapping(value ="/jsonp/searchByBrandSnList")
    public @ResponseBody
    JsonpVo searchByBrandSnListp(HttpServletRequest request, HttpServletResponse response, String par) {
        JsonpVo jsonpVo = new JsonpVo();
        String jsonpcallback=request.getParameter("callback");
        jsonpVo.setCallBack(jsonpcallback);
        DataTransferObject dto = new DataTransferObject();
        Header header = getHeader(request);
        if (Check.NuNStr(par)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            jsonpVo.setDto(dto);
            return jsonpVo;
        }
        dto = searchByBrandSnList( request,  response ,par);
        jsonpVo.setDto(dto);
        return jsonpVo;
    }
    
    /**
     * 类似房源  新接口
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value ="/getSimilarHouse")
    public @ResponseBody
    DataTransferObject getSimilarHouse(HttpServletRequest request,HttpServletResponse response) {
    	
    	response.addHeader( "Access-Control-Allow-Origin", "*" ); // open your api to any client
		// response.addHeader( "Access-Control-Allow-Methods", "POST" ); // a allow post
		response.addHeader( "Access-Control-Max-Age", "3000" ); // time from request to response before timeout
    	
        DataTransferObject dto = null;

        Header header = getHeader(request);
        if( Check.NuNObj(header)){
            LogUtil.debug(LOGGER, "getSimilarHouse head:{}", JsonEntityTransform.Object2Json(header));
        }

        LandHouseRequest landRequest = getEntity(request,LandHouseRequest.class);
        if(Check.NuNObj(landRequest)){
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto;
        }
        
        try {			
        	if (!Check.NuNObj(header) && !Check.NuNStr(header.getVersionCode())) {
        		landRequest.setVersionCode(ValueUtil.getintValue(header.getVersionCode()));
        	}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "版本号转化错误，versionCode={},e={}", header.getVersionCode(),e);
		}
        
        if(LOGGER.isDebugEnabled()){
            LogUtil.debug(LOGGER, "getSimilarHouse par:{}", JsonEntityTransform.Object2Json(landRequest));
        }
        //参数校验
        if(Check.NuNStr(landRequest.getLandlordUid())){
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto;
        }
        String jsonRst = null;
        try {
            //获取搜索结果
            jsonRst =searchService.getSimilarHouse(defaultIconSize,JsonEntityTransform.Object2Json(landRequest));
            dto = JsonEntityTransform.json2DataTransferObject(jsonRst);
        } catch (Exception e) {
            dto = new DataTransferObject();
            LogUtil.error(LOGGER, "getSimilarHouse par :{} e:{}", JsonEntityTransform.Object2Json(landRequest), e);
            return dto;
        }
        LogUtil.debug(LOGGER,"getSimilarHouse result {}.",jsonRst);
        return dto;
    }
    
}
