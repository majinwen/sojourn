package com.ziroom.minsu.services.search.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.dao.HotRegionDbInfoDao;
import com.ziroom.minsu.services.house.dao.HouseDbInfoDao;
import com.ziroom.minsu.services.search.vo.CommunityInfo;
import com.ziroom.minsu.services.search.vo.HotRegionSimpleVo;
import com.ziroom.minsu.services.search.vo.SuggestInfo;
import com.ziroom.minsu.services.solr.common.IndexService;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.services.solr.utils.PersistentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>扩展</p>
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
@Service(value = "search.suggestIndexServiceImpl")
public class SuggestIndexServiceImpl {


    private static final Logger LOGGER = LoggerFactory.getLogger(SearchIndexServiceImpl.class);


    @Resource(name = "search.hotRegionDbInfoDao")
    private HotRegionDbInfoDao hotRegionDbInfoDao;


    @Resource(name = "search.houseDbInfoDao")
    private HouseDbInfoDao houseDbInfoDao;

    @Resource(name="search.indexService")
    private IndexService indexService;


    /**
     * @author afi
     * 初始化联想词
     */
    public void initSuggestIndex(){


        //初始化热门区域信息
        initRegionSuggest();
        //初始化地铁拼音词库
        initSubwaySuggest();
        //初始化城市区域的联想词
        initAreaSuggest();
        //初始化楼盘联想词库
//       initHouseSuggest();
    }


    /**
     * @author afi
     * 初始化热门区域拼音词库
     */
    public void initRegionSuggest(){
        //初始化热门区域词库
        List<HotRegionSimpleVo>  hotRegionSimpleVoList = hotRegionDbInfoDao.getHotRegionSimpleList();
        List<Object> list = new ArrayList<>();
        if(!Check.NuNCollection(hotRegionSimpleVoList)){
            for(HotRegionSimpleVo hot: hotRegionSimpleVoList){
                SuggestInfo suggestInfo = new SuggestInfo();
                suggestInfo.setId(hot.getId());
                suggestInfo.setSuggestName(hot.getRegionName());
                suggestInfo.setSuggestType(1);
                suggestInfo.setAreaName(hot.getAreaName());
                suggestInfo.setCityCode(hot.getCityCode());
                list.add(suggestInfo);
            }
        }
        indexService.batchCreateIndex(SolrCore.m_suggest, list);
    }


    /**
     * @author afi
     * 初始化地铁拼音词库
     */
    public void initSubwaySuggest(){
        //初始化热门区域词库
        List<HotRegionSimpleVo>  subwaySimpleList = hotRegionDbInfoDao.getSubwaySimpleList();
        List<Object> list = new ArrayList<>();
        if(!Check.NuNCollection(subwaySimpleList)){
            for(HotRegionSimpleVo subway: subwaySimpleList){
                SuggestInfo suggestInfo = new SuggestInfo();
                suggestInfo.setId(subway.getId());
                suggestInfo.setSuggestName(subway.getRegionName());
                suggestInfo.setSuggestType(1);
                suggestInfo.setAreaName(subway.getAreaName());
                suggestInfo.setCityCode(subway.getCityCode());
                list.add(suggestInfo);
            }
        }
        indexService.batchCreateIndex(SolrCore.m_suggest,list);
    }


    /**
     * @author afi
     * 初始化城市区域的联想词
     */
    public void initAreaSuggest(){
        //初始化热门区域词库
        List<HotRegionSimpleVo>  areaSimpleList = hotRegionDbInfoDao.getAreaSimpleList();
        if(!Check.NuNCollection(areaSimpleList)){
            List<Object> list = new ArrayList<>();
            if(!Check.NuNCollection(areaSimpleList)){
                for(HotRegionSimpleVo hot: areaSimpleList){
                    SuggestInfo suggestInfo = new SuggestInfo();
                    suggestInfo.setId(hot.getId());
                    suggestInfo.setSuggestType(2);
                    suggestInfo.setSuggestName(hot.getRegionName());
                    suggestInfo.setAreaName(hot.getAreaName());
                    suggestInfo.setCityCode(hot.getCityCode());
                    list.add(suggestInfo);
                }
            }
            indexService.batchCreateIndex(SolrCore.m_suggest,list);
        }
    }



//    /**
//     * @author afi
//     * 初始化楼盘联想词库
//     */
//    public void initHouseSuggest(){
//        try {
//            //初始化热门区域词库
//            List<CommunityInfo>  communityInfoList = PersistentUtil.loadExtFile();
//            if(!Check.NuNCollection(communityInfoList)){
//                List<Object> list = new ArrayList<>();
//                if(!Check.NuNCollection(communityInfoList)){
//                    for(CommunityInfo communityInfo: communityInfoList){
//                        SuggestInfo suggestInfo = new SuggestInfo();
//                        suggestInfo.setId(UUIDGenerator.hexUUID());
//                        suggestInfo.setSuggestName(communityInfo.getCommunityName());
//                        suggestInfo.setAreaName(communityInfo.getAreaName());
//                        suggestInfo.setCityCode(communityInfo.getCityCode());
//                        suggestInfo.setSuggestType(3);
//                        list.add(suggestInfo);
//                    }
//                }
//                indexService.batchCreateIndex(SolrCore.m_suggest,list);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            LogUtil.error(LOGGER, "{}", e);
//            throw new BusinessException(e);
//        }
//    }




}
