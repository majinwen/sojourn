package com.ziroom.zrp.service.houses.dto;

import com.ziroom.zrp.houses.entity.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomSmartLockDto  extends PageRequest implements Serializable{

    private static final long serialVersionUID = 8437692131713349642L;
    /**
     * 主键自增
     */
    private Integer id;

    /**
     * 业务主键
     */
    private String fid;

    /***
     * 项目Id
     */
    private String projectId;

    /**
     * 合同标识
     */
    private String contractId;

    /**
     * 房间标识
     */
    private String roomId;

    /**
     * 入住人标识
     */
    private String checkinPersonId;

    /**
     * 智能锁回调标识
     */
    private String serviceId;

    /**
     * 更新密码（旧密码）
     */
    private String updatePwd;

    /**
     * 当前密码
     */
    private String pwd;

    /**
     * 当前发送人手机号
     */
    private String phone;

    /**
     * 操作人系统号
     */
    private String handlerCode;

    /**
     * 操作人姓名
     */
    private String handlerName;

    /**
     * 回调状态	0 获取中，1 成功，2 失败
     */
    private Integer status;

    /**
     * 生效时间
     */
    private Date startTime;

    /**
     * 失效时间
     */
    private String endTime;

    /**
     * 回调时间
     */
    private String callbackTime;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 是否删除(0:未删除；1:已删除)
     */
    private Integer isDel;

    /**
     * 使用人类型0=其他 1=管家 2=保洁 3=维修
     */
    private Integer userType;

    /**
     * 使用人姓名
     */
    private String userName;

    /**
     * 操作时间 开始-结束
     */
    private String createStartTime;
    private String createEndTime;

    /**
     * 失效时间 开始-结束
     */
    private String invalidStartTime;
    private String invalidEndTime;
}