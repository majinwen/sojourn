package com.ziroom.minsu.services.message.api.inner;


/**
 * 
 * <p>系统消息服务</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd on 2016年4月18日
 * @since 1.0
 * @version 1.0
 */
public interface SysMsgService {
	
	/**
	 * 发送系统消息
	 * @author jixd on 2016年4月18日
	 * @param sysMsgJson 实体类json
	 */
	String saveSysMsg(String sysMsgJson);
	
	/**
	 * 批量增加系统消息
	 * @author jixd on 2016年4月18日
	 */
	String saveSysMsgBatch(String sysMsgList);
	/**
	 * 查询系统消息
	 * @author jixd on 2016年4月18日
	 */
	String querySysMsg(String sysMsgRequest);
	
	/**
	 * 删除消息
	 * @author jixd on 2016年4月18日
	 */
	String deleteSysMsg(String fid);
	/**
	 * 更新已读
	 *
	 * @author jixd
	 * @created 2016年4月21日 下午4:22:10
	 *
	 * @param fid
	 * @return
	 */
	String updateSysMsgRead(String fid);
	
	/**
	 * 发送消息,传入模板code和对应的参数
	 * @author jixd on 2016年4月20日
	 */
	/*String sendSysMsg(String paramMap);*/
	
}
