package com.ziroom.minsu.services.customer.api.inner;


/**
 * 
 * <p>客户房源收藏操作接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public interface CustomerCollectionService {
	/**
	 * 
	 * 新增客户房源收藏信息
	 *
	 * @author liujun
	 * @created 2016年7月29日
	 *
	 * @param fid
	 * @return
	 */
	public String saveCustomerCollectionEntity(String paramJson);
	
	/**
     * 
     * 根据逻辑fid查询客户房源收藏信息
     *
     * @author liujun
     * @created 2016年7月28日
     *
     * @param fid
     * @return
     */
	public String findCustomerCollectionEntityByFid(String fid);
	
	/**
	 * 条件查询客户房源收藏信息
	 *
	 * @author liujun
	 * @created 2016年8月2日
	 *
	 * @param paramJson(houseFid:必传, rentWay:必传)
	 * @return
	 */
	public String findCustomerCollectionEntityByCondition(String paramJson);
	
	/**
     * 
     * 根据逻辑客户uid分页查询客户房源收藏信息集合
     *
     * @author liujun
     * @created 2016年7月28日
     *
     * @param paramJson
     * @return
     */
	public String findCustomerCollectionVoListByUid(String paramJson);
	
	/**
     * 
     * 根据逻辑id更新客户房源收藏信息
     *
     * @author liujun
     * @created 2016年7月28日
     *
     * @param paramJson
     * @return
     */
	public String updateCustomerCollectionByFid(String paramJson);
	
	/**
	 * 
	 * 根据逻辑客户uid查询房源收藏条数
	 *
	 * @author liujun
	 * @created 2016年7月28日
	 *
	 * @param uid
	 * @return
	 */
	public String countByUid(String uid);
}
