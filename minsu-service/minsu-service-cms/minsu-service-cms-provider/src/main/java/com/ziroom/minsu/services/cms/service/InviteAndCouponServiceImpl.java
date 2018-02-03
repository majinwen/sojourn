package com.ziroom.minsu.services.cms.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.*;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.constant.CouponConst;
import com.ziroom.minsu.services.cms.dao.*;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.cms.CouponTimeTypeEnum;
import com.ziroom.minsu.valenum.cms.InviteStatusEnum;
import com.ziroom.minsu.valenum.cms.IsGiveInviterPointsEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.order.CouponStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/2.
 * @version 1.0
 * @since 1.0
 */
@Service("cms.inviteAndCouponServiceImpl")
public class InviteAndCouponServiceImpl {


	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(InviteAndCouponServiceImpl.class);

	@Resource(name = "cms.inviteDao")
	private InviteDao inviteDao;


	@Resource(name = "cms.actCouponDao")
	private ActCouponDao actCouponDao;

	@Resource(name = "cms.userCouponDao")
	private UserCouponDao userCouponDao;

	@Resource(name = "cms.activityFullDao")
	private ActivityFullDao activityFullDao;

	@Resource(name = "cms.activityGroupReceiveLogDao")
	private ActivityGroupReceiveLogDao  activityGroupReceiveLogDao;
	
	@Resource(name = "cms.activityExtCouponDao")
    private ActivityExtCouponDao activityExtCouponDao;
    
    @Resource(name = "basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;
    
    @Resource(name="basedata.zkSysService")
    private ZkSysService zkSysService;

	@Resource(name = "cms.inviteServiceImpl")
	private InviteServiceImpl inviteServiceImpl;
    
	/**
	 * 接受邀请 并且给被邀请人送券
	 * @author  afi
	 * @param updateInvite
	 * @param groupSn
	 * @param dto
	 */
	public void acceptInviteAndCoupon(InviteEntity updateInvite,String groupSn, DataTransferObject dto) {
		if (Check.NuNObj(dto)){
			return;
		}
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}

		// 1、给被邀请人发优惠券
		dealCouponByGroupSn(groupSn,updateInvite.getUid(),3,dto);
		// 2、接受邀请
		if (dto.getCode() == DataTransferObject.SUCCESS){
			int num = inviteDao.updateInviteUid(updateInvite);
			if (num != 1) {
				LogUtil.error(LOGGER, "接受邀请失败,updateInvite:{}", JsonEntityTransform.Object2Json(updateInvite));
				throw new BusinessException("接受邀请失败");
			}
		}
	}


	/**
	 * 被邀请人下单后，给邀请人送券
	 * @param updateInvite
	 * @param groupSn
	 * @param dto
	 */
	public void giveInviterCoupon(InviteEntity updateInvite,String groupSn, DataTransferObject dto){
		if (Check.NuNObj(dto)){
			return;
		}
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}

		// 1、给邀请人发优惠券
		dealCouponByGroupSn(groupSn,updateInvite.getInviteUid(),3,dto);

		// 2、更新邀请状态
		if (dto.getCode() == DataTransferObject.SUCCESS){
			int num = inviteDao.updateStatusSendBack(updateInvite);
			if (num != 1) {
				LogUtil.error(LOGGER, "接受邀请失败,updateInvite:{}", JsonEntityTransform.Object2Json(updateInvite));
				throw new BusinessException("接受邀请失败");
			}
		}
	}


	/**
	 * 获取当前可用的优惠券金额
	 * @author afi
	 * @return
	 */
	public  ActCouponEntity getInviteCouponInfo(String groupSn){
		return actCouponDao.getOneActCouByGroupSn(groupSn);
	}

	/**
	 * 递归调用通过组号领取活动
	 * @param groupSn
	 * @param uid
	 * @param dealCount
	 * @param dto
	 */
	public void dealCouponByGroupSn(String groupSn,String uid,Integer dealCount,DataTransferObject dto){
		if (Check.NuNObj(dto)){
			return;
		}
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}
		if (Check.NuNStr(uid) || Check.NuNStr(groupSn)){
			
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

		ActCouponEntity actCouponEntity = actCouponDao.getOneActCouByGroupSn(groupSn);
		if (Check.NuNObj(actCouponEntity)){
			dto.setErrCode(CouponConst.COUPON_NO_MORE.getCode());
			dto.setMsg("当前优惠券已经被领完");
			return;
		}

		//获取当前的活动
		ActivityFullEntity activityFullEntity = activityFullDao.getActivityFullBySn(actCouponEntity.getActSn());
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
			//递归调用领取
			this.dealCouponByGroupSn( groupSn, uid,dealCount, dto);
			return;
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


		dto.putValue("coupon", actCouponEntity);
	}


	/**
	 * 根据 组内 和 活动号 给uid绑定多张优惠券
	 * @param groupSn
	 * @param uid
	 * @param dealCount
	 * @param dto
	 * @param listActSn
	 */
	public void dealManyCouponByGroupSn(String groupSn,String uid,DataTransferObject dto,List<String> listActSn,Integer dealCount){

		if (Check.NuNObj(dto)){
			return;
		}
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}
		if (Check.NuNStr(uid) || Check.NuNStr(groupSn)){
			dto.setErrCode(CouponConst.COUPON_PAR_ERROR.getCode());
			dto.setMsg(CouponConst.COUPON_PAR_ERROR.getName());
			return;
		}
		if (Check.NuNCollection(listActSn)){
			dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
			return;
		}

		if (ValueUtil.getintValue(dealCount) <= 0){
			dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
			dto.setMsg("网络超时请重试");
			return;
		}else {
			dealCount--;
		}
		List<ActCouponEntity> listActCouponEntity = new LinkedList<ActCouponEntity>();

		for (String actSn : listActSn){
			ActCouponEntity actCouponEntity = actCouponDao.getCouponByActSn(actSn);
			if (Check.NuNObj(actCouponEntity)){
				LogUtil.info(LOGGER, "【优惠卷已领完】actSn={},groupSn={},uid={}", actSn,groupSn,uid);
				continue;
			}
			//获取当前的活动
			ActivityFullEntity activityFullEntity = activityFullDao.getActivityFullBySn(actCouponEntity.getActSn());
			Date couponStartTime = null;
			Date couponEndTime = null;
			CouponTimeTypeEnum couponTimeType = CouponTimeTypeEnum.getByCode(activityFullEntity.getCouponTimeType());
			if (Check.NuNObj(couponTimeType)){
				dto.setErrCode(CouponConst.COUPON_TYPE_ERROR.getCode());
				dto.setMsg(CouponConst.COUPON_TYPE_ERROR.getName());
				LogUtil.info(LOGGER, "dto={},actSn={}",dto.toJsonString(),actSn);
				return;
			}

			if (couponTimeType.getCode() == CouponTimeTypeEnum.FIX.getCode()){
				if (Check.NuNObjs(actCouponEntity.getCouponStartTime(),actCouponEntity.getCouponEndTime())){
					dto.setErrCode(CouponConst.COUPON_INFO_ERROR.getCode());
					dto.setMsg(CouponConst.COUPON_INFO_ERROR.getName());
					LogUtil.info(LOGGER, "dto={},actSn={}",dto.toJsonString(),actSn);
					return ;
				}
				couponStartTime = actCouponEntity.getCouponStartTime();
				couponEndTime = actCouponEntity.getCouponEndTime();
			}else {
				if (ValueUtil.getintValue(activityFullEntity.getCouponTimeLast())<=0){
					dto.setErrCode(CouponConst.COUPON_TIME_ERROR.getCode());
					dto.setMsg(CouponConst.COUPON_TIME_ERROR.getName());
					LogUtil.info(LOGGER,"dto={},actSn={}", dto.toJsonString(),actSn);
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
			listActCouponEntity.add(coupon);
		}

		if(!Check.NuNCollection(listActCouponEntity)){
			for (ActCouponEntity actCoupon : listActCouponEntity) {
				int num = actCouponDao.updateCoupon(actCoupon);
				if(num != 1){
					LogUtil.error(LOGGER, "更新优惠券信息为已领取失败,num:{},actCoupon:{}", num, JsonEntityTransform.Object2Json(actCoupon));
					throw new BusinessException("更新优惠券信息为已领取失败");
				}else{
					this.actCouponNumWarn(actCoupon.getActSn());
				}
				// 插入绑定信息
				UserCouponEntity orderCoupon = new UserCouponEntity();
				orderCoupon.setCouponSn(actCoupon.getCouponSn());
				orderCoupon.setUid(uid);
				num = userCouponDao.saveUserCoupon(orderCoupon);
				if(num != 1){
					LogUtil.error(LOGGER, "插入绑定信息失败,num:{},orderCoupon:{}", num, orderCoupon);
					throw new BusinessException("插入绑定信息失败");
				}
			}

			//插入领取记录
			int  num =  activityGroupReceiveLogDao.insertActivityGroupReceiveLog(groupSn, null, uid);
			if(num!=1){
				LogUtil.error(LOGGER, "插入优惠券领取记录失败,groupSn={},num:{},uid={}",groupSn,num,uid);
				throw new BusinessException("根据uid领取优惠券失败");
			}
			/**
			 * yanb新版邀请活动
			 * 更新邀请状态
			if (groupSn.equals("BEIYAOQINGREN")) {
				InviteEntity invite = new InviteEntity();
				invite.setUid(uid);
				invite.setInviteSource(1);
				invite.setInviteStatus(1);
				Integer upResult=inviteDao.updateByUid(invite);
				if (upResult == 0) {
					LogUtil.error(LOGGER, "灌券更新邀请状态失败");
					throw new BusinessException("灌券更新邀请状态失败");
				}
			}
			 */

			//返回 领取的优惠卷礼包
			dto.putValue("listActCoupon", listActCouponEntity);
		}else{
			dto.setErrCode(CouponConst.COUPON_NO_MORE.getCode());
			dto.setMsg(CouponConst.COUPON_NO_MORE.getName());
			LogUtil.error(LOGGER, "【活动优惠卷已领取完】groupSn={},uid={}",groupSn,uid);
		}
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
	 * 接受邀请-灌券
	 * 新版邀请好友下单
	 *
	 * @param * @param 邀请人uid (可空), 被邀请人uid , 邀请码 ,  groupSn ,活动类别
	 * @author yanb
	 * @created 2017年12月14日 14:37:04
	 */
	public Integer acceptPullCouponByUid(Map paramMap) {
		//先灌券
		String groupSn = (String) paramMap.get("groupSn");
		String uid = (String) paramMap.get("uid");
		List listActSn = (List) paramMap.get("listActSn");
		DataTransferObject dto = (DataTransferObject) paramMap.get("dto");
		Integer dealCount = (Integer) paramMap.get("dealCount");
		String inviteUid = (String) paramMap.get("inviteUid");
		String inviteCode = (String) paramMap.get("inviteCode");
		Integer inviteSource = (Integer) paramMap.get("inviteSource");

		//先灌券
		dealManyCouponByGroupSn(groupSn, uid, dto, listActSn, dealCount);

		//再插入invite表
		InviteEntity invite = new InviteEntity();
		invite.setUid(uid);
		invite.setInviteUid(inviteUid);
		invite.setInviteSource(inviteSource);
		invite.setInviteStatus(InviteStatusEnum.SEND_OTHER.getCode());
		invite.setIsGiveInviterPoints(IsGiveInviterPointsEnum.NOT_YET_ADD.getCode());
		invite.setInviteCode(inviteCode);
		Integer result = inviteServiceImpl.saveInvite(invite);
		if (result == YesOrNoEnum.NO.getCode()) {
			LogUtil.error(LOGGER, "灌券更新邀请状态失败");
			throw new BusinessException("灌券更新邀请状态失败");
		}
		return result;
	}
}
