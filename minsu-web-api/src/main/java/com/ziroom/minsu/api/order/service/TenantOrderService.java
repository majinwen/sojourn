package com.ziroom.minsu.api.order.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.house.valueenum.RulesEnum;
import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.entity.cms.ActivityVo;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.cms.api.inner.ActivityGiftService;
import com.ziroom.minsu.services.cms.api.inner.ActivityRecordService;
import com.ziroom.minsu.services.cms.api.inner.ActivityService;
import com.ziroom.minsu.services.cms.dto.CheckCouponRequest;
import com.ziroom.minsu.services.cms.entity.OutCouponInfoVo;
import com.ziroom.minsu.services.cms.util.TransCouponUtil;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.TenantHouseService;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;
import com.ziroom.minsu.services.order.api.inner.HouseCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderUserService;
import com.ziroom.minsu.services.order.dto.CreateOrderRequest;
import com.ziroom.minsu.services.order.dto.NeedPayFeeRequest;
import com.ziroom.minsu.services.order.dto.NeedPayFeeRuleResponse;
import com.ziroom.minsu.services.order.dto.NeedPayNoticeResponse;
import com.ziroom.minsu.services.order.entity.CommissionFreeVo;
import com.ziroom.minsu.valenum.cms.ActivityTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.ClickTypeEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.CouponStatusEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005Enum;
import com.ziroom.minsu.valenum.version.VersionCodeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>用户的订单相关的操作</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 16/10/9.
 * @version 1.0
 * @since 1.0
 */
@Service("api.tenantOrderService")
public class TenantOrderService {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(TenantOrderService.class);


	@Resource(name = "cms.actCouponService")
	private ActCouponService actCouponService;


	@Resource(name = "cms.activityService")
	private ActivityService activityService;

	@Resource(name = "cms.activityGiftService")
	private ActivityGiftService activityGiftService;

	@Resource(name = "cms.activityRecordService")
	private ActivityRecordService activityRecordService;

	@Resource(name = "order.orderUserService")
	private OrderUserService orderUserService;


	@Resource(name = "house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Resource(name="house.tenantHouseService")
	private TenantHouseService tenantHouseService;

	@Resource(name="order.houseCommonService")
	private HouseCommonService houseCommonService;


	/**
	 * 填充当前的有效的活动信息
	 * 当前的方法是对下单活动的抽象
	 * 以后这个方法将承载较多的活动相关的配置信息
	 * @author afi
	 * @param request
	 */
	private void fillActivity(NeedPayFeeRequest request,DataTransferObject dto){
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}

		//校验当前的参数信息
		if (Check.NuNStr(request.getFid())  || Check.NuNObj(request.getRentWay())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数异常");
			return;
		}
		HouseBaseMsgEntity baseMsgEntity = null;
		//整租类型
		if (RentWayEnum.HOUSE.getCode() == request.getRentWay()){
			baseMsgEntity = getHouseBaseMsg(request.getFid(), dto);
			if (Check.NuNObj(baseMsgEntity) || HouseStatusEnum.SJ.getCode() != baseMsgEntity.getHouseStatus()){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("当前房源不存在或者已经下架");
				return;
			}
		}else if (RentWayEnum.ROOM.getCode() == request.getRentWay()){

			HouseRoomMsgEntity houseRoomMsgEntity = searchHouseRoomMsgByFid(request.getFid(), dto);
			if (dto.getCode() != DataTransferObject.SUCCESS){
				return;
			}
			if (Check.NuNObj(houseRoomMsgEntity) || HouseStatusEnum.SJ.getCode() != houseRoomMsgEntity.getRoomStatus()){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("当前房源不存在或者已经下架");
				return;
			}
			baseMsgEntity = getHouseBaseMsg(houseRoomMsgEntity.getHouseBaseFid(), dto);
			if (Check.NuNObj(baseMsgEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("当前房源不存在或者已经下架");
				return;
			}
		}else {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("异常的房源类型");
			return;
		}
		CommissionFreeVo landFree = new CommissionFreeVo();
		//填充当前房东免佣金的逻辑
		this.fillCommLandFree(landFree,baseMsgEntity.getLandlordUid());
		request.setLanFree(landFree);
		//填充 普通活动 
		fillConmmonActivity(request, dto);
	}

	/**
	 * 
	 * 填充普通活动-查出来后 : 按需要类型添加
	 * 说明: 普通活动有： 免佣金    首单立减  
	 * 首单立减：
	 *    1.查询满足活动  
	 *    2.判断但是用户的条件: a. 间夜为0 且 有效订单数为0 并且 （订单为房客取消或协商取消下，房客违约金为0）
	 *   
	 * @author yd
	 * @created 2017年6月5日 上午10:48:47
	 *
	 * @param request
	 * @param dto
	 */
	public void fillConmmonActivity(NeedPayFeeRequest request,DataTransferObject dto){

		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}


		//首单立即从  100017 当前版本号开始使用
		if(!VersionCodeEnum.checkFirstOrderCut(request.getVersionCode())){
			LogUtil.info(logger, "当前版本不支持首单立减:VersionCode={} ", request.getVersionCode());
			return ; 
		}
		//获取获取信息
		String json = activityService.getRealUnderwayActivityList();

		DataTransferObject acDto = JsonEntityTransform.json2DataTransferObject(json);
		if(acDto.getCode() != DataTransferObject.SUCCESS){
			LogUtil.error(logger, "获取活动信息失败：{} ", acDto.toJsonString());
			return ;
		}


		List<ActivityVo> activities = acDto.parseData("list", new TypeReference<List<ActivityVo>>() {});
		LogUtil.info(logger, "获取活动信息：{} ", JsonEntityTransform.Object2Json(activities));
		request.getActivitys().clear();
		for (ActivityVo activityVo : activities) {
			if(activityVo.getActType().intValue() == ActivityTypeEnum.FIRST_ORDER_REDUC.getCode()){
			
				dto = JsonEntityTransform.json2DataTransferObject(houseCommonService.isNewUserByOrder(request.getUserUid()));
				if(dto.getCode() == DataTransferObject.SUCCESS){
					Integer isNewUser = dto.parseData("isNewUser",new TypeReference<Integer>() {
					});
					if(isNewUser == 0){
						request.getActivitys().add(activityVo);
					}
				}
				break;
			}
		}
	}

	/**
	 * 填充当前的免佣金的活动
	 * @param landFree
	 * @param landUid
	 */
	private void fillCommLandFree(CommissionFreeVo landFree,String landUid){
		if (Check.NuNStr(landUid)){
			return;
		}
		String freeJson = activityRecordService.checkFree(landUid);
		DataTransferObject freeDto = JsonEntityTransform.json2DataTransferObject(freeJson);
		if(freeDto.getCode() != DataTransferObject.SUCCESS){
			return;
		}
		ActivityFreeEntity activityGiftEntity  = freeDto.parseData("obj", new TypeReference<ActivityFreeEntity>() {

		});
		if (!Check.NuNObj(activityGiftEntity)){
			landFree.setFreeFlag(YesOrNoEnum.YES.getCode());
			landFree.setFreeCode(activityGiftEntity.getActCode());
			landFree.setFreeName(activityGiftEntity.getActName());
		}
	}

	/**
	 * 获取整租的的房源信息
	 *
	 * @author afi
	 * @param houseFid
	 * @param dto
	 * @return
	 */
	private HouseBaseMsgEntity getHouseBaseMsg(String houseFid,DataTransferObject dto){
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return null;
		}
		String houseJson = houseIssueService.searchHouseBaseMsgByFid(houseFid);
		DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseJson);
		if(dto.getCode() != DataTransferObject.SUCCESS){
			dto.setErrCode(houseDto.getCode());
			dto.setMsg(houseDto.getMsg());
			return null;
		}
		HouseBaseMsgEntity houseBaseMsgEntity  = houseDto.parseData("obj", new TypeReference<HouseBaseMsgEntity>() {
		});
		return houseBaseMsgEntity;
	}

	/**
	 * 获取分租的的房源信息
	 * @author afi
	 * @param roomFid
	 * @param dto
	 * @return
	 */
	private HouseRoomMsgEntity searchHouseRoomMsgByFid(String roomFid, DataTransferObject dto){
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return null;
		}
		String roomJson = houseIssueService.searchHouseRoomMsgByFid(roomFid);
		DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(roomJson);
		if(roomDto.getCode() != DataTransferObject.SUCCESS){
			dto.setErrCode(roomDto.getCode());
			dto.setMsg(roomDto.getMsg());
			return null;
		}
		HouseRoomMsgEntity houseRoomMsgEntity  = roomDto.parseData("obj", new TypeReference<HouseRoomMsgEntity>() {
		});
		return houseRoomMsgEntity;
	}


	/**
	 * 获取当前的费用信息的依赖信息
	 * 这个指所有调用费用的依赖
	 * @author afi
	 * @param needPayRequest
	 * @return
	 */
	private DataTransferObject needPayPre(NeedPayFeeRequest needPayRequest) {
		ActCouponUserEntity actCouponUserEntity = null;
		DataTransferObject couponDto = new DataTransferObject();
		if (!Check.NuNStr(needPayRequest.getCouponSn())  && Check.NuNObj(needPayRequest.getActCouponUserEntity())) {
			actCouponUserEntity = getCouponFullInfo(couponDto, needPayRequest.getCouponSn());
			needPayRequest.setActCouponUserEntity(actCouponUserEntity);
		}
		//填充当前的有效的活动信息
		this.fillActivity(needPayRequest, couponDto);
		return couponDto;
	}

	/**
	 * 获取当前的费用信息
	 * 最开始老的方法，后来取消了但是为了兼容之前的版本依然存在
	 * @author afi
	 * @param needPayRequest
	 * @return
	 */
	public DataTransferObject needPay(NeedPayFeeRequest needPayRequest){
		DataTransferObject couponDto = this.needPayPre(needPayRequest);
		if (couponDto.getCode() != DataTransferObject.SUCCESS) {
			return couponDto;
		}
		LogUtil.info(logger, "needPay最后请求参数param={}", JsonEntityTransform.Object2Json(needPayRequest));
		//如果获取优惠券信息成功
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.orderUserService.needPay(JsonEntityTransform.Object2Json(needPayRequest)));

		if (!Check.NuNObj(needPayRequest.getActCouponUserEntity())){
			OutCouponInfoVo coupon = TransCouponUtil.transCouponEle(needPayRequest.getActCouponUserEntity());
			dto.putValue("coupon",coupon);
		}
		return dto;
	}

	/**
	 * 
	 * 房东端： 获取当前的费用信息 (IM聊天)
	 *
	 * @author yd
	 * @created 2017年4月5日 下午6:04:56
	 *
	 * @param needPayRequest
	 * @return
	 */
	public DataTransferObject needPayForLan(NeedPayFeeRequest needPayRequest){
		DataTransferObject couponDto = this.needPayPre(needPayRequest);
		if (couponDto.getCode() != DataTransferObject.SUCCESS) {
			return couponDto;
		}
		//如果获取优惠券信息成功
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.orderUserService.needPayForLan(JsonEntityTransform.Object2Json(needPayRequest)));

		if (!Check.NuNObj(needPayRequest.getActCouponUserEntity())){
			OutCouponInfoVo coupon = TransCouponUtil.transCouponEle(needPayRequest.getActCouponUserEntity());
			dto.putValue("coupon",coupon);
		}
		return dto;
	}

	/**
	 * 获取当前的费用信息
	 * 后来都调用这个方法，这个是处理
	 * @author afi
	 * @param needPayRequest
	 * @return
	 */
	public DataTransferObject getNeedPayFee(NeedPayFeeRequest needPayRequest){

		DataTransferObject couponDto = this.needPayPre(needPayRequest);
		if (couponDto.getCode() != DataTransferObject.SUCCESS) {
			return couponDto;
		}
		//如果获取优惠券信息成功
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.orderUserService.getNeedPayFee(JsonEntityTransform.Object2Json(needPayRequest)));
		return dto;
	}


	/**
	 * 将订单的金额转化成匹配优惠券的参数信息
	 * @author afi
	 * @param dto
	 * @param uid
	 * @return
	 */
	public CheckCouponRequest getCheckRequestByNeedPayFeeDto(DataTransferObject dto,String uid){
		CheckCouponRequest checkRequest = new CheckCouponRequest();
		int baseMoney = ValueUtil.getintValue(dto.getData().get("baseMoney"));
		String cityCode = ValueUtil.getStrValue(dto.getData().get("cityCode"));
		String startTime = ValueUtil.getStrValue(dto.getData().get("startTime"));
		String endTime = ValueUtil.getStrValue(dto.getData().get("endTime"));
		String houseSn = ValueUtil.getStrValue(dto.getData().get("houseSn"));
		int cleanMoney = ValueUtil.getintValue(dto.getData().get("cleanMoney"));
		Double rentCut = ValueUtil.getdoubleValue(dto.getData().get("rentCut"));
		int  userCommission = ValueUtil.getintValue(dto.getData().get("userCommission"));
		Double  userCommissionRate = ValueUtil.getdoubleValue(dto.getData().get("userCommissionRate"));
		Map<String, Integer> allPriceMap = (Map<String, Integer>)dto.getData().get("allPriceMap");
		checkRequest.setAllPriceMap(allPriceMap);
		checkRequest.setUid(uid);
		checkRequest.setCityCode(cityCode);
		checkRequest.setPrice(baseMoney);
		checkRequest.setStartTime(startTime);
		checkRequest.setEndTime(endTime);
		checkRequest.setHouseSn(houseSn);
		checkRequest.setCleanMoney(cleanMoney);
		checkRequest.setUserCommission(userCommission);
		checkRequest.setUserCommissionRate(userCommissionRate);
		if(!Check.NuNObj(rentCut)){
			checkRequest.setRentCut(rentCut);
		}
		return checkRequest;
	}

	/**
	 * 当优惠券不存在的时候 默认调用优惠券信息
	 * 说明： 优惠券和首单立减 叠加使用  优先级： 优惠券>首单立减
	 * @author afi
	 * @param needPayRequest
	 * @return
	 */
	public DataTransferObject needPayFeeAndDefaultCoupon(NeedPayFeeRequest needPayRequest){
		// 需要自动匹配最优优惠券
		if(Check.NuNStr(needPayRequest.getCouponSn()) && needPayRequest.getIsAutoCoupon()==1){
			DataTransferObject dto = this.getNeedPayFee(needPayRequest);
			LogUtil.info(logger, "getNeedPayFee resultJson:{}", dto.toJsonString());
			if (dto.getCode() != DataTransferObject.SUCCESS){
				return dto;
			}
			CheckCouponRequest checkRequest = this.getCheckRequestByNeedPayFeeDto(dto, needPayRequest.getUserUid());
			DataTransferObject dtoCoupon = JsonEntityTransform.json2DataTransferObject(actCouponService.getDefaultCouponByUid(JsonEntityTransform.Object2Json(checkRequest)));
			if (dtoCoupon.getCode() != DataTransferObject.SUCCESS){
				return dtoCoupon;
			}
			OutCouponInfoVo coupon  = dtoCoupon.parseData("coupon", new TypeReference<OutCouponInfoVo>() {
			});
			ActCouponUserEntity actCouponUserEntity  = dtoCoupon.parseData("obj", new TypeReference<ActCouponUserEntity>() {
			});
			if (!Check.NuNObj(coupon)){
				needPayRequest.setCouponSn(coupon.getCardId());
				needPayRequest.setActCouponUserEntity(actCouponUserEntity);
			}
		}

		// 获取下单页支付金额
		DataTransferObject needPayDto = needPay(needPayRequest);

		boolean addHouseRule = true;
		try {
			HouseDetailDto detailDto = new HouseDetailDto();
			detailDto.setFid(needPayRequest.getFid());
			detailDto.setRentWay(needPayRequest.getRentWay());
			String houseDetail = tenantHouseService.findHouseDetail(JsonEntityTransform.Object2Json(detailDto));
			TenantHouseDetailVo tenantHouseDetailVo = SOAResParseUtil.getValueFromDataByKey(houseDetail, "houseDetail", TenantHouseDetailVo.class);
			if(Check.NuNObj(tenantHouseDetailVo) || Check.NuNStr(tenantHouseDetailVo.getHouseRules())){
				addHouseRule = false;
			}
		} catch (Exception e) {
			LogUtil.error(logger, "查询房源详情异常e={}", e);
		}
		this.fillNoticeRules(needPayDto, addHouseRule);
		return needPayDto;
	}


	/**
	 * 填充注意事项守则
	 * @author lishaochuan
	 * @create 2016/12/5 19:44
	 * @param 
	 * @return 
	 */
	public void fillNoticeRules(DataTransferObject dto,boolean addHouseRule){
		NeedPayNoticeResponse returnRule = new NeedPayNoticeResponse();
		String notice = dto.getData().get("notices") + "";
		if (TradeRulesEnum005Enum.TradeRulesEnum005001.getValue().equals(notice)) {
			returnRule.setCode(RulesEnum.RETURN_STRICT.getCode());
			returnRule.setName(RulesEnum.RETURN_STRICT.getTitle());
		} else if (TradeRulesEnum005Enum.TradeRulesEnum005002.getValue().equals(notice)) {
			returnRule.setCode(RulesEnum.RETURN_MEDIUM.getCode());
			returnRule.setName(RulesEnum.RETURN_MEDIUM.getTitle());
		} else if (TradeRulesEnum005Enum.TradeRulesEnum005003.getValue().equals(notice)) {
			returnRule.setCode(RulesEnum.RETURN_FLEXIBLE.getCode());
			returnRule.setName(RulesEnum.RETURN_FLEXIBLE.getTitle());
		} else if (TradeRulesEnum005Enum.TradeRulesEnum005004.getValue().equals(notice)) {
			returnRule.setCode(RulesEnum.RETURN_LONG.getCode());
			returnRule.setName(RulesEnum.RETURN_LONG.getTitle());
		}
		returnRule.setClickType(ClickTypeEnum.CLICK.getCode());
		returnRule.setIndex(2);

		List<NeedPayNoticeResponse> notices = new ArrayList<>();

		if(addHouseRule){
			NeedPayNoticeResponse houseRule = new NeedPayNoticeResponse();
			houseRule.setCode(RulesEnum.HOUSE_RULES.getCode());
			houseRule.setName(RulesEnum.HOUSE_RULES.getTitle());
			houseRule.setClickType(ClickTypeEnum.CLICK_AND_AGREE.getCode());
			houseRule.setIndex(1);
			notices.add(houseRule);
		}  

		notices.add(returnRule);
		dto.putValue("notices", notices);


		//增加押金rule展示
		NeedPayFeeRuleResponse payFeeRuleResponse = dto.parseData("deposit", new TypeReference<NeedPayFeeRuleResponse>() {
		});
		if (!Check.NuNObj(payFeeRuleResponse)) {
			payFeeRuleResponse.setRuleCode(RulesEnum.DEPOSIT_FEE_TEN.getCode());
            payFeeRuleResponse.setRuleName(RulesEnum.DEPOSIT_FEE_TEN.getHouseDetailShowName());
            dto.putValue("deposit", payFeeRuleResponse);
		}


	}


	/**
	 * 创建订单的逻辑
	 * 所有的下单的逻辑都需要抽象到这里
	 * @author afi
	 * @param creatOrderRequest
	 * @return
	 */
	public DataTransferObject createOrder(CreateOrderRequest creatOrderRequest){
		//创建订单的逻辑
		DataTransferObject couponDto = new DataTransferObject();
		//填充当前的有效的活动信息
		this.fillActivity(creatOrderRequest,couponDto);
		if (couponDto.getCode() != DataTransferObject.SUCCESS){
			return couponDto;
		}
		ActCouponUserEntity actCouponOrderEntity = null;
		if (!Check.NuNStr(creatOrderRequest.getCouponSn())){
			actCouponOrderEntity = getCouponFullInfo(couponDto,creatOrderRequest.getCouponSn());
			creatOrderRequest.setActCouponUserEntity(actCouponOrderEntity);
		}
		if (couponDto.getCode() == DataTransferObject.SUCCESS){
			//如果获取优惠券信息成功
			DataTransferObject dto  = JsonEntityTransform.json2DataTransferObject(this.orderUserService.createOrder(JsonEntityTransform.Object2Json(creatOrderRequest)));
			//如果下单使用优惠券成功
			this.syncCouponStatus(dto,creatOrderRequest.getCouponSn(),ValueUtil.getStrValue(dto.getData().get("orderSn")));
			return dto;
		}else {
			return couponDto;
		}

	}


	/**
	 * 获取优惠券信息
	 * @author afi
	 * @param dto
	 * @param couponSn
	 * @return
	 */
	public ActCouponUserEntity getCouponFullInfo(DataTransferObject dto, String couponSn){
		if(Check.NuNStr(couponSn)){
			return null;
		}
		ActCouponUserEntity couponUserVo = null;
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return couponUserVo;
		}

		//获取cms的优惠券信息
		String couponResultJson = actCouponService.getActCouponUserVoByCouponSn(couponSn);
		DataTransferObject couponDto = JsonEntityTransform.json2DataTransferObject(couponResultJson);
		if(couponDto.getCode() != DataTransferObject.SUCCESS){
			LogUtil.info(logger, "获取优惠券信息失败：{}", couponDto.toJsonString());
			dto.setErrCode(couponDto.getCode());
			dto.setMsg(couponDto.getMsg());
			return couponUserVo;
		}else {
			couponUserVo = couponDto.parseData("obj", new TypeReference<ActCouponUserEntity>() {});
		}
		return couponUserVo;
	}


	/**
	 * 异步同步当前的优惠券信息
	 * @author afi
	 * @param dto
	 * @param couponSn
	 * @param orderSn
	 */
	private void syncCouponStatus(DataTransferObject dto,final String couponSn,final String orderSn){
		if (Check.NuNObj(dto)){
			return;
		}
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}
		if (Check.NuNStr(couponSn)){
			return;
		}
		if (Check.NuNStr(orderSn)){
			return;
		}
		Thread task = new Thread(){
			@Override
			public void run() {
				//下单成功，使用优惠券 不管成功还是失败，失败会有同步机制
				List<OrderActivityEntity> orderActList = new ArrayList<>();
				OrderActivityEntity orderAct = new OrderActivityEntity();
				orderAct.setAcFid(couponSn);
				orderAct.setAcStatus(CouponStatusEnum.FROZEN.getCode());
				orderAct.setOrderSn(ValueUtil.getStrValue(orderSn));
				orderActList.add(orderAct);
				//不管是否处理成功
				actCouponService.syncCouponStatus(JsonEntityTransform.Object2Json(orderActList));
			}
		};
		SendThreadPool.execute(task);
	}

}
