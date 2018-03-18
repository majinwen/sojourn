/**
 * @FileName: IntellectServiceProxy.java
 * @Package com.ziroom.zrp.service.trading.proxy
 *
 * @author bushujie
 * @created 2018年1月22日 下午2:45:56
 *
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.trading.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.JpushRequest;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.valenum.customer.JpushPersonType;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt.WattMeterChargingDto;
import com.ziroom.zrp.service.houses.valenum.WattRechargeStatuEnum;
import com.ziroom.zrp.service.trading.api.IntellectWattMeterService;
import com.ziroom.zrp.service.trading.dto.smarthydropower.LowLevelCallBackDto;
import com.ziroom.zrp.service.trading.proxy.commonlogic.IntellectPlatformLogic;
import com.ziroom.zrp.service.trading.proxy.commonlogic.RentContractLogic;
import com.ziroom.zrp.service.trading.service.IntellectWaterMeterBillLogServiceImpl;
import com.ziroom.zrp.service.trading.service.IntellectWattMeterSnapshotImpl;
import com.ziroom.zrp.trading.entity.IntellectWattMeterSnapshotEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import com.zra.common.constant.ContractMsgConstant;
import com.zra.common.dto.base.BasePageParamDto;
import com.zra.common.enums.SmsTemplateCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
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
@Slf4j
@Component("trading.intellectWattMeterServiceProxy")
public class IntellectWattMeterServiceProxy implements IntellectWattMeterService {

	@Resource(name="trading.intellectWaterMeterBillLogServiceImpl")
	private IntellectWaterMeterBillLogServiceImpl intellectWaterMeterBillLogService;

	@Resource(name="trading.intellectWattMeterSnapshotImpl")
	private IntellectWattMeterSnapshotImpl intellectWattMeterSnapshotImpl;

	@Resource(name="trading.intellectPlatformLogic")
	private IntellectPlatformLogic intellectPlatformLogic;

	@Resource(name = "trading.rentContractLogic")
	private RentContractLogic rentContractLogic;

	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

	@Resource(name="houses.projectService")
	private ProjectService projectService;

	/**
	 * 充电回调
	 * @param param
	 * @return
	 */
	@Override
	public String rechargeIntellectWattMeterCallBack(String param) {
		log.info("[rechargeIntellectWattMeterCallBack] param={}",param);
		DataTransferObject dto = new DataTransferObject();
		try {
			JSONObject paramObj = JSONObject.parseObject(param);
			Integer code = paramObj.getInteger("code");

			JSONObject data = paramObj.getJSONObject("data");
			String serviceId = data.getString("serviceId");
			IntellectWattMeterSnapshotEntity wattMeterSnapshotEntity = intellectWattMeterSnapshotImpl.findIntellectWattMeterByServiceId(serviceId);
			if (Check.NuNObj(wattMeterSnapshotEntity)){
				log.error("rechargeIntellectWattMeterCallBack 充值快照为空");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("充值快照为空");
				return dto.toJsonString();
			}
			wattMeterSnapshotEntity.setRemark(paramObj.getString("message"));
			if (code == 0){
				wattMeterSnapshotEntity.setStatu(WattRechargeStatuEnum.RECHARGE_SUCCESS.getCode());
				//Double totalAmount = data.getDouble("totalAmount");
				Double amount = data.getDouble("amount");
				wattMeterSnapshotEntity.setAmount(amount);;
			}else{
				wattMeterSnapshotEntity.setStatu(WattRechargeStatuEnum.RECHARGE_FAILED.getCode());
			}

			intellectWattMeterSnapshotImpl.updateIntellectWattMeterSnapshot(wattMeterSnapshotEntity);
		}catch (Exception e){
			log.error("rechargeIntellectWattMeterCallBack error={}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
		return dto.toJsonString();
	}

	/**
	 *低电量回调
	 * @author xiangb
	 * @created 2018/2/24 14:31
	 * @param param
	 * @return java.lang.String
	 */
	@Override
	public String lowlevelCallBack(String param){
		log.info("[lowlevelCallBack] param={}",param);
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(param)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			LowLevelCallBackDto paramDto = JsonEntityTransform.json2Object(param,LowLevelCallBackDto.class);
			String projectId= paramDto.getData().getDeviceUuid().getPositionRank1();
			String roomId = paramDto.getData().getDeviceUuid().getPositionRank2();

			String pushcode = String.valueOf(SmsTemplateCodeEnum.LOWLEVEL_CALLBACK_REMIND_PUSH_MSG.getCode());
			String smscode = String.valueOf(SmsTemplateCodeEnum.LOWLEVEL_CALLBACK_REMIND_SMS_MSG.getCode());
			List<RentContractEntity> roomValidContractList = this.rentContractLogic.getRoomValidContractList(roomId);
			ProjectEntity projectEntity = SOAResParseUtil.getValueFromDataByKey(projectService.findProjectById(projectId),"projectEntity", ProjectEntity.class);
			if(Check.NuNObj(projectEntity) || Check.NuNStr(projectEntity.getFname())){
				log.error("[lowlevelCallBack]未查询到项目信息的异常：projectId：{}",projectId);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("未查询到项目信息");
				return dto.toJsonString();
			}
			if(!Check.NuNCollection(roomValidContractList)){
				roomValidContractList.forEach(p->{
					SmsRequest smsRequest = new SmsRequest();
					smsRequest.setMobile(p.getCustomerMobile());
					Map<String, String> paramsMap=new HashMap<>();
					paramsMap.put(SysConst.MOBILE_NATION_CODE_KEY, "86");
					paramsMap.put("{1}",projectEntity.getFname());
					paramsMap.put("{2}",p.getHouseRoomNo());
					smsRequest.setParamsMap(paramsMap);
					smsRequest.setSmsCode(smscode);
					log.info("[lowlevelCallBack]开始给用户发送短信,param={}",JSONObject.toJSONString(paramsMap));
					String result = smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
					log.info("[lowlevelCallBack]用户发送自动交割短信返回结果,result={}",result);
					//发送系统消息给用户
					JpushRequest jpushRequest = new JpushRequest();
					jpushRequest.setParamsMap(paramsMap);
					jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
					jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
					jpushRequest.setSmsCode(pushcode);
					jpushRequest.setTitle(ContractMsgConstant.DELIVERY_CUSTOMER_DONE_PUSH_TITLE);
					jpushRequest.setUid(p.getCustomerUid());
					Map<String, String> extrasMap = new HashMap<>();
					extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.ZRP_MSG_BODY_TYPE_VALUE);
					extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_ALL);
					extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
					extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY,JpushConst.MSG_SUB_TYPE_VALULE_4);
					jpushRequest.setExtrasMap(extrasMap);
					log.info("[lowlevelCallBack]开始给用户发送自动交割短信,param={}",JSONObject.toJSONString(jpushRequest));
					String pushResult = smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
					log.info("[lowlevelCallBack]用户发送自动交割推送返回结果,pushResult={}",pushResult);
				});
			}
		}catch (Exception e){
			log.error("[lowlevelCallBack] error={}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
		return dto.toJsonString();
	}

	@Override
	public void retryFailWattMeterJob() {
		log.info("重试失败充值电费定时任务开始");
		BasePageParamDto basePageParamDto = new BasePageParamDto();
		basePageParamDto.setPage(1);
		basePageParamDto.setRows(10);

		try {
			for (;;){
				PagingResult<IntellectWattMeterSnapshotEntity> pagingResult = intellectWattMeterSnapshotImpl.listRetryWattMeterPage(basePageParamDto);
				List<IntellectWattMeterSnapshotEntity> rows = pagingResult.getRows();
				if (Check.NuNCollection(rows)){
					break;
				}

				for (IntellectWattMeterSnapshotEntity snapshotEntity : rows){
					WattMeterChargingDto wattMeterChargingDto = new WattMeterChargingDto();
					wattMeterChargingDto.setHireContractCode(snapshotEntity.getRentCode());
					wattMeterChargingDto.setPositionRank1(snapshotEntity.getProjectId());
					wattMeterChargingDto.setPositionRank2(snapshotEntity.getRoomId());
					wattMeterChargingDto.setTradeNum(snapshotEntity.getBillNum());
					wattMeterChargingDto.setAmount(snapshotEntity.getAmount().floatValue());

					IntellectWattMeterSnapshotEntity wattMeterSnapshotEntity = intellectPlatformLogic.rechargeElectricityBill(snapshotEntity, wattMeterChargingDto);
					intellectWattMeterSnapshotImpl.updateIntellectWattMeterSnapshot(wattMeterSnapshotEntity);

				}
			}
		}catch (Exception e){
			log.error("重试电费定时任务异常 e={}",e);
		}
		log.info("重试失败充值电费定时任务开始 定时任务结束");
	}

	@Override
	public String checkValidContractAndModifyWattPayType(String paramJson) {
		DataTransferObject dto = intellectPlatformLogic.checkValidContractAndModifyWattPayType(paramJson);
		return dto.toJsonString();
	}
}
