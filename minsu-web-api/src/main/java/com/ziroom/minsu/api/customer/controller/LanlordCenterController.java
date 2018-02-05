/**
 * @FileName: PersonalCenterController.java
 * @Package com.ziroom.minsu.api.customer.controller
 * @author yd
 * @created 2016年9月7日 上午10:14:33
 * <p>
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.RegExpUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.constant.ApiMessageConst;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.constant.HeaderParamName;
import com.ziroom.minsu.api.common.dto.BaseParamDto;
import com.ziroom.minsu.api.common.dto.LoginHeaderDto;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.header.Header;
import com.ziroom.minsu.api.common.logic.ParamCheckLogic;
import com.ziroom.minsu.api.common.logic.ValidateResult;
import com.ziroom.minsu.api.common.util.CustomerVoUtils;
import com.ziroom.minsu.api.customer.dto.AuthMsgDto;
import com.ziroom.minsu.api.customer.dto.LanlordCenterDto;
import com.ziroom.minsu.entity.base.NationCodeEntity;
import com.ziroom.minsu.entity.base.StaticResourceEntity;
import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.entity.message.SysComplainEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.StaticResourceService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.basedata.entity.StaticResourceVo;
import com.ziroom.minsu.services.cms.api.inner.ActivityRecordService;
import com.ziroom.minsu.services.common.commenum.AccountSignEnum;
import com.ziroom.minsu.services.common.entity.FieldSelectListVo;
import com.ziroom.minsu.services.common.entity.FieldSelectVo;
import com.ziroom.minsu.services.common.entity.NameValuePair;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.common.utils.JsonTransform;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.SignUtils;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.randomUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerAuthService;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.api.inner.TelExtensionService;
import com.ziroom.minsu.services.customer.dto.CustomerFieldAuditVo;
import com.ziroom.minsu.services.customer.dto.CustomerPicDto;
import com.ziroom.minsu.services.customer.entity.CustomerAuthVo;
import com.ziroom.minsu.services.customer.entity.CustomerBaseMsgVo;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.customer.util.UserHeadImgUtils;
import com.ziroom.minsu.services.message.api.inner.SysComplainService;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderPayService;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.valenum.account.CustomerTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;
import com.ziroom.minsu.valenum.customer.CustomerAuditStatusEnum;
import com.ziroom.minsu.valenum.customer.CustomerEduEnum;
import com.ziroom.minsu.valenum.customer.CustomerIdTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerSexEnum;
import com.ziroom.minsu.valenum.customer.CustomerUpdateLogEnum;
import com.ziroom.minsu.valenum.customer.IsLandlordEnum;
import com.ziroom.minsu.valenum.login.LoginCodeEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.tech.storage.client.service.StorageService;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>个人中心</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("/personal")
@Controller
public class LanlordCenterController extends AbstractController{

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LanlordCenterController.class);

    @Resource(name = "api.messageSource")
    private MessageSource messageSource;

    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;

    @Resource(name = "customer.customerInfoService")
    private CustomerInfoService customerInfoService;

    @Resource(name = "cms.activityRecordService")
    private ActivityRecordService activityRecordService;

    @Resource(name = "order.orderCommonService")
    private OrderCommonService orderCommonService;

    @Resource(name = "customer.telExtensionService")
    private TelExtensionService telExtensionService;

    @Resource(name = "order.orderPayService")
    private OrderPayService orderPayService;

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;

    @Resource(name = "message.sysComplainService")
    private SysComplainService sysComplainService;

    @Resource(name = "storageService")
    private StorageService storageService;

    @Resource(name = "basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;

    @Resource(name = "api.paramCheckLogic")
    private ParamCheckLogic paramCheckLogic;

    @Value("#{'${pic_base_addr_mona}'.trim()}")
    private String picBaseAddrMona;

    @Value("#{'${detail_big_pic}'.trim()}")
    private String detail_big_pic;

    @Value("#{'${list_small_pic}'.trim()}")
    private String list_small_pic;

    @Value("#{'${default_head_size}'.trim()}")
    private String default_head_size;

    @Value("#{'${LOGIN_UNAUTH}'.trim()}")
    private String loginUnauth;

    @Value("#{'${storage_key}'.trim()}")
    private String storageKey;

    @Value("#{'${storage_limit}'.trim()}")
    private String storageLimit;

    @Value("#{'${minsu.web.sys}'.trim()}")
    private String minsuWebSys;

    @Value("#{'${minsu.web.accept}'.trim()}")
    private String minsuWebAccept;

    @Value("#{'${USER_LOGINOUT_POST}'.trim()}")
    private String USER_LOGINOUT_POST;

    @Value("#{'${IMG_VERIFY_CODE_GET}'.trim()}")
    private String IMG_VERIFY_CODE_GET;

    @Autowired
    private RedisOperations redisOperations;

    @Value("#{'${passport_app_key_value}'.trim()}")
    private String appKeyValue;

    @Value("#{'${passport_save_appid}'.trim()}")
    private String saveAppid;

    @Value("#{'${passport_save_cert_uid}'.trim()}")
    private String saveCertUid;

    @Resource(name = "basedata.staticResourceService")
    private StaticResourceService staticResourceService;

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";


    @Value("#{'${AUTH_CODE}'.trim()}")
    private String AUTH_CODE;

    @Value("#{'${AUTH_SECRET_KEY}'.trim()}")
    private String AUTH_SECRET_KEY;

    @Resource(name = "basedata.confCityService")
    private ConfCityService confCityService;


    @Value("#{'${CUSTOMER_DETAIL_URL}'.trim()}")
    private String CUSTOMER_DETAIL_URL;

    @Resource(name = "customer.customerAuthService")
    private CustomerAuthService customerAuthService;


    /**
     * 个人资料
     *
     * @param request
     * @return
     * @author liujun
     * @created 2016年9月7日
     */
    
    //@RequestMapping(value = "/${NO_LGIN_AUTH}/showBasicDetail", method = RequestMethod.GET) 测试用 
    @RequestMapping(value = "/${LOGIN_AUTH}/showBasicDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> showPersonalDetail(HttpServletRequest request) {
        try {
            String uid = (String) request.getAttribute("uid");
            //uid="664524c5-6e75-ee98-4e0d-667d38b9eee1";
            LogUtil.info(LOGGER, "uid：" + uid);
            if (Check.NuNStr(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
                        MessageSourceUtil.getChinese(messageSource, ApiMessageConst.UID_NULL)), HttpStatus.OK);
            }

            String resultJson = customerInfoService.getCustomerInfoByUid(uid);
            CustomerBaseMsgVo customerBase = SOAResParseUtil
                    .getValueFromDataByKey(resultJson, "customerBase", CustomerBaseMsgVo.class);
            //如果没有用户信息创建一个空的
            if (Check.NuNObj(customerBase)) {
                customerBase = new CustomerBaseMsgVo();
            }
            String customerEduName = "";
            if (!Check.NuNObj(customerBase.getCustomerEdu())) {
                CustomerEduEnum eduEnum = CustomerEduEnum.getCustomerEduByCode(customerBase.getCustomerEdu());
                customerEduName = eduEnum == null ? "" : eduEnum.getName();
            }
            String sexName = "请选择您的性别";
            if (!Check.NuNObj(customerBase.getCustomerSex())) {
                CustomerSexEnum sexEnum = CustomerSexEnum.getCustomerSexEnumByCode(customerBase.getCustomerSex());
                sexName = sexEnum == null ? "" : sexEnum.getValue();
            }

            String introduceJson = customerMsgManagerService.selectCustomerExtByUid(uid);
            DataTransferObject introduceDto = JsonEntityTransform.json2DataTransferObject(introduceJson);
            String hasIntroduce = "未完成";
            if (introduceDto.getCode() == DataTransferObject.SUCCESS) {
                CustomerBaseMsgExtEntity extEntity = SOAResParseUtil
                        .getValueFromDataByKey(introduceJson, "customerBaseMsgExt", CustomerBaseMsgExtEntity.class);
                if (!Check.NuNObj(extEntity) && !Check.NuNStr(extEntity.getCustomerIntroduce())) {
                    hasIntroduce = "已完成";
                }
            }
            //判断个人介绍是否在审核中
    		CustomerBaseMsgExtEntity unCheckCustomerBaseMsg = new CustomerBaseMsgExtEntity();
    		CustomerPicMsgEntity latestUnAuditHeadPic = new CustomerPicMsgEntity();
            Map<String, String> auditMap=fillLandNewHeadPicAndIntroduce(latestUnAuditHeadPic,unCheckCustomerBaseMsg,uid,customerBase);
          //如果是默认昵称，置为空
    		/*if(YesOrNoEnum.YES.getCode() == customerBase.getIsLandlord() && !Check.NuNStr(customerBase.getNickName())){
    				if(isDefaultNickName(customerBase.getNickName(), customerBase.getUid())){
    					customerBase.setNickName(null);
    				}
    		}*/
    		
            customerBase.setIntroduceAuditMsg(auditMap.get("introduceAuditMsg"));
            customerBase.setCustomerEduName(customerEduName);
            customerBase.setSexName(sexName);
            customerBase.setHasIntroduce(hasIntroduce);
            customerBase.setNickNameAuditMsg(auditMap.get("nickNameAuditMsg"));

            //教育背景列表
            List<NameValuePair<String, Integer>> eduList = new ArrayList<>();
            for (Map.Entry<Integer, String> entry : CustomerEduEnum.getEnumMap().entrySet()) {
                NameValuePair<String, Integer> pair = new NameValuePair<>();
                pair.setName(entry.getValue());
                pair.setValue(entry.getKey());
                eduList.add(pair);
            }
            customerBase.setEduList(eduList);

            //性别列表
            List<NameValuePair<String, Integer>> sexList = new ArrayList<>();
            for (Map.Entry<Integer, String> entry : CustomerSexEnum.getEnumMap().entrySet()) {
                NameValuePair<String, Integer> pair = new NameValuePair<>();
                pair.setName(entry.getValue());
                pair.setValue(entry.getKey());
                sexList.add(pair);
            }
            customerBase.setSexList(sexList);

            if (!Check.NuNObj(customerBase.getCustomerBirthday())) {
                customerBase.setCustomerBirthdayStr(DateUtil.dateFormat(customerBase.getCustomerBirthday(), DATE_FORMAT_PATTERN));
            }

            LogUtil.info(LOGGER, "返回信息:{}", JsonEntityTransform.Object2Json(customerBase));
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(customerBase), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(e.getMessage()), HttpStatus.OK);
        }
    }

    /**
	 * 校验是否是默认昵称
	 * @author loushuai
	 * @param nickName
	 * @param uid
	 * @return
	 */
	public boolean isDefaultNickName(String nickName, String uid){
		if(!Check.NuNStr(nickName) && !Check.NuNStr(uid)){
			String uidTemp = uid.substring(uid.length()-5);
			String check = "自如客" + uidTemp;
			if(nickName.equals(check)){
				return true;
			} 
		}
		return false;
	}
	
    /**
     * 个人介绍
     *
     * @param request
     * @return
     * @author liujun
     * @created 2016年9月7日
     */
    @RequestMapping(value = "/${LOGIN_AUTH}/introduce", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> customerIntroduce(HttpServletRequest request) {
        try {
            String uid = (String) request.getAttribute("uid");
            LogUtil.info(LOGGER, "uid：" + uid);
            if (Check.NuNStr(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
                        MessageSourceUtil.getChinese(messageSource, ApiMessageConst.UID_NULL)), HttpStatus.OK);
            }
            String resultJson = customerMsgManagerService.selectCustomerExtByUid(uid);
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (resultDto.getCode() == DataTransferObject.ERROR) {
                LogUtil.info(LOGGER, "customerMsgManagerService.selectCustomerExtByUid接口失败,uid:{}", uid);
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()), HttpStatus.OK);
            }
            CustomerBaseMsgExtEntity customerBaseMsgExt = SOAResParseUtil
                    .getValueFromDataByKey(resultJson, "customerBaseMsgExt", CustomerBaseMsgExtEntity.class);
            //查询customerBaseMsg，获取状态
            String customerBaseMsgJson=customerInfoService.getCustomerInfoByUid(uid);
    		CustomerBaseMsgEntity customerBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(customerBaseMsgJson, "customerBase", CustomerBaseMsgEntity.class);
    		
    		//如果此房东的审核通过后，修改了头像或者个人介绍，则填充最新修改的后的值
    		CustomerBaseMsgExtEntity unCheckCustomerBaseMsg = new CustomerBaseMsgExtEntity();
    		CustomerPicMsgEntity latestUnAuditHeadPic = new CustomerPicMsgEntity();
    		//从t_customer_update_history_log表中查询,看是否存在需要替换的字段
    		if(customerBaseMsgEntity.getIsLandlord()==CustomerTypeEnum.landlord.getStatusCode()){
    				fillLandNewHeadPicAndIntroduce(latestUnAuditHeadPic,unCheckCustomerBaseMsg, uid, customerBaseMsgEntity);
    		}
    		
    		String customerIntroduce = null;
    		if(!Check.NuNObj(unCheckCustomerBaseMsg) && !Check.NuNStr(unCheckCustomerBaseMsg.getCustomerIntroduce())){
    			 customerIntroduce = unCheckCustomerBaseMsg.getCustomerIntroduce();
    		}else{
    			customerIntroduce = customerBaseMsgExt == null ? "" : customerBaseMsgExt.getCustomerIntroduce();
    		}
            
            DataTransferObject dto = new DataTransferObject();
            
            dto.putValue("introduce", customerIntroduce);
            LogUtil.debug(LOGGER, "返回个人介绍信息:{}", JsonEntityTransform.Object2Json(dto));
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(e.getMessage()), HttpStatus.OK);
        }
    }

    /**
     * 保存个人介绍
     *
     * @param request
     * @return
     * @author liujun
     * @created 2016年9月7日
     */
    @RequestMapping(value = "/${LOGIN_AUTH}/saveIntroduce", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> saveCustomerIntroduce(HttpServletRequest request) {
        try {
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "参数：" + paramJson);

            if (Check.NuNStr(paramJson)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
                        MessageSourceUtil.getChinese(messageSource, ApiMessageConst.PARAM_NULL)), HttpStatus.OK);
            }
            JSONObject jsonObject = JSON.parseObject(paramJson);
            String customerIntroduce = "";
            if (!Check.NuNObj(jsonObject) && jsonObject.containsKey("customerIntroduce")) {
                customerIntroduce = jsonObject.getString("customerIntroduce");
            }

            String uid = (String) request.getAttribute("uid");
            LogUtil.info(LOGGER, "参数：uid={},customerIntroduce={}", uid, customerIntroduce);
            if (Check.NuNStr(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
                        MessageSourceUtil.getChinese(messageSource, ApiMessageConst.UID_NULL)), HttpStatus.OK);
            }

            if (Check.NuNStr(customerIntroduce)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("个人介绍不能为空"), HttpStatus.OK);
            }

            if (customerIntroduce.length() > 500) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("超过500字数限制"), HttpStatus.OK);
            }

            if (customerIntroduce.length() < 70) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("字数不能少于70字"), HttpStatus.OK);
            }

            String resultJson = customerMsgManagerService.selectCustomerExtByUid(uid);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (dto.getCode() == DataTransferObject.ERROR) {
                LogUtil.info(LOGGER, "customerMsgManagerService.selectCustomerExtByUid接口失败,uid:{}", uid);
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            }
            CustomerBaseMsgExtEntity customerBaseMsgExt = SOAResParseUtil
                    .getValueFromDataByKey(resultJson, "customerBaseMsgExt", CustomerBaseMsgExtEntity.class);

            //如果存在个人介绍更新，不存在新增
            String updateJson = "";
            if (Check.NuNObj(customerBaseMsgExt)) {//新增
                CustomerBaseMsgExtEntity extEntity = new CustomerBaseMsgExtEntity();
                extEntity.setFid(UUIDGenerator.hexUUID());
                extEntity.setUid(uid);
                extEntity.setCustomerIntroduce(customerIntroduce);
                updateJson = customerMsgManagerService.insertCustomerExt(JsonEntityTransform.Object2Json(extEntity));
            } else {//更新
                customerBaseMsgExt.setCustomerIntroduce(customerIntroduce);
                updateJson = customerMsgManagerService.updateCustomerExtByUid(JsonEntityTransform.Object2Json(customerBaseMsgExt));
            }

            dto = JsonEntityTransform.json2DataTransferObject(updateJson);
            LogUtil.info(LOGGER, "返回信息:{}", dto.toJsonString());
            if (dto.getCode() == DataTransferObject.ERROR) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(e.getMessage()), HttpStatus.OK);
        }
    }

    /**
     * 修改个人资料
     *
     * @param request
     * @return
     * @author liujun
     * @created 2016年9月7日
     */
    @RequestMapping(value = "/${LOGIN_AUTH}/updateCustomerInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> updateCustomerInfo(HttpServletRequest request) {
        try {
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "参数：" + paramJson);

            ValidateResult<CustomerBaseMsgEntity> validateResult = paramCheckLogic
                    .checkParamValidate(paramJson, CustomerBaseMsgEntity.class);

            if (!validateResult.isSuccess()) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
                        validateResult.getDto().getMsg()), HttpStatus.OK);
            }

            CustomerBaseMsgEntity baseMsgEntity = validateResult.getResultObj();
            if (Check.NuNStr(baseMsgEntity.getUid())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(
                        MessageSourceUtil.getChinese(messageSource, ApiMessageConst.UID_NULL)), HttpStatus.OK);
            }

            String customerInfoJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(baseMsgEntity.getUid());
            CustomerBaseMsgEntity customerEntity = SOAResParseUtil.getValueFromDataByKey(customerInfoJson, "customer",
                    CustomerBaseMsgEntity.class);

            //如果已认证，昵称和个人介绍是不能为空
            if (!Check.NuNObj(customerEntity) && customerEntity.getAuditStatus() == AuditStatusEnum.COMPLETE.getCode()) {
                if (Check.NuNStr(baseMsgEntity.getNickName())) {
                    return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("昵称不能为空!"), HttpStatus.OK);
                }
            }
            //不提交手机号码
            baseMsgEntity.setCustomerMobile(null);
            String resultJson = customerInfoService.updateCustomerInfo(JsonTransform.Object2Json(baseMsgEntity));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            LogUtil.info(LOGGER, "返回信息:{}", dto.toJsonString());
            if (dto.getCode() == DataTransferObject.ERROR) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(e.getMessage()), HttpStatus.OK);
        }
    }

    /**
     * 获取个人信息
     *
     * @param
     * @return
     * @author wangwt
     * @created 2017年06月22日 10:38:34
     */
    @RequestMapping("/${LOGIN_AUTH}/getCustomerInfo")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> getCustomerInfo(HttpServletRequest request) {
        try {
            String uid = (String) request.getAttribute("uid");
            LogUtil.info(LOGGER, "uid：" + uid);
            if (Check.NuNStr(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
                        MessageSourceUtil.getChinese(messageSource, ApiMessageConst.UID_NULL)), HttpStatus.OK);
            }
            //获取自我介绍
            String resultJson = customerMsgManagerService.selectCustomerExtByUid(uid);
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (resultDto.getCode() == DataTransferObject.ERROR) {
                LogUtil.info(LOGGER, "customerMsgManagerService.selectCustomerExtByUid接口失败,uid:{}", uid);
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()), HttpStatus.OK);
            }
            CustomerBaseMsgExtEntity customerBaseMsgExt = SOAResParseUtil
                    .getValueFromDataByKey(resultJson, "customerBaseMsgExt", CustomerBaseMsgExtEntity.class);
            String customerIntroduce = customerBaseMsgExt == null ? "" : customerBaseMsgExt.getCustomerIntroduce();

            //获取昵称 是否审核完成
            String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
            if (customerDto.getCode() != DataTransferObject.SUCCESS) {
                LogUtil.info(LOGGER, "getCustomerBaseMsgEntitybyUid error : {}", customerDto.toJsonString());
            }
            CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
            });
            if (Check.NuNObj(entity)) {
                LogUtil.info(LOGGER, "getCustomerBaseMsgEntitybyUid return entity is null");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("没有找到用户信息"), HttpStatus.OK);
            }
            String nickName = Check.NuNStr(entity.getNickName()) ? "" : entity.getNickName();
            //如果是默认昵称，置为空
            if(!Check.NuNStr(nickName)){
            			if(isDefaultNickName(nickName, uid)){
            				nickName="";
            			}
            }
            
            boolean isEdit = true;
            if (entity.getAuditStatus() == AuditStatusEnum.COMPLETE.getCode()) {
                isEdit = false;
            }
            //获取头像
            CustomerPicDto picdto = new CustomerPicDto();
            picdto.setUid(uid);
            picdto.setPicType(CustomerPicTypeEnum.YHTX.getCode());
            String customerPicJson = customerMsgManagerService.getCustomerPicByType(JsonEntityTransform.Object2Json(picdto));
            DataTransferObject customerPicDto = JsonEntityTransform.json2DataTransferObject(customerPicJson);
            String headPicUrl = "";
            if (customerPicDto.getCode() == DataTransferObject.SUCCESS) {
                CustomerPicMsgEntity picEntity = customerPicDto.parseData("customerPicMsgEntity", new TypeReference<CustomerPicMsgEntity>() {
                });
                if (!Check.NuNObj(picEntity) && !Check.NuNStr(picEntity.getPicServerUuid())) {
                    headPicUrl = PicUtil.getFullPic(picBaseAddrMona, picEntity.getPicBaseUrl(), picEntity.getPicSuffix(), detail_big_pic);
                }
            }

            //获取上传头像提示文案
            Map<String, Object> headPicMap = new HashMap<>();
            headPicMap.put("headPicUrl", headPicUrl);
            String headPicTipJson = staticResourceService.findStaticResListByResCode("HEAD_PIC_TIP");
            List<StaticResourceVo> resList = SOAResParseUtil.getListValueFromDataByKey(headPicTipJson, "staticResList", StaticResourceVo.class);
            if (!Check.NuNCollection(resList)) {
                StaticResourceVo staticResourceVo = resList.get(0);
                String resContent = staticResourceVo.getResContent();
                String[] tips = resContent.split("\\|");
                headPicMap.put("explain", tips);
            }

            String remindMsgJson = staticResourceService.findStaticResourceByCode("DEFAULT_INTRODUCE");
            StaticResourceVo staticResourceVo = SOAResParseUtil.getValueFromDataByKey(remindMsgJson, "StaticResourceEntity", StaticResourceVo.class);
            String defaultIntroduce = "";
            if (!Check.NuNObj(staticResourceVo)) {
                defaultIntroduce = staticResourceVo.getResContent();
            }

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("customerIntroduce", customerIntroduce);
            resultMap.put("isEdit", isEdit);
            resultMap.put("nickName", nickName);
            resultMap.put("defaultIntroduce", defaultIntroduce);
            resultMap.put("pictrues", headPicMap);
            LogUtil.info(LOGGER, "getCustomerInfo return : {}", JsonEntityTransform.Object2Json(resultMap));
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务异常"), HttpStatus.OK);
        }
    }

    /**
     * 保存个人信息
     *
     * @param
     * @return
     * @author wangwt
     * @created 2017年06月22日 14:06:15
     */
    @RequestMapping(value = "/${LOGIN_AUTH}/saveCustomerInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> saveCustomerInfo(HttpServletRequest request) {
        try {
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "参数：" + paramJson);
            JSONObject obj = JSONObject.parseObject(paramJson);
            String uid = (String) request.getAttribute("uid");
            String nickName = obj.getString("nickName");
            String customerIntroduce = obj.getString("customerIntroduce");
            if (Check.NuNStr(uid)) {
                LogUtil.info(LOGGER, "uid is null");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("用户id不能为空"), HttpStatus.OK);
            }

            if (!Check.NuNStr(nickName) && nickName.length() > 30) {
                LogUtil.info(LOGGER, "nickName is too long");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("昵称不能超过30字"), HttpStatus.OK);
            }

            //校验个人介绍
            if (Check.NuNStr(customerIntroduce)) {
                LogUtil.info(LOGGER, "customerIntroduce is null");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("个人介绍不能为空"), HttpStatus.OK);
            }

            if (customerIntroduce.length() > 500) {
                LogUtil.info(LOGGER, "customerIntroduce is too long");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("超过500字数限制"), HttpStatus.OK);
            }


            if (customerIntroduce.length() < 70) {
                LogUtil.info(LOGGER, "customerIntroduce is too small");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("字数不能少于70字"), HttpStatus.OK);

            }

            CustomerVo customerVo = new CustomerVo();
            customerVo.setUid(uid);
            customerVo.setCustomerIntroduce(customerIntroduce);
            customerVo.setNickName(nickName);
            String customerJson = JsonEntityTransform.Object2Json(customerVo);


            LogUtil.info(LOGGER, "saveNickNameAndIntroduce param : {}", customerJson);
            String resultJson = customerMsgManagerService.saveNickNameAndIntroduce(customerJson);
            LogUtil.info(LOGGER, "saveNickNameAndIntroduce return : {}", resultJson);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (dto.getCode() != DataTransferObject.SUCCESS) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(e.getMessage()), HttpStatus.OK);
        }
    }

    /**
     * 房东个人中心
     *
     * @param request
     * @return
     * @author yd
     * @created 2016年9月7日 上午10:26:22
     */
    
    //@RequestMapping("/${NO_LGIN_AUTH}/initPersonalCenter")
    @RequestMapping("/${LOGIN_AUTH}/initPersonalCenter")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> initCustomerCenter(HttpServletRequest request) throws Exception {

        String uid = (String) request.getAttribute("uid");
        //uid="664524c5-6e75-ee98-4e0d-667d38b9eee1";
        String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
        DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);

        CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
        });

        if (Check.NuNObj(entity)) {
            LogUtil.error(LOGGER, "方法={initCustomerCenter}：根据uid={}，查询用户信息不存在", uid);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }

        //同步更新用户头像
        if (entity.getIsLandlord() == IsLandlordEnum.NOT_LANDLORD.getCode()) {
            CustomerPicDto customerPicDto = new CustomerPicDto();
            customerPicDto.setUid(uid);
            customerPicDto.setPicType(CustomerPicTypeEnum.YHTX.getCode());
            DataTransferObject picDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCustomerPicByType(JsonEntityTransform.Object2Json(customerPicDto)));
            if (picDto.getCode() == DataTransferObject.SUCCESS) {
                CustomerPicMsgEntity customerPicMsgEntity = picDto.parseData("customerPicMsgEntity", new TypeReference<CustomerPicMsgEntity>() {
                });
                if (Check.NuNObj(customerPicMsgEntity)) {
                    CustomerPicMsgEntity customerPicMsg = UserHeadImgUtils.getHeadImgFromZiroom(uid, CUSTOMER_DETAIL_URL);
                    if (!Check.NuNObj(customerPicMsg)) {
                        List<CustomerPicMsgEntity> picList = new ArrayList<CustomerPicMsgEntity>();
                        picList.add(customerPicMsg);
                        customerAuthService.customerIconAuth(JsonEntityTransform.Object2Json(picList));
                    }
                }

            }

        }

        CustomerVoUtils customerVoUtils = new CustomerVoUtils(customerMsgManagerService);
        CustomerVo customerVo = customerVoUtils.getCustomerVo(uid, customerMsgManagerService);
        if (Check.NuNObj(customerVo)) {
            LogUtil.error(LOGGER, "方法={initCustomerCenter}：根据uid={}，查询用户信息不存在", uid);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
        
        //如果此房东的审核通过后，修改了头像或者个人介绍，则填充最新修改的后的值
		CustomerBaseMsgExtEntity unCheckCustomerBaseMsg = new CustomerBaseMsgExtEntity();
		CustomerPicMsgEntity latestUnAuditHeadPic = new CustomerPicMsgEntity();
		//从t_customer_update_history_log表中查询,看是否存在需要替换的字段
		LanlordCenterDto lanlordCenterDto = new LanlordCenterDto();
      	if(customerVo.getIsLandlord()==CustomerTypeEnum.landlord.getStatusCode()){
      			Map<String, String> auditMap=fillLandNewHeadPicAndIntroduce(latestUnAuditHeadPic,unCheckCustomerBaseMsg, uid, entity);
      			lanlordCenterDto.setUserPicAuditMsg(auditMap.get("userPicAuditMsg"));
      	}
      //如果是默认昵称，置为空
     /* if(YesOrNoEnum.YES.getCode() == entity.getIsLandlord() && !Check.NuNStr(entity.getNickName())){
      			if(isDefaultNickName(entity.getNickName(), entity.getUid())){
      				entity.setNickName(null);
      			}
      }*/
      		
  	    if(!Check.NuNObj(latestUnAuditHeadPic)&&!Check.NuNStr(latestUnAuditHeadPic.getPicBaseUrl())){
            String fullPic = PicUtil.getFullPic(picBaseAddrMona, latestUnAuditHeadPic.getPicBaseUrl(), latestUnAuditHeadPic.getPicSuffix(), detail_big_pic);
			customerVo.setUserPicUrl(fullPic);
	    }	

        lanlordCenterDto.setIsAuth("未认证");
        //如果为空
        if (!Check.NuNObj(entity)) {
            lanlordCenterDto.setCustomer(entity);
            if (entity.getAuditStatus() == AuditStatusEnum.COMPLETE.getCode()) {
                lanlordCenterDto.setIsAuth("已认证");
            }
        }
        String phone400 = "";
        if (!Check.NuNStr(customerVo.getHostNumber())) {
            phone400 = customerVo.getHostNumber();
        }
        lanlordCenterDto.setPhone400(phone400);
        lanlordCenterDto.setAuthCode(entity == null ? 0 : entity.getAuditStatus());
        lanlordCenterDto.setPicBaseAddrMona(picBaseAddrMona);
        lanlordCenterDto.setDefaultHeadSize(default_head_size);
        lanlordCenterDto.setMenuType("userCenter");
        lanlordCenterDto.setLandlordUid(uid);
        lanlordCenterDto.setUserPicUrl(customerVo.getUserPicUrl());
        //afi 天使房东的逻 isAngel
        String roleJson = customerInfoService.getCustomerRoleInfoByUid(uid);
        List<CustomerRoleEntity> roles = SOAResParseUtil.getListValueFromDataByKey(roleJson,
                "roles", CustomerRoleEntity.class);
        if (!Check.NuNCollection(roles)) {
            //TODO 暂时有就是天使房东
            lanlordCenterDto.setIsAngel(YesOrNoEnum.YES.getCode());
        }

        //afi 五周年的逻辑 isFive
        String fiveJson = activityRecordService.getFive(uid);
        ActivityFreeEntity activityFreeEntity = SOAResParseUtil.getValueFromDataByKey(fiveJson,
                "obj", ActivityFreeEntity.class);
        if (!Check.NuNObj(activityFreeEntity)) {
            //TODO 暂时有就是五周年
            lanlordCenterDto.setIsFive(YesOrNoEnum.YES.getCode());
        }

        try {
            String resultJson = orderCommonService.countWaitEvaNumAll(uid, UserTypeEnum.LANDLORD.getUserType());
            Integer count = SOAResParseUtil.getValueFromDataByKey(resultJson, "count", Integer.class);
            count = Check.NuNObj(count) ? 0 : count;
            lanlordCenterDto.setWaitEvaNum(count);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "查询房东未评价订单数量失败，uid={},e={}", uid, e);
        }

        String headPicTipJson = staticResourceService.findStaticResourceByCode("HEAD_PIC_TIP");
        DataTransferObject headerDto = JsonEntityTransform.json2DataTransferObject(headPicTipJson);
        if (headerDto.getCode() == DataTransferObject.SUCCESS) {
            StaticResourceEntity staticResourceEntity = headerDto.parseData("StaticResourceEntity", new TypeReference<StaticResourceEntity>() {
            });
            if (!Check.NuNObj(staticResourceEntity)) {
                String resContent = staticResourceEntity.getResContent();
                String[] tips = resContent.split("\\|");
                List<String> strings = Arrays.asList(tips);
                List<String> photoRules = lanlordCenterDto.getPhotoRules();
                photoRules.addAll(strings);
            }
        }
        LogUtil.info(LOGGER, "房东用户中心返回结果：{}", JsonEntityTransform.Object2Json(lanlordCenterDto));
        return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(lanlordCenterDto), HttpStatus.OK);
    }

    
    /**
	 * 填充房东最新的修改信息
	 * @param latestUnAuditHeadPic
	 * @param uid
	 */
	public Map<String, String> fillLandNewHeadPicAndIntroduce(CustomerPicMsgEntity latestUnAuditHeadPic, CustomerBaseMsgExtEntity unCheckCustomerBaseMsg, String uid, CustomerBaseMsgEntity customerBaseMsg){
		LogUtil.info(LOGGER, "fillLandNewHeadPicAndIntroduce方法入参 uid={}", uid);
		Map<String, String> auditMap=new HashMap<String,String>();
		try {
			Map<String, Object> map = new HashMap<>();
	    	map.put("fieldAuditStatu", 0);
	    	map.put("uid", uid);
	    	String customerResultJson = customerInfoService.getFieldAuditNewLogByParam(JsonEntityTransform.Object2Json(map));
	    	DataTransferObject customerResultDto = JsonEntityTransform.json2DataTransferObject(customerResultJson);
	    	if(customerResultDto.getCode()==DataTransferObject.SUCCESS){
	    		 List<CustomerFieldAuditVo> customerFieldAuditVoList = customerResultDto.parseData("customerFieldAuditVoList", new TypeReference<List<CustomerFieldAuditVo>>() {});
	    	     if(!Check.NuNCollection(customerFieldAuditVoList) && customerFieldAuditVoList.size()>0){
	    	    	 for (CustomerFieldAuditVo customerFieldAuditVo : customerFieldAuditVoList) {
	    	    		 String fieldPath = customerFieldAuditVo.getFieldPath();
	         	    	 String fieldHeadPicPath = ClassReflectUtils.getFieldNamePath(CustomerPicMsgEntity.class,CustomerUpdateLogEnum.Customer_Pic_Msg_Pic_Base_Url.getFieldName());
	 					 if(!Check.NuNStr(fieldPath) && !Check.NuNStr(fieldHeadPicPath) && fieldHeadPicPath.equals(fieldPath)){
	 						Map<String, Object> headPicMap = new HashMap<String, Object>();
	 						headPicMap.put("auditStatus", CustomerAuditStatusEnum.UN_AUDIT.getCode());
	 						headPicMap.put("picType", 3);
	 						headPicMap.put("uid", uid);
	 						String latestUnAuditHeadPicJson = customerInfoService.getLatestUnAuditHeadPic(JsonEntityTransform.Object2Json(headPicMap));
	 						DataTransferObject latestUnAuditHeadPicDto = JsonEntityTransform.json2DataTransferObject(latestUnAuditHeadPicJson);
	 						if(latestUnAuditHeadPicDto.getCode()==DataTransferObject.SUCCESS){
	 							CustomerPicMsgEntity resultUnAuditHeadPic = latestUnAuditHeadPicDto.parseData("customerPicMsg", new TypeReference<CustomerPicMsgEntity>() {});
									BeanUtils.copyProperties(latestUnAuditHeadPic, resultUnAuditHeadPic);
	 						}
	 						auditMap.put("userPicAuditMsg", ApiMessageConst.FIELD_AUDIT_MSG);
	 					}
	 					 
	 					//修改了个人介绍
	 					String fieldIntroducePath = ClassReflectUtils.getFieldNamePath(CustomerBaseMsgExtEntity.class,CustomerUpdateLogEnum.Customer_Base_Msg_Ext_Introduce.getFieldName());
	 					if(!Check.NuNStr(fieldPath) && !Check.NuNStr(fieldIntroducePath) && fieldIntroducePath.equals(fieldPath)){
	 						//将待审核的字段的newlog的赋到该对象中，以便于审核通过，以该id修改newlog表中该该用户的该审核字段的审核状态
	 						unCheckCustomerBaseMsg.setCustomerIntroduce(customerFieldAuditVo.getNewValue());
	 						unCheckCustomerBaseMsg.setId(customerFieldAuditVo.getId());
	 						auditMap.put("introduceAuditMsg", ApiMessageConst.FIELD_AUDIT_MSG);
	 					} 
	 					//修改了昵称
	 					String fieldNickNamePath = ClassReflectUtils.getFieldNamePath(CustomerBaseMsgEntity.class,CustomerUpdateLogEnum.Customer_Base_Msg_NickName.getFieldName());
	 					if(!Check.NuNStr(fieldPath) && !Check.NuNStr(fieldNickNamePath) && fieldNickNamePath.equals(fieldPath)){
	 						//将待审核的字段的newlog的赋到该对象中，以便于审核通过，以该id修改newlog表中该该用户的该审核字段的审核状态
	 						customerBaseMsg.setNickName(customerFieldAuditVo.getNewValue());
	 						auditMap.put("nickNameAuditMsg", ApiMessageConst.FIELD_AUDIT_MSG);
	 					} 
	    	    	 }
	    	     }
	    	}
		} catch (IllegalAccessException
				| InvocationTargetException e) {
		}
		return auditMap;
	}
	
    /**
     * 查询认证信息
     *
     * @param request
     * @return
     * @author zl
     * @created 2016年9月7日
     */
    @RequestMapping("/${LOGIN_AUTH}/getCustomerAuthInfo")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> getCustomerAuthInfo(HttpServletRequest request) {

        try {
            long begin = System.currentTimeMillis();

            String uid = (String) request.getAttribute("uid");
            LogUtil.info(LOGGER, "查询认证信息 uid：" + uid);
            if (Check.NuNStr(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
                        MessageSourceUtil.getChinese(messageSource, ApiMessageConst.UID_NULL)), HttpStatus.OK);
            }

            CustomerAuthVo authVo = new CustomerAuthVo();

            //个人基本信息
            String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
            CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
            });

            BeanUtils.copyProperties(authVo, entity);

            //证件类型名称
            String idName = CustomerIdTypeEnum.getCustomerIdTypeByCode(entity.getIdType()).getName();
            authVo.setIdTypeName(idName);

            //证件正面照
            CustomerPicDto picDto = new CustomerPicDto();
            picDto.setUid(uid);
            picDto.setPicType(CustomerPicTypeEnum.ZJZM.getCode());
            String picJson1 = JsonEntityTransform.Object2Json(picDto);
            String picResultJosn1 = customerMsgManagerService.getCustomerPicByType(picJson1);
            CustomerPicMsgEntity customerPic1 = SOAResParseUtil.getValueFromDataByKey(picResultJosn1,
                    "customerPicMsgEntity", CustomerPicMsgEntity.class);
            String cardPicUrl1 = "";
            if (!Check.NuNObj(customerPic1)) {
                cardPicUrl1 = PicUtil.getFullPic(picBaseAddrMona, customerPic1.getPicBaseUrl(), customerPic1.getPicSuffix(), detail_big_pic);
                authVo.setVoucherFrontPicUrl(cardPicUrl1);
            }

            //证件反面照
            picDto.setPicType(CustomerPicTypeEnum.ZJFM.getCode());
            String picJson2 = JsonEntityTransform.Object2Json(picDto);
            String picResultJosn2 = customerMsgManagerService.getCustomerPicByType(picJson2);
            CustomerPicMsgEntity customerPic2 = SOAResParseUtil.getValueFromDataByKey(picResultJosn2,
                    "customerPicMsgEntity", CustomerPicMsgEntity.class);
            String cardPicUrl2 = "";
            if (!Check.NuNObj(customerPic2)) {
                cardPicUrl2 = PicUtil.getFullPic(picBaseAddrMona, customerPic2.getPicBaseUrl(), customerPic2.getPicSuffix(), detail_big_pic);
                authVo.setVoucherBackPicUrl(cardPicUrl2);
            }

            //手持证件照
            picDto.setPicType(CustomerPicTypeEnum.ZJSC.getCode());
            String picJson3 = JsonEntityTransform.Object2Json(picDto);
            String picResultJosn3 = customerMsgManagerService.getCustomerPicByType(picJson3);
            CustomerPicMsgEntity customerPic3 = SOAResParseUtil.getValueFromDataByKey(picResultJosn3,
                    "customerPicMsgEntity", CustomerPicMsgEntity.class);
            String cardPicUrl3 = "";
            if (!Check.NuNObj(customerPic3)) {
                cardPicUrl3 = PicUtil.getFullPic(picBaseAddrMona, customerPic3.getPicBaseUrl(), customerPic3.getPicSuffix(), detail_big_pic);
                authVo.setVoucherHandPicUrl(cardPicUrl3);
            }

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("realName", authVo.getRealName());
            resultMap.put("idType", authVo.getIdType());
            resultMap.put("idNo", authVo.getIdNo());
            resultMap.put("isIdentityAuth", authVo.getIsIdentityAuth());
            resultMap.put("idTypeName", authVo.getIdTypeName());
            resultMap.put("voucherFrontPicUrl", authVo.getVoucherFrontPicUrl());
            resultMap.put("voucherBackPicUrl", authVo.getVoucherBackPicUrl());
            resultMap.put("voucherHandPicUrl", authVo.getVoucherHandPicUrl());
            resultMap.put("idTypeMap", authVo.getIdTypeMap());
            resultMap.put("auditStatus", authVo.getAuditStatus());
            LogUtil.debug(LOGGER, "认证信息查询耗时{}ms,请求结果：{}", System.currentTimeMillis() - begin, JsonEntityTransform.Object2Json(resultMap));
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }

    }

    /**
     * 保存认证信息
     *
     * @param request
     * @return
     * @author zl
     * @created 2016年9月7日
     */
    @RequestMapping(value = "/${LOGIN_AUTH}/saveCustomerAuthInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> saveCustomerAuthInfo(HttpServletRequest request) {

        try {
            long begin = System.currentTimeMillis();

            String uid = (String) request.getAttribute("uid");
            LogUtil.info(LOGGER, "保存认证信息 uid：" + uid);
            if (Check.NuNStr(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
                        MessageSourceUtil.getChinese(messageSource, ApiMessageConst.UID_NULL)), HttpStatus.OK);
            }

            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "保存认证信息请求参数：" + paramJson);
            AuthMsgDto authMsgDto = JsonEntityTransform.json2Object(paramJson, AuthMsgDto.class);
            if (Check.NuNObj(authMsgDto)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数错误"), HttpStatus.OK);
            }

            authMsgDto.setUid(uid);


            String customerInfoJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerInfoJson);

            CustomerBaseMsgEntity customerInfoEntity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
            });

            if (!Check.NuNObj(customerInfoEntity) && customerInfoEntity.getAuditStatus() == AuditStatusEnum.COMPLETE.getCode()) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("信息已经认证，不可修改"), HttpStatus.OK);
            }

            if (Check.NuNStr(authMsgDto.getRealName())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请填写真实姓名"), HttpStatus.OK);
            }

            Pattern p = Pattern.compile("^[0-9]*$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(authMsgDto.getRealName());
            if (matcher.find()) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("真实姓名不能全是数字"), HttpStatus.OK);
            }

            if (Check.NuNObj(authMsgDto.getIdType())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请选择证件类型"), HttpStatus.OK);
            }
            if (Check.NuNObj(authMsgDto.getIdNo())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请填写证件号码"), HttpStatus.OK);
            }
            if (authMsgDto.getIdType() == CustomerIdTypeEnum.ID.getCode() && !RegExpUtil.idIdentifyCardNum(authMsgDto.getIdNo())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("身份证校验不通过"), HttpStatus.OK);
            }
            if (authMsgDto.getIdType() == CustomerIdTypeEnum.PASSPORT.getCode() && !RegExpUtil.isPassportNum(authMsgDto.getIdNo())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("护照校验不通过"), HttpStatus.OK);
            }
            if (authMsgDto.getIdType() == CustomerIdTypeEnum.GA_PASSPORT.getCode() && !RegExpUtil.isHMT(authMsgDto.getIdNo())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("港澳居民来往通行证校验不通过"), HttpStatus.OK);
            }
            if (authMsgDto.getIdType() == CustomerIdTypeEnum.tw_PASSPORT.getCode() && !RegExpUtil.isHMT(authMsgDto.getIdNo())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("台湾居民来往通行证校验不通过"), HttpStatus.OK);
            }
            if (authMsgDto.getIdType() == CustomerIdTypeEnum.GAT_ID.getCode() && !isGATIDCard(authMsgDto.getIdNo())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("港澳台居民身份证校验不通过"), HttpStatus.OK);
            }

            if (authMsgDto.getIdType() == CustomerIdTypeEnum.CHARTER.getCode() && !Check.NuNStr(authMsgDto.getIdNo()) &&
                    !authMsgDto.getIdNo().matches("^[A-Za-z0-9]{1,30}$")) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("营业执照校验不通过"), HttpStatus.OK);
            }

            //证件正面照 0
            CustomerPicDto picDto = new CustomerPicDto();
            picDto.setUid(authMsgDto.getUid());
            picDto.setPicType(CustomerPicTypeEnum.ZJZM.getCode());
            String picJson1 = JsonEntityTransform.Object2Json(picDto);
            String picResultJosn1 = customerMsgManagerService.getCustomerPicByType(picJson1);
            CustomerPicMsgEntity voucherFrontPicOld = SOAResParseUtil.getValueFromDataByKey(picResultJosn1,
                    "customerPicMsgEntity", CustomerPicMsgEntity.class);

            //证件反面照 1
            picDto.setPicType(CustomerPicTypeEnum.ZJFM.getCode());
            String picJson2 = JsonEntityTransform.Object2Json(picDto);
            String picResultJosn2 = customerMsgManagerService.getCustomerPicByType(picJson2);
            CustomerPicMsgEntity voucherBackPicOld = SOAResParseUtil.getValueFromDataByKey(picResultJosn2,
                    "customerPicMsgEntity", CustomerPicMsgEntity.class);

            //营业执照 4
            picDto.setPicType(CustomerPicTypeEnum.YYZZ.getCode());
            String picJson3 = JsonEntityTransform.Object2Json(picDto);
            String picResultJosn3 = customerMsgManagerService.getCustomerPicByType(picJson3);
            CustomerPicMsgEntity voucherHandPicOldYYZZ = SOAResParseUtil.getValueFromDataByKey(picResultJosn3,
                    "customerPicMsgEntity", CustomerPicMsgEntity.class);

            //手持证件照 3
            picDto.setPicType(CustomerPicTypeEnum.ZJSC.getCode());
            String picJson4 = JsonEntityTransform.Object2Json(picDto);
            String picResultJosn4 = customerMsgManagerService.getCustomerPicByType(picJson4);
            CustomerPicMsgEntity voucherHandPicOld = SOAResParseUtil.getValueFromDataByKey(picResultJosn4,
                    "customerPicMsgEntity", CustomerPicMsgEntity.class);

            boolean thirdPic = Check.NuNObj(voucherHandPicOldYYZZ) && Check.NuNObj(voucherHandPicOld);

            if (authMsgDto.getIdType() == CustomerIdTypeEnum.CHARTER.getCode()) {
                //手持照 和 营业执照都没有上传过，才提示；否则不提示，但可能出现照片类型， 照片不一致的情况，这个依赖人工校验
                if (Check.NuNObj(voucherFrontPicOld) || Check.NuNObj(voucherBackPicOld) || thirdPic) {
                    return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请按照顺序上传营业执照、" +
                            "法人身份证正面、法人身份证反面照片或者营业执照、个人资料页、持照人签名页"), HttpStatus.OK);
                }
            }

            if (Check.NuNObj(voucherFrontPicOld) || Check.NuNObj(voucherBackPicOld) || thirdPic) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请按照顺序上传证件正面、反面、" +
                        "本人手持证件照，手持照片要求可以同时看到您的面部、证件正面及证件号码"), HttpStatus.OK);
            }


            CustomerBaseMsgEntity customerBaseSave = new CustomerBaseMsgEntity();
            customerBaseSave.setUid(authMsgDto.getUid());
            customerBaseSave.setRealName(authMsgDto.getRealName());
            customerBaseSave.setIdType(authMsgDto.getIdType());
            customerBaseSave.setIdNo(authMsgDto.getIdNo());
            //提交认证信息后更改为房东角色
            customerBaseSave.setIsLandlord(IsLandlordEnum.IS_LANDLORD.getCode());
            //已经上传身份证
            customerBaseSave.setIsIdentityAuth(YesOrNoEnum.YES.getCode());
            customerBaseSave.setAuditStatus(AuditStatusEnum.SUBMITAUDIT.getCode());//待审核状态

            String customerJson = JsonEntityTransform.Object2Json(customerBaseSave);

            if (authMsgDto.getIdType() == CustomerIdTypeEnum.ID.getCode()) {
                //证件类型是身份证时，同步信息到自如网，验证证件信息是否被占用
                Map<String, String> savePar = new HashMap<>();
                savePar.put("uid", uid);
                savePar.put("real_name", customerBaseSave.getRealName());
                savePar.put("cert_type", ValueUtil.getStrValue(customerBaseSave.getIdType()));
                savePar.put("cert_num", ValueUtil.getStrValue(customerBaseSave.getIdNo()));
                String sign = SignUtils.md5AppkeySign(appKeyValue, savePar);
                savePar.put("sign", sign);
                savePar.put("appid", saveAppid);
                savePar.put("auth_code", AUTH_CODE);
                savePar.put("auth_secret_key", AUTH_SECRET_KEY);


                LogUtil.info(LOGGER, "同步用户信息到自如网参数：param:{}", JsonEntityTransform.Object2Json(savePar));
                String rstJson = CloseableHttpsUtil.sendFormPost(saveCertUid, savePar);
                JSONObject json = JSONObject.parseObject(rstJson);
                LogUtil.info(LOGGER, "同步用户信息到自如网返回结果：rstJson:{}", rstJson);
                Integer code = json.getInteger("error_code");
                AccountSignEnum signEnum = AccountSignEnum.getAccountSignEnumByCode(code);
                if (signEnum == null) {
                    return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("同步用户信息到自如网失败"), HttpStatus.OK);
                }
                if (code != AccountSignEnum.ACCOUNT_CODE_0.getCode()) {
                    String msg = signEnum.getShowTip();
                    LogUtil.info(LOGGER, "同步用户信息到自如网失败,失败原因" + json.getString("error_message"));
                    return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(msg), HttpStatus.OK);
                }
            }

            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerInfoService.updateCustomerInfo(customerJson));
            if (dto.getCode() != DataTransferObject.SUCCESS) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            }

            if (LOGGER.isDebugEnabled()) {
                LogUtil.info(LOGGER, "保存认证信息耗时{}ms", System.currentTimeMillis() - begin);
            }
            JSONObject jsonMsg = new JSONObject();
            jsonMsg.put("msg", dto.getMsg());
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(jsonMsg), HttpStatus.OK);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }

    }

    /**
	 * 是否港澳台身份证
	 * @param passport
	 * @return
	 */
	public static boolean isGATIDCard(String passport){
		if(Check.NuNStr(passport)) return false;
		String gATIDCardRegex = "^[a-zA-Z0-9\\/\\(\\)]{8,15}$";
		return passport.matches(gATIDCardRegex);
	}


    /**
     * 保存投诉建议
     *
     * @param request
     * @return
     * @author zl
     * @created 2016年9月8日
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/${LOGIN_AUTH}/saveAdvise")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> saveAdvise(HttpServletRequest request) {


        try {
            long begin = System.currentTimeMillis();

            String uid = (String) request.getAttribute("uid");
            LogUtil.info(LOGGER, "保存投诉建议 uid：" + uid);
            if (Check.NuNStr(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
                        MessageSourceUtil.getChinese(messageSource, ApiMessageConst.UID_NULL)), HttpStatus.OK);
            }

            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            if (Check.NuNObj(paramJson)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数为空"), HttpStatus.OK);
            }
            LogUtil.info(LOGGER, "保存投诉建议请求参数：" + paramJson);
            Map<String, String> param = JsonEntityTransform.json2Object(paramJson, Map.class);
            if (Check.NuNObj(param)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数错误"), HttpStatus.OK);
            }
            String complain = param.get("complain");

            if (Check.NuNStr(complain)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("投诉建议不能为空"), HttpStatus.OK);
            }
            if (!Check.NuNStr(complain) && complain.length() > 50) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("投诉建议不能超过50字"), HttpStatus.OK);
            }

            String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
            CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
            });

            SysComplainEntity sysComplainEntity = new SysComplainEntity();
            sysComplainEntity.setComplainUid(uid);
            sysComplainEntity.setComplainUsername(entity.getRealName());
            sysComplainEntity.setComplainMphone(entity.getCustomerMobile());
            sysComplainEntity.setContent(complain);

            String resultJson = sysComplainService.save(JsonEntityTransform.Object2Json(sysComplainEntity));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (dto.getCode() != DataTransferObject.SUCCESS) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            }
            LogUtil.info(LOGGER, "保存投诉建议耗时{}ms", System.currentTimeMillis() - begin);
            JSONObject json = new JSONObject();
            json.put("msg", "success");
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(json), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }

    }


    /**
     * 用户退出登录
     *
     * @param request
     * @return
     * @author liujun
     * @created 2016年9月7日
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/${LOGIN_UNAUTH}/loginOut")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> loginOut(HttpServletRequest request) {
        String token = request.getParameter("token");
        try {
            Map<String, String> headerMap = this.getHeaderMap(request);
            if (!Check.NuNObj(headerMap) && !Check.NuNStr(token)) {
                headerMap.put("token", token);
                String loginOut = CloseableHttpsUtil.sendFormPost(this.USER_LOGINOUT_POST, null, headerMap);
                LogUtil.info(LOGGER, "注册参数paramsMap={},注册请求返回参数loginOut={}", loginOut);
                Map<String, String> mapResult = (Map<String, String>) JsonEntityTransform.json2Map(loginOut);

                if (!Check.NuNMap(mapResult) && LoginCodeEnum.SUCCESS.getCode().equals(mapResult.get("code"))) {
                    HttpSession session = request.getSession();
                    session.invalidate();
                    LogUtil.info(LOGGER, "清除token={}成功", token);
                    return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK("清除token成功"), HttpStatus.OK);
                } else {
                    LogUtil.info(LOGGER, "清除token={}，失败", token);
                    return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("清除token失败"), HttpStatus.OK);
                }
            } else {
                LogUtil.info(LOGGER, "清除token={}，失败", token);
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("清除token失败"), HttpStatus.OK);
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "清除token={}，异常", token);
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(e.getMessage()), HttpStatus.OK);
        }
    }

    /**
     * 校验图形验证码 如果验证码校验成功,直接发送短信
     *
     * @param request
     * @return
     * @author jixd
     * @created 2016年9月19日 下午5:06:17
     */
    @RequestMapping("/${LOGIN_AUTH}/checkImgCode")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> checkImgCode(HttpServletRequest request) {
        try {
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "paramJson={}", paramJson);
            if (Check.NuNStrStrict(paramJson)) {
                LogUtil.info(LOGGER, "checkImgCode is null");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("验证码不能为空"), HttpStatus.OK);
            }
            JSONObject obj = JSONObject.parseObject(paramJson);
            String imgId = obj.getString("imgId");
            String imgCode = obj.getString("imgCode");
            String areaCode = obj.getString("areaCode");
            String phone = obj.getString("phone");
            String uid = (String) request.getAttribute("uid");

            if (Check.NuNStr(areaCode)) {
                areaCode = "86";
            }
            if (!areaCode.matches("^[0-9]{2}$")) {
                LogUtil.info(LOGGER, "checkImgCode areacode error");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("地区码错误"), HttpStatus.OK);
            }

            if (!"86".equals(areaCode)) {//外国不校验图形验证码，不发短信
                LogUtil.info(LOGGER, "check img code success, areeCode :{}", areaCode);
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("status", 0);
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(jsonObj), HttpStatus.OK);
            } else {
                Map<String, String> headerMap = getHeaderMap(request);
                if (Check.NuNObj(headerMap)) {
                    LogUtil.info(LOGGER, "checkImgCode getHeaderMap is null");
                    return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务异常"), HttpStatus.OK);
                }

                if (Check.NuNStr(imgId) || Check.NuNStr(imgCode)) {
                    LogUtil.info(LOGGER, "checkImgCode imgId or imgCode is null");
                    return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("验证码错误"), HttpStatus.OK);
                }

                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("imgId", imgId);
                paramsMap.put("imgVValue", imgCode);

                //校验验证码
                String registerResult = "";
                try {
                    registerResult = CloseableHttpsUtil.sendFormPost(IMG_VERIFY_CODE_GET, paramsMap, headerMap);
                } catch (Exception e) {
                    LogUtil.error(LOGGER, "获取图形验证码异常：{}", e);
                    return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("图形校验码异常"), HttpStatus.OK);
                }
                LogUtil.info(LOGGER, "check img code return :{}", registerResult);
                JSONObject resultJson = JSONObject.parseObject(registerResult);
                //成功返回
                if (!LoginCodeEnum.SUCCESS.getCode().equals(resultJson.getString("code"))) {
                    LogUtil.info(LOGGER, "chech img code no pass");
                    String msg = resultJson.getString("message");
                    return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(msg), HttpStatus.OK);
                }
            }

            //中国的手机号码 发送短信验证码
            String vcode = randomUtil.getNumrOrChar(6, "num");
            if (!Check.NuNStr(vcode)) {
                String key = RedisKeyConst.getMobileCodeKey(uid, phone);
                SmsRequest smsRequest = new SmsRequest();
                smsRequest.setMobile(phone);
                smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.MOBILE_CODE.getCode()));
                Map<String, String> paMap = new HashMap<String, String>();
                paMap.put("{1}", vcode);
                paMap.put("{2}", String.valueOf(RedisKeyConst.MOBILE_CODE_CACHE_TIME / 60));
                smsRequest.setParamsMap(paMap);
                LogUtil.info(LOGGER, "checkImgCode sendSmsByCode param :{}", JsonEntityTransform.Object2Json(smsRequest));
                String smsJson = this.smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
                LogUtil.info(LOGGER, "checkImgCode sendSmsByCode return :{}", smsJson);
                DataTransferObject smsDto = JsonEntityTransform.json2DataTransferObject(smsJson);
                if (smsDto.getCode() == DataTransferObject.SUCCESS) {
                    try {
                        LogUtil.info(LOGGER, "checkImgCode vcode={}", vcode);
                        this.redisOperations.setex(key, RedisKeyConst.MOBILE_CODE_CACHE_TIME, vcode);
                    } catch (Exception e) {
                        LogUtil.error(LOGGER, "redis错误e={}", e);
                    }
                    JSONObject jsonObj = new JSONObject();
                    if (IMG_VERIFY_CODE_GET.contains("qa")) {
                        jsonObj.put("vcode", vcode);
                    }
                    LogUtil.info(LOGGER, "check img code success");
                    return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(jsonObj), HttpStatus.OK);
                } else {
                    LogUtil.info(LOGGER, "check img code fail");
                    return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("短信发送失败"), HttpStatus.OK);
                }
            } else {
                LogUtil.info(LOGGER, "get vcode fail");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("校验图形验证码异常"), HttpStatus.OK);
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "校验图形验证码异常:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("校验图形验证码异常"), HttpStatus.OK);
        }

    }


    /**
     * 验证短信验证码，如果短信验证码通过直接保存
     *
     * @param request
     * @return
     * @author jixd
     * @created 2016年9月19日 下午5:53:47
     */
    @RequestMapping("/${LOGIN_AUTH}/validateSmsCode")
    @ResponseBody
    private ResponseEntity<ResponseSecurityDto> validateSmsCode(HttpServletRequest request) {
        String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
        if (Check.NuNStr(paramJson)) {
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数不能为空"), HttpStatus.OK);
        }
        LogUtil.info(LOGGER, "[validateSmsCode] param:{}", paramJson);
        JSONObject obj = JSONObject.parseObject(paramJson);
        String phone = obj.getString("phone");
        String smsCode = obj.getString("smsCode");
        String areaCode = obj.getString("areaCode");
        String uid = (String) request.getAttribute("uid");
        if (Check.NuNStr(phone)) {
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("手机号码不能为空"), HttpStatus.OK);
        }
        if (Check.NuNStr(areaCode) || "86".equals(areaCode)) {
            areaCode = "86";
            if (!RegExpUtil.isMobilePhoneNum(phone)) {//国内手机号校验
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("手机号码格式不正确"), HttpStatus.OK);
            }
        } else {//国外手机号校验
            if (phone.length() > 25 || !phone.matches("^[0-9]{1,25}$")) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("手机号码格式不正确"), HttpStatus.OK);
            }
        }

        if ("86".equals(areaCode)) {//只有国内手机校验短信码
            if (Check.NuNStr(smsCode)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("验证码为空"), HttpStatus.OK);
            }
            String rCode = "";
            try {
                rCode = this.redisOperations.get(RedisKeyConst.getMobileCodeKey(uid, phone));
            } catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误e={}", e);
            }
            if (Check.NuNStr(rCode)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("验证码已失效"), HttpStatus.OK);
            }
            if (!smsCode.equals(rCode)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("验证码错误"), HttpStatus.OK);
            }
        }

        //验证码校验成功以后更新用户信息
        String upJson = customerInfoService.updateLandPhone(phone, uid, areaCode);
        LogUtil.info(LOGGER, "[validateSmsCode] updateLandPhone return :{}", upJson);

        DataTransferObject upDto = JsonEntityTransform.json2DataTransferObject(upJson);
        if (upDto.getCode() == DataTransferObject.ERROR) {
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(upDto.getMsg()), HttpStatus.OK);
        }

        Map<String, String> json = new HashMap<>();
        json.put("msg", "success");
        return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(json), HttpStatus.OK);
    }

    /**
     * 切换房东角色
     *
     * @param request
     * @return
     * @author jixd
     * @created 2016年10月17日 下午5:53:47
     */
    @RequestMapping("/${LOGIN_AUTH}/toLandlordRole")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> changeToLandlordRole(HttpServletRequest request) {
        String uid = (String) request.getAttribute("uid");
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setLandlordUid(uid);
        orderRequest.setRequestType(2);
        orderRequest.setPage(1);
        orderRequest.setLimit(1);
        JSONObject resultObj = new JSONObject();
        String orderList = orderCommonService.getOrderListByCondiction(JsonEntityTransform.Object2Json(orderRequest));
        DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderList);
        int tab = 1;
        if (orderDto.getCode() == DataTransferObject.SUCCESS) {
            int size = (int) orderDto.getData().get("size");
            //如果存在订单则跳转到订单页，否则调转到房源页
            if (size > 0) {
                tab = 0;
            }
        }
        resultObj.put("tab", tab);
        LogUtil.debug(LOGGER, "【切换房东跳转返回】result={}", resultObj.toJSONString());
        return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultObj), HttpStatus.OK);
    }

    /**
     * 获取公用头信息
     *
     * @param request
     * @return
     * @author yd
     * @created 2016年5月11日 下午11:51:11
     */
    private Map<String, String> getHeaderMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);

        }
        LoginHeaderDto loginHeaderDto = new LoginHeaderDto();
        if (!Check.NuNStr(map.get("client-version")))
            loginHeaderDto.setClientVersion(map.get("client-version"));
        if (!Check.NuNStr(map.get("client-type")))
            loginHeaderDto.setClientType(Integer.valueOf(map.get("client-type")));
        if (!Check.NuNStr(map.get("user-agent")))
            loginHeaderDto.setUserAgent(map.get("user-agent"));

        if (Check.NuNStr(loginHeaderDto.getClientVersion())) {
            loginHeaderDto.setClientVersion("1.0");
        }
        if (Check.NuNObj(loginHeaderDto.getClientType())) {
            loginHeaderDto.setClientType(3);
        }
        loginHeaderDto.setAccept(minsuWebAccept);
        loginHeaderDto.setSys(minsuWebSys);
        Map<String, String> headerMap = new HashMap<String, String>();
        if (!checkLoginHeader(loginHeaderDto)) {
            LogUtil.debug(LOGGER, "登录头信息验证失败，当前头信息loginHeaderDto={}", loginHeaderDto.toString());
            return null;
        }

        headerMap.put(HeaderParamName.ACCEPT, loginHeaderDto.getAccept());
        headerMap.put(HeaderParamName.CLIENT_TYPE, String.valueOf(loginHeaderDto.getClientType()));
        headerMap.put(HeaderParamName.CLIENT_VERSION, loginHeaderDto.getClientVersion());
        headerMap.put(HeaderParamName.REQUEST_ID, loginHeaderDto.getRequestId());
        headerMap.put(HeaderParamName.SYS, loginHeaderDto.getSys());
        headerMap.put(HeaderParamName.USER_AGENT, loginHeaderDto.getUserAgent());
        return headerMap;
    }

    /**
     * @param loginHeaderDto
     * @return
     * @author yd
     * @created 2016年5月4日 下午11:25:00
     */
    private boolean checkLoginHeader(LoginHeaderDto loginHeaderDto) {
        if (Check.NuNObj(loginHeaderDto)
                || Check.NuNStr(loginHeaderDto.getAccept())
                || Check.NuNStr(loginHeaderDto.getClientVersion())
                || Check.NuNStr(loginHeaderDto.getRequestId())
                || Check.NuNStr(loginHeaderDto.getSys())
                || Check.NuNStr(loginHeaderDto.getUserAgent())
                || Check.NuNObj(loginHeaderDto.getClientType())) {
            return false;
        }
        return true;
    }

    /**
     * 查询认证信息V1
     *
     * @param request
     * @return
     * @author bushujie
     * @created 2017年5月9日 下午2:51:08
     */
    @RequestMapping("/${LOGIN_AUTH}/getCustomerAuthInfoV1")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> getCustomerAuthInfoV1(HttpServletRequest request) {

        try {
            long begin = System.currentTimeMillis();

            String uid = (String) request.getAttribute("uid");
            LogUtil.info(LOGGER, "查询认证信息 uid：" + uid);
            if (Check.NuNStr(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
                        MessageSourceUtil.getChinese(messageSource, ApiMessageConst.UID_NULL)), HttpStatus.OK);
            }

            CustomerAuthVo authVo = new CustomerAuthVo();

            //个人基本信息
            String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
            CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
            });

            BeanUtils.copyProperties(authVo, entity);

            //证件类型名称
            String idName = CustomerIdTypeEnum.getCustomerIdTypeByCode(entity.getIdType()).getName();
            authVo.setIdTypeName(idName);

            //证件正面照
            CustomerPicDto picDto = new CustomerPicDto();
            picDto.setUid(uid);
            picDto.setPicType(CustomerPicTypeEnum.ZJZM.getCode());
            String picJson1 = JsonEntityTransform.Object2Json(picDto);
            String picResultJosn1 = customerMsgManagerService.getCustomerPicByType(picJson1);
            CustomerPicMsgEntity customerPic1 = SOAResParseUtil.getValueFromDataByKey(picResultJosn1,
                    "customerPicMsgEntity", CustomerPicMsgEntity.class);
            String cardPicUrl1 = "";
            if (!Check.NuNObj(customerPic1)) {
                cardPicUrl1 = PicUtil.getFullPic(picBaseAddrMona, customerPic1.getPicBaseUrl(), customerPic1.getPicSuffix(), detail_big_pic);
                authVo.setVoucherFrontPicUrl(cardPicUrl1);
            }

            //证件反面照
            picDto.setPicType(CustomerPicTypeEnum.ZJFM.getCode());
            String picJson2 = JsonEntityTransform.Object2Json(picDto);
            String picResultJosn2 = customerMsgManagerService.getCustomerPicByType(picJson2);
            CustomerPicMsgEntity customerPic2 = SOAResParseUtil.getValueFromDataByKey(picResultJosn2,
                    "customerPicMsgEntity", CustomerPicMsgEntity.class);
            String cardPicUrl2 = "";
            if (!Check.NuNObj(customerPic2)) {
                cardPicUrl2 = PicUtil.getFullPic(picBaseAddrMona, customerPic2.getPicBaseUrl(), customerPic2.getPicSuffix(), detail_big_pic);
                authVo.setVoucherBackPicUrl(cardPicUrl2);
            }

            //手持证件照
            picDto.setPicType(CustomerPicTypeEnum.ZJSC.getCode());
            String picJson3 = JsonEntityTransform.Object2Json(picDto);
            String picResultJosn3 = customerMsgManagerService.getCustomerPicByType(picJson3);
            CustomerPicMsgEntity customerPic3 = SOAResParseUtil.getValueFromDataByKey(picResultJosn3,
                    "customerPicMsgEntity", CustomerPicMsgEntity.class);
            String cardPicUrl3 = "";
            if (!Check.NuNObj(customerPic3)) {
                cardPicUrl3 = PicUtil.getFullPic(picBaseAddrMona, customerPic3.getPicBaseUrl(), customerPic3.getPicSuffix(), detail_big_pic);
                authVo.setVoucherHandPicUrl(cardPicUrl3);
            }

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("realName", authVo.getRealName());
            resultMap.put("idType", authVo.getIdType());
            resultMap.put("idNo", authVo.getIdNo());
            resultMap.put("isIdentityAuth", authVo.getIsIdentityAuth());
            resultMap.put("idTypeName", authVo.getIdTypeName());
            resultMap.put("voucherFrontPicUrl", authVo.getVoucherFrontPicUrl());
            resultMap.put("voucherBackPicUrl", authVo.getVoucherBackPicUrl());
            resultMap.put("voucherHandPicUrl", authVo.getVoucherHandPicUrl());
            resultMap.put("idTypeMap", authVo.getIdTypeMap());
            resultMap.put("auditStatus", authVo.getAuditStatus());
            //设置证件类型温馨提示
            Map<Integer, String> customerIdExplainMap = CustomerIdTypeEnum.getCustomerIdExplainMap();
            for (Integer key : customerIdExplainMap.keySet()) {
                String resultJson = staticResourceService.findStaticResListByResCode(customerIdExplainMap.get(key));
                List<StaticResourceVo> resList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "staticResList", StaticResourceVo.class);
                if (!Check.NuNCollection(resList)) {
                    customerIdExplainMap.put(key, resList.get(0).getResContent());
                } else {
                    customerIdExplainMap.remove(key);
                }
            }
            resultMap.put("idExplainMap", customerIdExplainMap);
            LogUtil.debug(LOGGER, "认证信息查询耗时{}ms,请求结果：{}", System.currentTimeMillis() - begin, JsonEntityTransform.Object2Json(resultMap));
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }

    }

    /**
     * 资质认证是否完成展示接口
     *
     * @param request
     * @return ResponseEntity<ResponseSecurityDto>
     * @author wangwt
     * @created 2017年06月16日 11:01:51
     */
    @RequestMapping(value = "/${LOGIN_AUTH}/getCertification")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> getCertification(HttpServletRequest request) {
        try {
            long begin = System.currentTimeMillis();
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "getCertification paramJson :{}", paramJson);
            if (Check.NuNStr(paramJson)) {
                LogUtil.info(LOGGER, "getCertification get paramJson error");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("get paramJson error"), HttpStatus.OK);
            }
            BaseParamDto paramDto = JsonEntityTransform.json2Object(paramJson, BaseParamDto.class);
            if (Check.NuNObj(paramDto)) {
                LogUtil.info(LOGGER, "getCertification get paramDto error");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("get paramDto error"), HttpStatus.OK);
            }
            String uid = paramDto.getUid();
            if (Check.NuNStr(uid)) {
                LogUtil.info(LOGGER, "getCertification uid is null");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("uid is null"), HttpStatus.OK);
            }

            String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
            LogUtil.info(LOGGER, "getCustomerBaseMsgEntitybyUid return :{}", customerJson);
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
            CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
            });
            //校验 CustomerBaseMsgEntity
            if (Check.NuNObj(entity)) {
                LogUtil.info(LOGGER, "getCertification customer not found");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("customer not found"), HttpStatus.OK);
            }

            Map<String, Object> resultMap = getContactStatus(entity, begin);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "getCertification error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }

    /**
     * 获取资质认证状态
     *
     * @param
     * @return
     * @author wangwt
     * @created 2017年07月04日 17:30:31
     */
    private Map<String, Object> getContactStatus(CustomerBaseMsgEntity entity, long begin) throws Exception {
        //身份信息
        Integer isIdentityAuth = entity.getIsIdentityAuth();
        //联系方式
        Integer isContactAuth = entity.getIsContactAuth();
        //真实头像
        Integer isUploadIcon = entity.getIsUploadIcon();
        //头像 个人介绍  昵称
        Integer isFinishHead = YesOrNoEnum.NO.getCode();

        String nickName = entity.getNickName();
        Integer isFinishNickName = YesOrNoEnum.NO.getCode();
        if (!Check.NuNStr(nickName)) {
            isFinishNickName = YesOrNoEnum.YES.getCode();
        }
        Integer isFinishIntroduce = YesOrNoEnum.NO.getCode();
        String uid = entity.getUid();
        DataTransferObject customerExtDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.selectCustomerExtByUid(uid));
        if (customerExtDto.getCode() == DataTransferObject.SUCCESS) {
            CustomerBaseMsgExtEntity customerBaseMsgExt = customerExtDto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {
            });
            if (!Check.NuNObj(customerBaseMsgExt) && !Check.NuNStr(customerBaseMsgExt.getCustomerIntroduce())) {
                isFinishIntroduce = YesOrNoEnum.YES.getCode();
            }
        }

        if (isUploadIcon == YesOrNoEnum.YES.getCode()
                && isFinishNickName == YesOrNoEnum.YES.getCode()
                && isFinishIntroduce == YesOrNoEnum.YES.getCode()) {
            isFinishHead = YesOrNoEnum.YES.getCode();
        }

        int fullFlag = YesOrNoEnum.NO.getCode();
        if (isIdentityAuth == YesOrNoEnum.YES.getCode()
                && isContactAuth == YesOrNoEnum.YES.getCode()
                && isFinishHead == YesOrNoEnum.YES.getCode()) {
            fullFlag = YesOrNoEnum.YES.getCode();
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("isFinishHead", isFinishHead);
        resultMap.put("isIdentityAuth", isIdentityAuth);
        resultMap.put("isContactAuth", isContactAuth);
        resultMap.put("fullFlag", fullFlag);
        if (LOGGER.isDebugEnabled()) {
            LogUtil.debug(LOGGER, "getCertification use : {}ms, return ：{}", System.currentTimeMillis() - begin,
                    JsonEntityTransform.Object2Json(resultMap));
        } else {
            LogUtil.info(LOGGER, "getCertification return：{}", JsonEntityTransform.Object2Json(resultMap));
        }
        return resultMap;
    }

    /**
     * 获取联系信息
     *
     * @param request
     * @return ResponseEntity<ResponseSecurityDto>
     * @author wangwt
     * @created 2017年06月19日 10:05:21
     */
    @RequestMapping(value = "/${LOGIN_AUTH}/getContactInfo")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> getContactInfo(HttpServletRequest request) {
        try {
            long begin = System.currentTimeMillis();
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "getContactInfo paramJson :{}", paramJson);
            if (Check.NuNStr(paramJson)) {
                LogUtil.error(LOGGER, "getContactInfo get paramJson error");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("get paramJson error"), HttpStatus.OK);
            }
            BaseParamDto paramDto = JsonEntityTransform.json2Object(paramJson, BaseParamDto.class);
            if (Check.NuNObj(paramDto)) {
                LogUtil.error(LOGGER, "getContactInfo get paramDto error");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("get paramDto error"), HttpStatus.OK);
            }
            String uid = paramDto.getUid();
            if (Check.NuNStr(uid)) {
                LogUtil.info(LOGGER, "getContactInfo uid is null");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("uid is null"), HttpStatus.OK);
            }
            String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
            LogUtil.info(LOGGER, "getCustomerBaseMsgEntitybyUid return :{}", customerJson);
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
            CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
            });
            //校验 CustomerBaseMsgEntity
            if (Check.NuNObj(entity)) {
                LogUtil.info(LOGGER, "getContactInfo customer not found");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("customer not found"), HttpStatus.OK);
            }
            //电话号码
            String customerMobile = entity.getCustomerMobile();
            //国家码
            String countryCode = entity.getCountryCode();

            String sendCertificateCode = "0";
            if (!Check.NuNStr(countryCode) && "86".equals(countryCode)) {
                sendCertificateCode = "1";
            }
            String resultJson = confCityService.findNationCodeList();
            List<NationCodeEntity> nationCodeList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", NationCodeEntity.class);
            FieldSelectListVo fieldSelectListVo = new FieldSelectListVo();
            String nationName = "";
            if (!Check.NuNCollection(nationCodeList)) {
                List<FieldSelectVo> list = new ArrayList<>();
                for (NationCodeEntity nationCodeEntity : nationCodeList) {
                    String cc = nationCodeEntity.getCountryCode();
                    String nn = nationCodeEntity.getNationName();
                    FieldSelectVo<String> fieldSelectVo = new FieldSelectVo(cc, nn, "", false);
                    if (!Check.NuNStr(cc) && countryCode.equals(cc)) {
                        fieldSelectVo.setIsSelect(true);
                        nationName = nn;
                    }
                    list.add(fieldSelectVo);
                }
                fieldSelectListVo.setList(list);
                fieldSelectListVo.setIsEdit(false);
            }

            boolean isEdit = false;
            Integer auditStatus = entity.getAuditStatus();
            if (AuditStatusEnum.COMPLETE.getCode() != auditStatus) {
                isEdit = true;
            }

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("customerMobile", customerMobile);
            resultMap.put("nationCode", countryCode);
            resultMap.put("nationName", nationName);
            resultMap.put("sendCertificateCode", sendCertificateCode);
            resultMap.put("areaList", fieldSelectListVo);
            resultMap.put("isEdit", isEdit);
            if (LOGGER.isDebugEnabled()) {
                LogUtil.debug(LOGGER, "getContactInfo use : {}ms, return ：{}", System.currentTimeMillis() - begin,
                        JsonEntityTransform.Object2Json(resultMap));
            } else {
                LogUtil.info(LOGGER, "getContactInfo return：{}", JsonEntityTransform.Object2Json(resultMap));
            }
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "getContactInfo error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }

    /**
     * 获取身份信息
     *
     * @param
     * @return
     * @author wangwt
     * @created 2017年06月20日 10:31:26
     */
    @RequestMapping(value = "/${LOGIN_AUTH}/getIdentityInfo")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> getIdentityInfo(HttpServletRequest request) {
        try {
            long begin = System.currentTimeMillis();
            String uid = (String) request.getAttribute("uid");
            if (Check.NuNStr(uid)) {
                LogUtil.info(LOGGER, "getIdentityInfo uid is null");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
                        MessageSourceUtil.getChinese(messageSource, ApiMessageConst.UID_NULL)), HttpStatus.OK);
            }
            String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
            LogUtil.info(LOGGER, "getCustomerBaseMsgEntitybyUid return :{}", customerJson);
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
            CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
            });
            //校验 CustomerBaseMsgEntity
            if (Check.NuNObj(entity)) {
                LogUtil.info(LOGGER, "getIdentityInfo customer not found");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("没有找到对应的用户"), HttpStatus.OK);
            }

            boolean isEdit = false;
            Integer auditStatus = entity.getAuditStatus();
            if (AuditStatusEnum.COMPLETE.getCode() != auditStatus) {
                isEdit = true;
            }
            //真实姓名
            String realName = entity.getRealName();
            //证件类型
            Integer idType = entity.getIdType();
            //证件号码
            String idNo = entity.getIdNo();
            //证件照
            LogUtil.info(LOGGER, "getCustomerPicByType param :{}", uid);
            String picResultJson = customerMsgManagerService.getCustomerPicByUid(uid);
            LogUtil.info(LOGGER, "getCustomerPicByType return :{}", picResultJson);
            DataTransferObject picDto = JsonEntityTransform.json2DataTransferObject(picResultJson);
            if (picDto.getCode() != DataTransferObject.SUCCESS) {
                LogUtil.info(LOGGER, "getIdentityInfo get pic error");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("获取图片信息失败"), HttpStatus.OK);
            }
            List<CustomerPicMsgEntity> picList = picDto.parseData("list", new TypeReference<List<CustomerPicMsgEntity>>() {
            });
            if (Check.NuNCollection(picList)) {
                LogUtil.info(LOGGER, "getIdentityInfo get picList is null");
                picList = new ArrayList<>();
            }

            Map<String, Object> resultMap = new HashMap<>();
            for (CustomerPicMsgEntity picEntity : picList) {
                String picUrl = PicUtil.getFullPic(picBaseAddrMona, picEntity.getPicBaseUrl(),
                        picEntity.getPicSuffix(), detail_big_pic);
                Integer picType = picEntity.getPicType();
                if (picType == CustomerPicTypeEnum.ZJZM.getCode()) {
                    resultMap.put("voucherFrontPicUrl", picUrl);
                    continue;
                }
                if (picType == CustomerPicTypeEnum.ZJFM.getCode()) {
                    resultMap.put("voucherBackPicUrl", picUrl);
                    continue;
                }
                if (picType == CustomerPicTypeEnum.ZJSC.getCode() || picType == CustomerPicTypeEnum.YYZZ.getCode()) {
                    resultMap.put("voucherHandPicUrl", picUrl);
                    continue;
                }
            }


            //证件类型list
            List<CustomerIdTypeEnum> idTypeList = CustomerIdTypeEnum.getALLCustomerIdTypeEnums();
            Header header = getHeader(request);
            LogUtil.info(LOGGER, "verisonCode:{}", header.getVersionCode());
            if(!Check.NuNObj(header.getVersionCode()) && header.getVersionCode() < 100023){
            	idTypeList.remove(CustomerIdTypeEnum.getCustomerIdTypeByCode(CustomerIdTypeEnum.GAT_ID.getCode()));
            	if(CustomerIdTypeEnum.GAT_ID.getCode()==idType){
            		idNo=null;
            	}
            }
            Map<String, Object> idTypeMap = new HashMap<>();

            if (!Check.NuNCollection(idTypeList)) {
                List<FieldSelectVo> list = new ArrayList<>();
                String selectName = "身份证";
                for (CustomerIdTypeEnum customerIdTypeEnum : idTypeList) {
                    Integer cc = customerIdTypeEnum.getCode();
                    String nn = customerIdTypeEnum.getName();
                    String resultJson = staticResourceService.findStaticResListByResCode(customerIdTypeEnum.getExplainKey());
                    List<StaticResourceVo> resList = SOAResParseUtil.getListValueFromDataByKey(resultJson,
                            "staticResList", StaticResourceVo.class);
                    if (!Check.NuNCollection(resList)) {
                        String resContent = resList.get(0).getResContent();
                        FieldSelectVo<String> fieldSelectVo = new FieldSelectVo(cc, nn, resContent, false);
                        if (!Check.NuNObj(cc) && cc.equals(idType)) {
                            fieldSelectVo.setIsSelect(true);
                            selectName = nn;
                        }
                        list.add(fieldSelectVo);
                    }
                }

                //如果之前没有设置证件类型，默认选中身份证
                boolean hasSelect = false;
                for (FieldSelectVo<String> fieldSelectVo : list) {
                    hasSelect = hasSelect || fieldSelectVo.getIsSelect();
                }
                if (!hasSelect) {
                    list.get(0).setIsSelect(true);
                }
                idTypeMap.put("list", list);
                idTypeMap.put("selectName", selectName);
            }

            //组装返回的结果
            resultMap.put("realName", realName);
            resultMap.put("idTypeList", idTypeMap);
            resultMap.put("idNo", idNo);
            resultMap.put("isEdit", isEdit);

            if (LOGGER.isDebugEnabled()) {
                LogUtil.debug(LOGGER, "getIdentityInfo use : {}ms, return ：{}", System.currentTimeMillis() - begin, JsonEntityTransform.Object2Json(resultMap));
            } else {
                LogUtil.info(LOGGER, "getIdentityInfo return：{}", JsonEntityTransform.Object2Json(resultMap));
            }
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "getIdentityInfo error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("getIdentityInfo error"), HttpStatus.OK);
        }
    }
}
