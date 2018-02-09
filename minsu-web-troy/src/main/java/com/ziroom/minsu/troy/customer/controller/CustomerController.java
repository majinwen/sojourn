/**
 * @FileName: CustomerController.java
 * @Package com.ziroom.minsu.troy.customer.controller
 * 
 * @author jixd
 * @created 2016年4月23日 下午8:02:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.customer.controller;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.*;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.JpushRequest;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.jpush.JpushUtils;
import com.ziroom.minsu.services.common.jpush.base.JpushConfig;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.sms.MessageUtils;
import com.ziroom.minsu.services.common.sms.base.SmsMessage;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerBehaviorService;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.api.inner.TelExtensionService;
import com.ziroom.minsu.services.customer.dto.*;
import com.ziroom.minsu.services.customer.entity.CustomerBehaviorVo;
import com.ziroom.minsu.services.customer.entity.CustomerDetailVo;
import com.ziroom.minsu.services.customer.entity.CustomerExt;
import com.ziroom.minsu.services.customer.entity.TelExtensionVo;
import com.ziroom.minsu.services.house.api.inner.HouseGuardService;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.*;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.tech.storage.client.domain.FileInfoResponse;
import com.ziroom.tech.storage.client.service.StorageService;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * <p>客户信息管理</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("customer")
public class CustomerController {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Resource(name="storageService")
	private StorageService storageService;

	@Resource(name="customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Resource(name="customer.telExtensionService")
	private TelExtensionService telExtensionService;

	@Resource(name="house.houseGuardService")
	private HouseGuardService houseGuardService;

	@Resource(name = "basedata.employeeService")
	private EmployeeService employeeService;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;


	@Value("#{'${passport_save_cert_uid}'.trim()}")
	private String saveCertUid;

	@Value("#{'${passport_save_appid}'.trim()}")
	private String saveAppid;

	@Value("#{'${passport_app_key_value}'.trim()}")
	private String appKeyValue;

	@Value("#{'${pic_size}'.trim()}")
	private String picSize;

	@Value("#{'${storage_key}'.trim()}")
	private String storageKey;

	@Value("#{'${storage_limit}'.trim()}")
	private String storageLimit;

	@Value("#{'${pic_base_addr}'.trim()}")
	private String picBaseAddr;
	
	@Value("#{'${EUNOMIA_URL}'.trim()}")
	private String EUNOMIA_URL;
	
	@Value("#{'${SENSITIVE_URL}'.trim()}")
	private String SENSITIVE_URL;
	
	@Value("#{'${EUNOMIA_USERNAME}'.trim()}")
	private String EUNOMIA_USERNAME;
	
	@Value("#{'${EUNOMIA_PASSWORD}'.trim()}")
	private String EUNOMIA_PASSWORD;

	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;
	
	@Resource(name="customer.customerBehaviorService")
	private CustomerBehaviorService customerBehaviorService;


	/**
	 * 
	 * 列表页
	 *
	 * @author jixd
	 * @created 2016年4月25日 下午1:58:12
	 *
	 */
	@RequestMapping("listCustomerMsg")
	public void listCustomerMsg(HttpServletRequest request){
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
		request.setAttribute("picBaseAddr", picBaseAddr);
	}

    /**
     * 用户信息详情只读页面，包含用户行为记录列表
     * @param request
     * @param uid
     */
	@RequestMapping("customerDetailMsg")
	public void customerDetailMsg(HttpServletRequest request,String uid){
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
		request.setAttribute("picBaseAddr", picBaseAddr);
		if (Check.NuNStr(uid)) {
			request.setAttribute("uid", null);
		}
		request.setAttribute("uid", uid);
	}

	/**
	 * 
	 * 用户行为记录列表
	 * 
	 * @author zhangyl2
	 * @created 2017年10月15日 15:44
	 * @param 
	 * @return 
	 */
    @RequestMapping("getCustomerBehaviorList")
    @ResponseBody
    public PageResult getCustomerBehaviorList(CustomerBehaviorRequest customerBehaviorRequest) {
        if (Check.NuNObj(customerBehaviorRequest) || Check.NuNStr(customerBehaviorRequest.getUid())) {
            return new PageResult();
        }
        DataTransferObject customerBehaviorDto = JsonEntityTransform.json2DataTransferObject(customerBehaviorService.getCustomerBehaviorList(customerBehaviorRequest.toJsonStr()));
        if (customerBehaviorDto.getCode() == DataTransferObject.ERROR) {
            return new PageResult();
        }
        List<CustomerBehaviorVo> customerBehaviorList = customerBehaviorDto.parseData("customerBehaviorList", new TypeReference<List<CustomerBehaviorVo>>() {
        });

        for (CustomerBehaviorVo vo : customerBehaviorList) {
            // 行为属性名称
            vo.setAttributeName(CustomerBehaviorAttributeEnum.getNameByCode(vo.getAttribute()));
            // 角色名称
            vo.setRoleName(CustomerBehaviorRoleEnum.getNameByCode(vo.getRole()));
            // 行为类型名称
            if (vo.getRole() == CustomerBehaviorRoleEnum.LANDLORD.getCode()) {
                vo.setTypeName(LandlordBehaviorEnum.getNameByType(vo.getType()));
            } else {
                // 房客行为预留
            }
            // 用户行为创建类型(1-系统生成 2-人工录入)
            vo.setCreateTypeName(CustomerBehaviorCreateTypeEnum.getNameByCode(vo.getCreateType()));
            // 记录人（人工录入的显示为录入uid的角色姓名；系统生成的显示为admin）
            if (vo.getCreateType() == CustomerBehaviorCreateTypeEnum.SYSTEM_GENERATION.getCode()) {
                vo.setOperationName("admin");
            } else if (vo.getCreateType() == CustomerBehaviorCreateTypeEnum.MANUAL_GENERATION.getCode()) {
                try {
                    EmployeeEntity employ = findEmployeeEntityByFid(vo.getCreateFid());
                    if(!Check.NuNObj(employ)){
                        vo.setOperationName(employ.getEmpName());
                    }
                } catch (SOAParseException e) {
                    LogUtil.error(logger, "getCustomerBehaviorList#findEmployeeEntityByFid异常,empFid={}", vo.getCreateFid());
            }

            }
        }

        PageResult pageResult = new PageResult();
        pageResult.setRows(customerBehaviorList);
        pageResult.setTotal(customerBehaviorDto.parseData("total", new TypeReference<Long>() {
        }));
        return pageResult;
    }

    /**
     * 
     * 限制行为改为中性行为
     * 
     * @author zhangyl2
     * @created 2017年10月15日 13:35
     * @param 
     * @return 
     */
    @RequestMapping("updateCustomerBehaviorAttr")
    @ResponseBody
    public DataTransferObject updateCustomerBehaviorAttr(CustomerBehaviorEntity customerBehaviorEntity) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNObj(customerBehaviorEntity) || Check.NuNStr(customerBehaviorEntity.getFid())) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数非法");
            return dto;
        }

        CustomerBehaviorOperationLogEntity logEntity = new CustomerBehaviorOperationLogEntity();
        logEntity.setBehaviorFid(customerBehaviorEntity.getFid());
        logEntity.setEmpCode(UserUtil.getFullCurrentUser().getEmpCode());
        logEntity.setEmpName(UserUtil.getFullCurrentUser().getFullName());
        logEntity.setRemark("后台修改行为属性");

        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(customerBehaviorService.updateCustomerBehaviorAttr(logEntity.toJsonStr()));
        // 更改成功保存日志
        if (resultDto.getCode() == DataTransferObject.SUCCESS) {
            return dto;
        } else {
            LogUtil.error(logger, "updateCustomerBehavior logEntity={}", logEntity);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("修改失败");
            return dto;
        }
    }

    /**
     * 
     * 新增用户行为
     * 
     * @author zhangyl2
     * @created 2017年10月15日 15:45
     * @param 
     * @return 
     */
    @RequestMapping("addCustomerBehavior")
    @ResponseBody
    public DataTransferObject addCustomerBehavior(CustomerBehaviorEntity customerBehaviorEntity) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNObj(customerBehaviorEntity.getAttribute())
                || Check.NuNObj(customerBehaviorEntity.getRole())
                || Check.NuNObj(customerBehaviorEntity.getUid())
                || Check.NuNObj(customerBehaviorEntity.getType())
                || Check.NuNStr(customerBehaviorEntity.getRemark())) {

            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数非法");
            return dto;
        }
        customerBehaviorEntity.setFid(UUIDGenerator.hexUUID());
        // 人工录入的prove_fid与fid相同
        customerBehaviorEntity.setProveFid(customerBehaviorEntity.getFid());
        customerBehaviorEntity.setCreateFid(UserUtil.getFullCurrentUser().getEmployeeFid());
        customerBehaviorEntity.setCreateType(CustomerBehaviorCreateTypeEnum.MANUAL_GENERATION.getCode());
        customerBehaviorEntity.setScore(LandlordBehaviorEnum.getEnumByType(customerBehaviorEntity.getType()).getScore());
        dto = JsonEntityTransform.json2DataTransferObject(customerBehaviorService.saveCustomerBehavior(customerBehaviorEntity.toJsonStr()));

        return dto;
    }

	@RequestMapping("listCheckCustomerMsg")
	public void listCheckCustomerMsg(HttpServletRequest request){
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picBaseAddr", picBaseAddr);
		request.setAttribute("picSize", picSize);
		request.setAttribute("auditStatus", request.getParameter("auditStatus"));
		request.setAttribute("realName", request.getParameter("realName"));
		request.setAttribute("customerMobile", request.getParameter("customerMobile"));
		
		//客户信息审核      欧诺弥亚项目     获取AccessToken
		String accessToken = sensitiveWordCheck();
		request.setAttribute("access_token", accessToken);
		request.setAttribute("sensitiveUrl", SENSITIVE_URL);
	}

	@RequestMapping("listExtension")
	public void listExtension(){}


	/**
	 * 电话绑定列表
	 *
	 * @author afi
	 * @created 2016年4月25日 下午1:58:30
	 *
	 * @param telExtensionDto
	 * @return
	 */
	@RequestMapping("extensionDataList")
	@ResponseBody
	public PageResult extensionDataList(TelExtensionDto telExtensionDto){
		String result = telExtensionService.getExtensionVOByPage(JsonEntityTransform.Object2Json(telExtensionDto));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
		List<TelExtensionVo> customerList ;
		PageResult pageResult = new PageResult();
		try{
			customerList = SOAResParseUtil.getListValueFromDataByKey(result, "list", TelExtensionVo.class);
			//返回结果
			pageResult.setRows(customerList);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
		}catch(Exception e){
			LogUtil.error(logger, "数据转化异常e={}", e);
		}
		return pageResult;
	}


	/**
	 * 电话绑定列表
	 * @author afi
	 * @param uid
	 * @return
	 */
	@RequestMapping("test")
	@ResponseBody
	public DataTransferObject test(String uid){
		DataTransferObject dto = new DataTransferObject();
		return dto;
	}

	/**
	 * 电话绑定列表
	 * @author afi
	 * @param uid
	 * @return
	 */
	@RequestMapping("bindTel")
	@ResponseBody
	public DataTransferObject bindTel(String uid){
		DataTransferObject dto = null;
		if(Check.NuNStr(uid)){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("uid is null");
			return dto;
		}
		try {
			String json = telExtensionService.bindZiroomPhone(uid, UserUtil.getCurrentUserFid());
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(json);
			return resultDto;
		}catch (Exception e){
			LogUtil.error(logger, "数据转化异常e={}", e);
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("error");
		}
		return dto;
	}


	/**
	 * 电话解绑
	 * @author afi
	 * @param uid
	 * @return
	 */
	@RequestMapping("breakBindTel")
	@ResponseBody
	public DataTransferObject breakBindTel(String uid){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(uid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("uid is null");
			return dto;
		}
		try {
			String json = telExtensionService.breakBind(uid, UserUtil.getCurrentUserFid());
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(json);
			return resultDto;
		}catch (Exception e){
			LogUtil.error(logger, "数据转化异常e={}", e);
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("error");
		}
		return dto;
	}


	/**
	 * 查询列表
	 * @author afi
	 * @param customerExtDto
	 * @return
	 */
	@RequestMapping("customerDataList")
	@ResponseBody
	public PageResult customerDataList(CustomerExtDto customerExtDto){
		List<String> uidList = new ArrayList<>();
		PageResult pageResult = new PageResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fieldAuditStatu", CustomerAuditStatusEnum.UN_AUDIT.getCode());
		String allNeedAuditLandJson = customerInfoService.getAllNeedAuditLand(JsonEntityTransform.Object2Json(paramMap));
		DataTransferObject allNeedAuditLandDto = JsonEntityTransform.json2DataTransferObject(allNeedAuditLandJson);
		List<CustomerUpdateFieldAuditNewlogEntity> allNeedAuditLandList = null;
		if(allNeedAuditLandDto.getCode()==DataTransferObject.SUCCESS){
			allNeedAuditLandList = allNeedAuditLandDto.parseData("allNeedAuditLandList", new TypeReference<List<CustomerUpdateFieldAuditNewlogEntity>>() {});
			if(!Check.NuNCollection(allNeedAuditLandList)){
				for (CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlogEntity : allNeedAuditLandList) {
					uidList.add(customerUpdateFieldAuditNewlogEntity.getUid());
				}
				if(!Check.NuNObj(customerExtDto.getIsCanShow()) && customerExtDto.getIsCanShow() == YesOrNoEnum.YES.getCode()){
					customerExtDto.setUidList(uidList);
				}
			} else if(!Check.NuNObj(customerExtDto.getIsCanShow()) && Check.NuNCollection(allNeedAuditLandList)){
				return  pageResult; 
			}
		}
		String result = customerMsgManagerService.queryCustomerRoleMsg(JsonEntityTransform.Object2Json(customerExtDto));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
		List<CustomerExt> customerList ;
		try{
			customerList = SOAResParseUtil.getListValueFromDataByKey(result, "customerList", CustomerExt.class);
			for (CustomerExt customerExt : customerList) {
				if(!Check.NuNCollection(uidList) && uidList.contains(customerExt.getUid())){
					customerExt.setIsCanShow(YesOrNoEnum.YES.getCode());
				}
			}

			//返回结果
			pageResult.setRows(customerList);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
		}catch(Exception e){
			LogUtil.error(logger, "数据转化异常e={}", e);
		}
		return pageResult;
	}
	
	

	/**
	 * 
	 * 获取客户详情
	 *
	 * @author jixd
	 * @created 2016年4月25日 下午5:11:07
	 *
	 * @param uid
	 * @return
	 */
	@RequestMapping("customerDetailInfo")
	@ResponseBody
	public CustomerDetailVo customerDetailInfo(String uid,  HttpServletRequest request){
		if (Check.NuNStr(uid)) {
			return new CustomerDetailVo();
		}
		return customerDetailInfoByUid(uid, request);
	}

	/**
	 * 获取当前的用户信息
	 * @author afi
	 * @param uid
	 * @return
	 */
	private CustomerDetailVo customerDetailInfoByUid(String uid, HttpServletRequest request){
		if (Check.NuNStr(uid)) {
			return null;
		}
		String resutlJson = customerMsgManagerService.getCustomerDetail(uid);
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resutlJson);
		CustomerDetailVo parseData = resultDto.parseData("customerDetail", new TypeReference<CustomerDetailVo>() {});
		//1,房东  2，已认证信息修改待审核
		//从t_customer_update_history_log表中查询
		Map<String, Object> map = new HashMap<>();
		map.put("fieldAuditStatu", 0);
		map.put("uid", uid);
		String customerResultJson = customerInfoService.getFieldAuditNewLogByParam(JsonEntityTransform.Object2Json(map));
		DataTransferObject customerResultDto = JsonEntityTransform.json2DataTransferObject(customerResultJson);
		if(customerResultDto.getCode()==DataTransferObject.SUCCESS){
			List<CustomerFieldAuditVo> customerFieldAuditVoList = customerResultDto.parseData("customerFieldAuditVoList", new TypeReference<List<CustomerFieldAuditVo>>() {});
			//List<CustomerFieldAuditVo> customerFieldAuditVoList = resultDto.parseData("customerFieldAuditVoList", new TypeReference<CustomerFieldAuditVo>() {});
			for (CustomerFieldAuditVo customerFieldAuditVo : customerFieldAuditVoList) {
				//修改了头像
				String fieldPathKey = customerFieldAuditVo.getFieldPath();
				String fieldHeadPicKey = ClassReflectUtils.getFieldNamePath(CustomerPicMsgEntity.class,CustomerUpdateLogEnum.Customer_Pic_Msg_Pic_Base_Url.getFieldName());
				if(!Check.NuNStr(fieldPathKey) && !Check.NuNStr(fieldHeadPicKey) && fieldHeadPicKey.equals(fieldPathKey)){
					Map<String, Object> headPicMap = new HashMap<String, Object>();
					headPicMap.put("auditStatus", CustomerAuditStatusEnum.UN_AUDIT.getCode());
					headPicMap.put("picType", 3);
					headPicMap.put("uid", uid);
					String newHeadPicJson =  customerInfoService.getLatestUnAuditHeadPic(JsonEntityTransform.Object2Json(headPicMap));
					DataTransferObject newHeadPicDto = JsonEntityTransform.json2DataTransferObject(newHeadPicJson);
					if(newHeadPicDto.getCode()==DataTransferObject.SUCCESS){
						CustomerPicMsgEntity latestUnAuditHeadPic =  newHeadPicDto.parseData("customerPicMsg", new TypeReference<CustomerPicMsgEntity>() {});
						if(!Check.NuNObj(latestUnAuditHeadPic)){
							//将待审核的字段的newlog的赋到该对象中，以便于审核通过，以该id修改newlog表中该该用户的该审核字段的审核状态
							parseData.setCurCustomerFieldAuditFid(customerFieldAuditVo.getFid());
							parseData.setLatestUnAuditHeadPic(latestUnAuditHeadPic);
						}
					}
				}

				//修改了个人介绍
				String fieldIntroduceKey = ClassReflectUtils.getFieldNamePath(CustomerBaseMsgExtEntity.class,CustomerUpdateLogEnum.Customer_Base_Msg_Ext_Introduce.getFieldName());
				if(!Check.NuNStr(fieldPathKey) && !Check.NuNStr(fieldIntroduceKey) && fieldIntroduceKey.equals(fieldPathKey)){
					CustomerBaseMsgExtEntity unCheckCustomerBaseMsg = new CustomerBaseMsgExtEntity();
					//将待审核的字段的newlog的赋到该对象中，以便于审核通过，以该id修改newlog表中该该用户的该审核字段的审核状态
					unCheckCustomerBaseMsg.setCustomerIntroduce(customerFieldAuditVo.getNewValue());
					unCheckCustomerBaseMsg.setId(customerFieldAuditVo.getId());
					unCheckCustomerBaseMsg.setFid(customerFieldAuditVo.getFid());
					parseData.setUnCheckCustomerBaseMsgExt(unCheckCustomerBaseMsg);
				}
				
				//修改了昵称
				String fieldNickNamePath = ClassReflectUtils.getFieldNamePath(CustomerBaseMsgEntity.class,CustomerUpdateLogEnum.Customer_Base_Msg_NickName.getFieldName());
				if(!Check.NuNStr(fieldPathKey) && !Check.NuNStr(fieldNickNamePath) && fieldNickNamePath.equals(fieldPathKey)){
					//将待审核的字段的newlog的赋到该对象中，以便于审核通过，以该id修改newlog表中该该用户的该审核字段的审核状态
					parseData.setUnAuditNickName(customerFieldAuditVo.getNewValue());
					parseData.setUnAuditNickNameFieldFid(customerFieldAuditVo.getFid());
				} 

			}
		}
		
		return parseData;
	}

	/**
	 * 
	 * 敏感词校验公用方法
	 *
	 * @author loushuai
	 * @created 2017年11月21日 下午8:16:31
	 *
	 * @return String
	 */
	public String  sensitiveWordCheck(){
		 //欧诺弥亚项目     获取AccessToken
		String accessToken = null;
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("username", EUNOMIA_USERNAME);
			param.put("password", EUNOMIA_PASSWORD);
			LogUtil.info(logger, "敏感词校验方法   sensitiveWordCheck username={},password={},EUNOMIA_URL={}", EUNOMIA_USERNAME,EUNOMIA_PASSWORD,EUNOMIA_URL);
			String result  = CloseableHttpUtil.sendFormPost(EUNOMIA_URL, param);
			LogUtil.info(logger, "敏感词校验方法   sensitiveWordCheck result={}", result);
			 if(!Check.NuNStr(result)){
					JSONObject resultObj = JSONObject.parseObject(result);
					int code  = resultObj.getIntValue("code");
					if(code==10000){
						JSONObject jsonObject = resultObj.getJSONObject("data");
						String uid = (String) jsonObject.get("userId");
						String token = (String) jsonObject.get("token");
						accessToken = uid+"#"+token;
					}
			 }
		} catch (Exception e) {
			LogUtil.info(logger, "敏感词校验方法  sensitiveWordCheck， 发生错误", e);
			return accessToken;
		}
		return accessToken;
	}

	/**
	 * 
	 * 上传客户图片信息
	 *
	 * @author jixd
	 * @created 2016年4月25日 下午11:23:36
	 *
	 * @param file
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	@RequestMapping("uploadCustomerPic")
	@ResponseBody
	public String uploadCustomerPic(@RequestParam MultipartFile[] file,HttpServletRequest request) throws IOException{
		DataTransferObject dto=new DataTransferObject();
		String picType=request.getParameter("picType");
		String uid = request.getParameter("uid");

		CustomerPicDto picdto = new CustomerPicDto();
		picdto.setUid(uid);
		picdto.setPicType(Integer.parseInt(picType));
		String resultJson = customerMsgManagerService.getCustomerPicByType(JsonEntityTransform.Object2Json(picdto));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		CustomerPicMsgEntity customerPicMsgEntity = new CustomerPicMsgEntity();
		if(resultDto.getCode()==DataTransferObject.SUCCESS){
			 customerPicMsgEntity = resultDto.parseData("customerPicMsgEntity", new TypeReference<CustomerPicMsgEntity>() {});
		}
		
		//oldCustomerPicMsg用来存储老的头像，并将newlog表中，头像字段对应的id（fieldHeadPicKey）暂时放到oldCustomerPicMsg的id中，以作传值用
		CustomerPicMsgEntity oldCustomerPicMsg = new CustomerPicMsgEntity();
		if(!Check.NuNObj(customerPicMsgEntity)){
			BeanUtils.copyProperties(customerPicMsgEntity, oldCustomerPicMsg);
		}	


		String fieldHeadPicKey = ClassReflectUtils.getFieldNamePath(CustomerPicMsgEntity.class,CustomerUpdateLogEnum.Customer_Pic_Msg_Pic_Base_Url.getFieldName());
		String updateFieldAuditNewlogFid = MD5Util.MD5Encode(fieldHeadPicKey, "UTF-8");
		String updateFieldAuditNewlogJson = customerMsgManagerService.getUpdateFieldAuditNewlogByFid(updateFieldAuditNewlogFid);
		DataTransferObject updateFieldAuditNewlogDto = JsonEntityTransform.json2DataTransferObject(updateFieldAuditNewlogJson);
		if(updateFieldAuditNewlogDto.getCode()==DataTransferObject.SUCCESS){
			CustomerUpdateFieldAuditNewlogEntity updateFieldAuditNewlog = updateFieldAuditNewlogDto.parseData("updateFieldAuditNewlog", new TypeReference<CustomerUpdateFieldAuditNewlogEntity>() {});
			if(!Check.NuNObj(updateFieldAuditNewlog)){
				oldCustomerPicMsg.setId(updateFieldAuditNewlog.getId());
			}	
		}


		String oldServerPic = ""; 
		if(Check.NuNObj(customerPicMsgEntity)){
			customerPicMsgEntity = new CustomerPicMsgEntity();
		}else{
			//删除图片服务器图片
			oldServerPic = customerPicMsgEntity.getPicServerUuid();
		}
		if(file.length >0){
			//上传图片服务
			FileInfoResponse fileResponse=storageService.upload(storageKey, storageLimit, file[0].getOriginalFilename(),file[0].getBytes(), CustomerPicTypeEnum.getEnumMap().get(picType), 0l,file[0].getOriginalFilename());
			customerPicMsgEntity.setUid(uid);
			customerPicMsgEntity.setPicType(Integer.parseInt(picType));
			customerPicMsgEntity.setPicBaseUrl(fileResponse.getFile().getUrlBase());
			customerPicMsgEntity.setPicName(fileResponse.getFile().getOriginalFilename());
			customerPicMsgEntity.setPicSuffix(fileResponse.getFile().getUrlExt());
			customerPicMsgEntity.setPicServerUuid(fileResponse.getFile().getUuid());
			customerPicMsgEntity.setCreateDate(null);
			customerPicMsgEntity.setLastModifyDate(new Date());
			customerPicMsgEntity.setIsDel(0);
		}
		if(Check.NuNStr(customerPicMsgEntity.getFid())){
			customerPicMsgEntity.setFid(UUIDGenerator.hexUUID());
			String saveJson=customerMsgManagerService.insertCustomerPicMsg(JsonEntityTransform.Object2Json(customerPicMsgEntity));
			dto=JsonEntityTransform.json2DataTransferObject(saveJson);
			dto.putValue("pic", customerPicMsgEntity);
		}else{
			//			storageService.delete(oldServerPic);
			String updateJson = customerMsgManagerService.updateCustomerPicMsg(JsonEntityTransform.Object2Json(customerPicMsgEntity));
			Map<String, Object> headPicMap = new HashMap<String, Object>();
			headPicMap.put("auditStatus", CustomerAuditStatusEnum.UN_AUDIT.getCode());
			headPicMap.put("picType", 3);
			headPicMap.put("uid", uid);
			String newHeadPicJson =  customerInfoService.getLatestUnAuditHeadPic(JsonEntityTransform.Object2Json(headPicMap));
			DataTransferObject newHeadPicDto = JsonEntityTransform.json2DataTransferObject(newHeadPicJson);
			CustomerPicMsgEntity latestUnAuditHeadPic =  null;
			if(newHeadPicDto.getCode()==DataTransferObject.SUCCESS){
				latestUnAuditHeadPic =  newHeadPicDto.parseData("customerPicMsg", new TypeReference<CustomerPicMsgEntity>() {});
			}

			dto=JsonEntityTransform.json2DataTransferObject(updateJson);
			if(!Check.NuNObj(latestUnAuditHeadPic)){
				dto.putValue("pic", latestUnAuditHeadPic);
			}else{
				dto.putValue("pic", customerPicMsgEntity);
			}
			dto.putValue("oldHeadPicMsg", oldCustomerPicMsg);
		}
		return dto.toJsonString();
	}
	/**
	 * 删除客户照片信息
	 * @author jixd
	 * @created 2016年4月26日 上午11:01:37
	 * @param picdto
	 * @return
	 */
	@RequestMapping("delCustomerPic")
	@ResponseBody
	public DataTransferObject delCustomerPic(CustomerPicDto picdto){
		DataTransferObject dto=new DataTransferObject();
		String resultJson = customerMsgManagerService.getCustomerPicByType(JsonEntityTransform.Object2Json(picdto));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		CustomerPicMsgEntity customerPicEntity = resultDto.parseData("customerPicMsgEntity", new TypeReference<CustomerPicMsgEntity>() {});
		if(!Check.NuNObj(customerPicEntity)){
			//删除该图片
			customerPicEntity.setIsDel(1);
			dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.updateCustomerPicMsg(JsonEntityTransform.Object2Json(customerPicEntity)));

			//			try {
			//				boolean del = storageService.delete(customerPicEntity.getPicServerUuid());
			//				LogUtil.info(logger,"图片删除状态"+del);
			//			} catch (IOException e) {
			//				LogUtil.error(logger, "删除图片异常，PicServerUuid={}，e={}",customerPicEntity.getPicServerUuid(), e);
			//			}

		}else{
			dto.setErrCode(1);
			dto.setMsg("图片不存在");
		}
		return dto;
	}

	/**
	 * 审核客户信息
	 * @author jixd
	 * @created 2016年4月26日 上午11:03:28
	 * @return
	 */
	@RequestMapping("auditCustomer")
	@ResponseBody
	public DataTransferObject auditCustomer(HttpServletRequest request){
		DataTransferObject dto = new DataTransferObject();
		String uid = request.getParameter("uid");
		String afterOper = request.getParameter("afterOper");
		String beforeOper = request.getParameter("beforeOper");
		String remark = request.getParameter("remark");
		if(Check.NuNStr(uid)){
			dto.setErrCode(1);
			dto.setMsg("审核用户UID不能为空");
			return dto;
		}
		if(Check.NuNObj(afterOper)){
			dto.setErrCode(1);
			dto.setMsg("审核状态不能为空");
			return dto;
		}
		CustomerAuditRequest customerAuditRequest = new CustomerAuditRequest();

		CustomerBaseMsgEntity entity = new CustomerBaseMsgEntity();
		entity.setUid(uid);
		int afterOperInt = Integer.parseInt(afterOper);
		
		CustomerOperHistoryEntity historyEntity = new CustomerOperHistoryEntity();
		historyEntity.setUid(uid);
		historyEntity.setAuditAfterStatus(Integer.parseInt(afterOper));
		historyEntity.setCreateTime(new Date());
		historyEntity.setFid(UUIDGenerator.hexUUID());
		historyEntity.setOperRemark(remark);
		historyEntity.setOperUid(UserUtil.getCurrentUserFid());
		historyEntity.setAuditBeforeStatus(Integer.parseInt(beforeOper));
		//短信通知房东
		if(afterOperInt==3){
			if(Check.NuNStr(remark)){
				dto.setErrCode(1);
				dto.setMsg("请填写驳回备注");
				return dto;
			}
			afterOper =  AuditStatusEnum.COMPLETE.getCode()+"";
			historyEntity.setAuditAfterStatus(Integer.parseInt(afterOper));
			DataTransferObject dtoCus = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid));
			CustomerBaseMsgEntity customer =dtoCus.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
			});
			if(Check.NuNObj(customer)||Check.NuNStr(customer.getCustomerMobile())){
				LogUtil.error(logger, "【房东审核驳回错误】customer={}", customer==null?"":JsonEntityTransform.Object2Json(customer));
				dto.setErrCode(1);
				dto.setMsg("当前房东手机号不存在");
				return dto;
			}
			SmsMessage smsMessage = new SmsMessage(customer.getCustomerMobile(), remark);
			MessageUtils.sendSms(smsMessage, null);
			JpushRequest jpushRequest = new JpushRequest();
			jpushRequest.setContent(remark);
			jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
			jpushRequest.setTitle("房东信息审核通知");
			jpushRequest.setUid(customer.getUid());
			jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
			//自定义消息
			Map<String, String> extrasMap = new HashMap<>();
			extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
			extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_4);
			extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"0");
			extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
			extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
			jpushRequest.setExtrasMap(extrasMap);
		
			smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));

			//增加操作记录
			customerMsgManagerService.saveCustomerOperHistory(JsonEntityTransform.Object2Json(historyEntity));
		}

		//3: 保存备注 给房东发送短信
		if(afterOperInt!=3){
			//如果审核未通过 更新状态
			if(afterOperInt == AuditStatusEnum.REJECTED.getCode()){
				//身份信息置为0，设置未认证
				entity.setIsIdentityAuth(0);
			}else{
				entity.setIsIdentityAuth(1);
			}
			entity.setAuditStatus(Integer.parseInt(afterOper));
			customerAuditRequest.setCustomerBaseMsg(entity);
			//改变客户信息状态
			String updateJson = null;
			
			//添加审核记录
			customerAuditRequest.setHistoryEntity(historyEntity);
			//个人头像，个人介绍至少有一个审核通过
			updateJson =  customerMsgManagerService.updateBaseAndExtOrPic(JsonEntityTransform.Object2Json(customerAuditRequest));

			DataTransferObject updateDto = JsonEntityTransform.json2DataTransferObject(updateJson);
			try {
				if(updateDto.getCode() == DataTransferObject.SUCCESS){
					 
					JpushConfig jpushConfig = jpushNotic(uid, remark, "房东资质审核通知");
					//改变状态成功后发送消息通知
					if(afterOperInt == AuditStatusEnum.REJECTED.getCode()){
						//拒绝的话直接发送备注内容
						JpushUtils.sendPushOne(uid, jpushConfig, null);
					}
					if(afterOperInt == AuditStatusEnum.COMPLETE.getCode()){
						jpushConfig.setContent("您的审核已通过。");
						//成功的话发送特定消息，您的审核已通过
						JpushUtils.sendPushOne(uid, jpushConfig, null);
					}
				}
				
				
			}catch (Exception e){
				LogUtil.info(logger,"e:{}",e);
			}
		}

		return dto;
	}

	
	
	/**
	 * 
	 * push 通知
	 *
	 * @author yd
	 * @created 2017年9月14日 下午7:50:47
	 *
	 * @param uid
	 * @param content
	 * @param title
	 */
	private JpushConfig jpushNotic(String uid,String content,String title){
		
		JpushConfig jpushConfig = new JpushConfig();
		Map<String, String> extrasMap = new HashMap<String, String>();
		extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
		extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_4);
		extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"0");
		extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
		extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
		jpushConfig.setMessageType(MessageTypeEnum.MESSAGE.getCode());
		jpushConfig.setExtrasMap(extrasMap);
		jpushConfig.setContent(content);
		if(!Check.NuNStr(title)){
			jpushConfig.setTitle(title);
		}
		
		return jpushConfig;
	}
	
	/**
	 * 
	 * 用户审核通过
	 * 1. 个人介绍 审核通过 
	 * 2. 头像审核通过
	 * @author yd
	 * @created 2017年9月11日 下午9:00:52
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("auditedCustomerInfo")
	@ResponseBody
	public DataTransferObject auditedCustomerInfo(HttpServletRequest request){
		
		DataTransferObject dto = new DataTransferObject();
		
		//房东uid
		String uid = request.getParameter("uid");
		
		//个人介绍
		String introduceRejectId = request.getParameter("introduceRejectId");
		//用户头像
		String customerAuditHeadPicFid = request.getParameter("headPicFid");
		//待审核的头像 fid
		String headPicRejectId = request.getParameter("headPicRejectId");
		//待审核介绍
		String introduce = request.getParameter("introduce");
		//待审核昵称
		String unAuditNickNameFieldFid = request.getParameter("unAuditNickNameFieldFid");		
		//待审核昵称
		String nickName = request.getParameter("nickName");
		
		if(Check.NuNStr(uid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请选择房东");
			return dto;
		}
		
		if(Check.NuNStr(introduceRejectId)
				&&Check.NuNObjs(introduce)
				&&Check.NuNObjs(customerAuditHeadPicFid)
				&&Check.NuNObjs(headPicRejectId)
				&&Check.NuNObjs(unAuditNickNameFieldFid)
				&&Check.NuNObjs(nickName)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请选择审核通过信息");
			return dto;
		}
		int code = 0;
		CustomerAuditRequest customerAuditRequest = new CustomerAuditRequest();
		Map<String, Object> fieldAuditNewLogMap = new HashMap<>();
		CustomerBaseMsgEntity entity = new CustomerBaseMsgEntity();
		entity.setUid(uid);
		if(!Check.NuNStr(unAuditNickNameFieldFid) && !Check.NuNObjs(nickName)){
			//待审核昵称
			entity.setNickName(nickName);
			fieldAuditNewLogMap.put("unAuditNickNameFieldFid",unAuditNickNameFieldFid);
			customerAuditRequest.setFieldAuditNewLogMap(fieldAuditNewLogMap);
			code = MessageTemplateCodeEnum.LANLORD_AUDITED_NICKNAME.getCode();
		}
		
		customerAuditRequest.setCustomerBaseMsg(entity);
		//更新个人介绍
		if(!Check.NuNStr(introduceRejectId)
				&&!Check.NuNObjs(introduce)){
			CustomerBaseMsgExtEntity customerBaseMsgExt = new CustomerBaseMsgExtEntity();
			customerBaseMsgExt.setCustomerIntroduce(introduce);
			customerBaseMsgExt.setUid(uid);
			customerAuditRequest.setCustomerBaseMsgExt(customerBaseMsgExt);
			fieldAuditNewLogMap.put("introduceRejectId",introduceRejectId);
			customerAuditRequest.setFieldAuditNewLogMap(fieldAuditNewLogMap);
			code = MessageTemplateCodeEnum.LANLORD_AUDITED_INTRDUCE.getCode();
		}
		
		
		//更新头像审核
		if(!Check.NuNObjs(customerAuditHeadPicFid)
				&&!Check.NuNObjs(headPicRejectId)){
			CustomerPicMsgEntity customerPicMsg = new CustomerPicMsgEntity();
			customerPicMsg.setFid(customerAuditHeadPicFid);
			customerPicMsg.setUid(uid);
			customerPicMsg.setAuditStatus(CustomerAuditStatusEnum.AUDIT_ADOPT.getCode());
			customerPicMsg.setPicType(CustomerPicTypeEnum.YHTX.getCode());
			customerAuditRequest.setCustomerPicMsg(customerPicMsg);
			fieldAuditNewLogMap.put("headPicRejectId", headPicRejectId);
			customerAuditRequest.setFieldAuditNewLogMap(fieldAuditNewLogMap);
			code = MessageTemplateCodeEnum.LANLORD_AUDITED_HEAD_PIC.getCode();
		}
		dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.auditedCustomerInfo(JsonEntityTransform.Object2Json(customerAuditRequest)));
		dto.putValue("refreshDeal", YesOrNoEnum.NO.getCode());
		Map<String, Object> map = new HashMap<>();
		map.put("fieldAuditStatu", 0);
		map.put("uid", uid);
		dto.putValue("refreshDeal", YesOrNoEnum.NO.getCode());
		String customerResultJson = customerInfoService.getFieldAuditNewLogByParam(JsonEntityTransform.Object2Json(map));
		DataTransferObject customerResultDto = JsonEntityTransform.json2DataTransferObject(customerResultJson);
		if(customerResultDto.getCode()==DataTransferObject.SUCCESS){
			List<CustomerFieldAuditVo> customerFieldAuditVoList = customerResultDto.parseData("customerFieldAuditVoList", new TypeReference<List<CustomerFieldAuditVo>>() {});
			if(Check.NuNCollection(customerFieldAuditVoList)){
				dto.putValue("refreshDeal",  YesOrNoEnum.YES.getCode());
			}
		}
		//短信通知房东
		sendMsgNoticeLanlord(dto, code,uid);
		return dto;
	}
	
	/**
	 * 
	 * 房东信息审核通过 发送短信通知
	 *
	 * @author yd
	 * @created 2017年9月12日 下午2:26:56
	 *
	 * @param dto
	 * @param code
	 */
	private void sendMsgNoticeLanlord(DataTransferObject dto,int code ,String uid){
		
		if(!Check.NuNObj(dto)&&dto.getCode() == DataTransferObject.SUCCESS){
			String customerJson = customerInfoService.getCustomerInfoByUid(uid);
			DataTransferObject cusDto = JsonEntityTransform.json2DataTransferObject(customerJson);
			if (cusDto.getCode() == DataTransferObject.SUCCESS) {
				try {
					CustomerBaseMsgEntity customer = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
					this.sendSms(customer.getCustomerMobile(), null,code);
					
					JpushRequest jpushRequest = new JpushRequest();
					jpushRequest.setSmsCode(code+"");
					jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
					
					jpushRequest.setUid(customer.getUid());
					jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
					//自定义消息
					Map<String, String> extrasMap = new HashMap<>();
					extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
					extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_4);
					extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"0");
					extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
					extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
					jpushRequest.setTitle("房东信息审核通知");
					jpushRequest.setExtrasMap(extrasMap);
					smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
				} catch (SOAParseException e) {
					LogUtil.error(logger, "【跟进uid获取用户信息异常】e={}", e);
				}
			}
		}
	}
	
	/**
	 * 发送短信
	 *
	 * @author liujun
	 * @created 2016年7月22日
	 *
	 * @param mobile
	 * @param paramsMap
	 * @param smsCode
	 */
	private void sendSms(String mobile, Map<String, String> paramsMap, int smsCode) {
		SmsRequest smsRequest = new SmsRequest();
		smsRequest.setMobile(mobile);
		smsRequest.setParamsMap(paramsMap);
		smsRequest.setSmsCode(String.valueOf(smsCode));
		smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
	}
	/**
	 * 
	 * 更新客户个人介绍
	 *
	 * @author bushujie
	 * @created 2016年6月23日 下午2:37:11
	 *
	 * @param introduce
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("updataCustomerIntroduce")
	@ResponseBody
	public DataTransferObject  updataCustomerIntroduce(String introduce,String uid) throws SOAParseException{


		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(introduce)){

			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("个人介绍不能为空");
			return dto;
		}

		if(introduce.length() > 500){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("超过500字数限制");
			return dto;
		}

		if(introduce.length() < 70){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("字数不能少于70字");
			return dto;
		}
		String resultJson=customerMsgManagerService.selectCustomerExtByUid(uid);
		CustomerBaseMsgExtEntity ext=SOAResParseUtil.getValueFromDataByKey(resultJson, "customerBaseMsgExt", CustomerBaseMsgExtEntity.class);
		if(Check.NuNObj(ext)){
			ext=new CustomerBaseMsgExtEntity();
			ext.setFid(UUIDGenerator.hexUUID());
			ext.setUid(uid);
			ext.setCustomerIntroduce(introduce);
			resultJson=customerMsgManagerService.insertCustomerExt(JsonEntityTransform.Object2Json(ext));
		} else {
			ext.setUid(uid);
			ext.setCustomerIntroduce(introduce);
			resultJson=customerMsgManagerService.updateCustomerExtNotAudit(JsonEntityTransform.Object2Json(ext));
			//resultJson=customerMsgManagerService.updateCustomerExtByUid(JsonEntityTransform.Object2Json(ext));
		}
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}

	/**
	 * 
	 * 根据手机号获取信息审核通过的客户
	 *
	 * @author liujun
	 * @created 2016年9月5日
	 *
	 * @param mobile
	 * @return
	 *
	 */
	@RequestMapping("linkCustomerByMobile")
	@ResponseBody
	public DataTransferObject linkCustomerByMobile(String mobile){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStrStrict(mobile)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("手机号不能为空");
			return dto;
		}

		if(mobile.startsWith("0")){
			mobile = mobile.substring(1);
		}

		if(!RegExpUtil.isMobilePhoneNum(mobile)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("手机号格式不正确");
			return dto;
		}

		try {
			String resultJson = customerInfoService.getCustomerByMobile(mobile);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(logger, "customerInfoService.getCustomerByMobile接口失败,mobile={}.返回结果:{}", mobile, resultJson);
				return dto;
			}

			CustomerBaseMsgEntity customerBaseMsgEntity = SOAResParseUtil.getValueFromDataByKey(resultJson,
					"customerBase", CustomerBaseMsgEntity.class);
			if(Check.NuNObj(customerBaseMsgEntity)){
				dto = new DataTransferObject();
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("客户信息不存在");
				return dto;
			}

			if(Check.NuNObj(customerBaseMsgEntity.getAuditStatus()) 
					|| customerBaseMsgEntity.getAuditStatus().intValue() != AuditStatusEnum.COMPLETE.getCode()){
				dto = new DataTransferObject();
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("客户信息审核未通过");
				return dto;
			}
		} catch (Exception e) {
			LogUtil.info(logger, "error:{}", e);
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

	/**
	 * 
	 * 判断当前登录用户是否是该房源地推管家或维护管家
	 *
	 * @author liujun
	 * @created 2016年9月5日
	 *
	 * @param houseBaseFid
	 * @return
	 *
	 */
	@RequestMapping("judgeHouseZo")
	@ResponseBody
	public DataTransferObject judgeHouseZo(String houseBaseFid){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStrStrict(houseBaseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源id不能为空");
			return dto;
		}

		String empFid = UserUtil.getEmployeeFid();
		if(Check.NuNStrStrict(empFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请重新登录");
			return dto;
		}
		try {
			String resultJson = houseGuardService.findHouseGuardRelByHouseBaseFid(houseBaseFid);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.SUCCESS){
				HouseGuardRelEntity houseGuardRel = SOAResParseUtil
						.getValueFromDataByKey(resultJson, "houseGuardRel", HouseGuardRelEntity.class);
				EmployeeEntity emp = this.findEmployeeEntityByFid(empFid);
				if (!Check.NuNObj(houseGuardRel) && !Check.NuNObj(emp)
						&& (!Check.NuNStr(houseGuardRel.getEmpGuardCode()) && houseGuardRel.getEmpGuardCode().equals(emp.getEmpCode()))) {
					dto.putValue("isZo", true);
					return dto;
				}
			}
			dto.putValue("isZo", false);
		} catch (Exception e) {
			LogUtil.info(logger, "judgeHouseZo error:{}", e);
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

	/**
	 * 
	 * 根据员工表fid查询员工信息
	 *
	 * @author lunan
	 * @created 2016年8月19日 上午10:09:48
	 *
	 * @param empFid
	 * @throws SOAParseException
	 * @return
	 */
	private EmployeeEntity findEmployeeEntityByFid(String empFid) throws SOAParseException {
		String resultJson = employeeService.findEmployeByEmpFid(empFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(logger, "employeeService#findEmployeeEntityByFid调用接口失败,empFid={}", empFid);
			return null;
		} else {
			EmployeeEntity employee =SOAResParseUtil.getValueFromDataByKey(resultJson, "employee", EmployeeEntity.class);
			return employee;
		}
	}


	/**
	 * 
	 * 审核通过驳回
	 *
	 * @author bushujie
	 * @created 2017年5月22日 下午5:12:32
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("auditCustomerReject")
	@ResponseBody
	public DataTransferObject auditCustomerReject(HttpServletRequest request){
		DataTransferObject dto = new DataTransferObject();
		String uid = request.getParameter("uid");

		if(Check.NuNStr(uid)){
			dto.setErrCode(1);
			dto.setMsg("审核用户UID不能为空");
			return dto;
		}
		CustomerBaseMsgEntity entity = new CustomerBaseMsgEntity();
		entity.setUid(uid);
		entity.setAuditStatus(AuditStatusEnum.SUBMITAUDIT.getCode());
		//改变客户信息状态
		String updateJson = customerMsgManagerService.updateCustomerBaseMsg(JsonEntityTransform.Object2Json(entity));
		dto = JsonEntityTransform.json2DataTransferObject(updateJson);
		CustomerOperHistoryEntity historyEntity = new CustomerOperHistoryEntity();
		historyEntity.setUid(uid);
		historyEntity.setAuditAfterStatus(AuditStatusEnum.SUBMITAUDIT.getCode());
		historyEntity.setCreateTime(new Date());
		historyEntity.setFid(UUIDGenerator.hexUUID());
		historyEntity.setOperUid(UserUtil.getCurrentUserFid());
		historyEntity.setAuditBeforeStatus(AuditStatusEnum.COMPLETE.getCode());
		//增加操作记录
		customerMsgManagerService.saveCustomerOperHistory(JsonEntityTransform.Object2Json(historyEntity));
		return dto;
	}

	/**
	 * @description: 驳回待审核的房东字段信息（客户单个字段审核驳回）
	 * @author: loushuai
	 * @date: 2017/8/8 14:29
	 * @params: request
	 * @return:
	 */
	@RequestMapping("rejectCustomerField")
	@ResponseBody
	public DataTransferObject rejectCustomerField(HttpServletRequest request){
		DataTransferObject dto = new DataTransferObject();
		try {
			String fid = request.getParameter("id");
			String uid = request.getParameter("uid");
			if(Check.NuNStrStrict(fid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数错误");
				return dto;
			}
			CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlogEntity = new CustomerUpdateFieldAuditNewlogEntity();
			customerUpdateFieldAuditNewlogEntity.setFid(fid);
			customerUpdateFieldAuditNewlogEntity.setFieldAuditStatu(CustomerAuditStatusEnum.AUDIT_UNADOPT.getCode());
			String rejectNumJson = customerMsgManagerService.updateCustomerUpdateFieldAuditNewlogByFid(JsonEntityTransform.Object2Json(customerUpdateFieldAuditNewlogEntity));
			dto = JsonEntityTransform.json2DataTransferObject(rejectNumJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(logger,"rejectHouseField(),更新审核字段为驳回状态失败, params:{},errorMsg:{}",JsonEntityTransform.Object2Json(customerUpdateFieldAuditNewlogEntity),dto.getMsg());
				dto.setMsg("系统异常");
				return dto;
			}
			
			//操作完是否刷新表格
			/*dto.putValue("refreshDeal", YesOrNoEnum.NO.getCode());
			Map<String, Object> map = new HashMap<>();
			map.put("fieldAuditStatu", 0);
			map.put("uid", uid);
			dto.putValue("refreshDeal", YesOrNoEnum.NO.getCode());
			String customerResultJson = customerInfoService.getFieldAuditNewLogByParam(JsonEntityTransform.Object2Json(map));
			DataTransferObject customerResultDto = JsonEntityTransform.json2DataTransferObject(customerResultJson);
			if(customerResultDto.getCode()==DataTransferObject.SUCCESS){
				List<CustomerFieldAuditVo> customerFieldAuditVoList = customerResultDto.parseData("customerFieldAuditVoList", new TypeReference<List<CustomerFieldAuditVo>>() {});
				if(Check.NuNCollection(customerFieldAuditVoList)){
					dto.putValue("refreshDeal",  YesOrNoEnum.YES.getCode());
				}
			}*/
			
		}catch (Exception e) {
			LogUtil.error(logger, "rejectCustomerField error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
		}
		return dto;
	}

	/**
	 * @description: 驳回待审核的房东图片信息（客户单个字段审核驳回）
	 * @author: loushuai
	 * @date: 2017/8/8 14:29
	 * @params: request
	 * @return:
	 */
	@RequestMapping("rejectCustomerPicField")
	@ResponseBody
	public DataTransferObject rejectCustomerPicField(HttpServletRequest request){
		DataTransferObject dto = new DataTransferObject();
		try {
			String fid = request.getParameter("id");
			String uid = request.getParameter("uid");
			CustomerUpdateFieldAuditNewlogEntity customerUpdateFieldAuditNewlogEntity = new CustomerUpdateFieldAuditNewlogEntity();
			customerUpdateFieldAuditNewlogEntity.setFid(fid);
			customerUpdateFieldAuditNewlogEntity.setFieldAuditStatu(2);
			String rejectNumJson = customerMsgManagerService.updateCustomerUpdateFieldAuditNewlogByFid(JsonEntityTransform.Object2Json(customerUpdateFieldAuditNewlogEntity));
			dto = JsonEntityTransform.json2DataTransferObject(rejectNumJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(logger,"rejectHouseField(),更新审核字段为驳回状态失败, params:{},errorMsg:{}",JsonEntityTransform.Object2Json(customerUpdateFieldAuditNewlogEntity),dto.getMsg());
				dto.setMsg("系统异常");
				return dto;
			}
			
			//是否执行刷新操作
			/*dto.putValue("refreshDeal", YesOrNoEnum.NO.getCode());
			Map<String, Object> map = new HashMap<>();
			map.put("fieldAuditStatu", 0);
			map.put("uid", uid);
			dto.putValue("refreshDeal", YesOrNoEnum.NO.getCode());
			String customerResultJson = customerInfoService.getFieldAuditNewLogByParam(JsonEntityTransform.Object2Json(map));
			DataTransferObject customerResultDto = JsonEntityTransform.json2DataTransferObject(customerResultJson);
			if(customerResultDto.getCode()==DataTransferObject.SUCCESS){
				List<CustomerFieldAuditVo> customerFieldAuditVoList = customerResultDto.parseData("customerFieldAuditVoList", new TypeReference<List<CustomerFieldAuditVo>>() {});
				if(Check.NuNCollection(customerFieldAuditVoList)){
					dto.putValue("refreshDeal",  YesOrNoEnum.YES.getCode());
				}
			}*/
		}catch (Exception e) {
			LogUtil.error(logger, "rejectCustomerField error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
		}
		return dto;
	}


}
