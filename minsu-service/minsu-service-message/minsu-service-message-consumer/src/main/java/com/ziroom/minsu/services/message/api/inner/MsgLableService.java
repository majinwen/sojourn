/**
 * @FileName: MsgLableService.java
 * @Package com.ziroom.minsu.services.message.api.inner
 * 
 * @author yd
 * @created 2016年4月18日 下午6:05:35
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.api.inner;


/**
 * <p>消息标签的服务接口</p>
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
public interface MsgLableService {
	
	/**
	 * 
	 * 条件分页查询
	 *
	 * @author yd
	 * @created 2016年4月18日 下午6:07:20
	 *
	 * @param msgLableRequest
	 * @return
	 */
	public String queryByPage(String msgLableRequest);
	
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月18日 下午6:08:35
	 *
	 * @param msLableEntity
	 * @return
	 */
	public String save(String msLableEntity);
	
	/**
	 * 
	 * 根据fid修改实体
	 *
	 * @author yd
	 * @created 2016年4月18日 下午6:09:03
	 *
	 * @param msLableEntity
	 * @return
	 */
	public String updateByFid(String msLableEntity);
	
	/**
	 * 
	 * 用户输入关键词 获取要回答的内容
	 *
	 * @author yd
	 * @created 2016年4月18日 下午8:44:25
	 *
	 * @param msgLableRequest
	 * @return
	 */
	public String queryMsgContentByKey(String msgLableRequest);

}
