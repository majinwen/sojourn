package com.ziroom.zrp.service.trading.api;

/**
 * <p>友家服务接口</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年09月26日 11:39
 * @since 1.0
 */
public interface ZiroomCustomerService {

    /**
     * 根据手机号查询uid信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public String queryUidByPhone(String phone);

    /**
     * 根据uid查询用户信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public String findAuthInfoFromCustomer(String uid);

    /**
     * 查询用户实名认证状态
     * @param jsonParam {uid:""}
     * @return json
     * @author cuigh6
     * @Date 2017年10月
     */
    public String getUserAuthInfo(String jsonParam);

    /**
     * 保存客户库信息
     * @param paramJson 社会资质证明
     * @return
     * @author cuigh6
     * @Date 2017年12月
     */
    String saveCustomerInfo(String paramJson);
}
