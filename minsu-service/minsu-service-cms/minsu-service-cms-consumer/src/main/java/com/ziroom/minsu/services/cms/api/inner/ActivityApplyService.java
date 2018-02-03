package com.ziroom.minsu.services.cms.api.inner;

/**
 * <p>活动申请信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月29日
 * @since 1.0
 * @version 1.0
 */
public interface ActivityApplyService {

	/**
	 * 保存申请信息
	 * @author lishaochuan
	 * @create 2016年6月29日下午5:50:51
	 * @param paramJson
	 * @return
	 */
	public String saveApply(String paramJson);

	/**
	 * 种子房东申请列表
	 * @author liyingjie
	 * @create 2016年6月29日下午5:50:51
	 * @param paramJson
	 * @return
	 */
	String applyList(String paramJson);
	
	/**
	 * 查询申请详情
	 * @author zl
	 * @param applyFid
	 * @return
	 */
	public String getApplyDetailWithBLOBs(String applyFid);
}
