package com.ziroom.minsu.services.order.api.inner;


/**
 * <p>订单备注</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/6/23.
 * @version 1.0
 * @since 1.0
 */
public interface OrderRemarkService {
	
	/**
	 * 获取订单备注数量
	 * @author lishaochuan
	 * @create 2016年6月27日上午11:56:16
	 * @return
	 */
	public String getRemarkCount(String params);

    /**
     * 备注列表
     * @author liyingjie
     * @param param
     * @return
     */
	public String getRemarkList(String param);

    /**
     * 新增备注
     * @author liyingjie
     * @param param
     * @return
     */
	public String insertRemarkRes(String param);


    /**
     * 删除备注
     * @author liyingjie
     * @param param
     * @return
     */
	public String delRemark(String param);



    
    
}
