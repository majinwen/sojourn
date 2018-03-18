package com.zra.common.vo.base;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月15日 14:47
 * @since 1.0
 */
public class BaseFieldVo {
    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private String value;

    public BaseFieldVo() {
    }

    public BaseFieldVo(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
