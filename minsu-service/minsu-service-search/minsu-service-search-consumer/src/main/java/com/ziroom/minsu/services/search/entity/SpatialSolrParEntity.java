package com.ziroom.minsu.services.search.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>solr空间的查询条件</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/30.
 * @version 1.0
 * @since 1.0
 */
public class SpatialSolrParEntity extends BaseEntity {


    /** 纬度，经度 必须保持格式一致 */
    private String location;

    /** 需要搜索的列 */
    private String sfield;

    /**
     * 距离 放距离非空时 并做距离的校验
     */
    private Double d;

    /**
     * 空间的排序规则
     */
    private String orderRole;

    public String getOrderRole() {
        return orderRole;
    }

    public void setOrderRole(String orderRole) {
        this.orderRole = orderRole;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSfield() {
        return sfield;
    }

    public void setSfield(String sfield) {
        this.sfield = sfield;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }
}
