package com.ziroom.minsu.report.basedata.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

import java.util.List;

/**
 * <p>城市条件信息</p>
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
public class NationRegionCityRequest  extends PageRequest {

    private static final long serialVersionUID = 56456234324545403L;

    /**
     * 国家
     */
    protected String nationCode;

    protected String regionFid;

    protected String cityCode;


    private List<String>  cityList;

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getRegionFid() {
        return regionFid;
    }

    public void setRegionFid(String regionFid) {
        this.regionFid = regionFid;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }


    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }
}
