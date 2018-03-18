package com.zra.common.utils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * http接口调用工具类
 * @author busj
 *
 */
public class HttpClientUtils {
	
	public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }
    
    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }
    
    
    public static void main(String[] args){
//    	String resultJson=doPost("http://smsapi.t.ziroom.com/API/webservice/maintenanceVisualization!getGoodsList.do");
//    	JSONObject object=JSONObject.parseObject(resultJson);
//    	System.err.println(object.get("status"));
//    	System.err.println(resultJson);
    	
//    	JSONObject paramObject=new JSONObject();
//    	paramObject.put("goodsCode", "8a9e9aa65d82bc15015d82c0ae470004");
//    	paramObject.put("serviceId", "002");
//        Map<String, String> param=new HashMap<String, String>();
//        param.put("versionNumberStr", SysPropUtil.getSystemConstant("REPAIR_VERSION"));
//        param.put("json", paramObject.toJSONString());
//        String resultJson=HttpClientUtils.doPost(SysPropUtil.getSystemConstant("REPAIR_VISUALIZATION"), param);
//        System.err.println(resultJson);
//        JSONObject resultObject=JSONObject.parseObject(resultJson);
//        JSONObject data=resultObject.getJSONObject("data");
//        JSONArray repairGoodsList=data.getJSONArray("repairGoodsList");
//
//        System.err.println(repairGoodsList.getJSONObject(0).getJSONArray("faultFlagList").toString());
//      
//    	JSONObject paramObject=new JSONObject();
//    	paramObject.put("cuid", "621647a53225404eab024c3126c1cd7f");
//    	paramObject.put("isConfirm", 0);
//    	paramObject.put("contactPhone", "15811361402");
//    	paramObject.put("contactName", "大风");
//    	paramObject.put("operationPhone", "15811361402");
//    	paramObject.put("operationName", "大风");
//    	paramObject.put("dataSource", 13);
//    	paramObject.put("businessCode", 2);
//    	paramObject.put("resblockId", "1111027375919");
//    	paramObject.put("address", "北京市朝阳区将台乡东八间房村临8号");
//    	paramObject.put("cityCode", "110000");
//    	paramObject.put("contractCode", "PJ50050200001");
//    	paramObject.put("houseCode", "PJ50050200001");
//    	paramObject.put("rentContractCode", "PJ50050200001");
//    	paramObject.put("houseSourceCode", "PJ50050200001");
//    	JSONArray wxxmList=new JSONArray();
//    	JSONObject wxxm=new JSONObject();
//    	wxxm.put("orderTime", "2017-09-29");
//    	wxxm.put("orderTimeInterval", "13:00");
//    	wxxm.put("kongJianId", "8a908eca5da2413d015da24f4a70000c");
//    	wxxm.put("goodsId", "8a908eca5da2413d015da2571b890015");
//    	wxxm.put("remark", "fffff");
//    	wxxmList.add(wxxm);
//    	JSONArray lableList=new JSONArray();
//    	JSONObject logic=new JSONObject();
//    	logic.put("logicCode", "8a908eca5da2ef23015da5f1219a0003");
//    	lableList.add(logic);
//    	wxxm.put("lableCodeList", lableList);
//    	paramObject.put("wxxmList", wxxmList);
//	    Map<String, String> param=new HashMap<String, String>();
//	    param.put("versionNumberStr", SysPropUtil.getSystemConstant("REPAIR_VERSION"));
//	    param.put("json", paramObject.toString());
//	    String resultJson=HttpClientUtils.doPost("http://smsapi.t.ziroom.com/API/webservice/maintenanceVisualization!save.do", param);
//	    System.err.println(resultJson);
    	
//    	JSONObject paramObject=new JSONObject();
//    	paramObject.put("zhutiCode", "8a908eca5da2413d015da24f4a70000c");
//    	paramObject.put("cityCode", "110000");
//    	paramObject.put("goodsId", "8a908eca5da2413d015da2571b890015");
//    	paramObject.put("tagId", "8a908eca5da2ef23015da5f1219a0003");
//    	paramObject.put("nowDate", DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
//	    Map<String, String> param=new HashMap<String, String>();
//	    param.put("versionNumberStr", SysPropUtil.getSystemConstant("REPAIR_VERSION"));
//	    param.put("json", paramObject.toString());
//    	String resultJson=HttpClientUtils.doPost("http://smsapi.d.ziroom.com/API/webservice/maintenanceVisualization!findAppointmentTime.do", param);
//    	System.err.println(resultJson);
    	
    	
    	Map<String, String> param=new HashMap<String, String>();
    	JSONArray billArray=new JSONArray();
    	billArray.add("WX20171010135707");
    	billArray.add("WX20171011135721");
    	param.put("billNums", "WX20171010135707");
    	String resultJson=HttpClientUtils.doGet("http://smsapi.t.ziroom.com/API/webservice/maintenance!getBillList.do", param);
    	System.err.println(resultJson);
    	JSONObject resultObj=JSONObject.parseObject(resultJson);
    	JSONObject data=resultObj.getJSONObject("data");
    	JSONArray list=data.getJSONArray("list");
    	JSONObject order=list.getJSONObject(0);
    	System.err.println(order.toJSONString());
    }
}
