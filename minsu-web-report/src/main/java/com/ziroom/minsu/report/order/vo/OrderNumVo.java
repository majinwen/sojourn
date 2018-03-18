package com.ziroom.minsu.report.order.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>订单数量统计</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/2/19.
 * @version 1.0
 * @since 1.0
 */
public class OrderNumVo extends BaseEntity{

    @FieldMeta(skip = true)
    private static final long serialVersionUID = 1L;

    /**
     * 区域
     */
    @FieldMeta(name="区域",order=10)
    private String regionName;

    /**
     * 城市code
     */
    @FieldMeta(name="城市code",order=10)
    private String  cityCode;

    /**
     * 城市名称
     */
    @FieldMeta(name="城市名称",order=10)
    private String  cityName;

    //

    /**
     * 咨询量
     */
    @FieldMeta(name="咨询量",order=10)
    private Integer consoltNum = 0;

    /**
     * 申请量
     */
    @FieldMeta(name="申请量",order=10)
    private Integer applyNum = 0;

    /**
     * 房东接受定订单量
     */
    @FieldMeta(name="房东接受定订单量",order=10)
    private Integer acceptNum = 0;

    /**
     * 房客支付量
     */
    @FieldMeta(name="房客支付量",order=10)
    private Integer payNum = 0;


    /**
     * 房东接单率
     */
    @FieldMeta(name="房东接单率%",order=10)
    private Double acceptRate = 0.0;

    /**
     * 房客支付率
     */
    @FieldMeta(name="房客支付率%",order=10)
    private Double payRate = 0.0;

    /**
     * 订单成功率 客户支付量/申请量；
     */
    @FieldMeta(name="订单成功率%",order=10)
    private Double successRate = 0.0;

    /**
     * 客户量
     */
    @FieldMeta(name="客户量",order=10)
    private Integer userNum = 0;


    /**
     * 成功客户量
     */
    @FieldMeta(name="成功客户量",order=10)
    private Integer successUserNum = 0;

    /**
     * 客户预定成功率
     */
    @FieldMeta(name="客户预定成功率%",order=10)
    private Double successUserRate = 0.0;


    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getConsoltNum() {
        return consoltNum;
    }

    public void setConsoltNum(Integer consoltNum) {
        this.consoltNum = consoltNum;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public Integer getAcceptNum() {
        return acceptNum;
    }

    public void setAcceptNum(Integer acceptNum) {
        this.acceptNum = acceptNum;
    }

    public Integer getPayNum() {
        return payNum;
    }

    public void setPayNum(Integer payNum) {
        this.payNum = payNum;
    }

    public Double getAcceptRate() {
        return acceptRate;
    }

    public void setAcceptRate(Double acceptRate) {
        this.acceptRate = acceptRate;
    }

    public Double getPayRate() {
        return payRate;
    }

    public void setPayRate(Double payRate) {
        this.payRate = payRate;
    }

    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
        this.successRate = successRate;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getSuccessUserNum() {
        return successUserNum;
    }

    public void setSuccessUserNum(Integer successUserNum) {
        this.successUserNum = successUserNum;
    }

    public Double getSuccessUserRate() {
        return successUserRate;
    }

    public void setSuccessUserRate(Double successUserRate) {
        this.successUserRate = successUserRate;
    }
}
