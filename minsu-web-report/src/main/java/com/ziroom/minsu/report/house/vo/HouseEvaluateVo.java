package com.ziroom.minsu.report.house.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>HouseEvaluateVo</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */
public class HouseEvaluateVo extends BaseEntity {

    @FieldMeta(skip = true)
    private static final long serialVersionUID = -35029007906600240L;

    @FieldMeta(name = "国家", order = 1)
    private String nationCode;

    @FieldMeta(name = "大区", order = 2)
    private String regionName;

    @FieldMeta(name = "城市", order = 3)
    private String cityName;

    @FieldMeta(name="房源编号",order=4)
    private String houseSn;

    @FieldMeta(name = "首次上架时间", order = 5)
    private String putawayTime;

    @FieldMeta(name="房源/房间状态",order=6)
    private String houseStatusName;

    @FieldMeta(name = "入住订单量", order =7)
    private Integer checkInOrderNum;

    @FieldMeta(name = "评价率", order = 8)
    private Double evaRate;

    @FieldMeta(name = "累计评价量", order = 9)
    private Integer evaTotal;

    @FieldMeta(name = "累计评分平均分", order = 10)
    private Double sumScoreAvg;

    @FieldMeta(name = "整洁卫生平均分", order = 11)
    private Double houseCleanAvg;

    @FieldMeta(name = "描述相符平均分", order = 12)
    private Double desMathAvg;

    @FieldMeta(name = "安全程度平均分", order = 13)
    private Double safeDegreeAvg;

    @FieldMeta(name = "交通位置平均分", order = 14)
    private Double trafPosAvg;

    @FieldMeta(name = "性价比平均分", order = 15)
    private Double costPerforAvg;

    @FieldMeta(name = "评价少于4星数量", order = 16)
    private Double evaLessFourNum;

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getHouseSn() {
        return houseSn;
    }

    public void setHouseSn(String houseSn) {
        this.houseSn = houseSn;
    }

    public String getPutawayTime() {
        return putawayTime;
    }

    public void setPutawayTime(String putawayTime) {
        this.putawayTime = putawayTime;
    }

    public String getHouseStatusName() {
        return houseStatusName;
    }

    public void setHouseStatusName(String houseStatusName) {
        this.houseStatusName = houseStatusName;
    }

    public Integer getCheckInOrderNum() {
        return checkInOrderNum;
    }

    public void setCheckInOrderNum(Integer checkInOrderNum) {
        this.checkInOrderNum = checkInOrderNum;
    }

    public Double getEvaRate() {
        return evaRate;
    }

    public void setEvaRate(Double evaRate) {
        this.evaRate = evaRate;
    }

    public Integer getEvaTotal() {
        return evaTotal;
    }

    public void setEvaTotal(Integer evaTotal) {
        this.evaTotal = evaTotal;
    }

    public Double getSumScoreAvg() {
        return sumScoreAvg;
    }

    public void setSumScoreAvg(Double sumScoreAvg) {
        this.sumScoreAvg = sumScoreAvg;
    }

    public Double getHouseCleanAvg() {
        return houseCleanAvg;
    }

    public void setHouseCleanAvg(Double houseCleanAvg) {
        this.houseCleanAvg = houseCleanAvg;
    }

    public Double getDesMathAvg() {
        return desMathAvg;
    }

    public void setDesMathAvg(Double desMathAvg) {
        this.desMathAvg = desMathAvg;
    }

    public Double getSafeDegreeAvg() {
        return safeDegreeAvg;
    }

    public void setSafeDegreeAvg(Double safeDegreeAvg) {
        this.safeDegreeAvg = safeDegreeAvg;
    }

    public Double getTrafPosAvg() {
        return trafPosAvg;
    }

    public void setTrafPosAvg(Double trafPosAvg) {
        this.trafPosAvg = trafPosAvg;
    }

    public Double getCostPerforAvg() {
        return costPerforAvg;
    }

    public void setCostPerforAvg(Double costPerforAvg) {
        this.costPerforAvg = costPerforAvg;
    }

    public Double getEvaLessFourNum() {
        return evaLessFourNum;
    }

    public void setEvaLessFourNum(Double evaLessFourNum) {
        this.evaLessFourNum = evaLessFourNum;
    }
}
