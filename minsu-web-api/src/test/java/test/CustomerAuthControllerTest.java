
package test;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.dto.BaseParamDto;
import com.ziroom.minsu.api.common.encrypt.DESEncrypt;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.customer.dto.UpCustomerPicDto;

/**
 * <p>用户测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class CustomerAuthControllerTest {
	
	private static 	IEncrypt iEncrypt=EncryptFactory.createEncryption("DES");
	private static DESEncrypt dESEncrypt  = new DESEncrypt();

	@Test
	public static void testGetCustomerVo() {
		BaseParamDto houseSnapshotApiRe=  new BaseParamDto();

		houseSnapshotApiRe.setUid("d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		System.err.println(JsonEntityTransform.Object2Json(houseSnapshotApiRe));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(houseSnapshotApiRe));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(houseSnapshotApiRe).toString(),"UTF-8"));
	}
	
	public static void customerIconAuth() {
		UpCustomerPicDto dto = new UpCustomerPicDto();
		dto.setPicType(2);
		System.err.println(JsonEntityTransform.Object2Json(dto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(dto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(dto).toString(),"UTF-8"));
    	
	}
	
    public static void customerIconAuthDES(){
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9ac6adb5041fb3a669dc78a66ce7654e6f405efd38cf6070c0f7bf4b8d243eca7cefa499ea7353faf712def2fc2d5ab01540ba64c1f93e6ef36e441651a2ca2574e959c74177b2c9ac0f8cb3684c7a271b9e105356546c8677d3dbf12e139b9b246";
		content = dESEncrypt.decrypt(content);
		
		System.out.println(content);
	}
	
	public static void main(String[] args) {
//		testGetCustomerVo();
		customerIconAuth();
		customerIconAuthDES();
		
	}

}
