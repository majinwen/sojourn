package com.ziroom.zrp.service.trading.utils.surrender;

import com.ziroom.zrp.service.trading.pojo.CalNeedPojo;
import com.ziroom.zrp.service.trading.pojo.CalReturnPojo;
import com.ziroom.zrp.service.trading.valenum.LeaseCycleEnum;
import com.ziroom.zrp.service.trading.valenum.surrender.ResponsibilityEnum;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * <p></p>
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
public class CalOfOneSidedForV1 extends AbstractForDJZZHZ implements CalculateInterface {

    public CalOfOneSidedForV1(CalNeedPojo paramEntity) throws ParseException {
        super(paramEntity);
    }

    /**
     * 1、若选择了20天前的解约申请，则房租顺延扣除补⾜20天。例如：7⽉1⽇当天选择了7⽉10⽇的解约申请，则合同在结算时，按照租住到7⽉20⽇计算房租和违约⾦。<br/>
     * 2、单解违约⾦⽆折扣
     *
     * @param null
     * @return
     * @Author: wangxm113
     * @CreateDate: 2017年11月04日
     */
    @Override
    public CalReturnPojo calculate(CalNeedPojo paramEntity) throws Exception {
        CalReturnPojo result = new CalReturnPojo();
        if (LeaseCycleEnum.DAY.getCode().equals(paramEntity.getContract().getConType())) {
            actualPrice = new BigDecimal(paramEntity.getContract().getFactualprice() * 30);
        }
        //房租计算
        if (paramEntity.isHavePlanOfHaiYanOfQiLing()) {
            /*应缴房费＝实际居住天数＊月租金/30*/
            result.setNeedPayFZ(BigDecimal.valueOf(conStartToRelDays + 1).multiply(actualPrice)
                    .divide(BigDecimal.valueOf(30), 2, BigDecimal.ROUND_HALF_UP));  //其灵海燕计划收的房租
        } else {
            //判断是否处于提交申请之后的20天内
            int intervalDays = (int) ((releaseDate.getTime() - applicationDate.getTime()) / (24L * 60 * 60 * 1000));
            int conStartToRelDaysForFZ = conStartToRelDays;
            if (intervalDays < 20) {
                conStartToRelDaysForFZ = conStartToRelDays + 20 - intervalDays;
            }
            result.setNeedPayFZ(BigDecimal.valueOf(conStartToRelDaysForFZ + 1).multiply(totalNeedPayAmount)
                    .divide(BigDecimal.valueOf(conStartToEndDays + 1), 2, BigDecimal.ROUND_HALF_UP));  //新计算后的房租
        }
        //服务费计算
        result.setNeedPayFWF(servicePrice.multiply(BigDecimal.valueOf(conStartToRelDays + 1))
                .divide(BigDecimal.valueOf(conStartToEndDays + 1), 2, BigDecimal.ROUND_HALF_UP));
        //违约金计算
        if (paramEntity.getResNo() == ResponsibilityEnum.COMPANY.getCode()) {
            result.setNeedPayWYJ(BigDecimal.ZERO);
        } else {
            result.setNeedPayWYJ(actualPrice);
        }
        return result;
    }

}
