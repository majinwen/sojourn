package com.ziroom.minsu.api.customer.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.DESEncrypt;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.customer.dto.AuthMsgDto;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.services.customer.dto.CustomerCollectionDto;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * 
 * <p>收藏测试业务类</p>
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
public class CustomerCollectionControllerTest {

	
	private static 	IEncrypt iEncrypt=EncryptFactory.createEncryption("DES");
	/**
	 * 
	 * 收藏列表测试
	 *
	 * @author yd
	 * @created 2016年8月9日 上午10:20:54
	 *
	 */
	public static void collHouseListTest() {
		CustomerCollectionDto entity = new CustomerCollectionDto();
		entity.setUid("5f4f193b-07fd-a708-85f8-22907004fd6d");
		entity.setLimit(3);
		entity.setPage(1);
		System.err.println(JsonEntityTransform.Object2Json(entity));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(entity));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(entity).toString(),"UTF-8"));
	}
	
	/**
	 * 
	 * 校验房源是否收藏
	 *
	 * @author yd
	 * @created 2016年8月9日 下午5:16:45
	 *
	 */
	public static void  isCollection(){
		
		HouseDetailDto houseDetailDto = new HouseDetailDto();
		houseDetailDto.setFid("8a90a2d4549ac7990154a3ee6eee0237");
		houseDetailDto.setUid("5f4f193b-07fd-a708-85f8-22907004fd6d");
		houseDetailDto.setRentWay(RentWayEnum.HOUSE.getCode());
		
		System.err.println(JsonEntityTransform.Object2Json(houseDetailDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(houseDetailDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(houseDetailDto).toString(),"UTF-8"));
		
	}
	
	/**
	 * 
	 * 查询认证信息
	 *
	 * @author zl
	 * @created 2016年9月7日
	 *
	 */
	public static void getCustomerAuthInfoTest() {
		Map map = new HashMap();
		map.put("uid", "d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		System.err.println(JsonEntityTransform.Object2Json(map));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(map));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(map).toString(),"UTF-8"));
	}
	
	
	/**
	 * 
	 * 查询认证信息返回结果
	 *
	 * @author zl
	 * @created 2016年9月7日
	 *
	 */
	public static void getCustomerAuthInfoTestDES() {
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acdaa88ad8137fd10f2a0b0ff984328d346dabff4bc050d39a354050a7bd4c3c5456cd5d372840db870675a97da138c54baf15c3c4102901f3b7c0983cdf152494c8160e114b74d7cc18b19d8b792399a7d6c3afd52709f683e1b55b7f9a293076019ba403009c78b0bb971652e37a8522e77a4621703af9444c6c4f670c3e296ee81ca02331818fe4ad31bcc8ed30eccbeeb5c5ed071531345f8b62508653853703e940a82bf426bae0c8b5ccad9dbe7944dc5ec19bdff0659f12aee666379352f1ae4ce60e043211f723b24ef82db6ee802ea9450843b6e787ecac6ebf2671fd89f0cfc25e3819a2379201fa9f0ed6141bbc79c972e1f7f860aad9f281f592775cd6d021e7166ec96f25fde14fb44ed33f94e9a912a1a40a2bb1c2023e751eb7f7eb56e783926d5c4111b5ecfa666424e272d836a26e650f0a9fc83d0a709dfe652c7720c6f770f906bcd2e5f381907f868f63e3f91fa2115585b4768f6bc47c84d59f85cd1ad5e1cbd05e52544139596a08c5cd4004099101ec7e6b0bcdcf34f9264465e819045734f7918b7ecb4c0f43d94a85e32918ee7e4d64c4e921136b9062abca01242cb057f3ba53d5b506ab1207593c3b4c2cd8dfb1f5b84c4078cfac2f728ffbecaa894fb6012bfeac9d753c5c46124282e52674e0a81eed3fa6c7c77ed105bfddbff5173893f95d48c860b21977a8d15ac2005a4042bb9b7a4716bbe29dd848f2aedf1663c12509434201dbe43fb542ce05f238bd1e1d405feaa0f39814f796c5327ddf535f8bb36e6e530a594df90d7f24e3649e30c89598820009c12247afaf50cf1cd1c379bcadf978af56978df2812962a9c83ed6e3e760c3494addef777ba3a3f02f9da353d1b2fb909e2abcf2cbcacfe4abf731f3ecd982d1d1f51bdabc8d3cc2c95f25e322d527d76de1915a75dc45fc8fd83a92d1d154e5e603a58f1a96c7aedb65ecb1e9ae11ca5fe6ed2d49f8dd95cd770d4a294325afa7c9ecdf6e5ac593ab1d77b094240a3e6e3298a4bde97c929f4261cd329141186f8594905fa9a8b00049c87d4de8f481a4fd353e645f4c9bab94f2b130b30be0bc95d0687468d5920c5dcb9a08dc82a9528595fffca8eeae5eb83eed0b1695bd9ec82c37bffe5b531dc87db777226a2353273b699eac505961b9e7c7b5c0dbcb77db04921c086fd71e3cb2de8c3ca1938e8867d76e386af3aca6b90150a33ca7873c7abfbe4364ad7cfa3d894b19aa5e48f9b0225356e036a32ad7e6b4ce2138d08db12ff151b5d2841cc608380af64a366d73514b7b137d095e25b383562b7ed15d4c21ac8a7e9d90f2e5d2513a38c9dad59af42f1a8fd0db8898ae41ff94225ce2142fbd7883d88f5cda15c23aca1287139ea1cdf67479d9c3537413f35042eafa8a1f8b7af3365944a1807e4fe1c86934f80b04aad1b05bb3384a1c86d8c8a0e0af874b881612b064e931876c1d389d1fc63e42fe9173ffee1a05a76667e2ef9d6f598f6086e811e8cbe178cb21d7b91afa212dcdab96084ff87badf08cd6cfc49da8e11c63d872373d530bb2656d206bf7f194c14a64ae16a12dc1cfd5c888039fffdd65fea07d72373c25ea4358313c4df72d5c276b03ced295d767e85c0d54c6b5eb93d74890244781cc4cf9dc978381bb44af8b3dc2ff3e424e93309b97d775d4f3fd4e2b53497baf214b094bd1ec3cca8e7a5bc02c0c63f32964e39f8df3be22c57942bc9e38dbff7512182b91fa1228a629ae969503cd025c616826768d64da3047308013c388d8284c57f48628cb4bbeb813ece74a6280f1eac7a58688e8c5a3750b67ef4cd10cc1477b52edd4e2f1c05f4f8716e1b11660db6cec6eff354a436c123eada64e543b3bdc55b6ba0e16f462102916d16d47d45108f69045b1ffe2568e150a11dbc20605c1cc96c89d4a8685995147b9b1c2f65e34e0ebf5539c1391db7680f8f1266564cc6c6a062b165dac0832778b95c051b012b0f46cb06772417849218af2a6c13404291c6759c0292a1a9ba8ba86666f000b09570220cb2d2e13a1ab5eaa29734553a25ac969284053df14318efe5af32b9fdf4bdee42250ba8112980ea5495655b2bfda4e2b683480ef73845a86191853aac7fcb8b7fe907fef3d9dd659df4ef4a011afcdae8afcadeb1ee73747d97616b3b7f21cfd63445e070c59754a2ad41443";
		content = dESEncrypt.decrypt(content);		
		System.out.println(content);
	}
	
	
	/**
	 * 
	 * 保存认证信息
	 *
	 * @author zl
	 * @created 2016年9月8日
	 *
	 */
	public static void saveCustomerAuthInfoTest() {
		
		AuthMsgDto dto = new AuthMsgDto();
		dto.setUid("d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		dto.setLoginToken("kfdklklsdfkkf8695895948jffj");
		dto.setRealName("zl");
		dto.setIdType(1);
		dto.setIdNo("622824198904140611");
		
		CustomerPicMsgEntity voucherFrontPic = new CustomerPicMsgEntity();
		voucherFrontPic.setCreateDate(new Date());
		voucherFrontPic.setFid("8a90a2d3567ec93e01568cf27362000e");
		voucherFrontPic.setId(697);
		voucherFrontPic.setIsDel(0);
		voucherFrontPic.setLastModifyDate(new Date());
		voucherFrontPic.setPicBaseUrl("group1/M00/00/CB/ChAiMFexZPWAT5GvAAzodQCbVVc803");
		voucherFrontPic.setPicName("Desert.jpg");
		voucherFrontPic.setPicServerUuid("0ffefd31-9c48-4ec8-99a5-88697d3cbe87");
		voucherFrontPic.setPicSuffix(".jpg");
		voucherFrontPic.setPicType(1);
		voucherFrontPic.setUid("d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		
		CustomerPicMsgEntity voucherBackPic = new CustomerPicMsgEntity();
		voucherBackPic.setCreateDate(new Date());
		voucherBackPic.setFid("8a90a2d3567ec93e01568cf27362000f");
		voucherBackPic.setId(698);
		voucherBackPic.setIsDel(0);
		voucherBackPic.setLastModifyDate(new Date());
		voucherBackPic.setPicBaseUrl("group1/M00/00/CB/ChAiMFexZPiAJwApAAvWFkcZHjA507");
		voucherBackPic.setPicName("Jellyfish.jpg");
		voucherBackPic.setPicServerUuid("efa67df2-f4b3-4b1a-a9b7-50c3bb4c6658");
		voucherBackPic.setPicSuffix(".jpg");
		voucherBackPic.setPicType(2);
		voucherBackPic.setUid("d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		
		CustomerPicMsgEntity voucherHandPic = new CustomerPicMsgEntity();
		voucherHandPic.setCreateDate(new Date());
		voucherHandPic.setFid("8a90a2d3567ec93e01568cf2fd470011");
		voucherHandPic.setId(699);
		voucherHandPic.setIsDel(0);
		voucherHandPic.setLastModifyDate(new Date());
		voucherHandPic.setPicBaseUrl("group1/M00/00/CB/ChAiMFexZR2ABpHKAAgv7g_sx3o062");
		voucherHandPic.setPicName("8mE6.jpg");
		voucherHandPic.setPicServerUuid("7b70536a-d26b-4bf3-831f-3234e2b7464e");
		voucherHandPic.setPicSuffix(".jpg");
		voucherHandPic.setPicType(3);
		voucherHandPic.setUid("d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		dto.setVoucherFrontPic(voucherFrontPic);
		dto.setVoucherBackPic(voucherBackPic);
		dto.setVoucherHandPic(voucherHandPic);
		
		System.err.println(JsonEntityTransform.Object2Json(dto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(dto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(dto).toString(),"UTF-8"));
	}
	
	
	/**
	 * 
	 * 保存认证信息返回结果
	 *
	 * @author zl
	 * @created 2016年9月8日
	 *
	 */
	public static void saveCustomerAuthInfoTestDES() {
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9ace8eb79909e8ca96e";
		content = dESEncrypt.decrypt(content);		
		System.out.println(content);
	}
	
	/**
	 * 
	 * 保存咨询建议
	 *
	 * @author zl
	 * @created 2016年9月8日
	 *
	 */
	public static void saveAdviseTest() {
		
		Map<String,String> map = new HashMap<>();
		map.put("uid", "d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		map.put("complain", "设计的太丑了");
		
		System.err.println(JsonEntityTransform.Object2Json(map));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(map));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(map).toString(),"UTF-8"));
	}
	
	
	/**
	 * 
	 * 保存咨询建议返回结果
	 *
	 * @author zl
	 * @created 2016年9月8日
	 *
	 */
	public static void saveAdviseTestDES() {
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9ace8eb79909e8ca96e";
		content = dESEncrypt.decrypt(content);		
		System.out.println(content);
	}
	
	public static void main(String[] args) {
		//collHouseListTest();
//		isCollection();
//		getCustomerAuthInfoTest();
//		getCustomerAuthInfoTestDES();	
//		saveCustomerAuthInfoTest();
//		saveCustomerAuthInfoTestDES();
		saveAdviseTest();
		saveAdviseTestDES();
		
		
	}

}
