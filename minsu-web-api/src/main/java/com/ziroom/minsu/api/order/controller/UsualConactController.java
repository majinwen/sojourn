package com.ziroom.minsu.api.order.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.RegExpUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.constant.OrderConst;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.order.dto.*;
import com.ziroom.minsu.api.order.entity.UsualContactVo;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.order.UsualContactEntity;
import com.ziroom.minsu.services.common.utils.CheckIdCardUtils;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderUserService;
import com.ziroom.minsu.services.order.dto.UsualConRequest;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerIdTypeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>常用联系人接口</p>
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
@Controller
@RequestMapping("contact/")
public class UsualConactController extends AbstractController {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(UsualConactController.class);
	
	@Resource(name = "order.orderCommonService")
	private OrderCommonService orderCommonService;
	
	@Resource(name = "order.orderUserService")
	private OrderUserService orderUserService;

	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;


	/**
	 * 
	 * 批量添加入住人信息
	 * 参数：loginToken uid  [UsualContactVo{conName,cardValue,cardType}]
	 *
	 * @author yd
	 * @created 2016年4月30日 下午4:58:17
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_AUTH}/saveContacts")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveContacts(HttpServletRequest request){
		try{
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(logger, "saveContacts params:{}",paramJson);
			UsualContactRequest usualContactRequest = JsonEntityTransform.json2Object(paramJson, UsualContactRequest.class);
			
			DataTransferObject dto = null;
			List<UsualContactVo> listContactVos= usualContactRequest.getListContactVos();
			if(Check.NuNObj(usualContactRequest)||Check.NuNCollection(listContactVos)){
				dto = new DataTransferObject();
				dto.setErrCode(1);
				dto.setMsg("入住人信息不存在");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
			}
			List<String> fidString = new ArrayList<String>();
			for (UsualContactVo usualContactVo : listContactVos) {
				if(Check.NuNObj(usualContactVo.getCardType())){
					LogUtil.error(logger, "联系人证件类型为空");
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("证件类型为空"),HttpStatus.OK);
				}
				if(Check.NuNStr(usualContactVo.getConName())){
					LogUtil.error(logger, "联系人姓名为空");
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("联系人姓名为空"),HttpStatus.OK);
				}
				if(usualContactVo.getConName().length() > 16){
					LogUtil.info(logger, "联系人姓名超过16位,conName:{}", usualContactVo.getConName());
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("姓名太长了"),HttpStatus.OK);
				}
				if (!RegExpUtil.isMobilePhoneNum(usualContactVo.getConTel())){
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("电话号码格式错误"),HttpStatus.OK);
				}
				UsualContactEntity usualContact = new UsualContactEntity();
				usualContact.setFid(UUIDGenerator.hexUUID());
				usualContact.setCardType(usualContactVo.getCardType());
				//必须身份证code
				if(usualContactVo.getCardType() == CustomerIdTypeEnum.ID.getCode()){
					String cardValue = usualContactVo.getCardValue();
					if(!Check.NuNStr(cardValue)){
						//根据身份证号码计算性别入库
						int gen = CheckIdCardUtils.getGenderByIdCard(cardValue);
						if(gen == 0){
							gen = 2;
						}
						usualContact.setConSex(gen);
					}
				}
				usualContact.setCardValue(usualContactVo.getCardValue());
				usualContact.setConName(usualContactVo.getConName());
				usualContact.setUserUid(usualContactRequest.getUid());
				usualContact.setConTel(usualContactVo.getConTel());
				fidString.add(usualContact.getFid());
			    dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.saveUsualContact(JsonEntityTransform.Object2Json(usualContact)));
			}
			if (dto.getCode() == 0) {
				dto.putValue("listFid", fidString);
			}
			LogUtil.debug(logger, "saveContacts resultJson:{}",JsonEntityTransform.Object2Json(dto.getData()));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "saveContacts is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}


	/**
	 * 下单页获取预订人信息
	 * @author lishaochuan
	 * @create 2016/12/2 11:16
	 * @param 
	 * @return 
	 */
	@RequestMapping("${LOGIN_AUTH}/findBookerContacts")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> findBookerContacts(HttpServletRequest request){
		try{
			String userUid = getUserId(request);
			if(Check.NuNStr(userUid)){
				LogUtil.info(logger, "未登录");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("未登录"),HttpStatus.OK);
			}

			// 获取用户信息
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerInfoService.getCustomerInfoByUid(userUid));
			if (customerDto.getCode() == DataTransferObject.ERROR){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("预订人信息获取失败"),HttpStatus.OK);
			}
			CustomerBaseMsgEntity customerBaseMsgEntity = customerDto.parseData("customerBase", new TypeReference<CustomerBaseMsgEntity>() {});
			if (Check.NuNObj(customerBaseMsgEntity)){
				LogUtil.error(logger, "获取用户信息失败，customerDto:{}", customerDto.toJsonString());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("预订人信息获取失败"),HttpStatus.OK);
			}

			// 如果信息不完整，去补全
			if (Check.NuNObj(customerBaseMsgEntity.getRealName())
					|| Check.NuNObj(customerBaseMsgEntity.getCustomerMobile())
					|| Check.NuNObj(customerBaseMsgEntity.getIdType())
					|| Check.NuNObj(customerBaseMsgEntity.getIdNo())
					|| customerBaseMsgEntity.getRealName().equals(customerBaseMsgEntity.getCustomerMobile())) {
				ContactDetailResponse bookerDetailResponse = new ContactDetailResponse();
				bookerDetailResponse.setFid("");
				bookerDetailResponse.setUserUid(customerBaseMsgEntity.getUid());
				bookerDetailResponse.setConName(customerBaseMsgEntity.getRealName());
				bookerDetailResponse.setCardType(customerBaseMsgEntity.getIdType());
				bookerDetailResponse.setCardValue(customerBaseMsgEntity.getIdNo());
				bookerDetailResponse.setConTel(customerBaseMsgEntity.getCustomerMobile());

				FindBookerResponse response = new FindBookerResponse();
				response.setIsNeedReplenish(YesOrNoEnum.YES.getCode());
				response.setTips(OrderConst.MSG_BOOKER_ORDER);
				response.setBookerDetail(bookerDetailResponse);

				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(response), HttpStatus.OK);
			}


			// 获取预订人信息
			UsualContactEntity bookerContact = this.findBookerContacts(customerBaseMsgEntity, userUid);
			if(Check.NuNObj(bookerContact)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("预订人信息获取失败"),HttpStatus.OK);
			}


			// 组装出参，返回给客户端
			ContactDetailResponse bookerDetailResponse = new ContactDetailResponse();
			bookerDetailResponse.setFid(bookerContact.getFid());
			bookerDetailResponse.setUserUid(bookerContact.getUserUid());
			bookerDetailResponse.setConName(bookerContact.getConName());
			bookerDetailResponse.setCardType(bookerContact.getCardType());
			bookerDetailResponse.setCardValue(bookerContact.getCardValue());
			bookerDetailResponse.setConTel(bookerContact.getConTel());

			FindBookerResponse response = new FindBookerResponse();
			response.setIsNeedReplenish(YesOrNoEnum.NO.getCode());
			response.setTips(OrderConst.MSG_BOOKER_ORDER);
			response.setBookerDetail(bookerDetailResponse);

			LogUtil.info(logger, "result:{}", JsonEntityTransform.Object2Json(response));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(response), HttpStatus.OK);

		}catch(Exception e){
			LogUtil.error(logger, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}


	/**
	 * 获取预订人信息
	 * @author lishaochuan
	 * @create 2016/12/5 9:57
	 * @param 
	 * @return 
	 */
	private UsualContactEntity findBookerContacts(CustomerBaseMsgEntity customerBaseMsgEntity, String userUid){
		// 获取预订人联系信息
		DataTransferObject contactDto = JsonEntityTransform.json2DataTransferObject(orderCommonService.getBookerContact(userUid));
		if(contactDto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(logger, "查询预订人信息失败，contactDto:{}", contactDto.toJsonString());
			return null;
		}
		UsualContactEntity bookerContact = contactDto.parseData("bookerContact", new TypeReference<UsualContactEntity>() {});

		// 预订人信息不存在，自动插入
		if (Check.NuNObj(bookerContact)){
			bookerContact = new UsualContactEntity();
			bookerContact.setFid(UUIDGenerator.hexUUID());
			bookerContact.setUserUid(customerBaseMsgEntity.getUid());
			bookerContact.setConName(customerBaseMsgEntity.getRealName());
			bookerContact.setCardType(customerBaseMsgEntity.getIdType());
			bookerContact.setCardValue(customerBaseMsgEntity.getIdNo());
			bookerContact.setConTel(customerBaseMsgEntity.getCustomerMobile());
			bookerContact.setIsBooker(YesOrNoEnum.YES.getCode());
			DataTransferObject savedto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.saveUsualContact(JsonEntityTransform.Object2Json(bookerContact)));
			if(savedto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(logger, "保存预订人信息失败，contactDto:{}", savedto.toJsonString());
				return null;
			}

		} else if(!customerBaseMsgEntity.getRealName().equals(bookerContact.getConName())
				||!customerBaseMsgEntity.getIdType().equals(bookerContact.getCardType())
				||!customerBaseMsgEntity.getIdNo().equals(bookerContact.getCardValue())
				||!customerBaseMsgEntity.getCustomerMobile().equals(bookerContact.getConTel())){
			// 信息不一致，逻辑删除，再插入数据库，然后返回
			bookerContact.setConName(customerBaseMsgEntity.getRealName());
			bookerContact.setCardType(customerBaseMsgEntity.getIdType());
			bookerContact.setCardValue(customerBaseMsgEntity.getIdNo());
			bookerContact.setConTel(customerBaseMsgEntity.getCustomerMobile());

			DataTransferObject updateDto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.updateBookerContact(JsonEntityTransform.Object2Json(bookerContact)));
			if(updateDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(logger, "修改预订人信息失败，contactDto:{}", updateDto.toJsonString());
				return null;
			}
			String fid = updateDto.getData().get("fid") + "";
			bookerContact.setFid(fid);
		}
		return bookerContact;
	}



	/**
	 * 获取入住人列表V2
	 * @author lishaochuan
	 * @create 2016/12/2 16:56
	 * @param 
	 * @return 
	 */
	@RequestMapping("${LOGIN_AUTH}/findContactsV2")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> findContactsV2(HttpServletRequest request){
		try{
			String userUid = getUserId(request);
			if(Check.NuNStr(userUid)){
				LogUtil.info(logger, "未登录");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("未登录"),HttpStatus.OK);
			}

			// 参数
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.debug(logger, "params:{}",paramJson);
			UsualConRequest usualConRequest = JsonEntityTransform.json2Object(paramJson, UsualConRequest.class);
			usualConRequest.setUserUid(userUid);

			// 获取用户信息
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerInfoService.getCustomerInfoByUid(userUid));
			if (customerDto.getCode() == DataTransferObject.ERROR){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("预订人信息获取失败"),HttpStatus.OK);
			}
			CustomerBaseMsgEntity customerBaseMsgEntity = customerDto.parseData("customerBase", new TypeReference<CustomerBaseMsgEntity>() {});
			if (Check.NuNObj(customerBaseMsgEntity)){
				LogUtil.error(logger, "获取用户信息失败，customerDto:{}", customerDto.toJsonString());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("预订人信息获取失败"),HttpStatus.OK);
			}

			// 如果信息不完整，不展示预订人信息
			if (Check.NuNObj(customerBaseMsgEntity.getRealName())
					|| Check.NuNObj(customerBaseMsgEntity.getCustomerMobile())
					|| Check.NuNObj(customerBaseMsgEntity.getIdType())
					|| Check.NuNObj(customerBaseMsgEntity.getIdNo())
					|| customerBaseMsgEntity.getRealName().equals(customerBaseMsgEntity.getCustomerMobile())) {

			}else{
				// 更新预订人信息
				this.findBookerContacts(customerBaseMsgEntity, userUid);
			}

			// 查询普通常用联系人
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.findUsualContactsByContion(JsonEntityTransform.Object2Json(usualConRequest)));
			LogUtil.debug(logger, "resultJson:{}",JsonEntityTransform.Object2Json(dto));

			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}

	
	/**
	 * 
	 * 获取入住人列表V1
	 *
	 * @author yd
	 * @created 2016年4月30日 下午4:58:17
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_AUTH}/findContacts")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> findUsualContacts(HttpServletRequest request){
		try{
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.debug(logger, "findUsualContacts params:{}",paramJson);
			UsualConApiRequest usualConApiRequest = JsonEntityTransform.json2Object(paramJson, UsualConApiRequest.class);

			UsualConRequest usualConRequest = new UsualConRequest();
			BeanUtils.copyProperties(usualConApiRequest, usualConRequest);
			usualConRequest.setUserUid(usualConApiRequest.getUid());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.findUsualContactsByContion(JsonEntityTransform.Object2Json(usualConRequest)));
			LogUtil.debug(logger, "findUsualContacts resultJson:{}",JsonEntityTransform.Object2Json(dto));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "findUsualContacts error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}
	
	/**
	 * 
	 * 修改联系人信息
	 *
	 * @author yd
	 * @created 2016年4月30日 下午4:58:17
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_AUTH}/updateContact")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> updateContact(HttpServletRequest request){
		try{
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(logger, "updateContact params:{}", paramJson);
			UsualContactApiRequest usualContactApi= JsonEntityTransform.json2Object(paramJson, UsualContactApiRequest.class);
			
			UsualContactEntity usualContact = new UsualContactEntity();
			BeanUtils.copyProperties(usualContactApi,usualContact);
			usualContact.setUserUid(usualContactApi.getUid());
			usualContact.setLastModifyDate(new Date());

			if(Check.NuNObj(usualContact.getUserUid())){
				LogUtil.info(logger, "未登录");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("未登录"),HttpStatus.OK);
			}
			if(Check.NuNObj(usualContact.getConName())){
				LogUtil.info(logger, "真实姓名为空");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("真实姓名为空"),HttpStatus.OK);
			}
			if(Check.NuNObj(usualContact.getCardType())){
				LogUtil.info(logger, "联系人证件类型为空");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("证件类型为空"),HttpStatus.OK);
			}
			if(Check.NuNObj(usualContact.getCardValue())){
				LogUtil.info(logger, "联系人证件号码为空");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("证件号码为空"),HttpStatus.OK);
			}
			if(Check.NuNObj(usualContact.getConTel())){
				LogUtil.info(logger, "联系方式为空");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("联系方式为空"),HttpStatus.OK);
			}
			if (!RegExpUtil.isMobilePhoneNum(usualContact.getConTel())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("电话号码格式错误"),HttpStatus.OK);
			}
			// 校验身份证code
			if(usualContact.getCardType() == CustomerIdTypeEnum.ID.getCode()){
				String cardValue = usualContact.getCardValue();
				if(!Check.NuNStr(cardValue)){
					//根据身份证号码计算性别入库
					int gen = CheckIdCardUtils.getGenderByIdCard(cardValue);
					if(gen == 0){
						gen = 2;
					}
					usualContact.setConSex(gen);
				}
			}

			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.updateByFid(JsonEntityTransform.Object2Json(usualContact)));
			LogUtil.info(logger, "updateContact resultJson:{}",JsonEntityTransform.Object2Json(dto));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "updateContact error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}


	/**
	 * 修改联系人
	 * 1、逻辑删除
	 * 2、新增
	 * 
	 * @author lishaochuan
	 * @create 2016/12/21 16:49
	 * @param 
	 * @return 
	 */
	@RequestMapping("${LOGIN_AUTH}/updateContactV2")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> updateContactV2(HttpServletRequest request){
		try{
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(logger, "updateContactV2 params:{}", paramJson);
			UsualContactApiRequest usualContactApi= JsonEntityTransform.json2Object(paramJson, UsualContactApiRequest.class);

			UsualContactEntity usualContact = new UsualContactEntity();
			BeanUtils.copyProperties(usualContactApi,usualContact);
			usualContact.setUserUid(usualContactApi.getUid());
			usualContact.setLastModifyDate(new Date());

			if(Check.NuNObj(usualContact.getUserUid())){
				LogUtil.info(logger, "未登录");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("未登录"),HttpStatus.OK);
			}
			if(Check.NuNObj(usualContact.getConName())){
				LogUtil.info(logger, "真实姓名为空");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("真实姓名为空"),HttpStatus.OK);
			}
			if(Check.NuNObj(usualContact.getCardType())){
				LogUtil.info(logger, "联系人证件类型为空");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("证件类型为空"),HttpStatus.OK);
			}
			if(Check.NuNObj(usualContact.getCardValue())){
				LogUtil.info(logger, "联系人证件号码为空");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("证件号码为空"),HttpStatus.OK);
			}
			if(Check.NuNObj(usualContact.getConTel())){
				LogUtil.info(logger, "联系方式为空");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("联系方式为空"),HttpStatus.OK);
			}
			if (!RegExpUtil.isMobilePhoneNum(usualContact.getConTel())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("电话号码格式错误"),HttpStatus.OK);
			}
			// 校验身份证code
			if(usualContact.getCardType() == CustomerIdTypeEnum.ID.getCode()){
				String cardValue = usualContact.getCardValue();
				if(!Check.NuNStr(cardValue)){
					//根据身份证号码计算性别入库
					int gen = CheckIdCardUtils.getGenderByIdCard(cardValue);
					if(gen == 0){
						gen = 2;
					}
					usualContact.setConSex(gen);
				}
			}

			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.updateLogicDelete(JsonEntityTransform.Object2Json(usualContact)));
			LogUtil.info(logger, "updateContactV2 resultJson:{}",JsonEntityTransform.Object2Json(dto));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "updateContactV2 error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}



	/**
	 * 删除联系人
	 * @author lishaochuan
	 * @create 2016/12/2 10:11
	 * @param 
	 * @return 
	 */
	@RequestMapping("${LOGIN_AUTH}/deleteContact")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> deleteContact(HttpServletRequest request){
		try{
			DataTransferObject dto = new DataTransferObject();
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(logger, "参数:{}", paramJson);

			DeleteContactRequest deleteContactRequest = JsonEntityTransform.json2Object(paramJson, DeleteContactRequest.class);
			String fid = deleteContactRequest.getFid();
			String userUid = getUserId(request);
			if(Check.NuNStr(userUid)){
				LogUtil.info(logger, "未登录");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("未登录"),HttpStatus.OK);
			}
			if(Check.NuNStr(fid)){
				LogUtil.info(logger, "参数错误");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数错误"),HttpStatus.OK);
			}


			orderCommonService.deleteContact(fid, userUid);

			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}
}
