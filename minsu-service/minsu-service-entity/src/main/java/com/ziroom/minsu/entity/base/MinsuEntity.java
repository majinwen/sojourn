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
public class MinsuEntity extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -54564545423L;

    //名称
    private String name;

    //code
    private String code;

    //value
    private String value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
