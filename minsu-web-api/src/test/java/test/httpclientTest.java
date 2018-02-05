
/**
 * @FileName: httpclientTest.java
 * @Package test
 *
 * @author lusp
 * @created 2017年6月20日 下午9:01:40
 *
 */
package test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>

 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 *
 * @author lusp
 * @since 1.0
 * @version 1.0
 */
public class httpclientTest {


	private static IEncrypt iEncrypt= EncryptFactory.createEncryption("DES");

	/**
	 * @Description:
	 * @Author: lusp
	 * @Date: 2017/6/20 21:02
	 * @Params:
	 */
	public static void main(String[] args) throws IOException {

		/***************测试上传房源图片接口***************/

//		PicParamDto picParamDto = new PicParamDto();
//		picParamDto.setHouseBaseFid("8a9084df57ea57280157eb1d1f09001d");
//		picParamDto.setPicType(4);
//		String url = "http://localhost:8080/house/ea61d2/saveHousePic";
//		String filePath = "C:\\Users\\Public\\Pictures\\Sample Pictures\\file.png";
//		String result = sendPost(url,picParamDto,filePath);
//		System.out.print(result);

		/***************测试上传房源图片接口***************/


		/***************测试房源照片列表、回显接口***************/
//		JSONObject requestJson = new JSONObject();
//		requestJson.put("houseBaseFid","8a90a2d45d2b9fa7015d2bb0367c003f");
//		requestJson.put("rentWay",0);
////		requestJson.put("roomFid","8a9084df550d9bdd01550da88a020062");
//		String url = "https://tbnbapi.ziroom.com/housePic/ea61d2/housePhotosList";
////		String url = "http://localhost:8080/housePic/ea61d2/housePhotosList";
//		String result = sendPost(url,requestJson,"");
//		System.out.print(result);
		/***************测试房源照片列表、回显接口***************/


		/***************测试房源照片删除接口***************/
//		JSONObject requestJson = new JSONObject();
//		requestJson.put("houseBaseFid","8a9e989e5cc590b7015cc596f7ee0004");
//		requestJson.put("housePicFid","8a9e989e5cc590b7015cc59a3cec000e");
//		requestJson.put("picType",4);
////		requestJson.put("isDefault",0);
//		requestJson.put("houseRoomFid","8a9e989e5cc590b7015cc5990c080008");
//
//		String url = "https://dbnbapi.ziroom.com/housePic/ea61d2/deleteHousePic";
////		String url = "http://localhost:8080/housePic/ea61d2/deleteHousePic";
//		String result = sendPost(url,requestJson,"");
//		System.out.print(result);
		/***************测试房源照片删除接口***************/


		/***************测试房源设置封面照片接口***************/
//		JSONObject requestJson = new JSONObject();
//		requestJson.put("houseBaseFid","8a9e989e5cc590b7015cc596f7ee0004");
//		requestJson.put("housePicFid","8a9e989e5cc590b7015cc59a3cec000e");
//		requestJson.put("picType",0);
//		requestJson.put("isDefault",0);
//		requestJson.put("houseRoomFid","8a9e989e5cc590b7015cc5990c080008");
//
//		String url = "http://localhost:8080/housePic/ea61d2/setDefaultPic";
//		String result = sendPost(url,requestJson,"");
//		System.out.print(result);
		/***************测试房源设置封面照片接口***************/


		/***************测试管理房源接口***************/
//		JSONObject requestJson = new JSONObject();
//		requestJson.put("houseBaseFid","8a9e989e5cc590b7015cc596f7ee0004");
//		requestJson.put("rentWay",0);
////		requestJson.put("housePicFid","8a9e989e5cc590b7015cc59a3cec000e");
////		requestJson.put("picType",0);
////		requestJson.put("isDefault",0);
////		requestJson.put("houseRoomFid","8a9e989e5cc590b7015cc5990c080008");
//
//		String url = "http://localhost:8080/housePic/ea61d2/initHouseDesc";
//		String result = sendPost(url,requestJson,"");
//		System.out.print(result);
		/***************测试房源设置封面照片接口***************/


		/***************（整租）测试房源管理中房源描述及基础信息接口***************/
//		JSONObject requestJson = new JSONObject();
//		requestJson.put("houseBaseFid","8a9e989e5cc590b7015cc596f7ee0004");
//		requestJson.put("rentWay",0);
//
////		String url = "https://dbnbapi.ziroom.com/houseIssue/ea61d2/initDescAndBaseInfoEntire";
//		String url = "http://localhost:8080/houseIssue/ea61d2/initDescAndBaseInfoEntire";
//		String result = sendPost(url,requestJson,"");
//		System.out.print(result);
		/***************测试房源管理中房源描述及基础信息接口***************/


		/***************（分租）测试房源管理中房源描述及基础信息接口***************/
//		JSONObject requestJson = new JSONObject();
//		requestJson.put("houseBaseFid","8a9e9a9a548abce301548ae20cfc0047");
//		requestJson.put("rentWay",1);
//		requestJson.put("roomFid","8a9e9a94547fadb601547fadb77f0012");
//
////		String url = "https://dbnbapi.ziroom.com/houseIssue/ea61d2/initDescAndBaseInfoSublet";
//		String url = "http://localhost:8080/houseIssue/ea61d2/initDescAndBaseInfoSublet";
//		String result = sendPost(url,requestJson,"");
//		System.out.print(result);
		/***************测试房源管理中房源描述及基础信息接口***************/


		/***************发布房源接口***************/
//		JSONObject requestJson = new JSONObject();
//		requestJson.put("houseBaseFid","8a90a2d45d1c2c95015d1c36f418005e");
//		requestJson.put("rentWay",1);
////		requestJson.put("roomFid","null");
//		requestJson.put("landlordUid","1233587a-7096-4737-b4f7-e2c142b6e64d");
//
////		String url = "https://tbnbapi.ziroom.com/housePic/ea61d2/issueHouse";
//		String url = "http://localhost:8080/housePic/ea61d2/issueHouse";
//		String result = sendPost(url,requestJson,"");
//		System.out.print(result);
		/***************发布房源接口***************/


		/***************（分租）测试房源管理中房源描述及基础信息接口***************/
//		JSONObject requestJson = new JSONObject();
//		requestJson.put("houseBaseFid","8a9084df556cef6e01556d0ded1e0014");
//		requestJson.put("rentWay",1);
//		requestJson.put("roomFid","8a9084df556cd72c01556d0eaf1b000d");
//
////		String url = "https://dbnbapi.ziroom.com/houseIssue/ea61d2/initTypeLocation";
//		String url = "http://localhost:8080/houseIssue/ea61d2/initDescAndBaseInfoSublet";
//		String result = sendPost(url,requestJson,"");
//		System.out.print(result);

		/***************（分租）测试房源管理中房源描述及基础信息保存接口***************/
//		JSONObject requestJson = new JSONObject();
//		requestJson.put("houseBaseFid","8a9084df556cef6e01556d0ded1e0014");
//		requestJson.put("rentWay",1);
//		requestJson.put("roomFid","8a9084df556cd72c01556d0eaf1b000d");
//		requestJson.put("isTogetherLandlord",0);
//		requestJson.put("supportArray","ProductRulesEnum002002_2,ProductRulesEnum002001_1,ProductRulesEnum002003_2");
//		requestJson.put("roomNum",4);
//		requestJson.put("parlorNum",2);
//		requestJson.put("toiletNum",2);
//		requestJson.put("kitchenNum",1);
//		requestJson.put("balconyNum",3);
//		requestJson.put("houseDesc","房源描述");
//		requestJson.put("houseAroundDesc","周边信息");
//
////		String url = "https://dbnbapi.ziroom.com/houseIssue/ea61d2/saveDescAndBaseInfoSublet";
//		String url = "http://localhost:8080/houseIssue/ea61d2/saveDescAndBaseInfoSublet";
//		String result = sendPost(url,requestJson,"");
//		System.out.print(result);
		/***************（分租）测试房源管理中房源描述及基础信息保存接口***************/

		//测试首页接口
		String url = "https://dbnbapi.ziroom.com/firstPageService/ea61d2/fillFirstPageInfo";
		String result = sendPost(url,null,"");
		System.out.print(result);

	}

	public static String sendPost(String url,Object obj,String filePath)throws IOException{
		//加密参数
		String params=iEncrypt.encrypt(JsonEntityTransform.Object2Json(obj));

		String _params = iEncrypt.decrypt(params);

		//创建签名
		String sign = MD5Util.MD5Encode(_params, ConstDef.DEFAULT_CHARSET);

		//创建httpclient实例
		HttpClient httpClient = new HttpClient();

		//创建post方法实例
		PostMethod postMethod = new PostMethod(url+"?2y5QfvAy="+params+"&hPtJ39Xs="+sign);

		System.out.println("?2y5QfvAy="+params+"&hPtJ39Xs="+sign);

		//添加头信息
		List<Header> headers = new ArrayList<>();
		headers.add(new Header("client-version", "1.0"));
		headers.add(new Header("client-type", "2"));
		headers.add(new Header("user-agent", "test"));
		headers.add(new Header("token", "454521f212d1f12d"));
//		headers.add(new Header("uid", "4f54d5f45d4f"));
		httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);

		//使用系统提供的默认恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

		if(!filePath.equals("")&&filePath!=null){
			//创建文件实体
			File file = new File(filePath);
			FilePart fp = new FilePart("file", file);
			Part[] parts = {fp};
			MultipartRequestEntity multipartRequestEntity = new MultipartRequestEntity(parts, postMethod.getParams());

			postMethod.setRequestEntity(multipartRequestEntity);
		}

		// 执行postMethod
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed:" + postMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = postMethod.getResponseBody();
			// 处理内容
			String _responseString = new String(responseBody);

			System.out.println(iEncrypt.decrypt(_responseString));

			return iEncrypt.decrypt(_responseString);

		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
	}



}
