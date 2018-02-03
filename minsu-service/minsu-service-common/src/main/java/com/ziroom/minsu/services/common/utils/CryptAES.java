package com.ziroom.minsu.services.common.utils;

import com.asura.framework.utils.LogUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;

/**
 * ClassName: CryptAES
 * 
 * @Description: AES加密，解�?
 * @author liuxm
 * @date 2014年11月21日
 */
public class CryptAES {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CryptAES.class);

	
	private static final String AESTYPE = "AES/ECB/PKCS5Padding";

	/**
	 * @Description: AES 加密
	 * @param  keyStr 密钥
	 * @param  plainText 加密数据
	 * @return String 加密完数据
	 * @throws
	 * @author liuxm
	 * @date 2014年11月21日
	 */
	public static String AES_Encrypt(String keyStr, String plainText) {
		byte[] encrypt = null;
		try {
			Key key = generateKey(keyStr);
			Cipher cipher = Cipher.getInstance(AESTYPE);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encrypt = cipher.doFinal(plainText.getBytes());
		} catch (Exception e) {
			LogUtil.error(LOGGER,"error:{}",e);
		}
		return new String(Base64.encodeBase64(encrypt));
	}

	/**
	 * @Description: 解密
	 * @param  keyStr 密钥
	 * @param  encryptData 解密数据 
	 * @return String  
	 * @throws
	 * @author liuxm
	 * @date 2014年11月21日
	 */
	public static String AES_Decrypt(String keyStr, String encryptData) {
		byte[] decrypt = null;
		try {
			Key key = generateKey(keyStr);
			Cipher cipher = Cipher.getInstance(AESTYPE);
			cipher.init(Cipher.DECRYPT_MODE, key);
			decrypt = cipher
					.doFinal(Base64.decodeBase64(encryptData.getBytes()));
		} catch (Exception e) {
			;
		}
		return new String(decrypt).trim();
	}
	
	/**
	 * 
	 * AES加密后将byte数组转换为表示16进制值的字符串
	 *
	 * @author zhangyl
	 * @created 2017年6月30日 下午5:01:22
	 *
	 * @param keyStr
	 * @param plainText
	 * @return
	 */
	public static String AES_Encrypt_Hex(String keyStr, String plainText){
		byte[] encrypt = null;
		try {
			Key key = generateKey(keyStr);
			Cipher cipher = Cipher.getInstance(AESTYPE);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encrypt = cipher.doFinal(plainText.getBytes());
		} catch (Exception e) {
			LogUtil.error(LOGGER,"error:{}",e);
		}
		return DESPlus.byteArr2HexStr(encrypt);
	}
	
	/**
	 * 
	 * 先16进制值的字符串转换为byte数组后AES解密
	 *
	 * @author zhangyl
	 * @created 2017年6月30日 下午5:01:18
	 *
	 * @param keyStr
	 * @param encryptData
	 * @return
	 */
	public static String AES_Decrypt_Hex(String keyStr, String encryptData){
		byte[] decrypt = null;
		try {
			Key key = generateKey(keyStr);
			Cipher cipher = Cipher.getInstance(AESTYPE);
			cipher.init(Cipher.DECRYPT_MODE, key);
					
			decrypt = cipher
			.doFinal(DESPlus.hexStr2ByteArr(encryptData));
		} catch (Exception e) {
			;
		}
		return new String(decrypt).trim();
	}
	
	/**
	 * @Description: 封装KEY值
	 * @param key
	 * @param @throws Exception   
	 * @return Key  
	 * @throws
	 * @author liuxm
	 * @date 2014年11月21日
	 */
	private static Key generateKey(String key) throws Exception {
		
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
			return keySpec;
		} catch (Exception e) {
			LogUtil.error(LOGGER,"error:{}",e);
			throw e;
		}

	}

	public static void main(String[] args) {
		String shengchanKey = "8w091ql5l2tt6qxj";

		try {
			String meString = "RHgCc7gBWa5jwCks2ghl3pdTrsXyrFGs9PuHVce6%2FIX5Y1tkeWCo7ol%2F117M4GQQj%2Fn9BDqWUqqXvZOxX6p%2FihE7p9K9MAUZyuy2dHicVbIRBKftTdROnAuVnGa0BEjYTtrCN6OjOCKIFmRyCVBLd0cTCfIUKv4AtREq7Tfol1UNsvknJMDGiOxwhGFx0GZ3w%2B%2BhwWyV%2B2SVSepWnDI4klUNSy49L9J64L0ZD4c2ggOvEBTv%2FewByVxmOsPq3%2BEmw5UxMoAQW4R1O2ifly5%2B4rGaPT1zGh9h6sT6wcNMZmZFj89iZ0x6gXAqFU0LS2bWclZov7%2BG%2B1Ypa5mWuks8VrsYx9dw2noA6rIKzXd7K0GJ5pQtgbIy5HLaoFQ532zJYaWdokvZid%2FHeRrR5yO4dvhkfILYLQMG91xRJGGoVvu2ylAEPD4xtTVs1PLpYqwANljf928SE608Po%2FTru%2FAafEP8BCqnmYZRgvGXckhYACmOIisiHPcXx%2FsE8TUJHFq2de6Jq%2B2NpOhXqjQwByS%2B2Y59EpwIDDGJ3kv1U%2BQMZxnn%2BhAWhoFRbk0sJWPz297hnTB8ljutosgYg67nX4lzp8BwbSPs6w3VRZkePoSysCYUrwveV1MUVJhwFbCcxOiioGz2Qa%2F3Y6F4WmTVwKwEg4n13LSJuDzdyUE%2BQa71fSyJlZnz4Va9enEwZr%2BTtKKxEhXM79yyiV3wwubvUKAaynwpJI2peXYbup3eKJwmwPCJVNBLP8twn7jpq%2FyAiEilx7BABmTDUQR%2FNzz9GcklwjVX9BT%2BtXMhQv751o4NYrn27QLzr185rNRooYr3nAwldjZJbi8FzUd3IllRQIt049jxNktUN5lHpHVcaw83NDUEYu3Ly4TAsX%2Fwe16aKlAe9FpgYC6%2B0ExQvCzJFuhvw%3D%3D";
			String newEncryption = URLDecoder.decode(meString, "UTF-8");
			String payBackJson = CryptAES.AES_Decrypt(shengchanKey, newEncryption);
			System.err.println("解密后json串：");
			System.err.println(payBackJson);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}


		{
			String json = "{\"BizCode\":\"1607213T8D4Q45213632\",\"Chanal\":\"42\",\"audit_flag\":4,\"bill_type\":\"D\",\"bus_id\":\"FK161009446J3Y1W120400_1\",\"call_url\":\"http://bnb.api.ziroom.com/finance/ee5f86/financeCallBack\",\"company_code\":\"8013\",\"creator\":\"001\",\"customer_name\":\"张楠\",\"customer_phone\":\"18201018026\",\"data_sources\":\"dz\",\"mark_collect_code\":\"zrk\",\"order_code\":\"1607213T8D4Q45213632\",\"out_trade_no\":\"WXAPP146911805941711995796801300\",\"panyment_type_code\":\"ylfh\",\"pay_flag\":1,\"pay_time\":1475986025725,\"pay_total_fee\":\"2907.5\",\"pay_vouchers_detail\":[{\"cost_code\":\"txfy\",\"total_fee\":-1000}],\"payorderCode_origin\":\"80007146911805937711995795\",\"recieved_account\":\"1\",\"uid\":\"ba0bcf76-1040-489c-acec-bd38b9823e92\"}";
			try {
				String str = URLEncoder.encode(CryptAES.AES_Encrypt(shengchanKey, json), "UTF-8");
				System.err.println("加密后json串：");
				System.err.println(str);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		{
			String json = "{\"BizCode\":\"1610016X0R326W202510\",\"Chanal\":\"42\",\"audit_flag\":4,\"bill_type\":\"D\",\"bus_id\":\"FK161006X84ASGOY135845_1\",\"call_url\":\"http://bnb.api.ziroom.com/finance/ee5f86/financeCallBack\",\"company_code\":\"8013\",\"creator\":\"001\",\"customer_name\":\"吴桐\",\"customer_phone\":\"15590343399\",\"data_sources\":\"dz\",\"mark_collect_code\":\"zrk\",\"order_code\":\"1610016X0R326W202510\",\"out_trade_no\":\"WXAPP147532492869110000000801300\",\"panyment_type_code\":\"ylfh\",\"pay_flag\":1,\"pay_time\":1475734024713,\"pay_total_fee\":\"1736.1\",\"pay_vouchers_detail\":[{\"cost_code\":\"txfy\",\"total_fee\":-430}],\"payorderCode_origin\":\"80007147532492865310000000\",\"recieved_account\":\"1\",\"uid\":\"2cb9cc77-4d11-422e-ac6d-bb4b0126ef82\"}";
			try {
				String str = URLEncoder.encode(CryptAES.AES_Encrypt(shengchanKey, json), "UTF-8");
				System.err.println("加密后json串：");
				System.err.println(str);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		{
			String json = "{\"BizCode\":\"1608187F3S307S112258\",\"Chanal\":\"42\",\"audit_flag\":4,\"bill_type\":\"D\",\"bus_id\":\"FK1609217MT7X56P143614_1\",\"call_url\":\"http://bnb.api.ziroom.com/finance/ee5f86/financeCallBack\",\"company_code\":\"8013\",\"creator\":\"001\",\"customer_name\":\"邹云\",\"customer_phone\":\"18910137752\",\"data_sources\":\"dz\",\"mark_collect_code\":\"zrk\",\"order_code\":\"1608187F3S307S112258\",\"out_trade_no\":\"WXAPP147149085918210000000801300\",\"panyment_type_code\":\"ylfh\",\"pay_flag\":1,\"pay_time\":1474439824858,\"pay_total_fee\":\"8773.1\",\"pay_vouchers_detail\":[{\"cost_code\":\"txfy\",\"total_fee\":-500}],\"payorderCode_origin\":\"80007147149066072110000000\",\"recieved_account\":\"1\",\"uid\":\"ad314b6c-5fdb-edb1-8428-676266980fed\"}";
			try {
				String str = URLEncoder.encode(CryptAES.AES_Encrypt(shengchanKey, json), "UTF-8");
				System.err.println("加密后json串：");
				System.err.println(str);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

}