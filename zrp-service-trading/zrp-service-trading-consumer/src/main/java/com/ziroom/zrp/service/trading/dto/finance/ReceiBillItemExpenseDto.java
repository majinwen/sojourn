package com.ziroom.zrp.service.trading.dto.finance;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>企业合同业务展示账单数据数据结构</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月26日 12:07
 * @since 1.0
 */
public class ReceiBillItemExpenseDto {


    //序号
    private String orderNum;

    //费用id
    private String expenseItemId;

    //费用名称
    private String expenseItemName;

    //费用应缴金额
    private BigDecimal oughtAmount;




    public ReceiBillItemExpenseDto() {

    }

    public ReceiBillItemExpenseDto(BigDecimal oughtAmount, String expenseItemId, String expenseItemName) {
        this.oughtAmount = oughtAmount;
        this.expenseItemId = expenseItemId;
        this.expenseItemName = expenseItemName;
    }



    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getExpenseItemId() {
        return expenseItemId;
    }

    public void setExpenseItemId(String expenseItemId) {
        this.expenseItemId = expenseItemId;
    }

    public String getExpenseItemName() {
        return expenseItemName;
    }

    public void setExpenseItemName(String expenseItemName) {
        this.expenseItemName = expenseItemName;
    }

    public BigDecimal getOughtAmount() {
        return oughtAmount;
    }

    public void setOughtAmount(BigDecimal oughtAmount) {
        this.oughtAmount = oughtAmount;
    }

}
