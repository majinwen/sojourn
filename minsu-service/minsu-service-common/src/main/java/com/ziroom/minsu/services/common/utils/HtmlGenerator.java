package com.ziroom.minsu.services.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/14
 * 利用HtmlClient生成静态页面
 */
public class HtmlGenerator {

    private static final Logger logger = LoggerFactory.getLogger(HtmlGenerator.class);
    private static String UTF_8 = "UTF-8";  
  
    /**
     * 
     * 获取HttpClient 
     *
     * @author zl
     * @created 2017年5月8日 下午8:30:12
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {  
        return HttpClients.custom().build();  
    } 
    
    

    /** 根据模版及参数产生静态页面 */
    public static String createHtmlPage(String url){
        String page = null;
        HttpGet getMethod = new HttpGet(url);//创建GET方法的实例
        //创建一个HttpClient实例充当模拟浏览器
        CloseableHttpClient httpClient = getHttpClient();
        
        try{
            //设置Get方法提交参数时使用的字符集,以支持中文参数的正常传递
            getMethod.addHeader("Content-Type","text/html;charset=UTF-8");
            //执行Get方法并取得返回状态码，200表示正常，其它代码为	异常
            CloseableHttpResponse response = httpClient.execute(getMethod); 
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                logger.info("静态页面引擎在解析"+url+"时出错!");
                page = "";
            }else{
                //读取解析结果
            	StringBuffer sb = new StringBuffer();
            	
            	try {
    				HttpEntity entity = response.getEntity();
    				if (entity != null) {
	    				InputStream in = entity.getContent();
	    				BufferedReader br = new BufferedReader(new InputStreamReader(in,UTF_8));
	    				String line = null;
	    				while((line = br.readLine())!=null){
	    					sb.append(line+"\n");
	    				}
	    				
	    				try {
	                    	if(br!=null)br.close();
	    				}catch(Exception e){
	    					e.printStackTrace();
	    				}
	    				try {
	                    	if(in!=null)in.close();
	    				}catch(Exception e){
	    					e.printStackTrace();
	    				}
	    				
    				}
    				
    			} catch (ClientProtocolException e) {  
    	            e.printStackTrace();  
    	        } catch (IOException e) {  
    	            e.printStackTrace();  
    	        } finally {
    				response.close();
    			}
                page = sb.toString();
            }
        }catch(Exception ex){
            logger.error("静态页面引擎在解析"+url+"时出错:"+ex);
        }finally{
            //释放http连接
        	getMethod.abort();
            try {
            	httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
        }
        return page;
    }
 
    
    /**
     * 
     * 写文件
     *
     * @author bushujie
     * @created 2016年11月17日 下午2:57:22
     *
     * @param htmlFileName
     * @param page
     * @throws IOException 
     * @throws Exception
     */
    public static void writeHtml(String htmlFileName,String page) throws IOException{
    	File file = new File(htmlFileName);
    	if(!file.getParentFile().exists()){
    	    file.getParentFile().mkdirs();
    	}
    	file.createNewFile();
    	
    	OutputStreamWriter bw = new OutputStreamWriter(new FileOutputStream(file),UTF_8);
    	try {
    		bw.write(page);
		}finally {
			try {
				if(bw!=null)bw.close();				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
     }

}
