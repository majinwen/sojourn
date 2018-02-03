package com.ziroom.minsu.services.basedata.api.inner;


/**
 * 
 * <p>景点商圈管理接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public interface HotRegionService {
	
	/**
	 * 
	 * 条件查询景点商圈列表
	 *
	 * @author liujun
	 * @created 2016-3-21 下午7:08:37
	 *
	 * @param paramJson
	 * @return
	 */
	public String searchHotRegions(String paramJson);

	/**
	 * 新增景点商圈
	 *
	 * @author liujun
	 * @created 2016-3-22 上午10:20:00
	 *
	 * @param paramJson
	 * @return
	 */
	public String saveHotRegion(String paramJson);

	/**
	 * 修改景点商圈
	 *
	 * @author liujun
	 * @created 2016-3-22 上午10:53:25
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHotRegion(String paramJson);

	/**
	 * 根据景点商圈业务fid查询景点商圈
	 *
	 * @author liujun
	 * @created 2016-3-22 上午11:03:16
	 *
	 * @param hotRegionFid
	 * @return
	 */
	public String searchHotRegionByFid(String hotRegionFid);

	/**
	 * 启用|禁用景点商圈
	 *
	 * @author liujun
	 * @created 2016-3-22 上午11:23:54
	 *
	 * @param hotRegionJson
	 * @return
	 */
	public String editHotRegionStatus(String hotRegionJson);
	
	/**
	 * 
	 * 根据城市code查询已建立档案的景点商圈列表
	 *
	 * @author liujun
	 * @created 2016年11月10日
	 *
	 * @param cityCode
	 * @return
	 */
	public String getListWithFileByCityCode(String cityCode);
	
	/**
	 * 
	 * 获取景点商圈的有效区域半径集合
	 *
	 * @author liujun
	 * @created 2016年11月12日
	 *
	 * @return
	 */
	public String getValidRadiiMap();

	/**
	 * 查询景点商圈以及其描述和内容
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/16 17:56
	 */
	public String getRegionExtVoByRegionFid(String regionFid);

}
