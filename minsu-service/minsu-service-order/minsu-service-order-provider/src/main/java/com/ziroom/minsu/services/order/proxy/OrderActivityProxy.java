package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.StringUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityCityEntity;
import com.ziroom.minsu.entity.cms.ActivityVo;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.entity.order.OrderConfigEntity;
import com.ziroom.minsu.services.common.constant.ActicityConst;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import com.ziroom.minsu.services.order.dto.NeedPayFeeRequest;
import com.ziroom.minsu.services.order.entity.CommissionFreeVo;
import com.ziroom.minsu.services.order.entity.CustomerRoleVo;
import com.ziroom.minsu.services.order.entity.OrderSaveVo;
import com.ziroom.minsu.services.order.service.OrderConfigServiceImpl;
import com.ziroom.minsu.services.order.utils.FinanceMoneyUtil;
import com.ziroom.minsu.valenum.cms.ActivityTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.OrderAcTypeEnum;
import com.ziroom.minsu.valenum.order.OrderConfigEnum;
import com.ziroom.minsu.valenum.order.PreferentialEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>订单参加的活动</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/23.
 * @version 1.0
 * @since 1.0OrderOrderOrder
 */
@Component("order.orderActivityProxy")
public class OrderActivityProxy {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderActivityProxy.class);

	@Resource(name = "order.orderConfigServiceImpl")
	private OrderConfigServiceImpl orderConfigService;

	@Resource(name = "order.orderCouponProxy")
	private OrderCouponProxy orderCouponProxy;


	/**
	 * 处理当前的活动
	 * 除了房东折扣所有的活动都走这里
	 * @author afi
	 * @param dto
	 * @param houseInfo
	 * @param orderSaveInfo
	 * @param land
	 */
	public void fillActivity(DataTransferObject dto,NeedPayFeeRequest request,OrderNeedHouseVo houseInfo,OrderSaveVo orderSaveInfo,CustomerRoleVo land){
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}

		//0.客户限制 暂时去掉 当前不做男女和国籍的限制
		/**
		 * 特殊说明，如下的活动的计算是有逻辑顺序的
		 * 千万不动颠倒如下的顺序
		 * 如果动了，估计改bug就要完蛋了
		 */
		//1. 初始化默认的订单金额
		this.fillFristPartMoney(dto, houseInfo, orderSaveInfo);

		//2. 设置房东的折扣金额 需要依赖 fillFristPartMoney
		this.fillDiscountMoney(dto, houseInfo, orderSaveInfo);

		//3. 设置用户的佣金 需要依赖 fillDiscountMoney
		this.fillUserCommission(dto, houseInfo, orderSaveInfo);

		//4. 设置房东的佣金 需要依赖 fillDiscountMoney
		this.fillLanCommission(dto, houseInfo, orderSaveInfo);

		//5. 处理当前的所有活动信息
		this.dealOrderActivity(dto, request, houseInfo, orderSaveInfo, land);

		//6. 设置用户的佣金 需要依赖 dealOrderActivity
		this.fillCouponMoney(dto, houseInfo, orderSaveInfo);

		//7. 处理普通活动金额  优先级  : 优惠券>普通活动
		this.fillCommonActMoney(dto,orderSaveInfo,request);

		//8. 订单所有金额的拼装 需要依赖之前的所有的金额的计算
		this.fillLastPartMoney(dto, orderSaveInfo);


	}

	/**
	 * 
	 * 1.填充普通活动总金额
	 * 2.如果普通活动金额为0 直接清空普通活动
	 *
	 * @author yd
	 * @created 2017年6月5日 下午9:00:47
	 *
	 * @param dto
	 */
	public void  fillCommonActMoney(DataTransferObject dto,OrderSaveVo orderSaveInfo,NeedPayFeeRequest request){
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}
		Integer actMoney = orderSaveInfo.getOrderMoney().getActMoney();
		
		if(actMoney<=0){
			LogUtil.info(LOGGER, "【申请预定计算普通活动金额】request={},actMoney={}",JsonEntityTransform.Object2Json(request),actMoney);
			return ;
		}

		//当前折扣之后的的真实的房租 优惠之前的房租 - 房租折扣金额
		int rentalMoney = orderSaveInfo.getCost();
		if (rentalMoney < 0){
			rentalMoney = 0;
		}

		//用户需要支付的金额  折扣之后的的真实的房租 - 优惠券金额 + 房客佣金 + 清洁费
		int base = rentalMoney  - orderSaveInfo.getOrderMoney().getDiscountMoney()
				- orderSaveInfo.getOrderMoney().getCouponMoney()
				+ orderSaveInfo.getOrderMoney().getUserCommMoney()
				+ orderSaveInfo.getOrderMoney().getCleanMoney();

		//0元订单 就不在享受普通活动优惠
		if(base<=0){
			LogUtil.info(LOGGER, "【0元订单,不享受首单立减】actMoney={},listActivityVo={}", actMoney,JsonEntityTransform.Object2Json(request.getActivitys()));
			orderSaveInfo.getOrderMoney().setActMoney(0);
			orderSaveInfo.getOrderMoney().setActMoneyAll(0);
			return;
		}

		//首单立减金额不能抵扣押金
        int realActMoney = ValueUtil.getMin(actMoney,base);
		//填充订单活动信息
		List<ActivityVo> listAc =  request.getActivitys();
		if(!Check.NuNCollection(listAc)){
			LogUtil.info(LOGGER, "【下单填充订单普通活动信息】：orderSn={},listAc={}", orderSaveInfo.getOrder().getOrderSn(),JsonEntityTransform.Object2Json(listAc));
			for (ActivityVo activityVo : listAc) {
				if(activityVo.getActType().intValue() == ActivityTypeEnum.FIRST_ORDER_REDUC.getCode()){
					//立减活动不能抵押金
					OrderActivityEntity ac = new OrderActivityEntity();
					ac.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
					ac.setAcFid(activityVo.getActSn());
					ac.setAcMoney(realActMoney);
					ac.setAcMoneyAll(realActMoney);
					ac.setAcName(ActivityTypeEnum.FIRST_ORDER_REDUC.getName());
					ac.setAcType(OrderAcTypeEnum.FIRST_ORDER_REDUC.getCode());
					ac.setPreferentialSources(PreferentialEnum.PLATFORM.getCode());
					ac.setPreferentialUser(PreferentialEnum.TENANT.getCode());
					orderSaveInfo.getOrderActivitys().add(ac);
				}
			}
		}

        LogUtil.info(LOGGER, "【首单立减金额】原始立减金额actMoney={},真实立减金额realActMoney={},需要支金额needPay={},优惠券金额couponMoney={}", actMoney,realActMoney,base,orderSaveInfo.getOrderMoney().getCouponMoney());
        orderSaveInfo.getOrderMoney().setActMoney(realActMoney);
		orderSaveInfo.getOrderMoney().setActMoneyAll(realActMoney);
	}


	/**
	 * 处理所有的活动信息
	 * @author afi
	 * @param dto
	 * @param request
	 * @param houseInfo
	 * @param orderSaveInfo
	 * @param land
	 */
	public void dealOrderActivity(DataTransferObject dto,NeedPayFeeRequest request,OrderNeedHouseVo houseInfo,OrderSaveVo orderSaveInfo,CustomerRoleVo land){
		if (dto.getCode() == DataTransferObject.SUCCESS){
			List<ActivityVo> activityInfoEntities = request.getActivitys();
			if (!Check.NuNCollection(activityInfoEntities)){

				Integer actMoney = 0;
				//当前的活动不为空
				for (ActivityVo activity : activityInfoEntities) {
					if (activity.getActType() == ActivityTypeEnum.FIRST_ORDER_REDUC.getCode()){
						//封装普通活动金额   说明:  此处优先使用优惠券 ，故普通活动放在最后计算
						actMoney +=ValueUtil.getintValue(activity.getActCut());
					}else {
						//当前的活动还未支持
						LogUtil.info(LOGGER, "【活动】当前活动类型还不支持：activityInfoEntity", JsonEntityTransform.Object2Json(activity));
					}
				}
				if(actMoney<0){
					LogUtil.error(LOGGER, "【申请预定计算普通活动金额异常】request={},activityInfoEntities={},actMoney={}",JsonEntityTransform.Object2Json(request),JsonEntityTransform.Object2Json(activityInfoEntities),actMoney);
					throw new BusinessException("申请预定计算普通活动金额异常");
				}
				orderSaveInfo.getOrderMoney().setActMoney(actMoney);
				orderSaveInfo.getOrderMoney().setActMoneyAll(actMoney);
			}
			//强制 处理当前的免房东的佣金的逻辑
			dealFreeLanCommissionForce(dto, request, houseInfo, orderSaveInfo);

		}
	}


	/**
	 * 处理基础的房租价格
	 * @author afi
	 * @param dto
	 * @param houseInfo
	 * @param orderSaveInfo
	 */
	public void fillFristPartMoney(DataTransferObject dto,OrderNeedHouseVo houseInfo,OrderSaveVo orderSaveInfo){
		if (dto.getCode() == DataTransferObject.SUCCESS){

			//1.订单的天数
			Set<String> daySet = DateSplitUtil.getDateSplitFullDaySet(orderSaveInfo.getOrder().getStartTime(), orderSaveInfo.getOrder().getEndTime());
			orderSaveInfo.setDayCount(daySet.size());

			//实际房租(优惠前)
			Integer cost = 0;
			//2.订单每天的价格和订单的初始价格
			Integer price = FinanceMoneyUtil.getHousePrice(houseInfo);
			if(Check.NuNObj(price)){
				LogUtil.error(LOGGER, "【下单】 获取房钱的房间价格异常 :house {}", JsonEntityTransform.Object2Json(houseInfo));
				throw new BusinessException("房源的基础价格为空 shit!!!!");
			}
			Map<String,Integer> allPriceMap = new HashMap<>();
			for(String day: daySet){
				if(orderSaveInfo.getPriceMap().containsKey(day)){
					int priceDay = orderSaveInfo.getPriceMap().get(day);
					cost += priceDay;
					allPriceMap.put(day,priceDay);
				}else{
					cost += price;
					allPriceMap.put(day,price);
				}
			}
			orderSaveInfo.setCost(cost);
			/**
			 *  当前操作是必须的
			 *  为以后所有的金额的计算埋下了必须的价格条件
			 *  这个价格千万不要动，要不所有的依赖都完蛋了
			 *  以后所有的价格的区间的计算也尽量用这个，不要单独的去处理
			 */
			orderSaveInfo.setAllPriceMap(allPriceMap);

			//3.押金规则 直接获取押金金额
			int depositMoney = orderConfigService.getDepositMoneyFromHouseConfig(houseInfo.getHouseConfList(), orderSaveInfo, houseInfo);
			//押金
			LogUtil.info(LOGGER, "【下单】 押金 depositMoney： {}", depositMoney);
			orderSaveInfo.getOrderMoney().setDepositMoney(depositMoney);
			orderSaveInfo.getOrderMoney().setDepositMoneyAll(depositMoney);
			//真实的消费
			orderSaveInfo.getOrderMoney().setRealMoney(0);
			//已支付金额
			orderSaveInfo.getOrderMoney().setPayMoney(0);
			//额外消费
			orderSaveInfo.getOrderMoney().setOtherMoney(0);
			//违约金
			orderSaveInfo.getOrderMoney().setPenaltyMoney(0);
			//退款金额
			orderSaveInfo.getOrderMoney().setRefundMoney(0);
			//普通活动金额
			orderSaveInfo.getOrderMoney().setActMoney(0);
			orderSaveInfo.getOrderMoney().setActMoneyAll(0);
			//设置清洁费
			orderSaveInfo.getOrderMoney().setCleanMoney(ValueUtil.getintValue(FinanceMoneyUtil.getHouseCleanMoney(houseInfo)));
			orderSaveInfo.getOrderMoney().setOrderSn(orderSaveInfo.getOrder().getOrderSn());
		}
	}


	/**
	 * 处理房东的折扣金额
	 * 这个有前提依赖
	 * @see #fillFristPartMoney(DataTransferObject,OrderNeedHouseVo,OrderSaveVo)
	 * @author afi
	 * @param dto
	 * @param houseInfo
	 * @param orderSaveInfo
	 */
	public void fillDiscountMoney(DataTransferObject dto,OrderNeedHouseVo houseInfo,OrderSaveVo orderSaveInfo){
		if (dto.getCode() == DataTransferObject.SUCCESS){
			//设置当前订单 享受的房东设置的折扣金额
			int discountMoney = orderConfigService.fillDiscountFromHouseConfig(houseInfo.getHouseConfList(), orderSaveInfo, orderSaveInfo.getCost());
			//押金
			LogUtil.info(LOGGER, "【下单】 房东折扣金额 discountMoney： {}", discountMoney);
			orderSaveInfo.getOrderMoney().setDiscountMoney(discountMoney);
		}
	}

	/**
	 * 处理用户的佣金
	 * 这个有前提依赖
	 * @see #fillDiscountMoney(DataTransferObject,OrderNeedHouseVo,OrderSaveVo)
	 * @author afi
	 * @param dto
	 * @param houseInfo
	 * @param orderSaveInfo
	 */
	public void fillUserCommission(DataTransferObject dto,OrderNeedHouseVo houseInfo,OrderSaveVo orderSaveInfo){
		if (dto.getCode() == DataTransferObject.SUCCESS){
			int base = orderSaveInfo.getCost() - orderSaveInfo.getOrderMoney().getDiscountMoney();
			//获取佣金的基数是 折扣之前的房租 - 折扣金额
			int userCommMoney = 0;
			if (orderSaveInfo.getChangzuFlag()){
				//用户的佣金
				LogUtil.info(LOGGER, "【下单】 用户佣金 [长租 ]");
				userCommMoney = orderConfigService.getCommissionFromHouseConfigChangzu(dto,houseInfo.getHouseConfList(), base, UserTypeEnum.TENANT);
				int userCommMoneyPlan = orderConfigService.getCommissionFromHouseConfigDeal(dto,houseInfo.getHouseConfList(), base, UserTypeEnum.TENANT,false);
				int  saveUserComm = userCommMoneyPlan - userCommMoney;
				if (saveUserComm > 0){
					orderSaveInfo.setSaveUserComm(saveUserComm);
				}
			}else {
				LogUtil.info(LOGGER, "【下单】 用户佣金 [非长租 ]");
				userCommMoney = orderConfigService.getCommissionFromHouseConfig(dto,houseInfo.getHouseConfList(), base, UserTypeEnum.TENANT);
			}
			//用户的佣金
			LogUtil.info(LOGGER, "【下单】 用户佣金 userCommMoney： {}", userCommMoney);
			orderSaveInfo.getOrderMoney().setUserCommMoney(userCommMoney);
		}
	}

	/**
	 * 处理房东的佣金
	 * 这个有前提依赖
	 * @see #fillDiscountMoney(DataTransferObject,OrderNeedHouseVo,OrderSaveVo)
	 * @author afi
	 * @param dto
	 * @param houseInfo
	 * @param orderSaveInfo
	 */
	public void fillLanCommission(DataTransferObject dto,OrderNeedHouseVo houseInfo,OrderSaveVo orderSaveInfo){
		if (dto.getCode() == DataTransferObject.SUCCESS){
			int base = orderSaveInfo.getCost() - orderSaveInfo.getOrderMoney().getDiscountMoney() + orderSaveInfo.getOrderMoney().getCleanMoney();
			//获取佣金的基数是 折扣之前的房租 - 折扣金额
			int lanCommMoney = 0;
			if (orderSaveInfo.getChangzuFlag()){
				//用户的佣金
				LogUtil.info(LOGGER, "【下单】 房东佣金 [长租 ]");
				lanCommMoney = orderConfigService.getCommissionFromHouseConfigChangzu(dto,houseInfo.getHouseConfList(), base, UserTypeEnum.LANDLORD);
				int lanCommMoneyPlan = orderConfigService.getCommissionFromHouseConfigDeal(dto,houseInfo.getHouseConfList(), base, UserTypeEnum.LANDLORD,false);
				int  saveLanComm = lanCommMoneyPlan - lanCommMoney;
				if (saveLanComm > 0){
					orderSaveInfo.setSaveLanComm(saveLanComm);
				}
			}else {
				LogUtil.info(LOGGER, "【下单】 房东佣金 [非长租 ]");
				lanCommMoney = orderConfigService.getCommissionFromHouseConfig(dto,houseInfo.getHouseConfList(), base, UserTypeEnum.LANDLORD);
			}
			//房东的佣金
			LogUtil.info(LOGGER, "【下单】 房东佣金 lanCommMoney： {}", lanCommMoney);
			orderSaveInfo.getOrderMoney().setLanCommMoney(lanCommMoney);
		}
	}


	/**
	 * 处理优惠券的逻辑
	 * 这个有前提依赖
	 * @see #dealOrderActivity(DataTransferObject,NeedPayFeeRequest,OrderNeedHouseVo,OrderSaveVo,CustomerRoleVo)
	 * @author afi
	 * @param dto
	 * @param houseInfo
	 * @param orderSaveInfo
	 */
	public void fillCouponMoney(DataTransferObject dto,OrderNeedHouseVo houseInfo,OrderSaveVo orderSaveInfo){
		if (dto.getCode() == DataTransferObject.SUCCESS){
			//优惠券的基数是 折扣之前的房租 - 折扣金额 + 用户佣金 + 清洁费
			int base = orderSaveInfo.getCost() - orderSaveInfo.getOrderMoney().getDiscountMoney() + orderSaveInfo.getOrderMoney().getUserCommMoney() + orderSaveInfo.getOrderMoney().getCleanMoney();
			//设置优惠券的使用基数 这么做的目的是将优惠券的金额金额解耦都走一样的逻辑处理
			orderSaveInfo.getAllPriceMap().put("sum", base);
			//8.处理优惠券信息，并获取优惠券的金额
			int couponMoney = orderCouponProxy.dealAndGetCouponMoney(dto, orderSaveInfo.getAllPriceMap(), orderSaveInfo, houseInfo);
			LogUtil.info(LOGGER, "【下单】 优惠券金额 couponMoney： {}", couponMoney);
			orderSaveInfo.getOrderMoney().setCouponMoney(couponMoney);
			orderSaveInfo.getOrderMoney().setCouponMoneyAll(couponMoney);
		}
	}


	/**
	 * 处理活动之后的价格
	 * 价格的最后的落地处理
	 * 需要之前所有价格的计算
	 * @author afi
	 * @param dto
	 * @param orderSaveInfo
	 */
	public void fillLastPartMoney(DataTransferObject dto,OrderSaveVo orderSaveInfo){
		if (dto.getCode() == DataTransferObject.SUCCESS){

			//当前折扣之后的的真实的房租 优惠之前的房租 - 房租折扣金额
			int rentalMoney = orderSaveInfo.getCost();
			if (rentalMoney < 0){
				rentalMoney = 0;
			}

			//用户需要支付的金额  折扣之后的的真实的房租 - 优惠券金额 -普通活动金额+ 房客佣金+ 押金 + 清洁费
			int needPay = rentalMoney  - orderSaveInfo.getOrderMoney().getDiscountMoney()
					-orderSaveInfo.getOrderMoney().getCouponMoney()
					-orderSaveInfo.getOrderMoney().getActMoney()
					+ orderSaveInfo.getOrderMoney().getUserCommMoney()
					+ orderSaveInfo.getOrderMoney().getDepositMoney()
					+ orderSaveInfo.getOrderMoney().getCleanMoney();
			//需要支付金额
			orderSaveInfo.getOrderMoney().setNeedPay(needPay);

			//实际房租(优惠前)
			LogUtil.info(LOGGER, "【下单】 优惠前房租 rentalMoney： {}", rentalMoney);
			orderSaveInfo.getOrderMoney().setRentalMoney(rentalMoney);
			orderSaveInfo.getOrderMoney().setRentalMoneyAll(rentalMoney);

			//总金额
			int all = orderSaveInfo.getCost()  - orderSaveInfo.getOrderMoney().getDiscountMoney()
					+ orderSaveInfo.getOrderMoney().getDepositMoney()
					+ orderSaveInfo.getOrderMoney().getUserCommMoney()
					+ orderSaveInfo.getOrderMoney().getCleanMoney();

			LogUtil.info(LOGGER, "【下单】 总金额all：{} 优惠前房租：{} 押金：{} 用户佣金：{}  清洁费：{}",
					all, orderSaveInfo.getCost(),
					orderSaveInfo.getOrderMoney().getDepositMoney(),
					orderSaveInfo.getOrderMoney().getUserCommMoney(),
					orderSaveInfo.getOrderMoney().getCleanMoney());

			orderSaveInfo.getOrderMoney().setSumMoney(all);

		}
	}



	/**
	 * 处理种子房东免佣金的逻辑
	 * 主要用户活动的
	 * @author afi
	 * @param dto
	 * @param request
	 * @param houseInfo
	 * @param orderSaveInfo
	 */
	private void dealFreeLanCommissionForce(DataTransferObject dto,NeedPayFeeRequest request,OrderNeedHouseVo houseInfo,OrderSaveVo orderSaveInfo){
		if (dto.getCode() != DataTransferObject.SUCCESS){
			//异常请求
			return;
		}
		if (orderSaveInfo.getLandHasFree() == YesOrNoEnum.YES.getCode()){
			//当前房东已经免了佣金，直接过滤去重
			return;
		}
		CommissionFreeVo landFree = request.getLanFree();
		if (landFree.getFreeFlag() == YesOrNoEnum.NO.getCode()){
			//当前的房东没有免服务费的特权
			return;
		}
		fillFreeComminfo(houseInfo, orderSaveInfo, landFree.getFreeCode(), landFree.getFreeName());


	}

	/**
	 * 封装免佣金的配置逻辑
	 * @author afi
	 * @param houseInfo
	 * @param orderSaveInfo
	 * @param freeCode
	 * @param freeName
	 */
	private void fillFreeComminfo(OrderNeedHouseVo houseInfo, OrderSaveVo orderSaveInfo, String freeCode, String freeName) {
		/**
		 * 获取当前房源的配置信息
		 * 当前订单增加免佣金的活动配置
		 * 房钱配置的值对应的是活动的code 用于更新活动的金额
		 * 这个是为以后的退房埋下的伏笔
		 */
		List<HouseConfMsgEntity> houseConfList = houseInfo.getHouseConfList();
		HouseConfMsgEntity lanCommFree = new HouseConfMsgEntity();
		lanCommFree.setDicCode(OrderConfigEnum.LAN_COMM_FREE.getCode());
		lanCommFree.setDicVal(freeCode);
		houseConfList.add(lanCommFree);

		OrderConfigEntity configEntity = new OrderConfigEntity();
		configEntity.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
		configEntity.setConfigCode(OrderConfigEnum.LAN_COMM_FREE.getCode());
		configEntity.setConfigValue(freeCode);
		orderSaveInfo.getOrderConfigs().add(configEntity);

		//参加免佣金的活动
		OrderActivityEntity ac = new OrderActivityEntity();
		ac.setPreferentialSources(PreferentialEnum.PLATFORM.getCode());
		ac.setPreferentialUser(PreferentialEnum.LANLORD.getCode());
		ac.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
		ac.setAcFid(freeCode);
		ac.setAcName(freeName);
		//设置当前的免佣金的活动金额为当前的房客的佣金
		ac.setAcMoneyAll(orderSaveInfo.getOrderMoney().getLanCommMoney());
		ac.setAcMoney(orderSaveInfo.getOrderMoney().getLanCommMoney());
		ac.setAcType(OrderAcTypeEnum.LAN_FREE_COMM.getCode());
		orderSaveInfo.getOrderActivitys().add(ac);

		//并且清空当前的佣金情况
		orderSaveInfo.getOrderMoney().setLanCommMoney(0);
		//设置当前已经设置免佣金的标记
		orderSaveInfo.setLandHasFree(YesOrNoEnum.YES.getCode());
	}


	/**
	 * 处理种子房东免佣金的逻辑
	 * @author afi
	 * @param dto
	 * @param request
	 * @param houseInfo
	 * @param orderSaveInfo
	 * @param land
	 * @param activity
	 */
	@Deprecated
	private void dealFreeLanCommission(DataTransferObject dto,NeedPayFeeRequest request,OrderNeedHouseVo houseInfo,OrderSaveVo orderSaveInfo,CustomerRoleVo land,ActivityVo activity){
		if (dto.getCode() != DataTransferObject.SUCCESS){
			//异常请求
			return;
		}
		if (orderSaveInfo.getLandHasFree() == YesOrNoEnum.YES.getCode()){
			//当前房东已经免了佣金，直接过滤去重
			return;
		}
		if (activity.getActType() != ActivityTypeEnum.LAN_COM.getCode()){
			//当前的活动不是优惠券的逻辑，直接返回
			LogUtil.info(LOGGER,"【活动】当前的活动不是优惠券的逻辑：activityInfoEntity", JsonEntityTransform.Object2Json(activity));
			return;
		}
		if (Check.NuNObj(land)){
			//当前房东信息不存在
			return;
		}
		if (!checkCity(houseInfo.getCityCode(), activity.getCityList())){
			//当前城市不支持
			LogUtil.info(LOGGER,"【活动】当前城市不支持：activityInfoEntity", JsonEntityTransform.Object2Json(activity));
			return;
		}
		if (!checkRole(activity.getRoleCode(), land)){
			//当前用户角色不支持
			LogUtil.info(LOGGER,"【活动】当前用户角色不支持：activityInfoEntity", JsonEntityTransform.Object2Json(activity));
			return;
		}

		/**
		 * 获取当前房源的配置信息
		 * 当前订单增加免佣金的活动配置
		 * 房钱配置的值对应的是活动的code 用于更新活动的金额
		 * 这个是为以后的退房埋下的伏笔
		 */
		fillFreeComminfo(houseInfo, orderSaveInfo, activity.getActSn(), activity.getActName());
	}

	/**
	 * 校验当前城市是否参加当前的城市
	 * @author afi
	 * @param cityCode
	 * @param cityEntityList
	 * @return
	 */
	public boolean checkCity(String cityCode,List<ActivityCityEntity> cityEntityList){
		boolean rst = false;
		if (Check.NuNCollection(cityEntityList)){
			return rst;
		}
		if (Check.NuNStr(cityCode)){
			return rst;
		}

		for (ActivityCityEntity cityEntity : cityEntityList) {
			if (cityEntity.getCityCode().equals(ActicityConst.all_str)){
				rst = true;
				return rst;
			}else if (cityEntity.getCityCode().equals(cityCode)){
				rst = true;
				return rst;
			}
		}
		return rst;
	}


	/**
	 * 校验当前用户是否有当前的角色code特性
	 * @author afi
	 * @param roleCode
	 * @param customerRoleVo
	 * @return
	 */
	public boolean checkRole(String roleCode,CustomerRoleVo customerRoleVo){
		boolean rst = false;
		if (Check.NuNObjs(customerRoleVo)){
			return rst;
		}
		if (Check.NuNStr(roleCode)){
			return rst;
		}
		//遍历当前的角色code，验证是否具有当前的角色信息
		List<CustomerRoleEntity> roles = customerRoleVo.getRoles();
		if (Check.NuNCollection(roles)){
			return rst;
		}
		for (CustomerRoleEntity role : roles) {
			if (ValueUtil.getStrValue(role.getRoleCode()).equals(roleCode)){
				rst = true;
				break;
			}
		}
		return rst;
	}

}
