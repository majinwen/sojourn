package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * <p>搜索建议</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/13.
 * @version 1.0
 * @since 1.0
 */
public class SuggestInfo extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -21454256564544521L;

    /**
     * id
     */
    @Field
    private String id;

    /**
     * 名称
     */
    @Field
    private String suggestName;

    /**
     *
     */
    @Field
    private Integer suggestType;

    /**
     * 区域名称
     */
    @Field
    private String areaName;

    /**
     * 城市code
     */
    @Field
    private String cityCode;

    /**
     * 创建时间
     */
    @Field
    private Long createTime = new Date().getTime();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuggestName() {
        return suggestName;
    }

    public void setSuggestName(String suggestName) {
        this.suggestName = suggestName;
    }

    public Integer getSuggestType() {
        return suggestType;
    }

    public void setSuggestType(Integer suggestType) {
        this.suggestType = suggestType;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
