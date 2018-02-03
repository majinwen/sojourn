package com.ziroom.minsu.services.basedata.api.inner;

/**
 * <p>zk常量</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/7/30.
 * @version 1.0
 * @since 1.0
 */
public interface ZkSysService {

    /**
     * 获取当前的zk的值
     * @author afi
     * @param type
     * @param code
     * @return
     */
    String getZkSysValue(String type,String code);


}
