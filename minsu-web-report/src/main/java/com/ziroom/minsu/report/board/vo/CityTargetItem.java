package com.ziroom.minsu.report.board.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>返回目标item</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017/1/11.
 * @version 1.0
 * @since 1.0
 */
public class CityTargetItem  extends BaseEntity {

    private static final long serialVersionUID = -2305740903007597596L;
    /**
     * 大区名字
     */
    @FieldMeta(name="大区",order=1)
    private String regionName;
    /**
     * 大区fid
     */
    @FieldMeta(skip = true)
    private String regionFid;
    /**
     * 城市名字
     */
    @FieldMeta(name="城市",order=2)
    private String cityName;
    /**
     * 城市code
     */
    @FieldMeta(skip = true)
    private String cityCode;
    /**
     * 目标月份
     */
    @FieldMeta(name="目标月份",order=3)
    private String targetMonth;
    /**
     * 房源上架目标
     */
    @FieldMeta(name="房源上架目标",order=4)
    private Integer targetHouseNum;
    /**
     * 地推上架目标
     */
    @FieldMeta(name="地推上架目标",order=5)
    private Integer targetPushHouseNum;
    /**
     * 房源自主上架目标
     */
    @FieldMeta(name="房源自主上架目标",order=6)
    private Integer targetSelfHouseNum;
    /**
     * 订单目标
     */
    @FieldMeta(name="订单目标",order=7)
    private Integer targetOrderNum;
    /**
     * 出租间夜目标
     */
    @FieldMeta(name="出租间夜目标",order=8)
    private Integer targetRentNum;
    /**
     * 是否显示设置
     */
    @FieldMeta(skip = true)
    private Integer isSet;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionFid() {
        return regionFid;
    }

    public void setRegionFid(String regionFid) {
        this.regionFid = regionFid;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getTargetMonth() {
        return targetMonth;
    }

    public void setTargetMonth(String targetMonth) {
        this.targetMonth = targetMonth;
    }

    public Integer getTargetHouseNum() {
        return targetHouseNum;
    }

    public void setTargetHouseNum(Integer targetHouseNum) {
        this.targetHouseNum = targetHouseNum;
    }

    public Integer getTargetPushHouseNum() {
        return targetPushHouseNum;
    }

    public void setTargetPushHouseNum(Integer targetPushHouseNum) {
        this.targetPushHouseNum = targetPushHouseNum;
    }

    public Integer getTargetSelfHouseNum() {
        return targetSelfHouseNum;
    }

    public void setTargetSelfHouseNum(Integer targetSelfHouseNum) {
        this.targetSelfHouseNum = targetSelfHouseNum;
    }

    public Integer getTargetOrderNum() {
        return targetOrderNum;
    }

    public void setTargetOrderNum(Integer targetOrderNum) {
        this.targetOrderNum = targetOrderNum;
    }

    public Integer getTargetRentNum() {
        return targetRentNum;
    }

    public void setTargetRentNum(Integer targetRentNum) {
        this.targetRentNum = targetRentNum;
    }

    public Integer getIsSet() {
        return isSet;
    }

    public void setIsSet(Integer isSet) {
        this.isSet = isSet;
    }
}
