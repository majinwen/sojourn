package com.ziroom.zrp.service.trading.dto.smarthydropower;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;

/**
 * <p>结算智能水表清算记录入参</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2018年02月08日 14:37
 * @since 1.0
 */
@Data
public class SettleWaterClearingRecordDto extends BaseEntity{

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 结算方式
     * @see com.ziroom.zrp.service.trading.valenum.waterwatt.WaterwattSettlementTypeEnum
     */
    private Integer settlementType;

    /**
     * 创建类型  0=系统定时创建 1=管家 2=解约触发创建
     */
    private Integer createType;

    /**
     *  员工id
     */
    private String createId;


}
