/**
 * @FileName: CustomerAuthController.java
 * @Package com.ziroom.minsu.api.customer.controller
 * 
 * @author bushujie
 * @created 2016年4月23日 下午2:59:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.customer.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ApiConst;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.BaseParamDto;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.logic.ParamCheckLogic;
import com.ziroom.minsu.api.common.logic.ValidateResult;
import com.ziroom.minsu.api.common.util.CustomerVoUtils;
import com.ziroom.minsu.api.customer.dto.UpCustomerPicDto;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.services.customer.api.inner.CustomerAuthService;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.dto.CustomerPicDto;
import com.ziroom.minsu.services.customer.entity.CustomerBaseMsgVo;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;
import com.ziroom.tech.storage.client.domain.FileInfoResponse;
import com.ziroom.tech.storage.client.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>房东认证流程controller</p>
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
@Controller
@RequestMapping("/customer")
public class CustomerAuthController {
	
	@Resource(name="storageService")
	private StorageService storageService; 
	
	@Resource(name="customer.customerAuthService")
	private CustomerAuthService customerAuthService;
	
	@Resource(name="api.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;
	
	@Value("#{'${pic_base_addr}'.trim()}")
	private String picBaseAddr;
	
	@Value("#{'${storage_key}'.trim()}")
	private String storageKey;
	
	@Value("#{'${storage_limit}'.trim()}")
	private String storageLimit;
	
	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;
	
	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService; 
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAuthController.class);
	
	/**
	 * 
	 * 房东照片上传
	 *
	 * @author bushujie
	 * @created 2016年4月23日 下午3:07:13
	 *
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/customerIconAuth")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> customerIconAuth(@RequestParam MultipartFile file,HttpServletRequest request){
		try{
			
			String uid = (String) request.getAttribute("uid");
			LogUtil.info(LOGGER, "房东照片上传uid：" + uid);
			if(Check.NuNStr(uid)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("uid 不能为空"), HttpStatus.OK);
			}
			
			LogUtil.info(LOGGER, "房东照片上传文件名称：" + file.getOriginalFilename());
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "房东照片上传参数：" + paramJson);
			ValidateResult<UpCustomerPicDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					UpCustomerPicDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			
			String customerInfoJson = customerInfoService.getCustomerInfoByUid(uid);
			DataTransferObject customerInfoDto = JsonEntityTransform.json2DataTransferObject(customerInfoJson);
			if(customerInfoDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER, "customerInfoService.getCustomerInfoByUid接口失败,uid:{}", uid);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(customerInfoDto.getMsg()), HttpStatus.OK);
			}
			CustomerBaseMsgVo customerBase = SOAResParseUtil
					.getValueFromDataByKey(customerInfoJson, "customerBase", CustomerBaseMsgVo.class);
			 
			if(Check.NuNObj(customerBase)){
				LogUtil.info(LOGGER, "房东不存在,uid:{}", uid);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房东不存在"), HttpStatus.OK);
			}

			UpCustomerPicDto upCustomerPicDto = validateResult.getResultObj();
			String fileName = file.getOriginalFilename();
			if (!fileName.contains(".jpg") && !fileName.contains(".JPG")){
				fileName += ".jpg";
			}
			//上传图片文件
			FileInfoResponse fileResponse=storageService.upload(storageKey, storageLimit, fileName,file.getBytes(),CustomerPicTypeEnum.YHTX.getName(), 0l,fileName);
			
			if(Check.NuNObj(fileResponse)||!fileResponse.getResponseCode().equals(String.valueOf(DataTransferObject.SUCCESS))){
				LogUtil.error(LOGGER, "上传房东图片失败uid={},fileResponse={}", uid,fileResponse==null?"":JsonEntityTransform.Object2Json(fileResponse));
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("上传失败"), HttpStatus.OK);
			}
			LogUtil.info(LOGGER, "房东uid={}，上传图片信息fileResponse={}", uid,JsonEntityTransform.Object2Json(fileResponse));
			CustomerPicMsgEntity customerPicMsgEntity=new CustomerPicMsgEntity();
			customerPicMsgEntity.setFid(UUIDGenerator.hexUUID());
			customerPicMsgEntity.setUid(uid);
			customerPicMsgEntity.setPicType(upCustomerPicDto.getPicType());
			customerPicMsgEntity.setPicSuffix(fileResponse.getFile().getUrlExt());
			customerPicMsgEntity.setPicBaseUrl(fileResponse.getFile().getUrlBase());
			customerPicMsgEntity.setPicServerUuid(fileResponse.getFile().getUuid());
			customerPicMsgEntity.setPicName(fileResponse.getFile().getOriginalFilename());
			List<CustomerPicMsgEntity> picList=new ArrayList<CustomerPicMsgEntity>();
			picList.add(customerPicMsgEntity);
			String resultJson =customerAuthService.customerIconAuth(JsonEntityTransform.Object2Json(picList));
			DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(resultJson);
			if (dto.getCode() == ApiConst.OPERATION_FAILURE) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
			}
			
//			//删除原来的图片
//			if(!Check.NuNObj(dto.getData().get("picServerUuid"))){
//				boolean isDel=storageService.delete(dto.getData().get("picServerUuid").toString());
//				LogUtil.info(LOGGER, dto.getData().get("picServerUuid")+"删除结果：" + isDel);
//				dto.getData().remove("picServerUuid");
//			}
			
			if (CustomerPicTypeEnum.YHTX.getCode()==upCustomerPicDto.getPicType()) {//头像
				CustomerBaseMsgEntity customerBaseMsgEntity= new CustomerBaseMsgEntity();
				customerBaseMsgEntity.setIsUploadIcon(YesOrNoEnum.YES.getCode());
				customerBaseMsgEntity.setUid(uid);	
				LogUtil.info(LOGGER, "更新房东状态：" + JsonEntityTransform.Object2Json(customerBaseMsgEntity));
				customerInfoService.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsgEntity));
				
			}else if ( CustomerPicTypeEnum.ZJZM.getCode()==upCustomerPicDto.getPicType()
					|| CustomerPicTypeEnum.ZJFM.getCode()==upCustomerPicDto.getPicType()
					|| CustomerPicTypeEnum.ZJSC.getCode()==upCustomerPicDto.getPicType()
					|| CustomerPicTypeEnum.YYZZ.getCode()==upCustomerPicDto.getPicType()) {//正面照、反面照、手持证件照， 营业执照

				//证件正面照
				CustomerPicDto picDto = new CustomerPicDto();
				picDto.setUid(uid);
				picDto.setPicType(CustomerPicTypeEnum.ZJZM.getCode());
				String picJson1 = JsonEntityTransform.Object2Json(picDto);
				String picResultJosn1 = customerMsgManagerService.getCustomerPicByType(picJson1);
				CustomerPicMsgEntity voucherFrontPicOld = SOAResParseUtil.getValueFromDataByKey(picResultJosn1,
						"customerPicMsgEntity", CustomerPicMsgEntity.class);
				
				//证件反面照
				picDto.setPicType(CustomerPicTypeEnum.ZJFM.getCode());
				String picJson2 = JsonEntityTransform.Object2Json(picDto);
				String picResultJosn2 = customerMsgManagerService.getCustomerPicByType(picJson2);
				CustomerPicMsgEntity voucherBackPicOld = SOAResParseUtil.getValueFromDataByKey(picResultJosn2,
						"customerPicMsgEntity", CustomerPicMsgEntity.class);	
				
				//手持证件照
				picDto.setPicType(CustomerPicTypeEnum.ZJSC.getCode());
				String picJson3 = JsonEntityTransform.Object2Json(picDto);
				String picResultJosn3 = customerMsgManagerService.getCustomerPicByType(picJson3);
				CustomerPicMsgEntity voucherHandPicOld = SOAResParseUtil.getValueFromDataByKey(picResultJosn3,
	                    "customerPicMsgEntity", CustomerPicMsgEntity.class);

				//营业执照
				picDto.setPicType(CustomerPicTypeEnum.YYZZ.getCode());
				String picJson4 = JsonEntityTransform.Object2Json(picDto);
				String picResultJosn4 = customerMsgManagerService.getCustomerPicByType(picJson4);
				CustomerPicMsgEntity yyzzPicOld = SOAResParseUtil.getValueFromDataByKey(picResultJosn4,
						"customerPicMsgEntity", CustomerPicMsgEntity.class);

                boolean realNameCheck = !Check.NuNStr(customerBase.getRealName());
                boolean idTypeCheck = !Check.NuNObj(customerBase.getIdType());
                boolean idNoCheck = !Check.NuNObj(customerBase.getIdNo());
                boolean identity = realNameCheck && idTypeCheck && idNoCheck;

                //身份信息： 真实姓名 证件类型 证件号码 三张图片都不为空， 才去更新isIdentityAuth
                if (!Check.NuNObj(voucherFrontPicOld) && !Check.NuNObj(voucherBackPicOld) && !Check.NuNObj(voucherHandPicOld) && identity
                        || !Check.NuNObj(voucherFrontPicOld) && !Check.NuNObj(voucherBackPicOld) && !Check.NuNObj(yyzzPicOld) && identity) {
                    CustomerBaseMsgEntity customerBaseMsgEntity= new CustomerBaseMsgEntity();
					customerBaseMsgEntity.setIsIdentityAuth(YesOrNoEnum.YES.getCode());
					customerBaseMsgEntity.setUid(uid);
					
					LogUtil.info(LOGGER, "更新房东状态：" + JsonEntityTransform.Object2Json(customerBaseMsgEntity));
					customerInfoService.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsgEntity));
				}
				
			}
			
			//返回图片地址
			dto.putValue("picUrl", picBaseAddr+customerPicMsgEntity.getPicBaseUrl()+customerPicMsgEntity.getPicSuffix());
			LogUtil.info(LOGGER, "结果：" + JsonEntityTransform.Object2Json(dto));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}
	
	/**
	 * 
	 * 根据uid取用户基本信息
	 *
	 * @author yd
	 * @created 2016年5月9日 下午10:29:34
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/getCustomerVo")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> getCustomerVo(HttpServletRequest request){
		
		String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
		LogUtil.info(LOGGER, "参数："+paramJson);
		BaseParamDto  baseParamDto= JsonEntityTransform.json2Object(paramJson, BaseParamDto.class);
		
		CustomerVoUtils customerVoUtils = new CustomerVoUtils(customerMsgManagerService);
		CustomerVo customerVo = customerVoUtils.getCustomerVo(baseParamDto.getUid(), customerMsgManagerService);
		if(!Check.NuNObj(customerVo)&&Check.NuNStr(customerVo.getCustomerMobile())){
			customerVo.setCustomerMobile("");
			customerVo.setShowMobile("");
		}
		return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(customerVo), HttpStatus.OK);
	}
}
