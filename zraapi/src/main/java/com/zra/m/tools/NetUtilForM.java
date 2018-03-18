package com.zra.m.tools;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.common.utils.NetUtil;
import com.zra.common.utils.PropUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/9.
 */
public class NetUtilForM {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(NetUtilForM.class);

    public static Map<String, String> getHeadersInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Map<String, String> map = new HashMap<>();
        map.put("Client-Version", "1.0.0");
        map.put("Accept", "application/json");
        map.put("Sys", "m-web");
        map.put("Client-Type", "4");
//        map.put("User-Agent", UUID.randomUUID().toString());
        map.put("Request-Id", UUID.randomUUID().toString().substring(0, 8) + sdf.format(new Date()));
        return map;
    }

    public static Map<String, String> getHeaderAddToken(String token) {
        Map<String, String> map = getHeadersInfo();
        map.put("token", token);
        return map;
    }

    /**
     * 验证token是否有效
     *
     * @Author: wangxm113
     * @CreateDate: 2017-03-14
     */
    public static TokenResultDto checkTokenIsValid(String token) {
        Map<String, String> headerMap = getHeaderAddToken(token);
        try {
            String s = NetUtil.sendPostRequest(PropUtils.getString(TokenUrlConstant.TOKEN_URL_PRE) + TokenUrlConstant.CHECK_TOKEN_URL, headerMap, null);
            TokenResultDto result = new ObjectMapper()
                    .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)// 解析未知参数
                    .readValue(s, TokenResultDto.class);
            return result;
        } catch (Exception e) {
            LOGGER.error("[验证token是否有效]错误", e);
            return new TokenResultDto();
        }
    }

    /**
     * 根据token获取用户信息
     *
     * @Author: wangxm113
     * @CreateDate: 2017-03-14
     */
    public static TokenResultDto getUserInfoByToken(String token) {
        Map<String, String> headerMap = getHeaderAddToken(token);
        try {
            String s = NetUtil.sendGetRequest(PropUtils.getString(TokenUrlConstant.TOKEN_URL_PRE) + TokenUrlConstant.GET_INFO_BY_TOKEN_URL, headerMap, null);
            TokenResultDto result = new ObjectMapper()
                    .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)// 解析未知参数
                    .readValue(s, TokenResultDto.class);
            return result;
        } catch (Exception e) {
            LOGGER.error("[根据token获取用户信息]错误", e);
            return new TokenResultDto();
        }
    }
}
