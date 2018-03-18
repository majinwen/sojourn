package com.ziroom.minsu.report.order.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>订单房源统计</p>
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
public class OrderHouseEntity extends BaseEntity{

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
     * 发布数量
     */
    @FieldMeta(name="发布数量",order=10)
    private Integer fabuNum = 0;

    /**
     * 自主发布
     */
    @FieldMeta(name="自主发布",order=10)
    private Integer zizhuFabuNum = 0;

    /**
     * 地推发布
     */
    @FieldMeta(name="地推发布",order=10)
    private Integer dituiFabuNum = 0;

    /**
     * 上架数量
     */
    @FieldMeta(name="上架数量",order=10)
    private Integer shangjiaNum = 0;

    /**
     * 自主上架数
     */
    @FieldMeta(name="自主上架数",order=10)
    private Integer zizhuShangjiaNum = 0;


    /**
     * 地推上架数
     */
    @FieldMeta(name="地推上架数",order=10)
    private Integer dituiShangjiaNum = 0;


    /**
     * 订单数
     */
    @FieldMeta(name="订单数",order=10)
    private Integer orderNum = 0;

    /**
     * 间夜
     */
    @FieldMeta(name="间夜",order=10)
    private Integer dayNum = 0;



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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getFabuNum() {
        return fabuNum;
    }

    public void setFabuNum(Integer fabuNum) {
        this.fabuNum = fabuNum;
    }

    public Integer getZizhuFabuNum() {
        return zizhuFabuNum;
    }

    public void setZizhuFabuNum(Integer zizhuFabuNum) {
        this.zizhuFabuNum = zizhuFabuNum;
    }

    public Integer getDituiFabuNum() {
        return dituiFabuNum;
    }

    public void setDituiFabuNum(Integer dituiFabuNum) {
        this.dituiFabuNum = dituiFabuNum;
    }

    public Integer getShangjiaNum() {
        return shangjiaNum;
    }

    public void setShangjiaNum(Integer shangjiaNum) {
        this.shangjiaNum = shangjiaNum;
    }

    public Integer getZizhuShangjiaNum() {
        return zizhuShangjiaNum;
    }

    public void setZizhuShangjiaNum(Integer zizhuShangjiaNum) {
        this.zizhuShangjiaNum = zizhuShangjiaNum;
    }

    public Integer getDituiShangjiaNum() {
        return dituiShangjiaNum;
    }

    public void setDituiShangjiaNum(Integer dituiShangjiaNum) {
        this.dituiShangjiaNum = dituiShangjiaNum;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }
}
