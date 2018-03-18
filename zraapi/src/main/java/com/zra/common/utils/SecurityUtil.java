package com.zra.common.utils ;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * 安全验证工具类
 * 
 * @author wgh 2013-12-10
 */
public class SecurityUtil {


    public static String encryptParam(Map requestMap, String secretKey){

        ArrayList keys = new ArrayList(requestMap.keySet());
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < keys.size(); i++) {
            String key = (String)keys.get(i);
            // 密文字段不参与加密
            if("sign".equals(key)){
                continue;
            }
            String obj = (String)requestMap.get(key);
            String value = "";
            if(obj != null){
                value = obj;
            }
            sb.append(key).append("=").append(value);
            sb.append("&");
        }
        sb.append(secretKey);
        return md5(sb.toString()).toUpperCase();
    }

    public static String md5(String plainText) {
        String str = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes("UTF-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void main(String[] args) throws Exception {

        String str = "{\n  \"cityCode\" : \"110000\",\n  \"uid\" : \"2e47282d-5f48-5e72-6bbb-bd661f2108d6\",\n  \"imei\" : \"b8c08755b2dd185d7c85d83a18cd8618e8b3f8f2\",\n  \"phoneModel\" : \"iPhone 6S Plus\",\n  \"zhutiCode\" : \"2c9085f4338ddc420133907d56e30204\",\n  \"userPhone\" : \"15321791395\",\n  \"appVersionStr\" : \"5.0.4\",\n  \"sysVersionStr\" : 10,\n  \"timestamp\" : 1479804039511\n}";
        System.out.println(md5("贾学文"));
        System.out.println(URLEncoder.encode(str,"UTF-8").replaceAll("\\+", "%20"));
    }
}
