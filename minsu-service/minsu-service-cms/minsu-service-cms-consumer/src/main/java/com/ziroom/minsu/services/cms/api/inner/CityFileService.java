/**
 * @FileName: CityFileService.java
 * @Package com.ziroom.minsu.services.cms.api.inner
 * 
 * @author bushujie
 * @created 2016年11月7日 下午7:30:28
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.api.inner;

/**
 * <p>城市档案相关业务接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public interface CityFileService {
	
	/**
	 * 
	 * 专栏模板列表
	 *
	 * @author bushujie
	 * @created 2016年11月7日 下午7:32:23
	 *
	 * @param paramJson
	 * @return
	 */
	public String columnTemplateList(String paramJson);
	
	/**
	 * 
	 * 插入专栏模板
	 *
	 * @author bushujie
	 * @created 2016年11月7日 下午7:36:53
	 *
	 * @param paramJson
	 */
	public String insertColumnTemplate(String paramJson);
	
	/**
	 * 
	 * 模板fid查询专栏模板
	 *
	 * @author bushujie
	 * @created 2016年11月8日 下午7:55:40
	 *
	 * @param paramJson
	 * @return
	 */
	public String getColumnTemplateByFid(String tempFid);
	
	/**
	 * 
	 * 更新专栏模板
	 *
	 * @author bushujie
	 * @created 2016年11月8日 下午8:39:11
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateColumnTemplate(String paramJson);
	
	/**
	 * 
	 * 城市专栏列表
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午4:15:31
	 *
	 * @param paramJson
	 * @return
	 */
	public String columnCityList(String paramJson);
	
	/**
	 * 
	 * 插入城市专栏
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午4:16:41
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertColumnCity(String paramJson);
	
	/**
	 * 
	 * fid查询城市专栏
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午4:17:44
	 *
	 * @param fid
	 * @return
	 */
	public String getColumnCityByFid(String fid);
	
	/**
	 * 
	 * 更新城市专栏
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午4:18:56
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateColumnCity(String paramJson);
	
	/**
	 * 
	 * 查询所有注册的模板
	 *
	 * @author bushujie
	 * @created 2016年11月9日 下午7:35:43
	 *
	 * @return
	 */
	public String findAllRegTemplate();
	
	/**
	 * 
	 * 分页查询专栏商圈景点列表
	 *
	 * @author bushujie
	 * @created 2016年11月10日 下午6:37:37
	 *
	 * @return
	 */
	public String columnRegionList(String paramJson);
	
	/**
	 * 
	 * 插入专栏景点商圈
	 *
	 * @author bushujie
	 * @created 2016年11月14日 上午10:52:00
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertColumnRegion(String paramJson);
	
	/**
	 * 
	 * 初始化更新专栏景点商圈数据
	 *
	 * @author bushujie
	 * @created 2016年11月14日 下午5:32:33
	 *
	 * @param fid
	 * @return
	 */
	public String initUpColumnRegion(String fid);
	
	/**
	 * 
	 * 更新专栏景点商圈数据
	 *
	 * @author bushujie
	 * @created 2016年11月15日 下午4:49:41
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateColumnRegion(String paramJson);
	
	/**
	 * 
	 * fid删除景点商圈
	 *
	 * @author bushujie
	 * @created 2016年11月15日 下午9:03:15
	 *
	 * @param fid
	 * @return
	 */
	public String delColumnRegion(String fid);

	/**
	 * 根据城市code查询城市以及其下辖专栏
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/17 16:05
	 */
	public String getCityRegionsByCityCode(String cityCode);
	
	/**
	 * 
	 *  城市专栏查询包含景点商圈列表
	 *
	 * @author bushujie
	 * @created 2016年11月18日 下午4:32:31
	 *
	 * @param columnCityFid
	 * @return
	 */
	public String findColumnRegionUpVoListByCityFid(String columnCityFid);
	
	/**
	 * 
	 * 调整专栏景点排序
	 *
	 * @author bushujie
	 * @created 2017年1月6日 下午2:05:13
	 *
	 * @param paramJson
	 * @return
	 */
	public String upColumnRegionOrderSort(String paramJson);
}
