package com.ziroom.minsu.services.job.house;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseStatsDayMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.services.common.vo.HouseStatsVo;
import com.ziroom.minsu.services.house.api.inner.HouseJobService;
import com.ziroom.minsu.services.message.api.inner.MsgHouseService;
import com.ziroom.minsu.services.order.api.inner.OrderPayService;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

/**
 * 
 * <p>房源统计定时任务</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseStatsMsgJob extends AsuraJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseStatsMsgJob.class);

	/**
	 * 每日凌晨2点执行
	 *
	 * @author liujun
	 * @param jobExecutionContext
	 */
	@Override
	public void run(JobExecutionContext jobExecutionContext){
		LogUtil.info(LOGGER, "HouseStatisticsMsgJob 开始执行.....");
		// 0 0 2 * *
		try {
			
			//处理历史数据
			statsOrdersAndConsultationByHouse();
			
			Long t1 = System.currentTimeMillis();
			SmsTemplateService smsTemplateService =  (SmsTemplateService)ApplicationContext.getContext().getBean("job.smsTemplateService");
			String statsDate = DateUtil.getDayBeforeCurrentDate();
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("startTime", statsDate);
			paramMap.put("endTime", DateUtil.dateFormat(new Date()));

			MsgHouseService msgHouseService = (MsgHouseService) ApplicationContext.getContext().getBean("message.msgHouseService");
			String consultJson = msgHouseService.queryConsultNumByHouseFid(JsonEntityTransform.Object2Json(paramMap));
			List<HouseStatsVo> consultList = SOAResParseUtil.getListValueFromDataByKey(consultJson, "list", HouseStatsVo.class);

			OrderPayService orderPayService = (OrderPayService) ApplicationContext.getContext().getBean("order.orderPayService");
			String tradeJson = orderPayService.queryTradeNumByHouseFid(JsonEntityTransform.Object2Json(paramMap));
			List<HouseStatsVo> tradeList = SOAResParseUtil.getListValueFromDataByKey(tradeJson, "list", HouseStatsVo.class);

			List<HouseStatsDayMsgEntity> list = this.combineList(consultList, tradeList, DateUtil.parseDate(statsDate, "yyyy-MM-dd"));
			//同步上月房源收益
			HouseJobService houseJobService = (HouseJobService) ApplicationContext.getContext().getBean("job.houseJobService");

			houseJobService.houseStats(JsonEntityTransform.Object2Json(list));

			Long t2 = System.currentTimeMillis();
			LogUtil.info(LOGGER, "房源按日统计交易量以及咨询量结束，用时t2-t1={}",t2-t1);
			try {
				String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE.getCode());
				SmsRequest smsRequest  = new SmsRequest();
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("{1}", "房源按日统计交易量以及咨询量");
				paramsMap.put("{2}", "耗时"+(t2-t1)+"ms");
				smsRequest.setParamsMap(paramsMap);
				smsRequest.setMobile(ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mobileList.getType(),EnumMinsuConfig.minsu_mobileList.getCode()));
				smsRequest.setSmsCode(msgCode);
				smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
			}catch (Exception e){
				LogUtil.error(LOGGER,"定时任务发送短信失败：e：{}",e);
			}

			LogUtil.info(LOGGER, "HouseStatisticsMsgJob执行结束");
		}catch (Exception e){
			LogUtil.error(LOGGER,"HouseStatisticsMsgJob error:{}",e);
		}
	}


	/**
	 * 
	 * 房源维度： 按天统计房源咨询量和交易量
	 *
	 * @author yd
	 * @created 2017年6月27日 下午7:41:50
	 *
	 */
	public void statsOrdersAndConsultationByHouse(){

		try {
			LogUtil.info(LOGGER, "【statsOrdersAndConsultationByHouse】房源按日统计交易量以及咨询量历史数据处理开始");
			Long t1 = System.currentTimeMillis();
			MsgHouseService msgHouseService = (MsgHouseService) ApplicationContext.getContext().getBean("message.msgHouseService");
			OrderPayService orderPayService = (OrderPayService) ApplicationContext.getContext().getBean("order.orderPayService");
			HouseJobService houseJobService = (HouseJobService) ApplicationContext.getContext().getBean("job.houseJobService");
			Date statsDate = DateUtil.parseDate("2017-02-05", "yyyy-MM-dd");
			Date nowStatsDate = DateUtil.parseDate("2017-06-27", "yyyy-MM-dd");
			
			Date endStatsDate = DateUtil.parseDate("2017-06-28", "yyyy-MM-dd");
			statsDate = DateSplitUtil.jumpDate(statsDate, 1);
			
			if(DateUtil.parseDate(DateUtil.dateFormat(new Date()), "yyyy-MM-dd").getTime()>=endStatsDate.getTime()){
				return ;
			}

			for (; statsDate.getTime() < nowStatsDate.getTime(); statsDate=DateSplitUtil.jumpDate(statsDate, 1)) {
				
				Map<String, Object> paramMap = new HashMap<>();
				
				String startTime = DateUtil.dateFormat(DateSplitUtil.jumpDate(statsDate, -1), "yyyy-MM-dd");
				String endTime = DateUtil.dateFormat(statsDate, "yyyy-MM-dd");
				paramMap.put("startTime", startTime);
				paramMap.put("endTime", endTime);
				
				String consultJson = msgHouseService.queryConsultNumByHouseFid(JsonEntityTransform.Object2Json(paramMap));
				List<HouseStatsVo> consultList = SOAResParseUtil.getListValueFromDataByKey(consultJson, "list", HouseStatsVo.class);
				
				String tradeJson = orderPayService.queryTradeNumByHouseFid(JsonEntityTransform.Object2Json(paramMap));
				List<HouseStatsVo> tradeList = SOAResParseUtil.getListValueFromDataByKey(tradeJson, "list", HouseStatsVo.class);
				
				
				List<HouseStatsDayMsgEntity> list = this.combineList(consultList, tradeList, DateSplitUtil.jumpDate(statsDate, -1));
				houseJobService.houseStats(JsonEntityTransform.Object2Json(list));

			}
			
			Long t2 = System.currentTimeMillis();
			LogUtil.info(LOGGER, "【statsOrdersAndConsultationByHouse】房源按日统计交易量以及咨询量历史数据处理结束，用时t2-t1={}",t2-t1);
		} catch (Exception e) {
			LogUtil.error(LOGGER,"【statsOrdersAndConsultationByHouse】房源按日统计交易量以及咨询量历史数据处理异常 error:{}",e);
		}


	}

	/**
	 * 合并统计数据
	 *
	 * @author liujun
	 * @created 2016年12月2日
	 *
	 * @param consultList
	 * @param tradeList
	 * @param statsDate 
	 * @return
	 */
	private List<HouseStatsDayMsgEntity> combineList(List<HouseStatsVo> consultList, List<HouseStatsVo> tradeList, Date statsDate) {
		List<HouseStatsDayMsgEntity> statsList = new ArrayList<HouseStatsDayMsgEntity>();
		Map<String, HouseStatsDayMsgEntity> map = new HashMap<>();
		for (HouseStatsVo houseStatsVo : consultList) {
			HouseStatsDayMsgEntity entity = new HouseStatsDayMsgEntity();
			if(Check.NuNStr(houseStatsVo.getHouseFid())){
				continue;
			}
			entity.setHouseFid(houseStatsVo.getHouseFid());
			entity.setRentWay(houseStatsVo.getRentWay());
			entity.setConsultNum(houseStatsVo.getStatsNum());
			entity.setStatsDate(statsDate);
			statsList.add(entity);
			map.put(houseStatsVo.getHouseFid(), entity);
		}

		for (HouseStatsVo houseStatsVo : tradeList) {
			
			if(Check.NuNStr(houseStatsVo.getHouseFid())){
				continue;
			}
			if (map.containsKey(houseStatsVo.getHouseFid())) {
				HouseStatsDayMsgEntity entity = map.get(houseStatsVo.getHouseFid());
				entity.setTradeNum(houseStatsVo.getStatsNum());
			} else {
				HouseStatsDayMsgEntity entity = new HouseStatsDayMsgEntity();
				entity.setHouseFid(houseStatsVo.getHouseFid());
				entity.setRentWay(houseStatsVo.getRentWay());
				entity.setTradeNum(houseStatsVo.getStatsNum());
				entity.setStatsDate(statsDate);
				statsList.add(entity);
			}
		}
		return statsList;
	}

}
