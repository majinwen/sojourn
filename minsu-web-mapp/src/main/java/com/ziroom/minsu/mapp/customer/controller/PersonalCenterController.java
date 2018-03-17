package com.ziroom.minsu.mapp.customer.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.message.SysComplainEntity;
import com.ziroom.minsu.mapp.common.constant.MappMessageConst;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.mapp.common.util.StringUtil;
import com.ziroom.minsu.mapp.customer.service.CardService;
import com.ziroom.minsu.mapp.customer.service.CustomerService;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.common.commenum.AccountSignEnum;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.SignUtils;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.api.inner.TelExtensionService;
import com.ziroom.minsu.services.customer.dto.CustomerPicDto;
import com.ziroom.minsu.services.customer.dto.ImgDto;
import com.ziroom.minsu.services.customer.entity.CustomerDetailImageVo;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.message.api.inner.SysComplainService;
import com.ziroom.minsu.services.order.api.inner.OrderPayService;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;
import com.ziroom.minsu.valenum.customer.CustomerEduEnum;
import com.ziroom.minsu.valenum.customer.CustomerIdTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerSexEnum;
import com.ziroom.minsu.valenum.customer.IsLandlordEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum007Enum;
import com.ziroom.tech.storage.client.domain.FileInfoResponse;
import com.ziroom.tech.storage.client.domain.FileInfoResponse.InternalFile;
import com.ziroom.tech.storage.client.service.StorageService;

/**
 * <p>
 * 用户信息
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年5月5日
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/personal")
@Controller
public class PersonalCenterController {

	@Resource(name="mapp.messageSource")
	private MessageSource messageSource;

	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name = "customer.telExtensionService")
	private TelExtensionService telExtensionService;

	@Resource(name = "order.orderPayService")
	private OrderPayService orderPayService;

	@Resource(name = "m.customerService")
	private CustomerService customerService;

    @Resource(name = "m.cardService")
    private CardService cardService;

	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Resource(name = "message.sysComplainService")
	private SysComplainService sysComplainService;

	@Resource(name="storageService")
	private StorageService storageService;

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
	
    @Value("#{'${ACCOUNT.SYSTEM_SOURCE}'.trim()}")
    private String SYSTEM_SOURCE;
    
    @Value("#{'${passport_app_key_value}'.trim()}")
    private String appKeyValue;
    
    @Value("#{'${passport_save_appid}'.trim()}")
    private String saveAppid;
    
    @Value("#{'${passport_save_cert_uid}'.trim()}")
    private String saveCertUid;

	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(PersonalCenterController.class);
	
	
	
    @Value("#{'${AUTH_CODE}'.trim()}")
    private String AUTH_CODE;
    
    @Value("#{'${AUTH_SECRET_KEY}'.trim()}")
    private String AUTH_SECRET_KEY;

	/**
	 * 初始化个人中心
	 * @author jixd
	 * @create 2016年5月5日下午5:36:37
	 * @param model
	 * @param uid
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/initPersonalCenter")
	public String initCustomerCenter(HttpServletRequest request,Model model) {
		try {
			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String uid = customerVo.getUid();
		
			String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);

			CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
            });
			//如果为空
			if(!Check.NuNObj(entity)){
				model.addAttribute("customer", entity);
				if(entity.getAuditStatus() == AuditStatusEnum.COMPLETE.getCode()){
					model.addAttribute("isAuth", "已认证");
				}else{
					model.addAttribute("isAuth", "未认证");
				}
			}else{
				model.addAttribute("isAuth", "未认证");
			}
			
			String phone400 = "";
			if(!Check.NuNStr(customerVo.getHostNumber())){
				phone400 = customerVo.getHostNumber();
				/*if(!Check.NuNStr(customerVo.getZiroomPhone())){
					phone400 = phone400 + "," + customerVo.getZiroomPhone();
				}*/
			}
			model.addAttribute("phone400", phone400);
			model.addAttribute("authCode", entity == null ? 0 : entity.getAuditStatus());
			model.addAttribute("picBaseAddrMona", picBaseAddrMona);
			model.addAttribute("default_head_size", default_head_size);
			model.addAttribute("menuType", "userCenter");
			model.addAttribute("landlordUid", uid);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
		return "personal/myCenter";
	}
	
	/**
	 * 
	 * 上传剪切头像
	 *
	 * @author jixd
	 * @created 2016年5月30日 下午2:24:58
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/updateHeadPicBinary")
	@ResponseBody
	public DataTransferObject updateHeadPicBinary(HttpServletRequest request){
		DataTransferObject dto = new DataTransferObject();
		try {
			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String uid = customerVo.getUid();
			String picBin = request.getParameter("picBin");
			if(Check.NuNStr(picBin)){
				dto.setErrCode(1);
				dto.setMsg("图片信息为空");
				return dto;
			}
			
			BASE64Decoder decoder = new BASE64Decoder();
			String str = picBin.substring(picBin.indexOf("base64") +7);
			byte[] b = decoder.decodeBuffer(str);  
            for(int i=0;i<b.length;++i) {  
                if(b[i]<0){//调整异常数据  
                    b[i]+=256;  
                }  
            }
            
            //图片上传成功后才删除原图片
            String fileName = StringUtil.createNoncestr(4) + ".jpg";
            FileInfoResponse fileResponse = storageService.upload(storageKey, storageLimit, fileName, b, CustomerPicTypeEnum.ZJZM.getName(),0l, "民宿用户头像");
            if(!"0".equals(fileResponse.getResponseCode())){
            	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            	dto.setMsg("图片上传失败");
            	return dto;
            }
            
//			// 删除原头像
//			CustomerPicDto picdto = new CustomerPicDto();
//			picdto.setUid(uid);
//			picdto.setPicType(CustomerPicTypeEnum.YHTX.getCode());
//			String picJson = customerMsgManagerService.getCustomerPicListByType(JsonEntityTransform.Object2Json(picdto));
//			DataTransferObject picResultDto = JsonEntityTransform.json2DataTransferObject(picJson);
//			List<CustomerPicMsgEntity> customerPicList = picResultDto.parseData("customerPicList", new TypeReference<List<CustomerPicMsgEntity>>() {
//            });
//			//防止有多个头像生效状态，查询list集合，遍历删除
//			if(!Check.NuNCollection(customerPicList)){
//				for(CustomerPicMsgEntity picMsg:customerPicList){
//					CustomerPicMsgEntity picEntity =  new CustomerPicMsgEntity();
//					picEntity.setFid(picMsg.getFid());
//					//删除该图片
//					picEntity.setIsDel(1);
//					dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.updateCustomerPicMsg(JsonEntityTransform.Object2Json(picEntity)));
//					if(dto.getCode() == DataTransferObject.SUCCESS){
////						storageService.delete(picMsg.getPicServerUuid());
//					}else{
//						LogUtil.error(LOGGER, "【删除头像失败】 msg={}", dto.getMsg());
//						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
//						dto.setMsg("删除头像失败");
//						return dto;
//					}
//				}
//			}
			
			CustomerPicMsgEntity customerPicMsgEntity = new CustomerPicMsgEntity();
			customerPicMsgEntity.setFid(UUIDGenerator.hexUUID());
			customerPicMsgEntity.setUid(uid);
			customerPicMsgEntity.setPicType(CustomerPicTypeEnum.YHTX.getCode());
			customerPicMsgEntity.setPicBaseUrl(fileResponse.getFile().getUrlBase());
			customerPicMsgEntity.setPicName(fileResponse.getFile().getOriginalFilename());
			customerPicMsgEntity.setPicSuffix(fileResponse.getFile().getUrlExt());
			customerPicMsgEntity.setPicServerUuid(fileResponse.getFile().getUuid());
			customerPicMsgEntity.setLastModifyDate(new Date());
			customerPicMsgEntity.setIsDel(0);

			String savePicJson = customerMsgManagerService.insertCustomerPicMsgAndDelOthers(JsonEntityTransform.Object2Json(customerPicMsgEntity));
			DataTransferObject dtoSave = JsonEntityTransform.json2DataTransferObject(savePicJson);
			if (dtoSave.getCode() != DataTransferObject.SUCCESS) {
				return dtoSave;
			}
			//刷新session数据
			refreshSession(request, uid);
			return dto;
		} catch (IOException e) {
			LogUtil.info(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("服务错误");
		}
		return dto;
	}
	
	
	/**
	 * 
	 * 嵌入android页面后上传头像
	 *
	 * @author jixd
	 * @created 2016年5月27日 下午1:22:01
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/updateHeadPicAndroid")
	@ResponseBody
	public DataTransferObject updateHeadPicForAndroid(HttpServletRequest request,ImgDto imgDto){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(imgDto)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("没有图片数据");
			return dto;
		}
		
		CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		String uid = customerVo.getUid();
//
//		CustomerPicDto picdto = new CustomerPicDto();
//		picdto.setUid(uid);
//		picdto.setPicType(CustomerPicTypeEnum.YHTX.getCode());
//		String picJson = customerMsgManagerService.getCustomerPicListByType(JsonEntityTransform.Object2Json(picdto));
//		DataTransferObject picResultDto = JsonEntityTransform.json2DataTransferObject(picJson);
//		List<CustomerPicMsgEntity> customerPicList = picResultDto.parseData("customerPicList", new TypeReference<List<CustomerPicMsgEntity>>() {
//        });
//		//防止有多个头像生效状态，查询list集合，遍历删除
//		if(!Check.NuNCollection(customerPicList)){
//			for(CustomerPicMsgEntity picMsg:customerPicList){
//				CustomerPicMsgEntity picEntity =  new CustomerPicMsgEntity();
//				picEntity.setFid(picMsg.getFid());
//				//删除该图片
//				picEntity.setIsDel(1);
//				dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.updateCustomerPicMsg(JsonEntityTransform.Object2Json(picEntity)));
//				if(dto.getCode() == DataTransferObject.SUCCESS){
////					try {
////						storageService.delete(picMsg.getPicServerUuid());
////					} catch (Exception e) {
////						LogUtil.error(LOGGER, "删除用户头像失败，picserveruuid={},e={}",picMsg.getPicServerUuid(), e);
////					}
//				}else{
//					LogUtil.error(LOGGER, "【删除头像失败】 msg={}", dto.getMsg());
//					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
//					dto.setMsg("删除头像失败");
//					return dto;
//				}
//			}
//		}
		
		CustomerPicMsgEntity customerPicMsgEntity = new CustomerPicMsgEntity();
		customerPicMsgEntity.setFid(UUIDGenerator.hexUUID());
		customerPicMsgEntity.setUid(uid);
		customerPicMsgEntity.setPicType(CustomerPicTypeEnum.YHTX.getCode());
		customerPicMsgEntity.setPicBaseUrl(imgDto.getUrlBase());
		customerPicMsgEntity.setPicName(imgDto.getOriginalFilename());
		customerPicMsgEntity.setPicSuffix(imgDto.getUrlExt());
		customerPicMsgEntity.setPicServerUuid(imgDto.getUuid());
		customerPicMsgEntity.setLastModifyDate(new Date());
		customerPicMsgEntity.setIsDel(0);

		String savePicJson = customerMsgManagerService.insertCustomerPicMsgAndDelOthers(JsonEntityTransform.Object2Json(customerPicMsgEntity));
		DataTransferObject dtoSave = JsonEntityTransform.json2DataTransferObject(savePicJson);
		if (dtoSave.getCode() != DataTransferObject.SUCCESS) {
			return dtoSave;
		}


		//刷新session数据
		refreshSession(request,uid);
		dto.putValue("picUrl", imgDto.getUrl());
		return dto;
	}
	
	/**
	 * 
	 * 处理图片上传后操作
	 *
	 * @author jixd
	 * @created 2016年5月30日 下午6:28:02
	 *
	 * @return
	 */
//	@Deprecated
//	private DataTransferObject doCustomerHeadPic(CustomerPicMsgEntity customerPicMsgEntity){
//		DataTransferObject dto = new DataTransferObject();
//		// 插入用户图片表
//		String savePicJson = customerMsgManagerService.insertCustomerPicMsg(JsonEntityTransform.Object2Json(customerPicMsgEntity));
//		dto = JsonEntityTransform.json2DataTransferObject(savePicJson);
//		if (dto.getCode() != 0) {
//			return dto;
//		}
//		//头像更新成功，修改为已上传状态
//		CustomerBaseMsgEntity customerBase = new CustomerBaseMsgEntity();
//		customerBase.setUid(customerPicMsgEntity.getUid());
//		//customerBase.setAuditStatus(AuditStatusEnum.SUBMITAUDIT.getCode());
//		customerBase.setIsUploadIcon(YesOrNoEnum.YES.getCode());
//		customerMsgManagerService.updateCustomerBaseMsg(JsonEntityTransform.Object2Json(customerBase));
//		return dto;
//
//	}

	
	
	/**
	 * 
	 * 上传用户图片
	 *
	 * @author jixd
	 * @created 2016年5月15日 下午3:41:53
	 *
	 * @param file
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/uploadCustomerPic")
	@ResponseBody
	public DataTransferObject uploadCustomerPic(MultipartFile file){
		// 上传图片
		DataTransferObject dto = new DataTransferObject();
		try {
			FileInfoResponse fileResponse = storageService.upload(storageKey, storageLimit, file.getOriginalFilename(), file.getBytes(), "民宿用户图片",
					0l, file.getOriginalFilename());
			InternalFile fileInfo = fileResponse.getFile();
			dto.putValue("fileInfo", fileInfo);
		} catch (IOException e) {
			dto.setErrCode(1);
			dto.setMsg("上传文件出错");
			e.printStackTrace();
		}
		return dto;
	}

	/**
	 * 
	 * 删除用户图片
	 *
	 * @author jixd
	 * @created 2016年5月15日 下午3:49:36
	 *
	 * @param picServerUuid
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/delCustomerPic")
	@ResponseBody
	public DataTransferObject delCustomerPic(String picServerUuid){
		DataTransferObject dto = new DataTransferObject();
		try{
			if (Check.NuNStr(picServerUuid)) {
				dto.setErrCode(1);
				dto.setMsg("删除图片id不存在");
				return dto;
			}

//			if(storageService.delete(picServerUuid)){
//				dto.setErrCode(0);
//				return dto;
//			}

		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
		}
		return dto;
	}


	/**
	 * 个人资料
	 * @author lishaochuan
	 * @create 2016年5月5日下午5:36:46
	 * @param model
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/showBasicDetail")
	public String showPersonalDetail(HttpServletRequest request,Model model){
		try {
			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String resultJson = customerInfoService.getCustomerInfoByUid(customerVo.getUid());
			CustomerBaseMsgEntity customerBase = SOAResParseUtil.getValueFromDataByKey(resultJson, "customerBase", CustomerBaseMsgEntity.class);
			//如果没有用户信息创建一个空的
			if(Check.NuNObj(customerBase)){
				customerBase = new CustomerBaseMsgEntity();
			}
			String customerEduName = customerBase.getCustomerEdu() == null ? "" :(CustomerEduEnum.getCustomerEduByCode(customerBase.getCustomerEdu()).getName());
			String sexName = "请选择您的性别";
			if(!Check.NuNObj(customerBase.getCustomerSex())){
				Integer customerSex = customerBase.getCustomerSex();
				CustomerSexEnum sexEnum = CustomerSexEnum.getCustomerSexEnumByCode(customerSex);
				if(Check.NuNObj(sexEnum)){
					sexName = "请选择您的性别";
				}else{
					sexName = sexEnum.getValue();
				}
			}
			
			
			String introduceJson = customerMsgManagerService.selectCustomerExtByUid(customerVo.getUid());
			DataTransferObject introduceDto = JsonEntityTransform.json2DataTransferObject(introduceJson);
			if(introduceDto.getCode() == DataTransferObject.SUCCESS){
				CustomerBaseMsgExtEntity extEntity = introduceDto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {});
				if(!Check.NuNObj(extEntity)){
					model.addAttribute("hasIntroduce", 1);
				}
			}
			
			model.addAttribute("sexName", sexName);
			model.addAttribute("customerEduName", customerEduName);
			model.addAttribute("customerBase", customerBase);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
		return "personal/basicDetail";
	}

	/**
	 * 修改个人资料
	 * @author lishaochuan
	 * @create 2016年5月6日下午5:28:52
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/updateCustomerInfo")
	@ResponseBody
	public DataTransferObject updateCustomerInfo(HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		try {
			CustomerVo customerVo=CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String nickName = request.getParameter("nickName");
			String customerSex = request.getParameter("customerSex");
			String customerBirthday = request.getParameter("customerBirthday");
			String customerEmail = request.getParameter("customerEmail");
			String resideAddr = request.getParameter("resideAddr");
			String customerJob = request.getParameter("customerJob");
			String customerEdu=request.getParameter("customerEdu");

			CustomerBaseMsgEntity customerBase = new CustomerBaseMsgEntity();
			customerBase.setUid(customerVo.getUid());
			customerBase.setNickName(nickName);
			customerBase.setCustomerSex(ValueUtil.getintValue(customerSex));
			if(!Check.NuNObj(customerBirthday)){
				customerBase.setCustomerBirthday(DateUtil.parseDate(customerBirthday, DATE_FORMAT_PATTERN));
			}
			customerBase.setCustomerEmail(customerEmail);
			customerBase.setResideAddr(resideAddr);
			customerBase.setCustomerEdu(ValueUtil.getintValue(customerEdu));
			customerBase.setCustomerJob(customerJob);
			
			String customerInfoJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(customerVo.getUid());
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerInfoJson);
			CustomerBaseMsgEntity customerEntity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
            });
			//如果已认证，昵称和个人介绍是不能为空
			if(!Check.NuNObj(customerEntity) && customerEntity.getAuditStatus() == AuditStatusEnum.COMPLETE.getCode()){
				if (Check.NuNStr(customerBase.getNickName())) {
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("昵称不能为空！");
					return dto;
				}
				 
			}
			

			String customerJson = JsonEntityTransform.Object2Json(customerBase);
			String resultJson = customerInfoService.updateCustomerInfo(customerJson);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.SUCCESS){
				//刷新session数据,更新成功 刷新session数据
				refreshSession(request,customerVo.getUid());
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("服务错误");
		}
		return dto;
	}

	/**
	 * 
	 * 回显认证页，需要把图片的信息重新组装
	 *
	 * @author jixd
	 * @created 2016年5月25日 下午10:04:57
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toAuthDetail")
	public String toAuthDetail(HttpServletRequest request,Model model){
		try {
			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String uid = customerVo.getUid();
			//判断入口
			String authType = request.getParameter("authType");
			model.addAttribute("authType", authType == null ? "1" : authType);
			String customerImgJson = customerMsgManagerService.getCustomerDetailImage(uid);
			CustomerDetailImageVo customerImgVo = SOAResParseUtil.getValueFromDataByKey(customerImgJson, "customerImageVo", CustomerDetailImageVo.class);
			List<CustomerPicMsgEntity> customerPicList = customerImgVo.getCustomerPicList();
			Integer idType = customerImgVo.getIdType();
			model.addAttribute("idType", idType);
			//如果不为空
			if(!Check.NuNObj(idType)){
				CustomerIdTypeEnum idTypeEnum = CustomerIdTypeEnum.getCustomerIdTypeByCode(idType);
				if(!Check.NuNObj(idTypeEnum)){
					model.addAttribute("idTypName", idTypeEnum.getName());
				}
			}
			model.addAttribute("customerDetail", customerImgVo);
			//拼接字符串返回前端做回显
			if(!Check.NuNCollection(customerPicList)){
				model.addAttribute("backshow", "1");
				for(CustomerPicMsgEntity imgEntity : customerPicList){
					int picType = imgEntity.getPicType();
					String basePicUrl = imgEntity.getPicBaseUrl();
					String suffix = imgEntity.getPicSuffix();
					String fullPic = PicUtil.getFullPic(picBaseAddrMona, basePicUrl, suffix, list_small_pic);
					if(picType == CustomerPicTypeEnum.ZJZM.getCode()){
						model.addAttribute("upload_data_0", getPicStr(imgEntity));
						model.addAttribute("picZJZM", fullPic);
					}else if(picType == CustomerPicTypeEnum.ZJFM.getCode()){
						model.addAttribute("upload_data_1", getPicStr(imgEntity));
						model.addAttribute("picZJFM",fullPic);
					}else if(picType ==  CustomerPicTypeEnum.ZJSC.getCode()){
						model.addAttribute("upload_data_2", getPicStr(imgEntity));
						model.addAttribute("picZJSC", fullPic);
					}
				}
			}
			model.addAttribute("list_small_pic", list_small_pic);
			model.addAttribute("picBaseAddrMona", picBaseAddrMona);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "error:{}", e);
			e.printStackTrace();
		}
		return "personal/authDetail";
	}

	/**
	 * 
	 * 显示认证信息
	 *
	 * @author jixd
	 * @created 2016年6月12日 下午1:45:30
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/showAuthDetail")
	public String showAuthDetail(HttpServletRequest request,Model model){
		try {
			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String uid = customerVo.getUid();

			String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
			CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
            });

			int identity = entity.getIsIdentityAuth();
			if(identity == 1){
				model.addAttribute("hasIdentity","已完成");
			}else if(identity == 0){
				model.addAttribute("hasIdentity","未完成");
			}
			String idName = CustomerIdTypeEnum.getCustomerIdTypeByCode(entity.getIdType()).getName();
			model.addAttribute("idName", idName);
			model.addAttribute("customerEntity", entity);

			CustomerPicDto picDto = new CustomerPicDto();
			picDto.setUid(uid);
			picDto.setPicType(CustomerPicTypeEnum.ZJZM.getCode());
			String picJson1 = JsonEntityTransform.Object2Json(picDto);
			String picResultJosn1 = customerMsgManagerService.getCustomerPicByType(picJson1);
			CustomerPicMsgEntity customerPic1 = SOAResParseUtil.getValueFromDataByKey(picResultJosn1,
					"customerPicMsgEntity", CustomerPicMsgEntity.class);
			String cardPicUrl1 = "";
			if(!Check.NuNObj(customerPic1)){
				cardPicUrl1 = PicUtil.getFullPic(picBaseAddrMona, customerPic1.getPicBaseUrl(), customerPic1.getPicSuffix(), detail_big_pic);
				model.addAttribute("cardPicUrl1", cardPicUrl1);
			}

			picDto.setPicType(CustomerPicTypeEnum.ZJFM.getCode());
			String picJson2 = JsonEntityTransform.Object2Json(picDto);
			String picResultJosn2 = customerMsgManagerService.getCustomerPicByType(picJson2);
			CustomerPicMsgEntity customerPic2 = SOAResParseUtil.getValueFromDataByKey(picResultJosn2,
					"customerPicMsgEntity", CustomerPicMsgEntity.class);
			String cardPicUrl2 = "";
			if(!Check.NuNObj(customerPic2)){
				cardPicUrl2 = PicUtil.getFullPic(picBaseAddrMona, customerPic2.getPicBaseUrl(), customerPic2.getPicSuffix(), detail_big_pic);
				model.addAttribute("cardPicUrl2", cardPicUrl2);
			}

			picDto.setPicType(CustomerPicTypeEnum.ZJSC.getCode());
			String picJson3 = JsonEntityTransform.Object2Json(picDto);
			String picResultJosn3 = customerMsgManagerService.getCustomerPicByType(picJson3);
			CustomerPicMsgEntity customerPic3 = SOAResParseUtil.getValueFromDataByKey(picResultJosn3,
                    "customerPicMsgEntity", CustomerPicMsgEntity.class);
			String cardPicUrl3 = "";
			if(!Check.NuNObj(customerPic3)){
				cardPicUrl3 = PicUtil.getFullPic(picBaseAddrMona, customerPic3.getPicBaseUrl(), customerPic3.getPicSuffix(), detail_big_pic);
				model.addAttribute("cardPicUrl3", cardPicUrl3);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
		return "personal/showAuthDetail";
	}



	/**
	 * 修改身份认证信息
	 * @author jixd
	 * @create 2016年5月7日下午4:47:59
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/saveAuthDetail")
	@ResponseBody
	public String updateAuthDetail(HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		try {
			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String uid = customerVo.getUid();
			String realName = request.getParameter("realName");
			String idType = request.getParameter("idType");
			String idNo = request.getParameter("idNo");
			//图片相关信息
			String upload_data_0 = request.getParameter("upload_data_0");
			String upload_data_1 = request.getParameter("upload_data_1");
			String upload_data_2 = request.getParameter("upload_data_2");

			//图片回显的旧数据
			String old_upload_data_0 = request.getParameter("old_upload_data_0");
			String old_upload_data_1 = request.getParameter("old_upload_data_1");
			String old_upload_data_2 = request.getParameter("old_upload_data_2");
			
			if (!Check.NuNStr(realName)) {
				Pattern p = Pattern.compile("^[0-9]*$",Pattern.CASE_INSENSITIVE);
				Matcher matcher = p.matcher(realName);
				if (matcher.find()) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("姓名不能全部为数字");
					return dto.toJsonString();
				}
			}
			//同步信息到自如网，验证证件信息是否被占用 开始
			Map<String,String> savePar = new HashMap<>();
			savePar.put("uid",uid);
			savePar.put("real_name",realName);
			savePar.put("cert_type", ValueUtil.getStrValue(idType));
			savePar.put("cert_num", ValueUtil.getStrValue(idNo));
			String sign = SignUtils.md5AppkeySign(appKeyValue, savePar);
			savePar.put("sign",sign);
			savePar.put("appid",saveAppid);
			savePar.put("auth_code",AUTH_CODE);
			savePar.put("auth_secret_key",AUTH_SECRET_KEY);
            String rstJson = CloseableHttpsUtil.sendFormPost(saveCertUid, savePar);
			JSONObject json = JSONObject.parseObject(rstJson);
			LogUtil.info(LOGGER,"同步用户信息到自如网返回结果：rstJson:{}",rstJson);
			Integer code = json.getInteger("error_code");
			AccountSignEnum signEnum = AccountSignEnum.getAccountSignEnumByCode(code);
			if(signEnum == null){
				 dto.setErrCode(DataTransferObject.ERROR);
				 dto.setMsg("同步用户信息到自如网失败");
				 return dto.toJsonString();
			}
			if (code !=AccountSignEnum.ACCOUNT_CODE_0.getCode()){
				String msg = signEnum.getShowTip();
                LogUtil.info(LOGGER,"同步用户信息到自如网失败,失败原因"+json.getString("error_message"));
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(msg);
                return dto.toJsonString();
        	}
			//同步信息到自如网，验证证件信息是否被占用 结束
			List<CustomerPicMsgEntity> customerPicMsgEntityList = new ArrayList<CustomerPicMsgEntity>();

			//判断是否上传的是新图，如果是新图则删除旧照片的数据，否则不做图片的处理
			if(!upload_data_0.equals(old_upload_data_0)){
				customerPicMsgEntityList.add(getPicEntity(upload_data_0, CustomerPicTypeEnum.ZJZM.getCode()));
				deleteIdcard(uid,0);
			}
			if(!upload_data_1.equals(old_upload_data_1)){
				customerPicMsgEntityList.add(getPicEntity(upload_data_1, CustomerPicTypeEnum.ZJFM.getCode()));
				deleteIdcard(uid,1);
			}
			if(!upload_data_2.equals(old_upload_data_2)){
				customerPicMsgEntityList.add(getPicEntity(upload_data_2, CustomerPicTypeEnum.ZJSC.getCode()));
				deleteIdcard(uid, 2);
			}
			for(CustomerPicMsgEntity picEntity : customerPicMsgEntityList){
				picEntity.setUid(uid);
				picEntity.setIsDel(0);
				picEntity.setCreateDate(new Date());
				picEntity.setLastModifyDate(new Date());
				picEntity.setFid(UUIDGenerator.hexUUID());
			}

			//批量插入图片信息
			if (customerPicMsgEntityList.size() > 0) {
				String savePicJson = customerMsgManagerService.insertCustomerPicMsgList(JsonEntityTransform.Object2Json(customerPicMsgEntityList));
				dto = JsonEntityTransform.json2DataTransferObject(savePicJson);
				if (dto.getCode() != 0) {
					return dto.toJsonString();
				}
			}

			CustomerBaseMsgEntity customerBaseSave = new CustomerBaseMsgEntity();
			customerBaseSave.setUid(uid);
			customerBaseSave.setRealName(realName);
			customerBaseSave.setIdType(ValueUtil.getintValue(idType));
			customerBaseSave.setIdNo(idNo);
			//提交认证信息后更改为房东角色
			customerBaseSave.setIsLandlord(IsLandlordEnum.IS_LANDLORD.getCode());
			// 改成待审核状态
			customerBaseSave.setAuditStatus(AuditStatusEnum.SUBMITAUDIT.getCode());
			//上传身份证
			customerBaseSave.setIsIdentityAuth(YesOrNoEnum.YES.getCode());

			String customerJson = JsonEntityTransform.Object2Json(customerBaseSave);
			String saveBaseJson = customerInfoService.updateCustomerInfo(customerJson);
			dto = JsonEntityTransform.json2DataTransferObject(saveBaseJson);
			if(dto.getCode() == DataTransferObject.SUCCESS){
				//刷新session数据,更新成功 刷新session数据
				refreshSession(request, customerVo.getUid());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("服务错误");
		}
		return dto.toJsonString();
	}
	/**
	 * group1/M00/00/32/ChAiMFc4T7KAG_I7AAAU0MQ1_Zs231:d4f8a742-4bcc-4ac5-89a2-7e64e25d5555:.jpg:1.jpg
	 * 前端拼好的url返回后台接受后处理
	 *
	 * @author jixd
	 * @created 2016年5月15日 下午6:56:59
	 *
	 * @param uploadData
	 * @param i
	 * @return
	 */
	private CustomerPicMsgEntity getPicEntity(String uploadData,int i){
		CustomerPicMsgEntity customerPicMsgEntity = new CustomerPicMsgEntity();
		String[] picParam = uploadData.split(":");
		customerPicMsgEntity.setPicBaseUrl(picParam[0]);
		customerPicMsgEntity.setPicServerUuid(picParam[1]);
		customerPicMsgEntity.setPicSuffix(picParam[2]);
		customerPicMsgEntity.setPicName(picParam[3]);
		customerPicMsgEntity.setPicType(i);
		return customerPicMsgEntity;
	}

	/**
	 * 
	 * 获取图片的str,返回给页面用于回显
	 *
	 * @author jixd
	 * @created 2016年5月25日 下午10:45:37
	 * @param picMsgEntity
	 * @return
	 */
	private String getPicStr(CustomerPicMsgEntity picMsgEntity){
		StringBuilder sb = new StringBuilder();
		String picBaseUrl = picMsgEntity.getPicBaseUrl();
		String picServerUuid = picMsgEntity.getPicServerUuid();
		String picSuffix = picMsgEntity.getPicSuffix();
		String picName = picMsgEntity.getPicName();
		sb.append(picBaseUrl + ":" + picServerUuid + ":" + picSuffix + ":" + picName);
		return sb.toString();
	}

	/**
	 * 删除证件照图片
	 * TODO:测试时使用，完毕后需删除
	 * @author lishaochuan
	 * @create 2016年5月9日下午6:54:21
	 * @param uid
	 * @param picType
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/deleteIdcard")
	@ResponseBody
	public DataTransferObject deleteIdcard(String uid, int picType) {
		DataTransferObject dto = new DataTransferObject();
		try {
			CustomerPicDto picdto = new CustomerPicDto();
			picdto.setUid(uid);
			picdto.setPicType(picType);
			String picJson = customerMsgManagerService.getCustomerPicByType(JsonEntityTransform.Object2Json(picdto));
			DataTransferObject picResultDto = JsonEntityTransform.json2DataTransferObject(picJson);
			CustomerPicMsgEntity customerPicEntity = picResultDto.parseData("customerPicMsgEntity", new TypeReference<CustomerPicMsgEntity>() {
            });
			if(!Check.NuNObj(customerPicEntity)){
				//删除该图片
				customerPicEntity.setIsDel(1);
				dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.updateCustomerPicMsg(JsonEntityTransform.Object2Json(customerPicEntity)));
//				storageService.delete(customerPicEntity.getPicServerUuid());
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("服务错误");
		}
		return dto;
	}

	/**
	 * 账户信息明细
     * 从接口获取
	 * @author afi
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toMyBankAcount")
	public String showAccountDetail(HttpServletRequest request) {
		try {
			// 获取余额
				CustomerVo customerVo=CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String customerVoJson = customerMsgManagerService.getCutomerVoFromDb(customerVo.getUid());
			DataTransferObject newDto = JsonEntityTransform.json2DataTransferObject(customerVoJson);
			if(newDto.getCode() == DataTransferObject.SUCCESS){
				//获取最新的用户信息
				customerVo = newDto.parseData("customerVo", new TypeReference<CustomerVo>() {});
			}
			int userType = UserTypeEnum.TENANT.getUserType();

			if(customerVo.getIsLandlord() == 1){
				userType = UserTypeEnum.LANDLORD.getUserType();
			}
            //获取账户信息
			String accountJson = orderPayService.getAccountBalance(customerVo.getUid(), userType); 
			DataTransferObject accountDto = JsonEntityTransform.json2DataTransferObject(accountJson);
			if(accountDto.getCode() != DataTransferObject.SUCCESS){
				request.setAttribute("balance", "0");
			} else {
				Object balance = accountDto.getData().get("balance");
				if(!Check.NuNObj(balance)){
					request.setAttribute("balance", getPriceFormat(ValueUtil.getintValue(balance)));
				}else{
					request.setAttribute("balance", "0");
				}
			}
            // 获取银行卡信息
            Map<?, ?> bankMap = cardService.getUserCardInfo(customerVo.getUid());
            if (!Check.NuNMap(bankMap)){
                request.setAttribute("haveBank", 0);
				// 直接从银行卡信息中获取开户人姓名
				String bankcardHolder = ValueUtil.getStrValue(bankMap.get("accountName"));
				if (Check.NuNStr(bankcardHolder)) {
					// 开户人姓名为空则取用户真实姓名
					bankcardHolder = customerVo.getRealName();
				}
                request.setAttribute("bankcardHolder", bankcardHolder);
                request.setAttribute("bankName", ValueUtil.getStrValue(bankMap.get("bankName")));
                request.setAttribute("province", ValueUtil.getStrValue(bankMap.get("bankArea")));
                request.setAttribute("bankcardNo", ValueUtil.getStrValue(bankMap.get("bankCardNo")));
            }else {
                request.setAttribute("haveBank", 1);
            }

			String customerJson = customerInfoService.getCustomerInfoByUid(customerVo.getUid());
			CustomerBaseMsgEntity customerBase = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);

			String clearingCode = customerBase.getClearingCode();
			if(TradeRulesEnum007Enum.TradeRulesEnum007001.getValue().equals(clearingCode)){
				request.setAttribute("clearingCode", "1");
			}else if(TradeRulesEnum007Enum.TradeRulesEnum007002.getValue().equals(clearingCode)){
				request.setAttribute("clearingCode", "2");
			}
            request.setAttribute("rentPayment", customerBase.getRentPayment());

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
		return "personal/myBankAcount";
	}



    /**
     * 账户信息
     * @author lishaochuan
     * @create 2016年5月5日下午8:57:45
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/addBank")
    public String addBank(HttpServletRequest request) {
        // 获取余额
        CustomerVo customerVo=CustomerVoUtils.getCusotmerVoFromSesstion(request);

        if (Check.NuNObj(customerVo) || Check.NuNStr(customerVo.getRealName())){
            return "redirect:/personal/43e881/toAuthDetail?authType=3";
        }
        String cardName = "";
        // 获取银行卡信息
		Map<?, ?> bankMap = cardService.getUserCardInfo(customerVo.getUid());
		String dispatchUrl = "";
        if (!Check.NuNMap(bankMap)){
        	// 直接从银行卡信息中获取开户人姓名
        	String bankcardHolder = ValueUtil.getStrValue(bankMap.get("accountName"));
        	if (Check.NuNStr(bankcardHolder)) {
        		// 开户人姓名为空则取用户真实姓名
        		bankcardHolder = customerVo.getRealName();
			}
            request.setAttribute("bankcardHolder", bankcardHolder);
            cardName = ValueUtil.getStrValue(bankMap.get("bankCode"));
            request.setAttribute("cardName", cardName);
            request.setAttribute("bankName", ValueUtil.getStrValue(bankMap.get("bankName")));
            request.setAttribute("province", ValueUtil.getStrValue(bankMap.get("bankArea")));
            request.setAttribute("bankcardNo", ValueUtil.getStrValue(bankMap.get("bankCardNo")));
            dispatchUrl = "personal/delBank";
        } else {
            request.setAttribute("bankcardHolder", customerVo.getRealName());
            request.setAttribute("bankName", "");
            request.setAttribute("bankcardNo",  "");
            request.setAttribute("province", "");
            //获取银行卡列表
            List<Map<String, String>> cardList = cardService.getCardList(cardName);
            request.setAttribute("cardList", cardList);
            
            //获取城市列表
            List<Map<String, String>> cityList = cardService.getCityList();
            request.setAttribute("cityList", cityList);
            dispatchUrl = "personal/addBank";
        }
        return dispatchUrl;
    }


    /**
     * 账户信息 绑定银行卡信息
     * @author afi
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/saveBank")
    @ResponseBody
    public DataTransferObject saveBank(HttpServletRequest request) {
        DataTransferObject dto = new DataTransferObject();
        try {
            CustomerVo customerVo=CustomerVoUtils.getCusotmerVoFromSesstion(request);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("uid", customerVo.getUid());
            paramMap.put("accountName", request.getParameter("realName"));
            paramMap.put("bankArea", request.getParameter("cardCity"));
            paramMap.put("bankCode", ValueUtil.getintValue(request.getParameter("cardName")));
            paramMap.put("bankName", request.getParameter("bankName"));
            paramMap.put("bankCardNo", request.getParameter("bankcardNo"));
            paramMap.put("systemSource", SYSTEM_SOURCE);
            
            //直接保存信息
            Map<?, ?> rst = cardService.bindBankCard(paramMap);
            if (!cardService.invokeStatus(rst)){
            	String message = "操作失败";
            	if (!Check.NuNObj(rst) && "30404".equals(rst.get("errorCode"))) {
            		message = Check.NuNObj(rst.get("message")) ? message : (String) rst.get("message");
				}
            	dto.setMsg(message);
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("服务错误");
        }
        return dto;
    }
    
    
    /**
     * 
     * 账户信息 解绑银行卡
     *
     * @author liujun
     * @created 2016年10月10日
     *
     * @param request
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/delBank")
    @ResponseBody
    public DataTransferObject delBank(HttpServletRequest request) {
    	DataTransferObject dto = new DataTransferObject();
    	try {
    		CustomerVo customerVo=CustomerVoUtils.getCusotmerVoFromSesstion(request);
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		paramMap.put("uid", customerVo.getUid());
    		paramMap.put("bankCode", ValueUtil.getintValue(request.getParameter("cardName")));
    		paramMap.put("bankCardNo", request.getParameter("bankcardNo"));
    		paramMap.put("systemSource", SYSTEM_SOURCE); 
    		
    		//直接保存信息
    		Map<?, ?> rst = cardService.unbindBankCard(paramMap);
    			if (!cardService.invokeStatus(rst)){
    				dto.setMsg("操作失败");
    				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			}
    	} catch (Exception e) {
    		LogUtil.error(LOGGER, "error:{}", e);
    		dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    		dto.setMsg("服务错误");
    	}
    	return dto;
    }


    /**
     * 验证当前是否填写银行卡信息
     * @author afi
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/checkBank")
    @ResponseBody
    public DataTransferObject checkBank(HttpServletRequest request) {
        DataTransferObject dto = new DataTransferObject();
        try {
            CustomerVo customerVo=CustomerVoUtils.getCusotmerVoFromSesstion(request);

            String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(customerVo.getUid());
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);

            CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
            });

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
            if(!Check.NuNStr(nickName)){
                isFinishNickName = YesOrNoEnum.YES.getCode();
            }
            Integer isFinishIntroduce = YesOrNoEnum.NO.getCode();
            DataTransferObject customerExtDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.selectCustomerExtByUid(customerVo.getUid()));
            if(customerExtDto.getCode() == DataTransferObject.SUCCESS){
                CustomerBaseMsgExtEntity customerBaseMsgExt = customerExtDto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {});
                if(!Check.NuNObj(customerBaseMsgExt) && !Check.NuNStr(customerBaseMsgExt.getCustomerIntroduce())){
                    isFinishIntroduce = YesOrNoEnum.YES.getCode();
                }
            }
            if(isUploadIcon == YesOrNoEnum.YES.getCode()
                    && isFinishNickName == YesOrNoEnum.YES.getCode()
                    && isFinishIntroduce == YesOrNoEnum.YES.getCode()){
                isFinishHead = YesOrNoEnum.YES.getCode();
            }
            int isBank = 0;
            if(isIdentityAuth == YesOrNoEnum.YES.getCode()
                    && isContactAuth == YesOrNoEnum.YES.getCode()
                    && isFinishHead == YesOrNoEnum.YES.getCode()){
                isBank = 2;
            }else {
                Map<?, ?> card = cardService.getUserCardInfo(customerVo.getUid());
                if (!Check.NuNObj(card)){
                    isBank = 1;
                }
            }
            dto.putValue("isBank", isBank);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("服务错误");
        }
        return dto;
    }

    /**
     * 账户信息
     * @author lishaochuan
     * @create 2016年5月5日下午8:57:45
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/toMyBankAcount1")
    public String showAccountDetail1(HttpServletRequest request) {
        try {
            // 获取余额
            CustomerVo customerVo=CustomerVoUtils.getCusotmerVoFromSesstion(request);

            String customerVoJson = customerMsgManagerService.getCutomerVoFromDb(customerVo.getUid());

            DataTransferObject newDto = JsonEntityTransform.json2DataTransferObject(customerVoJson);
            if(newDto.getCode() == DataTransferObject.SUCCESS){
                //获取最新的用户信息
                customerVo = newDto.parseData("customerVo", new TypeReference<CustomerVo>() {
                });
            }

            int userType = UserTypeEnum.TENANT.getUserType();

            if(customerVo.getIsLandlord() == 1){
                userType = UserTypeEnum.LANDLORD.getUserType();;
            }
            String accountJson = orderPayService.getAccountBalance(customerVo.getUid(), userType);
            DataTransferObject accountDto = JsonEntityTransform.json2DataTransferObject(accountJson);
            if(accountDto.getCode() != DataTransferObject.SUCCESS){
                request.setAttribute("balance", "0");
            } else {
                Object balance = accountDto.getData().get("balance");
                if(!Check.NuNObj(balance)){
                    request.setAttribute("balance", getPriceFormat(ValueUtil.getintValue(balance)));
                }else{
                    request.setAttribute("balance", "0");
                }
            }

            // 获取银行卡信息
            String bankJson = customerInfoService.getCustomerBankcard(customerVo.getUid());
            CustomerBankCardMsgEntity bankcardEntity = SOAResParseUtil.getValueFromDataByKey(bankJson, "bankcard", CustomerBankCardMsgEntity.class);
            if(!Check.NuNObj(bankcardEntity)){
                request.setAttribute("haveBank", 0);
                request.setAttribute("bankcardHolder", bankcardEntity.getBankcardHolder());
                request.setAttribute("bankName", bankcardEntity.getBankName());
                request.setAttribute("bankcardNo", bankCardReplaceWithStar(bankcardEntity.getBankcardNo()));

            } else {
                request.setAttribute("haveBank", 1);
            }

            String customerJson = customerInfoService.getCustomerInfoByUid(customerVo.getUid());
            CustomerBaseMsgEntity customerBase = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);

            String clearingCode = customerBase.getClearingCode();
            if(TradeRulesEnum007Enum.TradeRulesEnum007001.getValue().equals(clearingCode)) {
                request.setAttribute("clearingCode", "1");
            }else if(TradeRulesEnum007Enum.TradeRulesEnum007002.getValue().equals(clearingCode)){
                request.setAttribute("clearingCode", "2");
            }
            request.setAttribute("rentPayment", customerBase.getRentPayment());

        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
        }
        return "personal/myBankAcount";
    }

	/**
	 * 收款信息页面
	 * @author lishaochuan
	 * @create 2016年5月6日下午10:32:44
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/updatePayInfo")
	public String updatePayInfo(HttpServletRequest request) {
		try {
			CustomerVo customerVo=CustomerVoUtils.getCusotmerVoFromSesstion(request);
			// 获取用户基本信息
			String customerJson = customerInfoService.getCustomerInfoByUid(customerVo.getUid());
			CustomerBaseMsgEntity customerBase = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);

			String clearingCode = customerBase.getClearingCode();
			if(TradeRulesEnum007Enum.TradeRulesEnum007001.getValue().equals(clearingCode)){
				request.setAttribute("clearingCode", "1");
			}else if(TradeRulesEnum007Enum.TradeRulesEnum007002.getValue().equals(clearingCode)){
				request.setAttribute("clearingCode", "2");
			}
			String rulesJson = cityTemplateService.getTextListByLikeCodes(null, TradeRulesEnum.TradeRulesEnum007.getValue());
			List<MinsuEleEntity> confList = SOAResParseUtil.getListValueFromDataByKey(rulesJson, "confList", MinsuEleEntity.class);
			for (MinsuEleEntity minsuEleEntity : confList) {
				if (TradeRulesEnum007Enum.TradeRulesEnum007001.getValue().equals(minsuEleEntity.getEleKey())) {
					request.setAttribute("checkByOrder", ValueUtil.getdoubleValue(minsuEleEntity.getEleValue()) * 100 + "%");
                }
                if (TradeRulesEnum007Enum.TradeRulesEnum007002.getValue().equals(minsuEleEntity.getEleKey())) {
					request.setAttribute("checkByDay", ValueUtil.getdoubleValue(minsuEleEntity.getEleValue()) * 100 +"%");
				}
			}

		} catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
        }
        return "personal/settleWay";
	}



    /**
     * 收款方式
     * @param model
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/updatePaymentInfo")
    public String updateMoneyInfo(HttpServletRequest request,Model model) {
        try {
            CustomerVo customerVo=CustomerVoUtils.getCusotmerVoFromSesstion(request);
            // 获取用户基本信息
            String customerJson = customerInfoService.getCustomerInfoByUid(customerVo.getUid());
            CustomerBaseMsgEntity customerBase = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
            Integer rentPayment = customerBase.getRentPayment();
            model.addAttribute("rentPayment",rentPayment);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
        }
        return "personal/rentPayment";
    }


    /**
     * 保存收款信息
     * @author lishaochuan
     * @create 2016年5月7日下午2:51:22
     * @param request
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/savePaymentInfo")
    @ResponseBody
    public DataTransferObject savePaymentInfo(HttpServletRequest request) {
        DataTransferObject dto = new DataTransferObject();
        try {
            CustomerVo customerVo=CustomerVoUtils.getCusotmerVoFromSesstion(request);
            String rentPayment = request.getParameter("rentPayment");
            CustomerBaseMsgEntity customerBase = new CustomerBaseMsgEntity();
            customerBase.setUid(customerVo.getUid());
            customerBase.setRentPayment(ValueUtil.getintValue(rentPayment));
            customerInfoService.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBase));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("服务错误");
        }
        return dto;
    }


	/**
	 * 保存收款信息
	 * @author lishaochuan
	 * @create 2016年5月7日下午2:51:22
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/savePayInfo")
	@ResponseBody
	public DataTransferObject savePayInfo(HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		try {
			CustomerVo customerVo=CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String clearingCode = request.getParameter("clearingCode");
			if("1".equals(clearingCode)){
				clearingCode = TradeRulesEnum007Enum.TradeRulesEnum007001.getValue();
			}else if("2".equals(clearingCode)){
				clearingCode = TradeRulesEnum007Enum.TradeRulesEnum007002.getValue();
			}
			CustomerBaseMsgEntity customerBase = new CustomerBaseMsgEntity();
			customerBase.setUid(customerVo.getUid());
			customerBase.setClearingCode(clearingCode);
			customerInfoService.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBase));

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("服务错误");
		}
		return dto;
	}

	/**
	 * 跳转到投诉建议页面
	 * @author lishaochuan
	 * @create 2016年5月7日下午3:40:20
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/complain")
	public String customerComplain() {
		return "personal/complain";
	}
	/**
	 * 
	 * 跳转到个人介绍页面
	 *
	 * @author jixd
	 * @created 2016年5月28日 下午3:13:01
	 *
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/introduce")
    public String customerIntroduce(HttpServletRequest request){
		CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		CustomerBaseMsgExtEntity customerBaseMsgExt = null;
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.selectCustomerExtByUid(customerVo.getUid()));
		if(dto.getCode() == DataTransferObject.SUCCESS){
			 customerBaseMsgExt = dto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {});
		}
		String customerIntroduce = customerBaseMsgExt == null ? "" : customerBaseMsgExt.getCustomerIntroduce();
		request.setAttribute("customerIntroduce", customerIntroduce);
        //保存成功后跳转页面
        request.setAttribute("type", request.getParameter("type"));
		return "personal/introduce";
	}

	/**
	 * 
	 * 保存投诉建议
	 *
	 * @author jixd
	 * @created 2016年5月28日 下午4:02:54
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/saveComplain")
	@ResponseBody
	public DataTransferObject saveCustomerComplain(HttpServletRequest request,Model model) {
		DataTransferObject dto = new DataTransferObject();
		try {
			CustomerVo c  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String uid = c.getUid();
			String complain  = request.getParameter("complain");
            if (!Check.NuNStr(complain)){
				if (complain.length() > 100){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("超过100字数限制");
                    return dto;
				}
			}

			SysComplainEntity sysComplainEntity = new SysComplainEntity();
            sysComplainEntity.setComplainUid(uid);
            sysComplainEntity.setComplainUsername(c.getRealName());
			sysComplainEntity.setComplainMphone(c.getShowMobile());
			sysComplainEntity.setContent(complain);
			
			if(Check.NuNStr(c.getShowMobile())) {
                LogUtil.info(LOGGER, "投诉保存失败，当前用户手机号不存在，保存实体sysComplainEntity={}", sysComplainEntity.toJsonStr());
				return dto;
			}
			sysComplainService.save(JsonEntityTransform.Object2Json(sysComplainEntity));
        } catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("保存失败");
        }
        return dto;
	}
	
	/**
	 * 
	 * 保存房东的自我介绍
	 *
	 * @author jixd
	 * @created 2016年5月28日 下午3:21:21
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/saveIntroduce")
	@ResponseBody
	public DataTransferObject saveCustomerIntroduce(HttpServletRequest request,Model model) {
		DataTransferObject dto = new DataTransferObject();
		try {
			CustomerVo c  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String uid = c.getUid();
			String introduce  = request.getParameter("introduce");
			
			String customerInfoJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerInfoJson);
			CustomerBaseMsgEntity customerEntity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
            });
			//如果已认证，昵称和个人介绍是不能为空
			if(!Check.NuNObj(customerEntity) && customerEntity.getAuditStatus() == AuditStatusEnum.COMPLETE.getCode()){
				if (Check.NuNStr(introduce)) {
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("个人介绍不能为空！");
					return dto;
				}
				 
			}
			
			if(!Check.NuNStr(introduce)){
				if(introduce.length() > 500){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("超过500字数限制");
					return dto;
				}
				if(introduce.length() <100){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("字数不能少于100字");
					return dto;
				}
			}
			
			//如果存在更新，不存在则创建新的自我介绍
			CustomerBaseMsgExtEntity customerBaseMsgExt = null;
			dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.selectCustomerExtByUid(uid));
			if(dto.getCode() == DataTransferObject.SUCCESS){
				 customerBaseMsgExt = dto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {});
				 if(!Check.NuNObj(customerBaseMsgExt)){
					//如果不为空的话 直接更新介绍
					 customerBaseMsgExt.setCustomerIntroduce(introduce);
					 String updateJson = customerMsgManagerService.updateCustomerExtByUid(JsonEntityTransform.Object2Json(customerBaseMsgExt));
					 return JsonEntityTransform.json2DataTransferObject(updateJson);
				 }
			}
			
			CustomerBaseMsgExtEntity baseMsgExtEntity = new CustomerBaseMsgExtEntity();
			baseMsgExtEntity.setCustomerIntroduce(introduce);
			baseMsgExtEntity.setUid(uid);
			baseMsgExtEntity.setFid(UUIDGenerator.hexUUID());
			String insertJson = customerMsgManagerService.insertCustomerExt(JsonEntityTransform.Object2Json(baseMsgExtEntity));
			return JsonEntityTransform.json2DataTransferObject(insertJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("保存失败");
		}
		return dto;
	}


	/**
	 * 
	 * 刷新session
	 *
	 * @author jixd
	 * @created 2016年5月16日 上午5:28:21
	 *
	 * @param request
	 * @param uid
	 */
	private void refreshSession(HttpServletRequest request,String uid){
		//更新session数据
		String newJson= customerMsgManagerService.getCutomerVoFromDb(uid);
		DataTransferObject newDto = JsonEntityTransform.json2DataTransferObject(newJson);
		if(newDto.getCode() == DataTransferObject.SUCCESS){
			//newDto.parseData("customerVo", newJson);
			CustomerVo newVo = newDto.parseData("customerVo", new TypeReference<CustomerVo>() {});
			if(!Check.NuNObj(newVo)){
				request.getSession().setAttribute(MappMessageConst.SESSION_USER_KEY,newVo);
			}
		}
	}

	/**
	 * 
	 * 跳转到提示成功页
	 *
	 * @author jixd
	 * @created 2016年5月15日 下午11:17:26
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/success")
	public String showSuccess(Model model,int type){
		//保存审核状态成功
		if(type == 1){
			model.addAttribute("msg", "您的认证信息已提交，审核通过后就可以发布房源了！");
		}else if(type == 2){
			//保存建议成功
			model.addAttribute("msg", "您的建议已提交成功");
		}
		return "personal/authSuccess";
	}

	/**
	 * 银行卡号保密
	 * @author lishaochuan
	 * @create 2016年5月5日下午9:17:30
	 * @param bankCard
	 * @return
	 */
	public static String bankCardReplaceWithStar(String bankCard) {
		if (Check.NuNStr(bankCard)) {
			return "";
		} else {
			bankCard = bankCard.replaceAll(" ", "");
			return bankCard.replaceAll("(?<=\\d{5})\\d(?=\\d{4})", "*");
		}
	}

	private static String getPriceFormat(int price) {
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,##0.00");

		double priceD = price / 100.00;
		return myformat.format(priceD);
	}
	
	
	


	/**
	 * 
	 * 更新客户昵称(同时需要校验个人介绍和用户头像是否填写 ，都填写的话更新  isUploadIcon)
	 *
	 * @author jixd
	 * @created 2016年6月16日 下午6:51:22
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/updateCustomerNickName")
	@ResponseBody
	public DataTransferObject updateCustomerNickName(HttpServletRequest request){
		CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		String nickName = request.getParameter("nickName");
		if(Check.NuNStr(nickName)){
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(1);
			dto.setMsg("昵称为空");
			return dto;
		}
		
		String uid = customerVo.getUid();
		CustomerBaseMsgEntity customerBaseMsg = new CustomerBaseMsgEntity();
		customerBaseMsg.setNickName(nickName);
		customerBaseMsg.setUid(uid);
		String resultJson = customerInfoService.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsg));
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if(dto.getCode() == DataTransferObject.SUCCESS){
			refreshSession(request,uid);
		}
		return dto;
	}

}
