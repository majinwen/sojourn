/**
 * @FileName: HouseBusinessService.java
 * @Package com.ziroom.minsu.services.house.api.inner
 * 
 * @author bushujie
 * @created 2016年7月6日 下午10:31:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.api.inner;

/**
 * <p>房源商机相关接口</p>
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
public interface HouseBusinessService {
	
	/**
	 * 
	 * 分页房源商机列表
	 *
	 * @author bushujie
	 * @created 2016年7月6日 下午10:33:25
	 *
	 * @param paramJson
	 * @return
	 */
	public String houseBusinessList(String paramJson);
	
	/**
	 * 
	 * 插入房源商机
	 *
	 * @author bushujie
	 * @created 2016年7月8日 上午11:04:42
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertHouseBusiness(String paramJson);
	
	/**
	 * 
	 * 根据房东查询地推管家员工号
	 *
	 * @author bushujie
	 * @created 2016年7月8日 上午11:55:21
	 *
	 * @param paramJson
	 * @return
	 */
	public String findDtGuardCodeByLandlord(String paramJson);
	
	/**
	 * 
	 * 查询商机信息详情
	 *
	 * @author bushujie
	 * @created 2016年7月9日 下午12:21:02
	 *
	 * @param paramJson
	 * @return
	 */
	public String findHouseBusinessDetailByFid(String businessFid );
	
	/**
	 * 
	 * 更新房源商机信息
	 *
	 * @author bushujie
	 * @created 2016年7月9日 下午5:28:34
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseBusiness(String paramJson);
	
	/**
	 * 
	 * 删除房源商机
	 *
	 * @author bushujie
	 * @created 2016年7月9日 下午5:49:48
	 *
	 * @param businessFid
	 * @return
	 */
	public String delHouseBusiness(String businessFid);
	 /**
     * 
     * 条件查询 商机扩展信息
     * 
     * 查询对象不能为null
     *
     * @author yd
     * @created 2016年7月9日 下午2:27:25
     *
     * @param houseBusinessMsgExtDto
     * @return
     */
    public String findHouseBusExtByCondition(String houseBusinessMsgExtDto);
    
    /**
     * 
     * uid查询房源数量
     *
     * @author bushujie
     * @created 2016年7月22日 下午1:47:46
     *
     * @param uid
     * @return
     */
    public String findHouseCountByUid(String uid);
}
