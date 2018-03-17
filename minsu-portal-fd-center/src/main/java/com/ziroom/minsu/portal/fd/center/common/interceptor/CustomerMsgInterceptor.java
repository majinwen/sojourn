/**
 * @FileName: CustomerMsgInterceptor.java
 * @Package com.ziroom.minsu.portal.fd.center.common.interceptor
 * 
 * @author bushujie
 * @created 2016年8月23日 下午2:55:11
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.common.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.portal.fd.center.common.utils.UserUtils;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerAuthService;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.dto.CustomerFieldAuditVo;
import com.ziroom.minsu.services.customer.entity.CustomerDetailImageVo;
import com.ziroom.minsu.services.customer.util.UserHeadImgUtils;
import com.ziroom.minsu.valenum.account.CustomerTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerAuditStatusEnum;
import com.ziroom.minsu.valenum.customer.CustomerUpdateLogEnum;
import com.ziroom.minsu.valenum.customer.IsLandlordEnum;
import com.ziroom.minsu.valenum.customer.PicTypeEnum;

/**
 * <p>用户公共信息拦截器</p>
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
public class CustomerMsgInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(CustomerMsgInterceptor.class);
	@Resource(name="customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Value("#{'${pic_size_120_120}'.trim()}")
	private String pic_size_120_120;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${pic_default_head_url}'.trim()}")
	private String defaultHeadPic;
	
	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name="customer.customerAuthService")
	private CustomerAuthService customerAuthService;

	@Value("#{'${SSO_USER_DETAIL_URL}'.trim()}")
	private String SSO_USER_DETAIL_URL;
	/**
	 * 请求拦截
	 * @throws SOAParseException 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws SOAParseException{
		String uid=UserUtils.getCurrentUid();
		String uri=request.getRequestURI();
		//查询房东基本信息
		String resultJson=customerInfoService.getCustomerInfoByUid(uid);
		CustomerBaseMsgEntity customerBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(resultJson, "customerBase", CustomerBaseMsgEntity.class);
		//异步更新用户头像
		if(!Check.NuNObj(customerBaseMsgEntity)&&customerBaseMsgEntity.getIsLandlord() == IsLandlordEnum.NOT_LANDLORD.getCode()){
			CustomerPicMsgEntity customerPicMsg = UserHeadImgUtils.getHeadImgFromZiroom(uid, SSO_USER_DETAIL_URL);
			if(!Check.NuNObj(customerPicMsg)){
				List<CustomerPicMsgEntity> picList = new ArrayList<CustomerPicMsgEntity>();
				picList.add(customerPicMsg);
				customerAuthService.customerIconAuth(JsonEntityTransform.Object2Json(picList));
			}
		}
		String customerJson=customerMsgManagerService.getCustomerDetailImage(uid);
		CustomerDetailImageVo customerVo=SOAResParseUtil.getValueFromDataByKey(customerJson, "customerImageVo", CustomerDetailImageVo.class);
		
		//如果此房东的审核通过后，修改了头像或者个人介绍，则填充最新修改的后的值
		CustomerPicMsgEntity latestUnAuditHeadPic = new CustomerPicMsgEntity();
	   
		//从t_customer_update_history_log表中查询,看是否存在需要替换的字段
		if(customerVo.getIsLandlord()==CustomerTypeEnum.landlord.getStatusCode()){
			fillLandNewHeadPic(latestUnAuditHeadPic, uid, customerVo);
		}
		//如果是默认昵称，置为空
		if(customerVo.getIsLandlord()==YesOrNoEnum.YES.getCode() && !Check.NuNStr(customerVo.getNickName())){
				if(isDefaultNickName(customerVo.getNickName(), customerVo.getUid())){
					customerVo.setNickName(null);
				}
		}
		request.setAttribute("customerVo", customerVo);
		String headPic = defaultHeadPic;	

		if(!Check.NuNObj(customerVo)&&!Check.NuNCollection(customerVo.getCustomerPicList())){
			for(CustomerPicMsgEntity pic:customerVo.getCustomerPicList()){
				if(PicTypeEnum.USER_PHOTO.getCode()==pic.getPicType()){
					if(!Check.NuNObj(latestUnAuditHeadPic) && !	Check.NuNStr(latestUnAuditHeadPic.getPicBaseUrl())){
						pic = latestUnAuditHeadPic;
					}
					
					headPic = PicUtil.getFullPic(picBaseAddrMona, pic.getPicBaseUrl(),pic.getPicSuffix() , pic_size_120_120);
				}
			}
		}
		if(Check.NuNStr(headPic)){//防止空字符串脏数据
			headPic = defaultHeadPic;
		}
		LogUtil.info(log, "【头像url】={}", headPic);
		request.setAttribute("headPic", headPic);
		request.setAttribute("uri", uri);
		return true;
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
	 * 填充房东最新的修改信息
	 * @param latestUnAuditHeadPic
	 * @param uid
	 */
	public void fillLandNewHeadPic(CustomerPicMsgEntity latestUnAuditHeadPic, String uid, CustomerBaseMsgEntity customerBaseMsg){
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
 					 if(!Check.NuNStr(fieldPath) && !Check.NuNStr(fieldHeadPicPath) && fieldPath.equals(fieldHeadPicPath)){
 						Map<String, Object> headPicMap = new HashMap<String, Object>();
 						headPicMap.put("auditStatus", CustomerAuditStatusEnum.UN_AUDIT.getCode());
 						headPicMap.put("picType", 3);
 						headPicMap.put("uid", uid);
 						String latestUnAuditHeadPicJson = customerInfoService.getLatestUnAuditHeadPic(JsonEntityTransform.Object2Json(headPicMap));
 						DataTransferObject latestUnAuditHeadPicDto = JsonEntityTransform.json2DataTransferObject(latestUnAuditHeadPicJson);
 						if(latestUnAuditHeadPicDto.getCode()==DataTransferObject.SUCCESS){
 							CustomerPicMsgEntity resultUnAuditHeadPic = latestUnAuditHeadPicDto.parseData("customerPicMsg", new TypeReference<CustomerPicMsgEntity>() {});
 						    if(!Check.NuNObj(resultUnAuditHeadPic)){
 						    	BeanUtils.copyProperties(resultUnAuditHeadPic, latestUnAuditHeadPic);
 						    }
 						}
 					}
 					 
 					//修改了昵称
  					String fieldNickNamePath = ClassReflectUtils.getFieldNamePath(CustomerBaseMsgEntity.class,CustomerUpdateLogEnum.Customer_Base_Msg_NickName.getFieldName());
  					if(!Check.NuNStr(fieldPath) && !Check.NuNStr(fieldNickNamePath) && fieldNickNamePath.equals(fieldPath)){
  						//将待审核的字段的newlog的赋到该对象中，以便于审核通过，以该id修改newlog表中该该用户的该审核字段的审核状态
  						customerBaseMsg.setNickName(customerFieldAuditVo.getNewValue());
  					} 
    	    	 }
    	     }
    	}
	}
}
