package com.ziroom.minsu.services.order.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.entity.order.OrderConfigEntity;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import com.ziroom.minsu.services.order.dao.OrderConfigDao;
import com.ziroom.minsu.services.order.entity.OrderConfigVo;
import com.ziroom.minsu.services.order.entity.OrderSaveVo;
import com.ziroom.minsu.services.order.entity.OrderSpecialOfferVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.CheckTypeEnum;
import com.ziroom.minsu.valenum.order.OrderAcTypeEnum;
import com.ziroom.minsu.valenum.order.OrderConfigEnum;
import com.ziroom.minsu.valenum.order.PreferentialEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum008Enum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.productrules.ProductRulesTonightDisEnum;
import com.ziroom.minsu.valenum.traderules.CheckOutStrategy;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum0020;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum0020002;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum0020003;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005001001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005002001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005003001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005004001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum007Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum008Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum009Enum;

/**
 * <p>配置信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/3.
 * @version 1.0
 * @since 1.0
 */
@Service("order.orderConfigServiceImpl")
public class OrderConfigServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderConfigServiceImpl.class);

	@Resource(name = "order.orderConfigDao")
	private OrderConfigDao orderConfigDao;


	/**
	 * 获取当前的佣金
	 * @param houseConfMsgEntityList
	 * @param cost
	 * @param userTypeEnum 用户或者房东
	 * @param isDeal 是否放值
	 * @return
	 */
	public Integer getCommissionFromHouseConfigDeal(DataTransferObject dto,List<HouseConfMsgEntity> houseConfMsgEntityList,int cost,UserTypeEnum userTypeEnum,boolean isDeal){
		if(Check.NuNObj(userTypeEnum)){
			LogUtil.error(LOGGER, "【计算佣金】 佣金类型 为空");
			throw new BusinessException("userTypeEnum is null please check it");
		}
		if(Check.NuNCollection(houseConfMsgEntityList)){
			LogUtil.error(LOGGER, "【计算佣金】 房源配置 为空");
			throw  new BusinessException("the houseConfMsgEntityList is null , please check the order_config info");
		}
		//房源配置
		HouseConfMsgEntity houseConfMsgEntity = null;

		String startKey = null;
		if(userTypeEnum.getUserType() == UserTypeEnum.LANDLORD.getUserType()){
			//房东
			startKey = TradeRulesEnum.TradeRulesEnum008.getValue();
		}else if(userTypeEnum.getUserType() == UserTypeEnum.TENANT.getUserType()){
			//房客
			startKey = TradeRulesEnum.TradeRulesEnum009.getValue();
		}else {
			LogUtil.error(LOGGER, "【计算佣金】 用户类型 为空");
			throw new BusinessException("userTypeEnum is error please check it");
		}
		//佣金折扣配置
		HouseConfMsgEntity commConfig = null;
		for(HouseConfMsgEntity config: houseConfMsgEntityList){
			String code = config.getDicCode();
			//收取佣金类型
			if(code.startsWith(startKey)){
				houseConfMsgEntity =config;
			}else if(code.startsWith(TradeRulesEnum.TradeRulesEnum007.getValue())){
				commConfig = config;
			}
		}
		if(Check.NuNObj(houseConfMsgEntity)){
			LogUtil.error(LOGGER,"【计算佣金】,houseConfMsgEntityList：{}",JsonEntityTransform.Object2Json(houseConfMsgEntityList));
			throw  new BusinessException("the houseConfMsgEntityList is null");
		}
		int commissionRst;
		double commission;
		if(houseConfMsgEntity.getDicCode().startsWith(TradeRulesEnum008Enum.TradeRulesEnum008002.getValue())
				|| houseConfMsgEntity.getDicCode().startsWith(TradeRulesEnum009Enum.TradeRulesEnum009001.getValue())){
			commission = ValueUtil.getdoubleValue(houseConfMsgEntity.getDicVal());
			Double rst = BigDecimalUtil.mul(cost, commission);
			commissionRst = rst.intValue();

		}else{
			LogUtil.error(LOGGER, "【计算佣金】 佣金类型只支持 固定和按照比例收取，异常的类型：{}",houseConfMsgEntity.getDicCode());
			throw new BusinessException("Commission is only support pertcent not fix ");
		}

		//房东享受佣金折扣
		if(userTypeEnum.getUserType() == UserTypeEnum.LANDLORD.getUserType()){
			if(!Check.NuNObj(commConfig)){
				double commissionCut = ValueUtil.getdoubleValue(commConfig.getDicVal());
				Double rst = BigDecimalUtil.mul(commissionRst, 1-commissionCut);
				commissionRst = rst.intValue();
			}
		}else {
			if (isDeal){
				dto.putValue("userCommissionRate",commission);
			}
		}
		LogUtil.info(LOGGER, "【计算佣金】 当前的佣金为：{}",commissionRst);
		return commissionRst;
	}

	/**
	 * 获取当前的佣金
	 * @param houseConfMsgEntityList
	 * @param cost
	 * @param userTypeEnum 用户或者房东
	 * @return
	 */
	public Integer getCommissionFromHouseConfig(DataTransferObject dto,List<HouseConfMsgEntity> houseConfMsgEntityList,int cost,UserTypeEnum userTypeEnum){
		return  getCommissionFromHouseConfigDeal(dto,houseConfMsgEntityList,cost,userTypeEnum,true);
	}


	/**
	 * 获取当前的佣金 长租
	 * @param houseConfMsgEntityList
	 * @param cost
	 * @param userTypeEnum 用户或者房东
	 * @param isDeal 是否放值
	 * @return
	 */
	public Integer getCommissionFromHouseConfigChangzuDeal(DataTransferObject dto,List<HouseConfMsgEntity> houseConfMsgEntityList, int cost, UserTypeEnum userTypeEnum,boolean isDeal){
		if(Check.NuNObj(userTypeEnum)){
			LogUtil.error(LOGGER, "【计算佣金】[长租] 佣金类型 为空");
			throw new BusinessException("userTypeEnum is null please check it");
		}
		if(Check.NuNCollection(houseConfMsgEntityList)){
			LogUtil.error(LOGGER, "【计算佣金】 [长租] 房源配置 为空");
			throw  new BusinessException("the houseConfMsgEntityList is null , please check the order_config info");
		}
		//房源配置
		HouseConfMsgEntity houseConfMsgEntity = null;
		String startKey = null;
		if(userTypeEnum.getUserType() == UserTypeEnum.LANDLORD.getUserType()){
			//房东
			startKey = TradeRulesEnum0020002.TradeRulesEnum0020002002.getValue();
		}else if(userTypeEnum.getUserType() == UserTypeEnum.TENANT.getUserType()){
			//房客
			startKey = TradeRulesEnum0020003.TradeRulesEnum0020003002.getValue();
		}else {
			LogUtil.error(LOGGER, "【计算佣金】[长租] 用户类型 为空");
			throw new BusinessException("userTypeEnum is error please check it");
		}
		//佣金折扣配置
		HouseConfMsgEntity commConfig = null;
		for(HouseConfMsgEntity config: houseConfMsgEntityList){
			String code = config.getDicCode();
			//收取佣金类型
			if(code.startsWith(startKey)){
				houseConfMsgEntity =config;
			}else if(code.startsWith(TradeRulesEnum.TradeRulesEnum007.getValue())){
				commConfig = config;
			}
		}
		if(Check.NuNObj(houseConfMsgEntity)){
			LogUtil.error(LOGGER,"【计算佣金】[长租],houseConfMsgEntityList：{}",JsonEntityTransform.Object2Json(houseConfMsgEntityList));
			throw  new BusinessException("the houseConfMsgEntityList is null");
		}
		int commissionRst;
		double commission;
		if(houseConfMsgEntity.getDicCode().startsWith(TradeRulesEnum008Enum.TradeRulesEnum008002.getValue())
				|| houseConfMsgEntity.getDicCode().startsWith(TradeRulesEnum009Enum.TradeRulesEnum009001.getValue())
				|| houseConfMsgEntity.getDicCode().startsWith(TradeRulesEnum0020003.TradeRulesEnum0020003002.getValue())
				|| houseConfMsgEntity.getDicCode().startsWith(TradeRulesEnum0020002.TradeRulesEnum0020002002.getValue())
				){
			commission = ValueUtil.getdoubleValue(houseConfMsgEntity.getDicVal());
			Double rst = BigDecimalUtil.mul(cost, commission);
			commissionRst = rst.intValue();
		}else{
			LogUtil.error(LOGGER, "【计算佣金】[长租] 佣金类型只支持 固定和按照比例收取，异常的类型：{}",houseConfMsgEntity.getDicCode());
			throw new BusinessException("Commission is only support pertcent not fix ");
		}
		//房东享受佣金折扣
		if(userTypeEnum.getUserType() == UserTypeEnum.LANDLORD.getUserType()){
			if(!Check.NuNObj(commConfig)){
				LogUtil.info(LOGGER, "[长租] 免佣金");
				double commissionCut = ValueUtil.getdoubleValue(commConfig.getDicVal());
				Double rst = BigDecimalUtil.mul(commissionRst, 1-commissionCut);
				commissionRst = rst.intValue();
			}
		}else {
			if (isDeal){
				dto.putValue("userCommissionRate",commission);
			}
		}
		LogUtil.info(LOGGER, "【计算佣金】[长租] 当前的佣金为：{}",commissionRst);
		return commissionRst;
	}

	/**
	 * 获取当前的佣金 长租
	 * @param houseConfMsgEntityList
	 * @param cost
	 * @param userTypeEnum 用户或者房东
	 * @return
	 */
	public Integer getCommissionFromHouseConfigChangzu(DataTransferObject dto,List<HouseConfMsgEntity> houseConfMsgEntityList, int cost, UserTypeEnum userTypeEnum){
		return getCommissionFromHouseConfigChangzuDeal(dto,houseConfMsgEntityList,cost,userTypeEnum,true);
	}



	/**
	 * 从房源配置获取 获取押金的值
	 * @author
	 * @param houseConfMsgEntityList
	 * @param orderSaveInfo
	 * @return
	 */
	public Integer getDepositMoneyFromHouseConfig(List<HouseConfMsgEntity> houseConfMsgEntityList,OrderSaveVo orderSaveInfo,OrderNeedHouseVo houseInfo){
		if(Check.NuNObj(houseConfMsgEntityList)){
			LogUtil.error(LOGGER,"参数错误houseConfMsgEntityList:{}", JsonEntityTransform.Object2Json(houseConfMsgEntityList));
			throw new BusinessException("houseConfMsgEntityList is null");
		}
		if(Check.NuNObj(orderSaveInfo.getOrder().getStartTime())){
			LogUtil.error(LOGGER,"参数错误 StartTime:{}", orderSaveInfo.getOrder().getStartTime());
			throw new BusinessException("startTime is null");
		}

		for(HouseConfMsgEntity houseConfMsgEntity: houseConfMsgEntityList){
			if(houseConfMsgEntity.getDicCode().equals(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue())) {
				//直接返回天数
				int dayCount = ValueUtil.getintValue(houseConfMsgEntity.getDicVal());
				//校验第一天的价格是否有特殊价格，如果有按照特殊价格来计算
				String dayKey = DateUtil.dateFormat(orderSaveInfo.getOrder().getStartTime());
				Integer price = orderSaveInfo.getAllPriceMap().get(dayKey);
				return price * dayCount;
			}else if(houseConfMsgEntity.getDicCode().equals(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue())){
				//直接返回固定金额
				return ValueUtil.getintValue(houseConfMsgEntity.getDicVal());
			}
		}
		LogUtil.error(LOGGER,"当前房源没有押金规则配置 houseInfo:{}", JsonEntityTransform.Object2Json(houseInfo));
		throw new BusinessException("no DepositMoney config");

	}

	/**
	 * 从房源配置获取 可享受的折扣
	 * @author afi
	 * @param houseConfMsgEntityList
	 * @param orderSaveInfo
	 * @param cost 消费金额
	 * @return
	 */
	public int fillDiscountFromHouseConfig(List<HouseConfMsgEntity> houseConfMsgEntityList,OrderSaveVo orderSaveInfo,Integer cost){
		if(Check.NuNObj(houseConfMsgEntityList)){
			LogUtil.error(LOGGER,"参数错误 houseConfMsgEntityList:{}", JsonEntityTransform.Object2Json(houseConfMsgEntityList));
			throw new BusinessException("houseConfMsgEntityList is null");
		}
		if(Check.NuNObj(orderSaveInfo.getOrder().getStartTime()) || Check.NuNObj(orderSaveInfo.getOrder().getEndTime())){
			LogUtil.error(LOGGER,"参数错误 statrTime:{}, endTime:{}", orderSaveInfo.getOrder().getStartTime(),orderSaveInfo.getOrder().getEndTime());
			throw new BusinessException("startTime  or endTime is null");
		}
		Double cut = null;
		for(HouseConfMsgEntity houseConfMsgEntity: houseConfMsgEntityList){
			if(houseConfMsgEntity.getDicCode().equals(ProductRulesEnum.ProductRulesEnum0019.getValue())){
				//设置当前的活动
				OrderActivityEntity ac = new OrderActivityEntity();
				ac.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
				ac.setAcFid(ProductRulesEnum.ProductRulesEnum0019.getValue());
				ac.setAcName(ProductRulesEnum.ProductRulesEnum0019.getName());
				cut = BigDecimalUtil.div(ValueUtil.getdoubleValue(houseConfMsgEntity.getDicVal()),100);
				orderSaveInfo.setRentCut(cut);
				Double realCut = BigDecimalUtil.sub(1,cut);
				Double discountMoneyDou = BigDecimalUtil.mul(cost, realCut);
				int discountMoney = discountMoneyDou.intValue();
				ac.setAcMoney(discountMoney);
				ac.setAcType(OrderAcTypeEnum.LAN_CUT_NEW.getCode());
				ac.setPreferentialSources(PreferentialEnum.LANLORD.getCode());
				ac.setPreferentialUser(PreferentialEnum.TENANT.getCode());
				ac.setAcMoneyAll(discountMoney);
				orderSaveInfo.getOrderActivitys().add(ac);

				//增加配置项
				//                OrderConfigEntity orderConfig = new OrderConfigEntity();
				//                orderConfig.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
				//                orderConfig.setConfigCode(ProductRulesEnum.ProductRulesEnum0019.getValue());
				//                orderConfig.setConfigValue(ValueUtil.getStrValue(cut));
				//                orderConfig.setIsValid(YesOrNoEnum.YES.getCode());
				//                orderSaveInfo.getOrderConfigs().add(orderConfig);
				//返回当前的折扣价格
				return discountMoney;
			}
		}
		return 0;
	}



	//    /**
	//     * 从房源配置获取 可享受的折扣
	//     * @author
	//     * @param houseConfMsgEntityList
	//     * @param orderSaveInfo
	//     * @param cost 消费金额
	//     * @return
	//     */
	//    @Deprecated
	//    public int getDiscountFromHouseConfigAndDealAc(List<HouseConfMsgEntity> houseConfMsgEntityList,OrderSaveVo orderSaveInfo,Integer cost){
	//        if(Check.NuNObj(houseConfMsgEntityList)){
	//            LogUtil.error(LOGGER,"参数错误houseConfMsgEntityList:{}", JsonEntityTransform.Object2Json(houseConfMsgEntityList));
	//            throw new BusinessException("houseConfMsgEntityList is null");
	//        }
	//        if(Check.NuNObj(orderSaveInfo.getOrder().getStartTime()) || Check.NuNObj(orderSaveInfo.getOrder().getEndTime())){
	//            LogUtil.error(LOGGER,"参数错误statrTime:{}, endTime:{}", orderSaveInfo.getOrder().getStartTime(),orderSaveInfo.getOrder().getEndTime());
	//            throw new BusinessException("startTime  or endTime is null");
	//        }
	//        HouseConfMsgEntity config30 = null;
	//        HouseConfMsgEntity config7 = null;
	//        HouseConfMsgEntity config3 = null;
	//        //需要清空不享受的折扣
	//
	//        List<HouseConfMsgEntity> listLast = new ArrayList<>();
	//        for(HouseConfMsgEntity houseConfMsgEntity: houseConfMsgEntityList){
	//            if(houseConfMsgEntity.getDicCode().equals(ProductRulesEnum0012Enum.ProductRulesEnum0012003.getValue())){
	//                //默认标志配置无效
	////                houseConfMsgEntity.setIsDel(YesOrNoEnum.YES.getCode());
	//                config30 = houseConfMsgEntity;
	//            }else if(houseConfMsgEntity.getDicCode().equals(ProductRulesEnum0012Enum.ProductRulesEnum0012002.getValue())){
	//                //默认标志配置无效
	////                houseConfMsgEntity.setIsDel(YesOrNoEnum.YES.getCode());
	//                config7 = houseConfMsgEntity;
	//            }else if(houseConfMsgEntity.getDicCode().equals(ProductRulesEnum0012Enum.ProductRulesEnum0012001.getValue())){
	//                //默认标志配置无效
	////                houseConfMsgEntity.setIsDel(YesOrNoEnum.YES.getCode());
	//                config3 = houseConfMsgEntity;
	//            }else {
	//                listLast.add(houseConfMsgEntity);
	//            }
	//        }
	//        //折扣百分比
	//        Double cut = null;
	//        //获取当前的天数
	//        OrderActivityEntity ac = new OrderActivityEntity();
	//        Integer dayCount = orderSaveInfo.getDayCount();
	//        if(!Check.NuNObj(config30) && dayCount >= ProductRulesEnum0012Enum.ProductRulesEnum0012003.getDay()){
	//            //满足30天优惠
	//            ac.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
	//            ac.setAcFid(ProductRulesEnum0012Enum.ProductRulesEnum0012003.getValue());
	//            ac.setAcName(ProductRulesEnum0012Enum.ProductRulesEnum0012003.getName());
	//            cut = ValueUtil.getdoubleValue(config30.getDicVal());
	//            orderSaveInfo.setRentCut(cut);
	//            Double discountMoneyDou = BigDecimalUtil.mul(cost, cut);
	//            int discountMoney = discountMoneyDou.intValue();
	//            ac.setAcMoney(discountMoney);
	//            ac.setAcType(OrderAcTypeEnum.LAN_CUT.getCode());
	//            //将当前的配置标志为有效
	//            this.validOrderConfig(orderSaveInfo.getOrderConfigs(), config30.getDicCode());
	//            orderSaveInfo.getOrderActivitys().add(ac);
	//            return discountMoney;
	//        }else if(!Check.NuNObj(config7) && dayCount >= ProductRulesEnum0012Enum.ProductRulesEnum0012002.getDay()){
	//            //满足7天优惠
	//            ac.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
	//            ac.setAcFid(ProductRulesEnum0012Enum.ProductRulesEnum0012002.getValue());
	//            ac.setAcName(ProductRulesEnum0012Enum.ProductRulesEnum0012002.getName());
	//            cut = ValueUtil.getdoubleValue(config7.getDicVal());
	//            orderSaveInfo.setRentCut(cut);
	//            Double discountMoneyDou = BigDecimalUtil.mul(cost, cut);
	//            int discountMoney = discountMoneyDou.intValue();
	//            ac.setAcMoney(discountMoney);
	//            ac.setAcType(OrderAcTypeEnum.LAN_CUT.getCode());
	//            //将当前的配置标志为有效
	//            this.validOrderConfig(orderSaveInfo.getOrderConfigs(), config7.getDicCode());
	//            orderSaveInfo.getOrderActivitys().add(ac);
	//            return discountMoney;
	//        }else if(!Check.NuNObj(config3) && dayCount >= ProductRulesEnum0012Enum.ProductRulesEnum0012001.getDay()){
	//            //满足3天优惠
	//            ac.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
	//            ac.setAcFid(ProductRulesEnum0012Enum.ProductRulesEnum0012001.getValue());
	//            ac.setAcName(ProductRulesEnum0012Enum.ProductRulesEnum0012001.getName());
	//            cut = ValueUtil.getdoubleValue(config3.getDicVal());
	//            orderSaveInfo.setRentCut(cut);
	//            Double discountMoneyDou = BigDecimalUtil.mul(cost, cut);
	//            int discountMoney = discountMoneyDou.intValue();
	//            ac.setAcMoney(discountMoney);
	//            ac.setAcType(OrderAcTypeEnum.LAN_CUT.getCode());
	//            //将当前的配置标志为有效
	//            this.validOrderConfig(orderSaveInfo.getOrderConfigs(), config3.getDicCode());
	//            orderSaveInfo.getOrderActivitys().add(ac);
	//            return discountMoney;
	//        }else{
	//            return 0;
	//        }
	//    }


	/**
	 * 将列表中的配置标志为有效
	 * @param orderConfigEntityList
	 * @param configCode
	 */
	private void validOrderConfig(List<OrderConfigEntity> orderConfigEntityList,String configCode){
		if(Check.NuNCollection(orderConfigEntityList)){
			return;
		}
		if(Check.NuNStr(configCode)){
			return;
		}
		for(OrderConfigEntity entity: orderConfigEntityList){
			if(entity.getConfigCode().equals(configCode)){
				entity.setIsValid(YesOrNoEnum.YES.getCode());
			}
		}
	}


	/**
	 * 将列表中的配置标志为有效
	 * @param houseConfMsgEntityList
	 * @param configCode
	 */
	private void validConfig(List<HouseConfMsgEntity> houseConfMsgEntityList,String configCode){
		if(Check.NuNCollection(houseConfMsgEntityList)){
			return;
		}
		if(Check.NuNStr(configCode)){
			return;
		}
		for(HouseConfMsgEntity entity: houseConfMsgEntityList){
			if(entity.getDicCode().equals(configCode)){
				entity.setIsDel(YesOrNoEnum.NO.getCode());
			}
		}
	}



	//    /**
	//     * 获取订单的入住时间
	//     * @author
	//     * @param orderSn
	//     * @return
	//     */
	//    public String getCheckInTime(String orderSn){
	//        String code = ProductRulesEnum.ProductRulesEnum003.getValue();
	//        OrderConfigEntity configEntity = orderConfigDao.getOrderConfigByOrderSnAndCode(orderSn, code);
	//        if(Check.NuNObj(configEntity)){
	//            LOGGER.error("request par is {},{}",orderSn,code);
	//            throw  new BusinessException("config is null code:"+code);
	//        }
	//        return configEntity.getConfigValue();
	//    }
	//
	//    /**
	//     * 获取订单的退房点时间 这个暂时去掉
	//     * @author
	//     * @param orderSn
	//     * @return
	//     */
	//    @Deprecated
	//    public String getCheckOutTime(String orderSn){
	//        String code = ProductRulesEnum.ProductRulesEnum004.getValue();
	//        OrderConfigEntity configEntity = orderConfigDao.getOrderConfigByOrderSnAndCode(orderSn, code);
	//        if(Check.NuNObj(configEntity)){
	//            LOGGER.error("request par is {},{}",orderSn,code);
	//            throw  new BusinessException("config is null code:"+code);
	//        }
	//        return configEntity.getConfigValue();
	//    }


	//    /**
	//     * 获取当前订单佣金类型
	//     * @author afi
	//     * @param orderSn
	//     * @param userTypeEnum 用户类型
	//     * @see com.ziroom.minsu.valenum.common.UserTypeEnum
	//     * @return
	//     */
	//    public CommissionVo getCommissionByOrderSn(String orderSn,UserTypeEnum userTypeEnum){
	//
	//        if(Check.NuNObj(userTypeEnum)){
	//            throw new BusinessException("userTypeEnum is null please check it");
	//        }
	//        List<OrderConfigEntity> orderConfigEntityList = orderConfigDao.getOrderConfigListByOrderSn(orderSn);
	//
	//        if(Check.NuNObj(orderConfigEntityList)){
	//            throw  new BusinessException("the getCommissionByOrderSn is null , please check the order_config info");
	//        }
	//        OrderConfigEntity orderConfigEntity = null;
	//
	//        String startKey = null;
	//        if(userTypeEnum.getUserType() == UserTypeEnum.LANDLORD.getUserType()){
	//            //房东
	//            startKey = TradeRulesEnum.TradeRulesEnum008.getValue();
	//        }else if(userTypeEnum.getUserType() == UserTypeEnum.TENANT.getUserType()){
	//            //房客
	//            startKey = TradeRulesEnum.TradeRulesEnum009.getValue();
	//        }else {
	//            throw new BusinessException("userTypeEnum is error please check it");
	//        }
	//        for(OrderConfigEntity config: orderConfigEntityList){
	//            String code = config.getConfigCode();
	//            //过滤退订政策
	//            if(code.startsWith(startKey)){
	//                orderConfigEntity =config;
	//            }
	//        }
	//        if(Check.NuNObj(orderConfigEntity)){
	//            LOGGER.error("request par is {}",orderSn);
	//            throw  new BusinessException("the getCommissionByOrderSn is null");
	//        }
	//        CommissionVo commission = new CommissionVo();
	//        if(orderConfigEntity.getConfigCode().startsWith(TradeRulesEnum008Enum.TradeRulesEnum008001.getValue())){
	//            commission.setMoneyType(MoneyTypeEnum.FIX.getCode());
	//            commission.setMoneyCost(ValueUtil.getdoubleValue(orderConfigEntity.getConfigValue()));
	//        }else{
	//            commission.setMoneyType(MoneyTypeEnum.PERSENT.getCode());
	//            commission.setMoneyCost(ValueUtil.getdoubleValue(orderConfigEntity.getConfigValue()));
	//        }
	//        return commission;
	//    }



	/**
	 * 获取当前订单的退订策略
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public CheckOutStrategy getCheckOutStrategyByOrderSn(String orderSn){
		List<OrderConfigEntity> orderConfigEntityList = orderConfigDao.getOrderConfigListByOrderSn(orderSn);

		if(Check.NuNObj(orderConfigEntityList)){
			LogUtil.error(LOGGER,"查询订单配置错误，orderSn:{}",orderSn);
			throw  new BusinessException("查询订单配置错误");
		}
		//初始化退订政策
		CheckOutStrategy checkOutStrategy = new CheckOutStrategy();
		List<OrderConfigEntity> checkOutConfigEntityList = new ArrayList<>();
		for(OrderConfigEntity config: orderConfigEntityList){
			String code = config.getConfigCode();
			//过滤退订政策
			if(code.startsWith(TradeRulesEnum.TradeRulesEnum005.getValue())){
				checkOutConfigEntityList.add(config);
			}else  if (code.equals(TradeRulesEnum0020.TradeRulesEnum0020001.getValue())){
				checkOutConfigEntityList.add(config);
				//满足长租的配置
				checkOutStrategy.setChangzuFlag(true);
			}
		}
		if(Check.NuNObj(checkOutConfigEntityList)){
			LogUtil.error(LOGGER,"异常的配置，config:{}",JsonEntityTransform.Object2Json(checkOutConfigEntityList));
			throw new BusinessException("退订政策为空，或者异常的退订政策配置");
		}
		//遍历具体的配置信息
		for(OrderConfigEntity configEntity: checkOutConfigEntityList){
			this.dealConfig(configEntity,checkOutStrategy);
		}
		return checkOutStrategy;
	}


	/**
	 * 
	 * 查询订单的优惠信息
	 * 1. 查询折扣信息
	 * 2. 查询灵活定价信息
	 * @author yd
	 * @created 2017年4月12日 下午4:20:01
	 *
	 * @param orderSn
	 * @return
	 */
	public List<OrderSpecialOfferVo> findOrderSpecialOffer(String orderSn){

		List<OrderConfigEntity> orderConfigEntityList = orderConfigDao.getOrderConfigListByOrderSn(orderSn);



		if(Check.NuNObj(orderConfigEntityList)){
			LogUtil.error(LOGGER,"查询订单配置错误,orderSn:{}",orderSn);
			throw  new BusinessException("【findOrderSpecialOffer】查询订单配置错误:orderSn="+orderSn);
		}

		List<OrderSpecialOfferVo> list = new LinkedList<OrderSpecialOfferVo>();

		try {
			for (OrderConfigEntity config : orderConfigEntityList) {
				String code = config.getConfigCode();
				OrderSpecialOfferVo orderSpecialOfferVo = new OrderSpecialOfferVo();
				orderSpecialOfferVo.setCode(code);
				orderSpecialOfferVo.setVal(config.getConfigValue());
				if(ProductRulesEnum.ProductRulesEnum0019.getValue().equals(code)
						||code.startsWith(ProductRulesEnum.ProductRulesEnum020.getValue())
						||ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getCode().equals(code)){

					if(ProductRulesEnum.ProductRulesEnum0019.getValue().equals(code)){
						orderSpecialOfferVo.setShowName(ProductRulesEnum.ProductRulesEnum0019.getName());
						orderSpecialOfferVo.setShowVal(DataFormat.formatIntegerVal(config.getConfigValue()));
					}
					if(code.startsWith(ProductRulesEnum.ProductRulesEnum020.getValue())){
						ProductRulesEnum020 productRulesEnum020 = ProductRulesEnum020.getByCode(code);
						if(!Check.NuNObj(productRulesEnum020)){
							orderSpecialOfferVo.setShowName(productRulesEnum020.getName());
							orderSpecialOfferVo.setShowVal(config.getConfigValue());
						}
					}
					if(ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getCode().equals(code)){
						orderSpecialOfferVo.setShowName(ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getVal());
						orderSpecialOfferVo.setShowVal(config.getConfigValue());
					}
					list.add(orderSpecialOfferVo);
				}
			
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【findOrderSpecialOffer】查询订单配置异常e={},orderSn={}", e,orderSn);
		}
		
		return list;
	}

	/**
	 * 处理退房策略
	 * @author afi
	 * @param configEntity
	 * @param checkOutStrategy
	 */
	private void dealConfig(OrderConfigEntity configEntity,CheckOutStrategy checkOutStrategy){
		String code = configEntity.getConfigCode();
		if(code.startsWith(TradeRulesEnum005Enum.TradeRulesEnum005001.getValue())){
			TradeRulesEnum005001001Enum em =TradeRulesEnum005001001Enum.getEmbyCode(code);
			em.trans2Rull(checkOutStrategy, ValueUtil.getStrValue(configEntity.getConfigValue()));
		}else if(code.startsWith(TradeRulesEnum005Enum.TradeRulesEnum005002.getValue())){
			TradeRulesEnum005002001Enum em =TradeRulesEnum005002001Enum.getEmbyCode(code);
			em.trans2Rull(checkOutStrategy, ValueUtil.getStrValue(configEntity.getConfigValue()));
		}else if(code.startsWith(TradeRulesEnum005Enum.TradeRulesEnum005003.getValue())){
			TradeRulesEnum005003001Enum em =TradeRulesEnum005003001Enum.getEmbyCode(code);
			em.trans2Rull(checkOutStrategy, ValueUtil.getStrValue(configEntity.getConfigValue()));
		}else if(code.startsWith(TradeRulesEnum005Enum.TradeRulesEnum005004.getValue())){
			TradeRulesEnum005004001Enum em =TradeRulesEnum005004001Enum.getEmbyCode(code);
			em.trans2Rull(checkOutStrategy, ValueUtil.getStrValue(configEntity.getConfigValue()));
		}else if(code.equals(TradeRulesEnum0020.TradeRulesEnum0020001.getValue())){
			checkOutStrategy.setChangzuCount(ValueUtil.getintValue(configEntity.getConfigValue()));
		}else {
			LogUtil.error(LOGGER,"退订政策错误，当前的策略还不支持ConfigCode：{}",code);
		}
	}


	/**
	 * 获取订单配置：优惠折扣、佣金、结算方式
	 * @author lishaochuan
	 * @create 2016年4月19日
	 * @param orderSn
	 * @return
	 */
	public OrderConfigVo getOrderConfigVo(String orderSn) {
		OrderConfigVo orderConfigVo = new OrderConfigVo();

		List<OrderConfigEntity> orderConfigEntityList = orderConfigDao.getOrderConfigListByOrderSn(orderSn);
		if(Check.NuNObj(orderConfigEntityList)){
			LogUtil.error(LOGGER, "查询订单配置信息为空，orderSn:{}", orderSn);
			throw new BusinessException("查询订单配置信息为空");
		}

		for (OrderConfigEntity orderConfigEntity : orderConfigEntityList) {
			if(orderConfigEntity.getConfigCode().equals(TradeRulesEnum007Enum.TradeRulesEnum007001.getValue())){
				// 房东按订单结算，佣金优惠率
				orderConfigVo.setCheckType(CheckTypeEnum.ORDER.getCode());
				orderConfigVo.setCommissionDiscountRateLandlord(ValueUtil.getdoubleValue(orderConfigEntity.getConfigValue()));
			} else if(orderConfigEntity.getConfigCode().equals(TradeRulesEnum007Enum.TradeRulesEnum007002.getValue())){
				// 房东按天结算，佣金优惠率
				orderConfigVo.setCheckType(CheckTypeEnum.DAY.getCode());
				orderConfigVo.setCommissionDiscountRateLandlord(ValueUtil.getdoubleValue(orderConfigEntity.getConfigValue()));
			}else if (orderConfigEntity.getConfigCode().equals(OrderConfigEnum.LAN_COMM_FREE.getCode())){
				//设置房东是否免佣金的活动
				orderConfigVo.setLandCom(orderConfigEntity.getConfigValue());
			}else if (orderConfigEntity.getConfigCode().equals(TradeRulesEnum0020.TradeRulesEnum0020001.getValue())){
				//是长租
				orderConfigVo.setChangzuFlag(true);
			}
		}
		if (orderConfigVo.isChangzuFlag()){
			//获取长租的配置
			this.dealChangzuOrderConfig(orderConfigVo,orderConfigEntityList);
		}else {
			//获取普通短租配置
			this.dealDefaultOrderConfig(orderConfigVo,orderConfigEntityList);
		}

		if (Check.NuNObj(orderConfigVo.getDiscountRate()) || Check.NuNObj(orderConfigVo.getCommissionRateLandlord()) || Check.NuNObj(orderConfigVo.getCommissionRateUser())) {
			LogUtil.info(LOGGER, "订单配置信息不全 | orderConfigVo:{}", JsonEntityTransform.Object2Json(orderConfigVo));
			throw new BusinessException("订单配置信息不全");
		}
		return orderConfigVo;
	}

	/**
	 * 填充普通的订单配置信息
	 * @author afi
	 * @param orderConfigVo
	 * @param orderConfigEntityList
	 */
	private void dealChangzuOrderConfig(OrderConfigVo orderConfigVo, List<OrderConfigEntity> orderConfigEntityList){
		if (Check.NuNObj(orderConfigVo)){
			throw new BusinessException("长租 orderConfigVo 为空");
		}
		if (Check.NuNCollection(orderConfigEntityList)){
			throw new BusinessException("长租 订单配置项 为空");
		}
		for (OrderConfigEntity orderConfigEntity : orderConfigEntityList) {
			//需要确认一下
			if (orderConfigEntity.getConfigCode().equals(TradeRulesEnum0020002.TradeRulesEnum0020002002.getValue())) {
				// 房东佣金
				orderConfigVo.setCommissionRateLandlord(ValueUtil.getdoubleValue(orderConfigEntity.getConfigValue()));
			} else if (orderConfigEntity.getConfigCode().equals(TradeRulesEnum0020003.TradeRulesEnum0020003002.getValue())){
				// 租客佣金
				orderConfigVo.setCommissionRateUser(ValueUtil.getdoubleValue(orderConfigEntity.getConfigValue()));
			}
			//去掉了折扣
		}
	}


	/**
	 * 填充普通的订单配置信息
	 * @author afi
	 * @param orderConfigVo
	 * @param orderConfigEntityList
	 */
	private void dealDefaultOrderConfig(OrderConfigVo orderConfigVo, List<OrderConfigEntity> orderConfigEntityList){
		if (Check.NuNObj(orderConfigVo)){
			throw new BusinessException("orderConfigVo 为空");
		}
		if (Check.NuNCollection(orderConfigEntityList)){
			throw new BusinessException("订单配置项 为空");
		}
		for (OrderConfigEntity orderConfigEntity : orderConfigEntityList) {
			if (orderConfigEntity.getConfigCode().startsWith(ProductRulesEnum.ProductRulesEnum0012.getValue())) {
				// 优惠折扣
				orderConfigVo.setDiscountRate(ValueUtil.getdoubleValue(orderConfigEntity.getConfigValue()));

			} else if (orderConfigEntity.getConfigCode().equals(TradeRulesEnum008Enum.TradeRulesEnum008002.getValue())) {
				// 房东佣金
				orderConfigVo.setCommissionRateLandlord(ValueUtil.getdoubleValue(orderConfigEntity.getConfigValue()));

			} else if (orderConfigEntity.getConfigCode().equals(TradeRulesEnum009Enum.TradeRulesEnum009001.getValue())){
				// 租客佣金
				orderConfigVo.setCommissionRateUser(ValueUtil.getdoubleValue(orderConfigEntity.getConfigValue()));

			}
		}
	}

	/**
	 * 插入orderConf实体
	 *
	 * @author loushuai
	 * @created 2017年5月11日 下午9:33:54
	 *
	 * @param orderConfigEntity
	 */
	public void insertOrderConf(OrderConfigEntity orderConfigEntity) {
		orderConfigDao.insertOrderConfig(orderConfigEntity);
	}

	/**
	 * 根据orderSn，confCode 修改t_order_config的config_value
	 *
	 * @author loushuai
	 * @created 2017年5月12日 下午1:40:45
	 *
	 * @param confValue
	 */
	public int updateOrderConfValue(String orderSn, String confCode, String confValue) {
		return orderConfigDao.updateOrderConfValue(orderSn, confCode, confValue);
	}
}
