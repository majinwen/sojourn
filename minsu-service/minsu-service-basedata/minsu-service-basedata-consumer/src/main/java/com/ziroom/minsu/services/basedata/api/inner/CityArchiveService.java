/**
 * @FileName: CityArchiveService.java
 * @Package com.ziroom.minsu.services.basedata.api.inner
 * 
 * @author lunan
 * @created 2016年11月07日
 *
 */
package com.ziroom.minsu.services.basedata.api.inner;


/**
 * <p>城市档案接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
public interface CityArchiveService {

	/**
	 * 获取城市档案列表
	 * @author lunan
	 * @create 2016年11月07日上午
	 */
	public String getCityArchiveList(String paramJson);

	/**
	 * 查询城市景点商圈
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/9 10:15
	 */
	public String getRegionList(String paramJson);

	/**
	 * 保存或者修改景点商圈
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/9 11:53
	 */
	public String saveOrUpRegion(String paramJson);

	/**
	 * 查找一条景点商圈内容
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/9 19:24
	 */
	public String getRegion(String paramJson);

	/**
	 * 保存或者修改景点商圈推荐项
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/9 19:21
	 */
	public String saveOrUpItem(String paramJson);

	/**
	 * 保存or修改城市档案
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/10 18:32
	 */
	public String saveOrUpArchive(String paramJson);

	/**
	 * 查找一条推荐项内容
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/9 19:47
	 */
	public String getRegionItem(String itemFid);

	/**
	 * 查找一条城市档案
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/11 10:48
	 */
	public String getArchive(String fid);

	/**
	 * 查询景点商圈及其下辖的推荐项
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/11 11:40
	 */
	public String getRegionItems(String paramJson);

	/**
	 * 通过景点商圈fid查询其已存在推荐项
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/14 9:41
	 */
	public String getItemsByHotRegionFid(String paramJson);
	
}
