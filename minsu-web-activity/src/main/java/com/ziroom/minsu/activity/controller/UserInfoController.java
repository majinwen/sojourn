/**
 * @FileName: UserInfoController.java
 * @Package com.ziroom.minsu.activity.controller
 * 
 * @author bushujie
 * @created 2017年8月31日 上午11:19:08
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.activity.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.dto.CustomerPicDto;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;
import com.ziroom.minsu.valenum.customer.IsLandlordEnum;

/**
 * <p>自如客信息获取</p>
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
@RequestMapping("/userInfo")
@Controller
public class UserInfoController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);
	
	
	@Value("#{'${CUSTOMER_DETAIL_URL}'.trim()}")
	private  String CUSTOMER_DETAIL_URL;
	
	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;
    
    @Value("#{'${detail_big_pic}'.trim()}")
    private String detail_big_pic;
    
    @Value("#{'${pic_base_addr_mona}'.trim()}")
    private String picBaseAddrMona;
	
	
	/**
	 * 
	 * uid获取用户头像和昵称
	 *
	 * @author bushujie
	 * @created 2017年8月29日 下午6:50:02
	 *
	 * @return
	 * @throws IOException 
	 * @throws SOAParseException 
	 */
	@RequestMapping("getNicknameAndHeadpicByUid")
	@ResponseBody
	public void getNicknameAndHeadpicByUid(HttpServletResponse response,HttpServletRequest request,String uid) throws IOException, SOAParseException{
        response.setContentType("text/plain");
        String callBackName = request.getParameter("callback");
		DataTransferObject dto=new DataTransferObject();
		//查询自如客头像和昵称
		StringBuffer url = new StringBuffer();
		try {
			url.append(CUSTOMER_DETAIL_URL).append(uid);
			String getResult = CloseableHttpUtil.sendGet(url.toString(), null);
			LogUtil.info(LOGGER, "调用接口：{}，返回用户信息：{}",url.toString(),getResult);
			if(Check.NuNStrStrict(getResult)){
				LogUtil.error(LOGGER, "CUSTOMER_ERROR:根据用户uid={},获取用户信息失败", uid);
				dto.putValue("nickname", "");
				dto.putValue("headimgurl", "");
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
			Map<String, String> resultMap=new HashMap<String, String>();
			try {
				resultMap = (Map<String, String>) JsonEntityTransform.json2Map(getResult);
			} catch (Exception e) {
				LogUtil.info(LOGGER, "用户信息转化错误，请求url={}，返回结果result={}，e={}",url.toString(),getResult,e);
			}
			Object code = resultMap.get("error_code");
			if(Check.NuNObj(code)){
				LogUtil.error(LOGGER, "【获取用户头像】获取用户头像错误code={}，请求url={}，返回结果result={}",code,url.toString(),getResult);
				dto.putValue("nickname", "");
				dto.putValue("headimgurl", "");
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
			if(String.valueOf(code).equals("10001")){
				LogUtil.error(LOGGER, "【获取用户头像】获取用户头像错误code={}，请求url={}，返回结果result={}",code,url.toString(),getResult);
				dto.putValue("nickname", "");
				dto.putValue("headimgurl", "");
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
			Map<String, String>  dataMap = (Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(resultMap.get("data")));
			dto.putValue("nickname", dataMap.get("nick_name"));
			dto.putValue("headimgurl", dataMap.get("head_img"));
			if(Check.NuNObj(dataMap.get("nick_name"))){
				dto.putValue("nickname", dataMap.get("user_name"));
			}
			//判断是否是民宿房东
			String resultJson=customerInfoService.getCustomerInfoByUid(uid);
			CustomerBaseMsgEntity customerInfo=SOAResParseUtil.getValueFromDataByKey(resultJson, "customerBase", CustomerBaseMsgEntity.class);
			if(!Check.NuNObj(customerInfo)&&customerInfo.getIsLandlord()==IsLandlordEnum.IS_LANDLORD.getCode()){
				if(Check.NuNObj(dto.getData().get("nickname"))){
					if(Check.NuNStr(customerInfo.getNickName())){
						dto.putValue("nickname", customerInfo.getRealName());
					} else {
						dto.putValue("nickname", customerInfo.getNickName());
					}
				}
				//获取房东头像
				if(Check.NuNObj(dto.getData().get("headimgurl"))){
			        CustomerPicDto customerPicDto = new CustomerPicDto();
			        customerPicDto.setUid(uid);
			        customerPicDto.setPicType(CustomerPicTypeEnum.YHTX.getCode());
			        String customerPicJson=customerMsgManagerService.getCustomerPicByType(JsonEntityTransform.Object2Json(customerPicDto));
			        LogUtil.info(LOGGER, "查询头像结果：{}",customerPicJson);
			        CustomerPicMsgEntity customerPicMsgEntity=SOAResParseUtil.getValueFromDataByKey(customerPicJson, "customerPicMsgEntity", CustomerPicMsgEntity.class);
			        if(!Check.NuNObj(customerPicMsgEntity)){
			        	String headPicUrl = PicUtil.getFullPic(picBaseAddrMona, customerPicMsgEntity.getPicBaseUrl(), customerPicMsgEntity.getPicSuffix(), detail_big_pic);
			        	dto.putValue("headimgurl", headPicUrl);
			        }
				}
			} 
			response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
		} catch (BusinessException e) {
			LogUtil.info(LOGGER, "getNicknameAndHeadpicByUid方法错误", e);
		}
	}
}
