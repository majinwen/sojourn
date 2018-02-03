package com.ziroom.minsu.entity.cms;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>活动vo对外只抛这个</p>
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
public class ActivityVo extends ActivityEntity {

    /**
     * 序列ID
     */
    private static final long serialVersionUID = -5303586262952202466L;

    private String cityStr;

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    List<ActivityCityEntity> cityList = new ArrayList<>();

    public List<ActivityCityEntity> getCityList() {
        return cityList;
    }

    public void setCityList(List<ActivityCityEntity> cityList) {
        this.cityList = cityList;
    }
}
