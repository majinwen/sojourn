package com.ziroom.minsu.services.cms.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.*;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.constant.CouponConst;
import com.ziroom.minsu.services.cms.dao.*;
import com.ziroom.minsu.services.cms.dto.*;
import com.ziroom.minsu.services.cms.entity.ActCouponInfoUserVo;
import com.ziroom.minsu.services.cms.entity.CouponUserUidVo;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.cms.CouponTimeTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.order.CouponStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>优惠券</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/8
 * @version 1.0
 * @since 1.0
 */
@Service("cms.actCouponServiceImpl")
public class ActCouponServiceImpl {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ActCouponServiceImpl.class);

	@Resource(name = "cms.actCouponDao")
	private ActCouponDao actCouponDao;

	@Resource(name = "cms.activityRecordDao")
	private ActivityRecordDao activityRecordDao;

	@Resource(name = "cms.couponMobileLogDao")
	private CouponMobileLogDao couponMobileLogDao;


	@Resource(name = "cms.userCouponDao")
	private UserCouponDao userCouponDao;


	@Resource(name = "cms.activityCityDao")
	private ActivityCityDao activityCityDao;

	@Resource(name = "cms.activityFullDao")
	private ActivityFullDao activityFullDao;

	@Resource(name = "cms.activityHouseDao")
	private ActivityHouseDao activityHouseDao;
	
	@Resource(name = "cms.activityGroupReceiveLogDao")
	private ActivityGroupReceiveLogDao  activityGroupReceiveLogDao;
	
	
	@Resource(name = "cms.activityExtCouponDao")
    private ActivityExtCouponDao activityExtCouponDao;
    
    @Resource(name = "basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;
    
    @Resource(name="basedata.zkSysService")
    private ZkSysService zkSysService;

	@Resource(name = "cms.couponOperateLogDao")
	private CouponOperateLogDao couponOperateLogDao;

	/**
	 * 获取优惠券信息
	 * @author lishaochuan
	 * @create 2016年6月8日下午8:36:59
	 * @param couponSn
	 * @return
	 */
	public ActCouponEntity getCouponBySn(String couponSn) {
		if (Check.NuNStr(couponSn)){
			return null;
		}
		return actCouponDao.getCouponBySn(couponSn);
	}


	/**
	 * 获取当前优惠券列表
	 * @author afi
	 * @param actCouponRequest
	 * @return
	 */
	public PagingResult<ActCouponUserEntity> getCouponFullList(ActCouponRequest actCouponRequest){
		return actCouponDao.getCouponFullList(actCouponRequest);
	}

	/**
	 * 通过活动编号获取优惠券列表
	 * @author lishaochuan
	 * @create 2016年6月8日下午8:46:53
	 * @param request
	 * @return
	 */
	public PagingResult<ActCouponEntity> getCouponListByActSn(ActCouponRequest request){
		return actCouponDao.getCouponListByActSn(request);
	}


	/**
	 * 获取当前活动下的所有优惠券
	 * 包括已经删除或者无效的
	 * @author afi
	 * @create 2016年9月8日下
	 * @param request
	 * @return
	 */
	public PagingResult<ActCouponEntity> getCouponListAllByActSn(ActCouponRequest request){
		return actCouponDao.getCouponListAllByActSn(request);
	}

	/**
	 * 根据组号和活动号查询领取过的活动号
	 * @param request
	 * @return
	 */
	public List<String> listActSnByGroupAndMobile(MobileCouponRequest request){
		return couponMobileLogDao.listActSnByGroup(request);
	}

	/**
	 * 通过活动编号获取优惠券列表
	 * @author lishaochuan
	 * @create 2016年6月8日下午8:46:53
	 * @param request
	 * @return
	 */
	public PagingResult<ActCouponUserEntity> getCouponListByUid(OutCouponRequest request){
		if (Check.NuNObj(request)){
			return new PagingResult<>(0L,new ArrayList<ActCouponUserEntity>());
		}
		if (Check.NuNObj(request.getStatus())){
			request.setStatus("3");
		}
		return actCouponDao.getCouponListByUid(request);
	}



	/**
	 * 通过活动编号获取优惠券列表
	 * @author afi
	 * @param request
	 * @return
	 */
	public PagingResult<ActCouponUserEntity> getCouponPageCheckByUid(CheckCouponRequest request){
		return actCouponDao.getCouponPageCheckByUid(request);
	}
	/**
	 * 通过活动编号获取优惠券列表
	 * @author afi
	 * @param request
	 * @return
	 */
	public List<ActCouponUserEntity> getCouponListCheckByUid(CheckCouponRequest request){
		return actCouponDao.getCouponListCheckByUid(request);
	}

	/**
	 * 获取当前的免天券 用户计算当前的默认优惠券
	 * @author afi
	 * @param request
	 * @return
	 */
	public List<ActCouponUserEntity> getCouponListCheckByUidDefault(CheckCouponRequest request){
		return actCouponDao.getCouponListCheckByUidDefault(request);
	}




	/**
	 * 保存优惠券信息
	 * @author afi
	 * @param actCouponEntity
	 * @return
	 */
	public int saveCoupon(ActCouponEntity actCouponEntity){
		return actCouponDao.saveCoupon(actCouponEntity);
	}

	/**
	 * 修改优惠券信息、优惠券绑定信息
	 * @author lishaochuan
	 * @create 2016年6月16日下午3:41:30
	 * @param couponList
	 */
	public void updateCouponList(List<ActCouponEntity> couponList, List<UserCouponEntity> orderCouponList){
		for (ActCouponEntity actCouponEntity : couponList) {
			int isSuccess = actCouponDao.updateCoupon(actCouponEntity);
			if(isSuccess == 1){
				this.actCouponNumWarn(actCouponEntity.getActSn());
			}
		}
		for (UserCouponEntity userCouponEntity : orderCouponList) {
			userCouponDao.updateByCouponSn(userCouponEntity);
		}
	}

	/**
	 * 通过优惠券号获取 优惠券信息, 城市信息 房源信息
	 * @author lishaochuan
	 * @create 2016年6月14日下午7:50:54
	 * @param couponSn
	 * @return
	 */
	public ActCouponUserEntity getActCouponUserVoByCouponSn(String couponSn){
		ActCouponUserEntity actCouponUserEntity = actCouponDao.getActCouponUserVoByCouponSn(couponSn);
		if (!Check.NuNObj(actCouponUserEntity)){
			actCouponUserEntity.setCityList(activityCityDao.getActivityCitiesByActSn(actCouponUserEntity.getActSn()));
		}
		if (!Check.NuNObj(actCouponUserEntity) && actCouponUserEntity.getIsLimitHouse() == YesOrNoEnum.YES.getCode()){
			actCouponUserEntity.setLimitHouseList(activityHouseDao.findHouseByActsn(actCouponUserEntity.getActSn()));
		}
		return actCouponUserEntity;
	}

	/**
	 * 根据活动编号查询限制的房源
	 * @author jixd
	 * @created 2016年11月18日 11:25:08
	 * @param
	 * @return
	 */
	public List<ActivityHouseEntity> findHouseByActsn(String actSn){
		return activityHouseDao.findHouseByActsn(actSn);
	}

	/**
	 * 当前活动开通的城市
	 * @author lishaochuan
	 * @create 2016年6月23日下午8:08:26
	 * @return
	 */
	public List<ActivityCityEntity> getActivityCitiesByActSn(String actSn) {
		if (Check.NuNStr(actSn)) {
			return null;
		}
		return activityCityDao.getActivityCitiesByActSn(actSn);
	}


	/**
	 * 通过优惠券号获取 活动信息 、优惠券信息
	 * @author lishaochuan
	 * @create 2016年6月15日下午2:20:01
	 * @param couponSn
	 * @return
	 */
	public ActCouponInfoUserVo getActCouponInfoVoByCouponSn(String couponSn){
		return actCouponDao.getActCouponInfoVoByCouponSn(couponSn);
	}

	/**
	 * 获取用户当前活动下的优惠券信息
	 * @author lishaochuan
	 * @create 2016年6月15日下午4:05:51
	 * @param actSn
	 * @param uid
	 * @return
	 */
	public List<ActCouponUserEntity> getCouponListByActUid(String actSn, String uid){
		return actCouponDao.getCouponListByActUid(actSn, uid);
	}

	/**
	 * 绑定优惠券
	 * @author lishaochuan
	 * @create 2016年6月15日下午5:42:13
	 * @param couponSn
	 * @param uid
	 */
	public void updateBindCoupon(String couponSn, String uid,Date couponStartTime,Date couponEndTime) {
		// 更新优惠券信息为已领取
		ActCouponEntity coupon = new ActCouponEntity();
		coupon.setCouponSn(couponSn);
		coupon.setCouponStatus(CouponStatusEnum.GET.getCode());
		coupon.setOldStatus(CouponStatusEnum.WAIT.getCode());
		if (!Check.NuNObj(couponStartTime)){
			coupon.setCouponStartTime(couponStartTime);
		}
		if (!Check.NuNObj(couponEndTime)){
			coupon.setCouponEndTime(couponEndTime);
		}
		int num = actCouponDao.updateCoupon(coupon);
		if(num != 1){
			LogUtil.error(LOGGER, "更新优惠券信息为已领取失败,num:{},coupon:{}", num, coupon);
			throw new BusinessException("更新优惠券信息为已领取失败");
		}else{
			ActCouponEntity actCouponEntity =  actCouponDao.getCouponBySn(couponSn);
			this.actCouponNumWarn(actCouponEntity.getActSn());
		}
		// 插入绑定信息
		UserCouponEntity orderCoupon = new UserCouponEntity();
		orderCoupon.setCouponSn(couponSn);
		orderCoupon.setUid(uid);
		num = userCouponDao.saveUserCoupon(orderCoupon);
		if(num != 1){
			LogUtil.error(LOGGER, "插入绑定信息失败,num:{},orderCoupon:{}", num, orderCoupon);
			throw new BusinessException("插入绑定信息失败");
		}
	}


	/**
	 * 获取未绑定uid的优惠券
	 * @author lisc
	 * @param customerMobile
	 * @return
	 */
	public List<CouponMobileLogEntity> getNotBindCouponMobile(String customerMobile){
		if (Check.NuNObj(customerMobile)){
			LogUtil.error(LOGGER, "getNotBindCouponMobile参数为空:{}", customerMobile);
			throw new BusinessException("getNotBindCouponMobile参数为空");
		}

		return couponMobileLogDao.getNotBindCouponMobile(customerMobile);
	}


	/**
	 * 根据手机号绑定优惠券
	 * @author lisc
	 * @param userCoupons
	 */
	public void bindCouponByMobile(List<UserCouponEntity> userCoupons){
		for (UserCouponEntity userCoupon : userCoupons) {
			int num = userCouponDao.saveUserCouponIgnore(userCoupon);
			if(num == 1){
				actCouponDao.updateCouponMobileStatus(userCoupon.getCouponSn());
			}
		}
	}


	/**
	 * 获取已过期未使用的优惠券count
	 * @author lishaochuan
	 * @create 2016年6月16日下午7:49:02
	 * @return
	 */
	public Long getExpireCount(){
		return actCouponDao.getExpireCount();
	}

	/**
	 * 修改优惠券过期
	 * @author lishaochuan
	 * @create 2016年6月16日下午7:49:10
	 * @param limit
	 * @return
	 */
	public int updateExpireList(int limit){
		return actCouponDao.updateExpireList(limit);
	}




	/**
	 * 校验当前的电话是否已经领取过当前活动
	 * @author afi
	 * @return
	 */
	public Long getCountMobileActCouNum(MobileCouponRequest request){
		if(Check.NuNObj(request)){
			LogUtil.error(LOGGER, "getCountMobileActCouNum request:{}", JsonEntityTransform.Object2Json(request));
			throw new BusinessException("领取电话为空，par:{}"+JsonEntityTransform.Object2Json(request));
		}
		if(Check.NuNStr(request.getActSn())){
			LogUtil.error(LOGGER, "getCountMobileActCouNum request:{}", JsonEntityTransform.Object2Json(request));
			throw new BusinessException("请求参数actSn为空");
		}
		if(Check.NuNStr(request.getMobile())){
			LogUtil.error(LOGGER, "getCountMobileActCouNum request:{}", JsonEntityTransform.Object2Json(request));
			throw new BusinessException("电话为空");
		}
		Long result =couponMobileLogDao.countMobileAc(request);
		return result;
	}

	/**
	 * 根据fid查询手机领取记录
	 * @param fid
	 * @return
	 */
	public CouponMobileLogEntity getCouponMobileLogByFid(String fid){
		return couponMobileLogDao.getCouponMobileLogByFid(fid);
	}


	//    /**
	//     * 校验当前的未领取数量
	//     * @author afi
	//     * @return
	//     */
	//    public Long aa getNoExchangeCountByGroupSn(String groupSn){
	//
	//        if(Check.NuNStr(groupSn)){
	//            LogUtil.error(LOGGER, "getNoCouponCountByGroupSn groupSn:{}",groupSn);
	//            throw new BusinessException("请求参数为空");
	//        }
	//
	//        Long result =couponMobileLogDao.getNoExchangeCountByGroupSn(groupSn);
	//        return result;
	//    }



	/**
	 * 校验当前的电话是否已经领取过当前活动
	 * @author afi
	 * @return
	 */
	public Long getCountMobileGroupCouNum(MobileCouponRequest request){
		if(Check.NuNObj(request)){
			LogUtil.error(LOGGER, "getCountMobileGroupCouNum request:{}", JsonEntityTransform.Object2Json(request));
			throw new BusinessException("领取电话为空par:{}"+JsonEntityTransform.Object2Json(request));
		}
		if(Check.NuNStr(request.getGroupSn())){
			LogUtil.error(LOGGER, "getCountMobileGroupCouNum request:{}", JsonEntityTransform.Object2Json(request));
			throw new BusinessException("请求参数groupSn为空");
		}
		if(Check.NuNStr(request.getMobile())){
			LogUtil.error(LOGGER, "getCountMobileGroupCouNum request:{}", JsonEntityTransform.Object2Json(request));
			throw new BusinessException("电话为空");
		}
		Long result =couponMobileLogDao.countMobileGroup(request);
		return result;
	}

	/**
	 * 查询多个组内的活动领取数量
	 * @param request
	 * @return
	 */
	public Long countMobileGroupSns(MobileCouponRequest request){
		return couponMobileLogDao.countMobileGroupSns(request);
	}

	/**
	 * 校验用户是否已经领取活动的优惠券
	 * @author liyingjie
	 * @create 2016年7月21日下午7:42:37
	 * @return
	 */
	public Long getCountUserActCouNum(BindCouponRequest request){
		if(Check.NuNObj(request)){
			LogUtil.error(LOGGER, "getCountUserActCouNum request:{}", JsonEntityTransform.Object2Json(request));
			throw new BusinessException("校验是否兑换过活动请求参数为空");
		}
		if(Check.NuNStr(request.getActSn())){
			LogUtil.error(LOGGER, "getCountUserActCouNum actSn:{}", request.getActSn());
			throw new BusinessException("请求参数actSn为空");
		}

		if(Check.NuNStr(request.getUid())){
			LogUtil.error(LOGGER, "getCountUserActCouNum uid:{}", request.getUid());
			throw new BusinessException("请求参数uid为空");
		}
		Long result = actCouponDao.getCountUserActCouNum(request);
		return result;
	}


	/**
	 * 检验当前用户是否参加了当前组
	 * @author afi
	 * @create 2016年10月21日下午7:42:37
	 * @return
	 */
	public Long getCountUserGroupCouNum(BindCouponRequest request){
		if(Check.NuNObj(request)){
			LogUtil.error(LOGGER, "getCountUserGroupCouNum request:{}", JsonEntityTransform.Object2Json(request));
			throw new BusinessException("校验是否兑换过活动请求参数为空");
		}
		if(Check.NuNStr(request.getGroupSn())){
			LogUtil.error(LOGGER, "getCountUserGroupCouNum groupSn:{}", request.getGroupSn());
			throw new BusinessException("请求参数为空");
		}
		if(Check.NuNStr(request.getUid())){
			LogUtil.error(LOGGER, "getCountUserGroupCouNum uid:{}", request.getUid());
			throw new BusinessException("请求参数为空");
		}
		return actCouponDao.getCountUserGroupUidNum(request);
	}





	/**
	 * 获取但前组内的的一张优惠券
	 * @author afi
	 * @create 2016年10月21日下午7:42:37
	 * @return
	 */
	public ActCouponEntity getOneActCouByGroupSn(String groupSn){
		if(Check.NuNStr(groupSn)){
			LogUtil.error(LOGGER, "getOneActCouByGroupSn groupSn:{}", groupSn);
			throw new BusinessException("请求参数为空");
		}
		return actCouponDao.getOneActCouByGroupSn(groupSn);
	}

	/**
	 * 取但前组内的的一张优惠券
	 * @author jixd
	 * @created 2017年03月02日 16:34:25
	 * @param
	 * @return
	 */
	public ActCouponEntity getActCouByGroupSnAndActList(String groupSn,List<String> actSns){
		return actCouponDao.getActCouByGroupSnAndActList(groupSn,actSns);
	}

	/**
	 * 获取但前活动的一张优惠券
	 * @author afi
	 * @create 2016年10月21日下午7:42:37
	 * @return
	 */
	public ActCouponEntity masterForUpdateOfgetCouponByActSn(String actSn){
		if(Check.NuNStr(actSn)){
			LogUtil.error(LOGGER, "getCouponByActSn actSn:{}", actSn);
			throw new BusinessException("请求参数actSn为空");
		}
		return actCouponDao.getCouponByActSn(actSn);
	}

	/**
	 * 获取当前未领取的优惠券券
	 * @author afi
	 * @create
	 * @return
	 */
	public ActCouponEntity getAvailableCouponByActSn(String actSn){
		if(Check.NuNStr(actSn)){
			LogUtil.error(LOGGER, "getAvailableCouponByActSn actSn:{}", actSn);
			throw new BusinessException("请求参数actSn为空");
		}
		return actCouponDao.getAvailableCouponByActSn(actSn);
	}



	/**
	 * 获取当前未领取的数量
	 * @author afi
	 * @create
	 * @return
	 */
	public Long getNoExchangeCountByGroupSn(String groupSn){
		if(Check.NuNStr(groupSn)){
			LogUtil.error(LOGGER, "getNoExchangeCountByGroupSn groupSn:{}",groupSn);
			throw new BusinessException("请求参数为空");
		}
		return actCouponDao.getNoExchangeCountByGroupSn(groupSn);
	}

	/**
	 * 查询可用优惠券数量
	 * @author jixd
	 * @created 2017年02月17日 16:33:58
	 * @param
	 * @return
	 */
	public Long countAvaliableCouponByActSn(String actSn){
		if(Check.NuNStr(actSn)){
			LogUtil.error(LOGGER, "getNoExchangeCountByGroupSn groupSn:{}",actSn);
			throw new BusinessException("请求参数为空");
		}
		return actCouponDao.countAvaliableCouponByActSn(actSn);
	}

	/**
	 * 强制绑定当前的电话占用
	 * @author afi
	 * @param request
	 * @param couponSn
	 * @return
	 */
	public String forceMobileCouponOnly(MobileCouponRequest request,String couponSn,String actSn,String groupSn){
		if (Check.NuNStr(couponSn)){
			throw new BusinessException("请求参数为空");
		}

		if (Check.NuNObj(request)){
			throw new BusinessException("请求参数为空");
		}
		if ((Check.NuNStr(request.getActSn()) && Check.NuNStr(request.getGroupSn()))|| Check.NuNStr(request.getMobile())){
			throw new BusinessException("请求参数为空");
		}

		ActivityFullEntity activityFullEntity = activityFullDao.getActivityFullBySn(actSn);
		Integer couponTimeType = activityFullEntity.getCouponTimeType();
		int rst = 0;
		if (couponTimeType == CouponTimeTypeEnum.COUNT.getCode()){
			Integer limitLast = activityFullEntity.getCouponTimeLast();
			Date couponStartTime = DateSplitUtil.jumpMinute(new Date(), -10);
			//获取当前的
			Date couponEndTime = DateSplitUtil.jumpDate(couponStartTime, ValueUtil.getintValue(limitLast));
			couponEndTime = DateSplitUtil.jumpMinute(couponEndTime, -11);
			//更新优惠券时间
			ActCouponEntity actCouponEntity = new ActCouponEntity();
			actCouponEntity.setCouponSn(couponSn);
			actCouponEntity.setCouponStatus(CouponStatusEnum.SEND.getCode());
			actCouponEntity.setCouponStartTime(couponStartTime);
			actCouponEntity.setCouponEndTime(couponEndTime);

			rst = actCouponDao.updateCoupon(actCouponEntity);

		}else{
			rst = actCouponDao.updateCouponMobile(couponSn);
		}
		if (rst > 0){
			if(!Check.NuNStr(actSn)){
				this.actCouponNumWarn(actSn);
			}else{
				ActCouponEntity actCouponEntity =  actCouponDao.getCouponBySn(couponSn);
				this.actCouponNumWarn(actCouponEntity.getActSn());
			}
			
			CouponMobileLogEntity couponMobileLogEntity = new CouponMobileLogEntity();
			//处理  手机号和订单号可以领取一个优惠券逻辑
			String orderSn = request.getOrderSn();
			if (!Check.NuNStr(orderSn)){
				couponMobileLogEntity.setOrderSn(orderSn);
				String sed = request.getMobile() + orderSn;
				//校验是否存在  存在 直接返回
				CouponMobileLogEntity couponMobileLogOld=this.couponMobileLogDao.getCouponMobileLogByFid(MD5Util.MD5Encode(sed));
				if(!Check.NuNObj(couponMobileLogOld)){
					LogUtil.info(LOGGER, "【重复发券】:orderSn={},mobile={},fid={}", orderSn,request.getMobile(),MD5Util.MD5Encode(sed));
					return couponSn;
				}
				couponMobileLogEntity.setFid(MD5Util.MD5Encode(sed));
			}

			couponMobileLogEntity.setActSn(actSn);
			couponMobileLogEntity.setCustomerMobile(request.getMobile());
			couponMobileLogEntity.setSourceType(request.getSourceType());
			couponMobileLogEntity.setCouponSn(couponSn);
			couponMobileLogEntity.setGroupSn(groupSn);
			couponMobileLogDao.saveMobileCoupon(couponMobileLogEntity);
			
			
			return couponSn;
		}else {
			return "";
		}
	}

	
	/**
	 * 
	 * 手机号 强制 根据组 领取优惠券
	 *
	 * @author yd
	 * @created 2017年5月27日 上午11:41:18
	 *
	 * @param request
	 * @param dto
	 * @param actSnList
	 * @param num
	 */
	public  void forceGroupCouponByMobile(MobileCouponRequest request,DataTransferObject dto,List<String> actSnList,int num){
		
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}
		if (num < 0){
			dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
			dto.setMsg(CouponConst.COUPON_ERROR.getName());
			return;
		}
		if (Check.NuNStr(request.getMobile()) && Check.NuNStr(request.getGroupSn())){
			dto.setErrCode(CouponConst.COUPON_PAR_NO.getCode());
			dto.setMsg(CouponConst.COUPON_PAR_NO.getName());
			return ;
		}
		ActCouponEntity actCouponEntity = null;
		List<ActCouponEntity> waitPullActList = new ArrayList<>();
		//如果actSnList为空 说明组内只能领一张，组内活动都互斥
		if (Check.NuNCollection(actSnList)||actSnList.size() == 0){
			actCouponEntity = getOneActCouByGroupSn(request.getGroupSn());
		}else{
			//组内不互斥，领取组内未领取的活动的券，领取是一个list集合
			for (String actSn : actSnList){
				ActCouponEntity couponEntity = getAvailableCouponByActSn(actSn);
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
				String couponSn = forceMobileCouponOnly(request, actCoupon.getCouponSn(), actCoupon.getActSn(), request.getGroupSn());
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
			String couponSn = forceMobileCouponOnly(request,actCouponEntity.getCouponSn(),actCouponEntity.getActSn(),request.getGroupSn());
			if (Check.NuNStr(couponSn)){
				//领取失败 再次领取
				forceGroupCouponByMobile(request,dto,actSnList,num-1);
			}else {
				dto.putValue("coupon",actCouponEntity);
			}

		}
		//保存 根据活动组 领取时候 的 组领取记录 ：　先查　１．如果有了就不插入　２．　当前没有　则插入
		if(!Check.NuNStr(request.getGroupSn())){
			//插入领取记录
			int logNum =  activityGroupReceiveLogDao.insertActivityGroupReceiveLog(request.getGroupSn(), request.getMobile(), null);
			if(logNum!=1){
				LogUtil.error(LOGGER, "插入优惠券领取记录失败,groupSn={},num:{},mobile={}",request.getGroupSn(),num,request.getMobile());
				throw new BusinessException("根据mobile领取优惠券失败");
			}
		}
	}


	/**
	 * 强制绑定当前的电话占用
	 * @author afi
	 * @param request
	 * @param couponSn
	 * @return
	 */
	public String forceMobileCoupon(MobileCouponRequest request,String couponSn,String actSn,String groupSn){
		if (Check.NuNStr(couponSn)){
			throw new BusinessException("请求参数为空");
		}

		if (Check.NuNObj(request)){
			throw new BusinessException("请求参数为空");
		}
		if ((Check.NuNStr(request.getActSn()) && Check.NuNStr(request.getGroupSn()))|| Check.NuNStr(request.getMobile())){
			throw new BusinessException("请求参数为空");
		}

		String rst = forceMobileCouponOnly(request,couponSn,actSn,groupSn);
		if (!Check.NuNStr(rst)){
			//添加到活动中
			ActivityRecordEntity activityRecord = new ActivityRecordEntity();
			activityRecord.setActSn(actSn);
			activityRecord.setGroupSn(request.getGroupSn());
			activityRecord.setGiftFid(couponSn);
			activityRecord.setIsPickUp(YesOrNoEnum.YES.getCode());
			activityRecord.setUserMobile(request.getMobile());
			activityRecordDao.saveActivityRecord(activityRecord);
		}
		return rst;

	}





	/**
	 * 校验强制绑定当前的电话占用
	 * @param request
	 * @return
	 */
	public boolean checkActivityByMobile(MobileCouponRequest request){

		if (Check.NuNObj(request)){
			throw new BusinessException("请求参数为空");
		}
		if (Check.NuNStr(request.getActSn()) || Check.NuNStr(request.getMobile())){
			throw new BusinessException("请求参数为空");
		}

		Long rst = couponMobileLogDao.countMobileAc(request);
		if (rst > 0){
			return false;
		}else {
			return true;
		}
	}


	/**
	 * 条件查询 用户根据uid绑定的优惠券数量
	 * @param request
	 * @return
	 */
	public Long  countUidAc(MobileCouponRequest request){

		if (Check.NuNObj(request)){
			throw new BusinessException("请求参数为空");
		}
		if (Check.NuNStr(request.getGroupSn()) || Check.NuNStr(request.getUid())){
			throw new BusinessException("请求参数为空");
		}
		return this.userCouponDao.countUidAc(request);
	}

	  
    /**
     * 条件查询 用户在 组内领取的活动
     * @param request
     * @return
     */
    public List<String>  findActSnsByUid(MobileCouponRequest request){
    	return this.userCouponDao.findActSnsByUid(request);
    }

	/**
	 *  根据uid 和 活动号 领取优惠券
	 * @author jixd
	 * @created 2017年06月14日 19:35:12
	 * @param
	 * @return 
	 */
    public ActCouponEntity pullActCouponByUid(String actSn,String uid){
		ActCouponEntity actCouponEntity = actCouponDao.getCouponByActSn(actSn);
		if (Check.NuNObj(actCouponEntity)){
			return null;
		}
		//获取当前的活动
		ActivityFullEntity activityFullEntity = activityFullDao.getActivityFullBySn(actCouponEntity.getActSn());
		CouponTimeTypeEnum couponTimeType = CouponTimeTypeEnum.getByCode(activityFullEntity.getCouponTimeType());

		// 更新优惠券信息为已领取
		ActCouponEntity coupon = new ActCouponEntity();
		//如果是有效时间 算出来开始结束时间 更新
		if (couponTimeType.getCode() == CouponTimeTypeEnum.COUNT.getCode()){
			Date couponStartTime = DateSplitUtil.jumpMinute(new Date(), -10);
			//获取当前的
			Date couponEndTime = DateSplitUtil.jumpDate(couponStartTime,ValueUtil.getintValue(activityFullEntity.getCouponTimeLast()));
			couponEndTime = DateSplitUtil.jumpMinute(couponEndTime, -11);
			coupon.setCouponStartTime(couponStartTime);
			coupon.setCouponEndTime(couponEndTime);

			actCouponEntity.setCouponStartTime(couponStartTime);
			actCouponEntity.setCouponEndTime(couponEndTime);
		}

		coupon.setCouponSn(actCouponEntity.getCouponSn());
		coupon.setCouponStatus(CouponStatusEnum.GET.getCode());
		coupon.setOldStatus(CouponStatusEnum.WAIT.getCode());
		int i = actCouponDao.updateCoupon(coupon);
		if (i > 0){
			this.actCouponNumWarn(actSn);
			// 插入绑定信息
			UserCouponEntity orderCoupon = new UserCouponEntity();
			orderCoupon.setCouponSn(coupon.getCouponSn());
			orderCoupon.setUid(uid);
			userCouponDao.saveUserCoupon(orderCoupon);
		}
		return actCouponEntity;
	}

	/**
	 * 查询 uid领取活动优惠券数量
	 * @author jixd
	 * @created 2017年06月14日 20:13:11
	 * @param
	 * @return
	 */
	public long countCouponNumByUidAndAct(String uid,String actSn,String date){
		return userCouponDao.countCouponNumByUidAndAct(uid,actSn,date);
	}

	/**
	 * 查询还有一个月优惠券过期的uid
	 * @author jixd
	 * @created 2017年06月16日 18:26:26
	 * @param
	 * @return
	 */
	public PagingResult<CouponUserUidVo> getOneMonthExpireCouponUidByGroupSN(ActCouponRequest request){
		return actCouponDao.getOneMonthExpireCouponUidByGroupSN(request);
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
    	                        	LogUtil.info(LOGGER, "发送{}活动优惠券数量提醒短信成功",actSn);
    	                        } else {
    	                        	LogUtil.info(LOGGER, "发送{}活动优惠券数量提醒短信失败error:{}",actSn,smsDto.getMsg());
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
	 * 优惠券作废
	 * @author yanb
	 * @created 2017年10月23日 23:14:07
	 * @param  * @param actCoupon
	 * @param paramMap
	 * @return int
	 */
    public int cancelCoupon(Map<String, Object> paramMap) {
		int toStatus = CouponStatusEnum.OVER_TIME.getCode();
		paramMap.put("toStatus", toStatus);
		this.saveCouponOperateLog(paramMap);
		ActCouponEntity actCouponEntity = new ActCouponEntity();
		actCouponEntity.setCouponSn(((ActCouponEntity)paramMap.get("actCoupon")).getCouponSn());
		actCouponEntity.setCouponStatus(toStatus);

		return actCouponDao.updateCoupon(actCouponEntity);
	}

	/**
	 * 新增优惠券操作记录
	 * @author yanb
	 * @created 2017年10月23日 23:14:55
	 * @param  * @param actCouponEntity
	 * @param paramMap
	 * @param toStatus
	 * @return void
	 */
	public void saveCouponOperateLog(Map<String, Object> paramMap) {
		CouponOperateLogEntity couponOperateLog = new CouponOperateLogEntity();
		couponOperateLog.setFid(UUIDGenerator.hexUUID());
		if (!Check.NuNMap(paramMap)) {
			couponOperateLog.setCouponSn(((ActCouponEntity)paramMap.get("actCoupon")).getCouponSn());
			couponOperateLog.setFromStatus(((ActCouponEntity)paramMap.get("actCoupon")).getCouponStatus());
			couponOperateLog.setActSn(((ActCouponEntity)paramMap.get("actCoupon")).getActSn());
			couponOperateLog.setRemark((String)paramMap.get("remark"));
			couponOperateLog.setEmpCode((String)paramMap.get("empCode"));
			couponOperateLog.setEmpName((String)paramMap.get("empName"));
			couponOperateLog.setToStatus((Integer) paramMap.get("toStatus"));
		}
		couponOperateLogDao.insertCouponOperateLog(couponOperateLog);
	}

	/**
	 *
	 * @author yanb
	 * @created 2017年11月06日 15:14:12
	 * @param  * @param couponSn
	 * @return com.ziroom.minsu.entity.cms.ActCouponEntity
	 */
	public ActCouponEntity getActSnStatusByCouponSn(String couponSn) {

		if (Check.NuNStr(couponSn)){
			return null;
		}
		return actCouponDao.getActSnStatusByCouponSn(couponSn);
	}
}

