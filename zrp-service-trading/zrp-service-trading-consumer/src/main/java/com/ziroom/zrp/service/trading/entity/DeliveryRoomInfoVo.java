package com.ziroom.zrp.service.trading.entity;

import lombok.Data;

import java.util.List;

/**
 * <p>房间交割物品</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月12日 10:40
 * @since 1.0
 */
@Data
public class DeliveryRoomInfoVo {
    /**
     * 项目Id
     */
    private String contractId;
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 房间号
     */
    private String roomNo;
    /**
     * 项目名称
     */
    private String proName;
    /**
     * 水示数
     */
    private Double waterNumber;
    /**
     * 电示数
     */
    private Double elecNumber;
    /**
     * 水费支付金额
     */
    private Double payWaterPrice;
    /**
     * 电费支付金额
     */
    private Double payElecPrice;
    /**
     * 交割物品
     */
    private List<RentItemVo> itemList;
    /**
     * 生活费用
     */
    private List<LifeItemVo> lifeFees;

    /**
     * 交割物品
     */
    @Data
    public static class RentItemVo{
        /**
         * 类型
         */
        private String itemType;
        /**
         * 物品名称
         */
        private String itemName;
        /**
         * 物品数量
         */
        private Integer itemNum;
        /**
         * 物品价格
         */
        private Double price;

    }

}
