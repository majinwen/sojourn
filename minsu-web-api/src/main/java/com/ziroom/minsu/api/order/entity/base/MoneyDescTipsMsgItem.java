package com.ziroom.minsu.api.order.entity.base;

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
 * @Date Created in 2017年08月01日 11:43
 * @since 1.0
 */
public class MoneyDescTipsMsgItem extends MoneyDescItem {

    /**
     * 提示信息
     */
    private String tipMsg;


    public String getTipMsg() {
        return tipMsg;
    }

    public void setTipMsg(String tipMsg) {
        this.tipMsg = tipMsg;
    }
}
