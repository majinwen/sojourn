/**
 * @FileName: HouseManageService.java
 * @Package com.ziroom.minsu.services.house.api.inner
 * 
 * @author bushujie
 * @created 2016年4月3日 上午11:25:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.api.inner;

/**
 * <p>房东房源管理</p>
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
public interface HouseManageService {
	
	/**
	 * 
	 * 刷新房源操作
	 *
	 * @author bushujie
	 * @created 2016年4月3日 上午11:28:13
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public String refreshHouse(String houseBaseFid);

	/**
	 * 根据房源编号集合查询房源信息集合
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/21 10:55
	 */
	public String getHouseListByHouseSns(String houseSns);

	/**
	 * 根据房间编号集合查询房间信息集合
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/21 10:56
	 */
	public String getRoomListByRoomSns(String roomSns);

	/**
	 * 
	 * 查询房源列表
	 *
	 * @author bushujie
	 * @created 2016年4月3日 下午5:35:12
	 *
	 * @param paramJson
	 * @return
	 */
	public String houseList(String paramJson);
	/**
	 * 
	 * 查询合租房间列表
	 *
	 * @author bushujie
	 * @created 2016年4月3日 下午6:27:14
	 *
	 * @param paramJson
	 * @return
	 */
	public String houseRoomList(String paramJson);
	
	/**
	 * 
	 * 下架房源
	 *
	 * @author bushujie
	 * @created 2016年4月3日 下午6:44:53
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public String upDownHouse(String houseBaseFid,String landlordUid);
	
	/**
	 * 
	 * 下架房间
	 *
	 * @author bushujie
	 * @created 2016年4月3日 下午9:54:04
	 *
	 * @param houseRoomFid
	 * @param landlordUid
	 * @return
	 */
	public String upDownHouseRoom(String houseRoomFid,String landlordUid );
	
	/**
	 * 
	 * 房源或者房间出租日历
	 *
	 * @author bushujie
	 * @created 2016年4月5日 上午10:39:25
	 *
	 * @param paramJson
	 * @return
	 */
	public String leaseCalendar(String paramJson);
	
	/**
	 * 
	 * 房源或者房间特殊价格设置
	 *
	 * @author bushujie
	 * @created 2016年4月5日 下午2:00:49
	 *
	 * @param paramJson
	 * @return
	 */
	public String setSpecialPrice(String paramJson);
	
	/**
     * 按照星期设置房源或者房间的特殊价格
     * 
     * @author zl
     * @created 2016年9月9日
     * 
     * @param paramJson
     * @return
     */
	public String saveHousePriceWeekConf(String paramJson);
	
	/**
	 * 
	 * 房源详细信息查询
	 *
	 * @author bushujie
	 * @created 2016年4月6日 上午12:06:31
	 *
	 * @param houseBaseFid
	 * @param landlordUid
	 * @return
	 */
	public String houseDetail(String houseBaseFid,String landlordUid );
	
	/**
	 * 
	 * 根据房东uid查询已上架房源
	 *
	 * @author lunan
	 * @created 2016年10月9日 上午11:50:02
	 *
	 * @param landlordUid
	 * @return
	 */
	public String searchHouseBaseMsgByLandlorduid(String landlordUid);
	
	/**
	 * 
	 * 查询订单需要房源信息
	 *
	 * @author bushujie
	 * @created 2016年4月6日 下午8:23:34
	 *
	 * @param fid
	 * @param rentWay
	 * @return
	 */
	public String findOrderNeedHouseVo(String fid,Integer rentWay);

	/**
	 * 查询订单需要房源信息(加强版)
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/7 20:34
	 */
	public String findOrderNeedHouseVoPlus(String paramJson);
	
	/**
	 * 
	 * 修改房源出租类型
	 *
	 * @author bushujie
	 * @created 2016年4月6日 下午11:37:07
	 *
	 * @param paramJson
	 * @return
	 */
	public String modifyHouseLeaseType(String paramJson);
	
	/**
	 * 
	 * 小区名称列表
	 *
	 * @author bushujie
	 * @created 2016年4月18日 下午8:48:45
	 *
	 * @param landlordUid
	 * @return
	 */
	public String communityNameList(String landlordUid);
	
	/**
	 * 
	 * 查询房源房间列表
	 *
	 * @author liujun
	 * @created 2016年4月19日 上午9:40:54
	 *
	 * @param paramJson
	 * @return
	 */
	public String searchHouseRoomList(String paramJson);
	
	/**
	 * 
	 * 查询房东收益
	 *
	 * @author liujun
	 * @created 2016年4月26日 下午2:28:36
	 *
	 * @param landlordUid
	 * @return
	 */
	public String searchLandlordRevenue(String landlordUid);
	
	/**
	 * 
	 * 查询房东房源月收益列表
	 *
	 * @author liujun
	 * @created 2016年4月26日 下午2:28:36
	 *
	 * @param paramJson
	 * @return
	 */
	public String searchHouseRevenueListByLandlordUid(String paramJson);
	
	/**
	 * 
	 * 查询房东房源各月收益列表
	 *
	 * @author liujun
	 * @created 2016年4月27日 下午2:01:53
	 *
	 * @param paramJson
	 * @return
	 */
	public String searchMonthRevenueListByHouseBaseFid(String paramJson);
	
	/**
	 * 
	 * 查询房东月收益列表
	 *
	 * @author liujun
	 * @created 2016年4月27日 下午2:01:53
	 *
	 * @param paramJson
	 * @return
	 */
	public String searchMonthRevenueList(String paramJson);
	
	/**

	 * 校验房源或房间是否存在
	 * @param paramJson
	 * @return
	 */
	public String checkHouseOrRoom(String hosueCheck);
	/**
	 * 
	 * 房东端房源列表（以房源的维度）
	 *
	 * @author bushujie
	 * @created 2016年6月14日 下午2:11:45
	 *
	 * @param paramJson
	 * @return
	 */
	public String searchLandlordHouseList(String paramJson);
	
	/**
	 * 更新房源智能锁状态
	 *
	 * @author liujun
	 * @created 2016年6月23日
	 *
	 * @param paramJson
	 * @return
	 */
	public String bindSmartLock(String paramJson);

	/**
	 * 
	 * 保存房源智能锁
	 *
	 * @author jixd
	 * @created 2016年6月24日 上午10:38:09
	 *
	 * @param paramJson
	 * @return
	 */
	public String saveHouseSmartlock(String paramJson);
	/**
	 * 
	 * 更新智能锁记录
	 *
	 * @author jixd
	 * @created 2016年6月24日 上午10:38:24
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseSmartlock(String paramJson);
	/**
	 * 
	 * 查找记录
	 *
	 * @author jixd
	 * @created 2016年6月24日 上午10:38:38
	 *
	 * @param paramJson
	 * @return
	 */
	public String findHouseSmartlock(String paramJson);
	
	/**
	 * 
	 * 房源是否有智能锁
	 *
	 * @author liujun
	 * @created 2016年6月24日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public String isHasSmartLock(String houseBaseFid);

	/**
	 * 
	 * 计算 房源和房间的数目
	 *
	 * @author liyingjie
	 * @created 2016年6月29日
	 *
	 * @param uid
	 * @return
	 */
	String countHouseRoomNum(String uid);

	/**
	 * 
	 * 根据
	 *
	 * @author liujun
	 * @created 2016年7月25日
	 *
	 * @param paramJson
	 * @return
	 */
	public String findHouseBaseExtListByCondition(String paramJson);
	
	/**
	 * 
	 * 查询图片列表  根据房源或者房间fid
	 *
	 * @author jixd
	 * @created 2016年9月1日 下午6:35:55
	 *
	 * @param paramJson
	 * @return
	 */
	String findPicListByHouseAndRoomFid(String paramJson);
	
	/**
	 * 
	 * 分租发布点击下一步： 更新房源步骤
	 *
	 * @author yd
	 * @created 2016年9月8日 上午10:30:05
	 *
	 * @param houseFid
	 * @return
	 */
	public String updateHouseBaseOpSeq(String houseBase);

	/**
	 * 查询周末价格
	 *
	 * @author jixd
	 * @created 2016年10月18日 上午10:30:05
	 * @param paramJson
	 * @return
	 */
	String findWeekPriceByFid(String paramJson);
	
	/**
	 * 查询房东最后一次修改日历时间
	 * @author zl
	 * @param landlordUid
	 * @return
	 */
	public String getLastModifyCalendarDate(String landlordUid);
	
	/**
	 * 
	 * 更新房源周末价格信息列表
	 * 仅限于priceVal isDel isValid字段
	 *
	 * @author liujun
	 * @created 2016年12月7日
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHousePriceWeekListByFid(String paramJson);
	
}
