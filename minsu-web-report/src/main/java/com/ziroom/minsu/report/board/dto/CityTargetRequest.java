package com.ziroom.minsu.report.board.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>查询请求</p>
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
public class CityTargetRequest extends PageRequest{
    /**
     * 大区名字
     */
    private String regionName;
    /**
     * 大区fid
     */
    private String regionFid;
    /**
     * 城市名字
     */
    private String cityName;
    /**
     * 城市code
     */
    private String cityCode;
    /**
     * 目标月份
     */
    private String targetMonth;

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
}
