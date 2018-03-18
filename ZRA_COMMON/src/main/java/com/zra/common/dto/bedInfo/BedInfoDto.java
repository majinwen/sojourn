package com.zra.common.dto.bedInfo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by PC on 2016/9/5.
 * dto对象，用于在ms和api层进行对象的传递--xiaona--2016年9月5日10:53:10
 *
 * Data 可以省去get和set方法的编写
 * NoArgsConstructor 生成无参的构造方法
 */
@Data
@NoArgsConstructor
public class BedInfoDto {

    /**
     *主键
     */
    private Integer id;
    /**
     *业务bid
     */
    private String bedBid;
    /**
     * 之前没有bid的只能是关联之前的id就可以了
     */
    private String roomId;

    /**
     * 关联配置方案的业务bid
     */
    private String standardBid;

    /**
     * 关联配置方案的名称
     */
    private String name;
    /**
     * 床位编码
     */
    private String code;

    /**
     * 长租价格
     */
    private Double longPrice;
    /**
     * 短租价格
     */
    private Double shortPrice;
    /**
     * 是否可短租
     */
    private String shortRent;
    /**
     * 当前床位状态,和房间的状态机是一样的
     */
    private String currentState;

    /**
     * 是否删除 0：未删除。1：已删除
     */
    private Byte isDeleted;

    /**
     * 是否有效 0：有效。1：无效
     */
    private Byte isValid;

    /**
     * 是否启用 0：启用，1：禁用
     */
    private Byte isEnabled;

    /**
     * 创建者
     */
    private String createrId;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改者
     */
    private String updaterId;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 删除者
     */
    private String deleterId;

    /**
     * 删除时间
     */
    private Date deletedTime;
}
