package com.ziroom.minsu.services.search.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>搜索房源的参数</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/13.
 * @version 1.0
 * @since 1.0
 */
public class HouseSearchRequest extends BaseEntity {

    private static final long serialVersionUID = 514654531345L;

    /**
     * 房源或者房间fid
     */
    private String fid;

    /**
     * 出租方式 0：整租，1：合租
     */
    private Integer rentWay;

    /**
     * @return the fid
     */
    public String getFid() {
        return fid;
    }

    /**
     * @param fid the fid to set
     */
    public void setFid(String fid) {
        this.fid = fid;
    }

    /**
     * @return the rentWay
     */
    public Integer getRentWay() {
        return rentWay;
    }

    /**
     * @param rentWay the rentWay to set
     */
    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }


}
