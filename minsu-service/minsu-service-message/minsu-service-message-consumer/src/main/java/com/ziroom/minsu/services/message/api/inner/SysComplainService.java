
package com.ziroom.minsu.services.message.api.inner;


import com.ziroom.minsu.entity.message.SysComplainEntity;

/**
 * <p>投诉建议代理层接口</p>
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
public interface SysComplainService {


	/**
	 * 
	 * 保存投诉建议实体
	 *
	 * @author yd
	 * @created 2016年5月4日 上午11:53:29
	 *
	 * @param sysComplainEntity
	 * @return
	 */
	public String save(String sysComplainEntity);

	/**
	 *	按条件查询
	 * @author wangwentao 2017/4/24 18:12+
	 *
	 */
	public String queryByCondition(String sysComplainEntity);

	/**
	 *
	 * @author wangwentao 2017/4/25 14:20
	 */
	public String selectByPrimaryKey(String id);
}
