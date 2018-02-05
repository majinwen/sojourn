
package com.ziroom.minsu.api.test.evaluate.controller;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.evaluate.dto.LanlordEvaApiRequest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;

/**
 * <p>房东评价测试</p>
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
public class LandlordEvaControllerTest {
	
	
	private static 	IEncrypt iEncrypt=EncryptFactory.createEncryption("DES");


	public static void testLanEvaluate() {
		LanlordEvaApiRequest lanlordEvaApiRequest  = new LanlordEvaApiRequest();
		
		
		lanlordEvaApiRequest.setOrderSn("16050267DY79C7174903");
		lanlordEvaApiRequest.setUid("8a9e9a8b53d6da8b0153d6da8bae0000");
		lanlordEvaApiRequest.setLoginToken("dfkdkfkdjf223j2kj32kj32j32j");
		lanlordEvaApiRequest.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
		
		lanlordEvaApiRequest.setContent("发的顺丰就偶是地方绝对是泼妇3333333333333333");
		lanlordEvaApiRequest.setLandlordSatisfied(3);
		
		
		System.err.println(JsonEntityTransform.Object2Json(lanlordEvaApiRequest));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(lanlordEvaApiRequest));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(lanlordEvaApiRequest).toString(),"UTF-8"));
	}
	/**
	 * 
	 * 测试
	 *
	 * @author yd
	 * @created 2016年5月3日 下午10:42:40
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		testLanEvaluate();
		
	}

}
