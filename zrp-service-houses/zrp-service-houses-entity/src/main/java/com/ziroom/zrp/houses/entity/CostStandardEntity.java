package com.ziroom.zrp.houses.entity;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
/**
 * 水电费标准
 * @author jixd
 * @created 2017年09月25日 17:57:10
 * @param
 * @return
 */
@Data
public class CostStandardEntity extends BaseEntity{

    private static final long serialVersionUID = 2543597708000755419L;

    private String fid;

    /**
     * 项目Id
     */
    private String projectid;

    /**
     * 水电表类型(0水 1电)
     */
    private String fmetertype;

    /**
     * 单价
     */
    private Double fprice;

    /**
     * 抄表周期
     */
    private Integer readCycle;

    /**
     * 备注
     */
    private String fremark;

    /**
     * 是否删除 1删除 0未删除
     */
    private Integer fisdel;

    /**
     * 是否有效  1有效 0无效
     */
    private Integer fvalid;

    /**
     * 创建人
     */
    private String createrid;

    private String updaterid;

    private Date fcreatetime;

    private Date fupdatetime;

    /**
     * 关联城市表
     */
    private String cityid;
}