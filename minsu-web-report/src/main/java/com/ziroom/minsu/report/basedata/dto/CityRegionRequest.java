package com.ziroom.minsu.report.basedata.dto;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>大区分页查询</p>
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
public class CityRegionRequest  extends PageRequest {

    private static final long serialVersionUID = 564523425403L;

    /**
     * 国家
     */
    private String nationCode;

    /**
     * 大区fid
     */
    private String regionFid;

    /**
     * 城市code
     */
    private String cityCode;


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
}
