package com.ziroom.zrp.service.trading.utils;

/*
 * <P></P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 18日 11:21
 * @Version 1.0
 * @Since 1.0
 */

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.zra.common.esp.utils.HttpClientHelper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EspUtil {

    /**
     * @description: 传入apiId生成含有固定请求参数的json对象
     * @author: lusp
     * @date: 2017/9/19 16:09
     * @params: apiId
     * @return: JSONObject
     */
    public static JSONObject getJSONRequest(String apiId) {
        JSONObject reqData = new JSONObject();
        reqData.put("apiId", apiId);
        reqData.put("timestamp", System.currentTimeMillis());
        return reqData;
    }

    /**
     * @description: 向签章服务方发送请求
     * @author: lusp
     * @date: 2017/9/19 16:10
     * @params: espUrl,value,apiSecret
     * @return: String
     */
    public static String send(String espUrl,String value,String apiSecret) {
        String jsonRespStr = "{\"isOK\":false}";
        try {
            jsonRespStr = HttpClientHelper.jsonCallWithHmacSha1(espUrl, value,apiSecret);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonRespStr;
    }

    /**
     * @description: 将文件转为base64加密串
     * @author: lusp
     * @date: 2017/9/19 16:07
     * @params: file
     * @return: String
     */
    public static String getPDFBinary(File file){
        FileInputStream fin =null;
        BufferedInputStream bin =null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream bout =null;

        try {
            fin = new FileInputStream(file);
            bin = new BufferedInputStream(fin);
            baos = new ByteArrayOutputStream();
            bout = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1024];
            int len = bin.read(buffer);
            while(len != -1){
                bout.write(buffer, 0, len);
                len = bin.read(buffer);
            }
            bout.flush();
            byte[] bytes = baos.toByteArray();
//            return encoder.encodeBuffer(bytes).trim();
            //apache公司的API
            return Base64.encodeBase64String(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fin.close();
                bin.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @description: 由base64加密串转为文件并保存到指定路径
     * @author: lusp
     * @date: 2017/9/19 16:08
     * @params: base64sString,filePath
     * @return:
     */
    public static void base64StringToPDF(String base64sString,String filePath){
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            //将base64编码的字符串解码成字节数组
//            byte[] bytes = decoder.decodeBuffer(base64sString);
            //apache公司的API
            byte[] bytes = Base64.decodeBase64(base64sString);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            bin = new BufferedInputStream(bais);
            File file = new File(filePath);
            fout  = new FileOutputStream(file);
            bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while(len != -1){
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            bout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                bin.close();
                fout.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
