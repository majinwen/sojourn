package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class IntellectWattMeterSnapshotEntity extends BaseEntity {

    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * 业务标识
     */
    private String fid;
    /**
     * 合同code
     */
    private String rentCode;

    /**
     * 项目id
     */
    private String projectId;
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 应收账单fid
     */
    private String billFid;
    /**
     * 账单编号
     */
    private String billNum;

    /**
     * 智能平台回调标识
     */
    private String serviceId;
    /**
     * 当次充值量
     */
    private Double amount;

    /**
     * 充值前示数
     */
    private Double startReading;

    /**
     * 充值后示数
     */
    private Double endReading;
    /**
     * 重试次数
     */
    private Integer tryTimes;

    /**
     * 充值单价
     */
    private Double price;

    /**
     * 充值状态  10=待充值 （a. 充值接口直接返回失败  b. 本身业务异常造成）  11=充值中  12=充值成功  13=充值失败
     */
    private Integer statu;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人类型   0=管家  1=客户
     */
    private Integer createType;

    /**
     * 创建人id  客户uid/如果是管家 存员工号
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建时间
     */
    private Date updateTime;

    /**
     * 是否删除  0=不删除  1=删除
     */
    private Integer isDel;

}