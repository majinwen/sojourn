package com.ziroom.minsu.services.job.statics;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.base.util.DateUtil.IntervalUnit;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.LandlordBehaviorEntity;
import com.ziroom.minsu.entity.customer.LandlordStatisticsEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.LandlordBehaviorService;
import com.ziroom.minsu.services.customer.api.inner.LandlordStaticsService;
import com.ziroom.minsu.services.customer.dto.StaticsCusBaseReqDto;
import com.ziroom.minsu.services.house.api.inner.HouseJobService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.dto.MsgReplyStaticsData;
import com.ziroom.minsu.services.message.dto.MsgReplyStaticsRequest;
import com.ziroom.minsu.services.message.dto.MsgStaticsRequest;
import com.ziroom.minsu.services.order.api.inner.StaticsService;
import com.ziroom.minsu.services.order.dto.OrderLandlordStaticsDto;
import com.ziroom.minsu.services.order.dto.OrderStaticsRequest;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerIdTypeEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>定时任务统计获取房东行为信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @since 1.0
 */
public class LandlordBehaviorJob extends AsuraJob{

    private static final Logger LOGGER = LoggerFactory.getLogger(LandlordBehaviorJob.class);
   
    /**
     * 定时任务统计获取房东行为信息
     */
   @Override
	public void run(JobExecutionContext jobExecutionContext) {

	   Long start = System.currentTimeMillis();
	   SmsTemplateService smsTemplat = (SmsTemplateService)ApplicationContext.getContext().getBean("basedata.smsTemplateService");

	   Map<String,ConfCityEntity>  cityMap = new ConcurrentHashMap<>();

	   String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE.getCode());
	   int successCount=0;
		try {
		    LogUtil.info(LOGGER, "StatisticsLandlordBehaviorJob 开始执行.....");

			HouseJobService houseJobService = (HouseJobService) ApplicationContext.getContext().getBean("job.houseJobService");


		    int pageLimit = 100;// 每页100条数据
		    int initPage = 1;//page初始化为1
		    
		    //计算总得房东数量
		    CustomerInfoService customerInfoService = (CustomerInfoService) ApplicationContext.getContext().getBean("job.customerInfoService");
		    String countResultJson = customerInfoService.countLanlordNum(null);
		    DataTransferObject countResult = JsonEntityTransform.json2DataTransferObject(countResultJson);
		    if(countResult.getCode() == DataTransferObject.ERROR){
		    	LogUtil.info(LOGGER, "LandlordBehaviorJob 计算总得房东数量错误，当前result:{}",countResult);
	 			return;
	 		}
		    int count = countResult.parseData("result", new TypeReference<Integer>(){});
		    if(count == 0){
		    	LogUtil.info(LOGGER, "LandlordBehaviorJob 计算总得房东数量为0");
		    	return;
		    }

		    //获取分页查询的次数
		    int pageAll = ValueUtil.getPage(count,pageLimit);
		    
		    //分页查询  房东列表参数设置
		    StaticsCusBaseReqDto customerDto = new StaticsCusBaseReqDto();
		    customerDto.setLimit(pageLimit);

		    //行为入库
		    LandlordBehaviorService landlordBehaviorService = (LandlordBehaviorService) ApplicationContext.getContext().getBean("job.landlordBehaviorService");
		    
			//订单数据统计 参数设置		    
		    ZkSysService zksysService =  (ZkSysService)ApplicationContext.getContext().getBean("basedata.zkSysService");
		    //统计X天前到当前时间的信息统计
		    int limitTimeDay = Integer.valueOf(zksysService.getZkSysValue("statics", "limitTimeDay"));
		    //统计房东响应时间 小于 X 分钟的信息统计
		    int sumTime = Integer.valueOf(zksysService.getZkSysValue("statics", "sumTime"));//获取分钟信息
		    //获取具体的时间限制
		    Date limitTime = DateUtil.intervalDate(-limitTimeDay, IntervalUnit.DAY);//获取限制时间
		    StaticsService staticsService = (StaticsService) ApplicationContext.getContext().getBean("job.staticsService");
		    MsgBaseService msgBaseService = (MsgBaseService) ApplicationContext.getContext().getBean("job.msgBaseService");

		    OrderStaticsRequest orderStaticRequest = new OrderStaticsRequest();
//		    orderStaticRequest.setLimitTime(limitTime);
//		    orderStaticRequest.setSumTime(sumTime*60);
		    
		    for(int i=0;i<pageAll;i++,initPage++){
		    	customerDto.setPage(initPage);
		        //分页获取房东数据
		    	String cusResultJson = customerInfoService.staticsGetLandlordList(JsonEntityTransform.Object2Json(customerDto));
		        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(cusResultJson);
		         if(dto.getCode() != DataTransferObject.SUCCESS){
		        	LogUtil.info(LOGGER, "LandlordBehaviorJob 分页 获取房东信息错误，当前initPage:{}",initPage);
		 			break;
		 		}
		        List<CustomerBaseMsgEntity> cusBaseList = dto.parseData("list", new TypeReference<List<CustomerBaseMsgEntity>>(){});
		        if(Check.NuNCollection(cusBaseList)){
		        	LogUtil.info(LOGGER, "LandlordBehaviorJob 分页 获取房东信息为空，当前initPage:{}",initPage);
		        	continue;
		        }
		        

				int advisoryNum = 0; //咨询数量
				int replyNum = 0; //回复数量
				int reply30Num = 0; //30min内回复数量
				int replyTimeAvg = 0;//平均回复时长
				int replyTimeMax = 0;//最长回复时间
				int replyTimeMin = 0;//最短回复时长
				int tenWaitEvaNum = 0;//房客待评价条数
				int tenHasEvaNum = 0;//房客已评价条数
				int lanWaitEvaNum = 0;//房东待评价订单数
				int lanHasEvaNum = 0;//房东已评价条数
				int totalOrderNum = 0;//订单总数
				int acceptOrderNum = 0;//接受订单个数
				int refuseOrderNum = 0;//拒绝订单数量
				int notDoOrderNum = 0;//未处理订单数量
				int houseNum = 0;//房源数
				int houseSkuNum = 0;//房源sku
		        for(CustomerBaseMsgEntity cbm : cusBaseList){
		        	String landUid = cbm.getUid();
		        	try {
		        		//获取订单统计信息
		        		orderStaticRequest.setLandlordUid(landUid);
		        		dto = JsonEntityTransform.json2DataTransferObject(staticsService.staticsLandOrderCountInfo(JsonEntityTransform.Object2Json(orderStaticRequest)));
		        		if(dto.getCode() == DataTransferObject.ERROR){
		        			LogUtil.info(LOGGER, "房东订单统计信息错误，结束本次循环，继续下次循环，uid:{},param:{}，msg:{}",cbm.getUid(),JsonEntityTransform.Object2Json(orderStaticRequest),dto.getMsg());
		        			continue;
		        		}
		        		
		        		OrderLandlordStaticsDto landstatics =dto.parseData("result", new TypeReference<OrderLandlordStaticsDto>(){});
		        		if (landstatics!=null) {
		        			 tenWaitEvaNum = landstatics.getWaitTenantEvaNum().intValue();//房客待评价条数
		    				 tenHasEvaNum = landstatics.getTenantEvaedNum().intValue();//房客已评价条数
		    				 lanWaitEvaNum = landstatics.getWaitLandlordEvaNum().intValue();//房东待评价订单数
		    				 lanHasEvaNum = landstatics.getLandlordEvaedNum().intValue();//房东已评价条数
		    				 totalOrderNum = landstatics.getLanOrderNum().intValue();//订单总数
		    				 acceptOrderNum = landstatics.getAcceptOrderNum().intValue();//接受订单个数
		    				 refuseOrderNum = landstatics.getLanRefuseOrderNum().intValue();//拒绝订单数量
		    				 notDoOrderNum = landstatics.getSysRefuseOrderNum().intValue();//未处理订单数量
		        		}
		        		
		        		//消息回复数据
		        		MsgReplyStaticsRequest msgReplyDto = new MsgReplyStaticsRequest();
		        		msgReplyDto.setLandlordUid(cbm.getUid());
		        		dto = JsonEntityTransform.json2DataTransferObject(msgBaseService.staticsLanReplyData(JsonEntityTransform.Object2Json(msgReplyDto)));
		        		if(dto.getCode() == DataTransferObject.ERROR){
		        			LogUtil.info(LOGGER, "房东消息回复统计错误，结束本次循环，继续下次循环，uid:{},param:{}，msg:{}",cbm.getUid(),JsonEntityTransform.Object2Json(msgReplyDto),dto.getMsg());
		        			continue;
		        		}
		        		MsgReplyStaticsData msgReplyData =dto.parseData("result", new TypeReference<MsgReplyStaticsData>(){});
		        		if (msgReplyData!=null) {
		        			 advisoryNum = msgReplyData.getTotalMsgNum(); //咨询数量
		    				 replyNum = msgReplyData.getRepliedMsgNum(); //回复数量
		    				 replyTimeAvg = msgReplyData.getAvgRepliedTime();//平均回复时长
		    				 replyTimeMax = msgReplyData.getMaxRepliedTime();//最长回复时间
		    				 replyTimeMin = msgReplyData.getMinRepliedTime();//最短回复时长
		    				 reply30Num = msgReplyData.getReplied30MsgNum(); //30min内回复数量
		        		}

		        		LandlordBehaviorEntity landlordBehaviorEntity = new LandlordBehaviorEntity();
		        		landlordBehaviorEntity.setAcceptOrderNum(acceptOrderNum);
		        		landlordBehaviorEntity.setAdvisoryNum(advisoryNum);
		        		landlordBehaviorEntity.setCreateTime(new Date());
		        		landlordBehaviorEntity.setIsDel(YesOrNoEnum.NO.getCode());
		        		landlordBehaviorEntity.setLandlordUid(cbm.getUid());
		        		landlordBehaviorEntity.setLanHasEvaNum(lanHasEvaNum);
		        		landlordBehaviorEntity.setLanWaitEvaNum(lanWaitEvaNum);

		        		landlordBehaviorEntity.setNotdoOrderNum(notDoOrderNum);
		        		landlordBehaviorEntity.setRefuseOrderNum(refuseOrderNum);
		        		landlordBehaviorEntity.setReplyNum(replyNum);
		        		landlordBehaviorEntity.setReplyTimeAvg(replyTimeAvg);
		        		landlordBehaviorEntity.setReplyTimeMax(replyTimeMax);
		        		landlordBehaviorEntity.setReplyTimeMax(replyTimeMax);
		        		landlordBehaviorEntity.setReplyTimeMin(replyTimeMin);
		        		landlordBehaviorEntity.setTenHasEvaNum(tenHasEvaNum);
		        		landlordBehaviorEntity.setTenWaitEvaNum(tenWaitEvaNum);
		        		landlordBehaviorEntity.setTotalOrderNum(totalOrderNum);

						String houseResultJson = houseJobService.countLandHouseInfo(landUid);
						DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseResultJson);

						//获取当前的上架房源数量
						Integer houseNumRst =houseDto.parseData("houseNum", new TypeReference<Integer>(){});
						if (houseNumRst!=null) {
							houseNum = houseNumRst;
						}
						//获取当前的房源sku数量
						Integer houseSkuNumRst =houseDto.parseData("houseSkuNum", new TypeReference<Integer>(){});
						if (houseNumRst!=null) {
							houseSkuNum = houseSkuNumRst;
						}
						landlordBehaviorEntity.setHouseNum(houseNum);
						landlordBehaviorEntity.setHouseSkuNum(houseSkuNum);

						String  idCityCode = this.getIdCityCode(cbm.getIdType(), cbm.getIdNo(), cityMap);
						//设置当前房东的身份证所在的城市code
						landlordBehaviorEntity.setCityCode(idCityCode);

		        		dto = JsonEntityTransform.json2DataTransferObject(landlordBehaviorService.findLandlordBehavior(cbm.getUid()));
		        		if(dto.getCode() == DataTransferObject.ERROR){
		        			LogUtil.info(LOGGER, "查询房东行为统计失败，结束本次循环，继续下次循环，uid:{},msg:{}",cbm.getUid(),dto.getMsg());
		        			continue;
		        		}
		        		LandlordBehaviorEntity oldlandlordBehaviorEntity =dto.parseData("obj", new TypeReference<LandlordBehaviorEntity>(){});
		        		if (Check.NuNObj(oldlandlordBehaviorEntity)) {
		        			landlordBehaviorEntity.setFid(UUIDGenerator.hexUUID());
		        			dto = JsonEntityTransform.json2DataTransferObject(landlordBehaviorService.saveLandlordBehavior(JsonEntityTransform.Object2Json(landlordBehaviorEntity)));
						}else {
							landlordBehaviorEntity.setFid(oldlandlordBehaviorEntity.getFid());
		        			landlordBehaviorEntity.setId(oldlandlordBehaviorEntity.getId());
		        			dto = JsonEntityTransform.json2DataTransferObject(landlordBehaviorService.updateLandlordBehaviorByUid(JsonEntityTransform.Object2Json(landlordBehaviorEntity)));
						}
		        		if(dto.getCode() == DataTransferObject.ERROR){
		        			LogUtil.info(LOGGER, "房东行为统计保存失败，结束本次循环，继续下次循环，uid:{},param:{},msg:{}",cbm.getUid(),JsonEntityTransform.Object2Json(landlordBehaviorEntity),dto.getMsg());
		        			continue;
		        		}
		        		
		        		Integer n =dto.parseData("count", new TypeReference<Integer>(){});
		        		if (n!=null) {
		        			successCount+=n;
						}
					} catch (Exception e) {
						LogUtil.error(LOGGER, "房东订单统计信息错误，结束本次循环，继续下次循环，uid:{},param:{}，e:{}",cbm.getUid(),JsonEntityTransform.Object2Json(orderStaticRequest),e);
					}
		        	
		        }
		         
		     }
			LogUtil.info(LOGGER, "StatisticsLandlordBehaviorJob 执行结束");
    	}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE_F.getCode());
       }

	   Long end = System.currentTimeMillis();
	   try {
		   SmsRequest smsRequest  = new SmsRequest();
		   Map<String, String> paramsMap = new HashMap<String, String>();
		   paramsMap.put("{1}", "房东行为统计");
		   paramsMap.put("{2}", "耗时："+ (end - start)/1000 + "秒,更新条数:" + successCount);
		   smsRequest.setParamsMap(paramsMap);
		   smsRequest.setMobile(ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mobileList.getType(),EnumMinsuConfig.minsu_mobileList.getCode()));
		   smsRequest.setSmsCode(msgCode);
		   smsTemplat.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
	   }catch (Exception e){
		   LogUtil.error(LOGGER,"定时任务发送短信失败：e：{}",e);
	   }

    }


	/**
	 * 通过身份证类型获取当前的城市code
	 * @author afi
	 * @param idType
	 * @param idNo
	 * @return
	 */
    private String getIdCityCode(Integer idType, String idNo, Map<String,ConfCityEntity>  cityMap){

		String idCityCode = null;
		if (Check.NuNObjs(idType,idNo)){
			return idCityCode;
		}
		if (idType.intValue()  != CustomerIdTypeEnum.ID.getCode()){
			return idCityCode;
		}
		//校验当前的身份证未通过
		if (!CustomerIdTypeEnum.checkId(idNo)){
			return idCityCode;
		}
		String cityKey = idNo.substring(0,6);

		ConfCityEntity  cityEntity = null;
		if (cityMap.containsKey(cityKey)){
			cityEntity = cityMap.get(cityKey);
		}else {
			cityEntity = this.getConfCityByCode(cityKey,cityMap);
		}
		if (Check.NuNObj(cityEntity)){
			return idCityCode;
		}else {
			idCityCode = cityEntity.getPcode();
		}
		return idCityCode;
	}

	/**
	 * 填充当前的城市code
	 * @author afi
	 * @param cityKey
	 * @param cityMap
	 * @return
	 */
	private ConfCityEntity getConfCityByCode(String cityKey,Map<String,ConfCityEntity>  cityMap){
		if (Check.NuNStr(cityKey)){
			return null;
		}
		ConfCityService confCityService = (ConfCityService) ApplicationContext.getContext().getBean("basedata.confCityService");
		String cityResultJson = confCityService.getConfCityByCode(cityKey);
		DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityResultJson);
		ConfCityEntity cityEntity =cityDto.parseData("cityEntity", new TypeReference<ConfCityEntity>(){});
		if (Check.NuNObj(cityEntity)){
			cityEntity = new ConfCityEntity();
		}
		cityMap.put(cityKey,cityEntity);
		return cityEntity;
	}
}
