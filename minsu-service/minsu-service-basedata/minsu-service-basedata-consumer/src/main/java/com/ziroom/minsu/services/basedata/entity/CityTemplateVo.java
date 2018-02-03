package com.ziroom.minsu.services.basedata.entity;

import com.ziroom.minsu.entity.conf.CityTemplateEntity;

/**
 * <p>城市模板试图</p>
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
public class CityTemplateVo extends CityTemplateEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -233553697590673L;


    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 城市名称
     */
    private String cityName = "";

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
