package com.ziroom.minsu.api.customer.service;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.ziroom.minsu.api.customer.entity.AccountInfoVo;
import com.ziroom.minsu.api.customer.entity.BankInfoVo;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.common.utils.CryptAES;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;


/**
 * <p>银行卡列表</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/6.
 * @version 1.0
 * @since 1.0
 */
@Service("api.cardService")
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


    @Value("#{'${CUSTOMER_BASE_URL}'.trim()}")
    private String CUSTOMER_BASE_URL;

    @Value("#{'${CUSTOMER_URL}'.trim()}")
    private String CUSTOMER_URL;

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
     * 填充银行卡信息
     * @param bankMap
     * @param accountInfoVo
     * @param realName
     */
    public void fillCardInfo(Map<?, ?> bankMap, AccountInfoVo accountInfoVo, String realName){
        if (Check.NuNObj(accountInfoVo)){
            return;
        }
        BankInfoVo bankInfoVo = this.getBankInfoOnly(bankMap,realName);
        if (!Check.NuNMap(bankMap)){
            accountInfoVo.setHaveBank(YesOrNoEnum.YES.getCode());
        }else {
            accountInfoVo.setHaveBank(YesOrNoEnum.NO.getCode());
        }
        accountInfoVo.setBankInfo(bankInfoVo);
    }

    /**
     *
     * @param bankMap
     * @param realName
     * @return
     */
    public BankInfoVo getBankInfo(Map<?, ?> bankMap, String realName){
        BankInfoVo bankInfoVo = new BankInfoVo();
        //填充银行卡信息
        bankInfoVo.setAccountName(realName);
        if (!Check.NuNMap(bankMap)){
        	String accountName = ValueUtil.getStrValue(bankMap.get("accountName"));
        	if(!Check.NuNStr(accountName)){
        		bankInfoVo.setAccountName(accountName);
        	}
        	bankInfoVo.setUid(ValueUtil.getStrValue(bankMap.get("uid")));
            bankInfoVo.setBankArea(ValueUtil.getStrValue(bankMap.get("bankArea")));
            bankInfoVo.setBankCode(ValueUtil.getintValue(bankMap.get("bankCode")));
            bankInfoVo.setBankName(ValueUtil.getStrValue(bankMap.get("bankName")));
            bankInfoVo.setBankCardNo(ValueUtil.getStrValue(bankMap.get("bankCardNo")));
        }
        //城市列表
        bankInfoVo.setCityList(this.getCityList());
        //银行卡列表
        bankInfoVo.setBankList(this.getCardList());
        return bankInfoVo;
    }

    /**
     *
     * @param bankMap
     * @param realName
     * @return
     */
    public BankInfoVo getBankInfoOnly(Map<?, ?> bankMap, String realName){
    	BankInfoVo bankInfoVo = new BankInfoVo();
        //填充银行卡信息
        bankInfoVo.setAccountName(realName);
        if (!Check.NuNMap(bankMap)){
        	String accountName = ValueUtil.getStrValue(bankMap.get("accountName"));
        	if(!Check.NuNStr(accountName)){
        		bankInfoVo.setAccountName(accountName);
        	}
        	bankInfoVo.setUid(ValueUtil.getStrValue(bankMap.get("uid")));
            bankInfoVo.setBankArea(ValueUtil.getStrValue(bankMap.get("bankArea")));
            bankInfoVo.setBankCode(ValueUtil.getintValue(bankMap.get("bankCode")));
            bankInfoVo.setBankName(ValueUtil.getStrValue(bankMap.get("bankName")));
            bankInfoVo.setBankCardNo(ValueUtil.getStrValue(bankMap.get("bankCardNo")));
        }
        return bankInfoVo;
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
					LogUtil.error(logger, "[账户系统]用户绑定银行卡数量等于{},超过限制", bankCardList.size());
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
			LogUtil.info(logger, "uid is invalid", paramMap.get("uid"));
			return false;
		}
		
		Object bankCode = paramMap.get("bankCode");
		if(Check.NuNObj(bankCode)){
			LogUtil.info(logger, "bankCode is invalid", bankCode);
			return false;
		}
		
		String bankCardNo = (String) paramMap.get("bankCardNo");
		if(Check.NuNObj(bankCardNo) || !StringUtils.isNumeric(bankCardNo)){
			LogUtil.info(logger, "bankCardNo is invalid", bankCardNo);
			return false;
		}
		
		return true;
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
}
