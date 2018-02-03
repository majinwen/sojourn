package com.ziroom.minsu.services.common.utils;


import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>加密签名的工具类</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/7/7.
 * @version 1.0
 * @since 1.0
 */
public class SignUtils {


    private static final String separator = "&";

    private static final String con_appkey = "appkey";

    private static final String con_timestamp = "timestamp";

    /**
     * passport成功的code
     */
    public static final Integer success = 200000;
    
    /**
     * passport 失败的code  身份信息已被占用
     */
    public static final Integer error_10005 = 10005;


    public static void main(String[] args) {
        String url = "http://passport.q.ziroom.com/api/index.php?r=user/save-cert-by-uid";
        Map<String,String> par = new HashMap<>();
        par.put("uid","5f4f193b-07fd-a708-85f8-22907004fd6d");
        par.put("real_name","real_name");
        par.put("cert_type", "1");
        par.put("cert_num","cert_num");
        String sign = md5AppkeySign("aaa", par);
        par.put("sign",sign);
        System.out.println("sign:" + sign);
        par.put("appid", "1f6c91a91fb0ae4d");
        System.out.println("par:" + JsonEntityTransform.Object2Json(par));
        String rst = CloseableHttpsUtil.sendFormPost(url, par);
        System.out.println("rst:"+rst);
    }

    /**
     * 对参数进行验签
     * @author afi
     * @param appkey
     * @param parameterMap
     * @return
     */
    public static String md5AppkeySign(String appkey,Map<String, String> parameterMap) {
        if (Check.NuNStr(appkey) || Check.NuNObj(parameterMap)){
            return null;
        }
        String bizPar = JoinUtil.joinSortMap(parameterMap, separator, true);
        Map<String,String> keyMap = new HashMap<>();
        keyMap.put(con_appkey,appkey);
        Long timestamp = System.currentTimeMillis();
        keyMap.put(con_timestamp,timestamp+"");
        parameterMap.put(con_timestamp, timestamp+"");
        String key = bizPar + separator + JoinUtil.joinSortMap(keyMap, separator, true);
        return DigestUtils.md5Hex(key);
    }


}
