package com.ziroom.minsu.services.basedata.entity;

import com.ziroom.minsu.entity.conf.TemplateEntity;

import java.util.List;

/**
 * <p>模板试图</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/21.
 * @version 1.0
 * @since 1.0
 */
public class TemplateEntityVo extends TemplateEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -233553697590673L;

    /**
     * 继承自
     */
    private String pName;

    /**
     * 城市名称
     */
    private String cityName;

    private List<String> cityList;


    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
