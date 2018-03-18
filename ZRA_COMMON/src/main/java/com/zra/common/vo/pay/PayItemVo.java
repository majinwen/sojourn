package com.zra.common.vo.pay;

import com.zra.common.vo.base.BaseItemVo;

/**
 * <p>支付方式</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月15日 11:03
 * @since 1.0
 */
public class PayItemVo extends BaseItemVo{
    /**
     * 描述信息
     */
    private String desc;
    /**
     * 是否选中
     */
    private boolean isSelect;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
