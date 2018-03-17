package com.ziroom.minsu.mapp.customer.service;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.mapp.common.constant.ConstDef;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.common.utils.CryptAES;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.common.utils.ValueUtil;


/**
 * <p>银行卡列表</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/26.
 * @version 1.0
 * @since 1.0
 */
@Service("m.cardService")
public class CardService {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CardService.class);
    
    private static final String ENCRYPTION = "encryption";
    
    /**
     * 编码格式
     */
    private static final String ENCODING = "UTF-8";
    
    private static final String SUCCESS = "SUCCESS";

    @Value("#{'${CARD_LIST_URL}'.trim()}")
    private String CARD_LIST_URL;

    @Value("#{'${CUSTOMER_URL}'.trim()}")
    private String CUSTOMER_URL;

    @Value("#{'${CUSTOMER_DETAIL_URL}'.trim()}")
    private String CUSTOMER_DETAIL_URL;
    
    @Value("#{'${ACCOUNT.ACCOUNT_KEY}'.trim()}")
    private String ACCOUNT_KEY;
    
    @Value("#{'${ACCOUNT.SYSTEM_SOURCE}'.trim()}")
    private String SYSTEM_SOURCE;
    
    @Value("#{'${ACCOUNT.BANK_LIST_URL}'.trim()}")
    private String BANK_LIST_URL;
    
    @Value("#{'${ACCOUNT.BANKCARD_DETAIL_URL}'.trim()}")
    private String BANKCARD_DETAIL_URL;
    
    @Value("#{'${ACCOUNT.BIND_BANKCARD_URL}'.trim()}")
    private String BIND_BANKCARD_URL;
    
    @Value("#{'${ACCOUNT.UNBIND_BANKCARD_URL}'.trim()}")
    private String UNBIND_BANKCARD_URL;

    @Resource(name = "basedata.confCityService")
    private ConfCityService confCityService;


    /**
     * 获取城市列表
     * @author afi
     * @return
     */
    public List<Map<String,String>> getCityList(){
        String resultJson = confCityService.getAllCityList();
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        List<Map<String,String>> cityList = resultDto.parseData("list", new TypeReference<List<Map<String,String>>>() {});
        return cityList;
    }

    /**
     * 获取用户的银行卡列表
     * @author afi
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Map<String,String>> getCardList(){
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("systemSource", SYSTEM_SOURCE);
    	
		List<Map<String,String>> bankList = null;
		Map<?, ?> rst = this.invokeInterface(BANK_LIST_URL, paramMap);
		Object status = rst.get("status");
        Object bankInfo = rst.get("data");
        if (!Check.NuNObj(status) && SUCCESS.equals(status) && !Check.NuNObj(bankInfo)){
            bankList = (List<Map<String,String>>) bankInfo;
        }
        return bankList;
    }

    /**
     * 获取用户的银行卡列表
     * @author afi
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Map<String,String>> getCardList(String cardCode) {
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("systemSource", SYSTEM_SOURCE);
    	
        List<Map<String,String>> bankList = null;
        Map<?, ?> rst = this.invokeInterface(BANK_LIST_URL, paramMap);
        Object status = rst.get("status");
        Object bankInfo = rst.get("data");
        if (!Check.NuNObj(status) && SUCCESS.equals(status) && !Check.NuNObj(bankInfo)){
            bankList = (List<Map<String,String>>) bankInfo;
            if (Check.NuNCollection(bankList) || Check.NuNStr(cardCode)){
                return bankList;
            }
            for (Map<String, String> map : bankList) {
                if (cardCode.equals(map.get("bankCode"))){
                    map.put("falg","1");
                }else {
                    map.put("falg","0");
                }
            }
        }
        return bankList;
    }


    /**
     * 获取用户的银行卡信息
     * @author afi
     * @param uid
     * @return
     */
    @Deprecated
    public boolean saveCard(String uid,String cardName,String bankcardNo,String cardCity){
        String url = CUSTOMER_URL + "/api/add_pay_msg.php";

        Map par = new HashMap();
        par.put("uid",uid);
        par.put("card_name",cardName);
        par.put("card_code",bankcardNo);
        par.put("province",cardCity);
        par.put("is_del","0");

        String json = CloseableHttpsUtil.sendFormPost(url, par);
        Map rst = JsonEntityTransform.json2Map(json);
        Object status = rst.get("status");
        if (!Check.NuNObj(status) && ConstDef.CUSTOMER_RST_SUCCESS.equals(ValueUtil.getStrValue(status))){
            return true;
        }else {
            return false;
        }
    }
    
    /**
     * 获取用户的银行卡信息
     * @author afi
     * @param uid
     * @return
     */
    public Map<?, ?> getUserCardInfo(String uid){
    	if(Check.NuNStr(uid)){
    		return null;
    	}
    	
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("uid", uid);
    	paramMap.put("systemSource", SYSTEM_SOURCE);
    	
    	Map<?, ?> rst = this.invokeInterface(BANKCARD_DETAIL_URL, paramMap);
    	Object status = rst.get("status");
    	Object data = rst.get("data");
    	if (!Check.NuNObj(status) && SUCCESS.equals(status) && !Check.NuNObj(data) && data instanceof List){
    		List<?> bankCardList = (List<?>)data;
    		if (!Check.NuNCollection(bankCardList)) {
				if (bankCardList.size() == 1) {
					return (Map<?, ?>) bankCardList.get(0);
				} else {
					LogUtil.error(logger, "[账户系统]用户绑定银行卡数量异常,size={}", bankCardList.size());
				}
			}
    	}
    	return null;
    }
    
    /**
     * 绑定银行卡
     *
     * @author liujun
     * @created 2016年10月9日
     *
     * @param null
     */
    public Map<?, ?> bindBankCard(Map<String, Object> paramMap) {
    	if (validateParam(paramMap)) {
    		return this.invokeInterface(BIND_BANKCARD_URL, paramMap);
    	}
    	LogUtil.info(logger, "bindBankCard paramMap is invalid", JsonEntityTransform.Object2Json(paramMap));
    	return null;
    }

    /**
	 * 解绑银行卡
	 *
	 * @author liujun
	 * @created 2016年10月9日
	 *
	 * @param null
	 */
	public Map<?, ?> unbindBankCard(Map<String, Object> paramMap) {
		if (validateParam(paramMap)) {
	        return this.invokeInterface(UNBIND_BANKCARD_URL, paramMap);
		}
		LogUtil.info(logger, "unbindBankCard paramMap is invalid", JsonEntityTransform.Object2Json(paramMap));
		return null;
	}
	
	/**
	 * 校验参数是否有效
	 *
	 * @author liujun
	 * @created 2016年10月9日
	 *
	 * @param paramMap
	 * @return
	 */
	private boolean validateParam(Map<String, Object> paramMap) {
		if(Check.NuNObj(paramMap.get("uid"))){
			LogUtil.info(logger, "uid is invalid, uid={}", paramMap.get("uid"));
			return false;
		}
		
		Object bankCode = paramMap.get("bankCode");
		if(Check.NuNObj(bankCode)){
			LogUtil.info(logger, "bankCode is invalid, bankCode={}", bankCode);
			return false;
		}
		
		String bankCardNo = (String) paramMap.get("bankCardNo");
		if(Check.NuNObj(bankCardNo) || !StringUtils.isNumeric(bankCardNo)){
			LogUtil.info(logger, "bankCardNo is invalid, bankCardNo={}", bankCardNo);
			return false;
		}
		
		return true;
	}
	
	/**
	 * luhm校验
	 * 1、从卡号最后一位数字开始,偶数位乘以2,如果乘以2的结果是两位数，将结果减去9。
     * 2、把所有数字相加,得到总和。
     * 3、如果信用卡号码是合法的，总和可以被10整除。
	 *
	 * @author liujun
	 * @created 2016年10月12日
	 *
	 * @param bankCardNo
	 * @return
	 */
	public static boolean luhm(String bankCardNo) {
 		if (Check.NuNStr(bankCardNo)) {
			return false;
		}
 		
 		if (!StringUtils.isNumeric(bankCardNo)){
 			return false;
 		}
 		
 		String[] arrays = bankCardNo.split("");
 		arrays = Arrays.copyOfRange(arrays, 1, arrays.length);
 		List<String> list = Arrays.asList(arrays);
 		Collections.reverse(list);
 		int total = 0;
 		for (int i = 0; i < list.size(); i++) {
			if ((i + 1) % 2 == 0) {
 				int temp = Integer.valueOf(list.get(i)) << 1;
 				total += temp > 9 ? temp - 9 : temp;
			} else {
				total += Integer.valueOf(list.get(i));
			}
		}
		return total % 10 == 0;
 	}
	
	/**
	 * 调用接口
	 *
	 * @author liujun
	 * @created 2016年10月9日
	 *
	 * @param url
	 * @param paramMap
	 */
	public Map<?, ?> invokeInterface(String url, Map<String, Object> paramMap) {
		StringBuilder sb = new StringBuilder(url);
        sb.append("?").append(ENCRYPTION).append("=");
        try {
			sb.append(URLEncoder.encode(CryptAES.AES_Encrypt(ACCOUNT_KEY, JsonEntityTransform.Object2Json(paramMap)), ENCODING));
		} catch (UnsupportedEncodingException e) {
			LogUtil.error(logger, "encode param failed:{}", e);
			return null;
		}
        
        String json = CloseableHttpsUtil.sendPost(sb.toString());
        return JsonEntityTransform.json2Map(json);
	}
	
	/**
	 * 调用接口状态
	 *
	 * @author liujun
	 * @created 2016年10月9日
	 *
	 * @param resultMap 接口返回集合
	 * @return true:成功 false:失败
	 */
	public boolean invokeStatus(Map<?, ?> resultMap) {
		if (!Check.NuNMap(resultMap)) {
			Object status = resultMap.get("status");
			if (!Check.NuNObj(status) && SUCCESS.equals(status)){
				return true;
			}
		}
		LogUtil.info(logger, "invoke failed,param:{}", JsonEntityTransform.Object2Json(resultMap));
		return false;
	}

    /**
     * 兼容获取用户的信息
     * @author afi
     * @param cert
     * @return
     */
    @Deprecated
    public static Map getCent(Object cert){
        if (!Check.NuNObj(cert) && (cert instanceof Collection)) {
            List<HashMap> list = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(cert), HashMap.class);
            if(!Check.NuNCollection(list)){
                return list.get(0);
            }
        }
        return null;
    }

}
