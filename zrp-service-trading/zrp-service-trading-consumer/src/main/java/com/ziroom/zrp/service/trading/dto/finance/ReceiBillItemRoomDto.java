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
public class ReceiBillItemRoomDto {

    private String roomId;

    private String roomNum;

    private BigDecimal roomOughtAmount;

    private List<ReceiBillItemExpenseDto> expenseReceiBillItemDtoList;

//    private BigDecimal roomDepositAmount;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public BigDecimal getRoomOughtAmount() {
        return roomOughtAmount;
    }

    public void setRoomOughtAmount(BigDecimal roomOughtAmount) {
        this.roomOughtAmount = roomOughtAmount;
    }

    public List<ReceiBillItemExpenseDto> getExpenseReceiBillItemDtoList() {
        return expenseReceiBillItemDtoList;
    }

    public void setExpenseReceiBillItemDtoList(List<ReceiBillItemExpenseDto> expenseReceiBillItemDtoList) {
        this.expenseReceiBillItemDtoList = expenseReceiBillItemDtoList;
    }

//    public BigDecimal getRoomDepositAmount() {
//        return roomDepositAmount;
//    }
//
//    public void setRoomDepositAmount(BigDecimal roomDepositAmount) {
//        this.roomDepositAmount = roomDepositAmount;
//    }
}
