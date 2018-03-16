package com.ziroom.minsu.api.search.vo;

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
 * @author afi on on 2016/11/8.
 * @version 1.0
 * @since 1.0
 */
public class SensitiveVo extends BaseEntity{

    private static final long serialVersionUID = 3023231446703L;

    /**
     * 过滤内容
     */
    private  String content;

    /**
     * 过滤类型
     */
    private Integer checkType;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }
}
