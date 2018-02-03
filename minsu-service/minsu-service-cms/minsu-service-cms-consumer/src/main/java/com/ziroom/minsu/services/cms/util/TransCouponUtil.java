package com.ziroom.minsu.services.cms.util;

import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.services.cms.entity.OutCouponInfoVo;
import com.ziroom.minsu.services.cms.entity.ZiRoomCouponInfoVo;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.valenum.cms.ActTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.CouponStatusEnum;

/**
 * <p>优惠券的转换</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/22.
 * @version 1.0
 * @since 1.0
 */
public class TransCouponUtil {


    private static final String dateFormat = "yyyy/MM/dd";

    /**
     * 将优惠券信息转化成对外的信息
     * @param entity
     * @return
     */
    public static OutCouponInfoVo transCouponEle(ActCouponUserEntity entity){
        if (Check.NuNObj(entity)){
            return null;
        }
        OutCouponInfoVo vo = new OutCouponInfoVo();
        vo.setCardId(entity.getCouponSn());
        String title = "";
        String unit = "";
        String unitDes = "";
        String price = "";
        String all = "全场通用";
        if (entity.getActType() == ActTypeEnum.FREE.getCode()){
            //免单
            if (!Check.NuNObj(entity.getActMax()) && entity.getActMax() > 0){
                title = "最高抵"+ DataFormat.formatHundredPriceInt(entity.getActMax())+"元";
            }
            price = "免"+entity.getActCut()+"天";
        }else if (entity.getActType() == ActTypeEnum.CACHE.getCode() || entity.getActType() == ActTypeEnum.RANDOM.getCode()){
            //现金、随机
            if (!Check.NuNObj(entity.getActLimit()) && entity.getActLimit() > 0){
                title = "满"+DataFormat.formatHundredPriceInt(entity.getActLimit())+"元可用";
            }else {
                title = all;
            }
            price = DataFormat.formatHundredPriceInt(entity.getActCut()) +"";

            unit = "¥";
            unitDes  = "元";
        }else if (entity.getActType() == ActTypeEnum.CUT.getCode()){
            if (!Check.NuNObj(entity.getActMax()) && entity.getActMax() > 0){
                title = "最高抵"+DataFormat.formatHundredPriceInt(entity.getActMax())+"元";
            }
            price = entity.getActCut()/10.0+"折";
        }

        if(entity.getIsLimitHouse() == YesOrNoEnum.YES.getCode()){
            if(all.equals(title)){
                title = "部分房源可用";
            }else{
                title += ",部分房源可用";
            }
        }


        if (!Check.NuNObj(entity.getCheckInTime()) && !Check.NuNObj(entity.getCheckOutTime())){
            String subTitle = "入住时间段："+ DateUtil.dateFormat(entity.getCheckInTime(),dateFormat)+"至"+DateUtil.dateFormat(entity.getCheckOutTime(),dateFormat);
            vo.setSubtitle(subTitle);
        }
        vo.setPrice(price);
        vo.setUnit(unit);
        vo.setUnitDes(unitDes);
        vo.setTitle(title);
        vo.setCardName(entity.getCouponName());
        vo.setStartDate(DateUtil.dateFormat(entity.getCouponStartTime()));
        vo.setEndDate(DateUtil.dateFormat(entity.getCouponEndTime()));
        //优惠券状态
        CouponStatusEnum statusEnum = CouponStatusEnum.getByCode(entity.getCouponStatus());
        vo.setState(statusEnum.getOutCode());
        CouponStatusEnum couponStatusEnum = CouponStatusEnum.getByCode(entity.getCouponStatus());
        if (!Check.NuNObj(couponStatusEnum)){
            vo.setState(couponStatusEnum.getOutCode());
        }
        return vo;
    }



    /**
     * 将优惠券信息转化成对外的信息
     * @param entity
     * @return
     */
    public static ZiRoomCouponInfoVo transCouponZiroomEle(ActCouponUserEntity entity){
        if (Check.NuNObj(entity)){
            return null;
        }
        ZiRoomCouponInfoVo vo = new ZiRoomCouponInfoVo();
        vo.setCardId(entity.getCouponSn());
        String title = "";
        String unit = "";
        String price = "";
        String all = "全场通用";
        if (entity.getActType() == ActTypeEnum.FREE.getCode()){
            //免单
            if (!Check.NuNObj(entity.getActMax()) && entity.getActMax() > 0){
                title = "最高抵"+DataFormat.formatHundredPriceInt(entity.getActMax())+"元";
            }
            price = "免"+entity.getActCut()+"天";
        }else if (entity.getActType() == ActTypeEnum.CACHE.getCode() || entity.getActType() == ActTypeEnum.RANDOM.getCode()){
            //现金、随机
            if (!Check.NuNObj(entity.getActLimit()) && entity.getActLimit() > 0){
                title = "满"+DataFormat.formatHundredPriceInt(entity.getActLimit())+"元可用";
            }else {
                title = all;
            }
            price = BigDecimalUtil.div(entity.getActCut(),100) +"";

            unit = "¥";
        }else if (entity.getActType() == ActTypeEnum.CUT.getCode()){
            if (!Check.NuNObj(entity.getActMax()) && entity.getActMax() > 0){
                title = "最高抵"+ DataFormat.formatHundredPriceInt(entity.getActMax())+"元";
            }
            price = entity.getActCut()/10.0+"折";

        }

        if(entity.getIsLimitHouse() == YesOrNoEnum.YES.getCode()){
            if(all.equals(title)){
                title = "部分房源可用";
            }else{
                title += ",部分房源可用";
            }
        }

        if (!Check.NuNObj(entity.getCheckInTime()) && !Check.NuNObj(entity.getCheckOutTime())){
            String subTitle = "入住时间段："+DateUtil.dateFormat(entity.getCheckInTime(),dateFormat)+"至"+DateUtil.dateFormat(entity.getCheckOutTime(),dateFormat);
            vo.setSubDescription(subTitle);
        }
        vo.setPrice(price);
        vo.setUnit(unit);
        vo.setDescription(title);
        vo.setCardName(entity.getCouponName());
        vo.setStartDate(DateUtil.dateFormat(entity.getCouponStartTime(), dateFormat));
        vo.setEndDate(DateUtil.dateFormat(entity.getCouponEndTime(),dateFormat));
        CouponStatusEnum statusEnum = CouponStatusEnum.getByCode(entity.getCouponStatus());
        vo.setStatus(statusEnum.getOutCode()+"");
        return vo;
    }

}
