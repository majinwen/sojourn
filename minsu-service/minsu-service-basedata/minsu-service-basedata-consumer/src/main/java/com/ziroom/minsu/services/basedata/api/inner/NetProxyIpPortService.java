package com.ziroom.minsu.services.basedata.api.inner;

/**
 * <p>用户的操作记录</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017/4/19.
 * @version 1.0
 * @since 1.0
 */
public interface NetProxyIpPortService {

    /**
     * 获取有效代理ip地址列表
     * @author jixd
     * @created 2017年04月19日 15:43:06
     * @param
     * @return
     */
    String listNetProxyIp();

    /**
     * 保存代理ip地址
     * @author jixd
     * @created 2017年04月19日 15:43:45
     * @param
     * @return
     */
    String saveNetProxyIp(String paramJson);

    /**
     * 更新代理ip地址状态
     * @author jixd
     * @created 2017年04月19日 15:44:24
     * @param
     * @return
     */
    String updateNetProxyIp(String paramJson);

    /**
     * 更新代理使用次数
     * @author jixd
     * @created 2017年04月19日 18:25:19
     * @param
     * @return
     */
    String updateCountByProxyIp(String proxyIp);

}
