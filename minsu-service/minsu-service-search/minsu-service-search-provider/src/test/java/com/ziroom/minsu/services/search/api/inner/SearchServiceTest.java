package com.ziroom.minsu.services.search.api.inner;

import base.BaseTest;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.dto.HouseListRequset;
import com.ziroom.minsu.services.search.dto.HouseSearchOneRequest;
import com.ziroom.minsu.services.search.dto.HouseSearchRequest;
import com.ziroom.minsu.services.search.dto.LandHouseRequest;
import com.ziroom.minsu.services.search.vo.HouseInfo;
import com.ziroom.minsu.valenum.house.OrderTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.search.SearchSourceTypeEnum;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.*;

/**
 * <p>搜索接口</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/16.
 * @version 1.0
 * @since 1.0
 */
public class SearchServiceTest extends BaseTest{


    @Resource(name="search.searchProxy")
    private SearchService searchService;

    private static final String picSize = "_Z_375_211";



    @Test
    public void Test(){
//        String par = "{\"page\":3,\"limit\":10,\"cityCode\":\"110100\",\"areaCode\":null,\"hotReginScenic\":null,\"hotReginBusiness\":null,\"subway\":null,\"q\":\"*:*\",\"rentWay\":0,\"rentWayName\":null,\"orderType\":1,\"priceStart\":55000,\"priceEnd\":120000,\"personCount\":2,\"roomCount\":null,\"services\":null,\"startTime\":null,\"endTime\":null,\"indexOrder\":null,\"indexOrderType\":null,\"sorts\":null,\"sortType\":null,\"iconType\":null,\"loc\":null}";
        String par ="{\"page\":1,\"limit\":10,\"cityCode\":\"110100\",\"areaCode\":null,\"hotReginScenic\":null,\"hotReginBusiness\":null,\"subway\":null,\"q\":null,\"rentWay\":null,\"rentWayName\":null,\"orderType\":null,\"priceStart\":0,\"priceEnd\":null,\"personCount\":null,\"roomCount\":0,\"services\":null,\"startTime\":\"\",\"endTime\":\"\",\"indexOrder\":null,\"indexOrderType\":null,\"sorts\":null,\"sortType\":null,\"iconType\":null,\"lineFid\":null,\"loc\":null,\"houseType\":\"1,3\"}";
        String rst = searchService.getHouseListInfoAndSuggest(picSize, par,"asd");
        System.out.println(rst);
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
    }


    @Test
    public void TestgetHouseListInfoAndSuggest(){

        Long  aa = System.currentTimeMillis();
        HouseInfoRequest info = new HouseInfoRequest();
        info.setCityCode("110100");
        info.setLimit(10);
        info.setPage(1);
//        info.setPersonCount(10000);
        info.setQ("8a909978567ded8201567f452cca011e");
//        info.setSearchSourceTypeEnum(SearchSourceTypeEnum.today_article);
        info.setVersionCode(100015);
//        info.setJiaxinDiscount("ProductRulesEnum020001");
        info.setStartTime("2017-06-08");
        info.setEndTime("2017-06-09");
        String rst = searchService.getHouseListInfoAndSuggest(picSize, JsonEntityTransform.Object2Json(info), "5f4f193b-07fd-a708-85f8-22907004fd6d");
        System.out.println(rst);
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  "+Thread.currentThread().getName()+"搜索一次需要时间：" + (bb - aa) );
        
        System.out.println("rst="+rst);

    }



    @Test
    public void TestgetHouseListInfo1(){

        Long  aa = System.currentTimeMillis();
        HouseInfoRequest info = new HouseInfoRequest();
//        info.setStartTime("2016-05-26");
//        info.setEndTime("2016-04-15");
        info.setCityCode("110100");
        info.setLimit(10);
        info.setPage(1);
        info.setRoomCount(6);
        info.setRentWay(0);
        info.setPersonCount(2);
        info.setPriceStart(55000);
        info.setPriceEnd(120000);
        info.setOrderType(1);
        info.setQ("*:*");
        info.setLineFid("1231");
//        info.setPersonCount(9);
//        info.setCityCode("beijing");
//        info.setLoc("39.975842,116.495458");
//        Map<String,Object> sortMap = new HashMap<>();
//        sortMap.put("dist","asc");
//        info.setSorts(sortMap);
//        info.setPersonCount(0);
        String rst = searchService.getHouseListInfoAndSuggest(picSize, JsonEntityTransform.Object2Json(info),"asd");
        System.out.println(rst);
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  "+Thread.currentThread().getName()+"搜索一次需要时间：" + (bb - aa) );

    }



    @Test
    public void getHouseListByListInfo(){

        Long  aa = System.currentTimeMillis();
//        List<HouseSearchRequest> houseList = new ArrayList<>();
//        HouseSearchRequest ele = new HouseSearchRequest();
//        ele.setFid("8a9e9aae53e97a9e0153e97a9ecb0000");
//        ele.setRentWay(RentWayEnum.ROOM.getCode());
//        houseList.add(ele);
//        HouseSearchRequest ele1 = new HouseSearchRequest();
//        ele1.setFid("8a90a2d455b47fe00155b9dd47c20121");
//        ele1.setRentWay(RentWayEnum.HOUSE.getCode());
//        houseList.add(ele1);

        HouseListRequset requset = new HouseListRequset();
//        requset.setHouseList(houseList);
        requset.setStartTime(new Date());
        requset.setEndTime(new Date());


        String rst = searchService.getHouseListByListInfo(JsonEntityTransform.Object2Json(requset));
        System.out.println(rst);
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  "+Thread.currentThread().getName()+"搜索一次需要时间：" + (bb - aa) );
    }



    @Test
    public void TestgetHouseListByList(){

        Long  aa = System.currentTimeMillis();
        List<HouseSearchRequest> houseList = new ArrayList<>();

        HouseSearchRequest ele = new HouseSearchRequest();
        ele.setFid("8a90997755ae6a380155b3d1623a005e");
        ele.setRentWay(RentWayEnum.HOUSE.getCode());
        houseList.add(ele);

        HouseSearchRequest ele1 = new HouseSearchRequest();
        ele1.setFid("8a909978582f448801583da11a7c0c5f");
        ele1.setRentWay(RentWayEnum.HOUSE.getCode());
        houseList.add(ele1);

        HouseSearchRequest ele2 = new HouseSearchRequest();
        ele2.setFid("8a909977582f33a801583a1c8e6008b9");
        ele2.setRentWay(RentWayEnum.HOUSE.getCode());
        houseList.add(ele2);


        String rst = searchService.getHouseListByList(picSize, JsonEntityTransform.Object2Json(houseList));
        System.out.println(rst);
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  "+Thread.currentThread().getName()+"搜索一次需要时间：" + (bb - aa) );
    }


    @Test
    public void TestgetLandHouseList(){

        Long  aa = System.currentTimeMillis();
        LandHouseRequest info = new LandHouseRequest();
        info.setLandlordUid("8a9e9a9e544b614001544b6140a70000");
        info.setFid("8a90a2d454966955015498d68236003f");
        info.setRentWay(0);

        String rst = searchService.getLandHouseList(picSize, JsonEntityTransform.Object2Json(info));
        System.out.println(rst);
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  " + Thread.currentThread().getName() + "搜索一次需要时间：" + (bb - aa));
    }

    @Test
    public void TestcreatAllIndex() throws Exception{

        String aa = searchService.creatAllIndex();
        System.out.println(aa);
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();
        System.out.println("========");

        Thread.sleep(200000);
    }


    @Test
    public void TestcreatAllIndexByArea(){

        String aa = searchService.creatAllIndexByArea("DCQ");
        System.out.println(aa);
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();

    }

    @Test
    public void TestfreshIndexByHouseFid(){

        String aa = searchService.freshIndexByHouseFid("8a90a2d45ad0c4bf015ad0df080c009c");
        System.out.println(aa);
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();

    }




    @Test
    public void TestgetSuggestInfo(){

        searchService.getSuggestInfo("测试", "BJS");

        int length = 100;
        Long  aa = System.currentTimeMillis();
        for(int i=0;i<length;i++){
            String aaa = searchService.getSuggestInfo("朝阳", "beijing");
            System.out.println(aaa);
        }
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  "+Thread.currentThread().getName()+" 补全"+length+"次的平均时间：" + (bb - aa) /length );

    }


    @Test
    public void TestgetOneCommunityInfo(){

        searchService.getCommunityInfo("测试", "beijing");

        int length = 100;
        Long  aa = System.currentTimeMillis();
        for(int i=0;i<length;i++){
            String aaa = searchService.getOneCommunityInfo("chaoyang", "beijing");
            System.out.println(aaa);
        }
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  "+Thread.currentThread().getName()+" 补全"+length+"次的平均时间：" + (bb - aa) /length );

    }



    @Test
    public void TestgetCommunityName(){

       searchService.getCommunityInfo("测试","beijing");

        int length = 100;
        Long  aa = System.currentTimeMillis();
        for(int i=0;i<length;i++){
            String aaa = searchService.getCommunityInfo("chaoyang", "beijing");
            System.out.println(aaa);
        }
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  "+Thread.currentThread().getName()+" 补全"+length+"次的平均时间：" + (bb - aa) /length );

    }




    @Test
    public void TestgetComplateHouseName(){

        String rst = searchService.getComplateTermsCommunityName("chaoyang");
        int length = 10;
        Long  aa = System.currentTimeMillis();
        for(int i=0;i<length;i++){
            String aaa = searchService.getComplateTermsCommunityName("chaoyang");
            System.out.println(aaa);
        }
        System.out.println(rst);
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  " + Thread.currentThread().getName() + " 补全" + length + "次需要的平均时间：" + (bb - aa) / length);

    }




    @Test
    public void getChangzuIkList(){

        Long  aa = System.currentTimeMillis();

        String txt="边缘化\n" +
                "边缘原始细胞\n" +
                "CBD核心\n" +
                "城市核心地段\n" +
                "你在城心\n" +
                "我在你心\n" +
                "中央\n" +
                "中枢\n" +
                "重点\n" +
                "腹地\n" +
                "地标";
            ;
        System.out.println("当前长度"+txt.length());
        //评论内容过滤
        DataTransferObject dto =JsonEntityTransform.json2DataTransferObject(this.searchService.getChangzuIkList(txt));


        System.out.println(dto.toJsonString());
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();

    }



    @Test
    public void TestgetIkList(){

        Long  aa = System.currentTimeMillis();

        String txt="边缘化\n" +
                "边缘原始细胞\n" +
                "边缘地\n" +
                "边缘增强\n" +
                "边缘学科\n" +
                "边缘封闭区\n" +
                "边缘嵴\n" +
                "边缘性\n" +
                "边缘性龈炎\n" +
                "边缘效应\n" +
                "边缘波动\n" +
                "边缘海\n" +
                "边缘种植\n" +
                "边缘科学\n" +
                "边缘穿孔卡";
        System.out.println("当前长度"+txt.length());
      //评论内容过滤
		DataTransferObject dto =JsonEntityTransform.json2DataTransferObject(this.searchService.getIkList(txt));

        Long aaa = System.currentTimeMillis();
        int i = 10;
        for (int j = 0; j < i; j++) {
            Long t1 = System.currentTimeMillis();
            DataTransferObject dto1 =JsonEntityTransform.json2DataTransferObject(this.searchService.getIkList(txt));
            Long t2 = System.currentTimeMillis();
            System.out.println(t2-t1);
        }
        Long bbb = System.currentTimeMillis();
        System.out.println("平均"+(aaa-bbb)/i);
        List<String> listStrings = dto.parseData("ikList", new TypeReference<List<String>>() {
		});

        String rst = searchService.getIkList(txt);
        System.out.println(rst);
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  " + Thread.currentThread().getName() + " 过滤需要的时间：" + (bb - aa));

    }



    @Test
    public void TestgetHouseListInfo(){

        query();

    }


    @Test
    public void TestgetHouseListInfoThead() throws Exception{
        query();
        query();
        query();

        for (int i = 0; i < 2; i++) {
            Thread task = new Thread(){
                @Override
                public void run() {
                    query();
                }
            };
            SendThreadPool.execute(task);
        }

        Thread.sleep(500000);

    }



    private void query(){
        Long  aa = System.currentTimeMillis();
        HouseInfoRequest info = new HouseInfoRequest();
//        info.setCityCode("110100");
        info.setLimit(10);
        info.setQ("赵龙");
        info.setStartTime("2017-03-14");
        info.setEndTime("2017-04-04");
        String rst = searchService.getHouseListInfoAndSuggest(picSize, JsonEntityTransform.Object2Json(info),"6583c907-dcbf-9241-89cb-6f9020b0a5f0");
        System.out.println(rst);
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  "+Thread.currentThread().getName()+"搜索一次需要时间：" + (bb - aa) );
    }

    @Test
    public void getLocationConditionSort(){
        String locationCondition = searchService.getLocationConditionSort("110100");
        //String locationCondition = searchService.getLocationCondition("440100");
        System.err.println(locationCondition);
    }
    
    @Test
    public void getLocationNestingStructureTest(){
        String locationCondition = searchService.getLocationNestingStructure("110100");
        //String locationCondition = searchService.getLocationCondition("440100");
        System.err.println(locationCondition);
    }

    
    @Test
    public void getLocationConditionTest(){
    	String locationCondition = searchService.getLocationCondition("110100");
    	//String locationCondition = searchService.getLocationCondition("440100");
    	System.err.println(locationCondition);
    }


    @Test
    public void testest(){
        Long  aa = System.currentTimeMillis();
        HouseInfoRequest info = new HouseInfoRequest();
        info.setCityCode("110100");
//        info.setSortType(2);
//        info.setQ("8a90a2d454bc76280154bec8b5800080");
        String rst = searchService.getHouseListInfoAndSuggest(picSize, JsonEntityTransform.Object2Json(info),"6583c907-dcbf-9241-89cb-6f9020b0a5f0");
        System.out.println(rst);
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  "+Thread.currentThread().getName()+"搜索一次需要时间：" + (bb - aa) );
    }




    @Test
    public void getNewHouseLst(){
        Long  aa = System.currentTimeMillis();
        String rst = searchService.getNewHouseLst(picSize);
        System.out.println(rst);
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  "+Thread.currentThread().getName()+"搜索一次需要时间：" + (bb - aa) );
    }
    
    @Test
    public void getHouseListInfoTest(){
    	HouseInfoRequest request=new HouseInfoRequest();	
    	request.setStartTime(DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
    	request.setEndTime(DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
    	
    	String rst=searchService.getHouseListInfo(picSize,JsonEntityTransform.Object2Json(request));
    	System.err.println(rst);
    }
    
    @Test
    public void getOneHouseInfo(){
    	HouseSearchOneRequest requset = new HouseSearchOneRequest();
    	requset.setFid("8a90a2d4593f6e3301593f730a14000d");
    	requset.setRentWay(0);
    	requset.setPicSize(picSize);
    	requset.setStartTime("2017-04-15");
    	requset.setEndTime("2017-04-17");
    	String rst=searchService.getOneHouseInfo(JsonEntityTransform.Object2Json(requset));
    	System.err.println(rst);
    }
    
    
}
