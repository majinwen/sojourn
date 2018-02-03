/**
 * @FileName: EvaluateOrderServiceProxy.java
 * @Package com.ziroom.minsu.services.evaluate.proxy
 * 
 * @author yd
 * @created 2016年4月7日 下午9:03:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBehaviorEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateShowEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.LandlordReplyEntity;
import com.ziroom.minsu.entity.evaluate.StatsHouseEvaEntity;
import com.ziroom.minsu.entity.evaluate.StatsTenantEvaEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.constant.EvaluateConsant;
import com.ziroom.minsu.services.evaluate.dto.EvaluateOrderRequest;
import com.ziroom.minsu.services.evaluate.dto.EvaluatePCRequest;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.evaluate.dto.StatsTenantEvaRequest;
import com.ziroom.minsu.services.evaluate.entity.EvaluateBothItemVo;
import com.ziroom.minsu.services.evaluate.entity.EvaluateHouseDetailVo;
import com.ziroom.minsu.services.evaluate.entity.EvaluateVo;
import com.ziroom.minsu.services.evaluate.entity.LandlordEvaluateVo;
import com.ziroom.minsu.services.evaluate.entity.TenantEvaluateVo;
import com.ziroom.minsu.services.evaluate.service.EvaluateOrderServiceImpl;
import com.ziroom.minsu.services.evaluate.service.LandlordEvaluateServiceImpl;
import com.ziroom.minsu.services.evaluate.service.StatsHouseEvaServiceImpl;
import com.ziroom.minsu.services.evaluate.service.StatsTenantEvaServiceImpl;
import com.ziroom.minsu.services.evaluate.service.TenantEvaluateServiceImpl;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerBehaviorRoleEnum;
import com.ziroom.minsu.valenum.customer.LandlordBehaviorEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateUserEnum;
import com.ziroom.minsu.valenum.evaluate.OrderEvaFlagEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>评价管理接口实现</p>
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
@Component("evaluate.evaluateOrderProxy")
public class EvaluateOrderProxy implements EvaluateOrderService{

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER =LoggerFactory.getLogger(EvaluateOrderProxy.class);

	@Resource(name = "evaluate.messageSource")
	private MessageSource messageSource;

	@Resource(name = "evaluate.evaluateOrderServiceImpl")
	private EvaluateOrderServiceImpl evaluateOrderServiceImpl;

	@Resource(name = "evaluate.landlordEvaluateServiceImpl")
	private LandlordEvaluateServiceImpl landlordEvaluateServiceImpl;


	@Resource(name = "evaluate.tenantEvaluateServiceImpl")
	private TenantEvaluateServiceImpl tenantEvaluateServiceImpl;

	@Resource(name = "evaluate.statsHouseEvaServiceImpl")
	private StatsHouseEvaServiceImpl statsHouseEvaServiceImpl;

	@Resource(name = "evaluate.statsTenantEvaServiceImpl")
	private StatsTenantEvaServiceImpl statsTenantEvaServiceImpl;

    @Resource(name = "evaluate.evaluateMsgProxy")
    private EvaluateMsgProxy evaluateMsgProxy;

	@Resource(name = "behavior.rabbitSendClient")
	private RabbitMqSendClient rabbitMqSendClient;

	@Resource(name = "behavior.queueName")
	private QueueName queueName ;

	/**
	 * 线程池框架
	 */
	private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 3000L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<>());


    /**
     *
     * 用户uid给定，分页查出其他房东给该用户的评价内容
     * 说明：被评人 rated_user_uid 必须给定； 状态必须审核成功  eva_statu = 3 ； 必须为房东的评价 eva_user_type = 1;
     *  is_del  未被删除
     *
     * @author yd
     * @created 2016年4月7日 下午8:40:38
     *
     * @param evaluateRequest
     * @return
     */
	@Override
	public String queryLandlordEvaluateByPage(String evaluateRequest) {

		//返回对象
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(evaluateRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数不能为空");
			return dto.toJsonString();
		}
		EvaluateRequest evaRequest = JsonEntityTransform.json2Object(evaluateRequest, EvaluateRequest.class);
		if(Check.NuNObj(evaRequest.getEvaUserType())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("评价人类型不能为空");
			return dto.toJsonString();
		}
		LogUtil.info(LOGGER,"evaluateRequest to toStirng {}",evaRequest.toString());

		evaRequest.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		evaRequest.setIsDel(0);

		PagingResult<LandlordEvaluateVo> pagingResult = this.evaluateOrderServiceImpl.queryLandlordEvaluateByPage(evaRequest);

		dto.putValue("listLandlordEvaluate", pagingResult.getRows());
		dto.putValue("total", pagingResult.getTotal());
		return dto.toJsonString();
	}

	/**
	 * 
	 * 查看其他房东 对 特定用户评价   互评记录才能展示sql - 单独给房东端 M站使用 
	 *
	 * @author yd
	 * @created 2016年4月7日 下午2:41:37
	 *
	 *
	 * @param evaluateRequest
	 * @return
	 */
	@Override
	public String queryOtherLanEvaByPage(String evaluateRequest){

		//返回对象
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(evaluateRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("evaluateRequest is null");
			return dto.toJsonString();
		}

		EvaluateRequest evaRequest = JsonEntityTransform.json2Object(evaluateRequest, EvaluateRequest.class);
		LogUtil.info(LOGGER,"evaluateRequest to toStirng {}",evaRequest.toString());

		if(Check.NuNStr(evaRequest.getEvaUserUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("eva_user_uid is null");
			return dto.toJsonString();
		}

		/*		evaRequest.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		evaRequest.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
		evaRequest.setIsDel(0);*/

		PagingResult<LandlordEvaluateVo> pagingResult = this.evaluateOrderServiceImpl.queryOtherLanEvaByPage(evaRequest);

		dto.putValue("listLandlordEvaluate", pagingResult.getRows());
		dto.putValue("total", pagingResult.getTotal());
		return dto.toJsonString();
	}


	/**
	 * 
	 * 按类型eva_user_type 分页查询该类型下所有的评价信息 此接口给后台评价管理列表使用
	 * 
	 * @author yd
	 * @created 2016年4月7日 下午8:44:50
	 *
	 * @param evaluateRequest
	 * @return
	 */
	@Override
	public String queryEvaluateByPage(String evaluateRequest) {
		//返回对象
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(evaluateRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("evaluateRequest is null");
			return dto.toJsonString();
		}

		EvaluateRequest evaRequest = JsonEntityTransform.json2Object(evaluateRequest, EvaluateRequest.class);

		if(Check.NuNObj(evaRequest.getEvaUserType())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("evaUserType is null , this param is 1 or 2");
			return dto.toJsonString();
		}
		LogUtil.info(LOGGER, "evaluateRequest to toStirng {}", evaRequest.toString());

		int evaUserType = evaRequest.getEvaUserType();

		if(evaUserType == 1){
			PagingResult<LandlordEvaluateVo> pagingResult = this.evaluateOrderServiceImpl.queryLandlordEvaluateByPage(evaRequest);
			dto.putValue("listLandlordEvaluate", pagingResult.getRows());
			dto.putValue("total", pagingResult.getTotal());
		}

		if(evaUserType == 2){
			PagingResult<TenantEvaluateVo> pagingResult = this.evaluateOrderServiceImpl.queryTenantEvaluateByPage(evaRequest);
			dto.putValue("listTenantEvaluateVo", pagingResult.getRows());
			dto.putValue("total", pagingResult.getTotal());
		}

		return dto.toJsonString();
	}

	/**
	 * 
	 * 条件分页查询所有房客的审核通过的评价 
	 *
	 * 说明：必须为房客的评价 eva_user_type = 2;  is_del  未被删除； 状态必须审核成功  eva_statu = 3 ；
	 * @author yd
	 * @created 2016年4月7日 下午8:46:23
	 *
	 * @param evaluateRequest
	 * @return
	 */
	@Override
	public String queryTenantEvaluateByPage(String evaluateRequest) {
		//返回对象
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(evaluateRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("evaluateRequest is null");
			return dto.toJsonString();
		}

		EvaluateRequest evaRequest = JsonEntityTransform.json2Object(evaluateRequest, EvaluateRequest.class);
		evaRequest.setEvaUserType(UserTypeEnum.TENANT.getUserType());
		evaRequest.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		evaRequest.setIsShow(YesOrNoEnum.YES.getCode());
		LogUtil.info(LOGGER,"evaluateRequest to toStirng {}",evaRequest.toString());
		PagingResult<TenantEvaluateVo> pagingResult = this.evaluateOrderServiceImpl.queryTenantEvaluateByPage(evaRequest);
		dto.putValue("listTenantEvaluateVo", pagingResult.getRows());
		dto.putValue("total", pagingResult.getTotal());
		return dto.toJsonString();
	}

	/**
	 * 
	 * 添加房客评论 
	 * 说明：先添加EvaluateOrder  在添加TenantEvaluate 在一个事物中
	 *
	 * @author yd
	 * @created 2016年4月7日 下午8:49:38
	 *
	 * @param tenantEvaluate
	 * @return
	 */
	@Override
	public String saveTenantEvaluate(String tenantEvaluate,String evaluateOrder,String orderEntity) {

		//返回对象
		DataTransferObject dto = new DataTransferObject();
		TenantEvaluateEntity tenant = JsonEntityTransform.json2Object(tenantEvaluate, TenantEvaluateEntity.class);
		EvaluateOrderEntity evaluateOrderEntity =  JsonEntityTransform.json2Object(evaluateOrder, EvaluateOrderEntity.class);

		OrderEntity order= JsonEntityTransform.json2Object(orderEntity, OrderEntity.class);
		if(Check.NuNObj(tenant)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("tenantEvaluate is null");
			return dto.toJsonString();
		}

		if(Check.NuNObj(evaluateOrderEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("evaluateOrder is null");
			return dto.toJsonString();
		}


		if(!Check.NuNObj(tenant)&&!Check.NuNObj(evaluateOrderEntity)&&!Check.NuNStr(evaluateOrderEntity.getHouseFid())&&!Check.NuNObj(evaluateOrderEntity.getRentWay())){
			//校验参数
			if(Check.NuNObj(evaluateOrderEntity)||Check.NuNStr(evaluateOrderEntity.getOrderSn())
					||Check.NuNStr(evaluateOrderEntity.getRatedUserUid())
					||Check.NuNObj(evaluateOrderEntity.getHouseFid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数不对");
				return dto.toJsonString();
			}

			//校验是否存在
			EvaluateRequest evaluateRequest = new EvaluateRequest();
			evaluateRequest.setOrderSn(evaluateOrderEntity.getOrderSn());
			evaluateRequest.setEvaUserType(UserTypeEnum.TENANT.getUserType());
			evaluateRequest.setEvaUserUid(evaluateOrderEntity.getEvaUserUid());
			evaluateRequest.setRatedUserUid(evaluateOrderEntity.getRatedUserUid());
			Map<String, Object> map = this.evaluateOrderServiceImpl.queryEvaluateByOrderSn(evaluateRequest);

			if(Check.NuNMap(map)||Check.NuNObj(map.get("tenantEvaluate"))){
				evaluateOrderEntity.setOrderEvaFlag(OrderEvaFlagEnum.ORDER_NOT_HAVE_EVA.getCode());
				int count = tenantEvaluateServiceImpl.saveTenantEvaluate(tenant, evaluateOrderEntity);
				dto.putValue("result", count);
				//添加统计信息
				//this.statsEvaluateManage(evaluateOrderEntity, null, dto,order);
				if (evaluateOrderEntity.getEvaStatu() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
					//房客评价 房东收到通知
					this.evaluateMsgProxy.sendMsgToLanAndTenFinish(order);
				}
				// 保存评价成功则记录用户行为
				if (count == 1) {
					saveEvaluateBehavior(tenant, evaluateOrderEntity);
				}
			}else{
				LogUtil.info(LOGGER,"房客评价已经存在");
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("不能重复评价");
			}

		}else{
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("tenantEvaluateEntity  or  evaluateOrderEntity or rentWay  or houseFid is null");
		}

		return dto.toJsonString();
	}

	/**
	 * 
	 * 添加房东评论 
	 * 说明：先添加EvaluateOrder  在添加LandlordEvaluate 在一个事物中
	 *
	 * @author yd
	 * @created 2016年4月7日 下午8:54:09
	 *
	 * @param landlordEvaluate
	 * @return
	 */
	@Override
	public String saveLandlordEvaluate(String landlordEvaluate,String evaluateOrder,String orderEntity) {
		//返回对象
		DataTransferObject dto = new DataTransferObject();

		LandlordEvaluateEntity landlord = JsonEntityTransform.json2Object(landlordEvaluate, LandlordEvaluateEntity.class);
		EvaluateOrderEntity evaluateOrderEntity =  JsonEntityTransform.json2Object(evaluateOrder, EvaluateOrderEntity.class);
		OrderEntity order = JsonEntityTransform.json2Object(orderEntity, OrderEntity.class);
		if(Check.NuNObj(landlord)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("landlordEvaluate is null");
			return dto.toJsonString();
		}

		if(Check.NuNObj(evaluateOrderEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("evaluateOrder is null");
			return dto.toJsonString();
		}


		if(!Check.NuNObj(landlord)&&!Check.NuNObj(evaluateOrderEntity)&&!Check.NuNStr(evaluateOrderEntity.getHouseFid())&&!Check.NuNObj(evaluateOrderEntity.getRentWay())){

			//校验是否存在
			EvaluateRequest evaluateRequest = new EvaluateRequest();
			evaluateRequest.setOrderSn(evaluateOrderEntity.getOrderSn());
			evaluateRequest.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
			evaluateRequest.setEvaUserUid(evaluateOrderEntity.getEvaUserUid());
			evaluateRequest.setRatedUserUid(evaluateOrderEntity.getRatedUserUid());
			Map<String, Object> map = this.evaluateOrderServiceImpl.queryEvaluateByOrderSn(evaluateRequest);

			if(Check.NuNMap(map)||Check.NuNObj(map.get("landlordEvaluate"))){
				evaluateOrderEntity.setOrderEvaFlag(OrderEvaFlagEnum.ORDER_NOT_HAVE_EVA.getCode());
				dto.putValue("result", this.landlordEvaluateServiceImpl.saveLandlordEvaluate(landlord, evaluateOrderEntity));

				//this.statsEvaluateManage(evaluateOrderEntity, null, dto,order);
                //房东评价 房客收到通知
				if (evaluateOrderEntity.getEvaStatu() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
                	this.evaluateMsgProxy.sendMsgToTenAndLanFinish(order);
				}
			}else{
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("房东评价已经存在");
			}

		}else{
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("landlordEvaluateEntity or  evaluateOrderEntity  or rentWay  or houseFid is null");
		}

		return dto.toJsonString();
	}

	@Override
	public String saveLandlordReply(String landlordReply, String evaluateOrder) {
		//返回对象
		DataTransferObject dto = new DataTransferObject();

		LandlordReplyEntity landlordReplyEntity = JsonEntityTransform.json2Object(landlordReply, LandlordReplyEntity.class);
		EvaluateOrderEntity evaluateOrderEntity =  JsonEntityTransform.json2Object(evaluateOrder, EvaluateOrderEntity.class);
		if(Check.NuNObj(landlordReplyEntity) || Check.NuNObj(evaluateOrderEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}

		if(!Check.NuNObj(landlordReplyEntity)&&!Check.NuNObj(evaluateOrderEntity)&&!Check.NuNStr(evaluateOrderEntity.getHouseFid())&&!Check.NuNObj(evaluateOrderEntity.getRentWay())){
			//校验是否存在
			EvaluateRequest evaluateRequest = new EvaluateRequest();
			evaluateRequest.setOrderSn(evaluateOrderEntity.getOrderSn());
			evaluateRequest.setEvaUserType(EvaluateUserEnum.LAN_REPLY.getCode());
			Map<String, Object> map = this.evaluateOrderServiceImpl.queryEvaluateByOrderSn(evaluateRequest);

			if(Check.NuNMap(map)||Check.NuNObj(map.get("landlordReplyEntity"))){
				dto.putValue("result", this.landlordEvaluateServiceImpl.saveLandlordReply(landlordReplyEntity,evaluateOrderEntity));
			}else{
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("房东回复已经存在");
			}

		}else{
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("数据为空");
		}
		return dto.toJsonString();
	}


	/**
	 * 
	 * 根据fid修改EvaluateOrder 这里只能修改状态和最后修改时间
	 * 说明：后台操作审核接口  状态不能为null 也不能为待审核
	 * @author yd
	 * @created 2016年4月7日 下午8:55:56
	 *
	 * @param evaluateOrderRequest
	 * @return
	 */
	@Override
	public String updateEvaluateOrderByFid(String evaluateOrderRequest,String orderEntity) {

		//返回对象
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(evaluateOrderRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("evaluateOrder is null");
			return dto.toJsonString();
		}

		EvaluateOrderRequest evaluateOrder = JsonEntityTransform.json2Object(evaluateOrderRequest, EvaluateOrderRequest.class);
		OrderEntity order= JsonEntityTransform.json2Object(orderEntity, OrderEntity.class);
		if(Check.NuNStr(evaluateOrder.getFid())||Check.NuNObj(evaluateOrder.getEvaStatu())||evaluateOrder.getEvaStatu().intValue() == EvaluateStatuEnum.AUDIT.getEvaStatuCode()){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("fid is null or the evaStatu is error ");
			return dto.toJsonString();
		}

		if(Check.NuNStr(evaluateOrder.getCreateUid())){
			LogUtil.debug(LOGGER, "修改人createUid不存在，此次无法记录日志");
		}
		
		EvaluateOrderEntity evaluateOrderEntity = this.evaluateOrderServiceImpl.queryByFid(evaluateOrder.getFid());
		if(Check.NuNObj(evaluateOrderEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("根据fid={}，查询评价中间表 evaluateOrderEntity 不存在");
			return dto.toJsonString();
		}
		evaluateOrderEntity.setEvaStatu(evaluateOrder.getEvaStatu());
		//statsEvaluateManage(evaluateOrderEntity, null, dto,order);
		dto.putValue("result",this.evaluateOrderServiceImpl.updateEvaluateOrderByFid(evaluateOrder,evaluateOrderEntity));
		return dto.toJsonString();
	}

	/**
	 * 
	 * 根据eva_order_fid修改：LandlordEvaluate  只能修改部分信息 ，包括内容  最后修改时间  满意度 是否删除
	 *
	 * @author yd
	 * @created 2016年4月7日 下午8:57:53
	 *
	 * @param landlordEvaluate
	 * @return
	 */
	@Override
	public String updateLandlordEvaluate(String landlordEvaluate) {
		//返回对象
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(landlordEvaluate)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("landlordEvaluate is null");
			return dto.toJsonString();
		}

		LandlordEvaluateEntity landlordEvaluateEntity = JsonEntityTransform.json2Object(landlordEvaluate, LandlordEvaluateEntity.class);

		if(Check.NuNObj(landlordEvaluateEntity)||Check.NuNObj(landlordEvaluateEntity.getEvaOrderFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("LandlordEvaluateEntity or evaOrderFid is null");
			return dto.toJsonString();
		}
		dto.putValue("result", this.landlordEvaluateServiceImpl.updateByEvaOrderFid(landlordEvaluateEntity));
		return dto.toJsonString();
	}

	/**
	 * 
	 * 根据eva_order_fid修改：TenantEvaluate 只能修改部分信息 包括 内容  星级的相关项  最后修改时间 是否删除
	 *
	 * @author yd
	 * @created 2016年4月7日 下午9:00:24
	 *
	 * @param tenantEvaluate
	 * @return
	 */
	@Override
	public String updateTenantEvaluate(String tenantEvaluate) {
		//返回对象
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(tenantEvaluate)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("tenantEvaluate is null");
			return dto.toJsonString();
		}

		TenantEvaluateEntity tenantEvaluateEntity = JsonEntityTransform.json2Object(tenantEvaluate, TenantEvaluateEntity.class);

		if(Check.NuNObj(tenantEvaluateEntity)||Check.NuNObj(tenantEvaluateEntity.getEvaOrderFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("TenantEvaluateEntity or evaOrderFid is null");
			return dto.toJsonString();
		}
		dto.putValue("result", this.tenantEvaluateServiceImpl.updateByEvaOrderFid(tenantEvaluateEntity));
		return dto.toJsonString();
	}


	/**
	 * @see com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService#queryStatsHouseEvaByCondition(java.lang.String)
	 * 条件查询统计房源信息
	 * 
	 * 说明：
	 * 业务要求：房客对房源的评价，如果当前房源 没有任何房客的评价，默认展示5颗星，评论数不存在 （注： 此要求后期有可能被干掉）
	 *
	 * @author yd
	 * @created 2016年4月9日 下午10:58:17
	 *
	 * @param statsHouseEvaRequest
	 */
	@Override
	public String queryStatsHouseEvaByCondition(String statsHouseEvaRequest) {

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(statsHouseEvaRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("statsHouseEvaRequest is null");
			return dto.toJsonString();
		}

		StatsHouseEvaRequest staRequest = JsonEntityTransform.json2Object(statsHouseEvaRequest, StatsHouseEvaRequest.class);

		List<StatsHouseEvaEntity> listStatsHouseEvaEntities = this.statsHouseEvaServiceImpl.queryByCondition(staRequest);
		
		//默认展示5课星业务
		if(Check.NuNCollection(listStatsHouseEvaEntities)){
			StatsHouseEvaEntity statsHouseEvaEntity = new StatsHouseEvaEntity();
			statsHouseEvaEntity.setBedFid(staRequest.getBedFid());
			statsHouseEvaEntity.setHouseFid(staRequest.getHouseFid());
			statsHouseEvaEntity.setRoomFid(staRequest.getRoomFid());
			statsHouseEvaEntity.setRentWay(staRequest.getRentWay());
			statsHouseEvaEntity.setEvaTotal(0);
			statsHouseEvaEntity.setCostPerforAva(EvaluateConsant.EVA_START_FIVE);
			statsHouseEvaEntity.setDesMatchAva(EvaluateConsant.EVA_START_FIVE);
			statsHouseEvaEntity.setHouseCleanAva(EvaluateConsant.EVA_START_FIVE);
			statsHouseEvaEntity.setSafeDegreeAva(EvaluateConsant.EVA_START_FIVE);
			statsHouseEvaEntity.setTrafPosAva(EvaluateConsant.EVA_START_FIVE);
			listStatsHouseEvaEntities.add(statsHouseEvaEntity);
		}

		dto.putValue("listStatsHouseEvaEntities", listStatsHouseEvaEntities);

		return dto.toJsonString();

	}
	
	

	/**
	 *  @see com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService#queryStatsTenantEvaByCondion(java.lang.String)
	 * 条件查询统计房客信息
	 *
	 * @author yd
	 * @created 2016年4月9日 下午11:22:07
	 *
	 * @param statsTenantEvaRequest
	 * @return
	 */
	@Override
	public String queryStatsTenantEvaByCondion(String statsTenantEvaRequest) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(statsTenantEvaRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("statsTenantEvaRequest is null");
			return dto.toJsonString();
		}

		StatsTenantEvaRequest statsEvaRequest = JsonEntityTransform.json2Object(statsTenantEvaRequest, StatsTenantEvaRequest.class);

		List<StatsTenantEvaEntity> lisTenantEvaEntities = this.statsTenantEvaServiceImpl.queryByCondition(statsEvaRequest);
		dto.putValue("lisTenantEvaEntities", lisTenantEvaEntities);
		return dto.toJsonString();
	}


	/**
	 * 根据订单编号查询 房东对房客的评价 以及该订单 房客对房东的评价
	 * @author yd
	 * @cteate 2016-04-11
	 * @param paramJson 订单编号
	 * @return 
	 */
	@Override
	public String queryEvaluateByOrderSn(String paramJson) {
		EvaluateRequest evaluateRequest = JsonEntityTransform.json2Object(paramJson, EvaluateRequest.class);

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(evaluateRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("evaluateRequest is null");
			return dto.toJsonString();
		}

		if(Check.NuNStr(evaluateRequest.getOrderSn())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("orderSn is null");
			return dto.toJsonString();
		}

		try {
			dto.putValue("evaluateMap", this.evaluateOrderServiceImpl.queryEvaluateByOrderSn(evaluateRequest));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}

		return dto.toJsonString();
	}

	/**
	 * 
	 * 查询评价公共信息(后台使用)
	 *
	 * @author yd
	 * @created 2016年4月11日 下午2:35:48
	 *
	 * @param evaluateRequest
	 * @return
	 */
	@Override
	public String queryAllEvaluateByPage(String evaluateRequest){

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(evaluateRequest)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("evaluateRequest is null");
			return dto.toJsonString();
		}
		EvaluateRequest evaRequest = JsonEntityTransform.json2Object(evaluateRequest, EvaluateRequest.class);

		PagingResult<EvaluateVo> pagingResult = this.evaluateOrderServiceImpl.queryAllEvaluateByPage(evaRequest);

		dto.putValue("lsitEvaluateVo", pagingResult.getRows());
		dto.putValue("total", pagingResult.getTotal());
		return dto.toJsonString();

	}


	/**
	 * 
	 * 按条件修改评价订单关系实体
	 *
	 * @author yd
	 * @created 2016年4月12日 上午11:54:34
	 *
	 * @param evaluateOrder
	 * @return
	 */
	@Override
	public String updateEvaluateOrderByCondition(String evaluateOrder) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(evaluateOrder)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("evaluateOrder is null");
			return dto.toJsonString();
		}
		EvaluateOrderEntity evaluateOrderEntity = JsonEntityTransform.json2Object(evaluateOrder, EvaluateOrderEntity.class);


		if(Check.NuNStr(evaluateOrderEntity.getOrderSn())||Check.NuNStr(evaluateOrderEntity.getFid())||Check.NuNObj(evaluateOrderEntity.getEvaUserType())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("orderSn or fid or evaUserType is null");
			return dto.toJsonString();
		}
		dto.putValue("result", this.evaluateOrderServiceImpl.updateBycondition(evaluateOrderEntity));
		return dto.toJsonString();
	}




	/**
	 * 获取前3个月评价未同步的列表
	 * 说明：1.不区分 评价状态  2.标示为订单未评价  3.3个月前的评价（防止时间过长 数据量太大）
	 * @return
	 */
	public String getEvaluteList(){
		DataTransferObject dto = new DataTransferObject();
		EvaluateRequest evaluateRequest = new EvaluateRequest();
		evaluateRequest.setOrderEvaFlag(OrderEvaFlagEnum.ORDER_NOT_HAVE_EVA.getCode());
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(new Date());//把当前时间赋给日历
		calendar.add(Calendar.MONTH, -3); //设置为前3月
		evaluateRequest.setStartTime(DateUtil.dateFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
		List<EvaluateOrderEntity> listOrderEntities = this.evaluateOrderServiceImpl.queryByEvaluateRe(evaluateRequest);
		dto.putValue("list",listOrderEntities);
		return dto.toJsonString();
	}


	/**
	 * 更新已经同步的订单状态
	 * @author afi
	 * @created 2016年11月12日 下午6:46:12
	 *
	 * @param listFid
	 */
	public void updateSynOrderEvaFlagByFid(List<String> listFid){
		evaluateOrderServiceImpl.updateSynOrderEvaFlagByFid(listFid);
	}
	/**
	 * 
	 * 根据订单号取修改OrderEvaflag 为1
	 *
	 * @author yd
	 * @created 2016年4月12日 下午6:46:12
	 *
	 * @param listOrderSn
	 * @param orderEvaflag
	 */
	public void updateOrderEvaFlag(List<String> listOrderSn ,int orderEvaflag,Integer evaUserType){
		if(!Check.NuNCollection(listOrderSn)&&orderEvaflag == OrderEvaFlagEnum.ORDE_HAVE_EVA.getCode()){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("listOrderSn", listOrderSn);
			params.put("orderEvaFlag", orderEvaflag);
			params.put("evaUserType", evaUserType);

			LogUtil.info(LOGGER, "当前修改评价参数params={}", params);
			this.evaluateOrderServiceImpl.updateOrderEvaFlag(params);
		}
	}


	/**
	 * 
	 * 评价上线的定时任务(半小时 扫秒一次  每次扫描  往前推一个小时状态为待审核的数据 )
	 * 
	 * 说明：扫描t_evaluate_order表， 创建时间 往前推hours小时，修改状态为 已发布
	 *
	 * 实现算法：先查询 再按 fid集合更新（查询条件： 创建时间 半小时之前的数据 ） 数据量是 半小时之前一周的数据
	 * @author yd
	 * @created 2016年5月3日 下午8:51:05
	 *
	 * @param hours 超时时间(即评价创建时间 超过这个时间  就上线) 单位 ：小时
	 * @return
	 */
	@Override
	public String evaluateOnline(String hours) {

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(hours)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("超时时间设置为null");
			return dto.toJsonString();
		}
		try {
			List<EvaluateOrderEntity> listEvaluateOrder = new ArrayList<EvaluateOrderEntity>();//需要推送的订单
			int hou = Integer.valueOf(hours);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE,-hou);
			EvaluateRequest evaluateRequest = new EvaluateRequest();
			evaluateRequest.setEndTime(DateUtil.dateFormat(calendar.getTime(),"yyyy-MM-dd HH:mm:ss"));
			evaluateRequest.setStartTime(DateUtil.dateFormat(DateSplitUtil.jumpDate(calendar.getTime(), -7),"yyyy-MM-dd HH:mm:ss"));
			evaluateRequest.setEvaStatu(EvaluateStatuEnum.AUDIT.getEvaStatuCode());
			List<EvaluateOrderEntity> listEvaluateOrderEntities = evaluateOrderServiceImpl.queryByEvaluateRe(evaluateRequest);//
			List<String> listFid = new ArrayList<String>();
			if(!Check.NuNCollection(listEvaluateOrderEntities)){
				for (EvaluateOrderEntity evaluateOrder : listEvaluateOrderEntities) {
					evaluateOrder.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
					listFid.add(evaluateOrder.getFid());
					listEvaluateOrder.add(evaluateOrder);
					/*if(evaluateOrder.getEvaStatu().intValue() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
						if(evaluateOrder.getEvaUserType().intValue() == UserTypeEnum.LANDLORD.getUserType()){
							LandlordEvaluateEntity  landlordEvaluate =  this.landlordEvaluateServiceImpl.queryByEvaOrderFid(evaluateOrder.getFid());
							this.landlordEvaluateServiceImpl.statsEvaluateManage(landlordEvaluate, evaluateOrder,null );
						}
						if(evaluateOrder.getEvaUserType().intValue() == UserTypeEnum.TENANT.getUserType()){
							TenantEvaluateEntity tenantEvaluate = this.tenantEvaluateServiceImpl.queryByEvaOrderFid(evaluateOrder.getFid());
							this.tenantEvaluateServiceImpl.statsEvaluateManage(tenantEvaluate, evaluateOrder,null);
						}
					}*/
				}
			}
			if(!Check.NuNCollection(listFid)){
				EvaluateRequest evaluateR = new EvaluateRequest();
				evaluateR.setListFid(listFid);
				int index = this.evaluateOrderServiceImpl.updateByEvaluateRe(evaluateR);
				dto.putValue("result", index);
			}
			dto.putValue("listEvaluateOrder", listEvaluateOrder);

		} catch (Exception e) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("定时发布评价失败");
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 
	 * 1.统计信息 
	 * 2.极光推送
	 *
	 * @author yd
	 * @created 2016年5月20日 上午7:11:59
	 *
	 * @param evaluateOrder
	 * @param orderEntity
	 * @return
	 */
	@Override
	public String statsEvaluateMessage(String evaluateOrder,String orderEntity){
		
		DataTransferObject dto = new DataTransferObject();
		
		
		EvaluateOrderEntity evaluateOr = JsonEntityTransform.json2Object(evaluateOrder, EvaluateOrderEntity.class);
		OrderEntity order= JsonEntityTransform.json2Object(orderEntity, OrderEntity.class);
		if(Check.NuNObj(evaluateOr)||Check.NuNObj(orderEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		statsEvaluateManage(evaluateOr, null, dto, order);
		return dto.toJsonString();
	}
	/**
	 * 
	 * 1.评价统计信息
     * 去掉 发送推送逻辑
	 * 
	 *
	 * @author yd
	 * @created 2016年5月3日 下午10:13:29
	 *
	 * @param evaluateOrder
	 */
	private DataTransferObject statsEvaluateManage(EvaluateOrderEntity evaluateOrder,List<String> listFid,DataTransferObject dto,OrderEntity orderEntity){

		if(!Check.NuNObj(evaluateOrder)&&!Check.NuNObj(evaluateOrder.getEvaStatu())&&evaluateOrder.getEvaStatu().intValue() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
			LogUtil.info(LOGGER, "定时任务评价上线开始，当前待更新实体evaluateOrder={}", evaluateOrder.toJsonStr());
			evaluateOrder.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
			if(listFid != null) listFid.add(evaluateOrder.getFid());

			if(Check.NuNObj(evaluateOrder)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("根据fid={}，查询评价中间表 evaluateOrderEntity 不存在");
				return dto;
			}

			if(Check.NuNObj(orderEntity)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("当前评价的订单已不存在");
				return dto;
			}
			/*Map<String, String> tenParamsMap = new HashMap<String, String>();//房客评价 极光推送map
			Map<String, String> lanParamsMap = new HashMap<String, String>();//房东评价 极光推送map
			String smsCode ="";

			if(!Check.NuNObj(evaluateOrder.getEvaUserType())&&evaluateOrder.getEvaUserType().intValue() == UserTypeEnum.TENANT.getUserType()){
				smsCode = String.valueOf(MessageTemplateCodeEnum.TENANT_EVALUATE.getCode());
				tenParamsMap.put("{1}", orderEntity.getUserName());
			}
			if(!Check.NuNObj(evaluateOrder.getEvaUserType())&&evaluateOrder.getEvaUserType().intValue() == UserTypeEnum.LANDLORD.getUserType()){
				smsCode = String.valueOf(MessageTemplateCodeEnum.LANLORD_EVALUATE.getCode());
				lanParamsMap.put("{1}", orderEntity.getLandlordName());
				lanParamsMap.put("{2}", orderEntity.getLandlordName());
			}

			dto = JsonEntityTransform.json2DataTransferObject(this.smsTemplateService.findEntityBySmsCode(JsonEntityTransform.Object2Json(smsCode)));
			SmsTemplateEntity smsTemplateEntity = null;
			if(dto.getCode() == 0){
				smsTemplateEntity = dto.parseData("smsTemplateEntity", new TypeReference<SmsTemplateEntity>() {
				});
			}
			if(!Check.NuNObj(smsTemplateEntity)){
				tenParamsMap.put("content", smsTemplateEntity.getSmsComment());
				tenParamsMap.put("title", smsTemplateEntity.getSmsName());

				lanParamsMap.put("content", smsTemplateEntity.getSmsComment());
				lanParamsMap.put("title", smsTemplateEntity.getSmsName());
				lanParamsMap.put("mobile", orderEntity.getUserTel());
			}*/

			if(evaluateOrder.getEvaStatu().intValue() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
				if(evaluateOrder.getEvaUserType().intValue() == UserTypeEnum.LANDLORD.getUserType()){
					LandlordEvaluateEntity  landlordEvaluate =  this.landlordEvaluateServiceImpl.queryByEvaOrderFid(evaluateOrder.getFid());
					this.landlordEvaluateServiceImpl.statsEvaluateManage(landlordEvaluate, evaluateOrder,null );
				}
				if(evaluateOrder.getEvaUserType().intValue() == UserTypeEnum.TENANT.getUserType()){
					TenantEvaluateEntity tenantEvaluate = this.tenantEvaluateServiceImpl.queryByEvaOrderFid(evaluateOrder.getFid());
					this.tenantEvaluateServiceImpl.statsEvaluateManage(tenantEvaluate, evaluateOrder,null);

				}
			}
		}


		return dto;
	}

	/**
	 * PC端房源详情显示评价相关信息
	 */
	@Override
	public String houseDetailEvaInfo(String param) {
		DataTransferObject dto = new DataTransferObject();
		EvaluatePCRequest request = JSONObject.parseObject(param, EvaluatePCRequest.class);
		if(Check.NuNObj(request)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		if(Check.NuNObj(request.getRentWay())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("出租方式为空");
			return dto.toJsonString();
		}
		if(request.getRentWay() == RentWayEnum.HOUSE.getCode()){
			if(Check.NuNObj(request.getHouseFid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("房源FID为空");
				return dto.toJsonString();
			}
		}
		if(request.getRentWay() == RentWayEnum.ROOM.getCode()){
			if(Check.NuNStr(request.getRoomFid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("房间FID为空");
				return dto.toJsonString();
			}
		}
		EvaluateHouseDetailVo evaHouseVo = new EvaluateHouseDetailVo();
		long houseCount = evaluateOrderServiceImpl.countHouseEva(request);
		long lanCount = evaluateOrderServiceImpl.countLanEva(request);
		evaHouseVo.setHouseEvaNum(houseCount);
		evaHouseVo.setLanEvaNum(lanCount);
		StatsHouseEvaRequest statsEvaRequest = new StatsHouseEvaRequest();
		statsEvaRequest.setHouseFid(request.getHouseFid());
		statsEvaRequest.setRoomFid(request.getRoomFid());
		statsEvaRequest.setRentWay(request.getRentWay());
		List<StatsHouseEvaEntity> statsList = statsHouseEvaServiceImpl.queryByCondition(statsEvaRequest);
		if(!Check.NuNCollection(statsList) && statsList.size() > 0){
			StatsHouseEvaEntity statsEvaEntity = statsList.get(0);
			Float costPerforAva = statsEvaEntity.getCostPerforAva();
			Float perforAva = statsEvaEntity.getCostPerforAva();
			Float houseCleanAva = statsEvaEntity.getHouseCleanAva();
			Float safeDegreeAva = statsEvaEntity.getSafeDegreeAva();
			Float trafPosAva = statsEvaEntity.getTrafPosAva();
			evaHouseVo.setCostPerforAva(costPerforAva);
			evaHouseVo.setDesMatchAva(perforAva);
			evaHouseVo.setHouseCleanAva(houseCleanAva);
			evaHouseVo.setSafeDegreeAva(safeDegreeAva);;
			evaHouseVo.setTrafPosAva(trafPosAva);
			Float allAva = costPerforAva + perforAva + houseCleanAva + safeDegreeAva + trafPosAva;
			evaHouseVo.setAvgAll((float)BigDecimalUtil.div(allAva,5.0,1));
		}
		dto.putValue("evaHouseVo", evaHouseVo);
		
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService#queryHouseDetailEvaPage(java.lang.String)
	 */
	@Override
	public String queryHouseDetailEvaPage(String param) {
		DataTransferObject dto = new DataTransferObject();
		EvaluatePCRequest request = JSONObject.parseObject(param, EvaluatePCRequest.class);
		
		Integer rentWay = request.getRentWay();
		if(Check.NuNObj(rentWay)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("出租方式为空");
			return dto.toJsonString();
		}
		
		if(rentWay == RentWayEnum.ROOM.getCode()){
			if(Check.NuNStr(request.getRoomFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间Fid为空");
				return dto.toJsonString();
			}
		}
		if(rentWay == RentWayEnum.HOUSE.getCode()){
			if(Check.NuNStr(request.getHouseFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源Fid为空");
				return dto.toJsonString();
			}
		}
		
		PagingResult<EvaluateBothItemVo> detailResult = evaluateOrderServiceImpl.queryHouseDetailEvaPage(request);
		long total = detailResult.getTotal();
		List<EvaluateBothItemVo> evaList = detailResult.getRows();
		dto.putValue("total", total);
		dto.putValue("evaList", evaList);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService#queryLanEvaPage(java.lang.String)
	 */
	@Override
	public String queryLanEvaPage(String param) {
		DataTransferObject dto = new DataTransferObject();
		EvaluatePCRequest request = JSONObject.parseObject(param, EvaluatePCRequest.class);
		if(Check.NuNStr(request.getLandlordUid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房东UID不能为空");
			return dto.toJsonString();
		}
		PagingResult<EvaluateBothItemVo> lanEva = evaluateOrderServiceImpl.queryLanEvaPage(request);
		long total = lanEva.getTotal();
		List<EvaluateBothItemVo> lanEvaList = lanEva.getRows();
		dto.putValue("total", total);
		dto.putValue("lanEvaList", lanEvaList);
		return dto.toJsonString();
	}
	
	
	/**
	 * 根据房东uid查询房东的所有评分的平均值
	 *
	 * @author zl
	 * @created 2016年9月18日 
	 * 
	 * @param landlordUid
	 * @return
	 */
	public String selectByAVEScoreByUid(String landlordUid){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(landlordUid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房东UID不能为空");
			return dto.toJsonString();
		}
		
		Float score =statsHouseEvaServiceImpl.selectByAVEScoreByUid(landlordUid);
		dto.putValue("aveScore", score);
		return dto.toJsonString();
	}








	@Override
	public String countEvaNum(String param) {
		DataTransferObject dto = new DataTransferObject();
		EvaluateRequest evaluateRequest = JsonEntityTransform.json2Object(param, EvaluateRequest.class);
		if (Check.NuNStr(evaluateRequest.getRatedUserUid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("UID为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(evaluateRequest.getEvaUserType())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("评价人类型为空");
			return dto.toJsonString();
		}
		long count = evaluateOrderServiceImpl.countEvaNum(evaluateRequest);
		dto.putValue("count",count);
		return dto.toJsonString();
	}

	@Override
	public String countTenToHouseEvaNum(String param) {
		DataTransferObject dto = new DataTransferObject();
		EvaluateRequest evaluateRequest = JsonEntityTransform.json2Object(param, EvaluateRequest.class);
		Integer rentWay = evaluateRequest.getRentWay();
		if (Check.NuNObj(rentWay)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("出租类型为空");
			return dto.toJsonString();
		}
		if (rentWay == RentWayEnum.HOUSE.getCode() && Check.NuNStr(evaluateRequest.getHouseFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源fid为空");
			return dto.toJsonString();
		}
		if (rentWay == RentWayEnum.ROOM.getCode() && Check.NuNStr(evaluateRequest.getRoomFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间fid为空");
			return dto.toJsonString();
		}
		long count = evaluateOrderServiceImpl.countTenToHouseEvaNum(evaluateRequest);
		dto.putValue("count",count);
		return dto.toJsonString();
	}


	@Override
	public void checkInNoticeTenantEva(String param) {
		List<OrderInfoVo> orderInfoVos = JsonEntityTransform.json2List(param, OrderInfoVo.class);
		if (Check.NuNCollection(orderInfoVos)){
			return;
		}
		evaluateMsgProxy.sendMsgToTenCheckIn(orderInfoVos);
	}

	@Override
	public void orderFinish2DayNoticeEva(String param) {
		if (Check.NuNStr(param)){
			return;
		}
		List<OrderInfoVo> orderInfoVos = JsonEntityTransform.json2List(param, OrderInfoVo.class);
		if (Check.NuNCollection(orderInfoVos)){
			return;
		}
		evaluateMsgProxy.semdMsg2DayNoticeEva(orderInfoVos);
	}

	@Override
	public String listEvaluateUncheckShow(String param) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(param)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		EvaluateRequest evaluateRequest = JsonEntityTransform.json2Object(param, EvaluateRequest.class);
		if (Check.NuNObj(evaluateRequest.getPage()) || Check.NuNObj(evaluateRequest.getLimit())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("分页信息为空");
			return dto.toJsonString();
		}
		PagingResult<EvaluateOrderEntity> pageResult = evaluateOrderServiceImpl.listEvaluateUncheckShow(evaluateRequest);
		List<EvaluateOrderEntity> rows = pageResult.getRows();
		long total = pageResult.getTotal();
		dto.putValue("list",rows);
		dto.putValue("total",total);
		return dto.toJsonString();
	}

	@Override
	public String saveEvaluateShow(String param) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(param)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		EvaluateShowEntity evaluateShowEntity = JsonEntityTransform.json2Object(param, EvaluateShowEntity.class);
		if (Check.NuNStr(evaluateShowEntity.getEvaOrderFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("fid为空");
			return dto.toJsonString();
		}
		long count= evaluateOrderServiceImpl.saveEvaluateShow(evaluateShowEntity);
		dto.putValue("count",count);
		return dto.toJsonString();
	}

	@Override
	public String listAllEvaOrderByCondition(String param) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(param)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		EvaluateRequest request = JsonEntityTransform.json2Object(param, EvaluateRequest.class);

		List<EvaluateOrderEntity> evaluateOrderEntities = evaluateOrderServiceImpl.listAllEvaOrderByCondition(request);
		dto.putValue("list",evaluateOrderEntities);
		return dto.toJsonString();
	}

	@Override
	public String updateShowAndStatEva(String list, String entity, String evaType) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(list) || Check.NuNStr(entity) || Check.NuNStr(evaType)){
			dto.setMsg("参数为空");
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
		}
		List<EvaluateOrderEntity> evaList = JsonEntityTransform.json2ObjectList(list, EvaluateOrderEntity.class);
		EvaluateOrderEntity evaluateOrderEntity = JsonEntityTransform.json2Object(entity, EvaluateOrderEntity.class);
		if (Check.NuNObj(evaluateOrderEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("显示评价状态为空");
			return dto.toJsonString();
		}
		int i = 0;

		if (Integer.parseInt(evaType) == UserTypeEnum.TENANT.getUserCode()){
			i = statsHouseEvaServiceImpl.updateShowAndHouseStatEva(evaList,evaluateOrderEntity);
		}
		if (Integer.parseInt(evaType) == UserTypeEnum.LANDLORD.getUserCode()){
			i = statsTenantEvaServiceImpl.updateShowAndTenantStatEva(evaList,evaluateOrderEntity);
		}
		dto.putValue("count",i);

		return dto.toJsonString();
	}

	@Override
	public void sendOnlineEvaMsg(String order, String evaType) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(order)){
			return;
		}
		if (Check.NuNStr(evaType)){
			return;
		}
		OrderEntity orderEntity = JsonEntityTransform.json2Object(order, OrderEntity.class);
		if (Check.NuNObj(orderEntity)){
			return;
		}
		if (Integer.parseInt(evaType) == UserTypeEnum.LANDLORD.getUserCode()){
			evaluateMsgProxy.sendMsgToTenAndLanFinish(orderEntity);
		}
		if (Integer.parseInt(evaType) == UserTypeEnum.TENANT.getUserCode()){
			evaluateMsgProxy.sendMsgToLanAndTenFinish(orderEntity);
		}
	}

	@Override
	public String findEvaReplyByOrderSn(String orderSn) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(orderSn)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("订单号为空");
			return dto.toJsonString();
		}
		LandlordReplyEntity landlordReplyEntity = evaluateOrderServiceImpl.findEvaReplyByOrderSn(orderSn);
		dto.putValue("landlordReply",landlordReplyEntity);
		return dto.toJsonString();
	}

	@Override
	public String delStatsData(String param) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(param)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		EvaluateOrderEntity evaluateOrderEntity = JsonEntityTransform.json2Object(param, EvaluateOrderEntity.class);
		int evaType = evaluateOrderEntity.getEvaUserType();
		int count = 0;
		if (evaType == EvaluateUserEnum.TEN.getCode()){
			StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
			statsHouseEvaRequest.setHouseFid(evaluateOrderEntity.getHouseFid());
			statsHouseEvaRequest.setRoomFid(evaluateOrderEntity.getRoomFid());
			statsHouseEvaRequest.setRentWay(evaluateOrderEntity.getRentWay());
			List<StatsHouseEvaEntity> statsHouseEvaEntities = statsHouseEvaServiceImpl.queryByCondition(statsHouseEvaRequest);
			if (!Check.NuNCollection(statsHouseEvaEntities)){
				StatsHouseEvaEntity statsHouseEvaEntity = statsHouseEvaEntities.get(0);
				count = statsHouseEvaServiceImpl.delHouseStatByFid(statsHouseEvaEntity.getFid());
			}

		}
		if (evaType == EvaluateUserEnum.LAN.getCode()){
			StatsTenantEvaRequest statsTenantEvaRequest = new StatsTenantEvaRequest();
			statsTenantEvaRequest.setTenantUid(evaluateOrderEntity.getRatedUserUid());
			List<StatsTenantEvaEntity> statsTenantEvaEntities = statsTenantEvaServiceImpl.queryByCondition(statsTenantEvaRequest);
			if (!Check.NuNCollection(statsTenantEvaEntities)){
				StatsTenantEvaEntity statsTenantEvaEntity = statsTenantEvaEntities.get(0);
				count = statsTenantEvaServiceImpl.delTenantStatByFid(statsTenantEvaEntity.getFid());
			}
		}
		dto.putValue("count",count);

		return dto.toJsonString();
	}

	
	@Override
	public String saveSystemEval(String param) {
		DataTransferObject dto = new DataTransferObject();
		OrderInfoVo orderInfoVo = JsonEntityTransform.json2Object(param,OrderInfoVo.class);
	    if(Check.NuNObj(orderInfoVo)){
	    	 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
             dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
             return dto.toJsonString();
	    }
		EvaluateOrderEntity evaluateOrderEntity = new EvaluateOrderEntity();
		evaluateOrderEntity.setOrderSn(orderInfoVo.getOrderSn());
		evaluateOrderEntity.setEvaStatu(EvaluateStatuEnum.ONLINE.getEvaStatuCode());
		evaluateOrderEntity.setEvaUserUid(orderInfoVo.getUserUid());
		evaluateOrderEntity.setRatedUserUid(orderInfoVo.getLandlordUid());
		evaluateOrderEntity.setEvaUserType(EvaluateUserEnum.LAN_CANCEL_ORDER_SYSTEM_EVAL.getCode());
		evaluateOrderEntity.setRentWay(orderInfoVo.getRentWay());//房源快照
		evaluateOrderEntity.setHouseFid(orderInfoVo.getHouseFid());//房源快照
		evaluateOrderEntity.setRoomFid(orderInfoVo.getRoomFid());//房源快照
		evaluateOrderEntity.setOrderEvaFlag(OrderEvaFlagEnum.ORDE_HAVE_EVA.getCode());
		
		//返回对象
	    int saveEvaluateOrder = evaluateOrderServiceImpl.saveEvaluateOrder(evaluateOrderEntity);
	    if(saveEvaluateOrder==0){
	    	 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
             dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.FAIL_CODE));
             return dto.toJsonString();
	    }

		return dto.toJsonString();
	}

	@Override
	public String updateEvaluateAndStatsHouseEva(String statList, String showEntity, int count) {
		//返回对象
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(statList)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("statList is null");
			return dto.toJsonString();
		}

		if(Check.NuNStr(showEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("showEntity is null");
			return dto.toJsonString();
		}
		try {
			List<EvaluateOrderEntity> statusList = JsonEntityTransform.json2List(statList, EvaluateOrderEntity.class);
			EvaluateOrderEntity evaluateOrderEntity = JsonEntityTransform.json2Entity(showEntity, EvaluateOrderEntity.class);

			//重新计算房源统计评价表的平均分
			count = this.statsHouseEvaServiceImpl.houseEvaReCalculate(statusList, evaluateOrderEntity, count);
			if(count==0){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("更新房源统计评价表失败");
                return dto.toJsonString();
            }
            dto.putValue("result", count);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "更新评价，重新计算平均分异常， statList:{}, showEntity:{}, count:{}, e:{}", statList,
					showEntity, count, e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("更新评价，重新计算平均分异常");
		}
		return dto.toJsonString();
	}

	/**
	 * 重新计算房客评分
	 * @author wangwt
	 * @created 2017年08月04日 18:08:18
	 * @param
	 * @return
	 */
	@Override
	public String updateLandAndStatsTenantEva(String statList, String showEntity, int count) {
		//返回对象
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(statList)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("statList is null");
			return dto.toJsonString();
		}

		if(Check.NuNStr(showEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("showEntity is null");
			return dto.toJsonString();
		}
		try {
			List<EvaluateOrderEntity> statusList = JsonEntityTransform.json2List(statList, EvaluateOrderEntity.class);
			EvaluateOrderEntity evaluateOrderEntity = JsonEntityTransform.json2Entity(showEntity, EvaluateOrderEntity.class);

			//重新计算房客统计评价表的平均分
			count = this.statsTenantEvaServiceImpl.statusTenantReCalculate(statusList, evaluateOrderEntity, count);
			if(count==0){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("更新房客统计评价表失败");
				return dto.toJsonString();
			}
			dto.putValue("result", count);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "更新房客评价，重新计算平均分异常， statList:{}, showEntity:{}, count:{}, e:{}", statList,
					showEntity, count, e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("更新房客评价，重新计算平均分异常");
		}
		return dto.toJsonString();
	}

    /**
	 * 保存评价成功则记录用户行为
	 *
	 * @param
	 * @return
	 * @author zhangyl2
	 * @created 2017年10月11日 20:20
	 */
	private void saveEvaluateBehavior(TenantEvaluateEntity tenant, EvaluateOrderEntity evaluateOrderEntity) {
		int eva = 2;
		// 评价存在单一评价维度评分≤2分/条
		if (tenant.getHouseClean() <= eva
				|| tenant.getDescriptionMatch() <= eva
				|| tenant.getSafetyDegree() <= eva
				|| tenant.getTrafficPosition() <= eva
				|| tenant.getCostPerformance() <= eva) {

			threadPoolExecutor.execute(() -> {
				LandlordBehaviorEnum behaviorEnum = LandlordBehaviorEnum.ORDER_SINGLE_EVALUATE;

				String logPreStr = "[记录用户行为]" + behaviorEnum.getDesc();

				try {
					LogUtil.info(LOGGER, logPreStr + "-tenant={}", tenant);

					CustomerBehaviorEntity customerBehaviorEntity = new CustomerBehaviorEntity();
					customerBehaviorEntity.setProveFid(tenant.getFid());
                    customerBehaviorEntity.setProveParam("orderSn=" + evaluateOrderEntity.getOrderSn());
					customerBehaviorEntity.setAttribute(behaviorEnum.getAttribute());
					customerBehaviorEntity.setRole(CustomerBehaviorRoleEnum.LANDLORD.getCode());
					customerBehaviorEntity.setUid(evaluateOrderEntity.getRatedUserUid());
					customerBehaviorEntity.setType(behaviorEnum.getType());
					customerBehaviorEntity.setScore(behaviorEnum.getScore());
					customerBehaviorEntity.setCreateFid(evaluateOrderEntity.getRatedUserUid());
					customerBehaviorEntity.setCreateType(behaviorEnum.getCreateType());

					customerBehaviorEntity.setRemark(String.format("行为说明={%s}, 房客uid={%s}, 房东uid={%s}, 订单编号={%s}",
							behaviorEnum.getDesc(),
							evaluateOrderEntity.getEvaUserUid(),
							evaluateOrderEntity.getRatedUserUid(),
							evaluateOrderEntity.getOrderSn()));

					LogUtil.info(LOGGER, logPreStr + "-customerBehaviorEntity={}", customerBehaviorEntity);
					// 推送队列消息
					rabbitMqSendClient.sendQueue(queueName, JsonEntityTransform.Object2Json(customerBehaviorEntity));
				} catch (Exception e) {
					LogUtil.error(LOGGER, logPreStr + "-推送mq异常，tenant={}, e={}", tenant, e);
				}
			});
		}
	}

	/**
	 * 
	 * 查询昨天遗漏的评价行为(房东行为成长体系定时任务)
	 * 
	 * @author zhangyl2
	 * @created 2017年10月12日 14:06
	 * @param 
	 * @return 
	 */
    @Override
    public String queryTenantEvaluateForCustomerBehaviorJob(String param) {
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(param)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("参数错误");
            return dto.toJsonString();
        }
        EvaluateRequest request = JsonEntityTransform.json2Object(param, EvaluateRequest.class);
        if(Check.NuNObj(request)
                || Check.NuNStr(request.getStartTime())
                || Check.NuNStr(request.getEndTime())){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("参数错误");
            return dto.toJsonString();
        }

        List<TenantEvaluateVo> tenantEvaluateList = evaluateOrderServiceImpl.queryTenantEvaluateForCustomerBehaviorJob(request);

        dto.putValue("tenantEvaluateList", tenantEvaluateList);

        return dto.toJsonString();
    }

}
