package com.ziroom.minsu.api.customer;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.DESEncrypt;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl
 * @version 1.0
 * @Date Created in 2017/9/18 18:20
 * @since 1.0
 */
public class ApiTest {

    private static IEncrypt iEncrypt = EncryptFactory.createEncryption("DES");

    private static final String API_URL = "http://bnb.api.ziroom.com/";

    private static final Map<String, String> HEADER_MAP = new HashMap<>();

    /**
     * TODO
     */
    private static final String UID = "";

    static {
        HEADER_MAP.put("token", "2121321");
        HEADER_MAP.put("uid", UID);
        HEADER_MAP.put("client-version", "client-version");
        HEADER_MAP.put("client-type", "111");
        HEADER_MAP.put("user-agent", "user-agent");
    }

    public static void main(String[] args) {

        String uri = "customerColl/ea61d2/collHouseList";

        JSONObject json = new JSONObject();
        json.put("page", 1);
        json.put("limit", 10);
        json.put("uid", UID);

        Map<String, String> par = new HashMap<>();
        par.put("2y5QfvAy", iEncrypt.encrypt(json.toJSONString()));
        par.put("hPtJ39Xs", MD5Util.MD5Encode(json.toJSONString(), "UTF-8"));

        String rst = CloseableHttpUtil.sendFormPost(API_URL + uri, par, HEADER_MAP);

        DESEncrypt dESEncrypt = new DESEncrypt();

        String content = dESEncrypt.decrypt(rst);
        System.out.println(content);

    }

}
