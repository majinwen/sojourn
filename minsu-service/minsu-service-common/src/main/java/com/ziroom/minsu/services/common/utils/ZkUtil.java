package com.ziroom.minsu.services.common.utils;

import com.asura.framework.base.util.Check;
import com.asura.framework.conf.subscribe.ConfigSubscriber;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>获取zk的值</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/29.
 * @version 1.0
 * @since 1.0
 */
public class ZkUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkUtil.class);

    public static String getZkSysValue(String type, String code) {
        if (Check.NuNStr(type) || Check.NuNStr(code)){
            return null;
        }
        String source = "zk配置";
        String value = null;
        try {
            value = ConfigSubscriber.getInstance().getConfigValue(type, code);
        }catch (Exception e){
            LogUtil.error(LOGGER, "zk获取配置异常：{}", e);
        }
        if (value == null) {
            EnumMinsuConfig config = EnumMinsuConfig.getConfig(type,code);
            if (!Check.NuNObj(config)){
                source = "默认配置";
                value = config.getDefaultValue();
            }
        }
        LogUtil.info(LOGGER,"配置type:{},code:{},source:{},值:{}",type,code,source,value);
        return value;
    }


    public static void main(String[] args) {
        String aa=  getZkSysValue("house", "todayDiscountStartTime");

        System.out.println(aa);
    }

}
