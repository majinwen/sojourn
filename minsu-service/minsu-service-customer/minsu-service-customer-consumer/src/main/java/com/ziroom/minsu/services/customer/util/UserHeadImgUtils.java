/**
 * @FileName: UserHeadImgUtils.java
 * @Package com.ziroom.minsu.services.customer.util
 * 
 * @author yd
 * @created 2017年7月4日 上午11:54:38
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.customer.dto.CustomerInfoDto;
import com.ziroom.minsu.valenum.customer.PicTypeEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * <p>用户头像 工具</p>
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
public class UserHeadImgUtils {


	private static Logger logger = LoggerFactory.getLogger(UserHeadImgUtils.class);
	/**
	 * 
	 * 去自如获取用户的头像信息
	 *
	 * @author yd
	 * @created 2017年7月4日 上午11:56:56
	 *
	 * @param uid
	 * @param url
	 * @return
	 */
	public static CustomerPicMsgEntity getHeadImgFromZiroom(String uid,String customerDetailUrl){


		CustomerPicMsgEntity customerPicMsg = null;
		if(!Check.NuNStr(uid)&&!Check.NuNStr(customerDetailUrl)){
			Map<String, String> paramMap = new HashMap<String, String>();
			StringBuffer url = new StringBuffer();
			url.append(customerDetailUrl).append(uid);
			try {
				String getResult = CloseableHttpUtil.sendGet(url.toString(), null);
				if(Check.NuNStrStrict(getResult)){
					LogUtil.error(logger, "CUSTOMER_ERROR:根据用户uid={},获取用户头像失败", uid);
					return customerPicMsg;
				}
				try {
					paramMap = (Map<String, String>) JsonEntityTransform.json2Map(getResult);
				} catch (Exception e) {
					LogUtil.info(logger, "用户信息转化错误，请求url={}，返回结果result={}，e={}",url.toString(),getResult,e);
					return customerPicMsg;
				}
				Object code = paramMap.get("error_code");
				if(Check.NuNObj(code)){
					LogUtil.error(logger, "【获取用户头像】获取用户头像错误code={}，请求url={}，返回结果result={}",code,url.toString(),getResult);
					return customerPicMsg;
				}
				Integer errorCode = (Integer)code;
				if(!Check.NuNMap(paramMap)&&DataTransferObject.SUCCESS == errorCode){
					//封装用户基本信息
					if(!Check.NuNObj(paramMap.get("data"))){
						paramMap = (Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(paramMap.get("data")));
						if(!Check.NuNMap(paramMap)){
							//保存用户照片 可能还有其他照片信息  身份证
							if(!Check.NuNStr(paramMap.get("head_img"))){
								customerPicMsg = initCustomerPicMsg(uid);
								customerPicMsg.setPicBaseUrl(paramMap.get("head_img"));
								LogUtil.info(logger, "【获取用户头像成功】uid={},headImgUrl={}", uid,paramMap.get("head_img"));
							}
						}
					}
				}
			} catch (Exception e) {
				LogUtil.error(logger, "【获取用户头像失败】,e={}", e);
			}
		}
		return customerPicMsg;
	}

	/**
	 * 
	 * 初始化头像信息
	 *
	 * @author yd
	 * @created 2016年11月2日 下午1:54:50
	 *
	 * @param uid
	 * @return
	 */
	private static CustomerPicMsgEntity initCustomerPicMsg(String uid){
		CustomerPicMsgEntity customerPicMsg = new CustomerPicMsgEntity();
		customerPicMsg.setFid(UUIDGenerator.hexUUID());
		customerPicMsg.setUid(uid); 
		customerPicMsg.setPicType(PicTypeEnum.USER_PHOTO.getCode());
		customerPicMsg.setIsDel(IsDelEnum.NOT_DEL.getCode());
		return customerPicMsg;
	}
}
