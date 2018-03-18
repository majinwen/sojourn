package com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>智能充电 返回基本信息</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2018年01月25日 20:37
 * @since 1.0
 */
public class IntellectWattBaseVo extends BaseEntity{

    /**
     * 总电量
     */
    private  String totalAmount;

    /**
     * 剩余电量
     */
    private  String amount;

    /**
     * 操作类型
     */
    private  Integer operationType;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }
}
