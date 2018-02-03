package com.ziroom.minsu.services.customer.api.inner;

/**
 * <p>用户位置收集</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/30.
 * @version 1.0
 * @since 1.0
 */
public interface CustomerLocationService {


    /**
     * 保存当前用户的微信信息
     * 并且填充信息
     * @param paramJson
     * @return
     */
    String saveUserLocationAndFill(String paramJson);
    

    /**
     * 保存当前用户的微信信息
     * 并且填充信息
     * @param paramJson
     * @return
     */
    String saveUserLocationAndFillV1(String paramJson);

    /**
     * 保存当前用户的
     * @param reuest
     * @return
     */
    String saveUserLocation(String reuest);


    /**
     * 定时任务补全当前的经纬度为空的数据信息
     * @return
     */
    public String taskFillUserLocation();
    
    /**
     * 查询用户的位置信息
     * TODO
     *
     * @author zl
     * @created 2016年12月12日 上午11:14:15
     *
     * @param params
     * @return
     */
    public String getCustomerLocation(String paramJson);

}
