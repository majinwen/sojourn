package com.ziroom.minsu.entity.cms;

import com.asura.framework.base.entity.BaseEntity;
/**
 *
 * <p>活动房源实体</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class ActivityHouseEntity extends BaseEntity{
    private static final long serialVersionUID = 531645244993992117L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 活动编号
     */
    private String actSn;

    /**
     * 房源或者房间编号
     */
    private String houseSn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn;
    }

    public String getHouseSn() {
        return houseSn;
    }

    public void setHouseSn(String houseSn) {
        this.houseSn = houseSn;
    }
}