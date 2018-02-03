package com.ziroom.minsu.services.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.vo.HouseInfo;
import com.ziroom.minsu.services.search.vo.ZryAreaMappedEnum;
import com.ziroom.minsu.services.search.vo.ZryCityMappedEnum;
import com.ziroom.minsu.services.search.vo.ZryProjectInfoVo;
import com.ziroom.minsu.services.solr.common.IndexService;
import com.ziroom.minsu.services.solr.common.QueryService;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.valenum.house.OrderTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.search.SearchDataSourceTypeEnum;

/**
 * <p>
 * 刷新自如驿索引都走这里
 * </p>
 * <p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年07月28日 10:21
 * @since 1.0
 */
@Service(value = "search.freshZryIndexServiceImpl")
public class FreshZryIndexServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreshZryIndexServiceImpl.class);

    @Resource(name = "search.indexService")
    private IndexService indexService;

    @Resource(name = "search.messageSource")
    private MessageSource messageSource;

    @Resource(name = "search.queryService")
    private QueryService queryService;

    @Value("#{'${ZRY_PROJECT_INFO_LIST_API}'.trim()}")
    private String ZRY_PROJECT_INFO_LIST_API;

    private static final String THREAD_NAME_ZRYPROJECT_LIST = "THREAD_NAME_ZRYPROJECT_LIST";

    /**
     * 创建自如驿索引
     *
     * @param projectBid
     * @return
     * @author zhangyl
     * @created 2017年8月4日 下午6:40:51
     */
    public String syncDealSearchIndex(String projectBid) {
        DataTransferObject dto = new DataTransferObject();

        boolean started = false;
        Thread[] threads = new Thread[Thread.activeCount()];
        Thread.enumerate(threads);
        if (!Check.NuNObject(threads)) {
            for (Thread th : threads) {
                if ((THREAD_NAME_ZRYPROJECT_LIST + projectBid).equalsIgnoreCase(th.getName()) && th.isAlive()) {
                    started = true;
                    break;
                }
            }
        }

        if (started) {
            LogUtil.info(LOGGER, "FreshZryIndexServiceImpl同步自如驿信息线程尚未结束!请勿频繁调用！");
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("同步自如驿信息线程尚未结束!请勿频繁调用！");
            return dto.toJsonString();
        } else {
            try {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            dealSearchIndex(projectBid);
                        } catch (Exception e) {
                            LogUtil.error(LOGGER, "FreshZryIndexServiceImpl同步自如驿信息失败 ,e:{}", e);
                            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
                        }

                    }
                });
                thread.setName(THREAD_NAME_ZRYPROJECT_LIST + projectBid);
                thread.start();
            } catch (Exception e) {
                LogUtil.error(LOGGER, "FreshZryIndexServiceImpl同步自如驿信息失败 ,e:{}", e);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("同步自如驿信息失败，请稍后再试");
                return dto.toJsonString();
            }

        }

        return dto.toJsonString();

    }

    /**
     * 更新自如驿索引
     *
     * @param projectBid
     * @author zhangyl
     * @created 2017年8月9日 下午2:41:59
     */
    public void dealSearchIndex(String projectBid) {
        try {
            boolean freshAllZryProject = Check.NuNStr(projectBid);
            projectBid = freshAllZryProject ? "" : projectBid;

            Map<String, String> param = new HashMap<>();
            param.put("projectBid", projectBid);

            LogUtil.info(LOGGER, "FreshZryIndexServiceImpl同步自如驿项目信息开始:projectBid={}", projectBid);
            // 自如驿系统提供的项目信息查询接口（projectBid传空查全量）
            String jsonRst = CloseableHttpUtil.sendFormPost(ZRY_PROJECT_INFO_LIST_API, param);
            LogUtil.info(LOGGER, "FreshZryIndexServiceImpl同步自如驿项目信息接口返回结果:jsonRst={}", jsonRst);

            JSONObject json = JSONObject.parseObject(jsonRst);
            if ("success".equalsIgnoreCase(json.getString("status"))) {
                List<ZryProjectInfoVo> houseVoList = JSONArray.parseArray(json.getString("data"), ZryProjectInfoVo.class);

                // 待更新的文档id集合
                Set<String> freshZryProjectBidSet = new HashSet<>();

                // 待更新的文档集合
                List<Object> houseInfoList = new ArrayList<>();
                for (ZryProjectInfoVo zryProjectInfoVo : houseVoList) {
                    HouseInfo houseInfo = transToHouseInfo(zryProjectInfoVo);
                    if (!Check.NuNObj(houseInfo)) {
                        houseInfoList.add(houseInfo);

                        // 如果是全量更新则保存更新的文档id，后面用来删除库里无效的
                        if (freshAllZryProject) {
                            freshZryProjectBidSet.add(houseInfo.getId());
                        }
                    }
                }

                indexService.batchCreateIndex(SolrCore.zry_house_info, houseInfoList);
                LogUtil.info(LOGGER, "FreshZryIndexServiceImpl同步自如驿项目信息结束，更新数量={}，删除数量={}", houseInfoList.size());

                // 如果是全量更新，删除库里多余的
                if (freshAllZryProject) {
                    delIndexNotExit(freshZryProjectBidSet);
                } else if (Check.NuNCollection(houseVoList)) {
                    //不是全量更新，判断单个信息，没获取到则删除单个自如驿索引
                    LogUtil.info(LOGGER, "FreshZryIndexServiceImpl删除单个自如驿索引！url={}, projectBid={}", ZRY_PROJECT_INFO_LIST_API, projectBid);
                    indexService.deleteByQuery(SolrCore.zry_house_info, "houseId:" + projectBid);
                }

            } else {
                LogUtil.error(LOGGER, "FreshZryIndexServiceImpl同步自如驿项目信息接口异常！url={}, jsonRst={}", ZRY_PROJECT_INFO_LIST_API, jsonRst);
            }

        } catch (Exception e) {
            LogUtil.error(LOGGER, "FreshZryIndexServiceImpl同步自如驿项目信息失败！url={}, e={}", ZRY_PROJECT_INFO_LIST_API, e);
        }
    }

    /**
     * 删除无效的自如驿文档
     *
     * @param hasSet
     * @author zhangyl
     * @created 2017年8月9日 上午11:38:24
     */
    private void delIndexNotExit(Set<String> freshZryProjectBidSet) {
        Set<String> indexIds = this.getAllDocIds();
        LogUtil.info(LOGGER, "FreshZryIndexServiceImpl delIndexNotExit 当前库里索引的条数：{}", indexIds.size());

        indexIds.removeAll(freshZryProjectBidSet);
        List<String> list = new ArrayList<>(indexIds);
        LogUtil.info(LOGGER, "FreshZryIndexServiceImpl delIndexNotExit 开始删除无效索引，无效索引条数：{}", list.size());

        // 删除历史索引信息
        indexService.deleteByIds(SolrCore.zry_house_info, list);
    }

    /**
     * 获取所有自如驿文档id
     *
     * @return
     * @author zhangyl
     * @created 2017年8月9日 下午2:41:11
     */
    private Set<String> getAllDocIds() {
        Map<String, Object> filterQueries = new HashMap<String, Object>();

        // 区分solr文档里 民宿、自如驿
        filterQueries.put("dataSource", SearchDataSourceTypeEnum.ziruyi.getCode());

        Set<String> all = queryService.getAllIds(SolrCore.m_house_info, filterQueries);

        return all;
    }

    public static void main(String[] args) {
        Map<String, String> param = new HashMap<>();
        param.put("projectBid", "1111");

        String jsonRst = CloseableHttpUtil.sendFormPost("http://zry.api.t.ziroom.com/third/solr/project/v1", param);
        System.out.println(jsonRst);
    }

    /**
     * 转化成要建索引的数据对象
     *
     * @param
     * @return
     * @author zl
     * @created 2017/7/28 11:00
     */
    private HouseInfo transToHouseInfo(ZryProjectInfoVo projectInfoVo) {

        if (Check.NuNObj(projectInfoVo) || Check.NuNObj(projectInfoVo.getProjectBid())) {
            return null;
        }

        HouseInfo houseInfo = new HouseInfo(SearchDataSourceTypeEnum.ziruyi.getCode());
        houseInfo.setId(projectInfoVo.getProjectBid());
        houseInfo.setHouseId(projectInfoVo.getProjectBid());
        houseInfo.setHouseSn(projectInfoVo.getProjectBid());
        houseInfo.setHouseName(projectInfoVo.getProjectName());
        houseInfo.setHouseModelCount(projectInfoVo.getHouseModelCount());// 房型种类数量

        // 映射地理位置
        mapLoc(projectInfoVo);
        houseInfo.setCityCode(projectInfoVo.getCityCode());
        houseInfo.setCityName(projectInfoVo.getCityName());
        houseInfo.setAreaCode(projectInfoVo.getAreaCode());
        houseInfo.setAreaName(projectInfoVo.getAreaName());


        houseInfo.setDayOrderLockedStocks(projectInfoVo.getDayOrderLockedStocks());// "170801,2"
        houseInfo.setDaySelfLockedStocks(projectInfoVo.getDaySelfLockedStocks());// "170801,2"
        houseInfo.setEvaluateCount(projectInfoVo.getEvaluateCount());
        houseInfo.setEvaluateScore(ValueUtil.getEvaluteSoreDefault(ValueUtil.getStrValue(projectInfoVo.getEvaluateScore())));
        houseInfo.setRealEvaluateScore(ValueUtil.getRealEvaluteSore(ValueUtil.getStrValue(projectInfoVo.getEvaluateScore())));
        houseInfo.setHouseAddr(projectInfoVo.getProjectAddress());
        houseInfo.setNickName(projectInfoVo.getNickName());
        houseInfo.setPassDate(projectInfoVo.getPassDate());
        houseInfo.setHouseEndTime(projectInfoVo.getTillDate());
        houseInfo.setPicUrl(projectInfoVo.getPicUrl());
        houseInfo.setPrice(projectInfoVo.getMinPrice());
        houseInfo.setPriceMax(projectInfoVo.getMaxPrice());
        houseInfo.setStatus(projectInfoVo.getStatus());
        houseInfo.setStatusName(projectInfoVo.getStatusName());
        houseInfo.setTotalStock(projectInfoVo.getTotalStock());
        houseInfo.setOrder(projectInfoVo.getOrder());
        houseInfo.setRefreshDate(projectInfoVo.getRefreshDate());
        houseInfo.setHotReginBusiness(projectInfoVo.getHotReginBusiness());

        if (!Check.NuNObj(projectInfoVo.getOrder()) && projectInfoVo.getOrder() >= 0) {
            houseInfo.setWeights(100L - projectInfoVo.getOrder());
        }

        /** 写死数据 开始 */
        houseInfo.setDataSource(SearchDataSourceTypeEnum.ziruyi.getCode());
        houseInfo.setHouseType(3);// 自如驿是公寓类型
        houseInfo.setHouseTypeName("公寓");
        houseInfo.setRentWay(RentWayEnum.BED.getCode());
        houseInfo.setRentWayName(RentWayEnum.BED.getName());
        houseInfo.setOrderType(OrderTypeEnum.CURRENT.getCode());
        houseInfo.setOrderTypeName(OrderTypeEnum.CURRENT.getName());
        houseInfo.setIsBalcony(1);//
        houseInfo.setIsFeatureHouse(0);
        houseInfo.setIsLandTogether(0);
        houseInfo.setToiletCount(1);
        houseInfo.setIsLock(0);
        houseInfo.setIsNew(0);
        houseInfo.setIsToilet(0);
        houseInfo.setIsTop50ListShow(0);
        houseInfo.setIsTop50Online(0);
        houseInfo.setMinDay(1);

        /** 写死数据 结束 */

        return houseInfo;
    }

    /**
     * TODO 以后完善系统间的映射功能
     * <p>
     * 与自如驿的地理位置code映射
     *
     * @param cityCode
     * @param areaCode
     * @return
     * @author zhangyl
     * @created 2017年8月10日 下午3:17:01
     */
    private void mapLoc(ZryProjectInfoVo zryProjectInfoVo) {

        // 城市
        ZryCityMappedEnum[] cityMappedEnums = ZryCityMappedEnum.values();
        for (ZryCityMappedEnum cityMappedEnum : cityMappedEnums) {
            if (cityMappedEnum.getZryCode().equals(zryProjectInfoVo.getCityCode())) {
                zryProjectInfoVo.setCityCode(cityMappedEnum.getMinsuCode());
                zryProjectInfoVo.setCityName(cityMappedEnum.getMinsuName());
                break;
            }
        }

        // 行政区
        ZryAreaMappedEnum[] areaMappedEnums = ZryAreaMappedEnum.values();
        for (ZryAreaMappedEnum areaMappedEnum : areaMappedEnums) {
            if (areaMappedEnum.getZryCode().equals(zryProjectInfoVo.getAreaCode())) {
                zryProjectInfoVo.setAreaCode(areaMappedEnum.getMinsuCode());
                zryProjectInfoVo.setAreaName(areaMappedEnum.getMinsuName());
                break;
            }
        }

    }

    /**
     * 假数据测试
     *
     * @return
     * @author zhangyl
     * @created 2017年8月9日 下午2:41:30
     */
    private static String fakeZryProject() {
        List<ZryProjectInfoVo> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            ZryProjectInfoVo projectInfoVo = new ZryProjectInfoVo();

            projectInfoVo.setProjectBid("201708071032" + i + i + i);

            projectInfoVo.setProjectName("自如驿A" + i + i + i);
            projectInfoVo.setHouseModelCount(3);
            if ((i & 1) == 1) {
                projectInfoVo.setCityCode("111");
                projectInfoVo.setCityName("北京");
                projectInfoVo.setAreaCode("333");
                projectInfoVo.setAreaName("朝阳区");
                projectInfoVo.setProjectAddress("北京市朝阳区青年北路");
                projectInfoVo.setStatus(0);
                projectInfoVo.setStatusName("默认");
            } else {
                projectInfoVo.setCityCode("222");
                projectInfoVo.setCityName("上海");
                projectInfoVo.setAreaCode("444");
                projectInfoVo.setAreaName("静安区");
                projectInfoVo.setProjectAddress("上海市静安区xx路");
                if ((i % 3) == 0) {
                    projectInfoVo.setStatus(2);
                    projectInfoVo.setStatusName("未上");
                } else {
                    projectInfoVo.setStatus(1);
                    projectInfoVo.setStatusName("新上");
                }
            }
            Set<String> set = new HashSet<>();
            set.add("170910,10");
            set.add("170915,10");
            set.add("170920,10");
            projectInfoVo.setDayOrderLockedStocks(set);
            set = new HashSet<>();
            set.add("170910,10");
            set.add("170915,6");
            set.add("170920,5");
            set.add("170925,10");
            projectInfoVo.setDaySelfLockedStocks(set);
            projectInfoVo.setEvaluateCount(99);
            projectInfoVo.setEvaluateScore(4.8);
            projectInfoVo.setNickName("业主A");
            projectInfoVo.setPassDate(System.currentTimeMillis());
            projectInfoVo.setPicUrl("http://image.ziroom.com/g2/M00/3D/81/ChAFD1lsoRSAO6NRAAzF4DS4dgo567.jpg");
            projectInfoVo.setMinPrice(9900);
            projectInfoVo.setMaxPrice(29900);

            projectInfoVo.setTotalStock(20);
            projectInfoVo.setOrder(1 + i);
            projectInfoVo.setRefreshDate(System.currentTimeMillis());

            list.add(projectInfoVo);
        }

        JSONObject json = new JSONObject();
        json.put("error_code", 200);
        json.put("status", "success");
        json.put("data", list);

        return json.toJSONString();
    }

}
