package com.zra.common.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 消息工具类
 */
public class MessageUtil {

    private static Logger LOGGER = Logger.getLogger(MessageUtil.class);

    /**
     * 获取信息
     *
     * @param requrl 请求地址
     * @return
     */
    public static JSONObject postTransmessage(String requrl, String str) {

        JSONObject result = null;

        if (requrl == null) {
            LOGGER.debug("http 请求 地址 为 null");
            return result;
        }
        LOGGER.debug("请求url=" + requrl);
        String strjson = null;

        try {
            strjson = postWithRes(requrl, str);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        if (strjson == null) {
            LOGGER.debug("http 请求 返回json 为 null");
            return result;
        }

        JSONObject jsonobj = parseJson(strjson);
        if (jsonobj == null) {
            return null;
        }

        return jsonobj;
    }


    /**
     * 获取ams网站接口参数
     *
     * @param map 参数列表
     * @return
     */
    public static String getParameter(Map<String, Object> map) {
        try {
            String result = "";
            if (map == null) {
                return result;
            }

            Set<String> keys = map.keySet();
            for (String key : keys) {
                if (map.get(key) != null) {
                    if (result != null && !"".equals(result)) {
                        result = result + "&";
                    }
                    result = result + "" + key + "=" + map.get(key);
                }
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("获取ams网站接口参数出错！", e);
        }
        return null;

    }


    public static String postWithRes(String strURL, String req,
                                     Map<String, String> headers) {
        return post(strURL, req, "application/x-www-form-urlencoded", "UTF-8",
                true, headers);
    }


    private static String post(String strURL, String req, String contentType,
                               String encode, boolean response, Map<String, String> headers) {
        //默认不打请求回来结果
        return post(strURL, req, contentType, encode, response, headers, false);
    }


    private static String post(String strURL, String req, String contentType,
                               String encode, boolean response, Map<String, String> headers, boolean isLog) {
        StopWatch watch = new StopWatch();
        watch.start();

        String result = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            URL url = new URL(strURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", contentType);
            con.setRequestProperty("Comp-Control", "dsmp/sms-mt");
            if ((headers != null) && (headers.size() > 0)) {
                Set keys = headers.keySet();
                Iterator ir = keys.iterator();

                while (ir.hasNext()) {
                    String key = (String) ir.next();
                    con.setRequestProperty(key, (String) headers.get(key));
                }
            }
            LOGGER.info("http 请求 url = " + strURL);
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setConnectTimeout(30000);
            con.setReadTimeout(30000);

            out = new BufferedOutputStream(con.getOutputStream());
            byte[] outBuf = req.getBytes(encode);
            out.write(outBuf);
            out.flush();
            if (response) {
                in = new BufferedInputStream(con.getInputStream());
                result = readByteStream(in, encode);
            } else {
                con.getInputStream();
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        String strLog = "HttpPoster:[total time=" + watch.getTime() + ", url="
                + strURL + ", contentType=" + contentType + ",ecode=" + encode;
        if (isLog) {//打印请求回来的结果
            LOGGER.info(strLog + ",response=" + result + "]");
        } else {//不打印请求回来的log
            LOGGER.info(strLog + "]");
        }

        return result;
    }

    public static String postWithRes(String strURL, String req) {
        return post(strURL, req, "application/x-www-form-urlencoded", "UTF-8",
                true, null);
    }


    /**
     * 解析请求的json串
     *
     * @param jsonStr
     * @return
     */
    private static JSONObject parseJson(String jsonStr) {

        if (jsonStr == null || jsonStr.equals("")) {
            return null;
        }

        try {
            JSONObject json = JSONObject.parseObject(jsonStr);

            if (json != null && !json.isEmpty()) {
                return json;
            }
        } catch (JSONException e) {
            LOGGER.error("解析请求的json串", e);
        }

        return null;
    }


    private static String readByteStream(BufferedInputStream in, String encode)
            throws IOException {
        LinkedList bufList = new LinkedList();
        int size = 0;
        while (true) {
            byte[] buf = new byte[128];
            int num = in.read(buf);
            if (num == -1)
                break;
            size += num;
            bufList.add(new mybuf(buf, num));
        }
        byte[] buf = new byte[size];
        int pos = 0;
        for (ListIterator p = bufList.listIterator(); p.hasNext(); ) {
            mybuf b = (mybuf) p.next();
            int i = 0;
            while (i < b.size) {
                buf[pos] = b.buf[i];
                ++i;
                ++pos;
            }
        }
        return new String(buf, encode);
    }

    private static class mybuf {
        public byte[] buf;
        public int size;

        public mybuf(byte[] b, int s) {
            this.buf = b;
            this.size = s;
        }
    }
}
