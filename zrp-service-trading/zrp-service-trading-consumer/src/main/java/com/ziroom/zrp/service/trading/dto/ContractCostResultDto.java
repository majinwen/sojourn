package com.ziroom.zrp.service.trading.dto;

import com.ziroom.zrp.trading.entity.RentContractActivityEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月21日 14:41
 * @since 1.0
 */
public class ContractCostResultDto {

    // 标准出房价
    private BigDecimal roomSalesPrice;

    //实际出房价格总额
    private BigDecimal actualPrice;

    //押金总额
    private BigDecimal conDeposit;

    //标准服务费总额
    private BigDecimal conCommission;

    //服务费总减免

    //优惠服务费总额
    private BigDecimal servicePrice;


    private List<RentContractActivityEntity> actList;//活动信息


    private List<ContractRoomCostResultDto> contractRoomCostResultDtoList;


    public ContractCostResultDto() {

    }

    public BigDecimal getRoomSalesPrice() {
        return roomSalesPrice;
    }

    public void setRoomSalesPrice(BigDecimal roomSalesPrice) {
        this.roomSalesPrice = roomSalesPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public BigDecimal getConDeposit() {
        return conDeposit;
    }

    public void setConDeposit(BigDecimal conDeposit) {
        this.conDeposit = conDeposit;
    }

    public BigDecimal getConCommission() {
        return conCommission;
    }

    public void setConCommission(BigDecimal conCommission) {
        this.conCommission = conCommission;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public List<RentContractActivityEntity> getActList() {
        return actList;
    }

    public void setActList(List<RentContractActivityEntity> actList) {
        this.actList = actList;
    }

    public List<ContractRoomCostResultDto> getContractRoomCostResultDtoList() {
        return contractRoomCostResultDtoList;
    }

    public void setContractRoomCostResultDtoList(List<ContractRoomCostResultDto> contractRoomCostResultDtoList) {
        this.contractRoomCostResultDtoList = contractRoomCostResultDtoList;
    }
}
