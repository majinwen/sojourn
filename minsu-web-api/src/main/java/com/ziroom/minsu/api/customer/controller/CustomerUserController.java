package com.ziroom.minsu.api.customer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.customer.service.CardService;
import com.ziroom.minsu.api.customer.service.CustomerService;
import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.dto.CustomerInfoVo;
import com.ziroom.minsu.services.message.api.inner.HuanxinImManagerService;
import com.ziroom.minsu.services.message.dto.GroupMemberPageInfoDto;
import com.ziroom.minsu.services.message.dto.ImMembersVo;

/**
 * <p>用户信息接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */

@RequestMapping("/customer")
@Controller
public class CustomerUserController {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerUserController.class);


	@Value("#{'${CUSTOMER_SAVE_CARD_URL}'.trim()}")
	private String CUSTOMER_SAVE_CARD_URL;



	@Resource(name = "api.customerService")
	private CustomerService customerService;


	@Resource(name = "api.cardService")
	private CardService cardService;

	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;


	@Value("#{'${ACCOUNT_QUERY_USER_INFO}'.trim()}")
	private String ACCOUNT_QUERY_USER_INFO;

	@Resource(name = "message.huanxinImManagerService")
	private  HuanxinImManagerService  huanxinImManagerService;

	
	@Value("#{'${AUTH_CODE}'.trim()}")
	private String AUTH_CODE;
	
	@Value("#{'${AUTH_SECRET_KEY}'.trim()}")
	private String AUTH_SECRET_KEY;

	/**
	 * 校验银行卡信息
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/checkCard")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> checkCard(HttpServletRequest request){
		try{
			String uid = (String)request.getAttribute("uid");
			if (Check.NuNStr(uid)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"),HttpStatus.OK);
			}
			CustomerBankCardMsgEntity cardMsgEntity = null;

			//获取当前用户的银行卡信息
			String json = customerInfoService.getCustomerBankCardDbByUid(uid);
			DataTransferObject cardDto = JsonEntityTransform.json2DataTransferObject(json);
			if(cardDto.getCode() == DataTransferObject.SUCCESS){
				cardMsgEntity = cardDto.parseData("bankcard", new TypeReference<CustomerBankCardMsgEntity>() {});
			}
			boolean cardFlag = false;
			if (!Check.NuNObj(cardMsgEntity)){
				cardFlag = true;
			}
			if (!cardFlag){
				//数据库中不存在 调用自如网
				Map card = cardService.getUserCardInfo(uid);
				if (!Check.NuNObj(card)){
					cardFlag = true;
				}
			}
			Map<String,Object> baseInfo = new HashMap<>();
			baseInfo.put("cardFlag",cardFlag);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(baseInfo), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(LOGGER, "checkCard is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}

	}

	/**
	 * 分页获取 当前组内的用户头像和昵称 
	 * 1. 根据 组id 分页查询 组内成员uid 
	 * 2. 根据uid  获取用户头像和昵称
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/queryGroupUserInfo")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> queryGroupUserInfo(HttpServletRequest request){

		String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
		LogUtil.info(LOGGER, "参数：" + paramJson);

		DataTransferObject dto = new DataTransferObject();
		GroupMemberPageInfoDto groupMemberPageInfoDto  = JsonEntityTransform.json2Object(paramJson, GroupMemberPageInfoDto.class);

		if(Check.NuNObj(groupMemberPageInfoDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		}

		if(Check.NuNStr(groupMemberPageInfoDto.getGroupId())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("群组参数错误");
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK); 
		}
		groupMemberPageInfoDto.setLimit(40);
		try {
			DataTransferObject dtoJ = JsonEntityTransform.json2DataTransferObject(huanxinImManagerService.queryGroupMemberByPage(JsonEntityTransform.Object2Json(groupMemberPageInfoDto)));

			if(dtoJ.getCode() == DataTransferObject.SUCCESS){
				List<ImMembersVo> uidList  = dtoJ.parseData("uidList", new TypeReference<List<ImMembersVo>>() {
				});

				List<CustomerInfoVo> listUserInfo = new ArrayList<CustomerInfoVo>();
				if(!Check.NuNCollection(uidList)){
					StringBuffer uids = new StringBuffer();
					int i = 0;
					for (ImMembersVo imMembersVo : uidList) {
						i++;
						uids.append(imMembersVo.getMember());
						if(i<uidList.size()){
							uids.append(",");
						}
					}
					Map<String, String> param = new HashMap<String, String>();
					param.put("uid", uids.toString());
					param.put("hide", "0");
					param.put("auth_code",AUTH_CODE);
					param.put("auth_secret_key", AUTH_SECRET_KEY);
					String result  = CloseableHttpUtil.sendFormPost(ACCOUNT_QUERY_USER_INFO, param);
					
					if(!Check.NuNStr(result)){
						JSONObject resultObj = JSONObject.parseObject(result);
						int errorCode  = resultObj.getIntValue("error_code");
						if(errorCode != DataTransferObject.SUCCESS){
							dto.setErrCode(DataTransferObject.ERROR);
							dto.setMsg(resultObj.getString("error_message"));
							return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto), HttpStatus.OK);
						}
						JSONArray  dataArray = resultObj.getJSONArray("data");
						if(!dataArray.isEmpty()){
							for(int j=0;j<dataArray.size();j++){
								JSONObject obj = dataArray.getJSONObject(j);
								CustomerInfoVo customerInfoVo = new CustomerInfoVo();
								customerInfoVo.setUid(obj.getString("uid"));
								customerInfoVo.setNickName(obj.getString("nick_name"));
								customerInfoVo.setHeadImg(obj.getString("head_img"));
								listUserInfo.add(customerInfoVo);
							}
							dto.putValue("listUserInfo", listUserInfo);
						}
					}
				}

			}
		} catch (Exception e) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("查询异常");
		}

		return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
	}

}
