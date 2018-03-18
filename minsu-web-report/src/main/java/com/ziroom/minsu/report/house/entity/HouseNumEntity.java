package com.ziroom.minsu.report.house.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/1/20.
 * @version 1.0
 * @since 1.0
 */
public class HouseNumEntity extends BaseEntity {

    private static final long serialVersionUID = 30968234201446703L;

    /**
     * 城市
     */
    private String cityCode;

    /**
     * 总数量
     */
    private Integer num;

    /**
     * 自主数量
     */
    private Integer zizhuNum;

    /**
     * 地推数量
     */
    private Integer dituiNum;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getZizhuNum() {
        return zizhuNum;
    }

    public void setZizhuNum(Integer zizhuNum) {
        this.zizhuNum = zizhuNum;
    }

    public Integer getDituiNum() {
        return dituiNum;
    }

    public void setDituiNum(Integer dituiNum) {
        this.dituiNum = dituiNum;
    }
}
