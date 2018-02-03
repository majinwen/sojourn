package com.ziroom.minsu.services.search.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.dao.BasedateDbInfoDao;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.house.dao.HouseDbInfoDao;
import com.ziroom.minsu.services.search.vo.HotRegionVo;
import com.ziroom.minsu.services.search.vo.HouseDbInfoVo;
import com.ziroom.minsu.services.solr.common.IndexService;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.valenum.search.SearchDataSourceTypeEnum;

/**
 * <p>刷新单条索引</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/16.
 * @version 1.0
 * @since 1.0
 */
@Service(value = "search.freshIndexServiceImpl")
public class FreshIndexServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreshIndexServiceImpl.class);

    @Resource(name = "search.houseDbInfoDao")
    private HouseDbInfoDao houseDbInfoDao;

    @Resource(name="search.searchIndexServiceImpl")
    private SearchIndexServiceImpl searchIndexService;


    @Resource(name = "search.messageSource")
    private MessageSource messageSource;

    @Resource(name="search.indexService")
    private IndexService indexService;
    
    @Resource(name="search.basedateDbInfoDao")
    private BasedateDbInfoDao basedateDbInfoDao;

    /**
     * 刷新房源信息
     * @author afi
     * @param houseFid
     * @param dto
     */
    public void freshIndexByHouseFid(String houseFid,DataTransferObject dto){
        //先删除
        delHouseIndex(houseFid,dto);
        //刷新房源信息
        freshHouseIndex(houseFid,dto);

    }

    /**
     * 刷新房源信息
     * @param houseFid
     * @param dto
     */
    private void delHouseIndex(String houseFid,DataTransferObject dto){
        LogUtil.info(LOGGER,"记录删除索引 开始: {}",houseFid);
        //删除房源信息
        indexService.deleteByQuery(SolrCore.m_house_info, "houseId:" + houseFid);
        LogUtil.info(LOGGER,"记录删除索引 结束: {}",houseFid);
    }

    /**
     * 刷新房源信息
     * @param houseFid
     * @param dto
     */
    private void freshHouseIndex(String houseFid,DataTransferObject dto){

        Integer km = searchIndexService.getKm();

        Map<String,List<HotRegionVo>>  reginMap = new HashMap<>();

        Map<String,Object>  baseMap = new HashMap<>();
        //虚拟整租的集合
        Set<String> virtualSet = new HashSet<>();
        List<HouseDbInfoVo>  houseDbInfos = houseDbInfoDao.getHouseDbInfoByHouseFid(houseFid);
        //获取第一页的信息
        if(Check.NuNCollection(houseDbInfos)){
            LogUtil.info(LOGGER, "当前房源不存在 houseFid:{}",houseFid);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.NOT_FOUND));
            return;
        }
      //城市map
        Map<String, String> cityMap= basedateDbInfoDao.getAllCityMap();
        //处理第一页的信息
        searchIndexService.dealSearchIndex(houseDbInfos, reginMap,baseMap, virtualSet,km,cityMap);
    }

}
