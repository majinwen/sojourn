/**
 * @FileName: InviteCreateOrderCmsImpl.java
 * @Package com.ziroom.minsu.services.cms.service
 * 
 * @author loushuai
 * @created 2017年12月2日 下午2:45:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.*;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.constant.CouponConst;
import com.ziroom.minsu.services.cms.constant.InviterCreateOrderConst;
import com.ziroom.minsu.services.cms.dao.*;
import com.ziroom.minsu.services.cms.dto.InviteCmsRequest;
import com.ziroom.minsu.services.cms.dto.InviteStateUidRequest;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.cms.CouponTimeTypeEnum;
import com.ziroom.minsu.valenum.cms.InviteSourceEnum;
import com.ziroom.minsu.valenum.cms.IsGiveInviterPointsEnum;
import com.ziroom.minsu.valenum.cms.PointLogChangeTypeEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.order.CouponStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
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
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
@Service("cms.inviteCreateOrderCmsImpl")
public class InviteCreateOrderCmsImpl {

	private static Logger LOGGER = LoggerFactory.getLogger(InviteCreateOrderCmsImpl.class);
	
	
	@Resource(name="cms.pointSumDao")
	private PointSumDao pointSumDao;
	
	@Resource(name="cms.pointDetailDao")
	private PointDetailDao pointDetailDao;
	
	@Resource(name="cms.inviteDao")
	private InviteDao inviteDao;
	
	@Resource(name="cms.pointTiersDao")
	private PointTiersDao pointTiersDao;
	
	@Resource(name="cms.inviterCodeDao")
	private InviterCodeDao inviterCodeDao;
	
	@Resource(name = "cms.actCouponDao")
	private ActCouponDao actCouponDao;
	
	@Resource(name = "cms.activityFullDao")
	private ActivityFullDao activityFullDao;

	@Resource(name = "cms.activityGroupReceiveLogDao")
	private ActivityGroupReceiveLogDao  activityGroupReceiveLogDao;
	
	@Resource(name = "cms.activityExtCouponDao")
    private ActivityExtCouponDao activityExtCouponDao;
	
	@Resource(name="basedata.zkSysService")
    private ZkSysService zkSysService;
    
	@Resource(name = "cms.userCouponDao")
	private UserCouponDao userCouponDao;
	
	@Resource(name = "basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;
	
	@Resource(name = "cms.pointLogDao")
	private PointLogDao pointLogDao;

	/**
	 * 获取邀请人，总积分，已兑换积分，已邀请人数（成功邀请好友数指已入住、可加分的好友数）
	 *
	 * @author loushuai
	 * @created 2017年12月2日 下午3:05:47
	 *
	 * @param inviterDetailRequest
	 * @return
	 */
	public PointSumEntity getPointsAndInvitnum(PointSumEntity pointSumParam) {
		return pointSumDao.selectByParam(pointSumParam);
	}

	/**
	 * 根据uid(在t_invite表中，对应invite_uid),获取已邀请的好友列表,
	 *
	 * @author loushuai
	 * @created 2017年12月2日 下午3:16:24
	 *
	 * @param inviterDetailRequest
	 * @return
	 */
	public PagingResult<InviteEntity> getBeInvitersByPage(InviteCmsRequest inviterDetailRequest) {
		return inviteDao.getBeInvitersByPage(inviterDetailRequest);
	}

	/**
	 * 根据iinviteUid查询实体
	 *
	 * @author loushuai
	 * @created 2017年12月2日 下午3:16:24
	 *
	 * @param inviterDetailRequest
	 * @return
	 */
	public InviterCodeEntity selectByInviteUid(String inviteUid) {
		return inviterCodeDao.selectByInviteUid(inviteUid);
	}

	/**
	 * 保存InviterCodeEntity
	 *
	 * @author loushuai
	 * @created 2017年12月2日 下午3:16:24
	 *
	 * @param inviterDetailRequest
	 * @return
	 */
	public int saveInviterCode(InviterCodeEntity inviterCodeEntity) {
		return inviterCodeDao.insertInviterCod(inviterCodeEntity);
	}

	/**
	 * 积分兑换
	 *
	 * @author loushuai
	 * @created 2017年12月5日 下午3:16:24
	 *
	 * @param inviterDetailRequest
	 * @return
	 */
	public void pointsExchange(String groupSn,String actSn, String uid,Integer dealCount,DataTransferObject dto) {
		if (Check.NuNObj(dto)){
			return;
		}
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}
		if (Check.NuNStr(uid) || Check.NuNStr(groupSn) || Check.NuNStr(actSn)){
			dto.setErrCode(CouponConst.COUPON_PAR_ERROR.getCode());
			dto.setMsg("参数异常");
			return;
		}
		if (ValueUtil.getintValue(dealCount) <= 0){
			dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
			dto.setMsg("网络超时请重试");
			return;
		}else {
			dealCount--;
		}

		ActCouponEntity actCouponEntity = actCouponDao.getCouponByActSn(actSn);
		LogUtil.info(LOGGER, "pointsExchange方法事务层，根据actSn获取优惠券对象，    actCouponEntity={}", JsonEntityTransform.Object2Json(actCouponEntity));
		if (Check.NuNObj(actCouponEntity)){
			dto.setErrCode(CouponConst.COUPON_NO_MORE.getCode());
			dto.setMsg("当前优惠券已经被领完");
			return;
		}

		//获取当前的活动
		ActivityFullEntity activityFullEntity = activityFullDao.getActivityFullBySn(actSn);
		LogUtil.info(LOGGER, "pointsExchange方法事务层，根据actSn获取的活动对象，    activityFullEntity={}", JsonEntityTransform.Object2Json(activityFullEntity));
		Date couponStartTime = null;
		Date couponEndTime = null;
		CouponTimeTypeEnum couponTimeType = CouponTimeTypeEnum.getByCode(activityFullEntity.getCouponTimeType());
		if (Check.NuNObj(couponTimeType)){
			dto.setErrCode(CouponConst.COUPON_TYPE_ERROR.getCode());
			dto.setMsg("异常的优惠券时间限制类型！");
			LogUtil.info(LOGGER, dto.toJsonString());
			return;
		}
		if (couponTimeType.getCode() == CouponTimeTypeEnum.FIX.getCode()){
			if (Check.NuNObjs(actCouponEntity.getCouponStartTime(),actCouponEntity.getCouponEndTime())){
				dto.setErrCode(CouponConst.COUPON_INFO_ERROR.getCode());
				dto.setMsg("异常的优惠券信息！");
				LogUtil.info(LOGGER, dto.toJsonString());
				return ;
			}
		}else {
			if (ValueUtil.getintValue(activityFullEntity.getCouponTimeLast())<=0){
				dto.setErrCode(CouponConst.COUPON_TIME_ERROR.getCode());
				dto.setMsg("优惠活动的优惠时长为空！");
				LogUtil.info(LOGGER, dto.toJsonString());
				return ;
			}
			couponStartTime = DateSplitUtil.jumpMinute(new Date(), -10);
			//获取当前的
			couponEndTime = DateSplitUtil.jumpDate(couponStartTime,ValueUtil.getintValue(activityFullEntity.getCouponTimeLast()));
			couponEndTime = DateSplitUtil.jumpMinute(couponEndTime, -11);
		}

		// 更新优惠券信息为已领取
		ActCouponEntity coupon = new ActCouponEntity();
		coupon.setCouponSn(actCouponEntity.getCouponSn());
		coupon.setCouponStatus(CouponStatusEnum.GET.getCode());
		coupon.setOldStatus(CouponStatusEnum.WAIT.getCode());
		if (!Check.NuNObj(couponStartTime)){
			coupon.setCouponStartTime(couponStartTime);
		}
		if (!Check.NuNObj(couponEndTime)){
			coupon.setCouponEndTime(couponEndTime);
		}

		//插入领取记录
		int  num = activityGroupReceiveLogDao.insertActivityGroupReceiveLog(groupSn, null, uid);

		if(num!=1){
			LogUtil.error(LOGGER, "插入优惠券领取记录失败,groupSn={},num:{},coupon:{},uid={}",groupSn,num, coupon,uid);
			throw new BusinessException("根据uid领取优惠券失败");
		}
		num = actCouponDao.updateCoupon(coupon);
		if(num > 1){
			LogUtil.error(LOGGER, "更新优惠券信息为已领取失败,num:{},coupon:{}", num, coupon);
			throw new BusinessException("更新优惠券信息为已领取失败");
		}else if (num == 0){
			LogUtil.error(LOGGER, "更新优惠券信息为已领取失败,num:{},coupon:{}", num, coupon);
			throw new BusinessException("更新优惠券信息为已领取失败");
		}else if(num == 1){
			this.actCouponNumWarn(actCouponEntity.getActSn());
		}
		// 插入绑定信息
		UserCouponEntity orderCoupon = new UserCouponEntity();
		orderCoupon.setCouponSn(actCouponEntity.getCouponSn());
		orderCoupon.setUid(uid);
		num = userCouponDao.saveUserCoupon(orderCoupon);
		if(num != 1){
			LogUtil.error(LOGGER, "插入绑定信息失败,num:{},orderCoupon:{}", num, orderCoupon);
			throw new BusinessException("插入绑定信息失败");
		}
		
		//扣除邀请人积分
		PointSumEntity pointSumParam = new PointSumEntity();
    	pointSumParam.setUid(uid);
    	pointSumParam.setPointsSource(InviteSourceEnum.NEW_INVITE.getCode());
		PointSumEntity pointsSum = pointSumDao.selectByParam(pointSumParam);
		if(Check.NuNObj(pointsSum) ){
			LogUtil.error(LOGGER, "查询uid对应的总积分   pointsSum:{}", JsonEntityTransform.Object2Json(pointsSum));
			throw new BusinessException("获取邀请人总积分失败");
		}
		
		//根据兑换积分，及积分兑换优惠券比例，
		Integer actCut = actCouponEntity.getActCut()/100;
		Integer exchagePoints = (int) (actCut/InviterCreateOrderConst.pointsExchageCashRate);
		LogUtil.info(LOGGER, "pointsExchange事务层优惠券兑换积分结果    actCut={}, exchagePoints={}, pointsExchageCashRate={}",actCut ,exchagePoints,InviterCreateOrderConst.pointsExchageCashRate);
		
		if(exchagePoints>pointsSum.getSumPoints()){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("所选现金券大于可兑换积分");
			return;
		}
		PointSumEntity updatePointSum = new PointSumEntity();
		updatePointSum.setUid(uid);
		updatePointSum.setSumPoints(pointsSum.getSumPoints()-exchagePoints);
		updatePointSum.setHasExchangePoints(pointsSum.getHasExchangePoints()+exchagePoints);
		updatePointSum.setPointsSource(InviteSourceEnum.NEW_INVITE.getCode());
		int updatePointSumNum = pointSumDao.updateByParam(updatePointSum);
		if(num != 1){
			LogUtil.error(LOGGER, "更新总积分表失败,updatePointSumNum={}, updatePointSum={}", updatePointSumNum, JsonEntityTransform.Object2Json(updatePointSum));
			throw new BusinessException("更新总积分表失败");
		}
		PointLogEntity pointLog = new PointLogEntity();
		pointLog.setChangePoints(exchagePoints);
		pointLog.setChangeType(PointLogChangeTypeEnum.EXCHANGE_COUPON.getCode());
		pointLog.setCreateId(uid);
		pointLog.setFid(UUIDGenerator.hexUUID());
		pointLog.setFromPoints(pointsSum.getSumPoints());
		pointLog.setRemark(PointLogChangeTypeEnum.EXCHANGE_COUPON.getName());
		pointLog.setToPoints(pointsSum.getSumPoints()-exchagePoints);
		pointLog.setUid(uid);
		int insertPointLog = pointLogDao.insertPointLog(pointLog);
		if(num != 1){
			LogUtil.error(LOGGER, "插入积分日志表失败, insertPointLog={}", insertPointLog);
			throw new BusinessException("更新总积分表失败");
		}
		dto.putValue("coupon", actCouponEntity);
	}
	
	/**
     * 异步执行优惠券数量提醒
     * 先判断是这个优惠券活动是否添加提醒功能（表`t_activity_ext_coupon`）
     * 查询当前剩余优惠券数量（表`t_act_coupon`）--与优惠券总数对比
     * 判断比值大于10%，小于20%，发送邮件，并把活动表中的发送次数（warn_times）置为1，
     * 判断比值大于5%，小于10%，发送邮件，并把活动表中的发送次数置为2，
     * 判断比值大于1%，小于5%，发送邮件，并把活动表中的发送次数置为3，
     * 判断比值小于1%，发送邮件，并把活动表中的发送次数置为4，
     * 当追加优惠券后把发送邮件次数置为0；
     * @author xiangbin
     * @create 2017年8月22日12:04:05
     * @param actSn 活动码
     * @return
     */
	public void actCouponNumWarn(String actSn){
    	if(Check.NuNStr(actSn)){
    		return;
    	}
    	Thread task = new Thread(){
    		
    		@Override
    		public void run(){
    			ActivityExtCouponEntity extCouponEntity = activityExtCouponDao.selectActExtCouponByActSn(actSn);
    	    	if(Check.NuNObj(extCouponEntity)){
    	    		LogUtil.error(LOGGER, "无效的优惠券活动码");
    				throw new BusinessException("无效的优惠券活动码");
    	    	}
    	    	if(!Check.NuNObj(extCouponEntity.getIsWarn()) && 1 == extCouponEntity.getIsWarn()){
    	    		long avaliableNum = actCouponDao.countAvaliableCouponByActSn(actSn);
    	    		double precent = 0.0;
    	    		if(extCouponEntity.getCouponNum() > 0 && avaliableNum > 0){
    	    			precent = Double.valueOf(avaliableNum-1) / Double.valueOf(extCouponEntity.getCouponNum());
    	    		}
    	    		String precentString = "";
    	    		boolean warn = false;
    	    		int warnTime = 0;
    	    		if(0.2 >= precent && precent > 0.1 && extCouponEntity.getWarnTimes() < 1){
    	    			precentString = "20";
    	    			warn = true;
    	    			warnTime = 1;
    	    		}else if(0.1 >= precent && precent > 0.05 && extCouponEntity.getWarnTimes() < 2){
    	    			precentString = "10";
    	    			warn = true;
    	    			warnTime = 2;
    	    		}else if(0.05 >= precent && precent > 0.01 && extCouponEntity.getWarnTimes() < 3){
    	    			precentString = "5";
    	    			warn = true;
    	    			warnTime = 3;
    	    		}else if(0.01 >= precent && precent >= 0 &&  extCouponEntity.getWarnTimes() < 4){
    	    			precentString = "1";
    	    			warn = true;
    	    			warnTime = 4;
    	    		}
    	    		if(warn){
    	    			//修改优惠券当前已经提醒次数
    	    			extCouponEntity.setWarnTimes(warnTime);
    	    			int isSuccess = activityExtCouponDao.updateActivityExtCoupon(extCouponEntity);
    	    			//按照手机号个数发送提醒短信
    	    			if(isSuccess > 0 ){
    	    				ActivityFullEntity activityFullEntity = activityFullDao.getActivityFullBySn(actSn);
    	    				String actName = activityFullEntity.getActName();
    	    				String phones = zkSysService.getZkSysValue("minsu", "warnPhone");
    	            		String[] phonez = phones.split(",");
    	            		for(String phone : phonez){
    	            			SmsRequest smsRequest = new SmsRequest();
    	                        smsRequest.setMobile(phone);
    	                        smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.WARN_COUPON.getCode()));
    	                        Map<String, String> paMap = new HashMap<String, String>();
    	                        //模板参数
    	                        paMap.put("{1}", actName);
    	                        paMap.put("{2}", precentString);
    	                        smsRequest.setParamsMap(paMap);
    	                        LogUtil.info(LOGGER, "checkImgCode sendSmsByCode param :{}", JsonEntityTransform.Object2Json(smsRequest));
    	                        String smsJson = smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
    	                        LogUtil.info(LOGGER, "checkImgCode sendSmsByCode return :{}", smsJson);
    	                        DataTransferObject smsDto = JsonEntityTransform.json2DataTransferObject(smsJson);
    	                        if (smsDto.getCode() == DataTransferObject.SUCCESS) {
    	                        	LogUtil.info(LOGGER, "send message success");
    	                        } else {
    	                            LogUtil.info(LOGGER, "check img code fail");
    	                        }	
    	            		}
    	    			}
    	    		}
    	    	
    	    	}
    		}
    		
    	};
    	SendThreadPool.execute(task);
    }

	/**
	 * 
	 * 查询积分详情对象
	 * @author loushuai
	 * @created 2017年12月7日 下午3:16:24
	 *
	 * @param pointDetail
	 * @return
	 */
	public PointDetailEntity getPointsDetail(PointDetailEntity pointDetail) {
		return pointDetailDao.selectByParam(pointDetail);
	}

	/**
	 * 根据总人数和活动类别查询积分档位
	 * @author yanb
	 * @created 2017年12月08日 14:31:36
	 * @param  * @param map
	 * @return com.ziroom.minsu.entity.cms.PointTiersEntity
	 */
	public PointTiersEntity getPointTiersByParam(Map<String, Object> param) {
		if (Check.NuNObj(param)) {
			LogUtil.error(LOGGER, "参数为空");
			throw new BusinessException("参数为空");
		}
		if (Check.NuNObj( param.get("sumPerson"))) {
			LogUtil.error(LOGGER, "sumPerson参数为空");
			throw new BusinessException("sumPerson参数为空");
		}
		if (Check.NuNObj(param.get("tiersType"))) {
			LogUtil.error(LOGGER, "积分类型tiersType参数为空");
			throw new BusinessException("积分类型tiersType参数为空");
		}
		return pointTiersDao.getPointTiersByParam(param);
	}

	/**
	 * 根据用户uid和pointsSource获取PointSum总积分表的信息
	 * @author yanb
	 * @created 2017年12月10日 16:51:17
	 * @param  * @param null
	 * @return
	 */
	public PointSumEntity getPointSumByUidSource(Map<String,Object> param) {
		if (Check.NuNObj(param)) {
			LogUtil.error(LOGGER, "参数为空");
			throw new BusinessException("参数为空");
		}
		if (Check.NuNStr((String) param.get("uid"))) {
			LogUtil.error(LOGGER, "uid为空");
			throw new BusinessException("uid为空");
		}
		if (Check.NuNObj(param.get("pointsSource"))) {
			LogUtil.error(LOGGER, "积分类型为空");
			throw new BusinessException("积分类型为空");
		}
		return pointSumDao.getPointSumByUidSource(param);
	}

	/**
	 * 根据用户uid和活动行类型获取邀请人uid和活动行类型
	 * @param  @InviteStateUidRequest
	 * @author yanb
	 * return string
	 */
	public String getInviteUidByUid(InviteStateUidRequest inviteStateUidRequest) {
		return inviteDao.getInviteUidByUid(inviteStateUidRequest);
	}

	/**
	 * 邀请好友下单为uid增加积分等事务操作
	 * 定时任务调用
	 * @author yanb
	 * @created 2017年12月13日 18:02:41
	 * @param  * @param addPointsBy
	 * @return java.lang.Integer
	 */
	public Integer addPointsByParam(Map<String, Object> paramMap) {
		Integer result = 0;
		String yaoQingRenUid = (String) paramMap.get("yaoQingRenUid");
		String beiYaoQingUid = (String) paramMap.get("beiYaoQingUid");
		String orderSn = (String) paramMap.get("orderSn");
		Integer inviteSource = (Integer) paramMap.get("inviteSource");
		Integer sumPerson = (Integer) paramMap.get("sumPerson");
		Integer tiersPoints = (Integer) paramMap.get("pointTiers");
		Integer sumPoints = (Integer) paramMap.get("sumPoints");

		/**
		 * 更新总积分pointSum表,增加积分
		 */
		PointSumEntity pointSum = new PointSumEntity();
		pointSum.setPointsSource(inviteSource);
		pointSum.setUid(yaoQingRenUid);
		pointSum.setSumPoints(sumPoints+tiersPoints);
		pointSum.setSumPerson(sumPerson+1);
		//根据sumPerson判断是insert还是update
		if (sumPerson == 0) {
			result = pointSumDao.insertPointSum(pointSum);
		} else {
			result = pointSumDao.updateByParam(pointSum);
		}

		if (result == 0) {
			LogUtil.error(LOGGER, "定时任务增加积分事务层,更新/插入 总积分表t_point_sum结果为0,参数={}", pointSum);
			throw new BusinessException("更新总积分表t_point_sum结果为0");
		}

		/**
		 *	更新邀请表的状态
		 */
		InviteEntity inviteEntity = new InviteEntity();
		inviteEntity.setUid(beiYaoQingUid);
		inviteEntity.setInviteSource(inviteSource);
		inviteEntity.setInviteUid(yaoQingRenUid);
		inviteEntity.setIsGiveInviterPoints(IsGiveInviterPointsEnum.ALREADY_ADD.getCode());
		result = inviteDao.updateByUid(inviteEntity);
		if (result == 0) {
			LogUtil.error(LOGGER, "定时任务增加积分事务层,更新邀请表t_invite结果为0,参数={}", inviteEntity);
			throw new BusinessException("更新邀请表t_invite结果为0");
		}
		/**
		 * 插入积分详情表
		 */
		/**插入之前做个被邀请人的校验,做个兼容*/
		PointDetailEntity pointDetail=new PointDetailEntity();
		pointDetail.setInviteUid(beiYaoQingUid);
		pointDetail.setPointsSource(inviteSource);
		Integer detailCount = pointDetailDao.countByParam(pointDetail);
		if (detailCount > 0) {
			LogUtil.error(LOGGER, "定时任务增加积分事务层,积分详情表校验不通过,该被邀请人在表中已有记录={}条", detailCount);
			throw new BusinessException("被邀请人在积分详情表t_point_detail中已有记录");
		}
		pointDetail.setUid(yaoQingRenUid);
		pointDetail.setOrderSn(orderSn);
		pointDetail.setPoints(tiersPoints);
		pointDetail.setRemark("好友成功入住,获得积分");
		pointDetail.setActSn("");
		pointDetail.setCreateId("定时任务-增加积分");
		pointDetail.setActSn("新版邀请好友下单");
		result = pointDetailDao.insertPointDetail(pointDetail);
		if (result == 0) {
			LogUtil.error(LOGGER, "定时任务增加积分事务层,插入积分详情表t_point_detail结果为0,参数={}", pointDetail);
			throw new BusinessException("插入积分详情表t_point_detail结果为0");
		}

		/**
		 * 插入积分日志表
		 */
		PointLogEntity pointLog=new PointLogEntity();
		pointLog.setFid(UUIDGenerator.hexUUID());
		pointLog.setUid(yaoQingRenUid);
		pointLog.setChangeType(PointLogChangeTypeEnum.INVITE_CREATE_ORDER.getCode());
		pointLog.setRemark("好友成功入住,获得积分");
		pointLog.setCreateId("定时任务-增加积分");
		pointLog.setChangePoints(tiersPoints);
		pointLog.setFromPoints(sumPoints);
		pointLog.setToPoints(sumPoints+tiersPoints);
		result = pointLogDao.insertPointLog(pointLog);
		if (result == 0) {
			LogUtil.error(LOGGER, "定时任务增加积分事务层,插入积分日志表t_point_log结果为0,参数={}", pointLog);
			throw new BusinessException("插入积分日志表t_point_log结果为0");
		}
		return result;
	}

	/**
	 * 根据inviteCode查询实体
	 * @author yanb
	 * @param record
	 * @return
	 */
	public InviterCodeEntity selectByInviteCode(String inviteCode) {
		return inviterCodeDao.selectByInviteCode(inviteCode);
	}
}
