/**
 * @FileName: CustomerInfoServiceProxy.java
 * @Package com.ziroom.minsu.services.customer.proxy
 * 
 * @author bushujie
 * @created 2016年4月25日 下午12:06:11
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerInvoiceTitleEntity;
import com.ziroom.minsu.entity.customer.CustomerOperHistoryEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.common.utils.CryptAES;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.constant.CustomerMessageConst;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.customer.dto.CustomerFieldAuditVo;
import com.ziroom.minsu.services.customer.dto.SmsAuthLogDto;
import com.ziroom.minsu.services.customer.dto.StaticsCusBaseReqDto;
import com.ziroom.minsu.services.customer.entity.CustomerSearchVo;
import com.ziroom.minsu.services.customer.entity.TelExtensionVo;
import com.ziroom.minsu.services.customer.service.CustomerBankCardServiceImpl;
import com.ziroom.minsu.services.customer.service.CustomerBaseMsgServiceImpl;
import com.ziroom.minsu.services.customer.service.CustomerInvoiceTitleServiceImpl;
import com.ziroom.minsu.services.customer.service.CustomerOperHistoryImpl;
import com.ziroom.minsu.services.customer.service.CustomerPicMsgServiceImpl;
import com.ziroom.minsu.services.customer.service.CustomerRoleServiceImpl;
import com.ziroom.minsu.services.customer.service.SmsAuthLogServiceImpl;
import com.ziroom.minsu.services.customer.service.TelExtensionServiceImpl;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.BankNameEnum;
import com.ziroom.minsu.valenum.customer.CusotmerAuthEnum;
import com.ziroom.minsu.valenum.customer.ExtStatusEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>客户中心相关业务接口实现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Component("customer.customerInfoServiceProxy")
public class CustomerInfoServiceProxy implements CustomerInfoService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerInfoServiceProxy.class);

    private static final String ENCRYPTION = "encryption";
    
    /**
     * 编码格式
     */
    private static final String ENCODING = "UTF-8";
    
    private static final String SUCCESS = "SUCCESS";

	@Autowired
	private RedisOperations redisOperations;
	
	@Resource(name="customer.customerBaseMsgServiceImpl")
	private CustomerBaseMsgServiceImpl customerBaseMsgServiceImpl;

	@Resource(name="customer.messageSource")
	private MessageSource messageSource;

    @Resource(name = "customer.customerRoleServiceImpl")
    private CustomerRoleServiceImpl customerRoleService;

	@Resource(name="customer.customerInvoiceTitleServiceImpl")
	private CustomerInvoiceTitleServiceImpl customerInvoiceTitleServiceImpl;

	@Resource(name="customer.customerBankCardServiceImpl")
	private CustomerBankCardServiceImpl customerBankCardServiceImpl;

    @Resource(name="customer.telExtensionServiceImpl")
    private TelExtensionServiceImpl telExtensionService;

    @Resource(name="customer.callCrmServiceProxy")
    private CallCrmServiceProxy callCrmServiceProxy;

	@Value("#{'${CUSTOMER_BANK_NAME_URL}'.trim()}")
	private String CUSTOMER_BANK_NAME_URL;

    @Value("#{'${ACCOUNT.ACCOUNT_KEY}'.trim()}")
    private String ACCOUNT_KEY;
    
    @Value("#{'${ACCOUNT.SYSTEM_SOURCE}'.trim()}")
    private String SYSTEM_SOURCE;
    
    @Value("#{'${ACCOUNT.BANKCARD_DETAIL_URL}'.trim()}")
    private String BANKCARD_DETAIL_URL;

	@Resource(name = "customer.smsAuthLogServiceImpl")
	private SmsAuthLogServiceImpl smsAuthLogServiceImpl;
	
	
	@Resource(name = "customer.customerOperHistoryImpl")
	private CustomerOperHistoryImpl customerOperHistoryImpl;
	

	@Resource(name = "customer.customerPicMsgServiceImpl")
	private CustomerPicMsgServiceImpl customerPicMsgServiceImpl;

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoService#insertCustomerInfo(java.lang.String)
	 */
	@Override
	public String insertCustomerInfo(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try{
			CustomerBaseMsgEntity customerBaseMsgEntity=JsonEntityTransform.json2Object(paramJson, CustomerBaseMsgEntity.class);
			//判断是否存在uid
			if(Check.NuNStr(customerBaseMsgEntity.getUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
				return dto.toJsonString();
			}
			customerBaseMsgServiceImpl.insertCustomerInfo(customerBaseMsgEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


    /**
     * 更新房东的电话信息
     * @author afi
     * @param phone
     * @param uid
     * @return
     */
    @Override
    public String updateLandPhone(String phone,String uid, String areaCode){
        DataTransferObject dto = new DataTransferObject();
        try{
            //判断是否存在uid
            if(Check.NuNStr(uid)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
                return dto.toJsonString();
            }
            //判断是否存在uid
            if(Check.NuNStr(phone)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.PHONE_NULL_ERROR));
                return dto.toJsonString();
            }
            CustomerBaseMsgEntity customerBaseMsgEntity=customerBaseMsgServiceImpl.getCustomerBaseMsgEntitybyUid(uid);
            if (Check.NuNObj(customerBaseMsgEntity)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.USER_NULL_ERROR));
                return dto.toJsonString();
            }
            TelExtensionVo extensionVo = telExtensionService.getExtensionVoByUid(uid);
            if (!Check.NuNObj(extensionVo) && extensionVo.getExtStatus() == ExtStatusEnum.HAS_OK.getCode()){
                //重新绑定400电话
                extensionVo.setLandPhone(phone);
                callCrmServiceProxy.postDoChange(uid,uid,extensionVo);
            }
            CustomerBaseMsgEntity saveInfo = new CustomerBaseMsgEntity();
            saveInfo.setUid(uid);
            saveInfo.setCustomerMobile(phone);
			if (!Check.NuNStr(areaCode)) {
				saveInfo.setCountryCode(areaCode);
			}
			saveInfo.setIsContactAuth(CusotmerAuthEnum.IsAuth.getCode());
            customerBaseMsgServiceImpl.updateCustomerInfo(saveInfo);
            //删除redis
            try {
                redisOperations.del(RedisKeyConst.getCutomerKey(uid));
            } catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
            }

        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }
	/**
	 * 更新用户信息，更新redis
	 */
	@Override
	public String updateCustomerInfo(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try{
			CustomerBaseMsgEntity customerBaseMsgEntity=JsonEntityTransform.json2Object(paramJson, CustomerBaseMsgEntity.class);
			//判断是否存在uid
			if(Check.NuNStr(customerBaseMsgEntity.getUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
				return dto.toJsonString();
			}
			long startTime=System.currentTimeMillis();
			customerBaseMsgServiceImpl.updateCustomerInfo(customerBaseMsgEntity);
			long time=System.currentTimeMillis()-startTime;
			LogUtil.info(LOGGER,"更新客户信息用时："+time);
			long startTime1=System.currentTimeMillis();
			//删除redis
			try {
				redisOperations.del(RedisKeyConst.getCutomerKey(customerBaseMsgEntity.getUid()));
			} catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			long time1=System.currentTimeMillis()-startTime1;
			LogUtil.info(LOGGER,"redis删除用户信息缓存用时："+time1);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoService#insertCustomerInvoice(java.lang.String)
	 */
	@Override
	public String insertCustomerInvoice(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try{
			CustomerInvoiceTitleEntity customerInvoiceTitleEntity=JsonEntityTransform.json2Object(paramJson, CustomerInvoiceTitleEntity.class);
			//判断是否存在uid
			if(Check.NuNStr(customerInvoiceTitleEntity.getUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
				return dto.toJsonString();
			}
			customerInvoiceTitleServiceImpl.insertCustomerInvoiceTitle(customerInvoiceTitleEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoService#insertCustomerBankcard(java.lang.String)
	 */
	@Override
	public String insertCustomerBankcard(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try{
			CustomerBankCardMsgEntity customerBankCardMsgEntity=JsonEntityTransform.json2Object(paramJson, CustomerBankCardMsgEntity.class);
			//判断是否存在uid
			if(Check.NuNStr(customerBankCardMsgEntity.getUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
				return dto.toJsonString();
			}
			customerBankCardServiceImpl.insertCustomerBankCard(customerBankCardMsgEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


    /**
     * 从数据库获取银行卡信息
     * @author afi
     * @param uid
     * @return
     */
    @Override
    public String getCustomerBankCardDbByUid(String uid){
        DataTransferObject dto = new DataTransferObject();
        try{
            //判断是否存在uid
            if(Check.NuNStr(uid)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, "uid is null"));
                return dto.toJsonString();
            }
            CustomerBankCardMsgEntity customerBankCardMsg = customerBankCardServiceImpl.getCustomerBankCardByUid(uid);
            dto.putValue("bankcard", customerBankCardMsg);
        }catch (Exception e){
            //当前可能原因，是在自如获取银行卡信息错误，请断点排查
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("获取用户银行卡信息异常");
        }
        return dto.toJsonString();
    }
    
    
    /**
     * 从数据库获取银行卡信息
     * 通过fid,uid
     * @author lishaochuan
     * @create 2016年8月16日下午6:58:41
     * @param fid
     * @param uid
     * @return
     */
    @Override
    public String getCustomerBankCardDbByFid(String fid, String uid) {
    	DataTransferObject dto = new DataTransferObject();
        try{
            //判断是否存在uid
            if(Check.NuNStr(fid) || Check.NuNStr(uid)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, "fid or uid为空"));
                return dto.toJsonString();
            }
            CustomerBankCardMsgEntity customerBankCardMsg = customerBankCardServiceImpl.getCustomerBankCardByFid(fid, uid);
            dto.putValue("bankcard", customerBankCardMsg);
        }catch (Exception e){
            //当前可能原因，是在自如获取银行卡信息错误，请断点排查
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("获取用户银行卡信息异常");
        }
        return dto.toJsonString();
    }


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoService#getCustomerBankcard(java.lang.String)
	 * 
	 * 说明：此时 不能从民宿库直接查询，要去调用自如空间的用户银行卡信息，因为这个是才是用户最新的银行卡
	 * 1.去查自如空间银行卡信息
	 * 2.无信息直接返回失败
	 * 3.有信息成功返回，并且更新用户的银行卡信息
	 */
	@Override
	public String getCustomerBankcard(String uid) {
		DataTransferObject dto = new DataTransferObject();
		try{
			//判断是否存在uid
			if(Check.NuNStr(uid)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource,"uid is null"));
				return dto.toJsonString();
			}
			
			CustomerBankCardMsgEntity customerBankCardMsg = getCustomerBank(uid);
			if(Check.NuNObj(customerBankCardMsg)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("当前用户的银行卡信息不存在，用户uid={"+uid+"}");
				return dto.toJsonString();
			}
			if(Check.NuNStr(customerBankCardMsg.getBankName())||Check.NuNStr(customerBankCardMsg.getBankcardNo())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("当前用户的银行卡,卡号不存在,或者名称不存在");
				return dto.toJsonString();
			}
			CustomerBankCardMsgEntity customerBankCardMsgEntity = customerBankCardServiceImpl.getCustomerBankCardByUid(uid);
			if (Check.NuNObj(customerBankCardMsgEntity) || compareIsDiffCard(customerBankCardMsg, customerBankCardMsgEntity)) {
				this.customerBankCardServiceImpl.insertCustomerBankCard(customerBankCardMsg);
			} else {
				customerBankCardMsg.setId(customerBankCardMsgEntity.getId());
				customerBankCardMsg.setFid(customerBankCardMsgEntity.getFid());
				customerBankCardMsg.setCreateDate(customerBankCardMsgEntity.getCreateDate());
				customerBankCardMsg.setIsDel(customerBankCardMsgEntity.getIsDel());
			}
			dto.putValue("bankcard", customerBankCardMsg);
		}catch (Exception e){
			//当前可能原因，是在自如获取银行卡信息错误，请断点排查
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("获取用户银行卡信息异常");
		}
		return dto.toJsonString();
	}
	
	
	/**
	 * 比较用户端信息卡信息是否有变更
	 * @author lishaochuan
	 * @create 2016年8月16日下午2:31:33
	 * @param source
	 * @param target
	 * @return
	 */
	private boolean compareIsDiffCard(CustomerBankCardMsgEntity source, CustomerBankCardMsgEntity target){
		try{
			// 比较之前，信息不能为空
			if (Check.NuNStr(source.getBankcardHolder()) || Check.NuNStr(source.getBankName()) || Check.NuNStr(source.getBankcardNo())) {
				return false;
			}
			// 比较开户名是否相同
			if (!source.getBankcardHolder().equals(target.getBankcardHolder())) {
				return true;
			}
			// 比较开户行是否相同
			if (!source.getBankName().equals(target.getBankName())) {
				return true;
			}
			// 比较银行卡号是否相同
			if (!source.getBankcardNo().equals(target.getBankcardNo())) {
				return true;
			}
			return false;
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			return false;
		}
	}

	/**
	 * 
	 * 保存用户银行卡信息  这里只处理用户银行卡信息  其他基本信息不做修改，全部由登录拦截器做
	 * @author yd
	 * @created 2016年5月6日 上午11:14:26
	 *
	 * @param uid
	 * @return 始终返回true 因为已经token验证通过就不阻碍用户登录了
	 */
	private CustomerBankCardMsgEntity getCustomerBank(String uid){
		CustomerBankCardMsgEntity customerBankCardMsg = null;
		if(Check.NuNStr(uid)){
    		return customerBankCardMsg;
    	}
    	
    	Map<String, String> paramMap = new HashMap<>();
    	paramMap.put("uid", uid);
    	paramMap.put("systemSource", SYSTEM_SOURCE);
    	
    	Map<?, ?> rst = this.invokeInterface(BANKCARD_DETAIL_URL, paramMap);
    	Object status = rst.get("status");
    	Object data = rst.get("data");
    	if (!Check.NuNObj(status) && SUCCESS.equals(status) && !Check.NuNObj(data) 
    			&& data instanceof List && !Check.NuNCollection((List<?>)data)){
    		List<?> bankCardList = (List<?>)data;
			if (bankCardList.size() == 1) {
				Map<?, ?> bankMap = (Map<?, ?>) bankCardList.get(0);
				if (!Check.NuNMap(bankMap)) {
					customerBankCardMsg = new CustomerBankCardMsgEntity();
					customerBankCardMsg.setFid(UUIDGenerator.hexUUID());
					customerBankCardMsg.setUid(uid);
					
					// 获取银行名称
					customerBankCardMsg.setBankcardHolder((String)bankMap.get("accountName"));
					customerBankCardMsg.setBankName((String)bankMap.get("bankName"));
					customerBankCardMsg.setBankcardNo((String)bankMap.get("bankCardNo"));
				}
			} else {
				// 当前情况下只允许用户绑定一张银行卡 2016-10-10
				LogUtil.error(LOGGER, "[账户系统]用户绑定银行卡数量异常,size={}", bankCardList.size());
			}
    	}
		return customerBankCardMsg;
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
	private Map<?, ?> invokeInterface(String url, Map<String, String> paramMap) {
		StringBuilder sb = new StringBuilder(url);
        sb.append("?").append(ENCRYPTION).append("=");
        try {
			sb.append(URLEncoder.encode(CryptAES.AES_Encrypt(ACCOUNT_KEY, JsonEntityTransform.Object2Json(paramMap)), ENCODING));
		} catch (UnsupportedEncodingException e) {
			LogUtil.error(LOGGER, "encode param failed:{}", e);
			return null;
		}
        
        String json = CloseableHttpsUtil.sendPost(sb.toString());
        return JsonEntityTransform.json2Map(json);
	}
	
	/**
	 * 调用接口获取银行枚举
	 * @author lishaochuan
	 * @create 2016年5月20日下午5:40:58
	 * @param bankCode
	 * @return
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	private String getBankName(String bankCode){
		try {
			LogUtil.info(LOGGER, "【银行名称】判断银行名称是否是数字,bankCode:{}", bankCode);
			if(Check.NuNStr(bankCode)){
				return "";
			}
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(bankCode);
			if (!isNum.matches()) {
				return bankCode;
			}
			LogUtil.info(LOGGER, "【银行名称】是数字，从枚举中获取银行名称");
			String bankName = BankNameEnum.getBankNameByCode(ValueUtil.getintValue(bankCode));
			LogUtil.info(LOGGER, "【银行名称】枚举中bankName:{}", bankName);
			if(!Check.NuNStr(bankName)){
				return bankName;
			}
			LogUtil.info(LOGGER, "【银行名称】枚举中没有，调用接口获取");
			Map<String, List<Map<String, String>>> bankInfoMap = (Map<String, List<Map<String, String>>>) JsonEntityTransform.json2Map(CloseableHttpUtil.sendGet(CUSTOMER_BANK_NAME_URL, null));
			List<Map<String, String>> bankInfoList = bankInfoMap.get("bankInfo");
			for (Map<String, String> map : bankInfoList) {
				if (bankCode.equals(map.get("bankFlag"))) {
					LogUtil.info(LOGGER, "【银行名称】接口中bankName:{}", map.get("bankName"));
					return ValueUtil.getStrValue(map.get("bankName"));
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【银行名称】bankCode:{}, e:{}", bankCode, e);
		}
		LogUtil.info(LOGGER, "【银行名称】调用接口也没获取到，返回自己，bankCode：{}", bankCode);
		return bankCode;
	}
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoService#updateCustomerBankcard(java.lang.String)
	 */
	@Override
	public String updateCustomerBankcard(String paramJson) {
		// 银行卡表不允许修改，只能新增，lishaochuan
		return null;
		/*DataTransferObject dto = new DataTransferObject();
		try{
			CustomerBankCardMsgEntity customerBankCardMsgEntity=JsonEntityTransform.json2Object(paramJson, CustomerBankCardMsgEntity.class);
			//判断是否存在uid
			if(Check.NuNStr(customerBankCardMsgEntity.getUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
				return dto.toJsonString();
			}
			int upNum=customerBankCardServiceImpl.updateCustomerBankCardByUid(customerBankCardMsgEntity);
			dto.putValue("upNum", upNum);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();*/
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoService#getCustomerInfoByUid(java.lang.String)
	 */
	@Override
	public String getCustomerInfoByUid(String uid) {
		DataTransferObject dto = new DataTransferObject();
		try{
			//判断是否存在uid
			if(Check.NuNStr(uid)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
				return dto.toJsonString();
			}
			CustomerBaseMsgEntity customerBaseMsgEntity=customerBaseMsgServiceImpl.getCustomerBaseMsgEntitybyUid(uid);
			dto.putValue("customerBase", customerBaseMsgEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 根据uidList查询客户基本信息List
	 * 注意uidList不要太长
	 * @author lishaochuan
	 * @create 2016年8月9日下午6:08:24
	 * @param uidList
	 * @return
	 */
	@Override
	public String getCustomerListByUidList(String json) {
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNStr(json)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
				return dto.toJsonString();
			}
			List<String> uidList = JsonEntityTransform.json2ObjectList(json, String.class);
			if(Check.NuNCollection(uidList)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
				return dto.toJsonString();
			}
			List<CustomerBaseMsgEntity> customerList = customerBaseMsgServiceImpl.queryCustomerListByUidList(uidList);
			dto.putValue("customerList", customerList);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	/**
	 * 获取当前用户的基本信息和角色信息
	 * @author afi
	 * @param uid
	 * @return
	 */
	@Override
	public String getCustomerRoleInfoByUid(String uid){
		DataTransferObject dto = new DataTransferObject();
		try{
			//判断是否存在uid
			if(Check.NuNStr(uid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
				return dto.toJsonString();
			}
			List<CustomerRoleEntity> roles = customerRoleService.getCustomerRoles(uid);
			dto.putValue("roles",roles);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}




    /**
     * 获取当前用户的基本信息和角色信息
     * @author afi
     * @param uid
     * @return
     */
    @Override
    public String getCustomerAndRoleInfoByUid(String uid){
        DataTransferObject dto = new DataTransferObject();
        try{
            //判断是否存在uid
            if(Check.NuNStr(uid)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
                return dto.toJsonString();
            }
            CustomerBaseMsgEntity customerBaseMsgEntity=customerBaseMsgServiceImpl.getCustomerBaseMsgEntitybyUid(uid);
            dto.putValue("customerBase", customerBaseMsgEntity);
            List<CustomerRoleEntity> roles = customerRoleService.getCustomerRoles(uid);
            dto.putValue("roles",roles);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }



	/**
	 * 
	 * 条件查询用户基本信息（条件不让为null 为null后就直接查全库了）
	 * 
	 * @author yd
	 * @created 2016年5月10日 下午5:36:54
	 *
	 * @param customerDto
	 * @return
	 */
	@Override
	public String selectByCondition(String customerBaseDtoStr){

		DataTransferObject dto = new DataTransferObject();
		CustomerBaseMsgDto customerBaseDto  = JsonEntityTransform.json2Object(customerBaseDtoStr, CustomerBaseMsgDto.class);
		List<CustomerBaseMsgEntity> listCustomerBaseMsg =  null;
		if(!Check.NuNObj(customerBaseDto)){
			LogUtil.info(LOGGER, "用户基本条件参数customerBaseDto", customerBaseDto.toJsonStr());	
			listCustomerBaseMsg = this.customerBaseMsgServiceImpl.selectByCondition(customerBaseDto);
		}
		dto.putValue("listCustomerBaseMsg", listCustomerBaseMsg);
		return dto.toJsonString();
	}
	/**
	 * 
	 * 条件查询 返回数量
	 *
	 * @author yd
	 * @created 2016年5月10日 下午3:55:36
	 *
	 * @param smsAuthLogDto
	 * @return
	 */
	@Override
	public String getSmsAuthLogCountByCondition(String smsAuthLogDtoStr){

		DataTransferObject dto = new DataTransferObject();
		SmsAuthLogDto smsAuthLogDto  = JsonEntityTransform.json2Object(smsAuthLogDtoStr, SmsAuthLogDto.class);
		int count =  this.smsAuthLogServiceImpl.getSmsAuthLogCountByCondition(smsAuthLogDto);
		dto.putValue("count", count);
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoService#getCustomerByMobile(java.lang.String)
	 */
	@Override
	public String getCustomerByMobile(String mobile) {
		DataTransferObject dto = new DataTransferObject();
		try{
			//判断是否存在mobile
			if(Check.NuNStr(mobile)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.PHONE_NULL_ERROR));
				return dto.toJsonString();
			}
			CustomerBaseMsgEntity customerBaseMsgEntity=customerBaseMsgServiceImpl.getCustomerByMobile(mobile);
			dto.putValue("customerBase", customerBaseMsgEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoService#getCustomerListByMobile(java.lang.String)
	 */
	@Override
	public String getCustomerListByMobile(String mobile) {
		DataTransferObject dto = new DataTransferObject();
		try{
			//判断是否存在mobile
			if(Check.NuNStr(mobile)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.PHONE_NULL_ERROR));
				return dto.toJsonString();
			}
			List<CustomerBaseMsgEntity> customerEntityList=customerBaseMsgServiceImpl.getCustomerListByMobile(mobile);
			dto.putValue("customerList", customerEntityList);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoService#staticsGetLandlordList(java.lang.String)
	 */
	@Override
    public String staticsGetLandlordList(String reuest){
		 DataTransferObject dto = new DataTransferObject();
		try{
		    LogUtil.info(LOGGER, "staticsGetLandlordList 请求参数:{}", reuest);
		   
		    if(Check.NuNStr(reuest)){
		    	LogUtil.info(LOGGER, "staticsGetLandlordList param:{}", reuest);
		        dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
		        dto.setMsg("param is null");
		        return dto.toJsonString();
		    }
		    StaticsCusBaseReqDto customerDto = JsonEntityTransform.json2Object(reuest, StaticsCusBaseReqDto.class);
		    if(Check.NuNObj(customerDto)){
			   LogUtil.info(LOGGER, "staticsGetLandlordList param:{}", JsonEntityTransform.Object2Json(customerDto));
		       dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
		       dto.setMsg("param is null");
		       return dto.toJsonString();
		    }
		    
		    //获取当前的加角色的信息
		    PagingResult<CustomerBaseMsgEntity> pageReuslt = customerBaseMsgServiceImpl.staticsGetLandlordList(customerDto);
		    dto.putValue("total", pageReuslt.getTotal());
		    dto.putValue("list", pageReuslt.getRows());
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
        return dto.toJsonString();
    }
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoService#countLanlordNum(java.lang.String)
	 */
	@Override
    public String countLanlordNum(String reuest){
        DataTransferObject dto = new DataTransferObject();
        try{
        	 LogUtil.info(LOGGER, "countLanlordNum 请求参数:{}", reuest);
        	 //获取当前的加角色的信息
             Long reuslt = customerBaseMsgServiceImpl.countLanlordNum();
             dto.putValue("result", reuslt);
        }catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
       
        return dto.toJsonString();
    }


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoService#updateLandPhoneNew(java.lang.String)
	 */
	@Override
	public String updateLandPhoneNew(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		CustomerBaseMsgEntity customerBaseMsgEntity=JsonEntityTransform.json2Object(paramJson, CustomerBaseMsgEntity.class);
        try{
            //判断是否存在uid
            if(Check.NuNStr(customerBaseMsgEntity.getUid())){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
                return dto.toJsonString();
            }
            //判断是否存在uid
            if(Check.NuNStr(customerBaseMsgEntity.getCustomerMobile())){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.PHONE_NULL_ERROR));
                return dto.toJsonString();
            }
            TelExtensionVo extensionVo = telExtensionService.getExtensionVoByUid(customerBaseMsgEntity.getUid());
            if (!Check.NuNObj(extensionVo) && extensionVo.getExtStatus() == ExtStatusEnum.HAS_OK.getCode()){
                //重新绑定400电话
                extensionVo.setLandPhone(customerBaseMsgEntity.getCustomerMobile());
                callCrmServiceProxy.postDoChange(customerBaseMsgEntity.getUid(),customerBaseMsgEntity.getUid(),extensionVo);
            }
            customerBaseMsgEntity.setIsContactAuth(CusotmerAuthEnum.IsAuth.getCode());
            customerBaseMsgServiceImpl.updateCustomerInfo(customerBaseMsgEntity);
            //删除redis
            try {
                redisOperations.del(RedisKeyConst.getCutomerKey(customerBaseMsgEntity.getUid()));
            } catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
            }

        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
	}


	@Override
	public String getByCustomNameAndTel(String paramJson) {
		LogUtil.info(LOGGER, "参数{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		Map<String, Object> paramMap = (Map<String, Object>) JsonEntityTransform.json2Map(paramJson);
		if(Check.NuNMap(paramMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		if(Check.NuNStr((String) paramMap.get("realName")) && Check.NuNStr((String) paramMap.get("customerMobile"))){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		List<CustomerBaseMsgEntity> customerList= customerBaseMsgServiceImpl.getByCustomNameAndTel(paramMap);
		dto.putValue("customerList", customerList);
		return dto.toJsonString();
	}


	/**
	 * 根据条件获取所有需要审核的字段最新信息
	 *
	 * @author loushuai
	 * @created 2017年8月8日 下午3:33:30
	 *
	 * @param object2Json
	 * @return
	 */
	@Override
	public String getFieldAuditNewLogByParam(String paramJson) {
		LogUtil.info(LOGGER, "参数{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		Map<String, Object> paramMap = (Map<String, Object>) JsonEntityTransform.json2Map(paramJson);
		if(Check.NuNMap(paramMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		List<CustomerFieldAuditVo> customerFieldAuditVoList = customerOperHistoryImpl.getFieldAuditNewLogByParam(paramMap);
		dto.putValue("customerFieldAuditVoList", customerFieldAuditVoList);
		return dto.toJsonString();
	}


	/**
	 * 
	 * 获取最新修改且尚未审核的照片
	 *
	 * @author loushuai
	 * @created 2017年8月8日 下午5:08:40
	 *
	 * @param headPicMap
	 * @return
	 */
	@Override
	public String getLatestUnAuditHeadPic(String paramJson) {
		LogUtil.info(LOGGER, "参数{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		Map<String, Object> paramMap = (Map<String, Object>) JsonEntityTransform.json2Map(paramJson);
		if(Check.NuNMap(paramMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		CustomerPicMsgEntity customerPicMsg = customerPicMsgServiceImpl.getLatestUnAuditHeadPic(paramMap);
		dto.putValue("customerPicMsg", customerPicMsg);
		return dto.toJsonString();
	}


	/**
	 * 获取所有需要审核的房东列表
	 *
	 * @author loushuai
	 * @created 2017年8月29日 下午9:51:23
	 *
	 * @return
	 */
	@Override
	public String getAllNeedAuditLand(String paramJson) {
		LogUtil.info(LOGGER, "参数{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		Map<String, Object> paramMap = (Map<String, Object>) JsonEntityTransform.json2Map(paramJson);
		if(Check.NuNMap(paramMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		List<CustomerUpdateFieldAuditNewlogEntity> allNeedAuditLandList = customerOperHistoryImpl.getAllNeedAuditLand(paramMap);
		dto.putValue("allNeedAuditLandList", allNeedAuditLandList);
		return dto.toJsonString();
	}


	@Override
	public String getCustomerSearchVoByUid(String landlordUid) {
		LogUtil.info(LOGGER, "参数{}", landlordUid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(landlordUid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		CustomerBaseMsgEntity customerBaseMsg=customerBaseMsgServiceImpl.getCustomerBaseMsgEntitybyUid(landlordUid);
		CustomerSearchVo customerSearchVo = new CustomerSearchVo();
		customerSearchVo.setCustomerBaseMsg(customerBaseMsg);
		Map<String, Object> parmaMap = new HashMap<>();
		parmaMap.put("uid", landlordUid);
		parmaMap.put("auditBeforeStatus", YesOrNoEnum.NO.getCode());
		parmaMap.put("auditAfterStatus", YesOrNoEnum.YES.getCode());		
		List<CustomerOperHistoryEntity> list= customerOperHistoryImpl.getIsAuditPassIn30Days(parmaMap);
		if (!Check.NuNCollection(list)) {
			customerSearchVo.setIsAuditPassIn30Days(YesOrNoEnum.YES.getCode());
		}
		dto.putValue("customerSearchVo", customerSearchVo);
		return dto.toJsonString();
	}
}
