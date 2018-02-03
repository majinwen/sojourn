package com.ziroom.minsu.entity.cms;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>活动的开通城市</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/7/13.
 * @version 1.0
 * @since 1.0
 */
public class ActivityCityEntity  extends BaseEntity{


    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -121313123123131313L;

    private Integer id;

    private String actSn;

    private String cityCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
