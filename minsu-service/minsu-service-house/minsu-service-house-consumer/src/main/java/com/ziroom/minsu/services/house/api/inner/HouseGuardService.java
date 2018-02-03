package com.ziroom.minsu.services.house.api.inner;


/**
 * 
 * <p>房源管家关系操作接口</p>
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
public interface HouseGuardService {

	/**
	 * 分页查询房源管家关系列表页
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param paramJson
	 * @return
	 */
	public String searchHouseGuardList(String paramJson);

	/**
	 * 根据房源逻辑id查询房源管家关系详情
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public String searchHouseGuardDetail(String houseBaseFid);

	/**
	 * 分页查询房源管家关系日志列表页
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param paramJson
	 * @return
	 */
	public String searchHouseGuardLogList(String paramJson);

	/**
	 * 批量修改房源管家关系
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param paramJson
	 * @return
	 */
	public String batchMergeHouseGuardRel(String paramJson);
	
	/**
	 * 
	 * 根据房源逻辑id查询房源维护管家关系
	 *
	 * @author liujun
	 * @created 2016年7月5日
	 *
	 * @param fid
	 * @return
	 */
	public String findHouseGuardRelByHouseBaseFid(String houseBaseFid);
	
	/**
	 * 
	 * 条件查询 房源和维护管家  内联
	 *
	 * @author yd
	 * @created 2016年7月12日 下午7:51:39
	 *
	 * @param paramJson
	 * @return
	 */
	public String findHouseGuardByCondition(String paramJson);

	/**
	 * 保存维护管家
	 * @author liyingjie
	 * @param paramJson
	 * @return
	 */
	public String saveHouseGuardRel(String paramJson);

	/**
	 * 更新维护管家
	 * @author liyingjie
	 * @param paramJson
	 * @return
	 */
	public String updateHouseGuardByHouseFid(String paramStr);


	/**
	 * 根据管家姓名查询，房源维护管家关联表
	 * @author lisc
	 * @param paramsJson
	 * @return
     */
	public String findHouseGuardRelByCondition(String paramsJson);
}
