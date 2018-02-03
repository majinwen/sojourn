package com.ziroom.minsu.services.job.evaluate;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateRulesEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.order.OrderEvaStatusEnum;
import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>定时检查 是否有可以显示的评价，如果有则重新统计该评价的房源或者对应人的评价 </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class CheckEvaluateShowJob extends AsuraJob {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckEvaluateShowJob.class);
	
	@Override
	public void run(JobExecutionContext jobExecutionContext) {
        
		Long t1 = System.currentTimeMillis();

        //订单的api
        OrderCommonService orderCommonService = (OrderCommonService) ApplicationContext.getContext().getBean("job.orderCommonService");

		EvaluateOrderService evaluateOrderService = (EvaluateOrderService) ApplicationContext.getContext().getBean("job.evaluateOrderService");

		CityTemplateService cityTemplateService = (CityTemplateService) ApplicationContext.getContext().getBean("job.cityTemplateService");

        LogUtil.info(LOGGER, "CheckEvaluateShowJob 开始执行.....");
        
        try {

			String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum005.getValue()));
			DataTransferObject textDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
			if (textDto.getCode() == DataTransferObject.ERROR){
				return;
			}
			int limitDay = ValueUtil.getintValue(textDto.getData().get("textValue"));
        	int page = 1;
        	int limit = 20;

			List<EvaluateOrderEntity> allList = new ArrayList<>();

			EvaluateRequest request = new EvaluateRequest();
			request.setLimit(limit);
        	for(;;) {
				request.setPage(page);
				LogUtil.info(LOGGER,"当前页数page={}",page);
				DataTransferObject resultDto= JsonEntityTransform.json2DataTransferObject(evaluateOrderService.listEvaluateUncheckShow(JsonEntityTransform.Object2Json(request)));
				LogUtil.info(LOGGER,"查询需要展示的未筛选列表list={}",resultDto.toJsonString());
				page++;
				if (resultDto.getCode() == DataTransferObject.ERROR){
					continue;
				}
				List<EvaluateOrderEntity> list = resultDto.parseData("list", new TypeReference<List<EvaluateOrderEntity>>() {});
				if (Check.NuNCollection(list)){
					break;
				}

				List<EvaluateOrderEntity> showList = canShowAndStat(orderCommonService, list, limitDay);
				LogUtil.info(LOGGER,"需要同步展示评价的列表list={}",JsonEntityTransform.Object2Json(showList));

				if (Check.NuNCollection(showList)){
					continue;
				}

				allList.addAll(showList);

				/*for (int i =0;i<showList.size();i++){
					//单个评价处理
					saveEvaluateShowAndStat(evaluateOrderService,orderCommonService,showList.get(i),limitDay);
				}*/
			}

			for (int j = 0;j<allList.size();j++){
				saveEvaluateShowAndStat(evaluateOrderService,orderCommonService,allList.get(j),limitDay);
			}
        	
		} catch (Exception e) {
			LogUtil.error(LOGGER,"CheckEvaluateShowJob 执行失败：e={}",e);
		}
        
        LogUtil.info(LOGGER, "CheckEvaluateShowJob 执行结束,共耗时"+(new Date().getTime()-t1)+"ms");
		
	}

	/**
	 * 评价时间是否超时
	 * @param realEndTime
	 * @param limitDay
	 * @return
	 */
	public boolean isEvaExpire(Date realEndTime,int limitDay){
		if(Check.NuNObj(realEndTime)){
			return false;
		}
		long between = ((new Date()).getTime() - realEndTime.getTime()) / 1000;
		if(between > (limitDay * 24 * 3600)){
			return true;
		}else{
			return false;
		}
	}


	public void saveEvaluateShowAndStat(EvaluateOrderService evaluateOrderService,OrderCommonService orderCommonService,EvaluateOrderEntity evaluateOrderEntity,int limitDay){
		LogUtil.info(LOGGER,"单个评价开始展示与同步信息evaluateOrderEntity={}",JsonEntityTransform.Object2Json(evaluateOrderEntity));
		//需要更新的
		EvaluateRequest request = new EvaluateRequest();
		request.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		if (evaluateOrderEntity.getEvaUserType() == UserTypeEnum.TENANT.getUserCode()){
			//评价人是房客
			request.setHouseFid(evaluateOrderEntity.getHouseFid());
			request.setRoomFid(evaluateOrderEntity.getRoomFid());
			request.setRentWay(evaluateOrderEntity.getRentWay());
			request.setEvaUserType(UserTypeEnum.TENANT.getUserType());
			//查询
			DataTransferObject listDto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.listAllEvaOrderByCondition(JsonEntityTransform.Object2Json(request)));
			if (listDto.getCode() == DataTransferObject.SUCCESS){
				List<EvaluateOrderEntity> evalist = listDto.parseData("list", new TypeReference<List<EvaluateOrderEntity>>() {});
				List<EvaluateOrderEntity> statList = canShowAndStat(orderCommonService, evalist, limitDay);
				//可以统计的列表
				LogUtil.info(LOGGER,"房客统计评价数量count={},list={}",statList.size(),JsonEntityTransform.Object2Json(statList));
				String s = evaluateOrderService.updateShowAndStatEva(JsonEntityTransform.Object2Json(statList),
						JsonEntityTransform.Object2Json(evaluateOrderEntity), String.valueOf(UserTypeEnum.TENANT.getUserType()));
				LogUtil.info(LOGGER,"同步房客评价统计和显示result={}",s);
			}
		}

		if (evaluateOrderEntity.getEvaUserType() == UserTypeEnum.LANDLORD.getUserCode()){
			request.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
			request.setRatedUserUid(evaluateOrderEntity.getRatedUserUid());
			DataTransferObject listDto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.listAllEvaOrderByCondition(JsonEntityTransform.Object2Json(request)));
			if (listDto.getCode() == DataTransferObject.SUCCESS){
				List<EvaluateOrderEntity> evalist = listDto.parseData("list", new TypeReference<List<EvaluateOrderEntity>>() {});
				List<EvaluateOrderEntity> statList = canShowAndStat(orderCommonService, evalist, limitDay);
				LogUtil.info(LOGGER,"房东统计评价数量count={},list={}",statList.size(),JsonEntityTransform.Object2Json(statList));
				//可以统计的列表
				String s = evaluateOrderService.updateShowAndStatEva(JsonEntityTransform.Object2Json(statList),
						JsonEntityTransform.Object2Json(evaluateOrderEntity), String.valueOf(UserTypeEnum.LANDLORD.getUserType()));
				LogUtil.info(LOGGER,"同步房东评价统计和显示result={}",s);
			}
		}

	}

	/**
	 * 可以显示和统计的
	 * @param orderCommonService
	 * @param list
	 * @param limitDay
	 * @return
	 */
	public List<EvaluateOrderEntity> canShowAndStat(OrderCommonService orderCommonService,List<EvaluateOrderEntity> list,int limitDay){
		List<EvaluateOrderEntity> getList = new ArrayList<>();
		for (EvaluateOrderEntity evaOrder : list){
			String orderSn = evaOrder.getOrderSn();
			DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderCommonService.getOrderByOrderSn(orderSn));
			if (orderDto.getCode() == DataTransferObject.ERROR){
				//更新显示状态
				getList.add(evaOrder);
				continue;
			}
			OrderEntity order = orderDto.parseData("order", new TypeReference<OrderEntity>() {});
			if (Check.NuNObj(order)){
				//更新显示状态
				getList.add(evaOrder);
				continue;
			}
			Integer evaUserType = evaOrder.getEvaUserType();
			Integer evaStatus = order.getEvaStatus();
			Date realEndTime = order.getRealEndTime();
			//评价人是房东
			if (evaStatus == OrderEvaStatusEnum.ALL_EVA.getCode()){
				//更新显示状态
				getList.add(evaOrder);
			}
			if ((evaUserType == UserTypeEnum.LANDLORD.getUserType() && evaStatus == OrderEvaStatusEnum.LANLORD_EVA.getCode())
					|| (evaUserType == UserTypeEnum.TENANT.getUserType() && evaStatus == OrderEvaStatusEnum.UESR_HVA_EVA.getCode())){
				if (!Check.NuNObj(realEndTime) && isEvaExpire(realEndTime,limitDay)){
					//更新
					getList.add(evaOrder);
				}
			}
		}
		return getList;
	}


}
