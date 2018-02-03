package com.ziroom.minsu.services.search.service;

import java.text.ParseException;
import java.util.*;

import javax.annotation.Resource;

import com.ziroom.minsu.valenum.common.YesOrNoEnum;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.services.basedata.dao.BasedateDbInfoDao;
import com.ziroom.minsu.services.common.constant.SearchConstant;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.dao.CustomerDbDao;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.dto.HouseListByBrandSnListRequest;
import com.ziroom.minsu.services.search.dto.HouseListRequset;
import com.ziroom.minsu.services.search.dto.HouseSearchOneRequest;
import com.ziroom.minsu.services.search.dto.HouseSearchRequest;
import com.ziroom.minsu.services.search.dto.LandHouseRequest;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.services.search.entity.QueryRequest;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.search.entity.SpatialSolrParEntity;
import com.ziroom.minsu.services.search.vo.StaticResourceVo;
import com.ziroom.minsu.services.solr.common.IKAnalyzerService;
import com.ziroom.minsu.services.solr.common.QueryService;
import com.ziroom.minsu.services.solr.constant.SolrConstant;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.services.solr.query.par.Range;
import com.ziroom.minsu.services.solr.utils.ExtWorldUtils;
import com.ziroom.minsu.valenum.house.QualityGradeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.search.SortTypeEnum;

/**
 * <p>搜索功能接口</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/27.
 * @version 1.0
 * @since 1.0
 */
@Service(value = "search.searchServiceImpl")
public class SearchServiceImpl {


    /**
     * log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);


    @Value("#{'${static_base_url}'.trim()}")
    private String staticBaseUrl;


    @Resource(name = "search.queryService")
    private QueryService queryService;

    @Resource(name = "search.ikAnalyzerService")
    private IKAnalyzerService ikAnalyzerService;


    @Resource(name = "search.customerDbDao")
    private CustomerDbDao customerDbDao;

    @Resource(name = "search.basedateDbInfoDao")
    private BasedateDbInfoDao basedateDbInfoDao;

    @Autowired
    private RedisOperations redisOperations;

    /**
     * 新上架的房源大小
     */
    private static final Integer new_house_size = 10;

    //更新缓存数据的时间（小时）
    private int updateCache = 4;

    //字典緩存
    private Map<String, Object> dicCacheMap = new HashMap<>();
    //字典緩存更新時間
    private Map<String, Date> dicCacheFreshDateMap = new HashMap<>();


    /**
     * 获取ik分词信息
     *
     * @param txt
     * @return
     */
    public List<String> getChangzuIkList(String txt) throws Exception {
        Set<String> ikSet = new HashSet<>();
        List<String> ikOrg = ikAnalyzerService.ikAnalysis(txt, false);
        if (!Check.NuNCollection(ikOrg)) {
            for (String ik : ikOrg) {
                if (ExtWorldUtils.checkChangzuExt(ik)) {
                    ikSet.add(ik);
                }
            }
        }
        return new ArrayList<>(ikSet);
    }

    /**
     * 获取ik分词信息
     *
     * @param txt
     * @return
     */
    public List<String> getIkList(String txt) throws Exception {
        Set<String> ikSet = new HashSet<>();
        List<String> ikOrg = ikAnalyzerService.ikAnalysis(txt, false);
        if (!Check.NuNCollection(ikOrg)) {
            for (String ik : ikOrg) {
                if (ExtWorldUtils.checkMinsuExt(ik)) {
                    ikSet.add(ik);
                }
            }
        }
        return new ArrayList<>(ikSet);
    }

    /**
     * 获取补全信息
     *
     * @param pre
     * @return
     */
    public Set<String> getComplateTermsCommunityName(String pre) {
        //获取房屋名称的补全信息
        String termField = "communityName";
        return queryService.autoCompleteWithTerms(SolrCore.m_spell, termField, pre, 10);
    }

    /**
     * 获取楼盘信息
     *
     * @param pageRequest
     * @return
     * @author afi
     */
    public QueryResult getSpellListInfo(PageRequest pageRequest) {
        QueryRequest request = new QueryRequest();
        request.setPageIndex(pageRequest.getPage());
        request.setPageSize(pageRequest.getLimit());
        QueryResult result = queryService.query(SolrCore.m_spell, request);
        return result;
    }


    /**
     * 获取房源的列表信息
     *
     * @param houseListRequset
     * @return
     * @author afi
     */
    public QueryResult getHouseListByList(HouseListRequset houseListRequset) {
        QueryRequest request = new QueryRequest();
        request.setPageSize(houseListRequset.getHouseList().size());
        Map<String, Object> filterQueries = new HashMap<>();
        filterQueries.put("id", ValueUtil.transList2StrInSolr(houseListRequset.transQuertList()));

        request.setFilterQueries(filterQueries);
        Map<String, Object> weights = new HashMap<>();
        weights.put("weights", "desc");
        request.setSorts(weights);
        Map<String, Object> par = new HashMap<>();
        par.put("picSize", houseListRequset.getPicSize());
        par.put("iconType", houseListRequset.getIconPicType());
        par.put("iconBaseUrl", staticBaseUrl);
        if (!Check.NuNObj(houseListRequset.getStartTime()) && !Check.NuNObj(houseListRequset.getEndTime())) {
            Set<String> daySet = DateSplitUtil.getDateSplitSet(houseListRequset.getStartTime(), houseListRequset.getEndTime());
            par.put("daySet", daySet);
        }
        String uid = houseListRequset.getUid();
        if (!Check.NuNStr(uid)) {
            //处理当前的用户收藏信息
            Map<String, String> collectMap = this.getCollectMapByUid(uid);
            par.put("collectMap", collectMap);
        }
        initDicParam(par);
        QueryResult result = queryService.query(SolrCore.m_house_info, par, request);
        if (result.getTotal() > 0) {
            Map<String, HouseInfoEntity> houseMap = new HashMap<>();
            List<HouseInfoEntity> list = (List<HouseInfoEntity>) result.getValue();
            if (!Check.NuNCollection(list)) {
                for (HouseInfoEntity entity : list) {
                    houseMap.put(entity.getFid() + "_" + entity.getRentWay(), entity);
                }
            }
            List rst = new ArrayList();
            if (!Check.NuNCollection(houseListRequset.getHouseList())) {
                for (HouseSearchRequest ele : houseListRequset.getHouseList()) {
                    if (houseMap.containsKey(ele.getFid() + "_" + ele.getRentWay())) {
                        rst.add(houseMap.get(ele.getFid() + "_" + ele.getRentWay()));
                    }
                }
            }
            result.setValue(rst);
        }
        return result;
    }

    /**
     * 获取当前用户下的收藏列表
     *
     * @param uid
     * @return
     * @author afi
     */
    private Map getCollectMapByUid(String uid) {
        if (Check.NuNStr(uid)) {
            return null;
        }
        String key = RedisKeyConst.getCollectKey(uid);
        String json = null;
        try {
            json = redisOperations.get(key);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}", e);
        }

        //判断缓存是否存在
        if (!StringUtils.isBlank(json)) {
            return JsonEntityTransform.json2Map(json);
        }
        List<String> list = customerDbDao.getCustomerCollect(uid);
        if (Check.NuNCollection(list)) {
            return null;
        }
        Map<String, String> collectMap = new HashMap<>();
        for (String fid : list) {
            collectMap.put(fid, fid);
        }
        try {
            redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, JsonEntityTransform.Object2Json(collectMap));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}", e);
        }
        return collectMap;
    }


    /**
     * 获取唯一房源信息
     *
     * @param searchOneRequest
     * @return
     * @author zl
     * @created 2017年6月7日 下午1:29:39
     */
    public QueryResult getOneHouseInfo(HouseSearchOneRequest searchOneRequest) {
        if (Check.NuNObjs(searchOneRequest, searchOneRequest.getFid(), searchOneRequest.getRentWay())) {
            return null;
        }
        QueryRequest request = new QueryRequest();
        request.setPageSize(1);
        Map<String, Object> filterQueries = new HashMap<>();
        filterQueries.put("id", searchOneRequest.getFid() + "_" + searchOneRequest.getRentWay());

        request.setFilterQueries(filterQueries);
        Map<String, Object> weights = new HashMap<>();
        weights.put("weights", "desc");
        request.setSorts(weights);
        Map<String, Object> par = new HashMap<>();
        par.put("picSize", searchOneRequest.getPicSize());
        par.put("iconType", null);
        par.put("iconBaseUrl", staticBaseUrl);
        if (!Check.NuNStr(searchOneRequest.getStartTime())) {
            par.put("startTime", searchOneRequest.getStartTime());
        }
        if (!Check.NuNStr(searchOneRequest.getEndTime())) {
            par.put("endTime", searchOneRequest.getEndTime());
        }
        if (!Check.NuNObj(searchOneRequest.getSearchSourceTypeEnum())) {
            par.put("searchSourceType", searchOneRequest.getSearchSourceTypeEnum().getCode());
        }
        initDicParam(par);
        QueryResult result = queryService.query(SolrCore.m_house_info, par, request);
        return result;
    }


    /**
     * 获取最新房源
     *
     * @param picSize
     * @param iconType
     * @return
     * @author afi
     */
    public QueryResult getNewHouseLst(String picSize, Integer iconType) {
        QueryRequest request = new QueryRequest();
        Map<String, Object> filterQueries = new HashMap<>();
        filterQueries.put("houseQualityGrade", QualityGradeEnum.GRADE_A.getCode());
        request.setFilterQueries(filterQueries);
        Map<String, Object> passDate = new HashMap<>();
        passDate.put("passDate", "desc");
        request.setSorts(passDate);
        Map<String, Object> par = new HashMap<>();
        par.put("picSize", picSize);
        par.put("iconType", iconType);
        par.put("iconBaseUrl", staticBaseUrl);

        Set<String> landlordUidSet = new HashSet<>();
        List<HouseInfoEntity> resultHouseList = new ArrayList<>();

        int page = 1;
        int limit = 50;//一次性查50个挑选
        request.setPageSize(limit);
        request.setPageIndex(page);

        QueryResult result = getNewHouseList(par, request);


        //给房客开通的城市
        List<ConfCityEntity> openCityTenant = getOpenCityTenant();
        Set<String> openCityTenantCode = new HashSet<>();
        if (!Check.NuNCollection(openCityTenant)) {
            for (ConfCityEntity confCityEntity : openCityTenant) {
                openCityTenantCode.add(confCityEntity.getCode());
            }
        }

        while (!Check.NuNObj(result) && !Check.NuNObj(result.getValue()) && resultHouseList.size() < new_house_size && page < 6) {
            List<HouseInfoEntity> houseList = (List<HouseInfoEntity>) result.getValue();

            if (Check.NuNCollection(houseList)) {
                break;
            }

            for (HouseInfoEntity houseInfoEntity : houseList) {

                if (!openCityTenantCode.contains(houseInfoEntity.getCityCode())) {//未给房客开通
                    LogUtil.debug(LOGGER, "[最新房源]房源所在城市未给房客开通，houseFid：{},rentWay：{}", houseInfoEntity.getFid(), houseInfoEntity.getRentWay());
                    continue;
                }

                if (resultHouseList.size() > new_house_size) {
                    break;
                } else if (!landlordUidSet.contains(houseInfoEntity.getLandlordUid())) {
                    landlordUidSet.add(houseInfoEntity.getLandlordUid());
                    resultHouseList.add(houseInfoEntity);
                }
            }
            page++;
            if (resultHouseList.size() < new_house_size) {
                request.setPageIndex(page);
                result = getNewHouseList(par, request);
            }

        }

        result.setValue(resultHouseList);

        return result;

    }

    /**
     * 给房客开通的城市缓存
     *
     * @return
     * @author zl
     */
    private List<ConfCityEntity> getOpenCityTenant() {
        String key = "KEY_OPEN_CITY_TENANT";
        key = RedisKeyConst.getCityConfKey(key);
        String json = null;
        try {
            json = redisOperations.get(key);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}", e);
        }

        //判断缓存是否存在
        if (!StringUtils.isBlank(json)) {
            return JsonEntityTransform.json2List(json, ConfCityEntity.class);
        }

        List<ConfCityEntity> openCityTenant = basedateDbInfoDao.getOpenCityTenant();
        if (!Check.NuNCollection(openCityTenant)) {
            try {
                redisOperations.setex(key, RedisKeyConst.CONF_CACHE_TIME, JsonEntityTransform.Object2Json(openCityTenant));
            } catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}", e);
            }
        }

        return openCityTenant;
    }


    //查询最新房源列表
    private QueryResult getNewHouseList(Map<String, Object> par, QueryRequest request) {
        if (Check.NuNObj(request)) {
            return null;
        }
        initDicParam(par);
        return queryService.query(SolrCore.m_house_info, par, request);
    }


    /**
     * 获取房东的房源列表
     *
     * @param picSize
     * @param landRequest
     * @return
     * @author afi
     */
    public QueryResult getLandHouseList(String picSize, Integer iconType, LandHouseRequest landRequest) {
        QueryRequest request = new QueryRequest();
        request.setPageIndex(landRequest.getPage());
        request.setPageSize(landRequest.getLimit());

        Map<String, Object> filterQueries = new HashMap<>();

        Map<String, Object> par = new HashMap<>();
        par.put("picSize", picSize);
        par.put("iconType", iconType);
        par.put("iconBaseUrl", staticBaseUrl);
        if (!Check.NuNObj(landRequest.getVersionCode())) {
            par.put("versionCode", landRequest.getVersionCode());
        }
        initDicParam(par);
        Map<String, Object> weights = new HashMap<>();
        weights.put("weights", "desc");
        request.setSorts(weights);


        if (!Check.NuNObj(landRequest.getIsTop50Online())) {
            filterQueries.put("isTop50Online", landRequest.getIsTop50Online());

            if (!Check.NuNObj(landRequest.getRentWay()) && landRequest.getRentWay() == RentWayEnum.ROOM.getCode()) {

                QueryRequest preRequest = new QueryRequest();
                preRequest.setPageIndex(1);
                preRequest.setPageSize(1);

                Map<String, Object> preFilterQueries = new HashMap<>();
                preFilterQueries.put("roomId", landRequest.getFid());
                preFilterQueries.put("landlordUid", landRequest.getLandlordUid());
                preRequest.setFilterQueries(preFilterQueries);
                preRequest.setSorts(weights);
                QueryResult preResult = queryService.query(SolrCore.m_house_info, par, preRequest);

                if (!Check.NuNObj(preResult.getValue())) {
                    List<HouseInfoEntity> preList = (List<HouseInfoEntity>) preResult.getValue();
                    if (!Check.NuNCollection(preList) && !Check.NuNObj(preList.get(0))) {
                        filterQueries.put("houseId", preList.get(0).getHouseFid());
                    }

                }
            }

            if (!Check.NuNObj(landRequest.getRentWay())) {
                filterQueries.put("rentWay", landRequest.getRentWay());
            }

        }


        filterQueries.put("landlordUid", landRequest.getLandlordUid());

        if (!Check.NuNStr(landRequest.getFid()) && !Check.NuNObj(landRequest.getRentWay())) {
            filterQueries.put("-id", landRequest.getFid() + "_" + landRequest.getRentWay());
        }

        //设置区间的过滤条件
        Map<String, Map<String, Object>> rangeFilterQueries = new HashMap<>();

        if (!Check.NuNObj(landRequest.getPersonCount())) {
            //入住人数的限制 搜做大于当前人数限制的房源
            rangeFilterQueries.put("personCount", Range.getRangeMap(landRequest.getPersonCount(), "*", true, false));
        }

        if (!Check.NuNObjs(landRequest.getStartTime(), landRequest.getEndTime())) {
            filterQueries.put("startTime", ValueUtil.getTrimStrValue(landRequest.getStartTime()));
            filterQueries.put("endTime", ValueUtil.getTrimStrValue(landRequest.getEndTime()));

            try {
                Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(landRequest.getStartTime()), "yyyy-MM-dd");
                Date endTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(landRequest.getEndTime()), "yyyy-MM-dd");

                List<Date> days = DateSplitUtil.dateSplit(startTime, endTime);
                int minDay = days.size();
                rangeFilterQueries.put("minDay", Range.getRangeMap(0, minDay, true, true));
            } catch (ParseException e) {
                LogUtil.error(LOGGER, "getLandHouseList 日期转化异常，param:{},e:{}", JsonEntityTransform.Object2Json(landRequest), e);
            }
        }


        if (!Check.NuNStr(landRequest.getEndTime())) {
            try {
                Date endTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(landRequest.getEndTime()), "yyyy-MM-dd");
                rangeFilterQueries.put("houseEndTime", Range.getRangeMap(endTime.getTime(), null, true, false));
            } catch (Exception e) {
                LogUtil.error(LOGGER, " getLandHouseList 截止日期转化异常，param:{},e:{}", JsonEntityTransform.Object2Json(landRequest), e);
            }
        }

        request.setFilterQueries(filterQueries);
        request.setRangeFilterQueries(rangeFilterQueries);

        QueryResult result = queryService.query(SolrCore.m_house_info, par, request);
        return result;
    }

        /**
     * 搜索房源信息
     *
     * @param houseInfoRequest
     * @return
     * @author afi
     */
    public QueryResult getHouseListInfo(String picSize, HouseInfoRequest houseInfoRequest, String uid) {
        Long start = System.currentTimeMillis();
        QueryRequest request = new QueryRequest();
        request.setPageIndex(houseInfoRequest.getPage());
        request.setPageSize(houseInfoRequest.getLimit());

        request.setInCityName(houseInfoRequest.getInCityName());
        request.setIsTargetCityLocal(houseInfoRequest.getIsTargetCityLocal());
        if (!Check.NuNObj(houseInfoRequest.getIsRecommend())) {
            request.setIsRecommend(houseInfoRequest.getIsRecommend());
        }
        if (!Check.NuNObj(houseInfoRequest.getSearchSourceTypeEnum())) {
            request.setSearchSourceTypeEnum(houseInfoRequest.getSearchSourceTypeEnum());
        }
        Map<String, Object> filterQueries = new HashMap<>();

        //设置区间的过滤条件
        Map<String, Map<String, Object>> rangeFilterQueries = new HashMap<>();

        if (!Check.NuNStr(houseInfoRequest.getQ())) {
            //拼音的转化
            // request.setQ(SpellUtils.trans2Words(houseInfoRequest.getQ(), 5, ""));
            String q = com.ziroom.minsu.services.common.utils.StringUtils.Parse(houseInfoRequest.getQ());
            request.setQ(q);
        }

        if (!Check.NuNStr(houseInfoRequest.getCityCode())) {
            filterQueries.put("cityCode", houseInfoRequest.getCityCode());
        }

        if (!Check.NuNStr(houseInfoRequest.getAreaCode())) {
            filterQueries.put("areaCode", houseInfoRequest.getAreaCode());
        }

        // 单个出租方式
        if (!Check.NuNObj(houseInfoRequest.getRentWay())) {
            filterQueries.put("rentWay", houseInfoRequest.getRentWay());
        } else if (!Check.NuNStr(houseInfoRequest.getMultiRentWay())) {
            // 多种出租方式
            Set<String> multiRentWay = new HashSet<>(Arrays.asList(houseInfoRequest.getMultiRentWay().split(",")));
            filterQueries.put("multiRentWay", multiRentWay);
        }

        if (!Check.NuNObj(houseInfoRequest.getRoomType())) {
            filterQueries.put("roomType", houseInfoRequest.getRoomType());
        }

        if (!Check.NuNObj(houseInfoRequest.getOrderType())) {
            filterQueries.put("orderType", houseInfoRequest.getOrderType());
        }

        if (!Check.NuNObj(houseInfoRequest.getHotReginBusiness())) {
            filterQueries.put("hotRegin", houseInfoRequest.getHotReginBusiness());
        }

        if (!Check.NuNObj(houseInfoRequest.getHotReginScenic())) {
            filterQueries.put("hotRegin", houseInfoRequest.getHotReginScenic());
        }

        if (!Check.NuNObj(houseInfoRequest.getHotSearchRegion())) {
            filterQueries.put("hotRegin", houseInfoRequest.getHotSearchRegion());
        }

        if (!Check.NuNObj(houseInfoRequest.getSubway())) {
            filterQueries.put("subway", houseInfoRequest.getSubway());
        }
        if (!Check.NuNObj(houseInfoRequest.getLineFid())) {
            filterQueries.put("lineFidSet", houseInfoRequest.getLineFid());
        }

        if (!Check.NuNObj(houseInfoRequest.getRoomCount()) && houseInfoRequest.getRoomCount() > 0) {
            filterQueries.put("roomCount", houseInfoRequest.getRoomCount());
        }

        if (!Check.NuNObjs(houseInfoRequest.getStartTime(), houseInfoRequest.getEndTime())) {
            filterQueries.put("startTime", ValueUtil.getTrimStrValue(houseInfoRequest.getStartTime()));
            filterQueries.put("endTime", ValueUtil.getTrimStrValue(houseInfoRequest.getEndTime()));
        }

        if (!Check.NuNStr(houseInfoRequest.getHouseType())) {
            String[] types = houseInfoRequest.getHouseType().split(",");
            if (types.length > 0) {
                Set<Integer> houseType = new HashSet<>();
                for (int i = 0; i < types.length; i++) {
                    houseType.add(Integer.valueOf(types[i]));
                }
                filterQueries.put("houseType", houseType);
            }
        }

        if (!Check.NuNObj(houseInfoRequest.getBedType())) {
            filterQueries.put("bedList", houseInfoRequest.getBedType() + ",*");
        }

        if (!Check.NuNObj(houseInfoRequest.getLongTermDiscount()) && houseInfoRequest.getLongTermDiscount().length() > 0) {
            String[] types = houseInfoRequest.getLongTermDiscount().split(",");
            if (types.length > 0) {
                Set<String> set = new HashSet<>();
                for (int i = 0; i < types.length; i++) {
                    set.add(types[i]);
                }
                filterQueries.put("longTermLeaseDiscount", set);
            }
        }


        if (!Check.NuNObj(houseInfoRequest.getIsTop50Online())) {
            filterQueries.put("isTop50Online", houseInfoRequest.getIsTop50Online());
        }

        if (!Check.NuNObj(houseInfoRequest.getIsTop50ListShow())) {
            filterQueries.put("isTop50ListShow", houseInfoRequest.getIsTop50ListShow());
        }

        /**
         * 2017-08-24
         * 原：客户端不否定与房东同住时传0，却过滤了与房东同住的房源。
         * 现改为：仅当明确与房东同住时，搜索与房东同住的房源；其他情况不进行过滤
         */
        Integer isLandTogether = houseInfoRequest.getIsLandTogether();
        if (!Check.NuNObj(isLandTogether) && YesOrNoEnum.YES.getCode() == isLandTogether) {
            filterQueries.put("isLandTogether", houseInfoRequest.getIsLandTogether());
        }

        if (!Check.NuNObj(houseInfoRequest.getHouseSn())) {
            filterQueries.put("houseSn", houseInfoRequest.getHouseSn());
        }

        if (!Check.NuNObj(houseInfoRequest.getLandlordName())) {
            filterQueries.put("landlordName", houseInfoRequest.getLandlordName());
        }

        if (!Check.NuNObj(houseInfoRequest.getHouseName())) {
            filterQueries.put("houseName", houseInfoRequest.getHouseName());
        }

        if (!Check.NuNStr(houseInfoRequest.getJiaxinDiscount())) {
            String[] types = houseInfoRequest.getJiaxinDiscount().split(",");
            Set<String> set = new HashSet<>();
            for (int i = 0; i < types.length; i++) {
                set.add(types[i]);
            }
            filterQueries.put("flexiblePrice", set);

            if (types.length == 1 && types[0].equals(ProductRulesEnum020.ProductRulesEnum020001.getValue())) {//今夜特价单独处理
                rangeFilterQueries.put("tonightDisDeadlineDate", Range.getRangeMap(new Date().getTime(), null, true, false));

                if (!Check.NuNObj(houseInfoRequest.getVersionCode()) && houseInfoRequest.getVersionCode() < 100015) {
                    rangeFilterQueries.put("tonightDisOpenDate", Range.getRangeMap(null, new Date().getTime(), false, true));
                }

            }
        }


        request.setFilterQueries(filterQueries);


        if (!Check.NuNObj(houseInfoRequest.getPriceStart()) || !Check.NuNObj(houseInfoRequest.getPriceEnd())) {
            rangeFilterQueries.put("price", Range.getRangeMap(houseInfoRequest.getPriceStart(), houseInfoRequest.getPriceEnd(), true, true));
        }
        if (!Check.NuNObj(houseInfoRequest.getPersonCount())) {
            //入住人数的限制 搜做大于当前人数限制的房源
            rangeFilterQueries.put("personCount", Range.getRangeMap(houseInfoRequest.getPersonCount(), "*", true, false));
        }
        //设置逻辑的最小天数 
        if (!Check.NuNStr(houseInfoRequest.getStartTime()) && !Check.NuNStr(houseInfoRequest.getEndTime())) {
            //不搜今夜特价才做此限制 2018-01-04 zhangyl2
            //背景：搜今夜特价时，开始结束时间默认是今明，间隔是1，则搜不到minDay > 1的房源
            Set<String> set = (Set<String>) filterQueries.get("flexiblePrice");
            if(Check.NuNCollection(set) || !set.contains(ProductRulesEnum020.ProductRulesEnum020001.getValue())){
                try {
                    Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfoRequest.getStartTime()), "yyyy-MM-dd");
                    Date endTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfoRequest.getEndTime()), "yyyy-MM-dd");

                    List<Date> days = DateSplitUtil.dateSplit(startTime, endTime);
                    int minDay = days.size();
                    rangeFilterQueries.put("minDay", Range.getRangeMap(0, minDay, true, true));

                } catch (Exception e) {
                    LogUtil.error(LOGGER, "最小入住天数 时间转化异常 ,param={},e:{}", JsonEntityTransform.Object2Json(houseInfoRequest), e);
                }
            }
        }

        if (!Check.NuNStr(houseInfoRequest.getEndTime())) {
            try {
                Date endTime = DateUtil.parseDate(houseInfoRequest.getEndTime().replace(" ", ""), "yyyy-MM-dd");
                rangeFilterQueries.put("houseEndTime", Range.getRangeMap(endTime.getTime(), null, true, false));
            } catch (Exception e) {
                LogUtil.error(LOGGER, "搜索截止日期转化异常，e:{}", e);
            }
        }
        request.setRangeFilterQueries(rangeFilterQueries);
        if (Check.NuNObj(houseInfoRequest.getSorts())) {
            SortTypeEnum sortTypeEnum = SortTypeEnum.getByCode(houseInfoRequest.getSortType());
            houseInfoRequest.setSorts(sortTypeEnum.getSortMap());
            request.setSortType(sortTypeEnum.getCode());
        } else {
            request.setSortType(houseInfoRequest.getSortType());
        }

        Map<String, Object> sortMap = houseInfoRequest.getSorts();
        //如果按照距离排序
        if (sortMap.containsKey("dist")) {
            //校验传过来的位置参数
            if (!Check.NuNStr(houseInfoRequest.getLoc())) {
                SpatialSolrParEntity solrParEntity = new SpatialSolrParEntity();
                solrParEntity.setSfield("loc");
                solrParEntity.setLocation(houseInfoRequest.getLoc());
                solrParEntity.setOrderRole(ValueUtil.getStrValue(sortMap.get("dist")));
                request.setSolrParEntity(solrParEntity);
            }
        } else {
            request.setSorts(houseInfoRequest.getSorts());
        }
        //处理距离的返回信息
        if (!Check.NuNStr(houseInfoRequest.getLoc())) {
            request.setReturnFields("*,dist:geodist(loc," + houseInfoRequest.getLoc() + ")");
        }
        Map<String, Object> par = new HashMap<>();
        par.put("picSize", picSize);
        par.put("iconType", houseInfoRequest.getIconType());
        par.put("iconBaseUrl", staticBaseUrl);
        if (!Check.NuNObj(houseInfoRequest.getVersionCode())) {
            par.put("versionCode", houseInfoRequest.getVersionCode());
        }
        if (!Check.NuNObj(houseInfoRequest.getSearchSourceTypeEnum())) {
            par.put("searchSourceType", houseInfoRequest.getSearchSourceTypeEnum().getCode());
        }

        if (!Check.NuNStr(houseInfoRequest.getStartTime())) {
            //开始时间非空
            par.put("startTime", houseInfoRequest.getStartTime());
        }
        if (!Check.NuNStr(houseInfoRequest.getEndTime())) {
            par.put("endTime", houseInfoRequest.getEndTime());
        }

        Long t1 = System.currentTimeMillis();

        if (!Check.NuNStr(uid)) {
            //处理当前的用户收藏信息
            Map<String, String> collectMap = this.getCollectMapByUid(uid);
            par.put("collectMap", collectMap);
        }

        Long t2 = System.currentTimeMillis();
        initDicParam(par);
        Long t3 = System.currentTimeMillis();

        QueryResult result = queryService.query(SolrCore.m_house_info, par, request);

        Long end = System.currentTimeMillis();

        if (end - start > 70) {
            LogUtil.info(LOGGER, "[累计时间超过70ms] 参数解析时间:{} 调用收藏的时间：{} 初始化字典参数的时间：{} 搜索时间：{} 累计时间：{}", t1 - start, t2 - t1, t3 - t2, end - t3, end - start);
        }

        return result;
    }

    /**
     * 根据品牌编码列表查询房源
     *
     * @param brandRequest
     * @return
     * @author zl
     * @created 2017年4月1日 下午3:34:45
     */
    public QueryResult searchByBrandSnList(HouseListByBrandSnListRequest brandRequest) {

        QueryRequest request = new QueryRequest();
        request.setPageSize(brandRequest.getLimit());
        request.setPageIndex(brandRequest.getPage());
        Map<String, Object> filterQueries = new HashMap<>();
        filterQueries.put("brandSn", ValueUtil.transList2StrInSolr(brandRequest.getBrandSnList()));

        request.setFilterQueries(filterQueries);
        Map<String, Object> weights = new HashMap<>();
        weights.put("weights", "desc");
        request.setSorts(weights);
        Map<String, Object> par = new HashMap<>();
        par.put("picSize", brandRequest.getPicSize());
        par.put("iconType", brandRequest.getIconPicType());
        par.put("iconBaseUrl", staticBaseUrl);

        String uid = brandRequest.getUid();
        if (!Check.NuNStr(uid)) {
            //处理当前的用户收藏信息
            Map<String, String> collectMap = this.getCollectMapByUid(uid);
            par.put("collectMap", collectMap);
        }
        initDicParam(par);
        QueryResult result = queryService.query(SolrCore.m_house_info, par, request);
        return result;
    }


    /**
     * 初始化配置参数
     *
     * @param param
     * @author zl
     * @created 2016年12月13日 上午11:35:48
     */
    private void initDicParam(Map<String, Object> param) {
        if (Check.NuNMap(param)) {
            return;
        }
        try {
            //满七天配置天数

            Integer longTerm7DaysLimit = getLongTerm7DaysLimit();
            if (!Check.NuNObj(longTerm7DaysLimit) && longTerm7DaysLimit > 0) {
                param.put(ProductRulesEnum0019.ProductRulesEnum0019001.getValue(), longTerm7DaysLimit);
            }

            //满30天配置天数
            Integer longTerm30DaysLimit = getLongTerm30DaysLimit();
            if (!Check.NuNObj(longTerm30DaysLimit) && longTerm30DaysLimit > 0) {
                param.put(ProductRulesEnum0019.ProductRulesEnum0019002.getValue(), longTerm30DaysLimit);
            }

            //间隙日期的最大配置天数
            Integer jianxiDaysLimit = getJianxiDaysLimit();
            if (!Check.NuNObj(jianxiDaysLimit) && jianxiDaysLimit > 0) {
                param.put(ProductRulesEnum0019.ProductRulesEnum0019003.getValue(), jianxiDaysLimit);
            }

            //今日特价生效时间文案
            String tonightDiscountStartTimeTips = getStaticResourceContentByResCode(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_STARTTIME_TIPS);
            if (!Check.NuNStr(tonightDiscountStartTimeTips)) {
                param.put(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_STARTTIME_TIPS, tonightDiscountStartTimeTips);
            }

            //今日特价截止时间文案
            String tonightDiscountEndTimeTips = getStaticResourceContentByResCode(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_ENDTIME_TIPS);
            if (!Check.NuNStr(tonightDiscountEndTimeTips)) {
                param.put(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_ENDTIME_TIPS, tonightDiscountEndTimeTips);
            }

            //今日特价生效时间列表文案
            String tonightDiscountStartTimeListTips = getStaticResourceContentByResCode(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_STARTTIME_LIST_TIPS);
            if (!Check.NuNStr(tonightDiscountStartTimeListTips)) {
                param.put(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_STARTTIME_LIST_TIPS, tonightDiscountStartTimeListTips);
            }

            //今日特价截止时间列表文案
            String tonightDiscountEndTimeListTips = getStaticResourceContentByResCode(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_ENDTIME_LIST_TIPS);
            if (!Check.NuNStr(tonightDiscountEndTimeListTips)) {
                param.put(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_ENDTIME_LIST_TIPS, tonightDiscountEndTimeListTips);
            }


        } catch (Exception e) {
            LogUtil.error(LOGGER, "查询配置参数异常,e:{}", e);
        }

    }

    /**
     * 查询静态资源内容
     *
     * @param resourceByResCode
     * @return
     * @author zl
     * @created 2017年5月16日 下午4:29:56
     */
    private String getStaticResourceContentByResCode(String resourceByResCode) {

        if (Check.NuNStr(resourceByResCode)) {
            return null;
        }

        Date date = dicCacheFreshDateMap.get(resourceByResCode);

        String value = null;
        if (!Check.NuNObj(date) && new Date().getTime() - date.getTime() < updateCache * 60 * 60 * 1000) {
            value = (String) dicCacheMap.get(resourceByResCode);
        }
        if (Check.NuNObj(value)) {
            StaticResourceVo resource = basedateDbInfoDao.getStaticResourceByResCode(resourceByResCode);
            if (!Check.NuNObj(resource) && !Check.NuNStr(resource.getResContent())) {
                value = resource.getResContent();
                dicCacheMap.put(resourceByResCode, value);
                dicCacheFreshDateMap.put(resourceByResCode, new Date());
            }

        }

        return value;


    }


    /**
     * 间隙日期的最大配置天数
     *
     * @return
     * @author zl
     * @created 2017年3月2日 下午8:01:03
     */
    private Integer getJianxiDaysLimit() {
        String key = "JianxiDaysLimit";
        Date date = dicCacheFreshDateMap.get(key);

        Integer jianxiDaysLimit = null;
        if (!Check.NuNObj(date) && new Date().getTime() - date.getTime() < updateCache * 60 * 60 * 1000) {
            jianxiDaysLimit = (Integer) dicCacheMap.get(key);
        }
        if (Check.NuNObj(jianxiDaysLimit)) {
            List<DicItemEntity> jianxiDays = basedateDbInfoDao
                    .getDicItemListByCodeAndTemplate(ProductRulesEnum0019.ProductRulesEnum0019003.getValue(), null);

            if (!Check.NuNCollection(jianxiDays) && !Check.NuNObj(jianxiDays.get(0))) {
                jianxiDaysLimit = ValueUtil.getintValue(jianxiDays.get(0).getItemValue());
            }
            if (!Check.NuNObj(jianxiDaysLimit)) {
                dicCacheMap.put(key, jianxiDaysLimit);
                dicCacheFreshDateMap.put(key, new Date());
            }

        }
        if (Check.NuNObj(jianxiDaysLimit)) {
            jianxiDaysLimit = ProductRulesEnum0019.ProductRulesEnum0019003.getDayNum();
        }

        return jianxiDaysLimit;
    }


    /**
     * 获取满30天配置天数
     *
     * @return
     * @author zl
     * @created 2017年3月2日 下午8:01:03
     */
    private Integer getLongTerm30DaysLimit() {
        String key = "LongTerm30DaysLimit";
        Date date = dicCacheFreshDateMap.get(key);

        Integer longTerm30DaysLimit = null;
        if (!Check.NuNObj(date) && new Date().getTime() - date.getTime() < updateCache * 60 * 60 * 1000) {
            longTerm30DaysLimit = (Integer) dicCacheMap.get(key);
        }
        if (Check.NuNObj(longTerm30DaysLimit)) {
            List<DicItemEntity> longTerm30Days = basedateDbInfoDao
                    .getDicItemListByCodeAndTemplate(ProductRulesEnum0019.ProductRulesEnum0019002.getValue(), null);

            if (!Check.NuNCollection(longTerm30Days) && !Check.NuNObj(longTerm30Days.get(0))) {
                longTerm30DaysLimit = ValueUtil.getintValue(longTerm30Days.get(0).getItemValue());
            }
            if (!Check.NuNObj(longTerm30DaysLimit)) {
                dicCacheMap.put(key, longTerm30DaysLimit);
                dicCacheFreshDateMap.put(key, new Date());
            }

        }
        if (Check.NuNObj(longTerm30DaysLimit)) {
            longTerm30DaysLimit = ProductRulesEnum0019.ProductRulesEnum0019002.getDayNum();
        }

        return longTerm30DaysLimit;
    }

    /**
     * 获取满七天配置天数
     *
     * @return
     * @author zl
     * @created 2017年3月2日 下午8:01:03
     */
    private Integer getLongTerm7DaysLimit() {
        String key = "LongTerm7DaysLimit";
        Date date = dicCacheFreshDateMap.get(key);

        Integer longTerm7DaysLimit = null;
        if (!Check.NuNObj(date) && new Date().getTime() - date.getTime() < updateCache * 60 * 60 * 1000) {
            longTerm7DaysLimit = (Integer) dicCacheMap.get(key);
        }
        if (Check.NuNObj(longTerm7DaysLimit)) {
            List<DicItemEntity> longTerm7Days = basedateDbInfoDao
                    .getDicItemListByCodeAndTemplate(ProductRulesEnum0019.ProductRulesEnum0019001.getValue(), null);

            if (!Check.NuNCollection(longTerm7Days) && !Check.NuNObj(longTerm7Days.get(0))) {
                longTerm7DaysLimit = ValueUtil.getintValue(longTerm7Days.get(0).getItemValue());
            }
            if (!Check.NuNObj(longTerm7DaysLimit)) {
                dicCacheMap.put(key, longTerm7DaysLimit);
                dicCacheFreshDateMap.put(key, new Date());
            }

        }
        if (Check.NuNObj(longTerm7DaysLimit)) {
            longTerm7DaysLimit = ProductRulesEnum0019.ProductRulesEnum0019001.getDayNum();
        }

        return longTerm7DaysLimit;
    }


    /**
     * 查询类似房源类别(只有上架房源有类似房源逻辑)
     *
     * @param picSize
     * @param landRequest
     * @return
     */
    public QueryResult getSimilarHouse(String picSize, LandHouseRequest landRequest) {
        QueryRequest request = new QueryRequest();
        request.setPageIndex(landRequest.getPage());
        request.setPageSize(landRequest.getLimit());

        QueryResult result = new QueryResult();

        Map<String, Object> filterQueries = new HashMap<>();

        Map<String, Object> par = new HashMap<>();
        par.put("picSize", picSize);
        par.put("iconBaseUrl", staticBaseUrl);
        if (!Check.NuNObj(landRequest.getVersionCode())) {
            par.put("versionCode", landRequest.getVersionCode());
        }
        initDicParam(par);
        /**查询主房源的信息**/
        HouseInfoEntity houseInfoEntity = new HouseInfoEntity();
        QueryRequest query = new QueryRequest();
        query.setPageIndex(1);
        query.setPageSize(1);
        Map<String, Object> filter = new HashMap<>();
        filter.put("id", landRequest.getFid() + "_" + landRequest.getRentWay());
        query.setFilterQueries(filter);
        QueryResult houseResult = queryService.query(SolrCore.m_house_info, par, query);
        if (!Check.NuNObj(houseResult.getValue())) {
            List<HouseInfoEntity> list = (List<HouseInfoEntity>) houseResult.getValue();
            if (!Check.NuNCollection(list)) {
                houseInfoEntity = list.get(0);
            } else {
                return result;
            }
        }
        /**查询主房源的信息**/
        /**排序**/
        Map<String, Object> sortMap = new HashMap<>();
        if (!Check.NuNObj(landRequest.getRentWay()) && landRequest.getRentWay() == RentWayEnum.HOUSE.getCode()) {
            sortMap.put("rentWay", "asc");
        } else if (!Check.NuNObj(landRequest.getRentWay()) && landRequest.getRentWay() == RentWayEnum.ROOM.getCode()) {
            sortMap.put("rentWay", "desc");
        }
        sortMap.put("orderType", "asc");
        sortMap.put("realEvaluateScore", "desc");
        sortMap.put("evaluateCount", "desc");
        request.setSorts(sortMap);
        /**排序**/
        /**条件过滤**/
        if (!Check.NuNObj(houseInfoEntity)) {
            filterQueries.put("cityCode", houseInfoEntity.getCityCode());
            Set<String> set = new HashSet<>();
            set.addAll(houseInfoEntity.getHotRegin());
            if (set != null && set.size() > 0) {
                StringBuffer str = new StringBuffer();
                for (String string : set) {
                    if (str.length() == 0) {
                        str.append("(").append(string).append("*");
                    } else {
                        str.append(" OR ").append(string).append("*");
                    }
                }
                str.append(")");
                filterQueries.put("hotRegin", str.toString());
            }
            StringBuffer personCountS = new StringBuffer();
            personCountS.append("([ ").append(houseInfoEntity.getPersonCount() - 2).append(" TO ").append(houseInfoEntity.getPersonCount() + 2).append(" ] OR ").append(SolrConstant.default_person_count)
                    .append(" )");
            filterQueries.put("personCount", personCountS.toString());
        }
        if (!Check.NuNStr(landRequest.getFid()) && !Check.NuNObj(landRequest.getRentWay())) {
            filterQueries.put("-id", landRequest.getFid() + "_" + landRequest.getRentWay());
        }
        /**条件过滤**/
        //设置区间的过滤条件
        Map<String, Map<String, Object>> rangeFilterQueries = new HashMap<>();

        if (!Check.NuNObj(houseInfoEntity.getPrice())) {
            //价格范围
            rangeFilterQueries.put("price", Range.getRangeMap((int) (houseInfoEntity.getPrice() * 0.8), (int) (houseInfoEntity.getPrice() * 1.2), true, true));
        }

        request.setFilterQueries(filterQueries);
        request.setRangeFilterQueries(rangeFilterQueries);

        result = queryService.query(SolrCore.m_house_info, par, request);
        return result;
    }
}
