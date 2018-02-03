package com.ziroom.minsu.services.search.api.inner;

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
public interface SearchService {
	
	

//###############  查询    ###### 房源数据  #######  开始   #################################################################################
	
	/**
	 * 
	 *	查询单个房源
	 *
	 * @author zl
	 * @created 2017年6月7日 上午11:34:38
	 *
	 * @param searchOneRequestJson 
	 * @return
	 */
	String getOneHouseInfo(String searchOneRequestJson);


    /**
     * 获取最新房源
     * @author afi
     * @param picSize
     * @return
     */
    String getNewHouseLst(String picSize);

    /**
     * 根据fid列表，时间段，uid 获取房源列表
     * @author afi
     * @param houseListRequsetJson
     * @return
     */
    String getHouseListByListInfo(String houseListRequsetJson);

    /**
     * 根据 fid列表 及图片大小  获取房源列表
     * @param picSize
     * @param houseJson
     * @return
     */
    String getHouseList(String picSize,Integer iconType,String houseJson);

    /**
     * 根据 fid列表 获取房源列表（收藏）
     * @param picSize
     * @param houseJson
     * @return
     */
    String getHouseListByList(String picSize,String houseJson);     


    /**
     * 根据 搜索条件 获取房源信息
     * @author afi
     * @param picSize
     * @param jsonDto
     * @return
     */
    String getHouseListInfo(String picSize,String jsonDto);


    /**
     * 根据 搜索条件 获取房源信息当没有数据时添加到建议
     * @author afi
     * @param picSize
     * @param jsonDto
     * @return
     */
    String getHouseListInfoAndSuggest(String picSize,String jsonDto,String uid);

    /**
     * 根据 房东uid，房源fid，出租方式  获取房东的房源列表
     * @param picSize
     * @param landJson
     * @return
     */
    String getLandHouseList(String picSize,String landJson);
    
    
    /**
     * 
     * 查找top50房源列表
     *
     * @author zl
     * @created 2017年3月17日 下午3:01:21
     *
     * @param top50HouseListRequestJson
     * @param picSize
     * @param uid
     * @return
     */
    String getTOP50List(String top50HouseListRequestJson,String picSize,String uid);
    
    
    /**
     * 根据 房东uid，起止时间  获取房东的分享房源列表
     * 
     * @author zl
     * @created 2017年3月28日 下午5:12:43
     *
     * @param picSize
     * @param landJson
     * @return
     */
    String getLandShareHouseList(String picSize,String landJson);
    
    /**
     * 
     * 根据品牌编码列表查询房源
     *
     * @author zl
     * @created 2017年4月1日 下午3:45:06
     *
     * @param picSize
     * @param brandSnListJson
     * @return
     */
    String getHouseListByBrandSnList(String picSize,String brandSnListJson);   
    
    
//###############  查询    ###### 房源数据  #######  结束   #################################################################################
    
    
//###############  查询    ###### 补全数据及分词数据  #######  开始   #################################################################################

    /**
     * 获取补全信息 Terms
     * @param pre
     * @return
     */
    String getComplateTermsCommunityName(String pre);


    /**
     * 获取一条补全信息
     * @param suggest
     * @return
     */
    String getOneCommunityInfo(String suggest,String cityCode);

    /**
     * 获取补全信息
     * @param suggest
     * @return
     */
    String getCommunityInfo(String suggest,String cityCode);


    /**
     * 获取房客端的补全信息
     * @param suggest
     * @return
     */
    String getSuggestInfo(String suggest,String cityCode);

    /**
     * 获取分词之后的数据
     * @param pre
     * @return
     */
    String getIkList(String pre);


    /**
     * 获取分词之后的数据
     * @param txt
     * @return
     */
    String getChangzuIkList(String txt);
    
    
//###############  查询    ###### 补全数据及分词数据  #######  结束   #################################################################################
    
//###############  更新    ###### 房源数据及补全数据  #######  开始   #################################################################################
    /**
     * 刷新房源的索引信息
     * @param houseFid
     * @return
     */
    String freshIndexByHouseFid(String houseFid);



    /**
     * 刷新某个区域的房源信息
     * @param areaCode
     * @return
     */
    String creatAllIndexByArea(String areaCode);

    /**
     * 全量刷新当前索引信息
     * @author afi
     * @return
     */
    String creatAllIndex();


    /**
     * 全量刷新用户的联想词信息
     * @author afi
     * @return
     */
    String creatAllSuggest();
    
    /**
     * 全量刷新数据库的楼盘信息
     * @author afi
     * @return
     */
    String creatAllCommunityInfoDb();


    /**
     * 同步楼盘信息
     * @param cityCodes
     * @return
     */
    String syncHousesInfoByCode(String cityCodes);

//###############  更新    ###### 房源数据及补全数据  #######  结束   #################################################################################


//###############  查询    ###### 基础数据  #######  开始   #################################################################################
    /**
     * 获取搜索位置条件
     * @author lishaochuan
     * @create 2016年8月23日下午2:21:21
     * @param cityCode
     * @return
     */
    String getLocationCondition(String cityCode);
    
    /**
     * 
     * 获取搜索位置条件（统一嵌套的数据结构）
     *
     * @author zhangyl
     * @created 2017年8月10日 下午5:50:33
     *
     * @param cityCode
     * @return
     */
    String getLocationNestingStructure(String cityCode);

    /**
     * 获取搜索位置条件 包含排序规则
     * @author afi
     * @create 2016年9月19日下午2:21:21
     * @param cityCode
     * @return
     */
    String getLocationConditionSort(String cityCode);
    
    /**
     * 获取当前模板的cod的值信息
     * @param code
     * @param templateFid
     * @return
     */
    String getDicItemListByCodeAndTemplate(String code, String templateFid);
    
    
    /**
     * 默认配置枚举列表（有效的）
     * @param templateFid
     * @param dicCode
     * @return
     */
    String selectEffectiveDefaultEnumList( String dicCode,String templateFid);
	
    /**
     * 按照有效的枚举查询商圈、景点。。。
     * @param dicCode
     * @param templateFid
     * @param cityCode
     * @param regionType
     * @return
     */
	String getHotRegionListByEffectiveEnum( String dicCode,String templateFid,String cityCode,String regionType );
	
	
	
	/**
	 * 按照有效的枚举查询有效的商圈、景点等类型下有效的景点商圈
	 * @author zl
	 * @param dicCode
	 * @param templateFid
	 * @param cityCode
	 * @param regionType
	 * @return
	 */
	String getHotRegionListByEffectiveEnumStatus( String dicCode,String templateFid,String cityCode,String regionType );
	
	
	 /**
     * 查询城市下已经上线的房源类型
     * @param cityCode
     * @return
     */
    String getHouseTypeByCityCode(String cityCode);   
    
    /**
     * 
     * 查询静态资源
     *
     * @author zl
     * @created 2017年3月17日 下午6:02:22
     *
     * @param resCode
     * @return
     */
    String getStaticResourceByResCode(String resCode);
    
    
    
    
  //###############  查询    ###### 基础数据  #######  结束  #################################################################################

    
    /**
     * 查询类似房源
     * @param picSize
     * @param landJson
     * @return
     */
    String getSimilarHouse(String picSize,String landJson);



}
