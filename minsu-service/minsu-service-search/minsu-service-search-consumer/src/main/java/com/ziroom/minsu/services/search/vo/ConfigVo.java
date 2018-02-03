package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>配置项的试图</p>
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
public class ConfigVo extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -7144472568025171L;

    private String code;

    private String value;

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
