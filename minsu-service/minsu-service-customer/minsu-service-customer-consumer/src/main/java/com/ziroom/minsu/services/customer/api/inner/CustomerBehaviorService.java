package com.ziroom.minsu.services.customer.api.inner;

/**
 * <p>用户行为（成长体系）</p>
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年10月12日
 * @version 1.0
 * @since 1.0
 */
public interface CustomerBehaviorService {

    /**
     * 
     * 保存用户行为
     * 
     * @author zhangyl2
     * @created 2017年10月12日 15:24
     * @param 
     * @return 
     */
    String saveCustomerBehavior(String paramJson);

    /**
     *
     * 定时任务补偿
     * 查询过去一段时间某类型行为记录
     *
     * @author zhangyl2
     * @created 2017年10月12日 18:08
     * @param
     * @return
     */
    String queryCustomerBehaviorProveFidsForJob(String paramJson);

	/**
	 * 根据uid查询所有行为记录
	 *
	 * @author loushuai
	 * @created 2017年10月13日 上午11:06:31
	 *
	 * @param paramJson
	 * @return
	 */
	String getCustomerBehaviorList(String paramJson);

	/**
	 * 
	 * 修改用户行为
	 * 
	 * @author zhangyl2
	 * @created 2017年10月14日 17:28
	 * @param 
	 * @return 
	 */
	String updateCustomerBehaviorAttr(String paramJson);

}
