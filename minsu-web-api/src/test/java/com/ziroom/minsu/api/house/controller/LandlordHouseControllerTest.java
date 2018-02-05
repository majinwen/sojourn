/**
 * @FileName: LandlordHouseControllerTest.java
 * @Package com.ziroom.minsu.api.house.controller
 * 
 * @author yd
 * @created 2016年8月13日 上午10:06:08
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.house.controller;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.dto.BaseParamDto;
import com.ziroom.minsu.api.common.encrypt.DESEncrypt;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.house.dto.CalendarParamDto;
import com.ziroom.minsu.api.house.dto.PicParamDto;
import com.ziroom.minsu.api.house.dto.UpDownHouseDto;
import com.ziroom.minsu.services.house.dto.HouseBaseListDto;
import com.ziroom.minsu.services.house.dto.HouseLockDto;
import com.ziroom.minsu.services.house.dto.HousePicDto;
import com.ziroom.minsu.services.house.dto.SpecialPriceDto;
import com.ziroom.minsu.services.house.entity.HouseBaseDetailVo;
import com.ziroom.minsu.services.house.issue.dto.HousePriceDto;
import com.ziroom.minsu.services.house.issue.dto.HouseTypeLocationDto;
import com.ziroom.minsu.services.house.issue.vo.HouseBaseVo;
import com.ziroom.minsu.services.house.issue.vo.HouseRoomVo;
import com.ziroom.minsu.services.message.dto.GroupMemberPageInfoDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房源测试类</p>
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
public class LandlordHouseControllerTest {
	
	
	private static 	IEncrypt iEncrypt=EncryptFactory.createEncryption("DES");
	

	public static void communityNameListTest() {
		
		BaseParamDto baseParamDto  = new BaseParamDto();
		baseParamDto.setLoginToken("ff90d0e51e7e40726c98663b4009d69e");
		baseParamDto.setPage(1);
		baseParamDto.setLimit(20);
		baseParamDto.setUid("5f4f193b-07fd-a708-85f8-22907004fd6d");
		
		System.err.println(JsonEntityTransform.Object2Json(baseParamDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(baseParamDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(baseParamDto).toString(),"UTF-8"));
	}
	
	
	/**
	 * 
	 * TODO
	 *
	 * @author yd
	 * @created 2016年8月13日 上午10:17:00
	 *
	 */
	public static void aaa(){
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acaa2c4a2cb1e797f12cdab808b71912ce3de3af0da7afdaa1d7bc82e0fef3908985908e5a386ae816f37e67e343309b87bf7af66f177fff88181c82fe010872a7ae3849e0ddf3bdde9eedfefa57d65ea06346cd646621e4a6abbaf171aec62a316e03244ba1b0101aaf8e0a0e8b071fe7d653e55e35341a25896c8a3a09e0dd43ab808082e93619e3f5f8401ea56c23062623c3e0f3b95801babe7ef046eb359a28609c9ba8e1d22f99092377decc8ae44e507ac2684d7b5fc7fccd5985261421dfc238203773e0ad79cd46310394f7d5cd4642387324c4bb31a88aed67446d57c2111d0df7b634feeab2928f9623b9470c07255326385232085ea22f5493f61c40f5beb56b961cc610dec649fec7e849b79a18152c525b5918fbb719cc9fa1f43b17982f6b315b4376a775ca6d5dd963a846d14f3f8d09eb8d18b01cc9df3231f2bfaa73fd64b23b0d253570667f2f1848ab40fd10c4c3b107885db52febac3b21d6061ab3d10d500ce19fbfb2bc5ab9f9ea9187700951197459ebe09d1cf858d04c2a87b82eee133d25f355d8ce81ad705229e97dec59fc52236c52e6dc1a5abd172943511200de81bcc4a01f18bdb6171c56ea8b49b6b72f2f4894265218d3b30be7fdd9fa35d07c3ae706710c2dfea65fdab409c46c81d74b577530e31b6416c37d53eec8bb1aeb441c8eaa522d11a939c0070e6ad93db9db91beeffd113fe7a67710252b45a64c83a977058cad152e39e1ba997a93031d2015974756a9e82a386e4ea303afbb9f75aeba7e80030e07122b198e958e3e7cc069455927f6a4ca66821a668ecdb23b9bd19aff05f5e416a91d1b76a11d13016f6b81bf4d3a50526cea1acd7a73724e072d716f96b82090b3be3cdfe5f87bc0ece4484b46d961a0aaf082db3e648d2b3d1acfda5966cf3e97ecb489f6cdb3e3484652be612d348db0146bee1fbc97d7b4b586ebe9172a85af2cdd10cbbcfed7f41abdd3c4976dd173ab52154b1bed945465c3ef74cdf97c5f80ff661f897547074589a49f2673a6485ee591766e60853e9a44f9d85174e815072d5e6faa86bed4e0c2d818fc8c379ed2cb5b5b59ae57d0924ec994b98a0044035076ab3d7a64fd474542653361dbb2ba3aba4fcc358fc930f9cc5874158161fc4c8b93272765883f930fa58c2f6d7d46c3a011e7d830f897aab63c76ab64559c30a3850af3af8ed96acd821bfff0cf4f66270320848ddc6e7b4d6ab7b2e5ad054582815831ff4f1dc8a0dc8b20a3ea7165164ed1fe17a9da7e4d32cb52f664019ace8f728f3027fdb05247cb0d2ad410a259a3dd1ed06de14f4681da2c574ff0dedd27a816400cf8d760fdf864bfde7c38fb470cb2a381e4ce9bce0cfeda3f3f28b655143a832e598938a00d592616d522db3699c481c61e6dcaacc5a63bb84e30032a1e82d1719655a2481081";
		content = dESEncrypt.decrypt(content);
		
		System.out.println(content);
	}
	
	public static void  houseRoomList() {
		HouseBaseListDto baseParamDto = new HouseBaseListDto();
		baseParamDto.setLandlordUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
		baseParamDto.setLimit(10);
		baseParamDto.setPage(1);
		System.err.println(JsonEntityTransform.Object2Json(baseParamDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(baseParamDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(baseParamDto).toString(),"UTF-8"));

		
	}
	
	public static void houseRoomListDes() {
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9ac780fe9331685a349135628707201ffaf4d8f9582eafc7d53393daf9987b73dcdbaf803d9fdf7780a6da80d8fb114ad405dc8c609a6760fa07f5d737197b745e7347e921b0d34a02f2f27ab7b1b62ce5058fa72b715a3704a6d4f8ba02cdb6080f555bb9067d5c446f2e2421f343bfaae355b7aa1090e4e0c8455f699551d5d69de68e6a4f0169b4ac8b9120391ba443da8ee1ae6a430baac0f0de33fe5c62d006bc75d19004dd744f19ed849ba41bbaa66a628648161474bbd0e7023bf2eec30291347e671e4b03a7c637f3b79d8561af1549e6c7f67950ddc57e3bf35732878082e9ef285c97965980078c2c93aef4f3b8ff548d24cbb7f44e9a3820c39b6d13203d978823f8153c99ae74f72cd175e08adf00ce69c59050c7e8617ee2e007f851250d8937985bf9ad583f7bc59b63191753629d69cddf0c0534bba8dad4ac83e14382a155f1ef32a1a0d64563be4478133386406ed29fd6d9e2100d939d31fb125dc583bda30c1ae3d93268eea42a9b7883ea9178b9532995bc7c2568b3da877d54b2c7d43bd957a47fb1d43a3401b22b91a62c36d6b1dbb65e5c11e0ffa468f9836a9912dddadbd34f037a9cf3fd8b666ad5562f891929a8a5726c303bf1254f1d70d7d9a6f223d963592531b03ad27e44923b54481396b84c6f9a2a8052689dcaae1f14afcd5e7281903cc97089270d1d519feb0f3d42084b61986a47a3e32dd7a3f6bdabe7e3e05a62fe606d0618ddc6f3512659d11713d774c6799ada035624bcb37497667eeb355be8c5029337459108a14e2c5defde2b50d3cecea34c1a5032fb4a1e8785cf519059de2d4aabe2d4ecc0859e6fd0b020f99c717e9c1a211c147cbdd41ac085753289f255969f70445c7ae45ee24d5b93606226b02ec260030d13d4a34969705a40977e449626a84a0ca8e4defcebbcb54f734d9f827cb0519dd789c6a41b666bccc0d32424e4932dba5fd4c619570efe5247d7c9bafb1a8f3c33bf984c8549f81a3511760e6b986f0dcae08ec0196dec4719dff19544860120e1a47b0e22aa225160336908bbdb1fa1a99e8830d55287e161a30746fc849f3649de71e94bf00740771803ceda8f59ddfb9990cd346949ab2a77ed0eb43d3308e28812e6407676448f11d62a617d9c416095a0dd03084aa8373cf97932093a169e205c189b4f37de82a78927201146ffb8c5b8faa751846c843ca9c0c27979f7ef82ce03a71b9d489cc83d0bf9a4142ba4e2887f4bec7a459a9c275a8324e31da20952f98bb343265723acb17368ced40cdcabd6c81409f303c1337345c18d01b043ab9cdd41e51e420f070351248f4f29f3fb7dcc5a16dad719a02d6db2d1be6f49a7d1795316b5f79a9c94646e13642b7cc95bd5e72f319f626dec2e6a741518f13a76b84d049e541379f483c9530aba4ffed0bad5b9708beaf4727b2ac7a00866e96d0ffad731e8bf1d3db3ba9addacb922993ae70210283a2fb884b4c05698dc717a1bed20c9c54fcd110a30a8e62bec88b09fc9783a493dbaea3964fd4a24906206d6d9e7bf490171577c081e71ef05010bb56875886bf930b6d591dd3f57dee190db8628d6fcf09709f97e5ee70faacd73ffdc5a29cc8de96e39b76ed427dd46ae91fdf9b2b244c1fec";
		content = dESEncrypt.decrypt(content);
		
		System.out.println(content);
	}
	
	public static void  setSpecialPrice() {
		SpecialPriceDto baseParamDto = new SpecialPriceDto();
		baseParamDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
//		baseParamDto.setHouseRoomFid("8a9e9cd95529f30f015529f30fd20000");
		baseParamDto.setRentWay(0);
		List<String> setTime=new ArrayList<String>();
		setTime.add("2016-08-23");
		baseParamDto.setSetTime(setTime);
		baseParamDto.setSpecialPrice(200);
		 
		System.err.println(JsonEntityTransform.Object2Json(baseParamDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(baseParamDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(baseParamDto).toString(),"UTF-8"));

		
	}
	
	public static void setSpecialPriceDes() {
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acc5d1b9961cee8f65a1303689428aefca2c227dd02b4c783d977d4ec7d1600a11ade6ade0ec51872b9d40fa08973959766d33f6fe0c915325eb17bc8f1e02c0f28e543af70794f2ce00575d52fd1b1e274dc5e0cd19fa6f92b898d35f1dfdd519";
		content = dESEncrypt.decrypt(content);
		
		System.out.println(content);
	}
	public static void  lockHouse() {
		HouseLockDto baseParamDto = new HouseLockDto();
		baseParamDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
//		baseParamDto.setHouseRoomFid("8a9e9cd95529f30f015529f30fd20000");
		baseParamDto.setRentWay(0); 
		List<String> setTime=new ArrayList<String>();
		setTime.add("2016-08-23");
		setTime.add("2016-08-24");
		baseParamDto.setLockDateList(setTime);
		System.err.println(JsonEntityTransform.Object2Json(baseParamDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(baseParamDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(baseParamDto).toString(),"UTF-8"));

		
	}
	
	public static void lockHouseDes() {
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9ac375bcd57a16159e1";
		content = dESEncrypt.decrypt(content);
		
		System.out.println(content);
	}
	
	public static void  leaseCalendar() {
		CalendarParamDto baseParamDto = new CalendarParamDto();
		baseParamDto.setLoginToken("ff90d0e51e7e40726c98663b4009d69e");
		baseParamDto.setPage(1);
		baseParamDto.setLimit(2);
		baseParamDto.setUid("9afeae98-56ff-0c35-77cf-8624b2e1efae");
		baseParamDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		baseParamDto.setRentWay(0);
		baseParamDto.setStartDate("2016-08-13");
		baseParamDto.setEndDate("2016-08-15");
		 
		System.err.println(JsonEntityTransform.Object2Json(baseParamDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(baseParamDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(baseParamDto).toString(),"UTF-8"));

		
	}
	
	public static void leaseCalendarDes() {
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acd417b16e80245a15ef16c725e28e3dbc04b729e63aaedf34a2ff22e92b9dd6f1fb32de45cbe958ff339b3aac7916b9de2d2d8bee9defffff612c70a5b6ca58120266d591013ed05c8abbb05029e2f94c94d07b45ea1966770018d7d324b4fae00c36a11ca0354c3b65f1f7344a1695ddd091bf9502ff67643baf5cdfe544c95eb91e6260c05afd93db11b490b682a99796c26cfa012e9b40bc48df3ade43f4ead5f195a1f57d4adbde4f9570942f5f57965203592a4837e1acb44bbcd02058ace4802c3a44439b0dcef3d19c261814d9371c5d667d063293070ca2b7e8f158e2";
		String calendarNew="b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acd417b16e80245a15ef16c725e28e3dbc04b729e63aaedf34a2ff22e92b9dd6f1fb32de45cbe958ff339b3aac7916b9de2d2d8bee9defffff612c70a5b6ca58120266d591013ed05c8abbb05029e2f94c94d07b45ea1966770018d7d324b4fae00c36a11ca0354c3b65f1f7344a1695ddd091bf9502ff67643baf5cdfe544c95eb91e6260c05afd93db11b490b682a99796c26cfa012e9b40bc48df3ade43f4ead5f195a1f57d4adbde4f9570942f5f57965203592a4837e1acb44bbcd02058ace4802c3a44439b0dcef3d19c261814d9371c5d667d063293070ca2b7e8f158e2";
		content = dESEncrypt.decrypt(content);		
		System.out.println(content);
		
		calendarNew = dESEncrypt.decrypt(calendarNew);
		
		System.out.println(calendarNew);
	}
	
	public static void  saveHousePic() {
		PicParamDto baseParamDto = new PicParamDto();
		baseParamDto.setLoginToken("ff90d0e51e7e40726c98663b4009d69e");
		baseParamDto.setPage(1);
		baseParamDto.setLimit(2);
		baseParamDto.setUid("9afeae98-56ff-0c35-77cf-8624b2e1efae");
		baseParamDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
//		baseParamDto.setHouseRoomFid(houseRoomFid);
		baseParamDto.setPicType(0);
		 
		System.err.println(JsonEntityTransform.Object2Json(baseParamDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(baseParamDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(baseParamDto).toString(),"UTF-8"));

		
	}
	
	public static void saveHousePicDes() {
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5513afebbf051b09b8636262133d44330a1dde3a24bb9fb813f1a7a1e62c81f602b8efc60cf672932950c9d20e7d6c704e10faf9fa3af163403c2818779dc2ef10f6aba8dab8db37a";
		content = dESEncrypt.decrypt(content);		
		System.out.println(content);
		
		
	}
	
	
	public static void  upDownHouse() {
		UpDownHouseDto baseParamDto = new UpDownHouseDto();
		baseParamDto.setLoginToken("ff90d0e51e7e40726c98663b4009d69e");
		baseParamDto.setPage(1);
		baseParamDto.setLimit(2);
		baseParamDto.setUid("9afeae98-56ff-0c35-77cf-8624b2e1efae");
		baseParamDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		baseParamDto.setRentWay(0);
		 
		System.err.println(JsonEntityTransform.Object2Json(baseParamDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(baseParamDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(baseParamDto).toString(),"UTF-8"));

		
	}
	
	public static void upDownHouseDes() {
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9aca29b78cd2ce7d25b0130b781d100764e";
		content = dESEncrypt.decrypt(content);		
		System.out.println(content);
		
	}
	
	public static void  housePicMsgList() {
		HousePicDto baseParamDto = new HousePicDto();
		baseParamDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		baseParamDto.setHouseRoomFid("8a9e9cd95529f30f015529f30fd20000");
//		baseParamDto.setHousePicFid(housePicFid);
		baseParamDto.setPicType(1);
//		baseParamDto.setIsDefault(isDefault);
//		baseParamDto.setPicList(picList);
		
		 
		System.err.println(JsonEntityTransform.Object2Json(baseParamDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(baseParamDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(baseParamDto).toString(),"UTF-8"));

		
	}
	
	public static void housePicMsgListDes() {
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9ac502be04c135ff3359ed000d14ca1a311adc7aa016c805424a06de1457575ba194f83e35845d2171c197b3d3d9da8e87827c556fda5a25689465359821558baa63c836663307d339da595ca9e228980b6b7c106c6f09d4bcad32fc51d349251cbecd20ecf4957a786c97f0797f2f5d6e15154e27e9fed41a4e767ebd7c36ad8737772b07a07c7066252d7dea42c41bcc25c1b229143d230a5b68bb117bfa08586a3989fadd470b5084b4d4005ae225559330f1f16f91993a21e4a2a0f66b1fbcc48328d585725c18e62b5e551266909e50eb14ca09de10bb26ee78b44cd4bd7ecd955ed84b487878759b74ae630a4a2b9148653cf3c42398033accef6653d5e8a3f824265e42ed2fd9e6b8370fb63f6710e95680a8e48b67fdb72f803e93d94e189994550c17217d0972b09042c21d0b32cbb19f870d1b1eeb6dd8a8b3f0f46135263b1b5c183a18e1acdf5fb6977d3c080056733568bf1d6103d1927ec4b1c45bab95f368643a415749d638e55fa27663e0732675b03d2c08feafdf24d9916ebd00101384d07f550c9dee2c526013cd1f8639829e77529c5a982b6cc98a169f2fe696f4202a3e5131588fb7599b664925c4dd8b5eb9427e140e2c92b1a3b5ac9688baad4fdbc70e72609cfef363ff84bc01d39a96111184792d4a34b099a0e3bfb75a6718f05a77316e1ea08dc8a74b9c022b4dc6fb1585c3a0e365508c4149e7eba351afd1825025a5342a91ca71de4db57e3661a9219a7e19045ccf9a36abf7c990bb1f8c6aba1b9f4c10adcb84df783b08116ddcdd6d77895fd78ff708fa324dd087a0305606580484f74be58aafae8b942d878411897c7f8874cbe741c31ef93c0701d2e04d2dc3d9f41691a708f77c00ac3c5de2db5a602705a007b482498c1541bb6dedb8f475777bedd83b70c213c0e87cff35a3fa05c02468c81280d4dedc83154a44fe3b36bf337e10d97332876f7ef55124c18531b7ab5af20f680f201436f5710f382d49ecfc6446f338da2b5ecef4797dfd063479b15f306e95d82e33081ffc052938f0c8be32b4cb4390de356996f0175ddaa0e9a0d90ec9af52dc8c105cee890719ccf25b3bc69b31324db6fcd86a0994b4dfe8107ea5acab4c6fefa9edb74f3e4f2e9c3dc834a46a1cb5fbcd694f288e10774e9177bd1af9ba08549d85713dcbb14d2fa179a18c799a27219fe5ea762045c90a5dbc1f4806e925974525a33a4e94f1222511cfd7a1fa3f66f655dbdcb837a232c2e07b1a5c6abfc256f8bcb16a075c40b6768180e01f5b27c8a5e3f687e159faf9abe27ccc33b41c2bba253c2c114e43e38e00ae9636ee2f3cf5e6a91d2198cc5a61a2088f1a66a46415a76eb646d1d280902176ae2fcf07983be0cf72049a6ebc9fe4ea645755c01bae4f1c8764b3d0f4daa2a96941df2cff943f70a5c6bd4d72a94a058ba531d8d116a99ce4cfdcb64a9566c85bb0d2ac918ad109b29bec80c55016c8949df04b9af7c30178c1eead70a23ed70d3d6bff363259ded170ae0d297ac4d94bc2f14f9a0c0dd61f9c71092fb730ac4fa160244a05d1b4331dec64af38cd851a26b951c0bfe97a9f4aff94f261a0fcf5f298eea712e68700467e9426e09adfb4aa86febe96b65a202c6e11bdd01cf419fd8715bd9f4acbe46c8d49cd96e4800807b02235a53cb0a1d78b249b9132af06854f712ff29c3e436410acc2141889021fc516f70a891978e69dc0657ccca44f8262d24c2f5fc49fa7d975a81e23ad33fa5bda6176b5d6bbc47f917681fc06762062bca107efd0d605abd6515c434e24b654e459c86d7613a3711a332ede89e86e1eab4f30c2c63adaa890c03b43456e4c5fa07863ba7d459b9831dd9d02f73041cacff92af3667dd1ec0d72f9c3499a65dae5c7adb2cdea9b58f3a25378668bec7c1ab364059db63e1bb1a5cf5470d675aef17aeca92dfd14fea659f39296b472f22e019168874122d04399cfcdb8c4bee3516149aa2a76f6545859d0b0c6435";
		content = dESEncrypt.decrypt(content);		
		System.out.println(content);
		
	}
	
	public static void  deleteHousePic() {
		HousePicDto baseParamDto = new HousePicDto();
		baseParamDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		baseParamDto.setHouseRoomFid("8a9e9cd95529f30f015529f30fd20000");
		baseParamDto.setHousePicFid("8a9e9a9a54e7367d0154e754d46a0007");
		baseParamDto.setPicType(1);
		//baseParamDto.setIsDefault(isDefault);
//		List<HousePicMsgEntity> picList=new ArrayList<HousePicMsgEntity>();
//		HousePicMsgEntity picMsgEntity = new HousePicMsgEntity();
//		picMsgEntity.setFid("8a9e9a9a54e7367d0154e754d46a0007");
//		picList.add(picMsgEntity);
//		baseParamDto.setPicList(picList);
		
		List<HousePicDto> list =new ArrayList<HousePicDto>();
		list.add(baseParamDto);
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("picList", JsonEntityTransform.Object2Json(list));
		
//		String jsonString = JsonEntityTransform.Object2Json(map);
//		System.out.println("json="+jsonString);
//		
//		Map<String,String> paramMap=(Map<String, String>) JsonEntityTransform.json2Map(jsonString);
//		System.out.println(paramMap.get("picList"));
//		List<HousePicDto> list2=JsonEntityTransform.json2ObjectList(paramMap.get("picList"), HousePicDto.class);		
		System.err.println(JsonEntityTransform.Object2Json(map));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(map));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(map).toString(),"UTF-8"));

		
	}
	
	public static void deleteHousePicDes() {
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9ac375bcd57a16159e1";
		content = dESEncrypt.decrypt(content);		
		System.out.println(content);
		
	}
	
	public static void  setDefaultPic() {
		HousePicDto baseParamDto = new HousePicDto();
		baseParamDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		baseParamDto.setHouseRoomFid("8a9e9cd95529f30f015529f30fd20000");
		baseParamDto.setHousePicFid("8a9e9ab3544b5a0101544b5a018b0000");
		baseParamDto.setPicType(1);
		//baseParamDto.setIsDefault(isDefault);
//		List<HousePicMsgEntity> picList=new ArrayList<HousePicMsgEntity>();
//		HousePicMsgEntity picMsgEntity = new HousePicMsgEntity();
//		picMsgEntity.setFid("8a9e9a9a54e7367d0154e754d46a0007");
//		picList.add(picMsgEntity);
//		baseParamDto.setPicList(picList);
		
//		List<HousePicDto> list =new ArrayList<HousePicDto>();
//		list.add(baseParamDto);
		
//		Map<String,String> map = new HashMap<String, String>();
//		map.put("picList", JsonEntityTransform.Object2Json(list));
		
//		String jsonString = JsonEntityTransform.Object2Json(map);
//		System.out.println("json="+jsonString);
//		
//		Map<String,String> paramMap=(Map<String, String>) JsonEntityTransform.json2Map(jsonString);
//		System.out.println(paramMap.get("picList"));
//		List<HousePicDto> list2=JsonEntityTransform.json2ObjectList(paramMap.get("picList"), HousePicDto.class);		
		System.err.println(JsonEntityTransform.Object2Json(baseParamDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(baseParamDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(baseParamDto).toString(),"UTF-8"));

		
	}
	
	public static void setDefaultPicDes() {
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acc5d1b9961cee8f658f407b202b40bffc5680b6f0dd779822fad997af5c731f4d87c33804e89562e85f392c265c2d3ce0ff11e5bf7e435c35241bb70fbb3ed15e21aeab587faeef2f3f616d94689ac670f8f69cc816796d55983737283d08509e01f88a02623062f1f3333828b58330c7ec6766ffdcfc22672a00c5455d4c356c566a8e94079cbadd731fa1ae0ae26470d796507cec7ac6f66c596a3d05fdb9aedc85570aed1081993b3bae9bb665690ec021915619dc03051bb7fef895dde08a6821624dc00c9d04f2988e18874feb7c8aa24ccda032a6795740d57212b62b98ebb49089d6982457eae3e752b85d395f0566fe6b80803ee04f66ebed03d6db9c362d4f999fb02164f1d10568f95277af848aa52d5ee1ee8e199310fa1bbe2348f5ace4c8d120935dd4d18a8c42248ce72ca74bbdffad28924f60ffae02f9b172f5aaae4cc2a9cb99fd3da313e0a6ab3ffeb2da991439f3de0fd8e194b794d83db6f44804ef80848598f7fac62333fd876813cf53603b4a5d0224437530aa50fced353e129f514509151e5f33c0cab88b9749ad7b9a31a28338442004e1361abf589f807b5d856395e4a0ad5c802c6b61a8f7f8c322984f2e0a0695f5e3d8fbd1ae4d0bada957c04513da6039de53fd61852e3266288cdc025f86b3b3769b4e2134b8a7403ec5cce9b5e21cb0bd4182756a38d43cfb305a0746baee81ac338e6d2e5076ae93adc686c5f93a77f481f821b5f7ef54337c056ddd29f54f710ed5c6";
		content = dESEncrypt.decrypt(content);		
		System.out.println(content);
		
	}
	
	public static void testHouseIssue(){
		HouseBaseVo vo=new HouseBaseVo();
		vo.setHouseBaseFid("8a90a2d45d358f42015d35987ccd0031");
		vo.setRentWay(0);
		System.err.println(JsonEntityTransform.Object2Json(vo));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(vo));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(vo).toString(),"UTF-8"));
	}
	
	public static void testSaveTypeLocation(){
		HouseTypeLocationDto dto=new HouseTypeLocationDto();
		dto.setHouseBaseFid("8a9e98a15cc577c6015cc577c7290001");
		dto.setLandlordUid("679ba3c7-b9ee-1e21-dced-e830351a4143");
		dto.setRentWay(0);
		dto.setHouseType(2);
		dto.setRegionCode("100000,110000,110100,110101");
		dto.setRegionName("中国,北京,北京市,东城区");
		dto.setHouseStreet("酒仙桥");
		dto.setCommunityName("将府家园");
		dto.setHouseNumber("2单元2203");
		dto.setLatitude(39.969398);
		dto.setLongitude(116.543488);
		dto.setHouseSource(2);
		System.err.println(JsonEntityTransform.Object2Json(dto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(dto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(dto).toString(),"UTF-8"));
	}
	
	public static void testSaveHouseDesc(){
		HouseBaseDetailVo vo=new HouseBaseDetailVo();
		vo.setFid("8a9e98a15cc577c6015cc577c7290001");
		vo.setRentWay(0);
		vo.setHouseName("测试原生发布房源2-1修改");
		vo.setHouseDesc("测试原生发布房源2-1修改房源描述测试原生发布房源2-1房源描述测试原生发布房源2-1房源描述测试原生发布房源2-1房源描述测试原生发布房源2-1房源描述");
		vo.setHouseAroundDesc("测试原生发布房源2-1修改房源周边情况测试原生发布房源2-1房源周边情况测试原生发布房源2-1房源周边情况测试原生发布房源2-1房源周边情况测试原生发布房源2-1房源周边情况");
		System.err.println(JsonEntityTransform.Object2Json(vo));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(vo));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(vo).toString(),"UTF-8"));
	}
	
	public static  void initHousePriceTest(){
		Map<String, String> vo=new HashMap<>();
		vo.put("fid", "8a9e989e5cc590b7015cc596f7ee0004");
		System.err.println(JsonEntityTransform.Object2Json(vo));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(vo));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(vo).toString(),"UTF-8"));
		
		
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acc5d1b9961cee8f654dea0cf2090d3d3bd6bafd5c9bd9b59ef1b04adf9fab7d9218f81497749f88685a83780c575a7322f127c9168748aba06cc29c7faebab4a6608df490d5172b4713fe2a8fdd0715bf6ce50a159c8140880cbcdb3fd693a3ce0e6796dd6a62b68179eb78fc4c0b3c511e5170fbd207641daa5e93ff7b3901ff75c84c9a2967ed5575ea1102c11795d5fefc3caa11b2bd6eb0606d68589696680c3e7568dde996d3137a12a0abced833ca2df601694f2c2fa855028b7451bf4643406db28c88d80dc7e16084851fd8edd2204687743a7c3a845d7ec628f71daf6a5bd1740f9aa8dfe21625b02537e09b1f146f3877ae5a91617d3f406b3a4a542241e94a0e48a7495be0b68f31e014b4e003b02069d506d45187deb48df5b72c4c5a527d044f52bc720b13450e23d9ce370837623a781d0d8aa3286c8875f494634020ea38d6b4681151bcc7264e01c8db0323ab2257c795076b9c851ba4a12a8bb6dc470c0fb1eab03d64c43c786f03eef45da2f67eaad2d2e2e616fb7c52a1ce3e0b8ef1ad4c21de5b643cc9ce5cc74de20e19c3965e265214683ff295637cd5ff997a31264aad3681fa0b603c0823dcf421360353a320a46b75a3d15ab83e34ed84ee3977424f6152730afd873a8bfb6a255263942e414f7f0b89f0be48d4b78b18b00649d1b646dd546f029a7793e14747de3f5bf571face29ea416571d47eae7e957ecacdec654b88a60abbcc8a1d370136ae10a04535b106d9ee4ce09c1b02d4d8bb370e7979f673c168898b5cff654828115af513aecb4505437cb39fa2df93fd47a676b060d6e5113db5a61093251a35c4e1ba2a30e24ccc145ce6480fe540ede92160bc7e7b05bb724abcd507ace6f29f98951cb72f3902b0e3949b014cb4b0e1e8b459f9661037fb9f7149e0df6a67a4c7bab252e48806ff9851432774be0bb57f76622b0904ad6dcee9f46c1fc0c63f9308db6a7428837b2cc65a30d264e83a72a726e32cbf7eb5d867f6c93bd02704dbe192986b8b2fa06667e245ad07c0d4b72588c3a635d1ee19db29213cec40afa364ac530a655bb01256e1dafc2f48b228be713f774ad5812fa66877d7f5120605b6e613374e9eaa86772d3a4726c46766827c56d0f22e10fbed63f799fb59d000e55d228b3f69810f840f679f987bd73aff87811a36a72d4a9b8a209a67b0a81b6ac62a28bff82dee87b4ec667166e2ec1a84a43f8ab7d42393ee2770f78a688c67b142ed6c446c8c60f70e83048b65ae636fe99e0b46ebae55da4db98302f011ce654a27d4e91189189cff3afa7f34f3df343482a81cd5f5ede69e98b38c28fcdb6d3b8448bce578e3733226f00e7c9949604d8d34ce397fe43862257b9b588d59049759719f3ddd90d10094c8b4212763f91f5dd26610cde6ffd726819c3e6e0c26f136819e5c967a7a5f6af25b8712cb085a2a9560842f169ec9d21b031f20f864e158a0dff4fedd931236080b265f6ac18c724e3e5a2ed1717c58822231b9531eb120097446192acb85119e04c16758d8a9d83b1ddf1ce6371bed0b12637c56fee8b8be187319d6b3baf79f8e233ca6cb319b6637d5eb23539da6ee3758599b361b7e54ef67a80ec65f5b70d62a8a9aa00c7ea2be1140da021d4f6a472613c815b03fd75e7b3941800af2ea6d723ea69baa8dcdd637269fe7e81e56075827d489f9659a098c10e742b1f194bc841f5800a15227526ff6035bdea6b137963b484be4e5f4ea9f0aa341eafb02e73cdcf26ef232a7bf08a7e30d9199d352195409fd60d58cbe034833878ab28e2d25348cb7a933960911c06e718e96b0b1cb617d906c053aef03e168968081c5796778e2c8d6be2d06fec76f2e988e376210ecabbd3b31da0932cf62e7d69833eaa9f45967159bb3428635a95d8ac506f00dd93bc849ebe81b84478116707ec9e5951d58c899f8ac3a01dee17ebe4ff1d7d4aa01368faadf47f0b963192feb9573d1a64170ea3120d7270100a06227e7fda19bf09cf758adbc3563ae805078fafe7753acb40e7d66f7c85e9c50b593d7f48cd58719f1bb001497603e57adb7b16bcc27424a2fc90eb2ad323e985f2a9aef4ee2cdd2cb36093408b5136a8717dbdbd5bc2dd197482264d1177de88d0dd7a6728faf4a7ef438f2555d6e23ac046fdbfd4983c37a2aa455766b63ea40c124ab00fc722cc1c54a0a42b1aa0ab70eb5782279aa354114448763b1ea4517c25c4e44d3659aa38439cfb30c63e9d576513c4beb48216165e456634c6bce80099445cc665c561d9f9934dad5f069dc4ccf976817fc9d1e28578d05b083e6805f35ef3b2eb427780848929146ffdd5d1599197c864f7a705bb26439831f2891411f79dbc1cdad4ea4911e7a519a52a952a3578f1451ce402a4f6c387e5e283e1ce740108bef047b6e95d66e95a925abe51d1af4dc7df0ea38aa6d87e382468cda9bcb4fdc60d80c1b0b4cede5cb855e7bad473acff7f1a9b246e2f7f8f48a7dac2c326759107162aeb80e738beab0bf4022b1791f7ba83c08a3db7c734df4442190253481b18dd9945c3bba33e5a0ad8d1b64fe939ee880147f1583bc63657f0d2352f5bfba8209d7409ac108896d97c5f2761af1ab87fa781f5c76f40ad9170651a7e558e17b4bc8cb9e15ffcd42fd51bf6844d47e46697c7882d63d01b58b1e9b544b2a90a58cdf047bc8a0ef701aa59b4bd235958944fea1b080d60a84f56feb818a1c7cf85726f74c1909a9e0c6134b4352a465790b4e447cad90a7b5beb3c871b25cbe9303f8c19237fc4895f84a3132bb89eced5833729efd245cac1eb53f46942361063251fb71c102841b41e6682577466a98e7221cc18ff52c56e1d32ddd7756d51da42df51477b5ab6dcd77a7f1f3a4aaf7bcdd7a02ab790fcc9d69a5df02557200ce41016805c0427c0db603004543ee65103ae2ed89d37df2375944d0e98cb5951a58d5e95288e7b1ddff13776fdb219a92a96d191e88f71a5ec1d073279bf30b515aaeeda386daa8e4ca2813175627224fadd4b9aff5e8a29978c3c3b8d716f4b3b91d25f40c23847cae6146346be1f6900b761e33776b32a24b1a95430310f43421e540848e5d9f24aee1d16db8b2b6c525369ee34b924f1e86b6c56222d732870bfef0e9aada2e325fcd57a87db0a78e6177b5f54efc38db2366039577a2e82ebe67f0bea8b1513d8b4c7ff3286d50388d3e24d043721916b77e5925230d8254a52301ca5b3e7fcd3f66c22602ae69ad6474b29d7041f3a46335c567bdd2451c0fc71214b31b9df897c220e0d0dc7d95e59046bfd9bea37fca7b1eb8177397094d207d2b9b9a403a80e9ba3a10bfdd7387a3479f417778b138ce2061d8a05b59d349194327d2facf76fa45e8ab52c75430fee968f4aa0f90265c6be23aecce3e782ec097737bbf6d483e8c92490f898ad13e0b6330457021b706d541181aabd7b3aeefd3d8b77a49f23258d04a88ffb12038b07a401a19cf2c68f1e73900b8b9a4de46cc0552aa191cdf192a96c1c24023468db7366241629cc9cf65a822c0c8502281a15520a69c3d0251808bed649f4c87c46de83e088af93639f626df5f54103c08dd93a17ee679d3806562f80adbb8d26c8c099b900c2827f2317811a4b8a001b7c1c54a9678f7c57eb26e9ec25055b1346f8ee3e68bd5d3f5bef61b380473c9cb7671560758d6d73ce66f854bac18e666d321d444af1408448a94bd38c7b46b93a509c3c17ef5d46be794cbe038cf4cf7b4053f44e9d510ac651ff0a2cf5fc5d047d2a37c6f805fa3110dc68ccba913f7c1665793522fad0107231f4a06fca9758ecd856d35e3b01c97753b020cbee05780b0ffc6d82d44a74eb9b3b736b80ffef34f5ab4763e713faf9bf62acef6d9c4f88a4b42e9a190b59abb54d198c129d07ef8ecd81b4fdecc1b3426b5000f872beec54f48c8572945c19650fb3eb67716f352e0d1f1810c4cbec0c66c88640b7a3569463d25c38a0a361393dc24194c6565a6c3e30c85b25834cf588b618a3ebf6a6fb4ed931cf8cf167c8c406c57be5596593893d6b2f7754555966185ff29b442caa1fcccc7ab2dc84b6a46e6a36e5cd234df55eeb2959f3e0f2d315f8287f4f242a51e9a673739c4fedf1998bd8535d74cd4f78b8b5799746b2818041ac5fdf6b36b40a685511a2aa7e11b359f8220decd412e2d3d7e45ac9fbac294f600311d5e7edb35282b1c52751defdb2a13066d2546684ca12368f90db9d72e06857fdac6ea2fe0ab02a09ae4d71b4d4ad5c70d46f48de648a9a0c60f953d6890b798988ba179df87c21e52da9640364b5c3555f7c436d23db6a3653e81febba57afc4ba09af6bee8f2b4e6acdab31ce24beb2d033b3849f897db3b8c11082249e11ffca1e654e4972678f737c516d2e7d192651e117547d34f5968ca8a97799b06eb0c6987aafed5694dd02ef3237c96f6e9bf64a1bddd9cbc4436222fb99c1bc0fca8e825cb71598b15fff7191d18e8a358d284b7b1054f46cb02c7684cb76d7b8cebebac045317d734dacb757c08c0aefeb88a0242ef9bfbb9898f9fcab2a6d34fdd04268046e7f4d7a3e42bf0942439fe1283e20c57f2c6f03378416a59db0d2cb4d96be1dace7fbf38bbf56855c70ddbe8953ce91d636040aa35b1ea148ecc293ce52c282362c05d49678a551897bb2d01e422cd2bd293217b04afa40518b6c770b8e832433c35c5b3907a85ea2121d16ee82ef256d61b43fb753f5853b960699235f2bc664c355695ad7274756acfddd0b9784837a2d871cc7d06354d67ef74eb978627d93aa4afa0117c715ce67202eaabfcd663f5bfaa5f522f87c9f21acbb3dd5397b4c7bb7afca9b229ae0e7b1c7428c14881b83a6d65fcd0183d85bf3ff19cbb6d99dc15f9786e3de1f3a567a695ab12d0cfe12c9b96ca1db94518f161557d1a93b66f2354e27226b77b39cd81ed6289e1ff3665783848da797fdf4dc6359e7d33a16b7268663d2e6a16a682071e5da75ae3567107add0d05f7facb1b521587bb623dba0ddb35af7d85c496b6d842b71af31e9e8efd38993a82634af610b5b7309ebef1a0f9da25005ba82d4bbe5d2988897893716bf94699f5b72651b9fae925bc1552ceff759091a38c1ae959c0a951b439d7114f2ad9efc8cfef07e126e5513bf022ffe4f234d7d5e455bdd16687bcbacf8c05bbf8bc60b37eee9dc7554ec969184dee6145d71a7ac9f4f5a568bc6a827febb04cbb43e6d8c2d877116928768822a926b806b7f5caa64bf8df6aa5a519396b347278b8354621e202a3c00bf0e2703a3575b6074ee5c4690e88ddcab9178acbb57f9bc5cdfbeca45de3db2bb8ccc0f777e833c31894bf1ce83eba0e78aa4e73351502a98204caf20cd5b7d6f7a02ed7367a03cc91d2b2d5acd9884e195013b18d6eab27cb27518216c1213b276b4b7abe8cf1e85a92b9987b2ee3c67736fb8f468ad3ed6748d623b3a404a26725d8e3eeae4b06616951a232562727cfa5326d0c6a59461a1f5d6f26c6fedc018808ed8d731bffdd1f8b02c0a0027250163759879e946ac20a9ac8d580ad817583dccf92b8bebf1d9e52b4e2604c2828ee8c83996ddcbe411968d77fedefa5793f626a49e3b95700029da26362cc6159e2ccce2976c86ce5f4a244d31beaa259f7f6f58bd9695cf4d0a859a580a8751ff125f6f6e342c952918f8a2e402a0f8e28d6f4a305fd64883b049fc08ea5f84a922e9dab6c6af65421f7da7161eb4d45b8b473740bf5f17bab44071133951b7f68e859af97fb82ff8b6077e143c828f2290ca9df98d7c282b2fb1bef28393eb803db9f0b36d3047d4";
		content = dESEncrypt.decrypt(content);		
		System.out.println(content);
	}
	
	public static void saveHousePriceTest(){
		HousePriceDto housePriceDto=new HousePriceDto();
		housePriceDto.setHouseBaseFid("8a9e989e5ce778d0015ce88b29190063");
		housePriceDto.setRentWay(0);
		housePriceDto.setHouseArea(230d);
		housePriceDto.setLeasePrice(256d);
		housePriceDto.setCleaningFees(12d);
		housePriceDto.setHouseModel("1,1,1,1,1");
		housePriceDto.setCheckInLimit(3);
		housePriceDto.setHouseFacility("ProductRulesEnum002001_1,ProductRulesEnum002001_2,ProductRulesEnum002002_1");
		housePriceDto.setWeekendPriceSwitch(1);
		housePriceDto.setWeekendPriceType("5,6");
		housePriceDto.setWeekendPriceVal(500d);
		housePriceDto.setFullDayRateSwitch(1);
		housePriceDto.setSevenDiscountRate(7d);
		housePriceDto.setThirtyDiscountRate(8d);
		HouseRoomVo vo=new HouseRoomVo();
		vo.setBedMsg("1_2,2_1");
		housePriceDto.getHouseRoomList().add(vo);
		HouseRoomVo vo1=new HouseRoomVo();
		vo1.setBedMsg("1_1,2_2");
		housePriceDto.getHouseRoomList().add(vo1);
		
		System.err.println(JsonEntityTransform.Object2Json(housePriceDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(housePriceDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(housePriceDto).toString(),"UTF-8"));
		
		
		DESEncrypt dESEncrypt  = new DESEncrypt();
		
		String content = "b29f8";
		content = dESEncrypt.decrypt(content);		
		System.out.println(content);
	}

	public static  void initSetPriceTest(){
		Map<String, Object> vo=new HashMap<>();
		vo.put("houseBaseFid", "8a9e9a9a548b218201548b223c98000e");
		vo.put("roomFid", "8a9e9a9a548b218201548b223cce0018");
		vo.put("rentWay", 1);
		System.err.println(JsonEntityTransform.Object2Json(vo));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(vo));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(vo).toString(),"UTF-8"));


		DESEncrypt dESEncrypt  = new DESEncrypt();

		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acc5d1b9961cee8f654dea0cf2090d3d3bd6bafd5c9bd9b59ef1b04adf9fab7d9218f81497749f88685a83780c575a7322f127c9168748aba06cc29c7faebab4a6608df490d5172b4713fe2a8fdd0715bf6ce50a159c8140880cbcdb3fd693a3ce0e6796dd6a62b68179eb78fc4c0b3c511e5170fbd207641daa5e93ff7b3901ff75c84c9a2967ed5575ea1102c11795d5fefc3caa11b2bd6eb0606d68589696680c3e7568dde996d3137a12a0abced833ca2df601694f2c2fa855028b7451bf4643406db28c88d80dc7e16084851fd8edd2204687743a7c3a845d7ec628f71daf6a5bd1740f9aa8dfe21625b02537e09b1f146f3877ae5a91617d3f406b3a4a542241e94a0e48a7495be0b68f31e014b4e003b02069d506d45187deb48df5b72c4c5a527d044f52bc720b13450e23d9ce370837623a781d0d8aa3286c8875f494634020ea38d6b4681151bcc7264e01c8db0323ab2257c795076b9c851ba4a12a8bb6dc470c0fb1eab03d64c43c786f03eef45da2f67eaad2d2e2e616fb7c52a1ce3e0b8ef1ad4c21de5b643cc9ce5cc74de20e19c3965e265214683ff295637cd5ff997a31264aad3681fa0b603c0823dcf421360353a320a46b75a3d15ab83e34ed84ee3977424f6152730afd873a8bfb6a255263942e414f7f0b89f0be48d4b78b18b00649d1b646dd546f029a7793e14747de3f5bf571face29ea416571d47eae7e957ecacdec654b88a60abbcc8a1d370136ae10a04535b106d9ee4ce09c1b02d4d8bb370e7979f673c168898b5cff654828115af513aecb4505437cb39fa2df93fd47a676b060d6e5113db5a61093251a35c4e1ba2a30e24ccc145ce6480fe540ede92160bc7e7b05bb724abcd507ace6f29f98951cb72f3902b0e3949b014cb4b0e1e8b459f9661037fb9f7149e0df6a67a4c7bab252e48806ff9851432774be0bb57f76622b0904ad6dcee9f46c1fc0c63f9308db6a7428837b2cc65a30d264e83a72a726e32cbf7eb5d867f6c93bd02704dbe192986b8b2fa06667e245ad07c0d4b72588c3a635d1ee19db29213cec40afa364ac530a655bb01256e1dafc2f48b228be713f774ad5812fa66877d7f5120605b6e613374e9eaa86772d3a4726c46766827c56d0f22e10fbed63f799fb59d000e55d228b3f69810f840f679f987bd73aff87811a36a72d4a9b8a209a67b0a81b6ac62a28bff82dee87b4ec667166e2ec1a84a43f8ab7d42393ee2770f78a688c67b142ed6c446c8c60f70e83048b65ae636fe99e0b46ebae55da4db98302f011ce654a27d4e91189189cff3afa7f34f3df343482a81cd5f5ede69e98b38c28fcdb6d3b8448bce578e3733226f00e7c9949604d8d34ce397fe43862257b9b588d59049759719f3ddd90d10094c8b4212763f91f5dd26610cde6ffd726819c3e6e0c26f136819e5c967a7a5f6af25b8712cb085a2a9560842f169ec9d21b031f20f864e158a0dff4fedd931236080b265f6ac18c724e3e5a2ed1717c58822231b9531eb120097446192acb85119e04c16758d8a9d83b1ddf1ce6371bed0b12637c56fee8b8be187319d6b3baf79f8e233ca6cb319b6637d5eb23539da6ee3758599b361b7e54ef67a80ec65f5b70d62a8a9aa00c7ea2be1140da021d4f6a472613c815b03fd75e7b3941800af2ea6d723ea69baa8dcdd637269fe7e81e56075827d489f9659a098c10e742b1f194bc841f5800a15227526ff6035bdea6b137963b484be4e5f4ea9f0aa341eafb02e73cdcf26ef232a7bf08a7e30d9199d352195409fd60d58cbe034833878ab28e2d25348cb7a933960911c06e718e96b0b1cb617d906c053aef03e168968081c5796778e2c8d6be2d06fec76f2e988e376210ecabbd3b31da0932cf62e7d69833eaa9f45967159bb3428635a95d8ac506f00dd93bc849ebe81b84478116707ec9e5951d58c899f8ac3a01dee17ebe4ff1d7d4aa01368faadf47f0b963192feb9573d1a64170ea3120d7270100a06227e7fda19bf09cf758adbc3563ae805078fafe7753acb40e7d66f7c85e9c50b593d7f48cd58719f1bb001497603e57adb7b16bcc27424a2fc90eb2ad323e985f2a9aef4ee2cdd2cb36093408b5136a8717dbdbd5bc2dd197482264d1177de88d0dd7a6728faf4a7ef438f2555d6e23ac046fdbfd4983c37a2aa455766b63ea40c124ab00fc722cc1c54a0a42b1aa0ab70eb5782279aa354114448763b1ea4517c25c4e44d3659aa38439cfb30c63e9d576513c4beb48216165e456634c6bce80099445cc665c561d9f9934dad5f069dc4ccf976817fc9d1e28578d05b083e6805f35ef3b2eb427780848929146ffdd5d1599197c864f7a705bb26439831f2891411f79dbc1cdad4ea4911e7a519a52a952a3578f1451ce402a4f6c387e5e283e1ce740108bef047b6e95d66e95a925abe51d1af4dc7df0ea38aa6d87e382468cda9bcb4fdc60d80c1b0b4cede5cb855e7bad473acff7f1a9b246e2f7f8f48a7dac2c326759107162aeb80e738beab0bf4022b1791f7ba83c08a3db7c734df4442190253481b18dd9945c3bba33e5a0ad8d1b64fe939ee880147f1583bc63657f0d2352f5bfba8209d7409ac108896d97c5f2761af1ab87fa781f5c76f40ad9170651a7e558e17b4bc8cb9e15ffcd42fd51bf6844d47e46697c7882d63d01b58b1e9b544b2a90a58cdf047bc8a0ef701aa59b4bd235958944fea1b080d60a84f56feb818a1c7cf85726f74c1909a9e0c6134b4352a465790b4e447cad90a7b5beb3c871b25cbe9303f8c19237fc4895f84a3132bb89eced5833729efd245cac1eb53f46942361063251fb71c102841b41e6682577466a98e7221cc18ff52c56e1d32ddd7756d51da42df51477b5ab6dcd77a7f1f3a4aaf7bcdd7a02ab790fcc9d69a5df02557200ce41016805c0427c0db603004543ee65103ae2ed89d37df2375944d0e98cb5951a58d5e95288e7b1ddff13776fdb219a92a96d191e88f71a5ec1d073279bf30b515aaeeda386daa8e4ca2813175627224fadd4b9aff5e8a29978c3c3b8d716f4b3b91d25f40c23847cae6146346be1f6900b761e33776b32a24b1a95430310f43421e540848e5d9f24aee1d16db8b2b6c525369ee34b924f1e86b6c56222d732870bfef0e9aada2e325fcd57a87db0a78e6177b5f54efc38db2366039577a2e82ebe67f0bea8b1513d8b4c7ff3286d50388d3e24d043721916b77e5925230d8254a52301ca5b3e7fcd3f66c22602ae69ad6474b29d7041f3a46335c567bdd2451c0fc71214b31b9df897c220e0d0dc7d95e59046bfd9bea37fca7b1eb8177397094d207d2b9b9a403a80e9ba3a10bfdd7387a3479f417778b138ce2061d8a05b59d349194327d2facf76fa45e8ab52c75430fee968f4aa0f90265c6be23aecce3e782ec097737bbf6d483e8c92490f898ad13e0b6330457021b706d541181aabd7b3aeefd3d8b77a49f23258d04a88ffb12038b07a401a19cf2c68f1e73900b8b9a4de46cc0552aa191cdf192a96c1c24023468db7366241629cc9cf65a822c0c8502281a15520a69c3d0251808bed649f4c87c46de83e088af93639f626df5f54103c08dd93a17ee679d3806562f80adbb8d26c8c099b900c2827f2317811a4b8a001b7c1c54a9678f7c57eb26e9ec25055b1346f8ee3e68bd5d3f5bef61b380473c9cb7671560758d6d73ce66f854bac18e666d321d444af1408448a94bd38c7b46b93a509c3c17ef5d46be794cbe038cf4cf7b4053f44e9d510ac651ff0a2cf5fc5d047d2a37c6f805fa3110dc68ccba913f7c1665793522fad0107231f4a06fca9758ecd856d35e3b01c97753b020cbee05780b0ffc6d82d44a74eb9b3b736b80ffef34f5ab4763e713faf9bf62acef6d9c4f88a4b42e9a190b59abb54d198c129d07ef8ecd81b4fdecc1b3426b5000f872beec54f48c8572945c19650fb3eb67716f352e0d1f1810c4cbec0c66c88640b7a3569463d25c38a0a361393dc24194c6565a6c3e30c85b25834cf588b618a3ebf6a6fb4ed931cf8cf167c8c406c57be5596593893d6b2f7754555966185ff29b442caa1fcccc7ab2dc84b6a46e6a36e5cd234df55eeb2959f3e0f2d315f8287f4f242a51e9a673739c4fedf1998bd8535d74cd4f78b8b5799746b2818041ac5fdf6b36b40a685511a2aa7e11b359f8220decd412e2d3d7e45ac9fbac294f600311d5e7edb35282b1c52751defdb2a13066d2546684ca12368f90db9d72e06857fdac6ea2fe0ab02a09ae4d71b4d4ad5c70d46f48de648a9a0c60f953d6890b798988ba179df87c21e52da9640364b5c3555f7c436d23db6a3653e81febba57afc4ba09af6bee8f2b4e6acdab31ce24beb2d033b3849f897db3b8c11082249e11ffca1e654e4972678f737c516d2e7d192651e117547d34f5968ca8a97799b06eb0c6987aafed5694dd02ef3237c96f6e9bf64a1bddd9cbc4436222fb99c1bc0fca8e825cb71598b15fff7191d18e8a358d284b7b1054f46cb02c7684cb76d7b8cebebac045317d734dacb757c08c0aefeb88a0242ef9bfbb9898f9fcab2a6d34fdd04268046e7f4d7a3e42bf0942439fe1283e20c57f2c6f03378416a59db0d2cb4d96be1dace7fbf38bbf56855c70ddbe8953ce91d636040aa35b1ea148ecc293ce52c282362c05d49678a551897bb2d01e422cd2bd293217b04afa40518b6c770b8e832433c35c5b3907a85ea2121d16ee82ef256d61b43fb753f5853b960699235f2bc664c355695ad7274756acfddd0b9784837a2d871cc7d06354d67ef74eb978627d93aa4afa0117c715ce67202eaabfcd663f5bfaa5f522f87c9f21acbb3dd5397b4c7bb7afca9b229ae0e7b1c7428c14881b83a6d65fcd0183d85bf3ff19cbb6d99dc15f9786e3de1f3a567a695ab12d0cfe12c9b96ca1db94518f161557d1a93b66f2354e27226b77b39cd81ed6289e1ff3665783848da797fdf4dc6359e7d33a16b7268663d2e6a16a682071e5da75ae3567107add0d05f7facb1b521587bb623dba0ddb35af7d85c496b6d842b71af31e9e8efd38993a82634af610b5b7309ebef1a0f9da25005ba82d4bbe5d2988897893716bf94699f5b72651b9fae925bc1552ceff759091a38c1ae959c0a951b439d7114f2ad9efc8cfef07e126e5513bf022ffe4f234d7d5e455bdd16687bcbacf8c05bbf8bc60b37eee9dc7554ec969184dee6145d71a7ac9f4f5a568bc6a827febb04cbb43e6d8c2d877116928768822a926b806b7f5caa64bf8df6aa5a519396b347278b8354621e202a3c00bf0e2703a3575b6074ee5c4690e88ddcab9178acbb57f9bc5cdfbeca45de3db2bb8ccc0f777e833c31894bf1ce83eba0e78aa4e73351502a98204caf20cd5b7d6f7a02ed7367a03cc91d2b2d5acd9884e195013b18d6eab27cb27518216c1213b276b4b7abe8cf1e85a92b9987b2ee3c67736fb8f468ad3ed6748d623b3a404a26725d8e3eeae4b06616951a232562727cfa5326d0c6a59461a1f5d6f26c6fedc018808ed8d731bffdd1f8b02c0a0027250163759879e946ac20a9ac8d580ad817583dccf92b8bebf1d9e52b4e2604c2828ee8c83996ddcbe411968d77fedefa5793f626a49e3b95700029da26362cc6159e2ccce2976c86ce5f4a244d31beaa259f7f6f58bd9695cf4d0a859a580a8751ff125f6f6e342c952918f8a2e402a0f8e28d6f4a305fd64883b049fc08ea5f84a922e9dab6c6af65421f7da7161eb4d45b8b473740bf5f17bab44071133951b7f68e859af97fb82ff8b6077e143c828f2290ca9df98d7c282b2fb1bef28393eb803db9f0b36d3047d4";
		content = dESEncrypt.decrypt(content);
		System.out.println(content);
	}

	public static  void savePriceTest(){
		HousePriceDto housePriceDto = new HousePriceDto();
		housePriceDto.setHouseBaseFid("8a90a2d4549341c6015494bb1e8501c3");
		housePriceDto.setRentWay(0);
		housePriceDto.setLeasePrice(11111.11D);
		housePriceDto.setCleaningFees(222.22D);

		housePriceDto.setWeekendPriceSwitch(1);
		housePriceDto.setWeekendPriceType("5,6");
		housePriceDto.setWeekendPriceVal(333.33D);

		housePriceDto.setFullDayRateSwitch(1);
		housePriceDto.setSevenDiscountRate(7.5D);
		housePriceDto.setThirtyDiscountRate(9.5D);
		System.err.println(JsonEntityTransform.Object2Json(housePriceDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(housePriceDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(housePriceDto).toString(),"UTF-8"));


		DESEncrypt dESEncrypt  = new DESEncrypt();

		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acc5d1b9961cee8f654dea0cf2090d3d3bd6bafd5c9bd9b59ef1b04adf9fab7d9218f81497749f88685a83780c575a7322f127c9168748aba06cc29c7faebab4a6608df490d5172b4713fe2a8fdd0715bf6ce50a159c8140880cbcdb3fd693a3ce0e6796dd6a62b68179eb78fc4c0b3c511e5170fbd207641daa5e93ff7b3901ff75c84c9a2967ed5575ea1102c11795d5fefc3caa11b2bd6eb0606d68589696680c3e7568dde996d3137a12a0abced833ca2df601694f2c2fa855028b7451bf4643406db28c88d80dc7e16084851fd8edd2204687743a7c3a845d7ec628f71daf6a5bd1740f9aa8dfe21625b02537e09b1f146f3877ae5a91617d3f406b3a4a542241e94a0e48a7495be0b68f31e014b4e003b02069d506d45187deb48df5b72c4c5a527d044f52bc720b13450e23d9ce370837623a781d0d8aa3286c8875f494634020ea38d6b4681151bcc7264e01c8db0323ab2257c795076b9c851ba4a12a8bb6dc470c0fb1eab03d64c43c786f03eef45da2f67eaad2d2e2e616fb7c52a1ce3e0b8ef1ad4c21de5b643cc9ce5cc74de20e19c3965e265214683ff295637cd5ff997a31264aad3681fa0b603c0823dcf421360353a320a46b75a3d15ab83e34ed84ee3977424f6152730afd873a8bfb6a255263942e414f7f0b89f0be48d4b78b18b00649d1b646dd546f029a7793e14747de3f5bf571face29ea416571d47eae7e957ecacdec654b88a60abbcc8a1d370136ae10a04535b106d9ee4ce09c1b02d4d8bb370e7979f673c168898b5cff654828115af513aecb4505437cb39fa2df93fd47a676b060d6e5113db5a61093251a35c4e1ba2a30e24ccc145ce6480fe540ede92160bc7e7b05bb724abcd507ace6f29f98951cb72f3902b0e3949b014cb4b0e1e8b459f9661037fb9f7149e0df6a67a4c7bab252e48806ff9851432774be0bb57f76622b0904ad6dcee9f46c1fc0c63f9308db6a7428837b2cc65a30d264e83a72a726e32cbf7eb5d867f6c93bd02704dbe192986b8b2fa06667e245ad07c0d4b72588c3a635d1ee19db29213cec40afa364ac530a655bb01256e1dafc2f48b228be713f774ad5812fa66877d7f5120605b6e613374e9eaa86772d3a4726c46766827c56d0f22e10fbed63f799fb59d000e55d228b3f69810f840f679f987bd73aff87811a36a72d4a9b8a209a67b0a81b6ac62a28bff82dee87b4ec667166e2ec1a84a43f8ab7d42393ee2770f78a688c67b142ed6c446c8c60f70e83048b65ae636fe99e0b46ebae55da4db98302f011ce654a27d4e91189189cff3afa7f34f3df343482a81cd5f5ede69e98b38c28fcdb6d3b8448bce578e3733226f00e7c9949604d8d34ce397fe43862257b9b588d59049759719f3ddd90d10094c8b4212763f91f5dd26610cde6ffd726819c3e6e0c26f136819e5c967a7a5f6af25b8712cb085a2a9560842f169ec9d21b031f20f864e158a0dff4fedd931236080b265f6ac18c724e3e5a2ed1717c58822231b9531eb120097446192acb85119e04c16758d8a9d83b1ddf1ce6371bed0b12637c56fee8b8be187319d6b3baf79f8e233ca6cb319b6637d5eb23539da6ee3758599b361b7e54ef67a80ec65f5b70d62a8a9aa00c7ea2be1140da021d4f6a472613c815b03fd75e7b3941800af2ea6d723ea69baa8dcdd637269fe7e81e56075827d489f9659a098c10e742b1f194bc841f5800a15227526ff6035bdea6b137963b484be4e5f4ea9f0aa341eafb02e73cdcf26ef232a7bf08a7e30d9199d352195409fd60d58cbe034833878ab28e2d25348cb7a933960911c06e718e96b0b1cb617d906c053aef03e168968081c5796778e2c8d6be2d06fec76f2e988e376210ecabbd3b31da0932cf62e7d69833eaa9f45967159bb3428635a95d8ac506f00dd93bc849ebe81b84478116707ec9e5951d58c899f8ac3a01dee17ebe4ff1d7d4aa01368faadf47f0b963192feb9573d1a64170ea3120d7270100a06227e7fda19bf09cf758adbc3563ae805078fafe7753acb40e7d66f7c85e9c50b593d7f48cd58719f1bb001497603e57adb7b16bcc27424a2fc90eb2ad323e985f2a9aef4ee2cdd2cb36093408b5136a8717dbdbd5bc2dd197482264d1177de88d0dd7a6728faf4a7ef438f2555d6e23ac046fdbfd4983c37a2aa455766b63ea40c124ab00fc722cc1c54a0a42b1aa0ab70eb5782279aa354114448763b1ea4517c25c4e44d3659aa38439cfb30c63e9d576513c4beb48216165e456634c6bce80099445cc665c561d9f9934dad5f069dc4ccf976817fc9d1e28578d05b083e6805f35ef3b2eb427780848929146ffdd5d1599197c864f7a705bb26439831f2891411f79dbc1cdad4ea4911e7a519a52a952a3578f1451ce402a4f6c387e5e283e1ce740108bef047b6e95d66e95a925abe51d1af4dc7df0ea38aa6d87e382468cda9bcb4fdc60d80c1b0b4cede5cb855e7bad473acff7f1a9b246e2f7f8f48a7dac2c326759107162aeb80e738beab0bf4022b1791f7ba83c08a3db7c734df4442190253481b18dd9945c3bba33e5a0ad8d1b64fe939ee880147f1583bc63657f0d2352f5bfba8209d7409ac108896d97c5f2761af1ab87fa781f5c76f40ad9170651a7e558e17b4bc8cb9e15ffcd42fd51bf6844d47e46697c7882d63d01b58b1e9b544b2a90a58cdf047bc8a0ef701aa59b4bd235958944fea1b080d60a84f56feb818a1c7cf85726f74c1909a9e0c6134b4352a465790b4e447cad90a7b5beb3c871b25cbe9303f8c19237fc4895f84a3132bb89eced5833729efd245cac1eb53f46942361063251fb71c102841b41e6682577466a98e7221cc18ff52c56e1d32ddd7756d51da42df51477b5ab6dcd77a7f1f3a4aaf7bcdd7a02ab790fcc9d69a5df02557200ce41016805c0427c0db603004543ee65103ae2ed89d37df2375944d0e98cb5951a58d5e95288e7b1ddff13776fdb219a92a96d191e88f71a5ec1d073279bf30b515aaeeda386daa8e4ca2813175627224fadd4b9aff5e8a29978c3c3b8d716f4b3b91d25f40c23847cae6146346be1f6900b761e33776b32a24b1a95430310f43421e540848e5d9f24aee1d16db8b2b6c525369ee34b924f1e86b6c56222d732870bfef0e9aada2e325fcd57a87db0a78e6177b5f54efc38db2366039577a2e82ebe67f0bea8b1513d8b4c7ff3286d50388d3e24d043721916b77e5925230d8254a52301ca5b3e7fcd3f66c22602ae69ad6474b29d7041f3a46335c567bdd2451c0fc71214b31b9df897c220e0d0dc7d95e59046bfd9bea37fca7b1eb8177397094d207d2b9b9a403a80e9ba3a10bfdd7387a3479f417778b138ce2061d8a05b59d349194327d2facf76fa45e8ab52c75430fee968f4aa0f90265c6be23aecce3e782ec097737bbf6d483e8c92490f898ad13e0b6330457021b706d541181aabd7b3aeefd3d8b77a49f23258d04a88ffb12038b07a401a19cf2c68f1e73900b8b9a4de46cc0552aa191cdf192a96c1c24023468db7366241629cc9cf65a822c0c8502281a15520a69c3d0251808bed649f4c87c46de83e088af93639f626df5f54103c08dd93a17ee679d3806562f80adbb8d26c8c099b900c2827f2317811a4b8a001b7c1c54a9678f7c57eb26e9ec25055b1346f8ee3e68bd5d3f5bef61b380473c9cb7671560758d6d73ce66f854bac18e666d321d444af1408448a94bd38c7b46b93a509c3c17ef5d46be794cbe038cf4cf7b4053f44e9d510ac651ff0a2cf5fc5d047d2a37c6f805fa3110dc68ccba913f7c1665793522fad0107231f4a06fca9758ecd856d35e3b01c97753b020cbee05780b0ffc6d82d44a74eb9b3b736b80ffef34f5ab4763e713faf9bf62acef6d9c4f88a4b42e9a190b59abb54d198c129d07ef8ecd81b4fdecc1b3426b5000f872beec54f48c8572945c19650fb3eb67716f352e0d1f1810c4cbec0c66c88640b7a3569463d25c38a0a361393dc24194c6565a6c3e30c85b25834cf588b618a3ebf6a6fb4ed931cf8cf167c8c406c57be5596593893d6b2f7754555966185ff29b442caa1fcccc7ab2dc84b6a46e6a36e5cd234df55eeb2959f3e0f2d315f8287f4f242a51e9a673739c4fedf1998bd8535d74cd4f78b8b5799746b2818041ac5fdf6b36b40a685511a2aa7e11b359f8220decd412e2d3d7e45ac9fbac294f600311d5e7edb35282b1c52751defdb2a13066d2546684ca12368f90db9d72e06857fdac6ea2fe0ab02a09ae4d71b4d4ad5c70d46f48de648a9a0c60f953d6890b798988ba179df87c21e52da9640364b5c3555f7c436d23db6a3653e81febba57afc4ba09af6bee8f2b4e6acdab31ce24beb2d033b3849f897db3b8c11082249e11ffca1e654e4972678f737c516d2e7d192651e117547d34f5968ca8a97799b06eb0c6987aafed5694dd02ef3237c96f6e9bf64a1bddd9cbc4436222fb99c1bc0fca8e825cb71598b15fff7191d18e8a358d284b7b1054f46cb02c7684cb76d7b8cebebac045317d734dacb757c08c0aefeb88a0242ef9bfbb9898f9fcab2a6d34fdd04268046e7f4d7a3e42bf0942439fe1283e20c57f2c6f03378416a59db0d2cb4d96be1dace7fbf38bbf56855c70ddbe8953ce91d636040aa35b1ea148ecc293ce52c282362c05d49678a551897bb2d01e422cd2bd293217b04afa40518b6c770b8e832433c35c5b3907a85ea2121d16ee82ef256d61b43fb753f5853b960699235f2bc664c355695ad7274756acfddd0b9784837a2d871cc7d06354d67ef74eb978627d93aa4afa0117c715ce67202eaabfcd663f5bfaa5f522f87c9f21acbb3dd5397b4c7bb7afca9b229ae0e7b1c7428c14881b83a6d65fcd0183d85bf3ff19cbb6d99dc15f9786e3de1f3a567a695ab12d0cfe12c9b96ca1db94518f161557d1a93b66f2354e27226b77b39cd81ed6289e1ff3665783848da797fdf4dc6359e7d33a16b7268663d2e6a16a682071e5da75ae3567107add0d05f7facb1b521587bb623dba0ddb35af7d85c496b6d842b71af31e9e8efd38993a82634af610b5b7309ebef1a0f9da25005ba82d4bbe5d2988897893716bf94699f5b72651b9fae925bc1552ceff759091a38c1ae959c0a951b439d7114f2ad9efc8cfef07e126e5513bf022ffe4f234d7d5e455bdd16687bcbacf8c05bbf8bc60b37eee9dc7554ec969184dee6145d71a7ac9f4f5a568bc6a827febb04cbb43e6d8c2d877116928768822a926b806b7f5caa64bf8df6aa5a519396b347278b8354621e202a3c00bf0e2703a3575b6074ee5c4690e88ddcab9178acbb57f9bc5cdfbeca45de3db2bb8ccc0f777e833c31894bf1ce83eba0e78aa4e73351502a98204caf20cd5b7d6f7a02ed7367a03cc91d2b2d5acd9884e195013b18d6eab27cb27518216c1213b276b4b7abe8cf1e85a92b9987b2ee3c67736fb8f468ad3ed6748d623b3a404a26725d8e3eeae4b06616951a232562727cfa5326d0c6a59461a1f5d6f26c6fedc018808ed8d731bffdd1f8b02c0a0027250163759879e946ac20a9ac8d580ad817583dccf92b8bebf1d9e52b4e2604c2828ee8c83996ddcbe411968d77fedefa5793f626a49e3b95700029da26362cc6159e2ccce2976c86ce5f4a244d31beaa259f7f6f58bd9695cf4d0a859a580a8751ff125f6f6e342c952918f8a2e402a0f8e28d6f4a305fd64883b049fc08ea5f84a922e9dab6c6af65421f7da7161eb4d45b8b473740bf5f17bab44071133951b7f68e859af97fb82ff8b6077e143c828f2290ca9df98d7c282b2fb1bef28393eb803db9f0b36d3047d4";
		content = dESEncrypt.decrypt(content);
		System.out.println(content);
	}

	
	//GroupMemberPageInfoDto groupMemberPageInfoDto  
	
	public static  void queryGroupUserInfo(){
		GroupMemberPageInfoDto groupMemberPageInfoDto   = new GroupMemberPageInfoDto();
		
		groupMemberPageInfoDto.setGroupId("22892344573954");
		groupMemberPageInfoDto.setPage(1);
		
		System.err.println(JsonEntityTransform.Object2Json(groupMemberPageInfoDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(groupMemberPageInfoDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(groupMemberPageInfoDto).toString(),"UTF-8"));


		DESEncrypt dESEncrypt  = new DESEncrypt();

		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9ac6c2bad419df24db615b62f05fe929bef6703ceaa553c939489a5c4a82f99f6f0685057da38558337d12e2c4e6bea1b019264b4b66173137c59bb941e05d19d1d0e3b2d32a47dbc4a7eeebbf6c6b35bfacea3eb2090553f4ba17c61ff25353bf99ddf7a892036d4ac71aa641cd74c552af37c7f38f4777abdc777f66d9e643cae0ecd01479c4e8d3e00819dcabdf36faa84caacce504ade9e8dd3acbde8afc179f558f99b78bdc2aa6965fccd6a80926382eb9e6612d6c88ab960cbf0a78a993b759b5cf6ea42a443ee68fad88fb2e0fea84855ea6b415fc9948859479336a201b543411797adcd7c6702cf47ed706bf399a743eb5b8c7a49cf50a4f84d7a2daeb66fc32a15bcd5be797a6c5684fc3a3ab0e074562132d38e4b095396f1fc0dc546de10abb5f9931c3bb8a2bf97105da1a6a14588fe9168622206a8953cf9b765a95a0d07d4984e75560b797187103c347eeaca886747738485ce163e13f5f82ff7d83bfb2719bfdadbce10943792277d81be6ee6f048e2646fb19e4b9db80fc9e962f2313c156f878138da3e64a6594b4461fce6fb3cb523f205e2b4148d62ad92f6d9d3d5ec316850acdf715b05f17d35f520b68fab96edcc8e99df5e172e880fd5ec8428e927bfde04830d9edfd79d1fad58d7e2f0a94a4ecaa0d962c94434e01a0770befbd38c8c1e3769ba960e6f30b11f5b2fb0f1d8b50c19949214b2b63b8cf8c176500e251355616db2280d5d3b48e46fa9f42a02cf08a82c3e1fd0750343f583814e9e7e8c24f5e4a53235aae3c2ec23f1485a547fefac03dae131af0460bc81ce579999252651ad87523434a0e157d93f7d605d01e4a6806867e22cea7cbafca1f0444129f12fca80173caa0bf8e6a91d18af8dcf3b1ddc2c799faa00c1b69dbab3ba253891549faf1324766bf29664374e5323a1f9185dfa0b89f3f9fc2d7cee81685ad8c08a22caa648787df54c3279230c41ac09bd74d9fc39c88d9cbcf4577793a7086b4d119bf440a2b5d31bf70529e7fe6992e5cb6fd5ce41669cfdc1fcaa3fbd";
		content = dESEncrypt.decrypt(content);
		System.out.println(content);
	}
	public static void main(String[] args) {
		//communityNameListTest();
//		aaa();
//		houseRoomList();
//		houseRoomListDes();
//		setSpecialPrice();
//		setSpecialPriceDes();
//		lockHouse();
//		lockHouseDes();
//		leaseCalendar();
//		leaseCalendarDes();
//		saveHousePic();
//		saveHousePicDes();
//		upDownHouse();
//		upDownHouseDes();
//		housePicMsgList();
//		housePicMsgListDes();
//		deleteHousePic();
//		deleteHousePicDes();
		//setDefaultPic();
		//testSaveHouseDesc();
		//testHouseIssue();
		//testSaveTypeLocation();
		//setDefaultPicDes();
//		saveHousePriceTest();
//		initSetPriceTest();
		//testHouseIssue();
		
		queryGroupUserInfo();
	}
	

}
