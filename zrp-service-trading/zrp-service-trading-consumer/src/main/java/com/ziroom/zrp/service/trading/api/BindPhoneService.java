package com.ziroom.zrp.service.trading.api;

/**
 * <p>管家400电话</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年11月01日 19:32
 * @since 1.0
 */
public interface BindPhoneService {

    /**
     * 根据员工id查询员工400分机信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String findBindPhoneByEmployeeId(String employeeId);
    /**
     * 根据管家code查询员工400分机
     * @author jixd
     * @created 2017年11月05日 14:55:28
     * @param
     * @return
     */
    String findBindPhoneByEmployeeCode(String projectId,String employeeCode);
}
