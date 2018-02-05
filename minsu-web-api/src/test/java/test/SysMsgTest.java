/**
 * @FileName: SysMsgTest.java
 * @Package test
 * 
 * @author jixd
 * @created 2016年4月21日 下午1:51:52
 * 
 * Copyright 2011-2015 asura
 */
package test;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class SysMsgTest {
	public static void main(String[] args) {

		Map<String,Object> map = new HashedMap();
		/*map.put("landlordUid", "8a9e9a8b53d6da8b0153d6da8bae0000");
		map.put("lanOrderType", "1");
		map.put("limit", "10");
		map.put("page", "1");
		map.put("uid", "8a9e9a8b53d6da8b0153d6da8bae0000");
		map.put("loginToken", "2222");*/
		
		
		/*map.put("page", 1);
		map.put("limit", 10);
		map.put("tenantOrderStatus", 2);
		map.put("uid", "8a9e9a9e543d23f901543d23f9e90000");
		map.put("loginToken", "2222");*/
		
		map.put("uid", "f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
		map.put("orderSn", "160511Z1759KK9190040");
		
		
		String json = JsonEntityTransform.Object2Json(map);
		System.out.println(json);
		
		IEncrypt encrypt = EncryptFactory.createEncryption("DES");
		String ddd = encrypt.encrypt(json);
		//参数加密
		System.out.println("2y5QfvAy:"+ ddd);
		
		
		//签名
		String sign = MD5Util.MD5Encode(json,"UTF-8");
		//md5 签名
		System.out.println("hPtJ39Xs:"+sign);
		String result ="b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9ac375bcd57a16159e1";
		
		System.err.println(encrypt.decrypt(result));
		
		
		
	}
	
	
	
	
	
	
}
