/**
 * @FileName: LanlordCenterControllerTest.java
 * @Package com.ziroom.minsu.api.test.customer.controller
 * 
 * @author yd
 * @created 2016年9月11日 上午11:32:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.test.customer.controller;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.dto.BaseParamDto;
import com.ziroom.minsu.api.common.encrypt.DESEncrypt;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.customer.dto.AuthMsgDto;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.valenum.customer.CustomerIdTypeEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>房东个人中心测试</p>
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
public class LanlordCenterControllerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public static void getCertification(){
//		BaseParamDto baseParamDto  = new BaseParamDto();
//		baseParamDto.setUid("8a9e9a9f544b35ff01544b35ff950000");
//		System.err.println(JsonEntityTransform.Object2Json(baseParamDto));

		String param = "{\"uid\":\"f471fb50-5beb-45db-bb86-5159174323d0\"}";
		String paramJson=iEncrypt.encrypt(param);
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(param,"UTF-8"));
    }

	@Test
	public static void saveContactInfo(){
		String param = "{\"uid\":\"8a9e9a9f544b372101544b3721de0000\", \"areaCode\":\"86\", \"customerMobile\":\"13021196666\", " +
				"\"certificateCode\":\"852776\"}";
		String paramJson=iEncrypt.encrypt(param);
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(param,"UTF-8"));
	}


	private static 	IEncrypt iEncrypt=EncryptFactory.createEncryption("DES");
	private static DESEncrypt dESEncrypt  = new DESEncrypt();

	public static void initCustomerCenterTest() {
		
		BaseParamDto baseParamDto  = new BaseParamDto();
		baseParamDto.setUid("8a9e9a9f544b35ff01544b35ff950000");
		System.err.println(JsonEntityTransform.Object2Json(baseParamDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(baseParamDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(baseParamDto).toString(),"UTF-8"));
	}

	public static void checkImgCodeTest() {
		String param = "{\"imgId\":\"8a9e98b45ce3235a015ce323b1a70002\", \"imgCode\":\"jadj\", \"areaCode\":\"66\", " +
				"\"phone\":\"15810006510\"}";
		String paramJson=iEncrypt.encrypt(param);
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(param,"UTF-8"));
	}

	public static void getHouseBaseTest() {
		String param = "{\"fid\":\"8a9e9a9454801ac501548026fb610029\", \"rentWay\":0}";
		String paramJson=iEncrypt.encrypt(param);
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(param,"UTF-8"));
	}
	
	public static void updateCustomerInfo() {
		CustomerBaseMsgEntity dto = new CustomerBaseMsgEntity();
		dto.setUid("1e758364-efd3-4645-8804-0ccc5e2c927c");
		dto.setCustomerEmail("754754@qq.com");
		dto.setCustomerJob("fjfj");
		dto.setResideAddr("8888888888888888");
		 
		System.err.println(JsonEntityTransform.Object2Json(dto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(dto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(dto).toString(),"UTF-8"));
	}

	public static void saveCustomerInfo() {
		String param = "{\"uid\":\"8a9e9a9f544b372101544b3721de0000\", \"nickName\":\"锤子2\", \"customerIntroduce\":\"介绍介绍十个字十个字\"}";
		String paramJson=iEncrypt.encrypt(param);
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(param,"UTF-8"));
	}

	public static void updateCustomerInfoTestDES() {
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acdaa88ad8137fd10f19c5e64c0a559228d666da64efb1035820b6a03e8e33149188349242e93fe6801424a872b2981414aa8d299e9d55b201e902ba4a2cb06f53c21098ef7b13318bfc070b72c59c8c091a7e858ef91c618e3bd5650a25e073679f1776a60aed45286736e51294788a3411ad33ad9125942905dc01df10c08e9e9104dbe64507b7800cbd82eac6c826fd8e6eb3e72883f066965c0e101d34c3fd0fe53f7628875d011f70a6f4be7a865a46ff93dcf6e67d0f7d2e6aff701db70e374169ad61626a668866aeb208c24840c66365cad1deb6077a6857495b5ff0623369902cbaee84bceb417a353546d956d8250e18338b5bbb08d06ac4ce70d4d1352ba0fb8a253c0769b82c1dba3675c9d18c94d551d6804b5241f81bdbaeaa1e30facbc0fc11d0a673f35a7dc515a7078372c2a05b530988fe4357b113f3d35332faa8707986590598b5bfa384c760be1ed6d2c4e18b392e79d6588775738e8860d5fad4fb2268601c39f2066f9f202ad86bb4fb0d9f7fb97b764c6762807730a655cec6ec0af771d48658b872f98c0a1ddb62dd1ecb093de152f378160e0b0693da309ab183b2b180830edea7e30780d4f483b5a18757b7d9d2baea0e3217aef9e437f9e91fc2115ce79ce83eb386cd857d4bdd5a1d9d5836678a3388fdacd89db1e15c5e256a4793b55f861d30f6628dd8be14814c13a1b846b1d80c4e31f665826b2ecf7a4ab2cb1d0a7f3a89bb8ccad7f82a1168c7a74ed8c9dbe18f1d803e8874faf21f9dba5320013e4a62a9dca7d20512469b1f17d1db27ec1fd7244135b4e81378f5f0d2ed2bb1a32bbe1e41d0fc091a19439f3d084ddd515804d33a11624093bf25726445053fd6dccb535720326f2617fb78cf95f7fe565aaa593ae4dd3f020bcda429e80b058e935b1890f81052a118ee523370e303223ec4630f542bbde67f0cec9221d618a480613b8e3a66eb3b4138963d8d56fccb2f64aab18d722434e698eafeef9790d00e72e1ca02ff47337cca2dc68c651836fc43af24fba3cc514c34da78f9b1e3c6c5f2896e1201dc092833cc635e45937bfc6abc054936b4bd0777cef8aff28f4062879f6ac9f949d5dd22ecab81d4a482ecfb12fda7b5458bcd7f657f9cb1530316edf8738afe075079ec2797065c0ea45582bc1bdd3e473d6d26b7a31e56b80701426ae893aea5a03d3ed10796b20daa90fb54ef7294279e1783a62e3fe350478a26b00fa37ae48b87c0ecb0c37e845490905c7d3eed8e67dff543ff23ccc5aafb9c05156795bdff51431778259f057016fbcf597a3046a2017b436d1c7d48915444db098c1c3fb9c2882c5706a0202b29f999525460771d8c6c0613fe9aa338804b794b3e2d87ad21711637d078925dfba0b07aabe6ce139327dfbe742bfd1ef4988719aedea4a255a6bcd2eb1336b2590cbec0bc7d610b11fe9b8fc20ce9d7613c95a286bbf89aeb05067d8668d40a9bcba6ae";
		content = dESEncrypt.decrypt(content);
		
		System.out.println(content);
    	
	}
	
	
	
	public static void saveCustomerAuthInfo(){
		
		AuthMsgDto  authMsgDto= new AuthMsgDto();
		
		authMsgDto.setIdNo("420684198907221552");
		authMsgDto.setIdType(CustomerIdTypeEnum.CHARTER.getCode());
		authMsgDto.setMobile("18800010103");
		authMsgDto.setRealName("张剑");
		authMsgDto.setUid("8a3ce129-38ef-4d1f-9f21-1233c5a069ef");
		
		System.err.println(JsonEntityTransform.Object2Json(authMsgDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(authMsgDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(authMsgDto).toString(),"UTF-8"));
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
		//initCustomerCenterTest();
		
//		System.out.println(iEncrypt.decrypt("b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acae55db1f2088b070c446dcf3e7d64f8bc5f998a32720495b71642f31cbdd56502cafb0b49d07471e7efec83a74d1cc393a6723437f0ff9c621d1cab682ed95ad1a7d6f35aeb8ce96f56653ec349673a5a2e8a39957029fcddadf2585df852c8dddc019ebd8ea3f518dac38fa665acf510bfee59fb4b0ea1c9548fcb3e0834703fd39ab647497beaed8f10b8e26d366f1332d16f0f516bac0b4b321a96ec6972a1cbae3691563e96206066b7855c8cff7bbb88a33e0df883a7b032bb31f9fec05129df94ae42eb7082999556e64bcd64301ae78ae12ab001d61d9c587d0db863ae6e3dee799cda696466657cc2f253927b4d49a4fe14bdc47757af14852650868212a4fae0787917c850aa5c8d6eb32a632b6de036878ba7e18133cd74341166e65395d929d26c89aefb65f288b1dfeaf2adc5f3aacb8f423372e9c82be4c566db0e07a124784790636aa321169fc3476d88f622cfe3d84a7b2298acec037e9ac78d7c9a839f49137a785f96f5a8c9aa6cfefc66ca3e1a45b9dbf5aebbe1f0e04a0190eae72d4d6de4041c869cd4680255b968babeba7f65f0c7f3c42dc8ccfdfd476d2516b676a2bd1b92f5d9c08d1977c650e56bf698445cd746e8ee030e4f3e57d4b79c17e5ede3850b2ebddbec6b41f0e44b1a02bb2fef1d026df0200ec6cc39e8de9e0ba3020ff94a450ce5ad560a91ba04184962378c7c831796dd26f33ca4707d1e3f7f0e23d7e61fd802c95e5eeafd4f7b6f9fac793e85938a1e1f86552b2a42bf4088785b2125b4205a4d57478eb2c8c222cb3c02f79b01290cd0aae99e8af60c61ec72de13115aa3fbb7c9e879f8185b27207851ee2bcc90c96a14c98ed7bf433a5877e2a478034833494879bad34eff3de34111a0507ac343a3ee2327a2f27233efa50fc2c4c436bcd098fe3a19d3e1dfc9ef60e42d59bc75bcf2edce02788a0c2e883232dab0ccb40c6d868d00bec20f87b22bcabb35a32e5fa75db76ba2fffed85f4b02baf99fba55382604c46875d381841700c3e500c926f7ead8a2ce2f749b7e5b2e261a1e6bcbbba34a53cc38f61374259258d71e556e24293a31b4fe752e5e7f562e72d33cf8ebea2338de310e2b98cdd3886521695ed1de2fa6d985c95a297fae3bfc8197b09e90ccdafa497f27e5ff20af44a7898d38f35253d80b775cc6fb0f66beb43c2a0b17c426c967546cc60b7e63f1b0d184b0c4aeaa7e4976aa87c"));
		
	//	updateCustomerInfo();
	//	updateCustomerInfoTestDES();
		saveCustomerAuthInfo();
//		initCustomerCenterTest();
//		getCertification();
//		saveContactInfo();
//		saveCustomerInfo();
//		checkImgCodeTest();
//		getHouseBaseTest();
	}

}
