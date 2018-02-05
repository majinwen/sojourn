package test;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.common.utils.CryptAES;
import com.ziroom.minsu.services.house.dto.HouseBaseListDto;
import com.ziroom.minsu.valenum.customer.CustomerEduEnum;
import com.ziroom.minsu.valenum.customer.CustomerSexEnum;

/**
 * 
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class ApiTest {
	private static final IEncrypt iEncrypt = EncryptFactory.createEncryption("DES");
	
	public static void houseRoomList(){
		HouseBaseListDto paramDto = new HouseBaseListDto();
		paramDto.setLandlordUid("3a59968c-1eb9-4612-e325-5cc0a856ac34");
		System.err.println(JsonEntityTransform.Object2Json(paramDto));
		System.err.println("?2y5QfvAy="+iEncrypt.encrypt(JsonEntityTransform.Object2Json(paramDto))+"&hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(paramDto),"UTF-8"));
		System.err.println(iEncrypt.decrypt("b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9ac780fe9331685a349ed9e644b5f0a768186b14e3397cfeb5f6468c011b691f7018f5298196104b2aa50feefed98de30dcc84631b09729ee3d6349f4e4e206a6f3b18a4fa1e17d8ce1f5efa675fc42b37279ff1f14cadf4938f137e94c6b6113a907adf0de06411fadba63eb3104c44ed1da4314ece22dc344513e920d61e3af2cbfec35da72d6295916cdddb3f98b2bc94e636874bd046c3a1b85e590f62a764e5ac00da7dec306fbca621ca7388a8a8dbbef089113886cfdf7b24e41955893dfaa06a11b14d377f607b7d3927291c38da4cdbba88bcad99811d7265db00b9425f13aa39ba8c12f09dbc9cd095dd8db96df74fd96340aff8f1481d24478795b13797a59aeb24c1f8cec0c3fd3ce63ea725066d4b0f2381cdc8a8a45c0e392ecf7d9da1b86d1fec9c35b131e785352dedd1534b11f1ca34038920b3af0e52d41682156ea38b47962b51cf38cc3aa2c5555f94bdacd45df6e422c05e51be1c203694dc9fa980a9e8219ffa0066a996e4376a52d6b475ab37fc725e54602c2bf77678bf00d56f0a8cc3a65fe8c4068741d632417a9c0970da58f60c29bbcfcf945750a3c6c27ca5a235f479d38c15c2b738c3139c078d70bd0704da7ccc05f9f35e8a2c21ff1e507bee756c6a516019f6d8b7c6ecb4b4fec598f2dbc8891e28cf4a1c1145578a1e38a4b0404ceb69e65e0268141615c5250331b321d5e00947f747f403fbb89b4e48516c3bc274ed1e47a1bb31ad028a6f3bf1db7b796525e0f3240b49e37ed748e3ae1ad5931921965d5f896d427e9677e05e1f19771ddb84c282ca5c1701ade9f47a6569723388edc38c31e8705255edbc62b3d0811d12b76a9043a342cf06f17ae4f76eda43dd6e9a6b84dba01b5bbd028eaa2a5fc4063fcb549feb7802aa426b3c92e406f880e9c1f2f29e2b16789eb3e633749bf397357106aa584aa830568232ca9d9a58ceba1680315697ecbecdd8923d887a1a96bdf8a7df8f5ef3f15d7eb8c8e549d58106feb32367868b1df93db1e9579c41a1d7a3f4cbe8552c91917e80b1c9f8b77d0d9c4abfd462611bf6e7e047770a30810b29b5f51741ecd0cb394b59a6a5b460ee9ec4cc0bd760e9bab5c242fda185c5cc53921bd1442e7de08d0a4f700b86bc83e88525e7c8643d717a11be9c9fb0eab66285426da5589aa42884f26c5bc59f4a811c226dbc0d7ee2dd6ac78dfd4a8defc0ac43a736c3877c379c3840d76bd26974aa7211e6a58edc0a75d08902f2d11c25f3ed8cc724cc6c93f608212fc6b019ec6572bb9e790821eef22a778fd10d1c7a27641d169cd37f9af3c6432ce3336c6eea448bd068c4a907f8ceb218a0f519a8b4b5fad2951bc5cc5e697b33bd5995f0aba1973d1bdd39af5b5857c787f64f25328c931c1e41c76d29dd7cd95e04cb30fd20bcb47096b2d4b76588ed14d1a6f17a5c42915f5f0d6849d4a50f2b2e866eee44d8fa35d6e77b0faebe541a4d828539b1ab1842e6f74a09e5f8a199a787be32002b9e1f4644f80f5ada868673298d646328e692c2290924fbc8b0929d5b96d14d8b378bbc45463cb495ad9bb524601f38c91b90755900f95c956fae48fe88ab7738d4e242a25f83c1d4a3a7c6a80fe4d04ffa92b2c187099851df45f442ee72133c5193d47d0229de90a70988b35acc1c89ac37af9ad43da45d75a92548ad9a0de11e6e98b57fc72d55db49e3e5f47f4bc88b88dc07defcf4c9cbdbb0532a333eedfb34776805b6f7dd3452bc9b980859291f97fa01fc547c019f51750a62657b20a02945593dac2b4fe10957bc2f248648ee36e1b9acf54184630ee1cb3627707901c8345bb82868d54667955e25fa8961f3241fa0d8d9b3502a5d3315800bb7b2a82d6ccee765104ffb31bd83f55e7c3a98495708c179bdb71f0ba19cef9ea29c5bcfbffc1a1d1dfbbe3658f3c85fb09e80fdc2161253ed46a0d80d2efb4d3587f24b4e07d9f5024a5ba459b0bb91117c453832d1a66c9bfbd2449662c244800c3ad52ae1f0e48338166eb4ea98a3d988889ce475ac698"));
	}
	
	public static void showPersonalDetail(){
		System.err.println(iEncrypt.decrypt("b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acdaa88ad8137fd10f81905e71a28bc7842d948ef17b2a54d4552c691a95e1f1ca080042b678a67e5739bbfdc9a007310c60953888d89d1ce218f2a21c676a350c4f24f551031be04f921568846e4b69ef64c6b858491d508d6075f465b7017413c20b5348b5a479207d8b5e51fc59f4fcccf07f680ce1fb66ef5cd6a5653e4240b194ea5e8244cc6a24374d210fd03bc9af3746072e45bc607fa77957b1d5860a1bdfa2d0304b6ef4751e255706e76cb13360a517df6e6fb13446727f233b1a71a1a94d2b60e469e66ec517f503ff4989d64e377f87d30c39d9b7f67a5092e36653d47e72ae75119d97099bae40e24b807691ed4bd46a260cd6407d1937c376062e06b7bc1885942b82bdb7add3f88d61bfcdccfc982e83663ae68e4824f3e3b73e98ddd31a89c7d675baa12e4eed7b73f8c583ad7fdaa686b57f2b2dbd055cc9a56b6c48bb342fdb23ac7d1b0970ace1cbf89f2a3e8bc19e1f6369113bc6ee6566d1971c931380fd0a13cef7c4be4287d2505966ba434eb9dd7fedd96668ccb68c87e1782734a38309aa79af57d4edb539dbaca03ecfffc606e6c875a5e20b9ffa6a90f5a98ce451bdd0a869d8c3c18ba909b96a438e40a86a60df612cfdbd46612218ef7c85c0dc43d9e226e6a1666448adac829d113839afe50a2d73d01fb583d64ec5d5e2082b1026fd1fc29c7d6216bf0a2d6f7c5aa5034eb4fd115188ec026175415174c9de961ce6a748e28929777e98f09a0ff94d0523913ba7f98cc51c1074389c5d580659799135c3d03a9770a7093d0f74f92fc38d69766d26356236aba2b3e2cbb6e63b41ead0c76712fe0843e614b042eeedf1d901fe2eccfd2aaa5c58fee5ccc795ee77dd5726f21792bdf72a0b1c2a8d0cb3dabc8e4f22446c02ac6750a40ea1649f14be399144a78f4e041c84f00d4dd5bff3c6445b09414454dd374e18596f02859af7eccf1fd4c539fad2864a77a1836abbcd61e4451eb2d0ffdcfb5d5b2cdfdda6dc1c616f38956f633113e1f33e2089e09b5d86998261d9b8ee8c8518ce2d79e1854a1962ede9e72f09bfadf4ad788d88372e256a96c2df5232f6da4f88ea7082fd5308103c2d5e0047565421b3651862f417dbe2114af3f17b3f844d28c5200f41de38e9add1625f4166b6789f616f239cd4a432fc72c8d91c31484828675f37c06e521b1176e08865557553e7edb0483ed99f6cc670067c0e424d41ebe85cc91983d6650659a196a33ee7b97acd68b5e6cb18db0b0924559137c4559a30c2744952669e2db64defbc672ed0845c523641dff126be6c13d6c0bed6a500b5a2816e6084c46dfc49243a160a85e11678f9eb6a79b9e44c1a34f707400129759a5824e1bb98974e71e20322a91c37ab637786f636aa7c70811a2dc1b3750b1846693ef520814003d0c35510c2ef46c258f0b510539e555f0188d1837e5f1d9758790033b37aeb9836f8a0d0bfc9f877cd407822a4e23bd832e3c7cafb2f32179cbce28f4418d7a7"));
	}
	
	public static void saveCustomerIntroduce(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerIntroduce", "2222222222222222222222222222222222222222");
		System.err.println(JsonEntityTransform.Object2Json(paramMap));
		System.err.println("?2y5QfvAy="+iEncrypt.encrypt(JsonEntityTransform.Object2Json(paramMap))+"&hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(paramMap),"UTF-8"));
	}
	
	public static void updateCustomerInfo(){
		CustomerBaseMsgEntity entity = new CustomerBaseMsgEntity();
		entity.setUid("52c91066-8365-45eb-b28c-e1de19dda979");
		entity.setCustomerBirthday(new Date());
		entity.setCustomerEmail("778924232@qq.com");
		entity.setResideAddr("天通苑北二区");
		entity.setCustomerEdu(CustomerEduEnum.PRIMARY.getCode());
		entity.setCustomerJob("程序员");
		entity.setCustomerSex(CustomerSexEnum.GIRL.getCode());
		System.err.println(JsonEntityTransform.Object2Json(entity));
		System.err.println("?2y5QfvAy="+iEncrypt.encrypt(JsonEntityTransform.Object2Json(entity))+"&hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(entity),"UTF-8"));
	}
	
	public static void getUserCardInfo(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uid", "4b37c562-6ce9-7065-e9e6-aab2182cdbc4");
		paramMap.put("systemSource", "dz");
		System.err.println(Check.encodeUrl(CryptAES.AES_Encrypt("8w091ql5l2tt6qxj", JsonEntityTransform.Object2Json(paramMap))));
	}
	

	public static void main(String[] args) {
		// houseRoomList();
		// showPersonalDetail();
		// saveCustomerIntroduce();
		// updateCustomerInfo();
		houseRoomList();
	}
	
}