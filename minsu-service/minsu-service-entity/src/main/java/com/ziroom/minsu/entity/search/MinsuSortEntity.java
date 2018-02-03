package com.ziroom.minsu.entity.search;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/23.
 * @version 1.0
 * @since 1.0
 */
public class MinsuSortEntity extends BaseEntity{

    private static final long serialVersionUID = 30968492342446703L;

    private Integer index;

    private Integer code;

    private String name;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
