package com.ziroom.minsu.services.cms.api.inner;

/**
 * <p>活动提醒信息记录相关操作</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lusp on 2017年6月6日
 * @since 1.0
 * @version 1.0
 */
public interface ActivityRemindLogService {

	/**
	 * @Description: 插入活动提醒信息记录表（不重复插入）
	 * @Author:lusp
	 * @Date: 2017/6/6 17:21
	 * @Params: paramJson
	 */
	public String insertActivityRemindLogIgnore(String paramJson);

	/**
	 * @Description: 根据用户uid 逻辑删除一条记录
	 * @Author:lusp
	 * @Date: 2017/6/6 17:22
	 * @Params: paramJson
	 */
	public String deleteActivityRemindLogByUid(String paramJson);

	/**
	 * @Description: 分页查询已经触发通知的新用户信息
	 * @Author:lusp
	 * @Date: 2017/6/7 11:18
	 * @Params: paramJson
	 */
	public String queryRemindUidInfoByPage(String paramJson);

	/**
	 * @Description: 根据用户uid 更新已发送次数 和下次发送时间
	 * @Author:lusp
	 * @Date: 2017/6/7 16:27
	 * @Params: paramJson
	 */
	public String updateSendTimesRunTimeByUid(String paramJson);
	

}
