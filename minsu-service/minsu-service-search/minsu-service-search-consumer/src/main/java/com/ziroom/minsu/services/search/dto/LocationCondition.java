package com.ziroom.minsu.services.search.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/10/15 11:39
 * @version 1.0
 * @since 1.0
 */
public class LocationCondition extends BaseEntity {
    
    /**
     * 行政区
     */
    private Object district;

    /**
     * 商圈
     */
    private Object commercialRegion;

    /**
     * 景点
     */
    private Object sceneryRegion;


    /**
     * 地铁
     */
    private Object subway;


    public Object getDistrict() {
        return district;
    }

    public void setDistrict(Object district) {
        this.district = district;
    }

    public Object getCommercialRegion() {
        return commercialRegion;
    }

    public void setCommercialRegion(Object commercialRegion) {
        this.commercialRegion = commercialRegion;
    }

    public Object getSceneryRegion() {
        return sceneryRegion;
    }

    public void setSceneryRegion(Object sceneryRegion) {
        this.sceneryRegion = sceneryRegion;
    }

    public Object getSubway() {
        return subway;
    }

    public void setSubway(Object subway) {
        this.subway = subway;
    }

}
