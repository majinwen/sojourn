/**
 * @FileName: ConfCityService.java
 * @Package com.ziroom.minsu.services.basedata.api.inner
 * 
 * @author liyingjie
 * @created 2016年3月21日 下午8:12:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.api.inner;

/**
 * <p>国家、城市、省份、区域 接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public interface ConfCityService {

	/**
	 * 获取所有的城市map
	 * @author afi
	 * @return
	 */
	public String getAllCityMap();

    /**
     * 获取所有城市的列表
     * @author afi
     * @return
     */
    public String getAllCityList();




	/**
	 * 获取开通的城市列表和热门城市
	 * @author afi
	 * @return
	 */
	public String getOpenCityAndHot();
	
	/**
	 * 获取开通的城市列表和热门城市(房东)
	 * @author lunan
	 * @return
	 */
	public String getOpenCityAndHotLandlord();
	
	/**
	 * 获取开通的城市列表和热门城市(房客)
	 * @author lunan
	 * @return
	 */
	public String getOpenCityAndHotTenant();


    /**
     * 获取开通的城市列表
     * @author afi
     * @return
     */
    public String getOpenCity();


	/**
	 *
	 * 获取开通的国家列表(房东)
	 *
	 * @author lunan
	 * @created 2016年9月26日 下午9:55:51
	 *
	 * @return
	 */
	String getOpenNationLandlord();


	/**
	 * 获取指定国家下开通的城市(房东)
	 * @author lishaochuan
	 * @create 2017/2/27 19:42
	 * @param
	 * @return
	 */
	String getOpenCityLandlord4Nation(String code);


    /**
     * 
     * 获取开通的城市列表(房东)
     *
     * @author lunan
     * @created 2016年9月26日 下午9:55:51
     *
     * @return
     */
    public String getOpenCityLandlord();
    
    /**
     * 
     * 获取开通的城市列表(房客)
     *
     * @author lunan
     * @created 2016年9月26日 下午10:29:51
     *
     * @return
     */
    public String getOpenCityTenant();
    /**
     * 获取开通的城市（返回map）
     * @author lishaochuan
     * @create 2016年6月26日下午1:03:18
     * @return
     */
    public String getOpenCityMap();

	/**
	 * 
	 * 新增 国家、省份、城市等配置 数据
	 *
	 * @author liyingjie
	 * @created 2016年3月21日 
	 *
	 * @param paramJson
	 */
	public void insertConfCityRes(String paramJson);
	
	/**
	 * 
	 * 更新 国家、省份、城市等配置 数据
	 *
	 * @author liyingjie
	 * @created 2016年3月21日 
	 *
	 * @param paramJson
	 */
	String updateConfCityByFid(String paramJson);
	
	
	/**
	 * 
	 * 查询 国家、省份、城市等配置 数据资源树结构查询
	 *
	 * @author bushujie
	 * @created 2016年3月21日 
	 *
	 * @return
	 */
	String confCityTreeVo();


	String confCityOnlyTreeVo();
	
	
	/**
	 * 
	 * 根据id 查询 国家、省份、城市等配置 数据
	 *
	 * @author liyingjie
	 * @created 2016年3月21日 
	 *
	 * @param paramJson
	 */
	String findConfCityByFid(String paramJson);
	
	/**
	 * 
	 * 根据 fid 查询 子节点集合   列表
	 *
	 * @author liyingjie
	 * @created 2016年3月21日 
	 *
	 * @param paramJson
	 * @return
	 */
	public String searchNodeListByFid(String paramJson);

	/**
	 * 级联查询下级行政区域列表
	 *
	 * @author liujun
	 * @created 2016-3-22 下午1:51:38
	 *
	 * @param pCode
	 * @return
	 */
	public String searchDistricts(String pCode);
	
	/**
	 * 
	 * 房东端 查询城市下级区域列表
	 *
	 * @author jixd
	 * @created 2016年9月27日 上午12:32:55
	 *
	 * @param pCode
	 * @return
	 */
	public String searchAreaListForLan(String pCode);
	
	/**
	 * 
	 * 城市多级数据结构-开通城市
	 *
	 * @author bushujie
	 * @created 2016年3月28日 下午2:58:23
	 *
	 * @return
	 */
	public String getConfCitySelect();
	
	/**
	 * 城市多级数据结构(房东开通)
	 *
	 * @author lunan
	 * @created 2016年9月24日 下午6:40:18
	 *
	 * @return
	 */
	public String getConfCitySelectForLandlord();
	
	/**
	 * 
	 * 城市多级数据结构(房客开通)
	 *
	 * @author lunan
	 * @created 2016年9月24日 下午6:41:18
	 *
	 * @return
	 */
	public String getConfCitySelectForTenant();
	
	/**
	 * 
	 * 城市多级数据结构-所有城市
	 *
	 * @author liujun
	 * @created 2016年12月15日
	 *
	 * @return
	 */
	public String getConfCitySelectForAll();

	/**
	 * 根据城市code获取城市名称
	 * @author jixd on 2016年4月14日
	 */
	public String getCityNameByCode(String code);
	
	
	/**
	 * 根据城市codeList获取城市名称
	 * @author lishaochuan
	 * @create 2016年5月26日上午12:08:40
	 * @param codeList
	 * @return
	 */
	public String getCityNameByCodeList(String paramJson);
	

	/**
	 * 獲取城市信息
	 * @author liyingjie
	 * @return
	 */
	public String confCityTreeDataVo();
	
	/**
	 * 
	 * 根据code查询城市
	 *
	 * @author bushujie
	 * @created 2016年8月8日 下午5:30:59
	 *
	 * @param code
	 * @return
	 */
	public String getConfCityByCode(String code);

	/**
	 *
	 * 查询已建立档案的开通城市列表
	 *
	 * @author liujun
	 * @created 2016年11月10日
	 *
	 * @return
	 */
	public String getOpenCityWithFile();
	
	
	/**
	 * 查询城市下的特色房源
	 * TODO
	 *
	 * @author zl
	 * @created 2016年12月1日 下午5:46:11
	 *
	 * @param countryCode
	 * @param provinceCode
	 * @param cityCode
	 * @return
	 */
	public String getCityFeatureHouseTypes(String countryCode,String provinceCode,String cityCode);
	
	
	/**
	 * 查询所有的有特色房源的城市的房源
	 * TODO
	 *
	 * @author zl
	 * @created 2016年12月1日 下午5:58:03
	 *
	 * @return
	 */
	public String getAllCityFeatureHouseTypes();
	
	/**
	 * 根据有效状态查询所有的特色标识列表
	 * @author zl
	 * @created 2017年1月9日 下午5:58:03
	 * @param isValid
	 * @return
	 */
	public String getAllFeatureTags(Boolean isValid,String templateFid);
	
	
	/**
	 * 更新特色标识
	 * @param paramJson
	 * @return
	 */
	public String updateFeatureTagByFid(String paramJson);
	
	
	/**
	 * 新增特色标识
	 * @param paramJson
	 * @return
	 */
	public String addFeatureTag(String paramJson);
	
	/**
	 * 
	 * 查询国家码列表
	 *
	 * @author bushujie
	 * @created 2017年4月11日 下午3:08:32
	 *
	 * @return
	 */
	public String findNationCodeList();
	
}
