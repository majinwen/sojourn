package com.ziroom.minsu.entity.base;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>民宿的map</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/21.
 * @version 1.0
 * @since 1.0
 */
public class MinsuEleEntity extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -54564545423L;

    //名称
    private String eleKey;

    //值
    private String eleValue;


    public String getEleKey() {
        return eleKey;
    }

    public void setEleKey(String eleKey) {
        this.eleKey = eleKey;
    }

    public String getEleValue() {
        return eleValue;
    }

    public void setEleValue(String eleValue) {
        this.eleValue = eleValue;
    }
}
