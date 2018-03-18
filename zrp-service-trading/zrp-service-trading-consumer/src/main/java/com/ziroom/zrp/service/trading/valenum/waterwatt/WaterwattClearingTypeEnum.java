package com.ziroom.zrp.service.trading.valenum.waterwatt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>清算类型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2018年02月27日
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
public enum WaterwattClearingTypeEnum {

    DINGSHI(0,"定时清算",0,"定时清算"),

    // 跟合同相关的清算需要区分他是合同本身，还是同租的合同
    // 如A合同新签，触发清算，则A合同的清算类型为 新签，其他的同租合同清算类型为 新签清算
    XINQIAN(1,"新签", 2,"新签清算"),
    JIEYUE(3,"解约",4,"解约清算"),
    DAOQI(5,"到期",6,"到期清算"),
    XUYUE(7,"续约",8,"续约清算"),

    RENGONG(9,"人工清算",9,"人工清算");

    /**
     * 自身合同清算的清算类型
     */
    @Getter
    private int oneSelfClearingCode;

    @Getter
    private String oneSelfClearingName;

    /**
     * 他人合同清算导致的清算类型
     */
    @Getter
    private int otherClearingCode;

    @Getter
    private String otherClearingName;

}
