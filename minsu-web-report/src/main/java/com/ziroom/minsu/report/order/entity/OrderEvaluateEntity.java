package com.ziroom.minsu.report.order.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>订单评价统计</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/1/19.
 * @version 1.0
 * @since 1.0
 */
public class OrderEvaluateEntity  extends BaseEntity{

    @FieldMeta(skip = true)
    private static final long serialVersionUID = 3234239013001446703L;

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

    /**
     * 订单数
     */
    @FieldMeta(name="订单数",order=10)
    private Integer orderNum = 0;

    /**
     * 房客评价量
     */
    @FieldMeta(name="房客评价量",order=10)
    private Integer tenNum  = 0;

    /**
     * 房东评价量
     */
    @FieldMeta(name="房东评价量",order=10)
    private Integer lanNum  = 0;

    /**
     * 房客评价率
     */
    @FieldMeta(name="房客评价率",order=10)
    private Double tenRate = 0.0;

    /**
     * 房东评价率
     */
    @FieldMeta(name="房东评价率",order=10)
    private Double lanRate = 0.0;


    /**
     * 房客环比评价
     */
    @FieldMeta(name="房客环比评价",order=10)
    private Double tenInfo = 0.0;


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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getTenNum() {
        return tenNum;
    }

    public void setTenNum(Integer tenNum) {
        this.tenNum = tenNum;
    }

    public Integer getLanNum() {
        return lanNum;
    }

    public void setLanNum(Integer lanNum) {
        this.lanNum = lanNum;
    }

    public Double getTenRate() {
        return tenRate;
    }

    public void setTenRate(Double tenRate) {
        this.tenRate = tenRate;
    }

    public Double getLanRate() {
        return lanRate;
    }

    public void setLanRate(Double lanRate) {
        this.lanRate = lanRate;
    }

    public Double getTenInfo() {
        return tenInfo;
    }

    public void setTenInfo(Double tenInfo) {
        this.tenInfo = tenInfo;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
