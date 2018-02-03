package com.ziroom.minsu.services.cms.api.inner;

/**
 * <p>
 * 活动信息proxy
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年6月23日
 * @since 1.0
 * @version 1.0
 */
public interface ActivityService {


	/**
	 * 获取当前的返现活动
	 * @author
	 * @param cachType
	 * @return
	 */
	String getCashbackList(Integer cachType);


	/**
	 * 获取最后一条种子房东免佣金的活动
	 * @author afi
	 * @create 2016年10月23日
	 * @return
	 */
	public String getSeedActivityLast();



	/**
	 * 根据活动sn查询活动
	 * @author lishaochuan
	 * @create 2016年6月24日下午8:07:16
	 * @return
	 */
	public String getActivityBySn(String activitySn);

	/**
	 * 
	 * 根据活动组查询活动
	 *
	 * @author lunan
	 * @created 2016年10月10日 下午5:04:11
	 *
	 * @param groupSn
	 * @return
	 */
	public String getActivityByGroupSn(String groupSn);

	
	/**
	 * 查询有效的活动列表
	 * @author lishaochuan
	 * @create 2016年6月23日下午8:15:16
	 * @return
	 */
	public String getUnderwayActivityList();

    /**
     * 当前的有效的活动
     * @author afi
     * @return
     */
    public String getRealUnderwayActivityList();




	/**
	 * 分页查询活动信息
	 * @author lishaochuan
	 * @create 2016年6月23日下午8:12:27
	 * @param paramJson
	 * @return
	 */
	public String getActivityVoListByCondiction(String paramJson);

	/**
	 * 保存活动信息
	 * @author lishaochuan
	 * @create 2016年6月23日下午3:53:26
	 * @param paramJson
	 * @return
	 */
	public String saveActivity(String paramJson, String cityCode);

	/**
	 * 修改活动信息
	 * @author lishaochuan
	 * @create 2016年6月23日下午3:53:40
	 * @param paramJson
	 * @param cityCode
	 * @return
	 */
	public String updateByActivity(String paramJson,String cityCode);


    /**
     * 终止活动
     * @author afi
     * @param paramJson
     * @return
     */
    public String endActivity(String paramJson);

	
	/**
	 * 启动活动
	 * @author lishaochuan
	 * @create 2016年6月23日下午6:06:20
	 * @param paramJson
	 * @return
	 */
	public String enableActivity(String paramJson);


	/**
	 * 保存优惠券活动
	 * @author liyingjie
	 * @create 2016年6月23日下午6:06:20
	 * @param paramJson
	 * @return
	 */
	String saveActCoupon(String paramJson, String cityCode,String houseSns);


	/**
	 * 修改活动信息
	 * @author afi
	 * @create 2016年9月13日下午3:53:26
	 * @param paramJson
	 * @return
	 */
	String updateActCoupon(String paramJson, String cityCode);
	
	/**
	 * 
	 * 保存礼品活动 
	 *
	 * @author yd
	 * @created 2016年10月9日 下午9:14:44
	 *
	 * @param paramJson
	 * @param cityCode
	 * @return
	 */
	public String saveGiftActivity(String paramJson, String cityCode);
	

	/**
	 * 修改 活动 (兼容 礼品的修改)
	 * @author yd
	 * @create 2016年6月23日下午3:53:40
	 * @param paramJson
	 * @return
	 */
	public String updateGiftAcByActivity(String paramJson,String cityCode) ;


	/**
	 * 查询首单立减活动详情
	 * @Description:
	 * @Author:lusp
	 * @Date: 2017/6/5 11:20
	 * @Params:
	 */
	public String getSDLJActivityInfo();

	/**
	 * 根据活动组号 查询活动列表
	 * @author jixd
	 * @created 2017年06月15日 11:31:01
	 * @param
	 * @return
	 */
	String listActivityByGroupSn(String groupSn);

	/**
	 * 查询符合条件的活动计算金额
	 *
	 * @param paramJson
	 * @return
	 * @author jixd
	 * @created 2017年10月16日 16:14:46
	 */
	String listActFeeConditionForZrp(String paramJson);


	/**
	 * 根据uid查询用户是否参与过邀请活动
	 * 如果参加过会返回邀请人的uid
	 * 必须同时传入uid和inviteSource
	 * inviteSource用来确定查询参与活动的类型,在controller里写上对应inviteSource
	 * @author yanb
	 * @created 2017年12月05日 14:27:21
	 * @param
	 * @return
	 */
	String checkUserInviteStateByUid(String paramJson);

	/**
	 * 遍历判断订单的uid是否满足增加积分的要求
	 * @param * @param null
	 * @return
	 * @author yanb
	 * @created 2017年12月13日 15:11:33
	 */
	public String checkUserInviteStateByList(String paramJson);

	/**
	 * 根据ActSn更新活动信息
	 * @author yanb
	 * @param paramJson
	 * @return
	 */
	public String updateActivityByActSn(String paramJson);
}

