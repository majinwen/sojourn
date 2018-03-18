package com.ziroom.minsu.report.order.dto;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.report.basedata.dto.NationRegionCityRequest;

import java.util.List;

/**
 * <p>订单联系人</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2017/3/7 17:22
 * @version 1.0
 * @since 1.0
 */
public class OrderContactRequest extends NationRegionCityRequest {
    private static final long serialVersionUID = 3584875377181266165L;

    protected String nationCode;
    protected String regionFid;
    protected String cityCode;
    private List<String> cityList;
    private String createStartTime;
    private String createEndTime;



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


    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    /**
     * 校验当前的参数是否全部为空
     * @return
     */
    public boolean checkEmpty(){
        if (Check.NuNStr(this.nationCode)
                && Check.NuNStr(this.regionFid)
                && Check.NuNStr(this.cityCode)
                ){
            return true;
        }else {
            return false;
        }
    }
}
