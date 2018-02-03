package com.ziroom.minsu.services.house.dao;

import base.BaseTest;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.services.search.dto.CreatIndexRequest;
import com.ziroom.minsu.services.search.dto.HouseCurrentStatsDto;
import com.ziroom.minsu.services.search.vo.*;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>房源的测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/6.
 * @version 1.0
 * @since 1.0
 */
public class HouseDbInfoDaoTest extends BaseTest {


    @Resource(name="search.houseDbInfoDao")
    private HouseDbInfoDao houseDbInfoDao;




    @Test
    public void getHouseDbInfoByHouseFid(){
        List<HouseDbInfoVo> aa = houseDbInfoDao.getHouseDbInfoByHouseFid("8a90a2d455b47fe00155b9ef6aab0161");
        System.out.println(JsonEntityTransform.Object2Json(aa));
    }

    @Test
    public void countPvByPv(){
        Long aa = houseDbInfoDao.countPvByPv(22);
        System.out.println(JsonEntityTransform.Object2Json(aa));
    }


    @Test
    public void countPvAll(){
        Long aa = houseDbInfoDao.countPvAll();
        System.out.println(JsonEntityTransform.Object2Json(aa));
    }




    @Test
    public void getBedNumByRoomFid(){

        List<BedNumVo> aa = houseDbInfoDao.getBedNumByRoomFid("8a90a2d455b47fe00155b9ef6aab0161");
        System.out.println(JsonEntityTransform.Object2Json(aa));
    }


    @Test
    public void getBedNumByHouseFid(){

        List<BedNumVo> aa = houseDbInfoDao.getBedNumByHouseFid("8a9e9a94547faae001547faae1660001");
        System.out.println(JsonEntityTransform.Object2Json(aa));
    }



    @Test
    public void getHousePassDate(){

        Date aa = houseDbInfoDao.getHousePassDate("8a9e9a9053e6f0070153e6f0091b0001");
        System.out.println(aa);
    }

    @Test
    public void getRoomPassDate(){

        Date aa = houseDbInfoDao.getRoomPassDate("8a9084df54c514b40154c514b4e5000e");
        System.out.println(aa);
    }


    @Test
    public void testtest(){
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        houseDbInfoDao.testtest(list);
    }

    @Test
    public void getRoomPv(){

        Integer aa = houseDbInfoDao.getRoomPv("1231");
        System.out.println(aa);
    }


    @Test
    public void getRoomWeekPrices(){
        List<HousePriceVo> ss  =houseDbInfoDao.getRoomWeekPrices("8a9e9a8b53d6da8b0153d6da8e4b0002");
        System.out.println(JsonEntityTransform.Object2Json(ss));
    }

    @Test
    public void getHouseWeekPrices(){
        List<HousePriceVo> ss  =houseDbInfoDao.getHouseWeekPrices("8a9084df56fad7b00156fd3e8a2006a1");
        System.out.println(JsonEntityTransform.Object2Json(ss));
    }



    @Test
    public void getRoomPrices(){
        List<HousePriceVo> ss  =houseDbInfoDao.getRoomPrices("8a9e9a8b53d6da8b0153d6da8e4b0002");
        System.out.println(JsonEntityTransform.Object2Json(ss));
    }

    @Test
    public void getHousePrices(){
        List<HousePriceVo> ss  =houseDbInfoDao.getHousePrices("8a9e9a8b53d6089f0153d608a1f80002");
        System.out.println(JsonEntityTransform.Object2Json(ss));
    }


    @Test
    public void getHousePicInfo(){
        HousePicVo ss =houseDbInfoDao.getHousePicInfo("8a9e9a8b53d6da8b0153d6da8e4b0002");
        System.out.println(JsonEntityTransform.Object2Json(ss));
    }


    @Test
    public void getHouseStatusByHouseFid(){
        Integer ss =houseDbInfoDao.getHouseStatusByHouseFid("8a9e9aae5419cc22015419cc24e60001");
        System.out.println(ss);
    }

    @Test
    public void TestgetHouseConfig(){
        List<ConfigVo> list =houseDbInfoDao.getHouseConfig("8a9e9a8b53d6089f0153d608a1f80002");
        System.out.println(JsonEntityTransform.Object2Json(list));
    }


    @Test
    public void TestgetHouseDbInfoForPage(){
        CreatIndexRequest pageRequest = new CreatIndexRequest();
        PagingResult<HouseDbInfoVo>  pagingResult =houseDbInfoDao.getHouseDbInfoForPage(pageRequest);
        System.out.println(JsonEntityTransform.Object2Json(pagingResult));
    }


    @Test
    public void TestgetHouseServices(){
        List<String> list =houseDbInfoDao.getHouseServices("8a9e9a8b53d6089f0153d608a1f80002");
        System.out.println(JsonEntityTransform.Object2Json(list));
    }

    @Test
    public void TestgetRoomServices(){
        List<String> list =houseDbInfoDao.getRoomServices("8a9e9a8b53d62d740153d62d76730002");
        System.out.println(JsonEntityTransform.Object2Json(list));
    }
    
    @Test
    public void TestgetHouseTypeByCityCode(){
        List<Integer> list =houseDbInfoDao.getHouseTypeByCityCode("110100");
        System.err.println(JsonEntityTransform.Object2Json(list));
    }
    
    @Test
    public void getHouseCurrentHot(){
    	HouseCurrentStatsDto dto = new HouseCurrentStatsDto();
    	dto.setHouseFid("1000000001");
    	dto.setRentWay(0);
    	Integer integer = houseDbInfoDao.getHouseCurrentHot(dto);
    	System.err.println(integer);
    }
    
    @Test
    public void getAllCityCurrentHot(){
    	HouseCurrentStatsDto dto = new HouseCurrentStatsDto();
    	Map map = houseDbInfoDao.getAllCityCurrentHot(dto);    	
    	System.out.println(JSON.toJSONString(map));
    }
    
    @Test
    public void getFlexiblePriceConf(){
    	List<String> list = houseDbInfoDao.getFlexiblePriceConf("8a9e9a8b53d6089f0153d608a1f80002","8a9e9a8b53d62d740153d62d76730002");    	
    	System.out.println(JSON.toJSONString(list));
    }
    
    @Test
    public void getLongTermLeaseDiscountConf(){
    	List<String> list = houseDbInfoDao.getLongTermLeaseDiscountConf("8a9e9a8b53d6089f0153d608a1f80002","8a9e9a8b53d62d740153d62d76730002");    	
    	System.out.println(JSON.toJSONString(list));
    }
    
    @Test
    public void getHouseOrRoomStatus(){    	
    	Integer status = houseDbInfoDao.getHouseOrRoomStatus("8a9e9a9454801ac50154801ac5900001", "8a90a2d4549341c601549379232900af", 0);
    	
    	System.out.println(status);
    }
    
    @Test
    public void getTop50ArticleTitle(){    	
    	String str = houseDbInfoDao.getTop50ArticleTitle("8a9e9a8b53d6089f0153d608a1f80002", null, 0);
    	System.out.println(str);
    }
    
    @Test
    public void getHouseIndexbyLand(){    	
    	Map<String, Integer> result = houseDbInfoDao.getHouseIndexbyLand("9afeae98-56ff-0c35-77cf-8624b2e1efae");
    	System.out.println(JsonEntityTransform.Object2Json(result));
    }
    
    
    @Test
    public void getRoomIndexbyHouse(){    	
    	Map<String, Integer> result = houseDbInfoDao.getRoomIndexbyHouse("8a9e9a9a548abce301548ae20cfc0047");
    	System.out.println(JsonEntityTransform.Object2Json(result));
    }
    
    @Test
    public void getTonightDiscountList(){
    	
    	List<TonightDiscountEntity> list =  houseDbInfoDao.getTonightDiscountList("8a9e98b45bf22b4d015bf22b4ded0002","8a9e98b45bf22b4d015bf22b4ded0003",true );
    	
    	System.out.println(JsonEntityTransform.Object2Json(list));
    	
    }
    



}
