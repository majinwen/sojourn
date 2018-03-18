package com.ziroom.zrp.service.trading.dto;

import com.ziroom.zrp.service.trading.dto.finance.BillDto;
import com.ziroom.zrp.trading.entity.FinReceiBillDetailEntity;
import com.ziroom.zrp.trading.entity.FinReceiBillEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;

import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2018年01月22日 14:22
 * @since 1.0
 */
public class WattRechargeInfo extends MemterRechargeDto {

    /**
     * 合同信息
     */
     private RentContractEntity rentContractEntity;

    /**
     * 应收账单
     */
    private FinReceiBillEntity finReceiBillEntity;

    /**
     * 应收账单详情
     */
    private List<FinReceiBillDetailEntity> receiBillDetails;

    private List<BillDto> bills;

    public RentContractEntity getRentContractEntity() {
        return rentContractEntity;
    }

    public void setRentContractEntity(RentContractEntity rentContractEntity) {
        this.rentContractEntity = rentContractEntity;
    }

    public FinReceiBillEntity getFinReceiBillEntity() {
        return finReceiBillEntity;
    }

    public void setFinReceiBillEntity(FinReceiBillEntity finReceiBillEntity) {
        this.finReceiBillEntity = finReceiBillEntity;
    }

    public List<FinReceiBillDetailEntity> getReceiBillDetails() {
        return receiBillDetails;
    }

    public void setReceiBillDetails(List<FinReceiBillDetailEntity> receiBillDetails) {
        this.receiBillDetails = receiBillDetails;
    }

    public List<BillDto> getBills() {
        return bills;
    }

    public void setBills(List<BillDto> bills) {
        this.bills = bills;
    }
}
