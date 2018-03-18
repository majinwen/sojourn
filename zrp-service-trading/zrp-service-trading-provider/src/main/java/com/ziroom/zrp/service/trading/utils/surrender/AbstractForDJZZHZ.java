package com.ziroom.zrp.service.trading.utils.surrender;

import com.ziroom.zrp.service.trading.pojo.CalNeedPojo;
import com.ziroom.zrp.trading.entity.RentContractEntity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p> 模板模式：因为单解、转租、换租需要的信息基本一致，所以建一个抽象类作为工具类来给他们继承，
 * 精简代码、方便使用、同时节省了其它算法获取这些信息的开销
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author: wangxm113
 * @Date: 2017年11月04日
 */
public abstract class AbstractForDJZZHZ {
    protected RentContractEntity contract;
    protected BigDecimal actualPrice;//实际出房价格
    protected BigDecimal servicePrice;//优惠后服务费
    protected BigDecimal activityServiceDisount;//获取合同的活动服务费减免总金额
    protected int rentTime;//租赁时长
    protected BigDecimal totalNeedPayAmount;//总租金
    protected Date conStartDate;//合同起租日期
    protected Date conEntDate;//合同到租日期
    protected int conStartToEndDays;//起租日期到合同到租日期的天数合同共租多少天
    protected Date releaseDate;//退租日期
    protected int conStartToRelDays;//起租日期到退租日期的天数
    protected Date applicationDate;//提出退租日期
    protected int surAppToRelDays;//退租申请日期到退租日期的天数

    /**
     * 模板模式来提供工具类
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日
     */
    protected AbstractForDJZZHZ(CalNeedPojo paramEntity) throws ParseException {
        contract = paramEntity.getContract();
        //实际出房价格
        actualPrice = new BigDecimal(contract.getFactualprice());
        //优惠后服务费
        servicePrice = contract.getConMustCommission() == null ? new BigDecimal(contract.getFserviceprice()) : new BigDecimal(contract.getConMustCommission());
        //获取合同的活动服务费减免总金额
        activityServiceDisount = contract.getActivitymoney() == null
                ? BigDecimal.ZERO : new BigDecimal(contract.getActivitymoney());
        servicePrice = servicePrice.add(activityServiceDisount);
        //租赁时长
        rentTime = contract.getConRentYear();
        //总租金
        totalNeedPayAmount = actualPrice.multiply(new BigDecimal(rentTime));
        //合同起租日期
        conStartDate = contract.getConStartDate();
        //合同到租日期 wangws21 2016-7-27 修改服务费计算规则 未发生服务费=服务费*（租赁期内未履行天数/租赁期限总天数）
        conEntDate = contract.getConEndDate();
        //起租日期到合同到租日期的天数  合同共租多少天
        conStartToEndDays = daysBetween(conStartDate, conEntDate);
        //退租日期
        releaseDate = paramEntity.getSearchPojo().getReleaseDate() == null
                ? new Date() : paramEntity.getSearchPojo().getReleaseDate();
        //起租日期到退租日期的天数
        conStartToRelDays = daysBetween(conStartDate, releaseDate);
        //提出退租日期
        applicationDate = contract.getFapplicationdate();  //edit by xiaona--2016年10月11日  申请日期改为从合同中读取，保存了一个字段
        //退租申请日期到退租日期的天数
        surAppToRelDays = daysBetween(applicationDate, releaseDate);
    }

    /**
     * 自用工具
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时36分40秒
     */
    private int daysBetween(Date startDate, Date endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        startDate = sdf.parse(sdf.format(startDate));
        endDate = sdf.parse(sdf.format(endDate));
        return (int) ((endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000));
    }
}
