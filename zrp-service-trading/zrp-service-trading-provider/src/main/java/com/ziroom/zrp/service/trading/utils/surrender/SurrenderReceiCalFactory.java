package com.ziroom.zrp.service.trading.utils.surrender;

import com.ziroom.zrp.service.trading.pojo.CalNeedPojo;
import com.ziroom.zrp.service.trading.pojo.CalReturnPojo;
import com.ziroom.zrp.service.trading.valenum.surrender.SurTypeEnum;

import java.text.ParseException;

/**
 * <p>工厂模式，来解约不同退租原因下的费用结算</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author wangxm113
 * @version 1.0
 * @Date Created in 2017年11月04日
 * @since 1.0
 */
public class SurrenderReceiCalFactory {

    /**
     * 工厂模式
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日
     */
    public static CalculateInterface createComputationalFormula(CalNeedPojo paramEntity) throws ParseException {
        CalculateInterface calculateInterface;
        Integer surType = Integer.valueOf(paramEntity.getSearchPojo().getSurType());

        if (surType == SurTypeEnum.NORMAL.getCode()) {//正常退租
            calculateInterface = new CalOfNormal();
        } else if (surType == SurTypeEnum.ABNORMAL.getCode()) {//非正常退租
            calculateInterface = new CalOfAbnormal();
        } else if (surType == SurTypeEnum.ONE_SIDED.getCode()) {//客户单方面解约
            if ("0".equals(paramEntity.getContract().getConTplVersion())) {//合同版本为0
                calculateInterface = new CalOfOneSidedForV0(paramEntity);
            } else {//合同版本为1
                calculateInterface = new CalOfOneSidedForV1(paramEntity);
            }
        } else if (surType == SurTypeEnum.THREE_DAYS_NOT_SATISFIED.getCode()) {//三天不满意退租
            calculateInterface = new CalOfThreeDaysNotSatisfied();
        } else if (surType == SurTypeEnum.IN_RENT.getCode()) {//换租
            calculateInterface = new CalOfInRent(paramEntity);
        } else if (surType == SurTypeEnum.SUBLET.getCode()) {//转租
            calculateInterface = new CalOfSublet(paramEntity);
        } else if (surType == SurTypeEnum.SHORT_UNRENT.getCode()) {//短租退租
            calculateInterface = new CalOfShortUnrent();
        } else {
            calculateInterface = param -> new CalReturnPojo();
        }

        return calculateInterface;
    }
}
