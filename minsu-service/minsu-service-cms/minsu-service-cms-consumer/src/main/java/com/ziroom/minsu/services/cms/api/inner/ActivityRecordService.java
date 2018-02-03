package com.ziroom.minsu.services.cms.api.inner;

/**
 * <p>活动记录接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public interface ActivityRecordService {


	/**
	 * 免佣金的数据
	 * @athor afi
	 * @param json
	 * @return
	 */
	public String saveFreeComm(String json,Integer dayTime);


	/**
	 * 校验当前房东是否参加免佣金的活动
	 * @param uid
	 * @return
	 */
	public String getFive(String uid);

	/**
	 * 校验当前房东是否免佣金
	 * @param uid
	 * @return
	 */
	public String checkFree(String uid);


	/**
	 * 更新当前的礼品地址
	 * @author afi
	 * @create 2016年10月22日下午7:59:04
	 * @param recordFid
	 * @param name
	 * @param address
	 * @return
	 */
	public String updateAddress(String recordFid,String name,String address);


	/**
	 * 校验当前的活动
	 * @author afi
	 * @create 2016年10月22日下午7:59:04
	 * @param paramJson
	 * @return
	 */
	public String checkActivity4Record(String paramJson);

	/**
	 * 参加活动
	 * @author afi
	 * @create 2016年10月22日下午7:59:04
	 * @param paramJson
	 * @return
	 */
	public String exchangeActivity4Record(String paramJson);
  
	/**
	 * 
	 * 保存活动记录实体
	 *
	 * @author yd
	 * @created 2016年10月9日 下午4:28:01
	 *
	 * @param activityRecord
	 * @return
	 */
	public String saveActivityRecord(String activityRecord);
	/**
	 * 
	 * 活动记录 分页查询
	 *
	 * @author yd
	 * @created 2016年10月9日 下午2:11:48
	 *
	 * @param activityRecordRequest
	 * @return
	 */
	public String  queryAcRecordInfoByPage(String activityRecordRequest);
}
