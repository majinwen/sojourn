package com.ziroom.minsu.api.common.util;

import com.asura.framework.base.util.Check;

import java.text.DecimalFormat;

/**
 *
 * <p>供API使用的字符串工具类</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class ApiStringUtil {

    /**
     * 真实姓名(除首字符外)其它字符用*替代
     *
     * @author liujun
     * @created 2016年7月27日
     *
     * @param realName
     * @return
     */
    public static String hideRealName(String realName) {
        if(!Check.NuNStr(realName)){
            char[] names = realName.toCharArray();
            for (int i = 1; i < names.length; i++) {
                names[i] = '*';
            }
            realName = String.valueOf(names);
        }
        return realName;
    }
}
