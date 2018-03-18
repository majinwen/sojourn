package com.ziroom.minsu.report.board.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>员工目标统计</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class EmpTargetStatItem extends BaseEntity{
    /**
     * 员工编号
     */
    private String empCode;
    /**
     * 员工名称
     */
    private String empName;

    /**
     * 员工发布房源目标数量
     */
    private Integer targetHouseNum;
    /**
     * 地推发布
     */
    private Integer housePubNum;
    /**
     * 地推上架
     */
    private Integer houseOnlineNum;

    /**
     * 地推达成率
     */
    private Double AchievingRate;
    /**
     * 环比上月
     */
    private Double relativeRate;


    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getTargetHouseNum() {
        return targetHouseNum;
    }

    public void setTargetHouseNum(Integer targetHouseNum) {
        this.targetHouseNum = targetHouseNum;
    }

    public Integer getHousePubNum() {
        return housePubNum;
    }

    public void setHousePubNum(Integer housePubNum) {
        this.housePubNum = housePubNum;
    }

    public Integer getHouseOnlineNum() {
        return houseOnlineNum;
    }

    public void setHouseOnlineNum(Integer houseOnlineNum) {
        this.houseOnlineNum = houseOnlineNum;
    }

    public Double getAchievingRate() {
        return AchievingRate;
    }

    public void setAchievingRate(Double achievingRate) {
        AchievingRate = achievingRate;
    }

    public Double getRelativeRate() {
        return relativeRate;
    }

    public void setRelativeRate(Double relativeRate) {
        this.relativeRate = relativeRate;
    }


}
