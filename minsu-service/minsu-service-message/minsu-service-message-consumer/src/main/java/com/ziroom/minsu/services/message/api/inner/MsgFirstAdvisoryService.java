package com.ziroom.minsu.services.message.api.inner;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/25
 * @version 1.0
 * @since 1.0
 */
public interface MsgFirstAdvisoryService {

    String queryByMsgBaseFid(String msgBaseFid);

	/**
	 * 获取所有需要跟进首次咨询
	 *
	 * @author loushuai
	 * @created 2017年5月25日 下午7:25:04
	 *
	 * @param object2Json
	 * @return
	 */
	String queryAllNeedFollowList(String object2Json);
}
