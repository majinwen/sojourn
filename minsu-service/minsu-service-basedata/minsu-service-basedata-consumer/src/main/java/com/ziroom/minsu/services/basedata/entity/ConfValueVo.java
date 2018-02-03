package com.ziroom.minsu.services.basedata.entity;

/**
 * <p>code</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月13日 10:31
 * @since 1.0
 */
public class ConfValueVo {
    /**
     * 名称
     */
    private String name;
    /**
     * code
     */
    private Integer code;
    /**
     * value
     */
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
