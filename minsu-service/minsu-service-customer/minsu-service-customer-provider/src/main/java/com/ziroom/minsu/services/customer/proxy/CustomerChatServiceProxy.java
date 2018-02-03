/**
 * @FileName: CustomerChatServiceProxy.java
 * @Package com.ziroom.minsu.services.customer.proxy
 * 
 * @author loushuai
 * @created 2017年9月18日 下午6:27:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerChatService;
import com.ziroom.minsu.services.customer.dto.CustomerChatRequest;
import com.ziroom.minsu.services.customer.dto.CustomerInfoVo;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.customer.service.CustomerBaseMsgServiceImpl;
import com.ziroom.minsu.services.customer.service.CustomerPicMsgServiceImpl;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.PicTypeEnum;

/**
 * <p>聊天用户业务</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
@Component("customer.customerChatServiceProxy")
public class CustomerChatServiceProxy implements CustomerChatService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerChatService.class);
	
	@Resource(name="customer.customerPicMsgServiceImpl")
	private CustomerPicMsgServiceImpl customerPicMsgServiceImpl;
	
	@Resource(name="customer.customerBaseMsgServiceImpl")
	private CustomerBaseMsgServiceImpl customerBaseMsgServiceImpl;
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${default_head_size}'.trim()}")
	private String picSize;
	
    @Value("#{'${FOR_CUSTOMER_DETAIL_URL}'.trim()}")
    private String FOR_CUSTOMER_DETAIL_URL;
    
    @Value("#{'${AUTH_CODE}'.trim()}")
    private String AUTH_CODE;
    
    @Value("#{'${AUTH_SECRET_KEY}'.trim()}")
    private String AUTH_SECRET_KEY;
    
    @Value("#{'${BATCH_SELECT_LIMIT}'.trim()}")
    private String BATCH_SELECT_LIMIT;
    
	/**
	 * 
	 * 获取用户（集合）头像和昵称
	 *
	 * @author loushuai
	 * @created 2017年9月18日 下午6:26:51
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String getListUserPicAndNickName(String paramJson) {
		long start = System.currentTimeMillis();
		LogUtil.info(LOGGER, "getListUserPicAndNickName paramJson={}", paramJson);
		DataTransferObject dto  = new DataTransferObject();

		CustomerChatRequest customerChatRequest = JsonEntityTransform.json2Object(paramJson, CustomerChatRequest.class);
		List<String> uidList = customerChatRequest.getUidList();
		if(Check.NuNObj(customerChatRequest) || Check.NuNCollection(uidList)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
       /* List<CustomerVo> customerVoList = new ArrayList<CustomerVo>();
        List<String> notLandlordList = new ArrayList<String>();
        int isRequest = 0;
        //第一次循环 == 1，获取所有是民宿房东的uid的头像和昵称，填充到customerVoList  2，将非房东的uid放入notLandlordList中
		for (String uid : uidList) {
			CustomerVo customerVo = new CustomerVo();
			customerVo.setUid(uid);
			//先查询是否是民宿房东
			CustomerBaseMsgEntity customerBaseMsg = customerBaseMsgServiceImpl.getCustomerBaseMsgEntitybyUid(uid);
			//此uid是房东
			if(!Check.NuNObjs(customerBaseMsg) && !Check.NuNObj(customerBaseMsg.getIsLandlord()) && customerBaseMsg.getIsLandlord()==YesOrNoEnum.YES.getCode()){
				customerVo.setNickName(customerBaseMsg.getNickName());
				CustomerPicMsgEntity customerPicMsg = this.customerPicMsgServiceImpl.getCustomerPicByType(uid, PicTypeEnum.USER_PHOTO.getCode());
				if(!Check.NuNObj(customerPicMsg)&&!Check.NuNStr(customerPicMsg.getPicBaseUrl())){
						customerVo.setUserPicUrl(customerPicMsg.getPicBaseUrl());
						if(!customerPicMsg.getPicBaseUrl().contains("http")){
	                        String fullPic = PicUtil.getFullPic(picBaseAddrMona, customerPicMsg.getPicBaseUrl(), customerPicMsg.getPicSuffix(), picSize);
							customerVo.setUserPicUrl(fullPic);
						}

				}
				customerVoList.add(customerVo);
			}else{
				notLandlordList.add(uid);				
			}
			
		}
		*/
		//将非房东的uid集合，调用友家批量查询用户头像和昵称的接口————{baseurl}/account/profile/batch-select.json，填充到customerVoList中
		    List<CustomerVo> customerVoList = new ArrayList<CustomerVo>();
		    StringBuffer uids = new StringBuffer();
			int i = 0;
			int isRequest=0;
			int limit = 20;
			if(!Check.NuNStr(BATCH_SELECT_LIMIT)){
				limit = Integer.valueOf(BATCH_SELECT_LIMIT);
			}
			if(uidList.size()<=limit){
				for (String uid : uidList) {
					i++;
					uids.append(uid);
					if(i < uidList.size()){
						uids.append(",");
					}
				}
				queryAccountProfile(uids, customerVoList);
			}else{
				for (String uid : uidList) {
					isRequest++;
					uids.append(uid);
					if(isRequest%limit == 0 ){
						queryAccountProfile(uids, customerVoList);
						uids = new StringBuffer();
						continue;
					}else if(isRequest==uidList.size()){
						queryAccountProfile(uids, customerVoList);
						uids = new StringBuffer();
					}else{
						uids.append(",");
					}
				}
			}
		
		dto.putValue("list", customerVoList);
		long end = System.currentTimeMillis();
		LogUtil.info(LOGGER, "getListUserPicAndNickName 耗时", (end-start));
		dto.putValue("批量调用耗时", start-end);
		return dto.toJsonString();
	}

	/**
     * 从第三方接口获取自如客信息
     *
     * @param String Map<String, Object>
     * @return Map<String, Object>
     * @author wangwentao
     * @created 2017/4/28 11:16
     */
    private void queryAccountProfile(StringBuffer uids, List<CustomerVo> customerVoList) {
    	LogUtil.info(LOGGER, "queryAccountProfile  uids={}", uids);
        try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("uid", uids.toString());
			param.put("hide", "0");
			param.put("auth_code", AUTH_CODE);
			param.put("auth_secret_key", AUTH_SECRET_KEY);
			LogUtil.info(LOGGER, "queryAccountProfile  开始调用第三方[批量查询个人信息] FOR_CUSTOMER_DETAIL_URL={}  param={}", FOR_CUSTOMER_DETAIL_URL.toString(),JsonEntityTransform.Object2Json(param));
			String result  = CloseableHttpUtil.sendFormPost(FOR_CUSTOMER_DETAIL_URL, param);
            LogUtil.info(LOGGER, "queryAccountProfile  开始调用第三方[批量查询个人信息] result={}", result);
            if(!Check.NuNStr(result)){
				JSONObject resultObj = JSONObject.parseObject(result);
				int errorCode  = resultObj.getIntValue("error_code");
				if(errorCode == DataTransferObject.SUCCESS){
					JSONArray  dataArray = resultObj.getJSONArray("data");
					if(!dataArray.isEmpty()){
						for(int j=0;j<dataArray.size();j++){
							JSONObject obj = dataArray.getJSONObject(j);
							CustomerVo customerVo = new CustomerVo();
							if(!Check.NuNObj(obj.getString("uid"))){
								customerVo.setUid(obj.getString("uid"));
								customerVo.setNickName(obj.getString("nick_name"));
								customerVo.setUserPicUrl(obj.getString("head_img"));
								customerVoList.add(customerVo);
							}
						}
					}
				}
			}
        } catch (Exception e) {
        	  LogUtil.info(LOGGER, "调用第三方[查询个人信息接口]异常url={},e", e);
        	  return;
        }
    }
    
    
    
    

}
