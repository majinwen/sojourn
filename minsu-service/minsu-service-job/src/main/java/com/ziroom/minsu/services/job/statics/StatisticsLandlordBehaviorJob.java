package com.ziroom.minsu.services.job.statics;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.DateUtil.IntervalUnit;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.LandlordStatisticsEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.LandlordStaticsService;
import com.ziroom.minsu.services.customer.dto.StaticsCusBaseReqDto;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.dto.MsgStaticsRequest;
import com.ziroom.minsu.services.order.api.inner.StaticsService;
import com.ziroom.minsu.services.order.dto.OrderStaticsRequest;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>定时任务统计获取房东行为信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @since 1.0
 */
public class StatisticsLandlordBehaviorJob extends AsuraJob{

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsLandlordBehaviorJob.class);
   
    /**
     * 定时任务统计获取房东行为信息
     */
   @Override
	public void run(JobExecutionContext jobExecutionContext) {

	   Long start = System.currentTimeMillis();
	   SmsTemplateService smsTemplat = (SmsTemplateService)ApplicationContext.getContext().getBean("basedata.smsTemplateService");
	   String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE.getCode());
		try {
		    LogUtil.info(LOGGER, "StatisticsLandlordBehaviorJob 开始执行.....");
		    //分页获取业主列表:获取两个时间间隔，如 统计间隔 时长 1天，响应时间在20分钟内的
		    //IM统计信息：IM回复人数接口，IM回复时间接口
		    //订单统计信息：房东响应人数接口 ，房东拒绝人数接口 ，系统自动拒绝人数接口，
		    //保存统计信息入库：判断库里是否有房东的数据，如果有，更新，如果没有，新增
		    
		    ZkSysService zksysService =  (ZkSysService)ApplicationContext.getContext().getBean("basedata.zkSysService");

		    //统计X天前到当前时间的信息统计
		    int limitTimeDay = Integer.valueOf(zksysService.getZkSysValue("statics", "limitTimeDay"));
		    //统计房东响应时间 小于 X 分钟的信息统计
		    int sumTime = Integer.valueOf(zksysService.getZkSysValue("statics", "sumTime"));//获取分钟信息
		    //获取具体的时间限制
		    Date limitTime = DateUtil.intervalDate(-limitTimeDay, IntervalUnit.DAY);//获取限制时间
		    int pageLimit = 30;//每次取30个房东信息
		    int initPage = 1;//page初始化为1
		    
		    //计算总得房东数量
		    CustomerInfoService customerInfoService = (CustomerInfoService) ApplicationContext.getContext().getBean("job.customerInfoService");
		    String countResultJson = customerInfoService.countLanlordNum(null);
		    DataTransferObject countResult = JsonEntityTransform.json2DataTransferObject(countResultJson);
		    if(countResult.getCode() == DataTransferObject.ERROR){
		    	LogUtil.info(LOGGER, "StatisticsLandlordBehaviorJob 计算总得房东数量错误，当前result:{}",countResult);
	 			return;
	 		}
		    int count = countResult.parseData("result", new TypeReference<Integer>(){});
		    if(count == 0){
		    	LogUtil.info(LOGGER, "StatisticsLandlordBehaviorJob 计算总得房东数量为0");
		    	return;
		    }
		    //获取分页查询的次数
		    int pageAll = ValueUtil.getPage(count,pageLimit);
		    
		    //分页查询  房东列表参数设置
		    StaticsCusBaseReqDto customerDto = new StaticsCusBaseReqDto();
		    customerDto.setLimit(pageLimit);
		    
		    //IM数据统计 请求参数设置
		    MsgBaseService msgBaseService = (MsgBaseService) ApplicationContext.getContext().getBean("job.msgBaseService");
		    MsgStaticsRequest msgRequest = new MsgStaticsRequest();
			msgRequest.setLimitTime(limitTime);
			msgRequest.setSumTime(sumTime*60);
			
			//订单数据统计 参数设置
		    StaticsService staticsService = (StaticsService) ApplicationContext.getContext().getBean("job.staticsService");
		    OrderStaticsRequest orderStaticRequest = new OrderStaticsRequest();
		    orderStaticRequest.setLimitTime(limitTime);
		    orderStaticRequest.setSumTime(sumTime*60);
		    
		    //保存统计信息入库
		    LandlordStaticsService landlordStaticsService = (LandlordStaticsService) ApplicationContext.getContext().getBean("job.landlordStaticsService");
		    
		    for(int i=0;i<pageAll;i++,initPage++){
		    	customerDto.setPage(initPage);
		        //分页获取房东数据
		    	String cusResultJson = customerInfoService.staticsGetLandlordList(JsonEntityTransform.Object2Json(customerDto));
		        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(cusResultJson);
		         if(dto.getCode() == DataTransferObject.ERROR){
		        	LogUtil.info(LOGGER, "StatisticsLandlordBehaviorJob 分页 获取房东信息错误，当前initPage:{}",initPage);
		 			break;
		 		}
		        List<CustomerBaseMsgEntity> cusBaseList = dto.parseData("list", new TypeReference<List<CustomerBaseMsgEntity>>(){});
		        if(Check.NuNCollection(cusBaseList)){
		        	LogUtil.info(LOGGER, "StatisticsLandlordBehaviorJob 分页 获取房东信息为空，当前initPage:{}",initPage);
		        	break;
		        }
		        
		        int IMReplyPeopleNum = 0;//IM 回复人数
		    	int IMReplySumTime = 0;//IM 回复人数 所用的总时间
		    	int replyOrderNum = 0;//房东 响应的总订单数
		        int lanRefuseOrderNum = 0;//房东 拒绝的总订单数
		        int sysRefuseOrderNum = 0;//系统 拒绝的总订单数
		        int orderReplySumTime = 0;//房东响应订单 所用的总时间
		    	int orderSumNum = 0;//房东 的订单总数
		        
		        for(CustomerBaseMsgEntity cbm : cusBaseList){
		        	if(Check.NuNStr(cbm.getUid())){
		        		LogUtil.info(LOGGER, "StatisticsLandlordBehaviorJob 用户uid为空，结束本次循环，继续下次循环，user:{}",JsonEntityTransform.Object2Json(cbm));
		        		continue;
		        	}
		        	
		        	msgRequest.setLandlordUid(cbm.getUid());
		        	orderStaticRequest.setLandlordUid(cbm.getUid());
		        	//获取IM统计信息
		        	dto = JsonEntityTransform.json2DataTransferObject(msgBaseService.taskStaticsCountLanImReplyInfo(JsonEntityTransform.Object2Json(msgRequest)));
		            if(dto.getCode() == DataTransferObject.ERROR){
		            	LogUtil.info(LOGGER, "StatisticsLandlordBehaviorJob 获取IM回统计信息错误，结束本次循环，继续下次循环，uid:{},param:{}，msg:{}",cbm.getUid(),JsonEntityTransform.Object2Json(msgRequest),dto.getMsg());
		    			continue;
		    		}
		            IMReplyPeopleNum = dto.parseData("replyPeopleNum", new TypeReference<Integer>(){});
		            IMReplySumTime = dto.parseData("replySumTime", new TypeReference<Integer>(){});
		           
		            //获取订单统计信息
		            dto = JsonEntityTransform.json2DataTransferObject(staticsService.taskStaticsCountInfo(JsonEntityTransform.Object2Json(orderStaticRequest)));
		            if(dto.getCode() == DataTransferObject.ERROR){
		            	LogUtil.info(LOGGER, "StatisticsLandlordBehaviorJob 获取订单统计信息错误，结束本次循环，继续下次循环，uid:{},param:{}，msg:{}",cbm.getUid(),JsonEntityTransform.Object2Json(orderStaticRequest),dto.getMsg());
		    			continue;
		    		}
		            
		            replyOrderNum = dto.parseData("replyOrderNum", new TypeReference<Integer>(){});
		            lanRefuseOrderNum = dto.parseData("lanRefuseOrderNum", new TypeReference<Integer>(){});
		            sysRefuseOrderNum = dto.parseData("sysRefuseOrderNum", new TypeReference<Integer>(){});
		            orderReplySumTime = dto.parseData("replyOrderTime", new TypeReference<Integer>(){});
		            orderSumNum = dto.parseData("lanOrderNum", new TypeReference<Integer>(){});
		            
		            LandlordStatisticsEntity landlordStatistics = new LandlordStatisticsEntity();
		            landlordStatistics.setLandlordUid(cbm.getUid());
		            landlordStatistics.setReplyPeopleNum(IMReplyPeopleNum);
		            landlordStatistics.setImReplySumTime(IMReplySumTime);
		            landlordStatistics.setReplyOrderNum(replyOrderNum);
		            landlordStatistics.setLanRefuseOrderNum(lanRefuseOrderNum);
		            landlordStatistics.setSysRefuseOrderNum(sysRefuseOrderNum);
		            landlordStatistics.setOrderReplySumTime(orderReplySumTime);
		            landlordStatistics.setOrderSumNum(orderSumNum);
		            
		            if(!checkIsHasLan(cbm.getUid(),landlordStaticsService)){
		            	landlordStaticsService.staticsInsertLanActAssociationImp(JsonEntityTransform.Object2Json(landlordStatistics));
		            }else{
		            	landlordStaticsService.staticsUpdateLanActAssociationImp(JsonEntityTransform.Object2Json(landlordStatistics));
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
		   paramsMap.put("{2}", "耗时："+ (end - start) + "ms");
		   smsRequest.setParamsMap(paramsMap);
		   smsRequest.setMobile(ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mobileList.getType(),EnumMinsuConfig.minsu_mobileList.getCode()));
		   smsRequest.setSmsCode(msgCode);
		   smsTemplat.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
	   }catch (Exception e){
		   LogUtil.error(LOGGER,"定时任务发送短信失败：e：{}",e);
	   }

    }
    
   /**
    * 校验房东是否已经存在
    * @param lanUid
    * @return
    */
    public static boolean checkIsHasLan(String lanUid,LandlordStaticsService landlordStaticsService){
    	boolean result = false;
    	//房东拒绝的总订单数
    	DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(landlordStaticsService.findLandlordStatisticsByUid(lanUid));
	     if(dto.getCode() != DataTransferObject.SUCCESS){
	    	LogUtil.info(LOGGER, "checkIsHasLan 查询房东信息 调用失败");
	    	result = true;
			return result;
		}   
	    LandlordStatisticsEntity lanStatics = dto.parseData("result", new TypeReference<LandlordStatisticsEntity>(){});
        if(!Check.NuNObj(lanStatics)){
        	LogUtil.info(LOGGER, "checkIsHasLan 库里存在该房东的统计信息");
        	result = true;
        }
        return result;
    }

	
}
