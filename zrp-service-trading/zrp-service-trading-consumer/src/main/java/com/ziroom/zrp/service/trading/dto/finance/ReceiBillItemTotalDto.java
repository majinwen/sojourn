package com.ziroom.zrp.service.trading.dto.finance;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>房间费用名细</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月26日 18:10
 * @since 1.0
 */
public class ReceiBillItemTotalDto {


    private BigDecimal totalOughtAmount;

    private List<ReceiBillItemExpenseDto> expenseReceiBillItemDtoList;

    public BigDecimal getTotalOughtAmount() {
        return totalOughtAmount;
    }

    public void setTotalOughtAmount(BigDecimal totalOughtAmount) {
        this.totalOughtAmount = totalOughtAmount;
    }

    public List<ReceiBillItemExpenseDto> getExpenseReceiBillItemDtoList() {
        return expenseReceiBillItemDtoList;
    }

    public void setExpenseReceiBillItemDtoList(List<ReceiBillItemExpenseDto> expenseReceiBillItemDtoList) {
        this.expenseReceiBillItemDtoList = expenseReceiBillItemDtoList;
    }
}
