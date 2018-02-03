package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import com.ziroom.minsu.services.order.constant.OrderMessageConst;
import com.ziroom.minsu.services.order.entity.OrderSaveVo;
import com.ziroom.minsu.services.order.service.OrderConfigServiceImpl;
import com.ziroom.minsu.valenum.cms.ActTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.order.CouponStatusEnum;
import com.ziroom.minsu.valenum.order.OrderAcTypeEnum;
import com.ziroom.minsu.valenum.order.PreferentialEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>订单优惠券的处理逻辑</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/15.
 * @version 1.0
 * @since 1.0
 */
@Service("order.orderCouponProxy")
public class OrderCouponProxy {


    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCouponProxy.class);


    @Resource(name = "order.messageSource")
    private MessageSource messageSource;


    @Resource(name = "order.orderConfigServiceImpl")
    private OrderConfigServiceImpl orderConfigService;

    /**
     * 获取优惠券的金额
     * @author afi
     * @param dto
     * @param allPriceMap
     * @param orderSaveInfo
     * @return
     */
    public int dealAndGetCouponMoney(DataTransferObject dto,Map<String,Integer> allPriceMap,OrderSaveVo orderSaveInfo,OrderNeedHouseVo houseInfo){
        //初始化优惠券信息
        int couponMoney = 0;
        ActCouponUserEntity couponOrderEntity = orderSaveInfo.getCouponEntity();
        //当前的优惠券信息存在
        if (dto.getCode() == DataTransferObject.SUCCESS && !Check.NuNObj(couponOrderEntity)){
            if(couponOrderEntity.getActType() == ActTypeEnum.CACHE.getCode()
                    || couponOrderEntity.getActType() == ActTypeEnum.RANDOM.getCode()){
                //获取现金券的逻辑
                return dealAndGetCacheCouponMoney(dto, allPriceMap, orderSaveInfo);
            }else if (couponOrderEntity.getActType() == ActTypeEnum.CUT.getCode()){
                return dealAndGetCutCouponMoney(dto,orderSaveInfo);
            }else if (couponOrderEntity.getActType() == ActTypeEnum.FREE.getCode()){
                return dealAndGetFreeCouponMoney(dto,allPriceMap,orderSaveInfo,houseInfo);
            }else {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ACT_COUPON_MONEY_TYPES));
            }

        }
        return couponMoney;
    }

    /**
     * 获取折扣券金额
     * @author jixd
     * @created 2016年11月17日 11:36:22
     * @param
     * @return
     */
    private int dealAndGetCutCouponMoney(DataTransferObject dto, OrderSaveVo orderSaveInfo) {
        //初始化优惠券信息
        int couponMoney = 0;
        ActCouponUserEntity couponOrderEntity = orderSaveInfo.getCouponEntity();
        //当前的优惠券信息存在
        if (dto.getCode() != DataTransferObject.SUCCESS || Check.NuNObj(couponOrderEntity)){
            return couponMoney;
        }
        //只处理折扣券
        if(couponOrderEntity.getActType() == ActTypeEnum.CUT.getCode()){
            if(!Check.NuNObj(couponOrderEntity.getCheckInTime())){
                if(orderSaveInfo.getOrder().getStartTime().before(couponOrderEntity.getCheckInTime())){
                    LogUtil.info(LOGGER, "【下单】 折扣优惠券还未到使用时间 优惠券信息 {}", JsonEntityTransform.Object2Json(couponOrderEntity));
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("入住时间段不满足优惠券的使用条件");
                    return couponMoney;
                }
            }
            if(!Check.NuNObj(couponOrderEntity.getCheckOutTime())){
                if (couponOrderEntity.getCheckOutTime().before(orderSaveInfo.getOrder().getEndTime())){
                    LogUtil.info(LOGGER, "【下单】 折扣优惠券过期 优惠券信息 {}", JsonEntityTransform.Object2Json(couponOrderEntity));
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("入住时间段不满足优惠券的使用条件");
                    return couponMoney;
                }
            }

            //优惠券的基数是 折扣之前的房租 - 折扣金额
            int base = orderSaveInfo.getCost() - orderSaveInfo.getOrderMoney().getDiscountMoney();
            Integer actCut = orderSaveInfo.getCouponEntity().getActCut();
            Integer actMax = orderSaveInfo.getCouponEntity().getActMax();
            //获取优惠金额 然后向上取整
            couponMoney = (int)BigDecimalUtil.mul(base, (1 - actCut / 100.0));
            LogUtil.info(LOGGER,"折扣券优惠金额couponMoney={}",couponMoney);
            if (couponMoney > actMax){
                //优惠金额超过优惠券设置最大值 直接使用最大值抵扣
                couponMoney = actMax;
            }

            OrderActivityEntity ac = new OrderActivityEntity();
            //当前订单参加的优惠券活动
            ac.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
            ac.setAcFid(couponOrderEntity.getCouponSn());
            ac.setAcName(couponOrderEntity.getCouponName());
            ac.setAcMoney(couponMoney);
            ac.setAcStatus(CouponStatusEnum.FROZEN.getCode());
            ac.setAcType(OrderAcTypeEnum.COUPON.getCode());
            ac.setPreferentialSources(PreferentialEnum.PLATFORM.getCode());
            ac.setPreferentialUser(PreferentialEnum.TENANT.getCode());
            ac.setAcMoneyAll(couponMoney);
            //填充优惠券信息
            orderSaveInfo.setCouponAc(ac);
            orderSaveInfo.getOrderActivitys().add(ac);
        }
        return couponMoney;
    }


    /**
     * 获取现金优惠券的金额
     * @author afi
     * @param dto
     * @param allPriceMap
     * @param orderSaveInfo
     * @return
     */
    private int dealAndGetCacheCouponMoney(DataTransferObject dto,Map<String,Integer> allPriceMap,OrderSaveVo orderSaveInfo){
        //初始化优惠券信息
        int couponMoney = 0;
        ActCouponUserEntity couponOrderEntity = orderSaveInfo.getCouponEntity();
        //当前的优惠券信息存在
        if (dto.getCode() != DataTransferObject.SUCCESS || Check.NuNObj(couponOrderEntity)){
            return couponMoney;
        }
        //只处理现金类型优惠券
        if(couponOrderEntity.getActType() == ActTypeEnum.CACHE.getCode()
                || couponOrderEntity.getActType() == ActTypeEnum.RANDOM.getCode()){
            //获取现金券的逻辑
            if(!Check.NuNObj(couponOrderEntity.getCheckInTime())){
                if(orderSaveInfo.getOrder().getStartTime().before(couponOrderEntity.getCheckInTime())){
                    LogUtil.info(LOGGER, "【下单】 优惠券还未到使用时间 优惠券信息 {}", JsonEntityTransform.Object2Json(couponOrderEntity));
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("入住时间段不满足优惠券的使用条件");
                    return couponMoney;
                }
            }
            if(!Check.NuNObj(couponOrderEntity.getCheckOutTime())){
                if (couponOrderEntity.getCheckOutTime().before(orderSaveInfo.getOrder().getEndTime())){
                    LogUtil.info(LOGGER, "【下单】 优惠券过期 优惠券信息 {}", JsonEntityTransform.Object2Json(couponOrderEntity));
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("入住时间段不满足优惠券的使用条件");
                    return couponMoney;
                }
            }
            int sum = ValueUtil.getintValue(allPriceMap.get("sum"));
            //获取当前的优惠券的最小金额
            int actLimit = couponOrderEntity.getActLimit();
            if (actLimit > sum){
                //当前的优惠券的最小金额限制不满足
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ACT_COUPON_MONEY_LIMIT));
                return couponMoney;
            }else {
                int realCut = ValueUtil.getMin(couponOrderEntity.getActCut(),sum);
                //参加了当前的优惠券
                OrderActivityEntity ac = new OrderActivityEntity();
                //当前订单参加的优惠券活动
                ac.setPreferentialSources(PreferentialEnum.PLATFORM.getCode());
                ac.setPreferentialUser(PreferentialEnum.TENANT.getCode());
                ac.setAcMoneyAll(realCut);
                ac.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
                ac.setAcFid(couponOrderEntity.getCouponSn());
                ac.setAcName(couponOrderEntity.getCouponName());
                ac.setAcMoney(realCut);
                ac.setAcStatus(CouponStatusEnum.FROZEN.getCode());
                ac.setAcType(OrderAcTypeEnum.COUPON.getCode());
                //填充优惠券信息
                orderSaveInfo.setCouponAc(ac);
                orderSaveInfo.getOrderActivitys().add(ac);
                couponMoney = realCut;
            }
        }
        return couponMoney;
    }


    /**
     * 获取面单优惠券的金额
     * @author afi
     * @param dto
     * @param allPriceMap
     * @param orderSaveInfo
     * @return
     */
    private int dealAndGetFreeCouponMoney(DataTransferObject dto,Map<String,Integer> allPriceMap,OrderSaveVo orderSaveInfo,OrderNeedHouseVo houseInfo){
        //初始化优惠券信息
        int couponMoney = 0;
        ActCouponUserEntity couponOrderEntity = orderSaveInfo.getCouponEntity();
        //当前的优惠券信息存在
        if (dto.getCode() != DataTransferObject.SUCCESS || Check.NuNObj(couponOrderEntity)){
            return couponMoney;
        }
        //只处理面单类型优惠券
        if(couponOrderEntity.getActType() == ActTypeEnum.FREE.getCode()){
            //获取现金券的逻辑
            Date start = DateSplitUtil.getCompareDateFrom(couponOrderEntity.getCheckInTime(), orderSaveInfo.getOrder().getStartTime());
            Date end = DateSplitUtil.getCompareDateTo(couponOrderEntity.getCheckOutTime(), orderSaveInfo.getOrder().getEndTime());
            if (start.after(end)){
                //当前优惠券不可用
                LogUtil.info(LOGGER, "【下单】 优惠券不可用 {} ，订单信息：{}", JsonEntityTransform.Object2Json(couponOrderEntity),JsonEntityTransform.Object2Json(orderSaveInfo.getOrder()));
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, OrderMessageConst.ORDER_CONPON_TIME));
                return couponMoney;
            }
            List<Date> days = DateSplitUtil.dateSplit(start, end);
            //优惠券的有效时间是 活动区间和订单时间的最小值
            int acDays = ValueUtil.getMin(days.size(), couponOrderEntity.getActCut());
            Map<String,Integer> freeMap = new HashMap<>();
            //遍历当前的免天的金额信息
            for (int i = 0; i < acDays; i++) {
                Date date = days.get(i);
                String str = DateUtil.dateFormat(date);
                int price = allPriceMap.get(str);
                couponMoney += price;
                freeMap.put(str,price);
            }
            LogUtil.info(LOGGER, "【下单】 【免单的优惠券】当前订单的有效天数：{}，当前活动的免天数：{}，实际的免单天数",days.size(),couponOrderEntity.getActCut(),acDays);
            LogUtil.info(LOGGER, "【下单】 【免单的优惠券】免单的天：{}", JsonEntityTransform.Object2Json(freeMap));

            //获取优惠券抵扣的房租的折扣金额
            int couponDiscountMoney = orderConfigService.fillDiscountFromHouseConfig(houseInfo.getHouseConfList(), orderSaveInfo, couponMoney);
            //优惠券需要减去 房租的折扣金额
            couponMoney = couponMoney - couponDiscountMoney;

            //优惠券的金额对应的佣金
            int userCommMoney = 0;
            if (orderSaveInfo.getChangzuFlag()){
                userCommMoney = orderConfigService.getCommissionFromHouseConfigChangzuDeal(dto,houseInfo.getHouseConfList(), couponMoney, UserTypeEnum.TENANT,false);
            }else {
                userCommMoney = orderConfigService.getCommissionFromHouseConfigDeal(dto,houseInfo.getHouseConfList(), couponMoney, UserTypeEnum.TENANT,false);
            }
            //优惠券处理优惠房租 并加上服务费
            couponMoney += userCommMoney;
            //优惠券需要支持清洁费
            couponMoney += orderSaveInfo.getOrderMoney().getCleanMoney();

            //校验当前的优惠券的使用金额
            if (!Check.NuNObj(couponOrderEntity.getActMax())
                    &&  couponOrderEntity.getActMax() > 0
                    && couponMoney > couponOrderEntity.getActMax()){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("抱歉，超过优惠券使用范围(金额不超过"+BigDecimalUtil.div(couponOrderEntity.getActMax(),100)+"元)");
                return couponMoney;
            }

            LogUtil.info(LOGGER, "【下单】 【免单的优惠券】免房租：{}，免佣金：{}，累计：{}",couponMoney-userCommMoney,userCommMoney,couponMoney);
            //参加了当前的优惠券
            OrderActivityEntity ac = new OrderActivityEntity();
            //当前订单参加的优惠券活动
            ac.setOrderSn(orderSaveInfo.getOrder().getOrderSn());
            ac.setAcFid(couponOrderEntity.getCouponSn());
            ac.setAcName(couponOrderEntity.getCouponName());
            ac.setAcMoney(couponMoney);
            ac.setAcStatus(CouponStatusEnum.FROZEN.getCode());
            ac.setAcType(OrderAcTypeEnum.COUPON.getCode());
            ac.setPreferentialSources(PreferentialEnum.PLATFORM.getCode());
            ac.setPreferentialUser(PreferentialEnum.TENANT.getCode());
            ac.setAcMoneyAll(couponMoney);
            //填充优惠券信息
            orderSaveInfo.setCouponAc(ac);
            orderSaveInfo.getOrderActivitys().add(ac);
        }
        return couponMoney;
    }




    public static void main(String[] args) throws Exception{
        Date couponDate = DateUtil.parseDate("2016-06-17 19:56:13", "yyyy-MM-dd HH:mm:ss");

        Date orderDate = DateUtil.parseDate("2016-06-17", "yyyy-MM-dd");


    }
}
