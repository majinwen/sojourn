package com.ziroom.minsu.report.basedata.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>城市数量</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/1/20.
 * @version 1.0
 * @since 1.0
 */
public class CityNumEntity extends BaseEntity {

    private static final long serialVersionUID = 302343201446703L;

    /**
     * 城市
     */
    private String cityCode;

    /**
     * 总数量
     */
    private Integer num;



    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

}
