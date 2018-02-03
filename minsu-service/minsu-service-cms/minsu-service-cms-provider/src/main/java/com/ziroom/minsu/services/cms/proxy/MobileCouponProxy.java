package com.ziroom.minsu.services.cms.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActCouponEntity;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.entity.cms.ActivityGroupEntity;
import com.ziroom.minsu.entity.cms.InviterCodeEntity;
import com.ziroom.minsu.services.cms.api.inner.MobileCouponService;
import com.ziroom.minsu.services.cms.constant.CouponConst;
import com.ziroom.minsu.services.cms.dto.InviteCouponRequest;
import com.ziroom.minsu.services.cms.dto.InviteStateUidRequest;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
import com.ziroom.minsu.services.cms.service.*;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.cms.ActStatusEnum;
import com.ziroom.minsu.valenum.cms.GroupCouponTypeEnum;
import com.ziroom.minsu.valenum.cms.InviteStatusEnum;
import com.ziroom.minsu.valenum.cms.IsCouponEnum;
import com.ziroom.minsu.valenum.common.ErrorCodeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * @author afi on on 2016/12/9.
 * @version 1.0
 * @since 1.0
 */
@Service("cms.mobileCouponProxy")
public class MobileCouponProxy implements MobileCouponService {


	@Resource(name = "cms.actCouponServiceImpl")
	private ActCouponServiceImpl actCouponServiceImpl;

	@Resource(name = "cms.activityServiceImpl")
	private ActivityServiceImpl activityServiceImpl ;

	@Resource(name = "cms.activityGroupServiceImpl")
	private ActivityGroupServiceImpl activityGroupServiceImpl ;

	@Resource(name = "cms.inviteAndCouponServiceImpl")
	private InviteAndCouponServiceImpl inviteAndCouponServiceImpl;

	@Resource(name = "cms.activityGroupReceiveLogServiceImpl")
	private  ActivityGroupReceiveLogServiceImpl activityGroupReceiveLogServiceImpl;

	@Resource(name="cms.inviteCreateOrderCmsImpl")
	private InviteCreateOrderCmsImpl inviteCreateOrderCmsImpl;
	
	@Value("#{'${CUSTOMER_DETAIL_URL}'.trim()}")
	private  String CUSTOMER_DETAIL_URL;


	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MobileCouponProxy.class);





	/**
	 * 电话领取优惠券活动
	 * @author afi
	 * @param paramJson
	 * @return
	 */
	public String pullGroupCouponByMobile(String paramJson) {
		LogUtil.info(LOGGER, "pullGroupCouponByMobile 参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			MobileCouponRequest request = JsonEntityTransform.json2Object(paramJson, MobileCouponRequest.class);
			if (Check.NuNObj(request)) {
				LogUtil.info(LOGGER, "pullGroupCouponByMobile request :{}", JsonEntityTransform.Object2Json(request));
				dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
				dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
				return dto.toJsonString();
			}
			if( Check.NuNStr(request.getMobile())){
				dto.setErrCode(CouponConst.COUPON_MOBILE_NULL.getCode());
				dto.setMsg(CouponConst.COUPON_MOBILE_NULL.getName());
				return dto.toJsonString();
			}
			String groupSn = request.getGroupSn();
			if(Check.NuNStr(groupSn)){
				dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
				dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
				return dto.toJsonString();
			}
			List<ActivityEntity> activityEntities = activityServiceImpl.listActivityByGroupSn(groupSn);
			if (Check.NuNCollection(activityEntities)){
				LogUtil.info(LOGGER,"活动列表为空");
				dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
				dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
				return dto.toJsonString();
			}

			ActivityGroupEntity activityGroupEntity = activityGroupServiceImpl.getGroupBySN(groupSn);
			List<String> lastActSn = new LinkedList<String>();
			/*	if (activityGroupEntity.getIsRepeat() == YesOrNoEnum.NO.getCode()){
				//校验当前电话是否已经领取
				if(Integer.valueOf(String.valueOf(actCouponServiceImpl.getCountMobileGroupCouNum(request)))>0){
					dto.setErrCode(CouponConst.COUPON_HAS.getCode());
					dto.setMsg(CouponConst.COUPON_HAS.getName());
					return dto.toJsonString();
				}
			}else{
				//可以领取同一组内不同的
				//List<ActivityEntity> activityEntities = activityServiceImpl.listActivityByGroupSn(groupSn);
				List<String> actSns = actCouponServiceImpl.listActSnByGroupAndMobile(request);
				List<String> allAct = new ArrayList<>();
				for (ActivityEntity act: activityEntities){
					allAct.add(act.getActSn());
				}
				allAct.removeAll(actSns);
				lastActSn = allAct;
				//如果集合为空 说明活动都已领取
				if (Check.NuNCollection(lastActSn)){
					dto.setErrCode(CouponConst.COUPON_HAS.getCode());
					dto.setMsg(CouponConst.COUPON_HAS.getName());
					return dto.toJsonString();
				}
			}*/

			//组条件 校验
			if(!checkActGroupLimitNum(dto, lastActSn, activityGroupEntity, activityEntities, request,GroupCouponTypeEnum.MOBILE.getTypeCode())){
				return dto.toJsonString();
			}
			//递归调用领取优惠券
			this.forceGroupCouponByMobile(request,dto,lastActSn,SysConst.RETRIES);
			//领取成功,直接获取优惠券信息
			/*if (dto.getCode() == DataTransferObject.SUCCESS){
                String couponSn = ValueUtil.getStrValue(dto.getData().get("couponSn"));
                ActCouponEntity couponEntity = actCouponServiceImpl.getCouponBySn(couponSn);
                dto.putValue("coupon",couponEntity);
            }*/
		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
			dto.setMsg(CouponConst.COUPON_ERROR.getName());
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}



	/**
	 * 电话领取优惠券活动
	 * @author afi
	 * @param paramJson
	 * @return
	 */
	public String pullActCouponByMobile(String paramJson) {
		LogUtil.info(LOGGER, "pullCouponByMobile 参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			MobileCouponRequest request = JsonEntityTransform.json2Object(paramJson, MobileCouponRequest.class);
			if (Check.NuNObj(request)) {
				LogUtil.info(LOGGER, "pullCouponByMobile request :{}", JsonEntityTransform.Object2Json(request));
				dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
				dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
				return dto.toJsonString();
			}
			if( Check.NuNStr(request.getMobile())){
				dto.setErrCode(CouponConst.COUPON_MOBILE_NULL.getCode());
				dto.setMsg(CouponConst.COUPON_MOBILE_NULL.getName());
				return dto.toJsonString();
			}
			String actSn = request.getActSn();
			if(Check.NuNStr(actSn)){
				dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
				dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
				return dto.toJsonString();
			}
			ActivityEntity activity = activityServiceImpl.selectByActSn(actSn);
			if(Check.NuNObj(activity)){
				dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
				dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
				return dto.toJsonString();
			}

			if(activity.getActStatus() == ActStatusEnum.DISABLE.getCode()){
				LogUtil.info(LOGGER, "pullCouponByMobile 活动未开始 actSn:{},actStatus:{}", request.getActSn(),activity.getActStatus());
				dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
				dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
				return dto.toJsonString();
			}

			if(activity.getActStatus() == ActStatusEnum.END.getCode()){
				LogUtil.info(LOGGER, "pullCouponByMobile 活动已经结束 actSn:{},actStatus:{}", request.getActSn(),activity.getActStatus());
				dto.setErrCode(CouponConst.COUPON_ACT_FINIS.getCode());
				dto.setMsg(CouponConst.COUPON_ACT_FINIS.getName());
				return dto.toJsonString();
			}

			if(activity.getIsCoupon() == IsCouponEnum.NO.getCode() || activity.getIsCoupon() == IsCouponEnum.ING.getCode()){
				LogUtil.info(LOGGER, "pullCouponByMobile actSn:{},isCoupon:{}", request.getActSn(),activity.getIsCoupon());
				dto.setErrCode(CouponConst.COUPON_ACT_FINIS.getCode());
				dto.setMsg(CouponConst.COUPON_ACT_FINIS.getName());
				return dto.toJsonString();
			}

			//校验当前电话是否已经领取
			if(Integer.valueOf(String.valueOf(actCouponServiceImpl.getCountMobileActCouNum(request)))>0){
				dto.setErrCode(CouponConst.COUPON_HAS.getCode());
				dto.setMsg(CouponConst.COUPON_HAS.getName());
				return dto.toJsonString();
			}
			//递归调用领取优惠券
			this.forceActCouponByMobile(request,dto, SysConst.RETRIES);

			//领取成功,直接获取优惠券信息
			if (dto.getCode() == DataTransferObject.SUCCESS){
				String couponSn = ValueUtil.getStrValue(dto.getData().get("couponSn"));
				ActCouponEntity couponEntity = actCouponServiceImpl.getCouponBySn(couponSn);
				dto.putValue("coupon",couponEntity);
			}
		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
			dto.setMsg(CouponConst.COUPON_ERROR.getName());
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 强制给当前的电话分配一个有效的活动券
	 * @author afi
	 * @param request
	 * @param dto
	 * @param num
	 */
	private void forceActCouponByMobile(MobileCouponRequest request,DataTransferObject dto,int num){
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}
		if (num < 0){
			dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
			dto.setMsg(CouponConst.COUPON_ERROR.getName());
			return;
		}
		if (Check.NuNStr(request.getMobile()) && Check.NuNStr(request.getActSn())){
			dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
			dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
			return ;
		}
		ActCouponEntity actCouponEntity = actCouponServiceImpl.getAvailableCouponByActSn(request.getActSn());
		if (Check.NuNObj(actCouponEntity)){
			dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
			return;
		}
		//活动号
		String groupSn = null;
		if (!Check.NuNStr(actCouponEntity.getGroupSn())){
			groupSn = actCouponEntity.getGroupSn();
		}
		String couponSn = actCouponServiceImpl.forceMobileCouponOnly(request,actCouponEntity.getCouponSn(),actCouponEntity.getActSn(),groupSn);
		if (Check.NuNStr(couponSn)){
			//领取失败 再次领取
			forceActCouponByMobile(request,dto,num-1);
		}else {
			dto.putValue("couponSn",couponSn);
		}
	}


	/**
	 * 强制给当前的电话分配一个有效的活动券
	 *
	 *
	 * @author afi
	 * @param request
	 * @param dto
	 * @param num
	 * @param actSnList 如果组类型为一个手机号可以在不同的活动中领取，该参数为剩余可以领取的活动码
	 */
	private void forceGroupCouponByMobile(MobileCouponRequest request,DataTransferObject dto,List<String> actSnList,int num){
		this.actCouponServiceImpl.forceGroupCouponByMobile(request, dto, actSnList, num);
		/*ActCouponEntity actCouponEntity = null;
		List<ActCouponEntity> waitPullActList = new ArrayList<>();
		//如果actSnList为空 说明组内只能领一张，组内活动都互斥
		if (Check.NuNCollection(actSnList)||actSnList.size() == 0){
			actCouponEntity = actCouponServiceImpl.getOneActCouByGroupSn(request.getGroupSn());
		}else{
			//组内不互斥，领取组内未领取的活动的券，领取是一个list集合
			for (String actSn : actSnList){
				ActCouponEntity couponEntity = actCouponServiceImpl.getAvailableCouponByActSn(actSn);
				if (!Check.NuNObj(couponEntity)){
					waitPullActList.add(couponEntity);
				}
			}
			if (!Check.NuNCollection(waitPullActList)){
				actCouponEntity = waitPullActList.get(0);
			}
		}
		if (Check.NuNObj(actCouponEntity)){
			LogUtil.info(LOGGER,"优惠券获取为空，actSnList={}",JsonEntityTransform.Object2Json(actSnList));
			dto.setErrCode(CouponConst.COUPON_NO_MORE.getCode());
			dto.setMsg(CouponConst.COUPON_NO_MORE.getName());
			return;
		}

		//开始领券
		if (!Check.NuNCollection(waitPullActList)){
			List<ActCouponEntity> hasPullList = new ArrayList<>();
			//组码领取  把组内可用活动优惠券领取完毕，并返回优惠券信息
			for (ActCouponEntity actCoupon : waitPullActList){
				String couponSn = actCouponServiceImpl.forceMobileCouponOnly(request, actCoupon.getCouponSn(), actCoupon.getActSn(), request.getGroupSn());
				//领取成功以后移除数组，防止下次领取查询到相同活动优惠券
				actSnList.remove(actCoupon.getActSn());
				if (Check.NuNStr(couponSn)){
					forceGroupCouponByMobile(request,dto,actSnList,num-1);
				}else{
					hasPullList.add(actCoupon);
				}
			}
			//领取的优惠券返回
			dto.putValue("couponList",hasPullList);
			//兼容之前活动 返回第一个优惠券
			dto.putValue("coupon",actCouponEntity);
		}else{
			//组内互斥，只能领一个
			String couponSn = actCouponServiceImpl.forceMobileCouponOnly(request,actCouponEntity.getCouponSn(),actCouponEntity.getActSn(),request.getGroupSn());
			if (Check.NuNStr(couponSn)){
				//领取失败 再次领取
				forceGroupCouponByMobile(request,dto,actSnList,num-1);
			}else {
				dto.putValue("coupon",actCouponEntity);
			}

		}
		 */
	}


	/**
	 * 通过 uid 绑定优惠券活动组
	 * @author yd
	 * @param paramJson
	 * @return
	 */
	@Override
	public String pullGroupCouponByUid(String paramJson){

		LogUtil.info(LOGGER, "pullGroupCouponByUid 参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();

		MobileCouponRequest request = JsonEntityTransform.json2Object(paramJson, MobileCouponRequest.class);
		if (Check.NuNObj(request)) {
			LogUtil.info(LOGGER, "pullGroupCouponByUid request :{}", JsonEntityTransform.Object2Json(request));
			dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
			dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
			return dto.toJsonString();
		}
		if( Check.NuNStr(request.getUid())){
			dto.setErrCode(CouponConst.COUPON_UID_NULL.getCode());
			dto.setMsg(CouponConst.COUPON_UID_NULL.getName());
			return dto.toJsonString();
		}
		String groupSn = request.getGroupSn();


		if(Check.NuNStr(groupSn)){
			dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
			dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
			return dto.toJsonString();
		}
		ActivityGroupEntity activityGroupEntity = activityGroupServiceImpl.getGroupBySN(groupSn);

		if(Check.NuNObj(activityGroupEntity)){
			dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
			return dto.toJsonString();
		}
		List<ActivityEntity> activityEntities = activityServiceImpl.listActivityByGroupSn(groupSn);
		if (Check.NuNCollection(activityEntities)){
			LogUtil.info(LOGGER,"活动列表为空");
			dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
			return dto.toJsonString();
		}
		if (Check.NuNCollection(activityEntities)){
			LogUtil.info(LOGGER,"活动列表为空");
			dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
			return dto.toJsonString();
		}

		List<String> lastActSn = new LinkedList<String>();
		if(!checkActGroupLimitNum(dto, lastActSn, activityGroupEntity, activityEntities, request,GroupCouponTypeEnum.UID.getTypeCode())){
			return dto.toJsonString();
		}
		int dealCount = 3;
		//组内不互斥
		if(Check.NuNCollection(lastActSn)||lastActSn.size() == 0){
			inviteAndCouponServiceImpl.dealCouponByGroupSn(groupSn, request.getUid(), dealCount, dto);
		}else{
			dealManyCouponByGroupSn(groupSn, request.getUid(), dto, lastActSn,dealCount);
		}
		return dto.toJsonString();
	}

	@Override
	public String pullActCouponByUid(String paramJson) {
		LogUtil.info(LOGGER, "pullActCouponByUid 参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		MobileCouponRequest mobileCouponRequest = JsonEntityTransform.json2Object(paramJson, MobileCouponRequest.class);
		String actSn = mobileCouponRequest.getActSn();
		String uid = mobileCouponRequest.getUid();
		if (Check.NuNObj(mobileCouponRequest)) {
			dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
			dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
			return dto.toJsonString();
		}
		if( Check.NuNStr(uid)){
			dto.setErrCode(CouponConst.COUPON_UID_NULL.getCode());
			dto.setMsg(CouponConst.COUPON_UID_NULL.getName());
			return dto.toJsonString();
		}
		if( Check.NuNStr(mobileCouponRequest.getActSn())){
			dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
			return dto.toJsonString();
		}
		ActivityEntity activity = activityServiceImpl.selectByActSn(actSn);
		if(Check.NuNObj(activity)){
			dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
			return dto.toJsonString();
		}

		if(activity.getActStatus() == ActStatusEnum.DISABLE.getCode()){
			LogUtil.info(LOGGER, "pullActCouponByUid 活动未开始 actSn:{},actStatus:{}", actSn,activity.getActStatus());
			dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
			return dto.toJsonString();
		}

		if(activity.getActStatus() == ActStatusEnum.END.getCode()){
			LogUtil.info(LOGGER, "pullActCouponByUid 活动已经结束 actSn:{},actStatus:{}", actSn,activity.getActStatus());
			dto.setErrCode(CouponConst.COUPON_ACT_FINIS.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_FINIS.getName());
			return dto.toJsonString();
		}

		if(activity.getIsCoupon() == IsCouponEnum.NO.getCode() || activity.getIsCoupon() == IsCouponEnum.ING.getCode()){
			LogUtil.info(LOGGER, "pullActCouponByUid actSn:{},isCoupon:{}",actSn,activity.getIsCoupon());
			dto.setErrCode(CouponConst.COUPON_ACT_FINIS.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_FINIS.getName());
			return dto.toJsonString();
		}
		Integer times = activity.getTimes();
		long num = actCouponServiceImpl.countCouponNumByUidAndAct(uid, actSn,null);
		if (num >= times){
			dto.setErrCode(CouponConst.COUPON_UID_OVER_LIMITNUM.getCode());
			dto.setMsg(CouponConst.COUPON_UID_OVER_LIMITNUM.getName());
			return dto.toJsonString();
		}
		//领取优惠券 uid绑定
		ActCouponEntity actCouponEntity = actCouponServiceImpl.pullActCouponByUid(actSn, uid);
		if (Check.NuNObj(actCouponEntity)){
			dto.setErrCode(CouponConst.COUPON_NO_MORE.getCode());
			dto.setMsg(CouponConst.COUPON_NO_MORE.getName());
			return dto.toJsonString();
		}
		dto.putValue("coupon",actCouponEntity);
		return dto.toJsonString();
	}


	/**
	 * 
	 * 活动组领券
	 *
	 * @author yd
	 * @created 2017年6月1日 上午10:26:35
	 *
	 * @param groupSn
	 * @param uid
	 * @param dto
	 * @param listActSn
	 * @param dealCount
	 */
	private  void  dealManyCouponByGroupSn(String groupSn,String uid,DataTransferObject dto,List<String> listActSn,Integer dealCount){
		try {
			inviteAndCouponServiceImpl.dealManyCouponByGroupSn(groupSn, uid, dto, listActSn,dealCount);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【领取优惠券失败】在重新领取一次,uid:{},groupSn:{},dealCount:{},dto:{},e={}",uid, groupSn,dealCount,dto.toJsonString(),e);
			dealManyCouponByGroupSn(groupSn, uid, dto, listActSn, --dealCount);
		}
	}
	/**
	 * 
	 * 检验当前活动组 次数
	 *
	 * @author yd
	 * @created 2017年5月26日 下午3:26:54
	 *
	 * @param dto
	 * @param lastActSn
	 * @param activityGroupEntity
	 * @param activityEntities
	 * @param request
	 * @return
	 */
	public boolean  checkActGroupLimitNum(DataTransferObject dto,List<String> lastActSn,ActivityGroupEntity activityGroupEntity
			,List<ActivityEntity> activityEntities,MobileCouponRequest request,int groupCouponType){

		if(Check.NuNObjs(dto,lastActSn,activityGroupEntity,activityEntities,request)){
			dto.setErrCode(CouponConst.COUPON_PAR_ERROR.getCode());
			dto.setMsg(CouponConst.COUPON_PAR_ERROR.getName());
			return false;
		}

		if(Check.NuNStr(request.getMobile())
				&&Check.NuNStr(request.getUid())){
			dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
			dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
			return false;
		}

		GroupCouponTypeEnum  groupCouponTypeEnum = GroupCouponTypeEnum.getGroupCouponTypeEnumByCode(groupCouponType);
		if(groupCouponTypeEnum == null){
			LogUtil.error(LOGGER, "领取优惠券类型错误：groupSn={},groupCouponType={}",activityGroupEntity.getGroupSn(), groupCouponType);
			dto.setErrCode(CouponConst.COUPON_PAR_ERROR.getCode());
			dto.setMsg(CouponConst.COUPON_PAR_ERROR.getName());
			return false;
		}
		int  groupLimitNum = activityGroupEntity.getGroupLimitNum();

		//限制1次
		if(groupLimitNum == 1){
			if (activityGroupEntity.getIsRepeat() == YesOrNoEnum.NO.getCode()){
				//校验当前u是否已经领取
				if(groupCouponTypeEnum.getTypeCode() == GroupCouponTypeEnum.UID.getTypeCode()){
					if(Integer.valueOf(String.valueOf(actCouponServiceImpl.countUidAc(request)))>0){
						dto.setErrCode(CouponConst.COUPON_HAS.getCode());
						dto.setMsg(CouponConst.COUPON_HAS.getName());
						return false;
					}
				}

				if(groupCouponTypeEnum.getTypeCode() == GroupCouponTypeEnum.MOBILE.getTypeCode()){
					//校验当前电话是否已经领取
					if(Integer.valueOf(String.valueOf(actCouponServiceImpl.getCountMobileGroupCouNum(request)))>0){
						dto.setErrCode(CouponConst.COUPON_HAS.getCode());
						dto.setMsg(CouponConst.COUPON_HAS.getName());
						return false;
					}
				}
			}else{
				//可以领取同一组内不同的
				List<String> actSns  = null;
				if(groupCouponTypeEnum.getTypeCode() == GroupCouponTypeEnum.UID.getTypeCode()){
					actSns = actCouponServiceImpl.findActSnsByUid(request);
				}
				if(groupCouponTypeEnum.getTypeCode() == GroupCouponTypeEnum.MOBILE.getTypeCode()){
					actSns = actCouponServiceImpl.listActSnByGroupAndMobile(request);
				}
				List<String> allAct = new ArrayList<>();
				for (ActivityEntity act: activityEntities){
					allAct.add(act.getActSn());
				}
				allAct.removeAll(actSns);
				lastActSn.addAll(allAct);
				//如果集合为空 说明活动都已领取
				if (Check.NuNCollection(lastActSn)||lastActSn.size() == 0){
					dto.setErrCode(CouponConst.COUPON_HAS.getCode());
					dto.setMsg(CouponConst.COUPON_HAS.getName());
					return false;
				}
			}
		}
		//1. 限制领取多次   2. 不限制的情况
		if(groupLimitNum > 1 || groupLimitNum == 0){
			if(groupLimitNum > 1){
				int currNum = 0;
				if(groupCouponTypeEnum.getTypeCode() == GroupCouponTypeEnum.UID.getTypeCode()){
					currNum = activityGroupReceiveLogServiceImpl.countGroupReceiveLogByUidAndGroupSn(request.getGroupSn(), request.getUid());
				}
				if(groupCouponTypeEnum.getTypeCode() == GroupCouponTypeEnum.MOBILE.getTypeCode()){
					currNum = activityGroupReceiveLogServiceImpl.countGroupReceiveLogByMobileAndGroupSn(request.getGroupSn(), request.getMobile());
				}
				int unclaimedNum = groupLimitNum-currNum;
				if(unclaimedNum==0){
					dto.setErrCode(CouponConst.COUPON_UID_OVER_LIMITNUM.getCode());
					dto.setMsg(CouponConst.COUPON_UID_OVER_LIMITNUM.getName());
					return false;
				}
				if(unclaimedNum<0){
					throw new BusinessException("【uid领取优惠券异常】领取次数已超过最大限制currNum="+currNum+",groupLimitNum="+groupLimitNum+",groupSn="+request.getGroupSn()+",uid="+request.getUid());
				}
			}
			if(activityGroupEntity.getIsRepeat() == YesOrNoEnum.YES.getCode()){
				//可以领取同一组内不同的
				for (ActivityEntity act: activityEntities){
					lastActSn.add(act.getActSn());
				}
				//如果集合为空 说明活动都已领取
				if (Check.NuNCollection(lastActSn)||lastActSn.size() == 0){
					dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
					dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
					return false;
				}
			}
		}
		return true;
	}
	
	
	/**
	 * uid领卷返回排名接口
	 */
	@Override
	public String pullGroupCouponByUidRank(String paramJson){

		LogUtil.info(LOGGER, "pullGroupCouponByUid 参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();

		MobileCouponRequest request = JsonEntityTransform.json2Object(paramJson, MobileCouponRequest.class);
		if (Check.NuNObj(request)) {
			LogUtil.info(LOGGER, "pullGroupCouponByUid request :{}", JsonEntityTransform.Object2Json(request));
			dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
			dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
			return dto.toJsonString();
		}
		if( Check.NuNStr(request.getUid())){
			dto.setErrCode(CouponConst.COUPON_UID_NULL.getCode());
			dto.setMsg(CouponConst.COUPON_UID_NULL.getName());
			return dto.toJsonString();
		}
		String groupSn = request.getGroupSn();


		if(Check.NuNStr(groupSn)){
			dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
			dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
			return dto.toJsonString();
		}
		ActivityGroupEntity activityGroupEntity = activityGroupServiceImpl.getGroupBySN(groupSn);

		if(Check.NuNObj(activityGroupEntity)){
			dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
			return dto.toJsonString();
		}
		List<ActivityEntity> activityEntities = activityServiceImpl.listActivityByGroupSn(groupSn);
		if (Check.NuNCollection(activityEntities)){
			LogUtil.info(LOGGER,"活动列表为空");
			dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
			return dto.toJsonString();
		}
		if (Check.NuNCollection(activityEntities)){
			LogUtil.info(LOGGER,"活动列表为空");
			dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
			dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
			return dto.toJsonString();
		}

		List<String> lastActSn = new LinkedList<String>();
		if(!checkActGroupLimitNum(dto, lastActSn, activityGroupEntity, activityEntities, request,GroupCouponTypeEnum.UID.getTypeCode())){
			dto.putValue("rank", String.format("%05d", activityGroupReceiveLogServiceImpl.getMobileRank(groupSn, null, request.getUid())));
			//如果之前是手机号领取的，uid获取不到排名，需要查询手机号获取排名
			if("00000".equals(dto.getData().get("rank").toString())){
				try{
					//查询自如客头像和昵称
					StringBuffer url = new StringBuffer();
					url.append(CUSTOMER_DETAIL_URL).append(request.getUid());
					String getResult = CloseableHttpUtil.sendGet(url.toString(), null);
					LogUtil.info(LOGGER, "调用接口：{}，返回用户信息：{}",url.toString(),getResult);
					if(Check.NuNStrStrict(getResult)){
						LogUtil.error(LOGGER, "CUSTOMER_ERROR:根据用户uid={},获取用户信息失败", request.getUid());
					}
					Map<String, String> resultMap=new HashMap<String, String>();
					try {
						resultMap = (Map<String, String>) JsonEntityTransform.json2Map(getResult);
					} catch (Exception e) {
						LogUtil.info(LOGGER, "用户信息转化错误，请求url={}，返回结果result={}，e={}",url.toString(),getResult,e);
					}
					Object code = resultMap.get("error_code");
					if(Check.NuNObj(code)){
						LogUtil.error(LOGGER, "【获取用户手机号】获取用户头像错误code={}，请求url={}，返回结果result={}",code,url.toString(),getResult);
					}
					Map<String, String>  dataMap = (Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(resultMap.get("data")));
					dto.putValue("rank", String.format("%05d", activityGroupReceiveLogServiceImpl.getMobileRank(groupSn, dataMap.get("mobile"), null)));
				} catch (Exception e) {
					LogUtil.error(LOGGER, "uid获取排名失败e:{}",e);
				}
			}
			LogUtil.info(LOGGER, "uid领取优惠卷返回结果：{}",dto.toJsonString());
			return dto.toJsonString();
		}
		int dealCount = 3;
		//组内不互斥
		if(Check.NuNCollection(lastActSn)||lastActSn.size() == 0){
			inviteAndCouponServiceImpl.dealCouponByGroupSn(groupSn, request.getUid(), dealCount, dto);
		}else{
			dealManyCouponByGroupSn(groupSn, request.getUid(), dto, lastActSn,dealCount);
		}
		//获取排名
		dto.putValue("rank", String.format("%05d", activityGroupReceiveLogServiceImpl.getMobileRank(groupSn, null, request.getUid())));
		LogUtil.info(LOGGER, "uid领取优惠卷返回结果：{}",dto.toJsonString());
		return dto.toJsonString();
	}



	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.MobileCouponService#pullGroupCouponByMobileRank(java.lang.String)
	 */
	@Override
	public String pullGroupCouponByMobileRank(String paramJson) {
		LogUtil.info(LOGGER, "pullGroupCouponByMobile 参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			MobileCouponRequest request = JsonEntityTransform.json2Object(paramJson, MobileCouponRequest.class);
			if (Check.NuNObj(request)) {
				LogUtil.info(LOGGER, "pullGroupCouponByMobile request :{}", JsonEntityTransform.Object2Json(request));
				dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
				dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
				return dto.toJsonString();
			}
			if( Check.NuNStr(request.getMobile())){
				dto.setErrCode(CouponConst.COUPON_MOBILE_NULL.getCode());
				dto.setMsg(CouponConst.COUPON_MOBILE_NULL.getName());
				return dto.toJsonString();
			}
			String groupSn = request.getGroupSn();
			if(Check.NuNStr(groupSn)){
				dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
				dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
				return dto.toJsonString();
			}
			List<ActivityEntity> activityEntities = activityServiceImpl.listActivityByGroupSn(groupSn);
			if (Check.NuNCollection(activityEntities)){
				LogUtil.info(LOGGER,"活动列表为空");
				dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
				dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
				return dto.toJsonString();
			}

			ActivityGroupEntity activityGroupEntity = activityGroupServiceImpl.getGroupBySN(groupSn);
			List<String> lastActSn = new LinkedList<String>();
			/*	if (activityGroupEntity.getIsRepeat() == YesOrNoEnum.NO.getCode()){
				//校验当前电话是否已经领取
				if(Integer.valueOf(String.valueOf(actCouponServiceImpl.getCountMobileGroupCouNum(request)))>0){
					dto.setErrCode(CouponConst.COUPON_HAS.getCode());
					dto.setMsg(CouponConst.COUPON_HAS.getName());
					return dto.toJsonString();
				}
			}else{
				//可以领取同一组内不同的
				//List<ActivityEntity> activityEntities = activityServiceImpl.listActivityByGroupSn(groupSn);
				List<String> actSns = actCouponServiceImpl.listActSnByGroupAndMobile(request);
				List<String> allAct = new ArrayList<>();
				for (ActivityEntity act: activityEntities){
					allAct.add(act.getActSn());
				}
				allAct.removeAll(actSns);
				lastActSn = allAct;
				//如果集合为空 说明活动都已领取
				if (Check.NuNCollection(lastActSn)){
					dto.setErrCode(CouponConst.COUPON_HAS.getCode());
					dto.setMsg(CouponConst.COUPON_HAS.getName());
					return dto.toJsonString();
				}
			}*/

			//组条件 校验
			if(!checkActGroupLimitNum(dto, lastActSn, activityGroupEntity, activityEntities, request,GroupCouponTypeEnum.MOBILE.getTypeCode())){
				dto.putValue("rank", String.format("%05d", activityGroupReceiveLogServiceImpl.getMobileRank(groupSn, request.getMobile(), null)));
				return dto.toJsonString();
			}
			//递归调用领取优惠券
			this.forceGroupCouponByMobile(request,dto,lastActSn,SysConst.RETRIES);
			//领取成功,直接获取优惠券信息
			/*if (dto.getCode() == DataTransferObject.SUCCESS){
                String couponSn = ValueUtil.getStrValue(dto.getData().get("couponSn"));
                ActCouponEntity couponEntity = actCouponServiceImpl.getCouponBySn(couponSn);
                dto.putValue("coupon",couponEntity);
            }*/
			//领卷完成，获取排名
			dto.putValue("rank", String.format("%05d", activityGroupReceiveLogServiceImpl.getMobileRank(groupSn, request.getMobile(), null)));
		} catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
			dto.setMsg(CouponConst.COUPON_ERROR.getName());
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 接受邀请-灌券
	 * 新版邀请好友下单
	 * @author yanb
	 * @created 2017年12月14日 14:37:04
	 * @param  * @param 邀请人uid (可空), 被邀请人uid , 邀请码 ,  groupSn ,活动类别
	 * @return java.lang.String
	 */
	@Override
	public String acceptPullCouponByUid(String paramJson) {
		LogUtil.info(LOGGER, "acceptPullCouponByUid 参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			InviteCouponRequest inviteCoupon = JsonEntityTransform.json2Object(paramJson, InviteCouponRequest.class);
			if (Check.NuNObj(inviteCoupon)) {
				dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
				dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
				return dto.toJsonString();
			}

			/**
			 * 查询用户的uid是否已经参加过活动
			 * 若参加过则返回ErrCode=111007,Msg="已经参加过"
			 */
			InviteStateUidRequest inviteStateUidRequest=new InviteStateUidRequest();
			inviteStateUidRequest.setUid(inviteCoupon.getUid());
			inviteStateUidRequest.setInviteSource(inviteCoupon.getInviteSource());
			inviteStateUidRequest.setInviteStatus(InviteStatusEnum.SEND_OTHER.getCode());
			Integer userInviteState = activityServiceImpl.checkUserInviteStateByUid(inviteStateUidRequest);
			LogUtil.info(LOGGER, "[acceptPullCouponByUid] 查询用户的uid是否已经参加过活动 参数:{}", inviteStateUidRequest);
			if (userInviteState > 0) {
				dto.setErrCode(ErrorCodeEnum.fail.getCode());
				dto.setMsg(CouponConst.COUPON_EMPTY.getName());
				return dto.toJsonString();
			}
			/**
			 * 如果邀请人的uid为空,则根据邀请码查询uid
			 */
			if (Check.NuNStr(inviteCoupon.getInviteUid())) {
				InviterCodeEntity inviterCodeEntity = inviteCreateOrderCmsImpl.selectByInviteCode(inviteCoupon.getInviteCode());
				if (Check.NuNObj(inviterCodeEntity)) {
					LogUtil.info(LOGGER, "[acceptPullCouponByUid]根据邀请码查询邀请人 邀请码:{}", inviteCoupon.getInviteCode());
					dto.setErrCode(ErrorCodeEnum.fail.getCode());
					dto.setMsg(CouponConst.COUPON_CODE_NULL.getName());
					return dto.toJsonString();
				}
				inviteCoupon.setInviteUid(inviterCodeEntity.getInviteUid());
			}
			/**
			 * 判断是不是自己邀请自己
			 */
			if (inviteCoupon.getUid().equals(inviteCoupon.getInviteUid())) {
				LogUtil.info(LOGGER, "自己邀请自己 用户uid :{}", inviteCoupon.getUid());
				dto.setErrCode(ErrorCodeEnum.fail.getCode());
				dto.setMsg("不能邀请自己");
				return dto.toJsonString();
			}

			/**
			 * 调用灌券并插入邀请表的方法
			 */
			MobileCouponRequest mobileCouponRequest = new MobileCouponRequest();
			mobileCouponRequest.setUid(inviteCoupon.getUid());
			if (!Check.NuNStr(inviteCoupon.getGroupSn())){
				mobileCouponRequest.setGroupSn(inviteCoupon.getGroupSn());
			}
			//默认是活动
			mobileCouponRequest.setSourceType(inviteCoupon.getInviteSource());

			if (Check.NuNObj(mobileCouponRequest)) {
				LogUtil.info(LOGGER, "根据uid灌券 request :{}", JsonEntityTransform.Object2Json(mobileCouponRequest));
				dto.setErrCode(ErrorCodeEnum.fail.getCode());
				dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
				return dto.toJsonString();
			}
			if( Check.NuNStr(mobileCouponRequest.getUid())){
				dto.setErrCode(ErrorCodeEnum.fail.getCode());
				dto.setMsg(CouponConst.COUPON_UID_NULL.getName());
				return dto.toJsonString();
			}
			String groupSn = mobileCouponRequest.getGroupSn();


			if(Check.NuNStr(groupSn)){
				dto.setErrCode(ErrorCodeEnum.fail.getCode());
				dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
				return dto.toJsonString();
			}
			ActivityGroupEntity activityGroupEntity = activityGroupServiceImpl.getGroupBySN(groupSn);

			if(Check.NuNObj(activityGroupEntity)){
				dto.setErrCode(ErrorCodeEnum.fail.getCode());
				dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
				return dto.toJsonString();
			}
			List<ActivityEntity> activityEntities = activityServiceImpl.listActivityByGroupSn(groupSn);
			if (Check.NuNCollection(activityEntities)){
				LogUtil.info(LOGGER,"活动列表为空");
				dto.setErrCode(ErrorCodeEnum.fail.getCode());
				dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
				return dto.toJsonString();
			}
			if (Check.NuNCollection(activityEntities)){
				LogUtil.info(LOGGER,"活动列表为空");
				dto.setErrCode(ErrorCodeEnum.fail.getCode());
				dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
				return dto.toJsonString();
			}

			List<String> listActSn = new LinkedList<String>();
			if(!checkActGroupLimitNum(dto, listActSn, activityGroupEntity, activityEntities, mobileCouponRequest,GroupCouponTypeEnum.UID.getTypeCode())){
				return dto.toJsonString();
			}
			int dealCount = 3;
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("groupSn", groupSn);
			paramMap.put("uid", mobileCouponRequest.getUid());
			paramMap.put("dto", dto);
			paramMap.put("listActSn",listActSn );
			paramMap.put("dealCount",dealCount );
			paramMap.put("inviteUid", inviteCoupon.getInviteUid());
			paramMap.put("inviteCode", inviteCoupon.getInviteCode());
			paramMap.put("inviteSource", inviteCoupon.getInviteSource());

			Integer result = inviteAndCouponServiceImpl.acceptPullCouponByUid(paramMap);
			if (result == 0) {
				dto.setErrCode(ErrorCodeEnum.fail.getCode());
				dto.setMsg(CouponConst.COUPON_ERROR.getName());
			}
			List<ActCouponEntity> listActCoupon= (List) dto.getData().get("listActCoupon");
			if (Check.NuNCollection(listActCoupon)) {
				dto.setErrCode(ErrorCodeEnum.fail.getCode());
			} else {
				ActCouponEntity actCouponEntity = listActCoupon.get(0);
				dto.putValue("coupon", actCouponEntity);
			}
			dto.getData().remove("listActCoupon");
			dto.putValue("inviteUid",inviteCoupon.getInviteUid());
		} catch(Exception e){
			LogUtil.error(LOGGER, "error接受邀请-灌券发生异常:{}", e);
			dto.setErrCode(ErrorCodeEnum.fail.getCode());
			dto.setMsg(CouponConst.COUPON_ERROR.getName());
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
}



