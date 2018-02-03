package com.ziroom.minsu.services.basedata.proxy;

import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import org.springframework.stereotype.Component;

/**
 * <p>zk的实现</p>
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
@Component("basedata.zkSysServiceProxy")
public class ZkSysServiceProxy implements ZkSysService{


    /**
     * 获取当前的zk的值
     * @author afi
     * @param type
     * @param code
     * @return
     */
    @Override
    public String getZkSysValue(String type, String code) {
        return ZkUtil.getZkSysValue(type, code);
    }
}
