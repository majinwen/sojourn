package com.ziroom.zrp.service.trading.utils.surrender;

import com.ziroom.zrp.service.trading.pojo.CalNeedPojo;
import com.ziroom.zrp.service.trading.pojo.CalReturnPojo;
import com.ziroom.zrp.service.trading.valenum.LeaseCycleEnum;
import com.ziroom.zrp.service.trading.valenum.surrender.ResponsibilityEnum;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * <p></p>
 * <p> 换租
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
public class CalOfInRent extends AbstractForDJZZHZ implements CalculateInterface {

    public CalOfInRent(CalNeedPojo paramEntity) throws ParseException {
        super(paramEntity);
    }

    /**
     * 单解：<br/>
     * 应缴房租 = 实际居住租期 <= 1个月：应缴租金 = 1 个月房租。<br/>
     * 实际居住租期 > 1个月：应缴租金 = 租期天数 * （月租金 / 30 ）。<br/>
     * 注意：如果签合同时用的带税价格，应缴租金为：<br/>
     * 应缴税费 =已缴租金 * 税率 <br/>
     * 应缴租金 = 应缴税费 + 无税当前应缴租金（无税月租金计算得出） <br/>
     * 应缴租金（税费展开） = 已缴租金 * 税率 + 无税当前应缴租金 <br/>
     * 应缴服务费 = 截至实际退租日期，客户应该缴纳的服务费。服务费计算公式为： <br/>
     * 租期 <= 3个月：应缴服务费 = 3个月服务费 <br/>
     * 租期 > 3个月：应缴服务费 = 租期月数（未满一月向上去整）* 月服务费 <br/>
     * 应缴押金 = 0 <br/>
     * 应缴违约金 = <br/>
     * 单解：根据解约提出时间给与折扣。<br/>
     * a)提前超过>30天提出：违约金为月租金70%。<br/>
     * b)提前[10-30]天：违约金=月租金X（100-提前通知的天数）/100 <br/>
     * c)小于<10天：违约金 = 月租金 <br/>
     * 转租或换租时 应缴违约金 = 半月租金
     *
     * @param null
     * @return
     * @Author: wangxm113
     * @CreateDate: 2017年11月04日
     */
    @Override
    public CalReturnPojo calculate(CalNeedPojo paramEntity) throws Exception {
        if (LeaseCycleEnum.DAY.getCode().equals(paramEntity.getContract().getConType())) {
            actualPrice = new BigDecimal(paramEntity.getContract().getFactualprice() * 30);
        }
        CalReturnPojo result = new CalReturnPojo();
        /*wangws21 其灵海燕计划特殊处理 减免最后一个月房租 start 2017-5-23*/
        if (paramEntity.isHavePlanOfHaiYanOfQiLing()) {
            /*应缴房费＝实际居住天数＊月租金/30*/
            result.setNeedPayFZ(BigDecimal.valueOf(conStartToRelDays + 1).multiply(actualPrice).
                    divide(BigDecimal.valueOf(30), 2, BigDecimal.ROUND_HALF_UP));  //其灵海燕计划收的房租
        } else {
            result.setNeedPayFZ(BigDecimal.valueOf(conStartToRelDays + 1).multiply(totalNeedPayAmount).
                    divide(BigDecimal.valueOf(conStartToEndDays + 1), 2, BigDecimal.ROUND_HALF_UP));//新计算后的房租
        }
        /*wangws21 其灵海燕计划特殊处理 减免最后一个月房租 end 2017-5-23*/

        /*
         * author--xiaona--2016年10月11日
         * 应缴服务费的修改-----逻辑不变，修改之前的小数获取逻辑
         * 之前在保留应两位小数时，是保留的（租期天数／租赁总天数）这个取值，
         * 这次处理为保留应缴租金的后两位取值，测试时需增加这部分测试。
         */
        result.setNeedPayFWF(servicePrice.multiply(BigDecimal.valueOf(conStartToRelDays + 1)).
                divide(BigDecimal.valueOf(conStartToEndDays + 1), 2, BigDecimal.ROUND_HALF_UP));

        /*
         * author xiaona 2016年10月12日
         * 违约金在换租的情况下面违约金的计算
         * 若违约责任方为公司，则取值默认为0，
         * 若违约责任方为客户，则违约金为半个月房租
         */
        if (paramEntity.getResNo() == ResponsibilityEnum.COMPANY.getCode()) {
            /*如果违约责任方是公司并且退租类型是换租*/
            /*此时的违约金是不计算的*/
            /*其他情况下面都是半个月的房租*/
            result.setNeedPayWYJ(BigDecimal.ZERO);
        } else {
            //退租或换租情况 违约金为半月房租
            result.setNeedPayWYJ(actualPrice.divide(BigDecimal.valueOf(2), 2, BigDecimal.ROUND_HALF_UP));
        }
        return result;
    }
}
