package com.ziroom.minsu.report.basedata.vo;

import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;

import java.util.List;

/**
 * <p>TODO</p>
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
public class CityRegionVo extends CityRegionEntity {



    private static final long serialVersionUID = -1734234436240L;


    /**
     * 城市code
     */
    private List<ConfCityEntity> cityList;

    public List<ConfCityEntity> getCityList() {
        return cityList;
    }

    public void setCityList(List<ConfCityEntity> cityList) {
        this.cityList = cityList;
    }
}
