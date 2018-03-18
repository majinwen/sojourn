package com.ziroom.zrp.service.trading.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>基本请求类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月22日 16:32
 * @since 1.0
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContractRoomDto {

    /**
     * 合同id
     */
    private String contractId;
    /**
     * 房间id
     */
    private String roomId;

    /**
     * 房间号
     */
    private String roomNum;

    /**
     * 年租、日租、月租标识
     * */
    private String conType;
    /**
     * 出租方式 0=房间 1=床位
     */
    private Integer roomType;
    /**
     * 签约类型 fsigntype 0:新签.1:续约.2:换租
     */
    private String signType;
    /**
     * 实际出租方式  1按房间2按床位
     */
    private Integer roomRentType;
}
