package com.ziroom.minsu.services.search.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.dao.HotRegionDbInfoDao;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.house.dao.HouseDbInfoDao;
import com.ziroom.minsu.services.lianjia.service.SyncHousesInfoServiceImpl;
import com.ziroom.minsu.services.search.vo.*;
import com.ziroom.minsu.services.solr.common.IndexService;
import com.ziroom.minsu.services.solr.constant.SolrConstant;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.services.solr.utils.PersistentUtil;
import org.apache.solr.client.solrj.beans.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>初始化拼音信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/13.
 * @version 1.0
 * @since 1.0
 */
@Service(value = "search.spellIndexServiceImpl")
public class SpellIndexServiceImpl {


    private static final Logger LOGGER = LoggerFactory.getLogger(SearchIndexServiceImpl.class);


    @Resource(name = "search.hotRegionDbInfoDao")
    private HotRegionDbInfoDao hotRegionDbInfoDao;


    @Resource(name = "search.houseDbInfoDao")
    private HouseDbInfoDao houseDbInfoDao;

    @Resource(name="search.indexService")
    private IndexService indexService;


    @Resource(name="search.syncHousesInfoServiceImpl")
    private SyncHousesInfoServiceImpl syncHousesInfoService;


    /**
     * 已经存在的楼盘信息
     */
    private static ConcurrentMap<String, Integer> hasDbMap;


    /**
     * @author afi
     * 初始化楼盘
     */
    public void initCommunityIndex(){
        //先初始化数据库的索引
        hasDbMap = initHouseIndexFromDb();
    }


    /**
     * @author afi
     * 初始化数据库中已经存在的
     */
    public ConcurrentMap<String,Integer> initDbMap(){
        try {
            ConcurrentMap<String,Integer> dbMap = new ConcurrentHashMap();
            PageRequest pageRequest = new PageRequest();
            pageRequest.setLimit(SolrConstant.create_index_page_limit);
            PagingResult<CommunityInfo> pagingResult = houseDbInfoDao.getCommunityInfoForPage(pageRequest);
            List<CommunityInfo> communityInfoList = pagingResult.getRows();
            if(Check.NuNObj(communityInfoList)){
                return dbMap;
            }
            //初始化map
            trans2DbMap(communityInfoList, dbMap);
            Integer length = ValueUtil.getintValue(pagingResult.getTotal());
            int page = ValueUtil.getPage(length, SolrConstant.create_index_page_limit);
            for(int i=1;i<page;i++){
                //从第二页开始，循环处理之后的数据信息
                pageRequest.setPage(pageRequest.getPage() + 1);
                trans2DbMap(houseDbInfoDao.getCommunityInfoForPage(pageRequest).getRows(), dbMap);
            }
            return dbMap;
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
            throw new BusinessException(e);
        }
    }


    /**
     * @author afi
     * 初始化数据库中已经存在的
     */
    public ConcurrentMap<String,Integer> initHouseIndexFromDb(){
        try {
            ConcurrentMap<String,Integer> dbMap = new ConcurrentHashMap();
            PageRequest pageRequest = new PageRequest();
            pageRequest.setLimit(SolrConstant.create_index_page_limit);
            PagingResult<CommunityInfo> pagingResult = houseDbInfoDao.getCommunityInfoForPage(pageRequest);
            List<CommunityInfo> communityInfoList = pagingResult.getRows();
            if(Check.NuNObj(communityInfoList)){
                return dbMap;
            }
            //处理第一页的信息
            dealSPellIndexFormDb(communityInfoList, dbMap);
            Integer length = ValueUtil.getintValue(pagingResult.getTotal());
            int page = ValueUtil.getPage(length, SolrConstant.create_index_page_limit);
            for(int i=1;i<page;i++){
                //从第二页开始，循环处理之后的数据信息
                pageRequest.setPage(pageRequest.getPage() + 1);
                dealSPellIndexFormDb(houseDbInfoDao.getCommunityInfoForPage(pageRequest).getRows(),dbMap);
            }
            return dbMap;
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
            throw new BusinessException(e);
        }
    }


    /**
     * 创建索引数据 并初始化数据库map信息
     * @param communityInfoList
     * @param dbMap
     */
    public void trans2DbMap(List<CommunityInfo> communityInfoList,Map<String,Integer> dbMap){
        try {
            for(CommunityInfo communityInfo:communityInfoList){
                dbMap.put(communityInfo.getCommunityName(), 1);
            }
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }
    }


    /**
     * 创建索引数据 并初始化数据库map信息
     * @param communityInfoList
     * @param dbMap
     */
    public void dealSPellIndexFormDb(List<CommunityInfo> communityInfoList,Map<String,Integer> dbMap){
        try {
            List<Object> houseInfoList = new ArrayList<>();
            for(CommunityInfo communityInfo:communityInfoList){
                dbMap.put(communityInfo.getCommunityName(),1);
                //设置默认是数据库的
                communityInfo.setSourceType(1);
                houseInfoList.add(communityInfo);
            }
            indexService.batchCreateIndex(SolrCore.m_spell, houseInfoList);
        }catch (Exception e){
            LogUtil.error(LOGGER, "创建数据库的索引异常", e);
        }
        if(Check.NuNObj(communityInfoList)){
            return;
        }

    }


    /**
     * 创建索引数据 并初始化数据库map信息
     * @param communityInfoList
     * @param dbMap
     */
    public void dealSPellIndexFormLianjia(List<CommunityInfo> communityInfoList,Map<String,Integer> dbMap){
        if(Check.NuNObj(communityInfoList)){
            return;
        }
        List<Object> houseInfoList = new ArrayList<>();
        for(CommunityInfo communityInfo:communityInfoList){
           if(dbMap.containsKey(communityInfo.getCommunityName())){
               //已经存在直接跳过
                continue;
           }
            communityInfo.setSourceType(2);
            if(Check.NuNStr(communityInfo.getId())){
                communityInfo.setId(UUIDGenerator.hexUUID());
            }
            houseInfoList.add(communityInfo);
        }
        indexService.batchCreateIndex(SolrCore.m_spell, houseInfoList);
    }

    /**
     * @author afi
     * 初始化链家的数据
     * @param cityCodeS 初始化城市code  多个用| 隔开
     */
    public void initHouseIndexFromLianjia(String cityCodeS){
        try {
             syncHousesInfoService.syncHousesInfo(cityCodeS);
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
            throw new BusinessException(e);
        }
    }

    /**
     * 创建索引
     * @param houseList
     */
    public void creatIndexFromSyncHousesInfo(List<Map> houseList){
        if(Check.NuNObj(hasDbMap)){
            hasDbMap = this.initDbMap();
        }
        List<CommunityInfo> communityInfoList = transResb2CommunityInfoList(houseList);
        //处理第一页的信息
        dealSPellIndexFormLianjia(communityInfoList, hasDbMap);
    }

    /**
     * 将房源的数据对象转化成民宿对象
     * @param houseList
     * @return
     */
    private List<CommunityInfo> transResb2CommunityInfoList(List<Map> houseList){

        List<CommunityInfo> communityInfoList = new ArrayList<>();
        if(Check.NuNCollection(houseList)){
            return communityInfoList;
        }
        for(Map res :houseList){
            CommunityInfo communityInfo = transResb2CommunityInfo(res);
            communityInfoList.add(communityInfo);
        }
        return communityInfoList;
    }

    /**
     * 将房源的数据对象转化成民宿对象
     * @param res
     * @return
     */
    private CommunityInfo transResb2CommunityInfo(Map<String,Object> res){
        if(Check.NuNObj(res)) {
            return null;
        }
        CommunityInfo communityInfo = new CommunityInfo();
        //设置楼盘的id
        communityInfo.setId(ValueUtil.getStrValue(res.get("standardId")));
        communityInfo.setCommunityName(ValueUtil.getStrValue(res.get("resblockName")));
        //设置链家
        communityInfo.setSourceType(2);
        //链家没有区域TODO
        communityInfo.setAreaName("");
        communityInfo.setHouseAroundDesc(ValueUtil.getStrValue(res.get("trafficInfo")));
        communityInfo.setHouseDesc(ValueUtil.getStrValue(res.get("surrounding")));
        communityInfo.setHouseStreet(ValueUtil.getStrValue(res.get("executiveAddress")));
        communityInfo.setLongitude(ValueUtil.getStrValue(res.get("longitude")));
        communityInfo.setLatitude(ValueUtil.getStrValue(res.get("latitude")));
        communityInfo.setCityCode(ValueUtil.getStrValue(res.get("territoryCode")));
        return communityInfo;
    }

}
