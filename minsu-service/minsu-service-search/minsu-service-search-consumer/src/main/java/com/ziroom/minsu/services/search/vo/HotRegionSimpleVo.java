package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>热门区域的试图</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/19.
 * @version 1.0
 * @since 1.0
 */
public class HotRegionSimpleVo extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -845423142315171L;

    private String id;

	// 名称 
    private String regionName;

    // 区域名称
    private String areaName;

    // 城市名称
    private String cityCode;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
