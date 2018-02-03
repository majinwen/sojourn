/**
 * @FileName: InviteCreateOrderCmsProxy.java
 * @Package com.ziroom.minsu.services.cms.proxy
 * 
 * @author loushuai
 * @created 2017年12月2日 下午2:14:13
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.*;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.cms.api.inner.InviteCreateOrderCmsService;
import com.ziroom.minsu.services.cms.constant.CouponConst;
import com.ziroom.minsu.services.cms.constant.InviterCreateOrderConst;
import com.ziroom.minsu.services.cms.dto.InviteAddPointsSmsVo;
import com.ziroom.minsu.services.cms.dto.InviteCmsRequest;
import com.ziroom.minsu.services.cms.dto.InviteOrderRequest;
import com.ziroom.minsu.services.cms.dto.InviteStateUidRequest;
import com.ziroom.minsu.services.cms.entity.BeInviterInfo;
import com.ziroom.minsu.services.cms.entity.InviterCouponsVo;
import com.ziroom.minsu.services.cms.entity.InviterDetailVo;
import com.ziroom.minsu.services.cms.service.ActCouponServiceImpl;
import com.ziroom.minsu.services.cms.service.ActivityGroupServiceImpl;
import com.ziroom.minsu.services.cms.service.ActivityServiceImpl;
import com.ziroom.minsu.services.cms.service.InviteCreateOrderCmsImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.SnUtil;
import com.ziroom.minsu.valenum.cms.BeInviterStatusEnum;
import com.ziroom.minsu.valenum.cms.InviteSourceEnum;
import com.ziroom.minsu.valenum.cms.TiersTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>邀请好友下单</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author 娄帅
 * @since 1.0
 * @version 1.0
 */
@Component("cms.inviteCreateOrderCmsProxy")
public class InviteCreateOrderCmsProxy implements InviteCreateOrderCmsService{

	private static Logger logger = LoggerFactory.getLogger(InviteCreateOrderCmsProxy.class);
	
	@Resource(name="cms.inviteCreateOrderCmsImpl")
	private InviteCreateOrderCmsImpl inviteCreateOrderCmsImpl;
	
	@Resource(name = "cms.activityServiceImpl")
	private ActivityServiceImpl activityServiceImpl ;
	
	@Resource(name = "cms.activityGroupServiceImpl")
	private ActivityGroupServiceImpl activityGroupServiceImpl ;
	
	@Resource(name = "cms.actCouponServiceImpl")
	private ActCouponServiceImpl actCouponServiceImpl ;

	@Resource(name = "cms.messageSource")
	private MessageSource messageSource;

	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;



	/**
	 * 获取邀请人详情页
	 *
	 * @author loushuai
	 * @created 2017年12月2日 下午2:39:03
	 *
	 * @param inviterDetailRequest
	 */
	@Override
	public String getInviterDetail(String param) {
		LogUtil.info(logger, "getInviterDetail方法   inviterDetailRequest={}", param);
		DataTransferObject dto = new DataTransferObject();
		InviteCmsRequest inviterDetailRequest = JsonEntityTransform.json2Entity(param, InviteCmsRequest.class);
		if(Check.NuNObj(inviterDetailRequest)){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("参数为空");
    		return dto.toJsonString();
    	}
    	String uid = inviterDetailRequest.getUid();
    	//String actSn = inviterDetailRequest.getActSn();
    	if(Check.NuNStr(uid)){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("uid为空");
    		return dto.toJsonString();
    	}
    	//填充到邀请人详情页==》inviterDetailVoVo中
    	InviterDetailVo inviterDetailVo = new InviterDetailVo();
    	
    	//根据uid获取邀请人的总积分，已兑换积分，已邀请人数（成功邀请好友数指已入住、可加分的好友数）
    	PointSumEntity pointSumParam = new PointSumEntity();
    	pointSumParam.setUid(uid);
    	pointSumParam.setPointsSource(InviteSourceEnum.NEW_INVITE.getCode());
    	PointSumEntity pointSum = inviteCreateOrderCmsImpl.getPointsAndInvitnum(pointSumParam);
    	
    	//根据uid(在t_invite表中，对应invite_uid), 获取已邀请的好友列表,
    	PagingResult<InviteEntity> pageResult= inviteCreateOrderCmsImpl.getBeInvitersByPage(inviterDetailRequest);
    	Integer sumPerson = 0;
    	//当前==》积分兑换现金券比例
		Double  nowPointsExchageCashRate = InviterCreateOrderConst.pointsExchageCashRate;
    	if(!Check.NuNObj(pointSum)){
    		sumPerson = pointSum.getSumPerson();
        	inviterDetailVo.setSuccessInviteFriends(String.valueOf(sumPerson));
        	//获取总积分，按照比例兑换成现金优惠券
        	Integer canExchangePoints = pointSum.getSumPoints();
        	Integer hasExchangePoints = pointSum.getHasExchangePoints();
        	Integer sumPoints = canExchangePoints + hasExchangePoints;
        	double sumCash = BigDecimalUtil.mul(sumPoints, nowPointsExchageCashRate);
        	String sumCashStr = String.valueOf(sumCash);
        	inviterDetailVo.setSumCash(sumCashStr.substring(0, sumCashStr.lastIndexOf(".")));
        	
        	//可兑换的积分，按照比例兑换成现金优惠券
        	double canExchangeCash = BigDecimalUtil.mul(canExchangePoints, nowPointsExchageCashRate);
        	String canExchangeCashStr = String.valueOf(canExchangeCash);
        	inviterDetailVo.setCanExchangeCash(canExchangeCashStr.substring(0, canExchangeCashStr.lastIndexOf(".")));
        	LogUtil.info(logger, "pointsExchange优惠券兑换积分结果    pointSum查新结果实体={}, pointsExchageCashRate={}",JsonEntityTransform.Object2Json(pointSum),nowPointsExchageCashRate);
    	}else{
    		inviterDetailVo.setSumCash(YesOrNoEnum.NO.getStr());
    		inviterDetailVo.setSuccessInviteFriends(YesOrNoEnum.NO.getStr());
    		inviterDetailVo.setCanExchangeCash(YesOrNoEnum.NO.getStr());
    	}
    	
    	if(pageResult.getTotal()!=0){
    		List<InviteEntity> rows = pageResult.getRows();
    		
    		List<String> beInviteUidList = new ArrayList<String>();
    		List<String> hasnotGivePointsUidList = new ArrayList<String>();
    		
    		List<BeInviterInfo> hasnotGivePointsList = new ArrayList<BeInviterInfo>();
    		List<BeInviterInfo> hasGivePointsList = new ArrayList<BeInviterInfo>();
    		for (InviteEntity invite : rows) {
    			beInviteUidList.add(invite.getUid());
    			BeInviterInfo beInviterInfo = new BeInviterInfo();
    			beInviterInfo.setBeInviterUid(invite.getUid());
    			//被邀请人接受邀请，会给其灌一张优惠券，该券的过期时间是180天
    			Date time = DateUtil.getTime(invite.getCreateTime(), InviterCreateOrderConst.couponExpireDay);
    			beInviterInfo.setExpiryTime(time);
    			beInviterInfo.setInviteTime(invite.getCreateTime());
    			if(invite.getIsGiveInviterPoints()==YesOrNoEnum.YES.getCode()){
    				beInviterInfo.setIsGiveInviterPoints(YesOrNoEnum.YES.getCode());
    				beInviterInfo.setInviteStatusCode(BeInviterStatusEnum.SUCCESS_ACCEPT.getStatusCode());
    				beInviterInfo.setInviteStatusName(BeInviterStatusEnum.getByStatusCode(BeInviterStatusEnum.SUCCESS_ACCEPT.getStatusCode()).getStatusName());
    				beInviterInfo.setInviteStatusShow(BeInviterStatusEnum.getByStatusCode(BeInviterStatusEnum.SUCCESS_ACCEPT.getStatusCode()).getStatusShow());
    				//填充预期奖金，预期奖金展示文案
    				PointDetailEntity pointDetailParam = new PointDetailEntity();
    				pointDetailParam.setUid(uid);
    				pointDetailParam.setInviteUid(invite.getUid());
    				pointDetailParam.setPointsSource(InviteSourceEnum.NEW_INVITE.getCode());
    				PointDetailEntity result = inviteCreateOrderCmsImpl.getPointsDetail(pointDetailParam);
    				if(!Check.NuNObj(result)){
    					Integer points = result.getPoints();//给邀请人增加的积分
    					Double pointsExchageCashRate = result.getPointsExchageCashRate();//积分兑换现金券比例
    					double addCashToInviter = BigDecimalUtil.mul(points, pointsExchageCashRate);//给邀请人增加的现金优惠券
    					String addCashToInviterStr = String.valueOf(addCashToInviter);
    					String addCash = addCashToInviterStr.substring(0, addCashToInviterStr.lastIndexOf(".")) + InviterCreateOrderConst.moneyUnit;
    					LogUtil.info(logger, "getInviterDetail方法，该被邀请人已经为邀请人获得积分，兑换的现金优惠券cash={}, 积分详情PointDetail={}", addCash, JsonEntityTransform.Object2Json(result));
    					beInviterInfo.setInviteStatusShow(addCash + BeInviterStatusEnum.getByStatusCode(BeInviterStatusEnum.SUCCESS_ACCEPT.getStatusCode()).getStatusShow());
    					beInviterInfo.setExpectBonus(InviterCreateOrderConst.expectBonusSymbol + addCash);
    					beInviterInfo.setExpectBonusShow(DateUtil.dateFormat(result.getCreateDate()));
    				}
    				hasGivePointsList.add(beInviterInfo);
    			}else{
    				hasnotGivePointsUidList.add(invite.getUid());
    				beInviterInfo.setIsGiveInviterPoints(YesOrNoEnum.NO.getCode());
    				beInviterInfo.setInviteStatusCode(BeInviterStatusEnum.HAS_ACCEPT_INVITE.getStatusCode());
    				beInviterInfo.setInviteStatusName(BeInviterStatusEnum.getByStatusCode(BeInviterStatusEnum.HAS_ACCEPT_INVITE.getStatusCode()).getStatusName());
    				beInviterInfo.setInviteStatusShow(BeInviterStatusEnum.getByStatusCode(BeInviterStatusEnum.HAS_ACCEPT_INVITE.getStatusCode()).getStatusShow());
    				//填充预期奖金，预期奖金展示文案
    				Map<String, Object> map = new HashMap<String, Object>();
					//预期积分=已经为邀请人增加积分的sumPerson+1，获取对应的档次对应的积分
					map.put("sumPerson", sumPerson+1);
    				map.put("tiersType", TiersTypeEnum.INVITE_CREATE_ORDER.getCode());
    				PointTiersEntity pointTiers =inviteCreateOrderCmsImpl.getPointTiersByParam(map);
    				if(!Check.NuNObj(pointTiers)){
						//给邀请人增加的现金优惠券
						double expectCash = BigDecimalUtil.mul(pointTiers.getPoints(), nowPointsExchageCashRate);
    					String expectCashStr = String.valueOf(expectCash);
    					beInviterInfo.setExpectBonus(InviterCreateOrderConst.expectBonusSymbol + expectCashStr.substring(0, expectCashStr.lastIndexOf(".")) + InviterCreateOrderConst.moneyUnit);
    					beInviterInfo.setExpectBonusShow(InviterCreateOrderConst.expectBonusName);
    				}
    				hasnotGivePointsList.add(beInviterInfo);
    			}
				
			}
    		inviterDetailVo.setBeInviteUidList(beInviteUidList);
    		inviterDetailVo.setHasnotGivePointsUidList(hasnotGivePointsUidList);
    		inviterDetailVo.setHasnotGivePointsList(hasnotGivePointsList);
    		inviterDetailVo.setHasGivePointsList(hasGivePointsList);
    		inviterDetailVo.setCount(pageResult.getTotal());
    	}
    	dto.putValue("inviterDetailVo", inviterDetailVo);
    	LogUtil.info(logger, "getInviterDetail方法出参   dto={}", JsonEntityTransform.entity2Json(dto));
    	return dto.toJsonString();
	}

	/**
	 * 根据邀请人uid获取邀请码
	 *
	 * @author loushuai
	 * @created 2017年12月4日 下午4:48:35
	 *
	 * @param object2Json
	 * @return
	 */
	@Override
	public String getOrInitInviteCode(String param) {
		LogUtil.info(logger, "getInviteCode方法   参数={}", param);
		DataTransferObject dto = new DataTransferObject();
		InviteCmsRequest inviterDetailRequest = JsonEntityTransform.json2Entity(param, InviteCmsRequest.class);
		if(Check.NuNObj(inviterDetailRequest)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		String inviterUid = inviterDetailRequest.getUid();
    	if(Check.NuNStr(inviterUid)){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("邀请人inviteUid为空");
    		return dto.toJsonString();
    	}
    	InviterCodeEntity inviterCode = inviteCreateOrderCmsImpl.selectByInviteUid(inviterUid);
    	if(Check.NuNObj(inviterCode)){
    		//该邀请人参加过邀请活动, 需要重新生成
    		String inviteCode = SnUtil.getInviteSnNew(inviterDetailRequest.getPhone());
    		LogUtil.info(logger, "getInviteCode方法  inviterCode={}", inviteCode);
    		InviterCodeEntity inviterCodeEntity = new InviterCodeEntity();
    		inviterCodeEntity.setInviteUid(inviterUid);
    		inviterCodeEntity.setInviteCode(inviteCode);
    		int saveInviterCode = inviteCreateOrderCmsImpl.saveInviterCode(inviterCodeEntity);
    		if(saveInviterCode>0){
    			dto.putValue("inviteCode", inviteCode);
    			return dto.toJsonString();
    		}
    	}
    	dto.putValue("inviteCode", inviterCode.getInviteCode());
		return dto.toJsonString();
	}

	/**
	 * 获取邀请人积分可兑换列表
	 *
	 * @author loushuai
	 * @created 2017年12月5日 下午2:15:22
	 *
	 * @param inviterDetailRequest
	 * @return
	 */
	@Override
	public String getCouponList(String param) {
		LogUtil.info(logger, "getCouponList方法   inviterDetailRequest={}", param);
		DataTransferObject dto = new DataTransferObject();
		InviteCmsRequest inviterDetailRequest = JsonEntityTransform.json2Entity(param, InviteCmsRequest.class);
		if(Check.NuNObj(inviterDetailRequest)){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("参数为空");
    		return dto.toJsonString();
    	}
    	String inviterUid = inviterDetailRequest.getUid();
    	if(Check.NuNStr(inviterUid)){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("邀请人uid为空");
    		return dto.toJsonString();
    	}
    	String groupSn = inviterDetailRequest.getGroupSn();
    	if(Check.NuNStr(groupSn)){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("活动组号为空");
    		return dto.toJsonString();
    	}
    	
    	//获取活动组信息
    	ActivityGroupEntity activityGroupEntity = activityGroupServiceImpl.getGroupBySN(groupSn);
    	if(Check.NuNObj(activityGroupEntity)){
			dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
			return dto.toJsonString();
		}
    	
    	//根据活动组号查询所有有效的活动
		List<ActivityEntity> activityEntities = activityServiceImpl.listActivityByGroupSn(groupSn);
		if (Check.NuNCollection(activityEntities)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("活动列表为空");
			return dto.toJsonString();
		}
		
		//填充邀请人可选的
		List<InviterCouponsVo> inviterCouponList = new ArrayList<InviterCouponsVo>();
		
		//获取邀请人总积分
    	PointSumEntity pointSumParam = new PointSumEntity();
    	pointSumParam.setUid(inviterUid);
    	pointSumParam.setPointsSource(InviteSourceEnum.NEW_INVITE.getCode());
    	PointSumEntity pointSum = inviteCreateOrderCmsImpl.getPointsAndInvitnum(pointSumParam);
    	LogUtil.info(logger, "getCouponList  邀请人总积分对象pointSum={}", JsonEntityTransform.Object2Json(pointSum));
    	if(Check.NuNObj(pointSum) || pointSum.getSumPoints()==0){
    		for (ActivityEntity activity : activityEntities) {
    	        InviterCouponsVo inviterCouponsVo = new InviterCouponsVo();
    	        inviterCouponsVo.setActSn(activity.getActSn());
    	        String actCut = String.valueOf(activity.getActCut()/100);
    	        inviterCouponsVo.setCouponMoney(actCut);
    	        inviterCouponsVo.setIsClick(YesOrNoEnum.NO.getCode());
    	        inviterCouponList.add(inviterCouponsVo);
    		}
    		dto.putValue("inviterCouponList", inviterCouponList);
    		LogUtil.info(logger, "getCouponList方法   出参={}", dto.toJsonString());
    		return dto.toJsonString();
    	}
    	
    	//邀请人
    	Integer sumPoints = pointSum.getSumPoints();
        for (ActivityEntity activity : activityEntities) {
        	InviterCouponsVo inviterCouponsVo = new InviterCouponsVo();
        	inviterCouponsVo.setActSn(activity.getActSn());
        	String actCut = String.valueOf(activity.getActCut()/100);
        	inviterCouponsVo.setCouponMoney(actCut);
        	Long avaliableCoupon = actCouponServiceImpl.countAvaliableCouponByActSn(activity.getActSn());
        	if(avaliableCoupon>0 && sumPoints>=activity.getActCut()/100){
        		inviterCouponsVo.setIsClick(YesOrNoEnum.YES.getCode());
        	}else{
        		inviterCouponsVo.setIsClick(YesOrNoEnum.NO.getCode());
        	}
        	inviterCouponList.add(inviterCouponsVo);
		}
        dto.putValue("inviterCouponList", inviterCouponList);
        LogUtil.info(logger, "getCouponList方法   出参={}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String pointsExchange(String param) {
		LogUtil.info(logger, "pointsExchange方法     inviteCmsRequest={}", param);
		DataTransferObject dto = new DataTransferObject();
		InviteCmsRequest inviteCmsRequest = JsonEntityTransform.json2Entity(param, InviteCmsRequest.class);
		if(Check.NuNObj(inviteCmsRequest)){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("参数为空");
    		return dto.toJsonString();
    	}
    	if(Check.NuNStr(inviteCmsRequest.getUid())){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("邀请人uid为空");
    		return dto.toJsonString();
    	}
    	if(Check.NuNStr(inviteCmsRequest.getGroupSn())){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("活动组号为空");
    		return dto.toJsonString();
    	}
    	if(Check.NuNStr(inviteCmsRequest.getActSn())){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("现金券对应的活动号为空");
    		return dto.toJsonString();
    	}
    	//获取邀请人总积分
    	PointSumEntity pointSumParam = new PointSumEntity();
    	pointSumParam.setUid(inviteCmsRequest.getUid());
    	pointSumParam.setPointsSource(InviteSourceEnum.NEW_INVITE.getCode());
    	PointSumEntity pointSum = inviteCreateOrderCmsImpl.getPointsAndInvitnum(pointSumParam);
    	if(Check.NuNObj(pointSum) || pointSum.getSumPoints()==0){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("邀请人无积分或积分为0");
    		return dto.toJsonString();
    	}
    	/*Integer sumPoints = pointSum.getSumPoints();
    	Integer points = Integer.valueOf(inviteCmsRequest.getPoints());
    	//如果兑换积分 > 改用户的总积分 
    	if(points > sumPoints){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("兑换的积分已经超过总积分");
    		return dto.toJsonString();
    	}*/
    	inviteCreateOrderCmsImpl.pointsExchange(inviteCmsRequest.getGroupSn(),inviteCmsRequest.getActSn(),inviteCmsRequest.getUid(),3, dto);
		return dto.toJsonString();
	}

	/**
	 * 根据邀请人的uid和积分类型查询出对应的档位和积分总表信息
	 * @author yanb
	 * @created 2017年12月08日 17:44:56
	 * @param  * @param uid
	 * @param  * @param pointsSource
	 * @return java.lang.String
	 */
	@Override
	public String getPointTiersByUidSource(String paramJson) {
		LogUtil.info(logger, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();

		try {
			//参数校验
			Map paramPointSum =JsonEntityTransform.json2Map(paramJson);
			if (Check.NuNObj(paramPointSum)) {
				LogUtil.error(logger, "参数为空");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			if (Check.NuNStr((String) paramPointSum.get("uid"))) {
				LogUtil.error(logger, "uid为空");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("uid为空");
				return dto.toJsonString();
			}
			if (Check.NuNObj(paramPointSum.get("pointsSource"))) {
				LogUtil.error(logger, "积分类型为空");
				dto.setMsg("积分类型为空");
				return dto.toJsonString();
			}
			//根据用户uid获取对应的积分总表信息
			PointSumEntity pointSum = inviteCreateOrderCmsImpl.getPointSumByUidSource(paramPointSum);

			//如果查询的结果为null,则说明该uid之前没有邀请成功的记录,默认把邀请人数设为0
			if (Check.NuNObj(pointSum)) {
				pointSum = new PointSumEntity();
				pointSum.setSumPerson(0);
				pointSum.setSumPoints(0);
				pointSum.setHasExchangePoints(0);
				pointSum.setPointsSource((Integer) paramPointSum.get("pointsSource"));
			}
			//根据积分总表信息进行阶梯查询,和页面预期显示的统一,查询档位时总人数+1
			Map<String,Object> paramPointTiersMap = new HashMap(2);
			paramPointTiersMap.put("sumPerson", pointSum.getSumPerson()+1);
			paramPointTiersMap.put("tiersType", pointSum.getPointsSource());
			PointTiersEntity pointTiers = inviteCreateOrderCmsImpl.getPointTiersByParam(paramPointTiersMap);
			/**返回值为 邀请总人数+积分档位对象*/
			dto.putValue("pointSum",pointSum);
			dto.putValue("pointTiers",pointTiers);
			LogUtil.info(logger, "getpointTiersByUidSource方法   出参={}", dto.toJsonString());
		} catch (Exception e) {
			LogUtil.error(logger, "getpointTiersByUidSource(),error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 为uid增加积分等事务操作的代理层
	 * 定时任务调用
	 * 参数为 InviteOrderVo类型的List
	 * @param * @String
	 * @return
	 * @author yanb
	 * @created 2017年12月13日 16:09:20
	 */
	@Override
	public String addPointsByList(String paramJson) {
		LogUtil.info(logger,"【addPointsByList】参数{}",paramJson);
		DataTransferObject dto = new DataTransferObject();
		List<InviteAddPointsSmsVo> resultSmsList = new ArrayList<>();
		try {
			//转换paramJson为list
			List<InviteOrderRequest> inviteOrderList = JsonEntityTransform.json2ObjectList(paramJson, InviteOrderRequest.class);
			if (!Check.NuNObj(inviteOrderList)) {
				for (InviteOrderRequest iov : inviteOrderList) {
					LogUtil.info(logger,"【addPointsByList】 循环增加积分 当前InviteOrderRequest={}",iov.toJsonStr());
					//检查uid是否在刚刚增加过积分,防止一个用户同时多个订单,增加多次积分 "增加积分成功"
					if (!Check.NuNObj(dto.getData().get(iov.getUserUid())) && "已完成邀请下单任务".equals(dto.getData().get(iov.getUserUid()))) {
						continue;
					}
					//new一个参数
					InviteStateUidRequest inviteStateUidRequest=new InviteStateUidRequest();
					inviteStateUidRequest.setUid(iov.getUserUid());
					inviteStateUidRequest.setInviteSource(iov.getInviteSource());
					//查询邀请人uid
					String inviteUid = inviteCreateOrderCmsImpl.getInviteUidByUid(inviteStateUidRequest);

					Map paramPointSum = new HashMap(2);
					paramPointSum.put("uid", inviteUid);
					paramPointSum.put("pointsSource", iov.getInviteSource());
					//查询对应的积分档位和积分总表信息
					String pointTiersJson = getPointTiersByUidSource(JsonEntityTransform.Object2Json(paramPointSum));
					DataTransferObject pointTiersDto = JsonEntityTransform.json2DataTransferObject(pointTiersJson);

					//如果到达最大积分限制
					PointSumEntity pointSum = SOAResParseUtil.getValueFromDataByKey(pointTiersJson, "pointSum", PointSumEntity.class);
					PointTiersEntity pointTiers = SOAResParseUtil.getValueFromDataByKey(pointTiersJson, "pointTiers", PointTiersEntity.class);
					if ((pointSum.getSumPoints()+pointSum.getHasExchangePoints()) >= 9999) {
						dto.putValue(inviteUid,"已到最大积分限制");
						continue;
					} else if ((pointSum.getSumPoints()+pointSum.getHasExchangePoints()+pointTiers.getPoints()) > 9999) {
						//积分上限是9999,最高加到9999
						pointTiers.setPoints(9999-(pointSum.getSumPoints()+pointSum.getHasExchangePoints()));
					}

					/** uid的命名容易混,所以换成拼音 **/
					//积分档位返回的map中两个值都会被用到
					Map<String, Object> paramMap = new HashMap<>(6);
					paramMap.put("yaoQingRenUid", inviteUid);
					paramMap.put("beiYaoQingUid", iov.getUserUid());
					paramMap.put("orderSn", iov.getOrderSn());
					paramMap.put("inviteSource", iov.getInviteSource());

					paramMap.put("sumPerson", pointSum.getSumPerson());
					paramMap.put("sumPoints", pointSum.getSumPoints());
					paramMap.put("pointTiers", pointTiers.getPoints());

					LogUtil.info(logger,"【addPointsByList】 循环增加积分,当前调用addPointsByParam方法的参数={}",paramMap);

					//赋值初始值为零,免得报空指针异常
					Integer result = YesOrNoEnum.NO.getCode();
					/**套上try-catch,因为调用的方法会主动抛出异常回滚,为了不影响遍历*/
					try {
						result = inviteCreateOrderCmsImpl.addPointsByParam(paramMap);
					} catch (Exception e) {
						LogUtil.error(logger,"【addPointsByList】 循环增加积分,当前调用addPointsByParam方法抛出异常={}",e);
						continue;
					}
					if (result > 0) {
						dto.putValue(inviteUid, "增加积分成功");
						dto.putValue(iov.getUserUid(),"已完成邀请下单任务");
						InviteAddPointsSmsVo inviteAddPointsSmsVo=new InviteAddPointsSmsVo();
						inviteAddPointsSmsVo.setUid(iov.getUserUid());
						inviteAddPointsSmsVo.setInviteUid(inviteUid);
						inviteAddPointsSmsVo.setPoints(pointTiers.getPoints());
						inviteAddPointsSmsVo.setSumPoints(pointSum.getSumPoints()+pointTiers.getPoints()+pointSum.getHasExchangePoints());
						resultSmsList.add(inviteAddPointsSmsVo);
					} else {
						dto.putValue(inviteUid, "增加积分失败!");
						dto.putValue(iov.getUserUid(),"未完成给邀请者增加积分");
					}
					LogUtil.info(logger,"【addPointsByList】 循环增加积分,本次执行的结果={}",dto.toJsonString());
				}
				LogUtil.info(logger,"【addPointsByList】 循环增加积分,最终执行的结果={}",dto.toJsonString());
			}
			dto.putValue("resultSmsList",resultSmsList);
		} catch (Exception e) {
			LogUtil.error(logger, "【addPointsByList】,异常错误e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}

		return dto.toJsonString();
	}
}
