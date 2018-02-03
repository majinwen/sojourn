/**
 * @FileName: NoticeLanDfbHouseMsgJob.java
 * @Package com.ziroom.minsu.services.job.house
 * 
 * @author yd
 * @created 2016年11月22日 下午7:15:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.job.house;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.JpushRequest;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.api.inner.ShortChainMapService;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.house.api.inner.HouseJobService;
import com.ziroom.minsu.services.house.dto.HouseDfbNoticeDto;
import com.ziroom.minsu.valenum.customer.JpushPersonType;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

/**
 * <p> 
 * 待发布 房源 发送提醒短信
 * 第3天  第7天 第14天 发送短信
 * 频次：未发布3天、未发布7天、未发布14天。
 * 
 * 算法:
 * 1.定时任务 每天晚上8点执行
 * 2.查询当前时间往前推14天 7天  3天的的房源（待发布状态）
 *   A. 当前时间往前推3天到4天的房源 包括第3天 和第4天
 *   B. 当前时间 往前推7天到8天的房源 包括第7天 和第8天
 *   C. 当前时间 往前推14天到15天的房源 包括第7天 和第8天

 * 3. 查询3批房源 不在一起 分批发送短信</p>
 * 
 *   描述时间: 0 0 20 * * ?
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
public class NoticeLanDfbHouseMsgJob extends AsuraJob{

	private static final Logger LOGGER = LoggerFactory.getLogger(NoticeLanDfbHouseMsgJob.class);

	public final static String THREE_DAY = "3";

	public final static String SEVEN_DAY = "7";

	public final static String FOURTEENTH_DAY = "14";

	//短信文案 固定，不在去数据库查询
	public final static String content ="温馨提示：您有x个待发布房源。如房源资料已上传完毕，请您点击“房源发布”按钮。发布后我们的平台运营专员将联系您进行后续支持服务。如您希望终止发布房源，请点击“取消发布”按钮。感谢支持自如民宿！";
	/* (non-Javadoc)
	 * @see com.asura.framework.quartz.job.AsuraJob#run(org.quartz.JobExecutionContext)
	 */
	@Override
	public void run(JobExecutionContext context) {

		Long t1 = System.currentTimeMillis();
		HouseJobService houseJobService =  (HouseJobService)ApplicationContext.getContext().getBean("job.houseJobService");
		SmsTemplateService smsTemplateService =  (SmsTemplateService)ApplicationContext.getContext().getBean("job.smsTemplateService");
		CustomerInfoService customerInfoService =  (CustomerInfoService)ApplicationContext.getContext().getBean("job.customerInfoService");
		String curTime = DateUtil.dateFormat(new Date(), "yyyy-MM-dd 20:00:00");
		//每天晚上8点 通知 待发布房源的房东
		findLanDFBHouseMsg(curTime, this.THREE_DAY, houseJobService,smsTemplateService,customerInfoService);
		findLanDFBHouseMsg(curTime, this.SEVEN_DAY, houseJobService,smsTemplateService,customerInfoService);
		findLanDFBHouseMsg(curTime, this.FOURTEENTH_DAY, houseJobService,smsTemplateService,customerInfoService);
		Long t2 = System.currentTimeMillis();
		LogUtil.info(LOGGER, "待发布房源通知房东结束，用时t2-t1={}",t2-t1);

		try {
			String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE.getCode());
			SmsRequest smsRequest  = new SmsRequest();
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("{1}", "待发布状态房源通知房东");
			paramsMap.put("{2}", "耗时"+(t2-t1)+"ms");
			smsRequest.setParamsMap(paramsMap);
			smsRequest.setMobile(ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mobileList.getType(),EnumMinsuConfig.minsu_mobileList.getCode()));
			smsRequest.setSmsCode(msgCode);
			smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
		}catch (Exception e){
			LogUtil.error(LOGGER,"定时任务发送短信失败：e：{}",e);
		}

	}


	/**
	 * 
	 * 查询当前符合条件的房源
	 *
	 * @author yd
	 * @created 2016年11月22日 下午8:45:54
	 *
	 * @param curTime
	 * @param day
	 * @param houseJobService
	 */
	private void findLanDFBHouseMsg(String curTime,String day,HouseJobService houseJobService,SmsTemplateService smsTemplateService ,CustomerInfoService customerInfoService){

		LogUtil.info(LOGGER, "待发布房源通知房东开始,curTime={},day={}", curTime,day);
		Long t1 = System.currentTimeMillis();
		if(!Check.NuNObj(houseJobService)){
			//每天晚上8点 通知 待发布房源的房东
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseJobService.noticeLanDFBHouseMsg(curTime,day));
			if(dto.getCode() == DataTransferObject.SUCCESS){
				List<HouseDfbNoticeDto> listDfbNotice = dto.parseData("listDfbNotice", new TypeReference<List<HouseDfbNoticeDto>>() {
				});
				sendMsg(listDfbNotice,smsTemplateService,customerInfoService);
			}

		}
		Long t2 = System.currentTimeMillis();
		LogUtil.info(LOGGER, "待发布房源通知房东结束，用时t2-t1={}",t2-t1);
	}

	/**
	 * 
	 * 发送短信以及极光推送
	 *
	 * @author yd
	 * @created 2016年11月22日 下午8:18:19
	 *
	 * @param listDfbNotice
	 */
	private void sendMsg(List<HouseDfbNoticeDto> listDfbNotice,SmsTemplateService smsTemplateService ,CustomerInfoService customerInfoService){
		ResourceBundle resource = ResourceBundle.getBundle("job");
		ShortChainMapService shortChainMapService =  (ShortChainMapService)ApplicationContext.getContext().getBean("job.shortChainMapService");
		
		if(!Check.NuNCollection(listDfbNotice)){

			Map<String, String> mobileMap = new HashMap<String, String>();
			List<String> lanlordUids = new ArrayList<String>();
			for (HouseDfbNoticeDto houseDfbNoticeDto : listDfbNotice) {
				if(!Check.NuNStrStrict(houseDfbNoticeDto.getLandlordUid())){
					lanlordUids.add(houseDfbNoticeDto.getLandlordUid());
				}
			}

			//查询房东电话
			if(!Check.NuNCollection(lanlordUids)){

				if(Check.NuNObj(customerInfoService)){
					LogUtil.error(LOGGER, "获取用户服务失败customerInfoService={}", customerInfoService);
					return ;
				}
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerInfoService.getCustomerListByUidList(JsonEntityTransform.Object2Json(lanlordUids)));

				if(dto.getCode() == DataTransferObject.SUCCESS){
					List<CustomerBaseMsgEntity> customerList  = dto.parseData("customerList", new TypeReference<List<CustomerBaseMsgEntity>>() {
					});
					if(!Check.NuNCollection(customerList)){
						for (CustomerBaseMsgEntity customerBaseMsgEntity : customerList) {
							if(Check.NuNStrStrict(customerBaseMsgEntity.getCustomerMobile())){
								continue;
							}
							mobileMap.put(customerBaseMsgEntity.getUid(), customerBaseMsgEntity.getCustomerMobile());
						}
					}
				}
			}

			if(Check.NuNObj(smsTemplateService)){
				LogUtil.error(LOGGER, "获取消息服务失败smsTemplateService={}", smsTemplateService);
				return ;
			}
			if(!Check.NuNMap(mobileMap)){

				//获取短信跳转连接
				String shortLink = null;
				String result = shortChainMapService.getMinsuHomeJump();
				DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
				if(resultDto.getCode()==DataTransferObject.SUCCESS){
				     shortLink = resultDto.parseData("shortLink", new TypeReference<String>() {});
				}
				
				for (HouseDfbNoticeDto houseDfbNoticeDto : listDfbNotice) {
					if(!Check.NuNStrStrict(houseDfbNoticeDto.getLandlordUid())){
						houseDfbNoticeDto.setMobile(mobileMap.get(houseDfbNoticeDto.getLandlordUid()));
						String contentStr = this.content.replace("x", houseDfbNoticeDto.getHouseNum());
						houseDfbNoticeDto.setContent(contentStr);
						try {
							SmsRequest smsRequest = new SmsRequest();
							Map<String, String> paramsMap = new HashMap<String, String>();
							paramsMap.put("{1}", houseDfbNoticeDto.getHouseNum());
							paramsMap.put("{2}", shortLink);
							smsRequest.setMobile(houseDfbNoticeDto.getMobile());
							smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_DFB_NOTICE_MESSAGE.getCode()));
							smsRequest.setParamsMap(paramsMap);
							smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
						} catch (Exception e) {
							LogUtil.error(LOGGER, "待发布房源短信通知失败houseDfbNoticeDto={}", JsonEntityTransform.Object2Json(houseDfbNoticeDto));
						}

						//极光推送

						try {
							//自定义消息额外参数
							Map<String, String> extrasMap = new HashMap<String, String>();
							extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
							extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_6);
							extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
							extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
							extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));

							JpushRequest jpushRequest = new JpushRequest();
							jpushRequest.setExtrasMap(extrasMap);
							jpushRequest.setContent(contentStr);
							jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
							jpushRequest.setUid(houseDfbNoticeDto.getLandlordUid());
							jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
							jpushRequest.setTitle("您有待发布房源");
							smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));

						} catch (Exception e) {
							LogUtil.error(LOGGER, "待发布房源消息通知失败houseDfbNoticeDto={}", JsonEntityTransform.Object2Json(houseDfbNoticeDto));
						}

					}
				}

			}


		}
	}

}
