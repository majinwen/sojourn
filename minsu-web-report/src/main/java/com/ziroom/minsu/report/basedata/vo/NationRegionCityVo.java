package com.ziroom.minsu.report.basedata.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>条件信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/10.
 * @version 1.0
 * @since 1.0
 */
public class NationRegionCityVo  extends BaseEntity{

    private static final long serialVersionUID = 323423446703L;


    private String nationName;


    private String regionName;

    private String cityName;

    private String cityCode;

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
