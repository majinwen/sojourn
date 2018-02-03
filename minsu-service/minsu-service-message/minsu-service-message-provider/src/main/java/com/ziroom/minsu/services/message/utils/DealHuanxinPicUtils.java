/**
 * @FileName: GetPicFromHuanxinUtils.java
 * @Package com.ziroom.minsu.services.message.utils
 * 
 * @author loushuai
 * @created 2017年9月14日 下午8:03:01
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;

/**
 * <p>1：环信下载图片并图片写入到临时目录    2：获取图片base64码  3：删除图片   </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class DealHuanxinPicUtils {

	private final static Logger LOGGER = LoggerFactory.getLogger(SendImMesToHuanxinThread.class);

	/**
	 * 
	 * 根据图片地址，获取图片，并写入临时目录
	 *
	 * @author loushuai
	 * @created 2017年9月15日 上午11:06:02
	 *
	 * @param huanxinConfig
	 * @param redisOperations
	 * @param secret
	 * @throws IOException 
	 */
	public static void getPicFromHuanxin(String picUrl) {
		LogUtil.info(LOGGER, "getPicFromHuanxin, 根据图片地址，获取图片，并写入临时目录  picUrl={}",picUrl);
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
        try {
        	inputStream = getInputStream(picUrl);
        	//int read = inputStream.read();
            byte[] data = new byte[1024];
            int len = 0;
            fileOutputStream = new FileOutputStream("D:\\p.jpg");
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (Exception e) {
        	LogUtil.info(LOGGER, "getPicFromHuanxin, 根据图片地址，获取图片，并写入临时目录 发生错误",e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                	LogUtil.info(LOGGER, "getPicFromHuanxin, 根据图片地址，获取图片，并写入临时目录 发生错误",e);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                	LogUtil.info(LOGGER, "getPicFromHuanxin, 根据图片地址，获取图片，并写入临时目录 发生错误",e);
                }
            }
        }
	}
	
	
	/**
	 * 
	 * 根据picUrl从环信服务器端获取数据，以InputStream形式返回
	 *
	 * @author loushuai
	 * @created 2017年9月15日 上午11:14:15
	 *
	 * @param picUrl
	 * @return
	 * @throws IOException
	 */
    public static InputStream getInputStream(String picUrl) throws IOException {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(picUrl);
            if (url != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                // 设置连接网络的超时时间
                httpURLConnection.setConnectTimeout(5 * 1000);
                httpURLConnection.setDoInput(true);
                // 设置本次http请求使用get方式请求
                httpURLConnection.setRequestMethod("GET");
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    // 从服务器获得一个输入流
                    inputStream = httpURLConnection.getInputStream();
                    //int contentLength = httpURLConnection.getContentLength();
                }
            }
        } catch (MalformedURLException e) {
        	LogUtil.info(LOGGER, "getInputStream, 根据picUrl从环信服务器端获取数据，以InputStream形式返回 发生错误",e);
        }
        return inputStream;
    }
    //inputStream2String
   public static String inputStream2String(InputStream is){
    	StringBuffer buffer = new StringBuffer();
    	   try {
    		   BufferedReader in = new BufferedReader(new InputStreamReader(is));
        	   String line = "";
			while ((line = in.readLine()) != null){
			     buffer.append(line);
			   }
		} catch (IOException e) {
			e.printStackTrace();
		}
    	   String string = buffer.toString();
    	   return string;
    }
    
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  
 
    /**
     * 
     * 1，根据图片地址picUrl，获取图片，并写入临时目录tempPicUrl   2，获取图片base64码    
     *
     * @author loushuai
     * @created 2017年9月15日 上午11:27:43
     *
     * @param picUrl
     * @param tempPicUrl
     * @return
     * @throws Exception 
     */
    public static String GetImageStr(String url) throws Exception{//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        //getPicFromHuanxin(url);
        InputStream in = null;
        byte[] data = null;  
        //读取图片字节数组  
        try{  
        	//in = new FileInputStream("D:\\p.jpg");
            //测试直接编码看是否可以
            in = getInputStream(url);
            data = readInputStream(in);
            //data = new byte[1024*16];
            //data = new byte[count]; 
            //Thread.sleep(000);
            //in.read(data);  
            in.close(); 
        }   
        catch (IOException e){  
            e.printStackTrace();  
        }  
        //对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();  
        String encode = encoder.encode(data);
        String replace = encode.replace("\r\n", "");
        return replace;//返回Base64编码过的字节数组字符串  
    }  
    
	
	/**
	 * 
	 * 删除临时图片
	 *
	 * @author loushuai
	 * @created 2017年9月15日 上午11:36:32
	 *
	 * @param imgFile
	 */
	public static void deleteTempPic(String imgFile){
		File file = new File(imgFile);
		file.delete();
	}
	
	
	 /**
	   * 将网络图片进行Base64位编码
	   * 
	   * @param imgUrl 图片的url路径，如http://.....xx.jpg
	   * @return
	   */
	  public static String encodeImgageToBase64(String picUrl) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		    if(Check.NuNStr(picUrl)){
			   return null;
		    }
		    ByteArrayOutputStream outputStream = null;
			try {
			  URL imageUrl = new URL(picUrl);
			  BufferedImage bufferedImage = ImageIO.read(imageUrl);
			  outputStream = new ByteArrayOutputStream();
			  ImageIO.write(bufferedImage, "jpg", outputStream);
			} catch (MalformedURLException e1) {
				LogUtil.info(LOGGER, "encodeImgageToBase64  将网络图片进行Base64位编码 发生错误",e1);
			} catch (IOException e) {
				LogUtil.info(LOGGER, "encodeImgageToBase64  将网络图片进行Base64位编码 发生错误",e);
			}
			// 对字节数组Base64编码
			BASE64Encoder encoder = new BASE64Encoder();
			String encode = encoder.encode(outputStream.toByteArray());
			String replace = encode.replace("\r\n", "");
			
			
			return replace;// 返回Base64编码过的字节数组字符串
	  }
	  
	    public static void main(String[] args) throws Exception {
	    	String imageStr = encodeImgageToBase64("https://a1.easemob.com/ziroom/ziroom/chatfiles/125c11c0-99ba-11e7-a292-a7092309a90c");
	    	// System.out.println(imageStr);
	    	//String a = imageStr;
	    	//System.out.println(a);
	        //deleteTempPic("D:\\p.jpg");
	        // 从服务器获得图片保存到本地
	    	//getPicFromHuanxin("https://a1.easemob.com/ziroom/ziroom/chatfiles/125c11c0-99ba-11e7-a292-a7092309a90c");
	        // System.out.println("传输步骤完毕");
	        //String imageStr = GetImageStr("https://a1.easemob.com/ziroom/ziroom/chatfiles/125c11c0-99ba-11e7-a292-a7092309a90c");
	    	//String imageStr = GetImageStr("http://www.51pptmoban.com/d/file/2013/01/15/223237633d1682702abee0a9902d75c3.jpg");
	        System.out.println(imageStr);
	    }
}
