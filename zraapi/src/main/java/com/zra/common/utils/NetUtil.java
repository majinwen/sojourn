package com.zra.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;

/**
 * 网络工具类
 *
 * @author Administrator
 */
public class NetUtil {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(NetUtil.class);

    private NetUtil() {

    }

    public static byte[] getImage(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(6 * 1000);
        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            return readStream(inputStream);
        }
        return null;
    }

    public static String getHtml(String path, String encoding) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(6 * 1000);
        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            byte[] data = readStream(inputStream);
            return new String(data, encoding);
        }
        return null;
    }

    /**
     * description:通过HTTPget请求获取数据 Nov 23, 2013
     *
     * @author:sajh
     */
    public static String getDataByHttpGet(String path) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(path);
        HttpResponse httpResponse = httpClient.execute(httpget);

        HttpEntity ett = httpResponse.getEntity(); // 获取响应里面的内容

        String str = EntityUtils.toString(ett);
        httpClient.getConnectionManager().shutdown();// Add By LiuM 2014-12-3
        return str;
    }

    /**
     * description:通过HTTPget请求获取数据 Nov 23, 2013
     *
     * @author:sajh
     */
    public static String getDataByHttpGet(URI uri) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpget);

        HttpEntity ett = httpResponse.getEntity(); // 获取响应里面的内容

        String str = EntityUtils.toString(ett);
        httpClient.getConnectionManager().shutdown();// Add By LiuM 2014-12-3
        return str;
    }

    /**
     * 发送 XML 请求
     *
     * @param path
     * @param map
     * @param encoding
     * @return
     * @throws Exception
     */
    public static InputStream sendXmlRequest(String path, InputStream inputStream, String encoding) throws Exception {
        byte[] data = readStream(inputStream);
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setConnectTimeout(6 * 1000);
        con.setDoOutput(true);// 发送 POST 请求必须允许输出
        con.setUseCaches(false);// 不设置 cache
        con.setRequestProperty("Connection", "Keep-Alive");// 维持长链接
        con.setRequestProperty("Charset", encoding);
        con.setRequestProperty("Content-Length", String.valueOf(data.length));
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        DataOutputStream dataOutputStream = new DataOutputStream(con.getOutputStream());
        dataOutputStream.write(data);
        dataOutputStream.flush();
        dataOutputStream.close();
        if (con.getResponseCode() == 200) {
            return con.getInputStream();
        }
        return null;
    }

    /**
     * 发送 POST 请求
     *
     * @param path 请求路径
     * @param map  参数
     * @return 如果为空设置 GET 请求
     * @throws Exception
     */
    public static InputStream sendPostRequest(String path, Map<String, String> map) throws Exception {
        LOGGER.info("[请求网络]地址:" + path + ";参数:" + map);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            sb.append('&');
        }
        sb.deleteCharAt(sb.length() - 1);

        byte[] data = sb.toString().getBytes();
        LOGGER.info("[请求网络]地址:" + path + ";encode之后的参数信息:" + map);
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setConnectTimeout(20 * 1000);
        con.setDoOutput(true);// 发送 POST 请求必须设置允许输出
        con.setUseCaches(false);// 不设置 cache
        con.setRequestProperty("Connection", "Keep-Alive");// 维持长链接
        con.setRequestProperty("Charset", "UTF-8");
        con.setRequestProperty("Content-Length", String.valueOf(data.length));
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        DataOutputStream dataOutputStream = new DataOutputStream(con.getOutputStream());
        dataOutputStream.write(data);
        dataOutputStream.flush();
        dataOutputStream.close();
        if (con.getResponseCode() == 200) {
            return con.getInputStream();
        }
        return null;
    }

    /**
     * description:发送post请求，参数是json字符串格式的 Apr 27, 2014
     *
     * @author:sajh
     */
    public static String sendPostReqByJsonParam(String jsonStr, String url) throws Exception {

        try {
            // 创建连接
            URL requestUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            conn.connect();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            // json参数
            out.writeBytes(jsonStr);
            out.flush();
            out.close();

            // 获取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String lines;
            StringBuilder sb = new StringBuilder();
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            // 关闭连接
            conn.disconnect();
            return sb.toString();
        } catch (Exception e) {
            LOGGER.error("发送post请求失败", e);
            throw new Exception("发送post请求失败：URL=" + url + " param=" + jsonStr);
        }
    }

    /**
     * 发送Get请求
     *
     * @param path
     * @param map
     * @return
     * @throws Exception
     * @Author: wangxm113
     * @CreateDate: 2016年5月5日
     */
    public static String sendGetRequest(String path, Map<String, String> map) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            sb.append('&');
        }
        sb.deleteCharAt(sb.length() - 1);
        path = path + sb.toString();
        InputStream content = getContent(path);
        String result = new String(content == null ? new byte[0] : readStream(content), "UTF-8");
        return result;
    }

    /**
     * 把输入流转换成字符串
     *
     * @param inputStream
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String getTextContent(InputStream inputStream, String encoding) throws Exception {
        byte[] data = readStream(inputStream);
        return new String(data, encoding);
    }

    /**
     * 根据路径获返回输入流
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static InputStream getContent(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(6 * 1000);
        if (conn.getResponseCode() == 200) {
            return conn.getInputStream();
        }
        return null;
    }

    /**
     * 把输入流转换成字节数组
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static byte[] readStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outputSteam = new ByteArrayOutputStream();// 往内存中写字节数据
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inputStream.read(buffer)) != -1) {
            outputSteam.write(buffer, 0, len);
        }

        inputStream.close();
        outputSteam.close();
        return outputSteam.toByteArray();
    }


    /**
     * 上传图片到服务器
     *
     * @param URL
     * @param bytes
     * @param filename
     * @return
     */
    public static String httpUploadTo(String URL, byte[] bytes, String filename) {
        String text = null;
        try {
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 发送POST请求必须设置如下两行 ,请求头
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=---------------------------------0xKhTmLbOuNdArY");

            conn.setRequestProperty("p", "12312321");
            conn.setRequestProperty("sign", "1231231");
            conn.setConnectTimeout(10000);
            conn.connect();
            OutputStream out = conn.getOutputStream();

            // 请求体 Postbody
            out.write(bytes);
            out.write("\r\n-----------------------------------0xKhTmLbOuNdArY--\r\n"
                    .getBytes("utf-8"));
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (text != null) {
                    text += line;
                } else {
                    text = line;
                }
            }
            conn.disconnect();
        } catch (Exception e) {
            LOGGER.error("发送文件出错！", e);
        }
        return text;
    }

    /**
     * 可以设置header的post请求
     *
     * @Author: wangxm113
     * @CreateDate: 2017-03-09
     */
    public static String sendPostRequest(String path, Map<String, String> headerMap, Map<String, String> map) throws Exception {
        LOGGER.info("[请求网络]地址:" + path + ";头信息:" + headerMap + ";参数:" + map);
        String body = "";
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(path);
        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        if (headerMap != null) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        LOGGER.info("[请求网络]地址:" + path + ";返回status:" + response.getStatusLine().getStatusCode());
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, HTTP.UTF_8);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        LOGGER.info("[请求网络]地址:" + path + ";返回response:" + body);
        return body;
    }

    /**
     * 可以设置header的post请求
     *
     * @Author: wangxm113
     * @CreateDate: 2017-03-09
     */
    public static String sendGetRequest(String path, Map<String, String> headerMap, Map<String, String> map) throws Exception {
        LOGGER.info("[请求网络]地址:" + path + ";头信息:" + headerMap + ";参数:" + map);
        String body = "";
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //装填参数
        if (map != null && map.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                sb.append('&');
            }
            sb.deleteCharAt(sb.length() - 1);
            path = path + sb.toString();
        }
        ///创建get方式请求对象
        HttpGet httpGet = new HttpGet(path);
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        if (headerMap != null) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpGet);
        LOGGER.info("[请求网络]地址:" + path + ";返回status:" + response.getStatusLine().getStatusCode());
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, HTTP.UTF_8);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        LOGGER.info("[请求网络]地址:" + path + ";返回response:" + body);
        return body;
    }
    
    
    
	/**description:发送post请求，参数是json字符串格式的
	 * Apr 27, 2014
	 * @author:sajh
	 */
	public static String sendPostReqByJsonParamForCompetency2(String jsonStr,String url)throws Exception{
		LOGGER.info("sendPostReqByJsonParamForCompetency:"+jsonStr+",url:"+url);
		try {
		    // 创建连接
		    URL requestUrl = new URL(url);
		    HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
		    conn.setDoOutput(true);
		    conn.setDoInput(true);
		    conn.setUseCaches(false);
		    conn.setRequestMethod("POST");
		    conn.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
		    conn.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			
		    conn.connect();
		    OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
		    // json参数
		    out.append(jsonStr);
		    out.flush();
		    out.close();
		    
		    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		    String lines;
		    StringBuilder sb = new StringBuilder();
		    while((lines = reader.readLine()) != null){
		        //lines = new String(lines.getBytes(), "UTF-8");
		        sb.append(lines);
		    }
		    return sb.toString();
			
		} catch (Exception e) {
			LOGGER.info("",e);
			throw new Exception("发送post请求失败：URL="+url+" param="+jsonStr);
		}
	}
}
