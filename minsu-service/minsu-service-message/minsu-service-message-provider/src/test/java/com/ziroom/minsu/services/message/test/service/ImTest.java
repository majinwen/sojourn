package com.ziroom.minsu.services.message.test.service;
import java.io.BufferedReader;
import java.io.BufferedWriter;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.io.OutputStreamWriter;  
import java.io.PrintWriter;
import java.net.HttpURLConnection;  
import java.net.MalformedURLException;  
import java.net.URL;  
import java.net.URLConnection;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
public class ImTest {
	/**  
     * 向指定URL发送POST方法的请求  
     * @param url 发送请求的URL  
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。  
     * @return URL所代表远程资源的响应  
     */  
    public static String sendPost(String url, String param) {  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            //打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            //设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
            //发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            //获取URLConnection对象对应的输出流  
            OutputStream outputStream = conn.getOutputStream();
            out = new PrintWriter(outputStream);  
            //发送请求参数  
            out.print(param);  
            //flush输出流的缓冲  
            out.flush();  
            //定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(  
                new InputStreamReader(conn.getInputStream()));  
            String line;  
            while ((line = in .readLine()) != null) {  
                result += "/n" + line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送POST请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        //使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if ( in != null) { in .close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
  
  
    //提供主方法，测试发送GET请求和POST请求  
    public static void main(String args[]) {  
    	
    	/*JSONObject msgObj = new  JSONObject();
    	msgObj.put("msg_id", "12345651111");
    	msgObj.put("timestamp", "1505207542498");
    	msgObj.put("direction", "outgoing");
    	msgObj.put("from", "ziroomerPro1");
    	msgObj.put("to", "49a2f38f-078a-7276-c95f-c6fe91a53b6c1");
    	msgObj.put("chat_type", "chat");
    	
    	
    	//bodies
    	JSONArray bodies = new  JSONArray();
    	JSONObject j1 = new  JSONObject();
    	j1.put("type", "txt");
    	j1.put("file_length", 128827);
    	j1.put("filename", "test1.jpg");
    	JSONObject size = new  JSONObject();
    	size.put("height", 1325);
    	size.put("width", 746);
    	j1.put("size", size);
    	j1.put("secret", "DRGM8OZrEeO1vafuJSo2IjHBeKlIhDp0GCnFu54xOF3M6KLr");
    	j1.put("url", "https://a1.easemob.com/easemob-demo/chatdemoui/chatfiles/65e54a4a-fd0b-11e3-b821-ebde7b50cc4b");
    	bodies.add(j1);
    	JSONObject bodies = new  JSONObject();
    	bodies.put("msg", "111");
    	bodies.put("type", "txt");
    	//ext
    	JSONObject ext1 = new  JSONObject();
    	ext1.put("ziroomFlag", "ZIROOM_CHANGZU_IM");
    	ext1.put( "ziroomType", 202);
    	ext1.put("domainFlag", "minsu_t");
    	ext1.put("linkUrl", "http://www.baidu.com/");
    	ext1.put("content", "这是自如的活动，非常棒，赶紧来参加吧");
    	ext1.put("activityPicUrl", "http://10.16.34.42:8080/group3/M00/01/DA/ChAiKlmWsIuAWmMBAAA8yKgUTOw983.jpg");
    	ext1.put("activityAddr", "自如公司总部");
    	ext1.put("activityDate",  "2017-9-26");
    	ext1.put( "activityWeek",  "星期天");
    	ext1.put("activityTime", "下午6点");
    	//payload
    	JSONObject payload = new  JSONObject();
    	payload.put("bodies", bodies);
    	payload.put("ext", ext1);
    	payload.put("from", "ziroomerPro");
    	payload.put("to", "app_49a2f38f-078a-7276-c95f-c6fe91a53b6c");
    	
    	msgObj.put("payload", payload);
    	
    	String jsonString = msgObj.toJSONString();*/
        //发送POST请求  
       /* String s1 = ImTest.sendPost("http://10.30.26.27:8080/im/ee5f86/saveImChat" ,  
        		jsonString);  
        System.out.println(s1);  */
    	String minsuStr = getMinsuStr();
      //发送POST请求  
        String s1 = ImTest.sendPost("http://10.30.26.10:8080/im/ee5f86/saveImChat" ,  
        		minsuStr);  
        System.out.println(s1);
    } 
    public static String  getMinsuStr(){

    	JSONObject msgObj = new  JSONObject();
    	msgObj.put("msg_id", "390486835001821184W");
    	msgObj.put("timestamp", "1508482101139");
    	msgObj.put("host", "msync@ebs-ali-beijing-msync4");
    	msgObj.put("appkey", "ziroom#ziroom");
    	msgObj.put("from", "app_5ea03f56-1070-49e0-8bfb-f3ca0428b375");
    	msgObj.put("to", "app_0a7a79fb-ffc1-41ac-95bf-43e86d829f33");
    	msgObj.put("chat_type", "chat");
    	msgObj.put("callId", "ziroom#ziroom_387550683307444192");
    	msgObj.put("eventType", "chat");
    	msgObj.put("security", "17012321aa29efd92ac61cf981950589");
    	
    	//bodies
    	JSONArray bodies = new  JSONArray();
    	JSONObject j1 = new  JSONObject();
//    	j1.put("type", "img");  //图片类型  
//    	j1.put("type", "计划经济家🌍🌸🎍🎋🌴🌴🌷🍄🌸");
//    	j1.put("file_length", 8331);
//    	j1.put("filename", "image.jpg");
//    	j1.put("thumbFilename", "thumbimage.jpg");
//    	j1.put("filename", "image.jpg");
//    	JSONObject size = new  JSONObject();
//    	size.put("height", 180);
//    	size.put("width", 208);
//    	j1.put("size", size);
//    	j1.put("secret", "-HZ3Gq8qEeeWz7vG7Qd2CvCHgP1qpJlInJtYg3Tlu-nNIaBQ");
//    	j1.put("url", "https://a1.easemob.com/ziroom/ziroom/chatfiles/f8767710-af2a-11e7-b00c-6b5248864a4e");
    	j1.put("msg", "计划经济家🌍🌸🎍🎋🌴🌴🌷🍄🌸");//文本类型
    	j1.put("type", "txt");
    	bodies.add(j1);
    	//ext
    	JSONObject ext1 = new  JSONObject();
    	ext1.put("domainFlag", "minsu_t");
    	JSONObject em_apns_ext = new  JSONObject();
    	em_apns_ext.put("em_push_title", "房客自如客8b375:计划经济家🌍🌸🎍🎋🌴🌴🌷🍄🌸");
    	em_apns_ext.put("msgSenderType", "2");
    	em_apns_ext.put("ziroomFlag", "ZIROOM_MINSU_IM");
    	//j1.put("em_apns_ext", em_apns_ext);
    	ext1.put("endDate", "2017-10-20");
    	ext1.put( "fid", null);
    	ext1.put("em_expr_big_name", "fdfas");
    	ext1.put("houseCard", "0");
    	ext1.put("houseName", "虾米音乐听到你们说话就说话修改");
    	ext1.put("housePicUrl", "http://10.16.34.44:8000/minsu/group3/M00/01/D2/ChAiKlmTrrOAQilnAALtqo-I_94210.jpg_Z_1200_800.jpg");
    	ext1.put("msgSenderType", "2");
    	ext1.put("nicName",  "自如客48a9c");
    	ext1.put( "rentWay",  "1");
    	ext1.put("roleType", "2");
    	ext1.put("source", "2");
    	ext1.put("startDate", "2017-10-16");
    	ext1.put("versionCode", "100020");
    	ext1.put("ziroomFlag", "ZIROOM_MINSU_IM");
    	//payload
    	JSONObject payload = new  JSONObject();
    	payload.put("bodies", bodies);
    	payload.put("ext", ext1);
    	payload.put("from", "ziroomerPro" );
    	payload.put("to", "app_49a2f38f-078a-7276-c95f-c6fe91a53b6c");
    	
    	msgObj.put("payload", payload);
    	
    	String jsonString = msgObj.toJSONString();
    	return jsonString;
    }
       
 }  
