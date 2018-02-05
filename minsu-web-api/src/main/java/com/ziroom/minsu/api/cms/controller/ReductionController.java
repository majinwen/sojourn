/**
 * @FileName: ReductionController.java
 * @Package com.ziroom.minsu.activity.outer.controller
 * 
 * @author lusp
 * @created 2017年6月2日 上午19:07:22
 *
 */
package com.ziroom.minsu.api.cms.controller;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.entity.cms.ActivityRemindLogEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.StaticResourceService;
import com.ziroom.minsu.services.basedata.dto.JpushRequest;
import com.ziroom.minsu.services.basedata.entity.StaticResourceVo;
import com.ziroom.minsu.services.cms.api.inner.ActivityRemindLogService;
import com.ziroom.minsu.services.cms.api.inner.ActivityService;
import com.ziroom.minsu.services.cms.constant.ReductionConst;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.order.api.inner.HouseCommonService;
import com.ziroom.minsu.valenum.base.MgClickEnum;
import com.ziroom.minsu.valenum.customer.JpushPersonType;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/reduction")
@Controller
public class ReductionController {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ReductionController.class);


	private boolean flag = true;

	@Resource(name="cms.activityService")
	private ActivityService activityService;

	@Resource(name="basedata.staticResourceService")
	private StaticResourceService staticResourceService;

	@Resource(name = "order.houseCommonService")
	private HouseCommonService houseCommonService;

	@Resource(name="customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name="cms.activityRemindLogService")
	private ActivityRemindLogService activityRemindLogService;

	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

	@Value("#{'${pic_base_addr}'.trim()}")
	private String pic_base_addr;

	@Value("#{'${reduction.MG_CLICK_SWITCH}'.trim()}")
	private Integer reduction_MG_CLICK_SWITCH;

	@Value("#{'${reduction.MG_CLICK_IMG}'.trim()}")
	private String reduction_MG_CLICK_IMG;

	@Value("#{'${reduction.MG_CLICK_TITLE}'.trim()}")
	private String reduction_MG_CLICK_TITLE;

	@Value("#{'${reduction.MG_CLICK_DESCRIPTION}'.trim()}")
	private String reduction_MG_CLICK_DESCRIPTION;

	@Value("#{'${reduction.MG_CLICK_TARGET}'.trim()}")
	private String reduction_MG_CLICK_TARGET;

	@Value("#{'${reduction.MG_CLICK_APP}'.trim()}")
	private Integer reduction_MG_CLICK_APP;




	/**
	 * @Description: 判断首单立减活动是否开启
	 * @Author:lusp
	 * @Date: 2017/6/13 18:36
	 * @Params: request,response
	 */
	@RequestMapping("/${NO_LGIN_AUTH}/whetherOpen")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> whetherOpen(HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		obj.put("whetherOpen",0);

		String resultJson = activityService.getSDLJActivityInfo();
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		//判断调用状态
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(LOGGER, "获取首单立减活动详情失败",dto.getMsg());
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(ReductionConst.REDUCTION_SYS_ERROR.getName()),
					HttpStatus.OK);
		}
		ActivityEntity activityEntity = null;
		try {
			activityEntity = SOAResParseUtil.getValueFromDataByKey(resultJson, "activity", ActivityEntity.class);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "SOAResParseUtil 转换异常",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(ReductionConst.REDUCTION_SYS_ERROR.getName()),
					HttpStatus.OK);
		}
		if(Check.NuNObj(activityEntity)){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);
		}
		if(Check.NuNObj(activityEntity.getActStatus())){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);
		}
		if(!activityEntity.getActStatus().equals(2)){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);
		}
		obj.put("whetherOpen",1);

		return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);

	}






	/**
	 *
	 * 返回首单立减活动详情
	 * 1.未登录和已登录符合首单立减条件时，返回 "首单立减活动信息和即刻启程"
	 * 2.已登录但是为老用户时，返回 "首单立减活动信息和朕知道了"
	 * 说明：
	 * 1.未登录时（根据参数中是否有uid 判断）
	 * 		① 校验活动是否开启
	 * 		② 若未开启，直接返回活动已关闭信息
	 * 		③ 若开启，返回"活动固定信息和提示登录信息"
	 * 2.已登录时（根据参数中含有uid 判断）
	 * 入参　
	 * 用户uid: uid（登录时必传）
	 * 		①. 校验活动是否开启
	 * 		②. 若未开启，直接返回活动已关闭信息
	 * 		③. 若活动开启，校验参数
	 * 		④. 校验当前用户是否满足活动条件
	 * 		⑤. 若满足，返回首单立减活动详细信息
	 * 		⑥. 保存记录到 t_activity_remind_log 表，方便以后通知该用户尽快参加活动
	 * 		⑦. 异步发送系统通知
	 *
	 * @author lusp
	 * @created 2017年6月3日 上午9:28:26
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/${NO_LGIN_AUTH}/firstSingleReduction")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> firstSingleReductionUnLgin(HttpServletRequest request) {
		JSONObject obj = new JSONObject();

		/**
		 * 动画点击弹窗控制
		 * yanb
		 */
		obj.put("mgClickSwitch", reduction_MG_CLICK_SWITCH);
		if (!Check.NuNObj(reduction_MG_CLICK_SWITCH) && !MgClickEnum.MG_CLICK_OFF.getCode().equals(reduction_MG_CLICK_SWITCH)) {
			Map lunbo = new HashMap(5);
			lunbo.put("img", reduction_MG_CLICK_IMG);
			lunbo.put("title", reduction_MG_CLICK_TITLE);
			lunbo.put("description", reduction_MG_CLICK_DESCRIPTION);
			lunbo.put("target", reduction_MG_CLICK_TARGET);
			lunbo.put("app", reduction_MG_CLICK_APP);
			obj.put("lunbo", lunbo);
		}
		LogUtil.info(LOGGER, "【firstSingleReductionUnLgin】弹窗返回obj={}",obj.toJSONString());
		obj.put("whetherOpen",0);

		String resultJson = activityService.getSDLJActivityInfo();
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		//判断调用状态
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(LOGGER, "获取首单立减活动详情失败",dto.getMsg());
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(ReductionConst.REDUCTION_SYS_ERROR.getName()),
					HttpStatus.OK);
		}
		ActivityEntity activityEntity = null;
		try {
			activityEntity = SOAResParseUtil.getValueFromDataByKey(resultJson, "activity", ActivityEntity.class);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "SOAResParseUtil 转换异常",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(ReductionConst.REDUCTION_SYS_ERROR.getName()),
					HttpStatus.OK);
		}
		if(Check.NuNObj(activityEntity)){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);
		}
		if(Check.NuNObj(activityEntity.getActStatus())){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);
		}
		if(!activityEntity.getActStatus().equals(2)){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);
		}
		obj.put("whetherOpen",1);

		//***************************用户未登录***************************//
		try {
			//查询第一个提醒
			String hintJson=staticResourceService.findStaticResListByResCode("FIRST_SINGLE_REDUCTION_PIC_HINT");
			List<StaticResourceVo> resList= SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
			if(!Check.NuNCollection(resList)){
				obj.put("picHint", PicUtil.getOrgPic(pic_base_addr, resList.get(resList.size()-1).getPicUrl()));
			}
			//查询第二个提醒
			hintJson=staticResourceService.findStaticResListByResCode("FIRST_SINGLE_REDUCTION_CONTENT_HINT");
			resList=SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
			if(!Check.NuNCollection(resList)){
				obj.put("contentHint", resList.get(resList.size()-1).getResContent());
			}
			//查询第三个提醒（按钮提示文字）
			hintJson=staticResourceService.findStaticResListByResCode("FIRST_SINGLE_REDUCTION_BUTTON_HINT_ONE");
			resList=SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
			if(!Check.NuNCollection(resList)){
				obj.put("buttonHint", resList.get(resList.size()-1).getResContent());
			}

			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "给未登录用户返回首单立减信息出错，firstSingleReduction()",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(ReductionConst.REDUCTION_SYS_ERROR.getName()),
					HttpStatus.OK);
		}

	}


	@RequestMapping("/${LOGIN_UNAUTH}/firstSingleReduction")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> firstSingleReductionLgin(HttpServletRequest request) {
		JSONObject obj = new JSONObject();

		/**
		 * 动画点击弹窗控制
		 * yanb
		 */
		obj.put("mgClickSwitch", reduction_MG_CLICK_SWITCH);
		if (!Check.NuNObj(reduction_MG_CLICK_SWITCH) && !MgClickEnum.MG_CLICK_OFF.getCode().equals(reduction_MG_CLICK_SWITCH)) {
			Map lunbo = new HashMap(5);
			lunbo.put("img", reduction_MG_CLICK_IMG);
			lunbo.put("title", reduction_MG_CLICK_TITLE);
			lunbo.put("description", reduction_MG_CLICK_DESCRIPTION);
			lunbo.put("target", reduction_MG_CLICK_TARGET);
			lunbo.put("app", reduction_MG_CLICK_APP);
			obj.put("lunbo", lunbo);
		}
		LogUtil.info(LOGGER, "【firstSingleReductionLgin】弹窗返回obj={}",obj.toJSONString());
		obj.put("whetherOpen",0);
		obj.put("isNew",0);
		String uid = request.getParameter("uid");

		String resultJson = activityService.getSDLJActivityInfo();
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		//判断调用状态
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(LOGGER, "获取首单立减活动详情失败",dto.getMsg());
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(ReductionConst.REDUCTION_SYS_ERROR.getName()),
					HttpStatus.OK);
		}
		ActivityEntity activityEntity = null;
		try {
			activityEntity = SOAResParseUtil.getValueFromDataByKey(resultJson, "activity", ActivityEntity.class);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "SOAResParseUtil 转换异常",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(ReductionConst.REDUCTION_SYS_ERROR.getName()),
					HttpStatus.OK);
		}
		if(Check.NuNObj(activityEntity)){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);
		}
		if(Check.NuNObj(activityEntity.getActStatus())){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);
		}
		if(!activityEntity.getActStatus().equals(2)){
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);
		}
		obj.put("whetherOpen",1);

		//***************************用户未登录***************************//
		try {
			if(Check.NuNStr(uid)){
				obj.put("isNew",2);
				//查询第一个提醒
				String hintJson=staticResourceService.findStaticResListByResCode("FIRST_SINGLE_REDUCTION_PIC_HINT");
				List<StaticResourceVo> resList= SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
				if(!Check.NuNCollection(resList)){
					obj.put("picHint", PicUtil.getOrgPic(pic_base_addr, resList.get(resList.size()-1).getPicUrl()));
				}
				//查询第二个提醒
				hintJson=staticResourceService.findStaticResListByResCode("FIRST_SINGLE_REDUCTION_CONTENT_HINT");
				resList=SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
				if(!Check.NuNCollection(resList)){
					obj.put("contentHint", resList.get(resList.size()-1).getResContent());
				}
				//查询第三个提醒（按钮提示文字）
				hintJson=staticResourceService.findStaticResListByResCode("FIRST_SINGLE_REDUCTION_BUTTON_HINT_ONE");
				resList=SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
				if(!Check.NuNCollection(resList)){
					obj.put("buttonHint", resList.get(resList.size()-1).getResContent());
				}

				return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "给未登录用户返回首单立减信息出错，firstSingleReduction()",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(ReductionConst.REDUCTION_SYS_ERROR.getName()),
					HttpStatus.OK);
		}

		//***************************用户已登录***************************//

		try {
			//判断该用户是否满足首单立减条件
			String result = houseCommonService.isNewUserByOrderForFirstPage(uid);
			DataTransferObject dtoTemp2 = JsonEntityTransform.json2DataTransferObject(result);
			//判断调用状态
			if(dtoTemp2.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "获取该用户是否满足首单立减信息失败,uid={}",uid,dto.getMsg());
				return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(ReductionConst.REDUCTION_SYS_ERROR.getName()),
						HttpStatus.OK);
			}
			Integer intFlag = SOAResParseUtil.getIntFromDataByKey(result, "isNewUser");
			if (!intFlag.equals(0)) {
				//此时为老用户
				//查询第一个提醒
				String hintJson=staticResourceService.findStaticResListByResCode("FIRST_SINGLE_REDUCTION_PIC_HINT");
				List<StaticResourceVo> resList= SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
				if(!Check.NuNCollection(resList)){
					obj.put("picHint", PicUtil.getOrgPic(pic_base_addr, resList.get(resList.size()-1).getPicUrl()));
				}
				//查询第二个提醒
				hintJson=staticResourceService.findStaticResListByResCode("FIRST_SINGLE_REDUCTION_CONTENT_HINT");
				resList=SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
				if(!Check.NuNCollection(resList)){
					obj.put("contentHint", resList.get(resList.size()-1).getResContent());
				}
				//查询第三个提醒（按钮提示文字）
				hintJson=staticResourceService.findStaticResListByResCode("FIRST_SINGLE_REDUCTION_BUTTON_HINT_TWO");
				resList=SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
				if(!Check.NuNCollection(resList)){
					obj.put("buttonHint", resList.get(resList.size()-1).getResContent());
				}
				return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);

			}
			obj.put("isNew",1);
			//查询第一个提醒
			String hintJson=staticResourceService.findStaticResListByResCode("FIRST_SINGLE_REDUCTION_PIC_HINT");
			List<StaticResourceVo> resList= SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
			if(!Check.NuNCollection(resList)){
				obj.put("picHint", PicUtil.getOrgPic(pic_base_addr, resList.get(resList.size()-1).getPicUrl()));
			}
			//查询第二个提醒
			hintJson=staticResourceService.findStaticResListByResCode("FIRST_SINGLE_REDUCTION_CONTENT_HINT");
			resList=SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
			if(!Check.NuNCollection(resList)){
				obj.put("contentHint", resList.get(resList.size()-1).getResContent());
			}
			//查询第三个提醒（按钮提示文字）
			hintJson=staticResourceService.findStaticResListByResCode("FIRST_SINGLE_REDUCTION_BUTTON_HINT_ONE");
			resList=SOAResParseUtil.getListValueFromDataByKey(hintJson, "staticResList", StaticResourceVo.class);
			if(!Check.NuNCollection(resList)){
				obj.put("buttonHint", resList.get(resList.size()-1).getResContent());
			}

			//异步保存记录到 t_activity_remind_log 表，方便以后通知该用户尽快参加活动
			//异步发送通知消息给用
			String clientType = request.getHeader("client-type");
			String actSn = activityEntity.getActSn();
			this.saveCustomerInfoAndPush(uid,actSn,clientType);

			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(obj), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "给已登录用户返回首单立减信息出错,uid={},e={}",uid,e);
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(ReductionConst.REDUCTION_SYS_ERROR.getName()),
					HttpStatus.OK);
		}
	}


	/**
	 * @Description: 异步发送通知消息给用户
	 * @Author:lusp
	 * @Date: 2017/6/5 17:30
	 * @Params: uid
	 */
	private void saveCustomerInfoAndPush(final String uid,final String actSn,final String clientType){
		Thread task = new Thread(){
			@Override
			public void run() {

				//保存记录到 t_activity_remind_log 表，方便以后通知该用户尽快参加活动
				int result = saveCustomerInfo(uid,actSn,clientType);

				if(result != 0){
                    //发送首单立减活动信息消息给用户
                    push(uid);
                }
			}
		};
		SendThreadPool.execute(task);
	}


	/**
	 * @Description: 发送首单立减活动信息消息给用户
	 * @Author:lusp
	 * @Date: 2017/6/6 14:53
	 * @Params: uid
	 */
	private void push(String uid){

		Map<String, String> jpushMap=new HashMap<>();
		JpushRequest jpushRequest = new JpushRequest();
		jpushRequest.setParamsMap(jpushMap);
		jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
		jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
		jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.CMS_FIRST_SINGLE_REDUCTION_NOTICE_MSG.getCode()));
		jpushRequest.setTitle("首单立减活动通知");
		jpushRequest.setUid(uid);
		//自定义消息
		Map<String, String> extrasMap = new HashMap<>();
		extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
		extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_4);
		extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"0");
		extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
		extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
		jpushRequest.setExtrasMap(extrasMap);
		try {
			smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
		} catch (BusinessException e) {
			LogUtil.error(LOGGER,"首单立减活动异步发送系统通知出现异常, uid={},e={}",uid,e);
		}

	}

	/**
	 * @Description: 保存记录到 t_activity_remind_log 表，方便以后通知该用户尽快参加活动
	 * @Author:lusp
	 * @Date: 2017/6/6 14:53
	 * @Params: uid,actSn,clientType
	 */
	private int saveCustomerInfo(String uid,String actSn,String clientType){
		String mobileNo = null;
		int upNum = 0;
		String customerResultJson = customerMsgManagerService.getCutomerVo(uid);
		DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerResultJson);
		if(customerDto.getCode()==DataTransferObject.ERROR){
			LogUtil.error(LOGGER,"调用customerMsgManagerService.getCutomerVo(uid)失败, uid={}",uid);
		}else{
			CustomerVo customerVo = null;
			try {
				customerVo = SOAResParseUtil.getValueFromDataByKey(customerResultJson,"customerVo",CustomerVo.class);
			} catch (SOAParseException e) {
				LogUtil.error(LOGGER, "SOAResParseUtil 转换异常",e);
			}
			if(!Check.NuNObj(customerVo)){
				mobileNo = customerVo.getShowMobile();
			}
		}
		if(Check.NuNStr(actSn)){
			LogUtil.error(LOGGER,"首单立减活动Sn为空,ReductionController.saveCustomerInfo(uid,actSn,clientType)");
			return upNum;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, +1);
		Date runTime = c.getTime();
		ActivityRemindLogEntity activityRemindLogEntity = new ActivityRemindLogEntity();
		activityRemindLogEntity.setUid(uid);
		activityRemindLogEntity.setActSn(actSn);
		if(!Check.NuNStr(mobileNo)){
			activityRemindLogEntity.setMobileNo(mobileNo);
		}
		activityRemindLogEntity.setRunTime(runTime);
		activityRemindLogEntity.setSendTimes(0);
		if(!Check.NuNStr(clientType)){
			activityRemindLogEntity.setSource(Integer.valueOf(clientType));
		}

		String paramJson = JsonEntityTransform.Object2Json(activityRemindLogEntity);
		String resultJson = activityRemindLogService.insertActivityRemindLogIgnore(paramJson);
		DataTransferObject dtoSave = JsonEntityTransform.json2DataTransferObject(resultJson);

		if(dtoSave.getCode() == DataTransferObject.ERROR){
			LogUtil.error(LOGGER,"调用activityRemindLogService.insertActivityRemindLogIgnore(paramJson)方法失败, paramJson={}",paramJson);
			return upNum;
		}
        upNum = SOAResParseUtil.getIntFromDataByKey(resultJson,"upNum");
		return upNum;

	}

}
