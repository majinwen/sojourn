package com.ziroom.minsu.services.search.service;

import base.BaseTest;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.house.dao.HouseDbInfoDao;
import com.ziroom.minsu.services.search.dto.CreatIndexRequest;
import com.ziroom.minsu.services.search.vo.HotRegionVo;
import com.ziroom.minsu.services.search.vo.HouseDbInfoVo;
import com.ziroom.minsu.services.search.vo.HouseInfo;
import com.ziroom.minsu.services.solr.common.IndexService;
import com.ziroom.minsu.services.solr.constant.SolrConstant;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.valenum.search.SearchDataSourceTypeEnum;

import org.junit.Test;

import javax.annotation.Resource;

import java.util.*;

/**
 * <p>索引测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/7.
 * @version 1.0
 * @since 1.0
 */
public class SearchIndexServiceImplTest  extends BaseTest{

    @Resource(name="search.searchIndexServiceImpl")
    private SearchIndexServiceImpl searchIndexService;


    @Resource(name = "search.houseDbInfoDao")
    private HouseDbInfoDao houseDbInfoDao;

    @Resource(name="search.indexService")
    private IndexService indexService;


    @Test
    public void TestTest(){

        searchIndexService.creatAllIndex();
        System.out.println("test");
    }
    
    
    @Test
    public void TestfreshHouseInfo(){
        searchIndexService.freshHouseInfo("8a9e9a8b53d6089f0153d608a1f80002");
        System.out.println("test");
    }


    @Test
    public void TestADDDDDDD(){

        Map<String,List<HotRegionVo>> reginMap = new HashMap<>();
        CreatIndexRequest pageRequest = new CreatIndexRequest();
        pageRequest.setLimit(SolrConstant.create_index_page_limit);
        PagingResult<HouseDbInfoVo> pagingResult = houseDbInfoDao.getHouseDbInfoForPage(pageRequest);
        //获取第一页的信息
        List<HouseDbInfoVo> houseDbInfoVoList = pagingResult.getRows();
        if(Check.NuNObj(houseDbInfoVoList)){
            return;
        }

        HouseDbInfoVo houseDbInfoVo = houseDbInfoVoList.get(0);

        List<Object> houseInfoList = new ArrayList<>();
        for(int i=0;i<100;i++){
            HouseInfo houseInfo = searchIndexService.transHouseInfoFromHouseDbInfoVo(houseDbInfoVo,reginMap,null,3);
            houseInfo.setId(i+"");
            houseInfo.setHouseName(houseInfo.getHouseName()+"_"+ i);
            houseInfo.setHouseNameAuto(houseInfo.getHouseName()+"_"+ i);
            houseInfoList.add(houseInfo);
        }
        indexService.batchCreateIndex(SolrCore.m_house_info,houseInfoList);
    }
    
    @Test
    public void Testrerr(){
    	List<HouseInfo> indexHouse = new ArrayList<>();
    	List<HouseInfo> list = new ArrayList<>();
    	
    	HouseInfo i1 = new HouseInfo(SearchDataSourceTypeEnum.minsu.getCode());
    	i1.setAdIndex(1);
    	i1.setAreaCode("111");
    	
    	HouseInfo i2 = new HouseInfo(SearchDataSourceTypeEnum.minsu.getCode());
    	i2.setAdIndex(2);
    	i2.setAreaCode("222");
    	
    	
    	indexHouse.add(i1);
    	indexHouse.add(i2);
    	
    	HouseInfo i3 = indexHouse.get(1);
    	i3.setAdIndex(3);
    	i3.setAreaCode("3");
    	indexHouse.set(1, i3) ;
    	
//    	indexHouse.retainAll(list);
    	
//    	indexHouse.removeAll(list);
    	System.out.println(JSON.toJSONString(indexHouse));
    	
    	
    }
    


}
