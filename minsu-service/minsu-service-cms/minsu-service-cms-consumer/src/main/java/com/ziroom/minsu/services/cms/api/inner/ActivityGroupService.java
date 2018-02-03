package com.ziroom.minsu.services.cms.api.inner;

/**
 * <p>活动组</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 16/10/10.
 * @version 1.0
 * @since 1.0
 */
public interface ActivityGroupService {

    /**
     * 获取当前的活动组
     * @author afi
     * @return
     */
    public String getGroupByPage(String parJson);

    /**
     * 获取当前的活动组
     * @author afi
     * @return
     */
    public String getAllGroup();

    /**
     * 通过组号获取但前的组
     * @author afi
     * @return
     */
    public String getGroupBySN(String groupSn);

	/**
	 * 新增活动组信息
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertActivityGroupEntity(String paramJson);

	/**
	 * 修改活动组信息
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateActivityGroupEntity(String paramJson);
}
