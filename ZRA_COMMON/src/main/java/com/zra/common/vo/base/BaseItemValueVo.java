package com.zra.common.vo.base;

/**
 * <p>带有value的vo</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月23日 14:47
 * @since 1.0
 */
public class BaseItemValueVo extends BaseItemVo{
    /**
     * 值
     */
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
