package com.ziroom.minsu.services.message.api.inner;
/**
 * 
 * 系统消息管理接口
 * @author jixd on 2016年4月18日
 * @version 1.0
 * @since 1.0
 */
public interface SysMsgManagerService {
	/**
	 * 新增消息
	 * @author jixd on 2016年4月18日
	 */
	String saveSysMsgManager(String paramJson);
	/**
	 * 发布消息
	 * @author jixd on 2016年4月18日
	 */
	String releaseMsg(String paramJson);
	
	/**
	 * 删除消息，只删除未发布消息
	 * @author jixd on 2016年4月18日
	 */
	String deleteMsg(String paramJson);
	/**
	 * 更新消息
	 * @author jixd on 2016年4月18日
	 */
	String updateMsg(String paramJson);
	
	/**
	 * 查询消息信息带分页
	 * @author jixd on 2016年4月18日
	 */
	String queryMsgPage(String paramJson);
	/**
	 * 根据fid查询实体单条记录
	 * @author jixd on 2016年4月19日
	 */
	String findSysMsgManagerByFid(String fid);
}
