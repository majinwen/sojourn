package com.zra.repair.entity.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Date;

/**
 * <p>新建报修单，传输参数mapping</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author fengbo.yue
 * @Date Created in 2017年09月29日 17:23
 * @since 1.0
 * @version 1.0
 */
@Data
@ToString
public class ZryRepairOrderDto implements Serializable{

    private static final long serialVersionUID = 4358628076842596866L;

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
     */
    private Integer orderStatus;

    /**
     * 创建人fid
     */
    private String createFid;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后更新时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0：否，1：是
     */
    private Integer isDel;

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
     * 备注
     */
    private String remark;
    
    /**
     * 城市编码
     */
    private String cityCode;

    /**==================================报修操作日志===============================*/
    /**
     * 维修单fid
     */
    private String repairOrder;

    /**
     * 前状态
     */
    private Integer fromStatus;

    /**
     * 后状态
     */
    private Integer toStatus;

}