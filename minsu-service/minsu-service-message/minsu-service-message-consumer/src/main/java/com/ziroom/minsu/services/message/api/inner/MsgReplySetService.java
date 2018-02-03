/**
 * @FileName: MsgReplySetService.java
 * @Package com.ziroom.minsu.services.message.api.inner
 * 
 * @author yd
 * @created 2016年4月18日 下午8:18:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.api.inner;


/**
 * <p>留言回复</p>
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
public interface MsgReplySetService {

	/**
	 * 查询当前房东设置实体
	 *
	 * @author yd
	 * @created 2016年4月18日 上午9:57:38
	 *
	 * @param msgReplySetRequest
	 * @return
	 */
	public String queryByLanglordFid(String msgReplySetRequest);
	
	/**
	 * 
	 * 保存
	 *
	 * @author yd
	 * @created 2016年4月18日 下午8:19:56
	 *
	 * @param msgReplySet
	 * @return
	 */
	public String save(String msgReplySet);
	
	/**
	 * 
	 * 条件更新
	 *
	 * @author yd
	 * @created 2016年4月18日 下午8:20:28
	 *
	 * @param msgReplySetEntity
	 * @return
	 */
	public String updateByCondition(String msgReplySetEntity);
}
