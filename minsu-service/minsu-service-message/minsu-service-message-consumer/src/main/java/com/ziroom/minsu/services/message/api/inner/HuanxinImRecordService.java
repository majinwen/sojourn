/**
 * @FileName: HuanxinImRecordService.java
 * @Package com.ziroom.minsu.services.message.api.inner
 * 
 * @author yd
 * @created 2016年9月10日 下午2:40:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.api.inner;

/**
 * <p>环信IM 接口实现</p>
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
public interface HuanxinImRecordService {

	/**
	 * 
	 * 定时任务同步环信IM消息  每天晚上12点，同步前一天的数据，即：同步前一天晚上到今天晚上12点的数据（例如：2016/09/09 24:00:00   到 2016/09/10 24:00:00）
	 * 环信地址：http://docs.easemob.com/im/100serverintegration/30chatlog
	 * 接口限流说明：同一个 APP 每分钟最多可调用10次，超过的部分会返回429或503错误。所以在调用程序中，如果碰到了这样的错误，需要稍微暂停一下并且重试。如果该限流控制不满足需求，请联系商务经理开放更高的权限。（一次最多返回1000条）
	 * 算法：
	 * 1. 获取环信token （保存当前redis中，失效时间6天，redis失效后去环信获取）
	 * 2. 由于 msg_id 在环信返回中是唯一的，故为主键，并且入库时，以此值，校验重复，重复插入直接忽略
	 * 3. 以当前时间往前推hours小时（25小时 这个时间做成可配置的，比定时任务时间长1个小时即可  例如：定时任务24小时，hours就是25，影响：理论上多取1个小时数据，好处：能保证数据不丢失）
	 * 4. 对于接口调用次数限制问题处理：让接口去调用，出现429或503，让当前线程睡30s
	 * 5. 接口循环去调用，直到接口获取完所以数据停止
	 *
	 * @author yd
	 * @param hours 同步时长
	 * @param sleepScends 请求环信异常，当前线程睡的时长，单位s
	 * @created 2016年9月10日 下午2:55:21
	 *
	 */
	public String sysHuanxinImMes(String hours,String sleepScends);
	
	/**
	 * 
	 * 发送IM消息到环信 异步
	 *
	 * @author yd
	 * @created 2017年4月8日 下午2:50:04
	 *
	 * @return
	 */
	public String sendImMesToHuanxin(String paramJson);
	
	/**
	 * 条件分页查询留言房源关系实体 （房源留言fid必须要有）
	 * @author wangwentao 2017/4/27 11:50
	 */
	public String queryByPage(String msgFirstDdvisoryRequest);
	
	/**
	 * 
	 * IM禁用 用户
	 *
	 * @author yd
	 * @created 2017年6月16日 下午12:39:47
	 *
	 * @param uid
	 */
	public void deactivateImUser(String uid);
	
	/**
	 * 
	 * IM禁用 解除
	 *
	 * @author yd
	 * @created 2017年6月16日 下午12:39:47
	 *
	 * @param uid
	 */
	public void activateImUser(String uid);
	
	/**
	 * 
	 * 保存环信 记录 至t_huanxin_im_record
	 *
	 * @author yd
	 * @created 2017年8月1日 下午8:18:48
	 *
	 * @param jsonParam
	 */
	public String saveHuanxinImRecord(String jsonParam);

	/**
	 * 获取两个聊天用户发送消息的条数
	 *
	 * @author loushuai
	 * @created 2017年9月7日 下午3:04:09
	 *
	 * @param jsonParam
	 * @return
	 */
	public String getCountMsgEach(String jsonParam);

	/**
	 * 发送IM消息到环信 异步
	 *
	 * @author loushuai
	 * @created 2017年9月7日 下午5:17:20
	 *
	 * @param jsonParam
	 */
	public String sendHuanxinForChangzu(String jsonParam);

	/**
	 * 保存环信离线消息
	 *
	 * @author loushuai
	 * @created 2017年9月19日 上午11:37:59
	 *
	 * @param object2Json
	 * @return
	 */
	public String saveHuanxinOffline(String object2Json);
}
