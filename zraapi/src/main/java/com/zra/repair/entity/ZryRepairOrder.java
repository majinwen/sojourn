package com.zra.repair.entity;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Data
public class ZryRepairOrder implements Serializable {

    private static final long serialVersionUID = 4849914813792485637L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 维修单号
     */
    private String orderSn;

    /**
     * 业务类型：0自如寓，1：自如驿
     */
    private Integer businessType;

    /**
     * 项目fid
     */
    private String itemFid;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 报修区域类型：0：公区，1：房间
     */
    private Integer areaType;

    /**
     * 房间号
     */
    private String roomNum;

    /**
     * 大类标示
     */
    private String categoryCode;

    /**
     * 大类名称
     */
    private String categoryName;

    /**
     * 收房合同号 一个寓统一一个
     */
    private String contractCode;

    /**
     * 承租合同号 公区和收房一样
     */
    private String rentContractCode;

    /***
     * 房源编号 数字 公区和收房一样
     */
    private String houseCode;

    /***
     * 房源编号 字母 公区和收房一样
     */
    private String houseSourceCode;

    /***
     * 报修人电话
     */
    private String repairsMobile;

    /***
     * 报修人姓名
     */
    private String repairsMan;

    /**
     * 物品标示
     */
    private String goodsCode;

    /**
     * 物品名称
     */
    private String goodsName;

    /**
     * 区域标示
     */
    private String areaCode;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 故障描述标示
     */
    private String failureId;

    /**
     * 故障描述
     */
    private String failureDesc;

    /**
     * 上门时间标示
     */
    private String visitTimeFlag;

    /**
     * 上门时间
     */
    private String visitTime;

    /**
     * 上门联系人
     */
    private String visitLinkman;

    /**
     * 上门联系方式
     */
    private String visitMobile;

    /**
     * 工单状态
     * 0:待接单  5:待上门  10:已取消 15:待生成方案  20:待确认方案 25:待办结  30:待验收  35:验收通过  40:已完成
     */
    private Integer orderStatus;

    /**
     * 创建人fid
     */
    private String createFid;

    /**
     * 创建时间
     */
    private Timestamp createDate;

    /**
     * 最后更新时间
     */
    private Timestamp lastModifyDate;

    /***
     * 备注
     */
    private String remark;

    /**
     * 是否删除 0：否，1：是
     */
    private Integer isDel;
    
    /**
     * 城市编码
     */
    private String cityCode;

    /***
     * 可以被废弃（取消）的状态
     * 0: 待接单；5：待上门
     */
    @JsonIgnore
    private static final List<Integer> CAN_BE_CANCEL_STATUS = Arrays.asList(0,5);
    // 可以被取消的订单状态
    public boolean cancelOrCannot(){

        boolean yesOrNo = false;

        if (this.getOrderStatus() != null) {
            if (CAN_BE_CANCEL_STATUS.contains(this.getOrderStatus())){
                yesOrNo = true;
            }
        }
        return yesOrNo;
    }

    /**
     * 是否是取消状态 ：orderStatus == 10
     * @return
     */
    public boolean cancelled() {
        return 10 == this.getOrderStatus();
    }

    /**
     * 工单状态
     * 0:待接单  5:待上门  10:已取消 15:待生成方案  20:待确认方案  25:待办结  30:待验收  35:验收通过  40:已完成
     */
    @Getter
    @ToString
    public enum OrderStatus {
        INIT(0,"待接单"),
        WATING_SERVICE(5, "待上门"),
        CANCEL(10, "已取消"),
        WATING_MAKE_SOLUTIONS(15, "待生成方案"),
        WATING_CONFIRMED_SOLUTIONS(20, "待确认方案"),
        WORKING(25, "待办结"),
        WATING_CONFIRMED(30, "待验收"),
        ACCEPTANCE(35, "验收通过"),
        COMPLETE(40, "已完成");

        private Integer status;
        private String statusCN;

        OrderStatus(Integer status, String statusCN) {
            this.status = status;
            this.statusCN = statusCN;
        }

        public static OrderStatus of(Integer status) {
            if (status == null) {
                throw new NullPointerException("OrderStatus getStatusCN require param status ,type of integer!");
            }
            for (OrderStatus os : OrderStatus.values()
                 ) {
                if (os.getStatus().compareTo(status) == 0) {
                    return os;
                }
            }
            return null;
        }
    }
}