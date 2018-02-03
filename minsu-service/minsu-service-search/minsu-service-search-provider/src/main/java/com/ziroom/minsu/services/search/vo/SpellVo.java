package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * <p>热门区域的试图</p>
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
public class SpellVo extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -2145455582358555821L;


    @Field
    private String id;

	// 名称
    @Field
    private String spellName;

    // 区域名称
    @Field
    private String areaName;

    //类型
    @Field
    private Integer segmentType;

    //城市code
    @Field
    private String cityCode;

    //创建时间
    @Field
    private Long createTime = new Date().getTime();



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getSegmentType() {
        return segmentType;
    }

    public void setSegmentType(Integer segmentType) {
        this.segmentType = segmentType;
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
