package com.ziroom.minsu.entity.conf;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>城市规则关系表</p>
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
public class CityTemplateEntity extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -71456858025171L;


    private Integer id;


    /**  城市code */
    private String cityCode;

    /** 规则code */
    private String templateFid;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getTemplateFid() {
        return templateFid;
    }

    public void setTemplateFid(String templateFid) {
        this.templateFid = templateFid;
    }
}
